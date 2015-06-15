package com.android.app.framework.command;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CMD执行工具
 *
 * @author frodoking
 * @date 2014-11-19 00:13:34
 */
public final class MacroCommand {

    private static MacroCommand macroCommand;

    private ExecutorService mPool;
    private Timer mTimer;

    public static MacroCommand getDefault() {
        if (macroCommand == null) {
            synchronized(MacroCommand.class) {
                macroCommand = new MacroCommand();
            }
        }

        return macroCommand;
    }

    public MacroCommand() {
        mPool = Executors.newCachedThreadPool();
        mTimer = new Timer();
    }

    public void execute(ICommand command) {
        command.setCancel(false);
        command.execute();
    }

    public void executeDelayed(final ICommand command, long delayMillis) {
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                execute(command);
            }
        }, delayMillis);
    }

    public void executeAsync(final ICommand command) {
        command.setCancel(false);
        mPool.execute(new Runnable() {
            @Override
            public void run() {
                command.execute();
            }
        });
    }

    public void executeAsyncDelayed(final ICommand command, long delayMillis) {
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                executeAsync(command);
            }
        }, delayMillis);
    }
}
