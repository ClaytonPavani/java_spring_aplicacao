package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Temos que dizer para ele que esse record Modelos é um list de <Dados> / Usamos o Json Properties para ignorar o desconhecido
@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos(List<Dados> modelos) {

}
