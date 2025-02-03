package grid.bit.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "grid_column")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GridColumn extends AbstractEntity<Long> {
    private int number;
    private Grid grid;
    private List<GridCell> cells = Collections.emptyList();

    public GridColumn(int number, Grid grid) {
        this.number = number;
        this.grid = grid;
    }

    @ManyToOne
    @JoinColumn(name = "grid_id")
    public Grid getGrid() {
        return grid;
    }

    @OneToMany(mappedBy = "gridColumnId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public List<GridCell> getCells() {
        return cells;
    }
}