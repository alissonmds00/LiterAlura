package dev.alissonmds.literalura.app;

import dev.alissonmds.literalura.models.*;
import dev.alissonmds.literalura.repository.AutorRepository;
import dev.alissonmds.literalura.repository.LivroRepository;
import dev.alissonmds.literalura.services.API;
import dev.alissonmds.literalura.services.ConverteDados;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    Scanner input = new Scanner(System.in);
    private final String API_URL = "https://gutendex.com/books/?search=";
    private ConverteDados conversor = new ConverteDados();
    private final AutorRepository AUTOR_REPOSITORY;
    private final LivroRepository LIVRO_REPOSITORY;

    public Menu(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.AUTOR_REPOSITORY = autorRepository;
        this.LIVRO_REPOSITORY = livroRepository;
    }

    public void mostrarOpcoes() {
        System.out.println("""
                -=-=-=-=-=-=-OPÇÕES=-=-=-=-=-=-
                1 - Buscar livro título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em um determinado ano
                5 - Listar livros em determinado idioma
                
                0 - Sair
                -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-""");
    }

    public void init() {
        int escolha = 1;
        while (escolha != 0) {
            mostrarOpcoes();
            escolha = input.nextInt();
            input.nextLine();
            switch (escolha) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosAteAno();
                    break;
                case 5:
                    listarLivrosDisponiveisEmIdioma();
                    break;
                case 0:
                    break;
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        try {
            String json = "";
            DadosAutor dadosAutor = null;
            DadosLivro dadosLivro = null;
            System.out.println("Qual o nome do livro?");
            String nomeLivro = input.nextLine();
            String url = "%s%s".formatted(API_URL, nomeLivro.replaceAll(" ", "+"));
            try {
                json = API.obterDados(url);
                dadosLivro = conversor.converteDados(json, DadosLivro.class);
                dadosAutor = conversor.converteDados(json, DadosAutor.class);
                var autor = new Autor(dadosAutor);
                var livro = new Livro(dadosLivro, autor);
                System.out.println(livro);
                salvarLivro(livro, autor);
            } catch (NullPointerException e) {
                System.out.println("Não foi possível encontrar o livro");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Não foi possível encontrar o livro " + e.getMessage());
        }
    }

    public void salvarLivro(Livro livro, Autor autor) {
        try {
                if (!AUTOR_REPOSITORY.existsByNome(autor.getNome())) {
                    AUTOR_REPOSITORY.save(autor);
                }
                if (!LIVRO_REPOSITORY.existsByTitulo(livro.getTitulo())) {
                    LIVRO_REPOSITORY.save(livro);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarLivrosRegistrados() {
        try {
            System.out.println();
            List<Livro> livros = LIVRO_REPOSITORY.findAll();
            if (!livros.isEmpty()) {
                exibirInfoLivros(livros);
            }
        } catch (Exception e) {
            System.out.println("Não foi possível consultar os livros" + e.getMessage());
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = AUTOR_REPOSITORY.findAll();
        exibirInfoAutores(autores);
    }
    private void listarAutoresVivosAteAno() {
        System.out.println("Por qual ano você deseja filtrar?");
        try {
            int ano = input.nextInt();
            List<Autor> autoresFiltrados = AUTOR_REPOSITORY.filterAutoresVivos(ano);
            System.out.println("-=-=-=-Autores vivos até o ano %s-=-=-=-".formatted(ano));
            exibirInfoAutores(autoresFiltrados);
        } catch (InputMismatchException e) {
            System.out.println("O ano digitado não é válido.");
        }
    }

    private void exibirInfoAutores(List<Autor> lista) {
        lista.forEach(a -> System.out.println("""
               -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                %s (%d-%d)
                Obras: %s
               -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
               """
                .formatted(a.getNome(), a.getNascimento(), a.getMorte(), a.getNomesLivros())));
    }

    public void exibirInfoLivros(List<Livro> lista) {
        lista.forEach(l -> System.out.println("""
                            -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                            %s - %s
                            Disponível nos idiomas: %s
                            Total de downloads: %d
                            -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                            """.
                formatted(l.getTitulo(), l.getAutor().getNome(),
                        l.getIdiomas(),
                        l.getDownloads())
        ));
    }

    private void listarLivrosDisponiveisEmIdioma() {
        System.out.println("Qual idioma você deseja filtrar os livros?");
        String idioma = input.nextLine();
        Idioma idiomaBuscado = Idioma.idiomaFromString(idioma);
        //List<Livro> livrosFiltrados = LIVRO_REPOSITORY.findByIdiomasContaining(idiomaBuscado);
        List<Livro> livrosFiltrados = LIVRO_REPOSITORY.findAll().stream().
                filter(l -> l.getIdiomas().contains(idiomaBuscado)).
                collect(Collectors.toList());
        if (!livrosFiltrados.isEmpty()) {
            exibirInfoLivros(livrosFiltrados);
        }
    }
}
