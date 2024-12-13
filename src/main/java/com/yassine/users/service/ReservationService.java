package com.yassine.users.service;

import com.yassine.users.entities.Reservation;
import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;
import com.yassine.users.repos.ReservationRepository;
import com.yassine.users.repos.ServiceRepository;
import com.yassine.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation createReservation(Long userId, Long serviceId, Date reservationDate, Date reservationTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));

        // Vérifier la disponibilité du service à la date et l'heure choisies
        if (!isAvailable(service, reservationDate, reservationTime)) {
            throw new RuntimeException("Service is not available at the selected time.");
        }

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setService(service);
        reservation.setReservationDate(reservationDate);
        reservation.setReservationTime(reservationTime);
        reservation.setStatus("Confirmed");

        return reservationRepository.save(reservation);
    }

    private boolean isAvailable(Services service, Date reservationDate, Date reservationTime) {
        // Vérifier la disponibilité du service pour la date et l'heure
        // Vous pouvez implémenter une logique plus complexe si nécessaire
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            if (reservation.getService().equals(service) && reservation.getReservationDate().equals(reservationDate) &&
                    reservation.getReservationTime().equals(reservationTime)) {
                return false;  // Service déjà réservé à ce moment-là
            }
        }
        return true;
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return reservationRepository.findAll(); // Filtrer par utilisateur si nécessaire
    }
}
