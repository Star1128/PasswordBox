package com.ethan.passwordbox.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.passwordbox.R;
import com.ethan.passwordbox.data.local.sp.SPManager;
import com.ethan.passwordbox.pojo.MatchingItem;
import com.ethan.passwordbox.preferences.CustomMatching;

import java.util.List;

/**
 * @author Ethan 2022/9/28
 */
public class MatchingItemAdapter extends RecyclerView.Adapter<MatchingItemAdapter.MatchingViewHolder> {

    private final List<MatchingItem> mList;

    public MatchingItemAdapter(List<MatchingItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public MatchingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matching, parent, false);
        return new MatchingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchingViewHolder holder, int position) {
        MatchingItem item = mList.get(position);
        holder.mKey.setText(item.getKey());
        holder.mValue.setText(item.getValue());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在 UI 上删除
                mList.remove(holder.getBindingAdapterPosition());
                notifyItemRemoved(holder.getBindingAdapterPosition());
                // 在 Map 中删除
                CustomMatching.unRegisterMatching(item.getKey());
                // 在 SP 中删除
                SPManager.deleteAsync(item.getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected static class MatchingViewHolder extends RecyclerView.ViewHolder {

        private final TextView mKey;
        private final TextView mValue;
        private final ImageButton mDelete;

        public MatchingViewHolder(@NonNull View itemView) {
            super(itemView);
            mKey = itemView.findViewById(R.id.tv_matching_key);
            mValue = itemView.findViewById(R.id.tv_matching_value);
            mDelete=itemView.findViewById(R.id.ib_delete);
        }
    }
}
