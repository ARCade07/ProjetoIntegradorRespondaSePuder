
package br.maua.respondasepuder;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.persistencia.AlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoAlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoDAO;
import br.maua.respondasepuder.telas.TelaOpcoes;
import br.maua.respondasepuder.telas.TelaOpcoes.DificuldadeListener;
import br.maua.respondasepuder.telas.TelaOpcoes.MateriaListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Jogo implements MateriaListener, DificuldadeListener{
    List<Questao> listaQuestoesFaceis;
    List<Questao> listaQuestoesMedias;
    List<Questao> listaQuestoesDificeis;
    List<Questao> listaQuestoesMatematicaF;
    List<Questao> listaQuestoesMatematicaM;
    List<Questao> listaQuestoesMatematicaD;
    List<Questao> listaQuestoesPortuguesF;
    List<Questao> listaQuestoesPortuguesM;
    List<Questao> listaQuestoesPortuguesD;
    List<Questao> listaQuestoesHistoriaF;
    List<Questao> listaQuestoesHistoriaM;
    List<Questao> listaQuestoesHistoriaD;
    List<Questao> listaQuestoesGeografiaF;
    List<Questao> listaQuestoesGeografiaM;
    List<Questao> listaQuestoesGeografiaD;
    List<Questao> listaQuestoesCienciasF;
    List<Questao> listaQuestoesCienciasM;
    List<Questao> listaQuestoesCienciasD;
    public int pergunta;
    public int pulos;
    int escudo;
    int eliminaDuas;
    private boolean desejaUsarEscudo = false;
    private boolean usouEscudo = false;
    private String materiaSelecionada;
    private String dificuldadeSelecionada;
    
     public void configurarListener(TelaOpcoes tela) {
        tela.adicionarMateriaListener(this);
        tela.adicionarDificuldadeListener(this);
    }
    @Override
    public void notificarMateria(String materia) {
        this.materiaSelecionada = materia;
    }
    @Override
    public void notificarDificuldade(String dificuldade) {
        this.dificuldadeSelecionada = dificuldade;
    }
    public void novaPartida() throws Exception {
        // instancia um objeto da classe QuestaoDAO
        var dao = new QuestaoDAO();
        // lógica para a seleção de perguntas por matéria e dificuldade
        if (materiaSelecionada != null) {
            if (materiaSelecionada.equals("Matemática")) {
                listaQuestoesMatematicaF = (ArrayList<Questao>) dao.consultarQuestao(null, "Matemática", "fácil");
                listaQuestoesMatematicaM = (ArrayList<Questao>) dao.consultarQuestao(null, "Matemática", "médio");
                listaQuestoesMatematicaD = (ArrayList<Questao>) dao.consultarQuestao(null, "Matemática", "difícil");
            } else if (materiaSelecionada.equals("Português")) {
                listaQuestoesPortuguesF = (ArrayList<Questao>) dao.consultarQuestao(null, "Português", "fácil");
                listaQuestoesPortuguesM = (ArrayList<Questao>) dao.consultarQuestao(null, "Português", "médio");
                listaQuestoesPortuguesD = (ArrayList<Questao>) dao.consultarQuestao(null, "Português", "difícil");
            } else if (materiaSelecionada.equals("História")) {
                listaQuestoesHistoriaF = (ArrayList<Questao>) dao.consultarQuestao(null, "História", "fácil");
                listaQuestoesHistoriaM = (ArrayList<Questao>) dao.consultarQuestao(null, "História", "médio");
                listaQuestoesHistoriaD = (ArrayList<Questao>) dao.consultarQuestao(null, "História", "difícil");
            } else if (materiaSelecionada.equals("Geografia")) {
                listaQuestoesGeografiaF = (ArrayList<Questao>) dao.consultarQuestao(null, "Geografia", "fácil");
                listaQuestoesGeografiaM = (ArrayList<Questao>) dao.consultarQuestao(null, "Geografia", "médio");
                listaQuestoesGeografiaD = (ArrayList<Questao>) dao.consultarQuestao(null, "Geografia", "difícil");
            } else if (materiaSelecionada.equals("Ciências")) {
                listaQuestoesCienciasF = (ArrayList<Questao>) dao.consultarQuestao(null, "Ciências", "fácil");
                listaQuestoesCienciasM = (ArrayList<Questao>) dao.consultarQuestao(null, "Ciências", "médio");
                listaQuestoesCienciasD = (ArrayList<Questao>) dao.consultarQuestao(null, "Ciências", "difícil");
            } 
        }
        else {
            listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
            listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
            listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
        }
        // inicializa as variáveis do jogo
        pergunta = 1;
        pulos = 2;
        escudo = 1;
        eliminaDuas = 1;

    }
    public Questao randomizarPergunta() throws Exception {
        // cria um número aleatório
        var r = new Random();
        // lógica para a randomização de perguntas
        if(materiaSelecionada != null){
            if (materiaSelecionada.equals("Matemática")){
                // seleciona as perguntas fáceis dependendo da matéria
                if(pergunta < 4){
                    Questao questaoAtual = listaQuestoesMatematicaF.get(r.nextInt(listaQuestoesMatematicaF.size()));
                    var index = listaQuestoesMatematicaF.indexOf(questaoAtual);
                    listaQuestoesMatematicaF.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas médias dependendo da matéria
                else if(pergunta < 10){
                    Questao questaoAtual = listaQuestoesMatematicaM.get(r.nextInt(listaQuestoesMatematicaM.size()));
                    var index = listaQuestoesMatematicaM.indexOf(questaoAtual);
                    listaQuestoesMatematicaM.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas difíceis dependendo da matéria
                else{
                    Questao questaoAtual = listaQuestoesMatematicaD.get(r.nextInt(listaQuestoesMatematicaD.size()));
                    var index = listaQuestoesMatematicaD.indexOf(questaoAtual);
                    listaQuestoesMatematicaD.remove(index);
                    return questaoAtual;
                }
            }
            else if (materiaSelecionada.equals("Português")){
                // seleciona as perguntas fáceis dependendo da matéria
                if(pergunta < 4){
                    Questao questaoAtual = listaQuestoesPortuguesF.get(r.nextInt(listaQuestoesPortuguesF.size()));
                    var index = listaQuestoesPortuguesF.indexOf(questaoAtual);
                    listaQuestoesPortuguesF.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas médias dependendo da matéria
                else if(pergunta < 10){
                    Questao questaoAtual = listaQuestoesPortuguesM.get(r.nextInt(listaQuestoesPortuguesM.size()));
                    var index = listaQuestoesPortuguesM.indexOf(questaoAtual);
                    listaQuestoesPortuguesM.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas difíceis dependendo da matéria
                else{
                    Questao questaoAtual = listaQuestoesPortuguesD.get(r.nextInt(listaQuestoesPortuguesD.size()));
                    var index = listaQuestoesPortuguesD.indexOf(questaoAtual);
                    listaQuestoesPortuguesD.remove(index);
                    return questaoAtual;
                }
            }
            else if (materiaSelecionada.equals("História")){
                // seleciona as perguntas fáceis dependendo da matéria
                if(pergunta < 4){
                    Questao questaoAtual = listaQuestoesHistoriaF.get(r.nextInt(listaQuestoesHistoriaF.size()));
                    var index = listaQuestoesHistoriaF.indexOf(questaoAtual);
                    listaQuestoesHistoriaF.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas médias dependendo da matéria
                else if(pergunta < 10){
                    Questao questaoAtual = listaQuestoesHistoriaM.get(r.nextInt(listaQuestoesHistoriaM.size()));
                    var index = listaQuestoesHistoriaM.indexOf(questaoAtual);
                    listaQuestoesHistoriaM.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas difíceis dependendo da matéria
                else{
                    Questao questaoAtual = listaQuestoesHistoriaD.get(r.nextInt(listaQuestoesHistoriaD.size()));
                    var index = listaQuestoesHistoriaD.indexOf(questaoAtual);
                    listaQuestoesHistoriaD.remove(index);
                    return questaoAtual;
                }
            }
            else if (materiaSelecionada.equals("Geografia")){
                // seleciona as perguntas fáceis dependendo da matéria
                if(pergunta < 4){
                    Questao questaoAtual = listaQuestoesGeografiaF.get(r.nextInt(listaQuestoesGeografiaF.size()));
                    var index = listaQuestoesGeografiaF.indexOf(questaoAtual);
                    listaQuestoesGeografiaF.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas médias dependendo da matéria
                else if(pergunta < 10){
                    Questao questaoAtual = listaQuestoesGeografiaM.get(r.nextInt(listaQuestoesGeografiaM.size()));
                    var index = listaQuestoesGeografiaM.indexOf(questaoAtual);
                    listaQuestoesGeografiaM.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas difíceis dependendo da matéria
                else{
                    Questao questaoAtual = listaQuestoesGeografiaD.get(r.nextInt(listaQuestoesGeografiaD.size()));
                    var index = listaQuestoesGeografiaD.indexOf(questaoAtual);
                    listaQuestoesGeografiaD.remove(index);
                    return questaoAtual;
                }
            }
            else if (materiaSelecionada.equals("Ciências")){
                // seleciona as perguntas fáceis dependendo da matéria
                if(pergunta < 4){
                    Questao questaoAtual = listaQuestoesCienciasF.get(r.nextInt(listaQuestoesCienciasF.size()));
                    var index = listaQuestoesCienciasF.indexOf(questaoAtual);
                    listaQuestoesCienciasF.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas médias dependendo da matéria
                else if(pergunta < 10){
                    Questao questaoAtual = listaQuestoesCienciasM.get(r.nextInt(listaQuestoesCienciasM.size()));
                    var index = listaQuestoesCienciasM.indexOf(questaoAtual);
                    listaQuestoesCienciasM.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas difíceis dependendo da matéria
                else{
                    Questao questaoAtual = listaQuestoesCienciasD.get(r.nextInt(listaQuestoesCienciasD.size()));
                    var index = listaQuestoesCienciasD.indexOf(questaoAtual);
                    listaQuestoesCienciasD.remove(index);
                    return questaoAtual;       
                }
            }
        }
        else{
            if(dificuldadeSelecionada != null){
                // seleciona as perguntas fáceis
                if(dificuldadeSelecionada.equals("fácil")){
                    Questao questaoAtual = listaQuestoesFaceis.get(r.nextInt(listaQuestoesFaceis.size()));
                    var index = listaQuestoesFaceis.indexOf(questaoAtual);
                    listaQuestoesFaceis.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas médias
                else if(dificuldadeSelecionada.equals("médio")){
                    Questao questaoAtual = listaQuestoesMedias.get(r.nextInt(listaQuestoesMedias.size()));
                    var index = listaQuestoesMedias.indexOf(questaoAtual);
                    listaQuestoesMedias.remove(index);
                    return questaoAtual;
                }
                // seleciona as perguntas difíceis
                else if(dificuldadeSelecionada.equals("dificil")){
                    Questao questaoAtual = listaQuestoesDificeis.get(r.nextInt(listaQuestoesDificeis.size()));
                    var index = listaQuestoesDificeis.indexOf(questaoAtual);
                    listaQuestoesDificeis.remove(index);
                    return questaoAtual;
                }
            }
            else{
                // lógica do jogo sem a seleção de matéria e dificuldade
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
        }
        return null;
    }
    public boolean verificarResposta(Questao questao, Alternativa alternativaEscolhida) throws Exception {
        //instância um objeto da classe QuestaoAlternativaDAO
        var dao = new QuestaoAlternativaDAO();
        //chama o método que consulta se alternativa escolhida é a que cosnta como correta no bd
        if (dao.consultarQuestaoAlternativaID(questao, alternativaEscolhida)) {
            if (pergunta <= 12) {
                //caso não seja a última questão adiciona 1 a pergunta atual
                pergunta += 1; 
            }
            //retorna true se a alternativa escolhida é a correta
            return true;
        } else {
            //retorna falso se a alternativa escolhida for incorreta
            return false;
        }
    }
    
    public int receberPontuacao (int pergunta, boolean acertou) {
        //Criação de um array com os respectivos valores de cada pergunta
        int [] valorPergunta = {1000, 5000, 10000, 25000, 50000, 75000, 100000, 150000, 250000, 400000, 500000, 1000000};
        //Criação de um array com os valores das perguntas que contém checkpoint
        int [] valorCheckPoint = {0, 10000, 100000, 400000};
        
        if (pergunta < 1){
            //Retorna 0 caso a pergunta seja menor que 1
            return 0;
        }
        
        if (acertou){
            //Em caso de acerto da resosta, retorna seu respectivo valor no array valorPergunta
            return valorPergunta[pergunta - 1];
        }
        else{
            //Em caso de resposta incorreta antes do primeiro checkpoint ou na última questão,
            //retorna 0
            if (pergunta <= 3 || pergunta == 12){
                return valorCheckPoint[0];
            }
            //Em caso de resposta incorreta entre as perguntas 4 e 7,
            //retorna o valor do primeiro checkpoint
            else if (pergunta <= 7){
                return valorCheckPoint[1];
            }
            //Em caso de resposta incorreta entre as perguntas 7 e 10,
            //retorna o valor do segundo checkpoint
            else if (pergunta <= 10) {
                return valorCheckPoint[2];
            }
            //Em caso de resposta incorreta para as perguntas 10 ou 11,
            //retorna o valor do último checkpoint
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
            pulos -= 1;
            var novaPergunta = randomizarPergunta();
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
        List<Alternativa> eliminadas = new ArrayList<>();
        if(eliminaDuas > 0 && pergunta != 12){
            var adao = new AlternativaDAO();
            var qadao = new QuestaoAlternativaDAO();
            var r = new Random();
            var alternativaCorreta = qadao.alternativaCorreta(questao);
            List<Alternativa> listaAlternativasQuestao = (ArrayList<Alternativa>) adao.consultarAlternativa(questao);
            String textoCorreto = alternativaCorreta.getTexto();
            listaAlternativasQuestao.removeIf(a -> a.getTexto().equals(textoCorreto));
//            int indexCorreto = -1;
//            for(Alternativa alt : listaAlternativasQuestao){
//                if(alt.getTexto().equals(textoCorreto)){
//                    listaAlternativasQuestao.remove(listaAlternativasQuestao.indexOf(alt));
//                }
//            }
//            for (int i = 0; i < listaAlternativasQuestao.size(); i++) {
//                if (listaAlternativasQuestao.get(i).getTexto().equals(textoCorreto)) {
//                    indexCorreto = i;
//                    break;
//                }
//            }
//            listaAlternativasQuestao.remove(indexCorreto);
            for (int i = 0; i < 2; i++) {
                int index = r.nextInt(listaAlternativasQuestao.size());
                Alternativa alternativaEliminada = listaAlternativasQuestao.remove(index); 
                eliminadas.add(alternativaEliminada);
            }
            return eliminadas;
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilizar essa ajuda!");
        }
        eliminaDuas -= 1;
        return null;
    }
}   
    
