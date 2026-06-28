package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Principal extends JFrame {

    private static final Logger logger = Logger.getLogger(Principal.class.getName());
    private JDesktopPane areaTrabalho;

    public Principal() {
        initComponents();
    }

    private void initComponents() {
        areaTrabalho = new JDesktopPane();
        areaTrabalho.setBackground(new java.awt.Color(230, 240, 255));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("RTH - Farmácia de Manipulação");
        setSize(1100, 750);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // ---- Menu Cadastros ----
        JMenu menuCadastros = new JMenu("Cadastros");

        JMenuItem miCliente = new JMenuItem("Clientes");
        miCliente.addActionListener(e -> abrirJanela(new ClienteView()));
        menuCadastros.add(miCliente);

        JMenuItem miProfissional = new JMenuItem("Profissionais");
        miProfissional.addActionListener(e -> abrirJanela(new ProfissionalView()));
        menuCadastros.add(miProfissional);

        JMenuItem miFornecedor = new JMenuItem("Fornecedores");
        miFornecedor.addActionListener(e -> abrirJanela(new FornecedorView()));
        menuCadastros.add(miFornecedor);

        menuCadastros.addSeparator();

        JMenuItem miInsumo = new JMenuItem("Insumos / Materiais");
        miInsumo.addActionListener(e -> abrirJanela(new InsumoMaterialView()));
        menuCadastros.add(miInsumo);

        JMenuItem miLote = new JMenuItem("Lotes de Insumos");
        miLote.addActionListener(e -> abrirJanela(new LoteInsumoView()));
        menuCadastros.add(miLote);

        menuCadastros.addSeparator();

        JMenuItem miFormula = new JMenuItem("Fórmulas Manipuladas");
        miFormula.addActionListener(e -> abrirJanela(new FormulaManipuladaView()));
        menuCadastros.add(miFormula);

        JMenuItem miPedido = new JMenuItem("Pedidos");
        miPedido.addActionListener(e -> abrirJanela(new PedidoView()));
        menuCadastros.add(miPedido);

        menuBar.add(menuCadastros);

        // ---- Menu Relatórios ----
        JMenu menuRelatorios = new JMenu("Relatórios");

        JMenuItem miRelPedidos = new JMenuItem("Pedidos Completos");
        miRelPedidos.addActionListener(e -> abrirJanela(new RelatorioView("pedidos_completos")));
        menuRelatorios.add(miRelPedidos);

        JMenuItem miRelLotes = new JMenuItem("Lotes a Vencer (60 dias)");
        miRelLotes.addActionListener(e -> abrirJanela(new RelatorioView("lotes_vencimento")));
        menuRelatorios.add(miRelLotes);

        JMenuItem miRelFormula = new JMenuItem("Composição de Fórmulas");
        miRelFormula.addActionListener(e -> abrirJanela(new RelatorioView("formula_composicao")));
        menuRelatorios.add(miRelFormula);

        JMenuItem miRelPedidosCidade = new JMenuItem("Pedidos por Cidade");
        miRelPedidosCidade.addActionListener(e -> abrirJanela(new RelatorioView("pedidos_cidade")));
        menuRelatorios.add(miRelPedidosCidade);

        menuBar.add(menuRelatorios);

        setJMenuBar(menuBar);
        getContentPane().add(areaTrabalho);
    }

    private void abrirJanela(JInternalFrame janela) {
        for (JInternalFrame aberta : areaTrabalho.getAllFrames()) {
            aberta.dispose();
        }
        janela.setVisible(true);
        areaTrabalho.add(janela);
        try {
            janela.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Principal().setVisible(true));
    }
}
