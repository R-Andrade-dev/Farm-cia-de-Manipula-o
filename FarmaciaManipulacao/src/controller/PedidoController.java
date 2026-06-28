package controller;

import model.PedidoModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PedidoController {

    public boolean inserir(PedidoModel p) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO pedido (id_cliente, id_profissional, medico_solicitante, crm_solicitante, valor_total, tipo_pagamento, observacao, status) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, p.getIdCliente());
            ps.setInt(2, p.getIdProfissional());
            ps.setString(3, p.getMedicoSolicitante());
            ps.setString(4, p.getCrmSolicitante());
            ps.setFloat(5, p.getValorTotal());
            ps.setString(6, p.getTipoPagamento());
            ps.setString(7, p.getObservacao());
            ps.setString(8, p.getStatus());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(PedidoModel p) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE pedido SET id_cliente=?, id_profissional=?, medico_solicitante=?, crm_solicitante=?, valor_total=?, tipo_pagamento=?, observacao=?, status=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, p.getIdCliente());
            ps.setInt(2, p.getIdProfissional());
            ps.setString(3, p.getMedicoSolicitante());
            ps.setString(4, p.getCrmSolicitante());
            ps.setFloat(5, p.getValorTotal());
            ps.setString(6, p.getTipoPagamento());
            ps.setString(7, p.getObservacao());
            ps.setString(8, p.getStatus());
            ps.setInt(9, p.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM pedido WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public PedidoModel pesquisar(int id) {
        PedidoModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT p.*, c.nome AS nome_cliente, pr.nome AS nome_profissional FROM pedido p JOIN cliente c ON c.id=p.id_cliente JOIN profissional pr ON pr.id=p.id_profissional WHERE p.id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<PedidoModel> selecionarTodos() {
        ArrayList<PedidoModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT p.*, c.nome AS nome_cliente, pr.nome AS nome_profissional FROM pedido p JOIN cliente c ON c.id=p.id_cliente JOIN profissional pr ON pr.id=p.id_profissional ORDER BY p.data_hora DESC";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    public int ultimoPedido() {
        int retorno = 0;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT MAX(id) FROM pedido";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao obter último pedido: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    private PedidoModel mapRow(ResultSet rs) throws SQLException {
        PedidoModel p = new PedidoModel();
        p.setId(rs.getInt("id"));
        p.setIdCliente(rs.getInt("id_cliente"));
        p.setIdProfissional(rs.getInt("id_profissional"));
        p.setDataHora(rs.getString("data_hora"));
        p.setMedicoSolicitante(rs.getString("medico_solicitante"));
        p.setCrmSolicitante(rs.getString("crm_solicitante"));
        p.setValorTotal(rs.getFloat("valor_total"));
        p.setTipoPagamento(rs.getString("tipo_pagamento"));
        p.setObservacao(rs.getString("observacao"));
        p.setStatus(rs.getString("status"));
        try { p.setNomeCliente(rs.getString("nome_cliente")); } catch (SQLException ex) {}
        try { p.setNomeProfissional(rs.getString("nome_profissional")); } catch (SQLException ex) {}
        return p;
    }
}
