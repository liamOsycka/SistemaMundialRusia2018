package Estructuras;

import Estructuras.Lista;

public class GrafoEtiq<T> {

	private NodoVert<T> inicio;

	public GrafoEtiq() {
		inicio = null;
	}

	public boolean insertarVertice(Object nuevoVertice) {
		boolean exito = false;
		NodoVert aux = this.ubicarVertice(nuevoVertice);
		if (aux == null) {
			this.inicio = new NodoVert(nuevoVertice, this.inicio);
			exito = true;
		}
		return exito;
	}

	private NodoVert ubicarVertice(Object verticeBuscado) {
		NodoVert aux = this.inicio;
		while (aux != null && !aux.getElem().equals(verticeBuscado)) {
			aux = aux.getSigVertice();
		}
		return aux;
	}

	public Object obtenerElemVertice(Object verticeBuscado) {
		return (ubicarVertice(verticeBuscado)).getElem();
	}

	public boolean eliminarVertice(Object elemento) {
		boolean exito = false;
		NodoVert aux = this.inicio, temp = null;
		while (aux != null && !exito) {
			if (aux.getElem().equals(elemento) && aux == this.inicio) {
				this.eliminarVerticeAux(this.inicio);
				this.inicio = this.inicio.getSigVertice();
				exito = true;
			} else {
				if (aux.getElem().equals(elemento)) {
					this.eliminarVerticeAux(aux);
					temp.setSigVertice(aux.getSigVertice());
					exito = true;
				} else {
					temp = aux;
					aux = aux.getSigVertice();
				}
			}
		}
		return exito;
	}

	private void eliminarVerticeAux(NodoVert aux) {
		NodoVert destino;
		if (aux != null) {
			NodoAdy arco = aux.getPrimerAdy();
			while (arco != null) {
				destino = arco.getVertice();
				NodoAdy arcoDestino = destino.getPrimerAdy();
				if (arcoDestino.getVertice().getElem().equals(aux.getElem())) {
					destino.setPrimerAdy(arcoDestino.getSigAdyacente());
				} else {
					NodoAdy arcoDestinoAux = arcoDestino;
					arcoDestino = arcoDestino.getSigAdyacente();
					boolean encontrado = false;
					while (arcoDestino != null && !encontrado) {
						if (arcoDestino.getVertice().getElem().equals(aux.getElem())) {
							arcoDestinoAux.setSigAdyacente(arcoDestino.getSigAdyacente());
							encontrado = true;
						}
						arcoDestino = arcoDestino.getSigAdyacente();
						arcoDestinoAux = arcoDestinoAux.getSigAdyacente();
					}
				}
				arco = arco.getSigAdyacente();
			}
		}
	}

	public boolean eliminarArco(Object origen, Object destino) {
		NodoVert nodOrigen = ubicarVertice(origen);
		NodoVert nodDestino = ubicarVertice(destino);
		boolean encontrado = false, exito = false;
		if (nodOrigen.getPrimerAdy() != null) {
			NodoAdy aux = nodOrigen.getPrimerAdy();
			if (aux.getVertice().getElem().equals(nodDestino.getElem())) {
				/*
				 * si el primer adyacente es el arco con el destino, entonces al quitarlo debo
				 * actualizar el 1er adyacente
				 */
				nodOrigen.setPrimerAdy(nodOrigen.getPrimerAdy().getSigAdyacente());
				exito = true;
			} else {
				NodoAdy aux2 = nodOrigen.getPrimerAdy().getSigAdyacente();
				while (aux2 != null && !encontrado) {
					if (aux2.getVertice().getElem().equals(nodDestino.getElem())) {
						encontrado = true;
						aux.setSigAdyacente(aux2.getSigAdyacente()); // podria agregarse verificacion de si es null
						exito = true;
					} else {
						aux2 = aux2.getSigAdyacente();
						aux = aux.getSigAdyacente();
					}
				}
			}
		}
		return exito;
	}

	public boolean insertarArco(Object origen, Object destino, double etiqueta) {
		NodoVert nodoOrigen = ubicarVertice(origen);
		NodoVert nodoDestino = ubicarVertice(destino);
		boolean exito = false;
		if (nodoOrigen != null && nodoDestino != null) {
			if (!existeArco(nodoOrigen, nodoDestino)) {
				exito = true;
				insertarArcoAux(nodoOrigen, nodoDestino, etiqueta);
				insertarArcoAux(nodoDestino, nodoOrigen, etiqueta);
			}else {
				System.out.println("El arco ya existe");
			}
		}
		return exito;
	}

	private void insertarArcoAux(NodoVert origen, NodoVert destino, double etiq) {
		NodoAdy nuevoArco = new NodoAdy();
		nuevoArco.setEtiqueta(etiq);
		nuevoArco.setVertice(destino);
		nuevoArco.setSigAdyacente(origen.getPrimerAdy());
		origen.setPrimerAdy(nuevoArco);
	}

	public boolean existeCamino(Object origen, Object destino) {
		boolean exito = false;
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert auxD = ubicarVertice(destino);
		if (aux0 != null && auxD != null) {
			Lista visitados = new Lista();
			exito = existeCaminoAux(aux0, destino, visitados);
		}
		return exito;
	}

	private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
		boolean exito = false;
		if (n != null) {
			if (n.getElem().equals(dest)) {
				exito = true;
			} else {
				vis.insertar(n.getElem(), vis.longitud() + 1);
				NodoAdy ady = n.getPrimerAdy();
				while (!exito && ady != null) {
					if (vis.localizar(ady.getVertice().getElem()) < 0) {
						exito = existeCaminoAux(ady.getVertice(), dest, vis);
					}
					ady = ady.getSigAdyacente();
				}
			}
		}
		return exito;
	}

	public boolean existeVertice(Object vertice) {
		return (ubicarVertice(vertice) != null);
	}

	private boolean existeArco(NodoVert origen, NodoVert destino) {
		boolean existe = false;
		NodoAdy arco = origen.getPrimerAdy();
		while (arco != null && !existe) {
			if (arco.getVertice().getElem().equals(destino.getElem())) {
				existe = true;
			}
			arco = arco.getSigAdyacente();
		}
		return existe;
	}

	public Lista listarEnProfundidad() {
		Lista visitados = new Lista();
		NodoVert aux = this.inicio;
		while (aux != null) {
			if (visitados.localizar(aux.getElem()) < 0) {
				listarEnProfundidadAux(aux, visitados);
			}
			aux = aux.getSigVertice();
		}
		return visitados;
	}

	private void listarEnProfundidadAux(NodoVert n, Lista vis) {
		if (n != null) {
			vis.insertar(n.getElem(), vis.longitud() + 1);
			NodoAdy ady = n.getPrimerAdy();
			while (ady != null) {
				if (vis.localizar(ady.getVertice().getElem()) < 0) {
					listarEnProfundidadAux(ady.getVertice(), vis);
				}
				ady = ady.getSigAdyacente();
			}
		}
	}

	public Lista listarEnAnchura() {
		Lista visitados = new Lista();
		NodoVert aux = this.inicio;
		while (aux != null) {
			if (visitados.localizar(aux.getElem()) < 0) {
				anchuraDesde(aux, visitados);
			}
			aux = aux.getSigVertice();
		}
		return visitados;
	}

	private Lista anchuraDesde(NodoVert vertInicial, Lista visitados) {
		Lista l = new Lista();
		l.insertar(vertInicial, l.longitud() + 1);
		NodoVert u;
		NodoAdy arco;
		while (l.longitud() != 0) {
			u = (NodoVert) l.recuperar(1);
			l.eliminar(1);
			visitados.insertar(u.getElem(), visitados.longitud() + 1);
			arco = u.getPrimerAdy();
			while (arco != null) {
				if (l.localizar(arco.getVertice()) < 0 && visitados.localizar(arco.getVertice().getElem()) < 0) {
					l.insertar(arco.getVertice(), l.longitud() + 1);
				}

				arco = arco.getSigAdyacente();
			}
		}
		return visitados;
	}

	/*
	 * Para algunos metodos de caminos utilizo un arreglo de 1 sola posicion para
	 * mantener en todo momento de la ejecucion del metodo el valor por referencia
	 * que necesito, ya que no pude hacerlo haciendo solo pasaje por valor. En el
	 * caso del camino mas corto en km, utilizo el arreglo para mantener el valor de
	 * km minimo que posee el camino encontrado, de manera que al encontrar un
	 * camino nuevo compara la distActual en km que viene sumando con la distMin que
	 * esta en el arreglo para saber si el camino es mas corto que el ya encontrado,
	 * lo mismo para verificar si se debe seguir buscando el camino cuando la
	 * distActual que viene siendo sumada ya supero al valor de distMin, por lo que
	 * no se recorre por ese camino.
	 */
	public Lista caminoMasCorto(Object origen, Object destino) {
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert aux1 = ubicarVertice(destino);
		Lista visitados = new Lista();
		Lista camino = new Lista(), caminoFinal = new Lista();
		double[] arr = new double[1];
		arr[0] = 9999;
		if (aux0 != null && aux1 != null) {
			caminoFinal = caminoMasCortoAux(aux0, destino, visitados, 0, camino, arr);
		}
		return caminoFinal;
	}

	private Lista caminoMasCortoAux(NodoVert n, Object destino, Lista visitados, double distActual, Lista camino,
			double[] distMin) {
		if (n != null) {
			visitados.insertar(n.getElem(), visitados.longitud() + 1);
			if (n.getElem().equals(destino)) {
				if (distActual < distMin[0]) {
					camino = visitados.clone();
					distMin[0] = distActual;
				}
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					distActual += arco.getEtiqueta();
					if (distActual < distMin[0]) {
						if (visitados.localizar(arco.getVertice().getElem()) < 0) {
							camino = caminoMasCortoAux(arco.getVertice(), destino, visitados, distActual, camino,
									distMin);
						}
					} else {
						System.out.println("Este camino ya es mas largo ");
					}
					distActual -= arco.getEtiqueta();
					arco = arco.getSigAdyacente();
				}

			}
			visitados.eliminar(visitados.longitud());
		}
		return camino;
	}

	public Lista caminoMenosNodos(Object origen, Object destino) {
		Lista visitados = new Lista();
		Lista camino = new Lista();
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert aux1 = ubicarVertice(destino);
		if (aux0 != null && aux1 != null) {
			camino = caminoMenosNodosAux(aux0, destino, visitados, camino);
		}
		return camino;
	}

	private Lista caminoMenosNodosAux(NodoVert n, Object destino, Lista visitados, Lista camino) {

		if (n != null) {
			visitados.insertar(n.getElem(), visitados.longitud() + 1);
			if (!n.getElem().equals(destino)) {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					if (visitados.localizar(arco.getVertice().getElem()) < 0) {
						if (camino.longitud() != 0) {
							if (visitados.longitud() < camino.longitud()) {
								camino = caminoMenosNodosAux(arco.getVertice(), destino, visitados, camino);
							}
						} else {
							camino = caminoMenosNodosAux(arco.getVertice(), destino, visitados, camino);
						}
					}
					arco = arco.getSigAdyacente();
				}
			} else {
				if (camino.longitud() == 0 || visitados.longitud() < camino.longitud()) {
					camino = visitados.clone();
				}
			}
			visitados.eliminar(visitados.longitud());
		}
		return camino;

	}

	public Cola caminosEntre(Object origen, Object destino) {
		Lista visitados = new Lista();
		Cola q = new Cola();
		Lista camino = new Lista();
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert aux1 = ubicarVertice(destino);
		if (aux0 != null && aux1 != null) {
			caminosEntreAux(aux0, destino, visitados, q);
		}
		return q;
	}

	private void caminosEntreAux(NodoVert n, Object destino, Lista visitados, Cola q) {

		if (n != null) {
			visitados.insertar(n.getElem(), visitados.longitud() + 1);
			if (n.getElem().equals(destino)) {
				q.poner(visitados.clone());
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					if (visitados.localizar(arco.getVertice().getElem()) < 0) {
						caminosEntreAux(arco.getVertice(), destino, visitados, q);
					}
					arco = arco.getSigAdyacente();
				}

			}
			visitados.eliminar(visitados.longitud());
		}

	}

	public Lista caminoMasCortoPorCiudad(Object origen, Object destino, Object medio) {
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert aux1 = ubicarVertice(destino);
		NodoVert aux2 = ubicarVertice(medio);

		Lista visitados = new Lista();
		Lista camino = new Lista();
		boolean[] arrBool = new boolean[1];
		double[] arrDoub = new double[1];
		arrBool[0] = false;
		arrDoub[0] = 9999;
		if (aux0 != null && aux1 != null && aux2 != null) {
			camino = caminoMasCortoPorCiudadAux(aux0, destino, medio, visitados, camino, arrBool, arrDoub, 0);
		}
		return camino;
	}

	private Lista caminoMasCortoPorCiudadAux(NodoVert n, Object destino, Object medio, Lista visitados, Lista camino,
			boolean[] bandera, double[] distMin, double distActual) {

		if (n != null) {
			visitados.insertar(n.getElem(), visitados.longitud() + 1);
			if (n.getElem().equals(medio)) {
				bandera[0] = true;
			}
			if (n.getElem().equals(destino)) {
				if (bandera[0]) {
					if (distActual < distMin[0]) {
						camino = visitados.clone();
						distMin[0] = distActual;
					}
				}
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null) {
					distActual += arco.getEtiqueta();
					if (distActual < distMin[0]) {
						if (visitados.localizar(arco.getVertice().getElem()) < 0) {
							camino = caminoMasCortoPorCiudadAux(arco.getVertice(), destino, medio, visitados, camino,
									bandera, distMin, distActual);
							if (arco.getVertice().getElem().equals(medio)) {
								bandera[0] = false;
							}
						}
					}
					distActual -= arco.getEtiqueta();
					arco = arco.getSigAdyacente();
				}
			}
			visitados.eliminar(visitados.longitud());
		}
		return camino;

	}

	public String toString() {
		String cad = "";

		NodoVert n = this.inicio;
		while (n != null) {
			cad += "\nVertice: " + n.getElem() + "\n";
			NodoAdy arco = n.getPrimerAdy();
			cad += "Arcos: \n";
			while (arco != null) {
				cad += "etiqueta: " + arco.getEtiqueta() + " ------> " + arco.getVertice().getElem() + "\n";
				arco = arco.getSigAdyacente();
			}
			n = n.getSigVertice();
		}
		return cad;
	}

	// modulos final
	public Lista caminoLongitudMenor(Object origen, Object destino, int max) {
		NodoVert aux0 = ubicarVertice(origen);
		NodoVert aux1 = ubicarVertice(destino);
		Lista visitados = new Lista();
		Lista camino = new Lista();
		Lista caminoFinal = new Lista();
		if (aux0 != null && aux1 != null) {
			caminoLongitudMenorAux(aux0, destino, max, visitados, camino);
		}
		return visitados;
	}

	private void caminoLongitudMenorAux(NodoVert n, Object destino, int max, Lista visitados, Lista camino) {
		if (n != null) {
			visitados.insertar(n.getElem(), visitados.longitud() + 1);

			if (n.getElem().equals(destino)) {
				if (visitados.longitud() > max) {
					visitados.eliminar(visitados.longitud());

				}
			} else {
				NodoAdy arco = n.getPrimerAdy();
				while (arco != null && visitados.localizar(destino) < 0) {
					if (visitados.localizar(arco.getVertice().getElem()) < 0) {
						caminoLongitudMenorAux(arco.getVertice(), destino, max, visitados, camino);
					}
					arco = arco.getSigAdyacente();
				}
				if (visitados.localizar(destino) < 0) {
					visitados.eliminar(visitados.longitud());
				}
			}

		}
	}

}
