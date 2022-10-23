package datalayer;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface DBConnection<T> {

    T commit(Connection connection) throws SQLException;

}
