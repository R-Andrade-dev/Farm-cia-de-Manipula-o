package model;

public class FormulaManipuladaModel {
    private int id, idProfissional;
    private String nome, posologia, observacao, fStatus;
    private float preco;
    private String nomeProfissional;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProfissional() { return idProfissional; }
    public void setIdProfissional(int idProfissional) { this.idProfissional = idProfissional; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPosologia() { return posologia; }
    public void setPosologia(String posologia) { this.posologia = posologia; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getfStatus() { return fStatus; }
    public void setfStatus(String fStatus) { this.fStatus = fStatus; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public String getNomeProfissional() { return nomeProfissional; }
    public void setNomeProfissional(String nomeProfissional) { this.nomeProfissional = nomeProfissional; }

    @Override
    public String toString() { return id + " - " + nome; }
}
