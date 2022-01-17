package xyz.beipingdenigni.xtermweb.channel;

import com.jcraft.jsch.Session;
import org.springframework.web.socket.WebSocketSession;
import xyz.beipingdenigni.xtermweb.socket.SshInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.channel
 * @description: ManagerShh
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:43 下午
 **/
public class ManagerShh {


    /**
     *
     */
    private static Map<String, SshInfo> sessionMap = new ConcurrentHashMap<>();


    /**
     * @param key
     * @return
     */
    public static SshInfo getByChannel(String key) {
        final SshInfo session = sessionMap.get(key);
        return session;
    }

    /**
     * @param session
     */
    public static void putChannel(String key,SshInfo session) {
        sessionMap.put(key, session);

    }

    /**
     * @param key
     */
    public static void delChannel(String key) {
        sessionMap.remove(key);

    }

}
