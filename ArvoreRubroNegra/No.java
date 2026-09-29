package arvore_rubro_negra;

public class No {

    private Integer elemento;
    private No pai;
    private No filhoDireita;
    private No filhoEsquerda;
    private Cor cor;

    public No() {
        this.elemento = null;
        this.pai = null;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
        this.cor = Cor.RUBRO;
    }

    public No(Integer elemento) {
        this.elemento = elemento;
        this.pai = null;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
        this.cor = Cor.RUBRO;
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

    public No getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(No filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    public No getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(No filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public boolean temDoisFilhos() {
        return filhoDireita != null && filhoEsquerda != null;
    }
}