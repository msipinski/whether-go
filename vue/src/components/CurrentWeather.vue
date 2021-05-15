<template>
  <div>
    <h1>Current weather:</h1>
    <h2>Temperature: <span v-if="latest">{{ latest.temp.toFixed(2) }}&deg;C</span></h2>
    <h2>Pressure: <span v-if="latest">{{ latest.pressure.toFixed(2) }} hPa</span></h2>
    <h2>Humidity: <span v-if="latest">{{ latest.humidity.toFixed(2) }}&percnt;</span></h2>
    <p>Last updated: <span v-if="latest">{{ latest.createdDate.toLocaleString() }}</span></p>
    <b-btn @click="fetchData">Refresh</b-btn>
  </div>
</template>

<script>
import {weatherService} from "@/services";

export default {
  name: "CurrentWeather",
  data() {
    return {
      latest: null,
    }
  },
  methods: {
    fetchData() {
      weatherService.latest().then(response => {
        this.latest = response.data
      })
    }
  },
  mounted() {
    this.fetchData()
  }
}
</script>

<style scoped>

</style>