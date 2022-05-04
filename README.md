# 3110Project2

Project 2 for 3110:
Evaluates java floating point literals and performs arithmetic operations with precedence using (+,-,/,*) and parentheses
Code for StackInterface, LinkedStack, and postfix creation and evaluation used is from Hao Ji's CS2400 Data Structures course

How to run program:
  With command prompt (requires JDK installed):
  - Go to directory of 3110Project2
  - Input "javac Test.java" into command prompt
  - Input "java Test" to test program

  With java IDE:
  - Run Test.java with IDE compatible with java

Input expressions and program will evaluate
- If expression is accepted, the program will print "Valid expression" and print out the postfix and final number of the evaulated postfix on at the bottom
- If expression is rejected, program will print "Invalid expression" and print out -1, (If -1 is printed and the expression is valid, then it will say 'valid expression' instead)


WARNING:
- During long expressions, program will print out extra lines and random numbers, but that should not affect the final value of the evaluated expression printed out at the bottom


Program may not handle certain underscore cases
