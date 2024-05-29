package dev.alissonmds.literalura.repository;

import dev.alissonmds.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNome(String nome);
}
