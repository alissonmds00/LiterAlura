package dev.alissonmds.literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private List<String> idiomas = new ArrayList<>();
    private Long downloads;

    public Livro() {
    }

    public Livro(DadosLivro dados, Autor autor) {
        this.titulo = dados.titulo();
        this.downloads = dados.downloads();
        this.getIdiomas().addAll(dados.idiomas());
        this.autor = autor;
        autor.getLivros().add(this);
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @Override
    public String toString() {
        return "titulo = '" + titulo + '\'' +
                ", autor = " + autor.getNome() +
                ", idiomas = " + idiomas +
                ", downloads = " + downloads;
    }
}
