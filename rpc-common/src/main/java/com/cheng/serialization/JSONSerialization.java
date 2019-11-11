package com.cheng.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@Slf4j
public class JSONSerialization implements ISerializable{

    private ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public <T> byte[] serializable(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("jackson write error is {}",e);
        }
        return null;
    }

    @Override
    public <T> T deSerializable(byte[] bytes, Class<T> obj) {
        try {
            T object = objectMapper.readValue(bytes, obj);
            return object;
        } catch (IOException e) {
            log.error("jackson deSerializable error is {}",e);
            return null;
        }
    }
}
