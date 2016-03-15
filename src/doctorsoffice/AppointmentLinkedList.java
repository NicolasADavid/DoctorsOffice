package doctorsoffice;

import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 * Class for holding appointments. Ordered by time.
 * @author Nico
 */
public class AppointmentLinkedList {

    private Node first;

    /**
     * Constructs an empty linked list.
     */
    public AppointmentLinkedList() {
        first = null;
    }

    /**
     * Returns the first element in the linked list.
     *
     * @return the first element in the linked list
     */
    public Appointment getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.data;
    }

    /**
     * Removes the first element in the linked list.
     *
     * @return the removed element
     */
    public Appointment removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Appointment element = first.data;
        first = first.next;
        return element;
    }

    /**
     * Adds an element to the front of the linked list.
     *
     * @param element the element to add
     */
    public void addFirst(Appointment element) {
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = first;
        first = newNode;
    }

    /**
     * Returns an iterator for iterating through this list.
     *
     * @return an iterator for iterating through this list
     */
    AppointmentListIterator AppointmentListIterator() {
        return new AppointmentListIterator();
    }

    class Node {

        public Appointment data;
        public Node next;
    }

    class AppointmentListIterator {

        private Node position;
        private Node previous;

        /**
         * Constructs an iterator that points to the front of the linked list.
         */
        public AppointmentListIterator() {
            position = null;
            previous = null;
        }

        /**
         * Moves the iterator past the next element.
         *
         * @return the traversed element
         */
        public Appointment next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            previous = position; // Remember for remove

            if (position == null) {
                position = first;
            } else {
                position = position.next;
            }

            return position.data;
        }

        /**
         * Tests if there is an element after the iterator position.
         *
         * @return true if there is an element after the iterator position
         */
        public boolean hasNext() {
            if (position == null) {
                return first != null;
            } else {
                return position.next != null;
            }

        }

        /**
         * Looks for anAppointment in the list.
         *
         * @param anAppointment
         * @return anAppointment if found, null if not
         */
        public Appointment find(Appointment anAppointment) {

            position = null; //Set iterator to beginning

            while (hasNext()) {
                if (next().equals(anAppointment)) {
                    return anAppointment;
                }
            }
            return null;
        }
        /**
         * Looks for anAppointment in the list with matching firstName and lastName
         * @param firstName
         * @param lastName
         * @return anAppointment if found, null if not
         */
        public Appointment find(String firstName, String lastName){
            position = null; //Set iterator to beginning

            while (hasNext()) {
                
                Appointment app = next();
                
                if (app.firstName.equals(firstName)&&app.lastName.equals(lastName)){
                    return app;
                }
                
            }
            return null;
            
        }

        /**
         * Attempts to remove an appointment and replace it with a new appointment.
         * Will attempt to add appointment regardless of remove old appointment success.
         * @param oldApp appointment to be removed
         * @param newApp appointment to be added
         * @return true if new appointment could be added, false if not
         */
        public boolean replaceAppointment(Appointment oldApp, Appointment newApp) {

            if (find(oldApp) != null) { //Removes it if it's found
                System.out.println("Found appointment. Removing.");
                remove();
            } else {
                System.out.println("Did not find appointment.");
            }

            System.out.println("Adding new appointment.");
            return makeAppointment(newApp); //Tries to add the new one

        }
        
        /**
         * Tries to add anAppointment to the appointment list.
         *
         * @param anAppointment
         * @return true if successful, false if unsuccessful (desired time occupied).
         */
        public boolean makeAppointment(Appointment anAppointment) {

            //Adds immediately if there are not appointments in list.
            if (position == null) {
                add(anAppointment);
                System.out.println("Added first appointment.");
                return true;
            }

            position = null; //Set iterator to beginning

            while (hasNext()) {
                next();
                if (anAppointment.time.compareTo(position.data.time) < 0) {
                    //Less than current position. Can only happen with first.
                    addFirst(anAppointment);
                    System.out.println("Added to beginning.");
                    return true;
                } //else if (anAppointment.time.getTime().compareTo(position.data.time.getTime()) == 0) {
                //This was puzzling to get to work with a Calendar object.
                  else if (anAppointment.time.equals(position.data.time)) {  
                    //Equals current position time.
                    System.out.println("Couldn't add.");
                    return false; //Queue it
                } else if (hasNext()) {
                    if (anAppointment.time.compareTo(position.next.data.time) < 0) {
                        //Is greater than current and less than next.
                        add(anAppointment);
                        System.out.println("Added.");
                        return true;
                    }
                } else {
                    //Could be equal or greater than next. Loop.
                }
            }

            //Reached here because !hasNext and haven't added yet.
            add(anAppointment);
            System.out.println("Added to end.");
            return true;
        }
        
        /**
         * Adds anAppointment in the current position. Pushing the current node
         * behind it.
         *
         * @param anAppointment
         */
        private void add(Appointment anAppointment) {
            if (position == null) {
                addFirst(anAppointment);
                position = first;
            } else {
                Node newNode = new Node();
                newNode.data = anAppointment;
                newNode.next = position.next;
                position.next = newNode;
                previous = position;
                position = newNode;
            }
        }

        /**
         * Attempts to remove an appointment.
         * @param anAppointment
         * @return true if successful, false if not
         */
        public boolean removeAppointment(Appointment anAppointment) {
            if (find(anAppointment) != null) { //Removes it if it's found
                remove();
                System.out.println("Appointment removed.");
                return true; //Found appointment
            } else {
                System.out.println("Could not find appointment.");
                return false; //Couldn't find that appointment
            }
        }
        
        /**
         * Validates the hour of an appointment. Valid time (9-11,13-15)
         * @param anAppointment
         * @return true if valid hour, false if not
         */
        public boolean validateAppointment(Appointment anAppointment){
            
            return anAppointment.time.getHours() == 9
                    || anAppointment.time.getHours() == 10
                    || anAppointment.time.getHours() == 11
                    || anAppointment.time.getHours() == 13
                    || anAppointment.time.getHours() == 14
                    || anAppointment.time.getHours() == 15;
            
        }

        /**
         * Remove the Node that the iterator's position is currently on.
         */
        private void remove() {
            if (previous == position) {
                throw new IllegalStateException();
            }

            if (position == first) {
                removeFirst();
            } else {
                previous.next = position.next;
            }
            position = previous;
        }
    }
}
