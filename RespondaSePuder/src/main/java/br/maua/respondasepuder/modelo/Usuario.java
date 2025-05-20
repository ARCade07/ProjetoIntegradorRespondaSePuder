package br.maua.respondasepuder.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
    private int identificador;
    private String nome;
    private String email;
    private String senha;
}
