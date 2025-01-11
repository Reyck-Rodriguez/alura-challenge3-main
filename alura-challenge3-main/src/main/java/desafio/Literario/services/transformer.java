package desafio.Literario.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class transformer implements IDataConverter{

    ObjectMapper object_mapper = new ObjectMapper();

    @Override
    public <T> T obternerdato(String json, Class<T> _class) {
        try{
            return object_mapper.readValue(json,_class);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
