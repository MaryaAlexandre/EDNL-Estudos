package arvore_avl;

import java.util.ArrayList;
import java.util.List;

public class ArvoreBinariaBusca {

    protected No raiz;

    public No getRaiz() {
        return raiz;
    }

    // busca
    public No buscar(int chave) {
        return buscarNo(chave);
    }

    protected No buscarNo(int chave) {
        No atual = raiz;
        while (atual != null) {
            if (chave == atual.getElemento()) {
                return atual;
            } else if (chave < atual.getElemento()) {
                atual = atual.getFilhoEsquerda();
            } else {
                atual = atual.getFilhoDireita();
            }
        }
        return null;
    }

    // insere
    public void inserir(int chave) {
        inserirNo(chave);
    }

    protected No inserirNo(int chave) {
        No novo = new No(chave);

        if (raiz == null) {
            raiz = novo;
            return novo;
        }

        No atual = raiz;
        No pai = null;

        while (atual != null) {
            pai = atual;
            if (chave < atual.getElemento()) {
                atual = atual.getFilhoEsquerda();
            } else if (chave > atual.getElemento()) {
                atual = atual.getFilhoDireita();
            } else {
                return null; // já existe
            }
        }

        novo.setPai(pai);
        if (chave < pai.getElemento()) {
            pai.setFilhoEsquerda(novo);
        } else {
            pai.setFilhoDireita(novo);
        }

        return novo;
    }

    // remove

    // reequilibra depois de remover
    protected static class PontoDeRebalanceamento {
        No no;
        boolean removidoDaEsquerda;

        PontoDeRebalanceamento(No no, boolean removidoDaEsquerda) {
            this.no = no;
            this.removidoDaEsquerda = removidoDaEsquerda;
        }
    }

    public void remover(int chave) {
        No alvo = buscarNo(chave);
        if (alvo != null) {
            removerNo(alvo);
        }
    }

    protected PontoDeRebalanceamento removerNo(No alvo) {
        boolean temDoisFilhos = alvo.getFilhoEsquerda() != null && alvo.getFilhoDireita() != null;

        if (temDoisFilhos) {
            // dois filhos: acha sucessor
            No sucessor = alvo.getFilhoDireita();
            while (sucessor.getFilhoEsquerda() != null) {
                sucessor = sucessor.getFilhoEsquerda();
            }

            alvo.setElemento(sucessor.getElemento());

            // remove o sucessor, o nó é folha sem filhos ou o nó só um filho
            return removerNo(sucessor);
        }

        // folha ou um filho só
        No filho = (alvo.getFilhoEsquerda() != null) ? alvo.getFilhoEsquerda() : alvo.getFilhoDireita();
        No pai = alvo.getPai();
        boolean eraFilhoEsquerdo = (pai != null) && (pai.getFilhoEsquerda() == alvo);

        if (filho != null) {
            filho.setPai(pai);
        }

        if (pai == null) {
            raiz = filho;
        } else if (eraFilhoEsquerdo) {
            pai.setFilhoEsquerda(filho);
        } else {
            pai.setFilhoDireita(filho);
        }

        return new PontoDeRebalanceamento(pai, eraFilhoEsquerdo);
    }

    // mostra árvore
    public void mostrarArvore() {
        if (raiz == null) {
            System.out.println("(árvore vazia)");
            return;
        }

        int altura = calcularAltura(raiz);
        int totalDeNos = contarNos(raiz);
        int larguraUnidade = 8;

        List<StringBuilder> linhas = new ArrayList<>();
        for (int i = 0; i <= altura; i++) {
            StringBuilder linha = new StringBuilder();
            for (int j = 0; j < totalDeNos * larguraUnidade; j++) {
                linha.append(' ');
            }
            linhas.add(linha);
        }

        preencherLinhas(raiz, 0, new int[]{0}, linhas, larguraUnidade);

        for (StringBuilder linha : linhas) {
            System.out.println(linha.toString());
        }
    }

    private void preencherLinhas(No no, int profundidade, int[] rankAtual,
                                  List<StringBuilder> linhas, int larguraUnidade) {
        if (no == null) {
            return;
        }

        preencherLinhas(no.getFilhoEsquerda(), profundidade + 1, rankAtual, linhas, larguraUnidade);

        String rotulo = rotuloNo(no);
        int coluna = rankAtual[0] * larguraUnidade;
        rankAtual[0]++;

        StringBuilder linha = linhas.get(profundidade);
        for (int k = 0; k < rotulo.length() && (coluna + k) < linha.length(); k++) {
            linha.setCharAt(coluna + k, rotulo.charAt(k));
        }

        preencherLinhas(no.getFilhoDireita(), profundidade + 1, rankAtual, linhas, larguraUnidade);
    }

    protected String rotuloNo(No no) {
        return String.valueOf(no.getElemento());
    }

    private int calcularAltura(No no) {
        if (no == null) {
            return -1;
        }
        return 1 + Math.max(calcularAltura(no.getFilhoEsquerda()), calcularAltura(no.getFilhoDireita()));
    }

    private int contarNos(No no) {
        if (no == null) {
            return 0;
        }
        return 1 + contarNos(no.getFilhoEsquerda()) + contarNos(no.getFilhoDireita());
    }
}