<template>
  <div class="width">
    <header>
      <ul class="nav-right">
        <li v-show="loggedOff">
          <router-link :to="{ name: 'login' }">Login</router-link>
        </li>
      </ul>
    </header>

    <div class="home">
      <div id="herosReading">
        <h1 id="loginHeader">Comic Book Collections</h1>
      </div>
      <div id="leaderboardContainer">
        <h2 id="leaderboardTitle">Comic Leaderboard</h2>
        <div id="leaderboard">
          <div id="topComics">
            <h4>Most Total Comics</h4>
            <div id="collectionUser" v-for="user in topComicUsers" :key="user.username">
              <p id="leaderboardUser">#{{user.rank}} {{user.username}}</p>
              <p>Comics: {{user.comicCount}}</p>
            </div>
          </div>
          <div id="topCollections">
            <h4>Most Collections</h4>
            <div id="collectionUser" v-for="user in topCollectionUsers" :key="user.username">
              <p id="leaderboardUser">#{{user.rank}} {{user.username}}</p>
              <p>Collections: {{user.collectionCount}}</p>
            </div>
          </div>
        </div>
      </div>

      <div class="collection-container-1">
        <br />
        <h2 id="browsePublic">Browse Public Collections</h2>
        <br />
        <input
          id="searchInput"
          type="text"
          v-model="search"
          placeholder="Search By Collection Name..."
        />
        <br />
        <br />
        <div
          v-for="collection in filteredList"
          :key="collection.collectionId"
          class="collection-card"
        >
          <div v-if="collection.comicsInCollection.length == 0">
            <h2 class="padding">Me and the boys when you have no comics in your collection</h2>
            <img class="boys-image" src="../assets/img/theboys.jpg" />
            <br />
          </div>

          <div v-if="collection.comicsInCollection.length > 0">
            <router-link
            :to="{name: 'collection', params: {username: collection.username, collectionId: collection.collectionId}}">
            <img class="collection-image" v-bind:src="collection.comicsInCollection[0].image" />
            </router-link>
          </div>

          <h2>{{collection.name}}</h2>
          <router-link
            :to="{name: 'collection', params: {username: collection.username, collectionId: collection.collectionId}}"
            tag="a"
          >View Details</router-link>
          <br />
          <br />Collection By
          <router-link
            :to="{name: 'user', params: {username: collection.username}}"
            tag="a"
          >{{collection.username}}</router-link>
          <br />
          <br />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import authLib from "@/auth";

export default {
  name: "logout-home",
  data() {
    return {
      search: "",
      collections: [],
      topCollectionUsers: [],
      topComicUsers: []
    };
  },
  methods: {
    getCollections() {
      fetch(`http://localhost:8080/AuthenticationApplication/api/collections`)
        .then(response => {
          return response.json();
        })
        .then(collections => {
          console.log("made it here");
          this.collections = collections;
        })
        .catch(err => {
          console.error(err);
        });
    },
    getTopUsersByCollection() {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/collection/topUsers`
      )
        .then(response => {
          return response.json();
        })
        .then(topCollectionUsers => {
          this.topCollectionUsers = topCollectionUsers;
        })
        .catch(err => {
          console.error(err);
        });
    },
    getTopUsersByComic() {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/comic/topUsers`
      )
        .then(response => {
          return response.json();
        })
        .then(topComicUsers => {
          this.topComicUsers = topComicUsers;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  computed: {
    filteredList() {
      return this.collections.filter(collection => {
        return collection.name
          .toLowerCase()
          .includes(this.search.toLocaleLowerCase());
      });
    },
    loggedIn() {
      let link = false;
      let logInUser = authLib.getUser();
      if (logInUser !== null) {
        link = true;
      }

      return link;
    },
    loggedOff() {
      let link = false;
      let logInUser = authLib.getUser();
      if (logInUser === null) {
        link = true;
      }

      return link;
    }
  },
  created() {
    this.getCollections();
    this.getTopUsersByCollection();
    this.getTopUsersByComic();
  }
};
</script>

<style>
.home {
  font-family: "Bangers", cursive;
  width: cover;
}
.collection-image {
  width: 200px;
  height: 300px;
}
.collection-container-1 {
  display: inline-block;
  width: 80%;
}
.collection-container-2 {
  display: inline-block;
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

.boys-image {
  width: 200px;
}
#searchInput {
  font-size: 20px;
  font-family: "Bangers", cursive;
}
#collectionUser {
  text-align: left;
  margin: 0;
  display: flex;
}
#collectionUser > p {
  margin: 0;
}
#leaderboardContainer {
  border: 2px;
  border-color: black;
  border-style: double;
  margin: 10px 5px 0 5px;
}
#leaderboard {
  background-image: url("../assets/img/justiceLeague.jpg");
  background-size: 20%;
  background-repeat: no-repeat;
  background-position: center;
  display: flex;
  justify-content: space-between;
  margin: 0 0 10px 0;
}
#topCollections {
  margin: 0 20px 0 0;
}
#topCollections > h4 {
  font-size: 34px;
  margin: 5px;
}
#topComics {
  margin: 0 0 0 20px;
}
#topComics > h4 {
  font-size: 34px;
  margin: 5px;
}
#leaderboardTitle {
  font-size: 46px;
  margin: 5px;
}
#leaderboardUser {
  width: 260px;
}
#browsePublic {
  font-size: 36px;
  margin: 30px 0 5px 0;
}
@media (max-width: 850px) {
  #leaderboard,
  #leaderboardTitle,
  #leaderboardContainer {
    display: none;
  }
  #herosReading {
    background-image: url("../assets/img/hulk.jpg");
    background-position: center;
  }
}
</style>