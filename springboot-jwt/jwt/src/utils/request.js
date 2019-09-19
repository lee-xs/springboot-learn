import axios from 'axios'
import {setToken, getToken} from '../utils/auth'

// 创建axios实例
const service = axios.create({
  withCredentials: true,
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5 * 1000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    if(getToken()){
      config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    config.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    return config
  },
  error => {
    //console.log('error :: ' + error)
    Promise.reject(error)
  }
)


// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    return response.data

  },
  error => {
    //console.log('error :: ' + error)

    return Promise.reject(error)
  }
)

export default service
