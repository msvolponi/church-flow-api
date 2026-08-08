package br.com.volps.church_flow_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.entity.Person;
import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import br.com.volps.church_flow_api.mapper.PersonMapper;
import br.com.volps.church_flow_api.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person create(Person person) {
        return repository.save(person);
    }

    public Person update(Long id, Person person) {
        Person personEntity = repository.findById(id)
                .orElseThrow(
                        () -> new DomainException(ErrorCode.NOT_FOUND, "Person not found with id: " + person.getId()));

        PersonMapper.update(personEntity, person);

        return repository.save(personEntity);
    }

    public void delete(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "Person not found with id: " + id));

        repository.delete(person);
    }

    public List<Person> list() {
        return repository.findAll();
    }
}