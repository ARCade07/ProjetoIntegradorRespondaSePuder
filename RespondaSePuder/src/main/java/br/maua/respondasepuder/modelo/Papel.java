package br.maua.respondasepuder.modelo;


public class Papel {
    private static int identificador;
    private String nome;

    public static void setIdentificador(int identificador) {
        Papel.identificador = identificador;
        
    
    }

    public static int getIdentificador() {
        return identificador;
    }
    
    
    
}
