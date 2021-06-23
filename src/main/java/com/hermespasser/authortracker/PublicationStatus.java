package com.hermespasser.authortracker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PublicationStatus {
    COMPLETED("Complete"),
    ONGOING("Ongoing"),
    HIATUS("Hiatus");

    private final String description;
}
