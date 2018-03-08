#include <stdio.h>
#include <string.h>
#define INF 2147483641
typedef struct heap{
  int key;
  int pos;
}Heap;

void heapInLoserTree(int arr[], int num, Heap result[]){
  int winPos[num];
  int winnerPos;
  int i = 0;

  for(i = 0; i < num; i++){
    winPos[i] = num + i;
  }

  for(i = num - 1; i > 0; i--){
    int a = arr[winPos[(i * 2 ) % num] - num ];
    int b = arr[winPos[(i * 2 + 1) % num] - num];

    if(a > b){
      result[i].key = a;
      result[i].pos = winPos[(i * 2) % num];
      winPos[i] = winPos[(i * 2 + 1) % num];
    }
    else{
      result[i].key = b;
      result[i].pos = winPos[(i * 2 + 1) % num];
      winPos[i] = winPos[(i * 2) % num];
    }
  }
  result[0].key = arr[winPos[1] - num ];
  result[0].pos = winPos[1];
  /*
  for(i = 0; i < num; i++){
    printf("arr[%d]: %d %d \n", i, result[i].key,result[i].pos);
    }
  */
}

int replacementSelect(Heap heap[], int replacePos, int value){
  Heap winner ;
  winner.key = value;
  winner.pos = replacePos;
  
  while(replacePos/2 > 0){
    int tmpKey, tmpPos;
    if(winner.key > heap[replacePos/2].key){
      tmpKey = heap[replacePos/2].key;
      tmpPos = heap[replacePos/2].pos;

      heap[replacePos/2].key = winner.key;
      heap[replacePos/2].pos = winner.pos;

      winner.key = tmpKey;
      winner.pos = tmpPos;
    }
    replacePos = replacePos / 2;
  }
  heap[0].key = winner.key;

  heap[0].pos = winner.pos;
}

void losertree(int arr[], int num){
}
#define NUM 16
void main(){
  int i = 0;
  int unsorted[] = {9,1003, 28,28,503,87,512,61,908,170,897,275,653,426,154,509};
  int sorted[NUM] = {0};
  Heap heap[NUM] = {0};
  heapInLoserTree(unsorted, NUM, heap); 
 printf("winPos: %d\n", heap[0].pos);

  for(i = 0; i < NUM; i++){
    printf("arr[%d]: %d %d \n", i, heap[i].key,heap[i].pos);
  }
  sorted[0] = heap[0].key;

  for(i = 1; i < NUM; i++){
    replacementSelect(heap, heap[0].pos, INF);
    sorted[i] = heap[0].key;
  }

  for(i = 0; i < NUM; i++){
    printf("sort[%d]: %d \n", i, sorted[i]);
  }

}
