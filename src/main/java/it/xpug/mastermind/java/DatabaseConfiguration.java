package it.xpug.mastermind.java;

import java.sql.*;

public interface DatabaseConfiguration {
	Connection getConnection();
}
