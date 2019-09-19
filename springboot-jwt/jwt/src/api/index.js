import request from '@/utils/request'

export function Index() {
  return request({
    url: '/user/index',
    method: 'get'
  })
}
