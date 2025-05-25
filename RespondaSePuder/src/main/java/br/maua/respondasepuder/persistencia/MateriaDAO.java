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
                var codigo = rs.getInt("id_materia");
                var nome = rs.getString("nome");
                var materia = new Materia(codigo, nome);
                materias.add(materia);
            }
        }
        return materias;
    }
}
