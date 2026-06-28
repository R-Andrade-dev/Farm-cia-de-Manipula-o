package view;

import util.Conexao;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class RelatorioView extends JInternalFrame {

    private final String tipo;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public RelatorioView(String tipo) {
        super(getTitulo(tipo), true, true, true, true);
        this.tipo = tipo;
        setSize(900, 500);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        carregarRelatorio();
    }

    private static String getTitulo(String tipo) {
        switch (tipo) {
            case "pedidos_completos": return "Relatório - Pedidos Completos";
            case "lotes_vencimento": return "Relatório - Lotes a Vencer (60 dias)";
            case "formula_composicao": return "Relatório - Composição de Fórmulas";
            case "pedidos_cidade": return "Relatório - Pedidos por Cidade (> R$ 50)";
            default: return "Relatório";
        }
    }

    private void initComponents() {
        modeloTabela = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new TitledBorder(getTitulo(tipo)));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnFechar = new JButton("Fechar");
        btnAtualizar.addActionListener(e -> carregarRelatorio());
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarRelatorio() {
        String sql = getSql();
        if (sql == null) return;

        Conexao conexao = new Conexao();
        conexao.conectar();
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int colunas = meta.getColumnCount();

            // Build column names
            String[] nomes = new String[colunas];
            for (int i = 1; i <= colunas; i++) nomes[i - 1] = meta.getColumnLabel(i);

            modeloTabela.setColumnIdentifiers(nomes);
            modeloTabela.setRowCount(0);

            while (rs.next()) {
                Object[] row = new Object[colunas];
                for (int i = 1; i <= colunas; i++) row[i - 1] = rs.getObject(i);
                modeloTabela.addRow(row);
            }
            ajustarLarguraColunas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatório: " + e.getMessage());
        }
        conexao.desconectar();
    }

    private void ajustarLarguraColunas() {
        TableColumnModel cm = tabela.getColumnModel();
        for (int col = 0; col < tabela.getColumnCount(); col++) {
            int largura = 60;
            // mede o cabeçalho
            java.awt.Component header = tabela.getTableHeader()
                .getDefaultRenderer()
                .getTableCellRendererComponent(tabela, cm.getColumn(col).getHeaderValue(), false, false, -1, col);
            largura = Math.max(largura, header.getPreferredSize().width + 10);
            // mede cada célula
            for (int row = 0; row < tabela.getRowCount(); row++) {
                java.awt.Component cell = tabela.getDefaultRenderer(tabela.getColumnClass(col))
                    .getTableCellRendererComponent(tabela, tabela.getValueAt(row, col), false, false, row, col);
                largura = Math.max(largura, cell.getPreferredSize().width + 10);
            }
            cm.getColumn(col).setPreferredWidth(Math.min(largura, 200));
        }
    }

    private String getSql() {
        switch (tipo) {
            case "pedidos_completos":
                return "SELECT * FROM vw_pedidos_completos";
            case "lotes_vencimento":
                return "SELECT * FROM vw_lotes_vencimento";
            case "formula_composicao":
                return "SELECT * FROM vw_formula_composicao";
            case "pedidos_cidade":
                return "SELECT * FROM vw_pedidos_cidade";
            default:
                return null;
        }
    }
}
