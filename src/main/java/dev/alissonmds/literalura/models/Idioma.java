package dev.alissonmds.literalura.models;

public enum Idioma {
    PORTUGUES("pt", "portugues"),
    INGLES("en", "ingles"),
    ESPANHOL("es", "espanhol"),
    FRANCES("it", "italiano"),
    JAPONES("ja", "japones"),
    CHINES("zh", "chines"),
    AFRICANO("af", "africano"),
    ARABE("ar", "arabe"),
    ALEMAO("de", "alemao"),
    COREANO("ko", "coreano"),
    RUSSO("ru", "russo"),
    FINLANDES("fi", "finlandes");


    private String idiomaAPI;
    private String idiomaDigitado;

    Idioma(String idioma, String idiomaDigitado) {
        this.idiomaAPI = idioma;
        this.idiomaDigitado = idiomaDigitado;
    }

    public static Idioma idiomaFromString(String idiomaBuscado) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaDigitado.equalsIgnoreCase(idiomaBuscado)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Não foi encontrado um idioma correspondente");
    }

    public static Idioma idiomaFromAPI(String idiomaLivro) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaAPI.equalsIgnoreCase(idiomaLivro)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Não foi encontrado um idioma correspondente");
    }



}
