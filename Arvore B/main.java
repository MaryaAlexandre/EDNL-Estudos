import java.util.Scanner;

public class Main {

    // Classe que representa um no da Arvore B
    static class NoB {
        int[] chaves;
        NoB[] filhos;
        int numChaves;
        boolean folha;

        NoB(int t, boolean folha) {
            this.chaves = new int[2 * t - 1];
            this.filhos = new NoB[2 * t];
            this.numChaves = 0;
            this.folha = folha;
        }
    }

    // Classe responsavel pelas operacoes da Arvore B
    static class ArvoreB {
        private NoB raiz;
        private final int t;

        ArvoreB(int t) {
            if (t < 2) {
                throw new IllegalArgumentException(
                    "O grau minimo deve ser >= 2."
                );
            }

            this.t = t;
            this.raiz = new NoB(t, true);
        }

        // OPERACAO DE BUSCA

        boolean buscar(int chave) {
            return buscar(raiz, chave);
        }

        private boolean buscar(NoB no, int chave) {
            int i = 0;

            while (i < no.numChaves && chave > no.chaves[i]) {
                i++;
            }

            if (i < no.numChaves && chave == no.chaves[i]) {
                return true;
            }

            if (no.folha) {
                return false;
            }

            return buscar(no.filhos[i], chave);
        }

        // OPERACAO DE INSERCAO

        boolean inserir(int chave) {
            if (buscar(chave)) {
                return false;
            }

            if (raiz.numChaves == 2 * t - 1) {
                NoB novaRaiz = new NoB(t, false);
                novaRaiz.filhos[0] = raiz;

                dividirFilho(novaRaiz, 0);
                raiz = novaRaiz;
            }

            inserirNaoCheio(raiz, chave);
            return true;
        }

        // Divide um no cheio em dois
        private void dividirFilho(NoB pai, int indice) {
            NoB cheio = pai.filhos[indice];
            NoB novo = new NoB(t, cheio.folha);

            novo.numChaves = t - 1;

            for (int j = 0; j < t - 1; j++) {
                novo.chaves[j] = cheio.chaves[j + t];
            }

            if (!cheio.folha) {
                for (int j = 0; j < t; j++) {
                    novo.filhos[j] = cheio.filhos[j + t];
                    cheio.filhos[j + t] = null;
                }
            }

            int mediana = cheio.chaves[t - 1];
            cheio.numChaves = t - 1;

            for (int j = pai.numChaves; j >= indice + 1; j--) {
                pai.filhos[j + 1] = pai.filhos[j];
            }

            pai.filhos[indice + 1] = novo;

            for (int j = pai.numChaves - 1; j >= indice; j--) {
                pai.chaves[j + 1] = pai.chaves[j];
            }

            pai.chaves[indice] = mediana;
            pai.numChaves++;
        }

        // Insere uma chave em um no que nao esta cheio
        private void inserirNaoCheio(NoB no, int chave) {
            int i = no.numChaves - 1;

            if (no.folha) {
                while (i >= 0 && chave < no.chaves[i]) {
                    no.chaves[i + 1] = no.chaves[i];
                    i--;
                }

                no.chaves[i + 1] = chave;
                no.numChaves++;

            } else {
                while (i >= 0 && chave < no.chaves[i]) {
                    i--;
                }

                i++;

                if (no.filhos[i].numChaves == 2 * t - 1) {
                    dividirFilho(no, i);

                    if (chave > no.chaves[i]) {
                        i++;
                    }
                }

                inserirNaoCheio(no.filhos[i], chave);
            }
        }

        // OPERACAO DE REMOCAO

        boolean remover(int chave) {
            if (!buscar(chave)) {
                return false;
            }

            remover(raiz, chave);

            // Ajusta a raiz quando ela fica vazia
            if (raiz.numChaves == 0 && !raiz.folha) {
                raiz = raiz.filhos[0];
            }

            return true;
        }

        private void remover(NoB no, int chave) {
            int i = 0;

            while (i < no.numChaves && chave > no.chaves[i]) {
                i++;
            }

            // Caso 1: a chave esta no no atual
            if (i < no.numChaves && no.chaves[i] == chave) {

                if (no.folha) {
                    // Remove diretamente da folha
                    for (int j = i + 1; j < no.numChaves; j++) {
                        no.chaves[j - 1] = no.chaves[j];
                    }

                    no.numChaves--;

                } else {
                    // Remove de um no interno
                    removerDeInterno(no, i);
                }

            } else {
                if (no.folha) {
                    return;
                }

                boolean ultimoFilho = (i == no.numChaves);

                // Garante chaves suficientes antes de descer
                if (no.filhos[i].numChaves == t - 1) {
                    prepararFilho(no, i);

                    if (ultimoFilho && i > no.numChaves) {
                        i--;
                    }
                }

                remover(no.filhos[i], chave);
            }
        }

        // Remove uma chave localizada em um no interno
        private void removerDeInterno(NoB no, int i) {
            int chave = no.chaves[i];

            // Caso 2a: substituir pelo predecessor
            if (no.filhos[i].numChaves >= t) {
                int anterior = maiorChave(no.filhos[i]);

                no.chaves[i] = anterior;
                remover(no.filhos[i], anterior);

            // Caso 2b: substituir pelo sucessor
            } else if (no.filhos[i + 1].numChaves >= t) {
                int proxima = menorChave(no.filhos[i + 1]);

                no.chaves[i] = proxima;
                remover(no.filhos[i + 1], proxima);

            // Caso 2c: realizar fusao
            } else {
                fundir(no, i);
                remover(no.filhos[i], chave);
            }
        }

        // Encontra a maior chave de uma subarvore
        private int maiorChave(NoB no) {
            while (!no.folha) {
                no = no.filhos[no.numChaves];
            }

            return no.chaves[no.numChaves - 1];
        }

        // Encontra a menor chave de uma subarvore
        private int menorChave(NoB no) {
            while (!no.folha) {
                no = no.filhos[0];
            }

            return no.chaves[0];
        }

        // Prepara um filho antes da remocao
        private void prepararFilho(NoB pai, int i) {

            if (i > 0 && pai.filhos[i - 1].numChaves >= t) {
                emprestarEsquerda(pai, i);

            } else if (
                i < pai.numChaves &&
                pai.filhos[i + 1].numChaves >= t
            ) {
                emprestarDireita(pai, i);

            } else if (i < pai.numChaves) {
                fundir(pai, i);

            } else {
                fundir(pai, i - 1);
            }
        }

        // Empresta uma chave do irmao esquerdo
        private void emprestarEsquerda(NoB pai, int i) {
            NoB filho = pai.filhos[i];
            NoB irmao = pai.filhos[i - 1];

            for (int j = filho.numChaves - 1; j >= 0; j--) {
                filho.chaves[j + 1] = filho.chaves[j];
            }

            if (!filho.folha) {
                for (int j = filho.numChaves; j >= 0; j--) {
                    filho.filhos[j + 1] = filho.filhos[j];
                }

                filho.filhos[0] = irmao.filhos[irmao.numChaves];
                irmao.filhos[irmao.numChaves] = null;
            }

            filho.chaves[0] = pai.chaves[i - 1];
            pai.chaves[i - 1] = irmao.chaves[irmao.numChaves - 1];

            filho.numChaves++;
            irmao.numChaves--;
        }

        // Empresta uma chave do irmao direito
        private void emprestarDireita(NoB pai, int i) {
            NoB filho = pai.filhos[i];
            NoB irmao = pai.filhos[i + 1];

            filho.chaves[filho.numChaves] = pai.chaves[i];

            if (!filho.folha) {
                filho.filhos[filho.numChaves + 1] = irmao.filhos[0];
            }

            pai.chaves[i] = irmao.chaves[0];

            for (int j = 1; j < irmao.numChaves; j++) {
                irmao.chaves[j - 1] = irmao.chaves[j];
            }

            if (!irmao.folha) {
                for (int j = 1; j <= irmao.numChaves; j++) {
                    irmao.filhos[j - 1] = irmao.filhos[j];
                }

                irmao.filhos[irmao.numChaves] = null;
            }

            filho.numChaves++;
            irmao.numChaves--;
        }

        // Realiza a fusao de dois nos
        private void fundir(NoB pai, int i) {
            NoB esquerdo = pai.filhos[i];
            NoB direito = pai.filhos[i + 1];

            int pos = esquerdo.numChaves;

            esquerdo.chaves[pos] = pai.chaves[i];

            for (int j = 0; j < direito.numChaves; j++) {
                esquerdo.chaves[pos + 1 + j] = direito.chaves[j];
            }

            if (!esquerdo.folha) {
                for (int j = 0; j <= direito.numChaves; j++) {
                    esquerdo.filhos[pos + 1 + j] = direito.filhos[j];
                }
            }

            esquerdo.numChaves += direito.numChaves + 1;

            for (int j = i + 1; j < pai.numChaves; j++) {
                pai.chaves[j - 1] = pai.chaves[j];
            }

            for (int j = i + 2; j <= pai.numChaves; j++) {
                pai.filhos[j - 1] = pai.filhos[j];
            }

            pai.filhos[pai.numChaves] = null;
            pai.numChaves--;
        }

        // EXIBICAO DA ARVORE

        void exibir() {
            System.out.println("\nEstrutura da Arvore B:");
            exibir(raiz, 0);
        }

        private void exibir(NoB no, int nivel) {
            System.out.print(
                "  ".repeat(nivel) + "Nivel " + nivel + ": ["
            );

            for (int i = 0; i < no.numChaves; i++) {
                if (i > 0) {
                    System.out.print(" | ");
                }

                System.out.print(no.chaves[i]);
            }

            System.out.println("]");

            if (!no.folha) {
                for (int i = 0; i <= no.numChaves; i++) {
                    exibir(no.filhos[i], nivel + 1);
                }
            }
        }
    }

    // CLASSE MAIN - MENU INTERATIVO

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Grau minimo 3, conforme os exemplos da aula
        ArvoreB arvore = new ArvoreB(3);

        int opcao = -1;

        do {
            System.out.println("\n=== MENU - ARVORE B ===");
            System.out.println("1 - Inserir chave");
            System.out.println("2 - Buscar chave");
            System.out.println("3 - Remover chave");
            System.out.println("4 - Exibir arvore");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            if (!entrada.hasNextInt()) {
                System.out.println("Digite um numero valido.");
                entrada.nextLine();
                continue;
            }

            opcao = entrada.nextInt();

            if (opcao >= 1 && opcao <= 3) {
                System.out.print("Digite a chave inteira: ");

                if (!entrada.hasNextInt()) {
                    System.out.println("Chave invalida.");
                    entrada.nextLine();
                    continue;
                }

                int chave = entrada.nextInt();

                switch (opcao) {
                    case 1 -> System.out.println(
                        arvore.inserir(chave)
                            ? "Chave inserida."
                            : "Chave ja existe."
                    );

                    case 2 -> System.out.println(
                        arvore.buscar(chave)
                            ? "Chave encontrada."
                            : "Chave nao encontrada."
                    );

                    case 3 -> System.out.println(
                        arvore.remover(chave)
                            ? "Chave removida."
                            : "Chave nao encontrada."
                    );
                }

            } else if (opcao == 4) {
                arvore.exibir();

            } else if (opcao != 0) {
                System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        entrada.close();
        System.out.println("Programa encerrado.");
    }
}
