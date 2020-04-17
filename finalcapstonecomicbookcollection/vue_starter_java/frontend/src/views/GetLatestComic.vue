<template>
  <div id="main">
    <header>
      <ul class="nav">
        <li>
          <router-link :to="{ name: 'user', params: {username: user.username}}">{{user.username}}</router-link>
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
        <img v-bind:src="latestComic.image" class="img" />
      </div>
      <div class="description-container">
        <br />
        <br />
        <h1>{{latestComic.title}}</h1>
        <p>{{latestComic.issueTitle}}</p>
        <p>Issue #{{latestComic.issueNumber}}</p>
        <div class="description">
          <p>{{latestComic.description}}</p>
        </div>
        <p>Publisher: {{latestComic.publisher}}</p>
        <p>Publish Date: {{latestComic.publishDate}}</p>
        <div class="buttons">
          <button
            id="addComicButton2"
            v-on:click="addComicToColletion(user.username,collectionId,latestComic.comicId)"
          >Confirm</button>

          <router-link
            :to="{name: 'collection', params:{username: user.username, collectionId: collectionId}}"
          >
            <button id="addComicButton2" v-on:click="goBack">Cancel</button>
          </router-link>
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
      user: {},
      comics: [],
      latestComic: {},
      collectionId: ""
    };
  },
  methods: {
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
          this.user = user;
        })
        .catch(err => {
          console.error(err);
        });
    },
    getLatestComic() {
      fetch("http://localhost:8080/AuthenticationApplication/api/comic/new", {
        headers: {
          Authorization: "Bearer " + authLib.getToken(),
          Accept: "application/json",
          "Content-Type": "application/json"
        }
      })
        .then(response => {
          return response.json();
        })
        .then(comic => {
          this.latestComic = comic;
        })
        .catch(err => {
          console.error(err);
        });
    },
    addComicToColletion(username, collectionId, comicId) {
      console.log(username + " " + collectionId + " " + comicId);
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/users/${username}/collection/${collectionId}/${comicId}/save`,
        {
          method: "POST",
          headers: {
            Authorization: "Bearer " + authLib.getToken(),
            Accept: "application/json",
            "Content-Type": "application/json"
          }
          //body: JSON.stringify(this.user)
        }
      )
        .then(() => {
          this.$router.push({
            name: "collection",
            params: { username: username, collectionId: collectionId }
          });
        })
        .catch(err => {
          console.error(err + " problem adding collection!");
        });
    },
    goBack() {
      this.$router.back();
    }
  },
  created() {
    this.getUserByUsername(this.$route.params.username);
    this.collectionId = this.$route.params.collectionId;
    this.getLatestComic();
  }
};
</script>

<style scoped>
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