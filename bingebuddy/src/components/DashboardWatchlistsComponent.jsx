import React, { useState } from "react";
import SidebarComponent from "./SidebarComponent";
import { useNavigate } from "react-router-dom";

const DashboardWatchlistsComponent = () => {
  const [watchlists, setWatchlists] = useState();
  const navigate = useNavigate();

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
          User can create / view their Watchlists here
        </h1>
      </div> */}

      <div className="container">
        <h2 className="text-center header"> Watchlists </h2>

        <form className="center">
          <h4>Pick from the list to check out the status of your movies:</h4>

          <select id="watchlists" name="watchlists">
            <option value="PLANNED">TO BE WATCHED</option>
            <option value="WATCHING">WATCHING</option>
            <option value="WATCHED">WATCHED</option>
          </select>
        </form>

        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Date</th>
              <th>Movie</th>
              {/* <th>Action</th> */}
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
              <td>14th Feb 2025</td>
              <td>Captain America: Brave New World</td>
              {/* <td>
                <button
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
                </button>
              </td> */}
            </tr>
            <tr>
              <td> 23rd May 2025</td>
              <td>Mission: Impossible – The Final Reckoning</td>
              {/* <td>
                <button
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
                </button>
              </td> */}
            </tr>
            <tr>
              <td>2nd July 2025</td>
              <td>Jurassic World Rebirth</td>
              {/* <td>
                <button
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
                </button>
              </td> */}
            </tr>
            <tr>
              <td>11th July 2025</td>
              <td>Superman</td>
              {/* <td>
                <button
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
                </button>
              </td> */}
            </tr>
            <tr>
              <td>19th Dec</td>
              <td>Avatar: Fire and Ash</td>
              {/* <td>
                <button
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
                </button>
              </td> */}
            </tr>
            {/* </tr>)
              }
               */}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default DashboardWatchlistsComponent;
