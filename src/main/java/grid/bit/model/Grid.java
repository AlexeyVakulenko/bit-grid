package grid.bit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "grid")
@AllArgsConstructor
@NoArgsConstructor
public class Grid extends AbstractEntity<Long> {
    @NotBlank
    @Size(min = 1, max = 200)
    private String name;
    @Positive
    @Max(100000)
    private int cellSize;
    private List<GridColumn> columns;
    private List<GridRow> rows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "cell_size")
    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    @OrderBy("number")
    @OneToMany(mappedBy = "grid", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public List<GridColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GridColumn> columns) {
        this.columns = columns;
    }

    @OrderBy("number")
    @OneToMany(mappedBy = "grid", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public List<GridRow> getRows() {
        return rows;
    }

    public void setRows(List<GridRow> rows) {
        this.rows = rows;
    }
}