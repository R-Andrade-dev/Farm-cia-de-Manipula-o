package model;

public class FormulaPedidoModel {
    private int idPedido, idFormula, quantidade;
    private float preco;
    private String nomeFormula;

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdFormula() { return idFormula; }
    public void setIdFormula(int idFormula) { this.idFormula = idFormula; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public String getNomeFormula() { return nomeFormula; }
    public void setNomeFormula(String nomeFormula) { this.nomeFormula = nomeFormula; }
}
