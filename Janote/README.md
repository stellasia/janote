# Janote

## Purpose

Provide a simple graphical interface for teachers to handle small group of students and their grades. 


## Developer guide

Application developed using java/swing and the sqlite databases.

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
- lib : contains dependencies
     - sqlite3
     - junit ans its dependencies
    
 
## Use guide

JaNote allows you to save some informations about your classes and students (name, age, email, ...). 


**WARNING** for the time being, the interface is only available in French. 


### Installation

- Download the janote-v*.jar
- Run `java -jar janote-v*.jar` from a terminal


### First start 

1. Create the file that will be used to save your data (on your local disk). Extension `.sql` will be appended if not present.
2. Go to the Group tab.
3. Click on 'Créer un nouveau'. Enter your first class/group name (and optional description)
4. Add a Student by clicking on 'Ajouter un étudian':
    - only name is mandatory
    - birthday date should be in dd-MM-yyyy format

You can modity a student by double-clicking on its row in the Group tab. 

**Be careful** if you delete an entity (group or student), this action **can not be undone**. 
 
