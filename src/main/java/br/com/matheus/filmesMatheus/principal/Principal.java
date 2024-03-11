package br.com.matheus.filmesMatheus.principal;

import br.com.matheus.filmesMatheus.model.DadosEpisodios;
import br.com.matheus.filmesMatheus.model.DadosSerie;
import br.com.matheus.filmesMatheus.model.DadosTemporadas;
import br.com.matheus.filmesMatheus.model.Episodio;
import br.com.matheus.filmesMatheus.service.ConsumoApi;
import br.com.matheus.filmesMatheus.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    //Coisas fixas é bom declarar como constante, melhor seria varável de hambiente
    //Constante são decladas com letras maiúcuças em JAVA
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=cf8ca6c6";
    //declara o atributo Scanner na classe, n pode usar "var" aqui

    public void exibeMenu() {
        System.out.println("Digite o nome da série: ");
        var nomeSerie = leitura.nextLine();
        String enderecoCompleto = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;

        var json = consumoApi.obterDados(enderecoCompleto);
        //"https://www.omdbapi.com/?t=gilmore+girls&apikey=cf8ca6c6"

        //Aqui recebe e faz o mapeamento em dadosSerie com o que vem do json, sendo convertino na classe DadosSerie
        DadosSerie dadosSerie = conversor.converterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporadas> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas() ; i++) {
            enderecoCompleto = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY + "&season="+ i;
			json = consumoApi.obterDados(enderecoCompleto);
			DadosTemporadas dadosTemporadas = conversor.converterDados(json, DadosTemporadas.class);
			temporadas.add(dadosTemporadas);
		}
		System.out.println("TEMPORADAS: ");
		//temporadas.forEach(System.out::println);

        //Lambda
        //Normalmente usa uma única letra para declarar, no caso foi a letra "t" e "e".
        //Ele ja entendeo tipo de dados, no caso de "t", vamos estar trabalhando com temporadas, para iterar
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
