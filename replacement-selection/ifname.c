#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <fcntl.h>
typedef int keyType;

typedef struct recTypeTag {
    keyType key;                                /* sort key for record */
    #if LRECL
        char data[LRECL-sizeof(keyType)];       /* other fields */
    #endif
} recType;

void generateOriginalNumbers(){
  int fp;
  int i;
  static FILE *ifp;           /* input file */
  int lessThanZero = 0;

  fp = open("in.dat",   O_RDWR | O_CREAT | O_EXCL, 0666);

  for(i = 0; i < 10000; i++){
    recType tmp;
    srand(time(NULL));
   int genTmp  = rand();

   if(genTmp < 0)
     lessThanZero++;
   else{
     tmp.key = genTmp % 10000;
   }
  int len = write(fp, &tmp, sizeof(recType));
  if(len == -1){
    printf("unsuccessfull\n");
  }

  }
  printf("%d digits\n", lessThanZero);
  close(fp);
}
