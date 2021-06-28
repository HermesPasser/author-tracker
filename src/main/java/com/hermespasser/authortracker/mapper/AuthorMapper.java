package com.hermespasser.authortracker.mapper;
import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.entity.Author;

public interface AuthorMapper {
    Author toModel(AuthorDto authorDto);

    AuthorDto toDto(Author author);
}
