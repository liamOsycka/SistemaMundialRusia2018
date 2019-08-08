package Estructuras;

public class Diccionario {
	private NodoAVLDicc raiz;

	public Diccionario() {
		this.raiz = null;
	}

	public boolean insertar(Comparable clave, Object dato) {
		boolean exito = true;
		if (this.raiz == null) {
			this.raiz = new NodoAVLDicc(clave, dato);
		} else {
			exito = insertarAux(this.raiz, clave, dato);
		}
		this.raiz.recalcularAltura();
		return exito;
	}

	private boolean insertarAux(NodoAVLDicc n, Comparable clave, Object dato) {
		boolean exito = true;
		if (clave.compareTo(n.getClave()) < 0) {
			if (n.getIzq() == null) {
				n.setIzq(new NodoAVLDicc(clave, dato));
			} else {
				exito = insertarAux(n.getIzq(), clave, dato);
			}
		} else {
			if (clave.compareTo(n.getClave()) > 0) {
				if (n.getDer() == null) {
					n.setDer(new NodoAVLDicc(clave, dato));
				} else {
					exito = insertarAux(n.getDer(), clave, dato);
				}
			} else {
				if (clave.compareTo(n.getClave()) == 0) {
					exito = false;
				}
			}
		}
		n.recalcularAltura();
		this.balancearArbol(n);
		return exito;
	}

	public boolean eliminar(Comparable elem) {
		boolean exito = false;
		if (this.raiz.getClave().compareTo(elem) == 0) {
			if (this.raiz.getDer() == null && this.raiz.getIzq() == null) {
				this.raiz = null;
			} else {
				if (this.raiz.getDer() != null && this.raiz.getIzq() == null) {
					this.raiz = this.raiz.getDer();
				} else {
					if (this.raiz.getIzq() != null && this.raiz.getDer() == null) {
						this.raiz = this.raiz.getIzq();
					} else {
						System.out.println("Eliminar caso 3");
						eliminarCasoTres(this.raiz);
					}
				}
				this.raiz.recalcularAltura();
				balancearArbol(this.raiz);
			}
		} else {

			if (elem.compareTo(this.raiz.getClave()) < 0) {
				exito = eliminarAux(this.raiz.getIzq(), elem, this.raiz);
			} else {
				exito = eliminarAux(this.raiz.getDer(), elem, this.raiz);
			}
		}
		return exito;
	}

	private boolean eliminarAux(NodoAVLDicc n, Comparable elem, NodoAVLDicc padre) {
		boolean exito = false;
		if (n != null) {
			if (!exito) {
				if (elem.compareTo(n.getClave()) == 0) {
					// Verifico si el nodo es hoja
					if (n.getIzq() == null && n.getDer() == null) {
						// el nodo es hoja
						eliminarCasoUno(n, padre);
						exito = true;
						// El nodo tiene almenos un hijo
					} else {
						if (n.getDer() != null && n.getIzq() != null) {
							eliminarCasoTres(n);
						} else {
							if (n.getClave().compareTo(padre.getClave()) > 0) {
								/*
								 * el nodo N es mayor al padre, por lo tanto se encuentra a su derecha Verifico
								 * si el nodo tiene 1 hijo
								 */
								eliminarCasoDos(n, padre, true);
							} else {
								// el nodo N es menor al padre, por lo tanto se encuentra a su izquierda
								eliminarCasoDos(n, padre, false);
							}
						}
						exito = true;

					}
				} else {

					if (elem.compareTo(n.getClave()) < 0) {
						exito = eliminarAux(n.getIzq(), elem, n);
					} else {
						exito = eliminarAux(n.getDer(), elem, n);
					}
				}
			}

		}
		padre.recalcularAltura();
		balancearArbol(padre);
		return exito;
	}

	private void eliminarCasoUno(NodoAVLDicc n, NodoAVLDicc padre) {
		if (padre.getIzq() == n) {
			padre.setIzq(null);
		} else {
			padre.setDer(null);
		}
	}

	private void eliminarCasoDos(NodoAVLDicc n, NodoAVLDicc padre, boolean derecha) {
		if (derecha) {
			if (n.getDer() != null) {
				// el nodo N tiene un solo hijo derecho
				padre.setDer(n.getDer());
			} else {
				padre.setDer(n.getIzq());
			}

		} else {
			if (n.getDer() != null) {
				// el nodo N tiene un solo hijo derecho
				padre.setIzq(n.getDer());
			} else {
				padre.setIzq(n.getIzq());
			}
		}

	}

	private void eliminarCasoTres(NodoAVLDicc n) {
		NodoAVLDicc[] arrNodos = buscarCandidato(n.getIzq(), n);
		NodoAVLDicc candidato = arrNodos[0];
		NodoAVLDicc padreCandidato = arrNodos[1];
		n.setDato((candidato.getClave()));
		if (candidato.getIzq() != null) {
			eliminarCasoDos(candidato, padreCandidato, false);
		} else {
			eliminarCasoUno(candidato, padreCandidato);
		}

	}

	private NodoAVLDicc[] buscarCandidato(NodoAVLDicc n, NodoAVLDicc padre) {
		NodoAVLDicc candidato = n;
		NodoAVLDicc father = padre;
		NodoAVLDicc[] arrNodos = new NodoAVLDicc[2];

		while (candidato.getDer() != null) {
			father = candidato;
			candidato = candidato.getDer();
		}
		arrNodos[0] = candidato;
		arrNodos[1] = father;
		return arrNodos;
	}

	private void balancearArbol(NodoAVLDicc n) {
		NodoAVLDicc padre = obtenerPadre(this.raiz, n.getClave());
		int balanceN = calcularBalance(n), caidoIzquierda = 2, caidoDerecha = -2;
		if (balanceN == caidoIzquierda) {
			/* arbol caido a la izquierda */
			if ((calcularBalance(n.getIzq())) < 0) {
				/* rotacion doble izq ---> derecha */
				//System.out.println("Rotacion doble izq derecha");
				rotacionDobleIzquierdaDerecha(n, padre);
			} else {
				/* rotacion simple a derecha */
				//System.out.println("Rotacion simple a derecha");
				if (n == this.raiz) {
					this.raiz = rotacionSimpleDerecha(n);
					this.raiz.recalcularAltura();
				} else {
					NodoAVLDicc nuevaRaiz = rotacionSimpleDerecha(n);
					if (padre.getIzq() == n) {
						padre.setIzq(nuevaRaiz);
					} else {
						padre.setDer(nuevaRaiz);

					}
				}
			}
		} else {
			if (balanceN == caidoDerecha) {
				/* arbol caido a la derecha */
				if (calcularBalance(n.getDer()) > 0) {
					/* rotacion doble derecha izquierda */
					//System.out.println("rotacion doble derecha izquierda");
					rotacionDobleDerechaIzquierda(n, padre);
				} else {
					/* rotacion simple a izquierda */
					//System.out.println("Rotacion simple a izq");
					if (n == this.raiz) {
						this.raiz = rotacionSimpleIzquierda(n);
						this.raiz.recalcularAltura();
					} else {
						NodoAVLDicc nuevaRaiz = rotacionSimpleIzquierda(n);
						if (padre.getIzq() == n) {
							padre.setIzq(nuevaRaiz);
						} else {
							padre.setDer(nuevaRaiz);

						}
					}
				}
			}
		}
		n.recalcularAltura();
	}

	private int calcularBalance(NodoAVLDicc n) {
		int balance = 0;
		if ((n.getIzq() == null) && (n.getDer() == null)) {
			balance = 0;
		} else {
			if (n.getIzq() != null) {

				if (n.getDer() == null) {
					balance = n.getIzq().getAltura() + 1;
				} else {
					balance = n.getIzq().getAltura() - n.getDer().getAltura();
				}
			} else {
				if (n.getDer() != null) {
					balance = -1 - n.getDer().getAltura();
				}
			}
		}
		return balance;
	}

	private NodoAVLDicc rotacionSimpleIzquierda(NodoAVLDicc r) {
		NodoAVLDicc h, temp;
		h = r.getDer();
		temp = h.getIzq();
		h.setIzq(r);
		r.setDer(temp);
		return h;
	}

	private NodoAVLDicc rotacionSimpleDerecha(NodoAVLDicc r) {
		NodoAVLDicc h, temp;
		h = r.getIzq();
		temp = h.getDer();
		h.setDer(r);
		r.setIzq(temp);
		return h;
	}

	private void rotacionDobleDerechaIzquierda(NodoAVLDicc r, NodoAVLDicc padre) {
		r.setDer(rotacionSimpleDerecha(r.getDer()));
		if (r == this.raiz) {
			this.raiz = rotacionSimpleIzquierda(r);
		} else {
			NodoAVLDicc nuevaRaiz = rotacionSimpleIzquierda(r);
			if (padre.getIzq() == r) {
				padre.setIzq(nuevaRaiz);
			} else {
				padre.setDer(nuevaRaiz);
			}
		}
	}

	private void rotacionDobleIzquierdaDerecha(NodoAVLDicc r, NodoAVLDicc padre) {
		r.setIzq(rotacionSimpleIzquierda(r.getIzq()));
		if (r == this.raiz) {
			this.raiz = rotacionSimpleDerecha(r);
		} else {
			NodoAVLDicc nuevaRaiz = rotacionSimpleDerecha(r);
			if (padre.getIzq() == r) {
				padre.setIzq(nuevaRaiz);
			} else {
				padre.setDer(nuevaRaiz);
			}
		}
	}

	public boolean existeClave(Comparable clave) {
		boolean exito = false;
		if (this.raiz != null) {
			if (clave.compareTo(this.raiz.getClave()) < 0) {
				exito = existeClaveAux(this.raiz.getIzq(), clave);
			} else {
				if (clave.compareTo(this.raiz.getClave()) > 0) {
					exito = existeClaveAux(this.raiz.getDer(), clave);
				} else {
					if (clave.compareTo(this.raiz.getClave()) == 0) {
						exito = true;
					}
				}
			}
		}
		return exito;
	}

	private boolean existeClaveAux(NodoAVLDicc n, Comparable clave) {
		boolean exito = false;
		if (n != null && !exito) {
			if (clave.compareTo(n.getClave()) == 0) {
				exito = true;
			} else {
				if (clave.compareTo((n.getClave())) < 0) {
					exito = existeClaveAux(n.getIzq(), clave);
				} else {
					exito = existeClaveAux(n.getDer(), clave);
				}

			}
		}
		return exito;
	}

	public boolean esVacio() {
		boolean vacio = false;
		if (this.raiz == null) {
			vacio = true;
		}
		return vacio;
	}

	public Object obtenerInformacion(Comparable clave) {
		Object dato = null;
		if (existeClave(clave)) {
			if (clave.compareTo(this.raiz.getClave()) < 0) {
				dato = obtenerInfAux(this.raiz.getIzq(), clave);
			} else {
				if (clave.compareTo(this.raiz.getClave()) > 0) {
					dato = obtenerInfAux(this.raiz.getDer(), clave);
				} else {
					if (clave.compareTo(this.raiz.getClave()) == 0) {
						dato = this.raiz.getDato();
					}
				}
			}
		}
		return dato;

	}

	private Object obtenerInfAux(NodoAVLDicc n, Comparable clave) {
		Object dato = null;
		if (n != null && dato == null) {
			if (clave.compareTo(n.getClave()) == 0) {
				dato = n.getDato();
			} else {
				if (clave.compareTo((n.getClave())) < 0) {
					dato = obtenerInfAux(n.getIzq(), clave);
				} else {
					dato = obtenerInfAux(n.getDer(), clave);
				}

			}
		}
		return dato;
	}

	private NodoAVLDicc obtenerPadre(NodoAVLDicc n, Comparable clave) {
		NodoAVLDicc nodo = null;
		if (n != null) {
			if ((n.getIzq() != null && n.getIzq().getClave().compareTo(clave) == 0)
					|| (n.getDer() != null && n.getDer().getClave().compareTo(clave) == 0)) {
				nodo = n;
			} else {
				if (n.getClave().compareTo(clave) > 0) {
					nodo = obtenerPadre(n.getIzq(), clave);
				} else {
					nodo = obtenerPadre(n.getDer(), clave);
				}
			}
		}
		return nodo;
	}

	public boolean modificarClaveNodo(Comparable claveModif, Comparable claveNueva) {
		boolean exito = true;
		if (existeClave(claveNueva)) {
			exito = false;
		} else {
			NodoAVLDicc n = obtenerNodo(this.raiz, claveModif);
			n.setClave(claveNueva);
		}
		return exito;
	}

	private NodoAVLDicc obtenerNodo(NodoAVLDicc n, Comparable clave) {
		NodoAVLDicc nuevo = null;
		if (n != null) {
			if (n.getClave().compareTo(clave) == 0) {
				nuevo = n;
			} else {
				if (n.getClave().compareTo(clave) > 0) {
					nuevo = obtenerNodo(n.getIzq(), clave);
				} else {
					nuevo = obtenerNodo(n.getDer(), clave);
				}
			}
		}
		return nuevo;
	}

	public Lista listarClaves() {
		Lista listaClaves = new Lista();
		if (!esVacio()) {
			listarClavesAux(this.raiz, listaClaves);
		}
		return listaClaves;
	}

	public Lista listarRango(Comparable min, Comparable max) {
		Lista lista = new Lista();
		System.out.println("en listarRango");
		listarRangoAux(lista, this.raiz, min, max);
		return lista;
	}

	private void listarRangoAux(Lista lista, NodoAVLDicc n, Comparable min, Comparable max) {
		// listar rango de mayor a menor
		if (n != null) {

			if (min.compareTo(n.getClave()) < 0)
				listarRangoAux(lista, n.getIzq(), min, max);

			if (max.compareTo(n.getClave()) >= 0 && min.compareTo(n.getClave()) <= 0)
				lista.insertar(n.getClave(), lista.longitud() + 1);
			if (max.compareTo(n.getClave()) > 0)
				listarRangoAux(lista, n.getDer(), min, max);
		}

	}

	private void listarClavesAux(NodoAVLDicc n, Lista listado) {

		if (n != null) {
			if ((n.getIzq() == null) || (n.getIzq() == null && n.getDer() == null)) {
				listado.insertar(n.getClave(), listado.longitud() + 1);
			} else {
				listarClavesAux(n.getIzq(), listado);
				listado.insertar(n.getClave(), listado.longitud() + 1);
			}
			listarClavesAux(n.getDer(), listado);
		}
	}

	public String toString() {
		String arbol = "";

		if (this.raiz != null) {
			arbol += toStringAux(this.raiz);
		} else {
			arbol = "Arbol vacio";
		}

		return arbol;
	}

	private String toStringAux(NodoAVLDicc nodo) {
		String listado = "";

		if (nodo != null) {
			listado += "Padre: " + nodo.getClave() + " ALTURA " + nodo.getAltura();
			listado += "\n";
			if (nodo.getIzq() != null) {
				listado += "Hijo izquierdo: " + nodo.getIzq().getClave() + " ";
			} else {
				listado += "Hijo izquierdo: No tiene ";
			}
			if (nodo.getDer() != null) {
				listado += "Hijo derecho: " + nodo.getDer().getClave() + " ";
			} else {
				listado += " Hijo derecho: No tiene ";
			}

			listado += "\n";
			listado += toStringAux(nodo.getIzq());
			listado += toStringAux(nodo.getDer());
		}
		return listado;
	}

}
