package xyz.beipingdenigni.xtermweb.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.config
 * @description: WebSocketInterceptor
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:07 下午
 **/
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("beforeHandshake {}", map);
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            //生成一个UUID，这里由于是独立的项目，没有用户模块，所以可以用随机的UUID
            //但是如果要集成到自己的项目中，需要将其改为自己识别用户的标识
            String uuid = UUID.randomUUID().toString().replace("-","");
            //将uuid放到websocketsession中
            map.put("key", request.getServletRequest().getAttribute("id"));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

        log.info("afterHandshake {}", webSocketHandler.toString());

    }


}
