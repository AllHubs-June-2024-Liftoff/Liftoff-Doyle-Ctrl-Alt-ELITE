import React, { useState } from "react";
import { Link } from "react-router-dom";
import { fetchMovies } from "../utils/api";

const MovieSearch = () => {
  const [query, setQuery] = useState("");
  const [movies, setMovies] = useState([]);

  const searchHandler = async () => {
    const data = await fetchMovies(query);
    setMovies(data.Search);
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Search movies..."
        value={query}
        onChange={e => setQuery(e.target.value)}
      />
      <button onClick={searchHandler}>Search</button>
      <div>
        {movies.map(movie => (
          <div key={movie.imdbID}>
            <Link>
              <h3>
                {movie.Title} ({movie.Year})
              </h3>
              {movie.Poster !== "N/A" ? (
                <img
                  src={movie.Poster}
                  alt={movie.Title}
                  style={{ width: "300px" }}
                />
              ) : (
                <p>No Poster Available</p>
              )}
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MovieSearch;
