package com.airlinejk.websockets;

import com.airlinejk.daos.UsersDao;
import com.airlinejk.websockets.decoders.Decoderr;
import com.airlinejk.websockets.encoders.EC_User;
import com.airlinejk.websockets.encoders.EC_UserList;
import java.io.IOException;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Kevin Flores
 */
@ServerEndpoint(
    value = "/userList", 
    decoders = {Decoderr.class}
)
public class WS_User {
    
    private final UsersDao usersDao = new UsersDao();
    
    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        /*List<Userss> list = usersDao.all();*/
        session.getBasicRemote().sendText("soy yo");
        for (Session sess : session.getOpenSessions()) {
            if (sess.isOpen())
               sess.getBasicRemote().sendText("connected: "+session.getId());
        }
    }
    
    @OnMessage
    public void onMessage(Session session, JsonObject user) 
            throws IOException, EncodeException {
        
        /*List<Userss> list = usersDao.all();*/
        System.out.println(user.toString());
        for (Session sess : session.getOpenSessions()) {
            if (sess.isOpen())
               sess.getBasicRemote().sendText("Crud: "+ user.getString("tyype"));
        }
    }
    
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        /*List<Userss> list = usersDao.all();*/
        for (Session sess : session.getOpenSessions()) {
            if (sess.isOpen())
               sess.getBasicRemote().sendText("disconnected: "+session.getId());
        }
        if(session.isOpen())
            session.close();
    }
    
}
