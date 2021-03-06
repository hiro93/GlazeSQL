GlazeSQL
========

## 缘起

这段时间学习了 [《MySQL实战45讲——从原理到实战，丁奇带你搞懂MySQL》](https://time.geekbang.org/column/intro/139) 
，为了验证和加深自己对数据库的理解，产生了造轮子的想法。

由于是学习和验证，这里的实现不一定合理和高效，只是能够实现数据库的基本结构和功能就已经满足了。

## 系统架构

![MySQL 逻辑架构图](https://static001.geekbang.org/resource/image/0d/d9/0d2070e8f84c4801adbfa03bda1f98d9.png)

总体上来说，`MySQL`可以分为`Server`层和`存储引擎`层两部分：

- Server层包括连接器、查询缓存（不建议使用，8.0已经砍掉了）、分析器、优化器、执行器等
- 存储引擎层负责数据的存储和提取，其架构模式是插件式的，常用的有`InnoDB`、`MyISAM`、`Memory`等



## 1. 连接器

连接器负责跟客户端建立连接、获取权限、维持和管理连接。连接命令一般是这么写的：

```bash
mysql -u127.0.0.1 -p3306 -uroot -p
```

- 如果用户名或密码不对，你就会收到一个"Access denied for user"的错误，然后客户端程序结束执行。
- 如果用户名密码认证通过，连接器会到权限表里面查出你拥有的权限。之后，这个连接里面的权限判断逻辑，都将依赖于此时读到的权限。
- 连接完成后，如果没有后续的动作，这个连接就处于空闲状态，你可以在 `show processlist` 命令中看到它。
- 客户端如果太长时间没动静，连接器就会自动将它断开。这个时间是由参数 `wait_timeout`控制的，默认值是 8 小时。

[协议分析与实现](./wiki/MySQL Protocol.md)

