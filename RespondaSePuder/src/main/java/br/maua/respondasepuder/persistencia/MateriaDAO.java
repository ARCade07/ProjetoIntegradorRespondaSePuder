package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Materia;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
    public List <Materia> obterMateria() throws Exception {
        var materias = new ArrayList <Materia>();
        var sql = "SELECT * FROM Materia";
        try (
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
                var rs = ps.executeQuery();
        ){
            while(rs.next()) {
                var materia = Materia.builder()
                        .identificador(rs.getInt("id_materia"))
                        .nome(rs.getString("nome"))
                        .build();
                materias.add(materia);
            }
        }
        return materias;
    }
    public int obterMateriaId(Materia materia) throws Exception {
        var sql = "SELECT id_materia FROM Materia WHERE nome =?";
        try(
                var conexao = new ConnectionFactory().obterConexao();
                var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, materia.getNome());
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_materia");
                }
            }
            
        }
        return 0;
    }
}
