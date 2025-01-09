package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.cs.faculty.system.storage.entity.Student;
import ru.vsu.cs.faculty.system.storage.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE " +
            "(:searchColumn IS NULL OR " +
            "(:searchColumn = 'name' AND t.name LIKE %:searchValue%) OR " +
            "(:searchColumn = 'email' AND t.email LIKE %:searchValue%) OR " +
            "(:searchColumn = 'phone' AND t.phone LIKE %:searchValue%) OR " +
            "(:searchColumn = 'academicDegree' AND CAST(t.academicDegree AS string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'position' AND CAST(t.position as string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'experience' AND CAST(t.experience AS string) LIKE %:searchValue%) OR " +
            "(:searchColumn = 'department' AND t.department.name LIKE %:searchValue%) OR " +
            "(:searchColumn = 'faculty' AND t.department.faculty.name LIKE %:searchValue%))")
    Page<Teacher> findAllWithFilters(@Param("searchValue") String searchValue,
                                     @Param("searchColumn") String searchColumn,
                                     Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
