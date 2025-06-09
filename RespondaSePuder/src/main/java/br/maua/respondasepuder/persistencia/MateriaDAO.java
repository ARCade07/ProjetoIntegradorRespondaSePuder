package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Materia;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
    public List <Materia> obterMateria() throws Exception {
        // Lista que armazena as matérias
        var materias = new ArrayList <Materia>();
        //Query para a obter todas as matérias.
        var sql = "SELECT * FROM Materia";
        //Utilização do try-with-resources para fechamento da conexão com o bd
        try (
               //Obtém a conexão com o banco de dados
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
                var rs = ps.executeQuery();
        ){
            // Move o cursor, enquanto houver tuplas correspondestes a query
            //, o loop é executado.
            while(rs.next()) {
                //Instancia o objeto materia do tipo Materia
                var materia = Materia.builder()
                        .identificador(rs.getInt("id_materia"))
                        .nome(rs.getString("nome"))
                        .build();
                //Adiciona a materia a lista de materias
                materias.add(materia);
            }
        }
        return materias;
    }
    public int obterMateriaId(Materia materia) throws Exception {
        //Query para a obter todas as matérias.
        var sql = "SELECT id_materia FROM Materia WHERE nome =?";
        try(
                //Obtém a conexão com o banco de dados
                var conexao = new ConnectionFactory().obterConexao();
                //método que prepara a query para ser executada
                var ps = conexao.prepareStatement(sql);
        ){
            //Substitui o placeholder pelo nome da matéria
            ps.setString(1, materia.getNome());
            //Utilização do try-with-resources para fechamento do recurso
            try (var rs = ps.executeQuery()) {
                // Move o cursor, se houver tuplas correspondestes a query é executada
                if (rs.next()) {
                    return rs.getInt("id_materia");
                }
            }
            
        }
        return 0;
    }
}
