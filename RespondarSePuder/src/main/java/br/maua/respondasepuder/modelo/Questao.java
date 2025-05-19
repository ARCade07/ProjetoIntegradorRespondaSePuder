package br.maua.respondasepuder.modelo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Questao {
    private int identificador;
    private String enunciado;
    private String nivel;
    private String materia;
    private List <QuestaoAlternativa> alternativas;
}
