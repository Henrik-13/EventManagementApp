package edu.bbte.idde.bhim2208.service;

import edu.bbte.idde.bhim2208.dataaccess.model.Participant;

import java.util.Collection;

public interface ParticipantService {
    Participant createParticipant(Participant participant);

    void updateParticipant(Participant participant);

    Participant getParticipant(int participantId);

    void deleteParticipant(int participantId);

    Collection<Participant> getParticipants();

}
