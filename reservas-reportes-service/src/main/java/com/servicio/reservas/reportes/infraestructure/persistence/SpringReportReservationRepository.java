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
    SELECT r.barberId AS barberId,
           r.nameBarber AS nameBarber,
           COUNT(r.id) AS totalReservations
    FROM ReportReservationModel r
    WHERE r.status = 'COMPLETED'
    AND r.date BETWEEN :startDate AND :endDate
    GROUP BY r.barberId, r.nameBarber
    ORDER BY totalReservations DESC
""")
    List<Object[]> findMostBusyBarbers(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
    SELECT r FROM ReportReservationModel r
    WHERE r.status = 'COMPLETED'
    AND r.date >= :startDate
""")
    List<ReportReservationModel> findCompletedStatusByDate(@Param("startDate") LocalDate startDate);

    @Query(
            "SELECT COALESCE(SUM(r.amount), 0.0) FROM ReportReservationModel r " +
            "WHERE r.status = 'COMPLETED' " +
            "AND r.date BETWEEN :startDate AND :endDate")
    Double getTotalByRange(@Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);

}
