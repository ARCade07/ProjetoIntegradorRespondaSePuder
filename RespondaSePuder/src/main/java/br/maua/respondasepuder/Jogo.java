
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
    List<Questao> listaQuestoesFaceis;
    List<Questao> listaQuestoesMedias;
    List<Questao> listaQuestoesDificeis;
    
    private void novaPartida() throws Exception {
        var dao = new QuestaoDAO();
        listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
        listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
        listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
    }
    
    private Questao randomizarPergunta() {
        if(pergunta < 4){
            var r = new Random();
            Questao questaoAtual = listaQuestoesFaceis.get(r.nextInt(listaQuestoesFaceis.size()));
            var index = listaQuestoesFaceis.indexOf(questaoAtual);
            listaQuestoesFaceis.remove(index);
            return questaoAtual;
        }
        else if(pergunta < 10){
            var r = new Random();
            Questao questaoAtual = listaQuestoesMedias.get(r.nextInt(listaQuestoesMedias.size()));
            var index = listaQuestoesMedias.indexOf(questaoAtual);
            listaQuestoesMedias.remove(index);
            return questaoAtual;
        }
        else{
            var r = new Random();
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
    
    private int receberPontuacao (int pergunta, boolean acertou) {
        if(acertou){
            int [] valorPergunta = {1000, 5000, 10000, 25000, 50000, 75000, 100000, 150000, 250000, 400000, 500000, 1000000};
            return valorPergunta[pergunta - 1];
        }
        else{
            int [] valorPergunta = {0, 500, 2500, 10000, 100000, 400000};
            int pontuacao;
            switch(pergunta){
                case 2:
                    pontuacao = valorPergunta[1];
                    break;
                case 3:
                    pontuacao = valorPergunta[2];
                    break;
                case 4:
                case 5:
                case 6:
                    pontuacao = valorPergunta[3];
                    break;
                case 7:
                case 8:
                case 9:
                    pontuacao = valorPergunta[4];
                    break;
                case 10:
                case 11:
                    pontuacao = valorPergunta[5];
                    break;
                default:
                    pontuacao = valorPergunta[0];
                
            }
            return pontuacao;
        }
    }
}
