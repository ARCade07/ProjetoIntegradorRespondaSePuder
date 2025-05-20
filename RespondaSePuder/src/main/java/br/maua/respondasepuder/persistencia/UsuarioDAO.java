package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Usuario;

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
    
}
