package com.servicio.reservas.reportes.infraestructure.persistence;

import com.servicio.reservas.reportes.infraestructure.persistence.entity.ReportReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringReportReservationRepository extends JpaRepository<ReportReservationModel, Long> {
    Optional<ReportReservationModel> findByReservationId(Long reservationId);

    @Query("""
    SELECT r FROM ReportReservationModel r
    WHERE r.status = 'COMPLETED'
    AND r.date >= :startDate
""")
    List<ReportReservationModel> findCompletedStatusByDate(@Param("startDate") LocalDate startDate);
}
