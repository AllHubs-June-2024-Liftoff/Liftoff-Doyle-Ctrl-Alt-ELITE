import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Appbar from "./componenets/Appbar";
import MovieSearch from "./pages/MovieSearch";
import BasicTextFields from "./componenets/SearchField";

function App() {
  return (
    <Router>
      <Appbar />
      <BasicTextFields />
      <Routes>
        {/* <Route path="/" element={<Landing />} /> */}
        <Route path="/MovieSearch" element={<MovieSearch />} />
      </Routes>
    </Router>
  );
}

export default App;
