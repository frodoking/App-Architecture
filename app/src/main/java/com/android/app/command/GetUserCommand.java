package com.android.app.command;

import com.android.app.framework.command.AbstractCommand;
import com.android.app.framework.controller.IController;
import com.android.app.framework.controller.Notifier;

import java.util.Map;

/**
 * example obtain user info by CMD
 * Created by frodo on 2014/12/19.
 */
public final class GetUserCommand extends AbstractCommand {

    public GetUserCommand(IController controller) {
        super(controller);
    }

    public GetUserCommand(IController controller, Map<String, String> paramMap) {
        super(controller, paramMap);
    }

    public GetUserCommand(IController controller, Map<String, String> paramMap, Notifier notifier) {
        super(controller, paramMap, notifier);
    }

    @Override
    public void execute() {

    }
}
