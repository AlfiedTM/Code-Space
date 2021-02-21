// Blood Bank console app that record all blood transactions
// From donor registrations to receipient's, with the ability to link a receipient with a list of donors with the same blood type
// Done by Alfred T Matakala
package BLOODBANK;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BloodBank {
    private ResultSet dbResponse = null;
    private PreparedStatement stmtPrepared = null;
    private String sql;

    // Database mutator method to insert a donor record
    public void registerDonor(String firstName, String middleName, String lastName, String houseNo, String streetName,
            String province, String nationality, String bloodGroup, String rhFactor, String phoneNumber, String NRCNo,
            int quantity, Connection conn) {
        int donationStatus = 0, status = 0;
        try {
            sql = "Insert Into Donor(FirstName, MiddleName, LastName, HouseNo, StreetName, Province, Nationality, BloodGroup, RhFactor, PhoneNumber, NRCNo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtPrepared = conn.prepareStatement(sql); // Preparing and Sql prepared statement

            // Formatting data to the approriate format before inserting it to the database
            String fName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            String mName;
            if (middleName.equals(null) || middleName.equals("")) {
                mName = middleName;
            } else
                mName = middleName.substring(0, 1).toUpperCase() + middleName.substring(1);
            String lName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            String sName = streetName.substring(0, 1).toUpperCase() + streetName.substring(1);
            String provinceOfStay = province.substring(0, 1).toUpperCase() + province.substring(1);
            String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1);
            String countryOfOrigin = nationality.substring(0, 1).toUpperCase() + nationality.substring(1);

            // Data binding of database values
            stmtPrepared.setString(1, fName);
            stmtPrepared.setString(2, mName);
            stmtPrepared.setString(3, lName);
            stmtPrepared.setString(4, houseNo);
            stmtPrepared.setString(5, sName);
            stmtPrepared.setString(6, provinceOfStay);
            stmtPrepared.setString(7, countryOfOrigin);
            stmtPrepared.setString(8, bloodGroup.toUpperCase());
            stmtPrepared.setString(9, RhFactor);
            stmtPrepared.setString(10, phoneNumber);
            stmtPrepared.setString(11, NRCNo);

            // Executing SQL statement
            status = stmtPrepared.executeUpdate();

            // Donating Blood
            donationStatus = donateBlood(bloodGroup, RhFactor, NRCNo, quantity, conn);
            // Closing the access of the prepared statement object
            stmtPrepared.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Checking the status of the record insertion
            if (status == 1 && donationStatus == 1) {
                System.out.println("Record successfully added");
            } else {
                System.out.println("Error could not add donor");
                return;
            }

        }

    }

    // Databse Mutator method for inserting a receipient record
    public void registerReciepient(String firstName, String middleName, String lastName, String houseNo,
            String streetName, String province, String nationality, String bloodGroup, String rhFactor,
            String phoneNumber, String NRCNo, int quantity, Connection conn) {
        int donationStatus = 0, status = 0;
        try {
            sql = "Insert Into Receipient(FirstName, MiddleName, LastName, HouseNo, StreetName, Province, Nationality, BloodGroup, RhFactor, PhoneNumber, NRCNo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtPrepared = conn.prepareStatement(sql);

            // Formatting data to the approriate format before inserting it to the database
            String fName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            String mName;
            if (middleName.equals(null) || middleName.equals("")) {
                mName = middleName;
            } else
                mName = middleName.substring(0, 1).toUpperCase() + middleName.substring(1);
            String lName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            String sName = streetName.substring(0, 1).toUpperCase() + streetName.substring(1);
            String provinceOfStay = province.substring(0, 1).toUpperCase() + province.substring(1);
            String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1);
            String countryOfOrigin = nationality.substring(0, 1).toUpperCase() + nationality.substring(1);

            // Binding data to the sql statement
            stmtPrepared.setString(1, fName);
            stmtPrepared.setString(2, mName);
            stmtPrepared.setString(3, lName);
            stmtPrepared.setString(4, houseNo);
            stmtPrepared.setString(5, sName);
            stmtPrepared.setString(6, provinceOfStay);
            stmtPrepared.setString(7, countryOfOrigin);
            stmtPrepared.setString(8, bloodGroup.toUpperCase());
            stmtPrepared.setString(9, RhFactor);
            stmtPrepared.setString(10, phoneNumber);
            stmtPrepared.setString(11, NRCNo);

            // Execute Sql statement
            status = stmtPrepared.executeUpdate();
            donationStatus = withdrawBlood(bloodGroup, RhFactor, NRCNo, quantity, conn);

            // Closing access to the database
            stmtPrepared.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Checking the status of the record insertion
            if (status == 1 && donationStatus == 1) {
                System.out.println("Record successfully added");

            } else {
                System.out.println("Error could not add receipient");
                return;
            }

        }

    }

    // Database mutator method that adds a donation turple to the BloodBank table as
    // the Donor donates blood
    public int donateBlood(String bloodGroup, String rhFactor, String NRCNo, int quantity, Connection conn) {
        int donationStatus = 0;
        String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1); // Fomatting the data to
                                                                                          // appropriate format

        try {
            sql = "insert into BloodBank (BloodType, RhFactor, NRCNo, Quantity) values(?, ?, ?, ?)";

            // Preparing sql statement
            stmtPrepared = conn.prepareStatement(sql);

            // Data binding of the sql statement values
            stmtPrepared.setString(1, bloodGroup.toUpperCase());
            stmtPrepared.setString(2, RhFactor);
            stmtPrepared.setString(3, NRCNo);
            stmtPrepared.setInt(4, quantity);

            // Execution of the sql statement
            donationStatus = stmtPrepared.executeUpdate();
            // Close access to the database
            stmtPrepared.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
        return donationStatus;
    }

    // Database mutator method that inserts a turple in the BloodBank table after a
    // receipient get's blood
    public int withdrawBlood(String bloodGroup, String rhFactor, String NRCNo, int quantity, Connection conn) {
        int donationStatus = 0;
        String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1); // Formatting the data to an
                                                                                          // appropriate format

        try {
            sql = "insert into BloodBank (BloodType, RhFactor, NRCNo, Quantity) values(?, ?, ?, ?)";

            // Prepare an sql statement
            stmtPrepared = conn.prepareStatement(sql);

            // Data binding of the sql values
            stmtPrepared.setString(1, bloodGroup.toUpperCase());
            stmtPrepared.setString(2, RhFactor);
            stmtPrepared.setString(3, NRCNo);
            stmtPrepared.setInt(4, -quantity);
            // Execute sql statement
            donationStatus = stmtPrepared.executeUpdate();

            // Close access to the database
            stmtPrepared.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
        return donationStatus;
    }

    // Database accessor method for a record
    public void fetchDonorRecord(String NRCNo, String bloodGroup, Connection conn) {
        sql = "Select * from Donor where NRCNo = ? AND BloodGroup = ?";
        try {
            stmtPrepared = conn.prepareStatement(sql);// Preparing a statement
            // Data binding to the sql values

            stmtPrepared.setString(1, NRCNo);
            stmtPrepared.setString(2, bloodGroup.toUpperCase());
            // Execute Sql querry
            dbResponse = stmtPrepared.executeQuery();
            // Check if the resultset object returned a record or not
            if (dbResponse.isAfterLast()) { // If no record was returned
                System.out.printf("%nNo record with ID: %s and blood group: %s exists%n%n", NRCNo,
                        bloodGroup.toUpperCase());
            } else { // Else if a record was returned
                System.out.printf("%-2s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", "No ",
                        "First Name", "Middle Name", "Last Name", "Blood Group", "Rhesus Factor", "NRC Number",
                        "Nationality", "Phone Number", "House Number", "Street", "Province");
                while (dbResponse.next()) {// Print out data of the returned object
                    int i = 1;
                    String firstName = dbResponse.getString(1);
                    String middleName = dbResponse.getString(2);
                    String lastName = dbResponse.getString(3);
                    String houseNo = dbResponse.getString(4);
                    String streetName = dbResponse.getString(5);
                    String province = dbResponse.getString(6);
                    String nationality = dbResponse.getString(7);
                    bloodGroup = dbResponse.getString(8);
                    String rhFactor = dbResponse.getString(9);
                    String phoneNumber = dbResponse.getString(10);
                    NRCNo = dbResponse.getString(11);

                    System.out.printf("%-3d %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", i,
                            firstName, (middleName == null) ? "" : middleName, lastName, bloodGroup, rhFactor, NRCNo,
                            nationality, phoneNumber, houseNo, streetName, province);
                    i++;

                }
            }
            // Close access to the database
            dbResponse.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
    }

    // Database accessor method for matching a receipient with a donor
    public void matchReceipient(String rhFactor, String bloodGroup, Connection conn) {
        String sql = "select FirstName, MiddleName, LastName, RhFactor, BloodGroup from donor where BloodGroup = ? and RhFactor = ?";
        String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1);// Format the data to the
                                                                                         // appropriate format before
                                                                                         // inserting it to the database

        try {
            PreparedStatement stmtPrepared = conn.prepareStatement(sql); // Prepare the sql statement
            // Data binding to the sql values
            stmtPrepared.setString(1, bloodGroup.toUpperCase());
            stmtPrepared.setString(2, RhFactor);

            // Execute sql querry
            ResultSet dbResponse = stmtPrepared.executeQuery();

            if (!dbResponse.isAfterLast()) { // Check if a record was returned
                System.out.printf("%-2s %-15s %-15s %-15s %-15s %-15s %n%n", "No ".toUpperCase(),
                        "First Name".toUpperCase(), "Middle Name".toUpperCase(), "Last Name".toUpperCase(),
                        "Blood Group".toUpperCase(), "Rhesus Factor".toUpperCase());
                int i = 1;
                while (dbResponse.next()) {
                    System.out.printf("%-3d %-15s %-15s %-15s %-15s %-15s %n", i, dbResponse.getString(1),
                            dbResponse.getString(2), dbResponse.getString(3), dbResponse.getString(5),
                            dbResponse.getString(4));
                    i++;
                }
            }
            // Close the database access
            dbResponse.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
    }

    // Database accessor method that fetches a record of a receipient
    public void fetchReciepientRecord(String NRCNo, String bloodGroup, Connection conn) {
        sql = "Select * from Receipient where NRCNo = ? AND BloodGroup = ?";
        try {
            stmtPrepared = conn.prepareStatement(sql); // Prepare an sql statement
            // Data binding to the sql values
            stmtPrepared.setString(1, NRCNo);
            stmtPrepared.setString(2, bloodGroup.toUpperCase());

            // Execute sql querry
            dbResponse = stmtPrepared.executeQuery();

            if (dbResponse.isAfterLast()) {// Check if a record was returned
                System.out.printf("%nNo record with ID: %s and blood group: %s exists%n%n", NRCNo.toUpperCase(),
                        bloodGroup.toUpperCase());
            } else {
                System.out.printf("%-2s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n", "No ",
                        "First Name", "Middle Name", "Last Name", "Blood Group", "Rhesus Factor", "NRC Number",
                        "Nationality", "Phone Number", "House Number", "Street", "Province");
                while (dbResponse.next()) {// Print out data of the returned object
                    int i = 1;
                    String firstName = dbResponse.getString(1);
                    String middleName = dbResponse.getString(2);
                    String lastName = dbResponse.getString(3);
                    String houseNo = dbResponse.getString(4);
                    String streetName = dbResponse.getString(5);
                    String province = dbResponse.getString(6);
                    String nationality = dbResponse.getString(7);
                    bloodGroup = dbResponse.getString(8);
                    String rhFactor = dbResponse.getString(9);
                    String phoneNumber = dbResponse.getString(10);
                    NRCNo = dbResponse.getString(11);

                    System.out.printf("%-3d %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s%n%n", i,
                            firstName, (middleName == null) ? "" : middleName, lastName, bloodGroup, rhFactor, NRCNo,
                            nationality, phoneNumber, houseNo, streetName, province);
                    i++;

                }
            }
            // Close the access to the database
            dbResponse.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {

        }
    }

    // Database accessor method that checks the availability of the bloodtype
    public boolean availability(Connection conn, String bloodGroup, String rhFactor, int quantity) {
        boolean available = false;

        // Formatting the data to an appropriate format before data binding
        String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1);
        String bloodType = bloodGroup.substring(0, 1).toUpperCase() + bloodGroup.substring(1);
        sql = "select sum(Quantity) from BloodBAnk where BloodType=? and RhFactor=?";

        try {
            PreparedStatement stmtPrepared = conn.prepareStatement(sql); // Prepare the statement
            // Data binding to the sql statement
            stmtPrepared.setString(1, bloodType);
            stmtPrepared.setString(2, RhFactor);

            // Execute querry
            ResultSet data = stmtPrepared.executeQuery();
            // Get the amount of blood available
            int totalBlood = data.getInt(1);

            // Check if there is enough blood to donate
            if ((totalBlood - quantity) >= 0)
                available = true;

            dbResponse.close();// Close access to the database
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {

        }
        return available;
    }

    // Accessor method that prints the availability of a blood group, its rh status
    // and quantity of blood available
    public void bloodGroupBalance(Connection conn, String bloodGroup, String rhFactor) {
        String RhFactor = rhFactor.substring(0, 1).toUpperCase() + rhFactor.substring(1); // Format data to appropriate
                                                                                          // format

        sql = "select sum(Quantity) from BloodBAnk where BloodType=? and RhFactor=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql); // Prepare statement

            // Data binding to the sql values
            stmt.setString(1, bloodGroup.toUpperCase());
            stmt.setString(2, RhFactor);

            // Execute sql Querry
            ResultSet dbResponse = stmt.executeQuery();

            while (dbResponse.next()) { // Print out the values
                System.out.printf("%-10s %-13s %s%n", "Blood Type".toUpperCase(), "Rhesus Status".toUpperCase(),
                        "Available".toUpperCase() + "(ml)");
                System.out.printf("%-10s %-13s %-5d%n%n", bloodGroup, RhFactor, dbResponse.getInt(1));
            }

            // Close the access to the database
            dbResponse.close();
        } catch (SQLException ex) {
            ex.getMessage();
        } finally {
            System.out.println();
        }
    }

    // Accessor method that prints all the blood groups, rh status and availible
    // blood
    public void netBalance(Connection conn) {
        // Sql statementd for each blood type and a specific rh factor
        sql = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"O\" and RhFactor=\"Negative\"";
        String sql1 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"O\" and RhFactor=\"Positive\"",
                sql2 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"A\" and RhFactor=\"Negative\"",
                sql3 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"A\" and RhFactor=\"Positive\"",
                sql4 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"AB\" and RhFactor=\"Negative\"",
                sql5 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"AB\" and RhFactor=\"Positive\"",
                sql6 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"B\" and RhFactor=\"Negative\"",
                sql7 = "Select BloodType, RhFactor, SUM(Quantity) from BloodBank where BloodType =\"B\" and RhFactor=\"Positive\"";
        try {

            // Preparing and executing querrie for each blood type and its rh factor status
            Statement stmt = conn.createStatement();
            ResultSet firstResult = stmt.executeQuery(sql);
            stmt = conn.createStatement();
            ResultSet secondResult = stmt.executeQuery(sql1);
            stmt = conn.createStatement();
            ResultSet thirdResult = stmt.executeQuery(sql2);
            stmt = conn.createStatement();
            ResultSet fourthResult = stmt.executeQuery(sql3);
            stmt = conn.createStatement();
            ResultSet fifthResult = stmt.executeQuery(sql4);
            stmt = conn.createStatement();
            ResultSet sixthResult = stmt.executeQuery(sql5);
            stmt = conn.createStatement();
            ResultSet seventhResult = stmt.executeQuery(sql6);
            stmt = conn.createStatement();
            ResultSet eighthResult = stmt.executeQuery(sql7);

            // Print all the data collected
            System.out.printf("%-15s %-15s %s%n", "Blood Type".toUpperCase(), "Rhesus Status".toUpperCase(),
                    "Available".toUpperCase() + " (ml)");
            System.out.println("==============================================================================");
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (firstResult.getString(1) == null) ? "O" : firstResult.getString(1),
                    (firstResult.getString(2) == null) ? "Negative" : firstResult.getString(2), firstResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (secondResult.getString(1) == null) ? "O" : secondResult.getString(1),
                    (secondResult.getString(2) == null) ? "Positive" : secondResult.getString(2),
                    secondResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (thirdResult.getString(1) == null) ? "A" : thirdResult.getString(1),
                    (thirdResult.getString(2) == null) ? "Negative" : thirdResult.getString(2), thirdResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (fourthResult.getString(1) == null) ? "A" : fourthResult.getString(1),
                    (fourthResult.getString(2) == null) ? "Positive" : fourthResult.getString(2),
                    fourthResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (seventhResult.getString(1) == null) ? "B" : seventhResult.getString(1),
                    (seventhResult.getString(2) == null) ? "Negative" : seventhResult.getString(2),
                    seventhResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (eighthResult.getString(1) == null) ? "B" : eighthResult.getString(1),
                    (eighthResult.getString(2) == null) ? "Positive" : eighthResult.getString(2),
                    eighthResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (fifthResult.getString(1) == null) ? "AB" : fifthResult.getString(1),
                    (fifthResult.getString(2) == null) ? "Negative" : fifthResult.getString(2), fifthResult.getInt(3));
            System.out.printf("%-15s %-15s %-5d%n%n",
                    (sixthResult.getString(1) == null) ? "AB" : sixthResult.getString(1),
                    (sixthResult.getString(2) == null) ? "Positive" : sixthResult.getString(2), sixthResult.getInt(3));
            System.out.printf("%-30s  %d %s%n%n", "Total blood available: ".toUpperCase(),
                    firstResult.getInt(3) + secondResult.getInt(3) + thirdResult.getInt(3) + fourthResult.getInt(3)
                            + fifthResult.getInt(3) + sixthResult.getInt(3) + seventhResult.getInt(3)
                            + eighthResult.getInt(3),
                    "ml");

            // Close access to the database
            firstResult.close();
            secondResult.close();
            thirdResult.close();
            fourthResult.close();
            fifthResult.close();
            sixthResult.close();
            seventhResult.close();
            eighthResult.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

        }
    }
}