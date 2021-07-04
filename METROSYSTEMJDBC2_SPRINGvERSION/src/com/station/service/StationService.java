package com.station.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import com.metro.bean.Station;

public interface StationService {
	boolean addStation(String stationName) throws ClassNotFoundException, SQLException, IOException;
	Collection<Station> getAllStations() throws ClassNotFoundException, SQLException, IOException;
}
