package com.tasks.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks._utils.BindableInterface;
import com.tasks.data.model.TaskModel;
import com.tasks.databinding.ItemHotTaskBinding;
import com.tasks.tasks.viewmodel.TasksViewModel;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public class HotTasksAdapter extends RecyclerView.Adapter<HotTasksAdapter.HotTaskViewHolder>
        implements BindableInterface<TaskModel, TasksViewModel> {

    private List<TaskModel> source;
    private TasksViewModel viewModel;

    @Override
    public void setData(List<TaskModel> source) {
        int oldItemCount = getItemCount();
        this.source = source;

        if (getItemCount() >= oldItemCount) {
            notifyItemRangeChanged(0, getItemCount());
        }else {
            notifyItemRangeRemoved(0, getItemCount());
        }
    }

    @Override
    public void setViewModel(TasksViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public HotTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemHotTaskBinding itemTaskBinding = ItemHotTaskBinding.inflate(layoutInflater, parent, false);
        return new HotTaskViewHolder(itemTaskBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotTaskViewHolder holder, int position) {
        TaskModel taskModel = source.get(position);
        holder.bind(taskModel);
    }

    @Override
    public int getItemCount() {
        return source != null ? source.size() : 0;
    }

    public class HotTaskViewHolder extends RecyclerView.ViewHolder {

        private ItemHotTaskBinding itemTaskBinding;

        HotTaskViewHolder(ItemHotTaskBinding itemTaskBinding) {
            super(itemTaskBinding.getRoot());
            this.itemTaskBinding = itemTaskBinding;
        }

        void bind(TaskModel taskModel) {
            itemTaskBinding.setViewHolder(this);
            itemTaskBinding.setModel(taskModel);
        }

        public void onHotTaskItemClicked(TaskModel taskModel) {
            if (taskModel.isCompleted()) return;

            taskModel.setCompleted(true);
            int position = source.indexOf(taskModel);
            notifyItemChanged(position);
            itemTaskBinding.rbStatus.postDelayed(this::updateTaskStatusCompleted, 500);
        }

        private void updateTaskStatusCompleted() {
            TaskModel taskModel = itemTaskBinding.getModel();
            notifyItemRemoved(source.indexOf(taskModel));
            source.remove(taskModel);
            viewModel.updateTaskStatusCompleted(taskModel);
        }
    }
}
