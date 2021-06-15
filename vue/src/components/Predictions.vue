<template>
  <div>
  <select class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="selected">
    <option selected>Open this select location</option>
    <option  v-for="city in activeCities" :key="city" v-bind:value="{city}">{{city}}</option>
  </select>
  <b-button @click="predict()">Prediction</b-button>
    <b-button @click="showSecond=true">Prediction in past</b-button>
  <b-modal
      v-model="show"
      title="Prediction weather"
      ok-only>
    <h2>Location: {{selected.city}}</h2>
    <h2>Temperature:{{ predictEntity.temp }}&deg;C</h2>
    <h2>Pressure: {{ predictEntity.pressure }}hPa</h2>
    <h2>Humidity: {{ predictEntity.humidity }} &percnt;</h2>
  </b-modal>

    <b-modal v-model="showSecond"
    title="Prediction weather"
    @ok="predictPast()"
    >
      <select class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="selectedSecond">
        <option selected>Open this select location</option>
        <option  v-for="city in activeCities" :key="city" v-bind:value="{city}">{{city}}</option>
      </select>
      <h2>Time:<input type="text" class="form-control" id="dateInput" aria-describedby="emailHelp" placeholder="2007-12-03T10:15:30.00Z." v-model="windowCenter"></h2>
      <h2>Window Center:<input type="text" class="form-control" id="windowInput" aria-describedby="emailHelp" placeholder="2007-12-03T10:15:30.00Z." v-model="predictionTime"></h2>
      <h2>Samples Amount:<input type="number" class="form-control" id="samplesInput" aria-describedby="emailHelp" v-model="samplesCount"></h2>
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
      selectedSecond:"Open this select location",
      show: false,
      showSecond: false,
      windowCenter:"",
      predictionTime:"",
      samplesCount:0,
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
      predictionsService.predict(this.selected.city).then(resp => {
        this.show = true;
        this.predictEntity.pressure = resp.data.pressure
        this.predictEntity.temp = resp.data.temp
        this.predictEntity.humidity = resp.data.humidity
          }
      );
    },

    predictPast() {
      if(this.selectedSecond=="Open this select location"){
        alert("Must first select location")
        return
      }
      predictionsService.predictPast(this.selected.city, this.windowCenter, this.samplesCount, this.predictionTime).then(resp => {
            this.show = true;
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