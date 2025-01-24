/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package messaging;

public interface EventReceiver {
	void receiveEvent(Event event) throws Exception;
}
