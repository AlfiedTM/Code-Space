// Main run for the app
package BLOODBANK;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String bloodGroup, NRCNo, rhFactor , firstName, middleName, lastName, houseNo, streetName, province, nationality, phoneNumber;
        int quantity;
        Connection conn = Connect.databaseConnection();
        BloodBank bloodBanker = new BloodBank();
        byte choice = 0;
        Scanner inputData = new Scanner(System.in);

        do{
            System.out.printf("%112s%n", "Bervin Haemo Health Clinic".toUpperCase());
            System.out.printf("%140s%n%n","============================================================================================");
            System.out.println("1. Add a donor");
            System.out.println("2. Add a reciepient");
            System.out.println("3. Search for Donor");
            System.out.println("4. Search for reciepient");
            System.out.println("5. Display the Blood Balance");
            System.out.println("6. Match reciepient to donor");
            System.out.println("7. Shut Down");
            System.out.printf(":  ");
           try{
            choice = Byte.parseByte(inputData.next());
           }catch(NumberFormatException ex){
                choice = 0;
            //System.out.println("Invalid option: ");
           }
           finally{
              
            inputData.nextLine();
            System.out.println();
            switch(choice){
                case 1:
                System.out.println("Please Enter the Following details\n");
                System.out.printf("First name: ");
                firstName= inputData.nextLine();

                System.out.printf("Middle name: ");
                middleName= inputData.nextLine();

                System.out.printf("Last name: ");
                lastName= inputData.nextLine();

                // Ensuring correct data is given
                System.out.printf("Blood group: ");
                bloodGroup= inputData.nextLine();
                if(!(bloodGroup.equalsIgnoreCase("o")||bloodGroup.equals("a"))){
                    if(!(bloodGroup.equalsIgnoreCase("ab")||bloodGroup.equals("b"))){
                        System.out.println("Invalid Blood type. Only O, A, AB and B are allowed");
                        break;
                    }
                }

                // Ensuring correct data is given
                System.out.printf("Rhesus Factor: ");
                rhFactor = inputData.nextLine();
                if(!(rhFactor.equalsIgnoreCase("negative")||rhFactor.equalsIgnoreCase("positive"))){
                    System.out.println("Invalid Rh factor status. Rhesus factor should be Negative or Positive");
                    break;
                }
                

                System.out.printf("Phone number: ");
                phoneNumber= inputData.nextLine();

                System.out.printf("Nationality: ");
                nationality = inputData.nextLine();

                System.out.printf("National Registration Card Number: ");
                NRCNo =inputData.nextLine();

                System.out.printf("House No: ");
                houseNo= inputData.nextLine();

                System.out.printf("Street: ");
                streetName= inputData.nextLine();

                System.out.printf("Province: ");
                province = inputData.nextLine();

                // Ensuring correct data is given
                System.out.printf("Blood donated in mls: ");
                try{
                    quantity =Integer.parseInt(inputData.next());
                }catch(NumberFormatException ex){
                    System.out.println("Invalid parameter for blood");
                   break;
                }
                if(quantity <1){
                    System.out.println("Blood quantity should be greater than 0");
                   break;
                }

                // Register a donor
                bloodBanker.registerDonor(firstName, middleName, lastName, houseNo, streetName, province, nationality, bloodGroup, rhFactor, phoneNumber, NRCNo, quantity, conn);
                break;

                case 2:
                System.out.println("Please enter the following details");
                System.out.printf("Blood group: ");
                bloodGroup= inputData.nextLine();
                // Ensuring correct data is given
                if(!(bloodGroup.equalsIgnoreCase("o")||bloodGroup.equals("a"))){
                    if(!(bloodGroup.equalsIgnoreCase("ab")||bloodGroup.equals("b"))){
                        System.out.println("Invalid Blood type. Only O, A, AB and B are allowed");
                        break;
                    }
                }

                System.out.printf("Rhesus Factor: ");
                rhFactor = inputData.nextLine();

                // Ensuring correct data is given
                if(!(rhFactor.equalsIgnoreCase("negative")||rhFactor.equalsIgnoreCase("positive"))){
                    System.out.println("Error, Rhesus factor should be Negative or Positive");
                    break;
                }
                
                // Ensuring correct data is given
                System.out.printf("Blood donated in mls: ");
                try{
                    quantity =Integer.parseInt(inputData.next());
                    
                }catch(NumberFormatException ex){
                    System.out.println("Blood quantity should be greater than 0");
                   break;
                }
                if(quantity <1){
                    System.out.println("Blood quantity should be greater than 0");
                   break;
                }
                inputData.nextLine();
                // Ensuring correct data is given
                if(bloodBanker.availability(conn, bloodGroup, rhFactor, quantity)){
                    System.out.println("\nPlease enter Receipient's  details");
                    System.out.printf("First name: ");
                    firstName= inputData.nextLine();
    
                    System.out.printf("Middle name: ");
                    middleName= inputData.nextLine();
    
                    System.out.printf("Last name: ");
                    lastName= inputData.nextLine();
    
                    System.out.printf("Phone number: ");
                    phoneNumber= inputData.nextLine();
    
                    System.out.printf("Nationality: ");
                    nationality = inputData.nextLine();
    
                    System.out.printf("National Registration Card Number: ");
                    NRCNo =inputData.nextLine();
    
                    System.out.printf("House No: ");
                    houseNo= inputData.nextLine();
    
                    System.out.printf("Street: ");
                    streetName= inputData.nextLine();
    
                    System.out.printf("Province: ");
                    province = inputData.nextLine();

                    // Register a receipient
                    bloodBanker.registerReciepient(firstName, middleName, lastName, houseNo, streetName, province, nationality, bloodGroup, rhFactor, phoneNumber, NRCNo, quantity, conn);
                }
                else{
                    String RhFactor = rhFactor.substring(0,1).toUpperCase() + rhFactor.substring(1);
                    System.out.println("No sufficient blood available for blood type " + bloodGroup.toUpperCase() + " with rhesus status "+ RhFactor+"\n");
                }
                break;
                
                case 3:
                System.out.println("Enter Donor's details");
                System.out.printf("National Registration Number: ");
                NRCNo= inputData.nextLine();

                System.out.printf("Blood type: ");
                bloodGroup = inputData.next();
                // Ensuring correct data is given
                if(!(bloodGroup.equalsIgnoreCase("o")||bloodGroup.equals("a"))){
                    if(!(bloodGroup.equalsIgnoreCase("ab")||bloodGroup.equals("b"))){
                        System.out.println("Invalid Blood type. Only O, A, AB and B are allowed");
                        break;
                    }
                }

                bloodBanker.fetchDonorRecord(NRCNo, bloodGroup, conn);
                break;

                case 4:
                System.out.println("Enter the follwing details");
                System.out.printf("National Registration Number: ");
                NRCNo= inputData.nextLine();
                
                System.out.printf("Blood type: ");
                bloodGroup = inputData.next();

                // Ensuring correct data is given
                if(!(bloodGroup.equalsIgnoreCase("o")||bloodGroup.equals("a"))){
                    if(!(bloodGroup.equalsIgnoreCase("ab")||bloodGroup.equals("b"))){
                        System.out.println("Invalid Blood type. Only O, A, AB and B are allowed");
                        break;
                    }
                }

                // Fetch receipient record
                bloodBanker.fetchReciepientRecord(NRCNo, bloodGroup, conn);

                break;

                case 5:
                // Fetch all the blood balances in the database
                bloodBanker.netBalance(conn);
                break;

                case 6:
                System.out.println("Enter the following details");
                System.out.printf("Blood type: ");
                bloodGroup = inputData.nextLine();

                // Ensuring correct data is given
                if(!(bloodGroup.equalsIgnoreCase("o")||bloodGroup.equals("a"))){
                    if(!(bloodGroup.equalsIgnoreCase("ab")||bloodGroup.equals("b"))){
                        System.out.println("Invalid Blood type. Only O, A, AB and B are allowed");
                        break;
                    }
                }

                System.out.printf("Rhesus Factor: ");
                rhFactor = inputData.nextLine();

                // Ensuring correct data is given
                if(!(rhFactor.equalsIgnoreCase("negative")||rhFactor.equalsIgnoreCase("positive"))){
                    System.out.println("Error, Rhesus factor should be Negative or Positive");
                    break;
                }
                
                // Match a receipient through a function call
                bloodBanker.matchReceipient(rhFactor, bloodGroup, conn);
                break;

                case 7:
                try{
                    if(conn!=null){
                        conn.close();
                    }
                }catch(SQLException ex){
                    ex.getMessage();
                }finally{
                    System.out.println("Shutting down.....");
                }
                return;


                default:
                System.out.println("Invalid option....");
                break;
            }
        }
        }while(choice!=7);
        inputData.close();
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException ex){
            ex.getMessage();
        }
    }
}
