/**
 * 开发环境
 */
;(function () {
    window.SITE_CONFIG = {}

    // api接口请求地址
    // api interface request url
    // All request from front end must have /api as prefix
    window.SITE_CONFIG['baseUrl'] = 'http://localhost:88/api'

    // cdn地址 = 域名 + 版本号
    window.SITE_CONFIG['domain'] = './' // 域名
    window.SITE_CONFIG['version'] = ''   // 版本号(年月日时分)
    window.SITE_CONFIG['cdnUrl'] = window.SITE_CONFIG.domain + window.SITE_CONFIG.version
})()
