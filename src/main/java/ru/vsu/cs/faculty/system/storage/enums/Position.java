package ru.vsu.cs.faculty.system.storage.enums;

public enum Position {

    ASSISTANT("Ассистент"),
    TEACHER("Преподаватель"),
    SENIOR_TEACHER("Старший преподаватель"),
    DOCENT("Доцент"),
    PROFESSOR("Профессор");

    private final String name;

    public String getName() {
        return name;
    }

    Position(String name) {
        this.name = name;
    }

}
