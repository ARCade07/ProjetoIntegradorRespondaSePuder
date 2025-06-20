
package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Aluno;
import br.maua.respondasepuder.modelo.Papel;
import br.maua.respondasepuder.modelo.Usuario;
import java.sql.Statement;

public class AlunoDAO {
     public void adicionarAluno(int acertos, int respondidas, int pontuacao) throws Exception{
        //Query para adição de alunos
        var sql = "INSERT INTO aluno(id_usuario, acertos, respondidas, pontuacao, ultima_pontuacao) VALUES (?, ?, ?, ?, ?)";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query pelos atributos que foram
            //passados como argumento do método
            ps.setInt(1, Usuario.getUsuarioLogado());
            ps.setInt(2, acertos);
            ps.setInt(3, respondidas);
            ps.setInt(4, pontuacao);
            ps.setInt(5, pontuacao);
            //executa a query
            ps.execute();
        }
    }
     public void atualizarAluno(int acertos, int respondidas, int ultimaPontuacao) throws Exception{
         //Query para atuazalização de alunos
         var sql = "UPDATE aluno SET acertos = acertos + ?, respondidas = respondidas + ?, pontuacao = pontuacao + ?, ultima_pontuacao = ultima_pontuacao + ? WHERE id_usuario = ?";
         //Utilização do try-with-resources para fechamento da conexão com o bd
         try(
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
        ){
             //Substitui os placeholders da query pelos atributos que foram
            //passados como argumento do método
             ps.setInt(1, acertos);
             ps.setInt(2,respondidas);
             ps.setInt(3, ultimaPontuacao);
             ps.setInt(4, ultimaPontuacao);
             ps.setInt(5, Usuario.getUsuarioLogado());
             //executa a query
             ps.executeUpdate();
         }
     }
     
    public boolean alunoCadastrado() throws Exception {
        var sql = "SELECT * FROM aluno WHERE id_usuario = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try (
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query
            ps.setInt(1, Usuario.getUsuarioLogado());
            //Utilização do try-with-resources para fechamento do recurso
            try (var rs = ps.executeQuery()){
                //Esse método move o cursor para a próxma linha: retorna true se
                //existe esse usuário, false se não existir.
                return rs.next();
            }
        }
    }
    public Aluno alunoPontuacao() throws Exception {
        var sql = "SELECT pontuacao, respondidas, acertos FROM aluno WHERE id_usuario = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try (
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query
            ps.setInt(1, Usuario.getUsuarioLogado());
            //Utilização do try-with-resources para fechamento do recurso
            try (var rs = ps.executeQuery()){
                if (rs.next()) {
                    var aluno = Aluno.builder()
                        .maiorPontuacao(rs.getInt("pontuacao"))
                        .acertos(rs.getInt("acertos"))
                        .respondidas(rs.getInt("respondidas"))
                        .build();
                    return aluno;
                } else {
                    return null;
                }
                
            }
        }
    }
}
