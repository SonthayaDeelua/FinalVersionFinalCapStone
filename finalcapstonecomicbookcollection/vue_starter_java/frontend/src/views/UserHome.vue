<template>
<div id="userHome">
    <header>
    <ul class="nav">
        <li><router-link :to="{ name: 'user', params: {username: viewer.username}}" >{{viewer.username}}</router-link></li>
      </ul>
      <ul class="nav-right">
        <li v-show="showLogoffLink" ><router-link :to="{ name: 'logoff' }" >Logoff</router-link></li>
        <li><router-link :to="{ name: 'login-home' }" >Home</router-link></li>
      </ul>
    </header>
    <div class='userCollectionHome'>
        <div class="name">
            <h1 id="userHomeUsername">{{user.username}}</h1>
        </div>
        <div v-if="user.username === viewer.username" class="collections-container">
        <br>
        <p>Collections</p>
        <br>
            <div v-for='collection in user.collections' :key='collection.id' class="collection-card">
                <div v-if="collection.comicsInCollection.length == 0">
                    <h2 class="padding">Me and the boys when you have no comics in your collection</h2>
                    <img class= "boys-image" src="../assets/img/theboys.jpg">
                    <br>
                </div>
                <div v-if="collection.comicsInCollection.length > 0">
                    <router-link :to="{name: 'collection', params: {username: username, collectionId: collection.collectionId}}"> 
                    <img class= "collection-image" v-bind:src="collection.comicsInCollection[0].image">
                    </router-link>
                </div>
                <h2>{{collection.name}}</h2>
                <router-link :to="{name: 'collection', params: {username: username, collectionId: collection.collectionId}}" tag="a"> 
                        View Details
                </router-link>
                <br><br>
                <div v-if="collection.private" class="private">
                Private
                </div>
                <div v-if="!collection.private" class="public">
                Public
                </div>
            </div>
        <router-link :to="{name: 'add-collection'}"> 
            <div class="add"><p>Add Collection</p></div>
        </router-link>
        </div>

        <div v-if="user.username !== viewer.username" class="collections-container">
            <br>
            <p>Collections</p>
            <div v-for='collection in publicCollections' :key='collection.id' class="collection-card">
                <div v-if="collection.comicsInCollection.length == 0">
                    <h2 class="padding">Me and the boys when you have no comics in your collection</h2>
                    <img class= "boys-image" src="../assets/img/theboys.jpg">
                    <br>
                </div>
                <div v-if="collection.comicsInCollection.length > 0">
                    <img class= "collection-image" v-bind:src="collection.comicsInCollection[0].image">
                </div>
                <h2>{{collection.name}}</h2>
                <router-link :to="{name: 'collection', params: {username: username, collectionId: collection.collectionId}}" tag="a"> 
                        View Details
                </router-link>
                <br><br>
            </div>
        </div>

    </div>
</div>


</template>

<script>
import authLib from '@/auth';

export default {
data() { 
        return { 
            user : {},
            username: '',
            viewer: {},
            publicCollections: [],
            viewerIsUser: false
        }
    },
    methods: {
        setViewerIsUser() {
            if (this.user.id === this.viewer.id) {
                this.viewerIsUser = true;
            }
        },
        getUserByUsername(username){
            fetch(`http://localhost:8080/AuthenticationApplication/api/users/${username}`,
                {
                    headers: {
                    Authorization: "Bearer " + authLib.getToken(),
                    Accept: "application/json",
                    "Content-Type": "application/json"
                    }
                }
            )
            .then (
                (response) => {
                    return response.json();
                }
            )
            .then (
                (newUser) => {
                    this.user = newUser;
                }
            )
            .catch (
                (err) => {
                    console.error(err);
                }
            )
        },
        getPublicCollections(username){
            fetch(`http://localhost:8080/AuthenticationApplication/api/user/${username}/collections`,
                {
                    headers: {
                    Authorization: "Bearer " + authLib.getToken(),
                    Accept: "application/json",
                    "Content-Type": "application/json"
                    }
                }
            )
            .then (
          (response) => {
            return response.json();
            }
            )
            .then (
            (collections) => {
            this.publicCollections = collections;
            }
            )
            .catch (
            (err) => {
            console.error(err);
            }
            )
            },
        getViewer(username){
            fetch(`http://localhost:8080/AuthenticationApplication/api/users/${username}`,
                {   
                    headers: {
                    Authorization: "Bearer " + authLib.getToken(),
                    Accept: "application/json",
                    "Content-Type": "application/json"
                    }
                }
            )
            .then (
                (response) => {
                    return response.json();
                }
            )
            .then (
                (user) => {
                    this.viewer = user;
                }
            )
            .catch (
                (err) => {
                    console.error(err);
                }
            )
        }
    },
computed: {
    showLogoffLink() {
      let route = true;
      if (this.$route.name === 'login') {
        route=false;
      }
      if (this.$route.name === 'logoff') {
        route=false;
      }
      if (this.$route.name === 'register') {
        route=false;
      }
      return route;
    }
  }, 
  created() {
    const logInUser = authLib.getUser();
    this.getUserByUsername(this.$route.params.username);
    this.getViewer(logInUser.sub);
    this.username = this.$route.params.username;
    this.setViewerIsUser();
    this.getPublicCollections(this.$route.params.username);
  }
}
</script>

<style scoped>
    a {
        text-decoration:none;
    }
    #userHome{
        font-family: 'Bangers', cursive;
        width: 100%;
    }
    
    #input-section {
        background-color: lightgray;
        margin: 20px;
        padding: 20px;
        width: 20%;
    }
    .public, .private {
        width:100%;
        height: 23px;
        color: white;
        border-radius: 0 0 5px 5px;
        padding: 3px 0 5px 0;
    }
    .public {
        background: #00E5EE;
        -webkit-text-stroke: 1px black;
        -webkit-text-fill-color: white;
        font-size: 24px;
    }
    .private {
        background: red;
        -webkit-text-stroke: 1px black;
        -webkit-text-fill-color: white;
        font-size: 24px;
    }
    .comic-form {
        margin: auto;
        width: 160px;
        text-align: left;
    }
    input {
        margin-bottom: 15px;
    }
    .collection-image {
        width: 200px;
    }
    .boys-image {
        width: 200px;
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
    .add>p {
        margin: 0;
    }
    .collection-card {
        box-shadow: 3px 3px 19px #dcdcdc;
        border-radius: 10px;
        margin: 0px 35px 50px 35px;
        width: 200px;
        display: inline-block;
    }
    .collection-card>p{
        font-size: 18px;
        margin: 0;
    }
    .collection-card>h2{
        font-size: 28px;
        margin-bottom: 5px;
    }
    .padding {
        padding: 10px
    }
    .collections-container {
        width: 80%;
        background: #ffffff;
        box-shadow: 0px 5px 10px #dcdcdc;
        margin: 90px auto;
        border-radius: 10px;
    }
    .name {
        width: 65%;
        margin: 60px auto -157px auto;
        height: 140px;
        background: #00e5ee;
        border-radius: 56px;
        vertical-align: middle;
        padding: 10px;
    }
    .name>h1{
        margin-top: 20px;
    }
    #userHomeUsername {
        font-size: 46px;
        color: white;
        -webkit-text-stroke: 1px black;
        -webkit-text-fill-color: white;
    }
</style>


