#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <math.h>
#include "timer.h"

#define TRUE 1
#define FALSE 0

#define TAMBUFFER 1000
#define TAMVETOR 95

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//declaração das structs
struct parametros{
	int id;
	char *caminho;
} typedef ParametrosProd;

struct parametros2{
	int id;
	int nthreadsCons;
} typedef ParametrosCons;

//declaração da variavel Mutex    									-------> GLOBAIS <--------
pthread_mutex_t mutex;

//declaração das variaveis de condição
pthread_cond_t cond_cons, cond_prod, cond;

char buffer[TAMBUFFER];
int letras[TAMVETOR];

int cont=0;

int acabou;

int nelementos = 0;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void barreira(int nthreads){
	static int contador = 0;
	pthread_mutex_lock(&mutex);
	contador++;
	if(contador == nthreads){
		pthread_cond_broadcast(&cond);
		contador = 0;
	}
	else{
		pthread_cond_wait(&cond, &mutex);
	}
	pthread_mutex_unlock(&mutex);
}
void contagem(char letraLida){



	if(     letraLida == 'A' || letraLida == 'B' || letraLida == 'C' || letraLida == 'D' || letraLida == 'E' || 
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
			letraLida == '4' || letraLida == '5' || letraLida == '6' || letraLida == '7' || letraLida == '8' || letraLida == '9') {

		pthread_mutex_lock(&mutex);
		letras[(int)letraLida - 32]++;
		pthread_mutex_unlock(&mutex);
	}
	


}


void *produtora(void *args){

	//declarações
	FILE *entrada;
	ParametrosProd *arg = args;
	int in = 0;
	int i;
	char letraLida;


	args = NULL;
	free(args);

	entrada = fopen(arg->caminho, "r");
	if(!entrada)
		printf("Erro ao abrir o aqruivo de entrada\n");

	while(!feof(entrada)){
			pthread_mutex_lock(&mutex);

			while(cont != 0){

				printf("vai bloquear produtora\n");
				pthread_cond_broadcast(&cond_cons);
				pthread_cond_wait(&cond_prod, &mutex);
				printf("desbloqueou produtora\n");
				
			}
			fgets(buffer, TAMBUFFER, entrada);
			if(feof(entrada)){
				buffer[strlen(buffer)] = EOF;
				buffer[strlen(buffer)+1] ='\0';
			}

			for(i = strlen(buffer); i<TAMBUFFER; i++)
				buffer[i] = '\0';

			nelementos = strlen(buffer);
			cont = strlen(buffer);


			pthread_mutex_unlock(&mutex);


	}
	printf("chegou no final da produtora\n");
	fclose(entrada);
	pthread_cond_broadcast(&cond_cons);
}

void *consumidora(void *args){
	
	ParametrosCons *arg = (ParametrosCons *)args;

	char elemento;

	int out, i, j;

	int contador = 0;

	int achouFim = 0;

	args = NULL;
	free(args);

	if(arg->id == arg->nthreadsCons - 1 ){
		contador = (trunc(TAMBUFFER/arg->nthreadsCons) + (TAMBUFFER % arg->nthreadsCons));
	}
	else{
		contador = trunc(TAMBUFFER/arg->nthreadsCons);
	}

	out = arg->id * (trunc(TAMBUFFER/arg->nthreadsCons));

	pthread_mutex_lock(&mutex);

	while(acabou == FALSE){

		while(cont == 0 && acabou == FALSE){
			printf("bloqueou consumidora %d\n", arg->id);
			pthread_cond_signal(&cond_prod);
			pthread_cond_wait(&cond_cons, &mutex);
		}	
		printf("desbloqueou consumidora %d\n", arg->id);

		pthread_mutex_unlock(&mutex);

		for (i = 0; i < contador; i++){
			pthread_mutex_lock(&mutex);
			if(out < nelementos){

					elemento = buffer[out];
					printf("%c\n", elemento);

					pthread_mutex_unlock(&mutex);

					if(elemento == EOF){
						achouFim = 1;
					}
					contagem(elemento);
					//
					barreira(arg->nthreadsCons);
					//
					pthread_mutex_lock(&mutex);
					cont--;
					printf("cont == %d\n", cont);
					pthread_mutex_unlock(&mutex);
			}
			out++;
			pthread_mutex_unlock(&mutex);
		}


		if(achouFim == 1){
			pthread_mutex_lock(&mutex);
			acabou = TRUE;
			pthread_mutex_unlock(&mutex);
		}
		//
		barreira(arg->nthreadsCons);
		//

		out = arg->id * (trunc(TAMBUFFER/arg->nthreadsCons));
		pthread_mutex_lock(&mutex);

	}
	pthread_mutex_unlock(&mutex);
	//printf("chegou no final da consumidora %d\n", arg->id);
}

int main( int argc, char *argv[ ] ){
	
	double tempoTotal, tempoLeituraArquivo, tempoProcessamento, tempoEscrita;
	double tempofinalTotal, tempofinalLeituraArquivo, tempofinalProcessamento, tempofinalEscrita;

	//declarações de ponteiros
	FILE *saida;
	ParametrosProd *args;
	ParametrosCons *args2;

	int NCONSUMIDORES, NTHREADS;

	int i;

	//caminhos dos arquivos
	char caminhoEntrada[200];
	char caminhoSaida[200];


	//declarações dos ponteiros que serão os vetores das threads
	pthread_t *tid_sistema;
	pthread_t *tid_sistema2;

	GET_TIME(tempoTotal);

	//inicializa vetor que armazena a quantidade de letras
	for(i=0; i<TAMVETOR; i++){
		letras[i] = 0;
	}

	//variavel que diz se as threads consumidoras vão continuar executando na proxma vez
	acabou  = FALSE;


	pthread_mutex_init(&mutex, NULL);
	pthread_cond_init(&cond_prod, NULL);
	pthread_cond_init(&cond_cons, NULL);
	pthread_cond_init(&cond, NULL);


	strcpy(caminhoEntrada,argv[1]);
	strcpy(caminhoSaida, argv[2]);
	NTHREADS = atoi(argv[3]);
	NCONSUMIDORES = NTHREADS-1;

	tid_sistema = malloc(sizeof(pthread_t));
	tid_sistema2 = malloc(sizeof(pthread_t) * NCONSUMIDORES);

	strcat(caminhoSaida,".csv");

	
	GET_TIME(tempoLeituraArquivo);
	
	args = malloc(sizeof(ParametrosProd));
	args->id = 0;
	args->caminho = caminhoEntrada;
	pthread_create(&tid_sistema[0], NULL, produtora, (void*)args);	

	GET_TIME(tempofinalLeituraArquivo);


	GET_TIME(tempoProcessamento);
	for(i = 0; i<NCONSUMIDORES; i++ ){

		args2 = malloc(sizeof(ParametrosCons));
		args2->id = i;
		args2->nthreadsCons = NCONSUMIDORES;

		pthread_create(&tid_sistema2[i], NULL, consumidora, (void *)args2);
	}
	GET_TIME(tempofinalProcessamento);


	pthread_join(tid_sistema[0], NULL);

	for(i=0; i<NCONSUMIDORES; i++)
		pthread_join(tid_sistema2[i], NULL);




	saida = fopen(caminhoSaida, "w");
	GET_TIME(tempoEscrita);
	if(!saida)
		printf("erro ao criar o arquivo de saida\n");

		fprintf(saida, "%s, %s\n", "Caractere", "Qtde");
		for(i=0; i<TAMVETOR;i++){
			if(letras[i] != 0){
				fprintf(saida, "%c,         %d\n", i+32, letras[i]);
			}
		}
	GET_TIME(tempofinalEscrita);

	GET_TIME(tempofinalTotal);
	
	fclose(saida);



	tempofinalTotal = tempofinalTotal-tempoTotal;
	tempofinalLeituraArquivo = tempofinalLeituraArquivo - tempoLeituraArquivo;
	tempofinalProcessamento = tempofinalProcessamento - tempoProcessamento;
	tempofinalEscrita = tempofinalEscrita - tempoEscrita;

	printf("Tempo total -----> %lf\n", tempofinalTotal);
	printf("Tempo de Leitura de arquivo -----> %lf\n", tempofinalLeituraArquivo);
	printf("Tempo de processamento -----> %lf\n", tempofinalProcessamento);
	printf("Tempo de escrita -----> %lf\n", tempofinalEscrita);

	pthread_exit (NULL);	

	pthread_mutex_destroy(&mutex);
  	pthread_cond_destroy(&cond_cons);
  	pthread_cond_destroy(&cond_prod);
  	pthread_cond_destroy(&cond);



}