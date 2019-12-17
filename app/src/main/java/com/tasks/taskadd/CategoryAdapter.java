package com.tasks.taskadd;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.R;
import com.tasks.databinding.ItemCategoryBinding;
import com.tasks._utils.BindableInterface;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements BindableInterface<CategoryModelWrapper, AddTaskViewModel> {

    private List<CategoryModelWrapper> source;
    private AddTaskViewModel viewModel;

    @Override
    public void setData(List<CategoryModelWrapper> source) {
        this.source = source;
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public void setViewModel(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false);
        return new CategoryViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(source.get(position));
    }

    @Override
    public int getItemCount() {
        return source == null ? 0 : source.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ItemCategoryBinding dataBinding;

        public CategoryViewHolder(ItemCategoryBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public void bind(CategoryModelWrapper model) {
            dataBinding.setModel(model);
            dataBinding.setViewHolder(this);
        }

        public void onCategtoryItemClick(CategoryModelWrapper categoryModel) {

            categoryModel.setSelect(!categoryModel.isSelect());
            if (!categoryModel.isSelect()) {
                notifyItemChanged(source.indexOf(categoryModel));
                viewModel.setTaskCategory("");
                return;
            }


            for (CategoryModelWrapper item : source) {
                if (categoryModel != item) {
                    item.setSelect(!categoryModel.isSelect());
                }
            }
            notifyItemRangeChanged(0, getItemCount());

            viewModel.setTaskCategory(categoryModel.getCategoryModel().getCategory());
        }
    }
}
