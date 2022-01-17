package xyz.beipingdenigni.xtermweb.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import xyz.beipingdenigni.xtermweb.channel.ManagerChannel;
import xyz.beipingdenigni.xtermweb.service.WebSshService;

import javax.annotation.Resource;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.socket
 * @description: ShhWebSocketHandler
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:04 下午
 **/
@Component
@Slf4j
public class ShhWebSocketHandler extends AbstractWebSocketHandler {

    @Resource
    private WebSshService webSshService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // final String name = (String) session.getAttributes().get("name");
        ManagerChannel.putChannel(session.getId(), session);
        webSshService.initConnection(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // 接受消息，回传消息
        webSshService.recvHandle(message.getPayload(),session);


    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);

        log.error("handleTransportError {}", exception.getMessage());
        ManagerChannel.delChannel(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.error("afterConnectionClosed {}", status.toString());

        ManagerChannel.delChannel(session.getId());
    }

}
