package JOJOLands.JOJO;

public class Vertex<T extends Comparable<T>, N extends Comparable<N>> {
    T vertexInfo;
    int indeg;
    int outdeg;
    Vertex<T, N> nextVertex;
    Edge<T, N> firstEdge;

    public Vertex() {
        vertexInfo = null;
        indeg = 0;
        outdeg = 0;
        nextVertex = null;
        firstEdge = null;
    }

    public Vertex(T vInfo, Vertex<T, N> next) {
        vertexInfo = vInfo;
        indeg = 0;
        outdeg = 0;
        nextVertex = next;
        firstEdge = null;
    }

    public T getVertexInfo() {
        return vertexInfo;
    }

    public int getIndegree() {
        return indeg;
    }

    public int getOutdegree() {
        return outdeg;
    }

    public Vertex<T, N> getNextVertex() {
        return nextVertex;
    }

    public Edge<T, N> getFirstEdge() {
        return firstEdge;
    }

    public void setFirstEdge(Edge<T, N> edge) {
        firstEdge = edge;
    }
}