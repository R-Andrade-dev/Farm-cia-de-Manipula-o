package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {
    public Connection con;

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/rth"; // ajustar com o nome correto do banco de dados
            String usuario = "root"; // ajustar com o usuário correto do banco de dados
            String senha = ""; // ajustar com a senha correta do banco de dados
            con = DriverManager.getConnection(url, usuario, senha);
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com banco: " + e.getMessage());
            return false;
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (con == null) throw new SQLException("Sem conexão com o banco de dados");
        return con.prepareStatement(sql);
    }

    public void desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
