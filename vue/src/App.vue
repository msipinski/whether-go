<template>
  <b-container id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <current-weather/>
    <weather-charts/>
    <city-importing/>
    <predictions/>
  </b-container>
</template>

<script>
import CurrentWeather from "./components/CurrentWeather";
import WeatherCharts from "./components/WeatherCharts";
import CityImporting from "./components/CityImporting";
import Predictions from "./components/Predictions";

export default {
  name: 'App',
  components: {
    Predictions,
    WeatherCharts,
    CurrentWeather,
    CityImporting
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
