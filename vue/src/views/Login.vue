<template>
  <b-container style="text-align: left; padding-top: 60px;">
    <b-form>
      <b-form-group label="Username:">
        <b-form-input v-model="form.username"/>
      </b-form-group>
      <b-form-group label="Password:">
        <b-form-input v-model="form.password" type="password"/>
      </b-form-group>
      <b-btn @click="submit">Log in</b-btn>
    </b-form>
  </b-container>
</template>

<script>
import api from '@/api'

export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
    }
  },
  methods: {
    submit() {
      api.post('/account/login', this.form).then(() => {
        this.$router.push('/')
      })
    },
  },
}
</script>

<style scoped>

</style>