package edu.bbte.idde.bhim2208.controller;

import edu.bbte.idde.bhim2208.controller.dto.dtoin.ParticipantInDto;
import edu.bbte.idde.bhim2208.controller.dto.dtoout.ParticipantOutDto;
import edu.bbte.idde.bhim2208.controller.mapper.ParticipantMapper;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import edu.bbte.idde.bhim2208.service.EventService;
import edu.bbte.idde.bhim2208.service.ParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/events/{eventId}/participants")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@Profile("jpa")
public class ParticipantController {
    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantMapper participantMapper;

    @GetMapping
    public Collection<ParticipantOutDto> getAllParticipants(@PathVariable Integer eventId)
            throws EventNotFoundException {
        log.info("GET /api/events/{}/participants", eventId);
        Event event = eventService.getEvent(eventId);
        return participantMapper.toDtos(eventService.findParticipantsByEvent(event));
    }

    @PostMapping
    public ParticipantOutDto createParticipant(@PathVariable Integer eventId,
                                               @RequestBody ParticipantInDto participantInDto)
            throws EventNotFoundException {
        log.info("POST /api/events/{}/participants", eventId);
        Event event = eventService.getEvent(eventId);
        Participant participant = participantMapper.toParticipant(participantInDto);
        event.getParticipants().add(participant);
        participant.setEvent(eventService.getEvent(eventId));
        return participantMapper.toDto(participantService.createParticipant(participant));
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Integer eventId, @PathVariable Integer participantId)
            throws EventNotFoundException {
        log.info("POST /api/events/{}/participants/{}", eventId, participantId);
        Event event = eventService.getEvent(eventId);
        Participant participant = participantService.getParticipant(participantId);

        if (participant == null || !event.getParticipants().contains(participant)) {
            log.warn("Participant not found in event {}", eventId);
            return ResponseEntity.notFound().build();
        }
        event.getParticipants().remove(participant);
        eventService.updateEvent(eventId, event);
        return ResponseEntity.noContent().build();
    }
}
