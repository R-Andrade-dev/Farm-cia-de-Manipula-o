package view;

import controller.ProfissionalController;
import model.ProfissionalModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ProfissionalView extends JInternalFrame {

    private JTextField txId, txNome, txCpf, txTel, txEmail;
    private JTextField txTelEmerg, txNomeEmerg;
    private JTextField txRua, txBairro, txNum, txCep, txCidade, txEstado, txNacionalidade;
    private JCheckBox chkFarmaceutico;
    private JTextField txCrf, txCargo, txSalario, txHoraInicio, txHoraFim;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnFechar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = 0;

    public ProfissionalView() {
        super("Cadastro de Profissionais", true, true, true, true);
        setSize(900, 650);
        setLayout(new BorderLayout(5, 5));
        initComponents();
        estadoInicial();
        carregarTabela();
    }

    private void initComponents() {
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(new TitledBorder("Dados do Profissional"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 5, 3, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        txId = new JTextField(6);
        txNome = new JTextField(25);
        txCpf = new JTextField(15);
        txTel = new JTextField(13);
        txEmail = new JTextField(25);
        txTelEmerg = new JTextField(13);
        txNomeEmerg = new JTextField(20);
        txRua = new JTextField(20);
        txBairro = new JTextField(15);
        txNum = new JTextField(6);
        txCep = new JTextField(10);
        txCidade = new JTextField(15);
        txEstado = new JTextField(8);
        txNacionalidade = new JTextField(15);
        chkFarmaceutico = new JCheckBox("É Farmacêutico?");
        txCrf = new JTextField(8);
        txCargo = new JTextField(15);
        txSalario = new JTextField(10);
        txHoraInicio = new JTextField(8);
        txHoraFim = new JTextField(8);

        // Row 0: ID + Pesquisar
        addField(painelCampos, g, "ID:", txId, 0, 0, 1);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(e -> pesquisar());
        g.gridx = 2; g.gridy = 0; g.gridwidth = 1;
        painelCampos.add(btnPesquisar, g);

        // Row 1: Nome
        addField(painelCampos, g, "Nome:", txNome, 0, 1, 5);
        // Row 2: CPF | Tel | Email
        addField(painelCampos, g, "CPF:", txCpf, 0, 2, 1);
        addField(painelCampos, g, "Tel:", txTel, 2, 2, 1);
        addField(painelCampos, g, "Email:", txEmail, 4, 2, 1);
        // Row 3: Tel Emergência | Nome Emergência
        addField(painelCampos, g, "Tel Emergência:", txTelEmerg, 0, 3, 1);
        addField(painelCampos, g, "Contato Emergência:", txNomeEmerg, 2, 3, 3);
        // Row 4: Rua | Num | Bairro
        addField(painelCampos, g, "Rua:", txRua, 0, 4, 1);
        addField(painelCampos, g, "Nº:", txNum, 2, 4, 1);
        addField(painelCampos, g, "Bairro:", txBairro, 4, 4, 1);
        // Row 5: CEP | Cidade | Estado | Nacionalidade
        addField(painelCampos, g, "CEP:", txCep, 0, 5, 1);
        addField(painelCampos, g, "Cidade:", txCidade, 2, 5, 1);
        addField(painelCampos, g, "Estado:", txEstado, 4, 5, 1);
        // Row 6: Nacionalidade | Farmacêutico | CRF
        addField(painelCampos, g, "Nacionalidade:", txNacionalidade, 0, 6, 1);
        g.gridx = 2; g.gridy = 6; g.gridwidth = 1;
        painelCampos.add(chkFarmaceutico, g);
        addField(painelCampos, g, "CRF:", txCrf, 3, 6, 1);
        // Row 7: Cargo | Salário | Hora Início + Hora Fim juntos
        addField(painelCampos, g, "Cargo:", txCargo, 0, 7, 1);
        addField(painelCampos, g, "Salário:", txSalario, 2, 7, 1);
        JPanel painelHoras = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        painelHoras.setOpaque(false);
        JLabel lblHI = new JLabel("Hora Início:");
        lblHI.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lblHF = new JLabel("Hora Fim:");
        lblHF.setHorizontalAlignment(SwingConstants.RIGHT);
        painelHoras.add(lblHI);
        painelHoras.add(txHoraInicio);
        painelHoras.add(Box.createHorizontalStrut(8));
        painelHoras.add(lblHF);
        painelHoras.add(txHoraFim);
        g.gridx = 4; g.gridy = 7; g.gridwidth = 2; g.weightx = 1;
        painelCampos.add(painelHoras, g);

        chkFarmaceutico.addActionListener(e -> txCrf.setEditable(chkFarmaceutico.isSelected()));

        // --- Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnFechar = new JButton("Fechar");

        btnNovo.setBackground(new Color(46, 139, 87)); btnNovo.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(30, 100, 200)); btnSalvar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(210, 140, 20)); btnEditar.setForeground(Color.WHITE);
        btnExcluir.setBackground(new Color(180, 30, 30)); btnExcluir.setForeground(Color.WHITE);

        btnNovo.addActionListener(e -> novo());
        btnSalvar.addActionListener(e -> salvar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnNovo); painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar); painelBotoes.add(btnExcluir); painelBotoes.add(btnFechar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelCampos, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        // --- Tabela ---
        String[] colunas = {"ID", "Nome", "CPF", "Cargo", "Farmacêutico", "CRF", "Cidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { selecionarLinha(); }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new TitledBorder("Lista de Profissionais"));

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
            ProfissionalModel p = new ProfissionalController().pesquisar(id);
            if (p == null) JOptionPane.showMessageDialog(this, "Profissional não encontrado!");
            else { preencherCampos(p); estadoSelecao(); }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um ID válido!");
        }
    }

    private void novo() { limparCampos(); estadoNovo(); }

    private void salvar() {
        if (txNome.getText().trim().isEmpty() || txCargo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Cargo são obrigatórios!"); return;
        }
        if (chkFarmaceutico.isSelected() && txCrf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Farmacêutico deve ter CRF!"); return;
        }
        try {
            ProfissionalModel p = coletarCampos();
            if (new ProfissionalController().inserir(p)) {
                JOptionPane.showMessageDialog(this, "Profissional cadastrado com sucesso!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao cadastrar profissional!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número, salário devem ser numéricos!");
        }
    }

    private void editar() {
        if (txNome.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Nome é obrigatório!"); return; }
        if (chkFarmaceutico.isSelected() && txCrf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Farmacêutico deve ter CRF!"); return;
        }
        try {
            ProfissionalModel p = coletarCampos();
            p.setId(idSelecionado);
            if (new ProfissionalController().editar(p)) {
                JOptionPane.showMessageDialog(this, "Profissional atualizado!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao atualizar!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número, salário devem ser numéricos!");
        }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (new ProfissionalController().excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Profissional excluído!");
                limparCampos(); carregarTabela(); estadoInicial();
            } else JOptionPane.showMessageDialog(this, "Erro ao excluir! Verifique vínculos.");
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = Integer.parseInt(String.valueOf(modeloTabela.getValueAt(linha, 0)));
            ProfissionalModel p = new ProfissionalController().pesquisar(idSelecionado);
            if (p != null) { preencherCampos(p); estadoSelecao(); }
        }
    }

    private ProfissionalModel coletarCampos() {
        ProfissionalModel p = new ProfissionalModel();
        p.setNome(txNome.getText().trim());
        p.setCpf(txCpf.getText().trim());
        p.setTel(txTel.getText().trim());
        p.setEmail(txEmail.getText().trim());
        p.setTelEmergencia(txTelEmerg.getText().trim());
        p.setNomeEmergencia(txNomeEmerg.getText().trim());
        p.setDescRua(txRua.getText().trim());
        p.setDescBairro(txBairro.getText().trim());
        p.setDescNumLocal(txNum.getText().trim().isEmpty() ? 0 : Integer.parseInt(txNum.getText().trim()));
        p.setCep(txCep.getText().trim().isEmpty() ? null : txCep.getText().trim());
        p.setCidade(txCidade.getText().trim());
        p.setEstado(txEstado.getText().trim());
        p.setNacionalidade(txNacionalidade.getText().trim());
        p.setFarmaceutico(chkFarmaceutico.isSelected());
        p.setCrf(txCrf.getText().trim().isEmpty() ? null : txCrf.getText().trim());
        p.setCargo(txCargo.getText().trim());
        p.setSalario(txSalario.getText().trim().isEmpty() ? 0 : Float.parseFloat(txSalario.getText().trim()));
        p.setHoraInicio(txHoraInicio.getText().trim());
        p.setHoraFim(txHoraFim.getText().trim());
        return p;
    }

    private void preencherCampos(ProfissionalModel p) {
        idSelecionado = p.getId();
        txId.setText(String.valueOf(p.getId()));
        txNome.setText(p.getNome()); txCpf.setText(p.getCpf());
        txTel.setText(p.getTel()); txEmail.setText(p.getEmail());
        txTelEmerg.setText(p.getTelEmergencia()); txNomeEmerg.setText(p.getNomeEmergencia());
        txRua.setText(p.getDescRua()); txBairro.setText(p.getDescBairro());
        txNum.setText(String.valueOf(p.getDescNumLocal())); txCep.setText(p.getCep() != null ? p.getCep() : "");
        txCidade.setText(p.getCidade()); txEstado.setText(p.getEstado()); txNacionalidade.setText(p.getNacionalidade());
        chkFarmaceutico.setSelected(p.isFarmaceutico());
        txCrf.setText(p.getCrf() != null ? p.getCrf() : "");
        txCargo.setText(p.getCargo()); txSalario.setText(String.valueOf(p.getSalario()));
        txHoraInicio.setText(p.getHoraInicio()); txHoraFim.setText(p.getHoraFim());
        txCrf.setEditable(p.isFarmaceutico());
    }

    private void limparCampos() {
        txId.setText(""); txNome.setText(""); txCpf.setText(""); txTel.setText(""); txEmail.setText("");
        txTelEmerg.setText(""); txNomeEmerg.setText(""); txRua.setText(""); txBairro.setText("");
        txNum.setText(""); txCep.setText(""); txCidade.setText(""); txEstado.setText("");
        txNacionalidade.setText(""); chkFarmaceutico.setSelected(false); txCrf.setText("");
        txCargo.setText(""); txSalario.setText(""); txHoraInicio.setText(""); txHoraFim.setText("");
        idSelecionado = 0;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (ProfissionalModel p : new ProfissionalController().selecionarTodos()) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getCpf(), p.getCargo(),
                p.isFarmaceutico() ? "Sim" : "Não", p.getCrf(), p.getCidade()
            });
        }
    }

    private void estadoInicial() {
        txId.setEditable(true); setFieldsEditable(false);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
        txCrf.setEditable(false);
    }

    private void estadoNovo() {
        txId.setEditable(false); setFieldsEditable(true);
        btnNovo.setEnabled(false); btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false); btnExcluir.setEnabled(false);
        txCrf.setEditable(chkFarmaceutico.isSelected());
    }

    private void estadoSelecao() {
        txId.setEditable(false); setFieldsEditable(true);
        btnNovo.setEnabled(true); btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true); btnExcluir.setEnabled(true);
        txCrf.setEditable(chkFarmaceutico.isSelected());
    }

    private void setFieldsEditable(boolean v) {
        txNome.setEditable(v); txCpf.setEditable(v); txTel.setEditable(v); txEmail.setEditable(v);
        txTelEmerg.setEditable(v); txNomeEmerg.setEditable(v); txRua.setEditable(v);
        txBairro.setEditable(v); txNum.setEditable(v); txCep.setEditable(v);
        txCidade.setEditable(v); txEstado.setEditable(v); txNacionalidade.setEditable(v);
        chkFarmaceutico.setEnabled(v);
        txCargo.setEditable(v); txSalario.setEditable(v);
        txHoraInicio.setEditable(v); txHoraFim.setEditable(v);
    }
}
