<template>
  <div id="main">
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
    <div id="confirmComic">
        <div class="image-container">
          <img v-bind:src="issues.image" class="img" />
        </div>
        <div class="description-container">
          <br><br>
          <h1 id="volume-name">{{issues.title}}</h1>
          <p id="issue-name">{{issues.issueTitle}}</p>
          <p id="issue-number">Issue #{{issues.issueNumber}}</p>
          <div class="description">
          <p id="issue-description">{{issues.description}}</p>
          </div>
          <p id="pub-date">Publish Date: {{issues.publishDate}}</p>
          <p id="publisher">Publisher: {{publisher.publisher}}</p>
          <div class="buttons">
          <button v-on:click="saveComic()" id="addComicButton2">Add comic</button>
          <button id="addComicButton2" v-on:click="goBack">Go Back</button>
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
      volume: [],
      issues: {},
      volumeNumber: "",
      publisher: {},
      username: "",
      collectionId: "",
      comicToAdd: {
        title: "",
        issueTitle: "",
        issueNumber: "",
        publisher: "",
        publishDate: "",
        description: "",
        image: ""
      }
    };
  },

  methods: {
    setComic() {
      this.comicToAdd.title = this.issues.title;
      this.comicToAdd.issueTitle = this.issues.issueTitle;
      this.comicToAdd.issueNumber = this.issues.issueNumber;
      this.comicToAdd.publisher = this.issues.publisher;
      this.comicToAdd.publishDate = this.issues.publishDate;
      this.comicToAdd.description = this.issues.description;
      this.comicToAdd.image = this.issues.image;
    },
    saveComic() {
      fetch(`http://localhost:8080/AuthenticationApplication/api/comic/save`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + authLib.getToken(),
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.comicToAdd)
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
      console.log(volumeNumber);
      console.log("grabbing publisher");

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
          console.log("aaaa");
          console.log(this.publisher);
        })

        .catch(err => {
          console.error(err);
        });
    },
    requestComic(volumeNumber, issueNumber) {
      //   console.log(issueNumber);
      //   console.log(volumeNumber);

      fetch(
        `http://localhost:8080/AuthenticationApplication/search/requestComic/${volumeNumber}/${issueNumber}`,
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
          this.issues = data;
          this.comicToAdd.title = this.issues.title;
          this.comicToAdd.issueTitle = this.issues.issueTitle;
          this.comicToAdd.issueNumber = this.issues.issueNumber;
          this.comicToAdd.publishDate = this.issues.publishDate;
          this.comicToAdd.description = this.issues.description;
          this.comicToAdd.image = this.issues.image;
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
    },
    goBack() {
      this.$router.back();
    }
  },
  created() {
    const logInUser = authLib.getUser();
    this.getUserByUsername(logInUser.sub);
    //this.volumeNumber = this.$route.params.volumeNumber;
    //this.viewVolume(this.volumeNumber);
    //this.issueNumber= this.$route.params.issueNumber
    this.requestComic(
      this.$route.params.volumeNumber,
      this.$route.params.issueNumber
    );
    this.getPublisher(this.$route.params.volumeNumber);
    this.collectionId = this.$route.params.collectionId;
    this.username = this.$route.params.username;
  }
};
</script>

<style>
#main {
  width: 100%;
}
.img {
  width: 350px;
  margin-top: 50px;
}
.image-container {
  width: 35%;
  height: auto;
  justify-content: center;
}
.description-container {
  width: 65%;
  justify-content: center;
}
.description {
  width: 90%;
  margin: auto;
}
#addComicButton2 {
  font-family: "Bangers", cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(135deg, #59b5cc, #00e5ee);
  border-style: none;
  width: 180px;
  height: 45px;
  transition: transform 0.3s;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 0 14px 20px 0;
}
#addComicButton2:hover {
  transform: scale(1.1);
}
#confirmComic {
  display: flex;
}
.buttons {
  margin: auto;
}
@media (max-width: 950px) {
  #confirmComic {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .image-container {
    width: 100%;
    height: auto;
    justify-content: center;
  }
  .description-container {
    width: 98%;
    justify-content: center;
  }
  .img {
    width: 350px;
    margin-top: 10px;
  }
}
</style>