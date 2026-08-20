package br.com.volps.church_flow_api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.entity.FollowUp;
import br.com.volps.church_flow_api.entity.Person;
import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import br.com.volps.church_flow_api.mapper.FollowUpMapper;
import br.com.volps.church_flow_api.repository.FollowUpRepository;
import br.com.volps.church_flow_api.repository.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class FollowUpService {

    private final FollowUpRepository repository;
    private final PersonRepository personRepository;

    public FollowUpService(FollowUpRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Transactional
    public FollowUp create(FollowUp followUp) {
        resolvePersons(followUp);
        validateDates(followUp);
        return repository.save(followUp);
    }

    @Transactional
    public FollowUp update(Long id, FollowUp followUp) {
        FollowUp followUpEntity = find(id);
        resolvePersons(followUp);
        FollowUpMapper.update(followUpEntity, followUp);
        validateDates(followUpEntity);
        return repository.save(followUpEntity);
    }

    @Transactional
    public FollowUp find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "Follow-up not found with id: " + id));
    }

    @Transactional
    public List<FollowUp> listByPerson(Long personId) {
        findPerson(personId);
        return repository.findByPersonIdOrderByDateDesc(personId);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Person findPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "Person not found with id: " + id));
    }

    private void resolvePersons(FollowUp followUp) {
        followUp.setPerson(findPerson(followUp.getPerson().getId()));
        followUp.setResponsible(findPerson(followUp.getResponsible().getId()));
    }

    private void validateDates(FollowUp followUp) {
        LocalDate date = followUp.getDate();
        if (date.isAfter(LocalDate.now())) {
            throw new DomainException(ErrorCode.VALIDATION_ERROR,
                    "Follow-up date cannot be in the future");
        }
        if (Boolean.TRUE.equals(followUp.getRequiresFollowUp()) && followUp.getNextFollowUpDate() == null) {
            throw new DomainException(ErrorCode.VALIDATION_ERROR,
                    "Next follow-up date is required when follow-up is required");
        }
        if (Boolean.TRUE.equals(followUp.getRequiresFollowUp()) && followUp.getNextFollowUpDate().isBefore(date)) {
            throw new DomainException(ErrorCode.VALIDATION_ERROR,
                    "Next follow-up date cannot be before the follow-up date");
        }
        if (!Boolean.TRUE.equals(followUp.getRequiresFollowUp())) {
            followUp.setNextFollowUpDate(null);
        }
    }
}
