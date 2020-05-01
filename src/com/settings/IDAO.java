package com.settings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IDAO<Object> {
    Object get(long id);
    void create(Object T);
    void delete(long id);
    void update(Object T);
    List<Object> getAll();

}
