package ru.job4j.accidents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.accidents.Application;
import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

/**
 * Тест класс реализации сервисного слоя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.service.ImplAccidentJpaServiceTest
 */
@SpringBootTest(classes = Application.class)
class ImplAccidentJpaServiceTest {


    /**
     * Объект заглушка для AccidentRepository
     */
    @MockBean
    private AccidentRepository accidentRepository;

    /**
     * Объект заглушка для AccidentTypeRepository
     */
    @MockBean
    private AccidentTypeRepository accidentTypeRepository;

    /**
     * Объект для доступа к методам RuleRepository
     */
    @MockBean
    private RuleRepository ruleRepository;

    /**
     * Объект для доступа к методам AccidentService
     */
    private AccidentService accidentService;

    /**
     * Объект для доступа к методам FileService
     */
    @MockBean
    private FileService fileService;

    /**
     * Объект для доступа к методам FileService
     */
    private UserService userService;

    /**
     * Инцидент
     */
    private Accident accident;

    /**
     * Файл dto
     */
    private FileDto image;

    /**
     * Файл
     */
    private File file;

    /**
     * Тип инцидента
     */
    private AccidentType type;

    /**
     * Тип инцидента
     */
    private Rule rule;

    /**
     * Роль пользователя
     */
    private Authority authority;

    /**
     * Пользователь
     */
    private User user;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        type = AccidentType.builder()
                .id(1)
                .name("type")
                .build();
        rule = Rule.builder().id(1).name("rule").build();
        authority = Authority.builder()
                .id(1)
                .authority("auth")
                .build();
        user = User.builder()
                .id(1)
                .username("username")
                .password("pass")
                .enabled(true)
                .authority(authority)
                .build();
        accident = Accident.builder()
                .id(1)
                .name("name")
                .description("description")
                .address("address")
                .type(type)
                .user(user)
                .rules(Set.of())
                .status(Status.NEW)
                .accidentDateTime(LocalDateTime.now())
                .fileId(1)
                .build();
        accidentService = new ImplAccidentJpaService(accidentRepository, accidentTypeRepository,
                ruleRepository, fileService, userService);
        image = new FileDto("fileDto", "fileDto".getBytes());
        file = File.builder().id(2).name("file").path("path").build();

    }

    /**
     * Выполняется проверка возвращения списка инцидентов,
     * если в списке есть элементы.
     */
    @Test
    void whenFindAllAccidentsThenReturnList() {
        List<Accident> accidents = new ArrayList<>();
        accidents.add(accident);
        doReturn(accidents).when(accidentRepository).findAll();
        List<Accident> accidentList = accidentService.findAll();

        assertThat(accidentList).isNotNull();
        assertThat(accidentList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка инцидентов,
     * если список пустой.
     */
    @Test
    void whenFindAllAccidentsThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(accidentRepository).findAll();
        List<Accident> accidentList = accidentService.findAll();

        assertThat(accidentList).isEmpty();
    }

    /**
     * Выполняется проверка возврата инцидента, при возврате от
     * accidentRepository, если инцидент был сохранен.
     */
    @Test
    void whenAddAccidentThenReturnAccident() {
        doReturn(accident).when(accidentRepository).save(accident);
        doReturn(file).when(fileService).save(image);
        Accident accidentFromDB = accidentService.create(accident, image);
        assertThat(accidentFromDB).isEqualTo(accident);
        assertThat(accidentFromDB).isNotNull();
        assertThat(accidentFromDB.getFileId()).isEqualTo(file.getId());
    }

    /**
     * Выполняется проверка возврата инцидента, при возврате от
     * accidentRepository, если инцидент был обновлен.
     */
    @Test
    void whenUpdateAccidentThenReturnAccident() {
        doReturn(accident).when(accidentRepository).save(accident);
        doReturn(file).when(fileService).save(image);
        Accident accidentFromDB = accidentService.update(accident, image);

        assertThat(accidentFromDB).isEqualTo(accident);
        assertThat(accidentFromDB).isNotNull();
        assertThat(accidentFromDB.getFileId()).isEqualTo(file.getId());
    }

    /**
     * Выполняется проверка возвращения инцидента, при возврате
     * от accidentRepository Optional.of(accident), т.е. если инцидент найден по id.
     */
    @Test
    void whenFindAccidentByIdThenReturnAccident() {
        doReturn(Optional.of(accident)).when(accidentRepository).findById(accident.getId());
        Accident accidentFromDB = accidentService.findById(accident.getId());

        assertThat(accidentFromDB).isEqualTo(accident);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * accidentRepository Optional.empty(), если инцидент не найден по id.
     */
    @Test
    void whenFindAccidentByIdThenThrowsException() {
        doReturn(Optional.empty()).when(accidentRepository).findById(accident.getId());

        assertThrows(NoSuchElementException.class,
                () -> accidentService.findById(accident.getId()));
    }

    /**
     * Выполняется проверка возвращения списка типов инцидентов.
     */
    @Test
    void whenFindAllTypesAccidentsThenReturnList() {
        Collection<AccidentType> accidentTypes = new ArrayList<>();
        accidentTypes.add(type);
        doReturn(accidentTypes).when(accidentTypeRepository).findAll();
        List<AccidentType> accidentTypeList = accidentService.findAllAccidentTypes();

        assertThat(accidentTypeList).isNotNull();
        assertThat(accidentTypeList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка правил инцидентов.
     */
    @Test
    void whenFindAllRuleSAccidentsThenReturnList() {
        List<Rule> rules = new ArrayList<>();
        rules.add(rule);
        doReturn(rules).when(ruleRepository).findAll();
        List<Rule> ruleList = accidentService.findAllAccidentRules();

        assertThat(ruleList).isNotNull();
        assertThat(ruleList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения правила, при возврате
     * от ruleRepository Optional.of(rule), т.е. если правило найдено по id.
     */
    @Test
    void whenFindRuleByIdThenReturnRule() {
        doReturn(Optional.of(rule)).when(ruleRepository).findById(rule.getId());
        Rule ruleFromDB = accidentService.findRuleById(rule.getId());

        assertThat(ruleFromDB).isEqualTo(rule);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * ruleRepository Optional.empty(), если правило не найдено по id.
     */
    @Test
    void whenFindRuleByIdThenThrowsException() {
        doReturn(Optional.empty()).when(ruleRepository).findById(rule.getId());

        assertThrows(NoSuchElementException.class,
                () -> accidentService.findRuleById(rule.getId()));
    }

    /**
     * Выполняется проверка возвращения правил, при возврате
     * от ruleRepository, т.е. если правила найдено по списку id.
     */
    @Test
    void whenFindRulesByIdsThenReturnRuleList() {
        doReturn(Optional.of(rule)).when(ruleRepository).findById(rule.getId());
        String[] ids = new String[]{"1"};
        Set<Rule> rulesFromDB = accidentService.findRulesByIds(ids);

        assertThat(rulesFromDB.size()).isEqualTo(1);
        assertThat(rulesFromDB).isNotNull();
    }

    /**
     * Выполняется проверка возвращения пустого множества правил, при возврате
     * от ruleRepository, т.е. если правила не найдены по списку ids.
     */
    @Test
    void whenFindRulesByIdsThenReturnEmptyList() {
        doReturn(Optional.of(rule)).when(ruleRepository).findById(rule.getId());
        String[] ids = new String[]{"0"};
        Set<Rule> rulesFromDB = accidentService.findRulesByIds(ids);

        assertThat(rulesFromDB).isEmpty();
    }

    /**
     * Выполняется проверка возврата инцидента, при возврате от
     * accidentRepository, если был обновлен статус инцидента.
     */
    @Test
    void whenUpdateStatusAccidentThenReturnAccident() {
        doReturn(accident).when(accidentRepository).save(accident);
        Accident accidentFromDB = accidentService.updateStatus(accident);

        assertThat(accidentFromDB).isEqualTo(accident);
        assertThat(accidentFromDB).isNotNull();
    }
}