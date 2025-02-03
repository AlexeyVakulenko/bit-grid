package grid.bit.repository;

import grid.bit.model.CompositeKey;
import grid.bit.model.GridCell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridCellRepository extends JpaRepository<GridCell, CompositeKey> {
}
