package br.maua.respondasepuder.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Aluno {
    private int identificador;
    private Usuario identificadorUsuario;
    private int acertos;
    private int respondidas;
    private int maiorPontuacao;
    private int ultimaPontuacao;
}
