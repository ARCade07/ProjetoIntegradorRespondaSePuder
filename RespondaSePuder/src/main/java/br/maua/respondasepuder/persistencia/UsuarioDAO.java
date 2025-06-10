package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Papel;
import br.maua.respondasepuder.modelo.Usuario;
import java.sql.PreparedStatement;
import java.util.*;

public class UsuarioDAO {
    public void adicionarUsuario(Usuario usuario) throws Exception {
        //Query para a adição de usuarios
        var sql = "INSERT INTO Usuario(nome, email, senha, id_papel)"
                + "VALUES (?, ?, ?, ?)";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
                //Obtém a conexão com o banco de dados
                var conexao = new ConnectionFactory().obterConexao();
                //método que prepara a query para ser executada
                var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query pelos atributos do usuário que
            //foi passado como argumento do método
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            // Substitui o placeholder por 1 que corresponde ao papel Aluno
            ps.setInt(4, 1);
            // Executa a query
            ps.execute();
        }
    }
    //Método para adição de Professor
    public void adicionarProfessor(Usuario usuario) throws Exception {
        var sql = "INSERT INTO Usuario(nome, email, senha, id_papel)"
                + "VALUES (?, ?, ?, ?)";
        
        try(
                var conexao = new ConnectionFactory().obterConexao();
                //método que prepara a query para ser executada
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            //Substitui o placeholder por 1 que corresponde ao papel Aluno
            ps.setInt(4, 2);
            ps.execute();
        }
    }
    public boolean removerUsuario(Usuario usuario) throws Exception {
        //Query para a remoção de usuarios
        var sql = "DELETE FROM Usuario WHERE nome = ? AND email = ? AND senha = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try(
                //Obtém a conexão com o banco de dados
                var conexao = new ConnectionFactory().obterConexao();
                //método que prepara a query para ser executada
                var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query pelos atributos do usuário que
            //foi passado como argumento do método
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            //Método para modificações no bd: retorna número de linhas afetadas
            //retorno maior que zero: (true) remoção feita com sucesso
            return ps.executeUpdate() > 0;
        }
       
    }
    //Método utilizado para consultar usuarios em uma Jtable
    public List<Usuario> consultarUsuario(String nome) throws Exception {
        // Lista que armazenará os resultados da consulta de usuários.
        List<Usuario> listaUsuarioConsulta = new ArrayList<>();
        // Utilização da classe StringBuilder para criar a query dinamicamente
        // id_papel está recebendo 1 para que consulte apenas alunos
        var sql = new StringBuilder("SELECT id_usuario, nome, email, senha, id_papel FROM Usuario WHERE id_papel = 1 AND 1=1");
        // Lista que armazenará os parâmetros da consulta
        List<String> parametrosConsulta = new ArrayList<>();
        // Adição do parâmetro nome caso ele não esteja vazio ou nulo
        // caso um nome em específico não seja adicionado, a consulta será feita
        // com todos os usuário (AND 1=1)
        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
            parametrosConsulta.add("%" + nome + "%");
        }
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try (
                //Obtém a conexão com o banco de dados
                var conexao = new ConnectionFactory().obterConexao();
                //método que prepara a query para ser executada, há a conversão
                //da variável sql para String
                var ps = conexao.prepareStatement(sql.toString());
            ){
                //Executa caso o nome seja especificado;
                if (!parametrosConsulta.isEmpty()) {
                ps.setString(1, parametrosConsulta.get(0));
                }
            //Utilização do try-with-resources para fechamento do recurso
            try (
                    var rs = ps.executeQuery();
                ){
                // Move o cursor, enquanto houver tuplas correspondestes a query
                //, o loop é executado.
                while (rs.next()) {
                    // constrói um objeto do tipo Usuário
                    var usuario = Usuario.builder()
                            .identificador(rs.getInt("id_usuario"))
                            .nome(rs.getString("nome"))
                            .email(rs.getString("email"))
                            .senha(rs.getString("senha"))
                            .build();
                    //Adicionana o usuario na lista
                    listaUsuarioConsulta.add(usuario);
                }
            }
        }
        //Retorna a lista (impotante para a JTable)
        return listaUsuarioConsulta;
    }
    public boolean autenticarUsuario(Usuario usuario) throws Exception {
        var sql = "SELECT id_usuario, id_papel, email, senha FROM Usuario JOIN Papel USING (id_papel) WHERE email = ? AND senha = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try (
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query pelos atributos do usuário que
            //foi passado como argumento do método
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            //Utilização do try-with-resources para fechamento do recurso
            try (var rs = ps.executeQuery()){
                //Esse método move o cursor para a próxma linha: retorna true se
                //existe esse usuário, false se não existir.
                if (rs.next()) {
                    // As variáveis recebem o id_usuario e o id_papel.
                    int id = rs.getInt("id_usuario");
                    int id_papel = rs.getInt("id_papel");
                    // Utilizado para verificar qual usuário está logado
                    Usuario.setUsuarioLogado(id);
                    // Utilizado para verificar o papel do usuário
                    Papel.setIdentificador(id_papel);
                    return true;
                }
                return false;
            }
    
        }
    }
    public boolean atualizarUsuario(int identificador, String nome, String email, String senha) throws Exception{
        // Utilização da classe StringBuilder para criar a query dinamicamente
        var sql = new StringBuilder("UPDATE Usuario SET ");
        //Lista que receberá os campos para construir a query
        List<String> camposParaAtualizar = new ArrayList<>();
        //Lista que receberá os valores para substituir os placeholders;
        List<Object> valores = new ArrayList<>();
        // Adição do parâmetro nome caso ele não esteja vazio ou nulo
        if (nome != null && !nome.isEmpty()) {
            camposParaAtualizar.add("nome = ?");
            valores.add(nome);
        }
        // Adição do parâmetro email caso ele não esteja vazio ou nulo
        if (email != null && !email.isEmpty()) {
            camposParaAtualizar.add("email = ?");
            valores.add(email);
        }
        // Adição do parâmetro senha caso ele não esteja vazio ou nulo
        if (senha != null && !senha.isEmpty()) {
            camposParaAtualizar.add("senha = ?");
            valores.add(senha);
        }
        // Caso não exista nenhum campo para atualizar: retorna false
        if (camposParaAtualizar.isEmpty()) {
            return false;
        }
        // Construção da query dinamicamente
        sql.append(String.join(", ", camposParaAtualizar));
        sql.append(" WHERE id_usuario = ?");
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
            for(int i = 0; i < valores.size(); i++) {
                ps.setObject(i + 1, valores.get(i));
            }
            //Método para modificações no bd: retorna número de linhas afetadas
            //retorno maior que zero: (true) atualização feita com sucesso
            return ps.executeUpdate() > 0;
        
        }
           
        
        
    }
    
    public int identificarID(String nome, String email, String senha) throws Exception {
        //Query para identificar o ID do usuário
        var sql = "SELECT id_usuario FROM Usuario WHERE nome = ?, email = ?, senha = ?";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try (
            //Obtém a conexão com o banco de dados
            var conexao = new ConnectionFactory().obterConexao();
            //método que prepara a query para ser executada
            var ps = conexao.prepareStatement(sql);
        ){
            //Substitui os placeholders da query pelos atributos do usuário que
            //foi passado como argumento do método
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            //Utilização do try-with-resources para fechamento do recurso
            try(var rs = ps.executeQuery();){
                // variável id recebe id_usuário
                int id = rs.getInt("id_usuario");
                return id;
            }
          
        }         
    }
}
