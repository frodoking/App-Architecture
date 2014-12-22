package com.android.app.command;

import com.android.app.framework.command.AbstractCommand;
import com.android.app.framework.controller.IController;
import com.android.app.framework.datasource.DataSource;
import com.android.app.framework.net.Request;
import com.android.app.framework.net.Response;

import java.util.Map;

/**
 * example obtain user info by CMD
 * Created by frodoking on 2014/12/19.
 */
public final class GetUserCommand extends AbstractCommand {


    public GetUserCommand(IController controller, Map<String, String> paramMap, Response response) {
        super(controller, paramMap, response);
    }

    @Override
    public void execute() {
        Request request = new Request();
        request.setAction("GetUserCommand");
        request.setType(Request.GET);
        request.setParams(getParams());
        request.setPath("/user");
//        request.setExpectType(User.class);
        request.setParserType(Request.ParserType.CUSTOM);

        DataSource ds = new DataSource(null);
        ds.doRequest(request,getResponse());
    }
}
