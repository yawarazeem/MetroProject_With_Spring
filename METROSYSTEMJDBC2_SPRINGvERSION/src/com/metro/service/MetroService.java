package com.metro.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import com.metro.bean.MetroCard;
import com.metro.bean.Transaction;



public interface MetroService {
	
	int addCard(int balance) throws ClassNotFoundException, SQLException, IOException;
	boolean removeCard(int id) throws ClassNotFoundException, SQLException, IOException;
	int balance(int id) throws ClassNotFoundException, SQLException, IOException;
	boolean addBalance(int id, int balance) throws ClassNotFoundException, SQLException, IOException;
	void deductBalance(int id, int amount) throws ClassNotFoundException, SQLException, IOException;
	int calcFare(int id, int souce, int dest) throws ClassNotFoundException, SQLException, IOException;
	boolean swipedIn(int idIn) throws ClassNotFoundException, SQLException, IOException;
	boolean swipedOut(int idOut) throws ClassNotFoundException, SQLException, IOException;
	boolean swipeIn(int idIn, int stationId) throws ClassNotFoundException, SQLException, IOException;
	boolean swipeOut(int idOut, int stationId) throws ClassNotFoundException, SQLException, IOException;
	
	Collection<MetroCard> getAllcards() throws ClassNotFoundException, SQLException, IOException;
	MetroCard getCard(int id) throws ClassNotFoundException, SQLException, IOException;
	

	Collection<Transaction> getAllTransactions(int id) throws SQLException, ClassNotFoundException, Exception;


}
