<template>
  <b-navbar type="dark" variant="info">
    <b-container>
      <b-navbar-brand to="/">Home</b-navbar-brand>
      <b-nav class="mb-auto">
        <b-nav-item v-if="!username" to="/login">Log in</b-nav-item>
        <b-nav-item v-if="!username" to="/register">Register</b-nav-item>
        <b-nav-item v-if="username" @click="logout">Logout</b-nav-item>
        <b-nav-text v-if="username">{{ username }}</b-nav-text>
        <b-nav-item v-if="username" to ="/subscriptions">Subscriptions</b-nav-item>
      </b-nav>
    </b-container>
  </b-navbar>
</template>

<script>
import api from '@/api'

export default {
  name: 'MyMenu',
  data() {
    return {
      username: null,
    }
  },
  methods: {
    getUsername() {
      api.get('/account/username').then(res => {
        console.log(res)
        if (Math.floor(res.status / 100) !== 2) {
          throw res
        }
        this.username = res.data
      }).catch(() => {
        this.username = null
      })
    },
    logout() {
      api.post('/account/logout').then(() => {
        this.getUsername()
      })
    },
  },
  mounted() {
    this.getUsername()
  },
}
</script>

<style scoped>

</style>