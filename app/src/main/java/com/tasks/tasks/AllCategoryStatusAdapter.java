package com.tasks.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.databinding.ItemCategoryStatusBinding;
import com.tasks.tasks.viewmodel.TasksViewModel;
import com.tasks._utils.BindableInterface;
import com.tasks._utils.CircleRevealHepler;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public class AllCategoryStatusAdapter extends RecyclerView.Adapter<AllCategoryStatusAdapter.CategoryStatusViewHolder>
        implements BindableInterface<CategoryStatusModel, TasksViewModel> {

    private List<CategoryStatusModel> source;
    private TasksViewModel viewModel;
    private FragmentActivity fragmentActivity;

    public AllCategoryStatusAdapter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public void setData(List<CategoryStatusModel> source) {
        this.source = source;
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public void setViewModel(TasksViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CategoryStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryStatusBinding statusBinding = ItemCategoryStatusBinding.inflate(inflater, parent, false);
        statusBinding.setViewModel(viewModel);
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

    public class CategoryStatusViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryStatusBinding categoryStatusBinding;

        CategoryStatusViewHolder(ItemCategoryStatusBinding categoryStatusBinding) {
            super(categoryStatusBinding.getRoot());
            this.categoryStatusBinding = categoryStatusBinding;
            categoryStatusBinding.setViewHolder(this);
        }

        void bind(CategoryStatusModel categoryStatusModel) {
            categoryStatusBinding.setModel(categoryStatusModel);
        }

        public void onCategoryStatusItemClicked(CategoryStatusModel model) {
            CircleRevealHepler circleRevealHepler = new CircleRevealHepler();
            circleRevealHepler.setCenterView(categoryStatusBinding.clContent);
            if (fragmentActivity instanceof OnCategoryCardListener) {
                ((OnCategoryCardListener) fragmentActivity).onCategoryCardClicked(model.getCategory(), circleRevealHepler);
            }

        }
    }

    public interface OnCategoryCardListener {
        void onCategoryCardClicked(String category, CircleRevealHepler hepler);
    }
}
