package repositories;

import entities.PetrolStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetrolStationRepository extends JpaRepository<PetrolStation, Long> {
}
