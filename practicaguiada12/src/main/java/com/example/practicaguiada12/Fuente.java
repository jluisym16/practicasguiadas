package com.example.practicaguiada12;

public class Fuente {
    private static final String[] NOTICIAS = {
            "El comercio internacional ha aumentado un 7%",
            "Hoy se publica un nuevo libro del coronel Baños",
            "El volcán de Cumbre Vieja ralentiza la emisión de lava",
            "La OCDE espera que el PIB europeo se acelere",
            "La negativa del Congreso a renovar el CGPJ genera una crisis institucional",
            "Los talibanes crean un nuevo gobierno sin mujeres y con alguna minoría",
            "Estados Unidos levanta las restricciones para los vacunados en Europa",
            "El invierno ya está aquí"
    };
    private static int ultima = 0;
    public static String getUltimaNoticia() {
        String noticia = NOTICIAS[ultima++];
        if (ultima == NOTICIAS.length)
            ultima = 0;
        return noticia;
    }
}
