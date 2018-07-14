package main.java.com.ra.courses.airport.dao.impl;

import main.java.com.ra.courses.airport.entity.Plane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException {

        Plane plane1=new Plane(1,"Lufthansa","Boeing","Big",12345);
        Plane plane2=new Plane(2,"Lufthansa","Boeing","Big",12346);
        Plane plane3=new Plane(3,"Lufthansa","Boeing","Big",12347);
        Plane plane4=new Plane(4,"Lufthansa","Boeing","Big",12348);
        Plane plane5=new Plane(5,"MAU","Hawker","Small",34567);
        Plane plane6=new Plane(6,"MAU","Hawker","Small",34568);
        Plane plane7=new Plane(7,"MAU","Hawker","Small",34569);

        String createTable="CREATE TABLE IF NOT EXISTS plane ("
                +  "id INTEGER NOT NULL"
                +  "owner VARCHAR(255),"
                +  "model VARCHAR(255),"
                +  "platenumber INTEGER)";

        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        final PreparedStatement preparedStatement;



        try{
            Connection connection=connectionFactory.getConnection();
            preparedStatement=connection.prepareStatement(createTable);
            preparedStatement.executeUpdate();

        }catch (SQLException e){e.printStackTrace();

        }



    }







}
