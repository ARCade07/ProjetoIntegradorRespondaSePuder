/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.maua.respondasepuder.telas;

import br.maua.respondasepuder.Jogo;
import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.modelo.QuestaoAlternativa;
import br.maua.respondasepuder.persistencia.AlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoAlternativaDAO;
import br.maua.respondasepuder.persistencia.QuestaoDAO;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class TelaPergunta extends javax.swing.JFrame {
    
    private Jogo jogo;
    private Questao questao;
    private Alternativa alternativaA;
    private Alternativa alternativaB;
    private Alternativa alternativaC;
    private Alternativa alternativaD;
    private Alternativa alternativaE;
    private TelaPergunta telaAtual;
    private int pontuacaoAcumulada;
    private String numeroQuestao;
    private int numeroQuestaoInt;
    private boolean escudoAtivo = false;
    
    public void montarTela(Questao questao) {
        var dao = new QuestaoAlternativaDAO();
        List<QuestaoAlternativa> listaQuestaoAlternativaConsulta;
        QuestaoDAO questaoDAO = new QuestaoDAO();
        try {
            Questao q = questaoDAO.buscarPorId(questao.getIdentificador(), questao);
            enunciadoPerguntaLabel.setText(String.format("<html><p style=\"width:500px\">%s</p></html>", q.getEnunciado()));
            listaQuestaoAlternativaConsulta = (ArrayList<QuestaoAlternativa>) dao.consultarQuestaoAlternativaPorIdQuestao(q);
            this.alternativaA = listaQuestaoAlternativaConsulta.get(0).getResposta();
            this.alternativaB = listaQuestaoAlternativaConsulta.get(1).getResposta();
            this.alternativaC = listaQuestaoAlternativaConsulta.get(2).getResposta();
            this.alternativaD = listaQuestaoAlternativaConsulta.get(3).getResposta();
            this.alternativaE = listaQuestaoAlternativaConsulta.get(4).getResposta();
            
            alternativaALabel.setText(alternativaA.getTexto());
            alternativaBLabel.setText(alternativaB.getTexto());
            alternativaCLabel.setText(alternativaC.getTexto());
            alternativaDLabel.setText(alternativaD.getTexto());
            alternativaELabel.setText(alternativaE.getTexto());
            
            numeroQuestao = String.valueOf(jogo.pergunta);
            numeroPerguntaLabel.setText(numeroQuestao);
            
            numeroQuestaoInt = jogo.pergunta;
            var pontuacao = jogo.receberPontuacao(numeroQuestaoInt,true);
            pontuacaoQuestaoLabel.setText(String.valueOf(pontuacao));
            
            if (numeroQuestaoInt == 1){
                this.pontuacaoAcumulada = 0;
            }
            else {
                this.pontuacaoAcumulada = jogo.receberPontuacao(numeroQuestaoInt - 1, true);
            }
            pontuacaoAcumuladaLabel.setText(String.valueOf(this.pontuacaoAcumulada));
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private void atualizarPontuacaoAcumulada(int numeroPergunta){
        this.pontuacaoAcumulada = jogo.receberPontuacao(numeroPergunta, true);
    }
    private void verificarAlternativa(Alternativa alternativa) {
        var dao = new QuestaoAlternativaDAO();
        try {
            var verificarResposta = jogo.verificarResposta(this.questao, alternativa);

            if (verificarResposta) {
                JOptionPane.showMessageDialog(null, "Acertou!");
                if (jogo.jogoAcabou()){
                    new TelaResultadosPartida(pontuacaoAcumulada * 2, this.numeroQuestaoInt).setVisible(true);
                    telaAtual.dispose();
                }
                else{
                    int pontuacao = jogo.receberPontuacao(jogo.pergunta, verificarResposta);
                    pontuacaoQuestaoLabel.setText(String.valueOf(pontuacao));
                    atualizarPontuacaoAcumulada(jogo.pergunta);
                    var novaQuestao = jogo.randomizarPergunta();
                    new TelaPergunta(jogo, novaQuestao).setVisible(true);
                    telaAtual.dispose();
                }
                
            } else {
                if(jogo.isDesejaUsarEscudo()){
                    jogo.ativarEscudo(false);
                    jogo.setDesejaUsarEscudo(false);
                    return;
                }
                if (escudoAtivo){
                    escudoAtivo = false;
                    JOptionPane.showMessageDialog(null, "Você errou, mas o escudo te protegeu!!!");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Errou!");
                new TelaResultadosPartida(jogo.receberPontuacao(jogo.pergunta, false), this.numeroQuestaoInt - 1).setVisible(true);
                telaAtual.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates new form TelaPergunta
     */
    public TelaPergunta(Jogo jogo, Questao questaoSelecionada) {
        initComponents();
        this.jogo = jogo;
        this.questao = questaoSelecionada;
        this.telaAtual = this;
        montarTela(this.questao);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numeroPerguntaLabel = new javax.swing.JLabel();
        alternativaALabel = new javax.swing.JLabel();
        alternativaAButton = new javax.swing.JButton();
        alternativaBLabel = new javax.swing.JLabel();
        alternativaBButton = new javax.swing.JButton();
        alternativaCLabel = new javax.swing.JLabel();
        alternativaCButton = new javax.swing.JButton();
        alternativaDLabel = new javax.swing.JLabel();
        alternativaDButton = new javax.swing.JButton();
        alternativaELabel = new javax.swing.JLabel();
        alternativaEButton = new javax.swing.JButton();
        eliminarDuasButton = new javax.swing.JButton();
        escudoButton = new javax.swing.JButton();
        pularQuestaoButton = new javax.swing.JButton();
        ativarMusicaButton = new javax.swing.JButton();
        pontuacaoAcumuladaLabel = new javax.swing.JLabel();
        pontuacaoQuestaoLabel = new javax.swing.JLabel();
        enunciadoPerguntaLabel = new javax.swing.JLabel();
        pararJogarButton = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        numeroPerguntaLabel.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        getContentPane().add(numeroPerguntaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 0, 80, 60));

        alternativaALabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        alternativaALabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(alternativaALabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 740, 40));

        alternativaAButton.setBackground(new java.awt.Color(179, 73, 12));
        alternativaAButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alternativaAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alternativaAButtonActionPerformed(evt);
            }
        });
        getContentPane().add(alternativaAButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 760, 60));

        alternativaBLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        alternativaBLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(alternativaBLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 740, 40));

        alternativaBButton.setBackground(new java.awt.Color(179, 73, 12));
        alternativaBButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alternativaBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alternativaBButtonActionPerformed(evt);
            }
        });
        getContentPane().add(alternativaBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 760, 60));

        alternativaCLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        alternativaCLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(alternativaCLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 740, 40));

        alternativaCButton.setBackground(new java.awt.Color(179, 73, 12));
        alternativaCButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alternativaCButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alternativaCButtonActionPerformed(evt);
            }
        });
        getContentPane().add(alternativaCButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 450, 760, 60));

        alternativaDLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        alternativaDLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(alternativaDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 560, 740, 40));

        alternativaDButton.setBackground(new java.awt.Color(179, 73, 12));
        alternativaDButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alternativaDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alternativaDButtonActionPerformed(evt);
            }
        });
        getContentPane().add(alternativaDButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 760, 60));

        alternativaELabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        alternativaELabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(alternativaELabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 660, 740, 40));

        alternativaEButton.setBackground(new java.awt.Color(179, 73, 12));
        alternativaEButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        alternativaEButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alternativaEButtonActionPerformed(evt);
            }
        });
        getContentPane().add(alternativaEButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 650, 760, 60));

        eliminarDuasButton.setContentAreaFilled(false);
        eliminarDuasButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        eliminarDuasButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarDuasButtonActionPerformed(evt);
            }
        });
        getContentPane().add(eliminarDuasButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, 130, 110));

        escudoButton.setContentAreaFilled(false);
        escudoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        escudoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escudoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(escudoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 610, 130, 110));

        pularQuestaoButton.setContentAreaFilled(false);
        pularQuestaoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pularQuestaoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pularQuestaoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(pularQuestaoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 130, 110));

        ativarMusicaButton.setContentAreaFilled(false);
        ativarMusicaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(ativarMusicaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 890, 130, 110));

        pontuacaoAcumuladaLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        getContentPane().add(pontuacaoAcumuladaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 900, 250, 40));

        pontuacaoQuestaoLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        getContentPane().add(pontuacaoQuestaoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 900, 310, 40));

        enunciadoPerguntaLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        getContentPane().add(enunciadoPerguntaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 190, 710, 560));

        pararJogarButton.setContentAreaFilled(false);
        pararJogarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(pararJogarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1760, 10, 110, 110));

        imageLabel.setBackground(new java.awt.Color(255, 106, 19));
        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Tela perguntas.png"))); // NOI18N
        getContentPane().add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -40, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void alternativaCButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alternativaCButtonActionPerformed
        int confirmacao = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Aviso", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            verificarAlternativa(alternativaC);
        }
    }//GEN-LAST:event_alternativaCButtonActionPerformed

    private void pularQuestaoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pularQuestaoButtonActionPerformed
        try {
            var questaoNova = jogo.pularQuestao();
            montarTela(questaoNova);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_pularQuestaoButtonActionPerformed

    private void alternativaAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alternativaAButtonActionPerformed
        int confirmacao = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Aviso", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            verificarAlternativa(alternativaA);
        }
    }//GEN-LAST:event_alternativaAButtonActionPerformed

    private void alternativaBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alternativaBButtonActionPerformed
        int confirmacao = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Aviso", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            verificarAlternativa(alternativaB);
        }
    }//GEN-LAST:event_alternativaBButtonActionPerformed

    private void alternativaDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alternativaDButtonActionPerformed
       int confirmacao = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Aviso", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            verificarAlternativa(alternativaD);
        }
    }//GEN-LAST:event_alternativaDButtonActionPerformed

    private void alternativaEButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alternativaEButtonActionPerformed
        int confirmacao = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Aviso", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            verificarAlternativa(alternativaE);
        }
    }//GEN-LAST:event_alternativaEButtonActionPerformed

    private void eliminarDuasButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarDuasButtonActionPerformed
        try {
            jogo.eliminarDuas(this.questao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_eliminarDuasButtonActionPerformed

    private void escudoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escudoButtonActionPerformed
        jogo.setDesejaUsarEscudo(true);
        JOptionPane.showMessageDialog(null, "Você ativou o escudo e terá mais uma chance, caso erre!!!!");
    }//GEN-LAST:event_escudoButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    var jogo = new Jogo();
                    jogo.novaPartida();
                    var questaoJogo = jogo.randomizarPergunta();
                    new TelaPergunta(jogo, questaoJogo).setVisible(true);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alternativaAButton;
    private javax.swing.JLabel alternativaALabel;
    private javax.swing.JButton alternativaBButton;
    private javax.swing.JLabel alternativaBLabel;
    private javax.swing.JButton alternativaCButton;
    private javax.swing.JLabel alternativaCLabel;
    private javax.swing.JButton alternativaDButton;
    private javax.swing.JLabel alternativaDLabel;
    private javax.swing.JButton alternativaEButton;
    private javax.swing.JLabel alternativaELabel;
    private javax.swing.JButton ativarMusicaButton;
    private javax.swing.JButton eliminarDuasButton;
    private javax.swing.JLabel enunciadoPerguntaLabel;
    private javax.swing.JButton escudoButton;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel numeroPerguntaLabel;
    private javax.swing.JButton pararJogarButton;
    private javax.swing.JLabel pontuacaoAcumuladaLabel;
    private javax.swing.JLabel pontuacaoQuestaoLabel;
    private javax.swing.JButton pularQuestaoButton;
    // End of variables declaration//GEN-END:variables
}
