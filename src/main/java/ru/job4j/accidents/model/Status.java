package ru.job4j.accidents.model;

/**
 * Перечисление статус инцидента
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public enum Status {

    /**
     * Статус ПРИНЯТО
     */
    ACCEPTED,

    /**
     * Статус ОТКЛОНЕНО
     */
    REJECTED,

    /**
     * Статус ЗАКРЫТО
     */
    CLOSED,

    /**
     * Статус НОВЫЙ
     */
    NEW
}