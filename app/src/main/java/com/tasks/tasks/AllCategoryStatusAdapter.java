package com.tasks.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.databinding.ItemCategoryStatusBinding;
import com.tasks.utils.BindableAdapter;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public class AllCategoryStatusAdapter extends RecyclerView.Adapter<AllCategoryStatusAdapter.CategoryStatusViewHolder>
        implements BindableAdapter<CategoryStatusModel> {

    private List<CategoryStatusModel> source;

    @Override
    public void setData(List<CategoryStatusModel> source) {
        this.source = source;
        notifyItemRangeChanged(0, getItemCount());
    }

    @NonNull
    @Override
    public CategoryStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryStatusBinding statusBinding = ItemCategoryStatusBinding.inflate(inflater, parent, false);
        return new CategoryStatusViewHolder(statusBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryStatusViewHolder holder, int position) {
        holder.bind(source.get(position));
    }

    @Override
    public int getItemCount() {
        return source != null ? source.size() : 0;
    }

    class CategoryStatusViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryStatusBinding categoryStatusBinding;

        CategoryStatusViewHolder(ItemCategoryStatusBinding categoryStatusBinding) {
            super(categoryStatusBinding.getRoot());
            this.categoryStatusBinding = categoryStatusBinding;
        }

        void bind(CategoryStatusModel categoryStatusModel) {
            categoryStatusBinding.setModel(categoryStatusModel);
        }
    }
}
