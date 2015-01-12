package com.android.app.framework.command;

import com.android.app.framework.controller.IController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * IMacroCommand impl
 * 
 * @author frodoking
 * @date 2014-11-19 00:13:34
 */
public final class MacroCommand implements IMacroCommand {

    private ExecutorService mPool;
    private IController mController;

    public MacroCommand(IController controller) {
        this.mController = controller;
        mPool = Executors.newCachedThreadPool();
    }

    @Override
    public void execute(final ICommand command) {
        command.setCancel(false);
        mPool.execute(new Runnable() {
            @Override
            public void run() {
                command.execute();
            }
        });
    }

    @Override
    public IController getController() {
        return mController;
    }
}
