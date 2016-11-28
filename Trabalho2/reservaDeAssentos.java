import java.util.Scanner;
class Assentos{                     		// recursos compartilhados
	private int[] t_Assentos;    	// Estrutura que representa todos os assentos
	private int t_Assento;		// Representa apenas um assento
	private int n;			// Quantidade de assentos

	Assentos(int n){
		int i;
		this.n = n;
		t_Assentos = new int[n];
		for(i = 0; i < this.n; i++){
			t_Assentos[i] = 0;
		}
	}

	public void visualizaAssentos(){
		int i;
		int j;
		for(i = 0; i < this.n; i++){
			j = i + 1;
			if (t_Assentos[i] == 0) {
				System.out.println("Assento "+j+": LIVRE");					
			}
			else
				System.out.println("Assento "+j+": RESERVADO");
		}
	}
	public int alocaAssentoLivre(int numAssento, int id){ 
		return 1;
	}

	public void liberaAssento(int numAssento, int id){

	}


}
class Principal{
	static int NASSENTOS;

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Entre com o número de assentos:");
		NASSENTOS = sc.nextInt();
		Assentos a = new Assentos(NASSENTOS);
		a.visualizaAssentos();

	}


}