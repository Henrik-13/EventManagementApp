package edu.bbte.idde.bhim2208.controller.dto.dtoin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Profile("jpa")
public class ParticipantInDto {
    @NotBlank
    @Size(max = 50)
    String name;
    @NotBlank
    @Size(max = 50)
    String email;
}
