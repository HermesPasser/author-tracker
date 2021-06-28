package com.hermespasser.authortracker;

import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.error.AuthorNotFoundException;
import com.hermespasser.authortracker.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.hermespasser.authortracker.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorControllerTests {

    private static final String AUTHOR_API_URL_PATH = "/api/v1/author";
    private static final long INVALID_AUTHOR_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void testIfPOSTCreatesAnAuthorSuccessfully() throws Exception {
        AuthorDto authorDto = AuthorDtoBuilder.builder().build().toAuthorDto();
        MessageResponse msg = MessageResponse.builder().message("Created author with id " + authorDto.getId()).data(authorDto).build();
        when(authorService.createAuthor(authorDto)).thenReturn(msg);

        mockMvc.perform(post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(authorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.penName", is(authorDto.getPenName())))
                .andExpect(jsonPath("$.altName", is(authorDto.getAltName())))
                .andExpect(jsonPath("$.profileImgUrl", is(authorDto.getProfileImgUrl())));
    }

    @Test
    void testIfPOSTThrowsAnErrorIfRequiredFieldIsMissing() throws Exception {
        AuthorDto authorDto = AuthorDtoBuilder.builder().build().toAuthorDto();
        authorDto.setAltName(null);

        mockMvc.perform(post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(authorDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testIfGETReturnsAnAuthorSuccessfully() throws Exception, AuthorNotFoundException {
        AuthorDto authorDto = AuthorDtoBuilder.builder().build().toAuthorDto();

        when(authorService.findById(authorDto.getId())).thenReturn(authorDto);
 
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH + "/" + authorDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.penName", is(authorDto.getPenName())))
                .andExpect(jsonPath("$.altName", is(authorDto.getAltName())))
                .andExpect(jsonPath("$.profileImgUrl", is(authorDto.getProfileImgUrl())));
    }

    @Test
    void testIfGETReturnsNotFoundIfTheAuthorIsNotRegistered() throws Exception, AuthorNotFoundException {
         AuthorDto authorDto = AuthorDtoBuilder.builder().build().toAuthorDto();

        when(authorService.findById(authorDto.getId())).thenThrow(AuthorNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH + "/" + authorDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testIfGETReturnsAllAuthorsRegistered() throws Exception {
        AuthorDto authorDto = AuthorDtoBuilder.builder().build().toAuthorDto();

        when(authorService.listAll()).thenReturn(Collections.singletonList(authorDto));

        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testIfDELETEReturnsNoContentStatusSuccessfully() throws Exception, AuthorNotFoundException {
        AuthorDto authorDto = AuthorDtoBuilder.builder().build().toAuthorDto();

        doNothing().when(authorService).deleteById(authorDto.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(AUTHOR_API_URL_PATH + "/" + authorDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testIfDELETEReturnsNotFoundIfAInvalidAuthorIdIsPassed() throws Exception, AuthorNotFoundException {
        doThrow(AuthorNotFoundException.class).when(authorService).deleteById(INVALID_AUTHOR_ID);

        //assertThrows(AuthorNotFound.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.delete(AUTHOR_API_URL_PATH + "/" + INVALID_AUTHOR_ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        //});
    }
}
