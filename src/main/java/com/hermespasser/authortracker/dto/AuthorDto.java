package com.hermespasser.authortracker.dto;

import com.hermespasser.authortracker.entity.Comic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String penName;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String altName;

    private String profileImgUrl;

    @Valid
    @NotEmpty
    private List<Comic> comics;
}
