
# Group table 
CREATE TABLE IF NOT EXISTS Groups
(
group_id 	        INTEGER PRIMARY KEY AUTOINCREMENT,
group_name        TEXT NOT NULL,
group_description TEXT
);

CREATE INDEX IF NOT EXISTS index_groups_id
ON Groups (group_id);


# Student table
CREATE TABLE IF NOT EXISTS Students
(
student_id 	        INTEGER PRIMARY KEY AUTOINCREMENT,
student_name        TEXT NOT NULL,
student_surname     TEXT,
student_email 	    TEXT,
student_birthday    TEXT,
student_repeating   INTEGER,
student_gender      TEXT,
student_group_id    INTEGER NOT NULL
#,
#FOREIGN KEY(groupID) REFERENCES Groups(id)
);

CREATE INDEX IF NOT EXISTS index_students_id
ON Students (student_id);

CREATE INDEX IF NOT EXISTS index_students_group_id
ON Students (student_group_id);


# Exam table 
CREATE TABLE IF NOT EXISTS Exams
(
exam_id				INTEGER PRIMARY KEY AUTOINCREMENT,
exam_name          	TEXT NOT NULL,
exam_description   	TEXT,
exam_group_id      	INTEDER NOT NULL,
exam_coefficient    REAL,
exam_date           TEXT
);

CREATE INDEX IF NOT EXISTS index_exams_id
ON Exams (exam_id);

CREATE INDEX IF NOT EXISTS index_exams_group_id
ON Exams (exam_group_id);


# Grade 
CREATE TABLE IF NOT EXISTS Grades
(
grade_id           INTEGER PRIMARY KEY AUTOINCREMENT,
grade_student_id   INTEGER NOT NULL,
grade_exam_id      INTEDER NOT NULL,
grade_value        REAL
);

CREATE INDEX IF NOT EXISTS index_grades_student_id
ON Grades (grade_student_id);

CREATE INDEX IF NOT EXISTS index_grades_exam_id
ON Grades (grade_exam_id);

