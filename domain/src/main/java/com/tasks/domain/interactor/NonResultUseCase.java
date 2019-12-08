package com.tasks.domain.interactor;

public interface NonResultUseCase<P> {

    void execute(P parameter);
}
