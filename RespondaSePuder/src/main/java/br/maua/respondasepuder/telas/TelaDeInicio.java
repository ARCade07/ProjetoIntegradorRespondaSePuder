/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.maua.respondasepuder.telas;

import Musica.Musica;
import br.maua.respondasepuder.Jogo;
import br.maua.respondasepuder.modelo.Questao;

/**
 *
 * @author Arthur
 */
public class TelaDeInicio extends javax.swing.JFrame {

    /**
     * Creates new form TelaDeInicio
     */
    public TelaDeInicio() {
        initComponents();
        Musica.tocarMusica();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iniciarJogoButton = new javax.swing.JButton();
        OpcoesButton = new javax.swing.JButton();
        pontuacaoButton = new javax.swing.JButton();
        acessarInformacoesButton = new javax.swing.JButton();
        ativarMusicaButton = new javax.swing.JButton();
        sairDoJogoButton = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iniciarJogoButton.setContentAreaFilled(false);
        iniciarJogoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        iniciarJogoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarJogoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(iniciarJogoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 323, 990, 130));

        OpcoesButton.setContentAreaFilled(false);
        OpcoesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        OpcoesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpcoesButtonActionPerformed(evt);
            }
        });
        getContentPane().add(OpcoesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 493, 690, 100));

        pontuacaoButton.setContentAreaFilled(false);
        pontuacaoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pontuacaoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pontuacaoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(pontuacaoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 633, 690, 110));

        acessarInformacoesButton.setContentAreaFilled(false);
        acessarInformacoesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        acessarInformacoesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acessarInformacoesButtonActionPerformed(evt);
            }
        });
        getContentPane().add(acessarInformacoesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 3, 170, 170));

        ativarMusicaButton.setContentAreaFilled(false);
        ativarMusicaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ativarMusicaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ativarMusicaButtonActionPerformed(evt);
            }
        });
        getContentPane().add(ativarMusicaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 800, 170, 170));

        sairDoJogoButton.setContentAreaFilled(false);
        sairDoJogoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sairDoJogoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairDoJogoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(sairDoJogoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1740, 30, 120, 120));

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Tela de início.png"))); // NOI18N
        imageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -60, 1990, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OpcoesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpcoesButtonActionPerformed
        new TelaOpcoes().setVisible(true);
    }//GEN-LAST:event_OpcoesButtonActionPerformed

    private void ativarMusicaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ativarMusicaButtonActionPerformed
        Musica.alternarMusica();
    }//GEN-LAST:event_ativarMusicaButtonActionPerformed

    private void sairDoJogoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairDoJogoButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_sairDoJogoButtonActionPerformed

    private void acessarInformacoesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acessarInformacoesButtonActionPerformed
        new TelaConta().setVisible(true);
    }//GEN-LAST:event_acessarInformacoesButtonActionPerformed

    private void iniciarJogoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarJogoButtonActionPerformed
        try {
            Jogo jogo = new Jogo();
            jogo.novaPartida();
            var questaoAleatoria = jogo.randomizarPergunta();
            
            new TelaPergunta(jogo, questaoAleatoria).setVisible(true);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_iniciarJogoButtonActionPerformed

    private void pontuacaoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pontuacaoButtonActionPerformed
        new TelaPontuacaoAluno().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_pontuacaoButtonActionPerformed

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
            java.util.logging.Logger.getLogger(TelaDeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDeInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDeInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OpcoesButton;
    private javax.swing.JButton acessarInformacoesButton;
    private javax.swing.JButton ativarMusicaButton;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JButton iniciarJogoButton;
    private javax.swing.JButton pontuacaoButton;
    private javax.swing.JButton sairDoJogoButton;
    // End of variables declaration//GEN-END:variables
}
