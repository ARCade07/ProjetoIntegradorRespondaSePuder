
package br.maua.respondasepuder;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.persistencia.AlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoAlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoDAO;
import br.maua.respondasepuder.telas.TelaOpcoes;
import br.maua.respondasepuder.telas.TelaOpcoes.MateriaSelectionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Jogo implements MateriaSelectionListener{
    List<Questao> listaQuestoesFaceis;
    List<Questao> listaQuestoesMedias;
    List<Questao> listaQuestoesDificeis;
    public int pergunta;
    int pulos;
    int escudo;
    int eliminaDuas;
    private boolean desejaUsarEscudo = false;
    private boolean usouEscudo = false;
    private String materiaSelecionada;
    
     public void configurarListener(TelaOpcoes tela) {
        tela.addMateriaSelectionListener(this);
    }
    @Override
    public void onMateriaSelected(String materia) {
        this.materiaSelecionada = materia;
    }
    
    public void novaPartida() throws Exception {
        var dao = new QuestaoDAO();
        listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
        listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
        listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
        pergunta = 1;
        pulos = 2;
        escudo = 1;
        eliminaDuas = 1;
    }
    public Questao randomizarPergunta() throws Exception {
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
            if (pergunta <= 12) {
                pergunta += 1; 
            }
            return true;
        } else {
            return false;
        }
    }
    
    public int receberPontuacao (int pergunta, boolean acertou) {
        int [] valorPergunta = {1000, 5000, 10000, 25000, 50000, 75000, 100000, 150000, 250000, 400000, 500000, 1000000};
        int [] valorCheckPoint = {0, 10000, 100000, 400000};
        
        if (pergunta < 1){
            return 0;
        }
        
        if (acertou){
            return valorPergunta[pergunta - 1];
        }
        else{
            if (pergunta <= 3 || pergunta == 12){
                return valorCheckPoint[0];
            }
            else if (pergunta <= 7){
                return valorCheckPoint[1];
            }
            else if (pergunta <= 10) {
                return valorCheckPoint[2];
            }
            else{
                return valorCheckPoint[3];
            }
        }
    }
    
    public boolean jogoAcabou(){
        return pergunta > 12;
    }
    
    public Questao pularQuestao() throws Exception{
        if(pulos > 0 && pergunta != 12){
            var j = new Jogo();
            pulos -= 1;
            var novaPergunta = j.randomizarPergunta();
            return novaPergunta;
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilizar esta ajuda!");
            return null;
        }
    }
    
    public void setDesejaUsarEscudo(boolean deseja){
        this.desejaUsarEscudo = deseja;
    }
    public boolean isDesejaUsarEscudo(){
        return desejaUsarEscudo;
    }
    public void ativarEscudo(boolean acertou) {
        if(!acertou && escudo > 0 && pergunta != 12 && !usouEscudo){
            usouEscudo = true;
            escudo -= 1;
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilzar essa ajuda!");
        }

    }
    
    public List<Alternativa> eliminarDuas (Questao questao) throws Exception {
        if(eliminaDuas > 0 && pergunta != 12){
            var adao = new AlternativaDAO();
            var qadao = new QuestaoAlternativaDAO();
            var r = new Random();
            var alternativaCorreta = qadao.alternativaCorreta(questao);
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
    
