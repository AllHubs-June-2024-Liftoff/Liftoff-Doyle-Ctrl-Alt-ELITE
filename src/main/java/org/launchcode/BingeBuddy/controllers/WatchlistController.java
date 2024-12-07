package org.launchcode.BingeBuddy.controllers;

import org.launchcode.BingeBuddy.data.WatchlistRepository;
import org.launchcode.BingeBuddy.models.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/watchlists")
public class WatchlistController {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("watchlist", watchlistRepository.findAll());
        return "watchlist";
    }

    @GetMapping("/add")
    public String displayAddWatchlistForm(Model model) {
        model.addAttribute("watchlist", new Watchlist());
        return "watchlist-add"; // Returns `watchlist-add.html`
    }

    @PostMapping("/add")
    public String addToWatchlist(@ModelAttribute Watchlist watchlist, Model model) {
        watchlistRepository.save(watchlist);
        return "redirect:/watchlists";
    }




}
