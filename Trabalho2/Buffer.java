
class Buffer{

	static final int N = 10; // tamanho do buffer
	private Struct[] buffer = new Struct[N];  //reserva espaco para o buffer
 	private int count=0, in=0, out=0;   //variaveis compartilhadas

 	Buffer(int nAssentos){
 		int i;
 		for( i= 0; i < N; i++){
 			buffer[i] = new Struct(nAssentos);
 		}
 	}


	
}