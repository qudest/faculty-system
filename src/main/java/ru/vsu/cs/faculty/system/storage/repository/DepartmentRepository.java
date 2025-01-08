package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.cs.faculty.system.storage.entity.Department;
import ru.vsu.cs.faculty.system.storage.entity.Faculty;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE " +
            "(:searchColumn IS NULL OR " +
            "(:searchColumn = 'name' AND d.name LIKE %:searchValue%) OR " +
            "(:searchColumn = 'headName' AND d.headName LIKE %:searchValue%) OR " +
            "(:searchColumn = 'email' AND d.email LIKE %:searchValue%) OR " +
            "(:searchColumn = 'phone' AND d.phone LIKE %:searchValue%) OR " +
            "(:searchColumn = 'faculty' AND d.faculty.name LIKE %:searchValue%))")
    Page<Department> findAllWithFilters(@Param("searchValue") String searchValue,
                                     @Param("searchColumn") String searchColumn,
                                     Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Department> findAllByFaculty_Id(Long facultyId);
}
