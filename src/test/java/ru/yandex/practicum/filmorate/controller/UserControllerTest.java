package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    UserController userController;
    @Autowired
    private MockMvc mockMvc;

    public static User user = new User(1l, "test@mail.ru", "login", "name", LocalDate.of(1988, 7, 13));

    @Test
    void getAllSuccess() throws Exception {
        userController.post(user);
        userController.getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("[{\"id\":1,\"email\":\"test@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1988-07-13\"}]"));
    }

    @Test
    void putAllOk() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\":1,\"email\":\"test@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1988-07-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":1,\"email\":\"test@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1988-07-13\"}"));
    }

    @Test
    void postAllOk() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\":1,\"email\":\"test@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1988-07-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":1,\"email\":\"test@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1988-07-13\"}"));
    }

    @Test
    void mailFailure () throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\":-1,\"email\":\"test@mail\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1988-07-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void birthdayFailure () throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\":1,\"email\":\"test@mail\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"3000-07-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
