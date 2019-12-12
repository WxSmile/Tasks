package com.tasks.utils;

import java.util.List;

/**
 * Author: murphy
 * Description:
 */
public interface BindableAdapter<T> {

    void setData(List<T> source);
}
