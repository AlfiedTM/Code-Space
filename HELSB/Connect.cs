using System;
using System.Data.SQLite;

namespace HELSB
{
   
    class Connect
    {
         private static string url =@"URI=file:HELSB_DB.db";
         private static SQLiteConnection conn = null;
        private Connect(){}

        public static SQLiteConnection databaseConnection(){
           if(conn==null){
               
            try
            {
            conn=new SQLiteConnection(url);
                    Console.WriteLine("Open");
           conn.Open();
            }
            catch (SQLiteException ex)
            {
                
                Console.WriteLine(ex.Message);
            }
       
           }
           
           return conn;
        }

// public static void Main(string[] args){
//     databaseConnection();
// }
    }
}
