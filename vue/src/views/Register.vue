<template>
  <b-container style="text-align: left; padding-top: 60px;">
    <h1>Register new account:</h1>
    <b-form>
      <b-form-group label="Username:">
        <b-form-input v-model="form.username"/>
      </b-form-group>
      <b-form-group label="Email:">
        <b-form-input v-model="form.email"/>
      </b-form-group>
      <b-form-group label="Password:">
        <b-form-input v-model="form.password" type="password"/>
      </b-form-group>
      <b-form-group label="Confirm Password:">
        <b-form-input v-model="form.passwordConfirm" :state="validConfirmPassword" type="password"/>
        <b-form-invalid-feedback>Password is not the same</b-form-invalid-feedback>
      </b-form-group>
      <b-btn @click="submit">Register</b-btn>
    </b-form>
  </b-container>
</template>

<script>
import api from '@/api'
import _ from 'lodash'

export default {
  name: 'Register',
  data() {
    return {
      form: {
        username: '',
        password: '',
        passwordConfirm: '',
        email: '',
      },
    }
  },
  computed: {
    validConfirmPassword() {
      return this.form.passwordConfirm === this.form.password
    },
  },
  methods: {
    submit() {
      api.post('/account/register', _.omit(this.form, ['passwordConfirm'])).then(() => {
        this.$router.push('/login')
      })
    },
  },
}
</script>

<style scoped>

</style>