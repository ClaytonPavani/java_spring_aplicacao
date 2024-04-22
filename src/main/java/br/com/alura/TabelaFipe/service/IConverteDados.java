package br.com.alura.TabelaFipe.service;

import java.util.List;

public interface IConverteDados {
    // Cabeçalho Genérico para converter dados para uma Classe:
    <T> T obterDados(String json, Class<T> classe);

    // Vamos criar um método para obter uma lista - Inserir o tipo genérico e que vamos devolver o list de alguma coisa:
    // é o generic types

    <T>List<T> obterLista(String json, Class<T> classe);

}
