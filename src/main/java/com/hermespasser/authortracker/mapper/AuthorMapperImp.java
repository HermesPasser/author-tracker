package com.hermespasser.authortracker.mapper;
import com.hermespasser.authortracker.dto.AuthorDto;
import com.hermespasser.authortracker.entity.Author;

public class AuthorMapperImp implements AuthorMapper {

    public Author toModel(AuthorDto authorDTO) {
        var author = new Author();
        author.setId(authorDTO.getId());
        author.setPenName(authorDTO.getPenName());
        author.setAltName(authorDTO.getAltName());
        author.setProfileImgUrl(authorDTO.getProfileImgUrl());
        author.setComics(authorDTO.getComics());
        return author;
    }

    public AuthorDto toDTO(Author author) {
        var dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setPenName(author.getPenName());
        dto.setAltName(author.getAltName());
        dto.setProfileImgUrl(author.getProfileImgUrl());
        dto.setComics(author.getComics());
        return dto;
    }
}