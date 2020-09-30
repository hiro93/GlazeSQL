### 1.1 MySQL Protocol

MySQL客户端和服务端通过`MySQL Protocol`协议通信，在`Connectors (Connector/C, Connector/J, 等)`，`MySQL Proxy`，主从同步、分片中有实现，协议可以使用SSL进行传输加密，可以数据压缩。

**连接的生命周期**

![Connection Lifecycle](https://dev.mysql.com/doc/dev/mysql-server/latest/inline_umlgraph_52.png)

对应三个阶段

- [Connection Phase](https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_connection_phase.html)
- [Command Phase](https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_command_phase.html)
- [Replication Protocol](https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_replication.html)

**连接阶段(Connection Phase)**

主要做了三件事情：

- 客户端、服务端交换`capabilities`
- 建立SSL通信（如果需要）
- 获取权限

![Connection Phase](https://dev.mysql.com/doc/dev/mysql-server/latest/inline_umlgraph_32.png)

## Connection Phase

我们使用`tcpdump`抓包分析连接阶段的数据包：

```shell
:~# tcpdump -i ens3 port 3306 -X
tcpdump: verbose output suppressed, use -v or -vv for full protocol decode
listening on ens3, link-type EN10MB (Ethernet), capture size 262144 bytes
17:53:29.535771 IP 172-0-102-144.lightspeed.stlsmo.sbcglobal.net.mysql > 192.168.10.201.57784: Flags [P.], seq 1:79, ack 1, win 229, length 78
	0x0000:  4500 0076 0fdc 4000 3f06 4da4 ac00 6690  E..v..@.?.M...f.
	0x0010:  c0a8 0ac9 0cea e1b8 dcd9 1c03 0472 2785  .............r'.
	0x0020:  5018 00e5 de6a 0000 4a00 0000 0a38 2e30  P....j..J....8.0
	0x0030:  2e31 3600 6600 0000 3550 5657 3129 5274  .16.f...5PVW1)Rt
	0x0040:  00ff ff21 0200 ffc3 1500 0000 0000 0000  ...!............
	0x0050:  0000 0007 6304 253d 6070 401e 3235 1100  ....c.%=`p@.25..
	0x0060:  6d79 7371 6c5f 6e61 7469 7665 5f70 6173  mysql_native_pas
	0x0070:  7377 6f72 6400                           sword.
17:53:29.554150 IP 192.168.10.201.57784 > 172-0-102-144.lightspeed.stlsmo.sbcglobal.net.mysql: Flags [P.], seq 1:181, ack 79, win 1028, length 180
	0x0000:  4500 00dc 2342 4000 7a06 fed7 c0a8 0ac9  E...#B@.z.......
	0x0010:  ac00 6690 e1b8 0cea 0472 2785 dcd9 1c51  ..f......r'....Q
	0x0020:  5018 0404 56ca 0000 b000 0001 85a6 7f00  P...V...........
	0x0030:  0000 0040 2100 0000 0000 0000 0000 0000  ...@!...........
	0x0040:  0000 0000 0000 0000 0000 0000 726f 6f74  ............root
	0x0050:  0014 1679 4256 186f e380 a686 8610 7be5  ...yBV.o......{.
	0x0060:  1b1b bb80 ca40 6d79 7371 6c5f 6e61 7469  .....@mysql_nati
	0x0070:  7665 5f70 6173 7377 6f72 6400 5f03 5f6f  ve_password._._o
	0x0080:  7305 5769 6e36 340c 5f63 6c69 656e 745f  s.Win64._client_
	0x0090:  6e61 6d65 086c 6962 6d79 7371 6c04 5f70  name.libmysql._p
	0x00a0:  6964 0439 3434 3007 5f74 6872 6561 6404  id.9440._thread.
	0x00b0:  3237 3234 095f 706c 6174 666f 726d 0541  2724._platform.A
	0x00c0:  4d44 3634 0f5f 636c 6965 6e74 5f76 6572  MD64._client_ver
	0x00d0:  7369 6f6e 0731 302e 312e 3433            sion.10.1.43
```

