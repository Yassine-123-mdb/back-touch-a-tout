package com.yassine.users.restControllers;

import com.yassine.users.entities.Reservation;
import com.yassine.users.entities.ReservationRequest;
import com.yassine.users.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Reservation> createReservation(
            @PathVariable Long userId, 
            @RequestBody ReservationRequest request) {
        
        try {
            // Utilisation de userId et des données de request pour créer la réservation
            Reservation reservation = reservationService.createReservation(
                    userId, 
                    request.getServiceId(), 
                    request.getReservationDate(), 
                    request.getReservationTime());
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
