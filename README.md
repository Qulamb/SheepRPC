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

TestServer:服务端的测试类

WorkThread:根据映射关系，通过反射调用服务端的方法

痛点：BIO传输性能低，可以进行优化

Version3

![Version3流程图](img_1.png)

更改：
TestClient:客户端的测试类

TestServer:服务端的测试类

新增：
NettyRPCClient:Netty客户端实现，进行连接和网络传输

NettyClientHandler:处理返回的数据，给channal设计别名，让NettyRPCClient能够通过别名获取到返回的数据

NettyClientInitializer：给Netty客户端初始化

NettyRPCServer：Netty高性能服务端实现，建立连接和处理请求的线程分开

NettyRPCServerHandler：处理Netty接收的请求，反射调用对应的服务方法并返回response

NettyServerInitializer：给Netty服务端初始化，在每次连接时执行


![Version4](img_2.png)
自定义实现了传输协议，将Java自带的序列化方式替换成FastJson方式，实现了编码器和解码器
序列化过程大致就是：对象想要在网络上传输需要变成字节流的形式，反序列化就算字节流通过一定的解码方式把字节流数据还原成原本的对象。这个过程中会出现“粘包”问题，也就是解码的时候不知道每一条消息的长度是多少，导致无法准确切分出一个个完整的序列化数据块。

我当前自定义的协议格式是8字节，2字节消息类型2字节序列化方式4字节消息长度。其实这里可以进一步优化，将信息保存精确到位，可以进一步节省空间，还可以增加魔数等信息。

这里的序列化方式也可以进一步优化，首先这是一个RPC框架，用途是调用远程服务，需要高性能、安全、可靠、是否有跨语言需求等,所以选择序列化方案的时候可以优先考量序列化速度、数据体积、安全性、跨语言进行选型。目前没有跨语言需求，所以后期感觉可以实现一下Kryo方案

![Version5.1](img_3.png)
![Version5.2](img_4.png)
中间做的时候我感觉Zookeeper就是存了一下【服务名称，port+host】键值对，这一点用redis也能实现。那它有什么过人之处呢？Zookeeper的数据结构是树形的，分为临时节点和持久节点。使用持久节点保存服务名和接口名，
临时节点保存服务的实例地址，临时节点的生命周期与创建它的客户端会话绑定，所以当服务下线时，临时节点自动就被删除了，和注册中心的契合度非常高。Redis实现的话要通过过期时间，但是过期时间长短又不好把握。
Zookeeper还有Watcher机制，可以监听某一路径的子节点变化，一旦有节点新增或者删除会立即通知所有监听者。如果用redis实现的话要用发布订阅功能，如果发布的时候消费者离线，则接收不到历史变更。这么一对比能看出zookeeper和注册中心的契合度真的非常非常高。


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
