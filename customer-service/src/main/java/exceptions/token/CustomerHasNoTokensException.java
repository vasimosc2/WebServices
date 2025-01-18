/**
 *  @primary-author Emil (s174265)
 *  @co-author Tobias (s173899)
 */
package exceptions.token;

public class CustomerHasNoTokensException extends Exception {
    public CustomerHasNoTokensException(String customerId) {
        super("Customer " + customerId + " has no tokens.");
    }
}
