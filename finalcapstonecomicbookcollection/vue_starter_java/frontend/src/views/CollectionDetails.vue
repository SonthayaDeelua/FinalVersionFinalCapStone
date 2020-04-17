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

    <div id="collectionDetail" v-if="addComic === false && edit === false">
      <div class="name">
        <h1>{{collection.name}}</h1>
      </div>
      <div class="collection-container">
        <br />
        <br />
        <p>{{collection.description}}</p>
        <div>
          <span>
            Collection By
            <router-link :to="{ name: 'user', params: {username: user.username}}">{{user.username}}</router-link>
          </span>
        </div>
        
        <div>
          <button
            v-if="user.username === viewer.username && addComic === false"
            v-on:click="setAddComic"
            id="addComicButton"
          >Add Comic</button>
        </div>
        <button id="facebookShare"><a id="facebookShare2" v-bind:href ="facebookLink"><img id="fbImage" src="../assets/img/fb.svg" />
        Share on Facebook</a>
        </button><br/>
        <div v-for="comic in comics" :key="comic.comicId" class="collection-card">
          <router-link
            :to="{name: 'comic-details', params:{username:user.username, collectionId: collection.collectionId, comicId: comic.comicId}}"
          >
            <img v-bind:src="comic.image" class="collection-image" />
          </router-link>
          <h2>{{comic.title}}</h2>
          <p>Issue #{{comic.issueNumber}}</p>
          <p>{{comic.issueTitle}}</p>
          <router-link
            :to="{name: 'comic-details', params:{username:user.username, collectionId: collection.collectionId, comicId: comic.comicId}}"
          >Comic Details</router-link>
          <br />
          <br />
        </div>
          <div
            v-if="user.username === viewer.username && addComic === false"
            v-on:click="setAddComic"
            class="add"
          ><p>Add Comic</p></div>
      </div>

      <p v-if="collection.private">Collection is set to private</p>
      <p v-if="!collection.private">Collection is set to public</p>

      <div v-if="edit === false && user.username === viewer.username">
        <button id="editCollectionButton" v-on:click="setEdit">Edit Collection</button>
      </div>
    </div>

    <div v-if="edit === true">
      <form class="edit-form">
        Name:
        <input v-model="collection.name" />
        Description:
        <input v-model="collection.description" />
        Private?
        <select v-model="collection.private">
          <option value="true" selected>True</option>
          <option value="false">False</option>
        </select>
        <button id="editCollectionFormButton" v-on:click="editCollection(collectionId)">Save</button>
        <button id="editCollectionFormButton" v-on:click="cancelEdit">Cancel</button>
        <button id="editCollectionFormButton" v-on:click="deleteCollection(collectionId)">Delete</button>
      </form>
    </div>

    <br />
    <br />

    <div id="addComicForm" v-if="addComic === true">
      <form id="comic-form">
        <p>
          <router-link
            :to="{ name: 'comicSearch', params: {username: user.username,
          collectionId:collection.collectionId} }"
          >
            <button id="addComicButton">Search for Comic</button>
          </router-link>
        </p>
        <label>Title:</label>
        <input v-model="comicToAdd.title" />
        <label>Issue Name:</label>
        <input v-model="comicToAdd.issueTitle" />
        <label>Issue Number:</label>
        <input v-model="comicToAdd.issueNumber" />
        <label>Publisher:</label>
        <input v-model="comicToAdd.publisher" />
        <label>Publish Date:</label>
        <input type="date" v-model="comicToAdd.publishDate" placeholder="mm/dd/yyyy" />
        <label>Description:</label>
        <input v-model="comicToAdd.description" />
        <label>Hero:</label>
        <input v-model="comicToAdd.hero" />
        <label>Image:</label>
        <input v-model="comicToAdd.image" placeholder="Reference image url" />
        <button id="addComicButton" v-on:click="saveComic">Add Comic</button>
        <button id="addComicButton" v-on:click="cancel">Cancel</button>
      </form>
    </div>
  </div>
</template>

<script>
import authLib from "@/auth";
export default {
  data() {
    return {
      addComic: false,
      edit: false,
      user: {},
      viewer: {},
      comics: [],
      collectionId: "",
      collection: {},
      comicToAdd: {
        title: "",
        image: "",
        issueTitle: "",
        issueNumber: "",
        publisher: "",
        publishDate: "",
        description: "",
        hero: ""
      },
      facebookLink: `https://www.facebook.com/sharer/sharer.php?u=http://localhost:8081${this.$route.fullPath}`
    };
  },
  methods: {
    setFile(event) {
      this.comicToAdd.imageUrl = event.target.files[0];
    },
    setAddComic() {
      this.addComic = true;
    },
    cancel() {
      this.addComic = false;
    },
    setEdit() {
      this.edit = true;
    },
    cancelEdit() {
      this.edit = false;
    },
    editCollection(collectionId) {
      console.log("here");
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/collection/${collectionId}`,
        {
          method: "PUT",
          headers: {
            Authorization: "Bearer " + authLib.getToken(),
            Accept: "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(this.collection)
        }
      ).catch(err => {
        console.error(err);
      });
    },
    deleteCollection(collectionId) {
      console.log("here");
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/collection/${collectionId}`,
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
    getComics(collectionId) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/collection/${collectionId}`,
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
        .then(comics => {
          this.comics = comics;
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
    getCollectionInfo(collectionId) {
      fetch(
        `http://localhost:8080/AuthenticationApplication/api/collection/${collectionId}/single`,
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
        .then(collection => {
          this.collection = collection;
        })
        .catch(err => {
          console.error(err);
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
              username: this.user.username,
              collection: this.collectionId
            }
          })
        )
        .catch(err => {
          console.error(err);
        });
    }
  },
  computed: {
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
    const logInUser = authLib.getUser();
    this.getUserByUsername(this.$route.params.username);
    this.getViewer(logInUser.sub);
    this.collectionId = this.$route.params.collectionId;
    this.getComics(this.$route.params.collectionId);
    this.getCollectionInfo(this.$route.params.collectionId);
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Bangers&display=swap");
a {
  text-decoration: none;
}
.edit-form {
  width: 360px;
  margin: 50px auto;
  height: auto;
  padding: 10px;
}
#mainCollectionDetail {
  width: 100%;
}
#collectionDetail {
  font-family: "Bangers", cursive;
  display: flex;
  flex-direction: column;
}
#collectionDetail > h1 {
  margin: 20px 0 -10px 0;
}
#input-section {
  background-color: lightgray;
  margin: 20px;
  padding: 20px;
  width: 20%;
}
#addComicForm {
  display: flex;
  justify-content: center;
}
#comic-form {
  margin: auto;
  width: 180px;
  height: auto;
  text-align: left;
  background-color: lightgray;
  border-radius: 10px;
  border-style: outset;
  border-color: rgb(23, 200, 245);
  border-width: 10px;
  justify-content: center;
}

#addComicButton {
  font-family: "Bangers", cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(135deg, #59b5cc, #00e5ee);
  border-style: none;
  width: 180px;
  height: 35px;
  margin: 10px 0 10px 0;

  transition: transform 0.3s;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
}
#addComicButton:hover {
  transform: scale(1.1);
}
#editCollectionFormButton {
  font-family: "Bangers", cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(135deg, #59b5cc, #00e5ee);
  border-style: none;
  width: 360px;
  height: 40px;
  transition: transform 0.3s;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 30px 0 0 0;
}
#editCollectionFormButton:hover {
  transform: scale(1.1);
}

#editCollectionButton {
  font-family: "Bangers", cursive;
  font-size: 24px;
  border-radius: 8px;
  background: linear-gradient(135deg, #59b5cc, #00e5ee);
  border-style: none;
  width: 160px;
  height: 45px;
  transition: transform 0.3s;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  padding: 10px;
}
#editCollectionButton:hover {
  transform: scale(1.1);
}
#facebookShare {
  font-family: "Bangers", cursive;
  display:inline-block;
  background: white;
  width: 180px;
  height: 25px;
  padding: 0;
  border: none;
  vertical-align: middle;
}

#facebookShare2 {
  color: #3b5998;
  font-size: 18px;
  height: 20px;
  width: 120px;
  vertical-align: middle;
}
#fbImage {
  height: 19px;
  width: 20px;
  vertical-align: middle;
}
input {
  margin-bottom: 14px;
  font-family: "Bangers", cursive;
  font-size: 20px;
}
.name {
  width: 65%;
  margin: 50px auto -157px auto;
  height: 140px;
  background: #00e5ee;
  border-radius: 56px;
  vertical-align: middle;
  padding: 10px;
}
.name > h1 {
  margin-top: 20px;
  font-size: 46px;
  color: white;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
}
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

p {
  margin: 0;
}

.collection-container {
  width: 80%;
  background: #ffffff;
  box-shadow: 0px 5px 10px #dcdcdc;
  margin: 90px auto 50px auto;
  border-radius: 10px;
}
.add {
  background: #00e5ee;
  height: 50px;
  padding-top: 20px;
  color: #ffffff;
  border-radius: 0 0 10px 10px;
  margin-top: 20px;
  -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
}
.add:hover {
  cursor: pointer;
}
.width {
  width: 100%;
}
::-webkit-input-placeholder {
  font-family: "Bangers", cursive;
}
</style>