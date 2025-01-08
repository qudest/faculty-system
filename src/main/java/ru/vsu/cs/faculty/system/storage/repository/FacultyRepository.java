package ru.vsu.cs.faculty.system.storage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.cs.faculty.system.storage.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query("SELECT f FROM Faculty f WHERE " +
            "(:searchColumn IS NULL OR " +
            "(:searchColumn = 'name' AND f.name LIKE %:searchValue%) OR " +
            "(:searchColumn = 'headName' AND f.headName LIKE %:searchValue%) OR " +
            "(:searchColumn = 'email' AND f.email LIKE %:searchValue%) OR " +
            "(:searchColumn = 'phone' AND f.phone LIKE %:searchValue%))")
    Page<Faculty> findAllWithFilters(@Param("searchValue") String searchValue,
                                     @Param("searchColumn") String searchColumn,
                                     Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
