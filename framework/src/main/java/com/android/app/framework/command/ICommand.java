package com.android.app.framework.command;


import com.android.app.framework.controller.IController;
import com.android.app.framework.controller.Notifier;
import com.android.app.framework.net.Response;

import java.util.Map;

/**
 * 统一命令规范 响应请求，并作出处理，进行统�?���?<br>
 * args 是请求参数的封装 //FIXME <br>
 * Notifier 是否会有状�?返回 <br>
 * 
 * @author frodoking
 * @date 2014�?1�?6�?上午11:59:53
 */
public interface ICommand {

    String getName();

    IController getController();

    void execute();

    Map<String,String> getParams();

    void setParams(Map<String,String> paramMap);

    Response getResponse();

    void setResponse(Response response);

    void setCancel(boolean isCancel);

    boolean isCancel();
}
