CREATE TABLE IF NOT EXISTS Teachers
(
teacher_id 	        INTEGER PRIMARY KEY AUTOINCREMENT,
teacher_name        TEXT NOT NULL,
teacher_surname     TEXT
);

CREATE INDEX IF NOT EXISTS index_teacher_id
ON Teachers (teacher_id);


CREATE TABLE IF NOT EXISTS Groups
(
group_id 	      INTEGER PRIMARY KEY AUTOINCREMENT,
group_name        TEXT NOT NULL,
group_description TEXT,
group_teacher_id  INTEGER,
FOREIGN KEY(group_teacher_id) REFERENCES Teachers(teacher_id)
);

CREATE INDEX IF NOT EXISTS index_groups_id
ON Groups (group_id);


CREATE TABLE IF NOT EXISTS Students
(
student_id 	        INTEGER PRIMARY KEY AUTOINCREMENT,
student_name        TEXT NOT NULL,
student_surname     TEXT,
student_email 	    TEXT,
student_birthday    TEXT,
student_repeating   INTEGER,
student_gender      TEXT,
student_group_id    INTEGER,
FOREIGN KEY(student_group_id) REFERENCES Groups(group_id)
);

CREATE INDEX IF NOT EXISTS index_students_id
ON Students (student_id);

CREATE INDEX IF NOT EXISTS index_students_group_id
ON Students (student_group_id);


CREATE TABLE IF NOT EXISTS Exams
(
exam_id				INTEGER PRIMARY KEY AUTOINCREMENT,
exam_name          	TEXT NOT NULL,
exam_description   	TEXT,
exam_coefficient    REAL,
exam_date           TEXT,
exam_group_id		INTEGER,
FOREIGN KEY(exam_group_id) REFERENCES Groups(group_id)
);

CREATE INDEX IF NOT EXISTS index_exams_id
ON Exams (exam_id);

CREATE INDEX IF NOT EXISTS index_exams_group_id
ON Exams (exam_group_id);


CREATE TABLE IF NOT EXISTS Grades
(
grade_id           INTEGER PRIMARY KEY AUTOINCREMENT,
grade_value        REAL,
grade_student_id   INTEGER,
grade_exam_id 	   INTEGER,
FOREIGN KEY(grade_student_id) REFERENCES Students(student_id),
FOREIGN KEY(grade_exam_id) REFERENCES Exams(exam_id)
);

CREATE INDEX IF NOT EXISTS index_grades_student_id
ON Grades (grade_student_id);

CREATE INDEX IF NOT EXISTS index_grades_exam_id
ON Grades (grade_exam_id);

