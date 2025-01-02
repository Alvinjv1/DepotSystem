import java.util.LinkedList;
import java.util.Queue;

public class QueofCustomers {
    private Queue<Customer> customerQueue;

    public QueofCustomers() {
        this.customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    public Customer processNextCustomer() {
        return customerQueue.poll(); // Retrieves and removes the head of the queue(a method used from linked list)
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public int size() {
        return customerQueue.size();
    }

    public Queue<Customer> getQueue() {
        return customerQueue;
    }
}
