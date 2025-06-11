package entidades;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger; 

public class Matricula {
    private static final AtomicInteger contadorIds = new AtomicInteger(0); 
    
    private final int id;
    private final Aluno aluno;
    private final Turma turma;
    private final LocalDate data;
    private Double nota;
    private Double frequencia;
    private Status status;

    public enum Status {
        CURSANDO, APROVADO, REPROVADO, TRANCADO
    }

    public Matricula(Aluno aluno, Turma turma) {
        if (aluno == null || turma == null) {
            throw new IllegalArgumentException("Aluno e Turma não podem ser nulos");
        }
        
        this.id = contadorIds.incrementAndGet(); 
        this.aluno = aluno;
        this.turma = turma;
        this.data = LocalDate.now();
        this.status = Status.CURSANDO;
        
       
        this.aluno.adicionarMatricula(this);
        this.turma.adicionarMatricula(this);
    }


    public int getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public LocalDate getData() {
        return data;
    }

 
    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        if (nota != null && (nota < 0 || nota > 10)) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        this.nota = nota;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        if (frequencia != null && (frequencia < 0 || frequencia > 100)) {
            throw new IllegalArgumentException("Frequência deve estar entre 0 e 100");
        }
        this.frequencia = frequencia;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        this.status = status;
    }
}