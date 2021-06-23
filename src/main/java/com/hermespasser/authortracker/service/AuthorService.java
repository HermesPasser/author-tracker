package com.hermespasser.authortracker.service;

import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.error.AuthorNotFound;
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

    private AuthorRepository authorRepository;
    private final AuthorMapper authorMapper = new AuthorMapperImp();

    public MessageResponse createAuthor(AuthorDto p) {
        var authorToSave = this.authorMapper.toModel(p);
        var savedAuthor = this.authorRepository.save(authorToSave);
        return createMessageResponse("Created author with id " + savedAuthor.getId());
    }

    public void deleteById(Long id) throws AuthorNotFound {
        findIfExists(id);
        this.authorRepository.deleteById(id);
    }

    public AuthorDto findById(Long id) throws AuthorNotFound {
        return findIfExists(id);
    }

    public List<AuthorDto> listAll() {
        var all = this.authorRepository.findAll();
        return all.stream().map(this.authorMapper::toDTO).collect(Collectors.toList());
    }

    public MessageResponse updateById(long id, AuthorDto pd) throws AuthorNotFound {
        findIfExists(id);
        var authorToSave = this.authorMapper.toModel(pd);
        var updatedAuthor = this.authorRepository.save(authorToSave);
        return createMessageResponse("Updated author with id " + updatedAuthor.getId());
    }

    private MessageResponse createMessageResponse(String msg) {
        return MessageResponse.builder().message(msg).build();
    }

    private AuthorDto findIfExists(Long id) throws AuthorNotFound {
        var p = this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFound(id));
        return this.authorMapper.toDTO(p);
    }
}
