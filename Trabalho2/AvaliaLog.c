
//Avaliador do log de saída
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0

int* v;
int* v2;

int nAssentos;

// protótipos das funções
void visualizaAssentos();
void alocaAssentoLivre(int id, int assento);
void alocaAssentoDado(int id, int assento);
void liberaAssento(int id, int assento);
int comparaVetores();

int main(int argc, char const *argv[])
{
	char caminho[300];
	char linha[300];
	char elemento[10] = "";
	char chr[2];

	int id;
	int task;
	int assento;
	int linhaDoArquivo = 2;

	int i,j,k;
	int cont;
	int aux;	

	FILE *arq;

	strcpy(caminho, argv[1]);

	arq = fopen(caminho, "r");
	if(arq == NULL)
	    printf("Erro, nao foi possivel abrir o arquivo\n");
	else{
		fgets(linha, sizeof(linha), arq);		// pega a quantidade de assentos
		nAssentos = atoi(linha);			// transgorma string pra int
		v = malloc(sizeof(int) * nAssentos);	// aloca o vetor de assentos
		v2 = malloc(sizeof(int) * nAssentos);	// aloca o vetor de assentos
		for(i = 0; i < nAssentos; i++){
			v[i] = 0;		// inicializa o vetor com 0
		}

		while( (fgets(linha, sizeof(linha), arq))!=NULL ){
			
			i = 0;
			cont = 0;
			while(linha[i] != ','){
				cont++;			// conta quantos dígitos tem antes da primeira  vírgula
				i++;			
			}
			strncat(elemento, linha, cont);	// copia os dígitos para uma  substring
			id = atoi(elemento);		// converte a string para int e atribui a id.
			strcpy(elemento,"");		// zera a substring para ser reutilizada

			i = i+2;				// passa o i para para o próximo numero no arquivo
			task = linha[i] - 48;		/* faz a conversão de string para int usando a tabela ASCII (0 é 48 na ASCII)
							     e atribui o valor a task*/

			i = i+3;				// passa o i para o próximo numero do arquivo
			aux = i;

			cont = 0;
			while(linha[i] != ' '){
				cont++;			// conta quantos dígitos tem antes do espaço antes do vetor
				 i++;			
			}

			for (j = 0; j < cont; j++){		// pega a informação de qual assento foi alocado, desalocado, etc
				chr[0] = linha[aux];
				strcat(elemento, chr);
				aux++;
			}
			assento = atoi(elemento);	// atribui a assento o numero do assento
			strcpy(elemento,"");

//toda essa parte é pra copiar do arquivo a lista de assentos e atribuir ao vetor para posterior comparação
/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
			i = i+2;				// passa o i para a primeira posição do vetor do arquivo
			aux = i;
			for(j = 0; j < nAssentos; j++){

				cont = 0;
				while(linha[i] != ' '){
					cont++;			// conta quantos dígitos tem antes do espaço antes do vetor
					 i++;			
				}

				for (k = 0; k < cont; k++){		// pega a informação de qual assento foi alocado, desalocado, etc
					chr[0] = linha[aux];
					strcat(elemento, chr);
					aux++;
				}
				v2[j] = atoi(elemento);
				strcpy(elemento,"");
				i++;
				aux = i;
			}
/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
			if(task == 1){
				visualizaAssentos();
				if(comparaVetores() == FALSE)
					printf("Falha! linha %d\n", linhaDoArquivo);
				else
					printf("Sucesso!\n");
			}
			if(task == 2){
				alocaAssentoLivre(id, assento);
				if(comparaVetores() == FALSE)
					printf("Falha! linha %d\n", linhaDoArquivo);
				else
					printf("Sucesso!\n");
			}
			if(task == 3){
				alocaAssentoDado(id, assento);
				if(comparaVetores() == FALSE)
					printf("Falha! linha %d\n", linhaDoArquivo);
				else
					printf("Sucesso!\n");
			}
			if(task == 4){
				liberaAssento(id, assento);
				if(comparaVetores() == FALSE)
					printf("Falha! linha %d\n", linhaDoArquivo);
				else
					printf("Sucesso!\n");
			}
			linhaDoArquivo++;
		}

	}

	fclose(arq);
	return 0;
}

void visualizaAssentos(){
	// não altera o vetor
}
void alocaAssentoLivre(int id, int assento){
	int posicao = assento-1;
	int i;
	int assentosEsgotados = 1;

	for (i = 0; i < nAssentos; i++){
		if(v[i] == 0)
			assentosEsgotados = 0;
	}

	if(assento <= nAssentos){
		if(assentosEsgotados == 0){
			if(v[posicao] == 0)
				v[posicao] = id;
		}
	}

}
void alocaAssentoDado(int id, int assento){
	int posicao = assento-1;
	int i;
	int assentosEsgotados = 1;

	for (i = 0; i < nAssentos; i++){
		if(v[i] == 0)
			assentosEsgotados = 0;
	}

	if(assento <= nAssentos){
		if(assentosEsgotados == 0){
			if(v[posicao] == 0)
				v[posicao] = id;
		}
	}
}
void liberaAssento(int id, int assento){
	int posicao = assento-1;
	if(v[posicao] != 0){
		if(v[posicao] == id){
			v[posicao] = 0;
		}
	}

}
int comparaVetores(){
	int avalia = TRUE;
	int i;
	for (i = 0; i < nAssentos; i++){
		if(v[i] != v2[i])
			avalia = FALSE;
	}
	return avalia;
}