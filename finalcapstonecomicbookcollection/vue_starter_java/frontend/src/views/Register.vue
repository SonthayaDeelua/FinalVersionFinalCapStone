<template>
<div>
  <header>
    <ul class="nav">
       
      </ul>
      <ul class="nav-right">
        <li><router-link :to="{ name: 'logoff-home' }" >Home</router-link></li>
      </ul>
    </header>
  <div id="register" class="text-center">
    <form id="form-register" @submit.prevent="register">
      <h1 id="regHeader">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        There were problems registering this user.
      </div>
      <label for="username" class="regLabel">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />    
      <label for="password" class="regLabel">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <p class="email">Enter an email to sign up for a premium account </p>
      <p class="email">(add unlimited comics to your collection)</p>
      <p class="email">*User agrees to receive the latest comic news and offers</p>
      <input type="text" 
        id="email"
        class="form-control"
        placeholder="batman@example.com"
        v-model="user.email" v-on:change="setPremium()">
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
            <router-link class="backToLogin" :to="{ name: 'login' }">
        Have an account?!?
      </router-link>
    </form>
     
  </div>
</div>
 
</template>

<script>
export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
        email: null
      },
      registrationErrors: false,
      isPremium: false
    };
  },
  methods: {
    setPremium() {
      if (this.user.email === null) {
        this.user.role = 'user';
      } else {
        this.user.role = 'premium';
      }
    },
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push({ path: '/login', query: { registration: 'success' } });
          } else {
            this.registrationErrors = true;
          }
        })

        .then((err) => console.error(err));
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Bangers&display=swap');
#register {
  font-family: 'Bangers', cursive;
  background-image: url('../assets/img/comicsMontage.jpg');
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
.email {
  margin: 0;
  font-size: 20px;
}
::-webkit-input-placeholder {
font-family: 'Bangers', cursive;
}
button {
font-family: 'Bangers', cursive;
font-size: 20px;
}
#form-register{
 background-color: rgba(255, 255, 255, 0.8);
 height: auto;
  grid-area: form;
}
.backToLogin {
  font-size: 30px;
}
#regHeader {
  margin: 5px;
  font-size: 40px;
  color: black;
}
.regLabel {
  font-size: 30px;
}
</style>
