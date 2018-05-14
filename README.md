# specs2 example

### Resources
specs2  
https://etorreborre.github.io/specs2/guide/SPECS2-2.4.17/org.specs2.guide.UserGuide.html#User+Guide

Scala HTTP client  
http://sttp.readthedocs.io/en/latest/index.html

### Run with sbt
sbt> testOnly example.NavaKlaraVideo

### Pros
+ Can be run by IntelliJ
+ \<Click to se difference> in IntelliJ works
+ Seems pretty fast
 
### Cons
- For some types of test conditions are specified twice, both in text and in method, i.e you can have the right condition in the text but the wrong ine int the method doing the actual check.  
  This might be fixed using StandardDelimitedStepParsers 
- Error messages could be more informative
- Not very intuitive (at least not for a Scala newbie)
- How pass result from Given to THen? Variables in Given is not in scope in Then