package com.android.app.framework.command;


import com.android.app.framework.controller.IController;

/**
 * @author frodoking
 * @date 2014-11-19 15:46:18
 */
public interface IMacroCommand {
    IController getController();

    void execute(ICommand command);
}
