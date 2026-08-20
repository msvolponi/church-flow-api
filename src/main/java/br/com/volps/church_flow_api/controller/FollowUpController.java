package br.com.volps.church_flow_api.controller;

import java.util.List;

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
import br.com.volps.church_flow_api.dto.FollowUpResponse;
import br.com.volps.church_flow_api.dto.FollowUpRequest;
import br.com.volps.church_flow_api.entity.FollowUp;
import br.com.volps.church_flow_api.mapper.FollowUpMapper;
import br.com.volps.church_flow_api.service.FollowUpService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/follow-ups")
public class FollowUpController {

    private final FollowUpService service;

    public FollowUpController(FollowUpService service) {
        this.service = service;
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<List<FollowUpResponse>>> listByPerson(@PathVariable Long personId) {
        List<FollowUpResponse> response = service.listByPerson(personId).stream()
                .map(FollowUpMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FollowUpResponse>> find(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(FollowUpMapper.toResponse(service.find(id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FollowUpResponse>> create(@RequestBody @Valid FollowUpRequest request) {
        FollowUp followUp = FollowUpMapper.toEntity(null, request);
        FollowUpResponse response = FollowUpMapper.toResponse(service.create(followUp));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FollowUpResponse>> update(@PathVariable Long id,
            @RequestBody @Valid FollowUpRequest request) {
        FollowUp followUp = FollowUpMapper.toEntity(id, request);
        FollowUpResponse response = FollowUpMapper.toResponse(service.update(id, followUp));
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.of(null));
    }
}
