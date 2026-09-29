package arvore_rubro_negra;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArvoreRubroNegra arvore =
            new ArvoreRubroNegra();

        int opcao = -1;

        while (opcao != 0) {

            mostrarMenu();

            try {

                System.out.print(
                    "Escolha uma opção: "
                );

                opcao = scanner.nextInt();

                switch (opcao) {

                    case 1:
                        inserir(scanner, arvore);
                        break;

                    case 2:
                        remover(scanner, arvore);
                        break;

                    case 3:
                        buscar(scanner, arvore);
                        break;

                    case 4:
                        arvore.mostrar();
                        break;

                    case 5:
                        System.out.println(
                            "\nQuantidade de nós: "
                            + arvore.getTamanho()
                        );
                        break;

                    case 0:
                        System.out.println(
                            "\nPrograma encerrado."
                        );
                        break;

                    default:
                        System.out.println(
                            "\nOpção inválida."
                        );
                }

            } catch (InputMismatchException e) {

                System.out.println(
                    "\nDigite apenas números inteiros."
                );

              
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void mostrarMenu() {

        System.out.println(
            "\n================================="
        );

        System.out.println(
            "       ÁRVORE RUBRO-NEGRA"
        );

        System.out.println(
            "================================="
        );

        System.out.println(
            "1 - Inserir nó"
        );

        System.out.println(
            "2 - Remover nó"
        );

        System.out.println(
            "3 - Buscar nó"
        );

        System.out.println(
            "4 - Mostrar árvore"
        );

        System.out.println(
            "5 - Mostrar quantidade de nós"
        );

        System.out.println(
            "0 - Sair"
        );

        System.out.println(
            "================================="
        );
    }

    private static void inserir(
        Scanner scanner,
        ArvoreRubroNegra arvore
    ) {

        System.out.print(
            "\nDigite o valor que deseja inserir: "
        );

        int valor = scanner.nextInt();

        boolean inserido = arvore.inserir(valor);

        if (inserido) {

            System.out.println(
                "\nValor "
                + valor
                + " inserido com sucesso."
            );

        } else {

            System.out.println(
                "\nO valor "
                + valor
                + " já existe na árvore."
            );
        }
    }

    private static void remover(
        Scanner scanner,
        ArvoreRubroNegra arvore
    ) {

        System.out.print(
            "\nDigite o valor que deseja remover: "
        );

        int valor = scanner.nextInt();

        boolean removido = arvore.remover(valor);

        if (removido) {

            System.out.println(
                "\nValor "
                + valor
                + " removido com sucesso."
            );

        } else {

            System.out.println(
                "\nO valor "
                + valor
                + " não foi encontrado."
            );
        }
    }

    private static void buscar(
        Scanner scanner,
        ArvoreRubroNegra arvore
    ) {

        System.out.print(
            "\nDigite o valor que deseja buscar: "
        );

        int valor = scanner.nextInt();

        boolean encontrado = arvore.buscar(valor);

        if (encontrado) {

            System.out.println(
                "\nO valor "
                + valor
                + " foi encontrado na árvore."
            );

        } else {

            System.out.println(
                "\nO valor "
                + valor
                + " não foi encontrado na árvore."
            );
        }
    }
}
