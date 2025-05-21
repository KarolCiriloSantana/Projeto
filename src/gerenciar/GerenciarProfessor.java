package gerenciar;

import entidades.Professor;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class GerenciarProfessor {
    private static List<Professor> professores = new ArrayList<>();

    public static void main(String[] args) {
        cadastrarDadosExemplo();
        
        while (true) {
            String opcao = JOptionPane.showInputDialog(
                "===== Menu de Professores =====\n" +
                "[1] Cadastrar Professor\n" +
                "[2] Remover Professor\n" +
                "[3] Listar Professores\n" +
                "[4] Voltar ao Menu Principal\n" +
                "Digite sua opção:");

            if (opcao == null || opcao.equals("4")) {
                return;
            }

            try {
                switch (opcao) {
                    case "1":
                        cadastrarProfessor();
                        break;
                    case "2":
                        removerProfessor();
                        break;
                    case "3":
                        listarProfessores();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }

    private static void cadastrarProfessor() {
        String nome = JOptionPane.showInputDialog("Digite o nome do professor:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF do professor:");
        String email = JOptionPane.showInputDialog("Digite o e-mail do professor:");
        String dataNasc = JOptionPane.showInputDialog("Digite a data de nascimento (dd/mm/aaaa):");
        String especialidade = JOptionPane.showInputDialog("Digite a especialidade do professor:");

        if (nome == null || cpf == null || email == null || dataNasc == null || especialidade == null) {
            JOptionPane.showMessageDialog(null, "Cadastro cancelado ou dados incompletos!");
            return;
        }

        Professor professor = new Professor(nome, cpf, email, dataNasc, especialidade);
        professores.add(professor);
        JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso!");
    }

    private static void removerProfessor() {
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum professor cadastrado!");
            return;
        }

        String lista = listarProfessoresParaSelecao();
        String indiceStr = JOptionPane.showInputDialog(
            "Professores cadastrados:\n" + lista + "\nDigite o número do professor a remover:");

        if (indiceStr == null) return;

        try {
            int indice = Integer.parseInt(indiceStr) - 1;
            if (indice >= 0 && indice < professores.size()) {
                professores.remove(indice);
                JOptionPane.showMessageDialog(null, "Professor removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Índice inválido!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite um número válido!");
        }
    }

    private static void listarProfessores() {
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum professor cadastrado!");
            return;
        }

        StringBuilder sb = new StringBuilder("===== Professores Cadastrados =====\n");
        for (Professor prof : professores) {
            sb.append("Nome: ").append(prof.getNome())
              .append(" | CPF: ").append(prof.getCpf())
              .append("\nE-mail: ").append(prof.getEmail())
              .append(" | Especialidade: ").append(prof.getEspecialidade())
              .append("\nData Nasc.: ").append(prof.getData_nasc())
              .append("\n--------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static String listarProfessoresParaSelecao() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < professores.size(); i++) {
            sb.append(i + 1).append(". ").append(professores.get(i).getNome())
              .append(" (").append(professores.get(i).getEspecialidade()).append(")\n");
        }
        return sb.toString();
    }

    private static void cadastrarDadosExemplo() {
        // Professores de exemplo
        professores.add(new Professor("Carlos Silva", "111.222.333-44", "carlos@escola.com", "15/03/1975", "Matemática"));
        professores.add(new Professor("Ana Souza", "222.333.444-55", "ana@escola.com", "22/07/1980", "Literatura"));
    }
}