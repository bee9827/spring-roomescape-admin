package roomescape.reservation.repository;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface Repository<T> {
    Collection<T> findAll();
    T findById(long id);
    T save(T t);
    void deleteById(Long id);
}
