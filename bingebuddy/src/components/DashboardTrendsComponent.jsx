import React, { useEffect, useRef, useState } from "react";
import SidebarComponent from "./SidebarComponent";
import { Chart as ChartJS, defaults, registerables } from "chart.js/auto";
import { Bar, Doughnut, Line } from "react-chartjs-2";

import axios from "axios";
import { jwtDecode } from "jwt-decode";

import userTrends from "../data/userTrends.json";
import averageUser from "../data/AverageUser.json";
import commentsReviewsTrends from "../data/CommentsReviewsTrends.json";
import { data } from "react-router-dom";

defaults.maintainAspectRatio = false;
defaults.responsive = true;

defaults.plugins.title.display = true;
defaults.plugins.title.align = "start";
defaults.plugins.title.font.size = 20;
defaults.plugins.title.color = "black";

ChartJS.register(...registerables);

const DashboardTrendsComponent = () => {
  const [userData, setUserData] = useState([
    {
      label: "",
      value: "",
    },
  ]);

  let x = 0;
  let y = 0;
  let z = 0;

  const [error, setError] = useState();

  const chartRef = useRef(null);

  useEffect(() => {
    loadUserData();
  }, []);

  const handleDataChange = ({ reviews, comments, watchlists }) => {
    setDoughnutData({
      labels: ["Reviews", "Comments", "Watchlists"],
      datasets: [
        {
          label: "User Activity Stats",
          data: [{ reviews }, { comments }, { watchlists }],
          backgroundColor: [
            "rgba(43, 63, 229, 0.8)",
            "rgba(250, 192, 19, 0.8)",
            "rgba(253, 135, 135, 0.8)",
          ],
          borderColor: [
            "rgba(43, 63, 229, 0.8)",
            "rgba(250, 192, 19, 0.8)",
            "rgba(253, 135, 135, 0.8)",
          ],
        },
      ],
    });
  };

  const loadUserData = async (e) => {
    //e.preventDefault();

    try {
      const token = localStorage.getItem("Token");
      if (!token) {
        setError("Cannot get user profile at this time");
        console.log("Cannot get user token at this time");
        throw new Error("Failed to fetch user token");
      }

      const decodedToken = jwtDecode(token);

      const tokenValue = decodedToken.sub.toString();

      const response = await axios.get(
        "http://localhost:8080/user-activity" + "/" + tokenValue,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      //
      console.log("Line 153: response.data :");
      console.log(response.data[1].label);
      x = response.data[0].value;
      console.log(response.data[2].label);
      y = response.data[1].value;
      console.log(response.data[1].value);
      z = response.data[2].value;
      console.log(response.data);

      // Set User Data
      setUserData({
        ...userData,
        [response.data.label]: response.data.value,
      });
      console.log("UserData :" + userData);
      console.log("----------");
      console.log(JSON.stringify(userData, null, 2));
      console.log("----------");

      // After setUserData
    } catch (error) {
      setError("Cannot get user trends at this time");
      console.log("Cannot get user trends at this time", error.toString());
    }

    // printing out userData response
    console.log("After Catch");
    console.log(userData);
    console.log("UserData :" + userData);

    //
  }; // end of loadData()

  return (
    <div className="flex">
      <div>
        <SidebarComponent />
      </div>

      <div className="container">
        <div className="App-card">
          <h1 className="center">
            Your Trends : Reviews, Comments & Watchlists
          </h1>
          {/* {loadStats}
        <Doughnut data={data} options={options}></Doughnut> */}

          <div className="dataCard categoryCard">
            <Doughnut
              data={{
                labels: ["Reviews", "Comments", "Watchlists"],
                datasets: [
                  {
                    label: "User Activity Stats",
                    // data: commentsReviewsTrends.map((data) => data.value),
                    //data: userData.map((data) => data.value),
                    // data: [{ reviews }, { comments }, { watchlists }],

                    data: [20, 25, 15],
                    backgroundColor: [
                      "rgba(43, 63, 229, 0.8)",
                      "rgba(250, 192, 19, 0.8)",
                      "rgba(253, 135, 135, 0.8)",
                    ],
                    borderColor: [
                      "rgba(43, 63, 229, 0.8)",
                      "rgba(250, 192, 19, 0.8)",
                      "rgba(253, 135, 135, 0.8)",
                    ],
                    hoverOffset: 4,
                  },
                ],
              }}
              options={{
                plugins: {
                  title: {
                    text: "",
                    // text: "Reviews, Comments & Watchlists",
                  },
                },
              }}
            />
          </div>
        </div>
      </div>

      <div>
        <label>{error}</label>
      </div>

      <div></div>
    </div>
  );
};

export default DashboardTrendsComponent;
