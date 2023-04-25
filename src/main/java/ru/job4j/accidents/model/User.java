package ru.job4j.accidents.model;


import lombok.*;

import javax.persistence.*;

/**
 * Модель данных пользователь
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Статус учетной записи пользователя
     */
    private boolean enabled;

    /**
     * Роль пользователя
     */
    @ManyToOne
    @JoinColumn(name = "authority_id")
    Authority authority;
}
