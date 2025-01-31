package grid.bit.repository;

import grid.bit.model.GridRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GridRowRepository extends JpaRepository<GridRow, Long> {
    @Deprecated
    @Modifying
    @Query(nativeQuery = true,
            value = """
                    update grid_row
                    set number = number + 1
                    where id in
                    (select id from grid_row where grid_id = :gridId and number > :afterNumber order by number desc)
                    """
    )
    int incNumberAfter(Long gridId, int afterNumber);

    @Query(nativeQuery = true,
            value = """
                    select * from grid_row where grid_id = :gridId and number > :afterNumber order by number desc
                    """)
    List<GridRow> findByGridIdAndAfterNumberOrderByDesc(Long gridId, int afterNumber);

    @Query(nativeQuery = true,
            value = """
                    select * from grid_row where grid_id = :gridId and number > :afterNumber order by number asc
                    """)
    List<GridRow> findByGridIdAndAfterNumberOrderByAsc(Long gridId, int afterNumber);

    //TODO обновить запрос
    @Modifying
    @Query(nativeQuery = true,
            value = """
                    update grid_row
                    set number = number - 1
                    where id in
                    (select id from grid_row where grid_id = :gridId and number > :afterNumber order by number asc)
                    """
    )
    int decNumberAfter(Long gridId, int afterNumber);

    @Modifying
    @Query(nativeQuery = true,
            value = """
                    delete from grid_row where id = :id
                    """)
    void delete(Long id);
}