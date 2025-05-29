package br.com.alura.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> clas) {
        try {
            return mapper.readValue(json, clas);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
