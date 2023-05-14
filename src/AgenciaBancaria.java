import java.util.ArrayList;
import java.util.Scanner;
import entidades.Conta;
import entidades.Pessoa;

public class AgenciaBancaria {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Conta> contasBancarias;

    public static void main(String[] args) {
        contasBancarias = new ArrayList<Conta>();

        // menu de operações
        operacoes();

    }
    public static void operacoes(){
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
                operacoes();
                break;
        }
    }

    public static void criarConta(){
        System.out.println("\n Nome: ");
        String nome = input.nextLine();
        System.out.println("\n CPF: ");
        String cpf =input.nextLine();
        System.out.println("\n E-mail: ");
        String email = input.nextLine();

        Pessoa pessoa = new Pessoa(nome, cpf, email);

        Conta conta = new Conta(pessoa);

        contasBancarias.add(conta);

        System.out.println("Sua conta foi criada com sucesso!");

        operacoes();
    }

    private static Conta encontrarConta(int numeroConta) {
        Conta conta = null;
        if (contasBancarias.size() > 0) {
            for (Conta c : contasBancarias) {
                if (c.getNumeroConta() == numeroConta) {
                    conta = c;
                }
            }

        }return conta;
    }

public static void depositar(){
    System.out.println("Digite o número da conta para Depósito: ");
    int numeroConta = input.nextInt();

    Conta conta = encontrarConta(numeroConta);

    if (conta != null){
        System.out.println("Qual valor deseja depositar?");
        Double valorDeposito = input.nextDouble();
        conta.depositar(valorDeposito);
        System.out.println("Valor depositado com sucesso!");
    }else{
        System.out.println("Conta não encontrada.");
    }
    operacoes();
}

public static void sacar(){

    System.out.println("Digite o número da conta: ");
    int numeroConta = input.nextInt();

    Conta conta = encontrarConta(numeroConta);

    if (conta != null){
        System.out.println("Qual valor deseja sacar?");
        Double valorSaque = input.nextDouble();
        conta.sacar(valorSaque);
        System.out.println("Valor sacado com sucesso!");
    }else{
        System.out.println("Conta não encontrada.");
    }
    operacoes();

}

public static void transferir(){
    System.out.println("Número da conta remetente: ");
    int numeroContaRemetente = input.nextInt();

    Conta contaRemetente = encontrarConta(numeroContaRemetente);

    if(contaRemetente!=null){
        System.out.println("Número da conta do Destinatário: ");
        int numeroContaDestinatario = input.nextInt();

        Conta contaDestinatario = encontrarConta(numeroContaDestinatario);
        if(contaDestinatario!= null){
            System.out.println("Digite o valor da transferência: ");
            Double valor = input.nextDouble();

            contaRemetente.transferir(contaDestinatario, valor);
        }

    }
    operacoes();
}

public static void listarContas(){
        if(contasBancarias.size()>0){
            for(Conta conta: contasBancarias){
                System.out.println(conta);
            }
            }else{
                System.out.println("Não há contas cadastradas.");

        }
        operacoes();
}
}
