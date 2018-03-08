#include <stdio.h>
#define T 6
#define RUNS 129
void main(){
  int A[T + 1] = {0,1,1,1,1,1,0} ;
  int D[T + 1] = {0,1,1,1,1,1,0};

  int len = 1;
  int j = 1;
  int runNumber = 0;
  for(runNumber = 0; runNumber < RUNS; runNumber++){//write one run at a time
    D[j] -= 1;
    //    printf("%d %d\n", j, D[j]);
    //D3
    if(D[j] < D[j + 1]){ 
      j += 1;
    }
    else if(D[j] == 0){
      len += 1;
      int a = A[1];

      for(j = 1; j < T; j++){
	D[j] = a + ((j + 1) != T ? A[j + 1] : 0) - A[j];
	A[j] = a + ((j + 1) != T ? A[j + 1] : 0);
      }
      printf("\n");
      for(j = 1; j <= T; j++){
	printf(" %d ", D[j]);
      }

      j = 1;
    }
    else{
      j = 1;
    }
  }

  printf("result: %d\n",len);
  for(len = 0; len <= T; len++){
    printf(" %d ", A[len]);
  }
  printf("\n");
}
