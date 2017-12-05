package com.netcircle.imageloader.util;

/**
 * Created by sweetgirl on 2017/11/17
 */

public class MessageEvent {

    private String msg;

    public MessageEvent(String message) {
        msg = message;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}
