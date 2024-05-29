package dev.alissonmds.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IConvertDados {
    public <T> T converteDados(String json, Class<T> classe) throws JsonProcessingException;
}
