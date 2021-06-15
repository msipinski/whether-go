<template>
  <div>
    <my-menu/>
    <b-container>
      <img alt="Vue logo" src="@/assets/logo.png">
      <current-weather/>
      <weather-charts/>
      <city-importing/>
    </b-container>
  </div>
</template>

<script>
import WeatherCharts from '@/components/WeatherCharts'
import CurrentWeather from '@/components/CurrentWeather'
import MyMenu from '@/components/MyMenu'
import CityImporting from '@/components/CityImporting'

export default {
  name: 'Home',
  components: {
    CityImporting,
    MyMenu,
    WeatherCharts,
    CurrentWeather,
  },
}
</script>

<style scoped>
</style>