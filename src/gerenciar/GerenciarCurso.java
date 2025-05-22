package gerenciar;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import entidades.Curso;

public class GerenciarCurso {
    public static void main(String[] args) {
        List<Curso> cursos = new ArrayList<>();

        while (true) {
            String res = JOptionPane.showInputDialog(
                "===== Menu de Cursos =====\n" +
                "[1] Adicionar Curso\n" +
                "[2] Remover Curso\n" +
                "[3] Listar Cursos\n" +
                "[4] Sair"
            );

            if (res == null || res.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                return;
            }

            try {
                int escolha = Integer.parseInt(res);

                switch (escolha) {
                    case 1:
                            
                        String idStr = JOptionPane.showInputDialog("Digite o ID do curso:");
                        if (idStr == null) break;  

                        String nomeCurso = JOptionPane.showInputDialog("Digite o nome do curso:");
                        if (nomeCurso == null) break;

                        String descricao = JOptionPane.showInputDialog("Digite a descrição do curso:");
                        if (descricao == null) break;

                        String duracaoStr = JOptionPane.showInputDialog("Digite a duração (em horas):");
                        if (duracaoStr == null) break;

                        try {
                            int id = Integer.parseInt(idStr);
                            int duracao = Integer.parseInt(duracaoStr);
        
                        if (!nomeCurso.trim().isEmpty()) { 
                            cursos.add(new Curso(id, nomeCurso, descricao, duracao));
                            JOptionPane.showMessageDialog(null, "Curso adicionado!");
                        }
                        } catch (NumberFormatException e) {
                           JOptionPane.showMessageDialog(null, "ID ou duração devem ser números!");
                        }
                        break;
                    case 2:
                        
                        if (cursos.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nenhum curso cadastrado!");
                        } else {
                            String listaCursos = listarCursos(cursos);
                            String indiceRemoverStr = JOptionPane.showInputDialog(
                                "Cursos disponíveis:\n" + listaCursos + "\nDigite o número do curso a remover:"
                            );
                            try {
                                int indiceRemover = Integer.parseInt(indiceRemoverStr) - 1;
                                if (indiceRemover >= 0 && indiceRemover < cursos.size()) {
                                    cursos.remove(indiceRemover);
                                    JOptionPane.showMessageDialog(null, "Curso removido!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Índice inválido!");
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Digite um número válido!");
                            }
                        }
                        break;
                    case 3:
                      
                        if (cursos.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nenhum curso cadastrado!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Cursos cadastrados:\n" + listarCursos(cursos));
                        }
                        break;
                    case 4:
                        return; 
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido!");
            }
        }
    }

    // Método auxiliar para listar cursos formatados
    private static String listarCursos(List<Curso> cursos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cursos.size(); i++) {
            sb.append((i + 1) + ". " + cursos.get(i).getNome() + "\n");
        }
        return sb.toString();
    }
}