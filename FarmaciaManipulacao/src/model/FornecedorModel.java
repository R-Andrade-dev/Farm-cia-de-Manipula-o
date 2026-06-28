package model;

public class FornecedorModel {
    private int id, descNumLocal;
    private String razaoSocial, nomeFantasia, cidade, estado, pais;
    private String responsavel, telResp, telComercial, emailResp, emailComercial;
    private String descRua, descBairro, cep;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public String getTelResp() { return telResp; }
    public void setTelResp(String telResp) { this.telResp = telResp; }

    public String getTelComercial() { return telComercial; }
    public void setTelComercial(String telComercial) { this.telComercial = telComercial; }

    public String getEmailResp() { return emailResp; }
    public void setEmailResp(String emailResp) { this.emailResp = emailResp; }

    public String getEmailComercial() { return emailComercial; }
    public void setEmailComercial(String emailComercial) { this.emailComercial = emailComercial; }

    public String getDescRua() { return descRua; }
    public void setDescRua(String descRua) { this.descRua = descRua; }

    public String getDescBairro() { return descBairro; }
    public void setDescBairro(String descBairro) { this.descBairro = descBairro; }

    public int getDescNumLocal() { return descNumLocal; }
    public void setDescNumLocal(int descNumLocal) { this.descNumLocal = descNumLocal; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    @Override
    public String toString() { return id + " - " + razaoSocial; }
}
