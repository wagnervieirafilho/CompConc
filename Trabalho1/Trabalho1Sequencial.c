#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include "timer.h"

#define TRUE 1
#define FALSE 0

struct letra{
	int cont;
	char letra;
	struct letra *prox;
} typedef Letra;

void main( int argc, char *argv[ ] ){
	double tempo, tempofinal;
	GET_TIME(tempo);
	FILE *entrada, *saida;
	Letra *inicio, *aux;

	int decisor = FALSE;

	char caminhoEntrada[200];
	char caminhoSaida[200];
	char letraLida = 'a';

	inicio = NULL;
	aux = NULL;

	printf("Entre com o <caminho do arquivo de entrada (incluindo extensão)> e com o <caminho do arquivo de saída (será .csv)>\n");
	scanf("%s %s", caminhoEntrada, caminhoSaida);

	strcat(caminhoSaida,".csv");

	entrada = fopen(caminhoEntrada, "r");

	if(!entrada)
		printf("Erro ao abrir o aqruivo de entrada\n");

	while (letraLida != EOF){
		
		letraLida = getc(entrada);
		if( letraLida == 'A' || letraLida == 'B' || letraLida == 'C' || letraLida == 'D' || letraLida == 'E' || 
			letraLida == 'F' || letraLida == 'G' || letraLida == 'H' || letraLida == 'I' || letraLida == 'J' || 
			letraLida == 'K' || letraLida == 'L' || letraLida == 'M' || letraLida == 'N' || letraLida == 'O' ||
			letraLida == 'P' || letraLida == 'Q' || letraLida == 'R' || letraLida == 'S' || letraLida == 'T' ||
			letraLida == 'U' || letraLida == 'V' || letraLida == 'W' || letraLida == 'X' || letraLida == 'Y' || 
			letraLida == 'Z' || letraLida == 'a' || letraLida == 'b' || letraLida == 'c' || letraLida == 'd' ||
			letraLida == 'e' || letraLida == 'f' || letraLida == 'g' || letraLida == 'h' || letraLida == 'i' ||
			letraLida == 'j' || letraLida == 'k' || letraLida == 'l' || letraLida == 'm' || letraLida == 'n' ||
			letraLida == 'o' || letraLida == 'p' || letraLida == 'q' || letraLida == 'r' || letraLida == 's' ||
			letraLida == 't' || letraLida == 'u' || letraLida == 'v' || letraLida == 'w' || letraLida == 'x' ||
			letraLida == 'y' || letraLida == 'z' || letraLida == '?' || letraLida == '!' || letraLida == '.' ||
			letraLida == ':' || letraLida == ';' || letraLida == '_' || letraLida == '-' || letraLida == '(' || 
			letraLida == ')' || letraLida == '@' || letraLida == '#' || letraLida == '$' || letraLida == '%' || 
			letraLida == '&' || letraLida == '0' || letraLida == '1' || letraLida == '2' || letraLida == '3' ||
			letraLida == '4' || letraLida == '5' || letraLida == '6' || letraLida == '7' || letraLida == '8' || 
			letraLida == '9'){

			if(inicio == NULL){
				inicio = (Letra*)malloc(sizeof(Letra));
				aux = inicio;
				aux->cont  = 1;
				aux->letra = letraLida;
				aux->prox = NULL;
			}
			else{
				aux = inicio;

				if(aux->letra == letraLida){
					aux->cont++;
					decisor = TRUE;
				}
				while(aux->prox !=NULL){
					aux = aux->prox;
					if(aux->letra == letraLida){
						aux->cont++;
						decisor = TRUE;
					}
				}


				if(decisor == FALSE){
					aux = inicio;
					while(aux->prox!=NULL)
						aux = aux->prox;
					aux->prox = (Letra*)malloc(sizeof(Letra));
					aux = aux->prox;
					aux->cont = 1;
					aux->letra = letraLida;
					aux->prox = NULL;

				}
				decisor = FALSE;
			}
		}
	}

	fclose(entrada);

	saida = fopen(caminhoSaida, "w");
	if(!saida)
		printf("erro ao criar o arquivo de saida\n");

	aux = inicio;

	fprintf(saida, "%s, %s\n", "Caractere", "Qtde");
	fprintf(saida, "%c,         %d\n", aux->letra, aux->cont);
	while(aux->prox != NULL){
		aux = aux->prox;
		fprintf(saida, "%c,         %d\n", aux->letra, aux->cont);
	}
	fclose(saida);

	//free(entrada);
	//free(saida);
	free(inicio);
	free(aux);
	GET_TIME(tempofinal);
	tempofinal = tempofinal - tempo;
	printf("%lf\n", tempofinal);



}