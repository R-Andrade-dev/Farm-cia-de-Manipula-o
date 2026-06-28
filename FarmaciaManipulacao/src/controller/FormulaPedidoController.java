package controller;

import model.FormulaPedidoModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormulaPedidoController {

    public boolean inserir(FormulaPedidoModel fp) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO formula_pedido (id_pedido, id_formula, quantidade, preco) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, fp.getIdPedido());
            ps.setInt(2, fp.getIdFormula());
            ps.setInt(3, fp.getQuantidade());
            ps.setFloat(4, fp.getPreco());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item de pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluirPorPedido(int idPedido) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM formula_pedido WHERE id_pedido=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPedido);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir itens do pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<FormulaPedidoModel> selecionarPorPedido(int idPedido) {
        ArrayList<FormulaPedidoModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT fp.*, fm.nome AS nome_formula FROM formula_pedido fp JOIN formula_manipulada fm ON fm.id=fp.id_formula WHERE fp.id_pedido=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FormulaPedidoModel fp = new FormulaPedidoModel();
                fp.setIdPedido(rs.getInt("id_pedido"));
                fp.setIdFormula(rs.getInt("id_formula"));
                fp.setQuantidade(rs.getInt("quantidade"));
                fp.setPreco(rs.getFloat("preco"));
                fp.setNomeFormula(rs.getString("nome_formula"));
                lista.add(fp);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar itens do pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }
}
