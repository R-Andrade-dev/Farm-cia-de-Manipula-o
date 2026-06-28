package view;

import controller.ClienteController;
import model.ClienteModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ClienteView extends JInternalFrame {

    private JTextField txId, txNome, txCpf, txTel1, txTel2, txEmail;
    private JTextField txRua, txBairro, txNum, txCep, txCidade, txEstado, txNacionalidade;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = 0;

    public ClienteView() {
        super("Cadastro de Clientes", true, true, true, true);
        setSize(820, 600);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        estadoInicial();
        carregarTabela();
    }

    private void initComponents() {
        // --- Painel de campos ---
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(new TitledBorder("Dados do Cliente"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6);
        txNome = new JTextField(25);
        txCpf = new JTextField(15);
        txTel1 = new JTextField(13);
        txTel2 = new JTextField(13);
        txEmail = new JTextField(25);
        txRua = new JTextField(20);
        txBairro = new JTextField(15);
        txNum = new JTextField(6);
        txCep = new JTextField(10);
        txCidade = new JTextField(15);
        txEstado = new JTextField(10);
        txNacionalidade = new JTextField(15);

        // Row 0: ID + Pesquisar
        addField(painelCampos, g, "ID:", txId, 0, 0, 1);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.gridy = 0; g.gridwidth = 1;
        painelCampos.add(btnPesquisar, g);

        // Row 1: Nome
        addField(painelCampos, g, "Nome:", txNome, 0, 1, 3);
        // Row 2: CPF | Tel1 | Tel2
        addField(painelCampos, g, "CPF:", txCpf, 0, 2, 1);
        addField(painelCampos, g, "Tel 1:", txTel1, 2, 2, 1);
        addField(painelCampos, g, "Tel 2:", txTel2, 4, 2, 1);
        // Row 3: Email
        addField(painelCampos, g, "Email:", txEmail, 0, 3, 3);
        // Row 4: Rua | Num
        addField(painelCampos, g, "Rua:", txRua, 0, 4, 1);
        addField(painelCampos, g, "Número:", txNum, 2, 4, 1);
        // Row 5: Bairro | CEP
        addField(painelCampos, g, "Bairro:", txBairro, 0, 5, 1);
        addField(painelCampos, g, "CEP:", txCep, 2, 5, 1);
        // Row 6: Cidade | Estado | Nacionalidade
        addField(painelCampos, g, "Cidade:", txCidade, 0, 6, 1);
        addField(painelCampos, g, "Estado:", txEstado, 2, 6, 1);
        addField(painelCampos, g, "Nacionalidade:", txNacionalidade, 4, 6, 1);

        // --- Painel de botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnFechar = new JButton("Fechar");

        btnNovo.setBackground(new Color(46, 139, 87));
        btnNovo.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(30, 100, 200));
        btnSalvar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(210, 140, 20));
        btnEditar.setForeground(Color.WHITE);
        btnExcluir.setBackground(new Color(180, 30, 30));
        btnExcluir.setForeground(Color.WHITE);

        btnNovo.addActionListener(e -> novo());
        btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnFechar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelCampos, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        // --- Tabela ---
        String[] colunas = {"ID", "Nome", "CPF", "Tel 1", "Email", "Cidade", "Estado"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new TitledBorder("Lista de Clientes"));

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
            ClienteController ctrl = new ClienteController();
            ClienteModel c = ctrl.pesquisar(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
            } else {
                preencherCampos(c);
                estadoSelecao();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um ID válido!");
        }
    }

    private void novo() {
        limparCampos();
        estadoNovo();
    }

    private void salvar() {
        if (txNome.getText().trim().isEmpty() || txCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!");
            return;
        }
        try {
            ClienteModel c = coletarCampos();
            ClienteController ctrl = new ClienteController();
            if (ctrl.inserir(c)) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
                estadoInicial();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número do local deve ser numérico!");
        }
    }

    private void editar() {
        if (txNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return;
        }
        try {
            ClienteModel c = coletarCampos();
            c.setId(idSelecionado);
            ClienteController ctrl = new ClienteController();
            if (ctrl.editar(c)) {
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                limparCampos();
                carregarTabela();
                estadoInicial();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número do local deve ser numérico!");
        }
    }

    private void excluir() {
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma a exclusão do cliente?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ClienteController ctrl = new ClienteController();
            if (ctrl.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                limparCampos();
                carregarTabela();
                estadoInicial();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente! Verifique se há pedidos vinculados.");
            }
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloTabela.getValueAt(linha, 0)));
            ClienteController ctrl = new ClienteController();
            ClienteModel c = ctrl.pesquisar(idSelecionado);
            if (c != null) {
                preencherCampos(c);
                estadoSelecao();
            }
        }
    }

    private ClienteModel coletarCampos() {
        ClienteModel c = new ClienteModel();
        c.setNome(txNome.getText().trim());
        c.setCpf(txCpf.getText().trim());
        c.setTel1(txTel1.getText().trim());
        c.setTel2(txTel2.getText().trim().isEmpty() ? null : txTel2.getText().trim());
        c.setEmail(txEmail.getText().trim().isEmpty() ? null : txEmail.getText().trim());
        c.setDescRua(txRua.getText().trim());
        c.setDescBairro(txBairro.getText().trim());
        c.setDescNumLocal(txNum.getText().trim().isEmpty() ? 0 : Integer.parseInt(txNum.getText().trim()));
        c.setCep(txCep.getText().trim().isEmpty() ? null : txCep.getText().trim());
        c.setCidade(txCidade.getText().trim());
        c.setEstado(txEstado.getText().trim());
        c.setNacionalidade(txNacionalidade.getText().trim());
        return c;
    }

    private void preencherCampos(ClienteModel c) {
        idSelecionado = c.getId();
        txId.setText(String.valueOf(c.getId()));
        txNome.setText(c.getNome());
        txCpf.setText(c.getCpf());
        txTel1.setText(c.getTel1());
        txTel2.setText(c.getTel2() != null ? c.getTel2() : "");
        txEmail.setText(c.getEmail() != null ? c.getEmail() : "");
        txRua.setText(c.getDescRua());
        txBairro.setText(c.getDescBairro());
        txNum.setText(String.valueOf(c.getDescNumLocal()));
        txCep.setText(c.getCep() != null ? c.getCep() : "");
        txCidade.setText(c.getCidade());
        txEstado.setText(c.getEstado());
        txNacionalidade.setText(c.getNacionalidade());
    }

    private void limparCampos() {
        txId.setText(""); txNome.setText(""); txCpf.setText("");
        txTel1.setText(""); txTel2.setText(""); txEmail.setText("");
        txRua.setText(""); txBairro.setText(""); txNum.setText("");
        txCep.setText(""); txCidade.setText(""); txEstado.setText("");
        txNacionalidade.setText(""); idSelecionado = 0;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ClienteController ctrl = new ClienteController();
        ArrayList<ClienteModel> lista = ctrl.selecionarTodos();
        for (ClienteModel c : lista) {
            modeloTabela.addRow(new Object[]{
                c.getId(), c.getNome(), c.getCpf(), c.getTel1(),
                c.getEmail(), c.getCidade(), c.getEstado()
            });
        }
    }

    private void estadoInicial() {
        txId.setEditable(true);
        setFieldsEditable(false);
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }

    private void estadoNovo() {
        txId.setEditable(false);
        setFieldsEditable(true);
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }

    private void estadoSelecao() {
        txId.setEditable(false);
        setFieldsEditable(true);
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
    }

    private void setFieldsEditable(boolean v) {
        txNome.setEditable(v); txCpf.setEditable(v);
        txTel1.setEditable(v); txTel2.setEditable(v); txEmail.setEditable(v);
        txRua.setEditable(v); txBairro.setEditable(v); txNum.setEditable(v);
        txCep.setEditable(v); txCidade.setEditable(v); txEstado.setEditable(v);
        txNacionalidade.setEditable(v);
    }
}
