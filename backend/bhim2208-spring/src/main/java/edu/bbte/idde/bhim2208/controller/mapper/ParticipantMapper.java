package edu.bbte.idde.bhim2208.controller.mapper;

import edu.bbte.idde.bhim2208.controller.dto.dtoin.ParticipantInDto;
import edu.bbte.idde.bhim2208.controller.dto.dtoout.ParticipantOutDto;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ParticipantMapper {
    public abstract ParticipantOutDto toDto(Participant participant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    public abstract Participant toParticipant(ParticipantInDto dto);

    public abstract Collection<ParticipantOutDto> toDtos(Collection<Participant> participants);
}
