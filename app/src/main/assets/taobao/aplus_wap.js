/* v6.9.6,6.9.12,1 2016-12-15 21:39:27 */
!function e(t,r,o){function n(i,s){if(!r[i]){if(!t[i]){var l="function"==typeof require&&require;if(!s&&l)return l(i,!0);if(a)return a(i,!0);throw new Error("Cannot find module '"+i+"'")}var c=r[i]={exports:{}};t[i][0].call(c.exports,function(e){var r=t[i][1][e];return n(r?r:e)},c,c.exports,e,t,r,o)}return r[i].exports}for(var a="function"==typeof require&&require,i=0;i<o.length;i++)n(o[i]);return n}({1:[function(e,t,r){t.exports=e("./src/grey")},{"./src/grey":2}],2:[function(e,t,r){function o(e){if(e)try{var t=d.createElement("script");t.appendChild(d.createTextNode(e)),p.parentNode.insertBefore(t,p)}catch(r){(g.execScript||function(e){g.eval.call(g,e)})(e)}}function n(e,t,r){if(/blitz/i.test(h))return void r();var o,n="GET",a=function(){o.responseText?t(o.responseText):r()};v?(o=new XMLHttpRequest,o.open(n,e,!0)):(o=new XDomainRequest,o.open(n,e)),o.timeout=_.timeout,o.onload=a,o.onerror=r,o.ontimeout=r,o.send()}function a(e,t){var r=d.createElement("script");r.async=!0,i(r,t),r.src=e,p.parentNode.insertBefore(r,p)}function i(e,t){function r(){e.onreadystatechange=e.onload=null,e=null,E(t)&&t({from:"script"})}if("onload"in e)e.onload=r;else{var o=function(){/loaded|complete/.test(e.readyState)&&r()};e.onreadystatechange=o,o()}}function s(e,t){return e+Math.floor(Math.random()*(t-e+1))}function l(e,t){return s(1,t)<=e}function c(e,t){var r;for(r in t)t.hasOwnProperty(r)&&(e[r]=t[r]);return e}function u(e,t){return function(r){return e.call(null,c(t,r||{}))}}function f(e){return function(t){return{}.toString.call(t)=="[object "+e+"]"}}var g=window,d=document,h=navigator.userAgent,p=d.getElementsByTagName("script")[0],y=g.XDomainRequest,v=g.XMLHttpRequest&&"withCredentials"in new XMLHttpRequest,m=function(){},b={set:function(e,t){try{return localStorage.setItem(e,t),!0}catch(r){return!1}},get:function(e){return localStorage.getItem(e)},test:function(){var e="grey_test_key";try{return localStorage.setItem(e,1),localStorage.removeItem(e),!0}catch(t){return!1}}},_={base:1e4,timeout:1e4},w={_config:_};w.load=function(e){e=c({isLoadDevVersion:function(){},dev:"",devKey:"",devHash:"",stable:"",stableKey:"",stableHash:"",grey:"",greyKey:"",greyHash:"",base:_.base},e);var t,r,i,s,f=e.hash,g={};if(e.ratio>=e.base||l(e.ratio,e.base)?(t=e.greyKey,r=e.grey,s=e.greyHash,g.type="grey"):(t=e.stableKey,r=e.stable,s=e.stableHash,g.type="stable"),E(e.isLoadDevVersion)&&e.isLoadDevVersion()&&(t=e.devKey,r=e.dev,s=e.devHash,g.type="dev"),g.url=r,g.key=t,g.hash=s,E(e.before)&&e.before(g),e.after=E(e.after)?u(e.after,g):m,b.test()&&t&&(v||y)&&E(f))if(i=b.get(t),i&&s===f(i,g))try{o(i),e.after({from:"local"})}catch(d){a(r,e.after)}else n(r,function(r){b.set(t,r),o(r),e.after({from:"cors"})},function(){a(r,e.after)});else a(r,e.after);return this},w.config=function(e){return c(_,e||{}),this};var E=(Array.isArray||f("Array"),f("Function"));t.exports=w},{}],3:[function(e,t,r){"use strict";!function(){var t=window,r="g_aplus_grey_launched";if(!t[r]){t[r]=1;var o=e("@ali/grey-publish"),n=location.protocol;0!=n.indexOf("http")&&(n="http:");var a=n+"//g.alicdn.com/alilog/s",i="aplus_wap.js",s=1e4,l={"aplus_o.js":[/^https?:\/\/(.*\.)?www\.youku\.com/i],"aplus_v2.js":[/^https?:\/\/(.*\.)?sycm\.taobao\.com/i,/^https?:\/\/(.*\.)?mongodb\.console\.aliyun\.com/i]},c=function(){var e;if(i){var t,r=l[i]||[];for(t=0;t<r.length;t++)if(location.href.match(r[t])){e=!0;break}}return e},u="aplus_grey_ratio";"number"==typeof t[u]&&(s=Math.floor(1e4*t[u]));var f=location.search.match(new RegExp("\\b"+u+"=([\\d\\.]+)"));f&&(f=parseFloat(f[1]),isNaN(f)||(s=Math.floor(1e4*f))),t.goldlog=t.goldlog||{},goldlog.record||(goldlog.record=function(e,r,o,n){(t.goldlog_queue||(t.goldlog_queue=[])).push({action:"goldlog.record",arguments:[e,r,o,n]})}),o.load({isLoadDevVersion:c,dev:[a,"@@DEV_VER",i].join("/"),devKey:"APLUSGREYd_aplus_wap",devHash:"@@DEV_HASH",stable:[a,"6.9.6",i].join("/"),grey:[a,"6.9.12",i].join("/"),ratio:s,stableKey:"APLUSGREYs_aplus_wap",greyKey:"APLUSGREYg_aplus_wap",stableHash:"6c997265",greyHash:"2623c35a",hash:e("./hash").hash,before:function(e){switch(e.type){case"grey":goldlog.lver="6.9.12";break;case"stable":goldlog.lver="6.9.6";break;case"dev":goldlog.lver="@@DEV_VER"}}})}}()},{"./hash":4,"@ali/grey-publish":1}],4:[function(e,t,r){"use strict";r.hash=function(e){var t,r,o=1315423911;for(t=e.length-1;t>=0;t--)r=e.charCodeAt(t),o^=(o<<5)+r+(o>>2);return(2147483647&o).toString(16)}},{}]},{},[3]);