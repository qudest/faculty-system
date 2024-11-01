package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.faculty.system.storage.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
