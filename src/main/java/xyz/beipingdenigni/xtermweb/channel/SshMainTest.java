package xyz.beipingdenigni.xtermweb.channel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.channel
 * @description: SshMainTest
 * @author: tianbeiping1
 * @Date: 2022/1/15 8:02 下午
 **/
@Slf4j
public class SshMainTest {


    public static void main(String[] args) throws JSchException, IOException {
        JSch sch = new JSch();
        final Session root = sch.getSession("root", "121.5.129.9", 22);
        root.setPassword("");
        // root.setUserInfo(new MyUserInfo());
        // root.setUserInfo();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey
        root.setConfig(config);
        // 30s
        root.connect(30000);
        log.info("session --------");
        final Channel channelExec = root.openChannel("shell");
        // 3s
        channelExec.connect(3000);
        log.info("channel --------");

        // InputStreamReader is = new InputStreamReader(System.in); //new构造InputStreamReader对象
        // BufferedReader br = new BufferedReader(is); //拿构造的方法传到BufferedReader中，此时获取到的就是整个缓存流
        // final String s = br.readLine();

        Scanner sc = new Scanner(System.in);
        final String s = sc.nextLine();

        final OutputStream outputStream = channelExec.getOutputStream();
        outputStream.write((s + " \r").getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        log.info("inputStream -------");

        final InputStream inputStream = channelExec.getInputStream();
        // final String s = IOUtils.toString(inputStream, Charset.defaultCharset());
        //循环读取
        byte[] buffer = new byte[1024];
        int i = 0;
        //如果没有数据来，线程会一直阻塞在这个地方等待数据。
        while ((i = inputStream.read(buffer)) != -1) {
            final byte[] bytes = Arrays.copyOfRange(buffer, 0, i);
            System.out.println(new String(bytes));
        }

        log.info(" ====> {}", "1");
        // System.out.println(s);


        channelExec.disconnect();

        // // 3. 获取标准输入流
        // BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
        // // 4. 获取标准错误输入流
        // BufferedReader errInputStreamReader = new BufferedReader(new InputStreamReader(channelExec.getErrStream()));
        //
        // StringBuilder runLog = new StringBuilder("");
        // StringBuilder errLog = new StringBuilder("");
        //
        // // 5. 记录命令执行 log
        // String line = null;
        // while ((line = inputStreamReader.readLine()) != null) {
        //     runLog.append(line).append("\n");
        // }
        //
        // // 6. 记录命令执行错误 log
        // String errLine = null;
        // while ((errLine = errInputStreamReader.readLine()) != null) {
        //     errLog.append(errLine).append("\n");
        // }

        // 7. 输出 shell 命令执行日志
        System.out.println("exitStatus=" + channelExec.getExitStatus() + ", openChannel.isClosed=" + channelExec.isClosed());
        // System.out.println("命令执行完成，执行日志如下:");
        // System.out.println(runLog.toString());
        // System.out.println("命令执行完成，执行错误日志如下:");
        // System.out.println(errLog.toString());

        root.disconnect();


    }

}
