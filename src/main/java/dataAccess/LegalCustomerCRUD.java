package dataAccess;

import dataAccess.connectionutil.DBConnection;
import dataAccess.entity.LegalCustomer;

import java.sql.*;

/**
 * Created by Dotin school 5 on 8/7/2016.
 */
public class LegalCustomerCRUD extends CustomerCRUD {


    public void createLegalCustomer(LegalCustomer legalCustomer) throws SQLException {
        String customerNumber = legalCustomer.getCustomerNumber();
        String companyName = legalCustomer.getCompanyName();
        String registrationDate = legalCustomer.getRegistrationDate();
        String economicID = legalCustomer.getEconomicID();
        if (validateCustomer(economicID)) {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO customer (customer_number) VALUE (?)");
            preparedStatement1.setString(1, customerNumber);
            preparedStatement1.executeUpdate();

            ResultSet resultSet = preparedStatement1.executeQuery("SELECT id FROM customer");
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO legal_customer (id, company_name,registration_date,economic_id) VALUES (?,?,?,?)");
            preparedStatement2.setInt(1, id);




//            khkhkhb
            preparedStatement2.setString(2, companyName);
            preparedStatement2.setString(3, registrationDate);
            preparedStatement2.setString(4, economicID);
            preparedStatement2.executeUpdate();
            System.out.println("hmmmmmmmmm");
        }


    }

    public boolean validateCustomer(String economicID) throws SQLException {
        Connection connection = DBConnection.getDBConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT economic_id FROM legal_customer");
        while (resultSet.next()) {
            if (resultSet.getString(1) == economicID) {
                return false;
            }
        }
        return true;
    }
}
