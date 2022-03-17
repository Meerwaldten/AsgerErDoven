/*
Date: 16/03/2022

Code from Dat21A today

*/
//package com.company;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class Main
{
    static Statement stmt;
    static ResultSet rs;
    static String sqlString;
    static Connection con;

    public static void main(String args[])
    {
        connectDB();
        makeTablePost();
        makeTablePersons();
        selectData();
        /*
        System.out.println("HEJSA");
        selectData2();
         */
    }

    public static void connectDB()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/test_db";
            con = DriverManager.getConnection(url,"root","Coloratj0");
            System.out.println("Ok, we have a connection.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void makeTablePersons()
    {
        try
        {
            stmt = con.createStatement();
            stmt.executeUpdate("DROP TABLE persons");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("No existing table to delete");
        }

        try
        {
            //Create a table in the database
            sqlString = "CREATE TABLE `persons` ( " +
                    "`fname` VARCHAR(45) NULL," +
                    "`sname` VARCHAR(45) NULL," +
                    "`age` INT NULL," +
                    "`postnr` INT NULL)";
            stmt.executeUpdate(sqlString);

            Scanner scanner = new Scanner(new File("data/persons.csv"));
            scanner.useDelimiter(",|\n");
            String fn, sn;
            int a, pn;
            while(scanner.hasNextLine())
            {
                fn = scanner.next();
                sn = scanner.next();
                a = scanner.nextInt();
                pn = scanner.nextInt();
                sqlString = "INSERT INTO persons (`fname`,`sname`,`age`,`postnr`) " +
                        "VALUES('" + fn + "','" + sn + "'," + a + "," + pn + ")";
                stmt.executeUpdate(sqlString);
                //System.out.println(sqlString);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void makeTablePost()
    {
        try
        {
            stmt = con.createStatement();
            stmt.executeUpdate("DROP TABLE post_nr");
        }
        catch (Exception e)
        {
            System.out.print(e);
            System.out.println("No existing table to delete");
        }

        try
        {
            //Create a table in the database
            sqlString = "CREATE TABLE `post_nr` " +
                    "(`postnr` INT NOT NULL," +
                    "`by` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`postnr`))";
            stmt.executeUpdate(sqlString);

            //Insert some values into the table
            sqlString = "INSERT INTO post_nr (`postnr`,`by`) " +
                    "VALUES(2100,'København Ø')";
            stmt.executeUpdate(sqlString);
            sqlString = "INSERT INTO post_nr (`postnr`,`by`) " +
                    "VALUES(2400,'København NV')";
            stmt.executeUpdate(sqlString);
            sqlString = "INSERT INTO post_nr (`postnr`,`by`) " +
                    "VALUES(1234,'København K')";
            stmt.executeUpdate(sqlString);
            sqlString = "INSERT INTO post_nr (`postnr`,`by`) " +
                    "VALUES(2900,'Hellerup')";
            stmt.executeUpdate(sqlString);
            sqlString = "INSERT INTO post_nr (`postnr`,`by`) " +
                    "VALUES(2630,'Taastrup')";
            stmt.executeUpdate(sqlString);
            sqlString = "INSERT INTO post_nr (`postnr`,`by`) " +
                    "VALUES(2860,'Søborg')";
            stmt.executeUpdate(sqlString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("We did it boyz!");
    }

    public static void selectData()
    {
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sqlString = "SELECT `fname`,`sname`,`by`"+
                        " FROM persons, post_nr"+
                        " WHERE persons.postnr = post_nr.postnr;";
            rs = stmt.executeQuery(sqlString);

            while(rs.next())
            {
                String fn= rs.getString("fname");
                String sn = rs.getString("sname");
                String by = rs.getString("by");
                System.out.println("\tname = " + fn + " " + sn + ", by = " + by);
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void selectData2()
    {
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sqlString = "SELECT * from post_nr";
            rs = stmt.executeQuery(sqlString);

            while(rs.next())
            {
                String postnr= rs.getString("postnr");
                String by = rs.getString("by");
                System.out.println("\tpostnummer = " + postnr + "\t bynavn =  " + by);
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
