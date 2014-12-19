package com.android.app.framework.command;


import com.android.app.framework.controller.IController;
import com.android.app.framework.controller.Notifier;

import java.util.Map;

/**
 * command 基类
 *
 * @author xuwei19
 * @date 2014年11月19日 下午12:22:50
 */
public abstract class AbstractCommand implements ICommand {
    private IController mController;
    private Map<String, String> mParams;
    private Notifier mNotifier;
    private volatile boolean isCancel = false;

    public AbstractCommand(IController controller) {
        this(controller, null);
    }

    public AbstractCommand(IController controller, Map<String, String> paramMap) {
        this(controller, paramMap, null);
    }

    public AbstractCommand(IController controller, Map<String, String> paramMap, Notifier notifier) {
        this.mController = controller;
        this.mParams = paramMap;
        this.mNotifier = notifier;
    }

    @Override
    public boolean isCancel() {
        return isCancel;
    }

    @Override
    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    @Override
    public final String getName() {
        return getClass().getCanonicalName();
    }

    @Override
    public final IController getController() {
        return mController;
    }

    @Override
    public final Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public final void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public final Notifier getNotifier() {
        return mNotifier;
    }

    @Override
    public final void setNotifier(Notifier notifier) {
        this.mNotifier = notifier;
    }
}
