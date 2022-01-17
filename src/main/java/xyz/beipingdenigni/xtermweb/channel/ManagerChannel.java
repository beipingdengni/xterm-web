package xyz.beipingdenigni.xtermweb.channel;

import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.channel
 * @description: ManagerChannel
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:09 下午
 **/
public class ManagerChannel {


    /**
     *
     */
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();


    /**
     * @param key
     * @return
     */
    public static WebSocketSession getByChannel(String key) {
        final WebSocketSession session = sessionMap.get(key);
        return session;
    }

    /**
     * @param session
     */
    public static void putChannel(String key,WebSocketSession session) {
        sessionMap.put(key, session);

    }

    /**
     * @param key
     */
    public static void delChannel(String key) {
        sessionMap.remove(key);

    }

}
