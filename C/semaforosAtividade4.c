/* Disciplina: Computacao Concorrente */
/*Nome: Wagner Vieira*/
/*Laboratório 9*/
/*Atividade 4*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>


#define NPRODUTORES 2
#define NCONSUMIDORES 2
#define TAMBUFFER 5


//variáveis globais
sem_t slotCheio, slotVazio; // condicao
sem_t mutexProd, mutexCons; // exclusao mutua
int Buffer[TAMBUFFER];
int in = 0, out = 0;
int cont = 0; //só pra saber se o buffer ta cheio ou não a cada operação


//funções chamadas dentro das threads
void Insere (int elemento) {
	sem_wait(&slotVazio); //espera até que haja um slot vazio

	sem_wait(&mutexProd); //inicio da seção crítica dentro dos produtores

		Buffer[in] = elemento;
		in = (in + 1) % TAMBUFFER;
		cont++;
		printf("elemento inserido == %d\n", elemento);
		if(cont == TAMBUFFER)
			printf("BUFFER CHEIO, CONT == %d\n", cont);
		else
			printf("BUFFER NÃO TÁ CHEIO, CONT == %d\n", cont);

	sem_post(&mutexProd); //fim da seção crítica

	sem_post(&slotCheio); //sinaliza um slot cheio
}


int Retira (void) {
	int elemento;
	
	sem_wait(&slotCheio); //espera até que haja um slot cheio

	sem_wait(&mutexCons); //inicio da seção crítica dentro dos consumidores

		elemento = Buffer[out];
		Buffer[out] = -1;
		out = (out + 1) % TAMBUFFER;
		cont--;

		printf("BUFFER NÃO ESTÁ CHEIO, CONT == %d\n", cont);
		
		printf("elemento consumido %d\n", elemento);

	sem_post(&mutexCons); //fim da seção crítica

	sem_post(&slotVazio); //sinaliza um slot vazio

	return elemento;
}


//função executada pelas threads produtoras
void *produtor (void *threadid) {
	int id;
	id = *(int *)threadid;
	
	int elemento;

	while(1) {
		elemento = id;
		Insere(elemento);
	}
	pthread_exit(NULL);


}

//função executada pelas threads consumidoras
void *consumidor (void *threadid) {
	int id;
	int elemento;
	id = *(int *)threadid;

	while(1) {
		elemento = Retira();
		//consome o elemento....
	}
	pthread_exit(NULL);

}


int main(int argc, char *argv[]){

	pthread_t tid[NPRODUTORES];
	pthread_t tid2[NCONSUMIDORES];
	 
	int *id[NPRODUTORES], t;
	int *id2[NCONSUMIDORES];

	//inicia os semaforos
	sem_init(&mutexCons, 0, 1);
	sem_init(&mutexProd, 0, 1);
	sem_init(&slotCheio, 0, 0);
	sem_init(&slotVazio, 0, TAMBUFFER);

	//seta o id das threads produtoras
	for (t=0; t<NPRODUTORES; t++) {
	    if ((id[t] = malloc(sizeof(int))) == NULL) {
	       pthread_exit(NULL); return 1;
	    }
	    *id[t] = t+1;
  	}

  	//seta o id das threads conumidoras
  	for (t=0; t<NCONSUMIDORES; t++) {
	    if ((id2[t] = malloc(sizeof(int))) == NULL) {
	       pthread_exit(NULL); return 1;
	    }
	    *id2[t] = t+1;
  	}



  	//cria as threads produtoras
  	for (t=0; t<NPRODUTORES; t++){
  		if (pthread_create(&tid[t], NULL, produtor, (void *)id[t])) { printf("--ERRO: pthread_create()\n"); exit(-1); }
  	}

  	//cria as threads consumidoras
  	for (t=0; t<NCONSUMIDORES; t++){
  		if (pthread_create(&tid2[t], NULL, consumidor, (void *)id2[t])) { printf("--ERRO: pthread_create()\n"); exit(-1); }
  	}





  	//--espera todas as threads terminarem
  	for (t=0; t<NPRODUTORES; t++) {
    	if (pthread_join(tid[t], NULL)) {
    	     printf("--ERRO: pthread_join() \n"); exit(-1); 
    	} 
    	free(id[t]);
  	} 
  	for (t=0; t<NCONSUMIDORES; t++) {
    	if (pthread_join(tid2[t], NULL)) {
    	     printf("--ERRO: pthread_join() \n"); exit(-1); 
    	} 
    	free(id2[t]);
  	}

  	pthread_exit(NULL);
	

}
