package org.launchcode.BingeBuddy.controllers;

import org.launchcode.BingeBuddy.data.CommentRepository;
import org.launchcode.BingeBuddy.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // Display all comments
    @GetMapping
    public String index(Model model) {
        model.addAttribute("comments", commentRepository.findAll());
        return "comment";
    }

    // Display comments for a specific review
    @GetMapping("/reviews/{reviewId}")
    public String getCommentsByReview(@PathVariable Long reviewId, Model model) {
        List<Comment> comments = commentRepository.findByReview_Id(reviewId);
        model.addAttribute("comments", comments);
        model.addAttribute("reviewId", reviewId);
        return "comment";
    }

    // Display the Add Comment Form
    @GetMapping("/reviews/{reviewId}/add")
    public String displayAddCommentForm(@PathVariable Long reviewId, Model model) {
        Comment comment = new Comment();
        comment.setReviewId(reviewId);
        model.addAttribute("comment", comment);
        model.addAttribute("reviewId", reviewId);
        return "comment-add";
    }

    // Handle the submission of the Add Comment Form
    @PostMapping("/reviews/{reviewId}/add")
    public String addComment(@PathVariable Long reviewId, @ModelAttribute Comment comment, Model model) {
        comment.setReviewId(reviewId);
        commentRepository.save(comment);
        return "redirect:/comments/reviews/" + reviewId;
    }

    // Delete a comment
    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, Model model) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            Long reviewId = comment.getReview().getId();
            commentRepository.deleteById(id);
            return "redirect:/comments/reviews/" + reviewId;
        }
        return "redirect:/comments";
    }
}


