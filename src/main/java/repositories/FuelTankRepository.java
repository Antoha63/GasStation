package repositories;

import entities.FuelTank;
import entities.PetrolStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTankRepository extends JpaRepository<FuelTank, Long> {
    FuelTank findByTopology_Id(long topology_id);
}
