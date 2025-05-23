package br.maua.respondasepuder.modelo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Questao {
    private int identificador;
    private String enunciado;
    private String nivel;
    private Materia materia;
    private List <QuestaoAlternativa> alternativas;
  
    
  
}
