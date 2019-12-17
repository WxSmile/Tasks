package com.tasks.categorydetail;

import com.tasks._utils.BindableInterface;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Author: murphy
 * Description:
 */
public class CategorySectionAdapter extends SectionedRecyclerViewAdapter implements BindableInterface<Void, CategoryDetailViewModel> {


    private CategoryDetailViewModel viewModel;

    @Override
    public void setData(List<Void> source) {
        //nothing to do
    }

    @Override
    public void setViewModel(CategoryDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public CategoryDetailViewModel getViewModel() {
        return viewModel;
    }
}
