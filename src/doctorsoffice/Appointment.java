package doctorsoffice;

import java.util.Calendar; //Was going to use this but aCalendar.getTime().equals(otherCalendar.getTime() was acting incomprehensibly.
import java.util.Date;

/**
 * Object that represents an appointment for a person.
 * @author Nico
 */
public class Appointment {

    String firstName; //First name of person
    String lastName; //Last name of person
    Date time; //Date/Time of appointment

    /**
     * Creates an empty appointment
     */
    public Appointment() {

    }

    /**
     * Creates an appointment with given parameters
     * @param firstName first name
     * @param lastName last name
     * @param year year of appointment
     * @param month month of appointment
     * @param dayOfMonth dayOfMonth of appointment
     * @param hour hour of appointment
     */
    public Appointment(String firstName, String lastName, int year, int month, int dayOfMonth, int hour) {
        this.firstName = firstName.replace(" ", "");
        this.lastName = lastName.replace(" ", "");

        time = new Date(year-1900, month - 1, dayOfMonth, hour, 0); //Number magic for user friendliness.
        System.out.println(firstName+" "+lastName+" "+time); //For debugging/confirmation purposes
    }

    /**
     * Compares an appointment to another appointment
     * @param o appointment to be compared to
     * @return true if firstName, lastName, and date/time matches
     */
    public boolean equals(Appointment o) {

        return (this.firstName.equals(o.firstName))
                && (this.lastName.equals(o.lastName))
                && (this.time.equals(o.time));

    }

    /**
     * Returns a string that represents the appointment.
     * @return string that represents the appointment
     */
    @Override
    public String toString() {

        String str = "\nName: " + this.lastName + ", " + this.firstName;

        //String time = this.time.getTime().getDay();
        str += "\nAppointment Date & Time: " + this.time.toString();
        
        return str;
    }

}
