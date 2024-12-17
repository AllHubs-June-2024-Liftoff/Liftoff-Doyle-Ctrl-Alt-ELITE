export const fetchMovies = async query => {
  const movieQ = await fetch(
    `http://www.omdbapi.com/?apikey=2e813b38&s=${query}`
  );

  return movieQ.json();
};
