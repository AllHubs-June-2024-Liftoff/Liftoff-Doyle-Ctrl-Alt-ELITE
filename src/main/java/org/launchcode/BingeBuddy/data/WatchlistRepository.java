package org.launchcode.BingeBuddy.data;

import org.launchcode.BingeBuddy.models.Watchlist;
import org.launchcode.BingeBuddy.models.WatchlistStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends CrudRepository<Watchlist, Integer> {
    List<Watchlist> findByStatus(WatchlistStatus status);

    public List<Watchlist> getWatchlist(Watchlist watchlist);

    List<Watchlist> findAllByUserId(Integer integer);
}
