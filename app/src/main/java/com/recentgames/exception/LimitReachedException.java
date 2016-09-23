package com.recentgames.exception;

public class LimitReachedException extends Error {

    public LimitReachedException(String message) {
        super(message);
    }
}
