package com.tasks.domain.interactor;

import androidx.lifecycle.LiveData;

public interface SingleUseCase<R, P> {

    LiveData<R> execute(P parameter);
}
