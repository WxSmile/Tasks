package com.tasks.categorydetail;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.R;
import com.tasks._utils.BindableInterface;
import com.tasks.data.model.TaskModel;
import com.tasks.databinding.ItemCategoryDetailTaskBinding;
import com.tasks.databinding.ItemCategoryDetailTaskGroupBinding;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

/**
 * Author: murphy
 * Description:
 */
public class TaskModelSection extends Section implements BindableInterface<TaskModel, CategoryDetailViewModel> {

    private List<TaskModel> source;
    private CategoryDetailViewModel viewModel;
    private String group;
    private CategorySectionAdapter adapter;

    public TaskModelSection(String group, CategorySectionAdapter adapter) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_category_detail_task)
                .headerResourceId(R.layout.item_category_detail_task_group)
                .build()
        );
        this.group = group;
        this.adapter = adapter;
    }

    @Override
    public int getContentItemsTotal() {
        return source == null ? 0 : source.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        ItemCategoryDetailTaskBinding bind = ItemCategoryDetailTaskBinding.bind(view);
        return new ItemViewHolder(bind);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        ItemCategoryDetailTaskGroupBinding bind = ItemCategoryDetailTaskGroupBinding.bind(view);
        return new HeaderViewHolder(bind);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((ItemViewHolder) viewHolder).bind(source.get(i));
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {
        ((HeaderViewHolder) viewHolder).bind(group);
    }

    @Override
    public void setData(List<TaskModel> source) {

        int oldItemCount = getContentItemsTotal();

        this.source = source;

        SectionAdapter sectionAdapter = adapter.getAdapterForSection(group);
        if (getContentItemsTotal() >= oldItemCount) {
            sectionAdapter.notifyItemRangeChanged(0, getContentItemsTotal());
        }else {
            sectionAdapter.notifyItemRangeRemoved(0, getContentItemsTotal());
        }
    }

    @Override
    public void setViewModel(CategoryDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemCategoryDetailTaskBinding dataBinding;

        public ItemViewHolder(ItemCategoryDetailTaskBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public void bind(TaskModel taskModel) {
            dataBinding.setModel(taskModel);
            dataBinding.setViewHolder(this);
        }

        public void onItemClicled(TaskModel model) {

            if (dataBinding.srlContainer.isOpened()) {
                dataBinding.srlContainer.close(true);
                return;
            }

            model.setCompleted(!model.isCompleted());
            SectionAdapter sectionAdapter = adapter.getAdapterForSection(group);
            sectionAdapter.notifyItemChanged(source.indexOf(model));

            dataBinding.rbStatus.postDelayed(this::updateTaskStatus, 500);
        }

        public void onDeleteClicked(TaskModel taskModel) {
            dataBinding.srlContainer.close(true);

            int position = source.indexOf(taskModel);
            SectionAdapter sectionAdapter = adapter.getAdapterForSection(group);
            sectionAdapter.notifyItemRemoved(position);
            source.remove(taskModel);

            dataBinding.rbStatus.postDelayed(this::deleteTask, 500);
        }

        private void updateTaskStatus() {
            viewModel.updateTaskStatus(dataBinding.getModel());
        }

        private void deleteTask() {
            viewModel.deleteTask(dataBinding.getModel());
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ItemCategoryDetailTaskGroupBinding dataBinding;

        public HeaderViewHolder(@NonNull ItemCategoryDetailTaskGroupBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public void bind(String category) {
            dataBinding.setGroup(category);
        }
    }
}
