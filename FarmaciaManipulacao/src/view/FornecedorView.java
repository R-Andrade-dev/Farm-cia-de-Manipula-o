package view;

import controller.FornecedorController;
import model.FornecedorModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class FornecedorView extends JInternalFrame {

    private JTextField txId, txRazaoSocial, txNomeFantasia, txCidade, txEstado, txPais;
    private JTextField txResponsavel, txTelResp, txTelCom, txEmailResp, txEmailCom;
    private JTextField txRua, txBairro, txNum, txCep;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = 0;

    public FornecedorView() {
        super("Cadastro de Fornecedores", true, true, true, true);
        setSize(900, 620);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        estadoInicial();
        carregarTabela();
    }

    private void initComponents() {
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(new TitledBorder("Dados do Fornecedor"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 5, 3, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6); txRazaoSocial = new JTextField(25);
        txNomeFantasia = new JTextField(20); txCidade = new JTextField(15);
        txEstado = new JTextField(8); txPais = new JTextField(15);
        txResponsavel = new JTextField(20); txTelResp = new JTextField(13);
        txTelCom = new JTextField(13); txEmailResp = new JTextField(20);
        txEmailCom = new JTextField(20); txRua = new JTextField(20);
        txBairro = new JTextField(15); txNum = new JTextField(6); txCep = new JTextField(10);

        // Row 0: ID + Pesquisar
        addField(painelCampos, g, "ID:", txId, 0, 0, 1);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.gridy = 0; g.gridwidth = 1; painelCampos.add(btnPesquisar, g);

        addField(painelCampos, g, "Razão Social:", txRazaoSocial, 0, 1, 3);
        addField(painelCampos, g, "Nome Fantasia:", txNomeFantasia, 0, 2, 1);
        addField(painelCampos, g, "Cidade:", txCidade, 2, 2, 1);
        addField(painelCampos, g, "Estado:", txEstado, 4, 2, 1);
        addField(painelCampos, g, "País:", txPais, 0, 3, 1);
        addField(painelCampos, g, "Responsável:", txResponsavel, 2, 3, 3);
        addField(painelCampos, g, "Tel Resp.:", txTelResp, 0, 4, 1);
        addField(painelCampos, g, "Tel Comercial:", txTelCom, 2, 4, 1);
        addField(painelCampos, g, "Email Resp.:", txEmailResp, 0, 5, 1);
        addField(painelCampos, g, "Email Comercial:", txEmailCom, 2, 5, 3);
        addField(painelCampos, g, "Rua:", txRua, 0, 6, 3);
        addField(painelCampos, g, "Nº:", txNum, 4, 6, 1);
        addField(painelCampos, g, "Bairro:", txBairro, 0, 7, 1);
        addField(painelCampos, g, "CEP:", txCep, 2, 7, 1);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
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

        String[] colunas = {"ID", "Razão Social", "Nome Fantasia", "Cidade", "Estado", "Responsável", "Tel Resp."};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new TitledBorder("Lista de Fornecedores"));

        add(painelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void addField(JPanel p, GridBagConstraints g, String label, JTextField field, int col, int row, int width) {
        JLabel lbl = new JLabel(label);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        g.gridx = col; g.gridy = row; g.gridwidth = 1; g.weightx = 0;
        p.add(lbl, g);
        g.gridx = col + 1; g.gridwidth = width; g.weightx = 1;
        p.add(field, g);
    }

    private void pesquisar() {
        try {
            int id = Integer.parseInt(txId.getText().trim());
            FornecedorModel f = new FornecedorController().pesquisar(id);
            if (f == null) JOptionPane.showMessageDialog(this, "Fornecedor não encontrado!");
            else { preencherCampos(f); estadoSelecao(); }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Informe um ID válido!"); }
    }

    private void novo() { limparCampos(); estadoNovo(); }

    private void salvar() {
        if (txRazaoSocial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Razão Social é obrigatória!"); return;
        }
        try {
            FornecedorModel f = coletarCampos();
            if (new FornecedorController().inserir(f)) {
                JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao cadastrar!");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Número deve ser numérico!"); }
    }

    private void editar() {
        if (txRazaoSocial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Razão Social é obrigatória!"); return;
        }
        try {
            FornecedorModel f = coletarCampos();
            f.setId(idSelecionado);
            if (new FornecedorController().editar(f)) {
                JOptionPane.showMessageDialog(this, "Fornecedor atualizado!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Número deve ser numérico!"); }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (new FornecedorController().excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Fornecedor excluído!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao excluir! Verifique vínculos.");
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloTabela.getValueAt(linha, 0)));
            FornecedorModel f = new FornecedorController().pesquisar(idSelecionado);
            if (f != null) { preencherCampos(f); estadoSelecao(); }
        }
    }

    private FornecedorModel coletarCampos() {
        FornecedorModel f = new FornecedorModel();
        f.setRazaoSocial(txRazaoSocial.getText().trim());
        f.setNomeFantasia(txNomeFantasia.getText().trim());
        f.setCidade(txCidade.getText().trim());
        f.setEstado(txEstado.getText().trim());
        f.setPais(txPais.getText().trim());
        f.setResponsavel(txResponsavel.getText().trim());
        f.setTelResp(txTelResp.getText().trim());
        f.setTelComercial(txTelCom.getText().trim());
        f.setEmailResp(txEmailResp.getText().trim());
        f.setEmailComercial(txEmailCom.getText().trim());
        f.setDescRua(txRua.getText().trim());
        f.setDescBairro(txBairro.getText().trim());
        f.setDescNumLocal(txNum.getText().trim().isEmpty() ? 0 : Integer.parseInt(txNum.getText().trim()));
        f.setCep(txCep.getText().trim().isEmpty() ? null : txCep.getText().trim());
        return f;
    }

    private void preencherCampos(FornecedorModel f) {
        idSelecionado = f.getId();
        txId.setText(String.valueOf(f.getId()));
        txRazaoSocial.setText(f.getRazaoSocial()); txNomeFantasia.setText(f.getNomeFantasia());
        txCidade.setText(f.getCidade()); txEstado.setText(f.getEstado()); txPais.setText(f.getPais());
        txResponsavel.setText(f.getResponsavel()); txTelResp.setText(f.getTelResp());
        txTelCom.setText(f.getTelComercial()); txEmailResp.setText(f.getEmailResp());
        txEmailCom.setText(f.getEmailComercial()); txRua.setText(f.getDescRua());
        txBairro.setText(f.getDescBairro()); txNum.setText(String.valueOf(f.getDescNumLocal()));
        txCep.setText(f.getCep() != null ? f.getCep() : "");
    }

    private void limparCampos() {
        txId.setText(""); txRazaoSocial.setText(""); txNomeFantasia.setText("");
        txCidade.setText(""); txEstado.setText(""); txPais.setText("");
        txResponsavel.setText(""); txTelResp.setText(""); txTelCom.setText("");
        txEmailResp.setText(""); txEmailCom.setText(""); txRua.setText("");
        txBairro.setText(""); txNum.setText(""); txCep.setText(""); idSelecionado = 0;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (FornecedorModel f : new FornecedorController().selecionarTodos()) {
            modeloTabela.addRow(new Object[]{f.getId(), f.getRazaoSocial(), f.getNomeFantasia(),
                f.getCidade(), f.getEstado(), f.getResponsavel(), f.getTelResp()});
        }
    }

    private void estadoInicial() {
        txId.setEditable(true); setFieldsEditable(false);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
    }

    private void estadoNovo() {
        txId.setEditable(false); setFieldsEditable(true);
        btnNovo.setEnabled(false); btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
    }

    private void estadoSelecao() {
        txId.setEditable(false); setFieldsEditable(true);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true); btnExcluir.setEnabled(true);
    }

    private void setFieldsEditable(boolean v) {
        txRazaoSocial.setEditable(v); txNomeFantasia.setEditable(v); txCidade.setEditable(v);
        txEstado.setEditable(v); txPais.setEditable(v); txResponsavel.setEditable(v);
        txTelResp.setEditable(v); txTelCom.setEditable(v); txEmailResp.setEditable(v);
        txEmailCom.setEditable(v); txRua.setEditable(v); txBairro.setEditable(v);
        txNum.setEditable(v); txCep.setEditable(v);
    }
}
