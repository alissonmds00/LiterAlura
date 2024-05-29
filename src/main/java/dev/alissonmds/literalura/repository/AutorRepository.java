package dev.alissonmds.literalura.repository;

import dev.alissonmds.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.nascimento <= :ano AND a.morte >= :ano")
    List<Autor> filterAutoresVivos(int ano);
}
