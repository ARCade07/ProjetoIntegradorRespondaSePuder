package br.maua.respondasepuder.persistencia;

import br.maua.respondasepuder.modelo.Questao;

public class QuestaoDAO {
    public void adicionarQuestao(Questao questao) throws Exception {
        var sql = "INSERT INTO Questao(enunciado, nivel, materia) VALUES (?, ?, ?)";
        try(
            var conexao = new ConnectionFactory().obterConexao();
            var ps = conexao.prepareStatement(sql);
        ){
            ps.setString(1, questao.getEnunciado());
            ps.setString(2, questao.getNivel());
            ps.setString(3, questao.getMateria());
            ps.execute();
        }
    }

}
