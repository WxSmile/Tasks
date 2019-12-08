package com.tasks.domain.interactor;

import io.reactivex.Completable;

public interface CompletableUseCase<P> {

    Completable execute(P parameter);
}
