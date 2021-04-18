package com.airlinejk.websockets;

import com.airlinejk.business_logic.Flights;
import com.airlinejk.daos.FlightsDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    private final String JSON = "{\"type\":\"%s\", \"content\":%s}";
    
    @OnOpen
    public void onOpen(Session s) throws IOException, EncodeException {
        List<Flights> l = dao.all();
        String json = gson.toJson(l, new TypeToken<List<Flights>>(){}.getType());
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
                deleteFlight(s, request.getString("content"));
                break;
            case "add":
                addFlight(s, request.getString("content"));
                break;
            case "update":
                updateFlight(s, request.getString("content"));
                break;
        }
    }
    
    private void updateFlight(Session s, String flight) throws IOException{
        Flights f = gson.fromJson(flight, Flights.class);
        dao.updateFlightInfo(f);
        f = dao.get(f.getId());
        broadcast(s, "update", gson.toJson(f, Flights.class));
    }
    
    private void deleteFlight(Session s, String flight) throws IOException{
        Flights f = gson.fromJson(flight, Flights.class);
        dao.delete(f.getId());
        broadcast(s, "delete",flight);
    }
    
    private void addFlight(Session s, String flight) throws IOException{
        Flights f = gson.fromJson(flight, Flights.class);
        dao.insert(f);
        f = dao.getLastAdded();
        broadcast(s, "add", gson.toJson(f, Flights.class));
    }
    
    private void broadcast(Session s, String type, String content) throws IOException{
        //s.getBasicRemote().sendText(String.format(JSON, type, content));
        for(Session sess : s.getOpenSessions()){
            if(sess.isOpen())
                sess.getBasicRemote().sendText(String.format(JSON, type, content));
        }
    }

    public JsonObject listAsJsonObj(List<Flights> l){
        String json = gson.toJson(l, new TypeToken<List<Flights>>(){}.getType());
        String j2 = "{ \"content\" : "+json+"}";
        System.out.println(j2);
        JsonReader jsonReader = Json.createReader(new StringReader(j2));
        return jsonReader.readObject();
    }
}
