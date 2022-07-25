package com.ethan.passwordbox.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ethan.passwordbox.POJO.Item;
import com.ethan.passwordbox.R;
import com.ethan.passwordbox.utils.IdUtil;

import java.util.List;

/**
 * NOTE:
 *
 * @author Ethan 2022/7/13
 */
public class SwipeItemAdapter extends BaseQuickAdapter<Item, SwipeItemAdapter.ViewHolder> {
    public SwipeItemAdapter(int layoutResId, @Nullable List<Item> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull ViewHolder viewHolder, Item item) {
        viewHolder.setText(R.id.appName, item.getAppName());
        viewHolder.setText(R.id.userName, item.getUserName());
        viewHolder.setImageResource(R.id.keyImage, IdUtil.importanceId2ImageId(item.getImportanceId()));
    }

    static class ViewHolder extends BaseViewHolder {

        public ViewHolder(@NonNull View view) {
            super(view);
        }
    }
}
