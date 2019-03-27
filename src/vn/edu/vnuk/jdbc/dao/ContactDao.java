package vn.edu.vnuk.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.vnuk.jdbc.ConnectionFactory;
import vn.edu.vnuk.jdbc.model.Contact;

public class ContactDao {
	private Connection connection;
	
	public ContactDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	//1 .show C
	public void create(Contact contact) throws SQLException {
		
		String sqlQuery = "insert into contacts (name, email, address, date_of_register) "
                +	"values (?, ?, ?, ?)";
		
		
		PreparedStatement statement;
		
		try {
			statement = connection.prepareStatement(sqlQuery);

            //	Replacing "?" through values
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getAddress());
            statement.setDate(4, new java.sql.Date(
                            contact.getDateOfRegister().getTimeInMillis()
                    )
            );

            // 	Executing statement
			statement.execute();
			statement.close();
	        System.out.println(" DATA successfully loaded in \'categories\'");
		
		}
		
		catch (Exception e) {
	        e.printStackTrace();
		}
		
		finally {
			System.out.println("<  Sql5010InsertIntoCategories ended");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("");
			connection.close();
		}
	}
	
	
	
	//2.show R
	@SuppressWarnings("finally")
	public List<Contact> read() throws SQLException{
			
		PreparedStatement statement;
			
			String sqlQuery = "select * from contacts";
			List<Contact> contacts = new ArrayList<Contact>();
			
			try {
							
				statement = connection.prepareStatement(sqlQuery);

	            // 	Executing statement
				ResultSet results = statement.executeQuery();
				
				while(results.next()) {
					contacts.add(buildContact(results));
				}
				results.close();
				statement.close();
				
			}
			
			catch (Exception e) {
		        e.printStackTrace();
			}
			
			finally {
				connection.close();
				return contacts;
			}
			
	}
	
	private Contact buildContact(ResultSet results) throws SQLException {
		Contact contact = new Contact();
		contact.setId(results.getLong("id"));
		contact.setName(results.getString("name"));
		contact.setAddress(results.getString("address"));
		contact.setEmail(results.getString("email"));
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(results.getDate("date_of_register"));
		contact.setDateOfRegister(calendar);
		
		return contact;
	}
}