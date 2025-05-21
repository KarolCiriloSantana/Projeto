package main;

import gerenciar.MenuPrincipal;
import gerenciar.MenuAlunos;
import gerenciar.MenuProfessores;
import gerenciar.MenuTurmas;

public class SistemaAcademico {
    public static void main(String[] args) {
        // Criação dos gerenciadores
        GerenciarEntidades menuAlunos = new MenuAlunos();
        GerenciarEntidades menuProfessores = new MenuProfessores();
        GerenciarEntidades menuTurmas = new MenuTurmas();
        
        // Menu principal que coordena todos os outros
        GerenciarEntidades menuPrincipal = new MenuPrincipal(
            menuAlunos, 
            menuProfessores,
            menuTurmas
        );
        
        // Inicia o sistema
        menuPrincipal.exibirMenu();
    }
}