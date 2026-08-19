package br.com.volps.church_flow_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.entity.Person;
import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import br.com.volps.church_flow_api.mapper.PersonMapper;
import br.com.volps.church_flow_api.repository.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Person create(Person person) {
        if (person.getSpouse() != null) {
            Person spouse = repository.getReferenceById(
                    person.getSpouse().getId());

            associateSpouse(person, spouse);
        }

        return repository.save(person);
    }

    @Transactional
    public Person update(Long id, Person person) {
        Person personEntity = repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "Person not found with id: " + id));

        PersonMapper.update(personEntity, person);

        if (person.getSpouse() != null) {
            Person spouse = repository.getReferenceById(person.getSpouse().getId());
            associateSpouse(personEntity, spouse);
        }

        return repository.save(personEntity);
    }

    @Transactional
    public void delete(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "Person not found with id: " + id));

        if (person.getSpouse() != null) {
            Person spouse = person.getSpouse();
            spouse.setSpouse(null);
            repository.save(spouse);
        }

        repository.delete(person);
    }

    public List<Person> list() {
        return repository.findAll();
    }

    private void associateSpouse(Person person, Person spouse) {
        if (spouse == null) {
            return;
        }

        if (spouse.getSpouse() != null
                && !spouse.getSpouse().getId().equals(person.getId())) {
            throw new DomainException(ErrorCode.BUSINESS_ERROR, "Person already has a spouse");
        }

        spouse.setSpouse(person);
        person.setSpouse(spouse);
    }
}