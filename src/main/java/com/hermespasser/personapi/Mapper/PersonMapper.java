package com.hermespasser.personapi.Mapper;
import com.hermespasser.personapi.DTO.PersonDto;
import com.hermespasser.personapi.Entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDto personDTO);

    PersonDto toDTO(Person person);
}
