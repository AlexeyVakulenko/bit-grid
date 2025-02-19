package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.model.CompositeKey;
import grid.bit.model.GridCell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql("GridCellControllerTest.sql")
public class GridCellControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/grid/cell";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void set_changesValueInCell() throws Exception {
        mockMvc.perform(post(BASE_URL + "/{columnId}/{rowId}", 55500055530L, 55500055540L)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .content("{\"value\":\"100110111011\"}"))
                .andExpect(status().isNoContent());
        flush();

        GridCell gridCell = find(GridCell.class, new CompositeKey(55500055530L, 55500055540L));
        Assertions.assertEquals("100110111011", gridCell.getValue());
    }
}