package Estructuras;

public class ArbolHeap {
	private Comparable[] arrHeap;
	private int ultimo;
	private static final int TAM = 50;

	// La raiz almacenada en el lugar 1
	public ArbolHeap() {
		this.arrHeap = new Comparable[TAM];
		this.ultimo = 1;
	}

	public boolean insertar(Comparable elem) {
		boolean exito = false;
		if (this.ultimo < TAM) {
			arrHeap[ultimo] = elem;
			if (ultimo != 1) {
				hacerSubir(ultimo);
			}
			ultimo++;
			exito = true;
		}
		return exito;
	}
	public boolean eliminarCima() {
		boolean exito;
		if (this.ultimo == 1) {
			exito = false;
		} else {
			this.arrHeap[1] = this.arrHeap[ultimo - 1];
			this.ultimo--;
			hacerBajar(1);
			exito = true;
		}
		return exito;
	}

	public Comparable recuperarCima() {
		return arrHeap[1];
	}

	public void hacerSubir(int pos) {
		boolean salir = false;
		while (!salir) {
			Comparable n = arrHeap[pos];
			Comparable padre = arrHeap[pos / 2];
			if (n.compareTo(padre) > 0) {
				Comparable temp = n;
				arrHeap[pos] = padre;
				arrHeap[pos / 2] = temp;
				pos = pos / 2;
				if (pos == 1) {
					salir = true;
				}
			} else {
				salir = true;
			}
		}
	}



	private void hacerBajar(int pos) {
		int hijoMenor;
		Comparable temp = this.arrHeap[pos];
		boolean salir = false;
		while (!salir) {
			hijoMenor = pos * 2;
			if (hijoMenor <= this.ultimo) {
				// temp tiene hijos(al menos uno)
				if (hijoMenor < this.ultimo) {
					// hijoMenor tiene hermano derecho
					if (this.arrHeap[hijoMenor + 1].compareTo(this.arrHeap[hijoMenor]) > 0) {
						hijoMenor++;

					}
				}
				if ((this.arrHeap[hijoMenor].compareTo(temp)) > 0) {
					this.arrHeap[pos] = this.arrHeap[hijoMenor];
					pos = hijoMenor;
				} else {
					// el padre es menor que sus hijos
					salir = true;
				}
			} else {
				// hijo menor es hoja
				salir = true;
			}
		}
		this.arrHeap[pos] = temp;

	}

	public boolean esVacio() {
		return ultimo == 1;
	}

}
