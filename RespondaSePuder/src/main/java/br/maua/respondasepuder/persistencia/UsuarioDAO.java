package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Papel;
import br.maua.respondasepuder.modelo.Usuario;
import java.sql.PreparedStatement;
import java.util.*;

public class UsuarioDAO {
    public void adicionarUsuario(Usuario usuario) throws Exception {
        var sql = "INSERT INTO Usuario(nome, email, senha, id_papel)"
                + "VALUES (?, ?, ?, ?)";
        try(
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, 1);
            ps.execute();
        }
    }
    public void adicionarProfessor(Usuario usuario) throws Exception {
        var sql = "INSERT INTO Usuario(nome, email, senha, id_papel)"
                + "VALUES (?, ?, ?, ?)";
        try(
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, 2);
            ps.execute();
        }
    }
    public boolean removerUsuario(Usuario usuario) throws Exception {
        var sql = "DELETE FROM Usuario WHERE nome = ? AND email = ? AND senha = ?";
        try(
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            int linhaRemovida = ps.executeUpdate();
            return linhaRemovida > 0;
        }
       
    }
    public Object[] consultarUsuario(String nome) throws Exception {
        List<Object> listaUsuarioConsulta = new ArrayList<>();
        var sql = new StringBuilder("SELECT id_usuario, nome, email, senha, id_papel FROM Usuario WHERE id_papel = 1 AND 1=1");
        List<String> parametrosConsulta = new ArrayList<>();

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
            parametrosConsulta.add("%" + nome + "%");
        }
        try (
                var conexao = new ConnectionFactory().obterConexao(); 
                var ps = conexao.prepareStatement(sql.toString());
            ){
                if (!parametrosConsulta.isEmpty()) {
                ps.setString(1, parametrosConsulta.get(0));
                }

            try (
                    var rs = ps.executeQuery();
                ){
                while (rs.next()) {
                    var usuario = Usuario.builder()
                            .identificador(rs.getInt("id_usuario"))
                            .nome(rs.getString("nome"))
                            .email(rs.getString("email"))
                            .senha(rs.getString("senha"))
                            .build();
                    listaUsuarioConsulta.add(usuario);
                }
            }
        }
        return listaUsuarioConsulta.toArray();
    }
    public boolean autenticarUsuario(Usuario usuario) throws Exception {
        var sql = "SELECT id_usuario, id_papel, email, senha FROM Usuario JOIN Papel USING (id_papel) WHERE email = ? AND senha = ?";
        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            try (var rs = ps.executeQuery()){
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    int id_papel = rs.getInt("id_papel");
                    Usuario.setUsuarioLogado(id);
                    Papel.setIdentificador(id_papel);
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
    
    public int identificarID(String nome, String email, String senha) throws Exception {
        var sql = "SELECT id_usuario FROM Usuario WHERE nome = ?, email = ?, senha = ?";
        try (
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            try(var rs = ps.executeQuery();){
                int id = rs.getInt("id_usuario");
                return id;
            }
          
        }         
    }
}
