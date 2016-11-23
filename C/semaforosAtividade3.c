/* Disciplina: Computacao Concorrente */
/*Nome: Wagner Vieira*/
/*Laboratório 9*/
/*Atividade 3*/
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>


#define NTHREADS 4


// Variaveis globais
sem_t _3e4, binario;     //semaforos para sincronizar a ordem de execucao das threads
int x = 0;



//função executada pela thread 1

void *t1 (void *threadid) {
	int i;

	printf("olá, tudo bem?\n");
	sem_wait(&binario);
	x++;
	if(x == 2)
		for(i = 0; i<2; i++){
			sem_post(&_3e4);
		}
	sem_post(&binario);

}

//função executada pela thread 2
void *t2 (void *threadid) {
	int i;

	printf("Hello!\n");
	sem_wait(&binario);
	x++;
	if(x == 2)
		for(i = 0; i<2; i++){
			sem_post(&_3e4);
		}
	sem_post(&binario);
	
}

//função executada pela thread 3
void *t3 (void *threadid) {

	sem_wait(&_3e4);
	printf("até mais tarde!\n");
	
}

//função executada pela thread 4
void *t4 (void *threadid) {
	sem_wait(&_3e4);
	printf("tchau!\n");
	
}

int main(int argc, char *argv[])
{

  pthread_t tid[NTHREADS];
  int *id[NTHREADS], t;

  //inicia os semaforos
  sem_init(&_3e4, 0, 0);
  sem_init(&binario, 0, 1);


  for (t=0; t<NTHREADS; t++) {
    if ((id[t] = malloc(sizeof(int))) == NULL) {
       pthread_exit(NULL); return 1;
    }
    *id[t] = t+1;
  }


  //cria as tres threads
  if (pthread_create(&tid[0], NULL, t1, (void *)id[0])) { printf("--ERRO: pthread_create()\n"); exit(-1); }
  if (pthread_create(&tid[1], NULL, t2, (void *)id[1])) { printf("--ERRO: pthread_create()\n"); exit(-1); }
  if (pthread_create(&tid[2], NULL, t3, (void *)id[2])) { printf("--ERRO: pthread_create()\n"); exit(-1); }
  if (pthread_create(&tid[3], NULL, t4, (void *)id[3])) { printf("--ERRO: pthread_create()\n"); exit(-1); }

  //--espera todas as threads terminarem
  for (t=0; t<NTHREADS; t++) {
    if (pthread_join(tid[t], NULL)) {
         printf("--ERRO: pthread_join() \n"); exit(-1); 
    } 
    free(id[t]);
  } 
  pthread_exit(NULL);
}
