package doctorsoffice;

import java.util.NoSuchElementException;

/**
 * Class for queuing appointments. Not in any order.
 * @author Nico
 */
public class WaitListQueue {

    private Node first;
    private Node last;

    public WaitListQueue() {
        first = null;
        last = null;
    }

    /**
     * Adds an appointment to the back of the queue
     * @param element appointment to be queued
     */
    public void enQueue(Appointment element) {

        Node newNode = new Node();
        newNode.data = element;

        if (empty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
    }

    /**
     * Removes the from the front of the queue
     *
     * @return the removed element
     */
    public Appointment deQueue() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Appointment element = first.data;
        first = first.next;
        return element;
    }

    /**
     * Checks whether this stack is empty.
     * @return true if the stack is empty
     */
    public boolean empty() {
        return first == null;
    }

    /**
     * Returns an iterator for the WaitListQueue
     * @return 
     */
    WaitListIterator WaitListIterator() {
        return new WaitListIterator();
    }

    class Node {

        public Appointment data;
        public Node next;
    }

    class WaitListIterator {

        private Node position;
        private Node previous;

        /**
         * Constructs an iterator that points to the front of the linked list.
         */
        public WaitListIterator() {
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
                return (position.next != null) && (position.next != position);
            }

        }

        /**
         * Finds an appointment that has a matching firstName and lastName
         * @param firstName
         * @param lastName
         * @return appointment if found, null if not
         */
        public Appointment find(String firstName, String lastName) {

            position = null; //Set iterator to beginning

            while (hasNext()) {

                Appointment app = next();

                if (app.firstName.equals(firstName) && app.lastName.equals(lastName)) {
                    return app;
                }

            }
            return null;

        }

    }

}
