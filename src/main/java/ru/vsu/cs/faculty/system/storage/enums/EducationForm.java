package ru.vsu.cs.faculty.system.storage.enums;

public enum EducationForm {

    FULL_TIME("Очно"),
    PART_TIME("Заочно"),
    DISTANT("Дистанционно");

    private final String name;

    public String getName() {
        return name;
    }

    EducationForm(String name) {
        this.name = name;
    }
}
