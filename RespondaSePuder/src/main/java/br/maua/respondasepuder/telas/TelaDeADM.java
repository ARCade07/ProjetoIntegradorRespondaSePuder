/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.maua.respondasepuder.telas;

/**
 *
 * @author Arthur
 */
public class TelaDeADM extends javax.swing.JFrame {

    /**
     * Creates new form TelaDeADM
     */
    public TelaDeADM() {
        super("Responda se puder");
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        consultarQuestoesButton = new javax.swing.JButton();
        consultarAlunosButton = new javax.swing.JButton();
        ranqueButton = new javax.swing.JButton();
        acessarInformacoesADMButton = new javax.swing.JButton();
        sairDoJogoADMButton = new javax.swing.JButton();
        adicionarProfessorButton = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        consultarQuestoesButton.setContentAreaFilled(false);
        consultarQuestoesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        consultarQuestoesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarQuestoesButtonActionPerformed(evt);
            }
        });
        getContentPane().add(consultarQuestoesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 333, 980, 120));

        consultarAlunosButton.setContentAreaFilled(false);
        consultarAlunosButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        consultarAlunosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarAlunosButtonActionPerformed(evt);
            }
        });
        getContentPane().add(consultarAlunosButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 493, 980, 120));

        ranqueButton.setContentAreaFilled(false);
        ranqueButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ranqueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ranqueButtonActionPerformed(evt);
            }
        });
        getContentPane().add(ranqueButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 830, 700, 100));

        acessarInformacoesADMButton.setContentAreaFilled(false);
        acessarInformacoesADMButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        acessarInformacoesADMButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acessarInformacoesADMButtonActionPerformed(evt);
            }
        });
        getContentPane().add(acessarInformacoesADMButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 13, 150, 150));

        sairDoJogoADMButton.setContentAreaFilled(false);
        sairDoJogoADMButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sairDoJogoADMButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairDoJogoADMButtonActionPerformed(evt);
            }
        });
        getContentPane().add(sairDoJogoADMButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1765, 23, 120, 120));

        adicionarProfessorButton.setContentAreaFilled(false);
        adicionarProfessorButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        adicionarProfessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarProfessorButtonActionPerformed(evt);
            }
        });
        getContentPane().add(adicionarProfessorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 660, 710, 120));

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Tela de ADM.png"))); // NOI18N
        getContentPane().add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -60, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void consultarQuestoesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarQuestoesButtonActionPerformed
        new TelaConsultarPergunta().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_consultarQuestoesButtonActionPerformed

    private void sairDoJogoADMButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairDoJogoADMButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_sairDoJogoADMButtonActionPerformed

    private void consultarAlunosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarAlunosButtonActionPerformed
        
        try {
            new TelaConsultarAluno().setVisible(true);
        } catch (Exception ex) {
            //Logger.getLogger(TelaDeADM.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_consultarAlunosButtonActionPerformed

    private void acessarInformacoesADMButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acessarInformacoesADMButtonActionPerformed
        new TelaConta().setVisible(true);
    }//GEN-LAST:event_acessarInformacoesADMButtonActionPerformed

    private void ranqueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ranqueButtonActionPerformed
        new TelaRanque().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ranqueButtonActionPerformed

    private void adicionarProfessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarProfessorButtonActionPerformed
        new TelaAdicionarProfessor().setVisible(true);
    }//GEN-LAST:event_adicionarProfessorButtonActionPerformed

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
            java.util.logging.Logger.getLogger(TelaDeADM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaDeADM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaDeADM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaDeADM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaDeADM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acessarInformacoesADMButton;
    private javax.swing.JButton adicionarProfessorButton;
    private javax.swing.JButton consultarAlunosButton;
    private javax.swing.JButton consultarQuestoesButton;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JButton ranqueButton;
    private javax.swing.JButton sairDoJogoADMButton;
    // End of variables declaration//GEN-END:variables
}
