package repositories;

import entities.FuelTank;
import entities.PetrolStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelTankRepository extends JpaRepository<FuelTank, Long> {
    List<FuelTank> findAllByTopology_Id(long topology_id);
}
