package com.android.app.framework.command;


import com.android.app.framework.controller.IController;
import com.android.app.framework.net.Response;

import java.util.Map;

/**
 * @author frodoking
 * @date 2014-11-10 11:59:53
 */
public interface ICommand {

    String getName();

    IController getController();

    void execute();

    Map<String, String> getParams();

    void setParams(Map<String, String> paramMap);

    Response getResponse();

    void setResponse(Response response);

    void setCancel(boolean isCancel);

    boolean isCancel();
}
