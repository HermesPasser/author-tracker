package com.hermespasser.authortracker;
import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.entity.Author;
import com.hermespasser.authortracker.error.AuthorNotFoundException;
import com.hermespasser.authortracker.mapper.AuthorMapper;
import com.hermespasser.authortracker.mapper.AuthorMapperImp;
import com.hermespasser.authortracker.repository.AuthorRepository;
import com.hermespasser.authortracker.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorServiceTests {
    private final AuthorMapper authorMapper = new AuthorMapperImp();

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void testIfAuthorCreationSucceed() {
        // Currently if the author already exists we update it.
        // TODO: test creating authors with registered comics

        AuthorDto expectedAuthorDto = AuthorDtoBuilder.builder().build().toAuthorDto();
        Author expectedSavedAuthor = authorMapper.toModel(expectedAuthorDto);

        when(authorRepository.findById(expectedAuthorDto.getId())).thenReturn(Optional.empty());
        when(authorRepository.save(expectedSavedAuthor)).thenReturn(expectedSavedAuthor);

        AuthorDto createdAuthorDto = (AuthorDto) authorService.createAuthor(expectedAuthorDto).getData();
        assertThat(createdAuthorDto.getId(), is(equalTo(expectedAuthorDto.getId())));
        assertThat(createdAuthorDto.getPenName(), is(equalTo(expectedAuthorDto.getPenName())));
        assertThat(createdAuthorDto.getAltName(), is(equalTo(expectedAuthorDto.getAltName())));
        assertThat(createdAuthorDto.getProfileImgUrl(), is(equalTo(expectedAuthorDto.getProfileImgUrl())));
    }

    @Test
    void testSeachAuthorByIdSucceed() throws AuthorNotFoundException {
        AuthorDto expectedFoundAuthorDto = AuthorDtoBuilder.builder().build().toAuthorDto();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDto);

        when(authorRepository.findById(expectedFoundAuthor.getId())).thenReturn(Optional.of(expectedFoundAuthor));

        AuthorDto foundAuthorDto = authorService.findById(expectedFoundAuthorDto.getId());
        assertThat(foundAuthorDto, is(equalTo(expectedFoundAuthorDto)));
    }

    @Test
    void testThrowExceptionIfTheAuthorIsNotFound() {
        AuthorDto expectedFoundAuthorDto = AuthorDtoBuilder.builder().build().toAuthorDto();

        when(authorRepository.findById(expectedFoundAuthorDto.getId())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.findById(expectedFoundAuthorDto.getId()));
    }

   @Test
    void testIfListAllAuthorsReturnsANonEmptyList() {
        AuthorDto expectedFoundAuthorDto = AuthorDtoBuilder.builder().build().toAuthorDto();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDto);

        when(authorRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundAuthor));

        List<AuthorDto> foundListAuthorsDto = authorService.listAll();
        assertThat(foundListAuthorsDto, is(not(empty())));
        assertThat(foundListAuthorsDto.get(0), is(equalTo(expectedFoundAuthorDto)));
    }

    @Test
    void testIfListAllAuthorsReturnsAEmptyList() {
        when(authorRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<AuthorDto> foundListAuthorsDto = authorService.listAll();
        assertThat(foundListAuthorsDto, is(empty()));
    }

    @Test
    void testIfAuthorDeletionSucceed() throws AuthorNotFoundException {
        AuthorDto expectedDeletedAuthorDto = AuthorDtoBuilder.builder().build().toAuthorDto();
        Author expectedDeletedAuthor = authorMapper.toModel(expectedDeletedAuthorDto);

        when(authorRepository.findById(expectedDeletedAuthorDto.getId())).thenReturn(Optional.of(expectedDeletedAuthor));
        doNothing().when(authorRepository).deleteById(expectedDeletedAuthorDto.getId());

        authorService.deleteById(expectedDeletedAuthorDto.getId());
        verify(authorRepository, times(1)).findById(expectedDeletedAuthorDto.getId());
        verify(authorRepository, times(1)).deleteById(expectedDeletedAuthorDto.getId());
    }
}
