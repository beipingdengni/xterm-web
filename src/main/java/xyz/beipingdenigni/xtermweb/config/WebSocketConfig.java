package xyz.beipingdenigni.xtermweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator;
import xyz.beipingdenigni.xtermweb.socket.ShhWebSocketHandler;
import xyz.beipingdenigni.xtermweb.socket.WebSocketInterceptor;

import javax.annotation.Resource;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.config
 * @description: WebSocketConfig
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:02 下午
 **/
@EnableWebSocket
@Configuration
public class WebSocketConfig extends WebSocketConfigurationSupport {

    @Resource
    private ShhWebSocketHandler shhWebSocketHandler;

    @Override
    protected void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        super.registerWebSocketHandlers(registry);

        registry.addHandler(shhWebSocketHandler,"/webSsh")
                // .addInterceptors(new LoggingWebSocketHandlerDecorator())
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
