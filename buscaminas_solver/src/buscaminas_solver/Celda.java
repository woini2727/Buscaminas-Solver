package buscaminas_solver;

public class Celda {
	int value;
	int prob;
	boolean vista;
	int col;
	int fila;
	boolean bomba;
	
	public Celda(int value,int col,int fila){
		this.value=value;
		this.vista=false;
		this.bomba=false;
		this.col=col;
		this.fila=fila;
	}
	
	public boolean isBomba() {
		return bomba;
	}

	public void setBomba(boolean bomba) {
		this.bomba = bomba;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}	
	public String vista_to_str() {
		
		String s1=Boolean.toString(this.vista);
		return s1;
	}
	public int getProb() {
		return prob;
	}

	public void setProb(int prob) {
		this.prob = prob; 
	}
	public boolean isVista() {
		return vista;
	}

	public void setVista(boolean vista) {
		this.vista = vista;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
