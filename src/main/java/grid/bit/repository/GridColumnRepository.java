package grid.bit.repository;

import grid.bit.model.GridColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GridColumnRepository extends JpaRepository<GridColumn, Long> {

    @Query(nativeQuery = true,
            value = """
                    select * from grid_column where grid_id = :gridId and number > :afterNumber order by number desc
                    """)
    List<GridColumn> findByGridIdAndAfterNumberOrderByDesc(Long gridId, int afterNumber);

    @Query(nativeQuery = true,
            value = """
                    select * from grid_column where grid_id = :gridId and number > :afterNumber order by number asc
                    """)
    List<GridColumn> findByGridIdAndAfterNumberOrderByAsc(Long gridId, int afterNumber);
}