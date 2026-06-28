package view;

import controller.ClienteController;
import controller.FormulaPedidoController;
import controller.FormulaManipuladaController;
import controller.PedidoController;
import controller.ProfissionalController;
import model.ClienteModel;
import model.FormulaPedidoModel;
import model.FormulaManipuladaModel;
import model.PedidoModel;
import model.ProfissionalModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PedidoView extends JInternalFrame {

    // Campos do pedido
    private JTextField txId, txMedico, txCrm, txObservacao;
    private JComboBox<ClienteModel> cbCliente;
    private JComboBox<ProfissionalModel> cbProfissional;
    private JComboBox<String> cbTipoPagamento, cbStatus;
    private JLabel lblValorTotal;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabelaPedidos;
    private DefaultTableModel modeloPedidos;
    private int idSelecionado = 0;
    private float valorTotal = 0;

    // Campos de fórmulas do pedido
    private JComboBox<FormulaManipuladaModel> cbFormula;
    private JTextField txQuantidade;
    private JButton btnAdicionarFormula, btnRemoverFormula;
    private JTable tabelaFormulas;
    private DefaultTableModel modeloFormulas;

    private ArrayList<ClienteModel> clientes = new ArrayList<>();
    private ArrayList<ProfissionalModel> profissionais = new ArrayList<>();
    private ArrayList<FormulaManipuladaModel> formulas = new ArrayList<>();

    public PedidoView() {
        super("Pedidos", true, true, true, true);
        setSize(980, 720);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        carregarCombos();
        estadoInicial();
        carregarTabelaPedidos();
    }

    private void initComponents() {
        // ========== PAINEL SUPERIOR: Dados do Pedido ==========
        JPanel painelPedido = new JPanel(new GridBagLayout());
        painelPedido.setBorder(new TitledBorder("Dados do Pedido"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6); txMedico = new JTextField(25);
        txCrm = new JTextField(12); txObservacao = new JTextField(30);
        cbCliente = new JComboBox<>();
        cbProfissional = new JComboBox<>();
        cbTipoPagamento = new JComboBox<>(new String[]{"Dinheiro", "Pix", "Cartão Débito", "Cartão Crédito", "Convênio"});
        cbStatus = new JComboBox<>(new String[]{"Em confecção", "Pronto", "Entregue", "Cancelado"});
        lblValorTotal = rl("R$ 0,00");
        lblValorTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblValorTotal.setForeground(new Color(20, 100, 20));

        // Row 0: ID + Pesquisar
        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        painelPedido.add(rl("ID Pedido:"), g);
        g.gridx = 1; g.weightx = 0.2;
        painelPedido.add(txId, g);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.weightx = 0;
        painelPedido.add(btnPesquisar, g);

        // Row 1: Cliente
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        painelPedido.add(rl("Cliente:"), g);
        g.gridx = 1; g.gridwidth = 4; g.weightx = 1;
        painelPedido.add(cbCliente, g);

        // Row 2: Profissional Atendente + Status
        g.gridx = 0; g.gridy = 2; g.gridwidth = 1; g.weightx = 0;
        painelPedido.add(rl("Atendente:"), g);
        g.gridx = 1; g.gridwidth = 2; g.weightx = 1;
        painelPedido.add(cbProfissional, g);
        g.gridx = 3; g.gridwidth = 1; g.weightx = 0;
        painelPedido.add(rl("Status:"), g);
        g.gridx = 4; g.weightx = 0.3;
        painelPedido.add(cbStatus, g);

        // Row 3: Médico Solicitante + CRM
        g.gridx = 0; g.gridy = 3; g.gridwidth = 1; g.weightx = 0;
        painelPedido.add(rl("Médico Solicitante:"), g);
        g.gridx = 1; g.gridwidth = 2; g.weightx = 1;
        painelPedido.add(txMedico, g);
        g.gridx = 3; g.gridwidth = 1; g.weightx = 0;
        painelPedido.add(rl("CRM:"), g);
        g.gridx = 4; g.weightx = 0.3;
        painelPedido.add(txCrm, g);

        // Row 4: Pagamento + Observação
        g.gridx = 0; g.gridy = 4; g.gridwidth = 1; g.weightx = 0;
        painelPedido.add(rl("Pagamento:"), g);
        g.gridx = 1; g.weightx = 0.3;
        painelPedido.add(cbTipoPagamento, g);
        g.gridx = 2; g.weightx = 0;
        painelPedido.add(rl("Observação:"), g);
        g.gridx = 3; g.gridwidth = 2; g.weightx = 1;
        painelPedido.add(txObservacao, g);

        // Row 5: Total
        g.gridx = 3; g.gridy = 5; g.gridwidth = 1; g.weightx = 0;
        painelPedido.add(rl("TOTAL:"), g);
        g.gridx = 4; g.weightx = 0;
        painelPedido.add(lblValorTotal, g);

        // --- Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        btnNovo = new JButton("Novo Pedido"); btnSalvar = new JButton("Salvar Pedido");
        btnEditar = new JButton("Atualizar"); btnExcluir = new JButton("Cancelar Pedido"); btnFechar = new JButton("Fechar");

        btnNovo.setBackground(new Color(46, 139, 87)); btnNovo.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(30, 100, 200)); btnSalvar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(210, 140, 20)); btnEditar.setForeground(Color.WHITE);
        btnExcluir.setBackground(new Color(180, 30, 30)); btnExcluir.setForeground(Color.WHITE);

        btnNovo.addActionListener(e -> novo()); btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar()); btnExcluir.addActionListener(e -> excluir());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnNovo); painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar); painelBotoes.add(btnExcluir); painelBotoes.add(btnFechar);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.add(painelPedido, BorderLayout.CENTER);
        painelTopo.add(painelBotoes, BorderLayout.SOUTH);

        // ========== PAINEL CENTRAL: Fórmulas do Pedido ==========
        JPanel painelItens = new JPanel(new BorderLayout(5, 5));
        painelItens.setBorder(new TitledBorder("Fórmulas do Pedido"));

        JPanel painelAdicionarFormula = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        cbFormula = new JComboBox<>();
        txQuantidade = new JTextField(5);
        btnAdicionarFormula = new JButton("+ Adicionar");
        btnRemoverFormula = new JButton("- Remover");
        btnAdicionarFormula.setBackground(new Color(30, 130, 180)); btnAdicionarFormula.setForeground(Color.WHITE);
        btnRemoverFormula.setBackground(new Color(160, 50, 50)); btnRemoverFormula.setForeground(Color.WHITE);

        btnAdicionarFormula.addActionListener(e -> adicionarFormula());
        btnRemoverFormula.addActionListener(e -> removerFormula());

        painelAdicionarFormula.add(rl("Fórmula:")); painelAdicionarFormula.add(cbFormula);
        painelAdicionarFormula.add(rl("Qtd:")); painelAdicionarFormula.add(txQuantidade);
        painelAdicionarFormula.add(btnAdicionarFormula); painelAdicionarFormula.add(btnRemoverFormula);

        String[] colItens = {"ID Fórmula", "Nome da Fórmula", "Quantidade", "Preço Unit.", "Subtotal"};
        modeloFormulas = new DefaultTableModel(colItens, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaFormulas = new JTable(modeloFormulas);
        tabelaFormulas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        painelItens.add(painelAdicionarFormula, BorderLayout.NORTH);
        painelItens.add(new JScrollPane(tabelaFormulas), BorderLayout.CENTER);

        // ========== PAINEL INFERIOR: Lista de Pedidos ==========
        String[] colPedidos = {"ID", "Cliente", "Atendente", "Data/Hora", "Médico", "Total", "Pagamento", "Status"};
        modeloPedidos = new DefaultTableModel(colPedidos, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPedidos = new JTable(modeloPedidos);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scrollPedidos = new JScrollPane(tabelaPedidos);
        scrollPedidos.setBorder(new TitledBorder("Pedidos"));
        scrollPedidos.setPreferredSize(new Dimension(0, 150));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, painelItens, scrollPedidos);
        split.setResizeWeight(0.5);
        split.setDividerLocation(200);

        add(painelTopo, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
    }

    private void carregarCombos() {
        clientes = new ClienteController().selecionarTodos();
        cbCliente.removeAllItems();
        for (ClienteModel c : clientes) cbCliente.addItem(c);

        profissionais = new ProfissionalController().selecionarTodos();
        cbProfissional.removeAllItems();
        for (ProfissionalModel p : profissionais) cbProfissional.addItem(p);

        formulas = new FormulaManipuladaController().selecionarTodos();
        cbFormula.removeAllItems();
        for (FormulaManipuladaModel fm : formulas) cbFormula.addItem(fm);
    }

    private JLabel rl(String text) {
        JLabel l = new JLabel(text);
        l.setHorizontalAlignment(SwingConstants.RIGHT);
        return l;
    }

    private void pesquisar() {
        try {
            int id = Integer.parseInt(txId.getText().trim());
            PedidoModel p = new PedidoController().pesquisar(id);
            if (p == null) JOptionPane.showMessageDialog(this, "Pedido não encontrado!");
            else {
                preencherCampos(p);
                carregarFormulasDoPedido(id);
                estadoSelecao();
            }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "ID inválido!"); }
    }

    private void novo() {
        limparCampos(); modeloFormulas.setRowCount(0); valorTotal = 0;
        atualizarTotal(); estadoNovo();
    }

    private void salvar() {
        if (txMedico.getText().trim().isEmpty() || txCrm.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Médico Solicitante e CRM são obrigatórios!"); return;
        }
        if (modeloFormulas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione ao menos uma fórmula ao pedido!"); return;
        }
        PedidoModel p = coletarCampos();
        PedidoController ctrl = new PedidoController();
        if (ctrl.inserir(p)) {
            int idPedido = ctrl.ultimoPedido();
            salvarFormulasDoPedido(idPedido);
            JOptionPane.showMessageDialog(this, "Pedido registrado com sucesso! Código: " + idPedido);
            limparCampos(); modeloFormulas.setRowCount(0); valorTotal = 0; atualizarTotal();
            carregarTabelaPedidos(); estadoInicial();
        } else JOptionPane.showMessageDialog(this, "Erro ao registrar pedido!");
    }

    private void editar() {
        if (txMedico.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Médico obrigatório!"); return; }
        PedidoModel p = coletarCampos();
        p.setId(idSelecionado);
        PedidoController ctrl = new PedidoController();
        if (ctrl.editar(p)) {
            new FormulaPedidoController().excluirPorPedido(idSelecionado);
            salvarFormulasDoPedido(idSelecionado);
            JOptionPane.showMessageDialog(this, "Pedido atualizado!");
            limparCampos(); modeloFormulas.setRowCount(0); valorTotal = 0; atualizarTotal();
            carregarTabelaPedidos(); estadoInicial();
        } else JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(this, "Confirma a exclusão do pedido?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            new FormulaPedidoController().excluirPorPedido(idSelecionado);
            if (new PedidoController().excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Pedido excluído!");
                limparCampos(); modeloFormulas.setRowCount(0); valorTotal = 0; atualizarTotal();
                carregarTabelaPedidos(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao excluir!");
        }
    }

    private void adicionarFormula() {
        FormulaManipuladaModel fm = (FormulaManipuladaModel) cbFormula.getSelectedItem();
        if (fm == null) { JOptionPane.showMessageDialog(this, "Selecione uma fórmula!"); return; }
        int qtd;
        try { qtd = Integer.parseInt(txQuantidade.getText().trim()); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantidade inválida!"); return; }
        if (qtd <= 0) { JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!"); return; }

        float precoUnit = fm.getPreco();
        float subtotal = precoUnit * qtd;
        valorTotal += subtotal;
        atualizarTotal();
        modeloFormulas.addRow(new Object[]{
            fm.getId(), fm.getNome(), qtd,
            String.format("R$ %.2f", precoUnit),
            String.format("R$ %.2f", subtotal)
        });
        txQuantidade.setText("");
    }

    private void removerFormula() {
        int linha = tabelaFormulas.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma fórmula na tabela!"); return; }
        float subtotal;
        try {
            String s = String.valueOf(modeloFormulas.getValueAt(linha, 4)).replace("R$ ", "").replace(",", ".");
            subtotal = Float.parseFloat(s);
        } catch (NumberFormatException ex) { subtotal = 0; }
        valorTotal -= subtotal;
        if (valorTotal < 0) valorTotal = 0;
        atualizarTotal();
        modeloFormulas.removeRow(linha);
    }

    private void atualizarTotal() {
        lblValorTotal.setText(String.format("R$ %.2f", valorTotal));
    }

    private void salvarFormulasDoPedido(int idPedido) {
        FormulaPedidoController ctrl = new FormulaPedidoController();
        for (int i = 0; i < modeloFormulas.getRowCount(); i++) {
            FormulaPedidoModel fp = new FormulaPedidoModel();
            fp.setIdPedido(idPedido);
            fp.setIdFormula(Integer.parseInt(String.valueOf(modeloFormulas.getValueAt(i, 0))));
            fp.setQuantidade(Integer.parseInt(String.valueOf(modeloFormulas.getValueAt(i, 2))));
            String precoStr = String.valueOf(modeloFormulas.getValueAt(i, 3)).replace("R$ ", "").replace(",", ".");
            fp.setPreco(Float.parseFloat(precoStr));
            ctrl.inserir(fp);
        }
    }

    private void carregarFormulasDoPedido(int idPedido) {
        modeloFormulas.setRowCount(0); valorTotal = 0;
        for (FormulaPedidoModel fp : new FormulaPedidoController().selecionarPorPedido(idPedido)) {
            float subtotal = fp.getPreco() * fp.getQuantidade();
            valorTotal += subtotal;
            modeloFormulas.addRow(new Object[]{
                fp.getIdFormula(), fp.getNomeFormula(), fp.getQuantidade(),
                String.format("R$ %.2f", fp.getPreco()),
                String.format("R$ %.2f", subtotal)
            });
        }
        atualizarTotal();
    }

    private void selecionarLinha() {
        int linha = tabelaPedidos.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloPedidos.getValueAt(linha, 0)));
            PedidoModel p = new PedidoController().pesquisar(idSelecionado);
            if (p != null) { preencherCampos(p); carregarFormulasDoPedido(idSelecionado); estadoSelecao(); }
        }
    }

    private PedidoModel coletarCampos() {
        PedidoModel p = new PedidoModel();
        ClienteModel c = (ClienteModel) cbCliente.getSelectedItem();
        ProfissionalModel prof = (ProfissionalModel) cbProfissional.getSelectedItem();
        if (c != null) p.setIdCliente(c.getId());
        if (prof != null) p.setIdProfissional(prof.getId());
        p.setMedicoSolicitante(txMedico.getText().trim());
        p.setCrmSolicitante(txCrm.getText().trim());
        p.setValorTotal(valorTotal);
        p.setTipoPagamento((String) cbTipoPagamento.getSelectedItem());
        p.setObservacao(txObservacao.getText().trim());
        p.setStatus((String) cbStatus.getSelectedItem());
        return p;
    }

    private void preencherCampos(PedidoModel p) {
        idSelecionado = p.getId();
        txId.setText(String.valueOf(p.getId()));
        txMedico.setText(p.getMedicoSolicitante());
        txCrm.setText(p.getCrmSolicitante());
        txObservacao.setText(p.getObservacao());
        cbTipoPagamento.setSelectedItem(p.getTipoPagamento());
        cbStatus.setSelectedItem(p.getStatus());
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == p.getIdCliente()) { cbCliente.setSelectedIndex(i); break; }
        }
        for (int i = 0; i < profissionais.size(); i++) {
            if (profissionais.get(i).getId() == p.getIdProfissional()) { cbProfissional.setSelectedIndex(i); break; }
        }
    }

    private void limparCampos() {
        txId.setText(""); txMedico.setText(""); txCrm.setText(""); txObservacao.setText("");
        if (cbCliente.getItemCount() > 0) cbCliente.setSelectedIndex(0);
        if (cbProfissional.getItemCount() > 0) cbProfissional.setSelectedIndex(0);
        if (cbTipoPagamento.getItemCount() > 0) cbTipoPagamento.setSelectedIndex(0);
        if (cbStatus.getItemCount() > 0) cbStatus.setSelectedIndex(0);
        txQuantidade.setText(""); idSelecionado = 0;
    }

    private void carregarTabelaPedidos() {
        modeloPedidos.setRowCount(0);
        for (PedidoModel p : new PedidoController().selecionarTodos()) {
            modeloPedidos.addRow(new Object[]{
                p.getId(), p.getNomeCliente(), p.getNomeProfissional(), p.getDataHora(),
                p.getMedicoSolicitante(), String.format("R$ %.2f", p.getValorTotal()),
                p.getTipoPagamento(), p.getStatus()
            });
        }
    }

    private void estadoInicial() {
        txId.setEditable(true); setFieldsEnabled(false);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
        btnAdicionarFormula.setEnabled(false); btnRemoverFormula.setEnabled(false);
    }

    private void estadoNovo() {
        txId.setEditable(false); setFieldsEnabled(true);
        btnNovo.setEnabled(false); btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
        btnAdicionarFormula.setEnabled(true); btnRemoverFormula.setEnabled(true);
    }

    private void estadoSelecao() {
        txId.setEditable(false); setFieldsEnabled(true);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true); btnExcluir.setEnabled(true);
        btnAdicionarFormula.setEnabled(true); btnRemoverFormula.setEnabled(true);
    }

    private void setFieldsEnabled(boolean v) {
        cbCliente.setEnabled(v); cbProfissional.setEnabled(v);
        txMedico.setEditable(v); txCrm.setEditable(v); txObservacao.setEditable(v);
        cbTipoPagamento.setEnabled(v); cbStatus.setEnabled(v);
        cbFormula.setEnabled(v); txQuantidade.setEditable(v);
    }
}
