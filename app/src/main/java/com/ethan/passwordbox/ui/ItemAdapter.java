package com.ethan.passwordbox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.passwordbox.R;
import com.ethan.passwordbox.POJO.Item;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/10
 * @version 1.0.0
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> mList;

    public ItemAdapter(List<Item> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.mView.setOnClickListener((View v) -> {
            int position = holder.getBindingAdapterPosition();
            Item item = mList.get(position);
            Intent intent = new Intent(parent.getContext(), ShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("app", item);
            intent.putExtra("info", bundle);
            parent.getContext().startActivity(intent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = mList.get(position);
        holder.mTextView.setText(item.getAppName());
        holder.mImageView.setImageResource(item.getImageId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
