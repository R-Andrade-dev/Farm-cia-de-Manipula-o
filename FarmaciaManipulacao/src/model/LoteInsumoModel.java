package model;

public class LoteInsumoModel {
    private int id, idInsumo, idFornecedor, quantidade;
    private String dtFabricacao, dtValidade;
    private String nomeInsumo, nomeForneecedor;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }

    public int getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getDtFabricacao() { return dtFabricacao; }
    public void setDtFabricacao(String dtFabricacao) { this.dtFabricacao = dtFabricacao; }

    public String getDtValidade() { return dtValidade; }
    public void setDtValidade(String dtValidade) { this.dtValidade = dtValidade; }

    public String getNomeInsumo() { return nomeInsumo; }
    public void setNomeInsumo(String nomeInsumo) { this.nomeInsumo = nomeInsumo; }

    public String getNomeFornecedor() { return nomeForneecedor; }
    public void setNomeFornecedor(String nomeFornecedor) { this.nomeForneecedor = nomeFornecedor; }

    @Override
    public String toString() { return id + " - " + nomeInsumo; }
}
