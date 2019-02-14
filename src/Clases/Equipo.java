package Clases;

import Estructuras.Lista;

public class Equipo implements Comparable {
	private String pais, dt;
	private Lista equiposJugados;
	private int golesFavor, golesEnContra, puntos;
	private char grupo;

	public Equipo() {

		this.equiposJugados = new Lista();
	}

	public Equipo(String nombrePais) {
		this.pais = nombrePais;
	}

	public Equipo(String pais, String dt, char grupo, int puntos, int golesF, int golesEnC) {
		this.pais = pais;
		this.dt = dt;
		this.grupo = grupo;
		this.puntos = puntos;
		this.golesFavor = golesF;
		this.golesEnContra = golesEnC;
		this.equiposJugados = new Lista();
	}

	public void agregarResultados(int puntos, int golesFavor, int golesEnContra) {
		this.puntos += puntos;
		this.golesFavor += golesFavor;
		this.golesEnContra += golesEnContra;
	}

	public void agregarEquipoJugado(Equipo eqJugado) {
		equiposJugados.insertar(eqJugado, (equiposJugados.longitud()) + 1);

	}

	public String getDt() {
		return this.dt;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public String getPais() {
		return this.pais;
	}

	public int getDifGol() {
		return (this.golesFavor - this.golesEnContra);
	}

	public Lista getEquiposJugados() {
		return this.equiposJugados;
	}
	public char getGrupo() {
		return this.grupo;
	}
	public int getGolesFavor() {
		return this.golesFavor;
	}
	public int getGolesContra() {
		return this.golesEnContra;
	}

	public void setNombreEquipo(String nombreEq) {
		this.pais = nombreEq;
	}

	public void setNombreDt(String dt) {
		this.dt = dt;
	}

	public void setGrupo(char grupo) {
		this.grupo = grupo;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public void setGolesFavor(int golesF) {
		this.golesFavor = golesF;
	}

	public void setGolesContra(int golesC) {
		this.golesEnContra = golesC;
	}

	public String toString() {
		String inf = "";
		inf += "Informacion Equipo \n Pais: " + this.pais + "\n DT: " + this.dt + "\n Grupo: " + this.grupo
				+ "\n Puntos: " + this.puntos + "\n GolesFavor: " + this.golesFavor + "\n GolesEnContra: "
				+ this.golesEnContra + "\n Diferencia de goles: " + getDifGol();
		return inf;
	}

	public boolean equals(Equipo eq) {
		boolean iguales = false;
		if (this.pais.equals(eq.getPais())) {
			iguales = true;
		}
		return iguales;
	}

	@Override
	public int compareTo(Object elem) {
		Equipo eq = (Equipo) elem;
		int comp;
		if (this.puntos == ((Equipo) elem).puntos) {
			if (this.getDifGol() == eq.getDifGol()) {
				comp = this.pais.toString().compareTo(eq.pais.toString());
			} else {
				if (this.getDifGol() > eq.getDifGol()) {
					comp = 1;
				} else {
					comp = -1;
				}
			}
		} else {
			if (this.puntos > eq.puntos) {
				comp = 1;
			} else {
				comp = -1;
			}
		}
		return comp;
	}

}