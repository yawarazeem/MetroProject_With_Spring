package com.metro.bean;

import java.sql.Timestamp;
import java.time.LocalTime;
public class Transaction
{
  private int card_id,sourceStation ,destinationStation ,fare;
  Timestamp ts,to;
  public Transaction()
  {
	  
  }
  public Transaction(int cardID,int source,int destination,int fare,Timestamp ts,Timestamp to)
  {
	  super();
	  this.card_id=cardID;
	  this.sourceStation=source;
	  this.destinationStation=destination;
	  this.fare=fare;
	  this.ts=ts;
	  this.to=to;
  }
public int getCard_num() {
	return card_id;
}
public void setCard_num(int card_num) {
	this.card_id = card_num;
}
public int getSource() {
	return sourceStation;
}
public void setSource(int source) {
	this.sourceStation = source;
}
public int getDestination() {
	return destinationStation;
}
public void setDestination(int destination) {
	this.destinationStation = destination;
}
public int getFare() {
	return fare;
}
public void setFare(int fare) {
	this.fare = fare;
}
public Timestamp getTs() {
	return ts;
}
public void setTs(Timestamp ts) {
	this.ts = ts;
}
public Timestamp getTo() {
	return to;
}
public void setTo(Timestamp to) {
	this.to = to;
}
@Override
public String toString()
{
	return "" + card_id + "             " + sourceStation + "                  " + destinationStation + "                          "+ fare + "            " + ts + "                 "+to;
	
	
	
}
  
}
