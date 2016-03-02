package com.frodo.app.android.simple;

import android.text.TextUtils;

import com.frodo.app.android.core.task.AndroidFetchNetworkDataTask;
import com.frodo.app.framework.controller.AbstractModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.entity.BeanNode;
import com.frodo.app.framework.net.Request;

import rx.Subscriber;

/**
 * Created by frodo on 2015/7/24.
 */
public class ConfigurationModel extends AbstractModel {

    private AndroidFetchNetworkDataTask fetchNetworkDataTask;
    private BeanNode beanNode;

    public ConfigurationModel(MainController controller) {
        super(controller);
        Request request = new Request("GET", Path.configuration);
        fetchNetworkDataTask = new AndroidFetchNetworkDataTask(controller.getNetworkTransport(), request, new Subscriber<BeanNode>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(BeanNode beanNode) {
            }
        });
    }

    public boolean isValid() {
        return beanNode != null
                && !TextUtils.isEmpty(beanNode.findBeanNodeByName("images").findBeanNodeByName("base_url").value.toString())
                && !TextUtils.isEmpty(beanNode.findBeanNodeByName("images").findBeanNodeByName("backdrop_sizes").value.toString())
                && !TextUtils.isEmpty(beanNode.findBeanNodeByName("images").findBeanNodeByName("poster_sizes").value.toString())
                && !TextUtils.isEmpty(beanNode.findBeanNodeByName("images").findBeanNodeByName("profile_sizes").value.toString());
    }

    public void setTmdbConfiguration( BeanNode beanNode) {
        this.beanNode = beanNode;
        getMainController().getConfig().setServerConfig(beanNode);
    }

    @Override
    public void initBusiness() {
        getMainController().getBackgroundExecutor().execute(fetchNetworkDataTask);
    }
}
