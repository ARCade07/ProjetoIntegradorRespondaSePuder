
package br.maua.respondasepuder;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.persistencia.AlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoAlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    private int pergunta = 1;
    
    private Questao randomizarPergunta() throws Exception {
        QuestaoDAO dao = new QuestaoDAO();
        List<Questao> listaQuestaoConsulta;
        if(pergunta < 5){
            listaQuestaoConsulta = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
            var r = new Random();
            Questao questaoAtual = listaQuestaoConsulta.get(r.nextInt(listaQuestaoConsulta.size()));
            return questaoAtual;
        }
        else if(pergunta < 11){
            listaQuestaoConsulta = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
            var r = new Random();
            Questao questaoAtual = listaQuestaoConsulta.get(r.nextInt(listaQuestaoConsulta.size()));
            return questaoAtual;
        }
        else{
            listaQuestaoConsulta = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
            var r = new Random();
            Questao questaoAtual = listaQuestaoConsulta.get(r.nextInt(listaQuestaoConsulta.size()));
            return questaoAtual;
        }
    }
    
    private boolean verificarResposta(Questao questao, Alternativa alternativaEscolhida) throws Exception {
        var dao = new QuestaoAlternativaDAO();
        if (dao.consultarQuestaoAlternativaID(questao, alternativaEscolhida)) {
            pergunta += 1; 
            return true;
        } else {
            return false;
        }
    }
}
