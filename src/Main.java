import static utilitarios.DataBaseService.*;

public class Main {
    public static void main(String[] args) {
        // cria o database e a tabela de dados no banco de dados;
        createDatabase();
        createTable();
        // inicia a aplicação;
        AgenciaBancaria.exibirMenu();
    }
}
