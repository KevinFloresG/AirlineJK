package com.airlinejk.websockets;

import com.airlinejk.business_logic.Schedules;
import com.airlinejk.daos.SchedulesDao;
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
 * @author Kevin Flores, Javier Amador 
 */
@ServerEndpoint(
    value = "/schedule",
    decoders = {Decodifier.class},
    encoders = {Encodifier.class}
)
public class WS_Schedule {

    private final SchedulesDao dao = new SchedulesDao();
    private final Gson gson = new Gson();
    private final String JSON = "{\"type\":\"%s\", \"content\":%s}";
    
    @OnOpen
    public void onOpen(Session s) throws IOException, EncodeException {
        List<Schedules> l = dao.all();
        String json = gson.toJson(l, new TypeToken<List<Schedules>>(){}.getType());
        String finalJson = String.format(JSON, "all", json);
        s.getBasicRemote().sendText(finalJson);
        /*
        *   You can send an object, but in this case is not necessary,
        *   because you can use JSON.parse() at JS to get a JS object
        *   from JSON text.
        *   ==> JsonObject json = listAsJsonObj(list);
        *   ==> session.getBasicRemote().sendObject(json);
        */
    }
    
    @OnMessage
    public void onMessage(Session s, JsonObject request) 
            throws IOException, EncodeException {
        switch(request.getString("type")){
            case "delete":
                deleteSchedule(s, request.getString("content"));
                break;
            case "add":
                addSchedule(s, request.getString("content"));
                break;
            case "update":
                updateSchedule(s, request.getString("content"));
                break;
        }
    }
    
    private void updateSchedule(Session s, String country) throws IOException{
        Schedules f = gson.fromJson(country, Schedules.class);
        dao.update(f);
        f = dao.get(f.getId());
        broadcast(s, "update", gson.toJson(f, Schedules.class));
    }
    
    private void deleteSchedule(Session s, String country) throws IOException{
        Schedules f = gson.fromJson(country, Schedules.class);
        dao.delete(f.getId());
        broadcast(s, "delete",country);
    }
    
    private void addSchedule(Session s, String flight) throws IOException{
        Schedules f = gson.fromJson(flight, Schedules.class);
        dao.insert(f);
        f = dao.getLastAdded();
        broadcast(s, "add", gson.toJson(f, Schedules.class));
    }
    
    private void broadcast(Session s, String type, String content) throws IOException{
        //s.getBasicRemote().sendText(String.format(JSON, type, content));
        for(Session sess : s.getOpenSessions()){
            if(sess.isOpen())
                sess.getBasicRemote().sendText(String.format(JSON, type, content));
        }
    }

    public JsonObject listAsJsonObj(List<Schedules> l){
        String json = gson.toJson(l, new TypeToken<List<Schedules>>(){}.getType());
        String j2 = "{ \"content\" : "+json+"}";
        System.out.println(j2);
        JsonReader jsonReader = Json.createReader(new StringReader(j2));
        return jsonReader.readObject();
    }
}
