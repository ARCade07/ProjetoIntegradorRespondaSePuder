
package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartidaDAO {
    public void adicionarPartida() throws Exception{
        //Query para adição de partidas
        var sql = "INSERT INTO partida(id_usuario) VALUES (?)";
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
            //executa a query
            ps.execute();
        }
    }
    
}
