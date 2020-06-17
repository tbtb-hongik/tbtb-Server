package io.psol.tbtb.tbtb.config;

import io.psol.tbtb.tbtb.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {
    @Autowired
    SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println("수정");
        webSocketHandlerRegistry.addHandler(socketHandler, "/ws/Android").setAllowedOrigins("*").withSockJS();
        //webSocketHandlerRegistry.addHandler(socketHandler, "/ws/iOS").setAllowedOrigins("*");
    }
}
