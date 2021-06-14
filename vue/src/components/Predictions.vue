<template>
  <div>
  <select class="form-select form-select-sm" aria-label=".form-select-sm example">
    <option selected>Open this select location</option>
    <div v-for="city in activeCities" :key="city">
      <option @click="selected=city">{{city}}</option>
    </div>
  </select>
  <b-button @click="predict(selected)">Prediction</b-button>
  <b-modal
      v-model="show"
      title="Prediction weather">
    <h2>Temperature:{{ predictEntity.temp }}&deg;C</h2>
    <h2>Pressure: {{ predictEntity.humidity }} hPa</h2>
    <h2>Humidity: {{ predictEntity.pressure }}&percnt;</h2>
  </b-modal>
  </div>
</template>


<script>
import {predictionsService, weatherService} from "@/services";

export default {
  name: "predictions",
  data() {
    return {
      selected:null,
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
    predict(location) {
      if(this.selected==null){
        alert("Must first select location")
        return
      }

      this.show = true;
      predictionsService.predict(location).then(resp => {
            alert(resp.body);
          }
      );
    },

    getActiveCities() {
      weatherService.getActiveCities().then(response => {
        this.activeCities.push(...response.data)
      })
    },

  }
}

</script>

<style scoped>

</style>