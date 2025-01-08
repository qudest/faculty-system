package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.faculty.system.storage.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByFaculty_Id(Long facultyId);
}
