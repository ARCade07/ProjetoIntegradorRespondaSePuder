package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Materia;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.modelo.Usuario;
import java.util.*;

public class QuestaoDAO {
    public void adicionarQuestao(Questao questao, int idMateria) throws Exception {
        var sql = "INSERT INTO Questao(enunciado, nivel, id_materia, id_professor) VALUES (?, ?, ?, ?)";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, questao.getEnunciado());
            ps.setString(2, questao.getNivel());
            ps.setInt(3, idMateria);
            ps.setInt(4, Usuario.getUsuarioLogado() - 1);
            ps.execute();
        }
    }
    public void alterarQuestao(Questao questao) throws Exception {
        var sql = "UPDATE Questao SET enunciado = ? WHERE id = ?";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, questao.getEnunciado());
            ps.setInt(2, questao.getIdentificador());
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
        var sql = new StringBuilder("SELECT * FROM Questao JOIN Materia USING (id_materia) WHERE 1=1");
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
                        .materia(rs.getObject("nome", Materia.class))
                        .nivel(rs.getString("nivel"))
                        .build();
                    listaQuestaoConsulta.add(questao);
                }
            }
        }  
        return listaQuestaoConsulta;
    }
    public boolean atualizarQuestao(int identificador, String enunciado, String materia, String nivel) throws Exception {
        var sql = new StringBuilder("UPDATE Questao SET ");
        List<String> camposParaAtualizar = new ArrayList<>();
        List<Object> valores = new ArrayList<>();
        if (enunciado != null && !enunciado.isEmpty()) {
            camposParaAtualizar.add("enunciado = ?");
            valores.add(enunciado);
        }
        
        if (materia != null && !materia.isEmpty()) {
            camposParaAtualizar.add("materia = ?");
            valores.add(materia);
        }
        
        if (nivel != null && !nivel.isEmpty()) {
            camposParaAtualizar.add("nivel = ?");
            valores.add(nivel);
        }
        
        if (camposParaAtualizar.isEmpty()) {
            return false;
        }
        
        sql.append(String.join(", ", camposParaAtualizar));
        sql.append(" WHERE id_questao = ?");
        valores.add(identificador);
        
        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql.toString());
        ){
            for (int i = 0; i < valores.size(); i++){
                ps.setObject(i + 1, valores.get(i));
            }
            return ps.executeUpdate() > 0;
        }
    }
    public Questao buscarPorId(int id, Questao questao) throws Exception {       
        var sql = "SELECT * FROM questoes WHERE id = ?";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);  
            
        ){
            ps.setInt(1, id);
            try(
                var rs = ps.executeQuery();  
            ){
                if (rs.next()) {
                    questao.setIdentificador(rs.getInt("id_questao"));
                    questao.setEnunciado(rs.getString("enunciado"));
                    return questao;
                }
            }
        }
        return null;
    }

}
