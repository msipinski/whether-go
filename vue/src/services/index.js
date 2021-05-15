import api from '../api'
import _ from 'lodash'

let processWeather = function (data) {
  return {
    temp: data.temp - 273.15,
    pressure: data.pressure,
    humidity: data.humidity,
    createdDate: new Date(data.created_date),
  }
}

let weatherService = {
  list() {
    return api.get('/weather').then(res => {
      return {...res, data: _.map(res.data, processWeather)}
    })
  },
  latest() {
    return api.get('/weather/latest').then(res => {
      return {...res, data: processWeather(res.data)}
    })
  }
}

export {
  weatherService
}