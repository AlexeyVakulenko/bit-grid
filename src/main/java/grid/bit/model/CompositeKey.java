package grid.bit.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Embeddable
@EqualsAndHashCode
public class CompositeKey implements Serializable {
    @Id
    private Long gridColumnId;
    @Id
    private Long gridRowId;
//    @ManyToOne
//    @JoinColumn(name="grid_column_id", referencedColumnName="id", insertable = false, updatable = false)
//    private Long grid_column_id;

//    @ManyToOne
//    @JoinColumn(name="grid_row_id", referencedColumnName="id", insertable = false, updatable = false)
//    private Long grid_row_id;
}
