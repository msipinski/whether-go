<template>
  <div>
  <select class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="selected">
    <option selected>Open this select location</option>
    <option  v-for="city in activeCities" :key="city" v-bind:value="{city}">{{city}}</option>
  </select>
  <b-button @click="predict(selected)">Prediction</b-button>
  <b-modal
      v-model="show"
      title="Prediction weather">
    <h2>Location: {{selected.city}}</h2>
    <h2>Temperature:{{ predictEntity.temp }}&deg;C</h2>
    <h2>Pressure: {{ predictEntity.pressure }}hPa</h2>
    <h2>Humidity: {{ predictEntity.humidity }} &percnt;</h2>
  </b-modal>
  </div>
</template>


<script>
import {predictionsService, weatherService} from "@/services";

export default {
  name: "predictions",
  data() {
    return {
      selected:"Open this select location",
      show: false,
      activeCities:[],
      predictEntity: {
        temp: 0,
        humidity: 0,
        pressure: 0,
      }
    }
  },
  methods: {
     predict() {
      if(this.selected=="Open this select location"){
        alert("Must first select location")
        return
      }

      this.show = true;
      predictionsService.predict(this.selected.city).then(resp => {
        this.predictEntity.pressure = resp.data.pressure
        this.predictEntity.temp = resp.data.temp
        this.predictEntity.humidity = resp.data.humidity
          }
      );
    },

    select(location){
       alert(this.selected)
       this.selected = location;
    },

    getActiveCities() {
      weatherService.getActiveCities().then(response => {
        this.activeCities.push(...response.data)
      })
    },

  },
  mounted() {
    this.getActiveCities()
  }
}

</script>

<style scoped>

</style>