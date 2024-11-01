package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.faculty.system.storage.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
