sevenpixelhwork
===============

Some Homework :-)

#What have I done?#
When developing this program (actually when developing every program) I was thinking about extensibility. So... I decided to extend a bit the task you gave me and here's what is done

* I prefered to use json as input and output format. Why? Well, because I do not like custom formats, at least you can use the input and the output into another program in future. If you prefer a custom format please tell me I will do it...
* I have externalized the whole thingy configuration, so when you decide that you want to change a tax percantage or you want to introduce a new kind of tax you won't have to rewerite and recompile anything, simply add the new tax in the configuration ;)

#What frameworks I have used?#
In order not to write tons of code and save myself time + make the code more readable I have used two additional libraries:
* Gson -> for serialization/deserialization to/from json strings
* Guava -> for different operations and validations over strings and data collections
* JUinit -> for my unit tests. The coverage currenltu is ~ 90%

#What should be improved#
* I do not like the the validation of the input data. I want to improve it, maybe using some jsr303 implementation, or if using databases I will just use spring.
* I do not like the unit tests, they have to be refactored too much code duplications, but for the sake of saving time I didn't do it.
* I should start using some library for logging like log4j
* Start using a more sophisticated database for storing reports. Actually one can extend this and uses the analytical engines of some rdbms in order to get some additional analytical data.

#How to run the program#
* Option A: Import the project as a maven project into eclipse
* Option B: run mvn clean install, there're unit tests that verify the whole thingy you can also go to the target folder and execute in the command line: `java -Dconfig.file=<path to config file> -Dinput.file=<path to input file> -Doutput.file=<path to output file> -jar <path to jar>`
* I haven't wirtten some serious documentation, but I know you'll manage to read and understand the code on your own.

#Project Setup#

In TaxesAreABadThing folder you will find:
* pom.xml -> builds na executable .jar file
* .classpath and .project files -> I have used eclipse, so I am submiting this for you you can import the project directly in Eclipse if you're using this IDE, otherwise please use maven in order to build and run your project :-)
* src -> folders with sources and tests
* src/main/resources -> contains configuration
