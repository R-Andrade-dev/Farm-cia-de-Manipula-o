package model;

public class PedidoModel {
    private int id, idCliente, idProfissional;
    private String dataHora, medicoSolicitante, crmSolicitante;
    private float valorTotal;
    private String tipoPagamento, observacao, status;
    private String nomeCliente, nomeProfissional;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdProfissional() { return idProfissional; }
    public void setIdProfissional(int idProfissional) { this.idProfissional = idProfissional; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getMedicoSolicitante() { return medicoSolicitante; }
    public void setMedicoSolicitante(String medicoSolicitante) { this.medicoSolicitante = medicoSolicitante; }

    public String getCrmSolicitante() { return crmSolicitante; }
    public void setCrmSolicitante(String crmSolicitante) { this.crmSolicitante = crmSolicitante; }

    public float getValorTotal() { return valorTotal; }
    public void setValorTotal(float valorTotal) { this.valorTotal = valorTotal; }

    public String getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getNomeProfissional() { return nomeProfissional; }
    public void setNomeProfissional(String nomeProfissional) { this.nomeProfissional = nomeProfissional; }
}
