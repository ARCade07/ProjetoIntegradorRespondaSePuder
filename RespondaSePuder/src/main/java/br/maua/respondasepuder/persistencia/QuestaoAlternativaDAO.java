package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.modelo.QuestaoAlternativa;
import java.sql.SQLException;
import java.util.*;

public class QuestaoAlternativaDAO {
    public void adicionarQuestaoAlternativa (QuestaoAlternativa questaoAlternativa) throws Exception{
        var sql = "INSERT INTO Questao_Alternativa(id_questao, id_alternativa, alternativa_correta) VALUES (?, ?, ?)";
        var sqlVerificarQuestao = "SELECT COUNT(*) FROM Questao WHERE id_questao = ?";
        var sqlVerificarAlternativa = "SELECT COUNT(*) FROM Alternativa WHERE id_alternativa = ?";
        try (var conexao = new ConnectionFactory().obterConexao()){
            try (var psVerificar = conexao.prepareStatement(sqlVerificarQuestao)){
                psVerificar.setInt(1, questaoAlternativa.getQuestao().getIdentificador());
                try (var rsVerificar = psVerificar.executeQuery()){
                    if (rsVerificar.next() && rsVerificar.getInt(1) == 0){
                        throw new SQLException("ERRO: Questão com ID " + questaoAlternativa.getQuestao().getIdentificador() + " não existe no banco!");
                    }
                }
            }
            try (var psVerificarAlt = conexao.prepareStatement(sqlVerificarAlternativa)){
                psVerificarAlt.setInt(1, questaoAlternativa.getResposta().getIdentificador());
                try (var rs = psVerificarAlt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        throw new SQLException("ERRO: Alternativa com ID " + questaoAlternativa.getResposta().getIdentificador() + " não existe!");
                    }
                }
            }    
            try (var ps = conexao.prepareStatement(sql)){
                ps.setInt(1, questaoAlternativa.getQuestao().getIdentificador());
                ps.setInt(2, questaoAlternativa.getResposta().getIdentificador());
                ps.setBoolean(3, questaoAlternativa.isAlternativaCorreta());
                
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Falha ao inserir questão alternativa!");
                }

            }
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
        var sql = "SELECT alternativa_correta FROM Questao_Alternativa WHERE id_questao = ? AND id_alternativa = ?";

        boolean alternativaEhCorreta = false;
        try (
            var conexao = new ConnectionFactory().obterConexao(); var ps = conexao.prepareStatement(sql);
        ) {
            ps.setInt(1, questao.getIdentificador());
            ps.setInt(2, alternativa.getIdentificador());

            try (
                var rs = ps.executeQuery()
            ){
                if (rs.next()) {
                    alternativaEhCorreta = rs.getBoolean("alternativa_correta");
                }
            }
        }
        return alternativaEhCorreta;
    }
    public Alternativa alternativaCorreta(Questao questao) throws Exception {
        var sql = "SELECT * FROM Alternativa a JOIN Questao_Alternativa USING(id_alternativa) JOIN Questao USING(id_questao) WHERE id_questao =?";
        try (
            var conexao = new ConnectionFactory().obterConexao(); 
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setInt(1, questao.getIdentificador());

            try (
                var rs = ps.executeQuery()
            ){
                if (rs.next()) {
                    var alternativaCorreta = Alternativa.builder()
                           .identificador(rs.getInt("id_alternativa"))
                           .texto(rs.getString("texto"))
                           .build();
                    return alternativaCorreta;
                }
            }
        }
        return null;
    }
    public List<QuestaoAlternativa> consultarQuestaoAlternativaPorIdQuestao (Questao questao) throws Exception{
        List<QuestaoAlternativa> listaQuestaoAlternativaConsultaPorIdQuestao = new ArrayList<>();
        var sql = "SELECT qa.alternativa_correta, a.texto, qa.id_alternativa, qa.id_questao FROM Questao_Alternativa qa JOIN Alternativa a USING(id_alternativa) WHERE qa.id_questao = ?";
        try (
            var conexao = new ConnectionFactory().obterConexao(); 
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setInt(1, questao.getIdentificador());
            try (
                var rs = ps.executeQuery()
            ) {
                while (rs.next()) {
                    questao = Questao.builder()
                            .identificador(rs.getInt("id_questao"))
                            .build();
                    var alternativa = Alternativa.builder()
                            .identificador(rs.getInt("id_alternativa"))
                            .texto(rs.getString("texto"))
                            .build();
                    var questaoAlternativa = QuestaoAlternativa.builder()
                            .questao(questao)
                            .resposta(alternativa)
                            .alternativaCorreta(rs.getBoolean("alternativa_correta"))
                            .build();
                    listaQuestaoAlternativaConsultaPorIdQuestao.add(questaoAlternativa);
                }
            }
        }  
        return listaQuestaoAlternativaConsultaPorIdQuestao;
    }
}
