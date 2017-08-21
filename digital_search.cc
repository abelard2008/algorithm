#include <stdio.h>
//#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include <set>

struct SYMBOL {
  const char *name;
  const unsigned int length;
  const unsigned int tok;
  int group; //< group mask, see SYM_GROUP enum for bits
};

class Lex_hash
{
private:
  const unsigned char *hash_map;
  const unsigned int entry_max_len;

public:
  Lex_hash(const unsigned char *hash_map_arg, unsigned int entry_max_len_arg)
  : hash_map(hash_map_arg), entry_max_len(entry_max_len_arg)
  {}

  const struct SYMBOL *get_hash_symbol(const char *s, unsigned int len) const;

  static const Lex_hash sql_keywords;
  static const Lex_hash sql_keywords_and_funcs;

  static const Lex_hash hint_keywords;
};

enum SYM_GROUP
{
  SG_KEYWORDS=          1 << 0, // SQL keywords and reserved words
  SG_FUNCTIONS=         1 << 1, // very special native SQL functions
  SG_HINTABLE_KEYWORDS= 1 << 2, // SQL keywords that accept optimizer hints
  SG_HINTS=             1 << 3, // optimizer hint parser keywords

  /* All tokens of the main parser: */
  SG_MAIN_PARSER=       SG_KEYWORDS | SG_HINTABLE_KEYWORDS | SG_FUNCTIONS
};
enum yytokentype {
  ADD = 262,
  AND_SYM = 274,
  DAY_SYM = 375,
  DAX_SYM = 378
};

#define STRING_WITH_LEN(X) (X), ((sizeof(X) - 1))
#define SYM_OR_NULL(A) A
#define SYM(T, A)    STRING_WITH_LEN(T),SYM_OR_NULL(A),SG_KEYWORDS
#define SYM_FN(T, A) STRING_WITH_LEN(T),SYM_OR_NULL(A),SG_FUNCTIONS
#define SYM_HK(T, A) STRING_WITH_LEN(T),SYM_OR_NULL(A),SG_HINTABLE_KEYWORDS
#define SYM_H(T, A)  STRING_WITH_LEN(T),SYM_OR_NULL(A),SG_HINTS

typedef unsigned short uint16;
typedef short int16;
typedef unsigned char uchar ;
typedef unsigned int uint32;
typedef unsigned int uint;
typedef int int32;

static inline uint32 uint4korr(const uchar *A) { return *((uint32*) A); }
static inline int32 sint4korr(const uchar *A) { return *((int32*) A); }

static const SYMBOL symbols[] = {
  { SYM("ADD",                      ADD)},
  { SYM("AND",                      AND_SYM)},
  { SYM("DAY",                      DAY_SYM)},
  { SYM("DAX",                      DAX_SYM)}
};

static const uchar to_upper_lex[]=
{
    0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
   16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
   32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
   48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,
   64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
   80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95,
   96, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
   80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,123,124,125,126,127,
  128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,
  144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,
  160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,
  176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,
  192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,
  208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,
  192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,
  208,209,210,211,212,213,214,247,216,217,218,219,220,221,222,255
};

#define array_elements(A) ((uint) (sizeof(A)/sizeof(A[0])))

inline int lex_casecmp(const char *s, const char *t, uint len)
{
  while (len-- != 0 &&
         to_upper_lex[(uchar) *s++] == to_upper_lex[(uchar) *t++]) ;
  return (int) len + 1;
}


const SYMBOL *Lex_hash::get_hash_symbol(const char *s, unsigned int len) const
{
  const char *cur_str= s;

  if (len == 0)
  {
    printf("warning, get_hash_symbol() received a request for a zero-length symbol, which is probably a mistake.");
    return NULL;
  }

  if (len > entry_max_len)
    return NULL;

  uint32 cur_struct= uint4korr(hash_map + ((len - 1) * 4));

  for (;;)
  {
    uchar first_char= (uchar) cur_struct;

    if (first_char == 0)
    {
      uint16 ires= (uint16) (cur_struct >> 16);
      if (ires == array_elements(symbols))
        return NULL;
      const SYMBOL *res= symbols + ires;
      uint count= (uint) (cur_str - s);
      return lex_casecmp(cur_str, res->name + count, len - count) ? NULL : res;
    }

    uchar cur_char= (uchar) to_upper_lex[(uchar) *cur_str];
    if (cur_char < first_char)
      return NULL;
    cur_struct>>= 8;
    if (cur_char > (uchar) cur_struct)
      return NULL;

    cur_struct>>= 8;
    cur_struct= uint4korr(hash_map +
        (((uint16) cur_struct + cur_char - first_char) * 4));
    cur_str++;
  }
}
static bool check_duplicates(uint group_mask);


struct hash_lex_struct
{
  int first_char;
  char last_char;
  // union value is undefined if first_char == 0
  union{
    hash_lex_struct *char_tails; // if first_char > 0
    int iresult;                 // if first_char == -1
  };
  int ithis;

  void destroy()
  {
    if (first_char <= 0)
      return;
    for (int i= 0, size= static_cast<uchar>(last_char) - first_char + 1;
         i < size; i++)
      char_tails[i].destroy();
    free(char_tails);
  }
};


class hash_map_info
{
public:
  hash_lex_struct *root_by_len;
  int max_len;

  char *hash_map;
  int size_hash_map;

  hash_map_info()
  : root_by_len(NULL), max_len(0), hash_map(NULL), size_hash_map(0)
  {}

  ~hash_map_info()
  {
    for (int i= 0; i < max_len; i++)
      root_by_len[i].destroy();
    free(root_by_len);
    free(hash_map);
  }

  hash_lex_struct *get_hash_struct_by_len(int len);
  void insert_symbols(int group_mask);
  void add_structs_to_map(hash_lex_struct *st, int len);
  void add_struct_to_map(hash_lex_struct *st);
  void set_links(hash_lex_struct *st, int len);
  bool print_hash_map(const char *name, uint group_mask);
};


hash_lex_struct *hash_map_info::get_hash_struct_by_len(int len)
{
  if (max_len < len)
  {
    root_by_len= (hash_lex_struct *) realloc((char*) root_by_len,
                                             sizeof(hash_lex_struct) * len);
    hash_lex_struct *cur, *end= root_by_len + len;
    for (cur= root_by_len + max_len; cur < end; cur++)
      cur->first_char= 0;
    max_len= len;
  }
  return root_by_len + len - 1;
}

void insert_into_hash(hash_lex_struct *root, const char *name, 
		      int len_from_begin, int index)
{
  hash_lex_struct *end, *cur, *tails;

  if (!root->first_char)
  {
    root->first_char= -1;
    root->iresult= index;
    return;
  }

  if (root->first_char == -1)
  {
    int index2= root->iresult;
    const char *name2= symbols[index2].name + len_from_begin;
    root->first_char= (int) (uchar) name2[0];
    root->last_char= (char) root->first_char;
    tails= (hash_lex_struct*)malloc(sizeof(hash_lex_struct));
    root->char_tails= tails;
    tails->first_char= -1;
    tails->iresult= index2;
  }

  size_t real_size= (root->last_char-root->first_char+1);

  if (root->first_char>(*name))
  {
    size_t new_size= root->last_char-(*name)+1;
    if (new_size<real_size) printf("error!!!!\n");
    tails= root->char_tails;
    tails= (hash_lex_struct*)realloc((char*)tails,
				       sizeof(hash_lex_struct)*new_size);
    root->char_tails= tails;
    memmove(tails+(new_size-real_size),tails,real_size*sizeof(hash_lex_struct));
    end= tails + new_size - real_size;
    for (cur= tails; cur<end; cur++)
      cur->first_char= 0;
    root->first_char= (int) (uchar) *name;
  }

  if (root->last_char<(*name))
  {
    size_t new_size= (*name)-root->first_char+1;
    if (new_size<real_size) printf("error!!!!\n");
    tails= root->char_tails;
    tails= (hash_lex_struct*)realloc((char*)tails,
				    sizeof(hash_lex_struct)*new_size);
    root->char_tails= tails;
    end= tails + new_size;
    for (cur= tails+real_size; cur<end; cur++)
      cur->first_char= 0;
    root->last_char= (*name);
  }

  insert_into_hash(root->char_tails+(*name)-root->first_char,
		   name+1,len_from_begin+1,index);
}

void hash_map_info::insert_symbols(int group_mask)
{
  size_t i= 0;
  const SYMBOL *cur;
  for (cur= symbols; i < array_elements(symbols); cur++, i++)
  {
    if (!(cur->group & group_mask))
      continue;
    hash_lex_struct *root= get_hash_struct_by_len(cur->length);
    insert_into_hash(root, cur->name, 0, i);
  }
}

void hash_map_info::add_struct_to_map(hash_lex_struct *st)
{
  st->ithis= size_hash_map/4;
  size_hash_map+= 4;
  hash_map= (char*)realloc(hash_map,size_hash_map);
  hash_map[size_hash_map-4]= (char) (st->first_char == -1 ? 0 :
				     st->first_char);
  hash_map[size_hash_map-3]= (char) (st->first_char == -1 ||
				     st->first_char == 0 ? 0 : st->last_char);
  if (st->first_char == -1)
  {
    hash_map[size_hash_map-2]= ((unsigned int)(int16)st->iresult)&255;
    hash_map[size_hash_map-1]= ((unsigned int)(int16)st->iresult)>>8;
  }
  else if (st->first_char == 0)
  {
    hash_map[size_hash_map-2]= ((unsigned int)(int16)array_elements(symbols))&255;
    hash_map[size_hash_map-1]= ((unsigned int)(int16)array_elements(symbols))>>8;
  }
}


void hash_map_info::add_structs_to_map(hash_lex_struct *st, int len)
{
  hash_lex_struct *cur, *end= st+len;
  for (cur= st; cur<end; cur++)
    add_struct_to_map(cur);
  for (cur= st; cur<end; cur++)
  {
    if (cur->first_char && cur->first_char != -1)
      add_structs_to_map(cur->char_tails,cur->last_char-cur->first_char+1);
  }
}


void hash_map_info::set_links(hash_lex_struct *st, int len)
{
  hash_lex_struct *cur, *end= st+len;
  for (cur= st; cur<end; cur++)
  {
    if (cur->first_char != 0 && cur->first_char != -1)
    {
      int ilink= cur->char_tails->ithis;
      hash_map[cur->ithis*4+2]= ilink%256;
      hash_map[cur->ithis*4+3]= ilink/256;
      set_links(cur->char_tails,cur->last_char-cur->first_char+1);
    }
  }
}


bool hash_map_info::print_hash_map(const char *name, uint group_mask)
{
  if (check_duplicates(group_mask))
    return true;
  insert_symbols(group_mask);
  add_structs_to_map(root_by_len, max_len);
  set_links(root_by_len, max_len);

  printf("static const unsigned char %s_map[%d]= {\n", name, size_hash_map);

  char *cur;
  int i;
  for (i=0, cur= hash_map; i < size_hash_map; i++, cur++)
  {
    switch(i%4){
    case 0: case 1:
      if (!*cur)
	printf("0,   ");
      else
	printf("\'%c\', ",*cur);
      break;
    case 2: printf("%u, ",(uint)(uchar)*cur); break;
    case 3: printf("%u,\n",(uint)(uchar)*cur); break;
    }
  }
  printf("};\n\n");
  printf("const unsigned int %s_max_len=%d;\n", name, max_len);
  return false;
}


bool check_duplicates(uint group_mask)
{
  std::set<const char *> names;

  size_t i= 0;
  const SYMBOL *cur;
  for (cur= symbols; i < array_elements(symbols); cur++, i++)
  {
    if (!(cur->group & group_mask))
      continue;
    if (!names.insert(cur->name).second)
    {
      const char *err_tmpl= "\ngen_lex_hash fatal error : "
        "Unfortunately gen_lex_hash can not generate a hash,\n since "
        "your lex.h has duplicate definition for a symbol \"%s\"\n\n";
      printf (err_tmpl, cur->name);
      fprintf (stderr, err_tmpl, cur->name);
      return true;
    }
  }
  return false;
}

static const unsigned char sql_keywords_map[84]= {
  0,   0,   4, 0, //0
  0,   0,   4, 0,//4
  'A', 'D', 3, 0,//8
  'D', 'N', 7, 0,//12
  0,   0,   4, 0,//16
  0,   0,   4, 0,//20
  'A', 'A', 18, 0,//24
  0,   0,   0, 0,//28
  0,   0,   4, 0,//32
  0,   0,   4, 0,//36
  0,   0,   4, 0,//40
  0,   0,   4, 0,//44
  0,   0,   4, 0,//48
  0,   0,   4, 0,//52
  0,   0,   4, 0,//56
  0,   0,   4, 0,//60
  0,   0,   4, 0,//64
  0,   0,   1, 0,//68
  'X', 'Y', 19, 0,//72
  0,   0,   3, 0,//76
  0,   0,   2, 0,//80
};

const unsigned int sql_keywords_max_len=3;
/*
static const unsigned char sql_keywords_map[72]= {
0,   0,   3, 0,
0,   0,   3, 0,
'A', 'D', 3, 0,
'D', 'N', 7, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   2, 0,
0,   0,   0, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   3, 0,
0,   0,   1, 0,
};

const unsigned int sql_keywords_max_len=3;
*/
const Lex_hash Lex_hash::sql_keywords(sql_keywords_map, sql_keywords_max_len);

int main() {
  /* generate sql_keywords_map and sql_keywords_max_len */

 if (hash_map_info().print_hash_map("sql_keywords",
                                     SG_KEYWORDS | SG_HINTABLE_KEYWORDS))
   {
    exit(1);
   }
  printf("\n");


  /*
  const SYMBOL *sym = Lex_hash::sql_keywords.get_hash_symbol("ADD", 3);
  printf("%s %d %d\n", sym->name,sym->length,sym->tok);

  sym = Lex_hash::sql_keywords.get_hash_symbol("AND", 3);
  printf("%s %d %d\n", sym->name,sym->length,sym->tok);

  sym = Lex_hash::sql_keywords.get_hash_symbol("DAX", 3);
  if(sym == NULL) {
    printf("sym is null\n");
      } else {
  printf("%s %d %d\n", sym->name,sym->length,sym->tok);
  }
  */
}
