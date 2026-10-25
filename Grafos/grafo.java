package grafos;

import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private List<Vertice> vertices;
    private List<List<List<Aresta>>> matriz; 
    public Grafo() {
        vertices = new ArrayList<>();
        matriz = new ArrayList<>();
    }

    // acha a posicao do vertice na matriz
    private int indice(Vertice v) {
        int i = vertices.indexOf(v);
        if (i == -1) {
            throw new IllegalArgumentException("vertice nao pertence a esse grafo");
        }
        return i;
    }

    // insere vertice
    public Vertice inserirVertice(Object o) {
        Vertice v = new Vertice(o);
        vertices.add(v);

        // nova linha da matriz, uma celula por vertice ja existente (+ ele mesmo)
        List<List<Aresta>> novaLinha = new ArrayList<>();
        for (int j = 0; j < vertices.size(); j++) {
            novaLinha.add(new ArrayList<>());
        }
        matriz.add(novaLinha);

        // nova coluna em todas as linhas (inclusive a que acabou de entrar)
        for (List<List<Aresta>> linha : matriz) {
            while (linha.size() < vertices.size()) {
                linha.add(new ArrayList<>());
            }
        }

        return v;
    }

    // remove vertice e todas as arestas incidentes a ele
    public void removeVértice(Vertice v) {
        int i = indice(v);

        matriz.remove(i);
        for (List<List<Aresta>> linha : matriz) {
            linha.remove(i);
        }
        vertices.remove(i);
    }

    // insere aresta nao direcionada
    public Aresta inserirAresta(Vertice v, Vertice w, Object o) {
        int i = indice(v);
        int j = indice(w);

        Aresta e = new Aresta(o, v, w, false);
        matriz.get(i).get(j).add(e);
        if (i != j) {
            matriz.get(j).get(i).add(e); // mesma aresta aparece nos dois sentidos
        }
        return e;
    }

    // insere aresta direcionada (v -> w)
    public Aresta inserirArestaDirecionada(Vertice v, Vertice w, Object o) {
        int i = indice(v);
        int j = indice(w);

        Aresta e = new Aresta(o, v, w, true);
        matriz.get(i).get(j).add(e);
        return e;
    }

    // remove aresta
    public void removeAresta(Aresta e) {
        int i = indice(e.getOrigem());
        int j = indice(e.getDestino());

        matriz.get(i).get(j).remove(e);
        if (!e.isDirecionada() && i != j) {
            matriz.get(j).get(i).remove(e);
        }
    }

    // vertices das extremidades da aresta
    public List<Vertice> finalVertices(Aresta e) {
        List<Vertice> extremidades = new ArrayList<>();
        extremidades.add(e.getOrigem());
        extremidades.add(e.getDestino());
        return extremidades;
    }

    // vertice do outro lado da aresta, a partir de v
    public Vertice oposto(Vertice v, Aresta e) {
        if (e.getOrigem() == v) {
            return e.getDestino();
        } else if (e.getDestino() == v) {
            return e.getOrigem();
        }
        throw new IllegalArgumentException("aresta nao e incidente a esse vertice");
    }

    // v e w sao adjacentes?
    public boolean éAdjacente(Vertice v, Vertice w) {
        int i = indice(v);
        int j = indice(w);
        return !matriz.get(i).get(j).isEmpty() || !matriz.get(j).get(i).isEmpty();
    }

    // troca o valor guardado no vertice
    public void substituir(Vertice v, Object x) {
        v.setElemento(x);
    }

    // troca o valor guardado na aresta
    public void substituir(Aresta e, Object x) {
        e.setElemento(x);
    }

    // todas as arestas que tocam v (saindo, chegando ou nao direcionadas)
    public List<Aresta> arestasIncidentes(Vertice v) {
        int i = indice(v);
        List<Aresta> resultado = new ArrayList<>();

        for (List<Aresta> celula : matriz.get(i)) {
            for (Aresta e : celula) {
                if (!resultado.contains(e)) {
                    resultado.add(e);
                }
            }
        }

        for (int k = 0; k < vertices.size(); k++) {
            for (Aresta e : matriz.get(k).get(i)) {
                if (!resultado.contains(e)) {
                    resultado.add(e);
                }
            }
        }

        return resultado;
    }

    // todos os vertices do grafo
    public List<Vertice> vertices() {
        return new ArrayList<>(vertices);
    }

    // todas as arestas do grafo, sem repetir
    public List<Aresta> arestas() {
        List<Aresta> resultado = new ArrayList<>();
        for (List<List<Aresta>> linha : matriz) {
            for (List<Aresta> celula : linha) {
                for (Aresta e : celula) {
                    if (!resultado.contains(e)) {
                        resultado.add(e);
                    }
                }
            }
        }
        return resultado;
    }

    // a aresta e direcionada?
    public boolean éDirecionado(Aresta e) {
        return e.isDirecionada();
    }

    // mostra o grafo inteiro (vertices + arestas)
    public void mostrarGrafo() {
        System.out.println("Vertices (" + vertices.size() + "): " + vertices);
        System.out.println("Arestas (" + arestas().size() + "):");
        for (Aresta e : arestas()) {
            System.out.println("  " + e);
        }
    }
}