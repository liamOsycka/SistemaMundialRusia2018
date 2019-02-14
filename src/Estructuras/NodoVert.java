package Estructuras;

public class NodoVert<Ciudad> {
	private Object elem;
	private NodoVert<Ciudad> sigVertice;
	private NodoAdy primerAdy;

	public NodoVert() {
		this.elem = null;
		this.sigVertice = null;
		this.primerAdy = null;
	}

	public NodoVert(Object elem, NodoVert<Ciudad> vert) {
		this.elem = elem;
		this.sigVertice = vert;
	}

	public Object getElem() {
		return this.elem;
	}

	public void setElem(Object nuevoElem) {
		this.elem = nuevoElem;
	}

	public NodoVert<Ciudad> getSigVertice() {
		return this.sigVertice;
	}

	public void setSigVertice(NodoVert<Ciudad> nodV) {
		this.sigVertice = nodV;
	}

	public NodoAdy getPrimerAdy() {
		return this.primerAdy;
	}

	public void setPrimerAdy(NodoAdy nodA) {
		this.primerAdy = nodA;
	}

}
