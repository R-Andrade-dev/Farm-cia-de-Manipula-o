package model;

public class ProfissionalModel {
    private int id, descNumLocal;
    private String nome, cpf, tel, email, telEmergencia, nomeEmergencia;
    private String descRua, descBairro, cep, cidade, estado, nacionalidade;
    private boolean farmaceutico;
    private String crf, cargo;
    private float salario;
    private String horaInicio, horaFim;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelEmergencia() { return telEmergencia; }
    public void setTelEmergencia(String telEmergencia) { this.telEmergencia = telEmergencia; }

    public String getNomeEmergencia() { return nomeEmergencia; }
    public void setNomeEmergencia(String nomeEmergencia) { this.nomeEmergencia = nomeEmergencia; }

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

    public boolean isFarmaceutico() { return farmaceutico; }
    public void setFarmaceutico(boolean farmaceutico) { this.farmaceutico = farmaceutico; }

    public String getCrf() { return crf; }
    public void setCrf(String crf) { this.crf = crf; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public float getSalario() { return salario; }
    public void setSalario(float salario) { this.salario = salario; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFim() { return horaFim; }
    public void setHoraFim(String horaFim) { this.horaFim = horaFim; }

    @Override
    public String toString() { return id + " - " + nome; }
}
