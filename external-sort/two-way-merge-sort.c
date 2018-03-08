#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
struct Page {
  int slot[2];
};

int power(int card, int times) {
  int tmp = 1;			   
  if(times == 0){
    return 1;
  }
  while(times-- > 0)			   
    tmp *= card; 
  return tmp;
}

int intCompare(const void* p, const void* q)
{
  int a = (int)(*(int*)p);
  int b = (int)(*(int *)q);
  if(a > b)
    return 1;
  else if ( a == b)
    return 0;
  else
    return -1;
}
main() {
  int fp;
  int i;
  int orig[] = {3,4,6,2,9,4,8,7,5,6,3,1,2,3};
  char fileName[50] = "merge-sort"; 
  const int pages = 8;
  int previousPages = 1;
  int newPages = 1;
  
  printf("2**4 %d, 2**3 %d, 2**2 %d, 2**1 %d, 2**0 %d\n", power(2,4),power(2,3),power(2,2),power(2,1),power(2,0));

  sprintf(fileName, "/bin/rm -rf merge-sort");

  system(fileName);
  /*
    qsort(&orig[0],13, sizeof(int), intCompare);
  
    for(i = 0; i < 13 ; i++)
    printf("%d ", orig[i]);
    printf("\n");
  */

  fp = open("merge-sort",   O_RDWR | O_CREAT | O_EXCL, 0666);
  int zero = 0;
  
  lseek(fp, (pages*sizeof(struct Page)) - 1, SEEK_SET);
  write(fp, &zero, 1);

  lseek(fp, 0, SEEK_SET);

  for(i = 0; i < 14; i++){
    struct Page tmp;
    tmp.slot[0] = orig[i++];
    tmp.slot[1] = orig[i];
    
    int len = write(fp, &tmp, sizeof(struct Page));
    printf("len %d %d %d\n",len, tmp.slot[0],tmp.slot[1]);
  }

  struct Page freePages[3];

  lseek(fp, 0, SEEK_SET);

  //pass 0
  for(i = 0; i < pages ; i++){
    int fd;
    char runi[10];
    struct Page tmp;

    int len=read(fp, &freePages[0], sizeof(struct Page));
    qsort(freePages[0].slot,2, sizeof(int), intCompare);

    sprintf(runi, "run%d",i);
    
    sprintf(fileName, "/bin/rm -rf %s", runi);

    system(fileName);
  
    fd = open(runi,   O_RDWR | O_CREAT | O_EXCL, 0666);
    write(fd, &freePages[0], sizeof(struct Page));
    lseek(fd, 0, SEEK_SET);
    read(fd, &tmp, sizeof(struct Page));
    printf("freePages[0] %d  %d\n", freePages[0].slot[0], freePages[0].slot[1]);

    close(fd);
  }

  //pass 1
  previousPages = power(2,0);
  newPages = power(2,1);
  int m0 = 0, m1 = 0;

  for(i = 0; i < (pages + 1)/2; i++){
    char runi[50];
    char newrun[50];
    int fda,fdb;
    int newRunPages;
    int fd;
    int pagesa = 0, pagesb = 0;

    sprintf(runi, "run%d", i*2);
    
    fda = open(runi, O_RDONLY);
    if(fda < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    sprintf(runi, "run%d", i*2+1);
    fdb = open(runi, O_RDONLY);
    if(fdb < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    read(fda, &freePages[0], sizeof(struct Page));
    read(fdb, &freePages[1], sizeof(struct Page));


    printf("%s %d  %d\n", __FILE__,__LINE__, i);
    printf("freePages[0] %d  %d\n", freePages[0].slot[0], freePages[0].slot[1]);
    printf("freePages[1] %d  %d\n", freePages[1].slot[0], freePages[1].slot[1]);


    sprintf(newrun,"run2pages%d",i);
    sprintf(fileName, "/bin/rm -rf %s", newrun);
    system(fileName);

    fd = open(newrun,   O_RDWR | O_CREAT | O_EXCL, 0666);
    if(fd < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,newrun);
      exit(1);
    }

    for(newRunPages = 0; newRunPages < newPages ; newRunPages++){

      int m = 0;
      for(m=0; m < 2; m++){
	// fda or fdb runfile reachs end
	if(pagesa == previousPages){
	  freePages[2].slot[m] = freePages[1].slot[m1];
	  m1++;
	  if(m1 == 2){
	    m1 = 0;
	    pagesb++;
	  }
	}
	else if(pagesb == previousPages){
	  freePages[2].slot[m] = freePages[0].slot[m0];
	  m0++;
	  if(m0 == 2){
	    m0 = 0;
	    pagesb++;
	  }
	}
	else {
	  if(freePages[0].slot[m0] < freePages[1].slot[m1] ){
	    freePages[2].slot[m] = freePages[0].slot[m0];
	    m0++;
	    if(m0 == 2){
	      m0 = 0;
	      pagesa++;
	    }

	  }
	  else {
	    freePages[2].slot[m] = freePages[1].slot[m1];
	    m1++;
	    if(m1 == 2){
	      m1 = 0;
	      pagesb++;
	    }
	  }
	}
      
      }

      //printf("freePages[2] %d  %d\n", freePages[2].slot[0], freePages[2].slot[1]);
      if(write(fd, &freePages[2],sizeof(struct Page)) < 0){
	printf("%s %d: write file %s failed\n", __FILE__, __LINE__, newrun);
      }
    }

    close(fda);
    close(fdb);
    close(fd);
    //    printf("%s %d\n", __FILE__,__LINE__);
  }

  printf("pass 1 results\n");
  for(i = 0; i < 4; i++){
    char runi[50];
    int fd;
    int j;
    sprintf(runi, "run2pages%d", i);

    
    fd = open(runi, O_RDONLY);
    
    for(j = 0; j < 2; j++){
      read(fd, &freePages[0], sizeof(struct Page));    
      printf("%d %d\n", freePages[0].slot[0], freePages[0].slot[1]);
    }
    close(fd);
  }

  //pass 2
  previousPages = power(2,1);
  newPages = power(2,2);

  for(i = 0; i < (pages+1)/4; i++){
    char runi[50];
    int fda, fdb;
    int pagesa = 0, pagesb = 0;
    int newFD;
    int oldPages = 0;
    int newRunPages = 0;
    m0 = m1 = 0;
    

    sprintf(runi, "run2pages%d", i*2);
    
    fda = open(runi, O_RDONLY);
    if(fda < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    sprintf(runi, "run2pages%d", i*2+1);
    fdb = open(runi, O_RDONLY);
    if(fdb < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }
    read(fda, &freePages[0], sizeof(struct Page));
    read(fdb, &freePages[1], sizeof(struct Page));

    sprintf(runi,"run4pages%d",i);
    sprintf(fileName, "/bin/rm -rf %s", runi);
    system(fileName);

    newFD = open(runi,   O_RDWR | O_CREAT | O_EXCL, 0666);
    if(newFD < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    printf("freePages[0] %d  %d\n", freePages[0].slot[0], freePages[0].slot[1]);
    printf("freePages[1] %d  %d\n", freePages[1].slot[0], freePages[1].slot[1]);

    printf("%d %s %d\n", newPages,__FILE__,__LINE__);

    for(newRunPages = 0; newRunPages < newPages; newRunPages++){
      int m = 0;
      
      for(m=0; m < 2; m++){
	// fda or fdb runfile reachs end
	if(pagesa == previousPages){
	  freePages[2].slot[m] = freePages[1].slot[m1];
	  m1++;
	  if(m1 == 2){
	    m1 = 0;
	    pagesb++;
	    if(pagesb == previousPages)
	      continue;
	    else
	      read(fdb, &freePages[1], sizeof(struct Page));
	  }
	}
	else if(pagesb == previousPages){
	  freePages[2].slot[m] = freePages[0].slot[m0];
	  m0++;
	  if(m0 == 2){
	    m0 = 0;
	    pagesa++;
	    if(pagesa == previousPages)
	      continue;
	    else
	      read(fda, &freePages[0], sizeof(struct Page));
	  }
	}
	else {
	  if(freePages[0].slot[m0] < freePages[1].slot[m1]){
	    freePages[2].slot[m] = freePages[0].slot[m0];
	    m0++;
	    if(m0 == 2){
	      m0 = 0;
	      pagesa++;
	      if(pagesa == previousPages)
		continue;
	      else
		read(fda, &freePages[0], sizeof(struct Page));
	    }
	  }
	  else {
	    freePages[2].slot[m] = freePages[1].slot[m1];
	    m1++;
	    if(m1 == 2){
	      m1 = 0; 
	      pagesb++;
	      if(pagesb == previousPages)
		continue;
	      else
		read(fdb, &freePages[1], sizeof(struct Page));
	    }
	  }
	}
      }
      
      printf("%d freePages[2] %d  %d\n", m,freePages[2].slot[0], freePages[2].slot[1]);
      if(write(newFD, &freePages[2],sizeof(struct Page)) < 0){
	printf("%s %d: write file %s failed\n", __FILE__, __LINE__, runi);
      }

    }
    close(newFD);
    close(fda);
    close(fdb);
    //}
    
  }
  
  printf("pass 2 results\n");
  for(i = 0; i < 2; i++){
    char runi[50];
    int fd;
    int j;
    sprintf(runi, "run4pages%d", i);
    
    fd = open(runi, O_RDONLY);
    
    for(j = 0; j < 4; j++){
      read(fd, &freePages[0], sizeof(struct Page));    
      printf("%d %d\n", freePages[0].slot[0], freePages[0].slot[1]);
    }
    close(fd);
  }

  //pass 3
  previousPages = power(2,2);
  newPages = power(2,3);
  for(i = 0; i < (pages+1)/8; i++){
    char runi[10];
    int fda, fdb;
    int newFD;
    int newRunPages = 0;
    int m0,m1;
    int pagesa = 0, pagesb = 0;
    m0 = m1 = 0;

    sprintf(runi, "run4pages%d", i*2);
    
    fda = open(runi, O_RDONLY);
    if(fda < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    sprintf(runi, "run4pages%d", i*2+1);
    fdb = open(runi, O_RDONLY);
    if(fdb < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    read(fda, &freePages[0], sizeof(struct Page));

    read(fdb, &freePages[1], sizeof(struct Page));

    sprintf(runi,"run8pages%d",i);
    sprintf(fileName, "/bin/rm -rf %s", runi);
    system(fileName);

    newFD = open(runi,   O_RDWR | O_CREAT | O_EXCL, 0666);
    if(newFD < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,runi);
      exit(1);
    }

    printf("freePages[0] %d  %d\n", freePages[0].slot[0], freePages[0].slot[1]);
    printf("freePages[1] %d  %d\n", freePages[1].slot[0], freePages[1].slot[1]);

    printf("%d %s %d\n", newPages,__FILE__,__LINE__);

    for(newRunPages = 0; newRunPages < newPages; newRunPages++){
      int m = 0;
      
      for(m=0; m < 2; m++){
	// fda or fdb runfile reachs end
	if(pagesa == previousPages){
	  freePages[2].slot[m] = freePages[1].slot[m1];
	  m1++;
	  if(m1 == 2){
	    m1 = 0;
	    pagesb++;
	    if(pagesb == previousPages)
	      continue;
	    else
	      read(fdb, &freePages[1], sizeof(struct Page));
	  }
	}
	else if(pagesb == previousPages){
	  freePages[2].slot[m] = freePages[0].slot[m0];
	  m0++;
	  if(m0 == 2){
	    m0 = 0;
	    pagesa++;
	    if(pagesa == previousPages)
	      continue;
	    else
	      read(fda, &freePages[0], sizeof(struct Page));
	  }
	}
	else {
	  if(freePages[0].slot[m0] < freePages[1].slot[m1]){
	    freePages[2].slot[m] = freePages[0].slot[m0];
	    m0++;
	    if(m0 == 2){
	      m0 = 0;
	      pagesa++;
	    if(pagesa == previousPages)
	      continue;
	    else
	      read(fda, &freePages[0], sizeof(struct Page));
	    }
	  }
	  else {
	    freePages[2].slot[m] = freePages[1].slot[m1];
	    m1++;
	    if(m1 == 2){
	      m1 = 0; 
	      pagesb++;
	    if(pagesb == previousPages)
	      continue;
	    else
	      read(fdb, &freePages[1], sizeof(struct Page));
	    }
	  }
	}
      }
      
      printf("%d freePages[2] %d  %d\n", m,freePages[2].slot[0], freePages[2].slot[1]);
      if(write(newFD, &freePages[2],sizeof(struct Page)) < 0){
	printf("%s %d: write file %s failed\n", __FILE__, __LINE__, runi);
      }
    }

    
  }

  // after sorting
  {
    int fda,fdb;
    fda = open("merge-sort", O_RDONLY);
    if(fda < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,"merge-sort");
      exit(1);
    }
    
    printf("original array:\n");
    for(i=0; i < pages; i++){
      read(fda, &freePages[0], sizeof(struct Page));    
      printf("%d %d ", freePages[0].slot[0], freePages[0].slot[1]);
    }

    fda = open("run8pages0", O_RDONLY);
    if(fda < 0){
      printf("%s %d: open file %s failed\n", __FILE__,__LINE__,"run8pages0");
      exit(1);
    }
    
    printf("sorted array:\n");
    for(i=0; i < pages; i++){
      read(fda, &freePages[0], sizeof(struct Page));    
      printf("%d %d ", freePages[0].slot[0], freePages[0].slot[1]);
    }

  }

  printf("\n");

}
