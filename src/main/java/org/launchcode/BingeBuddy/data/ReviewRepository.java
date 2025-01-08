package org.launchcode.BingeBuddy.data;

import org.launchcode.BingeBuddy.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findByMovie_Id(Integer movieId);

    Optional<Review> findById(Integer movieId);

    List<Review> findByMovie_IdOrderByCreatedAtDesc(Integer movieId);

}
