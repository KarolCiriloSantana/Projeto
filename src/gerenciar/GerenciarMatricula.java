package gerenciar;

import entidades.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class GerenciarMatricula {
    private static List<Matricula> matriculas = new ArrayList<>();
    
    
    private static List<Aluno> alunosSimulados = new ArrayList<>();
    private static List<Turma> turmasSimuladas = new ArrayList<>();

    public static void main(String[] args) {
        // Mock de dados para teste (pode ser removido)
        mockDados();
        
        while (true) {
            String res = JOptionPane.showInputDialog(
                "===== Gerenciamento de Matrículas =====\n" +
                "[1] Realizar Matrícula\n" +
                "[2] Cancelar Matrícula\n" +
                "[3] Listar Matrículas Ativas\n" +
                "[4] Sair\n" +
                "Digite sua opção:");

            if (res == null || res.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                break;
            }

            try {
                int escolha = Integer.parseInt(res);

                switch (escolha) {
                    case 1:
                        realizarMatricula();
                        break;
                    case 2:
                        cancelarMatricula();
                        break;
                    case 3:
                        listarMatriculas();
                        break;
                    case 4:
                        return;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida! Use 3-6.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido!");
            }
        }
    }

    private static void realizarMatricula() {
        if (alunosSimulados.isEmpty() || turmasSimuladas.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Cadastre alunos e turmas primeiro nos respectivos gerenciadores!");
            return;
        }

        // Seleção de aluno
        String listaAlunos = "Alunos disponíveis:\n";
        for (Aluno a : alunosSimulados) {
            listaAlunos += a.getMatricula() + " - " + a.getNome() + "\n";
        }
        String matriculaStr = JOptionPane.showInputDialog(listaAlunos + "\nDigite a matrícula do aluno:");
        
        // Seleção de turma
        String listaTurmas = "Turmas disponíveis:\n";
        for (Turma t : turmasSimuladas) {
            listaTurmas += t.getId_turma() + " - " + t.getCurso().getNome() + 
                          " (" + t.getMatriculas().size() + "/" + t.getMax_alunos() + ")\n";
        }
        String turmaStr = JOptionPane.showInputDialog(listaTurmas + "\nDigite o ID da turma:");

        try {
            int matriculaAluno = Integer.parseInt(matriculaStr);
            int idTurma = Integer.parseInt(turmaStr);
            
            Aluno aluno = alunosSimulados.stream()
                .filter(a -> a.getMatricula() == matriculaAluno)
                .findFirst()
                .orElse(null);
                
            Turma turma = turmasSimuladas.stream()
                .filter(t -> t.getId_turma() == idTurma)
                .findFirst()
                .orElse(null);

            // Validações
            if (aluno == null || turma == null) {
                JOptionPane.showMessageDialog(null, "Aluno ou turma não encontrados!");
                return;
            }
            
            if (turma.getMatriculas().size() >= turma.getMax_alunos()) {
                JOptionPane.showMessageDialog(null, "Turma lotada!");
                return;
            }
            
            if (matriculas.stream().anyMatch(m -> 
                m.getAluno().equals(aluno) && 
                m.getTurma().equals(turma) && 
                m.getStatus() == Matricula.Status.CURSANDO)) {
                JOptionPane.showMessageDialog(null, "Aluno já matriculado nesta turma!");
                return;
            }

            // Efetua matrícula
            Matricula nova = new Matricula(aluno, turma);
            matriculas.add(nova);
            JOptionPane.showMessageDialog(null, "Matrícula realizada!\n" +
                "Aluno: " + aluno.getNome() + "\n" +
                "Turma: " + turma.getCurso().getNome());
                
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dados inválidos! Use números.");
        }
    }

    private static void cancelarMatricula() {
        if (matriculas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma matrícula ativa!");
            return;
        }

        String lista = "Matrículas ativas:\n";
        for (Matricula m : matriculas) {
            if (m.getStatus() == Matricula.Status.CURSANDO) {
                lista += "ID: " + m.getId() + " - " + 
                         m.getAluno().getNome() + " | " + 
                         m.getTurma().getCurso().getNome() + "\n";
            }
        }
        
        String idStr = JOptionPane.showInputDialog(lista + "\nDigite o ID da matrícula para cancelar:");
        try {
            int id = Integer.parseInt(idStr);
            
            Matricula matricula = matriculas.stream()
                .filter(m -> m.getId() == id && m.getStatus() == Matricula.Status.CURSANDO)
                .findFirst()
                .orElse(null);
                
            if (matricula != null) {
                matricula.setStatus(Matricula.Status.TRANCADO);
                JOptionPane.showMessageDialog(null, "Matrícula cancelada!");
            } else {
                JOptionPane.showMessageDialog(null, "Matrícula não encontrada!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void listarMatriculas() {
        if (matriculas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma matrícula cadastrada!");
            return;
        }

        StringBuilder ativas = new StringBuilder("=== Matrículas Ativas ===\n");
        StringBuilder historico = new StringBuilder("\n=== Histórico ===\n");
        
        for (Matricula m : matriculas) {
            String info = "ID: " + m.getId() + 
                          " | Aluno: " + m.getAluno().getNome() +
                          " | Turma: " + m.getTurma().getCurso().getNome() +
                          " | Status: " + m.getStatus() + "\n";
            
            if (m.getStatus() == Matricula.Status.CURSANDO) {
                ativas.append(info);
            } else {
                historico.append(info);
            }
        }
        
        JOptionPane.showMessageDialog(null, ativas.toString() + historico.toString());
    }

    // Mock de dados para teste
    private static void mockDados() {

     
        Curso curso1 = new Curso(01, "Programação Java", "Curso de Programação em Java", 180);
        Curso curso2 = new Curso(01, "Programação Python", "Curso de Programação em Python", 160);
       
        Professor prof1 = new Professor("Ana", "11111111", "ana@curso", "01/01/1980", "Python");
        Professor prof2 = new Professor("Bob", "22222222", "bob@curso", "01/05/1985", "Java");
        
        turmasSimuladas.add(new Turma(1, curso1, prof1, 5, 10, "01/08/2023", "20/12/2023"));
        turmasSimuladas.add(new Turma(2, curso2, prof2, 3, 8, "01/08/2023", "20/12/2023"));
        
        alunosSimulados.add(new Aluno("João", "123", "joao@email", "10/05/2000", 1001));
        alunosSimulados.add(new Aluno("Maria", "456", "maria@email", "15/03/2001", 1002));
    }
}