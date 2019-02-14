package Clases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Estructuras.ArbolHeap;
import Estructuras.Diccionario;
import Estructuras.Lista;

public class TablaEquipos {
	private Diccionario dicc;
	private ArbolHeap arbH;
	private PartidosHashMap partidos;
	private static final int empate = 1, ganador = 3, perdedor = 0;

	public TablaEquipos(Diccionario dicc, PartidosHashMap partidos) {
		this.dicc = dicc;
		this.partidos = partidos;
	}

	public boolean insertarEquipo(String nombrePais, String nombreDT, char grupo, int puntos, int golesF,
			int golesContra) {
		Equipo equipoNuevo = new Equipo(nombrePais, nombreDT, grupo, puntos, golesF, golesContra);
		boolean exito = dicc.insertar(nombrePais, equipoNuevo);
		if (exito) {
			addToLog("\n Equipo agregado : " + equipoNuevo.toString());
		}
		return exito;

	}

	public void listarInformacionEquipo(String nombrePais) {
		Equipo eqAux = (Equipo) dicc.obtenerInformacion(nombrePais);
		System.out.println(eqAux.toString());
		boolean listaVacia = (eqAux.getEquiposJugados()).esVacia();
		if (!listaVacia) {
			int i = 1;
			Lista lista = eqAux.getEquiposJugados();
			while (i <= lista.longitud()) {
				Equipo aux = (Equipo) lista.recuperar(i);
				System.out.println("entra while");
				System.out.println(partidos.obtenerPartido(nombrePais, aux.getPais()).toString());
				i++;
			}
		}

	}

	public void listarRango(String min, String max) {
		System.out
				.println("Rango : [ " + min + " , " + max + " ] \n Listado de equipos: " + dicc.listarRango(min, max));
	}

	public void mostrarTablaEquipos() {
		System.out.println(dicc.toString());
	}

	public void eliminarEquipo(String nombrePais) {
		System.out.println(dicc.eliminar(nombrePais));
		addToLog("\nSe ha eliminado el equipo : "+nombrePais);
	}

	public void agregarLogFinEjecucion() {
		addToLog(mostrarTablaPosiciones());
	}

	public String mostrarTablaPosiciones() {
		arbH = new ArbolHeap();
		Equipo eqAux;
		String posiciones = "";
		int puesto = 1;
		Lista listadoEquipos = listarEquipos();
		Lista listaTablaPos = new Lista();
		while (!listadoEquipos.esVacia()) {
			eqAux = (Equipo) listadoEquipos.recuperar(1);
			arbH.insertar(eqAux);
			listadoEquipos.eliminar(1);
		}
		while (!arbH.esVacio()) {
			eqAux = (Equipo) arbH.recuperarCima();
			arbH.eliminarCima();
			posiciones += puesto + " ) " + eqAux.getPais() + " Puntos: " + eqAux.getPuntos() + " DifGol: "
					+ eqAux.getDifGol() + "\n";
			puesto++;
		}
		return posiciones;
	}

	public boolean existeEquipo(String nombreEquipo) {
		return dicc.existeClave(nombreEquipo);
	}

	private Lista listarEquipos() {
		Lista equipos = new Lista();
		Lista listadoClaves = dicc.listarClaves();
		while (!listadoClaves.esVacia()) {
			Equipo eq = (Equipo) dicc.obtenerInformacion((String) listadoClaves.recuperar(1));
			equipos.insertar(eq, equipos.longitud() + 1);
			listadoClaves.eliminar(1);
		}
		return equipos;
	}

	public void agregarNuevoPartido(String equipo1, String equipo2, int golesEq1, int golesEq2) {
		Equipo eq1 = (Equipo) dicc.obtenerInformacion(equipo1);
		Equipo eq2 = (Equipo) dicc.obtenerInformacion(equipo2);
		eq1.agregarEquipoJugado(eq2);
		eq2.agregarEquipoJugado(eq1);
		if (golesEq1 > golesEq2) {
			eq1.agregarResultados(ganador, golesEq1, golesEq2);
			eq2.agregarResultados(perdedor, golesEq2, golesEq1);
		} else {
			if (golesEq1 < golesEq2) {
				eq1.agregarResultados(perdedor, golesEq1, golesEq2);
				eq2.agregarResultados(ganador, golesEq2, golesEq1);
			} else {
				eq1.agregarResultados(empate, golesEq1, golesEq2);
				eq2.agregarResultados(empate, golesEq2, golesEq1);
			}
		}
	}

	public void listarEquiposConDifGolNeg() {
		Lista equipos = listarEquipos();
		Lista listado = new Lista();
		while (!equipos.esVacia()) {
			Equipo eqAux = (Equipo) equipos.recuperar(1);
			if (eqAux.getDifGol() < 0) {
				listado.insertar(eqAux.getPais(), 1);
			}
			equipos.eliminar(1);
		}
		System.out.println("Listado de equipos con dif gol negativa : " + listado.toString());

	}

	public void modificarNombreEq(String nombreEquipo, String nuevoNombre) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		dicc.modificarClaveNodo(nombreEquipo, nuevoNombre);
		eq.setNombreEquipo(nuevoNombre);
		addToLog("\nModificacion de nombre de : "+nombreEquipo+" nuevo Nombre: "+nuevoNombre);

	}

	public void modificarDt(String nombreEquipo, String nuevoDt) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		String dtAnterior = eq.getDt();
		eq.setNombreDt(nuevoDt);
		addToLog("\nModificacion de  DT de: " + nombreEquipo + ", DT anterior: " + dtAnterior + ", nuevo DT : "
				+ nuevoDt);
	}

	public void modificarGrupo(String nombreEquipo, char nuevoGrupo) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		char grupoAnterior = eq.getGrupo();
		eq.setGrupo(nuevoGrupo);
		addToLog("\nModificacion de grupo de: " + nombreEquipo + ", grupo anterior: " + grupoAnterior
				+ ", nuevo grupo: " + nuevoGrupo);
	}

	public void modificarPuntos(String nombreEquipo, int puntos) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		int puntosAnterior = eq.getPuntos();
		eq.setPuntos(puntos);
		addToLog("\nModificacion de puntos de: " + nombreEquipo + ", puntos anteriorres: " + puntosAnterior
				+ ", nuevos puntos : " + puntos);
	}

	public void modificarGolesFavor(String nombreEquipo, int golesFavor) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		int golesAnterior = eq.getGolesFavor();
		eq.setGolesFavor(golesFavor);
		addToLog("\nModificacion de goles favor de: " + nombreEquipo + ", goles favor anteriorres: " + golesAnterior
				+ ", nuevos golesFavor : " + golesFavor);
	}

	public void modificarGolesContra(String nombreEquipo, int golesContra) {
		Equipo eq = (Equipo) dicc.obtenerInformacion(nombreEquipo);
		int golesAnterior = eq.getGolesFavor();
		eq.setGolesContra(golesContra);
		addToLog("\nModificacion de goles contra de: " + nombreEquipo + ", goles contra anteriorres: " + golesAnterior
				+ ", nuevos golesContra : " + golesContra);
	}

	public String debug() {
		return dicc.toString();
	}

	public void addToLog(String res) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true));
			bw.write(res);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
