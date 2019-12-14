package com.tasks.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.TaskModel;
import com.tasks.databinding.ItemTaskBinding;
import com.tasks.tasks.viewmodel.TasksViewModel;
import com.tasks.utils.BindableInterface;

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
        this.source = source;
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public void setViewModel(TasksViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public HotTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTaskBinding itemTaskBinding = ItemTaskBinding.inflate(layoutInflater, parent, false);
        itemTaskBinding.setViewModel(viewModel);
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

        private ItemTaskBinding itemTaskBinding;

        HotTaskViewHolder(ItemTaskBinding itemTaskBinding) {
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
            itemTaskBinding.flRadio.postDelayed(() -> updateTaskModel(taskModel), 500);
        }

        private void updateTaskModel(TaskModel taskModel) {
            notifyItemRemoved(source.indexOf(taskModel));
            source.remove(taskModel);
            itemTaskBinding.getViewModel().updateTaskModel(taskModel);
        }
    }
}
