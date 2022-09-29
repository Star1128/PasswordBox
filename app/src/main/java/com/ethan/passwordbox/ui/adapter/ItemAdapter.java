package com.ethan.passwordbox.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.passwordbox.R;
import com.ethan.passwordbox.config.Cons;
import com.ethan.passwordbox.data.local.db.DBManager;
import com.ethan.passwordbox.encrypt.AES;
import com.ethan.passwordbox.pojo.Item;
import com.ethan.passwordbox.utils.IdUtil;

import java.util.List;

/**
 * @author Ethan 2021/8/10
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> mList;

    public ItemAdapter(List<Item> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getBindingAdapterPosition();
                Item item = mList.get(position);
                CharSequence userName = holder.mUserName.getText();
                try {
                    holder.mUserName.setText(AES.decrypt(item.getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 1s 之后再将原 UserName 设置回去
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.mUserName.setText(userName);
                    }
                }, Cons.Time.PSW_SHOW_TIME);
                return true;
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                Item item = mList.get(position);
                deleteItem(item, v.getContext(), position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = mList.get(position);
        holder.mAppName.setText(item.getAppName());
        holder.mUserName.setText(item.getUserName());
        holder.mImageView.setImageResource(IdUtil.importanceId2ImageId(item.getImportanceId()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void deleteItem(Item item, Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除确认");
        builder.setMessage("删除后不可恢复哦~");
        builder.setCancelable(true); // 使用back键可以取消该对话框
        builder.setPositiveButton("必须删！", (DialogInterface dialogInterface, int which) -> {
            // 在数据库中删除
            deleteItemDB(item);
            // 在当前列表UI中删除
            mList.remove(position);
            notifyItemRemoved(position);
        });
        builder.setNegativeButton("再想想", (DialogInterface dialogInterface, int which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        // 很特殊的一点，要在show()之后设置按钮颜色
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    public void deleteItemDB(Item item) {
        DBManager.deleteItem(item);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final ImageView mImageView;
        private final TextView mAppName;
        private final TextView mUserName;
        private final ImageButton mDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = itemView.findViewById(R.id.keyImage);
            mAppName = itemView.findViewById(R.id.appName);
            mUserName = itemView.findViewById(R.id.userName);
            mDelete = itemView.findViewById(R.id.delete);
        }
    }
}
