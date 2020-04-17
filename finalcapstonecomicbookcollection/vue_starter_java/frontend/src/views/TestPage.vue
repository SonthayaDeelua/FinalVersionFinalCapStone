<template>
  <div id='main'>
    <br>
    <div v-if="user.username === viewer.username && addComic === false">
      <button v-on:click='setAddComic'>Add Comic</button>
    </div>
    <div v-if="addComic === true">
        <label>Title: </label>
        <input v-model='comicToAdd.title'><br>
        <label>Issue Name: </label>
        <input v-model='comicToAdd.issueTitle'><br>
        <label>Issue Number: </label>
        <input v-model='comicToAdd.issueNumber'><br> 
        <label>Publisher: </label>
        <input v-model='comicToAdd.publisher'><br>
        <label>Publish Date: </label>
        <input v-model='comicToAdd.publishDate'><br>
        <label>Description: </label>
        <input v-model='comicToAdd.description'><br>
        <label>Hero: </label>
        <input v-model='comicToAdd.hero'><br>
        <label>Image Url: </label>
        <input v-model='comicToAdd.image'><br>
        <button v-on:click="saveComic">Add Comic</button>
    </div>
    <div>
        <label>Volume Title: </label>
        <input v-model='volumeSearch'><br>
        <button v-on:click='goToSearch'> </button>
    </div>
</div>
</template>

<script>
import authLib from '@/auth';

export default {

    data() {
        return { 
            addComic: false,
            user: {},
            viewer: {},
            comics: [],
            collectionId: '',
            comicToAdd: {
                title: '',
                image: 'blank.jpg',
                issueTitle: '',
                issueNumber: '',
                publisher: '',
                publishDate: '',
                description: '',
                hero: ''
            },
            volumeSearch:''
        };
    },
    methods: {
        setAddComic() {
            this.addComic = true;
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
                (user) => {
                    this.user = user;
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
        },
        saveComic() {
            fetch(`http://localhost:8080/AuthenticationApplication/api/comic/save`,
                {
                    method: 'POST',
                    headers: {
                    Authorization: "Bearer " + authLib.getToken(),
                    Accept: "application/json",
                    "Content-Type": "application/json"
                    },
                    body: JSON.stringify(this.comicToAdd)
                }
            )
            .then(
                this.$router.push({name: 'add-comic', params: {username: this.user.username, collection: this.collectionId}})
            )
            .catch (
                (err) => {
                    console.error(err);
                }
            )
        }
    },
    created() {
        const logInUser = authLib.getUser();
        this.getUserByUsername(this.$route.params.username);
        this.getViewer(logInUser.sub);
        this.collectionId = this.$route.params.collectionId;
    }
}
</script>

<style>
    #input-section {
        background-color: lightgray;
        margin: 20px;
        padding: 20px;
        width: 20%;
    }
</style>