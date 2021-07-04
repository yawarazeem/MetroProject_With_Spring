package com.metro.database;

import java.util.HashMap;

import com.metro.bean.Station;
public class Stations {
	
private static HashMap<Integer, Station> stations = new HashMap<Integer, Station>();
	
	static {
		stations.put(101, new Station(101,"Kashmiri Gate"));
		stations.put(102, new Station(101,"Chandhi Chowk"));
		stations.put(103, new Station(101,"Jama Masjid"));
		stations.put(104, new Station(101,"ITO"));
		stations.put(105, new Station(101,"Mandi House"));

	}

	public static HashMap<Integer, Station> getStations() {
		return stations;
	}

	public static void setStations(HashMap<Integer, Station> stations) {
		Stations.stations = stations;
	}


}
