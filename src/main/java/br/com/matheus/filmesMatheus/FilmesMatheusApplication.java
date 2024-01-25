package br.com.matheus.filmesMatheus;

import br.com.matheus.filmesMatheus.model.DadosSerie;
import br.com.matheus.filmesMatheus.service.ConsumoApi;
import br.com.matheus.filmesMatheus.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmesMatheusApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilmesMatheusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?i=tt3896198&apikey=cf8ca6c6&t=friends");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
