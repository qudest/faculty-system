package ru.vsu.cs.faculty.system.storage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.vsu.cs.faculty.system.storage.enums.EducationForm;
import ru.vsu.cs.faculty.system.storage.enums.AcademicDegree;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
    @SequenceGenerator(name = "student_id_seq", sequenceName = "student_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(name = "academic_degree", nullable = false)
    @Enumerated(EnumType.STRING)
    private AcademicDegree academicDegree;

    @Column(name = "education_form", nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationForm educationForm;

    @Column(nullable = false)
    private Integer course;

    @Column(nullable = false)
    private Integer team;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

}
