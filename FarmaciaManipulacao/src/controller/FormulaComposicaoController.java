package controller;

import model.FormulaComposicaoModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormulaComposicaoController {

    public boolean inserir(FormulaComposicaoModel fc) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO formula_composicao (id_formula, id_insumo, id_lote_insumo, dosagem) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, fc.getIdFormula());
            ps.setInt(2, fc.getIdInsumo());
            ps.setInt(3, fc.getIdLoteInsumo());
            ps.setString(4, fc.getDosagem());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir composição: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int idFormula, int idInsumo) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM formula_composicao WHERE id_formula=? AND id_insumo=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idFormula);
            ps.setInt(2, idInsumo);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir composição: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluirPorFormula(int idFormula) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM formula_composicao WHERE id_formula=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idFormula);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir composições: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<FormulaComposicaoModel> selecionarPorFormula(int idFormula) {
        ArrayList<FormulaComposicaoModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT fc.*, im.descricao AS nome_insumo, im.tipo AS tipo_insumo FROM formula_composicao fc JOIN insumo_material im ON im.id=fc.id_insumo WHERE fc.id_formula=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idFormula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FormulaComposicaoModel fc = new FormulaComposicaoModel();
                fc.setIdFormula(rs.getInt("id_formula"));
                fc.setIdInsumo(rs.getInt("id_insumo"));
                fc.setIdLoteInsumo(rs.getInt("id_lote_insumo"));
                fc.setDosagem(rs.getString("dosagem"));
                fc.setNomeInsumo(rs.getString("nome_insumo"));
                fc.setTipoInsumo(rs.getString("tipo_insumo"));
                lista.add(fc);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar composições: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }
}
