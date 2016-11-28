import java.util.Scanner;
class Assentos{                     	// recursos compartilhados
	int[] t_Assentos;    	// Estrutura que representa todos os assentos
	int t_Assento;		// Representa apenas um assento
	int n;			// Quantidade de assentos

	Assentos(int n){
		int i;
		this.n = n;
		t_Assentos = new int[n];
		for(i = 0; i < this.n; i++){
			t_Assentos[i] = 0;
		}
		System.out.println("todos os "+this.n+" assentos foram inicializados com 0");
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
				System.out.println("Assento "+j+": INDISPONÍVEL");
			t_Assentos[i] = 0;

		}
	}
	public int alocaAssentoLivre(int numAssento, int id){ return 1;}


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