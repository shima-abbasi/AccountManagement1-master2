package dataAccess;

import dataAccess.connectionutil.DBConnection;
import dataAccess.entity.LegalCustomer;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Dotin school 5 on 8/7/2016.
 */
public class LegalCustomerCRUD extends CustomerCRUD {
    static Connection connection = DBConnection.getDBConnection();

    public boolean validateCustomer(String economicID) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM legal_customer WHERE economic_id=" + economicID);

        if (resultSet.next()) {
            return false;
        }
        return true;
    }

    public void createLegalCustomer(LegalCustomer legalCustomer) throws SQLException {
        String customerNumber = legalCustomer.getCustomerNumber();
        String companyName = legalCustomer.getCompanyName();
        String registrationDate = legalCustomer.getRegistrationDate();
        String economicID = legalCustomer.getEconomicID();

        PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO customer (customer_number) VALUE (?)");
        preparedStatement1.setString(1, customerNumber);
        preparedStatement1.executeUpdate();

        ResultSet resultSet = preparedStatement1.executeQuery("SELECT id FROM customer");
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO legal_customer (id , company_name ,registration_date ,economic_id) VALUES (?,?,?,?)");
        preparedStatement2.setInt(1, id);
        preparedStatement2.setString(2, companyName);
        preparedStatement2.setString(3, registrationDate);
        preparedStatement2.setString(4, economicID);
        preparedStatement2.executeUpdate();
        System.out.println("Legal customer inserted");


    }

    public ArrayList searchCustomer(String customerNumber, String companyName, String registrationDate, String economicID) throws SQLException {
        PreparedStatement preparedStatement = generatePrepareStatement(customerNumber, companyName, registrationDate, economicID);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<LegalCustomer> legalCustomerResult = new ArrayList<LegalCustomer>();
        while (resultSet.next()) {
            LegalCustomer legalCustomer = new LegalCustomer();
            legalCustomer.setId(resultSet.getInt("id"));
            legalCustomer.setCustomerNumber(resultSet.getString("customer_number"));
            legalCustomer.setCompanyName(resultSet.getString("company_name"));
            legalCustomer.setRegistrationDate(resultSet.getString("registration_date"));
            legalCustomer.setEconomicID(resultSet.getString("economic_id"));
            legalCustomerResult.add(legalCustomer);
        }
        return legalCustomerResult;
    }

    public PreparedStatement generatePrepareStatement(String customerNumber, String companyName, String registrationDate, String economicID) throws SQLException {

        StringBuilder stringBuilder = new StringBuilder();

        if (customerNumber != "" | companyName != "" | registrationDate != "" | economicID != "") {
            stringBuilder.append("SELECT * FROM (legal_customer JOIN customer ON customer.id = legal_customer.id) WHERE ");
            if (customerNumber != "") {
                stringBuilder.append(" customer_number=" + customerNumber + " AND");
            }
            if (companyName != "") {
                stringBuilder.append(" company_name=" + companyName + " AND");
            }
            if (registrationDate != "") {
                stringBuilder.append(" registrationDate=" + registrationDate + " AND");
            }
            if (economicID != "") {
                stringBuilder.append(" economicID=" + economicID);
            }
        } else {
            stringBuilder.append("SELECT * FROM legal_customer JOIN customer ON customer.id = legal_customer.id");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());

        return preparedStatement;
    }

    public static void deleteCustomer (int id) throws SQLException {

        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE  FROM legal_customer WHERE id =?");
        preparedStatement1.setInt(1, id);
        preparedStatement1.executeUpdate();

        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE  FROM customer WHERE id =?");
        preparedStatement2.setInt(1, id);
        preparedStatement2.executeUpdate();
    }
    public  static  LegalCustomer retrieveCustomer (int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM legal_customer LEFT OUTER JOIN customer ON (legal_customer.id = customer.id) WHERE  customer.id =?");
        preparedStatement.setInt(1, id);
        ResultSet results = preparedStatement.executeQuery();
        System.out.println("*");
        LegalCustomer legalCustomer = new LegalCustomer();
        if (results.next()) {
            legalCustomer.setId(results.getInt("id"));
            legalCustomer.setCustomerNumber(results.getString("customer_number"));
            legalCustomer.setCompanyName(results.getString("company_name"));
            legalCustomer.setEconomicID(results.getString("economic_id"));
            legalCustomer.setRegistrationDate(results.getString("registration_date"));
        }
        return legalCustomer;
    }
    public static void updateCustomer (int id , String companyName, String registrationDate, String economicID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE legal_customer SET company_name = ? , economic_id =  ? ,  registration_date = ?  WHERE id=?");
        preparedStatement.setString(1, companyName);
        preparedStatement.setString(2, economicID);
        preparedStatement.setString(3,registrationDate );
        preparedStatement.setInt(4, id);
        preparedStatement.executeUpdate();
    }
}
