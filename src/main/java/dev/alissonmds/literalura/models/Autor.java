package dev.alissonmds.literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();
    private int nascimento;
    private int morte;

    public Autor() {
    }

    public Autor(DadosAutor dados) {
        this.nome = dados.nome();
        try {
            this.nascimento = dados.nascimento();
        } catch (Exception e) {
            this.nascimento = 0;
        }
        try {
            this.morte = dados.morte();
        } catch (Exception e) {
            this.morte = 0;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public int getMorte() {
        return morte;
    }

    public void setMorte(int morte) {
        this.morte = morte;
    }

    public List<String> getNomesLivros() {
        return this.livros.stream()
                .map(l -> l.getTitulo())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", morte=" + morte;
    }
}
