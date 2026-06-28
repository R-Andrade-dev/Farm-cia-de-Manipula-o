package controller;

import model.FormulaManipuladaModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormulaManipuladaController {

    public boolean inserir(FormulaManipuladaModel fm) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO formula_manipulada (id_profissional, nome, posologia, observacao, preco, f_status) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, fm.getIdProfissional());
            ps.setString(2, fm.getNome());
            ps.setString(3, fm.getPosologia());
            ps.setString(4, fm.getObservacao());
            ps.setFloat(5, fm.getPreco());
            ps.setString(6, fm.getfStatus());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir fórmula: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(FormulaManipuladaModel fm) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE formula_manipulada SET id_profissional=?, nome=?, posologia=?, observacao=?, preco=?, f_status=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, fm.getIdProfissional());
            ps.setString(2, fm.getNome());
            ps.setString(3, fm.getPosologia());
            ps.setString(4, fm.getObservacao());
            ps.setFloat(5, fm.getPreco());
            ps.setString(6, fm.getfStatus());
            ps.setInt(7, fm.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar fórmula: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM formula_manipulada WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir fórmula: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public FormulaManipuladaModel pesquisar(int id) {
        FormulaManipuladaModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT fm.*, p.nome AS nome_profissional FROM formula_manipulada fm JOIN profissional p ON p.id=fm.id_profissional WHERE fm.id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar fórmula: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<FormulaManipuladaModel> selecionarTodos() {
        ArrayList<FormulaManipuladaModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT fm.*, p.nome AS nome_profissional FROM formula_manipulada fm JOIN profissional p ON p.id=fm.id_profissional ORDER BY fm.nome";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar fórmulas: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    public int ultimaFormula() {
        int retorno = 0;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT MAX(id) FROM formula_manipulada";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao obter última fórmula: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    private FormulaManipuladaModel mapRow(ResultSet rs) throws SQLException {
        FormulaManipuladaModel fm = new FormulaManipuladaModel();
        fm.setId(rs.getInt("id"));
        fm.setIdProfissional(rs.getInt("id_profissional"));
        fm.setNome(rs.getString("nome"));
        fm.setPosologia(rs.getString("posologia"));
        fm.setObservacao(rs.getString("observacao"));
        fm.setPreco(rs.getFloat("preco"));
        fm.setfStatus(rs.getString("f_status"));
        try { fm.setNomeProfissional(rs.getString("nome_profissional")); } catch (SQLException ex) {}
        return fm;
    }
}
