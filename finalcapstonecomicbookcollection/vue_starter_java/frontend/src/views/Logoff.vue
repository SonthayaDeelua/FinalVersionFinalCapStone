<template>
<div id="logoff">
   <header>
      <ul class="nav-right">
        <li><router-link :to="{ name: 'logoff-home' }" >Home</router-link></li>
      </ul>
    </header>
<div >
  <p>
You have successfully logged out.
  </p>
<img id="logoffImage" src='../assets/img/logoff.jpg' />
</div>
  </div>

</template>

<script>
import authLib from '@/auth';
export default {
created() {
    authLib.logout();
    }   
}
</script>

<style>
p {
  font-size: 30px;
}

#logoff {
  display: flex;
  flex-direction: column;
  width: 100%;
}
#logoffImage {
  width: 90%;
  height: auto;
}
</style>
