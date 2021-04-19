package com.airlinejk.websockets;

import com.airlinejk.business_logic.Tickets;
import com.airlinejk.daos.TicketsDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Kevin Flores
 */
@ServerEndpoint(
    value = "/ticket",
    decoders = {Decodifier.class},
    encoders = {Encodifier.class}
)
public class WS_Ticket {
    private final TicketsDao dao = new TicketsDao();
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    private final String JSON = "{\"type\":\"%s\", \"content\":%s}";
    
    @OnOpen
    public void onOpen(Session s) throws IOException, EncodeException {
        String finalJson = String.format(JSON, "all", "\"connected\"");
        s.getBasicRemote().sendText(finalJson);
    }
    
    @OnMessage
    public void onMessage(Session s, JsonObject request) 
            throws IOException, EncodeException {
        switch(request.getString("type")){
            case "checkin":
                checkinTickets(s, request.getString("content"));
                break;
        }
    }
    
    private void checkinTickets(Session s, String Tickets) throws IOException{
        List<Tickets> tkts = gson.fromJson(Tickets, new TypeToken<List<Tickets>>(){}.getType());
        for(Tickets t : tkts){
            dao.insert(t);
        }
        broadcast(s, "checkin", Tickets);
    }
    
    private void broadcast(Session s, String type, String content) throws IOException{
        for(Session sess : s.getOpenSessions()){
            if(sess.isOpen())
                sess.getBasicRemote().sendText(String.format(JSON, type, content));
        }
    }
}
