package dto;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {

    private final Map<String, Object > session = new HashMap<>();

    public void storeObject(String key, Object obj){
        if(!isNull(obj)){
            session.put(key, obj);
        }
    }

    public <T> T getStoreObject(String key, final Class<T> returnType){
        return returnType.cast(session.get(key));
    }

    public Map<String, Object> getSessionStorage() {
        return session;
    }
}
