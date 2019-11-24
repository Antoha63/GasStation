package repositories;

import entities.FuelTank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTankRepository extends JpaRepository<FuelTank, Long> {
}
