package com.airlinejk.websockets.decoders;

import com.airlinejk.business_logic.Userss;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Kevin Flores
 */
public class DC_UserList implements Decoder.Text<List<Userss>>{
    
    private final Gson gson = new Gson();

    @Override
    public List<Userss> decode(String json) throws DecodeException {
        Type listType = new TypeToken<ArrayList<Userss>>(){}.getType();
        List<Userss> users = gson.fromJson(json, listType);
        return users;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
}
