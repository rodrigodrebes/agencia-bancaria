import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entidades.Conta;
import entidades.Pessoa;
import utilitarios.DataBaseService;

public class AgenciaBancaria {
    static Scanner input = new Scanner(System.in);

    //método comentado, salvaria as listas em memória
    //static ArrayList<Conta> contasBancarias = new ArrayList<Conta>();
    private static DataBaseService dbService = new DataBaseService();
    static List<Conta> contasBancarias = dbService.buscarTodasContas();




    public static void exibirMenu(){
        System.out.println("-----------------------------");
        System.out.println("-----------BEM VINDO---------");
        System.out.println("-----------------------------");
        System.out.println("**** SELECIONE UMA OPÇÃO ****");
        System.out.println("-----------------------------");
        System.out.println("|   OPÇÃO 1 - CRIAR CONTA   |");
        System.out.println("|   OPÇÃO 2 - DEPOSITAR     |");
        System.out.println("|   OPÇÃO 3 - SACAR         |");
        System.out.println("|   OPÇÃO 4 - TRANSFERIR    |");
        System.out.println("|   OPÇÃO 5 - LISTAR        |");
        System.out.println("|   OPÇÃO 6 - SAIR          |");

        int operacao = input.nextInt();
        input.nextLine();

        switch (operacao){
            case 1:
                criarConta();
                break;
            case 2:
                depositar();
                break;
            case 3:
                sacar();
                break;
            case 4:
                transferir();
                break;
            case 5:
                listarContas();
                break;
            case 6:
                System.out.println("Obrigado por usar nosso sistema!");
                System.exit(0);
            default:
                System.out.println("Opção inválida!");
                exibirMenu();
                break;
        }
    }

    // método para encontrar conta na memória

    /*private static Conta encontrarConta(int numeroConta) {
        Conta conta = null;
        if (contasBancarias.size() > 0) {
            for (Conta c : contasBancarias) {
                if (c.getNumeroConta() == numeroConta) {
                    conta = c;
                }
            }

        }return conta;
    }
*/

    public static void criarConta(){
        System.out.println("\n Nome: ");
        String nome = input.nextLine();
        System.out.println("\n CPF: ");
        String cpf =input.nextLine();
        System.out.println("\n E-mail: ");
        String email = input.nextLine();

        Pessoa pessoa = new Pessoa(nome, cpf, email);

        Conta conta = new Conta(pessoa);

        // salva em memória
        contasBancarias.add(conta);
        // salva no banco de dados
        dbService.criarConta(conta);

        System.out.println("Sua conta foi criada com sucesso!");

        exibirMenu();
    }
    // método para encontrar conta na memória
public static void depositar(){
    System.out.println("Digite o número da conta para Depósito: ");
    int numeroConta = input.nextInt();

    // busca em memória
    //Conta conta = encontrarConta(numeroConta);

    //busca em banco de dados
    Conta conta = dbService.buscarConta(numeroConta);

    if (conta != null){
        System.out.println("Qual valor deseja depositar?");
        Double valorDeposito = input.nextDouble();
        // salva em memória
        conta.depositar(valorDeposito);
        // salva no banco de dados
        dbService.atualizarSaldoConta(conta.getNumeroConta(), conta.getSaldo());
        System.out.println("Valor depositado com sucesso!");
    }else{
        System.out.println("Conta não encontrada.");
    }
    exibirMenu();
}

public static void sacar(){

    System.out.println("Digite o número da conta: ");
    int numeroConta = input.nextInt();
    // busca em memória
    //Conta conta = encontrarConta(numeroConta);

    // busca em banco de dados
    Conta conta = dbService.buscarConta(numeroConta);

    if (conta != null){
        System.out.println("Qual valor deseja sacar?");
        Double valorSaque = input.nextDouble();
        // salva em memória
        conta.sacar(valorSaque);
        // salva no banco de dados
        dbService.atualizarSaldoConta(conta.getNumeroConta(), conta.getSaldo());
        System.out.println("Valor sacado com sucesso!");
    }else{
        System.out.println("Conta não encontrada.");
    }

    exibirMenu();
}

    public static void transferir() {
        System.out.println("Número da conta remetente: ");
        int numeroContaRemetente = input.nextInt();

        // Antes, nós buscaríamos a conta na lista em memória usando o método encontrarConta:
        // Conta contaRemetente = encontrarConta(numeroContaRemetente);

        // Agora, buscamos a conta diretamente do banco de dados:
        Conta contaRemetente = dbService.buscarConta(numeroContaRemetente);

        if(contaRemetente != null) {
            System.out.println("Número da conta do Destinatário: ");
            int numeroContaDestinatario = input.nextInt();

            // Antes, nós buscaríamos a conta destinatária na lista em memória usando o método encontrarConta:
            // Conta contaDestinatario = encontrarConta(numeroContaDestinatario);

            // Agora, buscamos a conta destinatária diretamente do banco de dados:
            Conta contaDestinatario = dbService.buscarConta(numeroContaDestinatario);

            if(contaDestinatario != null) {
                System.out.println("Digite o valor da transferência: ");
                Double valor = input.nextDouble();

                // Realiza a transferência
                contaRemetente.transferir(contaDestinatario, valor);

                // Antes, a atualização do saldo das contas era feita automaticamente na memória quando chamávamos o método transferir.

                // Agora, precisamos atualizar o saldo das contas no banco de dados manualmente:
                dbService.atualizarSaldoConta(contaRemetente.getNumeroConta(), contaRemetente.getSaldo());
                dbService.atualizarSaldoConta(contaDestinatario.getNumeroConta(), contaDestinatario.getSaldo());
            } else {
                System.out.println("Conta destinatária não encontrada.");
            }

        } else {
            System.out.println("Conta remetente não encontrada.");
        }

        exibirMenu();
    }


    public static void listarContas(){
        // recupera as contas do banco de dados e da memória;
        List<Conta> contasDoBanco = dbService.buscarTodasContas();
        if(contasDoBanco.size()>0 || contasBancarias.size()>0){
            for(Conta conta: contasDoBanco){
                System.out.println(conta);
            }
        } else {
            System.out.println("Não há contas cadastradas.");
        }
        exibirMenu();
    }

}
