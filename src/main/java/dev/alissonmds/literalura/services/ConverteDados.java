package dev.alissonmds.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alissonmds.literalura.models.DadosAutor;
import dev.alissonmds.literalura.models.DadosLivro;

public class ConverteDados implements IConvertDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T converteDados(String json, Class<T> classe) {
        T response = null;
        try {
            if (classe == DadosLivro.class) {
                JsonNode node = mapper.readTree(json);
                JsonNode results = node.get("results").get(0);
                response = mapper.treeToValue(results, classe);
            } else if (classe == DadosAutor.class) {
                JsonNode node = mapper.readTree(json);
                JsonNode results = node.get("results").get(0).get("authors").get(0);
                response = mapper.treeToValue(results, classe);
            } else {
                response = mapper.readValue(json, classe);
            }
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return response;
    }
}
