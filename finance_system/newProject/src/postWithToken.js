import axios from 'axios'

export function post (data) {
  return new Promise((resolve, reject) => {
    axios.post(
      data.url,
      data.data || {},
      data.headers || {
        headers: {
          token: localStorage.getItem('token')
        }
      }
    )
      .then(response => {
        resolve(response.data)
      }, err => {
        reject(err)
      })
  })
}