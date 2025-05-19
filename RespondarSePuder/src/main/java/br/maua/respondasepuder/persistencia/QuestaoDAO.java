package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Questao;
import java.util.*;

public class QuestaoDAO {
    public void adicionarQuestao(Questao questao) throws Exception {
        var sql = "INSERT INTO Questao(enunciado, nivel, materia)"
                + " VALUES (?, ?, ?)";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, questao.getEnunciado());
            ps.setString(2, questao.getNivel());
            ps.setString(3, questao.getMateria());
            ps.execute();
        }
    }
    public boolean removerQuestao(Questao questao) throws Exception {
        var sql = "DELETE FROM Questao WHERE id_questao = ?";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql)
        ){
            ps.setInt(1, questao.getIdentificador());
            ps.execute();
            int linhaRemovida = ps.executeUpdate();
            // O método ps.executeUpdate() retorna linhas alteradas
            return linhaRemovida > 0;
        }
    }
    public List<Questao> consultarQuestao(String enunciado, String materia, String nivel) throws Exception{
        List<Questao> listaQuestaoConsulta = new ArrayList<>();
        var sql = new StringBuilder("SELECT * FROM Questao WHERE 1=1");
        List<String> parametrosConsulta = new ArrayList<>();
        if (enunciado != null && !enunciado.isEmpty()) {
            sql.append(" AND enunciado LIKE ?");
            parametrosConsulta.add("%" + enunciado + "%");
        }
        if (materia != null && !materia.isEmpty()) {
            sql.append(" AND materia = ?");
            parametrosConsulta.add(materia);
        }
        if (nivel != null && !nivel.isEmpty()) {
            sql.append(" AND nivel = ?");
            parametrosConsulta.add(nivel);
        }
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql.toString());
        ){
            for (int i = 0; i < parametrosConsulta.size(); i++) {
                ps.setString(i + 1, parametrosConsulta.get(i));
            }
            try(
                var rs = ps.executeQuery();
            ){
                while (rs.next()) {
                    var questao = Questao.builder()
                        .identificador(rs.getInt("id_questao"))
                        .enunciado(rs.getString("enunciado"))
                        .materia(rs.getString("materia"))
                        .nivel(rs.getString("nivel"))
                        .build();
                    listaQuestaoConsulta.add(questao);
                }
            }
        }  
        return listaQuestaoConsulta;
    }
}
