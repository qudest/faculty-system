package ru.vsu.cs.faculty.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.vsu.cs.faculty.system.storage.entity.Department;

@Data
public class DepartmentCreationDto {

    public DepartmentCreationDto(Department department) {
        this.name = department.getName();
        this.headName = department.getHeadName();
        this.email = department.getEmail();
        this.phone = department.getPhone();
        this.facultyId = department.getFaculty().getId();
    }

    public DepartmentCreationDto() {
    }

    @NotEmpty(message = "Название обязательно")
    @Size(max = 128, message = "Название слишком длинное, максимальная длина 128")
    private String name;

    @NotEmpty(message = "Имя руководителя обязательно")
    @Size(max = 128, message = "Имя руководителя слишком длинное, максимальная длина 128")
    private String headName;

    @NotEmpty(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @NotEmpty(message = "Телефон обязателен")
    @Size(max = 16, message = "Телефон слишком длинный, максимальная длина 16")
    private String phone;

    @NotNull(message = "Факультет обязателен")
    private Long facultyId;

}
