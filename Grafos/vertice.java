package grafos;

public class Vertice {
    private Object elemento;

    public Vertice(Object elemento) {
        this.elemento = elemento;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    @Override
    public String toString() {
        return String.valueOf(elemento);
    }
}