package br.com.matheus.filmesMatheus.service;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}
