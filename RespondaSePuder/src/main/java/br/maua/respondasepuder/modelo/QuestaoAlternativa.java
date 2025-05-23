package br.maua.respondasepuder.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestaoAlternativa {
    private Questao questao;
    private Alternativa resposta;
    private boolean alternativaCorreta;
}
