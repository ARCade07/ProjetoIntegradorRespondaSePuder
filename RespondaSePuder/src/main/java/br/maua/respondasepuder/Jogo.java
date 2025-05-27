
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
    int pergunta = 1;
    List<Questao> listaQuestoesFaceis;
    List<Questao> listaQuestoesMedias;
    List<Questao> listaQuestoesDificeis;
    int pulos = 2;
    
    private void novaPartida() throws Exception {
        var dao = new QuestaoDAO();
        listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
        listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
        listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
    }
    
    private Questao randomizarPergunta() {
        var r = new Random();
        if(pergunta < 4){
            Questao questaoAtual = listaQuestoesFaceis.get(r.nextInt(listaQuestoesFaceis.size()));
            var index = listaQuestoesFaceis.indexOf(questaoAtual);
            listaQuestoesFaceis.remove(index);
            return questaoAtual;
        }
        else if(pergunta < 10){
            Questao questaoAtual = listaQuestoesMedias.get(r.nextInt(listaQuestoesMedias.size()));
            var index = listaQuestoesMedias.indexOf(questaoAtual);
            listaQuestoesMedias.remove(index);
            return questaoAtual;
        }
        else{
            Questao questaoAtual = listaQuestoesDificeis.get(r.nextInt(listaQuestoesDificeis.size()));
            var index = listaQuestoesDificeis.indexOf(questaoAtual);
            listaQuestoesDificeis.remove(index);
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
    
