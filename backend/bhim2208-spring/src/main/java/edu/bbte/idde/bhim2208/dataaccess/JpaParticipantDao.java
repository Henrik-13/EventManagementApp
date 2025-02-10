package edu.bbte.idde.bhim2208.dataaccess;

import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface JpaParticipantDao extends JpaRepository<Participant, Integer> {
}
