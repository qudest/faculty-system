package ru.vsu.cs.faculty.system.storage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.vsu.cs.faculty.system.dto.FacultyCreationDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "faculty")
public class Faculty {

    public Faculty(FacultyCreationDto facultyCreationDto) {
        this.name = facultyCreationDto.getName();
        this.headName = facultyCreationDto.getHeadName();
        this.email = facultyCreationDto.getEmail();
        this.phone = facultyCreationDto.getPhone();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_id_seq")
    @SequenceGenerator(name = "faculty_id_seq", sequenceName = "faculty_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "head_name", nullable = false)
    private String headName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

}
