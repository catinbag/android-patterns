package org.demo.patterns.mvi;

/** Content that can be read once. Used for one-time events: navigation, toast. */
public class Event<T> {
    private final T content;
    private boolean handled;

    public Event(T content) {
        this.content = content;
    }

    /** Returns the content on the first call and null on the next calls. */
    public T getContentIfNotHandled() {
        if (handled) return null;
        handled = true;
        return content;
    }
}
