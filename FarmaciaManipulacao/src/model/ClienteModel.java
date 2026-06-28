package model;

public class ClienteModel {
    private int id;
    private String nome, cpf, tel1, tel2, email;
    private String descRua, descBairro, cep, cidade, estado, nacionalidade;
    private int descNumLocal;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTel1() { return tel1; }
    public void setTel1(String tel1) { this.tel1 = tel1; }

    public String getTel2() { return tel2; }
    public void setTel2(String tel2) { this.tel2 = tel2; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescRua() { return descRua; }
    public void setDescRua(String descRua) { this.descRua = descRua; }

    public String getDescBairro() { return descBairro; }
    public void setDescBairro(String descBairro) { this.descBairro = descBairro; }

    public int getDescNumLocal() { return descNumLocal; }
    public void setDescNumLocal(int descNumLocal) { this.descNumLocal = descNumLocal; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    @Override
    public String toString() { return id + " - " + nome; }
}
