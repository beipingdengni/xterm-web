package xyz.beipingdenigni.xtermweb.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.service
 * @description: WebSshService
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:30 下午
 **/
public interface WebSshService {

    /**
     * @Description: 初始化ssh连接
     */
    public void initConnection(WebSocketSession session);

    /**
     * @Description: 处理客户段发的数据
     */
    public void recvHandle(String buffer, WebSocketSession session) throws IOException;

    /**
     * @Description: 数据写回前端 for websocket
     */
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * @Description: 关闭连接
     */
    public void close(WebSocketSession session);


}
