package arvore_rubro_negra;

public class ArvoreRubroNegra {

    private No raiz;
    private int tamanho;

    public ArvoreRubroNegra() {
        this.raiz = null;
        this.tamanho = 0;
    }

    public No getRaiz() {
        return raiz;
    }

    public int getTamanho() {
        return tamanho;
    }

 

    public boolean inserirNo(No novoNo) {

        if (novoNo == null || novoNo.getElemento() == null) {
            return false;
        }

        No pai = null;
        No atual = raiz;

        while (atual != null) {

            pai = atual;

            if (novoNo.getElemento() < atual.getElemento()) {
                atual = atual.getFilhoEsquerda();

            } else if (novoNo.getElemento() > atual.getElemento()) {
                atual = atual.getFilhoDireita();

            } else {
              
                return false;
            }
        }

        novoNo.setPai(pai);
        novoNo.setFilhoEsquerda(null);
        novoNo.setFilhoDireita(null);
        novoNo.setCor(Cor.RUBRO);

       
        if (pai == null) {
            raiz = novoNo;

        } else if (novoNo.getElemento() < pai.getElemento()) {
            pai.setFilhoEsquerda(novoNo);

        } else {
            pai.setFilhoDireita(novoNo);
        }

        tamanho++;

        corrigirInsercao(novoNo);

        return true;
    }

   
    public boolean inserir(int elemento) {
        return inserirNo(new No(elemento));
    }

  
    private void corrigirInsercao(No no) {

 
        while (no != raiz && corDoNo(no.getPai()) == Cor.RUBRO) {

            No pai = no.getPai();
            No avo = pai.getPai();

            if (pai == avo.getFilhoEsquerda()) {

                No tio = avo.getFilhoDireita();

                
                if (corDoNo(tio) == Cor.RUBRO) {

                    pai.setCor(Cor.NEGRO);
                    tio.setCor(Cor.NEGRO);
                    avo.setCor(Cor.RUBRO);

                    no = avo;

                } else {

                    if (no == pai.getFilhoDireita()) {

                        no = pai;
                        rotacaoEsquerda(no);

                        pai = no.getPai();
                        avo = pai.getPai();
                    }

                    
                    pai.setCor(Cor.NEGRO);
                    avo.setCor(Cor.RUBRO);

                    rotacaoDireita(avo);
                }

            } else {

               
                No tio = avo.getFilhoEsquerda();

                /
                if (corDoNo(tio) == Cor.RUBRO) {

                    pai.setCor(Cor.NEGRO);
                    tio.setCor(Cor.NEGRO);
                    avo.setCor(Cor.RUBRO);

                    no = avo;

                } else {

                    if (no == pai.getFilhoEsquerda()) {

                        no = pai;
                        rotacaoDireita(no);

                        pai = no.getPai();
                        avo = pai.getPai();
                    }

                    /*
                     * CASO 3 espelhado.
                     */
                    pai.setCor(Cor.NEGRO);
                    avo.setCor(Cor.RUBRO);

                    rotacaoEsquerda(avo);
                }
            }
        }

      
        raiz.setCor(Cor.NEGRO);
    }

   

    public boolean buscar(int elemento) {
        return buscarNo(elemento) != null;
    }

    private No buscarNo(int elemento) {

        No atual = raiz;

        while (atual != null) {

            if (elemento == atual.getElemento()) {
                return atual;
            }

            if (elemento < atual.getElemento()) {
                atual = atual.getFilhoEsquerda();
            } else {
                atual = atual.getFilhoDireita();
            }
        }

        return null;
    }

   

    public boolean remover(int elemento) {

        No noRemover = buscarNo(elemento);

        if (noRemover == null) {
            return false;
        }

        No substituto = noRemover;

        Cor corOriginal = substituto.getCor();

        No x;
        No paiDeX;

       
        if (noRemover.getFilhoEsquerda() == null) {

            x = noRemover.getFilhoDireita();
            paiDeX = noRemover.getPai();

            transplantar(
                noRemover,
                noRemover.getFilhoDireita()
            );

            if (x != null) {
                paiDeX = x.getPai();
            }

        }

        
        else if (noRemover.getFilhoDireita() == null) {

            x = noRemover.getFilhoEsquerda();
            paiDeX = noRemover.getPai();

            transplantar(
                noRemover,
                noRemover.getFilhoEsquerda()
            );

            if (x != null) {
                paiDeX = x.getPai();
            }

        }

     
        else {

         
            substituto = minimo(noRemover.getFilhoDireita());

            corOriginal = substituto.getCor();

            x = substituto.getFilhoDireita();

            if (substituto.getPai() == noRemover) {

                paiDeX = substituto;

                if (x != null) {
                    x.setPai(substituto);
                }

            } else {

                paiDeX = substituto.getPai();

                transplantar(
                    substituto,
                    substituto.getFilhoDireita()
                );

                substituto.setFilhoDireita(
                    noRemover.getFilhoDireita()
                );

                substituto
                    .getFilhoDireita()
                    .setPai(substituto);
            }

            transplantar(noRemover, substituto);

            substituto.setFilhoEsquerda(
                noRemover.getFilhoEsquerda()
            );

            substituto
                .getFilhoEsquerda()
                .setPai(substituto);

            substituto.setCor(noRemover.getCor());

            if (x != null) {
                paiDeX = x.getPai();
            }
        }

        tamanho--;

     
        if (corOriginal == Cor.NEGRO) {
            corrigirRemocao(x, paiDeX);
        }

        
        if (raiz != null) {
            raiz.setCor(Cor.NEGRO);
        }

        return true;
    }

   
    private void corrigirRemocao(No x, No pai) {

        while (x != raiz && corDoNo(x) == Cor.NEGRO) {

            if (pai == null) {
                break;
            }

           
            if (x == pai.getFilhoEsquerda()) {

                No irmao = pai.getFilhoDireita();

           
                if (corDoNo(irmao) == Cor.RUBRO) {

                    irmao.setCor(Cor.NEGRO);
                    pai.setCor(Cor.RUBRO);

                    rotacaoEsquerda(pai);

                    irmao = pai.getFilhoDireita();
                }

               
                if (
                    corDoNo(filhoEsquerdo(irmao)) == Cor.NEGRO
                    &&
                    corDoNo(filhoDireito(irmao)) == Cor.NEGRO
                ) {

                    if (irmao != null) {
                        irmao.setCor(Cor.RUBRO);
                    }

                    x = pai;
                    pai = x.getPai();

                } else {

                  
                    if (
                        corDoNo(filhoDireito(irmao))
                        == Cor.NEGRO
                    ) {

                        No filhoEsquerdo =
                            filhoEsquerdo(irmao);

                        if (filhoEsquerdo != null) {
                            filhoEsquerdo.setCor(Cor.NEGRO);
                        }

                        if (irmao != null) {

                            irmao.setCor(Cor.RUBRO);

                            rotacaoDireita(irmao);
                        }

                        irmao = pai.getFilhoDireita();
                    }

                    if (irmao != null) {
                        irmao.setCor(pai.getCor());
                    }

                    pai.setCor(Cor.NEGRO);

                    No filhoDireito =
                        filhoDireito(irmao);

                    if (filhoDireito != null) {
                        filhoDireito.setCor(Cor.NEGRO);
                    }

                    rotacaoEsquerda(pai);

                    x = raiz;
                    pai = null;
                }

            } else {

                No irmao = pai.getFilhoEsquerda();

               
                if (corDoNo(irmao) == Cor.RUBRO) {

                    irmao.setCor(Cor.NEGRO);
                    pai.setCor(Cor.RUBRO);

                    rotacaoDireita(pai);

                    irmao = pai.getFilhoEsquerda();
                }

               
                if (
                    corDoNo(filhoDireito(irmao)) == Cor.NEGRO
                    &&
                    corDoNo(filhoEsquerdo(irmao)) == Cor.NEGRO
                ) {

                    if (irmao != null) {
                        irmao.setCor(Cor.RUBRO);
                    }

                    x = pai;
                    pai = x.getPai();

                } else {

                   
                    if (
                        corDoNo(filhoEsquerdo(irmao))
                        == Cor.NEGRO
                    ) {

                        No filhoDireito =
                            filhoDireito(irmao);

                        if (filhoDireito != null) {
                            filhoDireito.setCor(Cor.NEGRO);
                        }

                        if (irmao != null) {

                            irmao.setCor(Cor.RUBRO);

                            rotacaoEsquerda(irmao);
                        }

                        irmao = pai.getFilhoEsquerda();
                    }

                   
                    if (irmao != null) {
                        irmao.setCor(pai.getCor());
                    }

                    pai.setCor(Cor.NEGRO);

                    No filhoEsquerdo =
                        filhoEsquerdo(irmao);

                    if (filhoEsquerdo != null) {
                        filhoEsquerdo.setCor(Cor.NEGRO);
                    }

                    rotacaoDireita(pai);

                    x = raiz;
                    pai = null;
                }
            }
        }

        if (x != null) {
            x.setCor(Cor.NEGRO);
        }
    }

   

    private void rotacaoEsquerda(No x) {

        if (x == null) {
            return;
        }

        No y = x.getFilhoDireita();

        if (y == null) {
            return;
        }

      
        x.setFilhoDireita(y.getFilhoEsquerda());

        if (y.getFilhoEsquerda() != null) {

            y.getFilhoEsquerda().setPai(x);
        }

        
        y.setPai(x.getPai());

       
        if (x.getPai() == null) {

            raiz = y;

        } else if (
            x == x.getPai().getFilhoEsquerda()
        ) {

            x.getPai().setFilhoEsquerda(y);

        } else {

            x.getPai().setFilhoDireita(y);
        }

       
        y.setFilhoEsquerda(x);

        x.setPai(y);
    }

    private void rotacaoDireita(No x) {

        if (x == null) {
            return;
        }

        No y = x.getFilhoEsquerda();

        if (y == null) {
            return;
        }

        
        x.setFilhoEsquerda(y.getFilhoDireita());

        if (y.getFilhoDireita() != null) {

            y.getFilhoDireita().setPai(x);
        }

       
        y.setPai(x.getPai());

        
        if (x.getPai() == null) {

            raiz = y;

        } else if (
            x == x.getPai().getFilhoDireita()
        ) {

            x.getPai().setFilhoDireita(y);

        } else {

            x.getPai().setFilhoEsquerda(y);
        }

       
        y.setFilhoDireita(x);

        x.setPai(y);
    }

   
    private Cor corDoNo(No no) {

        if (no == null) {
            return Cor.NEGRO;
        }

        return no.getCor();
    }

    private No filhoEsquerdo(No no) {

        if (no == null) {
            return null;
        }

        return no.getFilhoEsquerda();
    }

    private No filhoDireito(No no) {

        if (no == null) {
            return null;
        }

        return no.getFilhoDireita();
    }

    private void transplantar(No antigo, No novo) {

        if (antigo.getPai() == null) {

            raiz = novo;

        } else if (
            antigo == antigo.getPai().getFilhoEsquerda()
        ) {

            antigo.getPai().setFilhoEsquerda(novo);

        } else {

            antigo.getPai().setFilhoDireita(novo);
        }

        if (novo != null) {
            novo.setPai(antigo.getPai());
        }
    }

   
    private No minimo(No no) {

        while (no.getFilhoEsquerda() != null) {
            no = no.getFilhoEsquerda();
        }

        return no;
    }

   
   

    public void mostrar() {

        if (raiz == null) {

            System.out.println(
                "\nA árvore está vazia."
            );

            return;
        }

        System.out.println(
            "\nÁrvore Rubro-Negra:"
        );

        mostrarHierarquico(
            raiz,
            "",
            true
        );
    }

    private void mostrarHierarquico(
        No no,
        String prefixo,
        boolean ultimo
    ) {

        if (no == null) {
            return;
        }

        System.out.print(prefixo);

        if (ultimo) {
            System.out.print("└── ");
        } else {
            System.out.print("├── ");
        }

        System.out.println(
            no.getElemento()
            + " ("
            + nomeCor(no.getCor())
            + ")"
        );

        No esquerdo = no.getFilhoEsquerda();
        No direito = no.getFilhoDireita();

      
        if (esquerdo != null && direito != null) {

            mostrarHierarquico(
                esquerdo,
                prefixo + (ultimo ? "    " : "│   "),
                false
            );

            mostrarHierarquico(
                direito,
                prefixo + (ultimo ? "    " : "│   "),
                true
            );

        }

       
        else if (esquerdo != null) {

            mostrarHierarquico(
                esquerdo,
                prefixo + (ultimo ? "    " : "│   "),
                true
            );

        }

       
        else if (direito != null) {

            mostrarHierarquico(
                direito,
                prefixo + (ultimo ? "    " : "│   "),
                true
            );
        }
    }

    private String nomeCor(Cor cor) {

        if (cor == Cor.RUBRO) {
            return "RUBRO";
        }

        return "NEGRO";
    }
}