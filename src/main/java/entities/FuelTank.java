package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "FUELTANK", uniqueConstraints = {@UniqueConstraint(columnNames = {"COORDINATEX", "COORDINATEY"})})
public class FuelTank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COORDINATEX", nullable = false)
    private int coordinateX;

    @Column(name = "COORDINATEY", nullable = false)
    private int coordinateY;

    @ManyToOne(targetEntity = Topology.class)
    @JoinColumn(nullable = false)
    private Topology topology;
}