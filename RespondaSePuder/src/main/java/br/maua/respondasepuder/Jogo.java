
package br.maua.respondasepuder;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.persistencia.AlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoAlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Jogo {
    List<Questao> listaQuestoesFaceis;
    List<Questao> listaQuestoesMedias;
    List<Questao> listaQuestoesDificeis;
    int pergunta;
    int pulos;
    int escudo;
    int eliminaDuas;
    
    private void novaPartida() throws Exception {
        var dao = new QuestaoDAO();
        listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
        listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
        listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
        pergunta = 1;
        pulos = 2;
        escudo = 1;
        eliminaDuas = 1;
    }
    
    public Questao randomizarPergunta() {
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
    public boolean verificarResposta(Questao questao, Alternativa alternativaEscolhida) throws Exception {
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
    
    private void pularQuestao() {
        if(pulos > 0 && pergunta != 12){
            var j = new Jogo();
            Questao novaPergunta = j.randomizarPergunta();
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilizar esta ajuda!");
        }
        pulos -= 1;
    }
    
    private void ativarEscudo(boolean acertou) {
        if(acertou && escudo > 0 && pergunta != 12){
            JOptionPane.showMessageDialog(null, "VOcê ativou o escudo e agora terá uma nova chance de responder essa pergunta");
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilzar essa ajuda!");
        }
        escudo -= 1;
    }
    
    private List<Alternativa> eliminarDuas (Questao questao, Alternativa alternativaCorreta) throws Exception {
        if(eliminaDuas > 0 && pergunta != 12){
            var adao = new AlternativaDAO();
            var qadao = new QuestaoAlternativaDAO();
            var r = new Random();
            alternativaCorreta = qadao.alternativaCorreta(questao);
            List<Alternativa> listaAlternativasQuestao = (ArrayList<Alternativa>) adao.consultarAlternativa(questao);
            var indexCorreto = listaAlternativasQuestao.indexOf(alternativaCorreta);
            listaAlternativasQuestao.remove(indexCorreto);
            for (int i = 0; i < 2; i++) {
                Alternativa alternativaEliminada = listaAlternativasQuestao.get(r.nextInt(listaAlternativasQuestao.size()));
                var index = listaAlternativasQuestao.indexOf(alternativaEliminada);
                listaAlternativasQuestao.remove(index);
            return listaAlternativasQuestao;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilizar essa ajuda!");
        }
        eliminaDuas -= 1;
        return null;
    }
}   
    
