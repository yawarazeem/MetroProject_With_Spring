package com.metro.presentation;

import java.sql.SQLException;

public interface MetroPresentation {
	
	void showMenu();
	void performMenu(int choice) throws ClassNotFoundException, SQLException;

}
