/**
 *  @primary-author Emil (s174265)
 *  @co-author Tobias (s173899)
 */
package exceptions.token;

public class CustomerAlreadyRegisteredException extends Exception {
    public CustomerAlreadyRegisteredException(String customerId) {
        super("Customer " + customerId + " is already registered.");
    }
}