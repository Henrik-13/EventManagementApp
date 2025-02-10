package edu.bbte.idde.bhim2208.controller.dto.dtoout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Profile("jpa")
public class ParticipantOutDto {
    private Integer id;
    private String name;
    private String email;
}
