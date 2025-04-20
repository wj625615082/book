import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/book/list',
    method: 'post',
    data: data
  })
}
