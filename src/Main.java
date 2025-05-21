import gerenciar.GerenciarAluno;

import gerenciar.GerenciarProfessor;

import gerenciar.GerenciarMatricula;
import gerenciar.MenuPrincipal;
import gerenciar.interfaces.IGerenciador;

public class Main {
    public static void main(String[] args) {
        // Inicializa todos os gerenciadores
        IGerenciador gTurmas = new GerenciarTurmas();
        IGerenciador gAlunos = new GerenciarAluno();
        IGerenciador gProfessores = new GerenciarProfessor();
        IGerenciador gCursos = new GerenciarCursos();
        IGerenciador gMatriculas = new GerenciarMatricula(gAlunos, gTurmas);

        MenuPrincipal menu = new MenuPrincipal(
            gTurmas, 
            gAlunos, 
            gProfessores, 
            gCursos,
            gMatriculas
        );
        
        menu.exibir();
    }
}