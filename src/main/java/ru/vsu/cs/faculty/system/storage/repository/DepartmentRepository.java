package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.faculty.system.storage.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}