package br.com.volps.church_flow_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.volps.church_flow_api.dto.ApiResponse;
import br.com.volps.church_flow_api.dto.PersonRequest;
import br.com.volps.church_flow_api.dto.PersonResponse;
import br.com.volps.church_flow_api.entity.Person;
import br.com.volps.church_flow_api.mapper.PersonMapper;
import br.com.volps.church_flow_api.service.PersonService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<PersonResponse>> register(@RequestBody @Valid PersonRequest request) {
        Person person = PersonMapper.toEntity(request);
        Person createdPerson = personService.create(person);
        PersonResponse response = PersonMapper.toResponse(createdPerson);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonResponse>> update(@RequestBody @Valid PersonRequest request,
            @PathVariable Long id) {
        Person person = PersonMapper.toEntity(request);
        personService.update(id, person);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.of(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PersonResponse>>> list() {
        List<PersonResponse> persons = personService.list().stream()
                .map(PersonMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of(persons));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.of(null));
    }
}
