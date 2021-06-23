package com.hermespasser.authortracker;

import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.error.AuthorNotFound;
import com.hermespasser.authortracker.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse create(@RequestBody @Valid AuthorDto p) {
        return this.authorService.createAuthor(p);
    }

    @GetMapping("/{id}")
    public AuthorDto find(@PathVariable Long id) throws AuthorNotFound {
        return this.authorService.findById(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws AuthorNotFound {
        this.authorService.deleteById(id);
    }

    @GetMapping
    public List<AuthorDto> list() {
        return this.authorService.listAll();
    }

    @PutMapping("/{id}")
    public MessageResponse update(@PathVariable Long id, @RequestBody @Valid AuthorDto authorDto) throws AuthorNotFound {
        return this.authorService.updateById(id, authorDto);
    }
}
