package ru.vsu.cs.faculty.system.storage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.vsu.cs.faculty.system.dto.TeacherCreationDto;
import ru.vsu.cs.faculty.system.storage.enums.AcademicDegree;
import ru.vsu.cs.faculty.system.storage.enums.Position;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "teacher")
public class Teacher {

    public Teacher(TeacherCreationDto teacherCreationDto) {
        this.name = teacherCreationDto.getName();
        this.email = teacherCreationDto.getEmail();
        this.phone = teacherCreationDto.getPhone();
        this.academicDegree = teacherCreationDto.getAcademicDegree();
        this.position = teacherCreationDto.getPosition();
        this.experience = teacherCreationDto.getExperience();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
    @SequenceGenerator(name = "teacher_id_seq", sequenceName = "teacher_id_seq", allocationSize = 1)
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private Integer experience;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

}
