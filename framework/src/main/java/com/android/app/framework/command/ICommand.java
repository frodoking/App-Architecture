package com.android.app.framework.command;


/**
 * @author frodoking
 * @date 2014-11-10 11:59:53
 */
public interface ICommand {

    String getName();

    void execute();

    void setCancel(boolean isCancel);

    boolean isCancel();
}
