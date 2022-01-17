package xyz.beipingdenigni.xtermweb.service.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;

import com.jcraft.jsch.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xyz.beipingdenigni.xtermweb.channel.ManagerShh;
import xyz.beipingdenigni.xtermweb.service.MyUserInfo;
import xyz.beipingdenigni.xtermweb.service.WebSshService;
import xyz.beipingdenigni.xtermweb.socket.SshInfo;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.service.impl
 * @description: WebSshServiceImpl
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:31 下午
 **/
@Slf4j
@Service
public class WebSshServiceImpl implements WebSshService {

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    @SneakyThrows
    public void initConnection(WebSocketSession session) {


        JSch sch = new JSch();
        final Session root = sch.getSession("root", "121.5.129.9", 22);
        root.setPassword("");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey
        root.setConfig(config);
        // root.setUserInfo(new MyUserInfo());
        // root.setUserInfo();
        // 30s
        root.connect(30000);
        SshInfo sshInfo = new SshInfo();
        sshInfo.setKey(session.getId());
        sshInfo.setSession(root);
        //
        ManagerShh.putChannel(session.getId(), sshInfo);
    }

    @Override
    public void recvHandle(String buffer, WebSocketSession session) throws IOException {

        SshInfo sshInfo = ManagerShh.getByChannel(session.getId());
        if (sshInfo.getChannel() != null) {
            final OutputStream outputStream = sshInfo.getChannel().getOutputStream();
            outputStream.write(buffer.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            return;
        }

        executorService.execute(() -> {
            try {
                Channel channel = sshInfo.getSession().openChannel("shell");
                // 3s
                channel.connect(3000);

                sshInfo.setChannel(channel);
                final OutputStream outputStream = sshInfo.getChannel().getOutputStream();
                outputStream.write("\r".getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                final InputStream inputStream = channel.getInputStream();
                final String s = IOUtils.toString(inputStream, Charset.defaultCharset());
                session.sendMessage(new TextMessage(s));

            } catch (Exception e) {
                e.printStackTrace();
            }


        });


    }

    @Override
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException {

    }

    @Override
    public void close(WebSocketSession session) {


    }
}
