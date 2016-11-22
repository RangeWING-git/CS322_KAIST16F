grammar RE;

compileUnit : e EOF ;

e  : e '*' # Closure
   | e e # Concatenation
   | e '+' e # Union
   | '(' e ')' # Parenthesize
   | SYMBOL # Symbol
   | '()' # Epsilon
   ;

SYMBOL  : [a-zA-Z0-9];
WS : [ \n\r\t\0]+ -> skip ;