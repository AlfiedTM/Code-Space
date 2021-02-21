// console app that performs CRUD ops as it interacts with the database
// Main run app
// Alfred T Matakala

using System;
using System.Data.SQLite;
namespace HELSB
{
    class App
    {
        public static void Main(string[] args)
        {
            HELSB studAccessor = new HELSB();
            SQLiteConnection conn = Connect.databaseConnection();
            string firstName, lastName, programme, institution;
            int studentNumber;
            byte choice = 0;
            do
            {
                Console.WriteLine("{0,50} {1,50}", "                                                    ", "Higher Education and Scholarship's Board".ToUpper());
                Console.WriteLine("{0,30} {1,40}", "         ", "===========================================================================================================");
                Console.WriteLine("1. Add a student record");
                Console.WriteLine("2. Update a student record");
                Console.WriteLine("3. Search for a student record");
                Console.WriteLine("4. View all student records");
                Console.WriteLine("5. Delete a student record");
                Console.WriteLine("6. Shutdown ");
                Console.Write(":  ");
                choice = Convert.ToByte(Console.ReadLine());
                switch (choice)
                {

                    case 1:
                        Console.WriteLine("Please enter Student's details");
                        Console.Write("First name: ");
                        firstName = Console.ReadLine();

                        Console.Write("Last name: ");
                        lastName = Console.ReadLine();

                        Console.Write("Student Identification Number: ");
                        studentNumber = Convert.ToInt32(Console.ReadLine());
                        // Console.ReadLine();
                        Console.Write("Programme: ");
                        programme = Console.ReadLine();

                        Console.Write("Learning institution: ");
                        institution = Console.ReadLine();
                        studAccessor.addStudent(firstName, lastName, programme, institution, studentNumber, conn);
                        break;

                    case 2:
                        Console.WriteLine("Please enter Student's details");
                        Console.Write("Student Identification number: ");
                        studentNumber = Convert.ToInt32(Console.ReadLine());

                        if (studAccessor.recordSearch(studentNumber, conn))
                        {
                            Console.Write("First name: ");
                            firstName = Console.ReadLine();

                            Console.Write("Last name: ");
                            lastName = Console.ReadLine();

                            Console.Write("New Student Identification Number: ");
                            var newStudentNumber = Convert.ToInt32(Console.ReadLine());

                            Console.Write("Programme: ");
                            programme = Console.ReadLine();

                            Console.Write("Learning institution: ");
                            institution = Console.ReadLine();
                            studAccessor.updateRecord(firstName, lastName, programme, institution, studentNumber, newStudentNumber, conn);
                        }
                        break;


                    case 3:
                        Console.Write("Enter student's Identification Number: ");
                        studentNumber = Convert.ToInt32(Console.ReadLine());
                        studAccessor.fetchRecord(studentNumber, conn);
                        break;

                    case 4:
                        studAccessor.studentRecords(conn);
                        break;

                    case 5:
                        Console.Write("Enter student's Identification Number: ");
                        studentNumber = Convert.ToInt32(Console.ReadLine());
                        studAccessor.deleteRecord(studentNumber, conn);
                        break;

                    case 6:
                        Console.WriteLine("Logging off......");
                        return;
                    default:
                        Console.WriteLine("Error.....Invalid options");
                        break;
                }
            } while (choice != 5);

            try
            {
                if (conn == null)
                {

                }
                else
                    conn.Close();
            }
            catch (SQLiteException ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                conn.Close();
            }
        }
    }
}
