<template>
  <div id="addCollection">
      <header>
       <ul class="nav">
        <li><router-link :to="{ name: 'user', params: {username: user.username}}" >{{user.username}}</router-link></li>
      </ul>
      <ul class="nav-right">
        <li><router-link :to="{ name: 'logoff' }" >Logoff</router-link></li>
        <li><router-link :to="{ name: 'login-home' }" >Home</router-link></li>
      </ul>
    </header>
     <div id='addCollection-input-section'> 
        <form>
            Name: <input id='collection-name' v-model='collection.name' /> 
            Description:  <input id='collection-description' v-model='collection.description' />           
            Private? <select id='collection-private' v-model='collection.private'>
                <option value='true' selected>True</option>    
                <option value='false'>False</option>     
            </select>
            <button v-bind='user' id='collection-add-btn' v-on:click='saveCollection(user.username)'>Add Collection!</button>
        </form>
      </div>
  </div>
</template>

<script>
import authLib from '@/auth';

export default {

    data() {
        return { 
            user: {},
            collection: {
                userId: '',
                name: '',  
                description: '',
                private: false  
            }
        };
    },
    methods: {
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
                    this.collection.userId = this.user.id;
                }
            )
            .catch (
                (err) => {
                    console.error(err);
                }
            )
        },
        saveCollection(username) {
            fetch(`http://localhost:8080/AuthenticationApplication/api/users/${username}/collection`,
                {
                method: 'POST',
                headers: {
                    Authorization: "Bearer " + authLib.getToken(),
                    Accept: "application/json",
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(this.collection)
            })
            .then (
                () => {
                    window.alert('Collection added!');
                }
            )
            .then (
                () => {

                } 
            )
            .catch(
                (err) => {console.error(err + ' problem adding collection!'); }
            )
            this.$router.push({name: 'user', params: {username: username}});
        }
    },
    created() {
        const logInUser = authLib.getUser();
        this.getUserByUsername(logInUser.sub);
    }
}
</script>

<style>
#addCollection {
    width:100%;
    display: flex;
    flex-direction: column;
    text-align: center;
}
#addCollection-input-section {
    background-image: url('../assets/img/add2.jpg');
    background-size: cover;
    height: 700px;
    width: cover;
    justify-content: center;
    display: flex;
    flex:auto;

}
form {
    font-family: 'Bangers', cursive;
    display: flex;
    flex-direction: column;
    justify-content: center;
    font-size: 24px;
    height:260px;
    margin: 50px 0 0 0;
    background-color: rgba(255, 255, 255, 0.7);
    padding: 0 10px 0 10px;
    
}

#collection-add-btn {
  font-family: 'Bangers', cursive;
  font-size: 24px;
  border-radius: 8px;
  background: #9a06fd;
  border-style: none;
  width: 193px;
  height: 45px;
  transition: transform .3s;
   -webkit-text-stroke: 1px black;
  -webkit-text-fill-color: white;
  margin: 15px 0 0 0;
  
}
#collection-add-btn:hover {
  transform: scale(1.1); 
}
input {
    margin-bottom: 8px;
    font-family: 'Bangers', cursive;
    font-size: 20px;
}
option {
    font-family: 'Bangers', cursive;
}
select {
    font-family: 'Bangers', cursive;
    font-size: 18px;
}

</style>