package buscaminas_solver;

import java.util.ArrayList;

public class Solver {
	final int BUSCAMINAS_SIZE=9;
	
	public Celda[][] clickBlanco(Celda[][]tablero,int col,int fila){   //muestro los adyacentes
		ArrayList<Celda>lista_ady=new ArrayList<Celda>();
		/*for(int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++) {
				if((j==col-1 || j==col+1 || j==col) && (i==fila-1 || i==fila+1 ||i==fila) ) {
					//System.out.println("col: "+j+ " fila: "+i);
					tablero[i][j].setVista(true);
					
				}
			}
		}*/

		lista_ady=get_ady(tablero, col, fila);
		for(Celda ady:lista_ady ) {
			int col1=0;
			int fila1=0;
			col1= ady.getCol();
			fila1=ady.getFila();
			tablero[col1][fila1].setVista(true);
		}
				
		return tablero;
		
	}
	public Celda[][] clickNum(Celda[][]tablero,int col,int fila){ //muestro los blancos adyacentes
		ArrayList<Celda>lista_ady=new ArrayList<Celda>();
		//System.out.println("col: "+col+ " fila: "+fila);
		/*for(int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++) {
				if((j==col-1 || j==col+1 || j==col) && (i==fila-1 || i==fila+1 ||i==fila) ) {
					if(tablero[i][j].getValue()==0 || (col==j && fila==i)) {
						//System.out.println("Valor: "+tablero[i][j].getValue());
						tablero[i][j].setVista(true);
					}

				}
			}
		}*/
		lista_ady=get_ady(tablero, col, fila);
		for(Celda ady:lista_ady ) {
			int col1=0;
			int fila1=0;
			col1= ady.getCol();
			fila1=ady.getFila();
			if(tablero[col1][fila1].getValue()==0 ) {
				//System.out.println("Valor: "+tablero[i][j].getValue());
				tablero[col1][fila1].setVista(true);
			}
			
		}
		tablero[col][fila].setVista(true);
			
		return tablero;
		
	}
	public ArrayList<Celda> checkBlancos(Celda[][] tablero) {
		ArrayList<Celda>listaVistos=new ArrayList<Celda>();
		for (int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++){
				if(tablero[i][j].isVista()==true) {
					listaVistos.add(new Celda(tablero[i][j].getValue(),tablero[i][j].getCol(),tablero[i][j].getFila()));	
				}
			}
		}
		return listaVistos;
	}
		
	//busco los numeros del tablero
	public ArrayList<Celda> checkNumbs(Celda[][] tablero){ 
		ArrayList<Celda>lista_Num=new ArrayList<Celda>();
		ArrayList<Celda>lista_ady=new ArrayList<Celda>();
		for (int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++){
				if((tablero[i][j].isVista()==true) && (tablero[i][j].getValue()>0)) {
					lista_Num.add(new Celda(tablero[i][j].getValue(),tablero[i][j].getCol(),tablero[i][j].getFila()));
					}
				}
			}
		
		return lista_Num;
	}
	public Celda[][]  clickNext(Celda[][] tablero){
		ArrayList<Celda> lista_Num= new ArrayList<Celda>();
		lista_Num=checkNumbs(tablero);
		boolean ady=false;
		int ncol = 0,nfila=0;
		for(int i=0;i<lista_Num.size();i++) {
			ady=verificarAdyacentesVanBombas(tablero,lista_Num.get(i).getCol(), lista_Num.get(i).getFila());
			if(ady==true) {
				ncol= lista_Num.get(i).getCol();
				nfila=lista_Num.get(i).getFila();
				tablero=colocarBombas(tablero,ncol,nfila);
			}
		}
		return tablero;
		
	}
	private Celda[][] colocarBombas(Celda[][] tablero, int ncol, int nfila) {
		for (int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++){
				if((j==ncol-1 || j==ncol+1 || j==ncol) && (i==nfila-1 || i==nfila+1 ||i==nfila) ) {
					if(tablero[i][j].isVista()==false) {
						//tablero[i][j].setVista(true);
						tablero[i][j].setBomba(true);
					}
				}
				}
			}
		return tablero;
		
		
	}
	public boolean verificarAdyacentesVanBombas(Celda[][] tablero,int col,int fila) {
		int contador_adyacentes=0;
		int contador_bombas=0;
		boolean certeza=false;
		for (int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++){
				if((j==col-1 || j==col+1 || j==col) && (i==fila-1 || i==fila+1 ||i==fila) ) {
					if((tablero[i][j].isVista()==false) && (tablero[i][j].isBomba()==false)) {
						contador_adyacentes+=1;
					}
					if(tablero[i][j].isBomba()==true) {
						contador_bombas+=1;
					}
					
				}
			}
		}
		
		if((contador_adyacentes==tablero[fila][col].getValue()-contador_bombas)) {
			certeza=true;
		}
		
		return certeza;	
	}
	public Celda[][] verif_bombas_ady(Celda[][] tablero,int col,int fila) {
		boolean b=false;
		int contador_bombas=0;
		for (int i=0;i<BUSCAMINAS_SIZE;i++) {
			for(int j=0;j<BUSCAMINAS_SIZE;j++){
				if((j==col-1 || j==col+1 || j==col) && (i==fila-1 || i==fila+1 ||i==fila) ) {
					if(tablero[i][j].isBomba()==true) {
						contador_bombas+=1;
					}
				}}}

		if(contador_bombas==tablero[fila][col].getValue()) {
	
			//desbloquear alrededores
			for (int i=0;i<BUSCAMINAS_SIZE;i++) {
				for(int j=0;j<BUSCAMINAS_SIZE;j++){
					if((j==col-1 || j==col+1 || j==col) && (i==fila-1 || i==fila+1 ||i==fila) ) {
						if(tablero[i][j].isBomba()==false) {
							tablero[i][j].setVista(true);
						}
											
					}}}
		
		}
		return tablero;
	}
	
	public Celda[][] buscar_noBombas(Celda[][] tablero){
		ArrayList<Celda> lista_Num= new ArrayList<Celda>();
		lista_Num=checkNumbs(tablero);
		boolean encontrado=false;
		for(int i=0;i<lista_Num.size();i++) {
			tablero=verif_bombas_ady(tablero,lista_Num.get(i).getCol(), lista_Num.get(i).getFila());
				
		}
		
		return tablero;
	}
	public ArrayList<Celda> get_ady(Celda[][] tablero,int col,int fila){
		ArrayList<Celda> lista_ady= new ArrayList<Celda>();
		
		if(col-1>=0){
			lista_ady.add(tablero[col-1][fila]);
			if(fila-1>=0) {
				lista_ady.add(tablero[col-1][fila-1]);
			}
			if(fila+1<BUSCAMINAS_SIZE) {
				lista_ady.add(tablero[col-1][fila+1]);
			}
		}
		if(col+1<BUSCAMINAS_SIZE) {
			lista_ady.add(tablero[col+1][fila]);
			if(fila-1>=0) {
				lista_ady.add(tablero[col+1][fila-1]);
			}
			if(fila+1<BUSCAMINAS_SIZE) {
				lista_ady.add(tablero[col+1][fila+1]);
			}
		}
		if(fila+1<BUSCAMINAS_SIZE) {
			lista_ady.add(tablero[col][fila+1]);
		}
		if(fila-1>=0) {
			lista_ady.add(tablero[col][fila-1]);
		}
		
			
		return  lista_ady;
	}
	
}
