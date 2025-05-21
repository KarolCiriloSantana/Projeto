// src/Main.java
public class Main {
    public static void main(String[] args) {
        // Inicializa todos os gerenciadores
        IGerenciador gTurmas = new GerenciarTurmas();
        IGerenciador gAlunos = new GerenciarAluno();
        IGerenciador gProfessores = new GerenciarProfessor();
        IGerenciador gCursos = new GerenciarCursos();
        IGerenciador gMatriculas = new GerenciarMatricula(gAlunos, gTurmas); // Novo

        MenuPrincipal menu = new MenuPrincipal(
            gTurmas, 
            gAlunos, 
            gProfessores, 
            gCursos,
            gMatriculas // Adicionado
        );
        
        menu.exibir();
    }
}