import java.util.ArrayList;
import java.util.Scanner;
import entidades.Conta;

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
                listar();
                break;
            case 6:
                System.out.println("Obrigado por usar nosso sistema!");
                system.exit(0);
            default:
                System.out.println("Opção inválida!");
                operacoes();
                break;
        }
    }
}
