<template>
<div>
   <header>
    <ul class="nav">
       
      </ul>
      <ul class="nav-right">
        <li><router-link :to="{ name: 'logoff-home' }" >Home</router-link></li>
      </ul>
    </header>
  <div id="login" class="text-center">
  
    <form id="form-signin" @submit.prevent="login">
      <h1 id="loginTitle">Please Sign In</h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <label for="username" class="sr-only">Username</label>
      
      <input
        type="text"
        id="username"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      
      <input
        type="password"
        id="password"
        placeholder="Password"
        v-model="user.password"
        required
      />
     
      <button type="submit">Sign in</button>
      <br>
      <router-link :to="{ name: 'register' }" class="signup">Need an account?!?</router-link>
    </form>
    <div>
    </div>
  </div>
  </div>
</template>

<script>
import auth from '../auth';

export default {
  name: 'login',
  components: {},
  data() {
    return {
      user: {
        username: '',
        password: '',
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/login`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            return response.text();
          } else {
            this.invalidCredentials = true;
          }
        })
        .then((token) => {
          if (token != undefined) {
            if (token.includes('"')) {
              token = token.replace(/"/g, '');
            }
            auth.saveToken(token);
            this.$router.push('/home');
          }
        })
        .catch((err) => console.error(err));
    },
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
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Bangers&display=swap');
#login {
  background-image: url('../assets/img/comicsMontage3.jpg');
  background-size: cover;
  height: 760px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas:
  ". . ."
  ". form ."
  ". . ."
  ". . .";
}
#form-signin {
  grid-area: form;
  font-family: 'Bangers', cursive;
  background-color: rgba(255, 255, 255, 0.7);
 height: auto;
  
}
.sr-only {
  font-size: 40px;
  -webkit-text-stroke: 1.8px black;
  -webkit-text-fill-color: white;
}
#loginTitle {
  font-family: 'Bangers', cursive;
  font-size: 50px;
  -webkit-text-stroke: 1.5px black;
  -webkit-text-fill-color: white;
  margin: 15px 0 15px 0;
}
.signup {
  font-size: 38px;
  
}
::-webkit-input-placeholder {
font-family: 'Bangers', cursive;
font-size: 18px;
}
button {
font-family: 'Bangers', cursive;
font-size: 20px;
}
#username {
  font-size: 20px;
  font-family: 'Bangers', cursive;
}
#password {
  font-size: 20px;
  font-family: 'Bangers', cursive;
}
</style>
