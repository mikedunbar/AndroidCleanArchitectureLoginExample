package com.example.mdunbar.sampleapp;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract base class for all use cases, handles the common need of being able to perform the
 * use case and handle the results on 2 separate threads. For an Android app, we want to perform
 * the core work in a background thread, freeing up the UI thread until we are ready to update
 * the UI with results.
 *
 *
 * Defines a template method - execute, which calls 2 abstract methods implemented in subclasses - {@link #performUseCase(Object)} and
 * {@link #handleResults(Object, Results)}. Allows performUseCase and handleResults to be execute in different threads or the same -
 * controlled by the constructor arguments.
 *
 * @param <ParamT> Parameter for the interaction, usually a struct-like thing
 * @param <ResultT> Result returned by the interaction
 *
 * @author Mike Dunbar
 */
public abstract class AbstractUseCase<ParamT, ResultT> {
    public static final ExecutorService BACKGROUND_EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private final ListeningExecutorService performUseCaseRunner;
    private final Executor handleResultsRunner;

    /**
     *
     * @param performUseCaseRunner Runs the {@link #performUseCase(Object)} task.
     * @param handleResultsRunner Runs the {@link #handleResults(Object, Results)} task.
     */
    protected AbstractUseCase(ExecutorService performUseCaseRunner, Executor handleResultsRunner) {
        this.performUseCaseRunner = checkNotNull(MoreExecutors.listeningDecorator(performUseCaseRunner));
        this.handleResultsRunner = checkNotNull(handleResultsRunner);
    }

    /**
     * Template method - calls {@link #performUseCase(Object)}, waits for completion, and passes results to {@link #handleResults(Object, Results)}.
     *
     * @param param Parameter for the interaction
     */
    protected void execute(final ParamT param) {
        final ListenableFuture<ResultT> future = performUseCaseRunner.submit(new Callable<ResultT>() {
            @Override public ResultT call() throws Exception {
                return performUseCase(param);
            }
        });
        future.addListener(new Runnable() {
            @Override public void run() {
                handleResults(param, new Results<>(future));
            }
        }, handleResultsRunner);
    }

    /**
     * Ran on performUseCaseRunner.
     *
     * @param param Parameter for the interaction, usually a struct-like thing
     * @return Results of the interaction, passed to handleResults.
     * @throws Exception if unable to do interaction
     */
    protected abstract ResultT performUseCase(ParamT param) throws Exception;

    /**
     * Ran on handleResultsRunner, after {@link #performUseCase(Object)} completes.
     *
     * @param param Parameter for the interaction, usually a struct-like thing
     * @param results Returned from performUseCase
     */
    protected abstract void handleResults(ParamT param, Results<ResultT> results);

    /**
     * Returned from {@link #performUseCase(Object)}, passed to {@link #handleResults(Object, Results)}.
     *
     * @param <ResultT> Results of interaction, can be Void if none.
     */
    public static class Results<ResultT> {
        private final Future<ResultT> future;

        private Results(Future<ResultT> future) {
            this.future = future;
        }

        /**
         *
         * @return results of the interaction
         * @throws Exception if one occurred during the interaction
         */
        public ResultT get() throws Exception {
            try {
                return future.get();
            } catch (ExecutionException e) {
                Throwables.propagateIfPossible(e.getCause(), Exception.class);
                throw Throwables.propagate(e.getCause());
            }
        }
    }
}