package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Materia;
import br.maua.respondasepuder.modelo.Questao;
import br.maua.respondasepuder.modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class QuestaoDAO {
    public int adicionarQuestao(Questao questao, int idMateria) throws Exception {
        //Query para adição de questões
        var sql = "INSERT INTO Questao(enunciado, nivel, id_materia, id_professor) VALUES (?, ?, ?, ?)";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            //O segundo argumento do método recupera as chaves primárias geradas
            //automaticamente pelo banco de dados.
            var ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            //Substitui os placeholders da query pelos atributos que foram
            //passados como argumento do método
            ps.setString(1, questao.getEnunciado());
            ps.setString(2, questao.getNivel());
            ps.setInt(3, idMateria);
            ps.setInt(4, Usuario.getUsuarioLogado());
            ps.executeUpdate();
            //Utilização do try-with-resources para fechamento do recurso
            // o método getGenereratedKeys acessa as keys que foram geradas
            try(ResultSet rs = ps.getGeneratedKeys()){
                //Esse método move o cursor para a próxma linha: retorna true se
                //existir uma tupla correspondente, false se não existir.
                if (rs.next()){
                    int idQuestaoGerado = rs.getInt(1);
                    System.out.println("DEBUG: ID da questão gerada: " + idQuestaoGerado);
                    return idQuestaoGerado;
                }
                else {
                    throw new SQLException("Falha ao obter o ID da questão inserida.");
                }
            }
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
        //Query para a remoção de questões
        var sql = "DELETE FROM Questao WHERE id_questao = ?";
        var sqlAlternativas = "DELETE FROM questao_alternativa WHERE id_questao = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
            var psAlt = conexao.prepareStatement(sqlAlternativas);
        ){
            psAlt.setInt(1, questao.getIdentificador());
            psAlt.executeUpdate();
            //Substitui o placeholder da query pelo atributo de questao que
            //foi passado como argumento do método
            ps.setInt(1, questao.getIdentificador());
            //Método para modificações no bd: retorna número de linhas afetadas
            //retorno maior que zero: (true) remoção feita com sucesso
            return ps.executeUpdate() > 0;
        }
    }
    public List<Questao> consultarQuestao(String enunciado, String materia, String nivel) throws Exception{
         // Lista que armazenará os resultados da consulta de questões.
        List<Questao> listaQuestaoConsulta = new ArrayList<>();
        // Utilização da classe StringBuilder para criar a query dinamicamente
        var sql = new StringBuilder("SELECT * FROM Questao JOIN Materia m USING (id_materia) WHERE 1=1");
        // Lista que armazenará os parâmetros da consulta
        List<String> parametrosConsulta = new ArrayList<>();
        // Adição do parâmetro enunciado caso ele não esteja vazio ou nulo
        if (enunciado != null && !enunciado.isEmpty()) {
            sql.append(" AND enunciado LIKE ?");
            parametrosConsulta.add("%" + enunciado + "%");
        }
        // Adição do parâmetro matéria caso ele não esteja vazio ou nulo
        if (materia != null && !materia.isEmpty()) {
            sql.append(" AND m.nome = ?");
            parametrosConsulta.add(materia);
        }
        // Adição do parâmetro nível caso ele não esteja vazio ou nulo
        if (nivel != null && !nivel.isEmpty()) {
            sql.append(" AND nivel = ?");
            parametrosConsulta.add(nivel);
        // Caso nada seja especificado, a consulta será feita
        // com todos as questões (AND 1=1)
        }
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada, há a conversão
            //da variável sql para String
            var ps = conexao.prepareStatement(sql.toString());
        ){
            // laço for que muda dependendo do número de parâmetros adicionados
            for (int i = 0; i < parametrosConsulta.size(); i++) {
                ps.setString(i + 1, parametrosConsulta.get(i));
            }
            //Utilização do try-with-resources para fechamento do recurso
            try(
                var rs = ps.executeQuery();
            ){
                // Move o cursor, enquanto houver tuplas correspondestes a query
                //, o loop é executado.
                while (rs.next()) {
                    // constrói um objeto do tipo Questão
                    var questao = Questao.builder()
                        .identificador(rs.getInt("id_questao"))
                        .enunciado(rs.getString("enunciado"))
                         // constrói um objeto do tipo Matéria
                        .materia(new Materia(
                            rs.getInt("id_materia"),
                            rs.getString("nome")
                        ))
                        .nivel(rs.getString("nivel"))
                        .build();
                    //adiciona a questão a lista
                    listaQuestaoConsulta.add(questao);
                }
            }
        }  
        return listaQuestaoConsulta;
    }
    public boolean atualizarQuestao(int identificador, String enunciado, String materia, String nivel) throws Exception {
        // Utilização da classe StringBuilder para criar a query dinamicamente
        var sql = new StringBuilder("UPDATE Questao SET ");
        //Lista que receberá os campos para construir a query
        List<String> camposParaAtualizar = new ArrayList<>();
        //Lista que receberá os valores para substituir os placeholders;
        List<Object> valores = new ArrayList<>();
        // Adição do parâmetro enunciado caso ele não esteja vazio ou nulo
        if (enunciado != null && !enunciado.isEmpty()) {
            camposParaAtualizar.add("enunciado = ?");
            valores.add(enunciado);
        }
        // Adição do parâmetro matéria caso ele não esteja vazio ou nulo
        if (materia != null && !materia.isEmpty()) {
            camposParaAtualizar.add("materia = ?");
            valores.add(materia);
        }
        // Adição do parâmetro nivel caso ele não esteja vazio ou nulo
        if (nivel != null && !nivel.isEmpty()) {
            camposParaAtualizar.add("nivel = ?");
            valores.add(nivel);
        }
        // Caso não exista nenhum campo para atualizar: retorna false
        if (camposParaAtualizar.isEmpty()) {
            return false;
        }
        // Construção da query dinamicamente
        sql.append(String.join(", ", camposParaAtualizar));
        sql.append(" WHERE id_questao = ?");
         // Adiciona o identificador aos valores
        valores.add(identificador);
        
        try (
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada, há a conversão
            //da variável sql para String
            var ps = conexao.prepareStatement(sql.toString());
        ){
            // laço for que muda dependendo do número de valores adicionados
            for (int i = 0; i < valores.size(); i++){
                ps.setObject(i + 1, valores.get(i));
            }
            //Método para modificações no bd: retorna número de linhas afetadas
            //retorno maior que zero: (true) atualização feita com sucesso
            return ps.executeUpdate() > 0;
        }
    }
    public Questao buscarPorId(int id, Questao questao) throws Exception {  
        //Query para buscar a questão pelo ID
        var sql = "SELECT * FROM Questao WHERE id_questao = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);  
            
        ){
            //Substitui o placeholder da query pelo atributo que
            //foi passado como argumento do método
            ps.setInt(1, id);
            //Utilização do try-with-resources para fechamento do recurso
            try(
                var rs = ps.executeQuery();  
            ){
                //Esse método move o cursor para a próxma linha: retorna true se
                //existe essa questão, false se não existir.
                if (rs.next()) {
                    // O objeto questao recebe o seu identificador e enunciado;
                    questao.setIdentificador(rs.getInt("id_questao"));
                    questao.setEnunciado(rs.getString("enunciado"));
                    //retorna a questão
                    return questao;
                }
            }
        }
        return null;
    }

}
