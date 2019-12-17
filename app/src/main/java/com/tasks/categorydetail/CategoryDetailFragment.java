package com.tasks.categorydetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.tasks.R;
import com.tasks._utils.CircleRevealHepler;
import com.tasks.databinding.FragmentCategoryDetailBinding;

import static com.tasks._utils.RecyclerViewItemAnimatorUtil.dissallowChangeAnimations;


public class CategoryDetailFragment extends Fragment {

    private String category;
    private CategoryDetailViewModel mViewModel;
    private FragmentCategoryDetailBinding dataBinding;
    private CircleRevealHepler circleRevealHepler;
    private View.OnLayoutChangeListener onLayoutChangeListener;

    public static CategoryDetailFragment newInstance(String category, CircleRevealHepler circleRevealHepler) {
        CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();
        categoryDetailFragment.setCircleRevealHepler(category, circleRevealHepler);
        return categoryDetailFragment;
    }

    private void setCircleRevealHepler(String category, CircleRevealHepler circleRevealHepler) {
        this.category = category;
        this.circleRevealHepler = circleRevealHepler;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = ViewModelProviders.of(requireActivity()).get(CategoryDetailViewModel.class);
        mViewModel.setCategory(category);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_detail, container, false);
        circleRevealHepler.setTarget(dataBinding.getRoot());
        onLayoutChangeListener = this::onLayoutChanged;
        dataBinding.getRoot().addOnLayoutChangeListener(onLayoutChangeListener);
        return dataBinding.getRoot();
    }

    private void onLayoutChanged(View view, int left, int top, int right, int bottom, int oldLeft,
                                 int oldTop, int oldRight, int oldBottom) {
        dataBinding.getRoot().removeOnLayoutChangeListener(onLayoutChangeListener);
        circleRevealHepler.startCircleRevealAnimator();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleRevealHepler.setTarget(view);
        circleRevealHepler.setListenerAdapter(this::popBackStack);
        dataBinding.toolbar.setNavigationOnClickListener(this::onNavCloseClicked);

        dataBinding.setViewModel(mViewModel);
        dataBinding.setCategoryName(category);
        dataBinding.setLifecycleOwner(this);

        dissallowChangeAnimations(dataBinding.rcvTasks.getItemAnimator());

        dataBinding.rcvTasks.setAdapter(new CategorySectionAdapter());

        mViewModel.fetchCategoryStatus();
        mViewModel.fetchCategoryTasks();
    }

    private void popBackStack() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void onNavCloseClicked(View view) {
        hide();
    }

    public void hide() {
        circleRevealHepler.startCircleRevealAnimator();
    }

}
