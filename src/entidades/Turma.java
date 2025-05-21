package entidades;

import java.util.ArrayList;
import java.util.List;

public class Turma {

    private Curso curso;
    private Professor professor;
    private int id_turma;
    private int min_alunos;
    private int max_alunos;
    private String data_inicio;
    private String data_fim;
    private List<Matricula> matriculas = new ArrayList<>();

  

    public Turma(int id_turma, Curso curso, Professor professor, int min_alunos, int max_alunos, String data_inicio, String data_fim){

    this.id_turma = id_turma;
    this.curso = curso;
    this.professor =  professor;
    this.min_alunos = min_alunos;
    this.max_alunos = max_alunos;
    this.data_inicio = data_inicio;
    this.data_fim = data_fim;
    }
    

    public void adicionarMatricula(Matricula matricula) {
        if (matriculas.size() < max_alunos) {
            matriculas.add(matricula);
        }
    }

      public int getAlunos() {
        return matriculas.size();
    }

    public List<Matricula> getMatriculas() {
        return new ArrayList<>(matriculas);
    }

    public Curso getCurso() {
        return curso;
    }
    
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public int getId_turma() {
        return id_turma;
    }


    public void setId_turma(int id_turma) {
        this.id_turma = id_turma;
    }


    public int getMin_alunos() {
        return min_alunos;
    }


    public void setMin_alunos(int min_alunos) {
        this.min_alunos = min_alunos;
    }


    public int getMax_alunos() {
        return max_alunos;
    }


    public void setMax_alunos(int max_alunos) {
        this.max_alunos = max_alunos;
    }


    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }


    
}
 
    

