
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
    var dao = new QuestaoDAO();
    
    // Inicializar todas as listas como vazias para evitar nulls
    initializeLists();
    
    if (materiaSelecionada != null && !materiaSelecionada.equals("Todas")) {
        if (dificuldadeSelecionada != null && !dificuldadeSelecionada.equals("Todas")) {
            // Matéria E dificuldade específicas
            carregarQuestoesPorMateriaEDificuldade(dao);
        } else {
            // Apenas matéria específica (todas as dificuldades)
            carregarQuestoesPorMateria(dao);
        }
    } else {
        if (dificuldadeSelecionada != null && !dificuldadeSelecionada.equals("Todas")) {
            // Apenas dificuldade específica (todas as matérias)
            carregarQuestoesPorDificuldade(dao);
        } else {
            // Todas as matérias e dificuldades
            carregarTodasQuestoes(dao);
        }
    }

    pergunta = 1;
    pulos = 2;
    escudo = 1;
    eliminaDuas = 1;
}
    private void initializeLists() {
        listaQuestoesFaceis = new ArrayList<>();
        listaQuestoesMedias = new ArrayList<>();
        listaQuestoesDificeis = new ArrayList<>();
        listaQuestoesMatematicaF = new ArrayList<>();
        listaQuestoesMatematicaM = new ArrayList<>();
        listaQuestoesMatematicaD = new ArrayList<>();
        listaQuestoesPortuguesF = new ArrayList<>();
        listaQuestoesPortuguesM = new ArrayList<>();
        listaQuestoesPortuguesD = new ArrayList<>();
        listaQuestoesHistoriaF = new ArrayList<>();
        listaQuestoesHistoriaM = new ArrayList<>();
        listaQuestoesHistoriaD = new ArrayList<>();
        listaQuestoesGeografiaF = new ArrayList<>();
        listaQuestoesGeografiaM = new ArrayList<>();
        listaQuestoesGeografiaD = new ArrayList<>();
        listaQuestoesCienciasF = new ArrayList<>();
        listaQuestoesCienciasM = new ArrayList<>();
        listaQuestoesCienciasD = new ArrayList<>();
    }
    private void carregarQuestoesPorMateriaEDificuldade(QuestaoDAO dao) throws Exception {
        String dificuldadeConsulta = dificuldadeSelecionada.toLowerCase();

        // Normalizar a dificuldade para a consulta
        if (dificuldadeConsulta.equals("médio")) {
            dificuldadeConsulta = "médio"; // ou "medio" dependendo do seu banco
        }

        List<Questao> questoes = (ArrayList<Questao>) dao.consultarQuestao(null, materiaSelecionada, dificuldadeConsulta);

        if (questoes == null || questoes.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nenhuma questão encontrada para " + materiaSelecionada + " - " + dificuldadeSelecionada);
            return;
        }

        switch (materiaSelecionada) {
            case "Matemática":
                if (dificuldadeSelecionada.equals("Fácil")) {
                    listaQuestoesMatematicaF = questoes;
                } else if (dificuldadeSelecionada.equals("Médio")) {
                    listaQuestoesMatematicaM = questoes;
                } else if (dificuldadeSelecionada.equals("Difícil")) {
                    listaQuestoesMatematicaD = questoes;
                }
                break;
            case "Português":
                if (dificuldadeSelecionada.equals("Fácil")) {
                    listaQuestoesPortuguesF = questoes;
                } else if (dificuldadeSelecionada.equals("Médio")) {
                    listaQuestoesPortuguesM = questoes;
                } else if (dificuldadeSelecionada.equals("Difícil")) {
                    listaQuestoesPortuguesD = questoes;
                }
                break;
            case "Ciências":
                if (dificuldadeSelecionada.equals("Fácil")) {
                    listaQuestoesCienciasF = questoes;
                } else if (dificuldadeSelecionada.equals("Médio")) {
                    listaQuestoesCienciasM = questoes;
                } else if (dificuldadeSelecionada.equals("Difícil")) {
                    listaQuestoesCienciasD = questoes;
                }
                break;
            case "História":
                if (dificuldadeSelecionada.equals("Fácil")) {
                    listaQuestoesHistoriaF = questoes;
                } else if (dificuldadeSelecionada.equals("Médio")) {
                    listaQuestoesHistoriaM = questoes;
                } else if (dificuldadeSelecionada.equals("Difícil")) {
                    listaQuestoesHistoriaD = questoes;
                }
                break;
            case "Geografia":
                if (dificuldadeSelecionada.equals("Fácil")) {
                    listaQuestoesGeografiaF = questoes;
                } else if (dificuldadeSelecionada.equals("Médio")) {
                    listaQuestoesGeografiaM = questoes;
                } else if (dificuldadeSelecionada.equals("Difícil")) {
                    listaQuestoesGeografiaD = questoes;
                }
                break;
        }
    }


    private void carregarQuestoesPorMateria(QuestaoDAO dao) throws Exception {
        switch (materiaSelecionada) {
            case "Matemática":
                listaQuestoesMatematicaF = (ArrayList<Questao>) dao.consultarQuestao(null, "Matemática", "fácil");
                listaQuestoesMatematicaM = (ArrayList<Questao>) dao.consultarQuestao(null, "Matemática", "médio");
                listaQuestoesMatematicaD = (ArrayList<Questao>) dao.consultarQuestao(null, "Matemática", "difícil");
                break;
            case "Português":
                listaQuestoesPortuguesF = (ArrayList<Questao>) dao.consultarQuestao(null, "Português", "fácil");
                listaQuestoesPortuguesM = (ArrayList<Questao>) dao.consultarQuestao(null, "Português", "médio");
                listaQuestoesPortuguesD = (ArrayList<Questao>) dao.consultarQuestao(null, "Português", "difícil");
                break;
            case "Ciências":
                listaQuestoesCienciasF = (ArrayList<Questao>) dao.consultarQuestao(null, "Ciências", "fácil");
                listaQuestoesCienciasM = (ArrayList<Questao>) dao.consultarQuestao(null, "Ciências", "médio");
                listaQuestoesCienciasD = (ArrayList<Questao>) dao.consultarQuestao(null, "Ciências", "difícil");
                break;
            case "História":
                listaQuestoesHistoriaF = (ArrayList<Questao>) dao.consultarQuestao(null, "História", "fácil");
                listaQuestoesHistoriaM = (ArrayList<Questao>) dao.consultarQuestao(null, "História", "médio");
                listaQuestoesHistoriaD = (ArrayList<Questao>) dao.consultarQuestao(null, "História", "difícil");
                break;
            case "Geografia":
                listaQuestoesGeografiaF = (ArrayList<Questao>) dao.consultarQuestao(null, "Geografia", "fácil");
                listaQuestoesGeografiaM = (ArrayList<Questao>) dao.consultarQuestao(null, "Geografia", "médio");
                listaQuestoesGeografiaD = (ArrayList<Questao>) dao.consultarQuestao(null, "Geografia", "difícil");
                break;
        }
    }


    private void carregarQuestoesPorDificuldade(QuestaoDAO dao) throws Exception {
        String dificuldadeConsulta = dificuldadeSelecionada.toLowerCase();

        switch (dificuldadeConsulta) {
            case "fácil":
                listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
                break;
            case "médio":
                listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
                break;
            case "difícil":
                listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
                break;
        }
    }


    private void carregarTodasQuestoes(QuestaoDAO dao) throws Exception {
        listaQuestoesFaceis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "fácil");
        listaQuestoesMedias = (ArrayList<Questao>) dao.consultarQuestao(null, null, "médio");
        listaQuestoesDificeis = (ArrayList<Questao>) dao.consultarQuestao(null, null, "difícil");
    }


    private Questao pegarQuestaoAleatoria(Random r, List<Questao> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("ERRO: Lista de questões está vazia ou nula!");
            JOptionPane.showMessageDialog(null, "Não há questões disponíveis para os critérios selecionados!");
            return null;
        }

        Questao questaoAtual = lista.get(r.nextInt(lista.size()));
        var index = lista.indexOf(questaoAtual);
        lista.remove(index);

        System.out.println("Questão selecionada: " + questaoAtual.getEnunciado());
        return questaoAtual;
    }

    

    
    public Questao randomizarPergunta() throws Exception{
        var r = new Random();
        
        if(materiaSelecionada != null && !materiaSelecionada.equals("Todas")){
            if(dificuldadeSelecionada != null && !dificuldadeSelecionada.equals("Todas")){
                return randomizarPorMateriaEDificuldade(r);
            }
            else {
                return randomizarPorMateriaComProgressao(r);
            }
        }
        else{
            if(dificuldadeSelecionada != null && !dificuldadeSelecionada.equals("Todas")){
                return randomizarPorDificuldadeEspecifica(r);
            }
            else {
                return randomizarPadrao(r);
            }
        }
    }
    
    private Questao randomizarPorMateriaEDificuldade(Random r) throws Exception {
        var dificuldade = dificuldadeSelecionada.toLowerCase();
        
        switch (materiaSelecionada){
            case "Matemática":
                if (dificuldade.equals("fácil")){
                    return pegarQuestaoAleatoria(r, listaQuestoesMatematicaF);
                }
                else if (dificuldade.equals("médio")){
                    return pegarQuestaoAleatoria(r, listaQuestoesMatematicaM);
                }
                else {
                    return pegarQuestaoAleatoria(r, listaQuestoesMatematicaD);
                }
            case "Português":
                if (dificuldade.equals("fácil")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesPortuguesF);
                } else if (dificuldade.equals("médio")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesPortuguesM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesPortuguesD);
                }
            case "Ciências":
                if (dificuldade.equals("fácil")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesCienciasF);
                } else if (dificuldade.equals("médio")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesCienciasM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesCienciasD);
                }
            case "História":
                if (dificuldade.equals("fácil")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesHistoriaF);
                } else if (dificuldade.equals("médio")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesHistoriaM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesHistoriaD);
                }
            case "Geografia":
                if (dificuldade.equals("fácil")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesGeografiaF);
                } else if (dificuldade.equals("médio")) {
                    return pegarQuestaoAleatoria(r, listaQuestoesGeografiaM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesGeografiaD);
                }
        }
        return null;
    }
    private Questao randomizarPorMateriaComProgressao(Random r) throws Exception{
        switch (materiaSelecionada){
            case "Matemática":
                if (pergunta < 4){
                    return pegarQuestaoAleatoria(r, listaQuestoesMatematicaF);
                }
                else if (pergunta < 10){
                    return pegarQuestaoAleatoria(r, listaQuestoesMatematicaM);
                }
                else {
                    return pegarQuestaoAleatoria(r, listaQuestoesMatematicaD);
                }
            case "Português":
                if (pergunta < 4) {
                    return pegarQuestaoAleatoria(r, listaQuestoesPortuguesF);
                } else if (pergunta < 10) {
                    return pegarQuestaoAleatoria(r, listaQuestoesPortuguesM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesPortuguesD);
                }
            case "Ciências":
                if (pergunta < 4) {
                    return pegarQuestaoAleatoria(r, listaQuestoesCienciasF);
                } else if (pergunta < 10) {
                    return pegarQuestaoAleatoria(r, listaQuestoesCienciasM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesCienciasD);
                }
            case "História":
                if (pergunta < 4) {
                    return pegarQuestaoAleatoria(r, listaQuestoesHistoriaF);
                } else if (pergunta < 10) {
                    return pegarQuestaoAleatoria(r, listaQuestoesHistoriaM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesHistoriaD);
                }
            case "Geografia":
                if (pergunta < 4) {
                    return pegarQuestaoAleatoria(r, listaQuestoesGeografiaF);
                } else if (pergunta < 10) {
                    return pegarQuestaoAleatoria(r, listaQuestoesGeografiaM);
                } else {
                    return pegarQuestaoAleatoria(r, listaQuestoesGeografiaD);
                }
        }
        return null;
    }
    private Questao randomizarPorDificuldadeEspecifica(Random r) throws Exception{
        var dificuldade = dificuldadeSelecionada.toLowerCase();
        
        if (dificuldade.equals("fácil")){
            return pegarQuestaoAleatoria(r, listaQuestoesFaceis);
        }
        else if (dificuldade.equals("médio")){
            return pegarQuestaoAleatoria(r, listaQuestoesMedias);
        }
        else {
            return pegarQuestaoAleatoria(r, listaQuestoesDificeis);
        }
    }
    private Questao randomizarPadrao(Random r) throws Exception {
        if (pergunta < 4){
            return pegarQuestaoAleatoria(r, listaQuestoesFaceis);
        }
        else if (pergunta < 10){
            return pegarQuestaoAleatoria(r, listaQuestoesMedias);
        }
        else {
            return pegarQuestaoAleatoria(r, listaQuestoesDificeis);
        }
    }
    public void debugListas() {
        System.out.println("=== DEBUG LISTAS ===");
        System.out.println("Matéria selecionada: " + materiaSelecionada);
        System.out.println("Dificuldade selecionada: " + dificuldadeSelecionada);

        System.out.println("Lista Fáceis: " + (listaQuestoesFaceis != null ? listaQuestoesFaceis.size() : "null"));
        System.out.println("Lista Médias: " + (listaQuestoesMedias != null ? listaQuestoesMedias.size() : "null"));
        System.out.println("Lista Difíceis: " + (listaQuestoesDificeis != null ? listaQuestoesDificeis.size() : "null"));

        System.out.println("Matemática F: " + (listaQuestoesMatematicaF != null ? listaQuestoesMatematicaF.size() : "null"));
        System.out.println("Matemática M: " + (listaQuestoesMatematicaM != null ? listaQuestoesMatematicaM.size() : "null"));
        System.out.println("Matemática D: " + (listaQuestoesMatematicaD != null ? listaQuestoesMatematicaD.size() : "null"));
    }
//    private Questao pegarQuestaoAleatoria(Random r, List<Questao> lista){
//        if (lista == null || lista.isEmpty()){
//            return null;
//        }
//        Questao questaoAtual = lista.get(r.nextInt(lista.size()));
//        var index = lista.indexOf(questaoAtual);
//        lista.remove(index);
//        return questaoAtual;
//    }
//    public Questao randomizarPergunta() throws Exception {
//        var r = new Random();
//        if(materiaSelecionada != null){
//            if (materiaSelecionada.equals("Matemática")){
//                if(pergunta < 4){
//                    Questao questaoAtual = listaQuestoesMatematicaF.get(r.nextInt(listaQuestoesMatematicaF.size()));
//                    var index = listaQuestoesMatematicaF.indexOf(questaoAtual);
//                    listaQuestoesMatematicaF.remove(index);
//                    return questaoAtual;
//                }
//                else if(pergunta < 10){
//                    Questao questaoAtual = listaQuestoesMatematicaM.get(r.nextInt(listaQuestoesMatematicaM.size()));
//                    var index = listaQuestoesMatematicaM.indexOf(questaoAtual);
//                    listaQuestoesMatematicaM.remove(index);
//                    return questaoAtual;
//                }
//                else{
//                    Questao questaoAtual = listaQuestoesMatematicaD.get(r.nextInt(listaQuestoesMatematicaD.size()));
//                    var index = listaQuestoesMatematicaD.indexOf(questaoAtual);
//                    listaQuestoesMatematicaD.remove(index);
//                    return questaoAtual;
//                }
//            }
//            else if (materiaSelecionada.equals("Português")){
//                if(pergunta < 4){
//                    Questao questaoAtual = listaQuestoesPortuguesF.get(r.nextInt(listaQuestoesPortuguesF.size()));
//                    var index = listaQuestoesPortuguesF.indexOf(questaoAtual);
//                    listaQuestoesPortuguesF.remove(index);
//                    return questaoAtual;
//                }
//                else if(pergunta < 10){
//                    Questao questaoAtual = listaQuestoesPortuguesM.get(r.nextInt(listaQuestoesPortuguesM.size()));
//                    var index = listaQuestoesPortuguesM.indexOf(questaoAtual);
//                    listaQuestoesPortuguesM.remove(index);
//                    return questaoAtual;
//                }
//                else{
//                    Questao questaoAtual = listaQuestoesPortuguesD.get(r.nextInt(listaQuestoesPortuguesD.size()));
//                    var index = listaQuestoesPortuguesD.indexOf(questaoAtual);
//                    listaQuestoesPortuguesD.remove(index);
//                    return questaoAtual;
//                }
//            }
//            else if (materiaSelecionada.equals("História")){
//                if(pergunta < 4){
//                    Questao questaoAtual = listaQuestoesHistoriaF.get(r.nextInt(listaQuestoesHistoriaF.size()));
//                    var index = listaQuestoesHistoriaF.indexOf(questaoAtual);
//                    listaQuestoesHistoriaF.remove(index);
//                    return questaoAtual;
//                }
//                else if(pergunta < 10){
//                    Questao questaoAtual = listaQuestoesHistoriaM.get(r.nextInt(listaQuestoesHistoriaM.size()));
//                    var index = listaQuestoesHistoriaM.indexOf(questaoAtual);
//                    listaQuestoesHistoriaM.remove(index);
//                    return questaoAtual;
//                }
//                else{
//                    Questao questaoAtual = listaQuestoesHistoriaD.get(r.nextInt(listaQuestoesHistoriaD.size()));
//                    var index = listaQuestoesHistoriaD.indexOf(questaoAtual);
//                    listaQuestoesHistoriaD.remove(index);
//                    return questaoAtual;
//                }
//            }
//            else if (materiaSelecionada.equals("Geografia")){
//                if(pergunta < 4){
//                    Questao questaoAtual = listaQuestoesGeografiaF.get(r.nextInt(listaQuestoesGeografiaF.size()));
//                    var index = listaQuestoesGeografiaF.indexOf(questaoAtual);
//                    listaQuestoesGeografiaF.remove(index);
//                    return questaoAtual;
//                }
//                else if(pergunta < 10){
//                    Questao questaoAtual = listaQuestoesGeografiaM.get(r.nextInt(listaQuestoesGeografiaM.size()));
//                    var index = listaQuestoesGeografiaM.indexOf(questaoAtual);
//                    listaQuestoesGeografiaM.remove(index);
//                    return questaoAtual;
//                }
//                else{
//                    Questao questaoAtual = listaQuestoesGeografiaD.get(r.nextInt(listaQuestoesGeografiaD.size()));
//                    var index = listaQuestoesGeografiaD.indexOf(questaoAtual);
//                    listaQuestoesGeografiaD.remove(index);
//                    return questaoAtual;
//                }
//            }
//            else if (materiaSelecionada.equals("Ciências")){
//                if(pergunta < 4){
//                    Questao questaoAtual = listaQuestoesCienciasF.get(r.nextInt(listaQuestoesCienciasF.size()));
//                    var index = listaQuestoesCienciasF.indexOf(questaoAtual);
//                    listaQuestoesCienciasF.remove(index);
//                    return questaoAtual;
//                }
//                else if(pergunta < 10){
//                    Questao questaoAtual = listaQuestoesCienciasM.get(r.nextInt(listaQuestoesCienciasM.size()));
//                    var index = listaQuestoesCienciasM.indexOf(questaoAtual);
//                    listaQuestoesCienciasM.remove(index);
//                    return questaoAtual;
//                }
//                else{
//                    Questao questaoAtual = listaQuestoesCienciasD.get(r.nextInt(listaQuestoesCienciasD.size()));
//                    var index = listaQuestoesCienciasD.indexOf(questaoAtual);
//                    listaQuestoesCienciasD.remove(index);
//                    return questaoAtual;       
//                }
//            }
//        }
//        else{
//            if(dificuldadeSelecionada != null){
//                if(dificuldadeSelecionada.equals("fácil")){
//                    Questao questaoAtual = listaQuestoesFaceis.get(r.nextInt(listaQuestoesFaceis.size()));
//                    var index = listaQuestoesFaceis.indexOf(questaoAtual);
//                    listaQuestoesFaceis.remove(index);
//                    return questaoAtual;
//                }
//                else if(dificuldadeSelecionada.equals("médio")){
//                    Questao questaoAtual = listaQuestoesMedias.get(r.nextInt(listaQuestoesMedias.size()));
//                    var index = listaQuestoesMedias.indexOf(questaoAtual);
//                    listaQuestoesMedias.remove(index);
//                    return questaoAtual;
//                }
//                else if(dificuldadeSelecionada.equals("dificil")){
//                    Questao questaoAtual = listaQuestoesDificeis.get(r.nextInt(listaQuestoesDificeis.size()));
//                    var index = listaQuestoesDificeis.indexOf(questaoAtual);
//                    listaQuestoesDificeis.remove(index);
//                    return questaoAtual;
//                }
//            }
//            else{
//                if(pergunta < 4){
//                    Questao questaoAtual = listaQuestoesFaceis.get(r.nextInt(listaQuestoesFaceis.size()));
//                    var index = listaQuestoesFaceis.indexOf(questaoAtual);
//                    listaQuestoesFaceis.remove(index);
//                    return questaoAtual;
//                }
//                else if(pergunta < 10){
//                    Questao questaoAtual = listaQuestoesMedias.get(r.nextInt(listaQuestoesMedias.size()));
//                    var index = listaQuestoesMedias.indexOf(questaoAtual);
//                    listaQuestoesMedias.remove(index);
//                    return questaoAtual;
//                }
//                else{
//                    Questao questaoAtual = listaQuestoesDificeis.get(r.nextInt(listaQuestoesDificeis.size()));
//                    var index = listaQuestoesDificeis.indexOf(questaoAtual);
//                    listaQuestoesDificeis.remove(index);
//                    return questaoAtual;
//                } 
//            }
//        }
//        return null;
//    }
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
            int indexCorreto = -1;
            for (int i = 0; i < listaAlternativasQuestao.size(); i++) {
                if (listaAlternativasQuestao.get(i).getTexto().equals(textoCorreto)) {
                    indexCorreto = i;
                    break;
                }
            }
            listaAlternativasQuestao.remove(indexCorreto);
            for (int i = 0; i < 2; i++) {
                int index = r.nextInt(listaAlternativasQuestao.size());
                Alternativa alternativaEliminada = listaAlternativasQuestao.remove(index); 
                eliminadas.add(alternativaEliminada);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Você não pode mais utilizar essa ajuda!");
        }
        eliminaDuas -= 1;
        return eliminadas;
    }
}   
    
