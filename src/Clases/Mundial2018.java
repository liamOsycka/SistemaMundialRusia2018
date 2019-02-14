package Clases;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import Estructuras.Diccionario;
import Clases.MapaCiudades;
import Clases.PartidosHashMap;
import Clases.TablaEquipos;
import Utiles.*;
import Estructuras.GrafoEtiq;
import Estructuras.Cola;

public class Mundial2018 {
	private static void cargarCiudad(Cola q, MapaCiudades mapa) {
		String nombreCiudad = (String) q.obtenerFrente();
		q.sacar();
		double superficie = Double.parseDouble(q.obtenerFrente().toString());
		q.sacar();
		int cantHabitantes = Integer.parseInt(q.obtenerFrente().toString());
		q.sacar();
		boolean sede = (q.obtenerFrente().toString().equals("TRUE"));
		q.sacar();
		mapa.insertarCiudad(nombreCiudad, superficie, cantHabitantes, sede);
	}

	private static void cargarEquipo(Cola q, TablaEquipos tabla) {
		String nombreEquipo = (String) q.obtenerFrente();
		q.sacar();
		String nombreDt = (String) q.obtenerFrente();
		q.sacar();
		char grupo = q.obtenerFrente().toString().charAt(0);
		q.sacar();
		int puntos = Integer.parseInt(q.obtenerFrente().toString());
		q.sacar();
		int golesFavor = Integer.parseInt(q.obtenerFrente().toString());
		q.sacar();
		int golesEnContra = Integer.parseInt(q.obtenerFrente().toString());
		q.sacar();
		tabla.insertarEquipo(nombreEquipo, nombreDt, grupo, puntos, golesFavor, golesEnContra);
	}

	public static void cargarPartido(Cola q, PartidosHashMap partidos, TablaEquipos tabla) {
		String eq1 = (String) q.obtenerFrente();
		q.sacar();
		String eq2 = (String) q.obtenerFrente();
		q.sacar();
		String ciudad = (String) q.obtenerFrente();
		q.sacar();
		String fase = (String) q.obtenerFrente();
		q.sacar();
		int golesEq1 = Integer.parseInt(q.obtenerFrente().toString());
		q.sacar();
		int golesEq2 = Integer.parseInt(q.obtenerFrente().toString());
		q.sacar();
		partidos.insertarPartido(eq1, eq2, ciudad, fase, golesEq1, golesEq2);
		tabla.agregarNuevoPartido(eq1, eq2, golesEq1, golesEq2);

	}
	public static void cargarRuta(Cola q,MapaCiudades mapa) {
		String ciudadOrigen=((String)q.obtenerFrente()).toUpperCase();
		q.sacar();
		String ciudadDestino=((String)q.obtenerFrente()).toUpperCase();
		q.sacar();
		double distancia=Double.parseDouble(q.obtenerFrente().toString());
		q.sacar();
		mapa.insertarRuta(ciudadOrigen, ciudadDestino, distancia);
	}

	public static void cargaInicial(TablaEquipos tabla, MapaCiudades mapa, PartidosHashMap partidos) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			br = new BufferedReader(new FileReader("cargaInicialMundial2018.txt"));
			String cadenaActual;
			int contadorIndice = 0;
			int inicioCadena = 2;
			Cola q = new Cola();
			while ((cadenaActual = br.readLine()) != null) {
				char tipoLinea = cadenaActual.charAt(0);
				String cad = "";
				int i = 2;
				while (cadenaActual.charAt(i) != '.') {
					char caracterActual = cadenaActual.charAt(i);
					if (caracterActual != ';') {
						cad += caracterActual;
					} else {
						q.poner(cad);
						cad = "";
					}
					i++;
				}
				q.poner(cad);
				switch (tipoLinea) {
				case 'E':
					cargarEquipo(q, tabla);
					break;
				case 'C':
					cargarCiudad(q, mapa);
					break;
				case 'P':
					cargarPartido(q, partidos, tabla);
					break;
				case 'R':
					cargarRuta(q,mapa);
					break;
				}
			}

			if (br != null)
				br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void consultasEquipos(TablaEquipos tabla) {
		char opcion;
		System.out.println("a) Listar informacion equipo");
		System.out.println("b) Listar equipos por rango : ");
		System.out.println("c) Listar equipos con dif gol negativa");
		opcion = TecladoIn.readChar();

		switch (opcion) {
		case 'a':
			System.out.println("Ingrese el nombre del equipo : ");
			String nombreEquipo = TecladoIn.readWord().toUpperCase();
			if(tabla.existeEquipo(nombreEquipo)) {
			tabla.listarInformacionEquipo(nombreEquipo);
			}else {
				System.out.println("\n el equipo no existe\n ");
			}
			break;
		case 'b':
			System.out.println("Ingrese el rango MIN: ");
			String min = TecladoIn.readWord().toUpperCase();
			System.out.println("Ingrese el rango MAX: ");
			String max = TecladoIn.readWord().toUpperCase();
			tabla.listarRango(min, max);
			break;
		case 'c':
			tabla.listarEquiposConDifGolNeg();
			break;
		}

	}
	public static void consultasViajes(MapaCiudades mapa) {
		char opcion;
		System.out.println("Ingrese el nombre de la ciudad origen");
		String origen=TecladoIn.readWord().toUpperCase();
		if(mapa.existeCiudad(origen)) {
			System.out.println("Ingrese el nombre de la ciudad destino");
			String destino=TecladoIn.readWord().toUpperCase();
			if(mapa.existeCiudad(destino)) {
				System.out.println("Seleccione que desea hacer : ");
				System.out.println("a) Camino mas corto en km\n"
						+ "b) Camino que pasa por menos Ciudades\n"
						+ "c) Todos los caminos \n"
						+ "d) Camino mas corto en km que pasa por una Ciudad\n");
				opcion=TecladoIn.readChar();
				switch(opcion) {
				case 'a':
					mapa.caminoMasCorto(origen, destino);
					break;
				case 'b':
					mapa.caminoMenosCiudades(origen, destino);
					break;
				case 'c':
					mapa.caminosTodos(origen,destino);
					break;
				case 'd':
					System.out.println("Ingrese la ciudad por la que quiere que pase el camino ");
					String ciudadMedio=TecladoIn.readWord().toUpperCase();
					if(mapa.existeCiudad(ciudadMedio)) {
						mapa.caminoMasCortoPorCiudad(origen,destino,ciudadMedio);
					}else {
						System.out.println("\nLa ciudad medio no existe");
					}
					break;
				}
			}else {
				System.out.println("\nLa ciudad destino no existe");
			}
		}else {
			System.out.println("\nLa ciudad origen no existe");
		}
		

	}

	private static void abmCiudades(MapaCiudades mapa) {
		System.out.println(
				" a)Agregar Ciudad. \n b) eliminar ciudad. \n c) Modificar Ciudad \n d) agregar Ruta entre ciudad A y ciudad B");
		char opcion = TecladoIn.readChar();
		String nombreCiudad;
		switch (opcion) {
		case 'a':
			System.out.println("Ingrese nombre de ciudad");
			nombreCiudad = TecladoIn.readWord().toUpperCase();
			if(!mapa.existeCiudad(nombreCiudad)) {
				
			System.out.println("Ingrese superficie aproximada");
			double sup = TecladoIn.readDouble();
			System.out.println("Ingrese cantidad de habitantes");
			int cantHabit = TecladoIn.readInt();
			System.out.println("La ciudad es sede del mundal ? (s=si / n=no)");
			char respuesta = TecladoIn.readChar();
			boolean sede = false;
			if (respuesta == 's') {
				sede = true;
			}
			mapa.insertarCiudad(nombreCiudad, sup, cantHabit, sede);
			}else {
				System.out.println("La ciudad no existe");
			}
			break;
		case 'b':
			System.out.println("ingrese nombre ciudad");
			nombreCiudad = TecladoIn.readWord().toUpperCase();
			if(mapa.existeCiudad(nombreCiudad)) {
				
				mapa.eliminarCiudad(nombreCiudad);
			}
			break;
		case 'c':
			System.out.println("Ingrese la ciudad a modificar");
			String ciudadModif = TecladoIn.readWord().toUpperCase();
			if (mapa.existeCiudad(ciudadModif)) {
				System.out.println("Seleccione el atributo que desea modificar");
				System.out.println(" 1) Nombre Ciudad \n 2) Superficie Aprox \n 3) Cantidad de Habit \n 4) Es sede? ");
				int opcion2 = TecladoIn.readInt();
				switch (opcion2) {
				case 1:
					System.out.println("Ingrese el nuevo nombre");
					String nuevoNombre = TecladoIn.readWord().toUpperCase();
					mapa.modificarNombre(ciudadModif, nuevoNombre);
					break;
				case 2:
					System.out.println("Ingrese la nueva superficie ");
					double sup=TecladoIn.readDouble();
					mapa.modificarSuperficie(ciudadModif, sup);
					break;
				case 3:
					System.out.println("Ingrese la cant habitantes");
					int cantHabit=TecladoIn.readInt();
					mapa.modificarCantHabit(ciudadModif, cantHabit);
					break;
				case 4:
					System.out.println("¿ La ciudad es sede ?   (s = si ||| n = no)");
					char resp=TecladoIn.readChar();
					if(resp=='s') {
						mapa.modificarSede(ciudadModif, true);
					}else {
						mapa.modificarSede(ciudadModif, false);
					}
					break;
					
				}
			} else {
				System.out.println("No existe la ciudad ingresada");
			}
			break;
		case 'd':
			System.out.println("Ingrese el nombre de la ciudad origen");
			String ciudadOrigen = TecladoIn.readWord().toUpperCase();
			System.out.println("Ingrese el nombre de la ciudad destino");
			String ciudadDestino = TecladoIn.readWord().toUpperCase();
			System.out.println("Ingrese la cantidad de km que tendra la ruta deseada");
			double km = TecladoIn.readDouble();
			mapa.insertarRuta(ciudadOrigen, ciudadDestino, km);
			break;
		}
	}

	private static void abmEquipos(TablaEquipos tabla) {
		System.out.println(" a)Agregar Equipo. \n b) eliminar Equipo. \n c) Modificar Equipo");
		char opcion = TecladoIn.readChar();
		String nombreEquipo;
		switch (opcion) {
		case 'a':
			System.out.println("Ingrese el nombre del equipo ");
			nombreEquipo = TecladoIn.readWord();
			nombreEquipo=nombreEquipo.toUpperCase();
			System.out.println("Ingrese el nombre del DT");
			String nombreDt = TecladoIn.readWord();
			nombreDt=nombreDt.toUpperCase();
			System.out.println("Ingrese el grupo ");
			char grupo = TecladoIn.readChar();
			System.out.println("Ingrese los puntos");
			int puntos = TecladoIn.readInt();
			System.out.println("ingrese los goles a favor");
			int golesFavor = TecladoIn.readInt();
			System.out.println("Ingrese los gole en contra");
			int golesContra = TecladoIn.readInt();
			tabla.insertarEquipo(nombreEquipo, nombreDt, grupo, puntos, golesFavor, golesContra);
			break;
		case 'b':
			System.out.println("Ingrese el nombre del equipo a eliminar");
			nombreEquipo = TecladoIn.readWord().toUpperCase();
			if(tabla.existeEquipo(nombreEquipo)) {
			tabla.eliminarEquipo(nombreEquipo);
			}else {
				System.out.println("\n El equipo no existe \n");
			}
			break;
		case 'c':
			System.out.println("Ingrese el equipo a modificar");
			String equipoModif = TecladoIn.readWord().toUpperCase();
			if (tabla.existeEquipo(equipoModif)) {
				System.out.println("Seleccione el atributo que desea modificar");
				System.out.println(
						" 1) Nombre equipo \n 2) Nombre DT \n 3) Grupo \n 4) Puntos \n 5) goles Favor \n 6) Goles Contra");
				int opcion2 = TecladoIn.readInt();
				switch (opcion2) {
				case 1:
					System.out.println("Ingrese el nuevo nombre de equipo");
					String nuevoNombre = TecladoIn.readWord().toUpperCase();
					tabla.modificarNombreEq(equipoModif, nuevoNombre);
					break;
				case 2:
					System.out.println("Ingrese el nombre del DT");
					String nuevoDt = TecladoIn.readWord().toUpperCase();
					tabla.modificarDt(equipoModif, nuevoDt);
					break;
				case 3:
					System.out.println("Ingrese el grupo");
					char nuevoGrupo = TecladoIn.readChar();
					tabla.modificarGrupo(equipoModif, nuevoGrupo);
					break;
				case 4:
					System.out.println("Ingrese los puntos");
					puntos=TecladoIn.readInt();
					tabla.modificarPuntos(equipoModif,puntos);
					break;
				case 5:
					System.out.println("Ingrese los goles a favor");
					golesFavor=TecladoIn.readInt();
					tabla.modificarGolesFavor(equipoModif,golesFavor);
					break;
				case 6:
					System.out.println("Ingrese los goles en contra");
					golesContra=TecladoIn.readInt();
					tabla.modificarGolesContra(equipoModif,golesContra);
					break;
				}
			}else {
				System.out.println("\n El equipo ingresado no existe\n");
			}
			break;
		}
	}

	private static int menuInicial() {
		System.out.println("Bienvenido al sistema del mundial Rusia 2018 !");
		System.out.println("Ingrese el numero correspondiente a la opcion que desea ejecutar.");
		System.out.println(
				" 1) ABM de Ciudades. \n 2) ABM de Equipos. \n 3) Altas de partidos. \n 4) Consultas sobre Equipos. \n 5) Consultas sobre Ciudades. \n 6) Consultas sobre viajes. \n 7) Mostrar la tabla de posiciones de los equipos (mayor a menor puntaje). \n 8) Mostrar sistema. \n -1) Salir del Sistema");
		int opcion = TecladoIn.readInt();
		return opcion;
	}

	public static void main(String[] args) {
		Diccionario dicc = new Diccionario();
		GrafoEtiq grafo = new GrafoEtiq();
		MapaCiudades mapa = new MapaCiudades(grafo);
		PartidosHashMap partidos = new PartidosHashMap();
		TablaEquipos tabla = new TablaEquipos(dicc, partidos);
		cargaInicial(tabla, mapa, partidos);

		int opcion = menuInicial();
		while (opcion != -1) {
			switch (opcion) {
			case 1:
				abmCiudades(mapa);
				break;
			case 2:
				abmEquipos(tabla);
				break;
			case 3:
				System.out.println("Ingrese nombre equipo 1 ");
				String eq1 = TecladoIn.readWord().toUpperCase();
				if(tabla.existeEquipo(eq1)) {
				System.out.println("Ingrese nombre equipo 2 ");
				String eq2 = TecladoIn.readWord().toUpperCase();
				if(tabla.existeEquipo(eq2)) {
					
				System.out.println("Ingrese goles hechos por : " + eq1);
				int golesEq1 = TecladoIn.readInt();
				System.out.println("Ingrese goles hechos por : " + eq2);
				int golesEq2 = TecladoIn.readInt();
				System.out.println("Ingrese la ciudad donde se disputo el partido ");
				String ciudad = TecladoIn.readWord();
				System.out.println("Ingrese la fase por la que se jugo (GRUPO/OCTAVOS/CUARTOS/SEMIFINAL/FINAL)");
				String fase = TecladoIn.readWord();
				partidos.insertarPartido(eq1, eq2, ciudad, fase, golesEq1, golesEq2);
				tabla.agregarNuevoPartido(eq1, eq2, golesEq1, golesEq2);
				}else {
					System.out.println("No existe el equipo 2");
				}
				}else {
					System.out.println("No existe el equipo 1");
				}
				break;
			case 4:
				consultasEquipos(tabla);
				break;
			case 5:
				System.out.println("Ingrese nombre ciudad");
				String nombreCiudad = TecladoIn.readWord().toUpperCase();
				if(mapa.existeCiudad(nombreCiudad)) {
				mapa.listarInformacionCiudad(nombreCiudad);
				}else {
					System.out.println("\n la ciudad ingresada no existe \n");
				}
				break;
			case 6:
				consultasViajes(mapa);
				break;
			case 7:
				System.out.println(tabla.mostrarTablaPosiciones());
				break;
			case 8:
				System.out.println("\n Equipos :\n"+tabla.debug());
				System.out.println("\n Ciudades :\n"+mapa.debug());
				System.out.println("\n Partidos :\n"+partidos.debug());
				break;
			}
			opcion = menuInicial();

		}
		
		String cadFinal="Fin Ejecucion Sistema...Estructuras:";
		cadFinal+="Equipos:"+tabla.debug();
		cadFinal+="Ciudades:"+mapa.debug();
		cadFinal+="Partidos:"+partidos.debug();
		cadFinal+="Tabla Posiciones:"+tabla.mostrarTablaPosiciones();
		addToLog(cadFinal);
		
		
		
		
	}
	public static void addToLog(String res) {
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

