package dev.alissonmds.literalura.repository;
import dev.alissonmds.literalura.models.Idioma;
import dev.alissonmds.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);

    List<Livro> findAll();

    //@Query("SELECT l FROM Livro l WHERE :idioma MEMBER OF l.idiomas")
    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i = :idioma")
    List<Livro> findByIdiomasContaining(Idioma idioma);
}
