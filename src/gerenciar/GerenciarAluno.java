package gerenciar;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import entidades.Aluno;

public class GerenciarAluno{
    private static List<Aluno> alunos = new ArrayList<>();
    

    public static void main(String[] args) {
        while (true) {
            String res = JOptionPane.showInputDialog(
                "===== Menu de Alunos =====\n" +
                "[1] Cadastrar Aluno\n" +
                "[2] Remover Aluno\n" +
                "[3] Listar Alunos\n" +
                "[4] Buscar Aluno por CPF\n" +
                "[5] Sair\n" +
                "Digite sua opção:");

            if (res == null || res.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                break;
            }

            try {
                int escolha = Integer.parseInt(res);

                switch (escolha) {
                    case 1:
                        cadastrarAluno();
                        break;
                    case 2:
                        removerAluno();
                        break;
                    case 3:
                        listarAlunos();
                        break;
                    case 4:
                        buscarAlunoPorCpf();
                        break;
                    case 5:
                        return;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido!");
            }
        }
    }

    private static void cadastrarAluno() {
        String nome = JOptionPane.showInputDialog("Digite o nome do aluno:");
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome é obrigatório!");
            return;
        }

        String cpf = JOptionPane.showInputDialog("Digite o CPF do aluno:");
        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CPF é obrigatório!");
            return;
        }

        // Verifica se CPF já existe
        if (alunos.stream().anyMatch(a -> a.getCpf().equals(cpf))) {
            JOptionPane.showMessageDialog(null, "Aluno com este CPF já cadastrado!");
            return;
        }

        String email = JOptionPane.showInputDialog("Digite o e-mail do aluno:");
        String dataNasc = JOptionPane.showInputDialog("Digite a data de nascimento (dd/mm/aaaa):");
        
        String matriculaStr = JOptionPane.showInputDialog("Digite o número de matrícula:");
        try {
            int matricula = Integer.parseInt(matriculaStr);
            
            Aluno novoAluno = new Aluno(nome, cpf, email, dataNasc, matricula);
            alunos.add(novoAluno);
            
            JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!\nMatrícula: " + matricula);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Matrícula deve ser um número válido!");
        }
    }

    private static void removerAluno() {
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno cadastrado!");
            return;
        }

        String cpf = JOptionPane.showInputDialog("Digite o CPF do aluno a ser removido:");
        if (cpf == null || cpf.trim().isEmpty()) {
            return;
        }

        boolean removido = alunos.removeIf(a -> a.getCpf().equals(cpf));
        
        if (removido) {
            JOptionPane.showMessageDialog(null, "Aluno removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Aluno não encontrado!");
        }
    }

    private static void listarAlunos() {
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum aluno cadastrado!");
            return;
        }

        StringBuilder sb = new StringBuilder("===== Lista de Alunos =====\n");
        for (Aluno aluno : alunos) {
            sb.append("Nome: ").append(aluno.getNome())
              .append("\nCPF: ").append(aluno.getCpf())
              .append("\nMatrícula: ").append(aluno.getMatricula())
              .append("\nE-mail: ").append(aluno.getEmail())
              .append("\nData Nasc.: ").append(aluno.getData_nasc())
              .append("\n----------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void buscarAlunoPorCpf() {
        String cpf = JOptionPane.showInputDialog("Digite o CPF do aluno:");
        if (cpf == null || cpf.trim().isEmpty()) {
            return;
        }

        alunos.stream()
            .filter(a -> a.getCpf().equals(cpf))
            .findFirst()
            .ifPresentOrElse(
                aluno -> JOptionPane.showMessageDialog(null, 
                    "Aluno encontrado:\n" +
                    "Nome: " + aluno.getNome() + "\n" +
                    "Matrícula: " + aluno.getMatricula() + "\n" +
                    "E-mail: " + aluno.getEmail()),
                () -> JOptionPane.showMessageDialog(null, "Aluno não encontrado!")
            );
    }
}