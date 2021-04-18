package com.airlinejk.websockets;

import com.airlinejk.business_logic.Countries;
import com.airlinejk.daos.CountriesDao;
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
    value = "/country",
    decoders = {Decodifier.class},
    encoders = {Encodifier.class}
)
public class WS_Country {

    private final CountriesDao dao = new CountriesDao();
    private final Gson gson = new Gson();
    private final String JSON = "{\"type\":\"%s\", \"content\":%s}";
    
    @OnOpen
    public void onOpen(Session s) throws IOException, EncodeException {
        List<Countries> l = dao.all();
        String json = gson.toJson(l, new TypeToken<List<Countries>>(){}.getType());
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
                deleteCountry(s, request.getString("content"));
                break;
            case "add":
                addCountry(s, request.getString("content"));
                break;
            case "update":
                updateCountry(s, request.getString("content"));
                break;
        }
    }
    
    private void updateCountry(Session s, String country) throws IOException{
        Countries f = gson.fromJson(country, Countries.class);
        dao.update(f);
        f = dao.get(f.getId());
        broadcast(s, "update", gson.toJson(f, Countries.class));
    }
    
    private void deleteCountry(Session s, String country) throws IOException{
        Countries f = gson.fromJson(country, Countries.class);
        dao.delete(f.getId());
        broadcast(s, "delete",country);
    }
    
    private void addCountry(Session s, String flight) throws IOException{
        Countries f = gson.fromJson(flight, Countries.class);
        dao.insert(f);
        broadcast(s, "add", gson.toJson(f, Countries.class));
    }
    
    private void broadcast(Session s, String type, String content) throws IOException{
        //s.getBasicRemote().sendText(String.format(JSON, type, content));
        for(Session sess : s.getOpenSessions()){
            if(sess.isOpen())
                sess.getBasicRemote().sendText(String.format(JSON, type, content));
        }
    }

    public JsonObject listAsJsonObj(List<Countries> l){
        String json = gson.toJson(l, new TypeToken<List<Countries>>(){}.getType());
        String j2 = "{ \"content\" : "+json+"}";
        System.out.println(j2);
        JsonReader jsonReader = Json.createReader(new StringReader(j2));
        return jsonReader.readObject();
    }
}
