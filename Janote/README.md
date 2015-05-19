# Janote

## Purpose

Provide a simple graphical interface for teachers to handle small group of students and their grades. 


## Developer guide

Application developed using java/swing and the sqlite databases.

### Dependencies 

- sqlite3 (add sqlite-jdbc-3.8.5-pre1.jar to your classpath)


### Project architecture

- src
	- model
    	- entities
     	- connection
        - dao
    - controller : link between model and view
	- view 
		- console (only for some tests)
    	- gui  (the graphical user interface with swing)
- data
    - table_schema_v1.sql : sqlite table creation for new files
    - help.* : the html and css files used in the help tab
	
    
 
## Use guide

JaNote allows you to save some informations about your classes and students (name, age, email, ...). 

### Installation

- Download the janote-v*.jar
- Run `java -jar janote-v*.jar` from a terminal


### First start 

1. Create the file that will be used to save your data (on your local disk). Extension `.sql` will be appended if not present.  
2. Enter your first class name (and optional description)
3. Populate it with students (only name is mandatory)

**Be careful** if you delete an entity (group or student), this action **can not be undone**. 
 
