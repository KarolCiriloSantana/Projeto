package gerenciar;

import gerenciar.interfaces.IGerenciarEntidades;
import javax.swing.*;

public class MenuPrincipal implements IGerenciarEntidades {
    private final IGerenciarEntidades gerenciarAlunos;
    private final GerenciarEntidades gerenciarProfessores;
    private final GerenciarEntidades gerenciarTurmas;

    public MenuPrincipal(GerenciarEntidades gerenciarAlunos, 
                        GerenciarEntidades gerenciarProfessores,
                        GerenciarEntidades gerenciarTurmas) {
        this.gerenciarAlunos = gerenciarAlunos;
        this.gerenciarProfessores = gerenciarProfessores;
        this.gerenciarTurmas = gerenciarTurmas;
    }

    @Override
    public void exibirMenu() {
        while (true) {
            String opcao = JOptionPane.showInputDialog(
                "===== MENU PRINCIPAL =====\n" +
                "1. Gerenciar Alunos\n" +
                "2. Gerenciar Professores\n" +
                "3. Gerenciar Turmas\n" +
                "4. Sair\n\n" +
                "Digite sua opção:");

            if (opcao == null || opcao.equals("4")) {
                return;
            }

            switch (opcao) {
                case "1":
                    gerenciarAlunos.exibirMenu();
                    break;
                case "2":
                    gerenciarProfessores.exibirMenu();
                    break;
                case "3":
                    gerenciarTurmas.exibirMenu();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
}
