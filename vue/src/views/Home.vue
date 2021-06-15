<template>
  <div>
    <my-menu/>
    <b-container>
      <img alt="Vue logo" src="@/assets/logo.png">
      <current-weather/>
      <weather-charts/>
    </b-container>
  </div>
</template>

<script>
import WeatherCharts from '@/components/WeatherCharts'
import CurrentWeather from '@/components/CurrentWeather'
import MyMenu from '@/components/MyMenu'

export default {
  name: 'Home',
  components: {
    MyMenu,
    WeatherCharts,
    CurrentWeather,
  },
}
</script>

<style scoped>
</style>