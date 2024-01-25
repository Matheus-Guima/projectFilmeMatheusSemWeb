package br.com.matheus.filmesMatheus.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
