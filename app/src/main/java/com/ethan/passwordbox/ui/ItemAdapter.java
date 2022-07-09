package com.ethan.passwordbox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.passwordbox.POJO.Item;
import com.ethan.passwordbox.R;

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
        holder.mAppName.setText(item.getAppName());
        holder.mUserName.setText(item.getUserName());
        holder.mImageView.setImageResource(importanceId2ImageId(item.getImportanceId()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int importanceId2ImageId(int importanceId) {
        int imageId;
        switch (importanceId) {
            case 1:
                imageId = R.drawable.key_red_v2;
                break;
            case 2:
                imageId = R.drawable.key_orange_v2;
                break;
            case 3:
                imageId = R.drawable.key_blue_v2;
                break;
            default:
                imageId = R.drawable.key_default;
        }
        return imageId;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final ImageView mImageView;
        private final TextView mAppName;
        private final TextView mUserName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = itemView.findViewById(R.id.keyImage);
            mAppName = itemView.findViewById(R.id.appName);
            mUserName=itemView.findViewById(R.id.userName);
        }
    }
}
