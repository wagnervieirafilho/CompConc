
//Avaliador do log de saída
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
	char caminho[300];
	char linha[300];
	char elemento[10] = "";

	int* v;
	int* v2;

	int id;
	int task;
	int assento;
	int nAssentos;

	int i;
	int cont;

	FILE *arq;

	strcpy(caminho, argv[1]);

	arq = fopen(caminho, "r");
	if(arq == NULL)
	    printf("Erro, nao foi possivel abrir o arquivo\n");
	else{
		fgets(linha, sizeof(linha), arq);		// pega a quantidade de assentos
		nAssentos = atoi(linha);			// transgorma string pra int
		v = malloc(sizeof(int) * nAssentos);	// aloca o vetor de assentos
		for(i = 0; i < nAssentos; i++){
			v[i] = 0;		// inicializa o vetor com 0
		}

		while( (fgets(linha, sizeof(linha), arq))!=NULL ){
			/* Aqui tem que separar os elementos e atribuir a id, task e assento*/
		}	

	}

	fclose(arq);


	return 0;
}