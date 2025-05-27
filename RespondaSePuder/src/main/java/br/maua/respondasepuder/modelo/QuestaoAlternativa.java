package br.maua.respondasepuder.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestaoAlternativa {
    private Questao questao;
    private Alternativa resposta;
    private boolean alternativaCorreta;

}
