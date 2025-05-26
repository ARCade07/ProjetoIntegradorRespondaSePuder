package br.maua.respondasepuder.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Usuario {
    private int identificador;
    private String nome;
    private String email;
    private String senha;
    private Papel papel;
    private static int usuarioLogado;
    
    public static void setUsuarioLogado(int id) {
        usuarioLogado = id;
    }

    public static int getUsuarioLogado() {
        return usuarioLogado;
    }
    
}
