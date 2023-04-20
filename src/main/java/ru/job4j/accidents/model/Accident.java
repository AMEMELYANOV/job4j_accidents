package ru.job4j.accidents.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Модель данных инцидент
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
public class Accident {

    /**
     * Идентификатор инцидента
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Наименование инцидента
     */
    private String name;

    /**
     * Описание инцидента
     */
    private String description;

    /**
     * Адрес инцидента
     */
    private String address;

    /**
     * Тип инцидента
     */
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_type_id")
    private AccidentType type;

    /**
     * Множество правил
     */
    @Fetch(FetchMode.JOIN)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "accidents_rules",
            joinColumns = { @JoinColumn(name = "accident_id") },
            inverseJoinColumns = { @JoinColumn(name = "rule_id") })
    private Set<Rule> rules;

    /**
     * Статус инцидента
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    /**
     * Дата и время заведения или редактирования в приложении
     */
    private LocalDateTime created = LocalDateTime.now();

    /**
     * Дата и время инцидента
     */
    @Column(name = "accident_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime accidentDateTime;

    /**
     * Пользователь инцидента
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Идентификатор файла
     */
    private Integer fileId;
}