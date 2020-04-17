<template>
  <div id="searchComic">
    <header>
      <ul class="nav">
        <li>
          <router-link
            :to="{ name: 'user', params: {username: logInUser.username}}"
          >{{logInUser.username}}</router-link>
        </li>
      </ul>
      <ul class="nav-right">
        <li>
          <router-link :to="{ name: 'logoff' }">Logoff</router-link>
        </li>
        <li>
          <router-link :to="{ name: 'login-home' }">Home</router-link>
        </li>
      </ul>
    </header>
    <div id="main">
      <br />
      <div>
        <label id="searchLabel">Volume Title:</label>
        <input id="searchComicInput" v-model="searchText" />
        <br>
        <button id="searchComicButton" v-on:click="requestVolume(searchText)">Search</button>

      <br>
        <div class="collection-card" v-for="comic in comics" :key="comic.volumeNumber">
          <router-link
            :to="{ name: 'searchResults', params:{username: username,
            collectionId: collectionId, volumeNumber:comic.volumeNumber}}"
          >
            <img class="collection-image" v-bind:src="comic.volumeImg" />
          </router-link>
          <h2 id="volume-name">{{comic.volumeName}}</h2>
          <p id="number-of-issues">Number of Issues: {{comic.numberOfIssues}}</p>
          <p id="publisher">Publisher: {{comic.publisher}}</p>
          <p id="start-year">Start Year: {{comic.startYear}}</p>
          <router-link
            :to="{ name: 'searchResults', params:{username: username,
            collectionId: collectionId, volumeNumber:comic.volumeNumber}}"
          >
            View Comics
          </router-link>
          <br><br>
        </div>



      </div>
    </div>
  </div>
</template>

<script>
import authLib from "@/auth";
export default {
  data() {
    return {
      logInUser: {},
      searchText: "",
      comics: [],
      username: "",
      collectionId: ""
    };
  },

  methods: {
    requestVolume(volumeTitle) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/search/addVolume/${volumeTitle}`,
        {
          headers: {
            Authorization: "Bearer " + authLib.getToken(),
            Accept: "application/json",
            "Content-Type": "application/json"
          }
        }
      )
        .then(resp => {
          return resp.json();
        })
        .then(data => {
          this.comics = data;
          console.log(this.comics);
        })

        .catch(err => {
          console.error(err);
        });
    },
    getUserByUsername(username) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/users/${username}`,
        {
          headers: {
            Authorization: "Bearer " + authLib.getToken(),
            Accept: "application/json",
            "Content-Type": "application/json"
          }
        }
      )
        .then(response => {
          return response.json();
        })
        .then(user => {
          this.logInUser = user;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    const logInUser = authLib.getUser();
    this.getUserByUsername(logInUser.sub);
    this.collectionId = this.$route.params.collectionId;
    this.username = this.$route.params.username;
  }
};
</script>

<style>
.collection-image {
  width: 200px;
}
.collection-card {
  box-shadow: 3px 3px 19px #dcdcdc;
  border-radius: 10px;
  margin: 35px;
  width: 200px;
  display: inline-block;
}

.collection-card > p {
  font-size: 18px;
  margin: 0;
}
.collection-card > h2 {
  font-size: 28px;
  margin-bottom: 5px;
}
#searchComic {
  width: 100%;
}
#input-section {
  background-color: lightgray;
  margin: 20px;
  padding: 20px;
  width: 20%;
}
#comic-image {
  height: auto;
  width: 40%;
}
#searchLabel {
  font-size: 30px;
}
#searchComicInput {
  font-size: 26px;
  margin: 0 0 0 10px;
  font-family: "Bangers", cursive;
  padding: 0;
}
#searchComicButton {
  font-family: 'Bangers', cursive;
  font-size: 30px;
  border-radius: 8px;
  background: linear-gradient(135deg, #59b5cc, #00e5ee);
  border-style: none;
  width: 180px;
  height: 45px;
  transition: transform .3s;
   -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 25px 0 0 0;
}
#searchComicButton:hover {
  transform: scale(1.1); 
}
</style>