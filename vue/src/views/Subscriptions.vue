<template>
  <b-container>
    <h3>Cities to subscribe:</h3>
    <b-row v-for="city in cities" v-bind:key="city.name">
        {{city}}
        <input v-model="hour" placeholder="hour">
        <input v-model="minute" placeholder="minute">
      <b-button @click="subcribe(city)" variant="success">Subscribe</b-button>
    </b-row>
    <h3>Subscribed cities</h3>
    <b-row v-for="sub in subscriptions" v-bind:key="sub.id">
      City:{{sub.location.name}} Hour:{{sub.hour}} Minute:{{sub.minute}}
      <b-button @click="unsubcribe(sub.id)" variant="danger">UnSubscribe</b-button>
    </b-row>
  </b-container>
</template>

<script>
import api from "@/api";

export default {
  name: "Subscriptions",
  data() {
    return {
      cities: [],
      subscriptions: [],
      hour: "",
      minute: "",

    }
  },
  methods: {
    getCities() {
      api.get('/weather/getCities/active').then((data) => {
        this.cities = data.data;
      })
    },
    getSubscriptions() {
      api.get('/subscriptions/').then((data) => {
        this.subscriptions = data.data;
      })
    },
    subcribe(city){
      api.post('/subscriptions/subscribe/', {hour: this.hour, minute:this.minute, "location":{
          name:city
        }}).then(() => {
        this.getSubscriptions();
      })
    },
    unsubcribe(id){
      api.delete('/subscriptions/unsubscribe/'+id).then(() => {
        this.getSubscriptions();
      })
    }
  },
  mounted: function () {
    this.getCities();
    this.getSubscriptions();
  }

}
</script>

<style scoped>

</style>