package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.model.Grid;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql("GridColumnControllerTest.sql")
public class GridRowControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/grid/row";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insert_addsNewRowToGrid() throws Exception {
        mockMvc.perform(post(BASE_URL).param("afterRowId", "5550005551201").accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json("{id: 5550005552001}"));
        flushAndClear();

        Grid grid = findByName(Grid.class, "A");
        assertThat(grid.getRows()).hasSize(5);
        assertThat(grid.getRows().get(0)).hasFieldOrPropertyWithValue("id", 5550005551201L);
        assertThat(grid.getRows().get(0)).hasFieldOrPropertyWithValue("number", 1);
        assertThat(grid.getRows().get(1)).hasFieldOrPropertyWithValue("id", 5550005552001L);
        assertThat(grid.getRows().get(1)).hasFieldOrPropertyWithValue("number", 2);
        assertThat(grid.getRows().get(2)).hasFieldOrPropertyWithValue("id", 5550005551202L);
        assertThat(grid.getRows().get(2)).hasFieldOrPropertyWithValue("number", 3);
        assertThat(grid.getRows().get(3)).hasFieldOrPropertyWithValue("id", 5550005551203L);
        assertThat(grid.getRows().get(3)).hasFieldOrPropertyWithValue("number", 4);
        assertThat(grid.getRows().get(4)).hasFieldOrPropertyWithValue("id", 5550005551204L);
        assertThat(grid.getRows().get(4)).hasFieldOrPropertyWithValue("number", 5);
    }

    @Test
    public void delete_deletesRowFromGrid() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", 5550005551202L))
                .andExpect(status().isNoContent());
        flushAndClear();

        Grid grid = findByName(Grid.class, "A");
        assertThat(grid.getRows()).hasSize(3);
        assertThat(grid.getRows().get(0)).hasFieldOrPropertyWithValue("id", 5550005551201L);
        assertThat(grid.getRows().get(0)).hasFieldOrPropertyWithValue("number", 1);
        assertThat(grid.getRows().get(1)).hasFieldOrPropertyWithValue("id", 5550005551203L);
        assertThat(grid.getRows().get(1)).hasFieldOrPropertyWithValue("number", 2);
        assertThat(grid.getRows().get(2)).hasFieldOrPropertyWithValue("id", 5550005551204L);
        assertThat(grid.getRows().get(2)).hasFieldOrPropertyWithValue("number", 3);
    }

    @Disabled // ToDo: enable
    @Test
    public void getCommonPrefix_evaluatesCorrectly() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}/common-prefix", 5550005551201L).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json("{value: '11010'}"));
    }
}
