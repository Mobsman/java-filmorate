package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest
//@AutoConfigureMockMvc
//class FilmControllerTest {
//    @Autowired
//    FilmController filmController;
//    @Autowired
//    private MockMvc mockMvc;
//
//    public static Film film = new Film(1l, "test film", "test description", 100, LocalDate.of(1999, 7, 14));
//
//    @Test
//    void getAllSuccess() throws Exception {
//        filmController.post(film);
//        filmController.getAll();
//        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(content()
//                        .json("[{\"id\":1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":100,\"releaseDate\":\"1999-07-14\"}]"));
//    }
//
//    @Test
//    void postSuccess() throws Exception {
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/films")
//                                .content("{\"id\":1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":100,\"releaseDate\":\"1999-07-14\"}")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(content()
//                        .json("{\"id\":1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":100,\"releaseDate\":\"1999-07-14\"}"));
//    }
//
//    @Test
//    void putSuccess() throws Exception {
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put("/films")
//                                .content("{\"id\":1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":100,\"releaseDate\":\"1999-07-14\"}")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(content()
//                        .json("{\"id\":1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":100,\"releaseDate\":\"1999-07-14\"}"));
//    }
//
//    @Test
//    void releaseDateFailure () throws Exception {
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/films")
//                                .content("{\"id\":1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":100,\"releaseDate\":\"1700-07-14\"}")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void releaseDurationFailure () throws Exception {
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/films")
//                                .content("{\"id\":-1,\"name\":\"test film\",\"description\":\"test description\",\"duration\":0,\"releaseDate\":\"1700-07-14\"}")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void releaseDescriptionFailure () throws Exception {
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/films")
//                                .content("{\"id\":-1,\"name\":\"test film\",\"description\":\"test zzzzzzzzzzzzzzzzzzzz" +
//                                        "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
//                                        "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
//                                        "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
//                                        "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
//                                        "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
//                                        "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
//                                        "\",\"duration\":0,\"releaseDate\":\"1700-07-14\"}")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @AfterEach
//    void delete() {
//        filmController.getAll().clear();
//    }
//}
