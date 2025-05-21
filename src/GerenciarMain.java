import entidades.Aluno;  // Importa a classe Aluno
import interfaces.IGerenciarEntidades;
import entidades.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciarMain implements IGerenciarEntidades{
    private static List<Curso> cursos = new ArrayList<>();
    private static List<Professor> professores = new ArrayList<>();
    private static List<Aluno> alunos = new ArrayList<>();
    private static List<Turma> turmas = new ArrayList<>();
    private static List<Matricula> matriculas = new ArrayList<>();

    public static void main(String[] args) {
        initDadosExemplo();
        
        while (true) {
            String opcao = JOptionPane.showInputDialog(
                "===== MENU PRINCIPAL =====\n" +
                "[1] Gerenciar Turmas\n" +
                "[2] Gerenciar Alunos\n" +
                "[3] Matricular Aluno\n" +
                "[4] Sair\n" +
                "Digite sua opção:");

            switch (opcao) {
                case "1": gerenciarTurmas(); break;
                case "2": gerenciarAlunos(); break;
                case "3": matricularAluno(); break;
                case "4": System.exit(0);
                default: JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    private static void matricularAluno() {
        if (alunos.isEmpty() || turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cadastre alunos e turmas primeiro!");
            return;
        }

        // Selecionar aluno
        Aluno aluno = selecionarAluno();
        if (aluno == null) return;

        // Selecionar turma
        Turma turma = selecionarTurma();
        if (turma == null) return;

        // Verificar se já está matriculado
        if (matriculas.stream().anyMatch(m -> 
            m.getAluno().equals(aluno) && m.getTurma().equals(turma))) {
            JOptionPane.showMessageDialog(null, "Aluno já matriculado nesta turma!");
            return;
        }

        // Criar matrícula
        Matricula matricula = new Matricula(aluno, turma);
        turma.adicionarMatricula(matricula);
        matriculas.add(matricula);

        JOptionPane.showMessageDialog(null, 
            "Matrícula realizada com sucesso!\n" +
            "Aluno: " + aluno.getNome() + "\n" +
            "Turma: " + turma.getCurso().getNome());
    }

    // Métodos auxiliares (selecionarAluno(), selecionarTurma(), etc.)
    private static Aluno selecionarAluno() {
        StringBuilder sb = new StringBuilder("ALUNOS DISPONÍVEIS:\n");
        for (int i = 0; i < alunos.size(); i++) {
            sb.append(i+1).append(". ").append(alunos.get(i).getNome()).append("\n");
        }
        
        String escolha = JOptionPane.showInputDialog(sb.toString() + "Digite o número do aluno:");
        try {
            int index = Integer.parseInt(escolha) - 1;
            return alunos.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    private static void initDadosExemplo() {
        // Inicializar com dados de exemplo
        cursos.add(new Curso(1, "POO", "Programação Orientada a Objetos", 60));
        professores.add(new Professor(1, "João Silva"));
        alunos.add(new Aluno("Maria", "111.111.111-11", "maria@email.com", "01/01/2000", 1001));
    }
}

