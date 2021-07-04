package com.metro.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.metro.bean.MetroCard;
import com.metro.bean.Station;
import com.metro.bean.Transaction;
import com.metro.service.MetroService;
import com.metrosystem.helper.MySQLConnection;
@Component("metroDao")
public class MetroDaoImpl implements MetroDao {
	int x,t; static int ans; static Timestamp ts;
	static Timestamp tso;
	 Timestamp checkInTime;
	 Timestamp checkOutTime;
	 static long duration = 0;
    int swipedInStation = 0;
	@Override
	public boolean swipedIn(int idIn) throws ClassNotFoundException, SQLException, IOException {
	    
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("select swipein from metrocards where id=?");
		statement.setInt(1,idIn);
		
	
		ResultSet res = statement.executeQuery();
		int swipeIn;
//		int swipedIn = MetroCards.getMetroCards().get(idIn).getSwipeInId();
		if(res.next()){
			swipeIn = res.getInt("swipein");
//			System.out.println(swipeIn);
			if(swipeIn==0){
				connection.close();
				return false;
			}
		}
		connection.close();
		return true;
	}

	@Override
	public boolean swipedOut(int idOut) throws SQLException, ClassNotFoundException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("select swipeout from metrocards where id=?");
		statement.setInt(1,idOut);
		ResultSet res = statement.executeQuery();
		int swipeOut;
//		int swipedOut = MetroCards.getMetroCards().get(idOut).getSwipeOut();
		if(res.next()){
			swipeOut = res.getInt("swipeout");
			if(swipeOut==0){
				connection.close();
				return false;
			}
		}
		connection.close();
		return true;
	}

	@Override
	public boolean swipeIn(int idIn, int stationId) throws ClassNotFoundException, SQLException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("update metrocards set swipein=?, swipeout=0 where id=?");
		statement.setInt(1,stationId);
		statement.setInt(2, idIn);
		swipedInStation = stationId;
		
		 Date date= new Date();
	     long swipe_in_time = date.getTime();
	     ts = new Timestamp(swipe_in_time);
		
		checkInTime = ts;
		
		int rows = statement.executeUpdate();
//		MetroCards.getMetroCards().get(idIn).setSwipeInId(stationId);
		if(rows>0){
			connection.close();
			return true;
		}
		connection.close();
		return false;
	}

	@Override
	public boolean swipeOut(int idOut, int stationId) throws ClassNotFoundException, SQLException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("update metrocards set swipeout=? where id=?");
		statement.setInt(1,stationId);
		statement.setInt(2, idOut);
		int rows = statement.executeUpdate();
//		MetroCards.getMetroCards().get(idOut).setSwipeOut(stationId);
		int travelDuration;
		Date date = new Date();
		long swipe_out_time = date.getTime();
		 tso = new Timestamp(swipe_out_time);
		checkOutTime = tso;
		
		 duration
        = Math.abs(ts.getTime() - tso.getTime());
		duration
         = (duration / (60 * 1000)) % 60;
		int deduct = 10;
		if(duration>1) {
			deductBalance(idOut, deduct);
			System.out.println("An additional fee of Rps: 10 has been deducted from your account as you have exceeded the MAX time limit!");
		}
		int checkIN = swipedInStation;
		statement.setInt(1,idOut);
		int x=statement.executeUpdate();
		statement=connection.prepareStatement("INSERT INTO TRANSACTION VALUES (?,?,?,?,?,CURRENT_TIME)");
		ans=Math.abs(checkIN-stationId);
		statement.setInt(1,idOut);
		statement.setInt(2,checkIN);
		statement.setInt(3,stationId);
		statement.setInt(4,ans*5);
		statement.setTimestamp(5, ts);
		
		int y=statement.executeUpdate();
		connection.close();
		
		
		if(rows>0){
			resetStatus(idOut);
			connection.close();
			return true;
		}
		connection.close();
		return false;
	}

	@Override
	public boolean removeCard(int id) throws ClassNotFoundException, SQLException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("delete from metrocards where id=?");
		statement.setInt(1,id);
		int rows = statement.executeUpdate();
//		MetroCard card = MetroCards.getMetroCards().remove(id);
		if(rows >= 0){
			connection.close();
			return true;
		}
		connection.close();
		return false;
	}

	@Override
	public int getBalance(int id) throws ClassNotFoundException, SQLException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("select balance from metrocards where id=?");
		statement.setInt(1,id);
		ResultSet res = statement.executeQuery();
		int balance = 0;
//		int swipedOut = MetroCards.getMetroCards().get(idOut).getSwipeOut();
		if(res.next()){
			balance = res.getInt("balance");
		}
		connection.close();
		return balance;
//		return MetroCards.getMetroCards().get(id).getBalance();
	}

	@Override
	public boolean addBalance(int id, int balance) throws ClassNotFoundException, SQLException, IOException {
		int prevBalance = getBalance(id);
		int newBalance = prevBalance+balance;
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("update metrocards set balance=? where id=?");
		statement.setInt(1,newBalance);
		statement.setInt(2, id);
		int rows = statement.executeUpdate();
//		MetroCards.getMetroCards().get(id).setBalance(newBalance);
		if(rows>0){
			connection.close();
			return true;
		}
		connection.close();
		return false;
	}

	@Override
	public void deductBalance(int id, int amount) throws ClassNotFoundException, SQLException, IOException {
		int prevBalance = getBalance(id);
		int newBalance = prevBalance-amount;
	
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("update metrocards set balance=? where id=?");
		statement.setInt(1,newBalance);
		statement.setInt(2, id);
		statement.executeUpdate();
		connection.close();
//		MetroCards.getMetroCards().get(id).setBalance(newbalance);
	}

	@Override
	public MetroCard getCard(int id) throws ClassNotFoundException, SQLException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from metrocards where id=?");
		statement.setInt(1,id);
		ResultSet res = statement.executeQuery();
		MetroCard card=null;
		if(res.next()){
			card = new MetroCard();
			card.setId(res.getInt("id"));
			card.setBalance(res.getInt("balance"));
			card.setSwipeInId(res.getInt("swipein"));
			card.setSwipeOut(res.getInt("swipeout"));
		}
//		return MetroCards.getMetroCards().get(id);
		connection.close();
		return card;
	}

	@Override
	public int addCard(int balance) throws SQLException, ClassNotFoundException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("insert into metrocards values(null, ?,0,0);", PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setInt(1, balance);
		int rows = statement.executeUpdate();
		ResultSet res = statement.getGeneratedKeys();
		res.next();
		int id = res.getInt(1);
//		MetroCard card = new MetroCard(id, balance, 0, 0);
//		MetroCards.getMetroCards().put(card.getId(), card);
		if(rows>0){
			connection.close();
			return id;
		}
		connection.close();
		return 0;
	}

	@Override
	public Collection<MetroCard> getAllCards() throws ClassNotFoundException, SQLException, IOException {
//		HashMap<Integer,MetroCard> cards=MetroCards.getMetroCards();
//		 Collection<MetroCard> recs=cards.values();
//		return recs;
		ArrayList<MetroCard> metrocards = null;
		Connection connection = MySQLConnection.getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from metrocards");
		ResultSet resultset = statement.executeQuery();

		MetroCard metrocard = null;
		metrocards = new ArrayList<MetroCard>();

		while (resultset.next()) {
			metrocard = new MetroCard();
			metrocard.setId(resultset.getInt("id"));
			metrocard.setBalance(resultset.getInt("balance"));
			metrocard.setSwipeInId(resultset.getInt("swipein"));
			metrocard.setSwipeOut(resultset.getInt("swipeout"));

			metrocards.add(metrocard);
		}

		connection.close();

		return metrocards;
	}

	@Override
	public void resetStatus(int id) throws ClassNotFoundException, SQLException, IOException {
//		MetroCards.getMetroCards().get(id).setSwipeInId(0);
//		MetroCards.getMetroCards().get(id).setSwipeOut(0);
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("update metrocards set swipein=0, swipeout=1 where id=?");
		statement.setInt(1,id);
		statement.executeUpdate();
		connection.close();
	}


	@Override
	public Collection<Transaction> getAlltrans(int card_id) throws SQLException, ClassNotFoundException, Exception {
		ArrayList<Transaction> transactions = null;
		Connection connection = MySQLConnection.getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from transaction where cardID=?");
		statement.setInt(1,card_id);
		ResultSet resultset = statement.executeQuery();

		Transaction card_Transaction = null;
		transactions = new ArrayList<Transaction>();

		while (resultset.next()!=false) 
		{
			card_Transaction = new Transaction();
			card_Transaction.setCard_num(resultset.getInt("cardID"));
			card_Transaction.setSource(resultset.getInt("sourceStation"));
			card_Transaction.setDestination(resultset.getInt("destinationStation"));
			card_Transaction.setFare(resultset.getInt("charges"));
			card_Transaction.setTs(resultset.getTimestamp("swipe_in_Station"));
			card_Transaction.setTo(resultset.getTimestamp("swipe_out_Station"));
			
			transactions.add(card_Transaction);
		}
		connection.close();
		return transactions;
	}
	
	
	
	

}
