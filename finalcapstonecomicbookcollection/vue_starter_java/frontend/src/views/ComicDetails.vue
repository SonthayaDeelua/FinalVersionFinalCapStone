<template>
  <div class="width">
    <header>
      <ul class="nav">
        <li>
          <router-link
            :to="{ name: 'user', params: {username: viewer.username}}"
          >{{viewer.username}}</router-link>
        </li>
      </ul>
      <ul class="nav-right">
        <li v-show="loggedIn">
          <router-link :to="{ name: 'logoff' }">Logoff</router-link>
        </li>
        <li>
          <router-link :to="{ name: 'login-home' }">Home</router-link>
        </li>
      </ul>
    </header>
    <br />

  <div class="background">
    <br />

    <div id="main" class="comic" v-if="edit===false">
      <img class="img" v-bind:src="comic.image" />

      <div class="detail">
        <h1>{{comic.title}}</h1>
        <p>
          <span>Issue Title :&nbsp; {{comic.issueTitle}}</span>
        </p>

        <p>
          <span>Issue Number #&nbsp;{{comic.issueNumber}}</span>
        </p>

        <p>
          <span>Published By :&nbsp; {{comic.publisher}}</span>
        </p>

        <p>
          <span>Published Date :&nbsp;{{comic.publishDate}}</span>
        </p>

        <div>
          <p class="description">{{comic.description}}</p>
        </div>
        
       
      </div>
    </div>
    <button v-on:click="goBack" id="goBackButton">Go Back</button>
    <button id="detailsEditButton" v-on:click="setEdit" v-if="edit === false && user.username === viewer.username">Edit Comic</button>
    <div v-if="edit === true">
      <form class="edit-form">
        <label>Title:</label>
        <input v-model="comic.title" />
        <label>Issue Name:</label>
        <input v-model="comic.issueTitle" />
        <label>Issue Number:</label>
        <input v-model="comic.issueNumber" />
        <label>Publisher:</label>
        <input v-model="comic.publisher" />
        <label>Publish Date:</label>
        <input v-model="comic.publishDate" type="date"/>
        <label>Description:</label>
        <input v-model="comic.description" />
        <label>Hero:</label>
        <input v-model="comic.hero" />
        <label>Image:</label>
        <input v-model="comic.image" placeholder="Reference image url" />
        <button v-on:click="editComic(comic.comicId)">Save Comic</button>
        <button v-on:click="cancel">Cancel</button>
        <button v-on:click="deleteComic(comic.comicId)">Delete Comic</button>
      </form>
    </div>
      </div>

  </div>
</template> 

<script>
import authLib from "@/auth";

export default {
  data() {
    return {
      edit: false,
      comic: {},
      user: {},
      viewer: {}
    };
  },

  methods: {
    getComic(comicId) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/comic/${comicId}`,
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

        .then(comic => {
          this.comic = comic;
        })

        .catch(err => {
          console.error(err);
        });
    },
    setEdit() {
      this.edit = true;
    },
    cancel() {
      this.edit = false;
    },
    editComic(comicId) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/comic/${comicId}`,
        {
          method: "PUT",
          headers: {
            Authorization: "Bearer " + authLib.getToken(),
            Accept: "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(this.comic)
        }
      ).catch(err => {
        console.error(err);
      });
    },
    deleteComic(comicId) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/comic/${comicId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + authLib.getToken(),
            Accept: "application/json",
            "Content-Type": "application/json"
          }
        }
      ).catch(err => {
        console.error(err);
      });
      this.$router.push({
        name: "user",
        params: {
          username: this.user.username        
          }
      });
    },
    getViewer(username) {
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
          this.viewer = user;
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
          this.user = user;
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
    this.getUserByUsername(this.$route.params.username);
    this.getViewer(logInUser.sub);
    this.getComic(this.$route.params.comicId);
  }
};
</script> 

 
 

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Bangers&display=swap");
.edit-form {
    width: 300px;
    margin: 0px auto;
    height: 600px;
}
.background {
  background-image: url("../assets/img/comicDetail.jpg");
  background-size: cover;
  height: auto;
  border-radius: 20px;
  min-height: 680px;
  width: 100%;
  background-position: center;
  margin-bottom: 100px;
}
.add {
  background: #00e5ee;
  height: 35px;
  padding-top: 50px;
  color: #ffffff;
  border-radius: 0 0 10px 10px;
  margin-top: 105px;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
}

.add>p {
  margin: -25px;
  font-family: "Bangers", cursive;
}
.add:hover {
  cursor: pointer;
}
.comic {
  display: flex;
  flex-direction: row;
}

.img {
  width: 300px;
  height: 30%;
  border-color: yellow;
  border-width: 15px;
  border-style: double;
  margin: 30px 0 5px 30px;
}

.width {
    width: 100%;
}

.detail {
  width: 100%;
  margin: 0px auto 30px auto;
  font-size: 100%;
  font-weight: 100;
  text-align: left;
  line-height: 2.5em;
  -webkit-text-stroke: 1.5px black;
  -webkit-text-fill-color: white;
  padding: 0 20px;
}

.detail > p {
  font-size: 28px;
}

.description {
  font-size: 20px;
  font-weight: 560;
  line-height: 1.5em;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
}

.detail > h1 {
  font-size: 80px;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 25px 0;
  text-align: left;
  line-height: 70px;
}
  #detailsEditButton {
  font-family: 'Bangers', cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(#b61cf3, #f5f116);
  border-style: none;
  width: 160px;
  height: 45px;
  transition: transform .3s;
   -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 0 0 20px 0;
  
}
#goBackButton {
  font-family: 'Bangers', cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(#b61cf3, #f5f116);
  border-style: none;
  width: 160px;
  height: 45px;
  transition: transform .3s;
   -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 0 30px 20px 0;
}
#detailsEditButton:hover {
  transform: scale(1.1); 
}
#goBackButton:hover {
  transform: scale(1.1); 
}
@media (max-width: 800px) {
  .comic {
    display: block;
    margin: auto;
  }
  .background {
    padding-left: 0;
    margin: auto;
    height: auto;
  }
  .detail {
    text-align: center;
    padding: 0;
  }
  .detail > h1 {
    text-align: center;
  }
  .img {
    margin: 0;
  }
    #detailsEditButton {
  font-family: 'Bangers', cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(135deg, #f31cf3, #f5f116);
  border-style: none;
  width: 160px;
  height: 45px;
  transition: transform .3s;
   -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 0 0 20px 0;
  
}
#detailsEditButton:hover {
  transform: scale(1.1); 
}

}
@media (min-width: 1450px) {
  .comic {
    width: 65%;
    margin: auto;
  }
}
</style> 