package view;

import controller.InsumoMaterialController;
import model.InsumoMaterialModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class InsumoMaterialView extends JInternalFrame {

    private JTextField txId, txDescricao, txTipo;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = 0;

    public InsumoMaterialView() {
        super("Cadastro de Insumos / Materiais", true, true, true, true);
        setSize(700, 480);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        estadoInicial();
        carregarTabela();
    }

    private void initComponents() {
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(new TitledBorder("Dados do Insumo"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6);
        txDescricao = new JTextField(30);
        txTipo = new JTextField(20);

        // Row 0: ID + Pesquisar
        g.gridx = 0; g.gridy = 0; g.gridwidth = 1; g.weightx = 0;
        painelCampos.add(rl("ID:"), g);
        g.gridx = 1; g.weightx = 0.3;
        painelCampos.add(txId, g);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.weightx = 0;
        painelCampos.add(btnPesquisar, g);

        // Row 1: Descrição
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        painelCampos.add(rl("Descrição:"), g);
        g.gridx = 1; g.gridwidth = 3; g.weightx = 1;
        painelCampos.add(txDescricao, g);

        // Row 2: Tipo
        g.gridx = 0; g.gridy = 2; g.gridwidth = 1; g.weightx = 0;
        painelCampos.add(rl("Tipo:"), g);
        g.gridx = 1; g.gridwidth = 2; g.weightx = 1;
        painelCampos.add(txTipo, g);

        // Hint label for Tipo
        g.gridx = 3; g.gridwidth = 1; g.weightx = 0;
        painelCampos.add(rl("(ex: Materia-Prima, Excipiente, Embalagem)"), g);

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

        String[] colunas = {"ID", "Descrição", "Tipo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new TitledBorder("Lista de Insumos"));

        add(painelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private JLabel rl(String text) {
        JLabel l = new JLabel(text);
        l.setHorizontalAlignment(SwingConstants.RIGHT);
        return l;
    }

    private void pesquisar() {
        try {
            int id = Integer.parseInt(txId.getText().trim());
            InsumoMaterialModel im = new InsumoMaterialController().pesquisar(id);
            if (im == null) JOptionPane.showMessageDialog(this, "Insumo não encontrado!");
            else { preencherCampos(im); estadoSelecao(); }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "ID inválido!"); }
    }

    private void novo() { limparCampos(); estadoNovo(); }

    private void salvar() {
        if (txDescricao.getText().trim().isEmpty() || txTipo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descrição e Tipo são obrigatórios!"); return;
        }
        InsumoMaterialModel im = new InsumoMaterialModel();
        im.setDescricao(txDescricao.getText().trim());
        im.setTipo(txTipo.getText().trim());
        if (new InsumoMaterialController().inserir(im)) {
            JOptionPane.showMessageDialog(this, "Insumo cadastrado com sucesso!");
            limparCampos(); carregarTabela(); estadoInicial();
        } else JOptionPane.showMessageDialog(this, "Erro ao cadastrar!");
    }

    private void editar() {
        if (txDescricao.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Descrição obrigatória!"); return; }
        InsumoMaterialModel im = new InsumoMaterialModel();
        im.setId(idSelecionado);
        im.setDescricao(txDescricao.getText().trim());
        im.setTipo(txTipo.getText().trim());
        if (new InsumoMaterialController().editar(im)) {
            JOptionPane.showMessageDialog(this, "Insumo atualizado!");
            limparCampos(); carregarTabela(); estadoInicial();
        } else JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (new InsumoMaterialController().excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Insumo excluído!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao excluir! Verifique vínculos.");
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloTabela.getValueAt(linha, 0)));
            InsumoMaterialModel im = new InsumoMaterialController().pesquisar(idSelecionado);
            if (im != null) { preencherCampos(im); estadoSelecao(); }
        }
    }

    private void preencherCampos(InsumoMaterialModel im) {
        idSelecionado = im.getId();
        txId.setText(String.valueOf(im.getId()));
        txDescricao.setText(im.getDescricao());
        txTipo.setText(im.getTipo());
    }

    private void limparCampos() {
        txId.setText(""); txDescricao.setText(""); txTipo.setText(""); idSelecionado = 0;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (InsumoMaterialModel im : new InsumoMaterialController().selecionarTodos()) {
            modeloTabela.addRow(new Object[]{im.getId(), im.getDescricao(), im.getTipo()});
        }
    }

    private void estadoInicial() {
        txId.setEditable(true); txDescricao.setEditable(false); txTipo.setEditable(false);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
    }

    private void estadoNovo() {
        txId.setEditable(false); txDescricao.setEditable(true); txTipo.setEditable(true);
        btnNovo.setEnabled(false); btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
    }

    private void estadoSelecao() {
        txId.setEditable(false); txDescricao.setEditable(true); txTipo.setEditable(true);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true); btnExcluir.setEnabled(true);
    }
}
