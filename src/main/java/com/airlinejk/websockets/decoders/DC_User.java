package com.airlinejk.websockets.decoders;

import com.airlinejk.business_logic.Userss;
import com.google.gson.Gson;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Kevin Flores
 */
public class DC_User implements Decoder.Text<Userss>{
    
    private final Gson gson = new Gson();

    @Override
    public Userss decode(String json) throws DecodeException {
        Userss users = gson.fromJson(json, Userss.class);
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
