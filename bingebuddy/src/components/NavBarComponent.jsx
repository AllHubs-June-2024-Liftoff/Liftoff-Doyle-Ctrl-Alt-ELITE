import React from "react";
import { Navigate, useNavigate } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { useAuth } from "./AuthContext";

const NavBarComponent = () => {
  const { logout } = useAuth();
  const navigator = useNavigate();

  const logoutUser = (e) => {
    e.preventDefault();
    logout();
    localStorage.removeItem("Token");
    navigator("/");
  };

  return (
    <>
      <Navbar className="navbar navbar-custom d-flex navbar-brand text-light">
        <Container>
          <Navbar.Brand className="navbar-brand text-light">
            BingeBuddy: An Online Movie Rating Platform
          </Navbar.Brand>

          <Nav className="justify-content-end nav-link navbar-brand text-light">
            <button
              className="btn btn-success logoutButton"
              name="logoutButton"
              type="submit"
              onClick={(e) => {
                logoutUser(e);
              }}
            >
              Logout
            </button>
          </Nav>
        </Container>
      </Navbar>
    </>
  );
};

export default NavBarComponent;
