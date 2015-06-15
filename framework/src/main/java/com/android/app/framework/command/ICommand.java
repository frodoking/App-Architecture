package com.android.app.framework.command;

/**
 * CMD主要作用是用于view界面上每一次的请求动作 <br>
 * 每一次的动作完成后再将后续逻辑转入到IView的IPresenter<br>
 * 实际后续逻辑由IModel来接管，同时IModel中需要的全局内容通过MainController来获取<br>
 *
 * @author frodoking
 * @date 2014-11-10 11:59:53
 */
public interface ICommand {

    String getName();

    void execute();

    void setCancel(boolean isCancel);

    boolean isCancel();
}
