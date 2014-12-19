package com.android.app.framework.net;

import com.android.app.framework.entity.Entity;

/**
 * 公共消息类处理
 * Created by frodoking on 2014/12/19.
 */
public class MessageResponseHandler extends ResponseHandler {

    @Override
    public void onSuccess(int statusCode, int stat, String msg, Entity data) {
        //FIXME
    }

    @Override
    public void onFailure(int statusCode, Throwable throwable) {
        //FIXME
    }

    @Override
    public Class<Entity> expectedClass() {
        return null;
    }

    @Override
    public Request.ParserType expectedParserType() {
        return Request.ParserType.NONE;
    }
}
