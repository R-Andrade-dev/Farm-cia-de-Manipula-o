package controller;

import model.ProfissionalModel;
import util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfissionalController {

    public boolean inserir(ProfissionalModel p) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "INSERT INTO profissional (nome, cpf, tel, email, tel_emergencia, nome_emergencia, desc_rua, desc_bairro, desc_num_local, cep, cidade, estado, nacionalidade, farmaceutico, crf, cargo, salario, hora_inicio, hora_fim) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getTel());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getTelEmergencia());
            ps.setString(6, p.getNomeEmergencia());
            ps.setString(7, p.getDescRua());
            ps.setString(8, p.getDescBairro());
            ps.setInt(9, p.getDescNumLocal());
            ps.setString(10, p.getCep());
            ps.setString(11, p.getCidade());
            ps.setString(12, p.getEstado());
            ps.setString(13, p.getNacionalidade());
            ps.setBoolean(14, p.isFarmaceutico());
            ps.setString(15, p.getCrf());
            ps.setString(16, p.getCargo());
            ps.setFloat(17, p.getSalario());
            ps.setString(18, p.getHoraInicio());
            ps.setString(19, p.getHoraFim());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir profissional: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean editar(ProfissionalModel p) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "UPDATE profissional SET nome=?, cpf=?, tel=?, email=?, tel_emergencia=?, nome_emergencia=?, desc_rua=?, desc_bairro=?, desc_num_local=?, cep=?, cidade=?, estado=?, nacionalidade=?, farmaceutico=?, crf=?, cargo=?, salario=?, hora_inicio=?, hora_fim=? WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getTel());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getTelEmergencia());
            ps.setString(6, p.getNomeEmergencia());
            ps.setString(7, p.getDescRua());
            ps.setString(8, p.getDescBairro());
            ps.setInt(9, p.getDescNumLocal());
            ps.setString(10, p.getCep());
            ps.setString(11, p.getCidade());
            ps.setString(12, p.getEstado());
            ps.setString(13, p.getNacionalidade());
            ps.setBoolean(14, p.isFarmaceutico());
            ps.setString(15, p.getCrf());
            ps.setString(16, p.getCargo());
            ps.setFloat(17, p.getSalario());
            ps.setString(18, p.getHoraInicio());
            ps.setString(19, p.getHoraFim());
            ps.setInt(20, p.getId());
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao editar profissional: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public boolean excluir(int id) {
        boolean retorno = false;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "DELETE FROM profissional WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            if (!ps.execute()) retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir profissional: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ProfissionalModel pesquisar(int id) {
        ProfissionalModel retorno = null;
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM profissional WHERE id=?";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) retorno = mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar profissional: " + e.getMessage());
        }
        conexao.desconectar();
        return retorno;
    }

    public ArrayList<ProfissionalModel> selecionarTodos() {
        ArrayList<ProfissionalModel> lista = new ArrayList<>();
        Conexao conexao = new Conexao();
        conexao.conectar();
        String sql = "SELECT * FROM profissional ORDER BY nome";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar profissionais: " + e.getMessage());
        }
        conexao.desconectar();
        return lista;
    }

    private ProfissionalModel mapRow(ResultSet rs) throws SQLException {
        ProfissionalModel p = new ProfissionalModel();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setCpf(rs.getString("cpf"));
        p.setTel(rs.getString("tel"));
        p.setEmail(rs.getString("email"));
        p.setTelEmergencia(rs.getString("tel_emergencia"));
        p.setNomeEmergencia(rs.getString("nome_emergencia"));
        p.setDescRua(rs.getString("desc_rua"));
        p.setDescBairro(rs.getString("desc_bairro"));
        p.setDescNumLocal(rs.getInt("desc_num_local"));
        p.setCep(rs.getString("cep"));
        p.setCidade(rs.getString("cidade"));
        p.setEstado(rs.getString("estado"));
        p.setNacionalidade(rs.getString("nacionalidade"));
        p.setFarmaceutico(rs.getBoolean("farmaceutico"));
        p.setCrf(rs.getString("crf"));
        p.setCargo(rs.getString("cargo"));
        p.setSalario(rs.getFloat("salario"));
        p.setHoraInicio(rs.getString("hora_inicio"));
        p.setHoraFim(rs.getString("hora_fim"));
        return p;
    }
}
