package com.android.app.event;

/**
 * Created by xuwei19 on 2014/12/19.
 */
public class AbstractEvent<T> {

    private T content;

    public AbstractEvent(T content) {
        this.content = content;
    }

    public T getContent(){
        return content;
    }
}
