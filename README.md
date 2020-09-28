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
