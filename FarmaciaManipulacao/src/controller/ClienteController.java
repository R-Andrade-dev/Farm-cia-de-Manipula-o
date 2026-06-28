package controller;

import model.ClienteModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteController {

    public boolean inserir(ClienteModel c) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO cliente (nome, cpf, tel1, tel2, email, desc_rua, desc_bairro, desc_num_local, cep, cidade, estado, nacionalidade) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTel1());
            ps.setString(4, c.getTel2());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getDescRua());
            ps.setString(7, c.getDescBairro());
            ps.setInt(8, c.getDescNumLocal());
            ps.setString(9, c.getCep());
            ps.setString(10, c.getCidade());
            ps.setString(11, c.getEstado());
            ps.setString(12, c.getNacionalidade());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(ClienteModel c) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE cliente SET nome=?, cpf=?, tel1=?, tel2=?, email=?, desc_rua=?, desc_bairro=?, desc_num_local=?, cep=?, cidade=?, estado=?, nacionalidade=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTel1());
            ps.setString(4, c.getTel2());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getDescRua());
            ps.setString(7, c.getDescBairro());
            ps.setInt(8, c.getDescNumLocal());
            ps.setString(9, c.getCep());
            ps.setString(10, c.getCidade());
            ps.setString(11, c.getEstado());
            ps.setString(12, c.getNacionalidade());
            ps.setInt(13, c.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM cliente WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ClienteModel pesquisar(int id) {
        ClienteModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM cliente WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                retorno = mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar cliente: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<ClienteModel> selecionarTodos() {
        ArrayList<ClienteModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM cliente ORDER BY nome";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    private ClienteModel mapRow(ResultSet rs) throws SQLException {
        ClienteModel c = new ClienteModel();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setTel1(rs.getString("tel1"));
        c.setTel2(rs.getString("tel2"));
        c.setEmail(rs.getString("email"));
        c.setDescRua(rs.getString("desc_rua"));
        c.setDescBairro(rs.getString("desc_bairro"));
        c.setDescNumLocal(rs.getInt("desc_num_local"));
        c.setCep(rs.getString("cep"));
        c.setCidade(rs.getString("cidade"));
        c.setEstado(rs.getString("estado"));
        c.setNacionalidade(rs.getString("nacionalidade"));
        return c;
    }
}
