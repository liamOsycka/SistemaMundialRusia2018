package Estructuras;

public class Nodo {
	private Object elem;
	private Nodo enlace;

	public Nodo(Object elemento, Nodo enl) {
		this.elem = elemento;
		this.enlace = enl;
	}

	public Nodo(Object elemento) {
		this.elem = elemento;
	}

	public Object getElem() {
		return this.elem;
	}

	public void setElem(Object elemento) {
		this.elem = elemento;
	}

	public Nodo getEnlace() {
		return this.enlace;
	}

	public void setEnlace(Nodo enl) {
		this.enlace = enl;
	}
}