# nohup和&后台运行，进程查看及终止

##   1.nohup

  用途：不挂断地运行命令。

  语法：nohup Command [ Arg … ] [　& ]

  　　无论是否将 nohup 命令的输出重定向到终端，输出都将附加到当前目录的 nohup.out 文件中。

  　　如果当前目录的 nohup.out 文件不可写，输出重定向到 $HOME/nohup.out 文件中。

  　　如果没有文件能创建或打开以用于追加，那么 Command 参数指定的命令不可调用。

  退出状态：该命令返回下列出口值： 　

  　　- 可以查找但不能调用 Command 参数指定的命令。 　

  　　- nohup 命令发生错误或不能查找由 Command 参数指定的命令。 　

  　　否则，nohup 命令的退出状态是 Command 参数指定命令的退出状态。


## 列举
功能：命令在后台运行，功能与Ctrl+z相同，一般配合nohup一起使用

```
eg：nohup ~/user/test.sh>output.log 2>&1 &
```
命令详解：
```
nohup ~/user/test.sh>output.log 
```
- 不挂断运行test.sh，输出结果重定向到当前目录的output.log

- 最后的& 表示后台运行

- 2>&1 0表示键盘输入，1屏幕输出即标准输出，2表示错误输出。其中2>&1表示将错误信息重定向到标准输出
试想一下，如果2>&1指将错误信息重定向到标准输出，那2>1指什么？
分别尝试2>1，2>&1  也就是说2>1会将错误信息重定向到文件1里面，所以2>&1中的&1指标准输出

##   2.&

  用途：在后台运行

  一般两个一起用

  nohup command &

  eg:

  ```
  nohup /usr/local/node/bin/node /www/im/chat.js >> /usr/local/node/output.log 2>&1 &
  ```

  ![img](asserts/798214-20170320150831908-545166421.png)

  进程号7585

  查看运行的后台进程

  （1）jobs -l

  ![img](asserts/798214-20170320150912955-1772662776.png)

  jobs命令只看当前终端生效的，关闭终端后，在另一个终端jobs已经无法看到后台跑得程序了，此时利用ps（进程查看命令）

  （2）ps -ef 

  ```
  ps -aux|grep chat.js
   a:显示所有程序 
   u:以用户为主的格式来显示 
   x:显示所有程序，不以终端机来区分
  ```

  ![img](asserts/798214-20170320153334877-1168175476.png)

  注：

  　　用ps -def | grep查找进程很方便，最后一行总是会grep自己

  　　用grep -v参数可以将grep命令排除掉

  ```
  ps -aux|grep chat.js| grep -v grep
  ```

  ![img](asserts/798214-20170320153456502-1139370768.png)

  　　再用awk提取一下进程ID　

  ```
  ps -aux|grep chat.js| grep -v grep | awk ``'{print $2}'
  ```

  ![img](asserts/798214-20170320153606799-967154073.png)

  ```
  
  ```

  3.如果某个进程起不来，可能是某个端口被占用

  查看使用某端口的进程

  ```
  lsof -i:8090
  ```

  ![img](asserts/798214-20170320154514377-1985478430.png)

  ```
  netstat -ap|grep 8090
  ```

  ![img](asserts/798214-20170320154600658-246972161.png)

  查看到进程id之后，使用netstat命令查看其占用的端口

  ```
  netstat -nap|grep 7779
  ```

  ![img](asserts/798214-20170320155041815-1272481492.png)

  使用kill杀掉进城后再启动

  4.终止后台运行的进程

  ```
  kill -9 进程号
  ```

  ![img](asserts/798214-20170320153728049-88100874.png)