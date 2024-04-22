package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    // Para ler a opção que o usuário escolheu, vamos usar o nosso SCANNER de leitura
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    // Declarando o conversor de
    private ConverteDados conversor = new ConverteDados();

    // Criando uma Constante do endereço
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){


        // Vamos criar uma variável para a pessoa determinar o que ela quer pesquisar:
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                
                """;

        System.out.println(menu);

        // Criar uma variável de opção para pessoa inserir:
        var opcao = leitura.nextLine();


        // Verificar o que digitou com base em trecho, Criaremos uma string de endereço de acordo com o que eu preciso:
        String endereco;
        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        // Cria variável que irá fazer o consumo e dar a resposta JSON
        var json = consumo.obterDados(endereco);
        System.out.println(json);
        // Vamos transformar o Json em uma estrutura: COLEÇÃO, que irá chamar o conversor do JSON e
        var marcas = conversor.obterLista(json, Dados.class);
        // Vamos exibir essa variável em uma ordem:
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nInforme o Código da marca para consulta: ");
        // Agora vamos ler o que o usuário irá inserir
        var codigoMarca = leitura.nextLine();

        //Vamos fazer uma nova requisição do endereço:
        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);

        // representando isso como uma lista de modelos de carro:
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        // Precisamos que usuário digite um trecho do nome do carro
        System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        // Vamos pegar essa lista de modelos, e gerar uma nova lista com os modelos filtrados
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        // Precisamos do Código específico do modelo, e para quais anos tem avaliação
        System.out.println("\nDigite o código do modelo para buscar os valroes de avaliação: ");
        var codigoModelo = leitura.nextLine();


        // Vamos precisar fazer uma requisição, para isso vamos precisar novamente do código do modelo
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        // Com isso conseguimos representar nossos anos.
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        // Vamos percorrer a lista de anos, pegar o dado do carro e jogar em uma lista para exibir posteriormente
        List<Veiculo> veiculos = new ArrayList<>();

        // Menor que o tamanho da lista de anos
        for (int i = 0; i < anos.size(); i++) {
            // vamos definir nosso endereço - inserindo uma variável:
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            // adicionando os veiculos a lista
            veiculos.add(veiculo);
        }

        // Exibindo:
        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}
