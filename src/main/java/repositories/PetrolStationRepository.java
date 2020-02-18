package repositories;

import entities.PetrolStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetrolStationRepository extends JpaRepository<PetrolStation, Long> {
    List<PetrolStation> findAllByTopology_Id(long topology_id);
}
