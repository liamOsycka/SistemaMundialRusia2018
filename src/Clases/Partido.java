package Clases;

public class Partido {

	private String eq1, eq2, ciudad, fase;
	private int golesEq1, golesEq2;

	public Partido() {

	}

	public Partido(String eq1, String eq2, String ciudad, String fase, int golesEq1, int golesEq2) {
		this.eq1 = eq1;
		this.eq2 = eq2;
		this.ciudad = ciudad;
		this.fase = fase;
		this.golesEq1 = golesEq1;
		this.golesEq2 = golesEq2;
	}

	public String getEq1() {
		return this.eq1;
	}

	public String getEq2() {
		return this.eq2;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public String getFase() {
		return this.fase;
	}

	public int getGolesEq1() {
		return this.golesEq1;
	}

	public int getGolesEq2() {
		return this.golesEq2;
	}

	public void setEq1(String e1) {
		this.eq1 = e1;
	}

	public void setEq2(String e2) {
		this.eq2 = e2;
	}

	public void setCiudad(String city) {
		this.ciudad = city;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public void setGolesE1(int golesE1) {
		this.golesEq1 = golesE1;
	}

	public void setGolesE2(int golesE2) {
		this.golesEq2 = golesE2;
	}

	public String toString() {
		String inf = "";
		inf += "\n " + this.eq1 + " : " + this.golesEq1 + " VS  " + this.eq2 + " : " + this.golesEq2 + "\n Ciudad: "
				+ this.ciudad + "\n Fase: " + this.fase;
		return inf;
	}
}
