GradleAndroid-App-Framework
===========================

## 简介

GradleAndroid-App-Framework是一个免费的开源关于Android  App一整套架构的解决方案。主要目的是整合流行开发模式结合其他开源模块形成的一整套Android快速开发解决方案。
项目主要包括两大部分

## Framework层

依托以java级别，主要提供底层API框架接口，提供一种编程思想同时从平台中剥离出来

1. CacheSystem 缓存系统
2. Configuration  基础应用配置
3. Context 接管App层级上下文
4. NetworkInteractor  网络模块，主要负责网络检测以及网络请求
5. FileSystem  文件系统
6. Database   数据库
7. Theme   主题
8. Scene   场景功能
9. ModelFactory    业务工厂（实现IModel功能的所有业务操作类）
10. PluginManager   插件化系统，主要对可扩展类的考虑。在一些特殊的系统中，可能存在ChildSystem级别的系统功能。需要继承PluginChildSystem来实现
11. LogCollector    日志收集系统，针对所有日志做处理（开关，打印，本地保持，上传server等功能）

## App层

依赖于Framework Library。实现基于Android平台下的一系列接口

1. 主要接管Activity和Fragment中的架构，采用了MVVM的方式来解放UI(最近受到IOS的MVC架构所启发，Android的View概念被弱化，Activity和Fragment被强化导致很多同学认为这两者就是UI上的事情。其实不是这样，Fragment应该类似于IOS中的UIViewController才对。因此本框架通过UIView来强化Android的UI概念。用Fragment来做为UIViewController。)
2. App全局只有一个入口启动MainActivity同时也是唯一的一个Activity。MainActivity继承了FragmentContainerActivity，因此他是Fragment容器
3. 所有页面都是Fragment实现，包括启动页面。主要利用support.v4包的FragmentManager来管理整个Fragment堆栈实现页面切换功能
4. 页面请求都采用线程池执行Task的方式来完成，回调使用了Rxjava的订阅/消费的观察者模式完成

## 框架图
![架构图](http://frodoking.github.io/img/GradleAndroid-App-Framework.png)

## Simple工程
1. simplecloudentity是服务器定义的基础数据结构
2. simpleentity是本地App需要的数据结构。这样定义的目的是为了App数据结构的组织不完全依赖Server
3. Simple工程数据主要参考[philm](https://github.com/OpenSource-Frodo/philm)工程

## 注

由于整个工程项目开发是一个很耗时的迭代过程，所以有考虑不全面的地方希望引用同学继续补充(贡献分支[branche_contributors](https://github.com/frodoking/GradleAndroid-App-Framework/tree/branche_contributors))。在补充过程中尽量通知到作者本人。希望有兴趣的同学加入进来，把这个工程完善得更好。

**依赖注入和事件总线思想在本项目里是排斥的，原因很简单，不仅仅从性能方面的考虑,同时：**

1. 依赖让代码指向混乱同时也给开发者造成结构的不清晰
2. 事件总线思想很容易造成滥用的现象，就像广播一样，没有目的的注册和广播很容易导致内存泄露发生

## 第三方依赖
1. 基础库guava
2. 网络库okhttp、retrofit
3. 图片库picasso（后期考虑glide）
4. 事件传递机制的Rxjava
5. 内存泄露检测库leakcanary

## 关于作者(frodoking)
* Email: awangyun8@gmail.com
* 个人技术Blog：http://frodoking.github.io/

#License
Copyright 2015 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
