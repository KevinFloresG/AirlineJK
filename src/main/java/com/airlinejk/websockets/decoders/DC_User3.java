package com.airlinejk.websockets.decoders;

import com.airlinejk.business_logic.Userss;
import com.airlinejk.websockets.message.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Kevin Flores
 * @param <K>
 */
public class DC_User3<K> implements Decoder.Text<Message<K>>{
    
    private final Gson gson = new Gson();

    @Override
    public Message<K> decode(String json) throws DecodeException {
        Type listType = new TypeToken<Message<K>>(){}.getType();
        Message<K> users = gson.fromJson(json, listType);
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