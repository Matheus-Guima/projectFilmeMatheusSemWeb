package br.com.matheus.filmesMatheus;

import br.com.matheus.filmesMatheus.principal.Principal;
import br.com.matheus.filmesMatheus.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Ctrl + Alt + O, limpa os imports que não estão sendo usados

@SpringBootApplication
public class FilmesMatheusApplication implements CommandLineRunner {
	@Autowired //injecao de dependencia, instacia uma classe que precisamos usar. Spring que gerencia
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(FilmesMatheusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.exibeMenu();


//		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=cf8ca6c6");
//		//Aqui recebe e faz o mapeamento em dadosEpisodios com o que vem do json, sendo convertino na classe DadosEpisodios
//		DadosEpisodios dadosEpisodios = conversor.converterDados(json, DadosEpisodios.class);
//		System.out.println(dadosEpisodios);

//
	}
}
