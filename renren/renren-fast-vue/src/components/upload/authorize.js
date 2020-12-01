import http from '@/utils/httpRequest.js'
export function authorize (objectName) {
    return new Promise((resolve, reject) => {
        http({
            url: http.adornUrl('/thirdparty/oss/url'),
            method: 'get',
            params: http.adornParams({ objectName }, false)
        }).then(({ data }) => {
            resolve(data)
        })
    })
}
