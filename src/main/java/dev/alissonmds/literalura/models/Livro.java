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
    @ManyToMany(mappedBy = "livros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor = new ArrayList<>();
    private List<String> idiomas;
    private long downloads;

    public Livro() {
    }

    public Livro(Long id, String titulo, String autores, String idiomas, long downloads) {
        this.id = id;
        this.titulo = titulo;
        this.downloads = downloads;
        //Recebe lista de autores e idiomas em string e as separa.
        String[] stringIdiomas = idiomas.split(", ");
        for (String idioma : stringIdiomas) {
            this.getIdiomas().add(idioma);
        }
        String[] stringAutores = autores.split(", ");
        for (String nome : stringAutores) {
            Autor autor = new Autor(nome);
            autor.getLivros().add(this);
            this.autor.add(autor);
        }
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
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
}
