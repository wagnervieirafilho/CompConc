#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

#define NLEITORES 2
#define NESCRITORES 2

#define BUFSIZE 100000

char *buffer[BUFSIZE];

sem_t 

void *leitor(void *arg) {
	int i;
	FILE *a = fopen("/dev/null");
	for (i = 0; i < BUFSIZE; i++) {
		fwrite("1", 1, 1, a);
	}


	while(1) {
		sem_wait(&leit);
		sem_wait(&em_l);
		l++;
		if (l == 1)
			sem_wait(&escr);
		sem_post(&em_l);
		sem_post(&leit);
		//le...
		sem_wait(&em_l);
		l--;
		if (l == 0){
			sem_post(&escr);
		}
		sem_post(&em_l);

	}







}

void *escritor(void *arg) {
	int i;
	FILE *a = fopen("/dev/null");
	for (i = 0; i < BUFSIZE; i++) {
		fwrite("1", 1, 1, a);
	}

	while(1) {
		sem_wait(&em_e); e++;
		if(e==1) sem_wait(&leit);
		sem_post(&em_e);
		sem_wait(&escr);
		//escreve...
		sem_post(&escr);
		sem_wait(&em_e); e--;
		if(e==0)
			sem_post(&leit);
		sem_post(&em_e);
 }


}

int main(int argc, char *argv[]) {

	int i;
	int *id;
	pthread_t tid_esc[NESCRITORES];
	pthread_t tid_leit[NLEITORES];


	for(i = 0; i < NLEITORES; i++) { 
		id = malloc(sizeof(*id));
		*id = i;
		(*id)++;
		pthread_create(&tid_leit[i], NULL, leitor, (void*) id);
	}

	for(i = 0; i < NESCRITORES; i++) { 
		id = malloc(sizeof(*id));
		*id = i;
		(*id)++;
		pthread_create(&tid_esc[i], NULL, escritor, (void*) id);
	}

	for(i = 0; i < NESCRITORES; i++) { 
		pthread_join(tid_esc[i], NULL);
	}


	pthread_exit(NULL);





}