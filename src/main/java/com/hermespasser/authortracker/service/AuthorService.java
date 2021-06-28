package com.hermespasser.authortracker.service;

import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.error.AuthorNotFoundException;
import com.hermespasser.authortracker.mapper.AuthorMapper;
import com.hermespasser.authortracker.mapper.AuthorMapperImp;
import com.hermespasser.authortracker.MessageResponse;
import com.hermespasser.authortracker.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper = new AuthorMapperImp();

    public MessageResponse createAuthor(AuthorDto p) {
        var authorToSave = this.authorMapper.toModel(p);
        var savedAuthor = this.authorRepository.save(authorToSave);
        return createMessageResponse("Created author with id " + savedAuthor.getId(), this.authorMapper.toDto(savedAuthor));
    }

    public void deleteById(Long id) throws AuthorNotFoundException {
        findIfExists(id);
        this.authorRepository.deleteById(id);
    }

    public AuthorDto findById(Long id) throws AuthorNotFoundException {
        return findIfExists(id);
    }

    public List<AuthorDto> listAll() {
        var all = this.authorRepository.findAll();
        return all.stream().map(this.authorMapper::toDto).collect(Collectors.toList());
    }

    public MessageResponse updateById(long id, AuthorDto pd) throws AuthorNotFoundException {
        findIfExists(id);
        var authorToUpdate = this.authorMapper.toModel(pd);
        var updatedAuthor = this.authorRepository.save(authorToUpdate);
        return createMessageResponse("Updated author with id " + updatedAuthor.getId(), this.authorMapper.toDto(updatedAuthor));
    }

    private MessageResponse createMessageResponse(String msg, AuthorDto a) {
        return MessageResponse.builder().message(msg).data(a).build();
    }

    private AuthorDto findIfExists(Long id) throws AuthorNotFoundException {
        var p = this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return this.authorMapper.toDto(p);
    }
}
