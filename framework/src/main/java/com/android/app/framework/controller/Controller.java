package com.android.app.framework.controller;


import com.android.app.framework.command.IMacroCommand;
import com.android.app.framework.command.MacroCommand;
import com.android.app.framework.config.Configuration;

/**
 * controller 实现
 * 
 * @author: frodoking
 * @date: 2014-11-28
 */
public class Controller implements IController {
    private MacroCommand mMacroCommand;

    private Configuration mConfiguration;

    public Controller(Configuration configuration) {
        this.mConfiguration = configuration;
    }

    @Override
    public IMacroCommand getMacroCommand() {
        return mMacroCommand == null ? mMacroCommand = new MacroCommand(this) : mMacroCommand;
    }

    @Override
    public Configuration getConfiguration() {
        return mConfiguration;
    }

}
