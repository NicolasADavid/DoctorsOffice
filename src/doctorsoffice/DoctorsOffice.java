package doctorsoffice;

import java.util.Scanner;
import doctorsoffice.AppointmentLinkedList.AppointmentListIterator;
import doctorsoffice.WaitListQueue.WaitListIterator;

/**
 *
 * Program that does some operations related to appointments in an
 * AppointmentLinkedList and WaitListQueue. Then presents a menu to the user for
 * further operations.
 *
 * @author Nico
 */
public class DoctorsOffice {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Create appointment list, iterator, and waitlist
        AppointmentLinkedList appointments = new AppointmentLinkedList();
        AppointmentListIterator iterator = appointments.AppointmentListIterator();

        WaitListQueue waitList = new WaitListQueue();

        //Creating/cancelling/changing the appointments in the rubric
        createDefaultAppointments(iterator, waitList);

        //Show what's in the appointment list and wait list
        printAppointmentList(appointments);
        printWaitList(waitList);

        //Menu
        boolean flag = false;
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Hi. Enter an option (a,b,c,d,e)");
            System.out.println("a: Make an appointment.");
            System.out.println("b: Change an appointment.");
            System.out.println("c: Cancel an appointment.");
            System.out.println("d: Look up an appointment.");
            System.out.println("e: Process an appointment from waiting list.");
            System.out.println("f: End program.");

            String input = in.next();
            switch (input) {
                case "a":
                    System.out.println("Make an appointment:");
                    makeAppointment(iterator, waitList);
                    break;
                case "b":
                    System.out.println("Change an appointment:");
                    changeAppointment(iterator, waitList);
                    break;
                case "c":
                    System.out.println("Cancel an appointment:");
                    cancelAppointment(iterator);
                    break;
                case "d":
                    System.out.println("Look up an appointment:");
                    findAppointment(iterator, waitList);
                    break;
                case "e":
                    System.out.println("Process an appointment from waiting list:");
                    processWaitQueue(iterator, waitList);
                    break;
                case "f":
                    System.out.println("Goodbye!");
                    flag = true;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }

            System.out.println();
            printAppointmentList(appointments);
            printWaitList(waitList);

        } while (!flag);

    }

    /**
     * Gathers input from user to make appointment and attempts to add it to the
     * list. Queues the appointment in waitList if unsuccessful
     *
     * @param iterator Of the list trying to be added to
     * @param waitList Queues the appointment if unsuccessful
     */
    static void makeAppointment(AppointmentListIterator iterator, WaitListQueue waitList) {

        try {

            //Gather input from user
            Scanner in = new Scanner(System.in);

            System.out.println("Enter first name");
            String firstName = in.next();

            System.out.println("Enter last name");
            String lastName = in.next();

            System.out.println("Enter year");
            int year = in.nextInt();

            System.out.println("Enter month (1-12)");
            int month = in.nextInt();

            System.out.println("Enter day of month (1-31)");
            int dayOfMonth = in.nextInt();

            System.out.println("Enter hour of day (9-11,13-16)");
            int hourOfDay = in.nextInt();

            //Create appointment
            Appointment anAppointment = new Appointment(firstName, lastName, year, month, dayOfMonth, hourOfDay);

            //Check if valid appointment
            if (iterator.validateAppointment(anAppointment)) {
                //Attempt to add
                if (iterator.makeAppointment(anAppointment)) {
                    System.out.println("Appointment added successfully");
                } else {
                    System.out.println("Desired time occupied, added to wait list queue.");
                    waitList.enQueue(anAppointment);
                }
            } else {
                System.out.println("Not a valid appointment time. Nothing done.");
            }

            System.out.println();

        } catch (Exception e) {
            System.out.println("Something went wrong with your input.");
            System.err.println("Caught Exception: " + e.getMessage());
            System.out.println();
        }

    }

    /**
     * Overloaded makeAppointment if Appointment already exists.
     *
     * @param iterator calls makeAppointment
     * @param waitList queues if unsuccessful
     * @param anAppointment appointment to be made
     */
    static void makeAppointment(AppointmentListIterator iterator, WaitListQueue waitList, Appointment anAppointment) {

        if (iterator.validateAppointment(anAppointment)) {
            if (iterator.makeAppointment(anAppointment)) {
                System.out.println("Appointment added successfully");
            } else {
                System.out.println("Desired time occupied, added to wait list queue.");
                waitList.enQueue(anAppointment);
            }
        } else {
            System.out.println("Not a valid appointment time. Nothing done.");
        }
    }

    /**
     * Removes appointment and attempts to add a new appointment. Asks user for
     * info about old appointment. If found, prompts user for new appointment
     * information and attempts to add with iterator. Queues in waitList if not
     * successful.
     *
     * @param iterator calls replaceAppointment
     * @param waitList queues if unsuccessful
     */
    static void changeAppointment(AppointmentListIterator iterator, WaitListQueue waitList) {

        try {
            //Ask user for old appointment information
            System.out.println("Enter OLD appointment information");

            Scanner in = new Scanner(System.in);

            System.out.println("Enter first name of appointment to be replaced");
            String firstName = in.next();

            System.out.println("Enter last name of appointment to be replaced");
            String lastName = in.next();

            System.out.println("Enter year of appointment to be replaced");
            int year = in.nextInt();

            System.out.println("Enter month (1-12) of appointment to be replaced");
            int month = in.nextInt();

            System.out.println("Enter day of month (1-31) of appointment to be replaced");
            int dayOfMonth = in.nextInt();

            System.out.println("Enter hour of day (9-11,13-15) of appointment to be replaced");
            int hourOfDay = in.nextInt();

            Appointment oldApp = new Appointment(firstName, lastName, year, month, dayOfMonth, hourOfDay);

            //Try to find old appointment
            if (iterator.find(oldApp) != null) {

                //If found, gather new appointment info, and replace
                System.out.println("Found old appointment! Now enter information"
                        + "for new appointment:");

                in = new Scanner(System.in);

                System.out.println("Enter year of new appointment");
                int newYear = in.nextInt();

                System.out.println("Enter month (1-12) of new appointment");
                int newMonth = in.nextInt();

                System.out.println("Enter day of month (1-31) of new appointment");
                int newDayOfMonth = in.nextInt();

                System.out.println("Enter hour of day (9-11,13-15) of new appointment");
                int newHourOfDay = in.nextInt();

                Appointment newApp = new Appointment(firstName, lastName, newYear, newMonth, newDayOfMonth, newHourOfDay);

                //Attempt to replace
                if (iterator.replaceAppointment(oldApp, newApp)) {
                    System.out.println("Successful change");
                } else {
                    //Only reached if found old appointment
                    System.out.println("Couldn't add new appointment. Queued");
                    waitList.enQueue(newApp);
                }

            } else {
                System.out.println("Couldn't find old appointment. Cannot replace.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong with your input.");
            System.err.println("Caught Exception: " + e.getMessage());
            System.out.println();
        }

    }

    /**
     * Overloaded chageAppointment if the Appointment already exists.
     *
     * @param iterator calls replaceAppointment
     * @param waitList Queues if unsuccessful
     * @param oldApp old Appointment
     * @param newApp new Appointment
     */
    static void changeAppointment(AppointmentListIterator iterator, WaitListQueue waitList, Appointment oldApp, Appointment newApp) {

        if (iterator.find(oldApp) != null) {

            if (iterator.replaceAppointment(oldApp, newApp)) {
                System.out.println("Successful change");
            } else {
                System.out.println("Couldn't add new appointment. Queued");
                waitList.enQueue(newApp);
            }

        } else {
            System.out.println("Couldn't find old appointment. Cannot replace.");
        }

    }

    /**
     * For removing and appointment for the appointment list. Gathers user input
     * and attempts to find/remove an appointment matching input.
     *
     * @param iterator iterator of the list for removing the appointment
     */
    static void cancelAppointment(AppointmentListIterator iterator) {
        try {
            //Ask user for appointment information
            System.out.println("Enter information of appointment to be canceled");

            Scanner in = new Scanner(System.in);

            System.out.println("Enter first name of appointment to be canceled");
            String firstName = in.next();

            System.out.println("Enter last name of appointment to be canceled");
            String lastName = in.next();

            System.out.println("Enter year of appointment to be canceled");
            int year = in.nextInt();

            System.out.println("Enter month (1-12) of appointment to be canceled");
            int month = in.nextInt();

            System.out.println("Enter day of month (1-31) of appointment to be canceled");
            int dayOfMonth = in.nextInt();

            System.out.println("Enter hour of day (9-11,13-15) of appointment to be canceled");
            int hourOfDay = in.nextInt();

            Appointment oldApp = new Appointment(firstName, lastName, year, month, dayOfMonth, hourOfDay);

            //Attempts to remove the appointment
            if (iterator.removeAppointment(oldApp)) {
                System.out.println("Successfully removed appointment.");
            } else {
                System.out.println("Could not find appointment. Could not remove. Check input.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong with your input.");
            System.err.println("Caught Exception: " + e.getMessage());
            System.out.println();
        }

    }

    /**
     * Overloaded cancelAppointment if the Appointment already exists.
     * @param iterator iterator of the list trying to remove from
     * @param anAppointment appointment to be removed
     */
    static void cancelAppointment(AppointmentListIterator iterator, Appointment anAppointment) {
        if (iterator.removeAppointment(anAppointment)) {
            System.out.println("Successfully removed appointment.");
        } else {
            System.out.println("Could not find appointment. Could not remove. Check input.");
        }
    }

    /**
     * Gathers input, searches for an appointment, and prints the appointment.
     * Attempts to find an appointment matching input. Prints if successful.
     * also searches waitList
     * @param iterator iterator of the list to be searched
     * @param waitList waitList to be searched
     */
    static void findAppointment(AppointmentListIterator iterator, WaitListQueue waitList) {

        try {
            WaitListIterator waitListIterator = waitList.WaitListIterator();

            //Ask user for appointment information
            System.out.println("Enter information of appointment to be found");

            Scanner in = new Scanner(System.in);

            System.out.println("Enter first name");
            String firstName = in.next();

            System.out.println("Enter last name");
            String lastName = in.next();

            //Attempt to find appointment in main list
            if (iterator.find(firstName, lastName) != null) {
                System.out.println("Found appointment in main list");
                System.out.println(iterator.find(firstName, lastName).toString());
            } else {
                System.out.println("Could not find appointment in main list");
            }

            //Attempt to find appointment in waiting list
            if (waitListIterator.find(firstName, lastName) != null) {
                System.out.println("Found appointment in waiting list");
                System.out.println(waitListIterator.find(firstName, lastName).toString());
            } else {
                System.out.println("Could not find appointment in wait list");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong with your input.");
            System.err.println("Caught Exception: " + e.getMessage());
            System.out.println();
        }

    }

    /**
     * Processes the appointment at the front of the waitList queue. Attempts
     * to add the appointment with iterator. Re-queues if unsuccessful.
     * @param iterator iterator of the list to attempt to add to
     * @param waitList waitList to take dequeue and re-queue appointments from/to.
     */
    static void processWaitQueue(AppointmentListIterator iterator, WaitListQueue waitList) {

        boolean flag = false;

        //Get an appointment
        Appointment anAppointment = waitList.deQueue();

        //Attempt to add appointment with iterator
        if (iterator.makeAppointment(anAppointment)) {
            System.out.println("Successfully made appointment for " + anAppointment.firstName + ". Removing from queue.");
        } else {
            System.out.println("Couldn't add appointment for " + anAppointment.firstName + ". Requeueing");
            waitList.enQueue(anAppointment);
        }

    }

    /**
     * Creates/cancels/changes appointments from assignment rubric. Calls the versions
     * of the relevant methods that receive an appointment as a parameter.
     * @param iterator of the list to add/cancel/remove to/from
     * @param waitList waitList to queue to if adding/changing unsuccessful
     */
    static void createDefaultAppointments(AppointmentListIterator iterator, WaitListQueue waitList) {

        System.out.println("Create a new Appointment for John Smith at 3pm on a certain date");
        //a.)  Create a new Appointment for John Smith at 3pm on a certain date
        Appointment a = new Appointment("John", "Smith", 2000, 1, 1, 15);
        makeAppointment(iterator, waitList, a);
        System.out.println();

        System.out.println("Create another Appointment for Mary Jones at 2pm  on a certain date");
        //b.)  Create another Appointment for Mary Jones at 2pm  on a certain date
        Appointment b = new Appointment("Mary", "Jones", 2000, 1, 1, 14);
        makeAppointment(iterator, waitList, b);
        System.out.println();

        System.out.println("Create another Appointment for Jim Hernandez at 1pm  on a certain date");
        //c.)  Create another Appointment for Jim Hernandez at 1pm  on a certain date
        Appointment c = new Appointment("Jim", "Hernandez", 2000, 1, 1, 13);
        makeAppointment(iterator, waitList, c);
        System.out.println();

        System.out.println("Change the Appointment for Mary Jones from 3pm to 11:00am  on a certain date");
        //d.)  Change the Appointment for Mary Jones from 3pm to 11:00am  on a certain date
        Appointment d = new Appointment("Mary", "Jones", 2000, 1, 1, 11);
        changeAppointment(iterator, waitList, b, d);
        System.out.println();

        System.out.println("Create another Appointment for Ron Stevens at 10am on a certain date");
        //e.)  Create another Appointment for Ron Stevens at 10am on a certain date
        Appointment e = new Appointment("Ron", "Stevens", 2000, 1, 1, 10);
        makeAppointment(iterator, waitList, e);
        System.out.println();

        System.out.println("\"Cancel\" the Appointment for John Smith at 3pm on a certain date .");
        //f.)   "Cancel" the Appointment for John Smith at 3pm on a certain date .
        cancelAppointment(iterator, a);
        System.out.println();

        System.out.println("Create another Appointment for Nick Hernandez at 9am on a certain date");
        //g.)  Create another Appointment for Nick Hernandez at 9am  on a certain date
        Appointment g = new Appointment("Nick", "Hernandez", 2000, 1, 1, 9);
        makeAppointment(iterator, waitList, g);
        System.out.println();

        System.out.println("Attempt to create another Appointment for Joe Kline at 9am on \n"
                + "same date as Nick (place Kline in the Waiting Queue for any cancellations)");
        //h.)  Attempt to create another Appointment for Joe Kline at 9am on 
        //same date as Nick (place Kline in the Waiting Queue for any cancellations)
        Appointment h = new Appointment("Joe", "Kline", 2000, 1, 1, 9);
        makeAppointment(iterator, waitList, h);
        System.out.println();

        System.out.println("Create another Appointment for Maria Garcia at 11am on a certain date.");
        //i.)  Create another Appointment for Maria Garcia at 11am on a certain date.
        Appointment i = new Appointment("Maria", "Garcia", 2000, 1, 2, 11);
        makeAppointment(iterator, waitList, i);
        System.out.println();

        System.out.println("Create another Appointment for Jose Gonzalez at 11am on same \n"
                + "date as Maria (place Gonzalez in the Waiting Queue for any cancellations)");
        //j.)  Create another Appointment for Jose Gonzalez at 11am on same 
        //date as Maria (place Gonzalez in the Waiting Queue for any cancellations)
        Appointment j = new Appointment("Jose", "Gonzalez", 2000, 1, 2, 11);
        makeAppointment(iterator, waitList, j);
        System.out.println();

        System.out.println("Attempt to create another Appointment for Luis Leeds at 5pm on a certain date (error message)");
        //k.)  Attempt to create another Appointment for Luis Leeds at 5pm on a certain date (error message)
        Appointment k = new Appointment("Luis", "Leeds", 2000, 1, 1, 17);
        makeAppointment(iterator, waitList, k);
        System.out.println();

    }

    /**
     * Prints all of the appointments in the appointments AppointmentLinkedList
     * @param appointments AppointmentLinkedList to print appointments from.
     */
    static void printAppointmentList(AppointmentLinkedList appointments) {

        System.out.println("Appointment List *******************");
        AppointmentListIterator iterator = appointments.AppointmentListIterator();
        while (iterator.hasNext()) {
            Appointment anAppointment = iterator.next();
            System.out.println(anAppointment.toString());
        }
        System.out.println();
    }

    /**
     * Prints all of the appointments in the waitList
     * @param waitList WaitListQueue to print appointments from.
     */
    static void printWaitList(WaitListQueue waitList) {
        System.out.println("Waiting List *******************");
        WaitListIterator waitIterator = waitList.WaitListIterator();
        while (waitIterator.hasNext()) {
            Appointment anAppointment = waitIterator.next();
            System.out.println(anAppointment.toString());
        }
        System.out.println();
    }

}
