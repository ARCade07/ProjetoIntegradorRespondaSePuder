package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Usuario;
import java.sql.PreparedStatement;
import java.util.*;

public class UsuarioDAO {
    public void adicionarUsuario(Usuario usuario) throws Exception {
        var sql = "INSERT INTO Usuario(nome, email, senha)"
                + "VALUES (?, ?, ?)";
        try(
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.execute();
        }
    }
    public boolean removerUsuario(Usuario usuario) throws Exception {
        var sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try(
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setInt(1, usuario.getIdentificador());
            int linhaRemovida = ps.executeUpdate();
            return linhaRemovida > 0;
        }
       
    }
    public List<Usuario> consultarUsuario(String nome, String email) throws Exception {
        List<Usuario> listaUsuarioConsulta = new ArrayList<>();
        var sql = new StringBuilder("SELECT * FROM Usuario WHERE 1=1");
        List<String> parametrosConsulta = new ArrayList<>();

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
            parametrosConsulta.add("%" + nome + "%");
        }

        if (email != null && !email.isEmpty()) {
            sql.append(" AND email = ?");
            parametrosConsulta.add(email);
        }

        try (
                var conexao = new ConnectionFactory().obterConexao(); 
                var ps = conexao.prepareStatement(sql.toString());
            ){
                for (int i = 0; i < parametrosConsulta.size(); i++) {
                ps.setString(i + 1, parametrosConsulta.get(i));
                }

            try (
                    var rs = ps.executeQuery();
                ){
                while (rs.next()) {
                    var usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                    listaUsuarioConsulta.add(usuario);
                }
            }
        }
        return listaUsuarioConsulta;
    }
    public boolean autenticarUsuario(Usuario usuario) throws Exception {
        var sql = "SELECT email, senha FROM Usuario WHERE email = ? AND senha = ?";
        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            try (var rs = ps.executeQuery()){
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    Usuario.setUsuarioLogado(id);
                    return true;
                }
                return false;
            }
    
    
        }
    }
    public boolean atualizarUsuario(int identificador, String nome, String email, String senha) throws Exception{
        var sql = new StringBuilder("UPDATE Usuario SET ");
        List<String> camposParaAtualizar = new ArrayList<>();
        List<Object> valores = new ArrayList<>();
        if (nome != null && !nome.isEmpty()) {
            camposParaAtualizar.add("nome = ?");
            valores.add(nome);
        }
        
        if (email != null && !email.isEmpty()) {
            camposParaAtualizar.add("email = ?");
            valores.add(email);
        }
        
        if (senha != null && !senha.isEmpty()) {
            camposParaAtualizar.add("senha = ?");
            valores.add(senha);
        }
        
        if (camposParaAtualizar.isEmpty()) {
            return false;
        }
        
        sql.append(String.join(", ", camposParaAtualizar));
        sql.append(" WHERE id_usuario = ?");
        valores.add(identificador);
        
        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql.toString());
        ){
            for(int i = 0; i < valores.size(); i++) {
                ps.setObject(i + 1, valores.get(i));
            }
            return ps.executeUpdate() > 0;
        
        }
           
        
        
    }
    
}
