package com.hermespasser.authortracker;

import com.hermespasser.authortracker.dto.AuthorDto;
import lombok.Builder;

import java.util.List;

@Builder
public class AuthorDtoBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String penName = "Yuna K.";

    @Builder.Default
    private String AltName = "Yuta K.";

    @Builder.Default
    private String ImgLoc = "kagesaki.png";

    public AuthorDto toAuthorDto() {
        return new AuthorDto(id,
                penName,
                AltName,
                ImgLoc,
                List.of());
    }
}
