package dto;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {

    private final Map<String, String > session = new HashMap<>();

    public void storeObject(String key, String obj){
        if(!isNull(obj)){
            session.put(key, obj);
        }
    }

    public String getStoreObject(String key){
        return session.get(key);
    }

}
