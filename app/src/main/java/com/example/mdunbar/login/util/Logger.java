package com.example.mdunbar.login.util;

/**
 * @author Mike Dunbar
 */

public interface Logger {
    void log(String tag, String message);

    void log(String tag, String message, Throwable throwable);
}
