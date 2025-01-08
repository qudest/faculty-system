package ru.vsu.cs.faculty.system.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.vsu.cs.faculty.system.storage.entity.Student;
import ru.vsu.cs.faculty.system.storage.enums.AcademicDegree;
import ru.vsu.cs.faculty.system.storage.enums.EducationForm;

@Data
public class StudentCreationDto {

    public StudentCreationDto() {
    }

    public StudentCreationDto(Student student) {
        this.name = student.getName();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.academicDegree = student.getAcademicDegree();
        this.educationForm = student.getEducationForm();
        this.course = student.getCourse();
        this.team = student.getTeam();
        this.facultyId = student.getDepartment().getFaculty().getId();
        this.departmentId = student.getDepartment().getId();
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

    @NotNull(message = "Форма обучения обязательна")
    private EducationForm educationForm;

    @NotNull(message = "Курс обязателен")
    @Min(value = 1, message = "Курс должен быть больше 0")
    @Max(value = 5, message = "Курс должен быть от 1 до 5")
    private Integer course;

    @NotNull(message = "Группа обязательна")
    @Min(value = 1, message = "Группа должна быть больше 0")
    private Integer team;

    @NotNull(message = "Факультет обязателен")
    private Long facultyId;

    @NotNull(message = "Кафедра обязательна")
    private Long departmentId;

}
