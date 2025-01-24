/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package infrastructure.repositories.interfaces;
import java.util.List;

public interface IRepository<T> {
    void add(T obj);
    List<T> getAll();
}