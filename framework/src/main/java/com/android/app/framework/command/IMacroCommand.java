package com.android.app.framework.command;


import com.android.app.framework.controller.IController;

/**
 * 系统需要一个代表宏命令的接口，以定义出具体宏命令所需要的接口。
 * 
 * @author frodoking
 * @date 2014年11月19日 下午3:46:18
 */
public interface IMacroCommand {
    IController getController();

    void execute(ICommand command);
}
