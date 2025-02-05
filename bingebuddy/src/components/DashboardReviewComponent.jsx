import React, { useEffect, useState } from "react";
import SidebarComponent from "./SidebarComponent";
import { useNavigate } from "react-router-dom";
import Mufasa from "../Images/Movies/Mufasa.jpg";
import Despicable from "../Images/Movies/DespicableMe4.jpg";
import Godzilla from "../Images/Movies/GodzillaKong.jpg";
import InsideOut from "../Images/Movies/InsideOut.png";
import Moana from "../Images/Movies/Moana.png";

const DashboardReviewComponent = () => {
  const [reviews, setReviews] = useState();
  const navigator = useNavigate();

  // useEffect(() => {
  //   getAllReviews();
  // }, [])

  // function getAllReviews(){
  //   listreviews()
  //   .then((response) => {
  //     setReviews(response.data);
  //   }).catch(error => {
  //     console.error(error);
  //   })
  // }

  //   function addNewReview(){
  //     navigator('/add-user') // when Add User button is clicked, will take user to /add-user page
  //   }

  //   function updateReview(id){
  //     navigator(`/edit-review/${id}`);
  //   }

  //   function removeReview(id){
  //     console.log(id);
  //     deleteReview(id).then((response) => {
  //         getAllReviews();
  //     }).catch(error => {
  //         console.log(error);
  //     })
  // }

  return (
    <div className="flex">
      <div>
        <SidebarComponent />
      </div>

      {/* <div className='h-screen flex-1 p-7'>
        <h1 className='center'>
          User can view the Reviews they wrote here
        </h1>    
      </div> */}

      <div className="container ">
        <h2 className="text-center header"> Reviews </h2>
        <div className="table-wrapper">
          <table className="table table-striped table-bordered">
            <thead>
              <tr>
                <th>Movies</th>
                <th>Review</th>
              </tr>
            </thead>
            <tbody>
              {/* {
                reviews.map(review => 
                  <tr key={review.id}>
                      <td>{review.created_at}</td>
                      <td>{review.content}</td>
                      <td>{review.movieId}</td> */}

              <tr>
                <td>
                  <img
                    className="image-size"
                    src={Godzilla}
                    alt="Godzilla x Kong: The New Empire"
                  ></img>
                </td>
                <td>
                  <p>Loved the movie!!</p>
                  <p>2nd April 2024</p>

                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateReview(review.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeReview(review.id)}
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
                    src={InsideOut}
                    alt="Inside Out 2"
                  ></img>
                </td>
                <td>
                  <p>Fantastic! My family had a fun time!</p>
                  <p>25th June 2024</p>

                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateReview(review.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeReview(review.id)}
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
                    src={Despicable}
                    alt="Despicable Me 4"
                  ></img>
                </td>
                <td>
                  <p>Would watch it again! An all around entertainer</p>
                  <p>15th July 2024</p>

                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateReview(review.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeReview(review.id)}
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
                  <p>Superb! My family's new favorite</p>
                  <p>10th Dec 2024</p>
                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateReview(review.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeReview(review.id)}
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
                  <p>
                    Already booked tickets for tomorrow. That's how good it is!
                  </p>
                  <p>25th Dec 2024</p>

                  {/* <button
                    className="btn btn-secondary"
                    onClick={() => updateReview(review.id)}
                  >
                    {" "}
                    Update
                  </button>
                  <button
                    className="btn btn-dark"
                    onClick={() => removeReview(review.id)}
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

export default DashboardReviewComponent;
