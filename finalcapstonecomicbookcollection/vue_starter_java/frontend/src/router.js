import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import LogInHome from './views/LogInHome.vue'
import LogOutHome from './views/LogOutHome.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import AddCollection from './views/AddCollection.vue'
import CollectionDetails from './views/CollectionDetails.vue'
import UserHome from './views/UserHome.vue'
import GetLatestComic from './views/GetLatestComic.vue'
import Logoff from './views/Logoff.vue'
import SearchComic from './views/SearchComic.vue'
import TestPage from './views/TestPage.vue'
import SearchResults from './views/SearchResults.vue'
import ComicDetails from './views/ComicDetails.vue'
import ComicResults from './views/ComicResults.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'logoff-home',
      component: LogOutHome,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/home',
      name: 'login-home',
      component: LogInHome,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/add-collection",
      name: "add-collection",
      component: AddCollection,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/user/:username/collection/:collectionId",
      name: "collection",
      component: CollectionDetails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/user/:username",
      name: "user",
      component: UserHome,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/user/:username/collection/:collectionId/add",
      name: "add-comic",
      component: GetLatestComic,
      meta: {
        requiresAuth: true
      },
    },
    {
        path: "/logoff",
        name: "logoff",
        component: Logoff,
        meta: {
          requiresAuth: false
        }
    },
    {
      path: "/user/:username/collection/:collectionId/comicSearch",
      name: "comicSearch",
      component: SearchComic,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/user/:username/collection/:collectionId/searchResults/:volumeNumber",
      name: "searchResults",
      component: SearchResults,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/user/:username/collection/:collectionId/searchResults/comicResults/:volumeNumber/issue/:issueNumber",
      name: "comicResults",
      component: ComicResults,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/user/:username/collection/:collectionId/comic/:comicId",
      name: "comic-details",
      component: ComicDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/test",
      name: "test",
      component: TestPage,
      meta: {
        requiresAuth: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const user = auth.getUser();

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && !user) {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
