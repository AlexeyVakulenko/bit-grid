INSERT INTO grid(id, name, cell_size) VALUES
  (5550005551, 'Foo', 12);

INSERT INTO grid_column(id, number, grid_id) VALUES
  (55500055530, 1, 5550005551);

INSERT INTO grid_row(id, number, grid_id) VALUES
  (55500055540, 1, 5550005551);

  INSERT INTO grid_cell(grid_column_id, grid_row_id, value) VALUES
    (55500055530, 55500055540, '111111111111');