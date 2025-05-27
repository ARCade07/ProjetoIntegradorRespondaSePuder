package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.modelo.QuestaoAlternativa;
import java.util.*;

public class QuestaoAlternativaDAO {
    public void adicionarQuestaoAlternativa (QuestaoAlternativa questaoAlternativa) throws Exception{
        var sql = "INSERT INTO Questao_Alternativa(id_questao, id_alternativa, alternativa_correta) VALUES (?, ?, ?)";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setInt(1, questaoAlternativa.getQuestao().getIdentificador());
            ps.setInt(2, questaoAlternativa.getResposta().getIdentificador());
            ps.setBoolean(3, questaoAlternativa.isAlternativaCorreta());
            ps.execute();
        }
    }
    public void alterarQuestaoAlternativa(QuestaoAlternativa questaoAlternativa) throws Exception {
        var sql = "UPDATE Questao_Alternativa SET alternativa_correta = ? WHERE id_questao_alternativa = ?)";
        try (
                var conexao = new ConnectionFactory().obterConexao(); 
                var ps = conexao.prepareStatement(sql);
        ) {
            ps.setBoolean(1, questaoAlternativa.isAlternativaCorreta());
            ps.execute();
        }
    }
    public boolean removerQuestaoAlternativa(QuestaoAlternativa questaoAlternativa) throws Exception {
        var sql = "DELETE FROM Questao_Alternativa WHERE id_questao_alternativa = ?";
        try (
                var conexao = new ConnectionFactory().obterConexao(); 
                var ps = conexao.prepareStatement(sql);
        ) {
            ps.execute();
            int linhaRemovida = ps.executeUpdate();
            // O método ps.executeUpdate() retorna linhas alteradas
            return linhaRemovida > 0;
        }
    }
    public List<QuestaoAlternativa> consultarQuestaoAlternativa(Boolean ehCorreta) throws Exception {
        List<QuestaoAlternativa> listaQuestaoAlternativaConsulta = new ArrayList<>();
        var sql = new StringBuilder("SELECT * FROM Questao_Alternativa WHERE 1=1");
        List<String> parametrosConsulta = new ArrayList<>();
        if (ehCorreta != null) {
            sql.append("AND ehCorreta LIKE ?");
            parametrosConsulta.add("%" + ehCorreta + "%");
        }
        try (
            var conexao = new ConnectionFactory().obterConexao(); 
            var ps = conexao.prepareStatement(sql.toString());
        ) {
            for (int i = 0; i < parametrosConsulta.size(); i++) {
                ps.setString(i + 1, parametrosConsulta.get(i));
            }
            try (
                var rs = ps.executeQuery();
            ) {
                while (rs.next()) {
                    var questaoAlternativa = new QuestaoAlternativa();
                    listaQuestaoAlternativaConsulta.add(questaoAlternativa);
                }
            }
        }
        return listaQuestaoAlternativaConsulta;
    }
    public boolean consultarQuestaoAlternativaID(Questao questao, Alternativa alternativa) throws Exception {
        boolean alternativaCorreta = false; // variável de retorno

        var sql = "SELECT ehCorreta FROM Questao_Alternativa WHERE id_questao = ? AND id_alternativa = ?";

        try (
                var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql);) {
            ps.setInt(1, questao.getIdentificador());
            ps.setInt(2, alternativa.getIdentificador());

            try (
                var rs = ps.executeQuery()
            ){
                if (rs.next()) {
                    alternativaCorreta = rs.getBoolean("ehCorreta");
                }
            }
        }

        return alternativaCorreta;
    }
}
