package com.frodo.app.framework.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by frodo on 2016/3/1. bean node from server
 */
public class BeanNode implements Serializable {
    public String name;
    public Object value;

    private final List<BeanNode> members;

    public BeanNode() {
        members = new ArrayList<>(0);
    }

    public BeanNode(int memberSize) {
        members = new ArrayList<>(memberSize);
    }

    public boolean isLeaf() {
        return members.isEmpty();
    }

    /**
     * 插入一个child节点到当前节点中
     */
    public void addChildNode(BeanNode beanNode) {
        for (int i = 0; i < members.size(); i++) {
            if (Objects.equals(members.get(i).name, beanNode.name)) {
                members.add(i, beanNode);
                return;
            }
        }

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) == null) {
                members.add(i, beanNode);
                break;
            }
        }
    }

    public BeanNode getChildNode(String name) {
        if (!isLeaf()) {
            for (int i = 0; i < members.size(); i++) {
                if (Objects.equals(members.get(i).name, name)) {
                    return members.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 返回当前节点的孩子集合
     */
    public List<BeanNode> getChildArray() {
        return members;
    }

    /**
     * 找到一颗树中某个节点
     */
    public BeanNode findBeanNodeByName(String name) {
        if (Objects.equals(this.name, name)) return this;

        if (!isLeaf()) {
            for (int i = 0; i < members.size(); i++) {
                BeanNode resultNode = members.get(i).findBeanNodeByName(name);
                if (resultNode != null) {
                    return resultNode;
                }
            }
        }
        return null;
    }
}
