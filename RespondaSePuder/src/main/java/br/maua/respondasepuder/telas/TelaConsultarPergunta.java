/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.maua.respondasepuder.telas;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Materia;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.persistencia.AlternativaDAO;
import br.maua.respondasepuder.persistencia.MateriaDAO;
import br.maua.respondasepuder.persistencia.QuestaoDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arthur
 */
public class TelaConsultarPergunta extends javax.swing.JFrame {
    private void obterMateriaComboBox() {
        try {
            //Intancia um objeto do tipo MateriaDAO().
            var dao = new MateriaDAO();
            //Chama o método obterMateria() e atribui o seu retorno a variável materias.
            var materias = dao.obterMateria();
            // Converte a lista de matérias para um array e define como modelo do ComboBox 'filtrarMateriaComboBox'
            // Preenche o combobox com as matérias obtidas.
            filtrarMateriaComboBox.setModel(
                    new DefaultComboBoxModel<Materia>(materias.toArray(new Materia[]{}))
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lista de matérias");
        }
    }
    private void mostrarQuestoes(){
        DefaultTableModel model = (DefaultTableModel) consultarPerguntasTable.getModel(); 
        model.setRowCount(0);
        var qdao = new QuestaoDAO();
        var adao = new AlternativaDAO();
        try {
            List<Questao> listaQuestoes = qdao.consultarQuestao(null, null, null);
            Object[]linha = new Object[9];
            for(int i = 0; i < listaQuestoes.size(); i++){
                var q = listaQuestoes.get(i);
                List<Alternativa> listaAlternativas = adao.consultarAlternativa(q);
                linha[0] = q.getEnunciado();
                for(int j= 0; j < 5; j++){
                    linha[j + 1] = listaAlternativas.get(j).getTexto();
                }
                linha[6] = q.getMateria().getNome();
                linha[7] = q.getNivel();
                linha[8] = q.getIdentificador();
                model.addRow(linha);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível conectar-se ao banco de dados");
        }
    }
    
    private void mostrarQuestoesMateria() {
        Materia materia = (Materia) filtrarMateriaComboBox.getSelectedItem();
        String materiaSelecionada = materia.getNome();
        DefaultTableModel model = (DefaultTableModel) consultarPerguntasTable.getModel();
        model.setRowCount(0);
        var qdao = new QuestaoDAO();
        var adao = new AlternativaDAO();
        try {
            List<Questao> listaQuestoes = qdao.consultarQuestao(null, materiaSelecionada, null);
            Object[] linha = new Object[9];
            for(int i = 0; i < listaQuestoes.size(); i++) {
                var q = listaQuestoes.get(i);
                List<Alternativa> listaAlternativas = adao.consultarAlternativa(q);
                linha[0] = q.getEnunciado();
                for(int j= 0; j < 5; j++){
                    linha[j + 1] = listaAlternativas.get(j).getTexto();
                }
                linha[6] = q.getMateria();
                linha[7] = q.getNivel();
                model.addRow(linha);
            }
        } catch (Exception e) {
            Logger.getLogger(TelaConsultarPergunta.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void mostrarQuestoesNivel() {
        String nivelSelecionado = (String) filtrarDificuldadeComboBox.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) consultarPerguntasTable.getModel();
        model.setRowCount(0);
        var qdao = new QuestaoDAO();
        var adao = new AlternativaDAO();
        try {
            List<Questao> listaQuestoes = qdao.consultarQuestao(null, null, nivelSelecionado);
            Object[] linha = new Object[9];
            for(int i = 0; i < listaQuestoes.size(); i++) {
                var q = listaQuestoes.get(i);
                List<Alternativa> listaAlternativas = adao.consultarAlternativa(q);
                linha[0] = q.getEnunciado();
                for(int j= 0; j < 5; j++){
                    linha[j + 1] = listaAlternativas.get(j).getTexto();
                }
                linha[6] = q.getMateria();
                linha[7] = q.getNivel();
                model.addRow(linha);
            }
        } catch (Exception e) {
            Logger.getLogger(TelaConsultarPergunta.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void pesquisarQuestao() {
        String enunciadoPesquisado = (String) pesquisarPerguntaTextField.getText();
        DefaultTableModel model = (DefaultTableModel) consultarPerguntasTable.getModel();
        model.setRowCount(0);
        var qdao = new QuestaoDAO();
        var adao = new AlternativaDAO();
        try{
            List<Questao> listaQuestoes = qdao.consultarQuestao(enunciadoPesquisado, null, null);
            Object[] linha = new Object[9];
            for(int i = 0; i < listaQuestoes.size(); i++){
                var q = listaQuestoes.get(i);
                List<Alternativa> listaAlternativas = adao.consultarAlternativa(q);
                linha[0] = q.getEnunciado();
                for(int j = 0; j < 5; j++) {
                    linha[j + 1] = listaAlternativas.get(j).getTexto();
                }
                linha[6] = q.getMateria();
                linha[7] = q.getNivel();
                model.addRow(linha);
            }
        }
        catch (Exception e) {
            Logger.getLogger(TelaConsultarPergunta.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void removerQuestao(){
        try{
            DefaultTableModel model = (DefaultTableModel) consultarPerguntasTable.getModel();
            var dao = new QuestaoDAO();
            int linhaSelecionada = -1;
            linhaSelecionada = consultarPerguntasTable.getSelectedRow();
            String enunciado = (String) model.getValueAt(linhaSelecionada, 0);
            List<Questao> questao = dao.consultarQuestao(enunciado, null, null);
            var questaoRemovida = questao.get(0);
            dao.removerQuestao(questaoRemovida);
            model.removeRow(linhaSelecionada);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao remover a questao, tente novamente!");
        }
    }
    private void atualizarQuestao() {
        if(consultarPerguntasTable.isEditing()){
            consultarPerguntasTable.getCellEditor().stopCellEditing();
        }
        DefaultTableModel model = (DefaultTableModel) consultarPerguntasTable.getModel();
        var dao = new QuestaoDAO();
        
        var linha = (Integer) consultarPerguntasTable.getSelectedRow();
        String enunciado = (String) consultarPerguntasTable.getValueAt(linha, 0);
        var id = (Integer) consultarPerguntasTable.getValueAt(linha, 8);
            
        try {
            dao.atualizarQuestao(id, enunciado, null, null);
            JOptionPane.showMessageDialog(null, "Questão atualizada com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(TelaConsultarPergunta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    public TelaConsultarPergunta() {
        initComponents();
        obterMateriaComboBox();
        mostrarQuestoes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        consultarPerguntasTable = new javax.swing.JTable();
        filtrarDificuldadeComboBox = new javax.swing.JComboBox<>();
        filtrarMateriaComboBox = new javax.swing.JComboBox<>();
        pesquisarPerguntaTextField = new javax.swing.JTextField();
        atualizarPerguntaButton = new javax.swing.JButton();
        removerPerguntaButton = new javax.swing.JButton();
        adicionarPerguntaButton = new javax.swing.JButton();
        voltarPerguntaQuestaoButton = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        consultarPerguntasTable.setBackground(new java.awt.Color(217, 217, 217));
        consultarPerguntasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pergunta", "Alternativa A", "Alternativa B", "Alternativa C", "Alternativa D", "Alternativa E", "Matéria", "Dificuldade", "id"
            }
        ));
        jScrollPane1.setViewportView(consultarPerguntasTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 1630, 690));

        filtrarDificuldadeComboBox.setBackground(new java.awt.Color(0, 176, 185));
        filtrarDificuldadeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "Fácil", "Médio", "Dificil" }));
        filtrarDificuldadeComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder("FIltrar Dificuldade"));
        filtrarDificuldadeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarDificuldadeComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(filtrarDificuldadeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 60, 310, 70));

        filtrarMateriaComboBox.setBackground(new java.awt.Color(0, 176, 185));
        filtrarMateriaComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder("FIltrar Matéria"));
        filtrarMateriaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarMateriaComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(filtrarMateriaComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 62, 310, 70));

        pesquisarPerguntaTextField.setBackground(new java.awt.Color(242, 92, 84));
        pesquisarPerguntaTextField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        pesquisarPerguntaTextField.setBorder(null);
        pesquisarPerguntaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisarPerguntaTextFieldActionPerformed(evt);
            }
        });
        getContentPane().add(pesquisarPerguntaTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 560, 40));

        atualizarPerguntaButton.setContentAreaFilled(false);
        atualizarPerguntaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        atualizarPerguntaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarPerguntaButtonActionPerformed(evt);
            }
        });
        getContentPane().add(atualizarPerguntaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 880, 380, 100));

        removerPerguntaButton.setContentAreaFilled(false);
        removerPerguntaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        removerPerguntaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerPerguntaButtonActionPerformed(evt);
            }
        });
        getContentPane().add(removerPerguntaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 880, 380, 100));

        adicionarPerguntaButton.setContentAreaFilled(false);
        adicionarPerguntaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        adicionarPerguntaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarPerguntaButtonActionPerformed(evt);
            }
        });
        getContentPane().add(adicionarPerguntaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 880, 380, 100));

        voltarPerguntaQuestaoButton.setContentAreaFilled(false);
        voltarPerguntaQuestaoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        voltarPerguntaQuestaoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltarPerguntaQuestaoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(voltarPerguntaQuestaoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 23, 120, 120));

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Tela consultar pergunta.png"))); // NOI18N
        getContentPane().add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void filtrarDificuldadeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarDificuldadeComboBoxActionPerformed
        mostrarQuestoesNivel();
    }//GEN-LAST:event_filtrarDificuldadeComboBoxActionPerformed

    private void voltarPerguntaQuestaoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltarPerguntaQuestaoButtonActionPerformed
        var telaADM = new TelaDeADM();
        telaADM.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_voltarPerguntaQuestaoButtonActionPerformed

    private void adicionarPerguntaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarPerguntaButtonActionPerformed
        new TelaAdicionarQuestoes().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_adicionarPerguntaButtonActionPerformed

    private void removerPerguntaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerPerguntaButtonActionPerformed
        removerQuestao();
    }//GEN-LAST:event_removerPerguntaButtonActionPerformed

    private void filtrarMateriaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarMateriaComboBoxActionPerformed
       mostrarQuestoesMateria();
    }//GEN-LAST:event_filtrarMateriaComboBoxActionPerformed

    private void pesquisarPerguntaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisarPerguntaTextFieldActionPerformed
        pesquisarQuestao();
    }//GEN-LAST:event_pesquisarPerguntaTextFieldActionPerformed

    private void atualizarPerguntaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarPerguntaButtonActionPerformed
        atualizarQuestao();
    }//GEN-LAST:event_atualizarPerguntaButtonActionPerformed

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
            java.util.logging.Logger.getLogger(TelaConsultarPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarPergunta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConsultarPergunta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarPerguntaButton;
    private javax.swing.JButton atualizarPerguntaButton;
    private javax.swing.JTable consultarPerguntasTable;
    private javax.swing.JComboBox<String> filtrarDificuldadeComboBox;
    private javax.swing.JComboBox<Materia> filtrarMateriaComboBox;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pesquisarPerguntaTextField;
    private javax.swing.JButton removerPerguntaButton;
    private javax.swing.JButton voltarPerguntaQuestaoButton;
    // End of variables declaration//GEN-END:variables
}
