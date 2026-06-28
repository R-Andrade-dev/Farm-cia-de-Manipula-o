package controller;

import model.LoteInsumoModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoteInsumoController {

    public boolean inserir(LoteInsumoModel l) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO lote_insumo (id_insumo, id_fornecedor, dt_fabricacao, dt_validade, quantidade) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, l.getIdInsumo());
            ps.setInt(2, l.getIdFornecedor());
            ps.setString(3, l.getDtFabricacao());
            ps.setString(4, l.getDtValidade());
            ps.setInt(5, l.getQuantidade());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir lote: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(LoteInsumoModel l) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE lote_insumo SET id_insumo=?, id_fornecedor=?, dt_fabricacao=?, dt_validade=?, quantidade=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, l.getIdInsumo());
            ps.setInt(2, l.getIdFornecedor());
            ps.setString(3, l.getDtFabricacao());
            ps.setString(4, l.getDtValidade());
            ps.setInt(5, l.getQuantidade());
            ps.setInt(6, l.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar lote: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM lote_insumo WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir lote: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public LoteInsumoModel pesquisar(int id) {
        LoteInsumoModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT li.*, im.descricao AS nome_insumo, f.razao_social AS nome_fornecedor FROM lote_insumo li JOIN insumo_material im ON im.id=li.id_insumo JOIN fornecedor f ON f.id=li.id_fornecedor WHERE li.id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar lote: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<LoteInsumoModel> selecionarTodos() {
        ArrayList<LoteInsumoModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT li.*, im.descricao AS nome_insumo, f.razao_social AS nome_fornecedor FROM lote_insumo li JOIN insumo_material im ON im.id=li.id_insumo JOIN fornecedor f ON f.id=li.id_fornecedor ORDER BY li.id";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar lotes: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    private LoteInsumoModel mapRow(ResultSet rs) throws SQLException {
        LoteInsumoModel l = new LoteInsumoModel();
        l.setId(rs.getInt("id"));
        l.setIdInsumo(rs.getInt("id_insumo"));
        l.setIdFornecedor(rs.getInt("id_fornecedor"));
        l.setDtFabricacao(rs.getString("dt_fabricacao"));
        l.setDtValidade(rs.getString("dt_validade"));
        l.setQuantidade(rs.getInt("quantidade"));
        try { l.setNomeInsumo(rs.getString("nome_insumo")); } catch (SQLException ex) {}
        try { l.setNomeFornecedor(rs.getString("nome_fornecedor")); } catch (SQLException ex) {}
        return l;
    }
}
