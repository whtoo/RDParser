grammar NestedNameList

list : '[' elements ']'
elements : element (',', element)*
element : NAME ( '=' NAME )? | list;
