//package cz.cvut.fel.aos.socket;
//
//
//import javax.websocket.Session;
//import javax.websocket.OnClose;
//import javax.websocket.OnOpen;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@ServerEndpoint("/websockets")
//public class WebsocketService {
//
//    private static List<Session> sessions = new ArrayList<>();
//
//    @OnOpen
//    public void open(Session session) throws IOException {
//        sessions.add(session);
//        for(Session s: sessions){
//            s.getBasicRemote().sendText(sessions.size()+"");
//        }
//    }
//
//    @OnClose
//    public void close(Session session) throws IOException {
//        sessions.remove(session);
//        for(Session s: sessions){
//            s.getBasicRemote().sendText(sessions.size()+"");
//        }
//    }
//}