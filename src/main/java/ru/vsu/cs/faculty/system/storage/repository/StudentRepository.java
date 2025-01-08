package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.cs.faculty.system.storage.entity.Faculty;
import ru.vsu.cs.faculty.system.storage.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE " +
            "(:searchColumn IS NULL OR " +
            "(:searchColumn = 'name' AND s.name LIKE %:searchValue%) OR " +
            "(:searchColumn = 'email' AND s.email LIKE %:searchValue%) OR " +
            "(:searchColumn = 'phone' AND s.phone LIKE %:searchValue%) OR " +
            "(:searchColumn = 'academicDegree' AND CAST(s.academicDegree AS string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'educationForm' AND CAST(s.educationForm as string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'course' AND CAST(s.course AS string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'team' AND CAST(s.team as string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'department' AND s.department.name LIKE %:searchValue%) OR " +
            "(:searchColumn = 'faculty' AND s.department.faculty.name LIKE %:searchValue%))")
    Page<Student> findAllWithFilters(@Param("searchValue") String searchValue,
                                     @Param("searchColumn") String searchColumn,
                                     Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
