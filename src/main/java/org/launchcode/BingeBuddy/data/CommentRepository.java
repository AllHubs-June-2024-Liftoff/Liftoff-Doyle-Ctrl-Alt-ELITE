package org.launchcode.BingeBuddy.data;

import org.launchcode.BingeBuddy.models.Comment;
import org.launchcode.BingeBuddy.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByReview(Review review);

    List<Comment> findByReview_Id(Long reviewId);
}
