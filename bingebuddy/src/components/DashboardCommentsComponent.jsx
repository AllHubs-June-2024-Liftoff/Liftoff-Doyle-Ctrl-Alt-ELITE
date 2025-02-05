import React, { useState } from "react";
import SidebarComponent from "./SidebarComponent";
import { useNavigate } from "react-router-dom";
import Mufasa from "../Images/Movies/Mufasa.jpg";
import Despicable from "../Images/Movies/DespicableMe4.jpg";
import Godzilla from "../Images/Movies/GodzillaKong.jpg";
import InsideOut from "../Images/Movies/InsideOut.png";
import Moana from "../Images/Movies/Moana.png";

const DashboardCommentsComponent = () => {
  const [comments, setComments] = useState();
  const navigate = useNavigate();

  return (
    <div className="flex">
      <div>
        <SidebarComponent />
      </div>

      <div className="container">
        <h2 className="text-center header"> Comments </h2>
        <div className="table-wrapper">
          <table className="table table-striped table-bordered">
            <thead>
              <tr>
                <th>Movies</th>
                <th>Comments</th>
              </tr>
            </thead>
            <tbody>
              {/* {
                comments.map(comment => 
                  <tr key={comments.id}>
                      <td>{comments.created_at}</td>
                      <td>{comments.content}</td>
                      <td>{comments.movieId}</td> */}
              <tr>
                <td>
                  <img
                    className="image-size"
                    src={Mufasa}
                    alt="Mufasa: The Lion King"
                  ></img>
                </td>
                <td>
                  <p> Agreed, a full-fledged family movie!</p>
                  <p>27th Dec 2025</p>
                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateComments(comment.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeComment(comment.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    Delete
                  </button> */}
                </td>
              </tr>
              <tr>
                <td>
                  <img className="image-size" src={Moana} alt="Moana 2"></img>
                </td>
                <td>
                  <p>Yes!</p>
                  <p>10th December 2024</p>
                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateComment(comment.id)}
                  >
                    {" "}
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeComment(comment.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    {" "}
                    Delete
                  </button> */}
                </td>
              </tr>
              <tr>
                <td>
                  <img
                    className="image-size"
                    src={Godzilla}
                    alt="Godzilla x Kong"
                  ></img>
                </td>
                <td>
                  <p>
                    Just enough of a thriller for my 10-year old. Would watch it
                    again.
                  </p>
                  <p>4th April 2024</p>
                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateComment(comment.id)}
                  >
                    {" "}
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeComment(comment.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    {" "}
                    Delete
                  </button> */}
                </td>
              </tr>
              <tr>
                <td>
                  <img
                    className="image-size"
                    src={InsideOut}
                    alt="Inside Out 2"
                  ></img>
                </td>
                <td>
                  <p>Yes, good family entertainer</p>
                  <p>7th July 2024</p>
                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateComment(comment.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeComment(comment.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    Delete
                  </button> */}
                </td>
              </tr>
              <tr>
                <td>
                  <img
                    className="image-size"
                    src={Mufasa}
                    alt="Mufasa: The Lion King"
                  ></img>
                </td>
                <td>
                  <p>We loved it too!</p>
                  <p>2nd Jan 2025</p>
                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateComment(comment.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeComment(comment.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    {" "}
                    Delete
                  </button> */}
                </td>
              </tr>
              {/* </tr>)
              }
               */}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default DashboardCommentsComponent;
