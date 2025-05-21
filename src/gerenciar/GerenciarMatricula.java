package gerenciar;

import entidades.Matricula;
import entidades.Aluno;
import entidades.Turma;
import gerenciar.interfaces.IGerenciador;
import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class GerenciarMatricula implements IGerenciador {
    private final GerenciarAluno gerenciarAluno;
    private final GerenciarTurma gerenciarTurma;

    public GerenciarMatricula(GerenciarAluno gerenciarAluno, GerenciarTurma gerenciarTurma) {
        this.gerenciarAluno = gerenciarAluno;
        this.gerenciarTurma = gerenciarTurma;
    }

    @Override
    public void exibirMenu() {
        String opcao;
        do {
            opcao = JOptionPane.showInputDialog(
                "===== MENU MATRÍCULAS =====\n" +
                "[1] Realizar Matrícula\n" +
                "[2] Lançar Nota\n" +
                "[3] Registrar Frequência\n" +
                "[4] Alterar Status\n" +
                "[5] Listar Matrículas\n" +
                "[6] Voltar\n\n" +
                "Digite sua opção:");

            switch(opcao) {
                case "1" -> realizarMatricula();
                case "2" -> lancarNota();
                case "3" -> registrarFrequencia();
                case "4" -> alterarStatus();
                case "5" -> listarMatriculas();
                case "6" -> JOptionPane.showMessageDialog(null, "Retornando...");
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while(!opcao.equals("6"));
    }

    private void realizarMatricula() {
        try {
            Optional<Aluno> alunoOpt = selecionarAluno();
            if (alunoOpt.isEmpty()) return;

            Optional<Turma> turmaOpt = selecionarTurma();
            if (turmaOpt.isEmpty()) return;

            Aluno aluno = alunoOpt.get();
            Turma turma = turmaOpt.get();

            Matricula novaMatricula = new Matricula(aluno, turma);
            aluno.adicionarMatricula(novaMatricula);
            turma.adicionarMatricula(novaMatricula);
            
            JOptionPane.showMessageDialog(null, 
                "Matrícula realizada com sucesso!\n" +
                "ID: " + novaMatricula.getId() + "\n" +
                "Aluno: " + aluno.getNome() + "\n" +
                "Turma: " + turma.getCodigo());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private Optional<Aluno> selecionarAluno() {
        List<Aluno> alunos = gerenciarAluno.listarAlunosDisponiveis();
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno disponível para matrícula!");
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder("Selecione o aluno:\n");
        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);
            sb.append("[").append(i + 1).append("] ")
              .append(a.getNome()).append(" - ").append(a.getCpf())
              .append("\n");
        }

        String escolha = JOptionPane.showInputDialog(sb.toString());
        try {
            int index = Integer.parseInt(escolha) - 1;
            return Optional.of(alunos.get(index));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleção inválida!");
            return Optional.empty();
        }
    }

    private Optional<Turma> selecionarTurma() {
        List<Turma> turmas = gerenciarTurma.listarTurmasDisponiveis();
        if (turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma turma disponível para matrícula!");
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder("Selecione a turma:\n");
        for (int i = 0; i < turmas.size(); i++) {
            Turma t = turmas.get(i);
            sb.append("[").append(i + 1).append("] ")
              .append(t.getCodigo()).append(" - ").append(t.getDisciplina())
              .append("\n");
        }

        String escolha = JOptionPane.showInputDialog(sb.toString());
        try {
            int index = Integer.parseInt(escolha) - 1;
            return Optional.of(turmas.get(index));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleção inválida!");
            return Optional.empty();
        }
    }

    // ... (outros métodos mantidos conforme a versão anterior)
}