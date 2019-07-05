package code.controller;

import code.JSON.Comment;
import code.model.Account;
import code.model.AdSlide;
import code.model.Rating;
import code.service.AccountService;
import code.service.AdSlideService;
import code.service.RatingService;
import code.service.ToyService;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentRestController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ToyService toyService;

    @Autowired
    private AccountService accountService;

    private String timeAgo(Timestamp time) {
        DateTime dateTime = new DateTime(time.getTime());
        DateTime currentTime = new DateTime(System.currentTimeMillis());
        int year = Years.yearsBetween(dateTime, currentTime).getYears();
        if (year != 0) {
            return year + " years ago";
        } else {
            int month = Months.monthsBetween(dateTime, currentTime).getMonths();
            if (month != 0) {
                return month + " months ago";
            } else {
                int week = Weeks.weeksBetween(dateTime, currentTime).getWeeks();
                if (week != 0) {
                    return week + " weeks ago";
                } else {
                    int day = Days.daysBetween(dateTime, currentTime).getDays();
                    if (day != 0) {
                        return day + " days ago";
                    } else {
                        int hour = Hours.hoursBetween(dateTime, currentTime).getHours();
                        if (hour != 0) {
                            return hour + " hours ago";
                        } else {
                            int minutes = Minutes.minutesBetween(dateTime, currentTime).getMinutes();
                            if (minutes != 0) {
                                return minutes + " minutes ago";
                            } else {
                                int second = Seconds.secondsBetween(dateTime, currentTime).getSeconds();
                                if (second != 0) {
                                    return second + " seconds ago";
                                } else {
                                    return "Just finished";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/comments/{toyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> listCommentsByToyId(@PathVariable Long toyId) {
        List<Rating> ratings = ratingService.findAllByToyId(toyId);
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Comment> comments = new ArrayList<>();
        for (Rating rating : ratings) {
            comments.add(new Comment(rating.getRatingStar(), rating.getComment(), timeAgo(rating.getPostDate()), rating.getAccount().getName()));
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/", method = RequestMethod.POST)
    public ResponseEntity<Void> createComment(@RequestBody Comment comment) {
        if (comment.getRatingStar() == null) {
            comment.setRatingStar(0L);
        }
        Account account = accountService.findAccountByUsername(comment.getUsername());
        Rating rating = new Rating(
                comment.getRatingStar(),
                comment.getComment(),
                0L,
                new Timestamp(System.currentTimeMillis()),
                toyService.findById(comment.getToyId()),
                account
        );
        ratingService.save(rating);
        if (comment.getRatingStar() != ratingService.findAllByAccount_UsernameAndToy_Id(comment.getUsername(), comment.getToyId()).get(0).getRatingStar()) {
            ratingService.updateRatingStar(comment.getToyId(), account.getId(), comment.getRatingStar());
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
