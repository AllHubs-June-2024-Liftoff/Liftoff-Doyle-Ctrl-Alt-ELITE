import React, { useEffect, useState } from "react";
import DashboardProfileComponent from "./DashboardProfileComponent";
import { jwtDecode } from "jwt-decode";

const DecodeTokenComponent = () => {
  useEffect(() => {
    const decodeToken = () => {
      try {
        const token = localStorage.getItem("jwtToken");
        if (!token) {
          throw new Error("No token found");
        }

        const decodedToken = jwtDecode(token);
        console.log("Decoded Token:  " + decodedToken.username);
      } catch (error) {
        console.error("Error decoding token", error);
      }
    };

    decodeToken();
  }, []); // Empty dependency array means this effect runs once on mount

  return (
    <div>
      <DashboardProfileComponent subject={subject} />
    </div>
  );
};

export default DecodeTokenComponent;
