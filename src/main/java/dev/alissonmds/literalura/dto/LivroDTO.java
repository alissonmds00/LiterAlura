package dev.alissonmds.literalura.dto;

public record LivroDTO(Long id,
                       String titulo,
                       String autor,
                       String idiomas,
                       Long downloads) {
}
