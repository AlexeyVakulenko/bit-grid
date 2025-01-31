package grid.bit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import grid.bit.model.Grid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GridRepository extends JpaRepository<Grid, Long> {

    @Modifying
    @Query("update Grid set name=:newName where id=:id")
    void updateName(Long id, String newName);
}