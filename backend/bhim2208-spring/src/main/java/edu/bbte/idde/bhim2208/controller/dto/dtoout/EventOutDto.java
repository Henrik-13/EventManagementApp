package edu.bbte.idde.bhim2208.controller.dto.dtoout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOutDto {
    private Integer id;
    private String title;
    private String location;
    private String date;
    private String time;
    private String description;
    private boolean online;
}
