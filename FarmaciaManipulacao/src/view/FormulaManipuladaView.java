package view;

import controller.FormulaComposicaoController;
import controller.FormulaManipuladaController;
import controller.InsumoMaterialController;
import controller.LoteInsumoController;
import controller.ProfissionalController;
import model.FormulaComposicaoModel;
import model.FormulaManipuladaModel;
import model.InsumoMaterialModel;
import model.LoteInsumoModel;
import model.ProfissionalModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class FormulaManipuladaView extends JInternalFrame {

    // Campos da fórmula
    private JTextField txId, txNome, txPosologia, txObservacao, txPreco;
    private JComboBox<ProfissionalModel> cbProfissional;
    private JComboBox<String> cbStatus;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabelaFormulas;
    private DefaultTableModel modeloFormulas;
    private int idSelecionado = 0;

    // Campos da composição
    private JComboBox<InsumoMaterialModel> cbInsumo;
    private JComboBox<LoteInsumoModel> cbLote;
    private JTextField txDosagem;
    private JButton btnAdicionarInsumo, btnRemoverInsumo;
    private JTable tabelaComposicao;
    private DefaultTableModel modeloComposicao;

    private ArrayList<ProfissionalModel> profissionais = new ArrayList<>();
    private ArrayList<InsumoMaterialModel> insumos = new ArrayList<>();
    private ArrayList<LoteInsumoModel> lotes = new ArrayList<>();

    public FormulaManipuladaView() {
        super("Fórmulas Manipuladas", true, true, true, true);
        setSize(950, 680);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        carregarCombos();
        estadoInicial();
        carregarTabelaFormulas();
    }

    private void initComponents() {
        // ========== PAINEL SUPERIOR: Campos da Fórmula ==========
        JPanel painelFormula = new JPanel(new GridBagLayout());
        painelFormula.setBorder(new TitledBorder("Dados da Fórmula"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6); txNome = new JTextField(25);
        txPosologia = new JTextField(25); txObservacao = new JTextField(30);
        txPreco = new JTextField(10);
        cbProfissional = new JComboBox<>();
        cbStatus = new JComboBox<>(new String[]{"Ativo", "Inativo", "Em Análise"});

        // Row 0: ID + Pesquisar
        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        painelFormula.add(rl("ID:"), g);
        g.gridx = 1; g.weightx = 0.2;
        painelFormula.add(txId, g);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.weightx = 0;
        painelFormula.add(btnPesquisar, g);

        // Row 1: Nome
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        painelFormula.add(rl("Nome da Fórmula:"), g);
        g.gridx = 1; g.gridwidth = 3; g.weightx = 1;
        painelFormula.add(txNome, g);

        // Row 2: Profissional Responsável + Status
        g.gridx = 0; g.gridy = 2; g.gridwidth = 1; g.weightx = 0;
        painelFormula.add(rl("Profissional:"), g);
        g.gridx = 1; g.gridwidth = 2; g.weightx = 1;
        painelFormula.add(cbProfissional, g);
        g.gridx = 3; g.gridwidth = 1; g.weightx = 0;
        painelFormula.add(rl("Status:"), g);
        g.gridx = 4; g.weightx = 0.3;
        painelFormula.add(cbStatus, g);

        // Row 3: Posologia
        g.gridx = 0; g.gridy = 3; g.weightx = 0; g.gridwidth = 1;
        painelFormula.add(rl("Posologia:"), g);
        g.gridx = 1; g.gridwidth = 4; g.weightx = 1;
        painelFormula.add(txPosologia, g);

        // Row 4: Observação + Preço
        g.gridx = 0; g.gridy = 4; g.gridwidth = 1; g.weightx = 0;
        painelFormula.add(rl("Observação:"), g);
        g.gridx = 1; g.gridwidth = 2; g.weightx = 1;
        painelFormula.add(txObservacao, g);
        g.gridx = 3; g.gridwidth = 1; g.weightx = 0;
        painelFormula.add(rl("Preço (R$):"), g);
        g.gridx = 4; g.weightx = 0.3;
        painelFormula.add(txPreco, g);

        // --- Botões da Fórmula ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        btnNovo = new JButton("Nova Fórmula"); btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Atualizar"); btnExcluir = new JButton("Excluir"); btnFechar = new JButton("Fechar");

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
        painelTopo.add(painelFormula, BorderLayout.CENTER);
        painelTopo.add(painelBotoes, BorderLayout.SOUTH);

        // ========== PAINEL CENTRAL: Composição (insumos da fórmula) ==========
        JPanel painelComposicao = new JPanel(new BorderLayout(5, 5));
        painelComposicao.setBorder(new TitledBorder("Composição da Fórmula (Insumos)"));

        JPanel painelAdicionarInsumo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        cbInsumo = new JComboBox<>();
        cbLote = new JComboBox<>();
        txDosagem = new JTextField(8);
        btnAdicionarInsumo = new JButton("+ Adicionar");
        btnRemoverInsumo = new JButton("- Remover");
        btnAdicionarInsumo.setBackground(new Color(30, 130, 180)); btnAdicionarInsumo.setForeground(Color.WHITE);
        btnRemoverInsumo.setBackground(new Color(160, 50, 50)); btnRemoverInsumo.setForeground(Color.WHITE);

        cbInsumo.addActionListener(e -> filtrarLotesPorInsumo());
        btnAdicionarInsumo.addActionListener(e -> adicionarInsumo());
        btnRemoverInsumo.addActionListener(e -> removerInsumo());

        painelAdicionarInsumo.add(rl("Insumo:")); painelAdicionarInsumo.add(cbInsumo);
        painelAdicionarInsumo.add(rl("Lote:")); painelAdicionarInsumo.add(cbLote);
        painelAdicionarInsumo.add(rl("Dosagem:")); painelAdicionarInsumo.add(txDosagem);
        painelAdicionarInsumo.add(btnAdicionarInsumo); painelAdicionarInsumo.add(btnRemoverInsumo);

        String[] colComp = {"ID Insumo", "Insumo", "Tipo", "ID Lote", "Dosagem"};
        modeloComposicao = new DefaultTableModel(colComp, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaComposicao = new JTable(modeloComposicao);
        tabelaComposicao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        painelComposicao.add(painelAdicionarInsumo, BorderLayout.NORTH);
        painelComposicao.add(new JScrollPane(tabelaComposicao), BorderLayout.CENTER);

        // ========== PAINEL INFERIOR: Lista de Fórmulas ==========
        String[] colFormulas = {"ID", "Nome", "Profissional", "Preço", "Status"};
        modeloFormulas = new DefaultTableModel(colFormulas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaFormulas = new JTable(modeloFormulas);
        tabelaFormulas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFormulas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scrollFormulas = new JScrollPane(tabelaFormulas);
        scrollFormulas.setBorder(new TitledBorder("Fórmulas Cadastradas"));
        scrollFormulas.setPreferredSize(new Dimension(0, 140));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, painelComposicao, scrollFormulas);
        split.setResizeWeight(0.55);
        split.setDividerLocation(200);

        add(painelTopo, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
    }

    private void carregarCombos() {
        profissionais = new ProfissionalController().selecionarTodos();
        cbProfissional.removeAllItems();
        for (ProfissionalModel p : profissionais) cbProfissional.addItem(p);

        insumos = new InsumoMaterialController().selecionarTodos();
        cbInsumo.removeAllItems();
        for (InsumoMaterialModel im : insumos) cbInsumo.addItem(im);

        filtrarLotesPorInsumo();
    }

    private void filtrarLotesPorInsumo() {
        InsumoMaterialModel im = (InsumoMaterialModel) cbInsumo.getSelectedItem();
        lotes = new LoteInsumoController().selecionarTodos();
        cbLote.removeAllItems();
        if (im != null) {
            for (LoteInsumoModel l : lotes) {
                if (l.getIdInsumo() == im.getId()) cbLote.addItem(l);
            }
        }
    }

    private JLabel rl(String text) {
        JLabel l = new JLabel(text);
        l.setHorizontalAlignment(SwingConstants.RIGHT);
        return l;
    }

    private void pesquisar() {
        try {
            int id = Integer.parseInt(txId.getText().trim());
            FormulaManipuladaModel fm = new FormulaManipuladaController().pesquisar(id);
            if (fm == null) JOptionPane.showMessageDialog(this, "Fórmula não encontrada!");
            else { preencherCampos(fm); carregarComposicao(id); estadoSelecao(); }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "ID inválido!"); }
    }

    private void novo() { limparCampos(); modeloComposicao.setRowCount(0); estadoNovo(); }

    private void salvar() {
        if (txNome.getText().trim().isEmpty() || txPreco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Preço são obrigatórios!"); return;
        }
        try {
            FormulaManipuladaModel fm = coletarCampos();
            FormulaManipuladaController ctrl = new FormulaManipuladaController();
            if (ctrl.inserir(fm)) {
                int idFormula = ctrl.ultimaFormula();
                salvarComposicao(idFormula);
                JOptionPane.showMessageDialog(this, "Fórmula cadastrada com sucesso!");
                limparCampos(); modeloComposicao.setRowCount(0); carregarTabelaFormulas(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao cadastrar fórmula!");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Preço deve ser numérico!"); }
    }

    private void editar() {
        if (txNome.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Nome obrigatório!"); return; }
        try {
            FormulaManipuladaModel fm = coletarCampos();
            fm.setId(idSelecionado);
            FormulaManipuladaController ctrl = new FormulaManipuladaController();
            if (ctrl.editar(fm)) {
                new FormulaComposicaoController().excluirPorFormula(idSelecionado);
                salvarComposicao(idSelecionado);
                JOptionPane.showMessageDialog(this, "Fórmula atualizada!");
                limparCampos(); modeloComposicao.setRowCount(0); carregarTabelaFormulas(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Preço deve ser numérico!"); }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(this, "Confirma exclusão da fórmula e sua composição?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            new FormulaComposicaoController().excluirPorFormula(idSelecionado);
            if (new FormulaManipuladaController().excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Fórmula excluída!");
                limparCampos(); modeloComposicao.setRowCount(0); carregarTabelaFormulas(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao excluir! Verifique pedidos vinculados.");
        }
    }

    private void adicionarInsumo() {
        InsumoMaterialModel im = (InsumoMaterialModel) cbInsumo.getSelectedItem();
        LoteInsumoModel lote = (LoteInsumoModel) cbLote.getSelectedItem();
        String dosagem = txDosagem.getText().trim();
        if (im == null || lote == null || dosagem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione insumo, lote e informe a dosagem!"); return;
        }
        modeloComposicao.addRow(new Object[]{im.getId(), im.getDescricao(), im.getTipo(), lote.getId(), dosagem});
        txDosagem.setText("");
    }

    private void removerInsumo() {
        int linha = tabelaComposicao.getSelectedRow();
        if (linha >= 0) modeloComposicao.removeRow(linha);
        else JOptionPane.showMessageDialog(this, "Selecione um insumo na tabela para remover!");
    }

    private void salvarComposicao(int idFormula) {
        FormulaComposicaoController ctrl = new FormulaComposicaoController();
        for (int i = 0; i < modeloComposicao.getRowCount(); i++) {
            FormulaComposicaoModel fc = new FormulaComposicaoModel();
            fc.setIdFormula(idFormula);
            fc.setIdInsumo(Integer.parseInt(String.valueOf(modeloComposicao.getValueAt(i, 0))));
            fc.setIdLoteInsumo(Integer.parseInt(String.valueOf(modeloComposicao.getValueAt(i, 3))));
            fc.setDosagem(String.valueOf(modeloComposicao.getValueAt(i, 4)));
            ctrl.inserir(fc);
        }
    }

    private void carregarComposicao(int idFormula) {
        modeloComposicao.setRowCount(0);
        for (FormulaComposicaoModel fc : new FormulaComposicaoController().selecionarPorFormula(idFormula)) {
            modeloComposicao.addRow(new Object[]{fc.getIdInsumo(), fc.getNomeInsumo(), fc.getTipoInsumo(), fc.getIdLoteInsumo(), fc.getDosagem()});
        }
    }

    private void selecionarLinha() {
        int linha = tabelaFormulas.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloFormulas.getValueAt(linha, 0)));
            FormulaManipuladaModel fm = new FormulaManipuladaController().pesquisar(idSelecionado);
            if (fm != null) { preencherCampos(fm); carregarComposicao(idSelecionado); estadoSelecao(); }
        }
    }

    private FormulaManipuladaModel coletarCampos() {
        FormulaManipuladaModel fm = new FormulaManipuladaModel();
        ProfissionalModel p = (ProfissionalModel) cbProfissional.getSelectedItem();
        if (p != null) fm.setIdProfissional(p.getId());
        fm.setNome(txNome.getText().trim());
        fm.setPosologia(txPosologia.getText().trim());
        fm.setObservacao(txObservacao.getText().trim().isEmpty() ? null : txObservacao.getText().trim());
        fm.setPreco(Float.parseFloat(txPreco.getText().trim()));
        fm.setfStatus((String) cbStatus.getSelectedItem());
        return fm;
    }

    private void preencherCampos(FormulaManipuladaModel fm) {
        idSelecionado = fm.getId();
        txId.setText(String.valueOf(fm.getId()));
        txNome.setText(fm.getNome()); txPosologia.setText(fm.getPosologia());
        txObservacao.setText(fm.getObservacao() != null ? fm.getObservacao() : "");
        txPreco.setText(String.valueOf(fm.getPreco()));
        cbStatus.setSelectedItem(fm.getfStatus());
        for (int i = 0; i < profissionais.size(); i++) {
            if (profissionais.get(i).getId() == fm.getIdProfissional()) { cbProfissional.setSelectedIndex(i); break; }
        }
    }

    private void limparCampos() {
        txId.setText(""); txNome.setText(""); txPosologia.setText("");
        txObservacao.setText(""); txPreco.setText(""); idSelecionado = 0;
        if (cbStatus.getItemCount() > 0) cbStatus.setSelectedIndex(0);
        if (cbProfissional.getItemCount() > 0) cbProfissional.setSelectedIndex(0);
    }

    private void carregarTabelaFormulas() {
        modeloFormulas.setRowCount(0);
        for (FormulaManipuladaModel fm : new FormulaManipuladaController().selecionarTodos()) {
            modeloFormulas.addRow(new Object[]{fm.getId(), fm.getNome(), fm.getNomeProfissional(),
                String.format("R$ %.2f", fm.getPreco()), fm.getfStatus()});
        }
    }

    private void estadoInicial() {
        txId.setEditable(true); setFieldsEnabled(false);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
        btnAdicionarInsumo.setEnabled(false); btnRemoverInsumo.setEnabled(false);
    }

    private void estadoNovo() {
        txId.setEditable(false); setFieldsEnabled(true);
        btnNovo.setEnabled(false); btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
        btnAdicionarInsumo.setEnabled(true); btnRemoverInsumo.setEnabled(true);
    }

    private void estadoSelecao() {
        txId.setEditable(false); setFieldsEnabled(true);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true); btnExcluir.setEnabled(true);
        btnAdicionarInsumo.setEnabled(true); btnRemoverInsumo.setEnabled(true);
    }

    private void setFieldsEnabled(boolean v) {
        txNome.setEditable(v); txPosologia.setEditable(v);
        txObservacao.setEditable(v); txPreco.setEditable(v);
        cbProfissional.setEnabled(v); cbStatus.setEnabled(v);
        txDosagem.setEditable(v);
        cbInsumo.setEnabled(v); cbLote.setEnabled(v);
    }
}
