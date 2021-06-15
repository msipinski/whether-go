<template>
  <b-row>
    <template v-if="chartData">
      <b-col cols="4">
        <line-chart :chart-data="chartData.temp"/>
      </b-col>
      <b-col cols="4">
        <line-chart :chart-data="chartData.pressure"/>
      </b-col>
      <b-col cols="4">
        <line-chart :chart-data="chartData.humidity"/>
      </b-col>
    </template>
    <div>
      <b-btn @click="fetchData">Refresh charts</b-btn>
    </div>
  </b-row>
</template>

<script>
import LineChart from "./LineChart";
import {weatherService} from "@/services";
import _ from "lodash";

export default {
  name: "WeatherCharts",
  components: {LineChart},
  data() {
    return {
      chartData: null,
    }
  },
  methods: {
    fetchData() {
      weatherService.list().then(response => {
        let labels = _.map(response.data.reverse(), e => e.createdDate.toLocaleString())
        let keys = ['temp', 'pressure', 'humidity']
        let {temp, pressure, humidity} = _.zipObject(keys, _.map(keys, key => _.map(response.data.reverse(), key)));

        // let temp = _.map(response.data, e => e.temp)
        // let pressure = _.map(response.data, e => e.temp)
        this.chartData = {
          temp: {
            labels,
            datasets: [
              {
                label: 'Temperature',
                data: temp
              }
            ]
          },
          pressure: {
            labels,
            datasets: [
              {
                label: 'Pressure',
                data: pressure
              }
            ]
          },
          humidity: {
            labels,
            datasets: [
              {
                label: 'Humidity',
                data: humidity
              }
            ]
          },
        }
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