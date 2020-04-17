<template>
  <div id="searchResults">
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
      <p>
        <label id="searchLabel">Issue Number:</label>
        <input id="searchComicInput" v-model="issueNum" />
        <br />
        <router-link :to="{ name: 'comicResults', params:{volumeNumber:volumeNumber, issueNumber:issueNum}}">
        <button id="searchComicButton">Search</button>
        </router-link>
      </p>
      <div class="collection-card" v-for="comic in comics" :key="comic.issueNumber">

          <img v-bind:src="comic.image" class="collection-image"  v-on:click="saveComic(comic.title,
        comic.issueTitle,
        comic.issueNumber,
        publisher.publisher,
        comic.publishDate,
        comic.description,
        comic.image)"/>
     
        <h2 id="volume-name">{{comic.title}}</h2>
        <p id="issue-name">{{comic.issueTitle}}</p>
        <p id="issue-number">Issue #{{comic.issueNumber}}</p>
        <p id="pub">Publisher: {{publisher.publisher}}</p>
        <p id="pub-date">Publish Date: {{comic.publishDate}}</p>
        <p class="add-comic" v-on:click="saveComic(comic.title,
        comic.issueTitle,
        comic.issueNumber,
        publisher.publisher,
        comic.publishDate,
        comic.description,
        comic.image)">Add Comic</p>
  
        <br />
        <br />
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
      comics: [],
      volumeNumber: "",
      username: "",
      collectionId: "",
      publisher: "",
      issueNum:""
    };
  },

  methods: {
    saveComic(
      titleX,
      issueTitleX,
      issueNumberX,
      publisherX,
      publishDateX,
      descriptionX,
      imageX
    ) {
      let comicToAdd = {
        title: titleX,
        issueTitle: issueTitleX,
        issueNumber: issueNumberX,
        publisher: publisherX,
        publishDate: publishDateX,
        description: descriptionX,
        image: imageX
      }
      fetch(`http://localhost:8080/AuthenticationApplication/api/comic/save`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + authLib.getToken(),
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(comicToAdd)
      })
        .then(
          this.$router.push({
            name: "add-comic",
            params: {
              username: this.username,
              collection: this.collectionId
            }
          })
        )
        .catch(err => {
          console.error(err);
        });
    },
    getPublisher(volumeNumber) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/search/getPub/${volumeNumber}`,
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
          this.publisher = data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    viewVolume(volumeNumber) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/search/viewVolume/${volumeNumber}`,
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
    this.volumeNumber = this.$route.params.volumeNumber;
    this.viewVolume(this.volumeNumber);
    this.collectionId = this.$route.params.collectionId;
    this.username = this.$route.params.username;
    this.getPublisher(this.$route.params.volumeNumber);
  }
};
</script>

<style>
.collection-image {
  width: 200px;
}
.collection-image:hover {
  cursor: pointer;
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
.add-comic {
  font-size: 14px;
  color:blue;
}
.add-comic:hover{
  cursor: pointer;
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
#searchResults {
  width: 100%;
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