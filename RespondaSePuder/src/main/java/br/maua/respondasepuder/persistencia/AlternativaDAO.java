package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Alternativa;
import br.maua.respondasepuder.modelo.Questao;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class AlternativaDAO {
    public int adicionarAlternativa(Alternativa alternativa) throws Exception {
        var sql = "INSERT INTO Alternativa(texto) VALUES (?)";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setString(1, alternativa.getTexto());
            ps.execute();
            
            try (var rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    int idGerado = rs.getInt(1);
                    alternativa.setIdentificador(idGerado);
                    return idGerado;
                }
            }
        }
        throw new SQLException("Falha ao obter ID");
    }
    public void alterarAlternativa(Alternativa alternativa) throws Exception {
        var sql = "UPDATE Alternativa SET texto = ? WHERE id = ?";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, alternativa.getTexto());
            ps.setInt(2, alternativa.getIdentificador());
            ps.execute();
        }
    }
    public boolean removerQuestao(Alternativa alternativa) throws Exception {
        var sql = "DELETE FROM Alternativa WHERE id_alternativa = ?";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql)
        ){
            ps.setInt(1, alternativa.getIdentificador());
            ps.execute();
            int linhaRemovida = ps.executeUpdate();
            // O método ps.executeUpdate() retorna linhas alteradas
            return linhaRemovida > 0;
        }
    }
    public List<Alternativa> consultarAlternativa(Questao questao) throws Exception{
        List<Alternativa> listaAlternativaConsulta = new ArrayList<>();
        var sql = "SELECT * FROM Alternativa WHERE id_questao = ?";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setInt(1, questao.getIdentificador());
            try(
                var rs = ps.executeQuery();
            ){
                while (rs.next()) {
                    var alternativa = Alternativa.builder()
                        .identificador(rs.getInt("id_alternativa"))
                        .texto(rs.getString("texto"))
                        .build();
                    listaAlternativaConsulta.add(alternativa);
                }
            }
        }  
        return listaAlternativaConsulta;
    }
}
