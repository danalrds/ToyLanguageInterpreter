# ToyLanguageInterpreter
there are many versions of the same project
## Description
Our mini interpreter uses the main structures:
- Execution Stack (ExeStack): a stack of statements to execute the currrent program
- Table of Symbols (SymTable): a table which keeps the variables values
- Output (Out): that keeps all the mesages printed by the toy program 
- Heap is a dictionary of mappings (address, content) where the
address is an integer (the index of a location in the heap) while the content is an integer value.
The ToyLanguage supports concurrent execution and procedures.
