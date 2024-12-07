package org.launchcode.BingeBuddy.data;

import org.launchcode.BingeBuddy.models.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findByApiId(String apiId);
}
