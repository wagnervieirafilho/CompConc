import java.util.Random;
import java.io.IOException;

class Assentos{                     			// recursos compartilhados
	private int[] t_Assentos;    		// Estrutura que representa todos os assentos
	private static int n;				// Quantidade de assentos

	Assentos(int n){
		int i;
		this.n = n;
		t_Assentos = new int[n]; 	// aloca a quantidade de assentos
		for(i = 0; i < this.n; i++){
			t_Assentos[i] = 0;	// inicializa o vetor de assentos
		}
	}

	public void visualizaAssentos(){	// printa todos os assentos e seus estados
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

	public int alocaAssentoLivre(int id){  		// aloca um assento aleatório
		Random random  = new Random();
		int numero, retorno;
		numero = random.nextInt(this.n);
		while(numero == 0){
			numero = random.nextInt(this.n);
		}
		retorno = alocaAssentoDado(numero, id);
		return retorno;
	}

	public int alocaAssentoDado(int numAssento, int id){		// aloca um assento escolhido pelo usuário
		int posicao = numAssento-1;
		int i;
		int assentosEsgotados = 1; // 0: não esgotado; 1: esgotado
		synchronized(this){

			for (i = 0; i < this.n; i++){			// verifica se ainda há assentos disponíveis
				if(t_Assentos[i] == 0)
					assentosEsgotados = 0;
			}

			if(assentosEsgotados == 0){							// se houver assentos disponíveis, tenta alocar
				if(t_Assentos[posicao] != 0){
					System.out.println("NEGADO! Assento já reservado");
					return 0;
				}
				else{
					t_Assentos[posicao] = id;
					System.out.println("Assento "+numAssento+" reservado com sucesso");
					return 1;
				}
			}
			else{									// se não houver assentos disponíveis, exibe mensagem
				System.out.println("Desculpe, assentos esgotados!");
				return 0;
			}
		}
	}

	public void liberaAssento(int numAssento, int id){ 	// libera assento previamente escolhido pelo usuário
		int posicao = numAssento-1;

		synchronized(this){
			if(t_Assentos[posicao] != 0){			// se o assento estiver reservado, tenta liberá-lo
				if(t_Assentos[posicao] == id){			// se o usuário que está tentando liberar o assento for o mesmo que o reservou, libera
					t_Assentos[posicao] = 0;
					System.out.println("Assento "+numAssento+" liberado com suscesso!");
				}
				else{			// se o usuário que está tentando liberar o assento não for o mesmo que reservou, exibe mensagem
					System.out.println("Cliente "+id+" não pode liberar esse assento, foi reservado por: "+t_Assentos[posicao]);
				}
			}
			else{
				System.out.println("Este assento não pode ser liberado pois não está reservado!");  // mensagem de erro caso o usuário tente liberar um assento que não está reservado
			}
		}
	}

	public int getIdAssento(int numAssento){
		int posicao = numAssento-1;
		return t_Assentos[posicao];
	}


}