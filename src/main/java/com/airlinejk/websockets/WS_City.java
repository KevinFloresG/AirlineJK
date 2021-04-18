package com.airlinejk.websockets;

import com.airlinejk.business_logic.Cities;
import com.airlinejk.daos.CitiesDao;
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
    value = "/city",
    decoders = {Decodifier.class},
    encoders = {Encodifier.class}
)
public class WS_City {

    private final CitiesDao dao = new CitiesDao();
    private final Gson gson = new Gson();
    private final String JSON = "{\"type\":\"%s\", \"content\":%s}";
    
    @OnOpen
    public void onOpen(Session s) throws IOException, EncodeException {
        List<Cities> l = dao.all();
        String json = gson.toJson(l, new TypeToken<List<Cities>>(){}.getType());
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
                deleteCity(s, request.getString("content"));
                break;
            case "add":
                addCity(s, request.getString("content"));
                break;
            case "update":
                updateCity(s, request.getString("content"));
                break;
        }
    }
    
    private void updateCity(Session s, String country) throws IOException{
        Cities f = gson.fromJson(country, Cities.class);
        dao.update(f);
        f = dao.get(f.getId());
        broadcast(s, "update", gson.toJson(f, Cities.class));
    }
    
    private void deleteCity(Session s, String country) throws IOException{
        Cities f = gson.fromJson(country, Cities.class);
        dao.delete(f.getId());
        broadcast(s, "delete",country);
    }
    
    private void addCity(Session s, String city) throws IOException{
        Cities f = gson.fromJson(city, Cities.class);
        dao.insert(f);
        broadcast(s, "add", gson.toJson(f, Cities.class));
    }
    
    private void broadcast(Session s, String type, String content) throws IOException{
        //s.getBasicRemote().sendText(String.format(JSON, type, content));
        for(Session sess : s.getOpenSessions()){
            if(sess.isOpen())
                sess.getBasicRemote().sendText(String.format(JSON, type, content));
        }
    }

    public JsonObject listAsJsonObj(List<Cities> l){
        String json = gson.toJson(l, new TypeToken<List<Cities>>(){}.getType());
        String j2 = "{ \"content\" : "+json+"}";
        System.out.println(j2);
        JsonReader jsonReader = Json.createReader(new StringReader(j2));
        return jsonReader.readObject();
    }
}
