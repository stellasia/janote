
# Group table 
CREATE TABLE IF NOT EXISTS Groups
(
id 	        INTEGER PRIMARY KEY AUTOINCREMENT,
name        TEXT NOT NULL,
description TEXT
);

CREATE INDEX IF NOT EXISTS index_groups_id
ON Groups (id);


# Student table
CREATE TABLE IF NOT EXISTS Students
(
id 	        INTEGER PRIMARY KEY AUTOINCREMENT,
name        TEXT NOT NULL,
surname     TEXT,
email 	    TEXT,
birthday    TEXT,
repeating   INTEGER,
gender      INTEGER,
groupID	    INTEGER NOT NULL
#,
#FOREIGN KEY(groupID) REFERENCES Groups(id)
);

CREATE INDEX IF NOT EXISTS index_students_id
ON Students (id);

CREATE INDEX IF NOT EXISTS index_students_group_id
ON Students (groupID);


# Exam table 
CREATE TABLE IF NOT EXISTS Exams
(
id 	            INTEGER PRIMARY KEY AUTOINCREMENT,
name            TEXT NOT NULL,
description     TEXT,
groupID         INTEDER NOT NULL,
coefficient     REAL,
date            TEXT
);

CREATE INDEX IF NOT EXISTS index_exams_id
ON Exams (id);

CREATE INDEX IF NOT EXISTS index_exams_group_id
ON Exams (groupID);


# Grade 
CREATE TABLE IF NOT EXISTS Grades
(
id           INTEGER PRIMARY KEY AUTOINCREMENT,
studentID    INTEGER NOT NULL,
examID       INTEDER NOT NULL,
value        REAL
);

CREATE INDEX IF NOT EXISTS index_grades_student_id
ON Grades (studentID);

CREATE INDEX IF NOT EXISTS index_grades_exam_id
ON Grades (examID);

