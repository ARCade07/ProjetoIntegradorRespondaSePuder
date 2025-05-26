package br.maua.respondasepuder.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionFactory {
    private String host = "mysql-2913945f-dancruzsan6-61ed.i.aivencloud.com";
    private String port = "27979";
    private String db = "RespondaSePuderdb";
    private String user = "avnadmin";
    private String password = "senha"; 
    
    public Connection obterConexao() throws Exception {
        var s = String.format("jdbc:mysql://%s:%s/%s", host, port, db);
        Connection conexao = DriverManager.getConnection(s, user, password);
        return conexao;
    }

}
