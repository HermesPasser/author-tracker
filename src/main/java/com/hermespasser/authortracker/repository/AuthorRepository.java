package com.hermespasser.authortracker.repository;

import com.hermespasser.authortracker.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>{
}
