package com.metro.presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metro.bean.MetroCard;
import com.metro.bean.Station;
import com.metro.bean.Transaction;
import com.metro.service.MetroServiceImpl;
import com.station.service.StationServiceImpl;

@Component("metroPresentation")
public class MetroPresentationImpl implements MetroPresentation {
	
//	private MetroServiceImpl metroService=new MetroServiceImpl();
	@Autowired
	private MetroServiceImpl metroService;
	
//	private StationServiceImpl stationService = new StationServiceImpl();
	@Autowired
	private StationServiceImpl stationService;
	Scanner scanner = new Scanner(System.in);

	@Override
	public void showMenu() {
		System.out.println("*****WELCOME TO METRO SYSTEM*****");
		System.out.println("1. Have a Card?");
		System.out.println("2. Buy a new Card?");
		System.out.println("3. Surrender your card?");
		System.out.println("4. Admin Panel");
		System.out.println("5. Exit");
		
	}
	
	public void createNewCard(){
		int id = 0;
		System.out.println("Enter the balance to be added");
		int balance = scanner.nextInt();
		if(balance>100){
			try {
				try {
					id = metroService.addCard(balance);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Your new card is generated with ID: "+id);
		}
		else
			System.out.println("Balance should atleast be Rs.100");
	}
	

	@Override
	public void performMenu(int choice) throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		switch(choice){
		case 1://Have a card
			System.out.println("Please Enter your Card ID");
			int id = scanner.nextInt();
			MetroCard card = null;
			try {
				card = metroService.getCard(id);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(card!=null){
				System.out.println("Card Holder Verified!");
				System.out.println("1. Check Balance");
				System.out.println("2. Add Balance");
				try {
					if(metroService.swipedIn(id) == false)
						System.out.println("3. Swipe In");
					else if(metroService.swipedOut(id) == false)
						System.out.println("4. Swipe Out");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("5. Go back");
				System.out.println("6. Check Your Card Transactions");
				int userChoice = scanner.nextInt();
				switch(userChoice){
				case 1:
					try {
						System.out.println("Balance: "+metroService.balance(id));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					System.out.println("Enter the Amount");
					try {
						if(metroService.addBalance(id, scanner.nextInt()))
							System.out.println("Balance Added Succesfully");
						else
							System.out.println("Balance Addition Failed!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					Collection<Station> stations = null;
					try {
						stations = stationService.getAllStations();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(Station station:stations)
						System.out.println(station);
					System.out.println("Select Source Station ID from above:");
					int stationId = scanner.nextInt();
					try {
						metroService.swipeIn(id,stationId);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					Collection<Station> stations1 = null;
					try {
						stations1 = stationService.getAllStations();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(Station station:stations1)
						System.out.println(station);
					System.out.println("Select Source Station ID from above:");
					int stationId1 = scanner.nextInt();
					try {
						metroService.swipeOut(id,stationId1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					break;
				case 6:
					//transaction part
					System.out.println("_____________________________________________________________________________________________________________________________________");
					System.out.println("CARD_ID       SOURCE_STATION     DESTINATION_STATION        CHARGES          SWIPE_IN_TIME                             SWIPE_OUT_TIME");
					System.out.println("____________________________________________________________________________________________________________________________________");
					Collection<Transaction> card_Transactions = null;
					try {
						card_Transactions = metroService.getAllTransactions(id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					for(Transaction card_Transaction: card_Transactions )
						System.out.println(card_Transaction);
					
					System.out.println("_____________________________________________________________________________________________________________________________________");
				}
				
			}else{
				System.out.println("Card Not Found!");
			}
			break;
		case 2://Buy a new card
			createNewCard();
			break;
		case 3://Surrender your card
			System.out.println("Please enter your card ID to surrender:");
			int idSurrender = scanner.nextInt();
			int balSurrender = 0;
			MetroCard cardd = null;
			try {
				cardd = metroService.getCard(idSurrender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				balSurrender = metroService.balance(idSurrender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(metroService.removeCard(idSurrender))
					if(cardd==null)
						System.out.println("CARD DOES'T EXIST!");
					else if(balSurrender>0)
						System.out.println("Your card is surrendered successfully! Please collect Rs."+balSurrender);
					else 
						System.out.println("Your card is surrendered successfully!");
				else
					System.out.println("Card Removal Failed!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("Welcome Admin!\nPlease Identify yoursekf by entering the password!");
			String pass = scanner.next();
			if(pass.equals("maja")){
				System.out.println("Admin Verified!");
				System.out.println("1. Display All Cards");
				System.out.println("2. Display All Stations");
				System.out.println("3. Add a new Station");
				System.out.println("4. Exit");
				int adminChoice = scanner.nextInt();
				switch(adminChoice){
				case 1:
					System.out.println("========All cards=======");
					Collection<MetroCard> cards = null;
					try {
						cards = metroService.getAllcards();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(MetroCard card1:cards) {
						System.out.println(card1);
					}
					break;
				case 2:
					System.out.println("========All Stations=======");
					Collection<Station> stations = null;
					try {
						stations = stationService.getAllStations();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(Station station:stations) {
						System.out.println(station);
					}
					break;
				case 3:
					System.out.println("Enter the station name");
					String stationName = scanner.nextLine();
					try {
						if(stationService.addStation(stationName)) {
							System.out.println("Congratulations Station: "+stationName+" has been added!");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					System.out.println("Thank You Admin! Hope to see you soon!");
					System.exit(0);
				}
			}else{
				System.out.println("Invalid credentials");
			}
			break;
		case 5:
			System.out.println("Thank You For Using Metro Service! Have a nice day!");
			System.exit(0);
		}

	}
}