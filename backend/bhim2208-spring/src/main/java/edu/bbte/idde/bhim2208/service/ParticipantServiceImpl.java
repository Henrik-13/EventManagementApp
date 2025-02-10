package edu.bbte.idde.bhim2208.service;

import edu.bbte.idde.bhim2208.dataaccess.JpaParticipantDao;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile("jpa")
public class ParticipantServiceImpl implements ParticipantService {
    @Autowired
    private JpaParticipantDao participantDao;

    @Override
    public Participant createParticipant(Participant participant) {
        return participantDao.save(participant);
    }

    @Override
    public void updateParticipant(Participant participant) {
        participantDao.save(participant);
    }

    @Override
    public Participant getParticipant(int participantId) {
        return participantDao.findById(participantId).orElse(null);
    }

    @Override
    public void deleteParticipant(int participantId) {
        participantDao.deleteById(participantId);
    }

    @Override
    public Collection<Participant> getParticipants() {
        return participantDao.findAll();
    }
}
