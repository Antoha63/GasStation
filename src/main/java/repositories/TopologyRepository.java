package repositories;

import entities.Fuel;
import entities.Topology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopologyRepository extends JpaRepository<Topology, Long> {
    Topology findByName(String name);
}
