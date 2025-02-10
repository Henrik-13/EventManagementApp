package edu.bbte.idde.bhim2208.controller;

import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.exception.InvalidDateTimeException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.controller.mapper.EventMapper;
import edu.bbte.idde.bhim2208.controller.dto.dtoin.EventInDto;
import edu.bbte.idde.bhim2208.controller.dto.dtoout.EventOutDto;
import edu.bbte.idde.bhim2208.service.EventService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @GetMapping
    public Collection<EventOutDto> getEvents(@RequestParam(name = "title", required = false) String title) {
        log.info("GET /events");
        if (title != null) {
            return eventMapper.toEventOutDtos(eventService.findByTitle(title));
        }
        return eventMapper.toEventOutDtos(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public EventOutDto getEvent(@PathVariable Integer id) throws EventNotFoundException {
        log.info("GET /events/{}", id);
        Event event = eventService.getEvent(id);
        return eventMapper.toEventOutDto(event);
    }

    @PostMapping
    public EventOutDto createEvent(@RequestBody @Valid EventInDto eventInDto) throws EventNotFoundException,
            InvalidDateTimeException {
        log.info("POST /events");
        validateDateTime(eventInDto.getDate(), eventInDto.getTime());
        Integer id = eventService.createEvent(eventMapper.toEvent(eventInDto));
        return eventMapper.toEventOutDto(eventService.getEvent(id));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) throws EventNotFoundException {
        log.info("DELETE /events/{}", id);
        eventService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public EventOutDto updateEvent(@PathVariable Integer id, @RequestBody @Valid EventInDto eventInDto)
            throws EventNotFoundException, InvalidDateTimeException {
        log.info("PUT /events/{}", id);
        validateDateTime(eventInDto.getDate(), eventInDto.getTime());

        eventService.updateEvent(id, eventMapper.toEvent(eventInDto));
        return eventMapper.toEventOutDto(eventService.getEvent(id));
    }

    private void validateDateTime(String date, String time) throws InvalidDateTimeException {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalTime parsedTime =  LocalTime.parse(time);
            log.debug("Parsed date: {}, time: {}", parsedDate, parsedTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException();
        }
    }

}
