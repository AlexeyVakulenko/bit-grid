package grid.bit.model;

import grid.bit.validator.CellValueConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@Entity
@Table(name = "grid_cell")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CompositeKey.class)
public class GridCell implements Persistable<CompositeKey> {
    @Id
    private Long gridColumnId;
    @Id
    private Long gridRowId;
    @CellValueConstraint
    private String value;

    @ManyToOne
    @JoinColumn(name = "grid_column_id")
    public Long getGridColumnId() {
        return gridColumnId;
    }

    @ManyToOne
    @JoinColumn(name = "grid_row_id")
    public Long getGridRowId() {
        return gridRowId;
    }

    @Override
    public CompositeKey getId() {
        return new CompositeKey(gridColumnId, gridRowId);
    }

    @Override
    public boolean isNew() {
        return null == getId();
    }
}
