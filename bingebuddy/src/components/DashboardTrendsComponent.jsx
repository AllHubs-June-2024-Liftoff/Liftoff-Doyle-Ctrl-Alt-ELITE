import React, { useEffect, useRef, useState } from "react";
import SidebarComponent from "./SidebarComponent";
import { Chart as ChartJS, defaults, registerables } from "chart.js/auto";
import { Doughnut } from "react-chartjs-2";

import axios from "axios";
import { jwtDecode } from "jwt-decode";

defaults.maintainAspectRatio = false;
defaults.responsive = true;

defaults.plugins.title.display = true;
defaults.plugins.title.align = "start";
defaults.plugins.title.font.size = 20;
defaults.plugins.title.color = "black";

ChartJS.register(...registerables);

const DashboardTrendsComponent = () => {
  const [labelX, setLabelX] = useState();
  const [labelY, setLabelY] = useState();
  const [labelZ, setLabelZ] = useState();

  const [numberX, setNumberX] = useState();
  const [numberY, setNumberY] = useState();
  const [numberZ, setNumberZ] = useState();

  let x = 0;
  let y = 0;
  let z = 0;

  let a;
  let b;
  let c;

  const [error, setError] = useState();

  const chartRef = useRef(null);

  useEffect(() => {
    loadUserData();

    console.log(
      "From loadUserData - Values in x: " + x + " y: " + y + " z: " + z
    );
  }, []);

  //

  let data = {
    // labels: ["Reviews", "Comments", "Watchlists"],
    labels: [labelX, labelY, labelZ],
    datasets: [
      {
        label: "User Activity Stats",
        data: [numberX, numberY, numberZ],
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
  };

  let options = {
    plugins: {
      title: {
        //text: "",
        text: "Reviews, Comments & Watchlists",
      },
    },
    legend: {
      labels: {
        fontSize: 25,
      },
    },
  };

  //
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
      console.log("Line 95: response.data :");
      console.log(response.data);

      a = response.data[0].label;
      x = response.data[0].value;

      console.log(" Label - a: ", response.data[0].label, a);
      console.log(" Value - x: ", response.data[0].value, x);

      setLabelX(response.data[0].label);
      console.log(" Value - LabelX: ", labelX);
      setNumberX(response.data[0].value);
      console.log(" Value - NumberX: ", numberX);
      //

      console.log("Line 105: response.data :");

      b = response.data[1].label;
      y = response.data[1].value;

      console.log(" Label - b: ", response.data[1].label, b);
      console.log(" Value - y: ", response.data[1].value, y);

      setLabelY(response.data[1].label);
      console.log(" Value - LabelY: ", labelY);
      setNumberY(response.data[1].value);
      console.log(" Value - NumberY: ", numberY);

      //
      console.log("Line 114: response.data :");

      c = response.data[2].label;
      z = response.data[2].value;

      console.log(" Label - c: ", response.data[2].label, c);
      console.log(" Value - z: ", response.data[2].value, z);

      setLabelZ(response.data[2].label);
      console.log(" Value - LabelZ: ", labelZ);
      setNumberZ(response.data[2].value);
      console.log(" Value - NumberZ: ", numberZ);

      //
      // After setUserData
    } catch (error) {
      setError("Cannot get user trends at this time");
      console.log("Cannot get user trends at this time", error.toString());
    }

    // printing out the response
    console.log("After Catch");
    console.log(x, y, z);
    // console.log("UserData :" + userData);

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
          {/* {loadStats} */}
          {/* <Doughnut data={data} height={200}></Doughnut> */}

          <div className="dataCard categoryCard">
            <Doughnut data={data}></Doughnut>

            {/* <Doughnut
              data={{
                labels: ["Reviews", "Comments", "Watchlists"],
                datasets: [
                  {
                    label: "User Activity Stats",
                    // data: commentsReviewsTrends.map((data) => data.value),
                    //data: userData.map((data) => data.value),
                    // data: [{ reviews }, { comments }, { watchlists }],

                    data: [x, y, z],
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
            /> */}
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
