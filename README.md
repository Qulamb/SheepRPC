# MyRPC

#### 介绍
手写RPC框架


Version1
![Version1流程图](%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20260227124030_102_158.jpg)

RPCClient：模拟客户端远程调用服务端的方法

RPCServer：模拟服务端，接收request并通过反射动态调用接收到的固定服务的方法

ClientProxy：代理类，实现了返回代理对象的方法和代理填充request并调用IOClient的方法

IOClient：向服务端发送request并获取返回值response

痛点： 

1.服务端直接绑定的是UserService服务，如果有多个服务就要写多遍重复代码，代码复用性低。 

2.BIO的方式性能比较低

3.服务端的功能太复杂：监听，处理。最好是拆分成单一功能







Version2
![Version2流程图](img.png)

RPCServer：从类抽象成了接口，以后的服务端实现这个接口即可

ServiceProvider:保存接口对应方法与实例的映射关系，便于后续通过反射调用不同服务的方法

SimpleRPCServer:简单实现了RPCServer，负责BIO监听，来一个任务就new一个线程去执行.处理任务的工作见WorkThread中

TestServer:服务端，做一些提前准备的工作（调用映射关系保存的函数等）

WorkThread:根据映射关系，通过反射调用服务端的方法

痛点：BIO传输性能低，可以进行优化






#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
