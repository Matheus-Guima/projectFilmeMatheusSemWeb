package br.com.matheus.filmesMatheus.model;

import br.com.matheus.filmesMatheus.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
@Entity //Indica que vai ser uma tabela no banco de dados
@Table(name = "series") //nome da tabelo no banco

public class Serie {
    @Id //Indica que é a chave primaria da tabela no banco
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Explica a estratégia da geração do id
    private long id;
    @Column(unique = true) //Indica que o titulo não pode se repetir
    private String titulo;
    private Integer totalTemporadas;
    @Enumerated(EnumType.STRING) //Indica que Categoria é enum no banco
    private Categoria genero;
    private String atores;
    private String posters;
    private String sinopse;
    private Double avaliacao;
    @Transient //Faz a JPA ignorar por enquanto no banco de dados
    private List<Episodio> episodios = new ArrayList<>();

    //Construtor padrão
    public Serie() {}

    //Contrutor Serie
    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0); //if melhorado, quase try catch
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.posters = dadosSerie.posters();
        this.sinopse = dadosSerie.sinopse();
        //this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPosters() {
        return posters;
    }

    public void setPosters(String posters) {
        this.posters = posters;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", genero=" + genero +
                ", atores='" + atores + '\'' +
                ", posters='" + posters + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", avaliacao=" + avaliacao + '}';
    }
}
