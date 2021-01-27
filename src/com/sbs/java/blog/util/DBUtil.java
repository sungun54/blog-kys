package com.sbs.java.blog.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.exception.SQLErrorException;

public class DBUtil {

	public static int selectRowIntValue(Connection connection, SecSql sql) {
		Map<String, Object> row = selectRow(connection, sql);

		for (String key : row.keySet()) {
			return (int) row.get(key);
		}

		return -1;
	}

	public static String selectRowStringValue(Connection connection, SecSql sql) {
		Map<String, Object> row = selectRow(connection, sql);

		for (String key : row.keySet()) {
			return (String) row.get(key);
		}

		return "";
	}

	public static Boolean selectRowBooleanValue(Connection connection, SecSql sql) {
		Map<String, Object> row = selectRow(connection, sql);

		for (String key : row.keySet()) {
			if (row.get(key) instanceof String) {
				return ((String) row.get(key)).equals("1");
			} else if (row.get(key) instanceof Integer) {
				return ((int) row.get(key)) == 1;
			} else if (row.get(key) instanceof Boolean) {
				return ((boolean) row.get(key));
			}
		}

		return false;
	}

	public static Map<String, Object> selectRow(Connection connection, SecSql sql) {
		List<Map<String, Object>> rows = selectRows(connection, sql);

		if (rows.size() == 0) {
			return new HashMap<String, Object>();
		}

		return rows.get(0);
	}

	public static List<Map<String, Object>> selectRows(Connection dbConn, SecSql sql) throws SQLErrorException {
		List<Map<String, Object>> rows = new ArrayList<>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnSize = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
					String columnName = metaData.getColumnName(columnIndex + 1);
					Object value = rs.getObject(columnName);
					if (value instanceof Long) {
						int numValue = (int) (long) value;
						row.put(columnName, numValue);
					} else if (value instanceof Timestamp) {
						String dateValue = value.toString();
						dateValue = dateValue.substring(0, dateValue.length() - 2);
						row.put(columnName, dateValue);
					} else {
						row.put(columnName, value);
					}
				}
				rows.add(row);
			}
		} catch (SQLException e) {
			throw new SQLErrorException("SQL 예외, SQL : " + stmt, e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, rs 닫기, SQL : " + stmt, e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, stmt 닫기, SQL : " + stmt, e);
				}
			}
		}
		return rows;
	}

	public static int delete(Connection dbConn, SecSql sql) {
		int affectedRows = 0;
		
		PreparedStatement stmt = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
		}

		return affectedRows;
	}

	public static int update(Connection dbConn, SecSql sql) {
		int affectedRows = 0;

		PreparedStatement stmt = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			affectedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
		}

		return affectedRows;
	}

	public static int insert(Connection dbConn, SecSql sql) {
		int id = -1;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sql.getPreparedStatement(dbConn);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new SQLErrorException("SQL 예외, SQL : " + sql, e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, rs 닫기, SQL : " + sql, e);
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new SQLErrorException("SQL 예외, stmt 닫기, SQL : " + sql, e);
				}
			}
		}

		return id;
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.printf("[SQL 예외] : %s\n", e.getMessage());
			}
		}
	}
}
