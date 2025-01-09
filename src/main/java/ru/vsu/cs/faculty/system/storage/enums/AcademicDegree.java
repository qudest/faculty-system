package ru.vsu.cs.faculty.system.storage.enums;

public enum AcademicDegree {

    BACHELOR("Бакалавр"),
    MASTER("Магистр"),
    SPECIALIST("Специалист"),
    CANDIDATE_OF_SCIENCES("Кандидат наук"),
    DOCTOR_OF_SCIENCES("Доктор наук");

    private final String name;

    public String getName() {
        return name;
    }

    AcademicDegree(String name) {
        this.name = name;
    }
}
