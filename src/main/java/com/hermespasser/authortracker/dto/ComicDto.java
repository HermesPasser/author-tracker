package com.hermespasser.authortracker.dto;

import com.hermespasser.authortracker.PublicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComicDto {

    private Long id;

    @NotEmpty
    private String title;
    
    @NotEmpty
    private String description;

    @NotEmpty
    private String genre;
    
    @NotEmpty
    private int year;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private PublicationStatus type;
}
