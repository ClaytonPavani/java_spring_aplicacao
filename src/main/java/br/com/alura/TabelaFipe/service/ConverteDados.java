package br.com.alura.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        //vamos criar aqui um Collection Type: - Esse método serve para construirmos uma coleção que vamos usar a LIST a partir da classe que passarmos
        CollectionType lista = mapper.getTypeFactory()
                // Assim ele irá construir uma lista com a classe que passarmos - Indicamos o genérico para a classe com o collection type
                .constructCollectionType(List.class, classe);

        // Vamos retornar um mapper que irá ler os valores do JSON e da Classe lista
        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
