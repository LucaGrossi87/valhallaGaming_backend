package it.epicode.valhallagaming.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "stations")
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("station-booking")
    private List<Booking> bookingList;

    @Column(nullable = false)
    private StationType stationType;

    @Column(nullable = false)
    private int seatsTotal;

    public Station(List<Booking> bookingList, StationType stationType, int seatsTotal){
        this.bookingList=bookingList;
        this.stationType=stationType;
        this.seatsTotal=seatsTotal;
    }
}
