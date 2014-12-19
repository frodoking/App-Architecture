package com.android.app.framework.controller;


import com.android.app.framework.command.IMacroCommand;
import com.android.app.framework.config.Configuration;

/**
 * controller接口
 * 
 * @author: frodoking
 * @date: 2014-11-28
 */
public interface IController {
    IMacroCommand getMacroCommand();

    Configuration getConfiguration();
}
