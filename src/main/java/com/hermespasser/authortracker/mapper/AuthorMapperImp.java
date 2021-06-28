package com.hermespasser.authortracker.mapper;
import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.entity.Author;

public class AuthorMapperImp implements AuthorMapper {
    public AuthorMapperImp() {}

    public Author toModel(AuthorDto authorDto) {
        var author = new Author();
        author.setId(authorDto.getId());
        author.setPenName(authorDto.getPenName());
        author.setAltName(authorDto.getAltName());
        author.setProfileImgUrl(authorDto.getProfileImgUrl());
        author.setComics(authorDto.getComics());
        return author;
    }

    public AuthorDto toDto(Author author) {
        var dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setPenName(author.getPenName());
        dto.setAltName(author.getAltName());
        dto.setProfileImgUrl(author.getProfileImgUrl());
        dto.setComics(author.getComics());
        return dto;
    }
}