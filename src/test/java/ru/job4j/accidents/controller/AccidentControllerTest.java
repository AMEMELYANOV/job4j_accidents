package ru.job4j.accidents.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Application;
import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.*;
import ru.job4j.accidents.service.ImplAccidentJpaService;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест класс реализации контроллеров
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.controller.AccidentController
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Объект заглушка для ImplAccidentJpaService
     */
    @MockBean
    private ImplAccidentJpaService accidentService;

    /**
     * Инцидент
     */
    private Accident accident;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        AccidentType type = AccidentType.builder()
                .id(1)
                .name("type")
                .build();
        Authority authority = Authority.builder()
                .id(1)
                .authority("auth")
                .build();
        User user = User.builder()
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
                .build();
    }

    /**
     * Выполняется проверка возвращения страницы списка инцидентов.
     */
    @Test
    @WithMockUser
    public void whenGetIndexThanReturnIndexView() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /**
     * Выполняется проверка возвращения страницы добавления инцидента.
     */
    @Test
    @WithMockUser
    public void whenGetAddAccidentThanReturnAddAccidentView() throws Exception {
        this.mockMvc.perform(get("/addAccident"))
                .andDo(print())
                .andExpect(view().name("accident/addAccident"));
    }

    /**
     * Выполняется проверка возвращения страницы редактирования инцидента.
     */
    @Test
    @WithMockUser
    public void whenGetEditAccidentThanReturnEditAccidentView() throws Exception {
        when(accidentService.findById(1)).thenReturn(accident);
        this.mockMvc.perform(get("/editAccident")
                        .param("accidentId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));

        ArgumentCaptor<Integer> id = ArgumentCaptor.forClass(Integer.class);
        verify(accidentService).findById(id.capture());
        assertThat(id.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка передачи идентификатора инцидента на сервисный слой
     * для поиска инцидента по идентификатору и возвращения страницы описания
     * инцидента.
     */
    @Test
    @WithMockUser
    public void whenGetAccidentDetailsThanReturnAccidentDetailsView() throws Exception {
        when(accidentService.findById(1)).thenReturn(accident);
        this.mockMvc.perform(get("/accidentDetails")
                        .param("accidentId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/accidentDetails"));

        ArgumentCaptor<Integer> id = ArgumentCaptor.forClass(Integer.class);
        verify(accidentService).findById(id.capture());
        assertThat(id.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения страницы администрирования инцидентов.
     */
    @Test
    @WithMockUser
    public void whenGetAccidentsAdminThanReturnAccidentsAdminView() throws Exception {
        this.mockMvc.perform(get("/accidentsAdmin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/accidentsAdmin"));
    }

    /**
     * Выполняется проверка передачи идентификатора на сервисный слой для удаления
     * инцидента и редирект на страницу списка инцидентов.
     */
    @Test
    @WithMockUser
    public void whenGetDeleteAccidentThanRedirectIndexView() throws Exception {
        this.mockMvc.perform(get("/deleteAccident")
                        .param("accidentId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Integer> id = ArgumentCaptor.forClass(Integer.class);
        verify(accidentService).deleteById(id.capture());
        assertThat(id.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка передачи идентификатора на сервисный слой для удаления
     * инцидента и редирект на страницу списка инцидентов для администратора.
     */
    @Test
    @WithMockUser
    public void whenPostDeleteAccidentThanRedirectAccidentsAdminView() throws Exception {
        this.mockMvc.perform(post("/deleteAccident")
                        .param("accidentId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accidentsAdmin"));

        ArgumentCaptor<Integer> user = ArgumentCaptor.forClass(Integer.class);
        verify(accidentService).deleteById(user.capture());
        assertThat(user.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения сохранения статуса и комментария инцидента
     * инспектором и редирект на страницу описания инцидента.
     */
    @Test
    @WithMockUser
    public void whenPostInspectorActionThanRedirectAccidentDetailsView() throws Exception {
        this.mockMvc.perform(post("/inspectorAction")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accidentDetails?accidentId=" + 1));

        ArgumentCaptor<Accident> accidentArgumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).updateStatus(accidentArgumentCaptor.capture());
        assertThat(accidentArgumentCaptor.getValue()).isEqualTo(Accident.builder().id(1).build());
    }


    /**
     * Выполняется проверка выполнения передачи инцидента для сохранения на сервисный
     * слой и редирект на страницу списка инцидентов.
     */
    @Test
    @WithMockUser
    public void whenPostSaveAccidentThanRedirectIndexView() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "photo.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "photo".getBytes()
        );
        String[] rIds = new String[]{"1"};

        this.mockMvc.perform(multipart("/saveAccident")
                        .file(file)
                        .param("id", "1")
                        .with(request -> {
                            request.addParameter("rIds", rIds);
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> accidentArgumentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> stringArgumentCaptor = ArgumentCaptor.forClass(String[].class);
        ArgumentCaptor<FileDto> fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);

        verify(accidentService).createOrUpdateAccident(accidentArgumentCaptor.capture(), stringArgumentCaptor.capture(),
                fileDtoArgumentCaptor.capture());
        assertThat(accidentArgumentCaptor.getValue()).isEqualTo(Accident.builder().id(1).build());
        assertThat(fileDtoArgumentCaptor.getValue().getName()).isEqualTo("photo.jpeg");
        assertThat(stringArgumentCaptor.getValue()[0]).isEqualTo("1");
    }

}