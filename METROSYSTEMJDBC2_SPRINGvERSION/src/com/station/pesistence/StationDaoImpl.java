package com.station.pesistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.metro.bean.Station;
import com.metrosystem.helper.MySQLConnection;
@Component("stationDao")
public class StationDaoImpl implements StationDao {
	@Override
	public boolean addStation(String stationName) throws ClassNotFoundException, SQLException, IOException {
		Connection connection=MySQLConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("insert into stations values(null, ?);");
		statement.setString(1, stationName);
		int rows = statement.executeUpdate();
//		Station name = new Station(id, stationName);
//		Stations.getStations().put(name.getId(), name);
		if(rows>0){
			connection.close();
			return true;
		}
		connection.close();
		return true;
	}
	
	@Override
	public Collection<Station> getAllStations() throws ClassNotFoundException, SQLException, IOException {
		ArrayList<Station> stations = null;
		Connection connection = MySQLConnection.getConnection();

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM STATIONS");
		ResultSet resultset = statement.executeQuery();

		Station station = null;
		stations = new ArrayList<Station>();

		while (resultset.next()) {
			station = new Station();
			station.setId(resultset.getInt("id"));
			station.setName(resultset.getString("name"));

			stations.add(station);
		}

		connection.close();

		return stations;
	}
}
