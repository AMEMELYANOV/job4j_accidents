# job4j_accidents

# **Проект - Автонарушители**
## <p id="contents">Оглавление</p>

<ul>
<li><a href="#01">Описание проекта</a></li>
<li><a href="#02">Стек технологий</a></li>
<li><a href="#03">Требования к окружению</a></li>
<li><a href="#04">Сборка и запуск проекта</a>
    <ol type="1">
        <li><a href="#0401">Сборка проекта</a></li>
        <li><a href="#0402">Запуск проекта</a></li>
    </ol>
</li>
<li><a href="#05">Взаимодействие с приложением</a>
    <ol  type="1">
        <li><a href="#0501">Страница регистрации</a></li>
        <li><a href="#0502">Страница входа</a></li>
        <li><a href="#0503">Страница списка инцидентов</a></li>
        <li><a href="#0504">Страница создания инцидента</a></li>
        <li><a href="#0505">Страница с подробной информацией об инциденте</a></li>
        <li><a href="#0506">Страница редактирования инцидента</a></li>
        <li><a href="#0507">Редактирование профиля</a></li>
        <li><a href="#0508">Работа с правами инспектора</a></li>
        <li><a href="#0509">Работа с правами администратора</a></li>
        <li><a href="#0510">Выход из приложения</a></li>
    </ol>
</li>
<li><a href="#contacts">Контакты</a></li>
</ul>

## <p id="01">Описание проекта</p>

* Приложение позволяет пользователям добавлять в систему инциденты связанные с дорожно-транспортными происшествиями.
* При добавлении инцидента доступно подробное описания: описание события, время, адрес, тип, нарушения ПДД, фотография.
* Доступны роли: администраторы, инспекторы и пользователи, у разных ролей различный функционал.
* Пользователь добавляет инциденты, инспектор рассматривает и принимает в работу, либо отклоняет, администратор
управляет списком пользователей и списком инцидентов.

<p><a href="#contents">К оглавлению</a></p>

## <p id="02">Стек технологий</p>

- Java 17
- Spring Boot 2.7
- HTML 5, Thymeleaf, Bootstrap 4
- Spring Data, Spring Security
- PostgreSQL 14
- JUnit 5
- Maven 3.8
- Lombok 1.18

Инструменты:

- Javadoc, JaCoCo, Checkstyle

<p><a href="#contents">К оглавлению</a></p>

## <p id="03">Требования к окружению</p>

Java 18, Maven 3.8, PostgreSQL 14

<p><a href="#contents">К оглавлению</a></p>

## <p id="04">Сборка и запуск проекта</p>

### <p id="0401">1. Сборка проекта</p>

Команда для сборки в jar:
`mvn clean package -DskipTests`

<p><a href="#contents">К оглавлению</a></p>

### <p id="0402">2. Запуск проекта</p>

Перед запуском проекта необходимо создать базу данных accidents
в PostgreSQL, команда для создания базы данных:
`create database accidents;`
Средство миграции Liquibase автоматически создаст структуру
базы данных и наполнит ее предустановленными данными.
Команда для запуска приложения:
`mvn spring-boot:run`

<p><a href="#contents">К оглавлению</a></p>

## <p id="05">Взаимодействие с приложением</p>

Локальный доступ к приложению осуществляется через любой современный браузер
по адресу `http://localhost:8080`

### <p id="0501">1. Страница регистрации</p>

На странице регистрации пользователю необходимо заполнить поля:
имя, ввести пароль.

![alt text](img/accident_0_1.png)

При ошибках регистрации на странице будут отражены замечания.

![alt text](img/accident_0_2.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0502">2. Страница входа</p>

На странице входа необходимо указать имя и ввести свой пароль.

![alt text](img/accident_0_3.png)

При неправильных учетных данных будет выведено
сообщение на странице входа.

![alt text](img/accident_0_4.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0503">3. Страница списка инцидентов</p>

На странице списка инцидентов, при клике по названию инцидента
происходит переход на страницу с подробной информацией об инциденте.

![alt text](img/accident_1_1.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0504">4. Страница создания инцидента</p>

На странице необходимо задать: наименование инцидента, адрес, подробное описание, 
тип, нарушенные правила, дата и время и фотографию,
приоритет и выбрать одну или несколько категорий.
По умолчанию статус инцидента будет `Новый`'.

![alt text](img/accident_2.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0505">5. Страница с подробной информацией об инциденте</p>

На странице отображается подробное описание инцидента, есть возможность перейти к 
странице редактирования (кнопка 'Редактировать') и удалить инцидент (кнопка 'Удалить').

![alt text](img/accident_3_1.png)

Если пользователь заходит не в свой инцидент, раздел с действиями на странице
отсутствует.

![alt text](img/accident_3_2.png)

В конце страницы располагается информация о текущем статусе рассмотрения 
инцидента со стороны инспектора.

![alt text](img/accident_3_3.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0506">6. Страница редактирования инцидента</p>

Страница аналогична странице по созданию инцидента. Поля редактирования свойств
заполнены данными редактируемого инцидента.

![alt text](img/accident_4.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0507">7. Редактирование профиля</p>

На странице редактирования профиля можно изменить: имя, пароль.

![alt text](img/accident_5_1.png)

Проверка новых значений данных пользователя происходит по правилам регистрации
и аналогичным выводом ошибок заполнения в соответствующие поля.

Ошибка имени пользователя.

![alt text](img/accident_5_2.png)

Ошибка ввода пароля.

![alt text](img/accident_5_3.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0508">8. Работа с правами инспектора</p>

На странице с подробной информацией об инциденте становится доступен `Блок инспектора`
с возможностью изменению статуса инцидента (Принят, Отклонен, Закрыт, Новый) и с полем
добавления комментария, который будет виден пользователям.

![alt text](img/accident_5_4.png)

Отображение статуса инцидента, комментария инспектора и его имени выводится в блок
`Данные рассмотрения инцидента`.

![alt text](img/accident_5_5.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0509">9. Работа с правами администратора</p>

При работе с правами администратора в основном меню становятся доступны дополнительные
формы: `Удаление инцидента`, `Пользователи`.

![alt text](img/accident_5_6.png)

На странице`Удаление инцидента` есть возможность удалить любой инцидент.

![alt text](img/accident_5_7.png)

На странице `Пользователи` доступно изменение ролей и удаление пользователей.

![alt text](img/accident_5_8.png)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0510">10. Выход из приложения</p>

При нажатии в панели навигации на ссылку "Выход", происходит
выход пользователя из приложения с перенаправлением на страницу входа и
сообщением о том, что пользователь вышел.

![alt text](img/accident_6.png)

<p><a href="#contents">К оглавлению</a></p>

## <p id="contacts">Контакты</p>

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/T_AlexME)
&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:amemelyanov@yandex.ru)
&nbsp;&nbsp;

<p><a href="#contents">К оглавлению</a></p>