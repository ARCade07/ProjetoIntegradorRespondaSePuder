package br.maua.respondasepuder.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Alternativa {
    private int identificador;
    private String texto;
}
