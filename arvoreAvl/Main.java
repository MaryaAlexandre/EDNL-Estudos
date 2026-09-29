package arvore_avl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArvoreAVL arvore = new ArvoreAVL();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println();
            System.out.println("===== Arvore AVL =====");
            System.out.println("1 - Inserir valor");
            System.out.println("2 - Remover valor");
            System.out.println("3 - Buscar valor");
            System.out.println("4 - Mostrar arvore");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    System.out.print("Valor a inserir: ");
                    int valorInserir = lerInteiro(scanner);
                    arvore.inserir(valorInserir);
                    System.out.println("Valor " + valorInserir + " inserido.");
                    break;

                case 2:
                    System.out.print("Valor a remover: ");
                    int valorRemover = lerInteiro(scanner);
                    arvore.remover(valorRemover);
                    System.out.println("Valor " + valorRemover + " removido (se existia na arvore).");
                    break;

                case 3:
                    System.out.print("Valor a buscar: ");
                    int valorBuscar = lerInteiro(scanner);
                    No encontrado = arvore.buscar(valorBuscar);
                    if (encontrado != null) {
                        System.out.println("Encontrado! Fator de balanceamento: " + encontrado.getBalanceamento());
                    } else {
                        System.out.println("Valor nao encontrado na arvore.");
                    }
                    break;

                case 4:
                    System.out.println();
                    arvore.mostrarArvore();
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero valido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}