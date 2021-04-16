package com.airlinejk.websockets.encoders;

import com.airlinejk.business_logic.Userss;
import com.google.gson.Gson;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Kevin Flores
 */
public class EC_UserList implements Encoder.Text<List<Userss>>{
    
    private final Gson gson = new Gson();

    @Override
    public String encode(List<Userss> users) throws EncodeException {
        String json = gson.toJson(users);
        return json;
    }
    
    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
    
}
