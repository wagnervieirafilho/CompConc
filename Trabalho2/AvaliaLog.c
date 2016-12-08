
//Avaliador do log de saída
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
	char caminho[300];
	char linha[300];
	char elemento[10] = "";
	char chr[2];

	int* v;
	int* v2;

	int id;
	int task;
	int assento;
	int nAssentos;

	int i,j;
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

			for (j = 0; j < cont; j++){
				chr[0] = linha[aux];
				strcat(elemento, chr);
				aux++;
			}
			assento = atoi(elemento);
			strcpy(elemento,"");

			printf("%d, %d, %d\n", id, task, assento);

		}		

	}

	fclose(arq);


	return 0;
}