package Estructuras;

public class NodoAVLDicc {
	private Comparable clave;
	private Object dato;
	private int altura;
	private NodoAVLDicc hijoIzq;
	private NodoAVLDicc hijoDer;

	public NodoAVLDicc() {
		this.altura = 0;
		this.hijoIzq = null;
		this.hijoDer = null;
	}

	public NodoAVLDicc(Comparable clave, Object dato) {
		this.clave = clave;
		this.dato = dato;
	}

	public Object getDato() {
		return dato;
	}

	public void setDato(Object nuevo) {
		dato = nuevo;
	}

	public int getAltura() {
		return altura;
	}

	public void recalcularAltura() {
		// altura = recalcularAltura(this);
		if (this.getIzq() != null) {
			if (this.getDer() != null) {
				altura = 1 + Math.max(this.getDer().getAltura(), this.getIzq().getAltura());
			} else {
				altura = 1 + this.getIzq().getAltura();
			}
		} else {
			if (this.getDer() != null) {
				altura = 1 + this.getDer().getAltura();
			} else {
				if (this.getDer() == null) {
					altura = 0;
				}
			}
		}
	}

	public NodoAVLDicc getIzq() {
		return hijoIzq;
	}

	public NodoAVLDicc getDer() {
		return hijoDer;
	}

	public void setIzq(NodoAVLDicc izquierdo) {
		hijoIzq = izquierdo;
		if (izquierdo != null) {
			izquierdo.recalcularAltura();
		}
		this.recalcularAltura();
	}

	public void setDer(NodoAVLDicc derecho) {
		hijoDer = derecho;
		if (derecho != null) {
			derecho.recalcularAltura();
		}
		this.recalcularAltura();
	}

	public void setClave(Comparable clave) {
		this.clave = clave;
	}

	public Comparable getClave() {
		return this.clave;
	}

}
