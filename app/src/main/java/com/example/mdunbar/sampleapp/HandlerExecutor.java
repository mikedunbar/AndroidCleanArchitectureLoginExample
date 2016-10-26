package com.example.mdunbar.sampleapp;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.Executor;

import android.os.Handler;
import android.os.Looper;


/**
 * An {@link Executor} that wraps and posts {@link Runnable}'s to an Android {@link Handler}. We
 * use this to keep the Presenter and Model layers decoupled from Android APIs.
 *
 * @author Mike Dunbar
 */
public class HandlerExecutor implements Executor {
    private final Handler handler;

    /**
     * Create a new HandlerExecutor.
     *
     * @param handler Handler to wrap, typically one attached to UI thread obtained via {@link Looper#getMainLooper()}
     */
    public HandlerExecutor(Handler handler) {
        this.handler = checkNotNull(handler);
    }

    /**
     * Post {@link Runnable} command to the supplied {@link Handler}
     *
     * @param command Command to run
     */
    @Override public void execute(Runnable command) {
        handler.post(command);
    }
}