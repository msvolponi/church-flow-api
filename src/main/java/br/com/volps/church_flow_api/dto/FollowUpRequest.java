package br.com.volps.church_flow_api.dto;

import java.time.LocalDate;

import br.com.volps.church_flow_api.enums.FollowUpReason;
import br.com.volps.church_flow_api.enums.FollowUpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FollowUpRequest(
                @NotNull Long personId,
                @NotNull Long responsibleId,
                @NotNull LocalDate date,
                @NotNull FollowUpType type,
                @NotNull FollowUpReason reason,
                @Size(max = 255) String location,
                @NotBlank @Size(max = 10000) String description,
                @NotNull Boolean requiresFollowUp,
                LocalDate nextFollowUpDate) {
}