# WithoutRegex
In this project, the sample language is written without using the regex library according to the regular expression pattern entered 


A regular expression is a language-defining expression. Languages defined by regular expressions are called regular languages. 
In the examples given in the basic concepts section, languages were defined as sets. For example, given ={x}, the set L={ xn , n=1,3,5,7,... } denotes a language of words consisting of an odd number of symbols x. While this notation is correct, a more formal notation should also be proposed. This would allow more complex languages to be defined in a standardized and easy way. Regular expressions provide this formal representation.  

For Example:

Question:Write a regular expression for the language of words with ={0,1} in which the symbols 0 and 1, combined in various ways, are followed by two symbols 1, and the symbol 0 is always followed by symbol(s) 1, if any.
  
Answer: (0+1)*11(1+01)*
  ![image](https://github.com/AhmetBeskazalioglu/WithoutRegex/assets/146031280/829aad78-11eb-464c-bd6e-bc874fbd36e7)


  Subsamples:
  1111111, 111101010101, 100, 00000011010101, 1111111, 1111010101010101, 000011111, 111, etc. 


