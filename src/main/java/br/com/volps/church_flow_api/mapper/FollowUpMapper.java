package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.FollowUpRequest;
import br.com.volps.church_flow_api.dto.FollowUpResponse;
import br.com.volps.church_flow_api.entity.FollowUp;
import br.com.volps.church_flow_api.entity.Person;

public final class FollowUpMapper {

    private FollowUpMapper() {
    }

    public static FollowUpResponse toResponse(FollowUp followUp) {
        FollowUpResponse response = new FollowUpResponse();
        response.setId(followUp.getId());
        response.setPersonId(followUp.getPerson().getId());
        response.setPersonName(followUp.getPerson().getFullName());
        response.setResponsibleId(followUp.getResponsible().getId());
        response.setResponsibleName(followUp.getResponsible().getFullName());
        response.setDate(followUp.getDate());
        response.setType(followUp.getType());
        response.setTypeLabel(followUp.getType().getLabel());
        response.setReason(followUp.getReason());
        response.setReasonLabel(followUp.getReason().getLabel());
        response.setLocation(followUp.getLocation());
        response.setDescription(followUp.getDescription());
        response.setRequiresFollowUp(followUp.getRequiresFollowUp());
        response.setNextFollowUpDate(followUp.getNextFollowUpDate());
        response.setCreatedAt(followUp.getCreatedAt());
        response.setUpdatedAt(followUp.getUpdatedAt());
        return response;
    }

    public static FollowUp toEntity(Long id, FollowUpRequest request) {
        FollowUp followUp = new FollowUp();
        followUp.setId(id);

        Person person = new Person();
        person.setId(request.personId());
        followUp.setPerson(person);

        Person responsible = new Person();
        responsible.setId(request.responsibleId());
        followUp.setResponsible(responsible);

        followUp.setDate(request.date());
        followUp.setType(request.type());
        followUp.setReason(request.reason());
        followUp.setLocation(request.location());
        followUp.setDescription(request.description());
        followUp.setRequiresFollowUp(request.requiresFollowUp());
        followUp.setNextFollowUpDate(request.nextFollowUpDate());
        return followUp;
    }

    public static void update(FollowUp target, FollowUp source) {
        target.setId(source.getId());
        target.setPerson(source.getPerson());
        target.setResponsible(source.getResponsible());
        target.setDate(source.getDate());
        target.setType(source.getType());
        target.setReason(source.getReason());
        target.setLocation(source.getLocation());
        target.setDescription(source.getDescription());
        target.setRequiresFollowUp(source.getRequiresFollowUp());
        target.setNextFollowUpDate(source.getNextFollowUpDate());
    }
}
