package com.umar.backend.participant.repository;


import com.umar.backend.participant.entity.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
    boolean existsByUserIdAndMeetingId(Long id, Long id1);
    Optional<MeetingParticipant> findByUserIdAndMeetingId(Long userId, Long meetingId);
    List<MeetingParticipant> findByMeetingId(Long meetingId);
}