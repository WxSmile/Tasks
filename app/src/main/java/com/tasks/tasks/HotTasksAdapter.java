package com.tasks.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.TaskModel;
import com.tasks.databinding.ItemTaskBinding;
import com.tasks.utils.BindableAdapter;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public class HotTasksAdapter extends RecyclerView.Adapter<HotTasksAdapter.HotTaskViewHolder>
        implements BindableAdapter<TaskModel> {

    private List<TaskModel> source;

    @Override
    public void setData(List<TaskModel> source) {
        this.source = source;
        notifyItemRangeChanged(0, getItemCount());
    }

    @NonNull
    @Override
    public HotTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTaskBinding itemTaskBinding = ItemTaskBinding.inflate(layoutInflater, parent, false);
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

    class HotTaskViewHolder extends RecyclerView.ViewHolder {

        private ItemTaskBinding itemTaskBinding;

        HotTaskViewHolder(ItemTaskBinding itemTaskBinding) {
            super(itemTaskBinding.getRoot());
            this.itemTaskBinding = itemTaskBinding;
        }

        void bind(TaskModel taskModel) {
            itemTaskBinding.setModel(taskModel);
        }
    }
}
