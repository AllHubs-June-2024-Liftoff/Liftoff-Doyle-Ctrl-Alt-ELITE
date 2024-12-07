package org.launchcode.BingeBuddy.data;

import org.launchcode.BingeBuddy.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByMovie_Id(Long movieId);



    List<Review> findByMovie_IdOrderByCreatedAtDesc(Long movieId);

}