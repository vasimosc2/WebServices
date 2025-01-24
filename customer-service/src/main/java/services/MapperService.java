/**
 * @primary-author Vasileios Moschou (s222566)
 * @co-author Marcu Muro (s233662)
 *
 */
package services;

import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@jakarta.enterprise.context.ApplicationScoped
public class MapperService extends ModelMapper {

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                     .map(element -> super.map(element, targetClass))
                     .collect(Collectors.toList());
    }
}
