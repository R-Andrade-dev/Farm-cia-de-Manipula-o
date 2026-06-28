package view;

import controller.FornecedorController;
import controller.InsumoMaterialController;
import controller.LoteInsumoController;
import model.FornecedorModel;
import model.InsumoMaterialModel;
import model.LoteInsumoModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class LoteInsumoView extends JInternalFrame {

    private JTextField txId, txFabricacao, txValidade, txQuantidade;
    private JComboBox<InsumoMaterialModel> cbInsumo;
    private JComboBox<FornecedorModel> cbFornecedor;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = 0;

    private ArrayList<InsumoMaterialModel> insumos = new ArrayList<>();
    private ArrayList<FornecedorModel> fornecedores = new ArrayList<>();

    public LoteInsumoView() {
        super("Lotes de Insumos", true, true, true, true);
        setSize(800, 530);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        carregarCombos();
        estadoInicial();
        carregarTabela();
    }

    private void initComponents() {
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(new TitledBorder("Dados do Lote"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6);
        cbInsumo = new JComboBox<>();
        cbFornecedor = new JComboBox<>();
        txFabricacao = new JTextField(12);
        txValidade = new JTextField(12);
        txQuantidade = new JTextField(10);

        // Row 0: ID + Pesquisar
        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        painelCampos.add(rl("ID Lote:"), g);
        g.gridx = 1; g.weightx = 0.3;
        painelCampos.add(txId, g);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.weightx = 0;
        painelCampos.add(btnPesquisar, g);

        // Row 1: Insumo
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        painelCampos.add(rl("Insumo:"), g);
        g.gridx = 1; g.gridwidth = 3; g.weightx = 1;
        painelCampos.add(cbInsumo, g);

        // Row 2: Fornecedor
        g.gridx = 0; g.gridy = 2; g.gridwidth = 1; g.weightx = 0;
        painelCampos.add(rl("Fornecedor:"), g);
        g.gridx = 1; g.gridwidth = 3; g.weightx = 1;
        painelCampos.add(cbFornecedor, g);

        // Row 3: Fabricação | Validade
        g.gridx = 0; g.gridy = 3; g.gridwidth = 1; g.weightx = 0;
        painelCampos.add(rl("Dt. Fabricação (aaaa-mm-dd):"), g);
        g.gridx = 1; g.weightx = 0.5;
        painelCampos.add(txFabricacao, g);
        g.gridx = 2; g.weightx = 0;
        painelCampos.add(rl("Dt. Validade (aaaa-mm-dd):"), g);
        g.gridx = 3; g.weightx = 0.5;
        painelCampos.add(txValidade, g);

        // Row 4: Quantidade
        g.gridx = 0; g.gridy = 4; g.weightx = 0;
        painelCampos.add(rl("Quantidade:"), g);
        g.gridx = 1; g.gridwidth = 1; g.weightx = 0.3;
        painelCampos.add(txQuantidade, g);

        // --- Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        btnNovo = new JButton("Novo"); btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar"); btnExcluir = new JButton("Excluir"); btnFechar = new JButton("Fechar");

        btnNovo.setBackground(new Color(46, 139, 87)); btnNovo.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(30, 100, 200)); btnSalvar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(210, 140, 20)); btnEditar.setForeground(Color.WHITE);
        btnExcluir.setBackground(new Color(180, 30, 30)); btnExcluir.setForeground(Color.WHITE);

        btnNovo.addActionListener(e -> novo()); btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar()); btnExcluir.addActionListener(e -> excluir());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnNovo); painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar); painelBotoes.add(btnExcluir); painelBotoes.add(btnFechar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelCampos, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        String[] colunas = {"ID", "Insumo", "Fornecedor", "Dt. Fabricação", "Dt. Validade", "Quantidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new TitledBorder("Lotes Cadastrados"));

        add(painelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void carregarCombos() {
        insumos = new InsumoMaterialController().selecionarTodos();
        cbInsumo.removeAllItems();
        for (InsumoMaterialModel im : insumos) cbInsumo.addItem(im);

        fornecedores = new FornecedorController().selecionarTodos();
        cbFornecedor.removeAllItems();
        for (FornecedorModel f : fornecedores) cbFornecedor.addItem(f);
    }

    private JLabel rl(String text) {
        JLabel l = new JLabel(text);
        l.setHorizontalAlignment(SwingConstants.RIGHT);
        return l;
    }

    private void pesquisar() {
        try {
            int id = Integer.parseInt(txId.getText().trim());
            LoteInsumoModel l = new LoteInsumoController().pesquisar(id);
            if (l == null) JOptionPane.showMessageDialog(this, "Lote não encontrado!");
            else { preencherCampos(l); estadoSelecao(); }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "ID inválido!"); }
    }

    private void novo() { limparCampos(); estadoNovo(); }

    private void salvar() {
        if (txFabricacao.getText().trim().isEmpty() || txValidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Datas são obrigatórias!"); return;
        }
        try {
            LoteInsumoModel l = coletarCampos();
            if (new LoteInsumoController().inserir(l)) {
                JOptionPane.showMessageDialog(this, "Lote cadastrado com sucesso!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao cadastrar! Verifique se validade > fabricação e quantidade > 0.");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantidade deve ser numérica!"); }
    }

    private void editar() {
        try {
            LoteInsumoModel l = coletarCampos();
            l.setId(idSelecionado);
            if (new LoteInsumoController().editar(l)) {
                JOptionPane.showMessageDialog(this, "Lote atualizado!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantidade deve ser numérica!"); }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (new LoteInsumoController().excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Lote excluído!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao excluir! Verifique vínculos.");
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloTabela.getValueAt(linha, 0)));
            LoteInsumoModel l = new LoteInsumoController().pesquisar(idSelecionado);
            if (l != null) { preencherCampos(l); estadoSelecao(); }
        }
    }

    private LoteInsumoModel coletarCampos() {
        LoteInsumoModel l = new LoteInsumoModel();
        InsumoMaterialModel im = (InsumoMaterialModel) cbInsumo.getSelectedItem();
        FornecedorModel f = (FornecedorModel) cbFornecedor.getSelectedItem();
        if (im != null) l.setIdInsumo(im.getId());
        if (f != null) l.setIdFornecedor(f.getId());
        l.setDtFabricacao(txFabricacao.getText().trim());
        l.setDtValidade(txValidade.getText().trim());
        l.setQuantidade(Integer.parseInt(txQuantidade.getText().trim()));
        return l;
    }

    private void preencherCampos(LoteInsumoModel l) {
        idSelecionado = l.getId();
        txId.setText(String.valueOf(l.getId()));
        for (int i = 0; i < insumos.size(); i++) {
            if (insumos.get(i).getId() == l.getIdInsumo()) { cbInsumo.setSelectedIndex(i); break; }
        }
        for (int i = 0; i < fornecedores.size(); i++) {
            if (fornecedores.get(i).getId() == l.getIdFornecedor()) { cbFornecedor.setSelectedIndex(i); break; }
        }
        txFabricacao.setText(l.getDtFabricacao());
        txValidade.setText(l.getDtValidade());
        txQuantidade.setText(String.valueOf(l.getQuantidade()));
    }

    private void limparCampos() {
        txId.setText(""); txFabricacao.setText(""); txValidade.setText(""); txQuantidade.setText("");
        if (cbInsumo.getItemCount() > 0) cbInsumo.setSelectedIndex(0);
        if (cbFornecedor.getItemCount() > 0) cbFornecedor.setSelectedIndex(0);
        idSelecionado = 0;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (LoteInsumoModel l : new LoteInsumoController().selecionarTodos()) {
            modeloTabela.addRow(new Object[]{l.getId(), l.getNomeInsumo(), l.getNomeFornecedor(),
                l.getDtFabricacao(), l.getDtValidade(), l.getQuantidade()});
        }
    }

    private void estadoInicial() {
        txId.setEditable(true); setFieldsEnabled(false);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
    }

    private void estadoNovo() {
        txId.setEditable(false); setFieldsEnabled(true);
        btnNovo.setEnabled(false); btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
    }

    private void estadoSelecao() {
        txId.setEditable(false); setFieldsEnabled(true);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true); btnExcluir.setEnabled(true);
    }

    private void setFieldsEnabled(boolean v) {
        cbInsumo.setEnabled(v); cbFornecedor.setEnabled(v);
        txFabricacao.setEditable(v); txValidade.setEditable(v); txQuantidade.setEditable(v);
    }
}
