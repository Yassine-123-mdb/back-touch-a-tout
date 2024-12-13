package com.yassine.users.repos;

import com.yassine.users.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}
