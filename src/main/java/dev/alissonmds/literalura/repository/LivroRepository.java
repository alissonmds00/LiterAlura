package dev.alissonmds.literalura.repository;

import dev.alissonmds.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);

    List<Livro> findAll();
}
