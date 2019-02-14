package Estructuras;

public class NodoAdy {
	private NodoVert vertice;
	private NodoAdy sigAdyacente;
	private double etiqueta;

	public NodoAdy() {
		this.vertice = null;
		this.sigAdyacente = null;
		this.etiqueta = 0;
	}

	public NodoAdy(NodoVert vertice, double etiqueta) {
		this.vertice = vertice;
		this.etiqueta = etiqueta;
	}

	public NodoVert getVertice() {
		return this.vertice;
	}

	public NodoAdy getSigAdyacente() {
		return this.sigAdyacente;
	}

	public double getEtiqueta() {
		return this.etiqueta;
	}

	public void setVertice(NodoVert nodV) {
		this.vertice = nodV;
	}

	public void setSigAdyacente(NodoAdy nodA) {
		this.sigAdyacente = nodA;
	}

	public void setEtiqueta(double etiq) {
		this.etiqueta = etiq;
	}

}
