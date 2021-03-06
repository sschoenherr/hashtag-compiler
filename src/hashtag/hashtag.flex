package hashtag;
import java_cup.runtime.Symbol;
%%

%class Lexer
%int
%unicode
%line
%column
%cup


%{
    /*para los simbolos generales*/
    private Symbol symbol(int type){
      return new JavaSymbol(type,yyline+1,yycolumn+1,yytext());
    }

    /*para el tipo de token con su valor*/
    private Symbol symbol (int type, Object value){
      return new JavaSymbol(type,yyline+1,yycolumn+1,yytext(),value);
    }

    StringBuilder string = new StringBuilder();
    StringBuilder comment = new StringBuilder();
%}

%eofval{
  return symbol(sym.EOF);
%eofval}



/*----------MACROS------------*/
DIGITO=[0-9]+
LETRA=[a-zA-Z]
ALPHANUMERICO={DIGITO}|{LETRA}
GUIONBAJO=[_]
ESPACIO=[" "]
SALTOLINEA=[\n\t\r]
IDENTIFICADOR={LETRA}({ALPHANUMERICO}|{GUIONBAJO})*
NUMERO= {DIGITO}
REAL= {DIGITO}"."{DIGITO}
CARACTER= ' . '
LLAVEIZQ=[{]
LLAVEDER=[}]
COMILLAS=[\"]
CONTENIDOCOMENT=([^}])*
HASHTAG=[#]
COMENTARIOUNALINEA={HASHTAG}.*

/*--------ESTADOS------------*/
%state COMMENT
%state SSTRING 
%%

<YYINITIAL> {
    {COMILLAS}              {string.setLength(0); yybegin(SSTRING);}
    {LLAVEIZQ}              {comment.setLength(0); yybegin(COMMENT);}
    {COMENTARIOUNALINEA}    {/*Ignore*/}
    {NUMERO}                {return symbol(sym.NUMERO, new Integer(yytext()));}
    {REAL}                  {return symbol(sym.REAL, new Double(yytext()));}
    {CARACTER}              {return symbol(sym.CARACTER, new Character(yytext().charAt(1)));}  
    {SALTOLINEA}            {/*Ignore*/}
    {ESPACIO}               {/*Ignore*/}
    
    /*-----------------------------------------OPERADORES----------------------------------*/
    "+"              {return symbol(sym.SUMA);}
    "-"              {return symbol(sym.MENOS);}
    "/"              {return symbol(sym.DIV);}
    "*"              {return symbol(sym.MULT);}
    ">"              {return symbol(sym.MAYOR);}
    "<"              {return symbol(sym.MENOR);}
    ">="             {return symbol(sym.MAYORIGUAL);}
    "<="             {return symbol(sym.MENORIGUAL);}
    "not"            {return symbol(sym.NOT);}
    "!="             {return symbol(sym.DIFERENTE);}
    "=="             {return symbol(sym.IGUAL);}
    "="              {return symbol(sym.ASIGNACION);}
    /*---------------------------------------SIGNOS----------------------------------------*/
    "("              {return symbol(sym.PARIZQ);}
    ")"              {return symbol(sym.PARDER);}
    "%"              {return symbol(sym.MOD);}
    ","              {return symbol(sym.COMA);}
    ";"              {return symbol(sym.PUNTOCOMA);}
    ":"              {return symbol(sym.DOSPUNTOS);}
    /*------------------------------TIPOS DE DATOS-----------------------------------------*/
    "int"            {return symbol(sym.INT);}
    "double"         {return symbol(sym.DOUBLE);}
    "char"           {return symbol (sym.CHAR);}
    "string"         {return symbol(sym.STRING);}
    "boolean"        {return symbol(sym.BOOLEAN);}
    /*-----------------------------PALABRAS RESERVADAS-------------------------------------*/
    "and"            {return symbol(sym.AND);}
    "or"             {return symbol(sym.OR);}
    "for"            {return symbol(sym.FOR);}
    "if"             {return symbol(sym.IF);}
    "else"           {return symbol(sym.ELSE);}
    "while"          {return symbol(sym.WHILE);}
    "function"       {return symbol(sym.FUNCTION);}
    "mainbegin"      {return symbol(sym.MAINBEGIN);}
    "begin"          {return symbol(sym.BEGIN); }
    "switch"         {return symbol(sym.SWITCH);}
    "case"           {return symbol(sym.CASE);}
    "do"             {return symbol(sym.DO);}
    "end"            {return symbol(sym.END); }
    "true"           {return symbol(sym.TRUE);}
    "false"          {return symbol(sym.FALSE);}
    "other"          {return symbol(sym.OTHER);}
    "break"          {return symbol(sym.BREAK);}
    "return"         {return symbol(sym.RETURN);}
    "readint"        {return symbol(sym.READINT);}
    "readdouble"     {return symbol(sym.READDOUBLE);}
    "readstring"     {return symbol(sym.READSTRING);}
    "readchar"       {return symbol(sym.READCHAR);}
    "print"          {return symbol(sym.PRINT);}
    "void"           {return symbol(sym.VOID);}
    {IDENTIFICADOR}  {return symbol(sym.IDENTIFICADOR, yytext());}
    [^]              {int l = yyline+1; int c = yycolumn+1;
                        Editor.console.setText(Editor.console.getText()+"Error: (line: " + l + ", column: " + c + "). Unrecognized token " + yytext() + " : Lexical error\n");
                    }
}

<COMMENT> {
    {CONTENIDOCOMENT}   {comment.append(yytext());}
    {LLAVEDER}          {yybegin (YYINITIAL);}
}

<SSTRING>{
    
    {COMILLAS}          {yybegin (YYINITIAL); return symbol(sym.CADENA, string.toString());  }
    .                   { string.append(yytext());}
} 
