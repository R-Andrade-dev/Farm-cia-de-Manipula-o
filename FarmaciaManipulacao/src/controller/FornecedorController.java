package controller;

import model.FornecedorModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FornecedorController {

    public boolean inserir(FornecedorModel f) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO fornecedor (razao_social, nome_fantasia, cidade, estado, pais, responsavel, tel_resp, tel_comercial, email_resp, email_comercial, desc_rua, desc_bairro, desc_num_local, cep) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, f.getRazaoSocial());
            ps.setString(2, f.getNomeFantasia());
            ps.setString(3, f.getCidade());
            ps.setString(4, f.getEstado());
            ps.setString(5, f.getPais());
            ps.setString(6, f.getResponsavel());
            ps.setString(7, f.getTelResp());
            ps.setString(8, f.getTelComercial());
            ps.setString(9, f.getEmailResp());
            ps.setString(10, f.getEmailComercial());
            ps.setString(11, f.getDescRua());
            ps.setString(12, f.getDescBairro());
            ps.setInt(13, f.getDescNumLocal());
            ps.setString(14, f.getCep());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir fornecedor: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(FornecedorModel f) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE fornecedor SET razao_social=?, nome_fantasia=?, cidade=?, estado=?, pais=?, responsavel=?, tel_resp=?, tel_comercial=?, email_resp=?, email_comercial=?, desc_rua=?, desc_bairro=?, desc_num_local=?, cep=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, f.getRazaoSocial());
            ps.setString(2, f.getNomeFantasia());
            ps.setString(3, f.getCidade());
            ps.setString(4, f.getEstado());
            ps.setString(5, f.getPais());
            ps.setString(6, f.getResponsavel());
            ps.setString(7, f.getTelResp());
            ps.setString(8, f.getTelComercial());
            ps.setString(9, f.getEmailResp());
            ps.setString(10, f.getEmailComercial());
            ps.setString(11, f.getDescRua());
            ps.setString(12, f.getDescBairro());
            ps.setInt(13, f.getDescNumLocal());
            ps.setString(14, f.getCep());
            ps.setInt(15, f.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar fornecedor: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM fornecedor WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir fornecedor: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public FornecedorModel pesquisar(int id) {
        FornecedorModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM fornecedor WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar fornecedor: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<FornecedorModel> selecionarTodos() {
        ArrayList<FornecedorModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM fornecedor ORDER BY razao_social";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    private FornecedorModel mapRow(ResultSet rs) throws SQLException {
        FornecedorModel f = new FornecedorModel();
        f.setId(rs.getInt("id"));
        f.setRazaoSocial(rs.getString("razao_social"));
        f.setNomeFantasia(rs.getString("nome_fantasia"));
        f.setCidade(rs.getString("cidade"));
        f.setEstado(rs.getString("estado"));
        f.setPais(rs.getString("pais"));
        f.setResponsavel(rs.getString("responsavel"));
        f.setTelResp(rs.getString("tel_resp"));
        f.setTelComercial(rs.getString("tel_comercial"));
        f.setEmailResp(rs.getString("email_resp"));
        f.setEmailComercial(rs.getString("email_comercial"));
        f.setDescRua(rs.getString("desc_rua"));
        f.setDescBairro(rs.getString("desc_bairro"));
        f.setDescNumLocal(rs.getInt("desc_num_local"));
        f.setCep(rs.getString("cep"));
        return f;
    }
}
