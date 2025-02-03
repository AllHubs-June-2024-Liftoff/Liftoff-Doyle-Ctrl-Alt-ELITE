import React from "react";
import { useState, useEffect } from "react";
import { useAuth } from "./AuthContext";
import axios from "axios";

const DashboardMainComponent = () => {
  const { authUser, setAuthUser, isLoggedIn, setIsLoggedIn } = useAuth();

  const [userId, setUserId] = useState(null);
  const [userFirstName, setUserFirstName] = useState(null);

  const [open, setOpen] = useState(true);
  const Menus = [
    { title: "Dashboard", src: "Chart_fill", path: "/dashboard-main/1" },
    { title: "Profile", src: "User", path: "/user-profile/1" },
    { title: "Reviews", src: "Chat", path: "/user-reviews/1" },
    { title: "Comments", src: "Folder", path: "/user-comments/1" },
    { title: "Watchlists ", src: "Calendar", path: "/user-watchlists/1" },
    { title: "Trends", src: "Chart", path: "/user-trends/1" },
    { title: "Search Movies", src: "Search", path: "/search-movie" },
    { title: "Search Users", src: "Search", path: "/search-user" },
    //{ title: "Settings", src: "Setting", path:"/user-settings/1" },
  ];

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      axios
        .get("http://localhost:8080/{userId}", {
          header: { Authorization: "Bearer ${token}" },
        })
        .then((response) => setUserId(response.data))
        .catch((error) => console.error(error));

      setUserId(response.id);
      setUserFirstName(response.firstName);
    }
  }, []);

  return (
    <>
      <div className="flex">
        {/* 1 */}
        <div
          className={`${open ? "w-72" : "w-20"} 
        bg-dark-purple duration-300 h-screen p-5 pt-8 relative`}
        >
          <img
            src="../src/Images/Dashboard-Images/control.png"
            className={`absolute cusor-pointer rounded-full -right-3 top-9 w-7 border-2 border-dark-purple 
          ${!open && "rotate-180"}`}
            // onClick={() => setOpen(!open)}    // arrow on dashboard
          />

          <div className="flex gap-x-4 items-center">
            <img
              src="../src/Images/Dashboard-Images/logo.png"
              className={`cursor-pointer duration-500 
          ${open && "rotate-[360deg]"}`}
            />
            <h1
              className={`text-white origin-left font-medium text-xl duration-200 
          ${!open && "scale-0"}`}
            >
              Dashboard
            </h1>
          </div>

          <ul className="pt-6">
            {Menus.map((menu, index) => (
              <li
                key={index}
                className={`text-gray-300 text-sm flex items-center gap-x-4 cursor-pointer p-2
          hover:bg-light-white rounded-md
          ${menu.gap ? "mt-9" : "mt-2"} 
          ${index === 0}`}
              >
                {/* index === 0 && "bg-light-white" */}
                <a href={menu.path} className="nav-link text-truncate">
                  <img src={`../src/Images/Dashboard-Images/${menu.src}.png`} />
                  <span
                    className={`${!open && "hidden"} origin-left duration-200`}
                  >
                    {menu.title}
                  </span>
                </a>
              </li>
            ))}
          </ul>
        </div>
        {/* 1 */}

        <div className="h-screen flex-1 p-7">
          {/* <h1 className="text-2xl font-semibold">Welcome BingeBuddy  {userFirstName} !</h1> */}

          <h1>Welcome BingeBuddy User!</h1>

          <div className="container-dashmain">
            <div className="header">
              <h2> </h2>
            </div>

            <div className="logo-image"></div>

            <div className="content">
              <p className="logo-text">
                Welcome to Bingebuddy – your go-to destination for honest,
                insightful, and entertaining movie reviews!
              </p>
              <p className="logo-text">
                Whether you’re searching for the next must-watch blockbuster, a
                hidden indie gem, or a binge-worthy film series, we have got you
                covered.
              </p>

              <p className="logo-text">
                Our reviews dive deep into storytelling, performances,
                cinematography, and everything that makes a movie unforgettable.
              </p>

              <p className="logo-text">
                Stay updated with the latest releases, explore classics, and
                join a community of fellow movie lovers who share your passion
                for cinema.
              </p>

              <p className="logo-text">
                Let Bingebuddy be your trusted guide for what to watch next!
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default DashboardMainComponent;
