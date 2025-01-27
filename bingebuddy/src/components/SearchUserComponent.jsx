import axios from "axios";
import { useState } from "react";

let initial = []
let update = []
const SearchUserComponent = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const[showUsers, setUsers] = useState(initial)
  

const displayUsers = function(user){
  return <ul key={user}>{user}</ul>
}
  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  }

  const handleSubmit = () => {
    if (searchTerm === ""){
      alert("enter a search term")
      return
    }
    const fetchData = async () => {
      axios.get(`http://localhost:8080/users/search/${searchTerm}`).then(
        function(response){
          for (let item in response.data){
             update.push(response.data[item]["username"])
          }
          initial = [];
          initial = update;
          setUsers(initial);
          update = [];
        }
      )
    }
    fetchData();
  }


  return (
    <div>
        <h1 className='center'>             
            Search for a User!
        </h1>
        <b>
          <input type="text" value={searchTerm} onChange={handleSearch}/>
          <input type="submit" onClick={handleSubmit}/>
      </b>
      <ul>
        {showUsers.map((x) => displayUsers(x))}
      </ul>
    </div>
  )
}

export default SearchUserComponent