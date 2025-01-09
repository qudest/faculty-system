package ru.vsu.cs.faculty.system.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.vsu.cs.faculty.system.storage.entity.Teacher;
import ru.vsu.cs.faculty.system.storage.enums.AcademicDegree;
import ru.vsu.cs.faculty.system.storage.enums.Position;

@Data
public class TeacherCreationDto {

    public TeacherCreationDto(Teacher teacher) {
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.phone = teacher.getPhone();
        this.academicDegree = teacher.getAcademicDegree();
        this.position = teacher.getPosition();
        this.experience = teacher.getExperience();
        this.facultyId = teacher.getDepartment().getFaculty().getId();
        this.departmentId = teacher.getDepartment().getId();
    }

    public TeacherCreationDto() {
    }

    @NotEmpty(message = "Имя обязательно")
    @Size(max = 128, message = "Имя слишком длинное, максимальная длина 128")
    private String name;

    @Email(message = "Некорректный email")
    @NotEmpty(message = "Email обязателен")
    private String email;

    @NotEmpty(message = "Телефон обязателен")
    @Size(max = 16, message = "Телефон слишком длинный, максимальная длина 16")
    private String phone;

    @NotNull(message = "Степень образования обязательна")
    private AcademicDegree academicDegree;

    @NotNull(message = "Должность обязательна")
    private Position position;

    @NotNull(message = "Стаж обязательнен")
    @Min(value = 0, message = "Стаж должен быть не отрицательным")
    private Integer experience;

    @NotNull(message = "Факультет обязателен")
    private Long facultyId;

    @NotNull(message = "Кафедра обязательна")
    private Long departmentId;
}
