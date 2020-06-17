package io.psol.tbtb.tbtb.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //메시지

        //request
        String payloadMessage = (String) message.getPayload();
        System.out.println("meg : " + payloadMessage);

        //response
        session.sendMessage(new TextMessage("asd" + payloadMessage));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        super.afterConnectionEstablished(session);
        System.out.println("클라이언트 접속됨");

        Map<String, Object> map = session.getAttributes();
        System.out.println(map);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        super.afterConnectionClosed(session, status);
        System.out.println("클라이언트 접속해제");
    }
}
