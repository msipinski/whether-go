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
  },
  setCity(cityName) {
    api.get('/weather/setCity/'+cityName)
  },
  getActiveCities() {
    return api.get('/weather/getCities/active').then(res => {
        return {...res, data: res.data}
    })
  },
  getInactiveCities() {
    return api.get('/weather/getCities/inactive').then(res => {
        return {...res, data: res.data}
    })
  },
  addCity(cityName) {
    return api.post('/weather/addCity/' + cityName).then(res => {
        return {...res, data: res.data}
    })
  },
  removeCity(cityName) {
      return api.post('/weather/removeCity/' + cityName).then(res => {
          return {...res, data: res.data}
      })
  },
  importForChosenCities() {
    api.get('/weather/importAll')
  },
  importForCity(cityName) {
    api.get('/weather/import/' + cityName)
  }
}

export {
  weatherService
}