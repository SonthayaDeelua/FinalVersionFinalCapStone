<template>
  <div id="app">
    
    <div class="content">
      <router-view />
    </div>
  </div>
</template>
<script>

export default {
   
   
}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Bangers&display=swap');
body {
  margin: 0;
}
a {
  text-decoration: none;
}
#app {
  text-align: center;
  width: 100%;
  font-family: 'Bangers', cursive;
}
header {
  height: 50px;
  width: cover;
  display: flex;
  flex-direction: row;
  background-color: rgb(247, 247, 247);
  font-family: 'Bangers', cursive;

}
.nav {
  padding:14px;
  margin:0;
  flex:auto;
  list-style-type: none;
  text-align: left;
}
.nav li {
  display: inline;
}
.nav li a, .nav li a:visited {
  text-decoration: none;
  color: rgb(56, 56, 146);
  text-transform: uppercase;
  font-size: 20px;
}
.nav li a:hover {
  text-decoration: underline;
}
.nav-right {
  flex: auto;
  list-style-type: none;
  padding: 14px;
  margin: 0;
  text-align: right;
}
.nav-right li {
  display: inline;
  padding-right: 10px;
  vertical-align: middle;
}
.nav-right li a, .nav-right li a:visited {
  text-decoration: none;
  color: rgb(56, 56, 146);
  text-transform: uppercase;
  font-size: 20px;
}
.nav-right li a:hover {
  text-decoration: underline;
}
.content {
  display: flex;
  justify-content: center;
  width: 100%;
}
::-webkit-input-placeholder {
font-family: 'Bangers', cursive;
}
</style>
