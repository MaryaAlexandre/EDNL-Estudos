package grafos;

public class Aresta {
    private Object elemento;
    private Vertice origem;
    private Vertice destino;
    private boolean direcionada;

    public Aresta(Object elemento, Vertice origem, Vertice destino, boolean direcionada) {
        this.elemento = elemento;
        this.origem = origem;
        this.destino = destino;
        this.direcionada = direcionada;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public boolean isDirecionada() {
        return direcionada;
    }

    @Override
    public String toString() {
        String seta = direcionada ? " -> " : " -- ";
        String rotulo = (elemento != null) ? " [" + elemento + "]" : "";
        return "(" + origem + seta + destino + ")" + rotulo;
    }
}