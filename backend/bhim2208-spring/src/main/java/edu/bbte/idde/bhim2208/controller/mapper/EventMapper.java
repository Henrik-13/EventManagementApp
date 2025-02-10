package edu.bbte.idde.bhim2208.controller.mapper;

import edu.bbte.idde.bhim2208.controller.dto.dtoin.EventInDto;
import edu.bbte.idde.bhim2208.controller.dto.dtoout.EventOutDto;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class EventMapper {
    public abstract EventOutDto toEventOutDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    public abstract Event toEvent(EventInDto eventInDto);

    public abstract Collection<EventOutDto> toEventOutDtos(Collection<Event> events);
}
