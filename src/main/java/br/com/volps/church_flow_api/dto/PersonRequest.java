package br.com.volps.church_flow_api.dto;

import java.time.LocalDate;

import br.com.volps.church_flow_api.enums.Gender;
import br.com.volps.church_flow_api.enums.MaritalStatus;
import br.com.volps.church_flow_api.enums.MemberType;
import br.com.volps.church_flow_api.enums.State;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonRequest(

                @NotBlank @Size(max = 255) String fullName,

                @NotNull MemberType memberType,

                @NotNull Gender gender,

                LocalDate birthDate,

                MaritalStatus maritalStatus,

                @Size(max = 20) String cellphone,

                @Size(max = 20) String phone,

                @Email @Size(max = 255) String email,

                @Size(max = 10) String zipCode,

                @Size(max = 255) String street,

                @Size(max = 20) String number,

                @Size(max = 255) String complement,

                @Size(max = 255) String neighborhood,

                @Size(max = 255) String city,

                State state,

                LocalDate memberSince,

                Boolean baptizedInWater,

                @Size(max = 255) String profession,

                @Size(max = 2000) String observations) {
}
