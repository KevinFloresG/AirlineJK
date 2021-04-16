package com.airlinejk.websockets;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Kevin Flores
 */
public class Decodifier implements Decoder.Text<JsonObject>{
    
    @Override
    public JsonObject decode(String s) throws DecodeException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(s))) {
            return jsonReader.readObject();
        }
    }
 
    @Override
    public boolean willDecode(String s) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(s))) {
            jsonReader.readObject();
            return true;
        }
    }
 
    @Override
    public void init(EndpointConfig config) {
    }
 
    @Override
    public void destroy() {
    }
    
}
