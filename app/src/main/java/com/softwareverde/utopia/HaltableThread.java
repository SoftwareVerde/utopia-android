package com.softwareverde.utopia;

public class HaltableThread extends Thread {
    private final Object _mutex = new Object();
    private Boolean _shouldContinue = true;
    private Long _sleepTime = 500L;
    private java.lang.Runnable _runnable;
    private ShouldContinueRunnable _shouldContinueShouldContinueRunnable;

    public interface ShouldContinueRunnable {
        Boolean run();
    }

    public HaltableThread() { }
    public HaltableThread(final ShouldContinueRunnable shouldContinueShouldContinueRunnable) { _shouldContinueShouldContinueRunnable = shouldContinueShouldContinueRunnable; }
    public HaltableThread(final java.lang.Runnable runnable) { _runnable = runnable; }

    public final void setSleepTime(final Long milliseconds) {
        _sleepTime = milliseconds;
    }

    public Boolean shouldContinue() {
        if (_shouldContinueShouldContinueRunnable != null) {
            final Boolean runnableResult = _shouldContinueShouldContinueRunnable.run();

            if (! runnableResult) {
                return false;
            }
        }
        else if (_runnable != null) {
            _runnable.run();
        }

        return true;
    }

    @Override
    public final void run() {
        Boolean shouldContinue;
        synchronized (_mutex) {
            shouldContinue = _shouldContinue;
        }

        while (shouldContinue) {
            final Boolean shouldContinueResult = this.shouldContinue();
            if (! shouldContinueResult) {
                shouldContinue = false;
            }

            try { Thread.sleep(_sleepTime); }  catch(Exception e) { }

            synchronized (_mutex) {
                if (! _shouldContinue) {
                    shouldContinue = false;
                }
            }
        }
    }

    public final void halt() {
        synchronized (_mutex) {
            _shouldContinue = false;
        }

        try {
            this.join();
        }
        catch (Exception e) { }
    }
}
