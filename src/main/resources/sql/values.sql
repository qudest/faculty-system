-- Insert 1 faculty record
INSERT INTO faculty (id, email, head_name, name, phone)
VALUES (1, 'faculty@university.edu', 'Dr. John Smith', 'Faculty of Science', '+12345678901');

-- Insert 1 department record linked to the faculty
INSERT INTO department (id, email, head_name, name, phone, faculty_id)
VALUES (1, 'department@university.edu', 'Dr. Jane Doe', 'Department of Physics', '+12345678902', 1);

WITH RECURSIVE numbers AS (
    SELECT 1 AS num
    UNION ALL
    SELECT num + 1
    FROM numbers
    WHERE num < 1000  -- Generate numbers from 1 to 1000
)
INSERT INTO student (id, academic_degree, course, education_form, email, name, phone, team, department_id)
SELECT
    num AS id,
    CASE abs(random() % 5)
        WHEN 0 THEN 'BACHELOR'
        WHEN 1 THEN 'MASTER'
        WHEN 2 THEN 'SPECIALIST'
        WHEN 3 THEN 'CANDIDATE_OF_SCIENCES'
        WHEN 4 THEN 'DOCTOR_OF_SCIENCES'
        END AS academic_degree,
    abs(random() % 10) + 1 AS course,  -- Assuming courses are numbered 1-10
    CASE abs(random() % 3)
        WHEN 0 THEN 'FULL_TIME'
        WHEN 1 THEN 'PART_TIME'
        WHEN 2 THEN 'DISTANT'
        END AS education_form,
    'student_' || num || '@example.com' AS email,  -- Generate a random email
    'Name_' || num AS name,  -- Generate a random name
    '+1-' || (abs(random() % 900) + 100) || '-' || (abs(random() % 900) + 100) || '-' || (abs(random() % 10000)) AS phone,  -- Generate a random phone number
    abs(random() % 5) + 1 AS team,  -- Assuming teams are numbered 1-5
    1
FROM numbers;