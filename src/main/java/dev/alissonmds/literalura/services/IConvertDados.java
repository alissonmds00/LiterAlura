package dev.alissonmds.literalura.services;

public interface IConvertDados {
    public <T> T converteDados(String json, Class<T> classe);
}
