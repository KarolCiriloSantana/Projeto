// src/gerenciar/MenuPrincipal.java
package gerenciar;

import gerenciar.interfaces.IGerenciador;
import javax.swing.JOptionPane;

public class MenuPrincipal {
    private final IGerenciador gerenciarTurmas;
    private final IGerenciador gerenciarAlunos;
    private final IGerenciador gerenciarProfessores;
    private final IGerenciador gerenciarCursos;
    private final IGerenciador gerenciarMatriculas; 

    public MenuPrincipal(IGerenciador turmas, IGerenciador alunos, 
                       IGerenciador professores, IGerenciador cursos,
                       IGerenciador matriculas) { 
        this.gerenciarTurmas = turmas;
        this.gerenciarAlunos = alunos;
        this.gerenciarProfessores = professores;
        this.gerenciarCursos = cursos;
        this.gerenciarMatriculas = matriculas; // Inicializa
    }

    public void exibir() {
        while (true) {
            String res = JOptionPane.showInputDialog(
                "===== Menu Principal =====\n" +
                "[1] Gerenciar Turmas\n" +
                "[2] Gerenciar Alunos\n" +
                "[3] Gerenciar Professores\n" +
                "[4] Gerenciar Cursos\n" +
                "[5] Gerenciar Matrículas\n" + 
                "[6] Sair\n\n" + 
                "Digite sua opção:");

            if (res == null || res.equals("6")) { 
                break;
            }

            switch (res) {
                case "1" -> gerenciarTurmas.exibirMenu();
                case "2" -> gerenciarAlunos.exibirMenu();
                case "3" -> gerenciarProfessores.exibirMenu();
                case "4" -> gerenciarCursos.exibirMenu();
                case "5" -> gerenciarMatriculas.exibirMenu(); // Nova case
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
}