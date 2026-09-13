package arvore_avl;

public class No {
    private Integer elemento;
    private No pai;
    private No filhoEsquerda;
    private No filhoDireita;
    private Integer balanceamento;

    public No() {
        this.elemento = null;
        this.pai = null;
        this.filhoEsquerda = null;
        this.filhoDireita = null;
        this.balanceamento = 0;
    }

    public No(Integer elemento) {
        this.elemento = elemento;
        this.pai = null;
        this.filhoEsquerda = null;
        this.filhoDireita = null;
        this.balanceamento = 0;
    }

    public Integer getElemento() {
        return elemento;
    }

    public void setElemento(Integer elemento) {
        this.elemento = elemento;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public No getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(No filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public No getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(No filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    public Integer getBalanceamento() {
        return balanceamento;
    }

    public void setBalanceamento(Integer balanceamento) {
        this.balanceamento = balanceamento;
    }

    public boolean temUmFilho() {
        boolean temEsquerda = filhoEsquerda != null;
        boolean temDireita = filhoDireita != null;
        return temEsquerda != temDireita;
    }

    public boolean Folha() {
        return filhoEsquerda == null && filhoDireita == null;
    }
}