import java.util.Random;
import java.io.IOException;

class Assentos{                     			// recursos compartilhados
	public int[] t_Assentos;    		// Estrutura que representa todos os assentos
	public static int n;			// Quantidade de assentos
	Buffer buffer;

	Assentos(int n, Buffer b){
		int i;
		this.n = n;
		t_Assentos = new int[n]; 	// aloca a quantidade de assentos
		for(i = 0; i < this.n; i++){
			t_Assentos[i] = 0;	// inicializa o vetor de assentos
		}
		this.buffer = b;
	}


	public void visualizaAssentos(int id){	// printa todos os assentos e seus estados
		int i;
		int j;
		int[] a;
		
		synchronized(this){
				
				
				for(i = 0; i < this.n; i++){
					j = i + 1;
					if (t_Assentos[i] == 0) {
						//System.out.println("Assento "+j+": LIVRE");
					}
					else{
						//System.out.println("Assento "+j+": RESERVADO");
					}
				}				
				
				this.buffer.Insere(id, 1, 0,t_Assentos);
		}

	}

	public  int[] alocaAssentoLivre(int id){  		// aloca um assento aleatório
		Random random  = new Random();
		int[] retorno = new int[2];
		int numero, aux;
		retorno[0] = 0;		// diz se foi possível alocar ou não
		retorno[1] = 0;		// diz qual assento foi alocado

		numero = random.nextInt(this.n);
		while(numero == 0){
			numero = random.nextInt(this.n);
		}
		aux = alocaAssentoDado(numero, id,2);
		if(aux != 0){
			retorno[0] = 1;
			retorno[1] = numero;
		}

		return retorno;		// retorna dois valores. Um diz se um assento foi alocado e o outro diz qual foi o assento
	}

	public int alocaAssentoDado(int numAssento, int id){		// aloca um assento escolhido pelo usuário
		int posicao = numAssento-1;
		int i;
		int assentosEsgotados = 1; // 0: não esgotado; 1: esgotado
		int a[];
		
		synchronized(this){
			
			

			for (i = 0; i < this.n; i++){			// verifica se ainda há assentos disponíveis
				if(t_Assentos[i] == 0)
					assentosEsgotados = 0;
			}
			if(numAssento <= this.n){
				if(assentosEsgotados == 0){							// se houver assentos disponíveis, tenta alocar
					if(t_Assentos[posicao] != 0){
						//System.out.println("NEGADO! Assento "+numAssento+" já reservado");
						a = t_Assentos;
						
						
						this.buffer.Insere(id, 3, numAssento, a);
						return 0;
					}
					else{
						t_Assentos[posicao] = id;
						//System.out.println("Assento "+numAssento+" reservado com sucesso para Cliente "+id);
						a = t_Assentos;
						
						
						this.buffer.Insere(id, 3, numAssento, a);
						return 1;
					}
				}
				else{									// se não houver assentos disponíveis, exibe mensagem
					//System.out.println("Desculpe, assentos esgotados!");
					a = t_Assentos;
					
					
					this.buffer.Insere(id, 3, numAssento, a);
					return 0;
				}
			}
			else{
				//System.out.println("Este assento não existe! Escolha um assento entre 1 e "+this.n+"...");
				a = t_Assentos;
				
				
				this.buffer.Insere(id, 3, numAssento, a);
				return 0;
			}

		}
	}

	public int alocaAssentoDado(int numAssento, int id, int x){		// aloca um assento escolhido pelo usuário
		int posicao = numAssento-1;
		int i;
		int assentosEsgotados = 1; // 0: não esgotado; 1: esgotado
		int[] a;
		
		synchronized(this){
			
			

			for (i = 0; i < this.n; i++){			// verifica se ainda há assentos disponíveis
				if(t_Assentos[i] == 0)
					assentosEsgotados = 0;
			}
			if(numAssento <= this.n){
				if(assentosEsgotados == 0){							// se houver assentos disponíveis, tenta alocar
					if(t_Assentos[posicao] != 0){
						//System.out.println("NEGADO! Assento "+numAssento+" já reservado");
						a = t_Assentos;
						
						
						this.buffer.Insere(id, x, numAssento, a);
						return 0;
					}
					else{
						t_Assentos[posicao] = id;
						//System.out.println("Assento "+numAssento+" reservado com sucesso para Cliente "+id);
						a = t_Assentos;
						
						
						this.buffer.Insere(id, x, numAssento, a);
						return 1;
					}
				}
				else{									// se não houver assentos disponíveis, exibe mensagem
					//System.out.println("Desculpe, assentos esgotados!");
					a = t_Assentos;
					
					
					this.buffer.Insere(id, x, numAssento, a);
					return 0;
				}
			}
			else{
				//System.out.println("Este assento não existe! Escolha um assento entre 1 e "+this.n+"...");
				a = t_Assentos;
				
				
				this.buffer.Insere(id, x, numAssento, a);
				return 0;
			}
			
		}
	}

	public void liberaAssento(int numAssento, int id){ 	// libera assento previamente escolhido pelo usuário
		int posicao = numAssento-1;
		int[] a;
		
		synchronized(this){
			
			
			if(t_Assentos[posicao] != 0){			// se o assento estiver reservado, tenta liberá-lo
				if(t_Assentos[posicao] == id){			// se o usuário que está tentando liberar o assento for o mesmo que o reservou, libera
					t_Assentos[posicao] = 0;
					//System.out.println("Assento "+numAssento+" liberado com suscesso pelo cliente "+id);
				}
				else{			// se o udsuário que está tentando liberar o assento não for o mesmo que reservou, exibe mensagem
					//System.out.println("Cliente "+id+" não pode liberar esse assento, foi reservado por: "+t_Assentos[posicao]);
				}
			}
			else{
				//System.out.println("O assento "+numAssento+" não pode ser liberado pois não está reservado!");  // mensagem de erro caso o usuário tente liberar um assento que não está reservado
			}
			
			this.buffer.Insere(id, 4, numAssento, t_Assentos);
		}
	}

	public synchronized int[] getMap(){
		return t_Assentos;
	}

}