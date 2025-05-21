package entidades;

public class Professor extends Usuario {
    private String especialidade;

    public Professor(String nome, String cpf, String email, String data_nasc, String especialidade) {
        super(nome, cpf, email, data_nasc);
        this.especialidade = especialidade;

    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}