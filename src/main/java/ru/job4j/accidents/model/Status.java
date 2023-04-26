package ru.job4j.accidents.model;

/**
 * Перечисление статус инцидента
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public enum Status {

    /**
     * Статус ПРИНЯТ
     */
    ACCEPTED("Принят"),

    /**
     * Статус ОТКЛОНЕН
     */
    REJECTED("Отклонен"),

    /**
     * Статус ЗАКРЫТ
     */
    CLOSED("Закрыт"),

    /**
     * Статус НОВЫЙ
     */
    NEW("Новый");

    /**
     * Имя перечисления
     */
    private final String displayName;

    /**
     * Конструктор
     *
     * @param displayName имя перечисления
     */
    private Status(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Геттер
     *
     * @return  displayName имя перечисления
     */
    public String getDisplayName() {
        return displayName;
    }
}