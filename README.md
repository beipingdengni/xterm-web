# 工程简介

使用webSocket 处理，远程ssh 交互

jsch 使用指令 shell

```java
JSch sch = new JSch();
Session root = sch.getSession("root", "127.0.0.1", 22);
root.setPassword("");
// root.setUserInfo(new MyUserInfo());
// root.setUserInfo();
Properties config = new Properties();
config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey
root.setConfig(config);
// 30s
root.connect(30000);
Channel channelExec = root.openChannel("shell");
// 3s
channelExec.connect(3000);

// 输入指令
OutputStream outputStream = channelExec.getOutputStream();
outputStream.write((s + " \r").getBytes(StandardCharsets.UTF_8));
outputStream.flush();

// 阻塞处理（添加线程池中）
InputStream inputStream = channelExec.getInputStream();
//循环读取
byte[] buffer = new byte[1024];
int i = 0;
//如果没有数据来，线程会一直阻塞在这个地方等待数据。
while ((i = inputStream.read(buffer)) != -1) {
    byte[] bytes = Arrays.copyOfRange(buffer, 0, i);
    System.out.println(new String(bytes));
}

channelExec.disconnect();
root.disconnect()
```

后端使用
spring-boot-starter-websocket

com.jcraft:jsch:0.1.55


前端使用： xterm
https://xtermjs.org/


# 延伸阅读

