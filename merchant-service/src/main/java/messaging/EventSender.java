/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package messaging;

public interface EventSender {

	void sendEvent(Event event) throws Exception;

}
