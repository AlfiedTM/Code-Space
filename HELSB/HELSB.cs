using System;
using System.Data.SQLite;
using System.Collections;
namespace HELSB
{
   public class HELSB
    {
        private int studentID;
        private string firstName;
        private string lastName;
        private string programme;
        private string institution;
        public HELSB (){}
        public HELSB (string firstName, string lastName, string programme, string institution, int studentID){

            setFirstName(firstName);
            setLastName(lastName);
            setProgramme(programme);
            setInstitution(institution);
            setStudentID(studentID);

        }

       

       public void addStudent(string firstName, string lastName, string programme, string institution, int studentID, SQLiteConnection conn){
           try{
              SQLiteCommand command = new SQLiteCommand(conn); 
           command.CommandText = @"insert into PersonalDetails (StudentID, FirstName, LastName, Programme, Institution) values(@StudentID, @FirstName, @LastName, @Programme, @Institution)";
           command.Parameters.AddWithValue("@StudentID", studentID);
           command.Parameters.AddWithValue("@FirstName", firstName);
           command.Parameters.AddWithValue("@LastName", lastName);
           command.Parameters.AddWithValue("@Programme", programme);
           command.Parameters.AddWithValue("@Institution", institution);
           command.Prepare();
           int count = command.ExecuteNonQuery();
          if(count==1){
              Console.WriteLine("Record successfully added\n");
          }
           }catch(SQLiteException ex){
               Console.WriteLine(ex.Message);
           }
           
           }
           
           public bool recordSearch(int studentID, SQLiteConnection conn){
               bool recordFound = false;
               try{
                   SQLiteCommand command = new SQLiteCommand(conn);
                command.CommandText =@"select * from PersonalDetails where StudentID = @studentID";
                    command.Parameters.AddWithValue("@StudentID", studentID);
                 command.Prepare();
                 SQLiteDataReader data = command.ExecuteReader();
                  if(data.HasRows){
                      recordFound = true;
                  }
                  
                  else{
                      recordFound = false;
                        Console.WriteLine($"Record with ID {studentID} does not exits\n");
                  }

               }catch(SQLiteException ex){
                   Console.WriteLine($"{ex.Message}\n");
               }
            return recordFound;
           }

           public void updateRecord(string firstName, string lastName, string programme, string institution, int studentID,int newStudentId,  SQLiteConnection conn){
               try{
                   SQLiteCommand command = new SQLiteCommand(conn);
  
                       command.CommandText = @"update personaldetails set StudentID = @newStudentId, FirstName=@FirstName, LastName=@LastName, Programme=@Programme, Institution=@Institution where StudentID = @StudentID";
                    command.Parameters.AddWithValue("@StudentID", studentID);
           command.Parameters.AddWithValue("@FirstName", firstName);
           command.Parameters.AddWithValue("@LastName", lastName);
           command.Parameters.AddWithValue("@Programme", programme);
           command.Parameters.AddWithValue("@Institution", institution);
           command.Parameters.AddWithValue("@newStudentId", newStudentId);
           command.Prepare();
           int count = command.ExecuteNonQuery();
          if(count==1){
              Console.WriteLine("Record successfully updated\n");
          }
                  
               }catch(SQLiteException ex){
                   Console.WriteLine($"{ex.Message}\n");
               }
           }
         public void fetchRecord(int StudentID, SQLiteConnection conn){
             try{
                 SQLiteCommand command = new SQLiteCommand(conn);
                 command.CommandText =@"select * from personaldetails where StudentID = @StudentID";
                 command.Parameters.AddWithValue("@StudentID", StudentID);
                 command.Prepare();
                 SQLiteDataReader data = command.ExecuteReader();
                 if(!data.HasRows){
                     Console.WriteLine($"Record with ID {StudentID} does not exits\n");
                 } 
                 else{
                     Console.WriteLine("{0, -15}  {1,-15} {2,-20} {3, -25} {4, -15}", "First Name".ToUpper(), "Last Name".ToUpper(), "Student Number".ToUpper(), "Programme".ToUpper(), "Institution".ToUpper());
                  Console.WriteLine("===================================================================================================");
                 while(data.Read()){
                     Console.WriteLine("{0, -15}  {1,-15} {2,-20} {3, -25} {4, -15} \n", data.GetString(1), data.GetString(2), data.GetInt32(0), data.GetString(3), data.GetString(4));
                    
                 }
                 }
                 data.Close();
             }catch(SQLiteException ex){
                 Console.WriteLine(ex.Message);
             }
         }  

         public ArrayList studentRecords(SQLiteConnection conn){
            //  HELSB dataAcce = new HELSB();
            ArrayList allRecord = new ArrayList();
              try{
                var sql =@"select * from PersonalDetails";
                SQLiteCommand command = new SQLiteCommand(sql, conn);
               SQLiteDataReader data = command.ExecuteReader();
                 Console.WriteLine("{0, -15}  {1,-15} {2,-20} {3, -25} {4, -15}", "First Name".ToUpper(), "Last Name".ToUpper(), "Student Number".ToUpper(), "Programme".ToUpper(), "Institution".ToUpper());
                  Console.WriteLine("===================================================================================================");
                 while(data.Read()){
                    allRecord.Add(new HELSB(data.GetString(1), data.GetString(2),data.GetString(3), data.GetString(4),data.GetInt32(0)));
                    
                 }
                 data.Close();
                 foreach(HELSB record in allRecord){
                     Console.WriteLine("{0, -15}  {1,-15} {2,-20} {3, -25} {4, -15}", record.getFirstName(), record.getLastName(), record.getStudentID(), record.getProgramme(), record.getInstitution());
                 }
                 Console.WriteLine();
         }catch(SQLiteException ex){
             Console.WriteLine(ex.Message);
         }
         return allRecord;
         }

        
         public void deleteRecord(int studentId, SQLiteConnection conn){
             try{
                 SQLiteCommand command = new SQLiteCommand(conn);
                 command.CommandText = @"delete from PersonalDetails where StudentID = @studentId";
                 command.Parameters.AddWithValue("@StudentID", studentId);
                 command.Prepare();
                 int update = command.ExecuteNonQuery();
                 if(update == 1){
                     Console.WriteLine($"Record with Id {studentId} has been deleted succesfully\n");
                 }
                 else{
                   Console.WriteLine($"No record with Id {studentId} exists\n");  
                 }
             }catch(SQLiteException ex){
                 Console.WriteLine($"{ex.Message}");
             }
         }

        // Mutator methods

        public void setFirstName(string firstName){
                if(firstName.Equals(null)||firstName.Equals(" "))
                throw new ArgumentNullException("First name cannot be null\n");
                
                else this.firstName=firstName;
            
        }

        public void setLastName(string lastName){
                if(lastName.Equals(null)||lastName.Equals(" "))
                throw new ArgumentNullException("Last name cannot be null\n");
                
                else this.lastName=lastName;
            
        }

        public void setProgramme(string programme){
                if(programme.Equals(null)||programme.Equals(" "))
                throw new ArgumentNullException("Programme cannot be null");
                
                else this.programme=programme;
            
        }

        
        public void setInstitution(string institution){
                if(institution.Equals(null)||institution.Equals(" "))
                throw new ArgumentNullException("Institution cannot be null\n");
                
                else this.institution=institution;
            
        }

        public void setStudentID(int studentID){
                if(studentID.ToString().Length<8)
                throw new ArgumentNullException("Student number should 8 characters or more\n");
                
                else this.studentID=studentID;
            
        }

        // Accessor Methods
        public string getFirstName(){
            return firstName;
        }

        public string getLastName(){
            return lastName;
        }

        public string getInstitution(){
            return institution;
        }

        public string getProgramme(){
            return programme;
        }

        public int getStudentID(){
            return studentID;
        }
       }
    }
   

