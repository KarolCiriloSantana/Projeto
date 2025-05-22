package gerenciar;

import entidades.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciarTurma {
    private static List<Curso> cursosDisponiveis = new ArrayList<>();
    private static List<Turma> turmas = new ArrayList<>();
    private static List<Aluno> alunos = new ArrayList<>();
    private static List<Matricula> matriculas = new ArrayList<>();

    public static void main(String[] args) {
       
        cadastrarDadosExemplo();
        
        while (true) {
            String res = JOptionPane.showInputDialog(
                "===== Menu de Turmas =====\n" +
                "[1] Adicionar Turma\n" +
                "[2] Remover Turma\n" +
                "[3] Listar Turmas\n" +
                "[4] Associar Aluno a Turma\n" +
                "[5] Sair"
            );

            if (res == null || res.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return;
            }

            try {
                int escolha = Integer.parseInt(res);

                switch (escolha) {
                    case 1: adicionarTurma(); break;
                    case 2: removerTurma(); break;
                    case 3: listarTurmas(); break;
                    case 4: associarAlunoTurma(); break;
                    case 5: return;
                    default: JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido!");
            }
        }
    }

    private static void adicionarTurma() {
    // Selecionar curso
    String listaCursos = listarCursosParaSelecao();
    String cursoSelecionadoStr = JOptionPane.showInputDialog(
        "Cursos disponíveis:\n" + listaCursos + "\nDigite o número do curso:");

    if (cursoSelecionadoStr == null) return;
    
    try {
        int cursoIndex = Integer.parseInt(cursoSelecionadoStr) - 1;
        if (cursoIndex < 0 || cursoIndex >= cursosDisponiveis.size()) {
            JOptionPane.showMessageDialog(null, "Índice de curso inválido!");
            return;
        }

        Curso cursoSelecionado = cursosDisponiveis.get(cursoIndex);

       
        String idStr = JOptionPane.showInputDialog("Digite o ID da turma:");
        if (idStr == null) return;

        String nomeProfessor = JOptionPane.showInputDialog("Digite o nome do professor:");
        if (nomeProfessor == null || nomeProfessor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome do professor é obrigatório!");
            return;
        }
        
        String minalunostr = JOptionPane.showInputDialog("Digite a quantidade mínima de alunos:");
        String maxalunostr = JOptionPane.showInputDialog("Digite a quantidade máxima de alunos:");
        String datain = JOptionPane.showInputDialog("Digite a data de inicio (dd/mm/aaaa):");
        String datafim = JOptionPane.showInputDialog("Digite a data de fim (dd/mm/aaaa):");

        try {
            int id = Integer.parseInt(idStr);
            int minaluno = Integer.parseInt(minalunostr);
            int maxaluno = Integer.parseInt(maxalunostr);
    
            // Validações de turma (mín/max alunos)
            if (minaluno <= 0 || maxaluno <= 0 || minaluno > maxaluno) {
                JOptionPane.showMessageDialog(null, "Quantidade de alunos inválida!");
                return;
            }
    
            // Coletar TODOS os dados do professor (necessários para o construtor)
            String cpfProfessor = JOptionPane.showInputDialog("Digite o CPF do professor:");
            String emailProfessor = JOptionPane.showInputDialog("Digite o e-mail do professor:");
            String dataNascProfessor = JOptionPane.showInputDialog("Digite a data de nascimento (dd/mm/aaaa):");
            String especialidadeProfessor = JOptionPane.showInputDialog("Digite a especialidade do professor:");
    
             // Validar se os dados não são nulos/vazios
            if (cpfProfessor == null || emailProfessor == null || dataNascProfessor == null || especialidadeProfessor == null) {
                JOptionPane.showMessageDialog(null, "Dados do professor incompletos!");
                return;
            }
    
            // Agora sim, criar o Professor corretamente
            Professor professor = new Professor(
                nomeProfessor,         // nome (já coletado anteriormente)
                cpfProfessor,          // CPF
                emailProfessor,        // e-mail
                dataNascProfessor,    // data de nascimento
                especialidadeProfessor // especialidade
            );
    
            
            // Criar a turma com o professor
            Turma novaTurma = new Turma(id, cursoSelecionado, professor, minaluno, maxaluno, datain, datafim);
            turmas.add(novaTurma);
            JOptionPane.showMessageDialog(null, "Turma adicionada com sucesso!");
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valores numéricos inválidos!");
        }
    } catch (NumberFormatException e) { // Adicionei esta linha para fechar o try externo
        JOptionPane.showMessageDialog(null, "Número de curso inválido!");
    }
} 

    private static void removerTurma() {
        if (turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma turma cadastrada!");
            return;
        }

        String listaTurmas = listarTurmasParaSelecao();
        String indiceRemoverStr = JOptionPane.showInputDialog(
            "Turmas disponíveis:\n" + listaTurmas + "\nDigite o número da turma a remover:");

        if (indiceRemoverStr == null) return;

        try {
            int indiceRemover = Integer.parseInt(indiceRemoverStr) - 1;
            if (indiceRemover >= 0 && indiceRemover < turmas.size()) {
                // Remover matrículas associadas
                Turma turma = turmas.get(indiceRemover);
                matriculas.removeIf(m -> m.getTurma().equals(turma));
                
                turmas.remove(indiceRemover);
                JOptionPane.showMessageDialog(null, "Turma removida com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Índice inválido!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite um número válido!");
        }
    }

    private static void listarTurmas() {
        if (turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma turma cadastrada!");
            return;
        }

        StringBuilder sb = new StringBuilder("===== Turmas Cadastradas =====\n");
        for (Turma turma : turmas) {
            sb.append("ID: ").append(turma.getId_turma())
              .append(" | Curso: ").append(turma.getCurso().getNome())
              .append(" | Professor: ").append(turma.getProfessor().getNome())
              .append("\nAlunos: ").append(turma.getAlunos()).append("/").append(turma.getMax_alunos())
              .append(" | Período: ").append(turma.getData_inicio()).append(" a ").append(turma.getData_fim())
              .append("\n--------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void associarAlunoTurma() {
        if (turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma turma cadastrada!");
            return;
        }

        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno cadastrado!");
            return;
        }

        // Selecionar turma
        String listaTurmas = listarTurmasParaSelecao();
        String turmaSelecionadaStr = JOptionPane.showInputDialog(
            "Turmas disponíveis:\n" + listaTurmas + "\nDigite o número da turma:");

        if (turmaSelecionadaStr == null) return;

        try {
            int turmaIndex = Integer.parseInt(turmaSelecionadaStr) - 1;
            if (turmaIndex < 0 || turmaIndex >= turmas.size()) {
                JOptionPane.showMessageDialog(null, "Índice de turma inválido!");
                return;
            }

            Turma turma = turmas.get(turmaIndex);

            // Selecionar aluno
            String listaAlunos = listarAlunosParaSelecao();
            String alunoSelecionadoStr = JOptionPane.showInputDialog(
                "Alunos disponíveis:\n" + listaAlunos + "\nDigite o número do aluno:");

            if (alunoSelecionadoStr == null) return;

            try {
                int alunoIndex = Integer.parseInt(alunoSelecionadoStr) - 1;
                if (alunoIndex < 0 || alunoIndex >= alunos.size()) {
                    JOptionPane.showMessageDialog(null, "Índice de aluno inválido!");
                    return;
                }

                Aluno aluno = alunos.get(alunoIndex);

                // Verificar se já está matriculado
                if (matriculas.stream().anyMatch(m -> 
                    m.getAluno().equals(aluno) && m.getTurma().equals(turma))) {
                    JOptionPane.showMessageDialog(null, "Aluno já matriculado nesta turma!");
                    return;
                }

                // Verificar se há vagas
                if (turma.getAlunos() >= turma.getMax_alunos()) {
                    JOptionPane.showMessageDialog(null, "Turma já está com capacidade máxima!");
                    return;
                }

                // Criar matrícula
                Matricula matricula = new Matricula(aluno, turma);
                matriculas.add(matricula);
                turma.adicionarMatricula(matricula);
                
                JOptionPane.showMessageDialog(null, 
                    "Aluno " + aluno.getNome() + " matriculado com sucesso na turma " + 
                    turma.getId_turma() + " - " + turma.getCurso().getNome() +
                    "\nVagas restantes: " + (turma.getMax_alunos() - turma.getAlunos()));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número de aluno inválido!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de turma inválido!");
        }
    }

    // Métodos auxiliares
    private static String listarCursosParaSelecao() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cursosDisponiveis.size(); i++) {
            sb.append(i + 1).append(". ").append(cursosDisponiveis.get(i).getNome()).append("\n");
        }
        return sb.toString();
    }

    private static String listarTurmasParaSelecao() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < turmas.size(); i++) {
            Turma turma = turmas.get(i);
            sb.append(i + 1).append(". ID: ").append(turma.getId_turma())
              .append(" | ").append(turma.getCurso().getNome())
              .append(" | Prof: ").append(turma.getProfessor().getNome())
              .append(" | Vagas: ").append(turma.getAlunos()).append("/").append(turma.getMax_alunos())
              .append("\n");
        }
        return sb.toString();
    }

    private static String listarAlunosParaSelecao() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alunos.size(); i++) {
            sb.append(i + 1).append(". ").append(alunos.get(i).getNome()).append("\n");
        }
        return sb.toString();
    }

    private static void cadastrarDadosExemplo() {
        // Cursos de exemplo
        cursosDisponiveis.add(new Curso(1, "Programação Orientada a Objetos", "Curso de Java", 60));
        cursosDisponiveis.add(new Curso(2, "Banco de Dados", "Fundamentos de SQL", 40));
        
        // Alunos de exemplo
        alunos.add(new Aluno("João Silva", "111.111.111-11", "joao@email.com", "01/01/2000", 1001));
        alunos.add(new Aluno("Maria Santos", "222.222.222-22", "maria@email.com", "02/02/2001", 1002));
  
        }
}