import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/book/list',
    method: 'post',
    data: data
  })
}
export function saveBook(data) {
  return request({
    url: '/book/save',
    method: 'post',
    data: data
  })
}

export function updateBook(data) {
  return request({
    url: '/book/update',
    method: 'post',
    data: data
  })
}
export function deleteBook(id) {
  return request({
    url: '/book/deleteById',
    method: 'post',
    data: { id: id }
  })
}
