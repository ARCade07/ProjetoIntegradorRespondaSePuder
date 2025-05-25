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
                        .nome(rs.getString("nome"))
                        .build();
                materias.add(materia);
            }
        }
        return materias;
    }
}
