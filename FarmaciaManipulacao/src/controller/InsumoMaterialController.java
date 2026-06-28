package controller;

import model.InsumoMaterialModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsumoMaterialController {

    public boolean inserir(InsumoMaterialModel im) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO insumo_material (descricao, tipo) VALUES (?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, im.getDescricao());
            ps.setString(2, im.getTipo());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir insumo: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(InsumoMaterialModel im) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE insumo_material SET descricao=?, tipo=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, im.getDescricao());
            ps.setString(2, im.getTipo());
            ps.setInt(3, im.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar insumo: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM insumo_material WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir insumo: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public InsumoMaterialModel pesquisar(int id) {
        InsumoMaterialModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM insumo_material WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar insumo: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<InsumoMaterialModel> selecionarTodos() {
        ArrayList<InsumoMaterialModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM insumo_material ORDER BY descricao";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar insumos: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    private InsumoMaterialModel mapRow(ResultSet rs) throws SQLException {
        InsumoMaterialModel im = new InsumoMaterialModel();
        im.setId(rs.getInt("id"));
        im.setDescricao(rs.getString("descricao"));
        im.setTipo(rs.getString("tipo"));
        return im;
    }
}
