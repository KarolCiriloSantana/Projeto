package entidades;

public abstract class Usuario {

    private String nome;
    private String cpf;
    private String email;
    private String data_nasc;

    public Usuario(String nome, String cpf, String email, String data_nasc) {
 
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.data_nasc = data_nasc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

}