#include <stdio.h>

typedef struct iNode {
  struct eNode *loser;
  struct iNode *PI;
}iNode;

typedef struct eNode {
  struct iNode *PE;
  int key;
  int run;
}eNode;
typedef struct Node {
  iNode pInnerNode;
  eNode pExtNode;
}Node;
eNode* replacementSeletion(){ //return winner
  //initiation
  int i = 0;
  int cntNodes = 12;
  Node nodes[12];

  for(i = 0; i < 12; i++){
    nodes[i].pInnerNode.loser = NULL;
    
  }
}

void makeRuns(){}
