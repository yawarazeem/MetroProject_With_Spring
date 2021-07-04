package com.metro.bean;

public class MetroCard {
	
	private int id, balance;
	private int swipeInId, swipeOutId;
	public MetroCard(){
	}
	public MetroCard(int id, int balance, int swipeInId, int swipeOutId){
		this.id = id;
		this.balance = balance;
		this.swipeInId = swipeInId;
		this.swipeOutId = swipeOutId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getSwipeInId() {
		return swipeInId;
	}
	public void setSwipeInId(int swipeInId) {
		this.swipeInId = swipeInId;
	}
	public int getSwipeOut() {
		return swipeOutId;
	}
	public void setSwipeOut(int swipeOutId) {
		this.swipeOutId = swipeOutId;
	}
	@Override
	public String toString() {
		return "MetroCard [id=" + id + ", balance=" + balance + ", swipeInId=" + swipeInId + ", swipeOutId=" + swipeOutId + "]";
	}
	

}