package com.airlinejk.websockets;

import com.airlinejk.daos.UsersDao;
import com.airlinejk.business_logic.Userss;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
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
    decoders = {Decodifier.class},
    encoders = {Encodifier.class}
)
public class WS_User {
    
    private final UsersDao usersDao = new UsersDao();
    private final Gson gson = new Gson();
    
    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        List<Userss> list = usersDao.all();
        String json = gson.toJson(list, new TypeToken<List<Userss>>(){}.getType());
        //session.getBasicRemote().sendObject(list);
        session.getBasicRemote().sendText(json);
        
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
               sess.getBasicRemote().sendObject(user);
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
