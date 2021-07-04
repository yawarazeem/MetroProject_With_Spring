package com.metro.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import com.metro.bean.MetroCard;
import com.metro.bean.Station;
import com.metro.bean.Transaction;

public interface MetroDao {
	
	int addCard(int balance) throws SQLException, ClassNotFoundException, IOException;
	boolean removeCard(int id) throws ClassNotFoundException, SQLException, IOException;
	int getBalance(int id) throws ClassNotFoundException, SQLException, IOException;
	void deductBalance(int id, int amount) throws ClassNotFoundException, SQLException, IOException;
	boolean addBalance(int id, int balance) throws ClassNotFoundException, SQLException, IOException;
	boolean swipedIn(int idIn) throws ClassNotFoundException, SQLException, IOException;
	boolean swipedOut(int idOut) throws SQLException, ClassNotFoundException, IOException;
	boolean swipeIn(int idIn, int stationId) throws ClassNotFoundException, SQLException, IOException;
	boolean swipeOut(int idOut, int stationId) throws ClassNotFoundException, SQLException, IOException;
	MetroCard getCard(int id) throws ClassNotFoundException, SQLException, IOException;
	void resetStatus(int id) throws ClassNotFoundException, SQLException, IOException;
	
	Collection<MetroCard> getAllCards() throws ClassNotFoundException, SQLException, IOException;
	Collection<Transaction> getAlltrans(int id)  throws SQLException, ClassNotFoundException, Exception;
	

}
