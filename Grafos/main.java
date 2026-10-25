package grafos;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println();
            System.out.println("===== TAD Grafo (Multigrafo Misto) =====");
            System.out.println("1  - Inserir vertice");
            System.out.println("2  - Inserir aresta (nao direcionada)");
            System.out.println("3  - Inserir aresta direcionada");
            System.out.println("4  - Remover vertice");
            System.out.println("5  - Remover aresta");
            System.out.println("6  - Listar vertices");
            System.out.println("7  - Listar arestas");
            System.out.println("8  - Arestas incidentes a um vertice");
            System.out.println("9  - Verificar se dois vertices sao adjacentes");
            System.out.println("10 - Vertice oposto numa aresta");
            System.out.println("11 - Substituir valor de um vertice");
            System.out.println("12 - Substituir valor de uma aresta");
            System.out.println("13 - Mostrar grafo completo");
            System.out.println("0  - Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    System.out.print("Valor do vertice: ");
                    String valorVertice = scanner.nextLine();
                    grafo.inserirVertice(valorVertice);
                    System.out.println("Vertice inserido.");
                    break;

                case 2: {
                    Vertice v = pedirVertice(grafo, scanner, "de origem");
                    Vertice w = pedirVertice(grafo, scanner, "de destino");
                    if (v == null || w == null) break;
                    System.out.print("Valor da aresta (rotulo/peso): ");
                    String valorAresta = scanner.nextLine();
                    grafo.inserirAresta(v, w, valorAresta);
                    System.out.println("Aresta inserida.");
                    break;
                }

                case 3: {
                    Vertice v = pedirVertice(grafo, scanner, "de origem");
                    Vertice w = pedirVertice(grafo, scanner, "de destino");
                    if (v == null || w == null) break;
                    System.out.print("Valor da aresta (rotulo/peso): ");
                    String valorAresta = scanner.nextLine();
                    grafo.inserirArestaDirecionada(v, w, valorAresta);
                    System.out.println("Aresta direcionada inserida.");
                    break;
                }

                case 4: {
                    Vertice v = pedirVertice(grafo, scanner, "a remover");
                    if (v == null) break;
                    grafo.removeVértice(v);
                    System.out.println("Vertice removido.");
                    break;
                }

                case 5: {
                    Aresta e = pedirAresta(grafo, scanner);
                    if (e == null) break;
                    grafo.removeAresta(e);
                    System.out.println("Aresta removida.");
                    break;
                }

                case 6:
                    System.out.println("Vertices: " + grafo.vertices());
                    break;

                case 7:
                    System.out.println("Arestas:");
                    for (Aresta e : grafo.arestas()) {
                        System.out.println("  " + e);
                    }
                    break;

                case 8: {
                    Vertice v = pedirVertice(grafo, scanner, "para ver as incidencias");
                    if (v == null) break;
                    System.out.println("Arestas incidentes:");
                    for (Aresta e : grafo.arestasIncidentes(v)) {
                        System.out.println("  " + e);
                    }
                    break;
                }

                case 9: {
                    Vertice v = pedirVertice(grafo, scanner, "primeiro");
                    Vertice w = pedirVertice(grafo, scanner, "segundo");
                    if (v == null || w == null) break;
                    System.out.println(grafo.éAdjacente(v, w) ? "Sao adjacentes." : "Nao sao adjacentes.");
                    break;
                }

                case 10: {
                    Vertice v = pedirVertice(grafo, scanner, "de referencia");
                    if (v == null) break;
                    Aresta e = pedirAresta(grafo, scanner);
                    if (e == null) break;
                    try {
                        System.out.println("Oposto: " + grafo.oposto(v, e));
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Essa aresta nao e incidente a esse vertice.");
                    }
                    break;
                }

                case 11: {
                    Vertice v = pedirVertice(grafo, scanner, "a alterar");
                    if (v == null) break;
                    System.out.print("Novo valor: ");
                    String novoValor = scanner.nextLine();
                    grafo.substituir(v, novoValor);
                    System.out.println("Valor do vertice atualizado.");
                    break;
                }

                case 12: {
                    Aresta e = pedirAresta(grafo, scanner);
                    if (e == null) break;
                    System.out.print("Novo valor: ");
                    String novoValor = scanner.nextLine();
                    grafo.substituir(e, novoValor);
                    System.out.println("Valor da aresta atualizado.");
                    break;
                }

                case 13:
                    System.out.println();
                    grafo.mostrarGrafo();
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

    // pede o valor de um vertice e acha ele no grafo
    private static Vertice pedirVertice(Grafo grafo, Scanner scanner, String rotulo) {
        System.out.print("Vertice " + rotulo + " (valor): ");
        String valor = scanner.nextLine();
        for (Vertice v : grafo.vertices()) {
            if (String.valueOf(v.getElemento()).equals(valor)) {
                return v;
            }
        }
        System.out.println("Vertice nao encontrado.");
        return null;
    }

    // lista as arestas numeradas e deixa escolher uma
    private static Aresta pedirAresta(Grafo grafo, Scanner scanner) {
        List<Aresta> arestas = grafo.arestas();
        if (arestas.isEmpty()) {
            System.out.println("Nao ha arestas no grafo.");
            return null;
        }

        System.out.println("Escolha a aresta:");
        for (int i = 0; i < arestas.size(); i++) {
            System.out.println("  " + i + " - " + arestas.get(i));
        }
        System.out.print("Numero da aresta: ");
        int indice = lerInteiro(scanner);
        if (indice < 0 || indice >= arestas.size()) {
            System.out.println("Indice invalido.");
            return null;
        }
        return arestas.get(indice);
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