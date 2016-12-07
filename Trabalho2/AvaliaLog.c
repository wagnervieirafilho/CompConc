
//Avaliador do log de saída
#include <stdio.h>
#include <string.h>

int main(int argc, char const *argv[])
{
	char caminho[300];
	int* v;
	int id;
	int task;
	int assento;
	int nAssentos;

	FILE *arq;

	strcpy(caminho, argv[1]);

	arq = fopen(caminho, "r");
	if(arq == NULL)
	    printf("Erro, nao foi possivel abrir o arquivo\n");
	else{
		fgets(, sizeof(info), arq)
	}

	fclose(arq);


	return 0;
}