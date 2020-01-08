package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TOPOLOGY")
public class Topology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "HEIGHT", nullable = false)
    private int height;

    @Column(name = "WIDTH", nullable = false)
    private int width;

    @OneToMany(mappedBy = "topology", cascade = CascadeType.ALL)
    private List<PetrolStation> petrolStationList;

    @OneToMany(mappedBy = "topology", cascade = CascadeType.ALL)
    private List<FuelTank> fuelTankList;

    @Column(name = "ENTRANCEX")
    private int entranceX;

    @Column(name = "ENTRANCEY")
    private int entranceY;

    @Column(name = "EXITX")
    private int exitX;

    @Column(name = "EXITY")
    private int exitY;

    @Column(name = "CASHBOXX")
    private int cashBoxX;

    @Column(name = "CASHBOXY")
    private int cashBoxY;
}
