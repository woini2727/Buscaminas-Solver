package buscaminas_solver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main {
	final static int  BUSCAMINAS_SIZE=9;	
	public Main() {
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		final int BUSCAMINAS_SIZE=9;
		
		Main game=new Main(); 	
		Celda [][] tablero= new Celda[BUSCAMINAS_SIZE][BUSCAMINAS_SIZE];
		
		//---Cargamos el juego desde un TXT---//
		File myObj = new File("ejemplo1.txt");
		Scanner myReader = new Scanner(myObj);
		int cont_i=0;
		int cont_j=0; 
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        String [] words = data.split("\t");
	        for (String palabra : words ) {
	        	//System.out.println(palabra);
	        	tablero[cont_i][cont_j]=new Celda(Integer.parseInt(palabra),cont_j,cont_i);
	        	cont_j+=1;
	        }
	        cont_i+=1;
	        cont_j=0;
	      }

	    //	Jugar
	    Solver solver= new Solver();
	    ArrayList<Celda>listaVistos=new ArrayList<Celda>();
	    boolean terminar=false;
	    boolean stop_check=false;
	    int colum = new Random().nextInt(9);
    	int fila=new Random().nextInt(9);
    	int value=tablero[fila][colum].getValue();
    	
    	//-------------------------------- JUEGO -----------------------------------------///
    	if (value==-1) {
    		terminar=true;
    		System.out.println("-- GAME OVER --");
    	}else if (value==0){
    		tablero=solver.clickBlanco(tablero, colum, fila);	
    	}else {
    		tablero=solver.clickNum(tablero, colum, fila);
    	}
    	
    	System.out.println(" ");
    	//antes de volver a jugar check
    	int n=0;
    	while(n<6) {
    		listaVistos=solver.checkBlancos(tablero);
    		for (Celda celdaVista: listaVistos) {
    			if(celdaVista.getValue()==0) {
    				tablero=solver.clickBlanco(tablero, celdaVista.getCol(),celdaVista.getFila());
    			}
    		}
    		n+=1;
    	}
    	imprimir_tablero(tablero);
    	//nuevo valor de value dependiendo 100% de certeza de bomba
    	int cont=0;
    	cont=contar_tablero(tablero);
    	boolean cambio2=true,cambio=true;
    	int cont2=0;
    	
    	//---------------------------------LOOP----------------------------------------------------------------////
	    while(terminar==false) {
	    	
	    	//agrego las bombas
	    	cambio=true;
	    	while(cambio) {
	    		tablero=solver.clickNext(tablero);
	    		System.out.println(" ");
	    		imprimir_tablero(tablero);
	    		if(cont==contar_tablero(tablero)) {
	    			cambio=false;
	    		}
	    		cont=contar_tablero(tablero);
	    	}
	    	//clickeo las 100 no Bombas, clicknumo blanco y despues check blancos
	    	cambio2=true;
	    	cont=0;
	    	while(cambio2) {
		    	tablero=solver.buscar_noBombas(tablero);
		    	System.out.println(" ");
		    	//imprimir_tablero(tablero);
		        //antes de volver a jugar check
		    	int n1=0;
		    	while(n1<6) {
		    		listaVistos=solver.checkBlancos(tablero);
		    		for (Celda celdaVista: listaVistos) {
		    			if(celdaVista.getValue()==0) {
		    				tablero=solver.clickBlanco(tablero, celdaVista.getCol(),celdaVista.getFila());
		    			}
		    		}
		    		n1+=1;
		    	}
		    	System.out.println(" ");
		    	imprimir_tablero(tablero);
		    	if(cont==contar_tablero(tablero)) {
		    		cambio2=false;
	    		}
	    		cont=contar_tablero(tablero);
	    	}// }}
	    	
	    	
	    	
	    	if(cont2>=cont) {
	    		terminar=true;
	    	}else {
	    		cont2=cont;
	    	}
	    }
	      
	}
	public static void imprimir_tablero(Celda[][]tablero) {
		//---listamos el tablero---//
	     
	      for (int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0; j<BUSCAMINAS_SIZE;j++) {
				//System.out.print(tablero[i][j].getValue());
				if(tablero[i][j].isVista()) {
					System.out.print(" "+tablero[i][j].getValue()+" ");
				}else if(tablero[i][j].isBomba()) {
					System.out.print(" B ");
				}
				else {
					System.out.print(" - ");
				}
				
				
			}
			System.out.println("\n");
		}
	}
	public static int contar_tablero(Celda[][]tablero) {
		int contador=0; 
		for (int i=0;i<BUSCAMINAS_SIZE;i++) {
				for(int j=0; j<BUSCAMINAS_SIZE;j++) {
					if(tablero[i][j].isVista()||tablero[i][j].isBomba()) {
						contador+=1;
					}
					}
			}
		return contador;
	}
	

}
