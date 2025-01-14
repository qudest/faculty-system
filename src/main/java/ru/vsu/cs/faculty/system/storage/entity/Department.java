package ru.vsu.cs.faculty.system.storage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.vsu.cs.faculty.system.dto.DepartmentCreationDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "department")
public class Department {

    public Department(DepartmentCreationDto departmentCreationDto) {
        this.name = departmentCreationDto.getName();
        this.headName = departmentCreationDto.getHeadName();
        this.email = departmentCreationDto.getEmail();
        this.phone = departmentCreationDto.getPhone();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_seq")
    @SequenceGenerator(name = "department_id_seq", sequenceName = "department_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "head_name", nullable = false)
    private String headName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

}
