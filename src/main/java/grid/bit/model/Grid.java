package grid.bit.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "grid")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Grid extends AbstractEntity<Long> {
    @NotBlank
    @Size(min = 1, max = 200)
    private String name;
    @Positive
    @Max(100000)
    private int cellSize;
    private List<GridColumn> columns;
    private List<GridRow> rows;

    @Column(name = "cell_size")
    public int getCellSize() {
        return cellSize;
    }

    @OrderBy("number")
    @OneToMany(mappedBy = "grid", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public List<GridColumn> getColumns() {
        return columns;
    }

    @OrderBy("number")
    @OneToMany(mappedBy = "grid", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public List<GridRow> getRows() {
        return rows;
    }
}