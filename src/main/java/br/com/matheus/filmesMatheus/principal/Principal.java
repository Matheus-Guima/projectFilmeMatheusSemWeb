package br.com.matheus.filmesMatheus.principal;

import br.com.matheus.filmesMatheus.model.*;
import br.com.matheus.filmesMatheus.repository.SerieRepository;
import br.com.matheus.filmesMatheus.service.ConsumoApi;
import br.com.matheus.filmesMatheus.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    //declara o atributo Scanner na classe, n pode usar "var" aqui
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    //Coisas fixas é bom declarar como constante, melhor seria varável de hambiente
    //Constante são decladas com letras maiúcuças em JAVA
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_APIKEY");
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private SerieRepository repository;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Séries Buscadas
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        //dadosSeries.add(dados);
        Serie serie = new Serie(dados);
        //injecao de dependencia, instacia uma classe que precisamos usar
        repository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.converterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumoApi.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporadas dadosTemporada = conversor.converterDados(json, DadosTemporadas.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas() {
        //List<Serie> series = new ArrayList<>();
        //series = dadosSeries.stream()
                        //.map(d -> new Serie(d))
                                //.collect(Collectors.toList());

        List<Serie> series = repository.findAll(); //find all retorna uma lista de <T>
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}