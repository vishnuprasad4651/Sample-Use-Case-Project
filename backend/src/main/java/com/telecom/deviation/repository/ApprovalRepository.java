package com.telecom.deviation.repository;

import com.telecom.deviation.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByDeviationId(Long deviationId);
    List<Approval> findByApproverId(Long approverId);
    Optional<Approval> findByDeviationIdAndStage(Long deviationId, String stage);
    List<Approval> findByDeviationIdOrderByCreatedAtDesc(Long deviationId);
}