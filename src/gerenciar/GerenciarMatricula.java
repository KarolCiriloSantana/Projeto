package gerenciar;

import entidades.Matricula;
import entidades.Aluno;
import entidades.Turma;
import gerenciar.interfaces.IGerenciador;
import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GerenciarMatricula implements IGerenciador {
    private final List<Matricula> matriculas = new ArrayList<>();
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
                "[5] Consultar Matrícula\n" +
                "[6] Listar Todas Matrículas\n" +
                "[7] Voltar\n\n" +
                "Digite sua opção:");

            switch(opcao) {
                case "1" -> realizarMatricula();
                case "2" -> lancarNota();
                case "3" -> registrarFrequencia();
                case "4" -> alterarStatus();
                case "5" -> consultarMatricula();
                case "6" -> listarMatriculas();
                case "7" -> JOptionPane.showMessageDialog(null, "Retornando...");
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        } while(!opcao.equals("7"));
    }

    private void realizarMatricula() {
        try {
            Optional<Aluno> alunoOpt = selecionarAluno();
            if (alunoOpt.isEmpty()) return;

            Optional<Turma> turmaOpt = selecionarTurma();
            if (turmaOpt.isEmpty()) return;

            Aluno aluno = alunoOpt.get();
            Turma turma = turmaOpt.get();

            // Verifica se aluno já está matriculado na turma
            if (alunoJaMatriculado(aluno, turma)) {
                JOptionPane.showMessageDialog(null, "Este aluno já está matriculado nesta turma!");
                return;
            }

            Matricula novaMatricula = new Matricula(aluno, turma);
            matriculas.add(novaMatricula);
            
            JOptionPane.showMessageDialog(null, 
                "✅ Matrícula realizada com sucesso!\n" +
                "ID: " + novaMatricula.getId() + "\n" +
                "Aluno: " + aluno.getNome() + "\n" +
                "Turma: " + turma.getCodigo() + "\n" +
                "Data: " + novaMatricula.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Erro: " + e.getMessage());
        }
    }

    private boolean alunoJaMatriculado(Aluno aluno, Turma turma) {
        return matriculas.stream()
            .anyMatch(m -> m.getAluno().equals(aluno) && m.getTurma().equals(turma));
    }

    private void lancarNota() {
        Optional<Matricula> matriculaOpt = selecionarMatricula();
        if (matriculaOpt.isEmpty()) return;

        Matricula matricula = matriculaOpt.get();
        String notaStr = JOptionPane.showInputDialog(
            "Digite a nota (0-10) para:\n" +
            "Aluno: " + matricula.getAluno().getNome() + "\n" +
            "Turma: " + matricula.getTurma().getCodigo());

        try {
            double nota = Double.parseDouble(notaStr);
            matricula.setNota(nota);
            JOptionPane.showMessageDialog(null, "Nota lançada com sucesso!");
            
            // Verifica se aluno foi aprovado
            if (nota >= 6 && matricula.getFrequencia() != null && matricula.getFrequencia() >= 75) {
                matricula.setStatus(Matricula.Status.APROVADO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void registrarFrequencia() {
        Optional<Matricula> matriculaOpt = selecionarMatricula();
        if (matriculaOpt.isEmpty()) return;

        Matricula matricula = matriculaOpt.get();
        String freqStr = JOptionPane.showInputDialog(
            "Digite a frequência (0-100%) para:\n" +
            "Aluno: " + matricula.getAluno().getNome() + "\n" +
            "Turma: " + matricula.getTurma().getCodigo());

        try {
            double frequencia = Double.parseDouble(freqStr);
            matricula.setFrequencia(frequencia);
            JOptionPane.showMessageDialog(null, "Frequência registrada com sucesso!");
            
            // Verifica se aluno foi reprovado por falta
            if (frequencia < 75 && matricula.getNota() != null && matricula.getNota() < 6) {
                matricula.setStatus(Matricula.Status.REPROVADO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void alterarStatus() {
        Optional<Matricula> matriculaOpt = selecionarMatricula();
        if (matriculaOpt.isEmpty()) return;

        Matricula matricula = matriculaOpt.get();
        String statusStr = JOptionPane.showInputDialog(
            "Selecione o novo status para:\n" +
            "Aluno: " + matricula.getAluno().getNome() + "\n" +
            "Turma: " + matricula.getTurma().getCodigo() + "\n\n" +
            "[1] Cursando\n" +
            "[2] Aprovado\n" +
            "[3] Reprovado\n" +
            "[4] Trancado");

        try {
            Matricula.Status novoStatus = switch(statusStr) {
                case "1" -> Matricula.Status.CURSANDO;
                case "2" -> Matricula.Status.APROVADO;
                case "3" -> Matricula.Status.REPROVADO;
                case "4" -> Matricula.Status.TRANCADO;
                default -> throw new IllegalArgumentException("Opção inválida");
            };
            
            matricula.setStatus(novoStatus);
            JOptionPane.showMessageDialog(null, "Status atualizado para: " + novoStatus);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void consultarMatricula() {
        Optional<Matricula> matriculaOpt = selecionarMatricula();
        if (matriculaOpt.isEmpty()) return;

        Matricula m = matriculaOpt.get();
        String info = String.format(
            "=== DETALHES DA MATRÍCULA ===\n" +
            "ID: %d\n" +
            "Aluno: %s (%s)\n" +
            "Turma: %s\n" +
            "Data: %s\n" +
            "Status: %s\n" +
            "Nota: %s\n" +
            "Frequência: %s%%\n",
            m.getId(),
            m.getAluno().getNome(), m.getAluno().getCpf(),
            m.getTurma().getCodigo(),
            m.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            m.getStatus(),
            m.getNota() != null ? String.format("%.1f", m.getNota()) : "N/A",
            m.getFrequencia() != null ? String.format("%.1f", m.getFrequencia()) : "N/A");

        JOptionPane.showMessageDialog(null, info);
    }

    private void listarMatriculas() {
        if (matriculas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma matrícula cadastrada.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== LISTA DE MATRÍCULAS ===\n");
        for (Matricula m : matriculas) {
            sb.append(String.format(
                "ID: %d | Aluno: %s | Turma: %s\n" +
                "Status: %s | Nota: %s | Freq: %s%%\n" +
                "--------------------------------\n",
                m.getId(),
                m.getAluno().getNome(),
                m.getTurma().getCodigo(),
                m.getStatus(),
                m.getNota() != null ? String.format("%.1f", m.getNota()) : "N/A",
                m.getFrequencia() != null ? String.format("%.1f", m.getFrequencia()) : "N/A"));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private Optional<Aluno> selecionarAluno() {
        List<Aluno> alunosDisponiveis = gerenciarAluno.listarAlunosAtivos();
        if (alunosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno disponível para matrícula!");
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder("Selecione o aluno:\n");
        for (int i = 0; i < alunosDisponiveis.size(); i++) {
            Aluno a = alunosDisponiveis.get(i);
            sb.append(String.format("[%d] %s (%s)\n", i+1, a.getNome(), a.getCpf()));
        }

        String escolha = JOptionPane.showInputDialog(sb.toString());
        try {
            int index = Integer.parseInt(escolha) - 1;
            return Optional.of(alunosDisponiveis.get(index));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleção inválida!");
            return Optional.empty();
        }
    }

    private Optional<Turma> selecionarTurma() {
        List<Turma> turmasDisponiveis = gerenciarTurma.listarTurmasAtivas();
        if (turmasDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma turma disponível para matrícula!");
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder("Selecione a turma:\n");
        for (int i = 0; i < turmasDisponiveis.size(); i++) {
            Turma t = turmasDisponiveis.get(i);
            sb.append(String.format("[%d] %s - %s (Vagas: %d)\n", 
                i+1, t.getCodigo(), t.getDisciplina(), t.getVagasDisponiveis()));
        }

        String escolha = JOptionPane.showInputDialog(sb.toString());
        try {
            int index = Integer.parseInt(escolha) - 1;
            return Optional.of(turmasDisponiveis.get(index));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleção inválida!");
            return Optional.empty();
        }
    }

    private Optional<Matricula> selecionarMatricula() {
        if (matriculas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma matrícula cadastrada!");
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder("Selecione a matrícula:\n");
        for (int i = 0; i < matriculas.size(); i++) {
            Matricula m = matriculas.get(i);
            sb.append(String.format("[%d] %s - %s (Status: %s)\n", 
                i+1, m.getAluno().getNome(), m.getTurma().getCodigo(), m.getStatus()));
        }

        String escolha = JOptionPane.showInputDialog(sb.toString());
        try {
            int index = Integer.parseInt(escolha) - 1;
            return Optional.of(matriculas.get(index));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleção inválida!");
            return Optional.empty();
        }
    }
}