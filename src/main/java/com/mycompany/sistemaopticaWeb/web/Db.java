package com.mycompany.sistemaoptica.web;

import java.sql.*;
import java.util.*;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class Db {
    private final String url;
    private final String token;
    public Db() {
        String configured = System.getenv().getOrDefault("TURSO_DATABASE_URL", "https://optica-db-davidgomez188.aws-us-east-1.turso.io").trim();
        this.url = configured.startsWith("jdbc:") ? configured : "jdbc:dbeaver:libsql:" + configured;
        this.token = System.getenv("TURSO_AUTH_TOKEN");
    }
    public Connection connection() throws SQLException {
        if (token == null || token.isBlank()) throw new SQLException("Falta configurar TURSO_AUTH_TOKEN en las variables de entorno.");
        return DriverManager.getConnection(url, null, token);
    }
    public List<Map<String,Object>> query(String sql, Object... params) throws SQLException {
        try(Connection c=connection(); PreparedStatement p=c.prepareStatement(sql)) {
            bind(p, params); try(ResultSet r=p.executeQuery()) {
                List<Map<String,Object>> rows=new ArrayList<>(); ResultSetMetaData md=r.getMetaData(); int n=md.getColumnCount();
                while(r.next()){ Map<String,Object> row=new LinkedHashMap<>(); for(int i=1;i<=n;i++) row.put(md.getColumnLabel(i),r.getObject(i)); rows.add(row); } return rows;
            }
        }
    }
    public int execute(String sql, Object... params) throws SQLException {
        try(Connection c=connection(); PreparedStatement p=c.prepareStatement(sql)) {
            bind(p,params); boolean result=p.execute();
            if(result) { try(ResultSet rs=p.getResultSet()){ while(rs!=null && rs.next()){} } }
            // libSQL JDBC driver may not implement executeUpdate; for DML, no exception means execution was accepted.
            return 1;
        }
    }
    private void bind(PreparedStatement p,Object... params)throws SQLException {
        for(int i=0;i<params.length;i++) {
            Object v=params[i]; int index=i+1;
            if(v==null) p.setNull(index, java.sql.Types.NULL);
            else if(v instanceof String x) p.setString(index,x);
            else if(v instanceof Integer x) p.setInt(index,x);
            else if(v instanceof Long x) p.setLong(index,x);
            else if(v instanceof Double x) p.setDouble(index,x);
            else if(v instanceof BigDecimal x) p.setBigDecimal(index,x);
            else if(v instanceof Boolean x) p.setBoolean(index,x);
            else p.setString(index,v.toString());
        }
    }
    public int nextId(String table,String column)throws SQLException {
        List<Map<String,Object>> r=query("SELECT COALESCE(MAX("+column+"),0)+1 AS next_id FROM "+table);
        return ((Number)r.get(0).get("next_id")).intValue();
    }
}
