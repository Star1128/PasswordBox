package com.example.passwordbox.ui;

import android.content.Intent;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordbox.R;
import com.example.passwordbox.model.Item;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/10
 * @version 1.0.0
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> mItemList;

    public ItemAdapter(List<Item> list) {
        mItemList = list;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.mView.setOnClickListener((View v)->{//设置子项点击响应
            int position=holder.getAdapterPosition();
            Item item=mItemList.get(position);
            Intent intent=new Intent(parent.getContext(), ShowActivity.class);
            intent.putExtra("item",item.getAppName());
            parent.getContext().startActivity(intent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = mItemList.get(position);
        holder.mTextView.setText(item.getAppName());
        holder.mImageView.setImageResource(item.getImageId());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final ImageView mImageView;
        private final TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = itemView.findViewById(R.id.keyImage);
            mTextView = itemView.findViewById(R.id.appName);
        }
    }
}
