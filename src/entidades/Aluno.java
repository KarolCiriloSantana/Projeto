package entidades;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private int matricula;
    private List<Matricula> matriculas = new ArrayList<>();

    public Aluno( String nome, String cpf, String email, String dataNasc, int matricula) {
        super(nome, cpf, email, dataNasc);
        this.matricula = matricula;
    }

    public void adicionarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
    }


    public List<Matricula> getMatriculas() {
        return new ArrayList<>(matriculas);
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

}