package com.airlinejk.websockets;

import com.airlinejk.business_logic.Flights;
import com.airlinejk.daos.FlightsDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
    value = "/flight",
    decoders = {Decodifier.class},
    encoders = {Encodifier.class}
)
public class WS_Flight {

    private final FlightsDao dao = new FlightsDao();
    private final Gson gson = new Gson();
    private final String JSON = "{\"type\":\"%s\", \"content\":%s}";
    
    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        List<Flights> l = dao.all();
        String json = gson.toJson(l, new TypeToken<List<Flights>>(){}.getType());
        String finalJson = String.format(JSON, "all", json);
        session.getBasicRemote().sendText(finalJson);
        /*
        *   You can send an object, but in this case is not necessary,
        *   because you can use JSON.parse() at JS to get a JS object
        *   from JSON text.
        *   ==> JsonObject json = listAsJsonObj(list);
        *   ==> session.getBasicRemote().sendObject(json);
        */
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
    
    public JsonObject listAsJsonObj(List<Flights> l){
        String json = gson.toJson(l, new TypeToken<List<Flights>>(){}.getType());
        String j2 = "{ \"content\" : "+json+"}";
        System.out.println(j2);
        JsonReader jsonReader = Json.createReader(new StringReader(j2));
        return jsonReader.readObject();
    }
}
