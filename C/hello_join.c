/* Disciplina: Computacao Concorrente */
/* Prof.: Silvana Rossetto */
/* Laboratório: 1 */
/* Codigo: "Hello World" usando threads em C e a funcao join*/

#include <stdio.h>
#include <stdlib.h> 
#include <pthread.h>

#define TAMANHO 10
#define NTHREADS  2

//cria a estrutura de dados para armazenar os argumentos da thread
typedef struct {
   int idThread, nThreads;
   //int vetor[TAMANHO];
   int *v;
} t_Args;


//funcao executada pelas threads
void *PrintHello (void *arg) {
  t_Args *args = (t_Args *) arg;
  int a;

  printf("Sou a thread %d de %d threads\n", args->idThread, args->nThreads);
  
  
 	 	printf("antes\n");
 	 	for(a = args->idThread ; a<TAMANHO ; a+=2){
 	 		printf("durante\n");
 	 		(args->v[a])++;
 	 	}
  
  free(arg);
  
  pthread_exit(NULL);
}

//funcao principal do programa
int main() {

  pthread_t tid_sistema[NTHREADS];
  int t;
  int i;
  int vetor[TAMANHO];
  t_Args *arg; //receberá os argumentos para a thread
  
  
  for(i=0 ; i< TAMANHO ; i++){
  	vetor[i]=0;
  	//arg->vetor[i]=0;
  }

  for(t=0; t<NTHREADS; t++) {
  	
  	arg = malloc(sizeof(t_Args));
  	arg->v = vetor;
  	
    printf("--Aloca e preenche argumentos para thread %d\n", t);
    
    if (arg == NULL) {
      printf("--ERRO: malloc()\n"); exit(-1);
    }
    arg->idThread = t; 
    arg->nThreads = NTHREADS; 
    
    printf("--Cria a thread %d\n", t);
    if (pthread_create(&tid_sistema[t], NULL, PrintHello, (void*) arg)) {
      printf("--ERRO: pthread_create()\n"); exit(-1);
    }
  }

  //--espera todas as threads terminarem
  for (t=0; t<NTHREADS; t++) {
    if (pthread_join(tid_sistema[t], NULL)) {
         printf("--ERRO: pthread_join() \n"); exit(-1); 
    } 
  }

	for(i=0;i<TAMANHO;i++){
		printf("%d", vetor[i]);
	}
  printf("--Thread principal terminou\n");
  pthread_exit(NULL);
}
