import axios from 'axios'

let api = axios.create({
  baseURL: '/api/',
})

api.interceptors.request.use(value => {
  console.log('req')
  console.log(value)
  return value
}, error => {
  console.log('req_err')
  console.log(error)
  throw error
})

api.interceptors.response.use(value => {
  console.log('res')
  console.log(value)
  return value
}, error => {
  console.log('res_err')
  console.log(error)
  throw error
})

export default api