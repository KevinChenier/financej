package ca.etsmtl.log240.financej;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;

public interface DAO  {
    public Object read(int row);
    public ResultSet readAll();
    public int create(String[] data);
    public boolean update(String fieldToUpdate, String valueToUpdate, String fieldWhere, String valueWhere);
    public boolean delete(String field, String value);
    public int count();
}
