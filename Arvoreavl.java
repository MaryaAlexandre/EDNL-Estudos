package arvore_avl;

public class ArvoreAVL extends ArvoreBinariaBusca {

    // insere
    @Override
    public void inserir(int chave) {
        No novo = inserirNo(chave);
        if (novo == null) {
            return; // já existia
        }
        balancearAposInsercao(novo.getPai(), novo);
    }

    private void balancearAposInsercao(No pai, No filhoInserido) {
        No atual = filhoInserido;
        while (pai != null) {
            boolean inseridoNaEsquerda = (pai.getFilhoEsquerda() == atual);
            int fbNovo = inseridoNaEsquerda ? pai.getBalanceamento() + 1 : pai.getBalanceamento() - 1;
            pai.setBalanceamento(fbNovo);

            if (fbNovo == 0) {
                return; 
            } else if (fbNovo == 1 || fbNovo == -1) {
                atual = pai;
                pai = pai.getPai();
            } else {
                rebalancear(pai); // desbalanceado, rotaciona
                return;
            }
        }
    }

    // remove
    @Override
    public void remover(int chave) {
        No alvo = buscarNo(chave);
        if (alvo == null) {
            return;
        }
        PontoDeRebalanceamento ponto = removerNo(alvo);
        balancearAposRemocao(ponto.no, ponto.removidoDaEsquerda);
    }

    private void balancearAposRemocao(No pai, boolean removidoDaEsquerda) {
        boolean daEsquerda = removidoDaEsquerda;
        while (pai != null) {
            int fbNovo = daEsquerda ? pai.getBalanceamento() - 1 : pai.getBalanceamento() + 1;
            pai.setBalanceamento(fbNovo);

            No proximoPai = pai.getPai();
            boolean proximoLadoEsquerdo = (proximoPai != null) && (proximoPai.getFilhoEsquerda() == pai);

            if (fbNovo == 1 || fbNovo == -1) {
                return; 
            }

            if (fbNovo == 0) {
                pai = proximoPai; 
                daEsquerda = proximoLadoEsquerdo;
            } else {
                No novoTopo = rebalancear(pai); // desbalanceado
                if (novoTopo.getBalanceamento() == 0) {
                    pai = proximoPai; 
                    daEsquerda = proximoLadoEsquerdo;
                } else {
                    return;
                }
            }
        }
    }

    // escolhe a rotação
    private No rebalancear(No b) {
        if (b.getBalanceamento() == 2) {
            No a = b.getFilhoEsquerda();
            if (a.getBalanceamento() >= 0) {
                return rotacaoDireitaSimples(b);
            } else {
                return rotacaoDuplaDireita(b);
            }
        } else { // FB(b) == -2
            No a = b.getFilhoDireita();
            if (a.getBalanceamento() <= 0) {
                return rotacaoEsquerdaSimples(b);
            } else {
                return rotacaoDuplaEsquerda(b);
            }
        }
    }

    // rotação esquerda simples 
    private No rotacaoEsquerdaSimples(No b) {
        int fbB = b.getBalanceamento();
        No a = b.getFilhoDireita();
        int fbA = a.getBalanceamento();

        No paiDeB = b.getPai();
        boolean bEraFilhoEsquerdo = (paiDeB != null) && (paiDeB.getFilhoEsquerda() == b);

        b.setFilhoDireita(a.getFilhoEsquerda());
        if (a.getFilhoEsquerda() != null) {
            a.getFilhoEsquerda().setPai(b);
        }

        a.setFilhoEsquerda(b);
        b.setPai(a);

        a.setPai(paiDeB);
        if (paiDeB == null) {
            raiz = a;
        } else if (bEraFilhoEsquerdo) {
            paiDeB.setFilhoEsquerda(a);
        } else {
            paiDeB.setFilhoDireita(a);
        }

        // A é o filho de B que vira o novo topo depois da rotação (na rotação esquerda, A é o filho direito de B; na direita, A é o filho esquerdo
        int fbBNovo = fbB + 1 - Math.min(fbA, 0);
        int fbANovo = fbA + 1 + Math.max(fbBNovo, 0);
        b.setBalanceamento(fbBNovo);
        a.setBalanceamento(fbANovo);

        return a;
    }

    // rotação simples a direita 
    private No rotacaoDireitaSimples(No b) {
        int fbB = b.getBalanceamento();
        No a = b.getFilhoEsquerda();
        int fbA = a.getBalanceamento();

        No paiDeB = b.getPai();
        boolean bEraFilhoEsquerdo = (paiDeB != null) && (paiDeB.getFilhoEsquerda() == b);

        b.setFilhoEsquerda(a.getFilhoDireita());
        if (a.getFilhoDireita() != null) {
            a.getFilhoDireita().setPai(b);
        }

        a.setFilhoDireita(b);
        b.setPai(a);

        a.setPai(paiDeB);
        if (paiDeB == null) {
            raiz = a;
        } else if (bEraFilhoEsquerdo) {
            paiDeB.setFilhoEsquerda(a);
        } else {
            paiDeB.setFilhoDireita(a);
        }

        int fbBNovo = fbB - 1 - Math.max(fbA, 0);
        int fbANovo = fbA - 1 + Math.min(fbBNovo, 0);
        b.setBalanceamento(fbBNovo);
        a.setBalanceamento(fbANovo);

        return a;
    }

    // rotação dupla a direita 
    private No rotacaoDuplaDireita(No b) {
        rotacaoEsquerdaSimples(b.getFilhoEsquerda());
        return rotacaoDireitaSimples(b);
    }

    // rotação dupla a esquerda 
    private No rotacaoDuplaEsquerda(No b) {
        rotacaoDireitaSimples(b.getFilhoDireita());
        return rotacaoEsquerdaSimples(b);
    }

    // FB
    @Override
    protected String rotuloNo(No no) {
        return no.getElemento() + "[" + no.getBalanceamento() + "]";
    }
}