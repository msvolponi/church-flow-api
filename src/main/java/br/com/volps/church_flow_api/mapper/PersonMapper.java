package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.PersonRequest;
import br.com.volps.church_flow_api.dto.PersonResponse;
import br.com.volps.church_flow_api.entity.Person;

public class PersonMapper {

    public static PersonResponse toResponse(Person person) {
        var response = new PersonResponse();
        response.setId(person.getId());
        response.setFullName(person.getFullName());
        response.setMemberType(person.getMemberType());
        response.setGender(person.getGender());
        response.setBirthDate(person.getBirthDate());
        response.setMaritalStatus(person.getMaritalStatus());
        response.setCellphone(person.getCellphone());
        response.setPhone(person.getPhone());
        response.setEmail(person.getEmail());
        response.setZipCode(person.getZipCode());
        response.setStreet(person.getStreet());
        response.setNumber(person.getNumber());
        response.setComplement(person.getComplement());
        response.setNeighborhood(person.getNeighborhood());
        response.setCity(person.getCity());
        response.setState(person.getState());
        response.setMemberSince(person.getMemberSince());
        response.setBaptizedInWater(person.getBaptizedInWater());
        response.setProfession(person.getProfession());
        return response;
    }

    public static Person toEntity(PersonRequest request) {
        var person = new Person();
        person.setFullName(request.fullName());
        person.setMemberType(request.memberType());
        person.setGender(request.gender());
        person.setBirthDate(request.birthDate());
        person.setMaritalStatus(request.maritalStatus());
        person.setCellphone(request.cellphone());
        person.setPhone(request.phone());
        person.setEmail(request.email());
        person.setZipCode(request.zipCode());
        person.setStreet(request.street());
        person.setNumber(request.number());
        person.setComplement(request.complement());
        person.setNeighborhood(request.neighborhood());
        person.setCity(request.city());
        person.setState(request.state());
        person.setMemberSince(request.memberSince());
        person.setBaptizedInWater(request.baptizedInWater());
        person.setProfession(request.profession());
        person.setObservations(request.observations());
        return person;
    }

    public static void update(Person target, Person source) {
        target.setFullName(source.getFullName());
        target.setMemberType(source.getMemberType());
        target.setGender(source.getGender());
        target.setBirthDate(source.getBirthDate());
        target.setMaritalStatus(source.getMaritalStatus());
        target.setCellphone(source.getCellphone());
        target.setPhone(source.getPhone());
        target.setEmail(source.getEmail());
        target.setZipCode(source.getZipCode());
        target.setStreet(source.getStreet());
        target.setNumber(source.getNumber());
        target.setComplement(source.getComplement());
        target.setNeighborhood(source.getNeighborhood());
        target.setCity(source.getCity());
        target.setState(source.getState());
        target.setMemberSince(source.getMemberSince());
        target.setBaptizedInWater(source.getBaptizedInWater());
        target.setProfession(source.getProfession());
        target.setObservations(source.getObservations());
    }
}
