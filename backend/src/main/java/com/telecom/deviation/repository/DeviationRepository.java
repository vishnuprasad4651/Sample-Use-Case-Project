package com.telecom.deviation.repository;

import com.telecom.deviation.entity.Deviation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviationRepository extends JpaRepository<Deviation, Long> {
    List<Deviation> findByInitiatorId(Long initiatorId);
    List<Deviation> findByStatus(String status);
    List<Deviation> findByInitiatorIdAndStatus(Long initiatorId, String status);

    @Query("SELECT d FROM Deviation d WHERE d.status = :status")
    List<Deviation> findDeviationsByStatus(@Param("status") String status);

    @Query("SELECT d FROM Deviation d WHERE d.initiatorId = :userId OR " +
           "(d.status = 'PENDING_OPS' AND EXISTS SELECT a FROM Approval a WHERE a.deviationId = d.deviationId AND a.approverId = :userId)")
    List<Deviation> findAccessibleDeviations(@Param("userId") Long userId);
}