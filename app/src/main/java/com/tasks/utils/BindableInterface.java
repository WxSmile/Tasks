package com.tasks.utils;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public interface BindableInterface<DATASOURCE, VIEWMODEL> {

    void setData(List<DATASOURCE> source);

    void setViewModel(VIEWMODEL viewModel);

}
