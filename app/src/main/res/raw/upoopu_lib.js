/*
---
name: namespace

description: Cocoscript helper to create namespaces

requires: []

provides: [namespace]

...
*/
var namespace, slice$ = [].slice;
namespace = function(target, name, block){
  var ref$, top, i$, ref1$, len$, item;
  if (arguments.length < 3) {
    ref$ = [typeof exports !== 'undefined'
      ? exports
      : typeof window !== 'undefined' ? window : self].concat(slice$.call(arguments)), target = ref$[0], name = ref$[1], block = ref$[2];
  }
  top = target;
  for (i$ = 0, len$ = (ref1$ = name.split('.')).length; i$ < len$; ++i$) {
    item = ref1$[i$];
    target = target[item] || (target[item] = {});
  }
  return block(target, top);
};

/*! jQuery v1.7.2 jquery.com | jquery.org/license */
(function(a,b){function cy(a){return f.isWindow(a)?a:a.nodeType===9?a.defaultView||a.parentWindow:!1}function cu(a){if(!cj[a]){var b=c.body,d=f("<"+a+">").appendTo(b),e=d.css("display");d.remove();if(e==="none"||e===""){ck||(ck=c.createElement("iframe"),ck.frameBorder=ck.width=ck.height=0),b.appendChild(ck);if(!cl||!ck.createElement)cl=(ck.contentWindow||ck.contentDocument).document,cl.write((f.support.boxModel?"<!doctype html>":"")+"<html><body>"),cl.close();d=cl.createElement(a),cl.body.appendChild(d),e=f.css(d,"display"),b.removeChild(ck)}cj[a]=e}return cj[a]}function ct(a,b){var c={};f.each(cp.concat.apply([],cp.slice(0,b)),function(){c[this]=a});return c}function cs(){cq=b}function cr(){setTimeout(cs,0);return cq=f.now()}function ci(){try{return new a.ActiveXObject("Microsoft.XMLHTTP")}catch(b){}}function ch(){try{return new a.XMLHttpRequest}catch(b){}}function cb(a,c){a.dataFilter&&(c=a.dataFilter(c,a.dataType));var d=a.dataTypes,e={},g,h,i=d.length,j,k=d[0],l,m,n,o,p;for(g=1;g<i;g++){if(g===1)for(h in a.converters)typeof h=="string"&&(e[h.toLowerCase()]=a.converters[h]);l=k,k=d[g];if(k==="*")k=l;else if(l!=="*"&&l!==k){m=l+" "+k,n=e[m]||e["* "+k];if(!n){p=b;for(o in e){j=o.split(" ");if(j[0]===l||j[0]==="*"){p=e[j[1]+" "+k];if(p){o=e[o],o===!0?n=p:p===!0&&(n=o);break}}}}!n&&!p&&f.error("No conversion from "+m.replace(" "," to ")),n!==!0&&(c=n?n(c):p(o(c)))}}return c}function ca(a,c,d){var e=a.contents,f=a.dataTypes,g=a.responseFields,h,i,j,k;for(i in g)i in d&&(c[g[i]]=d[i]);while(f[0]==="*")f.shift(),h===b&&(h=a.mimeType||c.getResponseHeader("content-type"));if(h)for(i in e)if(e[i]&&e[i].test(h)){f.unshift(i);break}if(f[0]in d)j=f[0];else{for(i in d){if(!f[0]||a.converters[i+" "+f[0]]){j=i;break}k||(k=i)}j=j||k}if(j){j!==f[0]&&f.unshift(j);return d[j]}}function b_(a,b,c,d){if(f.isArray(b))f.each(b,function(b,e){c||bD.test(a)?d(a,e):b_(a+"["+(typeof e=="object"?b:"")+"]",e,c,d)});else if(!c&&f.type(b)==="object")for(var e in b)b_(a+"["+e+"]",b[e],c,d);else d(a,b)}function b$(a,c){var d,e,g=f.ajaxSettings.flatOptions||{};for(d in c)c[d]!==b&&((g[d]?a:e||(e={}))[d]=c[d]);e&&f.extend(!0,a,e)}function bZ(a,c,d,e,f,g){f=f||c.dataTypes[0],g=g||{},g[f]=!0;var h=a[f],i=0,j=h?h.length:0,k=a===bS,l;for(;i<j&&(k||!l);i++)l=h[i](c,d,e),typeof l=="string"&&(!k||g[l]?l=b:(c.dataTypes.unshift(l),l=bZ(a,c,d,e,l,g)));(k||!l)&&!g["*"]&&(l=bZ(a,c,d,e,"*",g));return l}function bY(a){return function(b,c){typeof b!="string"&&(c=b,b="*");if(f.isFunction(c)){var d=b.toLowerCase().split(bO),e=0,g=d.length,h,i,j;for(;e<g;e++)h=d[e],j=/^\+/.test(h),j&&(h=h.substr(1)||"*"),i=a[h]=a[h]||[],i[j?"unshift":"push"](c)}}}function bB(a,b,c){var d=b==="width"?a.offsetWidth:a.offsetHeight,e=b==="width"?1:0,g=4;if(d>0){if(c!=="border")for(;e<g;e+=2)c||(d-=parseFloat(f.css(a,"padding"+bx[e]))||0),c==="margin"?d+=parseFloat(f.css(a,c+bx[e]))||0:d-=parseFloat(f.css(a,"border"+bx[e]+"Width"))||0;return d+"px"}d=by(a,b);if(d<0||d==null)d=a.style[b];if(bt.test(d))return d;d=parseFloat(d)||0;if(c)for(;e<g;e+=2)d+=parseFloat(f.css(a,"padding"+bx[e]))||0,c!=="padding"&&(d+=parseFloat(f.css(a,"border"+bx[e]+"Width"))||0),c==="margin"&&(d+=parseFloat(f.css(a,c+bx[e]))||0);return d+"px"}function bo(a){var b=c.createElement("div");bh.appendChild(b),b.innerHTML=a.outerHTML;return b.firstChild}function bn(a){var b=(a.nodeName||"").toLowerCase();b==="input"?bm(a):b!=="script"&&typeof a.getElementsByTagName!="undefined"&&f.grep(a.getElementsByTagName("input"),bm)}function bm(a){if(a.type==="checkbox"||a.type==="radio")a.defaultChecked=a.checked}function bl(a){return typeof a.getElementsByTagName!="undefined"?a.getElementsByTagName("*"):typeof a.querySelectorAll!="undefined"?a.querySelectorAll("*"):[]}function bk(a,b){var c;b.nodeType===1&&(b.clearAttributes&&b.clearAttributes(),b.mergeAttributes&&b.mergeAttributes(a),c=b.nodeName.toLowerCase(),c==="object"?b.outerHTML=a.outerHTML:c!=="input"||a.type!=="checkbox"&&a.type!=="radio"?c==="option"?b.selected=a.defaultSelected:c==="input"||c==="textarea"?b.defaultValue=a.defaultValue:c==="script"&&b.text!==a.text&&(b.text=a.text):(a.checked&&(b.defaultChecked=b.checked=a.checked),b.value!==a.value&&(b.value=a.value)),b.removeAttribute(f.expando),b.removeAttribute("_submit_attached"),b.removeAttribute("_change_attached"))}function bj(a,b){if(b.nodeType===1&&!!f.hasData(a)){var c,d,e,g=f._data(a),h=f._data(b,g),i=g.events;if(i){delete h.handle,h.events={};for(c in i)for(d=0,e=i[c].length;d<e;d++)f.event.add(b,c,i[c][d])}h.data&&(h.data=f.extend({},h.data))}}function bi(a,b){return f.nodeName(a,"table")?a.getElementsByTagName("tbody")[0]||a.appendChild(a.ownerDocument.createElement("tbody")):a}function U(a){var b=V.split("|"),c=a.createDocumentFragment();if(c.createElement)while(b.length)c.createElement(b.pop());return c}function T(a,b,c){b=b||0;if(f.isFunction(b))return f.grep(a,function(a,d){var e=!!b.call(a,d,a);return e===c});if(b.nodeType)return f.grep(a,function(a,d){return a===b===c});if(typeof b=="string"){var d=f.grep(a,function(a){return a.nodeType===1});if(O.test(b))return f.filter(b,d,!c);b=f.filter(b,d)}return f.grep(a,function(a,d){return f.inArray(a,b)>=0===c})}function S(a){return!a||!a.parentNode||a.parentNode.nodeType===11}function K(){return!0}function J(){return!1}function n(a,b,c){var d=b+"defer",e=b+"queue",g=b+"mark",h=f._data(a,d);h&&(c==="queue"||!f._data(a,e))&&(c==="mark"||!f._data(a,g))&&setTimeout(function(){!f._data(a,e)&&!f._data(a,g)&&(f.removeData(a,d,!0),h.fire())},0)}function m(a){for(var b in a){if(b==="data"&&f.isEmptyObject(a[b]))continue;if(b!=="toJSON")return!1}return!0}function l(a,c,d){if(d===b&&a.nodeType===1){var e="data-"+c.replace(k,"-$1").toLowerCase();d=a.getAttribute(e);if(typeof d=="string"){try{d=d==="true"?!0:d==="false"?!1:d==="null"?null:f.isNumeric(d)?+d:j.test(d)?f.parseJSON(d):d}catch(g){}f.data(a,c,d)}else d=b}return d}function h(a){var b=g[a]={},c,d;a=a.split(/\s+/);for(c=0,d=a.length;c<d;c++)b[a[c]]=!0;return b}var c=a.document,d=a.navigator,e=a.location,f=function(){function J(){if(!e.isReady){try{c.documentElement.doScroll("left")}catch(a){setTimeout(J,1);return}e.ready()}}var e=function(a,b){return new e.fn.init(a,b,h)},f=a.jQuery,g=a.$,h,i=/^(?:[^#<]*(<[\w\W]+>)[^>]*$|#([\w\-]*)$)/,j=/\S/,k=/^\s+/,l=/\s+$/,m=/^<(\w+)\s*\/?>(?:<\/\1>)?$/,n=/^[\],:{}\s]*$/,o=/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,p=/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,q=/(?:^|:|,)(?:\s*\[)+/g,r=/(webkit)[ \/]([\w.]+)/,s=/(opera)(?:.*version)?[ \/]([\w.]+)/,t=/(msie) ([\w.]+)/,u=/(mozilla)(?:.*? rv:([\w.]+))?/,v=/-([a-z]|[0-9])/ig,w=/^-ms-/,x=function(a,b){return(b+"").toUpperCase()},y=d.userAgent,z,A,B,C=Object.prototype.toString,D=Object.prototype.hasOwnProperty,E=Array.prototype.push,F=Array.prototype.slice,G=String.prototype.trim,H=Array.prototype.indexOf,I={};e.fn=e.prototype={constructor:e,init:function(a,d,f){var g,h,j,k;if(!a)return this;if(a.nodeType){this.context=this[0]=a,this.length=1;return this}if(a==="body"&&!d&&c.body){this.context=c,this[0]=c.body,this.selector=a,this.length=1;return this}if(typeof a=="string"){a.charAt(0)!=="<"||a.charAt(a.length-1)!==">"||a.length<3?g=i.exec(a):g=[null,a,null];if(g&&(g[1]||!d)){if(g[1]){d=d instanceof e?d[0]:d,k=d?d.ownerDocument||d:c,j=m.exec(a),j?e.isPlainObject(d)?(a=[c.createElement(j[1])],e.fn.attr.call(a,d,!0)):a=[k.createElement(j[1])]:(j=e.buildFragment([g[1]],[k]),a=(j.cacheable?e.clone(j.fragment):j.fragment).childNodes);return e.merge(this,a)}h=c.getElementById(g[2]);if(h&&h.parentNode){if(h.id!==g[2])return f.find(a);this.length=1,this[0]=h}this.context=c,this.selector=a;return this}return!d||d.jquery?(d||f).find(a):this.constructor(d).find(a)}if(e.isFunction(a))return f.ready(a);a.selector!==b&&(this.selector=a.selector,this.context=a.context);return e.makeArray(a,this)},selector:"",jquery:"1.7.2",length:0,size:function(){return this.length},toArray:function(){return F.call(this,0)},get:function(a){return a==null?this.toArray():a<0?this[this.length+a]:this[a]},pushStack:function(a,b,c){var d=this.constructor();e.isArray(a)?E.apply(d,a):e.merge(d,a),d.prevObject=this,d.context=this.context,b==="find"?d.selector=this.selector+(this.selector?" ":"")+c:b&&(d.selector=this.selector+"."+b+"("+c+")");return d},each:function(a,b){return e.each(this,a,b)},ready:function(a){e.bindReady(),A.add(a);return this},eq:function(a){a=+a;return a===-1?this.slice(a):this.slice(a,a+1)},first:function(){return this.eq(0)},last:function(){return this.eq(-1)},slice:function(){return this.pushStack(F.apply(this,arguments),"slice",F.call(arguments).join(","))},map:function(a){return this.pushStack(e.map(this,function(b,c){return a.call(b,c,b)}))},end:function(){return this.prevObject||this.constructor(null)},push:E,sort:[].sort,splice:[].splice},e.fn.init.prototype=e.fn,e.extend=e.fn.extend=function(){var a,c,d,f,g,h,i=arguments[0]||{},j=1,k=arguments.length,l=!1;typeof i=="boolean"&&(l=i,i=arguments[1]||{},j=2),typeof i!="object"&&!e.isFunction(i)&&(i={}),k===j&&(i=this,--j);for(;j<k;j++)if((a=arguments[j])!=null)for(c in a){d=i[c],f=a[c];if(i===f)continue;l&&f&&(e.isPlainObject(f)||(g=e.isArray(f)))?(g?(g=!1,h=d&&e.isArray(d)?d:[]):h=d&&e.isPlainObject(d)?d:{},i[c]=e.extend(l,h,f)):f!==b&&(i[c]=f)}return i},e.extend({noConflict:function(b){a.$===e&&(a.$=g),b&&a.jQuery===e&&(a.jQuery=f);return e},isReady:!1,readyWait:1,holdReady:function(a){a?e.readyWait++:e.ready(!0)},ready:function(a){if(a===!0&&!--e.readyWait||a!==!0&&!e.isReady){if(!c.body)return setTimeout(e.ready,1);e.isReady=!0;if(a!==!0&&--e.readyWait>0)return;A.fireWith(c,[e]),e.fn.trigger&&e(c).trigger("ready").off("ready")}},bindReady:function(){if(!A){A=e.Callbacks("once memory");if(c.readyState==="complete")return setTimeout(e.ready,1);if(c.addEventListener)c.addEventListener("DOMContentLoaded",B,!1),a.addEventListener("load",e.ready,!1);else if(c.attachEvent){c.attachEvent("onreadystatechange",B),a.attachEvent("onload",e.ready);var b=!1;try{b=a.frameElement==null}catch(d){}c.documentElement.doScroll&&b&&J()}}},isFunction:function(a){return e.type(a)==="function"},isArray:Array.isArray||function(a){return e.type(a)==="array"},isWindow:function(a){return a!=null&&a==a.window},isNumeric:function(a){return!isNaN(parseFloat(a))&&isFinite(a)},type:function(a){return a==null?String(a):I[C.call(a)]||"object"},isPlainObject:function(a){if(!a||e.type(a)!=="object"||a.nodeType||e.isWindow(a))return!1;try{if(a.constructor&&!D.call(a,"constructor")&&!D.call(a.constructor.prototype,"isPrototypeOf"))return!1}catch(c){return!1}var d;for(d in a);return d===b||D.call(a,d)},isEmptyObject:function(a){for(var b in a)return!1;return!0},error:function(a){throw new Error(a)},parseJSON:function(b){if(typeof b!="string"||!b)return null;b=e.trim(b);if(a.JSON&&a.JSON.parse)return a.JSON.parse(b);if(n.test(b.replace(o,"@").replace(p,"]").replace(q,"")))return(new Function("return "+b))();e.error("Invalid JSON: "+b)},parseXML:function(c){if(typeof c!="string"||!c)return null;var d,f;try{a.DOMParser?(f=new DOMParser,d=f.parseFromString(c,"text/xml")):(d=new ActiveXObject("Microsoft.XMLDOM"),d.async="false",d.loadXML(c))}catch(g){d=b}(!d||!d.documentElement||d.getElementsByTagName("parsererror").length)&&e.error("Invalid XML: "+c);return d},noop:function(){},globalEval:function(b){b&&j.test(b)&&(a.execScript||function(b){a.eval.call(a,b)})(b)},camelCase:function(a){return a.replace(w,"ms-").replace(v,x)},nodeName:function(a,b){return a.nodeName&&a.nodeName.toUpperCase()===b.toUpperCase()},each:function(a,c,d){var f,g=0,h=a.length,i=h===b||e.isFunction(a);if(d){if(i){for(f in a)if(c.apply(a[f],d)===!1)break}else for(;g<h;)if(c.apply(a[g++],d)===!1)break}else if(i){for(f in a)if(c.call(a[f],f,a[f])===!1)break}else for(;g<h;)if(c.call(a[g],g,a[g++])===!1)break;return a},trim:G?function(a){return a==null?"":G.call(a)}:function(a){return a==null?"":(a+"").replace(k,"").replace(l,"")},makeArray:function(a,b){var c=b||[];if(a!=null){var d=e.type(a);a.length==null||d==="string"||d==="function"||d==="regexp"||e.isWindow(a)?E.call(c,a):e.merge(c,a)}return c},inArray:function(a,b,c){var d;if(b){if(H)return H.call(b,a,c);d=b.length,c=c?c<0?Math.max(0,d+c):c:0;for(;c<d;c++)if(c in b&&b[c]===a)return c}return-1},merge:function(a,c){var d=a.length,e=0;if(typeof c.length=="number")for(var f=c.length;e<f;e++)a[d++]=c[e];else while(c[e]!==b)a[d++]=c[e++];a.length=d;return a},grep:function(a,b,c){var d=[],e;c=!!c;for(var f=0,g=a.length;f<g;f++)e=!!b(a[f],f),c!==e&&d.push(a[f]);return d},map:function(a,c,d){var f,g,h=[],i=0,j=a.length,k=a instanceof e||j!==b&&typeof j=="number"&&(j>0&&a[0]&&a[j-1]||j===0||e.isArray(a));if(k)for(;i<j;i++)f=c(a[i],i,d),f!=null&&(h[h.length]=f);else for(g in a)f=c(a[g],g,d),f!=null&&(h[h.length]=f);return h.concat.apply([],h)},guid:1,proxy:function(a,c){if(typeof c=="string"){var d=a[c];c=a,a=d}if(!e.isFunction(a))return b;var f=F.call(arguments,2),g=function(){return a.apply(c,f.concat(F.call(arguments)))};g.guid=a.guid=a.guid||g.guid||e.guid++;return g},access:function(a,c,d,f,g,h,i){var j,k=d==null,l=0,m=a.length;if(d&&typeof d=="object"){for(l in d)e.access(a,c,l,d[l],1,h,f);g=1}else if(f!==b){j=i===b&&e.isFunction(f),k&&(j?(j=c,c=function(a,b,c){return j.call(e(a),c)}):(c.call(a,f),c=null));if(c)for(;l<m;l++)c(a[l],d,j?f.call(a[l],l,c(a[l],d)):f,i);g=1}return g?a:k?c.call(a):m?c(a[0],d):h},now:function(){return(new Date).getTime()},uaMatch:function(a){a=a.toLowerCase();var b=r.exec(a)||s.exec(a)||t.exec(a)||a.indexOf("compatible")<0&&u.exec(a)||[];return{browser:b[1]||"",version:b[2]||"0"}},sub:function(){function a(b,c){return new a.fn.init(b,c)}e.extend(!0,a,this),a.superclass=this,a.fn=a.prototype=this(),a.fn.constructor=a,a.sub=this.sub,a.fn.init=function(d,f){f&&f instanceof e&&!(f instanceof a)&&(f=a(f));return e.fn.init.call(this,d,f,b)},a.fn.init.prototype=a.fn;var b=a(c);return a},browser:{}}),e.each("Boolean Number String Function Array Date RegExp Object".split(" "),function(a,b){I["[object "+b+"]"]=b.toLowerCase()}),z=e.uaMatch(y),z.browser&&(e.browser[z.browser]=!0,e.browser.version=z.version),e.browser.webkit&&(e.browser.safari=!0),j.test("Â ")&&(k=/^[\s\xA0]+/,l=/[\s\xA0]+$/),h=e(c),c.addEventListener?B=function(){c.removeEventListener("DOMContentLoaded",B,!1),e.ready()}:c.attachEvent&&(B=function(){c.readyState==="complete"&&(c.detachEvent("onreadystatechange",B),e.ready())});return e}(),g={};f.Callbacks=function(a){a=a?g[a]||h(a):{};var c=[],d=[],e,i,j,k,l,m,n=function(b){var d,e,g,h,i;for(d=0,e=b.length;d<e;d++)g=b[d],h=f.type(g),h==="array"?n(g):h==="function"&&(!a.unique||!p.has(g))&&c.push(g)},o=function(b,f){f=f||[],e=!a.memory||[b,f],i=!0,j=!0,m=k||0,k=0,l=c.length;for(;c&&m<l;m++)if(c[m].apply(b,f)===!1&&a.stopOnFalse){e=!0;break}j=!1,c&&(a.once?e===!0?p.disable():c=[]:d&&d.length&&(e=d.shift(),p.fireWith(e[0],e[1])))},p={add:function(){if(c){var a=c.length;n(arguments),j?l=c.length:e&&e!==!0&&(k=a,o(e[0],e[1]))}return this},remove:function(){if(c){var b=arguments,d=0,e=b.length;for(;d<e;d++)for(var f=0;f<c.length;f++)if(b[d]===c[f]){j&&f<=l&&(l--,f<=m&&m--),c.splice(f--,1);if(a.unique)break}}return this},has:function(a){if(c){var b=0,d=c.length;for(;b<d;b++)if(a===c[b])return!0}return!1},empty:function(){c=[];return this},disable:function(){c=d=e=b;return this},disabled:function(){return!c},lock:function(){d=b,(!e||e===!0)&&p.disable();return this},locked:function(){return!d},fireWith:function(b,c){d&&(j?a.once||d.push([b,c]):(!a.once||!e)&&o(b,c));return this},fire:function(){p.fireWith(this,arguments);return this},fired:function(){return!!i}};return p};var i=[].slice;f.extend({Deferred:function(a){var b=f.Callbacks("once memory"),c=f.Callbacks("once memory"),d=f.Callbacks("memory"),e="pending",g={resolve:b,reject:c,notify:d},h={done:b.add,fail:c.add,progress:d.add,state:function(){return e},isResolved:b.fired,isRejected:c.fired,then:function(a,b,c){i.done(a).fail(b).progress(c);return this},always:function(){i.done.apply(i,arguments).fail.apply(i,arguments);return this},pipe:function(a,b,c){return f.Deferred(function(d){f.each({done:[a,"resolve"],fail:[b,"reject"],progress:[c,"notify"]},function(a,b){var c=b[0],e=b[1],g;f.isFunction(c)?i[a](function(){g=c.apply(this,arguments),g&&f.isFunction(g.promise)?g.promise().then(d.resolve,d.reject,d.notify):d[e+"With"](this===i?d:this,[g])}):i[a](d[e])})}).promise()},promise:function(a){if(a==null)a=h;else for(var b in h)a[b]=h[b];return a}},i=h.promise({}),j;for(j in g)i[j]=g[j].fire,i[j+"With"]=g[j].fireWith;i.done(function(){e="resolved"},c.disable,d.lock).fail(function(){e="rejected"},b.disable,d.lock),a&&a.call(i,i);return i},when:function(a){function m(a){return function(b){e[a]=arguments.length>1?i.call(arguments,0):b,j.notifyWith(k,e)}}function l(a){return function(c){b[a]=arguments.length>1?i.call(arguments,0):c,--g||j.resolveWith(j,b)}}var b=i.call(arguments,0),c=0,d=b.length,e=Array(d),g=d,h=d,j=d<=1&&a&&f.isFunction(a.promise)?a:f.Deferred(),k=j.promise();if(d>1){for(;c<d;c++)b[c]&&b[c].promise&&f.isFunction(b[c].promise)?b[c].promise().then(l(c),j.reject,m(c)):--g;g||j.resolveWith(j,b)}else j!==a&&j.resolveWith(j,d?[a]:[]);return k}}),f.support=function(){var b,d,e,g,h,i,j,k,l,m,n,o,p=c.createElement("div"),q=c.documentElement;p.setAttribute("className","t"),p.innerHTML="   <link/><table></table><a href='/a' style='top:1px;float:left;opacity:.55;'>a</a><input type='checkbox'/>",d=p.getElementsByTagName("*"),e=p.getElementsByTagName("a")[0];if(!d||!d.length||!e)return{};g=c.createElement("select"),h=g.appendChild(c.createElement("option")),i=p.getElementsByTagName("input")[0],b={leadingWhitespace:p.firstChild.nodeType===3,tbody:!p.getElementsByTagName("tbody").length,htmlSerialize:!!p.getElementsByTagName("link").length,style:/top/.test(e.getAttribute("style")),hrefNormalized:e.getAttribute("href")==="/a",opacity:/^0.55/.test(e.style.opacity),cssFloat:!!e.style.cssFloat,checkOn:i.value==="on",optSelected:h.selected,getSetAttribute:p.className!=="t",enctype:!!c.createElement("form").enctype,html5Clone:c.createElement("nav").cloneNode(!0).outerHTML!=="<:nav></:nav>",submitBubbles:!0,changeBubbles:!0,focusinBubbles:!1,deleteExpando:!0,noCloneEvent:!0,inlineBlockNeedsLayout:!1,shrinkWrapBlocks:!1,reliableMarginRight:!0,pixelMargin:!0},f.boxModel=b.boxModel=c.compatMode==="CSS1Compat",i.checked=!0,b.noCloneChecked=i.cloneNode(!0).checked,g.disabled=!0,b.optDisabled=!h.disabled;try{delete p.test}catch(r){b.deleteExpando=!1}!p.addEventListener&&p.attachEvent&&p.fireEvent&&(p.attachEvent("onclick",function(){b.noCloneEvent=!1}),p.cloneNode(!0).fireEvent("onclick")),i=c.createElement("input"),i.value="t",i.setAttribute("type","radio"),b.radioValue=i.value==="t",i.setAttribute("checked","checked"),i.setAttribute("name","t"),p.appendChild(i),j=c.createDocumentFragment(),j.appendChild(p.lastChild),b.checkClone=j.cloneNode(!0).cloneNode(!0).lastChild.checked,b.appendChecked=i.checked,j.removeChild(i),j.appendChild(p);if(p.attachEvent)for(n in{submit:1,change:1,focusin:1})m="on"+n,o=m in p,o||(p.setAttribute(m,"return;"),o=typeof p[m]=="function"),b[n+"Bubbles"]=o;j.removeChild(p),j=g=h=p=i=null,f(function(){var d,e,g,h,i,j,l,m,n,q,r,s,t,u=c.getElementsByTagName("body")[0];!u||(m=1,t="padding:0;margin:0;border:",r="position:absolute;top:0;left:0;width:1px;height:1px;",s=t+"0;visibility:hidden;",n="style='"+r+t+"5px solid #000;",q="<div "+n+"display:block;'><div style='"+t+"0;display:block;overflow:hidden;'></div></div>"+"<table "+n+"' cellpadding='0' cellspacing='0'>"+"<tr><td></td></tr></table>",d=c.createElement("div"),d.style.cssText=s+"width:0;height:0;position:static;top:0;margin-top:"+m+"px",u.insertBefore(d,u.firstChild),p=c.createElement("div"),d.appendChild(p),p.innerHTML="<table><tr><td style='"+t+"0;display:none'></td><td>t</td></tr></table>",k=p.getElementsByTagName("td"),o=k[0].offsetHeight===0,k[0].style.display="",k[1].style.display="none",b.reliableHiddenOffsets=o&&k[0].offsetHeight===0,a.getComputedStyle&&(p.innerHTML="",l=c.createElement("div"),l.style.width="0",l.style.marginRight="0",p.style.width="2px",p.appendChild(l),b.reliableMarginRight=(parseInt((a.getComputedStyle(l,null)||{marginRight:0}).marginRight,10)||0)===0),typeof p.style.zoom!="undefined"&&(p.innerHTML="",p.style.width=p.style.padding="1px",p.style.border=0,p.style.overflow="hidden",p.style.display="inline",p.style.zoom=1,b.inlineBlockNeedsLayout=p.offsetWidth===3,p.style.display="block",p.style.overflow="visible",p.innerHTML="<div style='width:5px;'></div>",b.shrinkWrapBlocks=p.offsetWidth!==3),p.style.cssText=r+s,p.innerHTML=q,e=p.firstChild,g=e.firstChild,i=e.nextSibling.firstChild.firstChild,j={doesNotAddBorder:g.offsetTop!==5,doesAddBorderForTableAndCells:i.offsetTop===5},g.style.position="fixed",g.style.top="20px",j.fixedPosition=g.offsetTop===20||g.offsetTop===15,g.style.position=g.style.top="",e.style.overflow="hidden",e.style.position="relative",j.subtractsBorderForOverflowNotVisible=g.offsetTop===-5,j.doesNotIncludeMarginInBodyOffset=u.offsetTop!==m,a.getComputedStyle&&(p.style.marginTop="1%",b.pixelMargin=(a.getComputedStyle(p,null)||{marginTop:0}).marginTop!=="1%"),typeof d.style.zoom!="undefined"&&(d.style.zoom=1),u.removeChild(d),l=p=d=null,f.extend(b,j))});return b}();var j=/^(?:\{.*\}|\[.*\])$/,k=/([A-Z])/g;f.extend({cache:{},uuid:0,expando:"jQuery"+(f.fn.jquery+Math.random()).replace(/\D/g,""),noData:{embed:!0,object:"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000",applet:!0},hasData:function(a){a=a.nodeType?f.cache[a[f.expando]]:a[f.expando];return!!a&&!m(a)},data:function(a,c,d,e){if(!!f.acceptData(a)){var g,h,i,j=f.expando,k=typeof c=="string",l=a.nodeType,m=l?f.cache:a,n=l?a[j]:a[j]&&j,o=c==="events";if((!n||!m[n]||!o&&!e&&!m[n].data)&&k&&d===b)return;n||(l?a[j]=n=++f.uuid:n=j),m[n]||(m[n]={},l||(m[n].toJSON=f.noop));if(typeof c=="object"||typeof c=="function")e?m[n]=f.extend(m[n],c):m[n].data=f.extend(m[n].data,c);g=h=m[n],e||(h.data||(h.data={}),h=h.data),d!==b&&(h[f.camelCase(c)]=d);if(o&&!h[c])return g.events;k?(i=h[c],i==null&&(i=h[f.camelCase(c)])):i=h;return i}},removeData:function(a,b,c){if(!!f.acceptData(a)){var d,e,g,h=f.expando,i=a.nodeType,j=i?f.cache:a,k=i?a[h]:h;if(!j[k])return;if(b){d=c?j[k]:j[k].data;if(d){f.isArray(b)||(b in d?b=[b]:(b=f.camelCase(b),b in d?b=[b]:b=b.split(" ")));for(e=0,g=b.length;e<g;e++)delete d[b[e]];if(!(c?m:f.isEmptyObject)(d))return}}if(!c){delete j[k].data;if(!m(j[k]))return}f.support.deleteExpando||!j.setInterval?delete j[k]:j[k]=null,i&&(f.support.deleteExpando?delete a[h]:a.removeAttribute?a.removeAttribute(h):a[h]=null)}},_data:function(a,b,c){return f.data(a,b,c,!0)},acceptData:function(a){if(a.nodeName){var b=f.noData[a.nodeName.toLowerCase()];if(b)return b!==!0&&a.getAttribute("classid")===b}return!0}}),f.fn.extend({data:function(a,c){var d,e,g,h,i,j=this[0],k=0,m=null;if(a===b){if(this.length){m=f.data(j);if(j.nodeType===1&&!f._data(j,"parsedAttrs")){g=j.attributes;for(i=g.length;k<i;k++)h=g[k].name,h.indexOf("data-")===0&&(h=f.camelCase(h.substring(5)),l(j,h,m[h]));f._data(j,"parsedAttrs",!0)}}return m}if(typeof a=="object")return this.each(function(){f.data(this,a)});d=a.split(".",2),d[1]=d[1]?"."+d[1]:"",e=d[1]+"!";return f.access(this,function(c){if(c===b){m=this.triggerHandler("getData"+e,[d[0]]),m===b&&j&&(m=f.data(j,a),m=l(j,a,m));return m===b&&d[1]?this.data(d[0]):m}d[1]=c,this.each(function(){var b=f(this);b.triggerHandler("setData"+e,d),f.data(this,a,c),b.triggerHandler("changeData"+e,d)})},null,c,arguments.length>1,null,!1)},removeData:function(a){return this.each(function(){f.removeData(this,a)})}}),f.extend({_mark:function(a,b){a&&(b=(b||"fx")+"mark",f._data(a,b,(f._data(a,b)||0)+1))},_unmark:function(a,b,c){a!==!0&&(c=b,b=a,a=!1);if(b){c=c||"fx";var d=c+"mark",e=a?0:(f._data(b,d)||1)-1;e?f._data(b,d,e):(f.removeData(b,d,!0),n(b,c,"mark"))}},queue:function(a,b,c){var d;if(a){b=(b||"fx")+"queue",d=f._data(a,b),c&&(!d||f.isArray(c)?d=f._data(a,b,f.makeArray(c)):d.push(c));return d||[]}},dequeue:function(a,b){b=b||"fx";var c=f.queue(a,b),d=c.shift(),e={};d==="inprogress"&&(d=c.shift()),d&&(b==="fx"&&c.unshift("inprogress"),f._data(a,b+".run",e),d.call(a,function(){f.dequeue(a,b)},e)),c.length||(f.removeData(a,b+"queue "+b+".run",!0),n(a,b,"queue"))}}),f.fn.extend({queue:function(a,c){var d=2;typeof a!="string"&&(c=a,a="fx",d--);if(arguments.length<d)return f.queue(this[0],a);return c===b?this:this.each(function(){var b=f.queue(this,a,c);a==="fx"&&b[0]!=="inprogress"&&f.dequeue(this,a)})},dequeue:function(a){return this.each(function(){f.dequeue(this,a)})},delay:function(a,b){a=f.fx?f.fx.speeds[a]||a:a,b=b||"fx";return this.queue(b,function(b,c){var d=setTimeout(b,a);c.stop=function(){clearTimeout(d)}})},clearQueue:function(a){return this.queue(a||"fx",[])},promise:function(a,c){function m(){--h||d.resolveWith(e,[e])}typeof a!="string"&&(c=a,a=b),a=a||"fx";var d=f.Deferred(),e=this,g=e.length,h=1,i=a+"defer",j=a+"queue",k=a+"mark",l;while(g--)if(l=f.data(e[g],i,b,!0)||(f.data(e[g],j,b,!0)||f.data(e[g],k,b,!0))&&f.data(e[g],i,f.Callbacks("once memory"),!0))h++,l.add(m);m();return d.promise(c)}});var o=/[\n\t\r]/g,p=/\s+/,q=/\r/g,r=/^(?:button|input)$/i,s=/^(?:button|input|object|select|textarea)$/i,t=/^a(?:rea)?$/i,u=/^(?:autofocus|autoplay|async|checked|controls|defer|disabled|hidden|loop|multiple|open|readonly|required|scoped|selected)$/i,v=f.support.getSetAttribute,w,x,y;f.fn.extend({attr:function(a,b){return f.access(this,f.attr,a,b,arguments.length>1)},removeAttr:function(a){return this.each(function(){f.removeAttr(this,a)})},prop:function(a,b){return f.access(this,f.prop,a,b,arguments.length>1)},removeProp:function(a){a=f.propFix[a]||a;return this.each(function(){try{this[a]=b,delete this[a]}catch(c){}})},addClass:function(a){var b,c,d,e,g,h,i;if(f.isFunction(a))return this.each(function(b){f(this).addClass(a.call(this,b,this.className))});if(a&&typeof a=="string"){b=a.split(p);for(c=0,d=this.length;c<d;c++){e=this[c];if(e.nodeType===1)if(!e.className&&b.length===1)e.className=a;else{g=" "+e.className+" ";for(h=0,i=b.length;h<i;h++)~g.indexOf(" "+b[h]+" ")||(g+=b[h]+" ");e.className=f.trim(g)}}}return this},removeClass:function(a){var c,d,e,g,h,i,j;if(f.isFunction(a))return this.each(function(b){f(this).removeClass(a.call(this,b,this.className))});if(a&&typeof a=="string"||a===b){c=(a||"").split(p);for(d=0,e=this.length;d<e;d++){g=this[d];if(g.nodeType===1&&g.className)if(a){h=(" "+g.className+" ").replace(o," ");for(i=0,j=c.length;i<j;i++)h=h.replace(" "+c[i]+" "," ");g.className=f.trim(h)}else g.className=""}}return this},toggleClass:function(a,b){var c=typeof a,d=typeof b=="boolean";if(f.isFunction(a))return this.each(function(c){f(this).toggleClass(a.call(this,c,this.className,b),b)});return this.each(function(){if(c==="string"){var e,g=0,h=f(this),i=b,j=a.split(p);while(e=j[g++])i=d?i:!h.hasClass(e),h[i?"addClass":"removeClass"](e)}else if(c==="undefined"||c==="boolean")this.className&&f._data(this,"__className__",this.className),this.className=this.className||a===!1?"":f._data(this,"__className__")||""})},hasClass:function(a){var b=" "+a+" ",c=0,d=this.length;for(;c<d;c++)if(this[c].nodeType===1&&(" "+this[c].className+" ").replace(o," ").indexOf(b)>-1)return!0;return!1},val:function(a){var c,d,e,g=this[0];{if(!!arguments.length){e=f.isFunction(a);return this.each(function(d){var g=f(this),h;if(this.nodeType===1){e?h=a.call(this,d,g.val()):h=a,h==null?h="":typeof h=="number"?h+="":f.isArray(h)&&(h=f.map(h,function(a){return a==null?"":a+""})),c=f.valHooks[this.type]||f.valHooks[this.nodeName.toLowerCase()];if(!c||!("set"in c)||c.set(this,h,"value")===b)this.value=h}})}if(g){c=f.valHooks[g.type]||f.valHooks[g.nodeName.toLowerCase()];if(c&&"get"in c&&(d=c.get(g,"value"))!==b)return d;d=g.value;return typeof d=="string"?d.replace(q,""):d==null?"":d}}}}),f.extend({valHooks:{option:{get:function(a){var b=a.attributes.value;return!b||b.specified?a.value:a.text}},select:{get:function(a){var b,c,d,e,g=a.selectedIndex,h=[],i=a.options,j=a.type==="select-one";if(g<0)return null;c=j?g:0,d=j?g+1:i.length;for(;c<d;c++){e=i[c];if(e.selected&&(f.support.optDisabled?!e.disabled:e.getAttribute("disabled")===null)&&(!e.parentNode.disabled||!f.nodeName(e.parentNode,"optgroup"))){b=f(e).val();if(j)return b;h.push(b)}}if(j&&!h.length&&i.length)return f(i[g]).val();return h},set:function(a,b){var c=f.makeArray(b);f(a).find("option").each(function(){this.selected=f.inArray(f(this).val(),c)>=0}),c.length||(a.selectedIndex=-1);return c}}},attrFn:{val:!0,css:!0,html:!0,text:!0,data:!0,width:!0,height:!0,offset:!0},attr:function(a,c,d,e){var g,h,i,j=a.nodeType;if(!!a&&j!==3&&j!==8&&j!==2){if(e&&c in f.attrFn)return f(a)[c](d);if(typeof a.getAttribute=="undefined")return f.prop(a,c,d);i=j!==1||!f.isXMLDoc(a),i&&(c=c.toLowerCase(),h=f.attrHooks[c]||(u.test(c)?x:w));if(d!==b){if(d===null){f.removeAttr(a,c);return}if(h&&"set"in h&&i&&(g=h.set(a,d,c))!==b)return g;a.setAttribute(c,""+d);return d}if(h&&"get"in h&&i&&(g=h.get(a,c))!==null)return g;g=a.getAttribute(c);return g===null?b:g}},removeAttr:function(a,b){var c,d,e,g,h,i=0;if(b&&a.nodeType===1){d=b.toLowerCase().split(p),g=d.length;for(;i<g;i++)e=d[i],e&&(c=f.propFix[e]||e,h=u.test(e),h||f.attr(a,e,""),a.removeAttribute(v?e:c),h&&c in a&&(a[c]=!1))}},attrHooks:{type:{set:function(a,b){if(r.test(a.nodeName)&&a.parentNode)f.error("type property can't be changed");else if(!f.support.radioValue&&b==="radio"&&f.nodeName(a,"input")){var c=a.value;a.setAttribute("type",b),c&&(a.value=c);return b}}},value:{get:function(a,b){if(w&&f.nodeName(a,"button"))return w.get(a,b);return b in a?a.value:null},set:function(a,b,c){if(w&&f.nodeName(a,"button"))return w.set(a,b,c);a.value=b}}},propFix:{tabindex:"tabIndex",readonly:"readOnly","for":"htmlFor","class":"className",maxlength:"maxLength",cellspacing:"cellSpacing",cellpadding:"cellPadding",rowspan:"rowSpan",colspan:"colSpan",usemap:"useMap",frameborder:"frameBorder",contenteditable:"contentEditable"},prop:function(a,c,d){var e,g,h,i=a.nodeType;if(!!a&&i!==3&&i!==8&&i!==2){h=i!==1||!f.isXMLDoc(a),h&&(c=f.propFix[c]||c,g=f.propHooks[c]);return d!==b?g&&"set"in g&&(e=g.set(a,d,c))!==b?e:a[c]=d:g&&"get"in g&&(e=g.get(a,c))!==null?e:a[c]}},propHooks:{tabIndex:{get:function(a){var c=a.getAttributeNode("tabindex");return c&&c.specified?parseInt(c.value,10):s.test(a.nodeName)||t.test(a.nodeName)&&a.href?0:b}}}}),f.attrHooks.tabindex=f.propHooks.tabIndex,x={get:function(a,c){var d,e=f.prop(a,c);return e===!0||typeof e!="boolean"&&(d=a.getAttributeNode(c))&&d.nodeValue!==!1?c.toLowerCase():b},set:function(a,b,c){var d;b===!1?f.removeAttr(a,c):(d=f.propFix[c]||c,d in a&&(a[d]=!0),a.setAttribute(c,c.toLowerCase()));return c}},v||(y={name:!0,id:!0,coords:!0},w=f.valHooks.button={get:function(a,c){var d;d=a.getAttributeNode(c);return d&&(y[c]?d.nodeValue!=="":d.specified)?d.nodeValue:b},set:function(a,b,d){var e=a.getAttributeNode(d);e||(e=c.createAttribute(d),a.setAttributeNode(e));return e.nodeValue=b+""}},f.attrHooks.tabindex.set=w.set,f.each(["width","height"],function(a,b){f.attrHooks[b]=f.extend(f.attrHooks[b],{set:function(a,c){if(c===""){a.setAttribute(b,"auto");return c}}})}),f.attrHooks.contenteditable={get:w.get,set:function(a,b,c){b===""&&(b="false"),w.set(a,b,c)}}),f.support.hrefNormalized||f.each(["href","src","width","height"],function(a,c){f.attrHooks[c]=f.extend(f.attrHooks[c],{get:function(a){var d=a.getAttribute(c,2);return d===null?b:d}})}),f.support.style||(f.attrHooks.style={get:function(a){return a.style.cssText.toLowerCase()||b},set:function(a,b){return a.style.cssText=""+b}}),f.support.optSelected||(f.propHooks.selected=f.extend(f.propHooks.selected,{get:function(a){var b=a.parentNode;b&&(b.selectedIndex,b.parentNode&&b.parentNode.selectedIndex);return null}})),f.support.enctype||(f.propFix.enctype="encoding"),f.support.checkOn||f.each(["radio","checkbox"],function(){f.valHooks[this]={get:function(a){return a.getAttribute("value")===null?"on":a.value}}}),f.each(["radio","checkbox"],function(){f.valHooks[this]=f.extend(f.valHooks[this],{set:function(a,b){if(f.isArray(b))return a.checked=f.inArray(f(a).val(),b)>=0}})});var z=/^(?:textarea|input|select)$/i,A=/^([^\.]*)?(?:\.(.+))?$/,B=/(?:^|\s)hover(\.\S+)?\b/,C=/^key/,D=/^(?:mouse|contextmenu)|click/,E=/^(?:focusinfocus|focusoutblur)$/,F=/^(\w*)(?:#([\w\-]+))?(?:\.([\w\-]+))?$/,G=function(
a){var b=F.exec(a);b&&(b[1]=(b[1]||"").toLowerCase(),b[3]=b[3]&&new RegExp("(?:^|\\s)"+b[3]+"(?:\\s|$)"));return b},H=function(a,b){var c=a.attributes||{};return(!b[1]||a.nodeName.toLowerCase()===b[1])&&(!b[2]||(c.id||{}).value===b[2])&&(!b[3]||b[3].test((c["class"]||{}).value))},I=function(a){return f.event.special.hover?a:a.replace(B,"mouseenter$1 mouseleave$1")};f.event={add:function(a,c,d,e,g){var h,i,j,k,l,m,n,o,p,q,r,s;if(!(a.nodeType===3||a.nodeType===8||!c||!d||!(h=f._data(a)))){d.handler&&(p=d,d=p.handler,g=p.selector),d.guid||(d.guid=f.guid++),j=h.events,j||(h.events=j={}),i=h.handle,i||(h.handle=i=function(a){return typeof f!="undefined"&&(!a||f.event.triggered!==a.type)?f.event.dispatch.apply(i.elem,arguments):b},i.elem=a),c=f.trim(I(c)).split(" ");for(k=0;k<c.length;k++){l=A.exec(c[k])||[],m=l[1],n=(l[2]||"").split(".").sort(),s=f.event.special[m]||{},m=(g?s.delegateType:s.bindType)||m,s=f.event.special[m]||{},o=f.extend({type:m,origType:l[1],data:e,handler:d,guid:d.guid,selector:g,quick:g&&G(g),namespace:n.join(".")},p),r=j[m];if(!r){r=j[m]=[],r.delegateCount=0;if(!s.setup||s.setup.call(a,e,n,i)===!1)a.addEventListener?a.addEventListener(m,i,!1):a.attachEvent&&a.attachEvent("on"+m,i)}s.add&&(s.add.call(a,o),o.handler.guid||(o.handler.guid=d.guid)),g?r.splice(r.delegateCount++,0,o):r.push(o),f.event.global[m]=!0}a=null}},global:{},remove:function(a,b,c,d,e){var g=f.hasData(a)&&f._data(a),h,i,j,k,l,m,n,o,p,q,r,s;if(!!g&&!!(o=g.events)){b=f.trim(I(b||"")).split(" ");for(h=0;h<b.length;h++){i=A.exec(b[h])||[],j=k=i[1],l=i[2];if(!j){for(j in o)f.event.remove(a,j+b[h],c,d,!0);continue}p=f.event.special[j]||{},j=(d?p.delegateType:p.bindType)||j,r=o[j]||[],m=r.length,l=l?new RegExp("(^|\\.)"+l.split(".").sort().join("\\.(?:.*\\.)?")+"(\\.|$)"):null;for(n=0;n<r.length;n++)s=r[n],(e||k===s.origType)&&(!c||c.guid===s.guid)&&(!l||l.test(s.namespace))&&(!d||d===s.selector||d==="**"&&s.selector)&&(r.splice(n--,1),s.selector&&r.delegateCount--,p.remove&&p.remove.call(a,s));r.length===0&&m!==r.length&&((!p.teardown||p.teardown.call(a,l)===!1)&&f.removeEvent(a,j,g.handle),delete o[j])}f.isEmptyObject(o)&&(q=g.handle,q&&(q.elem=null),f.removeData(a,["events","handle"],!0))}},customEvent:{getData:!0,setData:!0,changeData:!0},trigger:function(c,d,e,g){if(!e||e.nodeType!==3&&e.nodeType!==8){var h=c.type||c,i=[],j,k,l,m,n,o,p,q,r,s;if(E.test(h+f.event.triggered))return;h.indexOf("!")>=0&&(h=h.slice(0,-1),k=!0),h.indexOf(".")>=0&&(i=h.split("."),h=i.shift(),i.sort());if((!e||f.event.customEvent[h])&&!f.event.global[h])return;c=typeof c=="object"?c[f.expando]?c:new f.Event(h,c):new f.Event(h),c.type=h,c.isTrigger=!0,c.exclusive=k,c.namespace=i.join("."),c.namespace_re=c.namespace?new RegExp("(^|\\.)"+i.join("\\.(?:.*\\.)?")+"(\\.|$)"):null,o=h.indexOf(":")<0?"on"+h:"";if(!e){j=f.cache;for(l in j)j[l].events&&j[l].events[h]&&f.event.trigger(c,d,j[l].handle.elem,!0);return}c.result=b,c.target||(c.target=e),d=d!=null?f.makeArray(d):[],d.unshift(c),p=f.event.special[h]||{};if(p.trigger&&p.trigger.apply(e,d)===!1)return;r=[[e,p.bindType||h]];if(!g&&!p.noBubble&&!f.isWindow(e)){s=p.delegateType||h,m=E.test(s+h)?e:e.parentNode,n=null;for(;m;m=m.parentNode)r.push([m,s]),n=m;n&&n===e.ownerDocument&&r.push([n.defaultView||n.parentWindow||a,s])}for(l=0;l<r.length&&!c.isPropagationStopped();l++)m=r[l][0],c.type=r[l][1],q=(f._data(m,"events")||{})[c.type]&&f._data(m,"handle"),q&&q.apply(m,d),q=o&&m[o],q&&f.acceptData(m)&&q.apply(m,d)===!1&&c.preventDefault();c.type=h,!g&&!c.isDefaultPrevented()&&(!p._default||p._default.apply(e.ownerDocument,d)===!1)&&(h!=="click"||!f.nodeName(e,"a"))&&f.acceptData(e)&&o&&e[h]&&(h!=="focus"&&h!=="blur"||c.target.offsetWidth!==0)&&!f.isWindow(e)&&(n=e[o],n&&(e[o]=null),f.event.triggered=h,e[h](),f.event.triggered=b,n&&(e[o]=n));return c.result}},dispatch:function(c){c=f.event.fix(c||a.event);var d=(f._data(this,"events")||{})[c.type]||[],e=d.delegateCount,g=[].slice.call(arguments,0),h=!c.exclusive&&!c.namespace,i=f.event.special[c.type]||{},j=[],k,l,m,n,o,p,q,r,s,t,u;g[0]=c,c.delegateTarget=this;if(!i.preDispatch||i.preDispatch.call(this,c)!==!1){if(e&&(!c.button||c.type!=="click")){n=f(this),n.context=this.ownerDocument||this;for(m=c.target;m!=this;m=m.parentNode||this)if(m.disabled!==!0){p={},r=[],n[0]=m;for(k=0;k<e;k++)s=d[k],t=s.selector,p[t]===b&&(p[t]=s.quick?H(m,s.quick):n.is(t)),p[t]&&r.push(s);r.length&&j.push({elem:m,matches:r})}}d.length>e&&j.push({elem:this,matches:d.slice(e)});for(k=0;k<j.length&&!c.isPropagationStopped();k++){q=j[k],c.currentTarget=q.elem;for(l=0;l<q.matches.length&&!c.isImmediatePropagationStopped();l++){s=q.matches[l];if(h||!c.namespace&&!s.namespace||c.namespace_re&&c.namespace_re.test(s.namespace))c.data=s.data,c.handleObj=s,o=((f.event.special[s.origType]||{}).handle||s.handler).apply(q.elem,g),o!==b&&(c.result=o,o===!1&&(c.preventDefault(),c.stopPropagation()))}}i.postDispatch&&i.postDispatch.call(this,c);return c.result}},props:"attrChange attrName relatedNode srcElement altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),fixHooks:{},keyHooks:{props:"char charCode key keyCode".split(" "),filter:function(a,b){a.which==null&&(a.which=b.charCode!=null?b.charCode:b.keyCode);return a}},mouseHooks:{props:"button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),filter:function(a,d){var e,f,g,h=d.button,i=d.fromElement;a.pageX==null&&d.clientX!=null&&(e=a.target.ownerDocument||c,f=e.documentElement,g=e.body,a.pageX=d.clientX+(f&&f.scrollLeft||g&&g.scrollLeft||0)-(f&&f.clientLeft||g&&g.clientLeft||0),a.pageY=d.clientY+(f&&f.scrollTop||g&&g.scrollTop||0)-(f&&f.clientTop||g&&g.clientTop||0)),!a.relatedTarget&&i&&(a.relatedTarget=i===a.target?d.toElement:i),!a.which&&h!==b&&(a.which=h&1?1:h&2?3:h&4?2:0);return a}},fix:function(a){if(a[f.expando])return a;var d,e,g=a,h=f.event.fixHooks[a.type]||{},i=h.props?this.props.concat(h.props):this.props;a=f.Event(g);for(d=i.length;d;)e=i[--d],a[e]=g[e];a.target||(a.target=g.srcElement||c),a.target.nodeType===3&&(a.target=a.target.parentNode),a.metaKey===b&&(a.metaKey=a.ctrlKey);return h.filter?h.filter(a,g):a},special:{ready:{setup:f.bindReady},load:{noBubble:!0},focus:{delegateType:"focusin"},blur:{delegateType:"focusout"},beforeunload:{setup:function(a,b,c){f.isWindow(this)&&(this.onbeforeunload=c)},teardown:function(a,b){this.onbeforeunload===b&&(this.onbeforeunload=null)}}},simulate:function(a,b,c,d){var e=f.extend(new f.Event,c,{type:a,isSimulated:!0,originalEvent:{}});d?f.event.trigger(e,null,b):f.event.dispatch.call(b,e),e.isDefaultPrevented()&&c.preventDefault()}},f.event.handle=f.event.dispatch,f.removeEvent=c.removeEventListener?function(a,b,c){a.removeEventListener&&a.removeEventListener(b,c,!1)}:function(a,b,c){a.detachEvent&&a.detachEvent("on"+b,c)},f.Event=function(a,b){if(!(this instanceof f.Event))return new f.Event(a,b);a&&a.type?(this.originalEvent=a,this.type=a.type,this.isDefaultPrevented=a.defaultPrevented||a.returnValue===!1||a.getPreventDefault&&a.getPreventDefault()?K:J):this.type=a,b&&f.extend(this,b),this.timeStamp=a&&a.timeStamp||f.now(),this[f.expando]=!0},f.Event.prototype={preventDefault:function(){this.isDefaultPrevented=K;var a=this.originalEvent;!a||(a.preventDefault?a.preventDefault():a.returnValue=!1)},stopPropagation:function(){this.isPropagationStopped=K;var a=this.originalEvent;!a||(a.stopPropagation&&a.stopPropagation(),a.cancelBubble=!0)},stopImmediatePropagation:function(){this.isImmediatePropagationStopped=K,this.stopPropagation()},isDefaultPrevented:J,isPropagationStopped:J,isImmediatePropagationStopped:J},f.each({mouseenter:"mouseover",mouseleave:"mouseout"},function(a,b){f.event.special[a]={delegateType:b,bindType:b,handle:function(a){var c=this,d=a.relatedTarget,e=a.handleObj,g=e.selector,h;if(!d||d!==c&&!f.contains(c,d))a.type=e.origType,h=e.handler.apply(this,arguments),a.type=b;return h}}}),f.support.submitBubbles||(f.event.special.submit={setup:function(){if(f.nodeName(this,"form"))return!1;f.event.add(this,"click._submit keypress._submit",function(a){var c=a.target,d=f.nodeName(c,"input")||f.nodeName(c,"button")?c.form:b;d&&!d._submit_attached&&(f.event.add(d,"submit._submit",function(a){a._submit_bubble=!0}),d._submit_attached=!0)})},postDispatch:function(a){a._submit_bubble&&(delete a._submit_bubble,this.parentNode&&!a.isTrigger&&f.event.simulate("submit",this.parentNode,a,!0))},teardown:function(){if(f.nodeName(this,"form"))return!1;f.event.remove(this,"._submit")}}),f.support.changeBubbles||(f.event.special.change={setup:function(){if(z.test(this.nodeName)){if(this.type==="checkbox"||this.type==="radio")f.event.add(this,"propertychange._change",function(a){a.originalEvent.propertyName==="checked"&&(this._just_changed=!0)}),f.event.add(this,"click._change",function(a){this._just_changed&&!a.isTrigger&&(this._just_changed=!1,f.event.simulate("change",this,a,!0))});return!1}f.event.add(this,"beforeactivate._change",function(a){var b=a.target;z.test(b.nodeName)&&!b._change_attached&&(f.event.add(b,"change._change",function(a){this.parentNode&&!a.isSimulated&&!a.isTrigger&&f.event.simulate("change",this.parentNode,a,!0)}),b._change_attached=!0)})},handle:function(a){var b=a.target;if(this!==b||a.isSimulated||a.isTrigger||b.type!=="radio"&&b.type!=="checkbox")return a.handleObj.handler.apply(this,arguments)},teardown:function(){f.event.remove(this,"._change");return z.test(this.nodeName)}}),f.support.focusinBubbles||f.each({focus:"focusin",blur:"focusout"},function(a,b){var d=0,e=function(a){f.event.simulate(b,a.target,f.event.fix(a),!0)};f.event.special[b]={setup:function(){d++===0&&c.addEventListener(a,e,!0)},teardown:function(){--d===0&&c.removeEventListener(a,e,!0)}}}),f.fn.extend({on:function(a,c,d,e,g){var h,i;if(typeof a=="object"){typeof c!="string"&&(d=d||c,c=b);for(i in a)this.on(i,c,d,a[i],g);return this}d==null&&e==null?(e=c,d=c=b):e==null&&(typeof c=="string"?(e=d,d=b):(e=d,d=c,c=b));if(e===!1)e=J;else if(!e)return this;g===1&&(h=e,e=function(a){f().off(a);return h.apply(this,arguments)},e.guid=h.guid||(h.guid=f.guid++));return this.each(function(){f.event.add(this,a,e,d,c)})},one:function(a,b,c,d){return this.on(a,b,c,d,1)},off:function(a,c,d){if(a&&a.preventDefault&&a.handleObj){var e=a.handleObj;f(a.delegateTarget).off(e.namespace?e.origType+"."+e.namespace:e.origType,e.selector,e.handler);return this}if(typeof a=="object"){for(var g in a)this.off(g,c,a[g]);return this}if(c===!1||typeof c=="function")d=c,c=b;d===!1&&(d=J);return this.each(function(){f.event.remove(this,a,d,c)})},bind:function(a,b,c){return this.on(a,null,b,c)},unbind:function(a,b){return this.off(a,null,b)},live:function(a,b,c){f(this.context).on(a,this.selector,b,c);return this},die:function(a,b){f(this.context).off(a,this.selector||"**",b);return this},delegate:function(a,b,c,d){return this.on(b,a,c,d)},undelegate:function(a,b,c){return arguments.length==1?this.off(a,"**"):this.off(b,a,c)},trigger:function(a,b){return this.each(function(){f.event.trigger(a,b,this)})},triggerHandler:function(a,b){if(this[0])return f.event.trigger(a,b,this[0],!0)},toggle:function(a){var b=arguments,c=a.guid||f.guid++,d=0,e=function(c){var e=(f._data(this,"lastToggle"+a.guid)||0)%d;f._data(this,"lastToggle"+a.guid,e+1),c.preventDefault();return b[e].apply(this,arguments)||!1};e.guid=c;while(d<b.length)b[d++].guid=c;return this.click(e)},hover:function(a,b){return this.mouseenter(a).mouseleave(b||a)}}),f.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "),function(a,b){f.fn[b]=function(a,c){c==null&&(c=a,a=null);return arguments.length>0?this.on(b,null,a,c):this.trigger(b)},f.attrFn&&(f.attrFn[b]=!0),C.test(b)&&(f.event.fixHooks[b]=f.event.keyHooks),D.test(b)&&(f.event.fixHooks[b]=f.event.mouseHooks)}),function(){function x(a,b,c,e,f,g){for(var h=0,i=e.length;h<i;h++){var j=e[h];if(j){var k=!1;j=j[a];while(j){if(j[d]===c){k=e[j.sizset];break}if(j.nodeType===1){g||(j[d]=c,j.sizset=h);if(typeof b!="string"){if(j===b){k=!0;break}}else if(m.filter(b,[j]).length>0){k=j;break}}j=j[a]}e[h]=k}}}function w(a,b,c,e,f,g){for(var h=0,i=e.length;h<i;h++){var j=e[h];if(j){var k=!1;j=j[a];while(j){if(j[d]===c){k=e[j.sizset];break}j.nodeType===1&&!g&&(j[d]=c,j.sizset=h);if(j.nodeName.toLowerCase()===b){k=j;break}j=j[a]}e[h]=k}}}var a=/((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^\[\]]*\]|['"][^'"]*['"]|[^\[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g,d="sizcache"+(Math.random()+"").replace(".",""),e=0,g=Object.prototype.toString,h=!1,i=!0,j=/\\/g,k=/\r\n/g,l=/\W/;[0,0].sort(function(){i=!1;return 0});var m=function(b,d,e,f){e=e||[],d=d||c;var h=d;if(d.nodeType!==1&&d.nodeType!==9)return[];if(!b||typeof b!="string")return e;var i,j,k,l,n,q,r,t,u=!0,v=m.isXML(d),w=[],x=b;do{a.exec(""),i=a.exec(x);if(i){x=i[3],w.push(i[1]);if(i[2]){l=i[3];break}}}while(i);if(w.length>1&&p.exec(b))if(w.length===2&&o.relative[w[0]])j=y(w[0]+w[1],d,f);else{j=o.relative[w[0]]?[d]:m(w.shift(),d);while(w.length)b=w.shift(),o.relative[b]&&(b+=w.shift()),j=y(b,j,f)}else{!f&&w.length>1&&d.nodeType===9&&!v&&o.match.ID.test(w[0])&&!o.match.ID.test(w[w.length-1])&&(n=m.find(w.shift(),d,v),d=n.expr?m.filter(n.expr,n.set)[0]:n.set[0]);if(d){n=f?{expr:w.pop(),set:s(f)}:m.find(w.pop(),w.length===1&&(w[0]==="~"||w[0]==="+")&&d.parentNode?d.parentNode:d,v),j=n.expr?m.filter(n.expr,n.set):n.set,w.length>0?k=s(j):u=!1;while(w.length)q=w.pop(),r=q,o.relative[q]?r=w.pop():q="",r==null&&(r=d),o.relative[q](k,r,v)}else k=w=[]}k||(k=j),k||m.error(q||b);if(g.call(k)==="[object Array]")if(!u)e.push.apply(e,k);else if(d&&d.nodeType===1)for(t=0;k[t]!=null;t++)k[t]&&(k[t]===!0||k[t].nodeType===1&&m.contains(d,k[t]))&&e.push(j[t]);else for(t=0;k[t]!=null;t++)k[t]&&k[t].nodeType===1&&e.push(j[t]);else s(k,e);l&&(m(l,h,e,f),m.uniqueSort(e));return e};m.uniqueSort=function(a){if(u){h=i,a.sort(u);if(h)for(var b=1;b<a.length;b++)a[b]===a[b-1]&&a.splice(b--,1)}return a},m.matches=function(a,b){return m(a,null,null,b)},m.matchesSelector=function(a,b){return m(b,null,null,[a]).length>0},m.find=function(a,b,c){var d,e,f,g,h,i;if(!a)return[];for(e=0,f=o.order.length;e<f;e++){h=o.order[e];if(g=o.leftMatch[h].exec(a)){i=g[1],g.splice(1,1);if(i.substr(i.length-1)!=="\\"){g[1]=(g[1]||"").replace(j,""),d=o.find[h](g,b,c);if(d!=null){a=a.replace(o.match[h],"");break}}}}d||(d=typeof b.getElementsByTagName!="undefined"?b.getElementsByTagName("*"):[]);return{set:d,expr:a}},m.filter=function(a,c,d,e){var f,g,h,i,j,k,l,n,p,q=a,r=[],s=c,t=c&&c[0]&&m.isXML(c[0]);while(a&&c.length){for(h in o.filter)if((f=o.leftMatch[h].exec(a))!=null&&f[2]){k=o.filter[h],l=f[1],g=!1,f.splice(1,1);if(l.substr(l.length-1)==="\\")continue;s===r&&(r=[]);if(o.preFilter[h]){f=o.preFilter[h](f,s,d,r,e,t);if(!f)g=i=!0;else if(f===!0)continue}if(f)for(n=0;(j=s[n])!=null;n++)j&&(i=k(j,f,n,s),p=e^i,d&&i!=null?p?g=!0:s[n]=!1:p&&(r.push(j),g=!0));if(i!==b){d||(s=r),a=a.replace(o.match[h],"");if(!g)return[];break}}if(a===q)if(g==null)m.error(a);else break;q=a}return s},m.error=function(a){throw new Error("Syntax error, unrecognized expression: "+a)};var n=m.getText=function(a){var b,c,d=a.nodeType,e="";if(d){if(d===1||d===9||d===11){if(typeof a.textContent=="string")return a.textContent;if(typeof a.innerText=="string")return a.innerText.replace(k,"");for(a=a.firstChild;a;a=a.nextSibling)e+=n(a)}else if(d===3||d===4)return a.nodeValue}else for(b=0;c=a[b];b++)c.nodeType!==8&&(e+=n(c));return e},o=m.selectors={order:["ID","NAME","TAG"],match:{ID:/#((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,CLASS:/\.((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,NAME:/\[name=['"]*((?:[\w\u00c0-\uFFFF\-]|\\.)+)['"]*\]/,ATTR:/\[\s*((?:[\w\u00c0-\uFFFF\-]|\\.)+)\s*(?:(\S?=)\s*(?:(['"])(.*?)\3|(#?(?:[\w\u00c0-\uFFFF\-]|\\.)*)|)|)\s*\]/,TAG:/^((?:[\w\u00c0-\uFFFF\*\-]|\\.)+)/,CHILD:/:(only|nth|last|first)-child(?:\(\s*(even|odd|(?:[+\-]?\d+|(?:[+\-]?\d*)?n\s*(?:[+\-]\s*\d+)?))\s*\))?/,POS:/:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^\-]|$)/,PSEUDO:/:((?:[\w\u00c0-\uFFFF\-]|\\.)+)(?:\((['"]?)((?:\([^\)]+\)|[^\(\)]*)+)\2\))?/},leftMatch:{},attrMap:{"class":"className","for":"htmlFor"},attrHandle:{href:function(a){return a.getAttribute("href")},type:function(a){return a.getAttribute("type")}},relative:{"+":function(a,b){var c=typeof b=="string",d=c&&!l.test(b),e=c&&!d;d&&(b=b.toLowerCase());for(var f=0,g=a.length,h;f<g;f++)if(h=a[f]){while((h=h.previousSibling)&&h.nodeType!==1);a[f]=e||h&&h.nodeName.toLowerCase()===b?h||!1:h===b}e&&m.filter(b,a,!0)},">":function(a,b){var c,d=typeof b=="string",e=0,f=a.length;if(d&&!l.test(b)){b=b.toLowerCase();for(;e<f;e++){c=a[e];if(c){var g=c.parentNode;a[e]=g.nodeName.toLowerCase()===b?g:!1}}}else{for(;e<f;e++)c=a[e],c&&(a[e]=d?c.parentNode:c.parentNode===b);d&&m.filter(b,a,!0)}},"":function(a,b,c){var d,f=e++,g=x;typeof b=="string"&&!l.test(b)&&(b=b.toLowerCase(),d=b,g=w),g("parentNode",b,f,a,d,c)},"~":function(a,b,c){var d,f=e++,g=x;typeof b=="string"&&!l.test(b)&&(b=b.toLowerCase(),d=b,g=w),g("previousSibling",b,f,a,d,c)}},find:{ID:function(a,b,c){if(typeof b.getElementById!="undefined"&&!c){var d=b.getElementById(a[1]);return d&&d.parentNode?[d]:[]}},NAME:function(a,b){if(typeof b.getElementsByName!="undefined"){var c=[],d=b.getElementsByName(a[1]);for(var e=0,f=d.length;e<f;e++)d[e].getAttribute("name")===a[1]&&c.push(d[e]);return c.length===0?null:c}},TAG:function(a,b){if(typeof b.getElementsByTagName!="undefined")return b.getElementsByTagName(a[1])}},preFilter:{CLASS:function(a,b,c,d,e,f){a=" "+a[1].replace(j,"")+" ";if(f)return a;for(var g=0,h;(h=b[g])!=null;g++)h&&(e^(h.className&&(" "+h.className+" ").replace(/[\t\n\r]/g," ").indexOf(a)>=0)?c||d.push(h):c&&(b[g]=!1));return!1},ID:function(a){return a[1].replace(j,"")},TAG:function(a,b){return a[1].replace(j,"").toLowerCase()},CHILD:function(a){if(a[1]==="nth"){a[2]||m.error(a[0]),a[2]=a[2].replace(/^\+|\s*/g,"");var b=/(-?)(\d*)(?:n([+\-]?\d*))?/.exec(a[2]==="even"&&"2n"||a[2]==="odd"&&"2n+1"||!/\D/.test(a[2])&&"0n+"+a[2]||a[2]);a[2]=b[1]+(b[2]||1)-0,a[3]=b[3]-0}else a[2]&&m.error(a[0]);a[0]=e++;return a},ATTR:function(a,b,c,d,e,f){var g=a[1]=a[1].replace(j,"");!f&&o.attrMap[g]&&(a[1]=o.attrMap[g]),a[4]=(a[4]||a[5]||"").replace(j,""),a[2]==="~="&&(a[4]=" "+a[4]+" ");return a},PSEUDO:function(b,c,d,e,f){if(b[1]==="not")if((a.exec(b[3])||"").length>1||/^\w/.test(b[3]))b[3]=m(b[3],null,null,c);else{var g=m.filter(b[3],c,d,!0^f);d||e.push.apply(e,g);return!1}else if(o.match.POS.test(b[0])||o.match.CHILD.test(b[0]))return!0;return b},POS:function(a){a.unshift(!0);return a}},filters:{enabled:function(a){return a.disabled===!1&&a.type!=="hidden"},disabled:function(a){return a.disabled===!0},checked:function(a){return a.checked===!0},selected:function(a){a.parentNode&&a.parentNode.selectedIndex;return a.selected===!0},parent:function(a){return!!a.firstChild},empty:function(a){return!a.firstChild},has:function(a,b,c){return!!m(c[3],a).length},header:function(a){return/h\d/i.test(a.nodeName)},text:function(a){var b=a.getAttribute("type"),c=a.type;return a.nodeName.toLowerCase()==="input"&&"text"===c&&(b===c||b===null)},radio:function(a){return a.nodeName.toLowerCase()==="input"&&"radio"===a.type},checkbox:function(a){return a.nodeName.toLowerCase()==="input"&&"checkbox"===a.type},file:function(a){return a.nodeName.toLowerCase()==="input"&&"file"===a.type},password:function(a){return a.nodeName.toLowerCase()==="input"&&"password"===a.type},submit:function(a){var b=a.nodeName.toLowerCase();return(b==="input"||b==="button")&&"submit"===a.type},image:function(a){return a.nodeName.toLowerCase()==="input"&&"image"===a.type},reset:function(a){var b=a.nodeName.toLowerCase();return(b==="input"||b==="button")&&"reset"===a.type},button:function(a){var b=a.nodeName.toLowerCase();return b==="input"&&"button"===a.type||b==="button"},input:function(a){return/input|select|textarea|button/i.test(a.nodeName)},focus:function(a){return a===a.ownerDocument.activeElement}},setFilters:{first:function(a,b){return b===0},last:function(a,b,c,d){return b===d.length-1},even:function(a,b){return b%2===0},odd:function(a,b){return b%2===1},lt:function(a,b,c){return b<c[3]-0},gt:function(a,b,c){return b>c[3]-0},nth:function(a,b,c){return c[3]-0===b},eq:function(a,b,c){return c[3]-0===b}},filter:{PSEUDO:function(a,b,c,d){var e=b[1],f=o.filters[e];if(f)return f(a,c,b,d);if(e==="contains")return(a.textContent||a.innerText||n([a])||"").indexOf(b[3])>=0;if(e==="not"){var g=b[3];for(var h=0,i=g.length;h<i;h++)if(g[h]===a)return!1;return!0}m.error(e)},CHILD:function(a,b){var c,e,f,g,h,i,j,k=b[1],l=a;switch(k){case"only":case"first":while(l=l.previousSibling)if(l.nodeType===1)return!1;if(k==="first")return!0;l=a;case"last":while(l=l.nextSibling)if(l.nodeType===1)return!1;return!0;case"nth":c=b[2],e=b[3];if(c===1&&e===0)return!0;f=b[0],g=a.parentNode;if(g&&(g[d]!==f||!a.nodeIndex)){i=0;for(l=g.firstChild;l;l=l.nextSibling)l.nodeType===1&&(l.nodeIndex=++i);g[d]=f}j=a.nodeIndex-e;return c===0?j===0:j%c===0&&j/c>=0}},ID:function(a,b){return a.nodeType===1&&a.getAttribute("id")===b},TAG:function(a,b){return b==="*"&&a.nodeType===1||!!a.nodeName&&a.nodeName.toLowerCase()===b},CLASS:function(a,b){return(" "+(a.className||a.getAttribute("class"))+" ").indexOf(b)>-1},ATTR:function(a,b){var c=b[1],d=m.attr?m.attr(a,c):o.attrHandle[c]?o.attrHandle[c](a):a[c]!=null?a[c]:a.getAttribute(c),e=d+"",f=b[2],g=b[4];return d==null?f==="!=":!f&&m.attr?d!=null:f==="="?e===g:f==="*="?e.indexOf(g)>=0:f==="~="?(" "+e+" ").indexOf(g)>=0:g?f==="!="?e!==g:f==="^="?e.indexOf(g)===0:f==="$="?e.substr(e.length-g.length)===g:f==="|="?e===g||e.substr(0,g.length+1)===g+"-":!1:e&&d!==!1},POS:function(a,b,c,d){var e=b[2],f=o.setFilters[e];if(f)return f(a,c,b,d)}}},p=o.match.POS,q=function(a,b){return"\\"+(b-0+1)};for(var r in o.match)o.match[r]=new RegExp(o.match[r].source+/(?![^\[]*\])(?![^\(]*\))/.source),o.leftMatch[r]=new RegExp(/(^(?:.|\r|\n)*?)/.source+o.match[r].source.replace(/\\(\d+)/g,q));o.match.globalPOS=p;var s=function(a,b){a=Array.prototype.slice.call(a,0);if(b){b.push.apply(b,a);return b}return a};try{Array.prototype.slice.call(c.documentElement.childNodes,0)[0].nodeType}catch(t){s=function(a,b){var c=0,d=b||[];if(g.call(a)==="[object Array]")Array.prototype.push.apply(d,a);else if(typeof a.length=="number")for(var e=a.length;c<e;c++)d.push(a[c]);else for(;a[c];c++)d.push(a[c]);return d}}var u,v;c.documentElement.compareDocumentPosition?u=function(a,b){if(a===b){h=!0;return 0}if(!a.compareDocumentPosition||!b.compareDocumentPosition)return a.compareDocumentPosition?-1:1;return a.compareDocumentPosition(b)&4?-1:1}:(u=function(a,b){if(a===b){h=!0;return 0}if(a.sourceIndex&&b.sourceIndex)return a.sourceIndex-b.sourceIndex;var c,d,e=[],f=[],g=a.parentNode,i=b.parentNode,j=g;if(g===i)return v(a,b);if(!g)return-1;if(!i)return 1;while(j)e.unshift(j),j=j.parentNode;j=i;while(j)f.unshift(j),j=j.parentNode;c=e.length,d=f.length;for(var k=0;k<c&&k<d;k++)if(e[k]!==f[k])return v(e[k],f[k]);return k===c?v(a,f[k],-1):v(e[k],b,1)},v=function(a,b,c){if(a===b)return c;var d=a.nextSibling;while(d){if(d===b)return-1;d=d.nextSibling}return 1}),function(){var a=c.createElement("div"),d="script"+(new Date).getTime(),e=c.documentElement;a.innerHTML="<a name='"+d+"'/>",e.insertBefore(a,e.firstChild),c.getElementById(d)&&(o.find.ID=function(a,c,d){if(typeof c.getElementById!="undefined"&&!d){var e=c.getElementById(a[1]);return e?e.id===a[1]||typeof e.getAttributeNode!="undefined"&&e.getAttributeNode("id").nodeValue===a[1]?[e]:b:[]}},o.filter.ID=function(a,b){var c=typeof a.getAttributeNode!="undefined"&&a.getAttributeNode("id");return a.nodeType===1&&c&&c.nodeValue===b}),e.removeChild(a),e=a=null}(),function(){var a=c.createElement("div");a.appendChild(c.createComment("")),a.getElementsByTagName("*").length>0&&(o.find.TAG=function(a,b){var c=b.getElementsByTagName(a[1]);if(a[1]==="*"){var d=[];for(var e=0;c[e];e++)c[e].nodeType===1&&d.push(c[e]);c=d}return c}),a.innerHTML="<a href='#'></a>",a.firstChild&&typeof a.firstChild.getAttribute!="undefined"&&a.firstChild.getAttribute("href")!=="#"&&(o.attrHandle.href=function(a){return a.getAttribute("href",2)}),a=null}(),c.querySelectorAll&&function(){var a=m,b=c.createElement("div"),d="__sizzle__";b.innerHTML="<p class='TEST'></p>";if(!b.querySelectorAll||b.querySelectorAll(".TEST").length!==0){m=function(b,e,f,g){e=e||c;if(!g&&!m.isXML(e)){var h=/^(\w+$)|^\.([\w\-]+$)|^#([\w\-]+$)/.exec(b);if(h&&(e.nodeType===1||e.nodeType===9)){if(h[1])return s(e.getElementsByTagName(b),f);if(h[2]&&o.find.CLASS&&e.getElementsByClassName)return s(e.getElementsByClassName(h[2]),f)}if(e.nodeType===9){if(b==="body"&&e.body)return s([e.body],f);if(h&&h[3]){var i=e.getElementById(h[3]);if(!i||!i.parentNode)return s([],f);if(i.id===h[3])return s([i],f)}try{return s(e.querySelectorAll(b),f)}catch(j){}}else if(e.nodeType===1&&e.nodeName.toLowerCase()!=="object"){var k=e,l=e.getAttribute("id"),n=l||d,p=e.parentNode,q=/^\s*[+~]/.test(b);l?n=n.replace(/'/g,"\\$&"):e.setAttribute("id",n),q&&p&&(e=e.parentNode);try{if(!q||p)return s(e.querySelectorAll("[id='"+n+"'] "+b),f)}catch(r){}finally{l||k.removeAttribute("id")}}}return a(b,e,f,g)};for(var e in a)m[e]=a[e];b=null}}(),function(){var a=c.documentElement,b=a.matchesSelector||a.mozMatchesSelector||a.webkitMatchesSelector||a.msMatchesSelector;if(b){var d=!b.call(c.createElement("div"),"div"),e=!1;try{b.call(c.documentElement,"[test!='']:sizzle")}catch(f){e=!0}m.matchesSelector=function(a,c){c=c.replace(/\=\s*([^'"\]]*)\s*\]/g,"='$1']");if(!m.isXML(a))try{if(e||!o.match.PSEUDO.test(c)&&!/!=/.test(c)){var f=b.call(a,c);if(f||!d||a.document&&a.document.nodeType!==11)return f}}catch(g){}return m(c,null,null,[a]).length>0}}}(),function(){var a=c.createElement("div");a.innerHTML="<div class='test e'></div><div class='test'></div>";if(!!a.getElementsByClassName&&a.getElementsByClassName("e").length!==0){a.lastChild.className="e";if(a.getElementsByClassName("e").length===1)return;o.order.splice(1,0,"CLASS"),o.find.CLASS=function(a,b,c){if(typeof b.getElementsByClassName!="undefined"&&!c)return b.getElementsByClassName(a[1])},a=null}}(),c.documentElement.contains?m.contains=function(a,b){return a!==b&&(a.contains?a.contains(b):!0)}:c.documentElement.compareDocumentPosition?m.contains=function(a,b){return!!(a.compareDocumentPosition(b)&16)}:m.contains=function(){return!1},m.isXML=function(a){var b=(a?a.ownerDocument||a:0).documentElement;return b?b.nodeName!=="HTML":!1};var y=function(a,b,c){var d,e=[],f="",g=b.nodeType?[b]:b;while(d=o.match.PSEUDO.exec(a))f+=d[0],a=a.replace(o.match.PSEUDO,"");a=o.relative[a]?a+"*":a;for(var h=0,i=g.length;h<i;h++)m(a,g[h],e,c);return m.filter(f,e)};m.attr=f.attr,m.selectors.attrMap={},f.find=m,f.expr=m.selectors,f.expr[":"]=f.expr.filters,f.unique=m.uniqueSort,f.text=m.getText,f.isXMLDoc=m.isXML,f.contains=m.contains}();var L=/Until$/,M=/^(?:parents|prevUntil|prevAll)/,N=/,/,O=/^.[^:#\[\.,]*$/,P=Array.prototype.slice,Q=f.expr.match.globalPOS,R={children:!0,contents:!0,next:!0,prev:!0};f.fn.extend({find:function(a){var b=this,c,d;if(typeof a!="string")return f(a).filter(function(){for(c=0,d=b.length;c<d;c++)if(f.contains(b[c],this))return!0});var e=this.pushStack("","find",a),g,h,i;for(c=0,d=this.length;c<d;c++){g=e.length,f.find(a,this[c],e);if(c>0)for(h=g;h<e.length;h++)for(i=0;i<g;i++)if(e[i]===e[h]){e.splice(h--,1);break}}return e},has:function(a){var b=f(a);return this.filter(function(){for(var a=0,c=b.length;a<c;a++)if(f.contains(this,b[a]))return!0})},not:function(a){return this.pushStack(T(this,a,!1),"not",a)},filter:function(a){return this.pushStack(T(this,a,!0),"filter",a)},is:function(a){return!!a&&(typeof a=="string"?Q.test(a)?f(a,this.context).index(this[0])>=0:f.filter(a,this).length>0:this.filter(a).length>0)},closest:function(a,b){var c=[],d,e,g=this[0];if(f.isArray(a)){var h=1;while(g&&g.ownerDocument&&g!==b){for(d=0;d<a.length;d++)f(g).is(a[d])&&c.push({selector:a[d],elem:g,level:h});g=g.parentNode,h++}return c}var i=Q.test(a)||typeof a!="string"?f(a,b||this.context):0;for(d=0,e=this.length;d<e;d++){g=this[d];while(g){if(i?i.index(g)>-1:f.find.matchesSelector(g,a)){c.push(g);break}g=g.parentNode;if(!g||!g.ownerDocument||g===b||g.nodeType===11)break}}c=c.length>1?f.unique(c):c;return this.pushStack(c,"closest",a)},index:function(a){if(!a)return this[0]&&this[0].parentNode?this.prevAll().length:-1;if(typeof a=="string")return f.inArray(this[0],f(a));return f.inArray(a.jquery?a[0]:a,this)},add:function(a,b){var c=typeof a=="string"?f(a,b):f.makeArray(a&&a.nodeType?[a]:a),d=f.merge(this.get(),c);return this.pushStack(S(c[0])||S(d[0])?d:f.unique(d))},andSelf:function(){return this.add(this.prevObject)}}),f.each({parent:function(a){var b=a.parentNode;return b&&b.nodeType!==11?b:null},parents:function(a){return f.dir(a,"parentNode")},parentsUntil:function(a,b,c){return f.dir(a,"parentNode",c)},next:function(a){return f.nth(a,2,"nextSibling")},prev:function(a){return f.nth(a,2,"previousSibling")},nextAll:function(a){return f.dir(a,"nextSibling")},prevAll:function(a){return f.dir(a,"previousSibling")},nextUntil:function(a,b,c){return f.dir(a,"nextSibling",c)},prevUntil:function(a,b,c){return f.dir(a,"previousSibling",c)},siblings:function(a){return f.sibling((a.parentNode||{}).firstChild,a)},children:function(a){return f.sibling(a.firstChild)},contents:function(a){return f.nodeName(a,"iframe")?a.contentDocument||a.contentWindow.document:f.makeArray(a.childNodes)}},function(a,b){f.fn[a]=function(c,d){var e=f.map(this,b,c);L.test(a)||(d=c),d&&typeof d=="string"&&(e=f.filter(d,e)),e=this.length>1&&!R[a]?f.unique(e):e,(this.length>1||N.test(d))&&M.test(a)&&(e=e.reverse());return this.pushStack(e,a,P.call(arguments).join(","))}}),f.extend({filter:function(a,b,c){c&&(a=":not("+a+")");return b.length===1?f.find.matchesSelector(b[0],a)?[b[0]]:[]:f.find.matches(a,b)},dir:function(a,c,d){var e=[],g=a[c];while(g&&g.nodeType!==9&&(d===b||g.nodeType!==1||!f(g).is(d)))g.nodeType===1&&e.push(g),g=g[c];return e},nth:function(a,b,c,d){b=b||1;var e=0;for(;a;a=a[c])if(a.nodeType===1&&++e===b)break;return a},sibling:function(a,b){var c=[];for(;a;a=a.nextSibling)a.nodeType===1&&a!==b&&c.push(a);return c}});var V="abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",W=/ jQuery\d+="(?:\d+|null)"/g,X=/^\s+/,Y=/<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/ig,Z=/<([\w:]+)/,$=/<tbody/i,_=/<|&#?\w+;/,ba=/<(?:script|style)/i,bb=/<(?:script|object|embed|option|style)/i,bc=new RegExp("<(?:"+V+")[\\s/>]","i"),bd=/checked\s*(?:[^=]|=\s*.checked.)/i,be=/\/(java|ecma)script/i,bf=/^\s*<!(?:\[CDATA\[|\-\-)/,bg={option:[1,"<select multiple='multiple'>","</select>"],legend:[1,"<fieldset>","</fieldset>"],thead:[1,"<table>","</table>"],tr:[2,"<table><tbody>","</tbody></table>"],td:[3,"<table><tbody><tr>","</tr></tbody></table>"],col:[2,"<table><tbody></tbody><colgroup>","</colgroup></table>"],area:[1,"<map>","</map>"],_default:[0,"",""]},bh=U(c);bg.optgroup=bg.option,bg.tbody=bg.tfoot=bg.colgroup=bg.caption=bg.thead,bg.th=bg.td,f.support.htmlSerialize||(bg._default=[1,"div<div>","</div>"]),f.fn.extend({text:function(a){return f.access(this,function(a){return a===b?f.text(this):this.empty().append((this[0]&&this[0].ownerDocument||c).createTextNode(a))},null,a,arguments.length)},wrapAll:function(a){if(f.isFunction(a))return this.each(function(b){f(this).wrapAll(a.call(this,b))});if(this[0]){var b=f(a,this[0].ownerDocument).eq(0).clone(!0);this[0].parentNode&&b.insertBefore(this[0]),b.map(function(){var a=this;while(a.firstChild&&a.firstChild.nodeType===1)a=a.firstChild;return a}).append(this)}return this},wrapInner:function(a){if(f.isFunction(a))return this.each(function(b){f(this).wrapInner(a.call(this,b))});return this.each(function(){var b=f(this),c=b.contents();c.length?c.wrapAll(a):b.append(a)})},wrap:function(a){var b=f.isFunction(a);return this.each(function(c){f(this).wrapAll(b?a.call(this,c):a)})},unwrap:function(){return this.parent().each(function(){f.nodeName(this,"body")||f(this).replaceWith(this.childNodes)}).end()},append:function(){return this.domManip(arguments,!0,function(a){this.nodeType===1&&this.appendChild(a)})},prepend:function(){return this.domManip(arguments,!0,function(a){this.nodeType===1&&this.insertBefore(a,this.firstChild)})},before:function(){if(this[0]&&this[0].parentNode)return this.domManip(arguments,!1,function(a){this.parentNode.insertBefore(a,this)});if(arguments.length){var a=f
.clean(arguments);a.push.apply(a,this.toArray());return this.pushStack(a,"before",arguments)}},after:function(){if(this[0]&&this[0].parentNode)return this.domManip(arguments,!1,function(a){this.parentNode.insertBefore(a,this.nextSibling)});if(arguments.length){var a=this.pushStack(this,"after",arguments);a.push.apply(a,f.clean(arguments));return a}},remove:function(a,b){for(var c=0,d;(d=this[c])!=null;c++)if(!a||f.filter(a,[d]).length)!b&&d.nodeType===1&&(f.cleanData(d.getElementsByTagName("*")),f.cleanData([d])),d.parentNode&&d.parentNode.removeChild(d);return this},empty:function(){for(var a=0,b;(b=this[a])!=null;a++){b.nodeType===1&&f.cleanData(b.getElementsByTagName("*"));while(b.firstChild)b.removeChild(b.firstChild)}return this},clone:function(a,b){a=a==null?!1:a,b=b==null?a:b;return this.map(function(){return f.clone(this,a,b)})},html:function(a){return f.access(this,function(a){var c=this[0]||{},d=0,e=this.length;if(a===b)return c.nodeType===1?c.innerHTML.replace(W,""):null;if(typeof a=="string"&&!ba.test(a)&&(f.support.leadingWhitespace||!X.test(a))&&!bg[(Z.exec(a)||["",""])[1].toLowerCase()]){a=a.replace(Y,"<$1></$2>");try{for(;d<e;d++)c=this[d]||{},c.nodeType===1&&(f.cleanData(c.getElementsByTagName("*")),c.innerHTML=a);c=0}catch(g){}}c&&this.empty().append(a)},null,a,arguments.length)},replaceWith:function(a){if(this[0]&&this[0].parentNode){if(f.isFunction(a))return this.each(function(b){var c=f(this),d=c.html();c.replaceWith(a.call(this,b,d))});typeof a!="string"&&(a=f(a).detach());return this.each(function(){var b=this.nextSibling,c=this.parentNode;f(this).remove(),b?f(b).before(a):f(c).append(a)})}return this.length?this.pushStack(f(f.isFunction(a)?a():a),"replaceWith",a):this},detach:function(a){return this.remove(a,!0)},domManip:function(a,c,d){var e,g,h,i,j=a[0],k=[];if(!f.support.checkClone&&arguments.length===3&&typeof j=="string"&&bd.test(j))return this.each(function(){f(this).domManip(a,c,d,!0)});if(f.isFunction(j))return this.each(function(e){var g=f(this);a[0]=j.call(this,e,c?g.html():b),g.domManip(a,c,d)});if(this[0]){i=j&&j.parentNode,f.support.parentNode&&i&&i.nodeType===11&&i.childNodes.length===this.length?e={fragment:i}:e=f.buildFragment(a,this,k),h=e.fragment,h.childNodes.length===1?g=h=h.firstChild:g=h.firstChild;if(g){c=c&&f.nodeName(g,"tr");for(var l=0,m=this.length,n=m-1;l<m;l++)d.call(c?bi(this[l],g):this[l],e.cacheable||m>1&&l<n?f.clone(h,!0,!0):h)}k.length&&f.each(k,function(a,b){b.src?f.ajax({type:"GET",global:!1,url:b.src,async:!1,dataType:"script"}):f.globalEval((b.text||b.textContent||b.innerHTML||"").replace(bf,"/*$0*/")),b.parentNode&&b.parentNode.removeChild(b)})}return this}}),f.buildFragment=function(a,b,d){var e,g,h,i,j=a[0];b&&b[0]&&(i=b[0].ownerDocument||b[0]),i.createDocumentFragment||(i=c),a.length===1&&typeof j=="string"&&j.length<512&&i===c&&j.charAt(0)==="<"&&!bb.test(j)&&(f.support.checkClone||!bd.test(j))&&(f.support.html5Clone||!bc.test(j))&&(g=!0,h=f.fragments[j],h&&h!==1&&(e=h)),e||(e=i.createDocumentFragment(),f.clean(a,i,e,d)),g&&(f.fragments[j]=h?e:1);return{fragment:e,cacheable:g}},f.fragments={},f.each({appendTo:"append",prependTo:"prepend",insertBefore:"before",insertAfter:"after",replaceAll:"replaceWith"},function(a,b){f.fn[a]=function(c){var d=[],e=f(c),g=this.length===1&&this[0].parentNode;if(g&&g.nodeType===11&&g.childNodes.length===1&&e.length===1){e[b](this[0]);return this}for(var h=0,i=e.length;h<i;h++){var j=(h>0?this.clone(!0):this).get();f(e[h])[b](j),d=d.concat(j)}return this.pushStack(d,a,e.selector)}}),f.extend({clone:function(a,b,c){var d,e,g,h=f.support.html5Clone||f.isXMLDoc(a)||!bc.test("<"+a.nodeName+">")?a.cloneNode(!0):bo(a);if((!f.support.noCloneEvent||!f.support.noCloneChecked)&&(a.nodeType===1||a.nodeType===11)&&!f.isXMLDoc(a)){bk(a,h),d=bl(a),e=bl(h);for(g=0;d[g];++g)e[g]&&bk(d[g],e[g])}if(b){bj(a,h);if(c){d=bl(a),e=bl(h);for(g=0;d[g];++g)bj(d[g],e[g])}}d=e=null;return h},clean:function(a,b,d,e){var g,h,i,j=[];b=b||c,typeof b.createElement=="undefined"&&(b=b.ownerDocument||b[0]&&b[0].ownerDocument||c);for(var k=0,l;(l=a[k])!=null;k++){typeof l=="number"&&(l+="");if(!l)continue;if(typeof l=="string")if(!_.test(l))l=b.createTextNode(l);else{l=l.replace(Y,"<$1></$2>");var m=(Z.exec(l)||["",""])[1].toLowerCase(),n=bg[m]||bg._default,o=n[0],p=b.createElement("div"),q=bh.childNodes,r;b===c?bh.appendChild(p):U(b).appendChild(p),p.innerHTML=n[1]+l+n[2];while(o--)p=p.lastChild;if(!f.support.tbody){var s=$.test(l),t=m==="table"&&!s?p.firstChild&&p.firstChild.childNodes:n[1]==="<table>"&&!s?p.childNodes:[];for(i=t.length-1;i>=0;--i)f.nodeName(t[i],"tbody")&&!t[i].childNodes.length&&t[i].parentNode.removeChild(t[i])}!f.support.leadingWhitespace&&X.test(l)&&p.insertBefore(b.createTextNode(X.exec(l)[0]),p.firstChild),l=p.childNodes,p&&(p.parentNode.removeChild(p),q.length>0&&(r=q[q.length-1],r&&r.parentNode&&r.parentNode.removeChild(r)))}var u;if(!f.support.appendChecked)if(l[0]&&typeof (u=l.length)=="number")for(i=0;i<u;i++)bn(l[i]);else bn(l);l.nodeType?j.push(l):j=f.merge(j,l)}if(d){g=function(a){return!a.type||be.test(a.type)};for(k=0;j[k];k++){h=j[k];if(e&&f.nodeName(h,"script")&&(!h.type||be.test(h.type)))e.push(h.parentNode?h.parentNode.removeChild(h):h);else{if(h.nodeType===1){var v=f.grep(h.getElementsByTagName("script"),g);j.splice.apply(j,[k+1,0].concat(v))}d.appendChild(h)}}}return j},cleanData:function(a){var b,c,d=f.cache,e=f.event.special,g=f.support.deleteExpando;for(var h=0,i;(i=a[h])!=null;h++){if(i.nodeName&&f.noData[i.nodeName.toLowerCase()])continue;c=i[f.expando];if(c){b=d[c];if(b&&b.events){for(var j in b.events)e[j]?f.event.remove(i,j):f.removeEvent(i,j,b.handle);b.handle&&(b.handle.elem=null)}g?delete i[f.expando]:i.removeAttribute&&i.removeAttribute(f.expando),delete d[c]}}}});var bp=/alpha\([^)]*\)/i,bq=/opacity=([^)]*)/,br=/([A-Z]|^ms)/g,bs=/^[\-+]?(?:\d*\.)?\d+$/i,bt=/^-?(?:\d*\.)?\d+(?!px)[^\d\s]+$/i,bu=/^([\-+])=([\-+.\de]+)/,bv=/^margin/,bw={position:"absolute",visibility:"hidden",display:"block"},bx=["Top","Right","Bottom","Left"],by,bz,bA;f.fn.css=function(a,c){return f.access(this,function(a,c,d){return d!==b?f.style(a,c,d):f.css(a,c)},a,c,arguments.length>1)},f.extend({cssHooks:{opacity:{get:function(a,b){if(b){var c=by(a,"opacity");return c===""?"1":c}return a.style.opacity}}},cssNumber:{fillOpacity:!0,fontWeight:!0,lineHeight:!0,opacity:!0,orphans:!0,widows:!0,zIndex:!0,zoom:!0},cssProps:{"float":f.support.cssFloat?"cssFloat":"styleFloat"},style:function(a,c,d,e){if(!!a&&a.nodeType!==3&&a.nodeType!==8&&!!a.style){var g,h,i=f.camelCase(c),j=a.style,k=f.cssHooks[i];c=f.cssProps[i]||i;if(d===b){if(k&&"get"in k&&(g=k.get(a,!1,e))!==b)return g;return j[c]}h=typeof d,h==="string"&&(g=bu.exec(d))&&(d=+(g[1]+1)*+g[2]+parseFloat(f.css(a,c)),h="number");if(d==null||h==="number"&&isNaN(d))return;h==="number"&&!f.cssNumber[i]&&(d+="px");if(!k||!("set"in k)||(d=k.set(a,d))!==b)try{j[c]=d}catch(l){}}},css:function(a,c,d){var e,g;c=f.camelCase(c),g=f.cssHooks[c],c=f.cssProps[c]||c,c==="cssFloat"&&(c="float");if(g&&"get"in g&&(e=g.get(a,!0,d))!==b)return e;if(by)return by(a,c)},swap:function(a,b,c){var d={},e,f;for(f in b)d[f]=a.style[f],a.style[f]=b[f];e=c.call(a);for(f in b)a.style[f]=d[f];return e}}),f.curCSS=f.css,c.defaultView&&c.defaultView.getComputedStyle&&(bz=function(a,b){var c,d,e,g,h=a.style;b=b.replace(br,"-$1").toLowerCase(),(d=a.ownerDocument.defaultView)&&(e=d.getComputedStyle(a,null))&&(c=e.getPropertyValue(b),c===""&&!f.contains(a.ownerDocument.documentElement,a)&&(c=f.style(a,b))),!f.support.pixelMargin&&e&&bv.test(b)&&bt.test(c)&&(g=h.width,h.width=c,c=e.width,h.width=g);return c}),c.documentElement.currentStyle&&(bA=function(a,b){var c,d,e,f=a.currentStyle&&a.currentStyle[b],g=a.style;f==null&&g&&(e=g[b])&&(f=e),bt.test(f)&&(c=g.left,d=a.runtimeStyle&&a.runtimeStyle.left,d&&(a.runtimeStyle.left=a.currentStyle.left),g.left=b==="fontSize"?"1em":f,f=g.pixelLeft+"px",g.left=c,d&&(a.runtimeStyle.left=d));return f===""?"auto":f}),by=bz||bA,f.each(["height","width"],function(a,b){f.cssHooks[b]={get:function(a,c,d){if(c)return a.offsetWidth!==0?bB(a,b,d):f.swap(a,bw,function(){return bB(a,b,d)})},set:function(a,b){return bs.test(b)?b+"px":b}}}),f.support.opacity||(f.cssHooks.opacity={get:function(a,b){return bq.test((b&&a.currentStyle?a.currentStyle.filter:a.style.filter)||"")?parseFloat(RegExp.$1)/100+"":b?"1":""},set:function(a,b){var c=a.style,d=a.currentStyle,e=f.isNumeric(b)?"alpha(opacity="+b*100+")":"",g=d&&d.filter||c.filter||"";c.zoom=1;if(b>=1&&f.trim(g.replace(bp,""))===""){c.removeAttribute("filter");if(d&&!d.filter)return}c.filter=bp.test(g)?g.replace(bp,e):g+" "+e}}),f(function(){f.support.reliableMarginRight||(f.cssHooks.marginRight={get:function(a,b){return f.swap(a,{display:"inline-block"},function(){return b?by(a,"margin-right"):a.style.marginRight})}})}),f.expr&&f.expr.filters&&(f.expr.filters.hidden=function(a){var b=a.offsetWidth,c=a.offsetHeight;return b===0&&c===0||!f.support.reliableHiddenOffsets&&(a.style&&a.style.display||f.css(a,"display"))==="none"},f.expr.filters.visible=function(a){return!f.expr.filters.hidden(a)}),f.each({margin:"",padding:"",border:"Width"},function(a,b){f.cssHooks[a+b]={expand:function(c){var d,e=typeof c=="string"?c.split(" "):[c],f={};for(d=0;d<4;d++)f[a+bx[d]+b]=e[d]||e[d-2]||e[0];return f}}});var bC=/%20/g,bD=/\[\]$/,bE=/\r?\n/g,bF=/#.*$/,bG=/^(.*?):[ \t]*([^\r\n]*)\r?$/mg,bH=/^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|range|search|tel|text|time|url|week)$/i,bI=/^(?:about|app|app\-storage|.+\-extension|file|res|widget):$/,bJ=/^(?:GET|HEAD)$/,bK=/^\/\//,bL=/\?/,bM=/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi,bN=/^(?:select|textarea)/i,bO=/\s+/,bP=/([?&])_=[^&]*/,bQ=/^([\w\+\.\-]+:)(?:\/\/([^\/?#:]*)(?::(\d+))?)?/,bR=f.fn.load,bS={},bT={},bU,bV,bW=["*/"]+["*"];try{bU=e.href}catch(bX){bU=c.createElement("a"),bU.href="",bU=bU.href}bV=bQ.exec(bU.toLowerCase())||[],f.fn.extend({load:function(a,c,d){if(typeof a!="string"&&bR)return bR.apply(this,arguments);if(!this.length)return this;var e=a.indexOf(" ");if(e>=0){var g=a.slice(e,a.length);a=a.slice(0,e)}var h="GET";c&&(f.isFunction(c)?(d=c,c=b):typeof c=="object"&&(c=f.param(c,f.ajaxSettings.traditional),h="POST"));var i=this;f.ajax({url:a,type:h,dataType:"html",data:c,complete:function(a,b,c){c=a.responseText,a.isResolved()&&(a.done(function(a){c=a}),i.html(g?f("<div>").append(c.replace(bM,"")).find(g):c)),d&&i.each(d,[c,b,a])}});return this},serialize:function(){return f.param(this.serializeArray())},serializeArray:function(){return this.map(function(){return this.elements?f.makeArray(this.elements):this}).filter(function(){return this.name&&!this.disabled&&(this.checked||bN.test(this.nodeName)||bH.test(this.type))}).map(function(a,b){var c=f(this).val();return c==null?null:f.isArray(c)?f.map(c,function(a,c){return{name:b.name,value:a.replace(bE,"\r\n")}}):{name:b.name,value:c.replace(bE,"\r\n")}}).get()}}),f.each("ajaxStart ajaxStop ajaxComplete ajaxError ajaxSuccess ajaxSend".split(" "),function(a,b){f.fn[b]=function(a){return this.on(b,a)}}),f.each(["get","post"],function(a,c){f[c]=function(a,d,e,g){f.isFunction(d)&&(g=g||e,e=d,d=b);return f.ajax({type:c,url:a,data:d,success:e,dataType:g})}}),f.extend({getScript:function(a,c){return f.get(a,b,c,"script")},getJSON:function(a,b,c){return f.get(a,b,c,"json")},ajaxSetup:function(a,b){b?b$(a,f.ajaxSettings):(b=a,a=f.ajaxSettings),b$(a,b);return a},ajaxSettings:{url:bU,isLocal:bI.test(bV[1]),global:!0,type:"GET",contentType:"application/x-www-form-urlencoded; charset=UTF-8",processData:!0,async:!0,accepts:{xml:"application/xml, text/xml",html:"text/html",text:"text/plain",json:"application/json, text/javascript","*":bW},contents:{xml:/xml/,html:/html/,json:/json/},responseFields:{xml:"responseXML",text:"responseText"},converters:{"* text":a.String,"text html":!0,"text json":f.parseJSON,"text xml":f.parseXML},flatOptions:{context:!0,url:!0}},ajaxPrefilter:bY(bS),ajaxTransport:bY(bT),ajax:function(a,c){function w(a,c,l,m){if(s!==2){s=2,q&&clearTimeout(q),p=b,n=m||"",v.readyState=a>0?4:0;var o,r,u,w=c,x=l?ca(d,v,l):b,y,z;if(a>=200&&a<300||a===304){if(d.ifModified){if(y=v.getResponseHeader("Last-Modified"))f.lastModified[k]=y;if(z=v.getResponseHeader("Etag"))f.etag[k]=z}if(a===304)w="notmodified",o=!0;else try{r=cb(d,x),w="success",o=!0}catch(A){w="parsererror",u=A}}else{u=w;if(!w||a)w="error",a<0&&(a=0)}v.status=a,v.statusText=""+(c||w),o?h.resolveWith(e,[r,w,v]):h.rejectWith(e,[v,w,u]),v.statusCode(j),j=b,t&&g.trigger("ajax"+(o?"Success":"Error"),[v,d,o?r:u]),i.fireWith(e,[v,w]),t&&(g.trigger("ajaxComplete",[v,d]),--f.active||f.event.trigger("ajaxStop"))}}typeof a=="object"&&(c=a,a=b),c=c||{};var d=f.ajaxSetup({},c),e=d.context||d,g=e!==d&&(e.nodeType||e instanceof f)?f(e):f.event,h=f.Deferred(),i=f.Callbacks("once memory"),j=d.statusCode||{},k,l={},m={},n,o,p,q,r,s=0,t,u,v={readyState:0,setRequestHeader:function(a,b){if(!s){var c=a.toLowerCase();a=m[c]=m[c]||a,l[a]=b}return this},getAllResponseHeaders:function(){return s===2?n:null},getResponseHeader:function(a){var c;if(s===2){if(!o){o={};while(c=bG.exec(n))o[c[1].toLowerCase()]=c[2]}c=o[a.toLowerCase()]}return c===b?null:c},overrideMimeType:function(a){s||(d.mimeType=a);return this},abort:function(a){a=a||"abort",p&&p.abort(a),w(0,a);return this}};h.promise(v),v.success=v.done,v.error=v.fail,v.complete=i.add,v.statusCode=function(a){if(a){var b;if(s<2)for(b in a)j[b]=[j[b],a[b]];else b=a[v.status],v.then(b,b)}return this},d.url=((a||d.url)+"").replace(bF,"").replace(bK,bV[1]+"//"),d.dataTypes=f.trim(d.dataType||"*").toLowerCase().split(bO),d.crossDomain==null&&(r=bQ.exec(d.url.toLowerCase()),d.crossDomain=!(!r||r[1]==bV[1]&&r[2]==bV[2]&&(r[3]||(r[1]==="http:"?80:443))==(bV[3]||(bV[1]==="http:"?80:443)))),d.data&&d.processData&&typeof d.data!="string"&&(d.data=f.param(d.data,d.traditional)),bZ(bS,d,c,v);if(s===2)return!1;t=d.global,d.type=d.type.toUpperCase(),d.hasContent=!bJ.test(d.type),t&&f.active++===0&&f.event.trigger("ajaxStart");if(!d.hasContent){d.data&&(d.url+=(bL.test(d.url)?"&":"?")+d.data,delete d.data),k=d.url;if(d.cache===!1){var x=f.now(),y=d.url.replace(bP,"$1_="+x);d.url=y+(y===d.url?(bL.test(d.url)?"&":"?")+"_="+x:"")}}(d.data&&d.hasContent&&d.contentType!==!1||c.contentType)&&v.setRequestHeader("Content-Type",d.contentType),d.ifModified&&(k=k||d.url,f.lastModified[k]&&v.setRequestHeader("If-Modified-Since",f.lastModified[k]),f.etag[k]&&v.setRequestHeader("If-None-Match",f.etag[k])),v.setRequestHeader("Accept",d.dataTypes[0]&&d.accepts[d.dataTypes[0]]?d.accepts[d.dataTypes[0]]+(d.dataTypes[0]!=="*"?", "+bW+"; q=0.01":""):d.accepts["*"]);for(u in d.headers)v.setRequestHeader(u,d.headers[u]);if(d.beforeSend&&(d.beforeSend.call(e,v,d)===!1||s===2)){v.abort();return!1}for(u in{success:1,error:1,complete:1})v[u](d[u]);p=bZ(bT,d,c,v);if(!p)w(-1,"No Transport");else{v.readyState=1,t&&g.trigger("ajaxSend",[v,d]),d.async&&d.timeout>0&&(q=setTimeout(function(){v.abort("timeout")},d.timeout));try{s=1,p.send(l,w)}catch(z){if(s<2)w(-1,z);else throw z}}return v},param:function(a,c){var d=[],e=function(a,b){b=f.isFunction(b)?b():b,d[d.length]=encodeURIComponent(a)+"="+encodeURIComponent(b)};c===b&&(c=f.ajaxSettings.traditional);if(f.isArray(a)||a.jquery&&!f.isPlainObject(a))f.each(a,function(){e(this.name,this.value)});else for(var g in a)b_(g,a[g],c,e);return d.join("&").replace(bC,"+")}}),f.extend({active:0,lastModified:{},etag:{}});var cc=f.now(),cd=/(\=)\?(&|$)|\?\?/i;f.ajaxSetup({jsonp:"callback",jsonpCallback:function(){return f.expando+"_"+cc++}}),f.ajaxPrefilter("json jsonp",function(b,c,d){var e=typeof b.data=="string"&&/^application\/x\-www\-form\-urlencoded/.test(b.contentType);if(b.dataTypes[0]==="jsonp"||b.jsonp!==!1&&(cd.test(b.url)||e&&cd.test(b.data))){var g,h=b.jsonpCallback=f.isFunction(b.jsonpCallback)?b.jsonpCallback():b.jsonpCallback,i=a[h],j=b.url,k=b.data,l="$1"+h+"$2";b.jsonp!==!1&&(j=j.replace(cd,l),b.url===j&&(e&&(k=k.replace(cd,l)),b.data===k&&(j+=(/\?/.test(j)?"&":"?")+b.jsonp+"="+h))),b.url=j,b.data=k,a[h]=function(a){g=[a]},d.always(function(){a[h]=i,g&&f.isFunction(i)&&a[h](g[0])}),b.converters["script json"]=function(){g||f.error(h+" was not called");return g[0]},b.dataTypes[0]="json";return"script"}}),f.ajaxSetup({accepts:{script:"text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},contents:{script:/javascript|ecmascript/},converters:{"text script":function(a){f.globalEval(a);return a}}}),f.ajaxPrefilter("script",function(a){a.cache===b&&(a.cache=!1),a.crossDomain&&(a.type="GET",a.global=!1)}),f.ajaxTransport("script",function(a){if(a.crossDomain){var d,e=c.head||c.getElementsByTagName("head")[0]||c.documentElement;return{send:function(f,g){d=c.createElement("script"),d.async="async",a.scriptCharset&&(d.charset=a.scriptCharset),d.src=a.url,d.onload=d.onreadystatechange=function(a,c){if(c||!d.readyState||/loaded|complete/.test(d.readyState))d.onload=d.onreadystatechange=null,e&&d.parentNode&&e.removeChild(d),d=b,c||g(200,"success")},e.insertBefore(d,e.firstChild)},abort:function(){d&&d.onload(0,1)}}}});var ce=a.ActiveXObject?function(){for(var a in cg)cg[a](0,1)}:!1,cf=0,cg;f.ajaxSettings.xhr=a.ActiveXObject?function(){return!this.isLocal&&ch()||ci()}:ch,function(a){f.extend(f.support,{ajax:!!a,cors:!!a&&"withCredentials"in a})}(f.ajaxSettings.xhr()),f.support.ajax&&f.ajaxTransport(function(c){if(!c.crossDomain||f.support.cors){var d;return{send:function(e,g){var h=c.xhr(),i,j;c.username?h.open(c.type,c.url,c.async,c.username,c.password):h.open(c.type,c.url,c.async);if(c.xhrFields)for(j in c.xhrFields)h[j]=c.xhrFields[j];c.mimeType&&h.overrideMimeType&&h.overrideMimeType(c.mimeType),!c.crossDomain&&!e["X-Requested-With"]&&(e["X-Requested-With"]="XMLHttpRequest");try{for(j in e)h.setRequestHeader(j,e[j])}catch(k){}h.send(c.hasContent&&c.data||null),d=function(a,e){var j,k,l,m,n;try{if(d&&(e||h.readyState===4)){d=b,i&&(h.onreadystatechange=f.noop,ce&&delete cg[i]);if(e)h.readyState!==4&&h.abort();else{j=h.status,l=h.getAllResponseHeaders(),m={},n=h.responseXML,n&&n.documentElement&&(m.xml=n);try{m.text=h.responseText}catch(a){}try{k=h.statusText}catch(o){k=""}!j&&c.isLocal&&!c.crossDomain?j=m.text?200:404:j===1223&&(j=204)}}}catch(p){e||g(-1,p)}m&&g(j,k,m,l)},!c.async||h.readyState===4?d():(i=++cf,ce&&(cg||(cg={},f(a).unload(ce)),cg[i]=d),h.onreadystatechange=d)},abort:function(){d&&d(0,1)}}}});var cj={},ck,cl,cm=/^(?:toggle|show|hide)$/,cn=/^([+\-]=)?([\d+.\-]+)([a-z%]*)$/i,co,cp=[["height","marginTop","marginBottom","paddingTop","paddingBottom"],["width","marginLeft","marginRight","paddingLeft","paddingRight"],["opacity"]],cq;f.fn.extend({show:function(a,b,c){var d,e;if(a||a===0)return this.animate(ct("show",3),a,b,c);for(var g=0,h=this.length;g<h;g++)d=this[g],d.style&&(e=d.style.display,!f._data(d,"olddisplay")&&e==="none"&&(e=d.style.display=""),(e===""&&f.css(d,"display")==="none"||!f.contains(d.ownerDocument.documentElement,d))&&f._data(d,"olddisplay",cu(d.nodeName)));for(g=0;g<h;g++){d=this[g];if(d.style){e=d.style.display;if(e===""||e==="none")d.style.display=f._data(d,"olddisplay")||""}}return this},hide:function(a,b,c){if(a||a===0)return this.animate(ct("hide",3),a,b,c);var d,e,g=0,h=this.length;for(;g<h;g++)d=this[g],d.style&&(e=f.css(d,"display"),e!=="none"&&!f._data(d,"olddisplay")&&f._data(d,"olddisplay",e));for(g=0;g<h;g++)this[g].style&&(this[g].style.display="none");return this},_toggle:f.fn.toggle,toggle:function(a,b,c){var d=typeof a=="boolean";f.isFunction(a)&&f.isFunction(b)?this._toggle.apply(this,arguments):a==null||d?this.each(function(){var b=d?a:f(this).is(":hidden");f(this)[b?"show":"hide"]()}):this.animate(ct("toggle",3),a,b,c);return this},fadeTo:function(a,b,c,d){return this.filter(":hidden").css("opacity",0).show().end().animate({opacity:b},a,c,d)},animate:function(a,b,c,d){function g(){e.queue===!1&&f._mark(this);var b=f.extend({},e),c=this.nodeType===1,d=c&&f(this).is(":hidden"),g,h,i,j,k,l,m,n,o,p,q;b.animatedProperties={};for(i in a){g=f.camelCase(i),i!==g&&(a[g]=a[i],delete a[i]);if((k=f.cssHooks[g])&&"expand"in k){l=k.expand(a[g]),delete a[g];for(i in l)i in a||(a[i]=l[i])}}for(g in a){h=a[g],f.isArray(h)?(b.animatedProperties[g]=h[1],h=a[g]=h[0]):b.animatedProperties[g]=b.specialEasing&&b.specialEasing[g]||b.easing||"swing";if(h==="hide"&&d||h==="show"&&!d)return b.complete.call(this);c&&(g==="height"||g==="width")&&(b.overflow=[this.style.overflow,this.style.overflowX,this.style.overflowY],f.css(this,"display")==="inline"&&f.css(this,"float")==="none"&&(!f.support.inlineBlockNeedsLayout||cu(this.nodeName)==="inline"?this.style.display="inline-block":this.style.zoom=1))}b.overflow!=null&&(this.style.overflow="hidden");for(i in a)j=new f.fx(this,b,i),h=a[i],cm.test(h)?(q=f._data(this,"toggle"+i)||(h==="toggle"?d?"show":"hide":0),q?(f._data(this,"toggle"+i,q==="show"?"hide":"show"),j[q]()):j[h]()):(m=cn.exec(h),n=j.cur(),m?(o=parseFloat(m[2]),p=m[3]||(f.cssNumber[i]?"":"px"),p!=="px"&&(f.style(this,i,(o||1)+p),n=(o||1)/j.cur()*n,f.style(this,i,n+p)),m[1]&&(o=(m[1]==="-="?-1:1)*o+n),j.custom(n,o,p)):j.custom(n,h,""));return!0}var e=f.speed(b,c,d);if(f.isEmptyObject(a))return this.each(e.complete,[!1]);a=f.extend({},a);return e.queue===!1?this.each(g):this.queue(e.queue,g)},stop:function(a,c,d){typeof a!="string"&&(d=c,c=a,a=b),c&&a!==!1&&this.queue(a||"fx",[]);return this.each(function(){function h(a,b,c){var e=b[c];f.removeData(a,c,!0),e.stop(d)}var b,c=!1,e=f.timers,g=f._data(this);d||f._unmark(!0,this);if(a==null)for(b in g)g[b]&&g[b].stop&&b.indexOf(".run")===b.length-4&&h(this,g,b);else g[b=a+".run"]&&g[b].stop&&h(this,g,b);for(b=e.length;b--;)e[b].elem===this&&(a==null||e[b].queue===a)&&(d?e[b](!0):e[b].saveState(),c=!0,e.splice(b,1));(!d||!c)&&f.dequeue(this,a)})}}),f.each({slideDown:ct("show",1),slideUp:ct("hide",1),slideToggle:ct("toggle",1),fadeIn:{opacity:"show"},fadeOut:{opacity:"hide"},fadeToggle:{opacity:"toggle"}},function(a,b){f.fn[a]=function(a,c,d){return this.animate(b,a,c,d)}}),f.extend({speed:function(a,b,c){var d=a&&typeof a=="object"?f.extend({},a):{complete:c||!c&&b||f.isFunction(a)&&a,duration:a,easing:c&&b||b&&!f.isFunction(b)&&b};d.duration=f.fx.off?0:typeof d.duration=="number"?d.duration:d.duration in f.fx.speeds?f.fx.speeds[d.duration]:f.fx.speeds._default;if(d.queue==null||d.queue===!0)d.queue="fx";d.old=d.complete,d.complete=function(a){f.isFunction(d.old)&&d.old.call(this),d.queue?f.dequeue(this,d.queue):a!==!1&&f._unmark(this)};return d},easing:{linear:function(a){return a},swing:function(a){return-Math.cos(a*Math.PI)/2+.5}},timers:[],fx:function(a,b,c){this.options=b,this.elem=a,this.prop=c,b.orig=b.orig||{}}}),f.fx.prototype={update:function(){this.options.step&&this.options.step.call(this.elem,this.now,this),(f.fx.step[this.prop]||f.fx.step._default)(this)},cur:function(){if(this.elem[this.prop]!=null&&(!this.elem.style||this.elem.style[this.prop]==null))return this.elem[this.prop];var a,b=f.css(this.elem,this.prop);return isNaN(a=parseFloat(b))?!b||b==="auto"?0:b:a},custom:function(a,c,d){function h(a){return e.step(a)}var e=this,g=f.fx;this.startTime=cq||cr(),this.end=c,this.now=this.start=a,this.pos=this.state=0,this.unit=d||this.unit||(f.cssNumber[this.prop]?"":"px"),h.queue=this.options.queue,h.elem=this.elem,h.saveState=function(){f._data(e.elem,"fxshow"+e.prop)===b&&(e.options.hide?f._data(e.elem,"fxshow"+e.prop,e.start):e.options.show&&f._data(e.elem,"fxshow"+e.prop,e.end))},h()&&f.timers.push(h)&&!co&&(co=setInterval(g.tick,g.interval))},show:function(){var a=f._data(this.elem,"fxshow"+this.prop);this.options.orig[this.prop]=a||f.style(this.elem,this.prop),this.options.show=!0,a!==b?this.custom(this.cur(),a):this.custom(this.prop==="width"||this.prop==="height"?1:0,this.cur()),f(this.elem).show()},hide:function(){this.options.orig[this.prop]=f._data(this.elem,"fxshow"+this.prop)||f.style(this.elem,this.prop),this.options.hide=!0,this.custom(this.cur(),0)},step:function(a){var b,c,d,e=cq||cr(),g=!0,h=this.elem,i=this.options;if(a||e>=i.duration+this.startTime){this.now=this.end,this.pos=this.state=1,this.update(),i.animatedProperties[this.prop]=!0;for(b in i.animatedProperties)i.animatedProperties[b]!==!0&&(g=!1);if(g){i.overflow!=null&&!f.support.shrinkWrapBlocks&&f.each(["","X","Y"],function(a,b){h.style["overflow"+b]=i.overflow[a]}),i.hide&&f(h).hide();if(i.hide||i.show)for(b in i.animatedProperties)f.style(h,b,i.orig[b]),f.removeData(h,"fxshow"+b,!0),f.removeData(h,"toggle"+b,!0);d=i.complete,d&&(i.complete=!1,d.call(h))}return!1}i.duration==Infinity?this.now=e:(c=e-this.startTime,this.state=c/i.duration,this.pos=f.easing[i.animatedProperties[this.prop]](this.state,c,0,1,i.duration),this.now=this.start+(this.end-this.start)*this.pos),this.update();return!0}},f.extend(f.fx,{tick:function(){var a,b=f.timers,c=0;for(;c<b.length;c++)a=b[c],!a()&&b[c]===a&&b.splice(c--,1);b.length||f.fx.stop()},interval:13,stop:function(){clearInterval(co),co=null},speeds:{slow:600,fast:200,_default:400},step:{opacity:function(a){f.style(a.elem,"opacity",a.now)},_default:function(a){a.elem.style&&a.elem.style[a.prop]!=null?a.elem.style[a.prop]=a.now+a.unit:a.elem[a.prop]=a.now}}}),f.each(cp.concat.apply([],cp),function(a,b){b.indexOf("margin")&&(f.fx.step[b]=function(a){f.style(a.elem,b,Math.max(0,a.now)+a.unit)})}),f.expr&&f.expr.filters&&(f.expr.filters.animated=function(a){return f.grep(f.timers,function(b){return a===b.elem}).length});var cv,cw=/^t(?:able|d|h)$/i,cx=/^(?:body|html)$/i;"getBoundingClientRect"in c.documentElement?cv=function(a,b,c,d){try{d=a.getBoundingClientRect()}catch(e){}if(!d||!f.contains(c,a))return d?{top:d.top,left:d.left}:{top:0,left:0};var g=b.body,h=cy(b),i=c.clientTop||g.clientTop||0,j=c.clientLeft||g.clientLeft||0,k=h.pageYOffset||f.support.boxModel&&c.scrollTop||g.scrollTop,l=h.pageXOffset||f.support.boxModel&&c.scrollLeft||g.scrollLeft,m=d.top+k-i,n=d.left+l-j;return{top:m,left:n}}:cv=function(a,b,c){var d,e=a.offsetParent,g=a,h=b.body,i=b.defaultView,j=i?i.getComputedStyle(a,null):a.currentStyle,k=a.offsetTop,l=a.offsetLeft;while((a=a.parentNode)&&a!==h&&a!==c){if(f.support.fixedPosition&&j.position==="fixed")break;d=i?i.getComputedStyle(a,null):a.currentStyle,k-=a.scrollTop,l-=a.scrollLeft,a===e&&(k+=a.offsetTop,l+=a.offsetLeft,f.support.doesNotAddBorder&&(!f.support.doesAddBorderForTableAndCells||!cw.test(a.nodeName))&&(k+=parseFloat(d.borderTopWidth)||0,l+=parseFloat(d.borderLeftWidth)||0),g=e,e=a.offsetParent),f.support.subtractsBorderForOverflowNotVisible&&d.overflow!=="visible"&&(k+=parseFloat(d.borderTopWidth)||0,l+=parseFloat(d.borderLeftWidth)||0),j=d}if(j.position==="relative"||j.position==="static")k+=h.offsetTop,l+=h.offsetLeft;f.support.fixedPosition&&j.position==="fixed"&&(k+=Math.max(c.scrollTop,h.scrollTop),l+=Math.max(c.scrollLeft,h.scrollLeft));return{top:k,left:l}},f.fn.offset=function(a){if(arguments.length)return a===b?this:this.each(function(b){f.offset.setOffset(this,a,b)});var c=this[0],d=c&&c.ownerDocument;if(!d)return null;if(c===d.body)return f.offset.bodyOffset(c);return cv(c,d,d.documentElement)},f.offset={bodyOffset:function(a){var b=a.offsetTop,c=a.offsetLeft;f.support.doesNotIncludeMarginInBodyOffset&&(b+=parseFloat(f.css(a,"marginTop"))||0,c+=parseFloat(f.css(a,"marginLeft"))||0);return{top:b,left:c}},setOffset:function(a,b,c){var d=f.css(a,"position");d==="static"&&(a.style.position="relative");var e=f(a),g=e.offset(),h=f.css(a,"top"),i=f.css(a,"left"),j=(d==="absolute"||d==="fixed")&&f.inArray("auto",[h,i])>-1,k={},l={},m,n;j?(l=e.position(),m=l.top,n=l.left):(m=parseFloat(h)||0,n=parseFloat(i)||0),f.isFunction(b)&&(b=b.call(a,c,g)),b.top!=null&&(k.top=b.top-g.top+m),b.left!=null&&(k.left=b.left-g.left+n),"using"in b?b.using.call(a,k):e.css(k)}},f.fn.extend({position:function(){if(!this[0])return null;var a=this[0],b=this.offsetParent(),c=this.offset(),d=cx.test(b[0].nodeName)?{top:0,left:0}:b.offset();c.top-=parseFloat(f.css(a,"marginTop"))||0,c.left-=parseFloat(f.css(a,"marginLeft"))||0,d.top+=parseFloat(f.css(b[0],"borderTopWidth"))||0,d.left+=parseFloat(f.css(b[0],"borderLeftWidth"))||0;return{top:c.top-d.top,left:c.left-d.left}},offsetParent:function(){return this.map(function(){var a=this.offsetParent||c.body;while(a&&!cx.test(a.nodeName)&&f.css(a,"position")==="static")a=a.offsetParent;return a})}}),f.each({scrollLeft:"pageXOffset",scrollTop:"pageYOffset"},function(a,c){var d=/Y/.test(c);f.fn[a]=function(e){return f.access(this,function(a,e,g){var h=cy(a);if(g===b)return h?c in h?h[c]:f.support.boxModel&&h.document.documentElement[e]||h.document.body[e]:a[e];h?h.scrollTo(d?f(h).scrollLeft():g,d?g:f(h).scrollTop()):a[e]=g},a,e,arguments.length,null)}}),f.each({Height:"height",Width:"width"},function(a,c){var d="client"+a,e="scroll"+a,g="offset"+a;f.fn["inner"+a]=function(){var a=this[0];return a?a.style?parseFloat(f.css(a,c,"padding")):this[c]():null},f.fn["outer"+a]=function(a){var b=this[0];return b?b.style?parseFloat(f.css(b,c,a?"margin":"border")):this[c]():null},f.fn[c]=function(a){return f.access(this,function(a,c,h){var i,j,k,l;if(f.isWindow(a)){i=a.document,j=i.documentElement[d];return f.support.boxModel&&j||i.body&&i.body[d]||j}if(a.nodeType===9){i=a.documentElement;if(i[d]>=i[e])return i[d];return Math.max(a.body[e],i[e],a.body[g],i[g])}if(h===b){k=f.css(a,c),l=parseFloat(k);return f.isNumeric(l)?l:k}f(a).css(c,h)},c,a,arguments.length,null)}}),a.jQuery=a.$=f,typeof define=="function"&&define.amd&&define.amd.jQuery&&define("jquery",[],function(){return f})})(window);

/*
 * jQuery UI Position 1.8.6
 *
 * Copyright 2010, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Position
 */
(function( $, undefined ) {

$.ui = $.ui || {};

var horizontalPositions = /left|center|right/,
	verticalPositions = /top|center|bottom/,
	center = "center",
	_position = $.fn.position,
	_offset = $.fn.offset;

$.fn.position = function( options ) {
	if ( !options || !options.of ) {
		return _position.apply( this, arguments );
	}

	// make a copy, we don't want to modify arguments
	options = $.extend( {}, options );

	var target = $( options.of ),
		targetElem = target[0],
		collision = ( options.collision || "flip" ).split( " " ),
		offset = options.offset ? options.offset.split( " " ) : [ 0, 0 ],
		targetWidth,
		targetHeight,
		basePosition;

	if ( targetElem.nodeType === 9 ) {
		targetWidth = target.width();
		targetHeight = target.height();
		basePosition = { top: 0, left: 0 };
	// TODO: use $.isWindow() in 1.9
	} else if ( targetElem.setTimeout ) {
		targetWidth = target.width();
		targetHeight = target.height();
		basePosition = { top: target.scrollTop(), left: target.scrollLeft() };
	} else if ( targetElem.preventDefault ) {
		// force left top to allow flipping
		options.at = "left top";
		targetWidth = targetHeight = 0;
		basePosition = { top: options.of.pageY, left: options.of.pageX };
	} else {
		targetWidth = target.outerWidth();
		targetHeight = target.outerHeight();
		basePosition = target.offset();
	}

	// force my and at to have valid horizontal and veritcal positions
	// if a value is missing or invalid, it will be converted to center 
	$.each( [ "my", "at" ], function() {
		var pos = ( options[this] || "" ).split( " " );
		if ( pos.length === 1) {
			pos = horizontalPositions.test( pos[0] ) ?
				pos.concat( [center] ) :
				verticalPositions.test( pos[0] ) ?
					[ center ].concat( pos ) :
					[ center, center ];
		}
		pos[ 0 ] = horizontalPositions.test( pos[0] ) ? pos[ 0 ] : center;
		pos[ 1 ] = verticalPositions.test( pos[1] ) ? pos[ 1 ] : center;
		options[ this ] = pos;
	});

	// normalize collision option
	if ( collision.length === 1 ) {
		collision[ 1 ] = collision[ 0 ];
	}

	// normalize offset option
	offset[ 0 ] = parseInt( offset[0], 10 ) || 0;
	if ( offset.length === 1 ) {
		offset[ 1 ] = offset[ 0 ];
	}
	offset[ 1 ] = parseInt( offset[1], 10 ) || 0;

	if ( options.at[0] === "right" ) {
		basePosition.left += targetWidth;
	} else if (options.at[0] === center ) {
		basePosition.left += targetWidth / 2;
	}

	if ( options.at[1] === "bottom" ) {
		basePosition.top += targetHeight;
	} else if ( options.at[1] === center ) {
		basePosition.top += targetHeight / 2;
	}

	basePosition.left += offset[ 0 ];
	basePosition.top += offset[ 1 ];

	return this.each(function() {
		var elem = $( this ),
			elemWidth = elem.outerWidth(),
			elemHeight = elem.outerHeight(),
			marginLeft = parseInt( $.curCSS( this, "marginLeft", true ) ) || 0,
			marginTop = parseInt( $.curCSS( this, "marginTop", true ) ) || 0,
			collisionWidth = elemWidth + marginLeft +
				parseInt( $.curCSS( this, "marginRight", true ) ) || 0,
			collisionHeight = elemHeight + marginTop +
				parseInt( $.curCSS( this, "marginBottom", true ) ) || 0,
			position = $.extend( {}, basePosition ),
			collisionPosition;

		if ( options.my[0] === "right" ) {
			position.left -= elemWidth;
		} else if ( options.my[0] === center ) {
			position.left -= elemWidth / 2;
		}

		if ( options.my[1] === "bottom" ) {
			position.top -= elemHeight;
		} else if ( options.my[1] === center ) {
			position.top -= elemHeight / 2;
		}

		// prevent fractions (see #5280)
		position.left = parseInt( position.left );
		position.top = parseInt( position.top );

		collisionPosition = {
			left: position.left - marginLeft,
			top: position.top - marginTop
		};

		$.each( [ "left", "top" ], function( i, dir ) {
			if ( $.ui.position[ collision[i] ] ) {
				$.ui.position[ collision[i] ][ dir ]( position, {
					targetWidth: targetWidth,
					targetHeight: targetHeight,
					elemWidth: elemWidth,
					elemHeight: elemHeight,
					collisionPosition: collisionPosition,
					collisionWidth: collisionWidth,
					collisionHeight: collisionHeight,
					offset: offset,
					my: options.my,
					at: options.at
				});
			}
		});

		if ( $.fn.bgiframe ) {
			elem.bgiframe();
		}
		elem.offset( $.extend( position, { using: options.using } ) );
	});
};

$.ui.position = {
	fit: {
		left: function( position, data ) {
			var win = $( window ),
				over = data.collisionPosition.left + data.collisionWidth - win.width() - win.scrollLeft();
			position.left = over > 0 ? position.left - over : Math.max( position.left - data.collisionPosition.left, position.left );
		},
		top: function( position, data ) {
			var win = $( window ),
				over = data.collisionPosition.top + data.collisionHeight - win.height() - win.scrollTop();
			position.top = over > 0 ? position.top - over : Math.max( position.top - data.collisionPosition.top, position.top );
		}
	},

	flip: {
		left: function( position, data ) {
			if ( data.at[0] === center ) {
				return;
			}
			var win = $( window ),
				over = data.collisionPosition.left + data.collisionWidth - win.width() - win.scrollLeft(),
				myOffset = data.my[ 0 ] === "left" ?
					-data.elemWidth :
					data.my[ 0 ] === "right" ?
						data.elemWidth :
						0,
				atOffset = data.at[ 0 ] === "left" ?
					data.targetWidth :
					-data.targetWidth,
				offset = -2 * data.offset[ 0 ];
			position.left += data.collisionPosition.left < 0 ?
				myOffset + atOffset + offset :
				over > 0 ?
					myOffset + atOffset + offset :
					0;
		},
		top: function( position, data ) {
			if ( data.at[1] === center ) {
				return;
			}
			var win = $( window ),
				over = data.collisionPosition.top + data.collisionHeight - win.height() - win.scrollTop(),
				myOffset = data.my[ 1 ] === "top" ?
					-data.elemHeight :
					data.my[ 1 ] === "bottom" ?
						data.elemHeight :
						0,
				atOffset = data.at[ 1 ] === "top" ?
					data.targetHeight :
					-data.targetHeight,
				offset = -2 * data.offset[ 1 ];
			position.top += data.collisionPosition.top < 0 ?
				myOffset + atOffset + offset :
				over > 0 ?
					myOffset + atOffset + offset :
					0;
		}
	}
};

// offset setter from jQuery 1.4
if ( !$.offset.setOffset ) {
	$.offset.setOffset = function( elem, options ) {
		// set position first, in-case top/left are set even on static elem
		if ( /static/.test( $.curCSS( elem, "position" ) ) ) {
			elem.style.position = "relative";
		}
		var curElem   = $( elem ),
			curOffset = curElem.offset(),
			curTop    = parseInt( $.curCSS( elem, "top",  true ), 10 ) || 0,
			curLeft   = parseInt( $.curCSS( elem, "left", true ), 10)  || 0,
			props     = {
				top:  (options.top  - curOffset.top)  + curTop,
				left: (options.left - curOffset.left) + curLeft
			};
		
		if ( 'using' in options ) {
			options.using.call( elem, props );
		} else {
			curElem.css( props );
		}
	};

	$.fn.offset = function( options ) {
		var elem = this[ 0 ];
		if ( !elem || !elem.ownerDocument ) { return null; }
		if ( options ) { 
			return this.each(function() {
				$.offset.setOffset( this, options );
			});
		}
		return _offset.call( this );
	};
}

}( jQuery ));


/*!
 * jQuery contextMenu - Plugin for simple contextMenu handling
 *
 * Version: git-master
 *
 * Authors: Rodney Rehm, Addy Osmani (patches for FF)
 * Web: http://medialize.github.com/jQuery-contextMenu/
 *
 * Licensed under
 *   MIT License http://www.opensource.org/licenses/mit-license
 *   GPL v3 http://opensource.org/licenses/GPL-3.0
 *
 */

(function($, undefined){
    
    // TODO: -
        // ARIA stuff: menuitem, menuitemcheckbox und menuitemradio
        // create <menu> structure if $.support[htmlCommand || htmlMenuitem] and !opt.disableNative

// determine html5 compatibility
$.support.htmlMenuitem = ('HTMLMenuItemElement' in window);
$.support.htmlCommand = ('HTMLCommandElement' in window);
$.support.eventSelectstart = ("onselectstart" in document.documentElement);
/* // should the need arise, test for css user-select
$.support.cssUserSelect = (function(){
    var t = false,
        e = document.createElement('div');
    
    $.each('Moz|Webkit|Khtml|O|ms|Icab|'.split('|'), function(i, prefix) {
        var propCC = prefix + (prefix ? 'U' : 'u') + 'serSelect',
            prop = (prefix ? ('-' + prefix.toLowerCase() + '-') : '') + 'user-select';
            
        e.style.cssText = prop + ': text;';
        if (e.style[propCC] == 'text') {
            t = true;
            return false;
        }
        
        return true;
    });
    
    return t;
})();
*/

if (!$.ui || !$.ui.widget) {
    // duck punch $.cleanData like jQueryUI does to get that remove event
    // https://github.com/jquery/jquery-ui/blob/master/ui/jquery.ui.widget.js#L16-24
    var _cleanData = $.cleanData;
    $.cleanData = function( elems ) {
        for ( var i = 0, elem; (elem = elems[i]) != null; i++ ) {
            try {
                $( elem ).triggerHandler( "remove" );
                // http://bugs.jquery.com/ticket/8235
            } catch( e ) {}
        }
        _cleanData( elems );
    };
}

var // currently active contextMenu trigger
    $currentTrigger = null,
    // is contextMenu initialized with at least one menu?
    initialized = false,
    // window handle
    $win = $(window),
    // number of registered menus
    counter = 0,
    // mapping selector to namespace
    namespaces = {},
    // mapping namespace to options
    menus = {},
    // custom command type handlers
    types = {},
    // default values
    defaults = {
        // selector of contextMenu trigger
        selector: null,
        // where to append the menu to
        appendTo: null,
        // method to trigger context menu ["right", "left", "hover"]
        trigger: "right",
        // hide menu when mouse leaves trigger / menu elements
        autoHide: false,
        // ms to wait before showing a hover-triggered context menu
        delay: 200,
        // flag denoting if a second trigger should simply move (true) or rebuild (false) an open menu
        // as long as the trigger happened on one of the trigger-element's child nodes
        reposition: true,
        // determine position to show menu at
        determinePosition: function($menu) {
            // position to the lower middle of the trigger element
            if ($.ui && $.ui.position) {
                // .position() is provided as a jQuery UI utility
                // (...and it won't work on hidden elements)
                $menu.css('display', 'block').position({
                    my: "center top",
                    at: "center bottom",
                    of: this,
                    offset: "0 5",
                    collision: "fit"
                }).css('display', 'none');
            } else {
                // determine contextMenu position
                var offset = this.offset();
                offset.top += this.outerHeight();
                offset.left += this.outerWidth() / 2 - $menu.outerWidth() / 2;
                $menu.css(offset);
            }
        },
        // position menu
        position: function(opt, x, y) {
            var $this = this,
                offset;
            // determine contextMenu position
            if (!x && !y) {
                opt.determinePosition.call(this, opt.$menu);
                return;
            } else if (x === "maintain" && y === "maintain") {
                // x and y must not be changed (after re-show on command click)
                offset = opt.$menu.position();
            } else {
                // x and y are given (by mouse event)
                offset = {top: y, left: x};
            }
            
            // correct offset if viewport demands it
            var bottom = $win.scrollTop() + $win.height(),
                right = $win.scrollLeft() + $win.width(),
                height = opt.$menu.height(),
                width = opt.$menu.width();
            
            if (offset.top + height > bottom) {
                offset.top -= height;
            }

            if (offset.top<0) {
                offset.top = 0;
            }
            
            if (offset.left + width > right) {
                offset.left -= width;
            }
            
            opt.$menu.css(offset);
        },
        // position the sub-menu
        positionSubmenu: function($menu) {
            if ($.ui && $.ui.position) {
                // .position() is provided as a jQuery UI utility
                // (...and it won't work on hidden elements)
                $menu.css('display', 'block').position({
                    my: "left top",
                    at: "right top",
                    of: this,
                    collision: "flipfit fit"
                }).css('display', '');
            } else {
                // determine contextMenu position
                var offset = {
                    top: 0,
                    left: this.outerWidth()
                };
                $menu.css(offset);
            }
        },
        // offset to add to zIndex
        zIndex: 1,
        // show hide animation settings
        animation: {
            duration: 50,
            show: 'slideDown',
            hide: 'slideUp'
        },
        // events
        events: {
            show: $.noop,
            hide: $.noop
        },
        // default callback
        callback: null,
        // list of contextMenu items
        items: {}
    },
    // mouse position for hover activation
    hoveract = {
        timer: null,
        pageX: null,
        pageY: null
    },
    // determine zIndex
    zindex = function($t) {
        var zin = 0,
            $tt = $t;

        while (true) {
            zin = Math.max(zin, parseInt($tt.css('z-index'), 10) || 0);
            $tt = $tt.parent();
            if (!$tt || !$tt.length || "html body".indexOf($tt.prop('nodeName').toLowerCase()) > -1 ) {
                break;
            }
        }
        
        return zin;
    },
    // event handlers
    handle = {
        // abort anything
        abortevent: function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
        },
        
        // contextmenu show dispatcher
        contextmenu: function(e) {
            var $this = $(this);
            
            // disable actual context-menu if we are using the right mouse button as the trigger
            if (e.data.trigger == 'right') {
                e.preventDefault();
                e.stopImmediatePropagation();
            }
            
            // abort native-triggered events unless we're triggering on right click
            if (e.data.trigger != 'right' && e.originalEvent) {
                return;
            }
            
            // abort event if menu is visible for this trigger
            if ($this.hasClass('context-menu-active')) {
                return;
            }
            
            if (!$this.hasClass('context-menu-disabled')) {
                // theoretically need to fire a show event at <menu>
                // http://www.whatwg.org/specs/web-apps/current-work/multipage/interactive-elements.html#context-menus
                // var evt = jQuery.Event("show", { data: data, pageX: e.pageX, pageY: e.pageY, relatedTarget: this });
                // e.data.$menu.trigger(evt);
                
                $currentTrigger = $this;
                if (e.data.build) {
                    var built = e.data.build($currentTrigger, e);
                    // abort if build() returned false
                    if (built === false) {
                        return;
                    }
                    
                    // dynamically build menu on invocation
                    e.data = $.extend(true, {}, defaults, e.data, built || {});

                    // abort if there are no items to display
                    if (!e.data.items || $.isEmptyObject(e.data.items)) {
                        // Note: jQuery captures and ignores errors from event handlers
                        if (window.console) {
                            (console.error || console.log).call(console, "No items specified to show in contextMenu");
                        }
                        
                        throw new Error('No Items specified');
                    }
                    
                    // backreference for custom command type creation
                    e.data.$trigger = $currentTrigger;
                    
                    op.create(e.data);
                }
                // show menu
                op.show.call($this, e.data, e.pageX, e.pageY);
            }
        },
        // contextMenu left-click trigger
        click: function(e) {
            e.preventDefault();
            e.stopImmediatePropagation();
            $(this).trigger($.Event("contextmenu", { data: e.data, pageX: e.pageX, pageY: e.pageY }));
        },
        // contextMenu right-click trigger
        mousedown: function(e) {
            // register mouse down
            var $this = $(this);
            
            // hide any previous menus
            if ($currentTrigger && $currentTrigger.length && !$currentTrigger.is($this)) {
                $currentTrigger.data('contextMenu').$menu.trigger('contextmenu:hide');
            }
            
            // activate on right click
            if (e.button == 2) {
                $currentTrigger = $this.data('contextMenuActive', true);
            }
        },
        // contextMenu right-click trigger
        mouseup: function(e) {
            // show menu
            var $this = $(this);
            if ($this.data('contextMenuActive') && $currentTrigger && $currentTrigger.length && $currentTrigger.is($this) && !$this.hasClass('context-menu-disabled')) {
                e.preventDefault();
                e.stopImmediatePropagation();
                $currentTrigger = $this;
                $this.trigger($.Event("contextmenu", { data: e.data, pageX: e.pageX, pageY: e.pageY }));
            }
            
            $this.removeData('contextMenuActive');
        },
        // contextMenu hover trigger
        mouseenter: function(e) {
            var $this = $(this),
                $related = $(e.relatedTarget),
                $document = $(document);
            
            // abort if we're coming from a menu
            if ($related.is('.context-menu-list') || $related.closest('.context-menu-list').length) {
                return;
            }
            
            // abort if a menu is shown
            if ($currentTrigger && $currentTrigger.length) {
                return;
            }
            
            hoveract.pageX = e.pageX;
            hoveract.pageY = e.pageY;
            hoveract.data = e.data;
            $document.on('mousemove.contextMenuShow', handle.mousemove);
            hoveract.timer = setTimeout(function() {
                hoveract.timer = null;
                $document.off('mousemove.contextMenuShow');
                $currentTrigger = $this;
                $this.trigger($.Event("contextmenu", { data: hoveract.data, pageX: hoveract.pageX, pageY: hoveract.pageY }));
            }, e.data.delay );
        },
        // contextMenu hover trigger
        mousemove: function(e) {
            hoveract.pageX = e.pageX;
            hoveract.pageY = e.pageY;
        },
        // contextMenu hover trigger
        mouseleave: function(e) {
            // abort if we're leaving for a menu
            var $related = $(e.relatedTarget);
            if ($related.is('.context-menu-list') || $related.closest('.context-menu-list').length) {
                return;
            }
            
            try {
                clearTimeout(hoveract.timer);
            } catch(e) {}
            
            hoveract.timer = null;
        },
        
        // click on layer to hide contextMenu
        layerClick: function(e) {
            var $this = $(this),
                root = $this.data('contextMenuRoot'),
                button = e.button,
                x = e.pageX,
                y = e.pageY,
                target, 
                offset;
                
            e.preventDefault();
            e.stopImmediatePropagation();
            
            setTimeout(function() {
                var $window;
                var triggerAction = ((root.trigger == 'left' && button === 0) || (root.trigger == 'right' && button === 2));
                
                // find the element that would've been clicked, wasn't the layer in the way
                if (document.elementFromPoint) {
                    root.$layer.hide();
                    target = document.elementFromPoint(x - $win.scrollLeft(), y - $win.scrollTop());
                    root.$layer.show();
                }
                
                if (root.reposition && triggerAction) {
                    if (document.elementFromPoint) {
                        if (root.$trigger.is(target) || root.$trigger.has(target).length) {
                            root.position.call(root.$trigger, root, x, y);
                            return;
                        }
                    } else {
                        offset = root.$trigger.offset();
                        $window = $(window);
                        // while this looks kinda awful, it's the best way to avoid
                        // unnecessarily calculating any positions
                        offset.top += $window.scrollTop();
                        if (offset.top <= e.pageY) {
                            offset.left += $window.scrollLeft();
                            if (offset.left <= e.pageX) {
                                offset.bottom = offset.top + root.$trigger.outerHeight();
                                if (offset.bottom >= e.pageY) {
                                    offset.right = offset.left + root.$trigger.outerWidth();
                                    if (offset.right >= e.pageX) {
                                        // reposition
                                        root.position.call(root.$trigger, root, x, y);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (target && triggerAction) {
                    root.$trigger.one('contextmenu:hidden', function() {
                        $(target).contextMenu({x: x, y: y});
                    });
                }

                root.$menu.trigger('contextmenu:hide');
            }, 50);
        },
        // key handled :hover
        keyStop: function(e, opt) {
            if (!opt.isInput) {
                e.preventDefault();
            }
            
            e.stopPropagation();
        },
        key: function(e) {

            var opt = {};

            // Only get the data from $currentTrigger if it exists
            if ($currentTrigger) {
                opt = $currentTrigger.data('contextMenu') || {};
            }

            switch (e.keyCode) {
                case 9:
                case 38: // up
                    handle.keyStop(e, opt);
                    // if keyCode is [38 (up)] or [9 (tab) with shift]
                    if (opt.isInput) {
                        if (e.keyCode == 9 && e.shiftKey) {
                            e.preventDefault();
                            opt.$selected && opt.$selected.find('input, textarea, select').blur();
                            opt.$menu.trigger('prevcommand');
                            return;
                        } else if (e.keyCode == 38 && opt.$selected.find('input, textarea, select').prop('type') == 'checkbox') {
                            // checkboxes don't capture this key
                            e.preventDefault();
                            return;
                        }
                    } else if (e.keyCode != 9 || e.shiftKey) {
                        opt.$menu.trigger('prevcommand');
                        return;
                    }
                    // omitting break;
                    
                // case 9: // tab - reached through omitted break;
                case 40: // down
                    handle.keyStop(e, opt);
                    if (opt.isInput) {
                        if (e.keyCode == 9) {
                            e.preventDefault();
                            opt.$selected && opt.$selected.find('input, textarea, select').blur();
                            opt.$menu.trigger('nextcommand');
                            return;
                        } else if (e.keyCode == 40 && opt.$selected.find('input, textarea, select').prop('type') == 'checkbox') {
                            // checkboxes don't capture this key
                            e.preventDefault();
                            return;
                        }
                    } else {
                        opt.$menu.trigger('nextcommand');
                        return;
                    }
                    break;
                
                case 37: // left
                    handle.keyStop(e, opt);
                    if (opt.isInput || !opt.$selected || !opt.$selected.length) {
                        break;
                    }
                
                    if (!opt.$selected.parent().hasClass('context-menu-root')) {
                        var $parent = opt.$selected.parent().parent();
                        opt.$selected.trigger('contextmenu:blur');
                        opt.$selected = $parent;
                        return;
                    }
                    break;
                    
                case 39: // right
                    handle.keyStop(e, opt);
                    if (opt.isInput || !opt.$selected || !opt.$selected.length) {
                        break;
                    }
                    
                    var itemdata = opt.$selected.data('contextMenu') || {};
                    if (itemdata.$menu && opt.$selected.hasClass('context-menu-submenu')) {
                        opt.$selected = null;
                        itemdata.$selected = null;
                        itemdata.$menu.trigger('nextcommand');
                        return;
                    }
                    break;
                
                case 35: // end
                case 36: // home
                    if (opt.$selected && opt.$selected.find('input, textarea, select').length) {
                        return;
                    } else {
                        (opt.$selected && opt.$selected.parent() || opt.$menu)
                            .children(':not(.disabled, .not-selectable)')[e.keyCode == 36 ? 'first' : 'last']()
                            .trigger('contextmenu:focus');
                        e.preventDefault();
                        return;
                    }
                    break;
                    
                case 13: // enter
                    handle.keyStop(e, opt);
                    if (opt.isInput) {
                        if (opt.$selected && !opt.$selected.is('textarea, select')) {
                            e.preventDefault();
                            return;
                        }
                        break;
                    }
                    opt.$selected && opt.$selected.trigger('mouseup');
                    return;
                    
                case 32: // space
                case 33: // page up
                case 34: // page down
                    // prevent browser from scrolling down while menu is visible
                    handle.keyStop(e, opt);
                    return;
                    
                case 27: // esc
                    handle.keyStop(e, opt);
                    opt.$menu.trigger('contextmenu:hide');
                    return;
                    
                default: // 0-9, a-z
                    var k = (String.fromCharCode(e.keyCode)).toUpperCase();
                    if (opt.accesskeys && opt.accesskeys[k]) {
                        // according to the specs accesskeys must be invoked immediately
                        opt.accesskeys[k].$node.trigger(opt.accesskeys[k].$menu
                            ? 'contextmenu:focus'
                            : 'mouseup'
                        );
                        return;
                    }
                    break;
            }
            // pass event to selected item, 
            // stop propagation to avoid endless recursion
            e.stopPropagation();
            opt.$selected && opt.$selected.trigger(e);
        },

        // select previous possible command in menu
        prevItem: function(e) {
            e.stopPropagation();
            var opt = $(this).data('contextMenu') || {};

            // obtain currently selected menu
            if (opt.$selected) {
                var $s = opt.$selected;
                opt = opt.$selected.parent().data('contextMenu') || {};
                opt.$selected = $s;
            }
            
            var $children = opt.$menu.children(),
                $prev = !opt.$selected || !opt.$selected.prev().length ? $children.last() : opt.$selected.prev(),
                $round = $prev;
            
            // skip disabled
            while ($prev.hasClass('disabled') || $prev.hasClass('not-selectable')) {
                if ($prev.prev().length) {
                    $prev = $prev.prev();
                } else {
                    $prev = $children.last();
                }
                if ($prev.is($round)) {
                    // break endless loop
                    return;
                }
            }
            
            // leave current
            if (opt.$selected) {
                handle.itemMouseleave.call(opt.$selected.get(0), e);
            }
            
            // activate next
            handle.itemMouseenter.call($prev.get(0), e);
            
            // focus input
            var $input = $prev.find('input, textarea, select');
            if ($input.length) {
                $input.focus();
            }
        },
        // select next possible command in menu
        nextItem: function(e) {
            e.stopPropagation();
            var opt = $(this).data('contextMenu') || {};

            // obtain currently selected menu
            if (opt.$selected) {
                var $s = opt.$selected;
                opt = opt.$selected.parent().data('contextMenu') || {};
                opt.$selected = $s;
            }

            var $children = opt.$menu.children(),
                $next = !opt.$selected || !opt.$selected.next().length ? $children.first() : opt.$selected.next(),
                $round = $next;

            // skip disabled
            while ($next.hasClass('disabled') || $next.hasClass('not-selectable')) {
                if ($next.next().length) {
                    $next = $next.next();
                } else {
                    $next = $children.first();
                }
                if ($next.is($round)) {
                    // break endless loop
                    return;
                }
            }
            
            // leave current
            if (opt.$selected) {
                handle.itemMouseleave.call(opt.$selected.get(0), e);
            }
            
            // activate next
            handle.itemMouseenter.call($next.get(0), e);
            
            // focus input
            var $input = $next.find('input, textarea, select');
            if ($input.length) {
                $input.focus();
            }
        },
        
        // flag that we're inside an input so the key handler can act accordingly
        focusInput: function(e) {
            var $this = $(this).closest('.context-menu-item'),
                data = $this.data(),
                opt = data.contextMenu,
                root = data.contextMenuRoot;

            root.$selected = opt.$selected = $this;
            root.isInput = opt.isInput = true;
        },
        // flag that we're inside an input so the key handler can act accordingly
        blurInput: function(e) {
            var $this = $(this).closest('.context-menu-item'),
                data = $this.data(),
                opt = data.contextMenu,
                root = data.contextMenuRoot;

            root.isInput = opt.isInput = false;
        },
        
        // :hover on menu
        menuMouseenter: function(e) {
            var root = $(this).data().contextMenuRoot;
            root.hovering = true;
        },
        // :hover on menu
        menuMouseleave: function(e) {
            var root = $(this).data().contextMenuRoot;
            if (root.$layer && root.$layer.is(e.relatedTarget)) {
                root.hovering = false;
            }
        },
        
        // :hover done manually so key handling is possible
        itemMouseenter: function(e) {
            var $this = $(this),
                data = $this.data(),
                opt = data.contextMenu,
                root = data.contextMenuRoot;
            
            root.hovering = true;

            // abort if we're re-entering
            if (e && root.$layer && root.$layer.is(e.relatedTarget)) {
                e.preventDefault();
                e.stopImmediatePropagation();
            }

            // make sure only one item is selected
            (opt.$menu ? opt : root).$menu
                .children('.hover').trigger('contextmenu:blur');

            if ($this.hasClass('disabled') || $this.hasClass('not-selectable')) {
                opt.$selected = null;
                return;
            }
            
            $this.trigger('contextmenu:focus');
        },
        // :hover done manually so key handling is possible
        itemMouseleave: function(e) {
            var $this = $(this),
                data = $this.data(),
                opt = data.contextMenu,
                root = data.contextMenuRoot;

            if (root !== opt && root.$layer && root.$layer.is(e.relatedTarget)) {
                root.$selected && root.$selected.trigger('contextmenu:blur');
                e.preventDefault();
                e.stopImmediatePropagation();
                root.$selected = opt.$selected = opt.$node;
                return;
            }
            
            $this.trigger('contextmenu:blur');
        },
        // contextMenu item click
        itemClick: function(e) {
            var $this = $(this),
                data = $this.data(),
                opt = data.contextMenu,
                root = data.contextMenuRoot,
                key = data.contextMenuKey,
                callback;

            // abort if the key is unknown or disabled or is a menu
            if (!opt.items[key] || $this.is('.disabled, .context-menu-submenu, .context-menu-separator, .not-selectable')) {
                return;
            }

            e.preventDefault();
            e.stopImmediatePropagation();

            if ($.isFunction(root.callbacks[key]) && Object.prototype.hasOwnProperty.call(root.callbacks, key)) {
                // item-specific callback
                callback = root.callbacks[key];
            } else if ($.isFunction(root.callback)) {
                // default callback
                callback = root.callback;                
            } else {
                // no callback, no action
                return;
            }

            // hide menu if callback doesn't stop that
            if (callback.call(root.$trigger, key, root) !== false) {
                root.$menu.trigger('contextmenu:hide');
            } else if (root.$menu.parent().length) {
                op.update.call(root.$trigger, root);
            }
        },
        // ignore click events on input elements
        inputClick: function(e) {
            e.stopImmediatePropagation();
        },
        
        // hide <menu>
        hideMenu: function(e, data) {
            var root = $(this).data('contextMenuRoot');
            op.hide.call(root.$trigger, root, data && data.force);
        },
        // focus <command>
        focusItem: function(e) {
            e.stopPropagation();
            var $this = $(this),
                data = $this.data(),
                opt = data.contextMenu,
                root = data.contextMenuRoot;

            $this.addClass('hover')
                .siblings('.hover').trigger('contextmenu:blur');
            
            // remember selected
            opt.$selected = root.$selected = $this;
            
            // position sub-menu - do after show so dumb $.ui.position can keep up
            if (opt.$node) {
                root.positionSubmenu.call(opt.$node, opt.$menu);
            }
        },
        // blur <command>
        blurItem: function(e) {
            e.stopPropagation();
            var $this = $(this),
                data = $this.data(),
                opt = data.contextMenu;
            
            $this.removeClass('hover');
            opt.$selected = null;
        }
    },
    // operations
    op = {
        show: function(opt, x, y) {
            var $trigger = $(this),
                css = {};

            // hide any open menus
            $('#context-menu-layer').trigger('mousedown');

            // backreference for callbacks
            opt.$trigger = $trigger;

            // show event
            if (opt.events.show.call($trigger, opt) === false) {
                $currentTrigger = null;
                return;
            }

            // create or update context menu
            op.update.call($trigger, opt);
            
            // position menu
            opt.position.call($trigger, opt, x, y);

            // make sure we're in front
            if (opt.zIndex) {
                css.zIndex = zindex($trigger) + opt.zIndex;
            }
            
            // add layer
            op.layer.call(opt.$menu, opt, css.zIndex);
            
            // adjust sub-menu zIndexes
            opt.$menu.find('ul').css('zIndex', css.zIndex + 1);
            
            // position and show context menu
            opt.$menu.css( css )[opt.animation.show](opt.animation.duration, function() {
                $trigger.trigger('contextmenu:visible');
            });
            // make options available and set state
            $trigger
                .data('contextMenu', opt)
                .addClass("context-menu-active");
            
            // register key handler
            $(document).off('keydown.contextMenu').on('keydown.contextMenu', handle.key);
            // register autoHide handler
            if (opt.autoHide) {
                // mouse position handler
                $(document).on('mousemove.contextMenuAutoHide', function(e) {
                    // need to capture the offset on mousemove,
                    // since the page might've been scrolled since activation
                    var pos = $trigger.offset();
                    pos.right = pos.left + $trigger.outerWidth();
                    pos.bottom = pos.top + $trigger.outerHeight();
                    
                    if (opt.$layer && !opt.hovering && (!(e.pageX >= pos.left && e.pageX <= pos.right) || !(e.pageY >= pos.top && e.pageY <= pos.bottom))) {
                        // if mouse in menu...
                        opt.$menu.trigger('contextmenu:hide');
                    }
                });
            }
        },
        hide: function(opt, force) {
            var $trigger = $(this);
            if (!opt) {
                opt = $trigger.data('contextMenu') || {};
            }
            
            // hide event
            if (!force && opt.events && opt.events.hide.call($trigger, opt) === false) {
                return;
            }
            
            // remove options and revert state
            $trigger
                .removeData('contextMenu')
                .removeClass("context-menu-active");
            
            if (opt.$layer) {
                // keep layer for a bit so the contextmenu event can be aborted properly by opera
                setTimeout((function($layer) {
                    return function(){
                        $layer.remove();
                    };
                })(opt.$layer), 10);
                
                try {
                    delete opt.$layer;
                } catch(e) {
                    opt.$layer = null;
                }
            }
            
            // remove handle
            $currentTrigger = null;
            // remove selected
            opt.$menu.find('.hover').trigger('contextmenu:blur');
            opt.$selected = null;
            // unregister key and mouse handlers
            //$(document).off('.contextMenuAutoHide keydown.contextMenu'); // http://bugs.jquery.com/ticket/10705
            $(document).off('.contextMenuAutoHide').off('keydown.contextMenu');
            // hide menu
            opt.$menu && opt.$menu[opt.animation.hide](opt.animation.duration, function (){
                // tear down dynamically built menu after animation is completed.
                if (opt.build) {
                    opt.$menu.remove();
                    $.each(opt, function(key, value) {
                        switch (key) {
                            case 'ns':
                            case 'selector':
                            case 'build':
                            case 'trigger':
                                return true;

                            default:
                                opt[key] = undefined;
                                try {
                                    delete opt[key];
                                } catch (e) {}
                                return true;
                        }
                    });
                }
                
                setTimeout(function() {
                    $trigger.trigger('contextmenu:hidden');
                }, 10);
            });
        },
        create: function(opt, root) {
            if (root === undefined) {
                root = opt;
            }
            // create contextMenu
            opt.$menu = $('<ul class="context-menu-list"></ul>').addClass(opt.className || "").data({
                'contextMenu': opt,
                'contextMenuRoot': root
            });
            
            $.each(['callbacks', 'commands', 'inputs'], function(i,k){
                opt[k] = {};
                if (!root[k]) {
                    root[k] = {};
                }
            });
            
            root.accesskeys || (root.accesskeys = {});
            
            // create contextMenu items
            $.each(opt.items, function(key, item){
                var $t = $('<li class="context-menu-item"></li>').addClass(item.className || ""),
                    $label = null,
                    $input = null;
                
                // iOS needs to see a click-event bound to an element to actually
                // have the TouchEvents infrastructure trigger the click event
                $t.on('click', $.noop);
                
                item.$node = $t.data({
                    'contextMenu': opt,
                    'contextMenuRoot': root,
                    'contextMenuKey': key
                });
                
                // register accesskey
                // NOTE: the accesskey attribute should be applicable to any element, but Safari5 and Chrome13 still can't do that
                if (item.accesskey) {
                    var aks = splitAccesskey(item.accesskey);
                    for (var i=0, ak; ak = aks[i]; i++) {
                        if (!root.accesskeys[ak]) {
                            root.accesskeys[ak] = item;
                            item._name = item.name.replace(new RegExp('(' + ak + ')', 'i'), '<span class="context-menu-accesskey">$1</span>');
                            break;
                        }
                    }
                }
                
                if (typeof item == "string") {
                    $t.addClass('context-menu-separator not-selectable');
                } else if (item.type && types[item.type]) {
                    // run custom type handler
                    types[item.type].call($t, item, opt, root);
                    // register commands
                    $.each([opt, root], function(i,k){
                        k.commands[key] = item;
                        if ($.isFunction(item.callback)) {
                            k.callbacks[key] = item.callback;
                        }
                    });
                } else {
                    // add label for input
                    if (item.type == 'html') {
                        $t.addClass('context-menu-html not-selectable');
                    } else if (item.type) {
                        $label = $('<label></label>').appendTo($t);
                        $('<span></span>').text(item._name || item.name).appendTo($label);
                        $t.addClass('context-menu-input');
                        opt.hasTypes = true;
                        $.each([opt, root], function(i,k){
                            k.commands[key] = item;
                            k.inputs[key] = item;
                        });
                    } else if (item.items) {
                        item.type = 'sub';
                    }
                
                    switch (item.type) {
                        case 'text':
                            $input = $('<input type="text" value="1" name="" value="">')
                                .attr('name', 'context-menu-input-' + key)
                                .val(item.value || "")
                                .appendTo($label);
                            break;
                    
                        case 'textarea':
                            $input = $('<textarea name=""></textarea>')
                                .attr('name', 'context-menu-input-' + key)
                                .val(item.value || "")
                                .appendTo($label);

                            if (item.height) {
                                $input.height(item.height);
                            }
                            break;

                        case 'checkbox':
                            $input = $('<input type="checkbox" value="1" name="" value="">')
                                .attr('name', 'context-menu-input-' + key)
                                .val(item.value || "")
                                .prop("checked", !!item.selected)
                                .prependTo($label);
                            break;

                        case 'radio':
                            $input = $('<input type="radio" value="1" name="" value="">')
                                .attr('name', 'context-menu-input-' + item.radio)
                                .val(item.value || "")
                                .prop("checked", !!item.selected)
                                .prependTo($label);
                            break;
                    
                        case 'select':
                            $input = $('<select name="">')
                                .attr('name', 'context-menu-input-' + key)
                                .appendTo($label);
                            if (item.options) {
                                $.each(item.options, function(value, text) {
                                    $('<option></option>').val(value).text(text).appendTo($input);
                                });
                                $input.val(item.selected);
                            }
                            break;
                        
                        case 'sub':
                            $('<span></span>').text(item._name || item.name).appendTo($t);
                            item.appendTo = item.$node;
                            op.create(item, root);
                            $t.data('contextMenu', item).addClass('context-menu-submenu');
                            item.callback = null;
                            break;
                        
                        case 'html':
                            $(item.html).appendTo($t);
                            break;
                        
                        default:
                            $.each([opt, root], function(i,k){
                                k.commands[key] = item;
                                if ($.isFunction(item.callback)) {
                                    k.callbacks[key] = item.callback;
                                }
                            });
                            $('<span></span>').text(item._name || item.name || "").appendTo($t);
                            break;
                    }
                    
                    // disable key listener in <input>
                    if (item.type && item.type != 'sub' && item.type != 'html') {
                        $input
                            .on('focus', handle.focusInput)
                            .on('blur', handle.blurInput);
                        
                        if (item.events) {
                            $input.on(item.events, opt);
                        }
                    }
                
                    // add icons
                    if (item.icon) {
                        $t.addClass("icon icon-" + item.icon);
                    }
                }
                
                // cache contained elements
                item.$input = $input;
                item.$label = $label;

                // attach item to menu
                $t.appendTo(opt.$menu);
                
                // Disable text selection
                if (!opt.hasTypes && $.support.eventSelectstart) {
                    // browsers support user-select: none, 
                    // IE has a special event for text-selection
                    // browsers supporting neither will not be preventing text-selection
                    $t.on('selectstart.disableTextSelect', handle.abortevent);
                }
            });
            // attach contextMenu to <body> (to bypass any possible overflow:hidden issues on parents of the trigger element)
            if (!opt.$node) {
                opt.$menu.css('display', 'none').addClass('context-menu-root');
            }
            opt.$menu.appendTo(opt.appendTo || document.body);
        },
        resize: function($menu, nested) {
            // determine widths of submenus, as CSS won't grow them automatically
            // position:absolute within position:absolute; min-width:100; max-width:200; results in width: 100;
            // kinda sucks hard...

            // determine width of absolutely positioned element
            $menu.css({position: 'absolute', display: 'block'});
            // don't apply yet, because that would break nested elements' widths
            // add a pixel to circumvent word-break issue in IE9 - #80
            $menu.data('width', Math.ceil($menu.width()) + 1);
            // reset styles so they allow nested elements to grow/shrink naturally
            $menu.css({
                position: 'static',
                minWidth: '0px',
                maxWidth: '100000px'
            });
            // identify width of nested menus
            $menu.find('> li > ul').each(function() {
                op.resize($(this), true);
            });
            // reset and apply changes in the end because nested
            // elements' widths wouldn't be calculatable otherwise
            if (!nested) {
                $menu.find('ul').addBack().css({
                    position: '', 
                    display: '',
                    minWidth: '',
                    maxWidth: ''
                }).width(function() {
                    return $(this).data('width');
                });
            }
        },
        update: function(opt, root) {
            var $trigger = this;
            if (root === undefined) {
                root = opt;
                op.resize(opt.$menu);
            }
            // re-check disabled for each item
            opt.$menu.children().each(function(){
                var $item = $(this),
                    key = $item.data('contextMenuKey'),
                    item = opt.items[key],
                    disabled = ($.isFunction(item.disabled) && item.disabled.call($trigger, key, root)) || item.disabled === true;

                // dis- / enable item
                $item[disabled ? 'addClass' : 'removeClass']('disabled');
                
                if (item.type) {
                    // dis- / enable input elements
                    $item.find('input, select, textarea').prop('disabled', disabled);
                    
                    // update input states
                    switch (item.type) {
                        case 'text':
                        case 'textarea':
                            item.$input.val(item.value || "");
                            break;
                            
                        case 'checkbox':
                        case 'radio':
                            item.$input.val(item.value || "").prop('checked', !!item.selected);
                            break;
                            
                        case 'select':
                            item.$input.val(item.selected || "");
                            break;
                    }
                }
                
                if (item.$menu) {
                    // update sub-menu
                    op.update.call($trigger, item, root);
                }
            });
        },
        layer: function(opt, zIndex) {
            // add transparent layer for click area
            // filter and background for Internet Explorer, Issue #23
            var $layer = opt.$layer = $('<div id="context-menu-layer" style="position:fixed; z-index:' + zIndex + '; top:0; left:0; opacity: 0; filter: alpha(opacity=0); background-color: #000;"></div>')
                .css({height: $win.height(), width: $win.width(), display: 'block'})
                .data('contextMenuRoot', opt)
                .insertBefore(this)
                .on('contextmenu', handle.abortevent)
                .on('mousedown', handle.layerClick);
            
            // IE6 doesn't know position:fixed;
            if (document.body.style.maxWidth === undefined) {//IE6 doesn't support maxWidth
                $layer.css({
                    'position' : 'absolute',
                    'height' : $(document).height()
                });
            }
            
            return $layer;
        }
    };

// split accesskey according to http://www.whatwg.org/specs/web-apps/current-work/multipage/editing.html#assigned-access-key
function splitAccesskey(val) {
    var t = val.split(/\s+/),
        keys = [];
        
    for (var i=0, k; k = t[i]; i++) {
        k = k.charAt(0).toUpperCase(); // first character only
        // theoretically non-accessible characters should be ignored, but different systems, different keyboard layouts, ... screw it.
        // a map to look up already used access keys would be nice
        keys.push(k);
    }
    
    return keys;
}

// handle contextMenu triggers
$.fn.contextMenu = function(operation) {
    if (operation === undefined) {
        this.first().trigger('contextmenu');
    } else if (operation.x && operation.y) {
        this.first().trigger($.Event("contextmenu", {pageX: operation.x, pageY: operation.y}));
    } else if (operation === "hide") {
        var $menu = this.first().data('contextMenu') ? this.first().data('contextMenu').$menu : null;
        $menu && $menu.trigger('contextmenu:hide');
    } else if (operation === "destroy") {
        $.contextMenu("destroy", {context: this});
    } else if ($.isPlainObject(operation)) {
        operation.context = this;
        $.contextMenu("create", operation);
    } else if (operation) {
        this.removeClass('context-menu-disabled');
    } else if (!operation) {
        this.addClass('context-menu-disabled');
    }
    
    return this;
};

// manage contextMenu instances
$.contextMenu = function(operation, options) {
    if (typeof operation != 'string') {
        options = operation;
        operation = 'create';
    }
    
    if (typeof options == 'string') {
        options = {selector: options};
    } else if (options === undefined) {
        options = {};
    }
    
    // merge with default options
    var o = $.extend(true, {}, defaults, options || {});
    var $document = $(document);
    var $context = $document;
    var _hasContext = false;
    
    if (!o.context || !o.context.length) {
        o.context = document;
    } else {
        // you never know what they throw at you...
        $context = $(o.context).first();
        o.context = $context.get(0);
        _hasContext = o.context !== document;
    }
    
    switch (operation) {
        case 'create':
            // no selector no joy
            if (!o.selector) {
                throw new Error('No selector specified');
            }
            // make sure internal classes are not bound to
            if (o.selector.match(/.context-menu-(list|item|input)($|\s)/)) {
                throw new Error('Cannot bind to selector "' + o.selector + '" as it contains a reserved className');
            }
            if (!o.build && (!o.items || $.isEmptyObject(o.items))) {
                throw new Error('No Items specified');
            }
            counter ++;
            o.ns = '.contextMenu' + counter;
            if (!_hasContext) {
                namespaces[o.selector] = o.ns;
            }
            menus[o.ns] = o;
            
            // default to right click
            if (!o.trigger) {
                o.trigger = 'right';
            }
            
            if (!initialized) {
                // make sure item click is registered first
                $document
                    .on({
                        'contextmenu:hide.contextMenu': handle.hideMenu,
                        'prevcommand.contextMenu': handle.prevItem,
                        'nextcommand.contextMenu': handle.nextItem,
                        'contextmenu.contextMenu': handle.abortevent,
                        'mouseenter.contextMenu': handle.menuMouseenter,
                        'mouseleave.contextMenu': handle.menuMouseleave
                    }, '.context-menu-list')
                    .on('mouseup.contextMenu', '.context-menu-input', handle.inputClick)
                    .on({
                        'mouseup.contextMenu': handle.itemClick,
                        'contextmenu:focus.contextMenu': handle.focusItem,
                        'contextmenu:blur.contextMenu': handle.blurItem,
                        'contextmenu.contextMenu': handle.abortevent,
                        'mouseenter.contextMenu': handle.itemMouseenter,
                        'mouseleave.contextMenu': handle.itemMouseleave
                    }, '.context-menu-item');

                initialized = true;
            }
            
            // engage native contextmenu event
            $context
                .on('contextmenu' + o.ns, o.selector, o, handle.contextmenu);
            
            if (_hasContext) {
                // add remove hook, just in case
                $context.on('remove' + o.ns, function() {
                    $(this).contextMenu("destroy");
                });
            }
            
            switch (o.trigger) {
                case 'hover':
                        $context
                            .on('mouseenter' + o.ns, o.selector, o, handle.mouseenter)
                            .on('mouseleave' + o.ns, o.selector, o, handle.mouseleave);                    
                    break;
                    
                case 'left':
                        $context.on('click' + o.ns, o.selector, o, handle.click);
                    break;
                /*
                default:
                    // http://www.quirksmode.org/dom/events/contextmenu.html
                    $document
                        .on('mousedown' + o.ns, o.selector, o, handle.mousedown)
                        .on('mouseup' + o.ns, o.selector, o, handle.mouseup);
                    break;
                */
            }
            
            // create menu
            if (!o.build) {
                op.create(o);
            }
            break;
        
        case 'destroy':
            var $visibleMenu;
            if (_hasContext) {
                // get proper options 
                var context = o.context;
                $.each(menus, function(ns, o) {
                    if (o.context !== context) {
                        return true;
                    }
                    
                    $visibleMenu = $('.context-menu-list').filter(':visible');
                    if ($visibleMenu.length && $visibleMenu.data().contextMenuRoot.$trigger.is($(o.context).find(o.selector))) {
                        $visibleMenu.trigger('contextmenu:hide', {force: true});
                    }

                    try {
                        if (menus[o.ns].$menu) {
                            menus[o.ns].$menu.remove();
                        }

                        delete menus[o.ns];
                    } catch(e) {
                        menus[o.ns] = null;
                    }

                    $(o.context).off(o.ns);
                    
                    return true;
                });
            } else if (!o.selector) {
                $document.off('.contextMenu .contextMenuAutoHide');
                $.each(menus, function(ns, o) {
                    $(o.context).off(o.ns);
                });
                
                namespaces = {};
                menus = {};
                counter = 0;
                initialized = false;
                
                $('#context-menu-layer, .context-menu-list').remove();
            } else if (namespaces[o.selector]) {
                $visibleMenu = $('.context-menu-list').filter(':visible');
                if ($visibleMenu.length && $visibleMenu.data().contextMenuRoot.$trigger.is(o.selector)) {
                    $visibleMenu.trigger('contextmenu:hide', {force: true});
                }
                
                try {
                    if (menus[namespaces[o.selector]].$menu) {
                        menus[namespaces[o.selector]].$menu.remove();
                    }
                    
                    delete menus[namespaces[o.selector]];
                } catch(e) {
                    menus[namespaces[o.selector]] = null;
                }
                
                $document.off(namespaces[o.selector]);
            }
            break;
        
        case 'html5':
            // if <command> or <menuitem> are not handled by the browser,
            // or options was a bool true,
            // initialize $.contextMenu for them
            if ((!$.support.htmlCommand && !$.support.htmlMenuitem) || (typeof options == "boolean" && options)) {
                $('menu[type="context"]').each(function() {
                    if (this.id) {
                        $.contextMenu({
                            selector: '[contextmenu=' + this.id +']',
                            items: $.contextMenu.fromMenu(this)
                        });
                    }
                }).css('display', 'none');
            }
            break;
        
        default:
            throw new Error('Unknown operation "' + operation + '"');
    }
    
    return this;
};

// import values into <input> commands
$.contextMenu.setInputValues = function(opt, data) {
    if (data === undefined) {
        data = {};
    }
    
    $.each(opt.inputs, function(key, item) {
        switch (item.type) {
            case 'text':
            case 'textarea':
                item.value = data[key] || "";
                break;

            case 'checkbox':
                item.selected = data[key] ? true : false;
                break;
                
            case 'radio':
                item.selected = (data[item.radio] || "") == item.value ? true : false;
                break;
            
            case 'select':
                item.selected = data[key] || "";
                break;
        }
    });
};

// export values from <input> commands
$.contextMenu.getInputValues = function(opt, data) {
    if (data === undefined) {
        data = {};
    }
    
    $.each(opt.inputs, function(key, item) {
        switch (item.type) {
            case 'text':
            case 'textarea':
            case 'select':
                data[key] = item.$input.val();
                break;

            case 'checkbox':
                data[key] = item.$input.prop('checked');
                break;
                
            case 'radio':
                if (item.$input.prop('checked')) {
                    data[item.radio] = item.value;
                }
                break;
        }
    });
    
    return data;
};

// find <label for="xyz">
function inputLabel(node) {
    return (node.id && $('label[for="'+ node.id +'"]').val()) || node.name;
}

// convert <menu> to items object
function menuChildren(items, $children, counter) {
    if (!counter) {
        counter = 0;
    }
    
    $children.each(function() {
        var $node = $(this),
            node = this,
            nodeName = this.nodeName.toLowerCase(),
            label,
            item;
        
        // extract <label><input>
        if (nodeName == 'label' && $node.find('input, textarea, select').length) {
            label = $node.text();
            $node = $node.children().first();
            node = $node.get(0);
            nodeName = node.nodeName.toLowerCase();
        }
        
        /*
         * <menu> accepts flow-content as children. that means <embed>, <canvas> and such are valid menu items.
         * Not being the sadistic kind, $.contextMenu only accepts:
         * <command>, <menuitem>, <hr>, <span>, <p> <input [text, radio, checkbox]>, <textarea>, <select> and of course <menu>.
         * Everything else will be imported as an html node, which is not interfaced with contextMenu.
         */
        
        // http://www.whatwg.org/specs/web-apps/current-work/multipage/commands.html#concept-command
        switch (nodeName) {
            // http://www.whatwg.org/specs/web-apps/current-work/multipage/interactive-elements.html#the-menu-element
            case 'menu':
                item = {name: $node.attr('label'), items: {}};
                counter = menuChildren(item.items, $node.children(), counter);
                break;
            
            // http://www.whatwg.org/specs/web-apps/current-work/multipage/commands.html#using-the-a-element-to-define-a-command
            case 'a':
            // http://www.whatwg.org/specs/web-apps/current-work/multipage/commands.html#using-the-button-element-to-define-a-command
            case 'button':
                item = {
                    name: $node.text(),
                    disabled: !!$node.attr('disabled'),
                    callback: (function(){ return function(){ $node.click(); }; })()
                };
                break;
            
            // http://www.whatwg.org/specs/web-apps/current-work/multipage/commands.html#using-the-command-element-to-define-a-command

            case 'menuitem':
            case 'command':
                switch ($node.attr('type')) {
                    case undefined:
                    case 'command':
                    case 'menuitem':
                        item = {
                            name: $node.attr('label'),
                            disabled: !!$node.attr('disabled'),
                            callback: (function(){ return function(){ $node.click(); }; })()
                        };
                        break;
                        
                    case 'checkbox':
                        item = {
                            type: 'checkbox',
                            disabled: !!$node.attr('disabled'),
                            name: $node.attr('label'),
                            selected: !!$node.attr('checked')
                        };
                        break;
                        
                    case 'radio':
                        item = {
                            type: 'radio',
                            disabled: !!$node.attr('disabled'),
                            name: $node.attr('label'),
                            radio: $node.attr('radiogroup'),
                            value: $node.attr('id'),
                            selected: !!$node.attr('checked')
                        };
                        break;
                        
                    default:
                        item = undefined;
                }
                break;
 
            case 'hr':
                item = '-------';
                break;
                
            case 'input':
                switch ($node.attr('type')) {
                    case 'text':
                        item = {
                            type: 'text',
                            name: label || inputLabel(node),
                            disabled: !!$node.attr('disabled'),
                            value: $node.val()
                        };
                        break;
                        
                    case 'checkbox':
                        item = {
                            type: 'checkbox',
                            name: label || inputLabel(node),
                            disabled: !!$node.attr('disabled'),
                            selected: !!$node.attr('checked')
                        };
                        break;
                        
                    case 'radio':
                        item = {
                            type: 'radio',
                            name: label || inputLabel(node),
                            disabled: !!$node.attr('disabled'),
                            radio: !!$node.attr('name'),
                            value: $node.val(),
                            selected: !!$node.attr('checked')
                        };
                        break;
                    
                    default:
                        item = undefined;
                        break;
                }
                break;
                
            case 'select':
                item = {
                    type: 'select',
                    name: label || inputLabel(node),
                    disabled: !!$node.attr('disabled'),
                    selected: $node.val(),
                    options: {}
                };
                $node.children().each(function(){
                    item.options[this.value] = $(this).text();
                });
                break;
                
            case 'textarea':
                item = {
                    type: 'textarea',
                    name: label || inputLabel(node),
                    disabled: !!$node.attr('disabled'),
                    value: $node.val()
                };
                break;
            
            case 'label':
                break;
            
            default:
                item = {type: 'html', html: $node.clone(true)};
                break;
        }
        
        if (item) {
            counter++;
            items['key' + counter] = item;
        }
    });
    
    return counter;
}

// convert html5 menu
$.contextMenu.fromMenu = function(element) {
    var $this = $(element),
        items = {};
        
    menuChildren(items, $this.children());
    
    return items;
};

// make defaults accessible
$.contextMenu.defaults = defaults;
$.contextMenu.types = types;
// export internal functions - undocumented, for hacking only!
$.contextMenu.handle = handle;
$.contextMenu.op = op;
$.contextMenu.menus = menus;

})(jQuery);

/*
---

name: Core

description: The heart of MooTools.

license: MIT-style license.

copyright: Copyright (c) 2006-2012 [Valerio Proietti](http://mad4milk.net/).

authors: The MooTools production team (http://mootools.net/developers/)

inspiration:
  - Class implementation inspired by [Base.js](http://dean.edwards.name/weblog/2006/03/base/) Copyright (c) 2006 Dean Edwards, [GNU Lesser General Public License](http://opensource.org/licenses/lgpl-license.php)
  - Some functionality inspired by [Prototype.js](http://prototypejs.org) Copyright (c) 2005-2007 Sam Stephenson, [MIT License](http://opensource.org/licenses/mit-license.php)

provides: [Core, MooTools, Type, typeOf, instanceOf, Native]

...
*/

(function(){

this.MooTools = {
	version: '1.4.5',
	build: 'ab8ea8824dc3b24b6666867a2c4ed58ebb762cf0'
};

// typeOf, instanceOf

var typeOf = this.typeOf = function(item){
	if (item == null) return 'null';
	if (item.$family != null) return item.$family();

	if (item.nodeName){
		if (item.nodeType == 1) return 'element';
		if (item.nodeType == 3) return (/\S/).test(item.nodeValue) ? 'textnode' : 'whitespace';
	} else if (typeof item.length == 'number'){
		if (item.callee) return 'arguments';
		if ('item' in item) return 'collection';
	}

	return typeof item;
};

var instanceOf = this.instanceOf = function(item, object){
	if (item == null) return false;
	var constructor = item.$constructor || item.constructor;
	while (constructor){
		if (constructor === object) return true;
		constructor = constructor.parent;
	}
	/*<ltIE8>*/
	if (!item.hasOwnProperty) return false;
	/*</ltIE8>*/
	return item instanceof object;
};

// Function overloading

var Function = this.Function;

var enumerables = true;
for (var i in {toString: 1}) enumerables = null;
if (enumerables) enumerables = ['hasOwnProperty', 'valueOf', 'isPrototypeOf', 'propertyIsEnumerable', 'toLocaleString', 'toString', 'constructor'];

Function.prototype.overloadSetter = function(usePlural){
	var self = this;
	return function(a, b){
		if (a == null) return this;
		if (usePlural || typeof a != 'string'){
			for (var k in a) self.call(this, k, a[k]);
			if (enumerables) for (var i = enumerables.length; i--;){
				k = enumerables[i];
				if (a.hasOwnProperty(k)) self.call(this, k, a[k]);
			}
		} else {
			self.call(this, a, b);
		}
		return this;
	};
};

Function.prototype.overloadGetter = function(usePlural){
	var self = this;
	return function(a){
		var args, result;
		if (typeof a != 'string') args = a;
		else if (arguments.length > 1) args = arguments;
		else if (usePlural) args = [a];
		if (args){
			result = {};
			for (var i = 0; i < args.length; i++) result[args[i]] = self.call(this, args[i]);
		} else {
			result = self.call(this, a);
		}
		return result;
	};
};

Function.prototype.extend = function(key, value){
	this[key] = value;
}.overloadSetter();

Function.prototype.implement = function(key, value){
	this.prototype[key] = value;
}.overloadSetter();

// From

var slice = Array.prototype.slice;

Function.from = function(item){
	return (typeOf(item) == 'function') ? item : function(){
		return item;
	};
};

Array.from = function(item){
	if (item == null) return [];
	return (Type.isEnumerable(item) && typeof item != 'string') ? (typeOf(item) == 'array') ? item : slice.call(item) : [item];
};

Number.from = function(item){
	var number = parseFloat(item);
	return isFinite(number) ? number : null;
};

String.from = function(item){
	return item + '';
};

// hide, protect

Function.implement({

	hide: function(){
		this.$hidden = true;
		return this;
	},

	protect: function(){
		this.$protected = true;
		return this;
	}

});

// Type

var Type = this.Type = function(name, object){
	if (name){
		var lower = name.toLowerCase();
		var typeCheck = function(item){
			return (typeOf(item) == lower);
		};

		Type['is' + name] = typeCheck;
		if (object != null){
			object.prototype.$family = (function(){
				return lower;
			}).hide();
			//<1.2compat>
			object.type = typeCheck;
			//</1.2compat>
		}
	}

	if (object == null) return null;

	if(object.extend) {
		object.extend(this);
	} else {
		object.extend = function(key, value){
			this[key] = value;
		}.overloadSetter();
		console.log("problems with " + (typeOf(object)))
		object.extend(this);
	}
	
	object.$constructor = Type;
	object.prototype.$constructor = object;

	return object;
};

var toString = Object.prototype.toString;

Type.isEnumerable = function(item){
	return (item != null && typeof item.length == 'number' && toString.call(item) != '[object Function]' );
};

var hooks = {};

var hooksOf = function(object){
	var type = typeOf(object.prototype);
	return hooks[type] || (hooks[type] = []);
};

var implement = function(name, method){
	if (method && method.$hidden) return;

	var hooks = hooksOf(this);

	for (var i = 0; i < hooks.length; i++){
		var hook = hooks[i];
		if (typeOf(hook) == 'type') implement.call(hook, name, method);
		else hook.call(this, name, method);
	}

	var previous = this.prototype[name];
	if (previous == null || !previous.$protected) this.prototype[name] = method;

	if (this[name] == null && typeOf(method) == 'function') extend.call(this, name, function(item){
		return method.apply(item, slice.call(arguments, 1));
	});
};

var extend = function(name, method){
	if (method && method.$hidden) return;
	var previous = this[name];
	if (previous == null || !previous.$protected) this[name] = method;
};

Type.implement({

	implement: implement.overloadSetter(),

	extend: extend.overloadSetter(),

	alias: function(name, existing){
		implement.call(this, name, this.prototype[existing]);
	}.overloadSetter(),

	mirror: function(hook){
		hooksOf(this).push(hook);
		return this;
	}

});

new Type('Type', Type);

// Default Types

var force = function(name, object, methods){
	var isType = (object != Object),
		prototype = object.prototype;

	if (isType) object = new Type(name, object);

	for (var i = 0, l = methods.length; i < l; i++){
		var key = methods[i],
			generic = object[key],
			proto = prototype[key];

		if (generic) generic.protect();
		if (isType && proto &&  proto.protect) object.implement(key, proto.protect());
	}

	if (isType){
		var methodsEnumerable = prototype.propertyIsEnumerable(methods[0]);
		object.forEachMethod = function(fn){
			if (!methodsEnumerable) for (var i = 0, l = methods.length; i < l; i++){
				fn.call(prototype, prototype[methods[i]], methods[i]);
			}
			for (var key in prototype) fn.call(prototype, prototype[key], key)
		};
	}

	return force;
};

force('String', String, [
	'charAt', 'charCodeAt', 'concat', 'indexOf', 'lastIndexOf', 'match', 'quote', 'replace', 'search',
	'slice', 'split', 'substr', 'substring', 'trim', 'toLowerCase', 'toUpperCase'
])('Array', Array, [
	'pop', 'push', 'reverse', 'shift', 'sort', 'splice', 'unshift', 'concat', 'join', 'slice',
	'indexOf', 'lastIndexOf', 'filter', 'forEach', 'every', 'map', 'some', 'reduce', 'reduceRight'
])('Number', Number, [
	'toExponential', 'toFixed', 'toLocaleString', 'toPrecision'
])('Function', Function, [
	'apply', 'call', 'bind'
])('RegExp', RegExp, [
	'exec', 'test'
])('Object', Object, [
	'create', 'defineProperty', 'defineProperties', 'keys',
	'getPrototypeOf', 'getOwnPropertyDescriptor', 'getOwnPropertyNames',
	'preventExtensions', 'isExtensible', 'seal', 'isSealed', 'freeze', 'isFrozen'
])('Date', Date, ['now']);

Object.extend = extend.overloadSetter();

Date.extend('now', function(){
	return +(new Date);
});

new Type('Boolean', Boolean);

// fixes NaN returning as Number

Number.prototype.$family = function(){
	return isFinite(this) ? 'number' : 'null';
}.hide();

// Number.random

Number.extend('random', function(min, max){
	return Math.floor(Math.random() * (max - min + 1) + min);
});

// forEach, each

var hasOwnProperty = Object.prototype.hasOwnProperty;
Object.extend('forEach', function(object, fn, bind){
	for (var key in object){
		if (hasOwnProperty.call(object, key)) fn.call(bind, object[key], key, object);
	}
});

Object.each = Object.forEach;

Array.implement({

	forEach: function(fn, bind){
		for (var i = 0, l = this.length; i < l; i++){
			if (i in this) fn.call(bind, this[i], i, this);
		}
	},

	each: function(fn, bind){
		Array.forEach(this, fn, bind);
		return this;
	}

});

// Array & Object cloning, Object merging and appending

var cloneOf = function(item){
	switch (typeOf(item)){
		case 'array': return item.clone();
		case 'object': return Object.clone(item);
		default: return item;
	}
};

Array.implement('clone', function(){
	var i = this.length, clone = new Array(i);
	while (i--) clone[i] = cloneOf(this[i]);
	return clone;
});

var mergeOne = function(source, key, current){
	switch (typeOf(current)){
		case 'object':
			if (typeOf(source[key]) == 'object') Object.merge(source[key], current);
			else source[key] = Object.clone(current);
		break;
		case 'array': source[key] = current.clone(); break;
		default: source[key] = current;
	}
	return source;
};

Object.extend({

	merge: function(source, k, v){
		if (typeOf(k) == 'string') return mergeOne(source, k, v);
		for (var i = 1, l = arguments.length; i < l; i++){
			var object = arguments[i];
			for (var key in object) mergeOne(source, key, object[key]);
		}
		return source;
	},

	clone: function(object){
		var clone = {};
		for (var key in object) clone[key] = cloneOf(object[key]);
		return clone;
	},

	append: function(original){
		for (var i = 1, l = arguments.length; i < l; i++){
			var extended = arguments[i] || {};
			for (var key in extended) original[key] = extended[key];
		}
		return original;
	}

});

// Object-less types

['Object', 'WhiteSpace', 'TextNode', 'Collection', 'Arguments'].each(function(name){
	new Type(name);
});

// Unique ID

var UID = Date.now();

String.extend('uniqueID', function(){
	return (UID++).toString(36);
});

//<1.2compat>

var Hash = this.Hash = new Type('Hash', function(object){
	if (typeOf(object) == 'hash') object = Object.clone(object.getClean());
	for (var key in object) this[key] = object[key];
	return this;
});

Hash.implement({

	forEach: function(fn, bind){
		Object.forEach(this, fn, bind);
	},

	getClean: function(){
		var clean = {};
		for (var key in this){
			if (this.hasOwnProperty(key)) clean[key] = this[key];
		}
		return clean;
	},

	getLength: function(){
		var length = 0;
		for (var key in this){
			if (this.hasOwnProperty(key)) length++;
		}
		return length;
	}

});

Hash.alias('each', 'forEach');

Object.type = Type.isObject;

var Native = this.Native = function(properties){
	return new Type(properties.name, properties.initialize);
};

Native.type = Type.type;

Native.implement = function(objects, methods){
	for (var i = 0; i < objects.length; i++) objects[i].implement(methods);
	return Native;
};

var arrayType = Array.type;
Array.type = function(item){
	return instanceOf(item, Array) || arrayType(item);
};

this.$A = function(item){
	return Array.from(item).slice();
};

this.$arguments = function(i){
	return function(){
		return arguments[i];
	};
};

this.$chk = function(obj){
	return !!(obj || obj === 0);
};

this.$clear = function(timer){
	clearTimeout(timer);
	clearInterval(timer);
	return null;
};

this.$defined = function(obj){
	return (obj != null);
};

this.$each = function(iterable, fn, bind){
	var type = typeOf(iterable);
	((type == 'arguments' || type == 'collection' || type == 'array' || type == 'elements') ? Array : Object).each(iterable, fn, bind);
};

this.$empty = function(){};

this.$extend = function(original, extended){
	return Object.append(original, extended);
};

this.$H = function(object){
	return new Hash(object);
};

this.$merge = function(){
	var args = Array.slice(arguments);
	args.unshift({});
	return Object.merge.apply(null, args);
};

this.$lambda = Function.from;
this.$mixin = Object.merge;
this.$random = Number.random;
this.$splat = Array.from;
this.$time = Date.now;

this.$type = function(object){
	var type = typeOf(object);
	if (type == 'elements') return 'array';
	return (type == 'null') ? false : type;
};

this.$unlink = function(object){
	switch (typeOf(object)){
		case 'object': return Object.clone(object);
		case 'array': return Array.clone(object);
		case 'hash': return new Hash(object);
		default: return object;
	}
};

//</1.2compat>

})();


/*
---

name: Object

description: Object generic methods

license: MIT-style license.

requires: Type

provides: [Object, Hash]

...
*/

(function(){

var hasOwnProperty = Object.prototype.hasOwnProperty;

Object.extend({

	subset: function(object, keys){
		var results = {};
		for (var i = 0, l = keys.length; i < l; i++){
			var k = keys[i];
			if (k in object) results[k] = object[k];
		}
		return results;
	},

	map: function(object, fn, bind){
		var results = {};
		for (var key in object){
			if (hasOwnProperty.call(object, key)) results[key] = fn.call(bind, object[key], key, object);
		}
		return results;
	},

	filter: function(object, fn, bind){
		var results = {};
		for (var key in object){
			var value = object[key];
			if (hasOwnProperty.call(object, key) && fn.call(bind, value, key, object)) results[key] = value;
		}
		return results;
	},

	every: function(object, fn, bind){
		for (var key in object){
			if (hasOwnProperty.call(object, key) && !fn.call(bind, object[key], key)) return false;
		}
		return true;
	},

	some: function(object, fn, bind){
		for (var key in object){
			if (hasOwnProperty.call(object, key) && fn.call(bind, object[key], key)) return true;
		}
		return false;
	},

	keys: function(object){
		var keys = [];
		for (var key in object){
			if (hasOwnProperty.call(object, key)) keys.push(key);
		}
		return keys;
	},

	values: function(object){
		var values = [];
		for (var key in object){
			if (hasOwnProperty.call(object, key)) values.push(object[key]);
		}
		return values;
	},

	getLength: function(object){
		return Object.keys(object).length;
	},

	keyOf: function(object, value){
		for (var key in object){
			if (hasOwnProperty.call(object, key) && object[key] === value) return key;
		}
		return null;
	},

	contains: function(object, value){
		return Object.keyOf(object, value) != null;
	},

	toQueryString: function(object, base){
		var queryString = [];

		Object.each(object, function(value, key){
			if (base) key = base + '[' + key + ']';
			var result;
			switch (typeOf(value)){
				case 'object': result = Object.toQueryString(value, key); break;
				case 'array':
					var qs = {};
					value.each(function(val, i){
						qs[i] = val;
					});
					result = Object.toQueryString(qs, key);
				break;
				default: result = key + '=' + encodeURIComponent(value);
			}
			if (value != null) queryString.push(result);
		});

		return queryString.join('&');
	}

});

})();

//<1.2compat>

Hash.implement({

	has: Object.prototype.hasOwnProperty,

	keyOf: function(value){
		return Object.keyOf(this, value);
	},

	hasValue: function(value){
		return Object.contains(this, value);
	},

	extend: function(properties){
		Hash.each(properties || {}, function(value, key){
			Hash.set(this, key, value);
		}, this);
		return this;
	},

	combine: function(properties){
		Hash.each(properties || {}, function(value, key){
			Hash.include(this, key, value);
		}, this);
		return this;
	},

	erase: function(key){
		if (this.hasOwnProperty(key)) delete this[key];
		return this;
	},

	get: function(key){
		return (this.hasOwnProperty(key)) ? this[key] : null;
	},

	set: function(key, value){
		if (!this[key] || this.hasOwnProperty(key)) this[key] = value;
		return this;
	},

	empty: function(){
		Hash.each(this, function(value, key){
			delete this[key];
		}, this);
		return this;
	},

	include: function(key, value){
		if (this[key] == null) this[key] = value;
		return this;
	},

	map: function(fn, bind){
		return new Hash(Object.map(this, fn, bind));
	},

	filter: function(fn, bind){
		return new Hash(Object.filter(this, fn, bind));
	},

	every: function(fn, bind){
		return Object.every(this, fn, bind);
	},

	some: function(fn, bind){
		return Object.some(this, fn, bind);
	},

	getKeys: function(){
		return Object.keys(this);
	},

	getValues: function(){
		return Object.values(this);
	},

	toQueryString: function(base){
		return Object.toQueryString(this, base);
	}

});

Hash.extend = Object.append;

Hash.alias({indexOf: 'keyOf', contains: 'hasValue'});

//</1.2compat>


/*
---

name: String

description: Contains String Prototypes like camelCase, capitalize, test, and toInt.

license: MIT-style license.

requires: Type

provides: String

...
*/

String.implement({

	test: function(regex, params){
		return ((typeOf(regex) == 'regexp') ? regex : new RegExp('' + regex, params)).test(this);
	},

	contains: function(string, separator){
		return (separator) ? (separator + this + separator).indexOf(separator + string + separator) > -1 : String(this).indexOf(string) > -1;
	},

	trim: function(){
		return String(this).replace(/^\s+|\s+$/g, '');
	},

	clean: function(){
		return String(this).replace(/\s+/g, ' ').trim();
	},

	camelCase: function(){
		return String(this).replace(/-\D/g, function(match){
			return match.charAt(1).toUpperCase();
		});
	},

	hyphenate: function(){
		return String(this).replace(/[A-Z]/g, function(match){
			return ('-' + match.charAt(0).toLowerCase());
		});
	},

	capitalize: function(){
		return String(this).replace(/\b[a-z]/g, function(match){
			return match.toUpperCase();
		});
	},

	escapeRegExp: function(){
		return String(this).replace(/([-.*+?^${}()|[\]\/\\])/g, '\\$1');
	},

	toInt: function(base){
		return parseInt(this, base || 10);
	},

	toFloat: function(){
		return parseFloat(this);
	},

	hexToRgb: function(array){
		var hex = String(this).match(/^#?(\w{1,2})(\w{1,2})(\w{1,2})$/);
		return (hex) ? hex.slice(1).hexToRgb(array) : null;
	},

	rgbToHex: function(array){
		var rgb = String(this).match(/\d{1,3}/g);
		return (rgb) ? rgb.rgbToHex(array) : null;
	},

	substitute: function(object, regexp){
		return String(this).replace(regexp || (/\\?\{([^{}]+)\}/g), function(match, name){
			if (match.charAt(0) == '\\') return match.slice(1);
			return (object[name] != null) ? object[name] : '';
		});
	}

});


/*
---

name: Array

description: Contains Array Prototypes like each, contains, and erase.

license: MIT-style license.

requires: Type

provides: Array

...
*/

Array.implement({

	/*<!ES5>*/
	every: function(fn, bind){
		for (var i = 0, l = this.length >>> 0; i < l; i++){
			if ((i in this) && !fn.call(bind, this[i], i, this)) return false;
		}
		return true;
	},

	filter: function(fn, bind){
		var results = [];
		for (var value, i = 0, l = this.length >>> 0; i < l; i++) if (i in this){
			value = this[i];
			if (fn.call(bind, value, i, this)) results.push(value);
		}
		return results;
	},

	indexOf: function(item, from){
		var length = this.length >>> 0;
		for (var i = (from < 0) ? Math.max(0, length + from) : from || 0; i < length; i++){
			if (this[i] === item) return i;
		}
		return -1;
	},

	map: function(fn, bind){
		var length = this.length >>> 0, results = Array(length);
		for (var i = 0; i < length; i++){
			if (i in this) results[i] = fn.call(bind, this[i], i, this);
		}
		return results;
	},

	some: function(fn, bind){
		for (var i = 0, l = this.length >>> 0; i < l; i++){
			if ((i in this) && fn.call(bind, this[i], i, this)) return true;
		}
		return false;
	},
	/*</!ES5>*/

	clean: function(){
		return this.filter(function(item){
			return item != null;
		});
	},

	invoke: function(methodName){
		var args = Array.slice(arguments, 1);
		return this.map(function(item){
			return item[methodName].apply(item, args);
		});
	},

	associate: function(keys){
		var obj = {}, length = Math.min(this.length, keys.length);
		for (var i = 0; i < length; i++) obj[keys[i]] = this[i];
		return obj;
	},

	link: function(object){
		var result = {};
		for (var i = 0, l = this.length; i < l; i++){
			for (var key in object){
				if (object[key](this[i])){
					result[key] = this[i];
					delete object[key];
					break;
				}
			}
		}
		return result;
	},

	contains: function(item, from){
		return this.indexOf(item, from) != -1;
	},

	append: function(array){
		this.push.apply(this, array);
		return this;
	},

	getLast: function(){
		return (this.length) ? this[this.length - 1] : null;
	},

	getRandom: function(){
		return (this.length) ? this[Number.random(0, this.length - 1)] : null;
	},

	include: function(item){
		if (!this.contains(item)) this.push(item);
		return this;
	},

	combine: function(array){
		for (var i = 0, l = array.length; i < l; i++) this.include(array[i]);
		return this;
	},

	erase: function(item){
		for (var i = this.length; i--;){
			if (this[i] === item) this.splice(i, 1);
		}
		return this;
	},

	empty: function(){
		this.length = 0;
		return this;
	},

	flatten: function(){
		var array = [];
		for (var i = 0, l = this.length; i < l; i++){
			var type = typeOf(this[i]);
			if (type == 'null') continue;
			array = array.concat((type == 'array' || type == 'collection' || type == 'arguments' || instanceOf(this[i], Array)) ? Array.flatten(this[i]) : this[i]);
		}
		return array;
	},

	pick: function(){
		for (var i = 0, l = this.length; i < l; i++){
			if (this[i] != null) return this[i];
		}
		return null;
	},

	hexToRgb: function(array){
		if (this.length != 3) return null;
		var rgb = this.map(function(value){
			if (value.length == 1) value += value;
			return value.toInt(16);
		});
		return (array) ? rgb : 'rgb(' + rgb + ')';
	},

	rgbToHex: function(array){
		if (this.length < 3) return null;
		if (this.length == 4 && this[3] == 0 && !array) return 'transparent';
		var hex = [];
		for (var i = 0; i < 3; i++){
			var bit = (this[i] - 0).toString(16);
			hex.push((bit.length == 1) ? '0' + bit : bit);
		}
		return (array) ? hex : '#' + hex.join('');
	}

});

//<1.2compat>

Array.alias('extend', 'append');

var $pick = function(){
	return Array.from(arguments).pick();
};

//</1.2compat>


/*
---

script: More.js

name: More

description: MooTools More

license: MIT-style license

authors:
  - Guillermo Rauch
  - Thomas Aylott
  - Scott Kyle
  - Arian Stolwijk
  - Tim Wienk
  - Christoph Pojer
  - Aaron Newton
  - Jacob Thornton

requires:
  - Core/MooTools

provides: [MooTools.More]

...
*/

MooTools.More = {
	'version': '1.4.0.1',
	'build': 'a4244edf2aa97ac8a196fc96082dd35af1abab87'
};


/*
---

script: Array.Extras.js

name: Array.Extras

description: Extends the Array native object to include useful methods to work with arrays.

license: MIT-style license

authors:
  - Christoph Pojer
  - Sebastian Marke

requires:
  - Core/Array
  - MooTools.More

provides: [Array.Extras]

...
*/

(function(nil){

Array.implement({

	min: function(){
		return Math.min.apply(null, this);
	},

	max: function(){
		return Math.max.apply(null, this);
	},

	average: function(){
		return this.length ? this.sum() / this.length : 0;
	},

	sum: function(){
		var result = 0, l = this.length;
		if (l){
			while (l--) result += this[l];
		}
		return result;
	},

	unique: function(){
		return [].combine(this);
	},

	shuffle: function(){
		for (var i = this.length; i && --i;){
			var temp = this[i], r = Math.floor(Math.random() * ( i + 1 ));
			this[i] = this[r];
			this[r] = temp;
		}
		return this;
	},

	reduce: function(fn, value){
		for (var i = 0, l = this.length; i < l; i++){
			if (i in this) value = value === nil ? this[i] : fn.call(null, value, this[i], i, this);
		}
		return value;
	},

	reduceRight: function(fn, value){
		var i = this.length;
		while (i--){
			if (i in this) value = value === nil ? this[i] : fn.call(null, value, this[i], i, this);
		}
		return value;
	}

});

})();

/*
---
name: upoopu.ubrowser.injectables.context_menu

description: The tools to inject context menus to strategic places

requires:
    - namespace
    - jquery-latest.min.js
    - jquery.ui.position.js
    - jquery.contextMenu.js
    - Core
    - Object
    - String
    - Array.Extras
provides: [upoopu.ubrowser.injectables.context_menu]

...
*/
namespace("upoopu.ubrowser.injectables.context_menu", function(ns){
  $.contextMenu.types.header = function(item, opt, root){
    return $("<strong>" + item.name + "</strong>").appendTo(this);
  };
  return ns.inject = function(check_for_utopia, cb){
    return $.contextMenu({
      selector: 'ul,td.province',
      className: 'data-title',
      autoHide: true,
      build: function(trigger, e){
        var cfu, hash, ref$, i$, prov, isl, kd, thievery_cb;
        cfu = check_for_utopia();
        if (!cfu) {
          return false;
        } else {
          hash = $(trigger).parent().attr("hash");
          ref$ = hash.split(":"), i$ = ref$.length - 3, prov = ref$[i$], isl = ref$[i$ + 1], kd = ref$[i$ + 2];
          thievery_cb = function(action, thieves){
            return function(key, opt){
              return cb("thief_op", [prov, kd, isl, action, thieves], 1);
            };
          };
          return {
            items: {
              title: {
                name: "Actions",
                type: "header",
                disabled: true
              },
              intel: {
                name: "Intel",
                items: {
                  header: {
                    name: prov + " (" + kd + ":" + isl + ")",
                    type: "header",
                    disabled: true
                  },
                  sot1: {
                    name: "SoT 1 thief",
                    callback: thievery_cb("SPY_ON_THRONE", 1)
                  },
                  sot10pct: {
                    name: "SoT 10% thieves",
                    callback: thievery_cb("SPY_ON_THRONE", {
                      pct: 10
                    })
                  },
                  som1: {
                    name: "SoM 1 thief",
                    callback: thievery_cb("SPY_ON_MILITARY", 1)
                  },
                  som10pct: {
                    name: "SoM 10% thieves",
                    callback: thievery_cb("SPY_ON_MILITARY", {
                      pct: 10
                    })
                  },
                  survey1: {
                    name: "Survey 1 thief",
                    callback: thievery_cb("SURVEY", 1)
                  },
                  survey10pct: {
                    name: "Survey 10% thieves",
                    callback: thievery_cb("SURVEY", {
                      pct: 10
                    })
                  },
                  sos1: {
                    name: "SoS 1 thief",
                    callback: thievery_cb("SPY_ON_SCIENCES", 1)
                  },
                  sos10pct: {
                    name: "SoS 10% thieves",
                    callback: thievery_cb("SPY_ON_SCIENCES", {
                      pct: 10
                    })
                  },
                  inf1: {
                    name: "Infiltrate 1 thief",
                    callback: thievery_cb("INFILTRATE", 1)
                  },
                  inf10pct: {
                    name: "Infiltrate 10% thieves",
                    callback: thievery_cb("INFILTRATE", {
                      pct: 10
                    })
                  }
                }
              }
            }
          };
        }
      }
    });
  };
});

/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview
 * Utility functions and classes for Soy.
 *
 * <p>
 * The top portion of this file contains utilities for Soy users:<ul>
 *   <li> soy.StringBuilder: Compatible with the 'stringbuilder' code style.
 *   <li> soy.renderElement: Render template and set as innerHTML of an element.
 *   <li> soy.renderAsFragment: Render template and return as HTML fragment.
 * </ul>
 *
 * <p>
 * The bottom portion of this file contains utilities that should only be called
 * by Soy-generated JS code. Please do not use these functions directly from
 * your hand-writen code. Their names all start with '$$'.
 *
 */


// COPIED FROM nogoog_shim.js

// Create closure namespaces.
var goog = goog || {};


goog.inherits = function(childCtor, parentCtor) {
  /** @constructor */
  function tempCtor() {}
  tempCtor.prototype = parentCtor.prototype;
  childCtor.superClass_ = parentCtor.prototype;
  childCtor.prototype = new tempCtor();
  childCtor.prototype.constructor = childCtor;
};


// Just enough browser detection for this file.
if (!goog.userAgent) {
  goog.userAgent = (function() {
    var userAgent = "";
    if ("undefined" !== typeof navigator && navigator
        && "string" == typeof navigator.userAgent) {
      userAgent = navigator.userAgent;
    }
    var isOpera = userAgent.indexOf('Opera') == 0;
    return {
      /**
       * @type {boolean}
       */
      HAS_JSCRIPT: typeof 'ScriptEngine' in this,
      /**
       * @type {boolean}
       */
      IS_OPERA: isOpera,
      /**
       * @type {boolean}
       */
      IS_IE: !isOpera && userAgent.indexOf('MSIE') != -1,
      /**
       * @type {boolean}
       */
      IS_WEBKIT: !isOpera && userAgent.indexOf('WebKit') != -1
    };
  })();
}

if (!goog.asserts) {
  goog.asserts = {
    /**
     * @param {...*} var_args
     */
    fail: function (var_args) {}
  };
}


// Stub out the document wrapper used by renderAs*.
if (!goog.dom) {
  goog.dom = {};
  /**
   * @param {Document=} d
   * @constructor
   */
  goog.dom.DomHelper = function(d) {
    this.document_ = d || document;
  };
  /**
   * @return {!Document}
   */
  goog.dom.DomHelper.prototype.getDocument = function() {
    return this.document_;
  };
  /**
   * Creates a new element.
   * @param {string} name Tag name.
   * @return {!Element}
   */
  goog.dom.DomHelper.prototype.createElement = function(name) {
    return this.document_.createElement(name);
  };
  /**
   * Creates a new document fragment.
   * @return {!DocumentFragment}
   */
  goog.dom.DomHelper.prototype.createDocumentFragment = function() {
    return this.document_.createDocumentFragment();
  };
}


if (!goog.format) {
  goog.format = {
    insertWordBreaks: function(str, maxCharsBetweenWordBreaks) {
      str = String(str);

      var resultArr = [];
      var resultArrLen = 0;

      // These variables keep track of important state inside str.
      var isInTag = false;  // whether we're inside an HTML tag
      var isMaybeInEntity = false;  // whether we might be inside an HTML entity
      var numCharsWithoutBreak = 0;  // number of chars since last word break
      var flushIndex = 0;  // index of first char not yet flushed to resultArr

      for (var i = 0, n = str.length; i < n; ++i) {
        var charCode = str.charCodeAt(i);

        // If hit maxCharsBetweenWordBreaks, and not space next, then add <wbr>.
        if (numCharsWithoutBreak >= maxCharsBetweenWordBreaks &&
            // space
            charCode != 32) {
          resultArr[resultArrLen++] = str.substring(flushIndex, i);
          flushIndex = i;
          resultArr[resultArrLen++] = goog.format.WORD_BREAK;
          numCharsWithoutBreak = 0;
        }

        if (isInTag) {
          // If inside an HTML tag and we see '>', it's the end of the tag.
          if (charCode == 62) {
            isInTag = false;
          }

        } else if (isMaybeInEntity) {
          switch (charCode) {
            // Inside an entity, a ';' is the end of the entity.
            // The entity that just ended counts as one char, so increment
            // numCharsWithoutBreak.
          case 59:  // ';'
            isMaybeInEntity = false;
            ++numCharsWithoutBreak;
            break;
            // If maybe inside an entity and we see '<', we weren't actually in
            // an entity. But now we're inside and HTML tag.
          case 60:  // '<'
            isMaybeInEntity = false;
            isInTag = true;
            break;
            // If maybe inside an entity and we see ' ', we weren't actually in
            // an entity. Just correct the state and reset the
            // numCharsWithoutBreak since we just saw a space.
          case 32:  // ' '
            isMaybeInEntity = false;
            numCharsWithoutBreak = 0;
            break;
          }

        } else {  // !isInTag && !isInEntity
          switch (charCode) {
            // When not within a tag or an entity and we see '<', we're now
            // inside an HTML tag.
          case 60:  // '<'
            isInTag = true;
            break;
            // When not within a tag or an entity and we see '&', we might be
            // inside an entity.
          case 38:  // '&'
            isMaybeInEntity = true;
            break;
            // When we see a space, reset the numCharsWithoutBreak count.
          case 32:  // ' '
            numCharsWithoutBreak = 0;
            break;
            // When we see a non-space, increment the numCharsWithoutBreak.
          default:
            ++numCharsWithoutBreak;
            break;
          }
        }
      }

      // Flush the remaining chars at the end of the string.
      resultArr[resultArrLen++] = str.substring(flushIndex);

      return resultArr.join('');
    },
    /**
     * String inserted as a word break by insertWordBreaks(). Safari requires
     * <wbr></wbr>, Opera needs the 'shy' entity, though this will give a
     * visible hyphen at breaks. Other browsers just use <wbr>.
     * @type {string}
     * @private
     */
    WORD_BREAK: goog.userAgent.IS_WEBKIT
        ? '<wbr></wbr>' : goog.userAgent.IS_OPERA ? '&shy;' : '<wbr>'
  };
}


if (!goog.i18n) {
  goog.i18n = {
    bidi: {
      /**
       * Check the directionality of a piece of text, return true if the piece
       * of text should be laid out in RTL direction.
       * @param {string} text The piece of text that need to be detected.
       * @param {boolean=} opt_isHtml Whether {@code text} is HTML/HTML-escaped.
       *     Default: false.
       * @return {boolean}
       * @private
       */
      detectRtlDirectionality: function(text, opt_isHtml) {
        text = soyshim.$$bidiStripHtmlIfNecessary_(text, opt_isHtml);
        return soyshim.$$bidiRtlWordRatio_(text)
            > soyshim.$$bidiRtlDetectionThreshold_;
      }
    }
  };
}

/**
 * Directionality enum.
 * @enum {number}
 */
goog.i18n.bidi.Dir = {
  RTL: -1,
  UNKNOWN: 0,
  LTR: 1
};


/**
 * Convert a directionality given in various formats to a goog.i18n.bidi.Dir
 * constant. Useful for interaction with different standards of directionality
 * representation.
 *
 * @param {goog.i18n.bidi.Dir|number|boolean} givenDir Directionality given in
 *     one of the following formats:
 *     1. A goog.i18n.bidi.Dir constant.
 *     2. A number (positive = LRT, negative = RTL, 0 = unknown).
 *     3. A boolean (true = RTL, false = LTR).
 * @return {goog.i18n.bidi.Dir} A goog.i18n.bidi.Dir constant matching the given
 *     directionality.
 */
goog.i18n.bidi.toDir = function(givenDir) {
  if (typeof givenDir == 'number') {
    return givenDir > 0 ? goog.i18n.bidi.Dir.LTR :
        givenDir < 0 ? goog.i18n.bidi.Dir.RTL : goog.i18n.bidi.Dir.UNKNOWN;
  } else {
    return givenDir ? goog.i18n.bidi.Dir.RTL : goog.i18n.bidi.Dir.LTR;
  }
};


/**
 * Utility class for formatting text for display in a potentially
 * opposite-directionality context without garbling. Provides the following
 * functionality:
 *
 * @param {goog.i18n.bidi.Dir|number|boolean} dir The context
 *     directionality as a number
 *     (positive = LRT, negative = RTL, 0 = unknown).
 * @constructor
 */
goog.i18n.BidiFormatter = function(dir) {
  this.dir_ = goog.i18n.bidi.toDir(dir);
};


/**
 * Returns "dir=ltr" or "dir=rtl", depending on {@code text}'s estimated
 * directionality, if it is not the same as the context directionality.
 * Otherwise, returns the empty string.
 *
 * @param {string} text Text whose directionality is to be estimated.
 * @param {boolean=} opt_isHtml Whether {@code text} is HTML / HTML-escaped.
 *     Default: false.
 * @return {string} "dir=rtl" for RTL text in non-RTL context; "dir=ltr" for LTR
 *     text in non-LTR context; else, the empty string.
 */
goog.i18n.BidiFormatter.prototype.dirAttr = function (text, opt_isHtml) {
  var dir = soy.$$bidiTextDir(text, opt_isHtml);
  return dir && dir != this.dir_ ? dir < 0 ? 'dir=rtl' : 'dir=ltr' : '';
};

/**
 * Returns the trailing horizontal edge, i.e. "right" or "left", depending on
 * the global bidi directionality.
 * @return {string} "left" for RTL context and "right" otherwise.
 */
goog.i18n.BidiFormatter.prototype.endEdge = function () {
  return this.dir_ < 0 ? 'left' : 'right';
};

/**
 * Returns the Unicode BiDi mark matching the context directionality (LRM for
 * LTR context directionality, RLM for RTL context directionality), or the
 * empty string for neutral / unknown context directionality.
 *
 * @return {string} LRM for LTR context directionality and RLM for RTL context
 *     directionality.
 */
goog.i18n.BidiFormatter.prototype.mark = function () {
  return (
      (this.dir_ > 0) ? '\u200E' /*LRM*/ :
      (this.dir_ < 0) ? '\u200F' /*RLM*/ :
      '');
};

/**
 * Returns a Unicode BiDi mark matching the context directionality (LRM or RLM)
 * if the directionality or the exit directionality of {@code text} are opposite
 * to the context directionality. Otherwise returns the empty string.
 *
 * @param {string} text The input text.
 * @param {boolean=} opt_isHtml Whether {@code text} is HTML / HTML-escaped.
 *     Default: false.
 * @return {string} A Unicode bidi mark matching the global directionality or
 *     the empty string.
 */
goog.i18n.BidiFormatter.prototype.markAfter = function (text, opt_isHtml) {
  var dir = soy.$$bidiTextDir(text, opt_isHtml);
  return soyshim.$$bidiMarkAfterKnownDir_(this.dir_, dir, text, opt_isHtml);
};

/**
 * Formats a string of unknown directionality for use in HTML output of the
 * context directionality, so an opposite-directionality string is neither
 * garbled nor garbles what follows it.
 *
 * @param {string} str The input text.
 * @param {boolean=} placeholder This argument exists for consistency with the
 *     Closure Library. Specifying it has no effect.
 * @return {string} Input text after applying the above processing.
 */
goog.i18n.BidiFormatter.prototype.spanWrap = function(str, placeholder) {
  str = String(str);
  var textDir = soy.$$bidiTextDir(str, true);
  var reset = soyshim.$$bidiMarkAfterKnownDir_(this.dir_, textDir, str, true);
  if (textDir > 0 && this.dir_ <= 0) {
    str = '<span dir=ltr>' + str + '</span>';
  } else if (textDir < 0 && this.dir_ >= 0) {
    str = '<span dir=rtl>' + str + '</span>';
  }
  return str + reset;
};

/**
 * Returns the leading horizontal edge, i.e. "left" or "right", depending on
 * the global bidi directionality.
 * @return {string} "right" for RTL context and "left" otherwise.
 */
goog.i18n.BidiFormatter.prototype.startEdge = function () {
  return this.dir_ < 0 ? 'right' : 'left';
};

/**
 * Formats a string of unknown directionality for use in plain-text output of
 * the context directionality, so an opposite-directionality string is neither
 * garbled nor garbles what follows it.
 * As opposed to {@link #spanWrap}, this makes use of unicode BiDi formatting
 * characters. In HTML, its *only* valid use is inside of elements that do not
 * allow mark-up, e.g. an 'option' tag.
 *
 * @param {string} str The input text.
 * @param {boolean=} placeholder This argument exists for consistency with the
 *     Closure Library. Specifying it has no effect.
 * @return {string} Input text after applying the above processing.
 */
goog.i18n.BidiFormatter.prototype.unicodeWrap = function(str, placeholder) {
  str = String(str);
  var textDir = soy.$$bidiTextDir(str, true);
  var reset = soyshim.$$bidiMarkAfterKnownDir_(this.dir_, textDir, str, true);
  if (textDir > 0 && this.dir_ <= 0) {
    str = '\u202A' + str + '\u202C';
  } else if (textDir < 0 && this.dir_ >= 0) {
    str = '\u202B' + str + '\u202C';
  }
  return str + reset;
};


goog.string = {

  /**
   * Converts \r\n, \r, and \n to <br>s
   * @param {*} str The string in which to convert newlines.
   * @param {boolean=} opt_xml Whether to use XML compatible tags.
   * @return {string} A copy of {@code str} with converted newlines.
   */
  newLineToBr: function(str, opt_xml) {

    str = String(str);

    // This quick test helps in the case when there are no chars to replace,
    // in the worst case this makes barely a difference to the time taken.
    if (!goog.string.NEWLINE_TO_BR_RE_.test(str)) {
      return str;
    }

    return str.replace(/(\r\n|\r|\n)/g, opt_xml ? '<br />' : '<br>');
  },
  urlEncode: encodeURIComponent,
  /**
   * Regular expression used within newlineToBr().
   * @type {RegExp}
   * @private
   */
  NEWLINE_TO_BR_RE_: /[\r\n]/
};


/**
 * Utility class to facilitate much faster string concatenation in IE,
 * using Array.join() rather than the '+' operator.  For other browsers
 * we simply use the '+' operator.
 *
 * @param {Object|number|string|boolean=} opt_a1 Optional first initial item
 *     to append.
 * @param {...Object|number|string|boolean} var_args Other initial items to
 *     append, e.g., new goog.string.StringBuffer('foo', 'bar').
 * @constructor
 */
goog.string.StringBuffer = function(opt_a1, var_args) {
  /**
   * Internal buffer for the string to be concatenated.
   * @type {string|Array}
   * @private
   */
  this.buffer_ = goog.userAgent.HAS_JSCRIPT ? [] : '';

  if (opt_a1 != null) {
    this.append.apply(this, arguments);
  }
};


/**
 * Length of internal buffer (faster than calling buffer_.length).
 * Only used for IE.
 * @type {number}
 * @private
 */
goog.string.StringBuffer.prototype.bufferLength_ = 0;

/**
 * Appends one or more items to the string.
 *
 * Calling this with null, undefined, or empty arguments is an error.
 *
 * @param {Object|number|string|boolean} a1 Required first string.
 * @param {Object|number|string|boolean=} opt_a2 Optional second string.
 * @param {...Object|number|string|boolean} var_args Other items to append,
 *     e.g., sb.append('foo', 'bar', 'baz').
 * @return {goog.string.StringBuffer} This same StringBuilder object.
 */
goog.string.StringBuffer.prototype.append = function(a1, opt_a2, var_args) {

  if (goog.userAgent.HAS_JSCRIPT) {
    if (opt_a2 == null) {  // no second argument (note: undefined == null)
      // Array assignment is 2x faster than Array push.  Also, use a1
      // directly to avoid arguments instantiation, another 2x improvement.
      this.buffer_[this.bufferLength_++] = a1;
    } else {
      var arr = /**@type {Array.<number|string|boolean>}*/this.buffer_;
      arr.push.apply(arr, arguments);
      this.bufferLength_ = this.buffer_.length;
    }

  } else {

    // Use a1 directly to avoid arguments instantiation for single-arg case.
    this.buffer_ += a1;
    if (opt_a2 != null) {  // no second argument (note: undefined == null)
      for (var i = 1; i < arguments.length; i++) {
        this.buffer_ += arguments[i];
      }
    }
  }

  return this;
};


/**
 * Clears the string.
 */
goog.string.StringBuffer.prototype.clear = function() {

  if (goog.userAgent.HAS_JSCRIPT) {
     this.buffer_.length = 0;  // reuse array to avoid creating new object
     this.bufferLength_ = 0;

   } else {
     this.buffer_ = '';
   }
};


/**
 * Returns the concatenated string.
 *
 * @return {string} The concatenated string.
 */
goog.string.StringBuffer.prototype.toString = function() {

  if (goog.userAgent.HAS_JSCRIPT) {
    var str = this.buffer_.join('');
    // Given a string with the entire contents, simplify the StringBuilder by
    // setting its contents to only be this string, rather than many fragments.
    this.clear();
    if (str) {
      this.append(str);
    }
    return str;

  } else {
    return /** @type {string} */ (this.buffer_);
  }
};


if (!goog.soy) goog.soy = {
  /**
   * Helper function to render a Soy template and then set the
   * output string as the innerHTML of an element. It is recommended
   * to use this helper function instead of directly setting
   * innerHTML in your hand-written code, so that it will be easier
   * to audit the code for cross-site scripting vulnerabilities.
   *
   * @param {Function} template The Soy template defining element's content.
   * @param {Object=} opt_templateData The data for the template.
   * @param {Object=} opt_injectedData The injected data for the template.
   * @param {(goog.dom.DomHelper|Document)=} opt_dom The context in which DOM
   *     nodes will be created.
   */
  renderAsElement: function(
    template, opt_templateData, opt_injectedData, opt_dom) {
    return /** @type {!Element} */ (soyshim.$$renderWithWrapper_(
        template, opt_templateData, opt_dom, true /* asElement */,
        opt_injectedData));
  },
  /**
   * Helper function to render a Soy template into a single node or
   * a document fragment. If the rendered HTML string represents a
   * single node, then that node is returned (note that this is
   * *not* a fragment, despite them name of the method). Otherwise a
   * document fragment is returned containing the rendered nodes.
   *
   * @param {Function} template The Soy template defining element's content.
   * @param {Object=} opt_templateData The data for the template.
   * @param {Object=} opt_injectedData The injected data for the template.
   * @param {(goog.dom.DomHelper|Document)=} opt_dom The context in which DOM
   *     nodes will be created.
   * @return {!Node} The resulting node or document fragment.
   */
  renderAsFragment: function(
    template, opt_templateData, opt_injectedData, opt_dom) {
    return soyshim.$$renderWithWrapper_(
        template, opt_templateData, opt_dom, false /* asElement */,
        opt_injectedData);
  },
  /**
   * Helper function to render a Soy template and then set the output string as
   * the innerHTML of an element. It is recommended to use this helper function
   * instead of directly setting innerHTML in your hand-written code, so that it
   * will be easier to audit the code for cross-site scripting vulnerabilities.
   *
   * NOTE: New code should consider using goog.soy.renderElement instead.
   *
   * @param {Element} element The element whose content we are rendering.
   * @param {Function} template The Soy template defining the element's content.
   * @param {Object=} opt_templateData The data for the template.
   * @param {Object=} opt_injectedData The injected data for the template.
   */
  renderElement: function(
      element, template, opt_templateData, opt_injectedData) {
    element.innerHTML = template(opt_templateData, null, opt_injectedData);
  }
};


var soy = { esc: {} };
var soydata = {};
var soyshim = { $$DEFAULT_TEMPLATE_DATA_: {} };
/**
 * Helper function to render a Soy template into a single node or a document
 * fragment. If the rendered HTML string represents a single node, then that
 * node is returned. Otherwise a document fragment is created and returned
 * (wrapped in a DIV element if #opt_singleNode is true).
 *
 * @param {Function} template The Soy template defining the element's content.
 * @param {Object=} opt_templateData The data for the template.
 * @param {(goog.dom.DomHelper|Document)=} opt_dom The context in which DOM
 *     nodes will be created.
 * @param {boolean=} opt_asElement Whether to wrap the fragment in an
 *     element if the template does not render a single element. If true,
 *     result is always an Element.
 * @param {Object=} opt_injectedData The injected data for the template.
 * @return {!Node} The resulting node or document fragment.
 * @private
 */
soyshim.$$renderWithWrapper_ = function(
    template, opt_templateData, opt_dom, opt_asElement, opt_injectedData) {

  var dom = opt_dom || document;
  var wrapper = dom.createElement('div');
  wrapper.innerHTML = template(
    opt_templateData || soyshim.$$DEFAULT_TEMPLATE_DATA_, undefined,
    opt_injectedData);

  // If the template renders as a single element, return it.
  if (wrapper.childNodes.length == 1) {
    var firstChild = wrapper.firstChild;
    if (!opt_asElement || firstChild.nodeType == 1 /* Element */) {
      return /** @type {!Node} */ (firstChild);
    }
  }

  // If we're forcing it to be a single element, return the wrapper DIV.
  if (opt_asElement) {
    return wrapper;
  }

  // Otherwise, create and return a fragment.
  var fragment = dom.createDocumentFragment();
  while (wrapper.firstChild) {
    fragment.appendChild(wrapper.firstChild);
  }
  return fragment;
};


/**
 * Returns a Unicode BiDi mark matching bidiGlobalDir (LRM or RLM) if the
 * directionality or the exit directionality of text are opposite to
 * bidiGlobalDir. Otherwise returns the empty string.
 * If opt_isHtml, makes sure to ignore the LTR nature of the mark-up and escapes
 * in text, making the logic suitable for HTML and HTML-escaped text.
 * @param {number} bidiGlobalDir The global directionality context: 1 if ltr, -1
 *     if rtl, 0 if unknown.
 * @param {number} dir text's directionality: 1 if ltr, -1 if rtl, 0 if unknown.
 * @param {string} text The text whose directionality is to be estimated.
 * @param {boolean=} opt_isHtml Whether text is HTML/HTML-escaped.
 *     Default: false.
 * @return {string} A Unicode bidi mark matching bidiGlobalDir, or
 *     the empty string when text's overall and exit directionalities both match
 *     bidiGlobalDir, or bidiGlobalDir is 0 (unknown).
 * @private
 */
soyshim.$$bidiMarkAfterKnownDir_ = function(
    bidiGlobalDir, dir, text, opt_isHtml) {
  return (
      bidiGlobalDir > 0 && (dir < 0 ||
          soyshim.$$bidiIsRtlExitText_(text, opt_isHtml)) ? '\u200E' : // LRM
      bidiGlobalDir < 0 && (dir > 0 ||
          soyshim.$$bidiIsLtrExitText_(text, opt_isHtml)) ? '\u200F' : // RLM
      '');
};


/**
 * Strips str of any HTML mark-up and escapes. Imprecise in several ways, but
 * precision is not very important, since the result is only meant to be used
 * for directionality detection.
 * @param {string} str The string to be stripped.
 * @param {boolean=} opt_isHtml Whether str is HTML / HTML-escaped.
 *     Default: false.
 * @return {string} The stripped string.
 * @private
 */
soyshim.$$bidiStripHtmlIfNecessary_ = function(str, opt_isHtml) {
  return opt_isHtml ? str.replace(soyshim.$$BIDI_HTML_SKIP_RE_, ' ') : str;
};


/**
 * Simplified regular expression for am HTML tag (opening or closing) or an HTML
 * escape - the things we want to skip over in order to ignore their ltr
 * characters.
 * @type {RegExp}
 * @private
 */
soyshim.$$BIDI_HTML_SKIP_RE_ = /<[^>]*>|&[^;]+;/g;


/**
 * A practical pattern to identify strong LTR character. This pattern is not
 * theoretically correct according to unicode standard. It is simplified for
 * performance and small code size.
 * @type {string}
 * @private
 */
soyshim.$$bidiLtrChars_ =
    'A-Za-z\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02B8\u0300-\u0590\u0800-\u1FFF' +
    '\u2C00-\uFB1C\uFDFE-\uFE6F\uFEFD-\uFFFF';


/**
 * A practical pattern to identify strong neutral and weak character. This
 * pattern is not theoretically correct according to unicode standard. It is
 * simplified for performance and small code size.
 * @type {string}
 * @private
 */
soyshim.$$bidiNeutralChars_ =
    '\u0000-\u0020!-@[-`{-\u00BF\u00D7\u00F7\u02B9-\u02FF\u2000-\u2BFF';


/**
 * A practical pattern to identify strong RTL character. This pattern is not
 * theoretically correct according to unicode standard. It is simplified for
 * performance and small code size.
 * @type {string}
 * @private
 */
soyshim.$$bidiRtlChars_ = '\u0591-\u07FF\uFB1D-\uFDFD\uFE70-\uFEFC';


/**
 * Regular expressions to check if a piece of text is of RTL directionality
 * on first character with strong directionality.
 * @type {RegExp}
 * @private
 */
soyshim.$$bidiRtlDirCheckRe_ = new RegExp(
    '^[^' + soyshim.$$bidiLtrChars_ + ']*[' + soyshim.$$bidiRtlChars_ + ']');


/**
 * Regular expressions to check if a piece of text is of neutral directionality.
 * Url are considered as neutral.
 * @type {RegExp}
 * @private
 */
soyshim.$$bidiNeutralDirCheckRe_ = new RegExp(
    '^[' + soyshim.$$bidiNeutralChars_ + ']*$|^http://');


/**
 * Check the directionality of the a piece of text based on the first character
 * with strong directionality.
 * @param {string} str string being checked.
 * @return {boolean} return true if rtl directionality is being detected.
 * @private
 */
soyshim.$$bidiIsRtlText_ = function(str) {
  return soyshim.$$bidiRtlDirCheckRe_.test(str);
};


/**
 * Check the directionality of the a piece of text based on the first character
 * with strong directionality.
 * @param {string} str string being checked.
 * @return {boolean} true if all characters have neutral directionality.
 * @private
 */
soyshim.$$bidiIsNeutralText_ = function(str) {
  return soyshim.$$bidiNeutralDirCheckRe_.test(str);
};


/**
 * This constant controls threshold of rtl directionality.
 * @type {number}
 * @private
 */
soyshim.$$bidiRtlDetectionThreshold_ = 0.40;


/**
 * Returns the RTL ratio based on word count.
 * @param {string} str the string that need to be checked.
 * @return {number} the ratio of RTL words among all words with directionality.
 * @private
 */
soyshim.$$bidiRtlWordRatio_ = function(str) {
  var rtlCount = 0;
  var totalCount = 0;
  var tokens = str.split(' ');
  for (var i = 0; i < tokens.length; i++) {
    if (soyshim.$$bidiIsRtlText_(tokens[i])) {
      rtlCount++;
      totalCount++;
    } else if (!soyshim.$$bidiIsNeutralText_(tokens[i])) {
      totalCount++;
    }
  }

  return totalCount == 0 ? 0 : rtlCount / totalCount;
};


/**
 * Regular expressions to check if the last strongly-directional character in a
 * piece of text is LTR.
 * @type {RegExp}
 * @private
 */
soyshim.$$bidiLtrExitDirCheckRe_ = new RegExp(
    '[' + soyshim.$$bidiLtrChars_ + '][^' + soyshim.$$bidiRtlChars_ + ']*$');


/**
 * Regular expressions to check if the last strongly-directional character in a
 * piece of text is RTL.
 * @type {RegExp}
 * @private
 */
soyshim.$$bidiRtlExitDirCheckRe_ = new RegExp(
    '[' + soyshim.$$bidiRtlChars_ + '][^' + soyshim.$$bidiLtrChars_ + ']*$');


/**
 * Check if the exit directionality a piece of text is LTR, i.e. if the last
 * strongly-directional character in the string is LTR.
 * @param {string} str string being checked.
 * @param {boolean=} opt_isHtml Whether str is HTML / HTML-escaped.
 *     Default: false.
 * @return {boolean} Whether LTR exit directionality was detected.
 * @private
 */
soyshim.$$bidiIsLtrExitText_ = function(str, opt_isHtml) {
  str = soyshim.$$bidiStripHtmlIfNecessary_(str, opt_isHtml);
  return soyshim.$$bidiLtrExitDirCheckRe_.test(str);
};


/**
 * Check if the exit directionality a piece of text is RTL, i.e. if the last
 * strongly-directional character in the string is RTL.
 * @param {string} str string being checked.
 * @param {boolean=} opt_isHtml Whether str is HTML / HTML-escaped.
 *     Default: false.
 * @return {boolean} Whether RTL exit directionality was detected.
 * @private
 */
soyshim.$$bidiIsRtlExitText_ = function(str, opt_isHtml) {
  str = soyshim.$$bidiStripHtmlIfNecessary_(str, opt_isHtml);
  return soyshim.$$bidiRtlExitDirCheckRe_.test(str);
};


// =============================================================================
// COPIED FROM soyutils_usegoog.js


// -----------------------------------------------------------------------------
// StringBuilder (compatible with the 'stringbuilder' code style).


/**
 * Utility class to facilitate much faster string concatenation in IE,
 * using Array.join() rather than the '+' operator.  For other browsers
 * we simply use the '+' operator.
 *
 * @param {Object} var_args Initial items to append,
 *     e.g., new soy.StringBuilder('foo', 'bar').
 * @constructor
 */
soy.StringBuilder = goog.string.StringBuffer;


// -----------------------------------------------------------------------------
// soydata: Defines typed strings, e.g. an HTML string {@code "a<b>c"} is
// semantically distinct from the plain text string {@code "a<b>c"} and smart
// templates can take that distinction into account.

/**
 * A type of textual content.
 * @enum {number}
 */
soydata.SanitizedContentKind = {

  /**
   * A snippet of HTML that does not start or end inside a tag, comment, entity,
   * or DOCTYPE; and that does not contain any executable code
   * (JS, {@code <object>}s, etc.) from a different trust domain.
   */
  HTML: 0,

  /**
   * A sequence of code units that can appear between quotes (either kind) in a
   * JS program without causing a parse error, and without causing any side
   * effects.
   * <p>
   * The content should not contain unescaped quotes, newlines, or anything else
   * that would cause parsing to fail or to cause a JS parser to finish the
   * string its parsing inside the content.
   * <p>
   * The content must also not end inside an escape sequence ; no partial octal
   * escape sequences or odd number of '{@code \}'s at the end.
   */
  JS_STR_CHARS: 1,

  /** A properly encoded portion of a URI. */
  URI: 2,

  /** An attribute name and value such as {@code dir="ltr"}. */
  HTML_ATTRIBUTE: 3
};


/**
 * A string-like object that carries a content-type.
 * @param {string} content
 * @constructor
 * @private
 */
soydata.SanitizedContent = function(content) {
  /**
   * The textual content.
   * @type {string}
   */
  this.content = content;
};

/** @type {soydata.SanitizedContentKind} */
soydata.SanitizedContent.prototype.contentKind;

/** @override */
soydata.SanitizedContent.prototype.toString = function() {
  return this.content;
};


/**
 * Content of type {@link soydata.SanitizedContentKind.HTML}.
 * @param {string} content A string of HTML that can safely be embedded in
 *     a PCDATA context in your app.  If you would be surprised to find that an
 *     HTML sanitizer produced {@code s} (e.g. it runs code or fetches bad URLs)
 *     and you wouldn't write a template that produces {@code s} on security or
 *     privacy grounds, then don't pass {@code s} here.
 * @constructor
 * @extends {soydata.SanitizedContent}
 */
soydata.SanitizedHtml = function(content) {
  soydata.SanitizedContent.call(this, content);
};
goog.inherits(soydata.SanitizedHtml, soydata.SanitizedContent);

/** @override */
soydata.SanitizedHtml.prototype.contentKind = soydata.SanitizedContentKind.HTML;


/**
 * Content of type {@link soydata.SanitizedContentKind.JS_STR_CHARS}.
 * @param {string} content A string of JS that when evaled, produces a
 *     value that does not depend on any sensitive data and has no side effects
 *     <b>OR</b> a string of JS that does not reference any variables or have
 *     any side effects not known statically to the app authors.
 * @constructor
 * @extends {soydata.SanitizedContent}
 */
soydata.SanitizedJsStrChars = function(content) {
  soydata.SanitizedContent.call(this, content);
};
goog.inherits(soydata.SanitizedJsStrChars, soydata.SanitizedContent);

/** @override */
soydata.SanitizedJsStrChars.prototype.contentKind =
    soydata.SanitizedContentKind.JS_STR_CHARS;


/**
 * Content of type {@link soydata.SanitizedContentKind.URI}.
 * @param {string} content A chunk of URI that the caller knows is safe to
 *     emit in a template.
 * @constructor
 * @extends {soydata.SanitizedContent}
 */
soydata.SanitizedUri = function(content) {
  soydata.SanitizedContent.call(this, content);
};
goog.inherits(soydata.SanitizedUri, soydata.SanitizedContent);

/** @override */
soydata.SanitizedUri.prototype.contentKind = soydata.SanitizedContentKind.URI;


/**
 * Content of type {@link soydata.SanitizedContentKind.HTML_ATTRIBUTE}.
 * @param {string} content An attribute name and value, such as
 *     {@code dir="ltr"}.
 * @constructor
 * @extends {soydata.SanitizedContent}
 */
soydata.SanitizedHtmlAttribute = function(content) {
  soydata.SanitizedContent.call(this, content);
};
goog.inherits(soydata.SanitizedHtmlAttribute, soydata.SanitizedContent);

/** @override */
soydata.SanitizedHtmlAttribute.prototype.contentKind =
    soydata.SanitizedContentKind.HTML_ATTRIBUTE;


// -----------------------------------------------------------------------------
// Public utilities.


/**
 * Helper function to render a Soy template and then set the output string as
 * the innerHTML of an element. It is recommended to use this helper function
 * instead of directly setting innerHTML in your hand-written code, so that it
 * will be easier to audit the code for cross-site scripting vulnerabilities.
 *
 * NOTE: New code should consider using goog.soy.renderElement instead.
 *
 * @param {Element} element The element whose content we are rendering.
 * @param {Function} template The Soy template defining the element's content.
 * @param {Object=} opt_templateData The data for the template.
 * @param {Object=} opt_injectedData The injected data for the template.
 */
soy.renderElement = goog.soy.renderElement;


/**
 * Helper function to render a Soy template into a single node or a document
 * fragment. If the rendered HTML string represents a single node, then that
 * node is returned (note that this is *not* a fragment, despite them name of
 * the method). Otherwise a document fragment is returned containing the
 * rendered nodes.
 *
 * NOTE: New code should consider using goog.soy.renderAsFragment
 * instead (note that the arguments are different).
 *
 * @param {Function} template The Soy template defining the element's content.
 * @param {Object=} opt_templateData The data for the template.
 * @param {Document=} opt_document The document used to create DOM nodes. If not
 *     specified, global document object is used.
 * @param {Object=} opt_injectedData The injected data for the template.
 * @return {!Node} The resulting node or document fragment.
 */
soy.renderAsFragment = function(
    template, opt_templateData, opt_document, opt_injectedData) {
  return goog.soy.renderAsFragment(
      template, opt_templateData, opt_injectedData,
      new goog.dom.DomHelper(opt_document));
};


/**
 * Helper function to render a Soy template into a single node. If the rendered
 * HTML string represents a single node, then that node is returned. Otherwise,
 * a DIV element is returned containing the rendered nodes.
 *
 * NOTE: New code should consider using goog.soy.renderAsElement
 * instead (note that the arguments are different).
 *
 * @param {Function} template The Soy template defining the element's content.
 * @param {Object=} opt_templateData The data for the template.
 * @param {Document=} opt_document The document used to create DOM nodes. If not
 *     specified, global document object is used.
 * @param {Object=} opt_injectedData The injected data for the template.
 * @return {!Element} Rendered template contents, wrapped in a parent DIV
 *     element if necessary.
 */
soy.renderAsElement = function(
    template, opt_templateData, opt_document, opt_injectedData) {
  return goog.soy.renderAsElement(
      template, opt_templateData, opt_injectedData,
      new goog.dom.DomHelper(opt_document));
};


// -----------------------------------------------------------------------------
// Below are private utilities to be used by Soy-generated code only.


/**
 * Builds an augmented data object to be passed when a template calls another,
 * and needs to pass both original data and additional params. The returned
 * object will contain both the original data and the additional params. If the
 * same key appears in both, then the value from the additional params will be
 * visible, while the value from the original data will be hidden. The original
 * data object will be used, but not modified.
 *
 * @param {!Object} origData The original data to pass.
 * @param {Object} additionalParams The additional params to pass.
 * @return {Object} An augmented data object containing both the original data
 *     and the additional params.
 */
soy.$$augmentData = function(origData, additionalParams) {

  // Create a new object whose '__proto__' field is set to origData.
  /** @constructor */
  function TempCtor() {}
  TempCtor.prototype = origData;
  var newData = new TempCtor();

  // Add the additional params to the new object.
  for (var key in additionalParams) {
    newData[key] = additionalParams[key];
  }

  return newData;
};


/**
 * Gets the keys in a map as an array. There are no guarantees on the order.
 * @param {Object} map The map to get the keys of.
 * @return {Array.<string>} The array of keys in the given map.
 */
soy.$$getMapKeys = function(map) {
  var mapKeys = [];
  for (var key in map) {
    mapKeys.push(key);
  }
  return mapKeys;
};


/**
 * Gets a consistent unique id for the given delegate template name. Two calls
 * to this function will return the same id if and only if the input names are
 * the same.
 *
 * <p> Important: This function must always be called with a string constant.
 *
 * <p> If Closure Compiler is not being used, then this is just this identity
 * function. If Closure Compiler is being used, then each call to this function
 * will be replaced with a short string constant, which will be consistent per
 * input name.
 *
 * @param {string} delTemplateName The delegate template name for which to get a
 *     consistent unique id.
 * @return {string} A unique id that is consistent per input name.
 *
 * @consistentIdGenerator
 */
soy.$$getDelegateId = function(delTemplateName) {
  return delTemplateName;
};


/**
 * Map from registered delegate template id/name to the priority of the
 * implementation.
 * @type {Object}
 * @private
 */
soy.$$DELEGATE_REGISTRY_PRIORITIES_ = {};

/**
 * Map from registered delegate template id/name to the implementation function.
 * @type {Object}
 * @private
 */
soy.$$DELEGATE_REGISTRY_FUNCTIONS_ = {};


/**
 * Registers a delegate implementation. If the same delegate template id/name
 * has been registered previously, then priority values are compared and only
 * the higher priority implementation is stored (if priorities are equal, an
 * error is thrown).
 *
 * @param {string} delTemplateId The delegate template id/name to register.
 * @param {number} delPriority The implementation's priority value.
 * @param {Function} delFn The implementation function.
 */
soy.$$registerDelegateFn = function(delTemplateId, delPriority, delFn) {
  var mapKey = 'key_' + delTemplateId;
  var currPriority = soy.$$DELEGATE_REGISTRY_PRIORITIES_[mapKey];
  if (currPriority === undefined || delPriority > currPriority) {
    // Registering new or higher-priority function: replace registry entry.
    soy.$$DELEGATE_REGISTRY_PRIORITIES_[mapKey] = delPriority;
    soy.$$DELEGATE_REGISTRY_FUNCTIONS_[mapKey] = delFn;
  } else if (delPriority == currPriority) {
    // Registering same-priority function: error.
    throw Error(
        'Encountered two active delegates with same priority (id/name "' +
        delTemplateId + '").');
  } else {
    // Registering lower-priority function: do nothing.
  }
};


/**
 * Retrieves the (highest-priority) implementation that has been registered for
 * a given delegate template id/name. If no implementation has been registered
 * for the id/name, then returns an implementation that is equivalent to an
 * empty template (i.e. rendered output would be empty string).
 *
 * @param {string} delTemplateId The delegate template id/name to get.
 * @return {Function} The retrieved implementation function.
 */
soy.$$getDelegateFn = function(delTemplateId) {
  var delFn = soy.$$DELEGATE_REGISTRY_FUNCTIONS_['key_' + delTemplateId];
  return delFn ? delFn : soy.$$EMPTY_TEMPLATE_FN_;
};


/**
 * Private helper soy.$$getDelegateFn(). This is the empty template function
 * that is returned whenever there's no delegate implementation found.
 *
 * @param {Object.<string, *>=} opt_data
 * @param {soy.StringBuilder=} opt_sb
 * @param {Object.<string, *>=} opt_ijData
 * @return {string}
 * @private
 */
soy.$$EMPTY_TEMPLATE_FN_ = function(opt_data, opt_sb, opt_ijData) {
  return '';
};


// -----------------------------------------------------------------------------
// Escape/filter/normalize.


/**
 * Escapes HTML special characters in a string.  Escapes double quote '"' in
 * addition to '&', '<', and '>' so that a string can be included in an HTML
 * tag attribute value within double quotes.
 * Will emit known safe HTML as-is.
 *
 * @param {*} value The string-like value to be escaped.  May not be a string,
 *     but the value will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeHtml = function(value) {
  if (typeof value === 'object' && value &&
      value.contentKind === soydata.SanitizedContentKind.HTML) {
    return value.content;
  }
  return soy.esc.$$escapeHtmlHelper(value);
};


/**
 * Escapes HTML special characters in a string so that it can be embedded in
 * RCDATA.
 * <p>
 * Escapes HTML special characters so that the value will not prematurely end
 * the body of a tag like {@code <textarea>} or {@code <title>}.  RCDATA tags
 * cannot contain other HTML entities, so it is not strictly necessary to escape
 * HTML special characters except when part of that text looks like an HTML
 * entity or like a close tag : {@code </textarea>}.
 * <p>
 * Will normalize known safe HTML to make sure that sanitized HTML (which could
 * contain an innocuous {@code </textarea>} don't prematurely end an RCDATA
 * element.
 *
 * @param {*} value The string-like value to be escaped.  May not be a string,
 *     but the value will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeHtmlRcdata = function(value) {
  if (typeof value === 'object' && value &&
      value.contentKind === soydata.SanitizedContentKind.HTML) {
    return soy.esc.$$normalizeHtmlHelper(value.content);
  }
  return soy.esc.$$escapeHtmlHelper(value);
};


/**
 * Removes HTML tags from a string of known safe HTML so it can be used as an
 * attribute value.
 *
 * @param {*} value The HTML to be escaped.  May not be a string, but the
 *     value will be coerced to a string.
 * @return {string} A representation of value without tags, HTML comments, or
 *     other content.
 */
soy.$$stripHtmlTags = function(value) {
  return String(value).replace(soy.esc.$$HTML_TAG_REGEX_, '');
};


/**
 * Escapes HTML special characters in an HTML attribute value.
 *
 * @param {*} value The HTML to be escaped.  May not be a string, but the
 *     value will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeHtmlAttribute = function(value) {
  if (typeof value === 'object' && value &&
      value.contentKind === soydata.SanitizedContentKind.HTML) {
    return soy.esc.$$normalizeHtmlHelper(soy.$$stripHtmlTags(value.content));
  }
  return soy.esc.$$escapeHtmlHelper(value);
};


/**
 * Escapes HTML special characters in a string including space and other
 * characters that can end an unquoted HTML attribute value.
 *
 * @param {*} value The HTML to be escaped.  May not be a string, but the
 *     value will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeHtmlAttributeNospace = function(value) {
  if (typeof value === 'object' && value &&
      value.contentKind === soydata.SanitizedContentKind.HTML) {
    return soy.esc.$$normalizeHtmlNospaceHelper(
        soy.$$stripHtmlTags(value.content));
  }
  return soy.esc.$$escapeHtmlNospaceHelper(value);
};


/**
 * Filters out strings that cannot be a substring of a valid HTML attribute.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} A valid HTML attribute name part or name/value pair.
 *     {@code "zSoyz"} if the input is invalid.
 */
soy.$$filterHtmlAttribute = function(value) {
  if (typeof value === 'object' && value &&
      value.contentKind === soydata.SanitizedContentKind.HTML_ATTRIBUTE) {
    return value.content.replace(/=([^"']*)$/, '="$1"');
  }
  return soy.esc.$$filterHtmlAttributeHelper(value);
};


/**
 * Filters out strings that cannot be a substring of a valid HTML element name.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} A valid HTML element name part.
 *     {@code "zSoyz"} if the input is invalid.
 */
soy.$$filterHtmlElementName = function(value) {
  return soy.esc.$$filterHtmlElementNameHelper(value);
};


/**
 * Escapes characters in the value to make it valid content for a JS string
 * literal.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 * @deprecated
 */
soy.$$escapeJs = function(value) {
  return soy.$$escapeJsString(value);
};


/**
 * Escapes characters in the value to make it valid content for a JS string
 * literal.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeJsString = function(value) {
  if (typeof value === 'object' &&
      value.contentKind === soydata.SanitizedContentKind.JS_STR_CHARS) {
    return value.content;
  }
  return soy.esc.$$escapeJsStringHelper(value);
};


/**
 * Encodes a value as a JavaScript literal.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} A JavaScript code representation of the input.
 */
soy.$$escapeJsValue = function(value) {
  // We surround values with spaces so that they can't be interpolated into
  // identifiers by accident.
  // We could use parentheses but those might be interpreted as a function call.
  if (value == null) {  // Intentionally matches undefined.
    // Java returns null from maps where there is no corresponding key while
    // JS returns undefined.
    // We always output null for compatibility with Java which does not have a
    // distinct undefined value.
    return ' null ';
  }
  switch (typeof value) {
    case 'boolean': case 'number':
      return ' ' + value + ' ';
    default:
      return "'" + soy.esc.$$escapeJsStringHelper(String(value)) + "'";
  }
};


/**
 * Escapes characters in the string to make it valid content for a JS regular
 * expression literal.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeJsRegex = function(value) {
  return soy.esc.$$escapeJsRegexHelper(value);
};


/**
 * Matches all URI mark characters that conflict with HTML attribute delimiters
 * or that cannot appear in a CSS uri.
 * From <a href="http://www.w3.org/TR/CSS2/grammar.html">G.2: CSS grammar</a>
 * <pre>
 *     url        ([!#$%&*-~]|{nonascii}|{escape})*
 * </pre>
 *
 * @type {RegExp}
 * @private
 */
soy.$$problematicUriMarks_ = /['()]/g;

/**
 * @param {string} ch A single character in {@link soy.$$problematicUriMarks_}.
 * @return {string}
 * @private
 */
soy.$$pctEncode_ = function(ch) {
  return '%' + ch.charCodeAt(0).toString(16);
};

/**
 * Escapes a string so that it can be safely included in a URI.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeUri = function(value) {
  if (typeof value === 'object' &&
      value.contentKind === soydata.SanitizedContentKind.URI) {
    return soy.$$normalizeUri(value);
  }
  // Apostophes and parentheses are not matched by encodeURIComponent.
  // They are technically special in URIs, but only appear in the obsolete mark
  // production in Appendix D.2 of RFC 3986, so can be encoded without changing
  // semantics.
  var encoded = soy.esc.$$escapeUriHelper(value);
  soy.$$problematicUriMarks_.lastIndex = 0;
  if (soy.$$problematicUriMarks_.test(encoded)) {
    return encoded.replace(soy.$$problematicUriMarks_, soy.$$pctEncode_);
  }
  return encoded;
};


/**
 * Removes rough edges from a URI by escaping any raw HTML/JS string delimiters.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$normalizeUri = function(value) {
  return soy.esc.$$normalizeUriHelper(value);
};


/**
 * Vets a URI's protocol and removes rough edges from a URI by escaping
 * any raw HTML/JS string delimiters.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$filterNormalizeUri = function(value) {
  return soy.esc.$$filterNormalizeUriHelper(value);
};


/**
 * Escapes a string so it can safely be included inside a quoted CSS string.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} An escaped version of value.
 */
soy.$$escapeCssString = function(value) {
  return soy.esc.$$escapeCssStringHelper(value);
};


/**
 * Encodes a value as a CSS identifier part, keyword, or quantity.
 *
 * @param {*} value The value to escape.  May not be a string, but the value
 *     will be coerced to a string.
 * @return {string} A safe CSS identifier part, keyword, or quanitity.
 */
soy.$$filterCssValue = function(value) {
  // Uses == to intentionally match null and undefined for Java compatibility.
  if (value == null) {
    return '';
  }
  return soy.esc.$$filterCssValueHelper(value);
};


// -----------------------------------------------------------------------------
// Basic directives/functions.


/**
 * Converts \r\n, \r, and \n to <br>s
 * @param {*} str The string in which to convert newlines.
 * @return {string} A copy of {@code str} with converted newlines.
 */
soy.$$changeNewlineToBr = function(str) {
  return goog.string.newLineToBr(String(str), false);
};


/**
 * Inserts word breaks ('wbr' tags) into a HTML string at a given interval. The
 * counter is reset if a space is encountered. Word breaks aren't inserted into
 * HTML tags or entities. Entites count towards the character count; HTML tags
 * do not.
 *
 * @param {*} str The HTML string to insert word breaks into. Can be other
 *     types, but the value will be coerced to a string.
 * @param {number} maxCharsBetweenWordBreaks Maximum number of non-space
 *     characters to allow before adding a word break.
 * @return {string} The string including word breaks.
 */
soy.$$insertWordBreaks = function(str, maxCharsBetweenWordBreaks) {
  return goog.format.insertWordBreaks(String(str), maxCharsBetweenWordBreaks);
};


/**
 * Truncates a string to a given max length (if it's currently longer),
 * optionally adding ellipsis at the end.
 *
 * @param {*} str The string to truncate. Can be other types, but the value will
 *     be coerced to a string.
 * @param {number} maxLen The maximum length of the string after truncation
 *     (including ellipsis, if applicable).
 * @param {boolean} doAddEllipsis Whether to add ellipsis if the string needs
 *     truncation.
 * @return {string} The string after truncation.
 */
soy.$$truncate = function(str, maxLen, doAddEllipsis) {

  str = String(str);
  if (str.length <= maxLen) {
    return str;  // no need to truncate
  }

  // If doAddEllipsis, either reduce maxLen to compensate, or else if maxLen is
  // too small, just turn off doAddEllipsis.
  if (doAddEllipsis) {
    if (maxLen > 3) {
      maxLen -= 3;
    } else {
      doAddEllipsis = false;
    }
  }

  // Make sure truncating at maxLen doesn't cut up a unicode surrogate pair.
  if (soy.$$isHighSurrogate_(str.charAt(maxLen - 1)) &&
      soy.$$isLowSurrogate_(str.charAt(maxLen))) {
    maxLen -= 1;
  }

  // Truncate.
  str = str.substring(0, maxLen);

  // Add ellipsis.
  if (doAddEllipsis) {
    str += '...';
  }

  return str;
};

/**
 * Private helper for $$truncate() to check whether a char is a high surrogate.
 * @param {string} ch The char to check.
 * @return {boolean} Whether the given char is a unicode high surrogate.
 * @private
 */
soy.$$isHighSurrogate_ = function(ch) {
  return 0xD800 <= ch && ch <= 0xDBFF;
};

/**
 * Private helper for $$truncate() to check whether a char is a low surrogate.
 * @param {string} ch The char to check.
 * @return {boolean} Whether the given char is a unicode low surrogate.
 * @private
 */
soy.$$isLowSurrogate_ = function(ch) {
  return 0xDC00 <= ch && ch <= 0xDFFF;
};


// -----------------------------------------------------------------------------
// Bidi directives/functions.


/**
 * Cache of bidi formatter by context directionality, so we don't keep on
 * creating new objects.
 * @type {!Object.<!goog.i18n.BidiFormatter>}
 * @private
 */
soy.$$bidiFormatterCache_ = {};


/**
 * Returns cached bidi formatter for bidiGlobalDir, or creates a new one.
 * @param {number} bidiGlobalDir The global directionality context: 1 if ltr, -1
 *     if rtl, 0 if unknown.
 * @return {goog.i18n.BidiFormatter} A formatter for bidiGlobalDir.
 * @private
 */
soy.$$getBidiFormatterInstance_ = function(bidiGlobalDir) {
  return soy.$$bidiFormatterCache_[bidiGlobalDir] ||
         (soy.$$bidiFormatterCache_[bidiGlobalDir] =
             new goog.i18n.BidiFormatter(bidiGlobalDir));
};


/**
 * Estimate the overall directionality of text. If opt_isHtml, makes sure to
 * ignore the LTR nature of the mark-up and escapes in text, making the logic
 * suitable for HTML and HTML-escaped text.
 * @param {string} text The text whose directionality is to be estimated.
 * @param {boolean=} opt_isHtml Whether text is HTML/HTML-escaped.
 *     Default: false.
 * @return {number} 1 if text is LTR, -1 if it is RTL, and 0 if it is neutral.
 */
soy.$$bidiTextDir = function(text, opt_isHtml) {
  if (!text) {
    return 0;
  }
  return goog.i18n.bidi.detectRtlDirectionality(text, opt_isHtml) ? -1 : 1;
};


/**
 * Returns "dir=ltr" or "dir=rtl", depending on text's estimated
 * directionality, if it is not the same as bidiGlobalDir.
 * Otherwise, returns the empty string.
 * If opt_isHtml, makes sure to ignore the LTR nature of the mark-up and escapes
 * in text, making the logic suitable for HTML and HTML-escaped text.
 * @param {number} bidiGlobalDir The global directionality context: 1 if ltr, -1
 *     if rtl, 0 if unknown.
 * @param {string} text The text whose directionality is to be estimated.
 * @param {boolean=} opt_isHtml Whether text is HTML/HTML-escaped.
 *     Default: false.
 * @return {soydata.SanitizedHtmlAttribute} "dir=rtl" for RTL text in non-RTL
 *     context; "dir=ltr" for LTR text in non-LTR context;
 *     else, the empty string.
 */
soy.$$bidiDirAttr = function(bidiGlobalDir, text, opt_isHtml) {
  return new soydata.SanitizedHtmlAttribute(
      soy.$$getBidiFormatterInstance_(bidiGlobalDir).dirAttr(text, opt_isHtml));
};


/**
 * Returns a Unicode BiDi mark matching bidiGlobalDir (LRM or RLM) if the
 * directionality or the exit directionality of text are opposite to
 * bidiGlobalDir. Otherwise returns the empty string.
 * If opt_isHtml, makes sure to ignore the LTR nature of the mark-up and escapes
 * in text, making the logic suitable for HTML and HTML-escaped text.
 * @param {number} bidiGlobalDir The global directionality context: 1 if ltr, -1
 *     if rtl, 0 if unknown.
 * @param {string} text The text whose directionality is to be estimated.
 * @param {boolean=} opt_isHtml Whether text is HTML/HTML-escaped.
 *     Default: false.
 * @return {string} A Unicode bidi mark matching bidiGlobalDir, or the empty
 *     string when text's overall and exit directionalities both match
 *     bidiGlobalDir, or bidiGlobalDir is 0 (unknown).
 */
soy.$$bidiMarkAfter = function(bidiGlobalDir, text, opt_isHtml) {
  var formatter = soy.$$getBidiFormatterInstance_(bidiGlobalDir);
  return formatter.markAfter(text, opt_isHtml);
};


/**
 * Returns str wrapped in a <span dir=ltr|rtl> according to its directionality -
 * but only if that is neither neutral nor the same as the global context.
 * Otherwise, returns str unchanged.
 * Always treats str as HTML/HTML-escaped, i.e. ignores mark-up and escapes when
 * estimating str's directionality.
 * @param {number} bidiGlobalDir The global directionality context: 1 if ltr, -1
 *     if rtl, 0 if unknown.
 * @param {*} str The string to be wrapped. Can be other types, but the value
 *     will be coerced to a string.
 * @return {string} The wrapped string.
 */
soy.$$bidiSpanWrap = function(bidiGlobalDir, str) {
  var formatter = soy.$$getBidiFormatterInstance_(bidiGlobalDir);
  return formatter.spanWrap(str + '', true);
};


/**
 * Returns str wrapped in Unicode BiDi formatting characters according to its
 * directionality, i.e. either LRE or RLE at the beginning and PDF at the end -
 * but only if str's directionality is neither neutral nor the same as the
 * global context. Otherwise, returns str unchanged.
 * Always treats str as HTML/HTML-escaped, i.e. ignores mark-up and escapes when
 * estimating str's directionality.
 * @param {number} bidiGlobalDir The global directionality context: 1 if ltr, -1
 *     if rtl, 0 if unknown.
 * @param {*} str The string to be wrapped. Can be other types, but the value
 *     will be coerced to a string.
 * @return {string} The wrapped string.
 */
soy.$$bidiUnicodeWrap = function(bidiGlobalDir, str) {
  var formatter = soy.$$getBidiFormatterInstance_(bidiGlobalDir);
  return formatter.unicodeWrap(str + '', true);
};


// -----------------------------------------------------------------------------
// Generated code.




// START GENERATED CODE FOR ESCAPERS.

/**
 * @type {function (*) : string}
 */
soy.esc.$$escapeUriHelper = function(v) {
  return encodeURIComponent(String(v));
};

/**
 * Maps charcters to the escaped versions for the named escape directives.
 * @type {Object.<string, string>}
 * @private
 */
soy.esc.$$ESCAPE_MAP_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_ = {
  '\x00': '\x26#0;',
  '\x22': '\x26quot;',
  '\x26': '\x26amp;',
  '\x27': '\x26#39;',
  '\x3c': '\x26lt;',
  '\x3e': '\x26gt;',
  '\x09': '\x26#9;',
  '\x0a': '\x26#10;',
  '\x0b': '\x26#11;',
  '\x0c': '\x26#12;',
  '\x0d': '\x26#13;',
  ' ': '\x26#32;',
  '-': '\x26#45;',
  '\/': '\x26#47;',
  '\x3d': '\x26#61;',
  '`': '\x26#96;',
  '\x85': '\x26#133;',
  '\xa0': '\x26#160;',
  '\u2028': '\x26#8232;',
  '\u2029': '\x26#8233;'
};

/**
 * A function that can be used with String.replace..
 * @param {string} ch A single character matched by a compatible matcher.
 * @return {string} A token in the output language.
 * @private
 */
soy.esc.$$REPLACER_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_ = function(ch) {
  return soy.esc.$$ESCAPE_MAP_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_[ch];
};

/**
 * Maps charcters to the escaped versions for the named escape directives.
 * @type {Object.<string, string>}
 * @private
 */
soy.esc.$$ESCAPE_MAP_FOR_ESCAPE_JS_STRING__AND__ESCAPE_JS_REGEX_ = {
  '\x00': '\\x00',
  '\x08': '\\x08',
  '\x09': '\\t',
  '\x0a': '\\n',
  '\x0b': '\\x0b',
  '\x0c': '\\f',
  '\x0d': '\\r',
  '\x22': '\\x22',
  '\x26': '\\x26',
  '\x27': '\\x27',
  '\/': '\\\/',
  '\x3c': '\\x3c',
  '\x3d': '\\x3d',
  '\x3e': '\\x3e',
  '\\': '\\\\',
  '\x85': '\\x85',
  '\u2028': '\\u2028',
  '\u2029': '\\u2029',
  '$': '\\x24',
  '(': '\\x28',
  ')': '\\x29',
  '*': '\\x2a',
  '+': '\\x2b',
  ',': '\\x2c',
  '-': '\\x2d',
  '.': '\\x2e',
  ':': '\\x3a',
  '?': '\\x3f',
  '[': '\\x5b',
  ']': '\\x5d',
  '^': '\\x5e',
  '{': '\\x7b',
  '|': '\\x7c',
  '}': '\\x7d'
};

/**
 * A function that can be used with String.replace..
 * @param {string} ch A single character matched by a compatible matcher.
 * @return {string} A token in the output language.
 * @private
 */
soy.esc.$$REPLACER_FOR_ESCAPE_JS_STRING__AND__ESCAPE_JS_REGEX_ = function(ch) {
  return soy.esc.$$ESCAPE_MAP_FOR_ESCAPE_JS_STRING__AND__ESCAPE_JS_REGEX_[ch];
};

/**
 * Maps charcters to the escaped versions for the named escape directives.
 * @type {Object.<string, string>}
 * @private
 */
soy.esc.$$ESCAPE_MAP_FOR_ESCAPE_CSS_STRING_ = {
  '\x00': '\\0 ',
  '\x08': '\\8 ',
  '\x09': '\\9 ',
  '\x0a': '\\a ',
  '\x0b': '\\b ',
  '\x0c': '\\c ',
  '\x0d': '\\d ',
  '\x22': '\\22 ',
  '\x26': '\\26 ',
  '\x27': '\\27 ',
  '(': '\\28 ',
  ')': '\\29 ',
  '*': '\\2a ',
  '\/': '\\2f ',
  ':': '\\3a ',
  ';': '\\3b ',
  '\x3c': '\\3c ',
  '\x3d': '\\3d ',
  '\x3e': '\\3e ',
  '@': '\\40 ',
  '\\': '\\5c ',
  '{': '\\7b ',
  '}': '\\7d ',
  '\x85': '\\85 ',
  '\xa0': '\\a0 ',
  '\u2028': '\\2028 ',
  '\u2029': '\\2029 '
};

/**
 * A function that can be used with String.replace..
 * @param {string} ch A single character matched by a compatible matcher.
 * @return {string} A token in the output language.
 * @private
 */
soy.esc.$$REPLACER_FOR_ESCAPE_CSS_STRING_ = function(ch) {
  return soy.esc.$$ESCAPE_MAP_FOR_ESCAPE_CSS_STRING_[ch];
};

/**
 * Maps charcters to the escaped versions for the named escape directives.
 * @type {Object.<string, string>}
 * @private
 */
soy.esc.$$ESCAPE_MAP_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_ = {
  '\x00': '%00',
  '\x01': '%01',
  '\x02': '%02',
  '\x03': '%03',
  '\x04': '%04',
  '\x05': '%05',
  '\x06': '%06',
  '\x07': '%07',
  '\x08': '%08',
  '\x09': '%09',
  '\x0a': '%0A',
  '\x0b': '%0B',
  '\x0c': '%0C',
  '\x0d': '%0D',
  '\x0e': '%0E',
  '\x0f': '%0F',
  '\x10': '%10',
  '\x11': '%11',
  '\x12': '%12',
  '\x13': '%13',
  '\x14': '%14',
  '\x15': '%15',
  '\x16': '%16',
  '\x17': '%17',
  '\x18': '%18',
  '\x19': '%19',
  '\x1a': '%1A',
  '\x1b': '%1B',
  '\x1c': '%1C',
  '\x1d': '%1D',
  '\x1e': '%1E',
  '\x1f': '%1F',
  ' ': '%20',
  '\x22': '%22',
  '\x27': '%27',
  '(': '%28',
  ')': '%29',
  '\x3c': '%3C',
  '\x3e': '%3E',
  '\\': '%5C',
  '{': '%7B',
  '}': '%7D',
  '\x7f': '%7F',
  '\x85': '%C2%85',
  '\xa0': '%C2%A0',
  '\u2028': '%E2%80%A8',
  '\u2029': '%E2%80%A9',
  '\uff01': '%EF%BC%81',
  '\uff03': '%EF%BC%83',
  '\uff04': '%EF%BC%84',
  '\uff06': '%EF%BC%86',
  '\uff07': '%EF%BC%87',
  '\uff08': '%EF%BC%88',
  '\uff09': '%EF%BC%89',
  '\uff0a': '%EF%BC%8A',
  '\uff0b': '%EF%BC%8B',
  '\uff0c': '%EF%BC%8C',
  '\uff0f': '%EF%BC%8F',
  '\uff1a': '%EF%BC%9A',
  '\uff1b': '%EF%BC%9B',
  '\uff1d': '%EF%BC%9D',
  '\uff1f': '%EF%BC%9F',
  '\uff20': '%EF%BC%A0',
  '\uff3b': '%EF%BC%BB',
  '\uff3d': '%EF%BC%BD'
};

/**
 * A function that can be used with String.replace..
 * @param {string} ch A single character matched by a compatible matcher.
 * @return {string} A token in the output language.
 * @private
 */
soy.esc.$$REPLACER_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_ = function(ch) {
  return soy.esc.$$ESCAPE_MAP_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_[ch];
};

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_ESCAPE_HTML_ = /[\x00\x22\x26\x27\x3c\x3e]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_NORMALIZE_HTML_ = /[\x00\x22\x27\x3c\x3e]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_ESCAPE_HTML_NOSPACE_ = /[\x00\x09-\x0d \x22\x26\x27\x2d\/\x3c-\x3e`\x85\xa0\u2028\u2029]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_NORMALIZE_HTML_NOSPACE_ = /[\x00\x09-\x0d \x22\x27\x2d\/\x3c-\x3e`\x85\xa0\u2028\u2029]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_ESCAPE_JS_STRING_ = /[\x00\x08-\x0d\x22\x26\x27\/\x3c-\x3e\\\x85\u2028\u2029]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_ESCAPE_JS_REGEX_ = /[\x00\x08-\x0d\x22\x24\x26-\/\x3a\x3c-\x3f\x5b-\x5e\x7b-\x7d\x85\u2028\u2029]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_ESCAPE_CSS_STRING_ = /[\x00\x08-\x0d\x22\x26-\x2a\/\x3a-\x3e@\\\x7b\x7d\x85\xa0\u2028\u2029]/g;

/**
 * Matches characters that need to be escaped for the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$MATCHER_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_ = /[\x00- \x22\x27-\x29\x3c\x3e\\\x7b\x7d\x7f\x85\xa0\u2028\u2029\uff01\uff03\uff04\uff06-\uff0c\uff0f\uff1a\uff1b\uff1d\uff1f\uff20\uff3b\uff3d]/g;

/**
 * A pattern that vets values produced by the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$FILTER_FOR_FILTER_CSS_VALUE_ = /^(?!-*(?:expression|(?:moz-)?binding))(?:[.#]?-?(?:[_a-z0-9-]+)(?:-[_a-z0-9-]+)*-?|-?(?:[0-9]+(?:\.[0-9]*)?|\.[0-9]+)(?:[a-z]{1,2}|%)?|!important|)$/i;

/**
 * A pattern that vets values produced by the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$FILTER_FOR_FILTER_NORMALIZE_URI_ = /^(?:(?:https?|mailto):|[^&:\/?#]*(?:[\/?#]|$))/i;

/**
 * A pattern that vets values produced by the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$FILTER_FOR_FILTER_HTML_ATTRIBUTE_ = /^(?!style|on|action|archive|background|cite|classid|codebase|data|dsync|href|longdesc|src|usemap)(?:[a-z0-9_$:-]*)$/i;

/**
 * A pattern that vets values produced by the named directives.
 * @type RegExp
 * @private
 */
soy.esc.$$FILTER_FOR_FILTER_HTML_ELEMENT_NAME_ = /^(?!script|style|title|textarea|xmp|no)[a-z0-9_$:-]*$/i;

/**
 * A helper for the Soy directive |escapeHtml
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$escapeHtmlHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_ESCAPE_HTML_,
      soy.esc.$$REPLACER_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_);
};

/**
 * A helper for the Soy directive |normalizeHtml
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$normalizeHtmlHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_NORMALIZE_HTML_,
      soy.esc.$$REPLACER_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_);
};

/**
 * A helper for the Soy directive |escapeHtmlNospace
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$escapeHtmlNospaceHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_ESCAPE_HTML_NOSPACE_,
      soy.esc.$$REPLACER_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_);
};

/**
 * A helper for the Soy directive |normalizeHtmlNospace
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$normalizeHtmlNospaceHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_NORMALIZE_HTML_NOSPACE_,
      soy.esc.$$REPLACER_FOR_ESCAPE_HTML__AND__NORMALIZE_HTML__AND__ESCAPE_HTML_NOSPACE__AND__NORMALIZE_HTML_NOSPACE_);
};

/**
 * A helper for the Soy directive |escapeJsString
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$escapeJsStringHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_ESCAPE_JS_STRING_,
      soy.esc.$$REPLACER_FOR_ESCAPE_JS_STRING__AND__ESCAPE_JS_REGEX_);
};

/**
 * A helper for the Soy directive |escapeJsRegex
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$escapeJsRegexHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_ESCAPE_JS_REGEX_,
      soy.esc.$$REPLACER_FOR_ESCAPE_JS_STRING__AND__ESCAPE_JS_REGEX_);
};

/**
 * A helper for the Soy directive |escapeCssString
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$escapeCssStringHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_ESCAPE_CSS_STRING_,
      soy.esc.$$REPLACER_FOR_ESCAPE_CSS_STRING_);
};

/**
 * A helper for the Soy directive |filterCssValue
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$filterCssValueHelper = function(value) {
  var str = String(value);
  if (!soy.esc.$$FILTER_FOR_FILTER_CSS_VALUE_.test(str)) {
    return 'zSoyz';
  }
  return str;
};

/**
 * A helper for the Soy directive |normalizeUri
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$normalizeUriHelper = function(value) {
  var str = String(value);
  return str.replace(
      soy.esc.$$MATCHER_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_,
      soy.esc.$$REPLACER_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_);
};

/**
 * A helper for the Soy directive |filterNormalizeUri
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$filterNormalizeUriHelper = function(value) {
  var str = String(value);
  if (!soy.esc.$$FILTER_FOR_FILTER_NORMALIZE_URI_.test(str)) {
    return 'zSoyz';
  }
  return str.replace(
      soy.esc.$$MATCHER_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_,
      soy.esc.$$REPLACER_FOR_NORMALIZE_URI__AND__FILTER_NORMALIZE_URI_);
};

/**
 * A helper for the Soy directive |filterHtmlAttribute
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$filterHtmlAttributeHelper = function(value) {
  var str = String(value);
  if (!soy.esc.$$FILTER_FOR_FILTER_HTML_ATTRIBUTE_.test(str)) {
    return 'zSoyz';
  }
  return str;
};

/**
 * A helper for the Soy directive |filterHtmlElementName
 * @param {*} value Can be of any type but will be coerced to a string.
 * @return {string} The escaped text.
 */
soy.esc.$$filterHtmlElementNameHelper = function(value) {
  var str = String(value);
  if (!soy.esc.$$FILTER_FOR_FILTER_HTML_ELEMENT_NAME_.test(str)) {
    return 'zSoyz';
  }
  return str;
};

/**
 * Matches all tags, HTML comments, and DOCTYPEs in tag soup HTML.
 *
 * @type {RegExp}
 * @private
 */
soy.esc.$$HTML_TAG_REGEX_ = /<(?:!|\/?[a-zA-Z])(?:[^>'"]|"[^"]*"|'[^']*')*>/g;

// END GENERATED CODE


/*
---

name: Number

description: Contains Number Prototypes like limit, round, times, and ceil.

license: MIT-style license.

requires: Type

provides: Number

...
*/

Number.implement({

	limit: function(min, max){
		return Math.min(max, Math.max(min, this));
	},

	round: function(precision){
		precision = Math.pow(10, precision || 0).toFixed(precision < 0 ? -precision : 0);
		return Math.round(this * precision) / precision;
	},

	times: function(fn, bind){
		for (var i = 0; i < this; i++) fn.call(bind, i, this);
	},

	toFloat: function(){
		return parseFloat(this);
	},

	toInt: function(base){
		return parseInt(this, base || 10);
	}

});

Number.alias('each', 'times');

(function(math){
	var methods = {};
	math.each(function(name){
		if (!Number[name]) methods[name] = function(){
			return Math[name].apply(null, [this].concat(Array.from(arguments)));
		};
	});
	Number.implement(methods);
})(['abs', 'acos', 'asin', 'atan', 'atan2', 'ceil', 'cos', 'exp', 'floor', 'log', 'max', 'min', 'pow', 'sin', 'sqrt', 'tan']);


/*
---

name: Function

description: Contains Function Prototypes like create, bind, pass, and delay.

license: MIT-style license.

requires: Type

provides: Function

...
*/

Function.extend({

	attempt: function(){
		for (var i = 0, l = arguments.length; i < l; i++){
			try {
				return arguments[i]();
			} catch (e){}
		}
		return null;
	}

});

Function.implement({

	attempt: function(args, bind){
		try {
			return this.apply(bind, Array.from(args));
		} catch (e){}

		return null;
	},

	/*<!ES5-bind>*/
	bind: function(that){
		var self = this,
			args = arguments.length > 1 ? Array.slice(arguments, 1) : null,
			F = function(){};

		var bound = function(){
			var context = that, length = arguments.length;
			if (this instanceof bound){
				F.prototype = self.prototype;
				context = new F;
			}
			var result = (!args && !length)
				? self.call(context)
				: self.apply(context, args && length ? args.concat(Array.slice(arguments)) : args || arguments);
			return context == that ? result : context;
		};
		return bound;
	},
	/*</!ES5-bind>*/

	pass: function(args, bind){
		var self = this;
		if (args != null) args = Array.from(args);
		return function(){
			return self.apply(bind, args || arguments);
		};
	},

	delay: function(delay, bind, args){
		return setTimeout(this.pass((args == null ? [] : args), bind), delay);
	},

	periodical: function(periodical, bind, args){
		return setInterval(this.pass((args == null ? [] : args), bind), periodical);
	}

});

//<1.2compat>
/*
THIS BACKWARDS COMPATABILITY BORKS IN CHROME

delete Function.prototype.bind;

Function.implement({

	create: function(options){
		var self = this;
		options = options || {};
		return function(event){
			var args = options.arguments;
			args = (args != null) ? Array.from(args) : Array.slice(arguments, (options.event) ? 1 : 0);
			if (options.event) args = [event || window.event].extend(args);
			var returns = function(){
				return self.apply(options.bind || null, args);
			};
			if (options.delay) return setTimeout(returns, options.delay);
			if (options.periodical) return setInterval(returns, options.periodical);
			if (options.attempt) return Function.attempt(returns);
			return returns();
		};
	},

	bind: function(bind, args){
		var self = this;
		if (args != null) args = Array.from(args);
		return function(){
			return self.apply(bind, args || arguments);
		};
	},

	bindWithEvent: function(bind, args){
		var self = this;
		if (args != null) args = Array.from(args);
		return function(event){
			return self.apply(bind, (args == null) ? arguments : [event].concat(args));
		};
	},

	run: function(args, bind){
		return this.apply(bind, Array.from(args));
	}

});

if (Object.create == Function.prototype.create) Object.create = null;

var $try = Function.attempt;
*/
//</1.2compat>


/*
---

name: Class

description: Contains the Class Function for easily creating, extending, and implementing reusable Classes.

license: MIT-style license.

requires: [Array, String, Function, Number]

provides: Class

...
*/

(function(){

var Class = this.Class = new Type('Class', function(params){
	if (instanceOf(params, Function)) params = {initialize: params};

	var newClass = function(){
		reset(this);
		if (newClass.$prototyping) return this;
		this.$caller = null;
		var value = (this.initialize) ? this.initialize.apply(this, arguments) : this;
		this.$caller = this.caller = null;
		return value;
	}.extend(this).implement(params);

	newClass.$constructor = Class;
	newClass.prototype.$constructor = newClass;
	newClass.prototype.parent = parent;

	return newClass;
});

var parent = function(){
	if (!this.$caller) throw new Error('The method "parent" cannot be called.');
	var name = this.$caller.$name,
		parent = this.$caller.$owner.parent,
		previous = (parent) ? parent.prototype[name] : null;
	if (!previous) throw new Error('The method "' + name + '" has no parent.');
	return previous.apply(this, arguments);
};

var reset = function(object){
	for (var key in object){
		var value = object[key];
		switch (typeOf(value)){
			case 'object':
				var F = function(){};
				F.prototype = value;
				object[key] = reset(new F);
			break;
			case 'array': object[key] = value.clone(); break;
		}
	}
	return object;
};

var wrap = function(self, key, method){
	if (method.$origin) method = method.$origin;
	var wrapper = function(){
		if (method.$protected && this.$caller == null) throw new Error('The method "' + key + '" cannot be called.');
		var caller = this.caller, current = this.$caller;
		this.caller = current; this.$caller = wrapper;
		var result = method.apply(this, arguments);
		this.$caller = current; this.caller = caller;
		return result;
	}.extend({$owner: self, $origin: method, $name: key});
	return wrapper;
};

var implement = function(key, value, retain){
	if (Class.Mutators.hasOwnProperty(key)){
		value = Class.Mutators[key].call(this, value);
		if (value == null) return this;
	}

	if (typeOf(value) == 'function'){
		if (value.$hidden) return this;
		this.prototype[key] = (retain) ? value : wrap(this, key, value);
	} else {
		Object.merge(this.prototype, key, value);
	}

	return this;
};

var getInstance = function(klass){
	klass.$prototyping = true;
	var proto = new klass;
	delete klass.$prototyping;
	return proto;
};

Class.implement('implement', implement.overloadSetter());

Class.Mutators = {

	Extends: function(parent){
		this.parent = parent;
		this.prototype = getInstance(parent);
	},

	Implements: function(items){
		Array.from(items).each(function(item){
			var instance = new item;
			for (var key in instance) implement.call(this, key, instance[key], true);
		}, this);
	}
};

})();


/*
---

name: Class.Extras

description: Contains Utility Classes that can be implemented into your own Classes to ease the execution of many common tasks.

license: MIT-style license.

requires: Class

provides: [Class.Extras, Chain, Events, Options]

...
*/

(function(){

this.Chain = new Class({

	$chain: [],

	chain: function(){
		this.$chain.append(Array.flatten(arguments));
		return this;
	},

	callChain: function(){
		return (this.$chain.length) ? this.$chain.shift().apply(this, arguments) : false;
	},

	clearChain: function(){
		this.$chain.empty();
		return this;
	}

});

var removeOn = function(string){
	return string.replace(/^on([A-Z])/, function(full, first){
		return first.toLowerCase();
	});
};

this.Events = new Class({

	$events: {},

	addEvent: function(type, fn, internal){
		type = removeOn(type);

		/*<1.2compat>*/
		if (fn == $empty) return this;
		/*</1.2compat>*/

		this.$events[type] = (this.$events[type] || []).include(fn);
		if (internal) fn.internal = true;
		return this;
	},

	addEvents: function(events){
		for (var type in events) this.addEvent(type, events[type]);
		return this;
	},

	fireEvent: function(type, args, delay){
		type = removeOn(type);
		var events = this.$events[type];
		if (!events) return this;
		args = Array.from(args);
		events.each(function(fn){
			if (delay) fn.delay(delay, this, args);
			else fn.apply(this, args);
		}, this);
		return this;
	},

	removeEvent: function(type, fn){
		type = removeOn(type);
		var events = this.$events[type];
		if (events && !fn.internal){
			var index =  events.indexOf(fn);
			if (index != -1) delete events[index];
		}
		return this;
	},

	removeEvents: function(events){
		var type;
		if (typeOf(events) == 'object'){
			for (type in events) this.removeEvent(type, events[type]);
			return this;
		}
		if (events) events = removeOn(events);
		for (type in this.$events){
			if (events && events != type) continue;
			var fns = this.$events[type];
			for (var i = fns.length; i--;) if (i in fns){
				this.removeEvent(type, fns[i]);
			}
		}
		return this;
	}

});

this.Options = new Class({

	setOptions: function(){
		var options = this.options = Object.merge.apply(null, [{}, this.options].append(arguments));
		if (this.addEvent) for (var option in options){
			if (typeOf(options[option]) != 'function' || !(/^on[A-Z]/).test(option)) continue;
			this.addEvent(option, options[option]);
			delete options[option];
		}
		return this;
	}

});

})();


/*
---

script: Object.Extras.js

name: Object.Extras

description: Extra Object generics, like getFromPath which allows a path notation to child elements.

license: MIT-style license

authors:
  - Aaron Newton

requires:
  - Core/Object
  - /MooTools.More

provides: [Object.Extras]

...
*/

(function(){

var defined = function(value){
	return value != null;
};

var hasOwnProperty = Object.prototype.hasOwnProperty;

Object.extend({

	getFromPath: function(source, parts){
		if (typeof parts == 'string') parts = parts.split('.');
		for (var i = 0, l = parts.length; i < l; i++){
			if (hasOwnProperty.call(source, parts[i])) source = source[parts[i]];
			else return null;
		}
		return source;
	},

	cleanValues: function(object, method){
		method = method || defined;
		for (var key in object) if (!method(object[key])){
			delete object[key];
		}
		return object;
	},

	erase: function(object, key){
		if (hasOwnProperty.call(object, key)) delete object[key];
		return object;
	},

	run: function(object){
		var args = Array.slice(arguments, 1);
		for (var key in object) if (object[key].apply){
			object[key].apply(object, args);
		}
		return object;
	}

});

})();


/*
---

script: Locale.js

name: Locale

description: Provides methods for localization.

license: MIT-style license

authors:
  - Aaron Newton
  - Arian Stolwijk

requires:
  - Core/Events
  - /Object.Extras
  - /MooTools.More

provides: [Locale, Lang]

...
*/

(function(){

var current = null,
	locales = {},
	inherits = {};

var getSet = function(set){
	if (instanceOf(set, Locale.Set)) return set;
	else return locales[set];
};

var Locale = this.Locale = {

	define: function(locale, set, key, value){
		var name;
		if (instanceOf(locale, Locale.Set)){
			name = locale.name;
			if (name) locales[name] = locale;
		} else {
			name = locale;
			if (!locales[name]) locales[name] = new Locale.Set(name);
			locale = locales[name];
		}

		if (set) locale.define(set, key, value);

		/*<1.2compat>*/
		if (set == 'cascade') return Locale.inherit(name, key);
		/*</1.2compat>*/

		if (!current) current = locale;

		return locale;
	},

	use: function(locale){
		locale = getSet(locale);

		if (locale){
			current = locale;

			this.fireEvent('change', locale);

			/*<1.2compat>*/
			this.fireEvent('langChange', locale.name);
			/*</1.2compat>*/
		}

		return this;
	},

	getCurrent: function(){
		return current;
	},

	get: function(key, args){
		return (current) ? current.get(key, args) : '';
	},

	inherit: function(locale, inherits, set){
		locale = getSet(locale);

		if (locale) locale.inherit(inherits, set);
		return this;
	},

	list: function(){
		return Object.keys(locales);
	}

};

Object.append(Locale, new Events);

Locale.Set = new Class({

	sets: {},

	inherits: {
		locales: [],
		sets: {}
	},

	initialize: function(name){
		this.name = name || '';
	},

	define: function(set, key, value){
		var defineData = this.sets[set];
		if (!defineData) defineData = {};

		if (key){
			if (typeOf(key) == 'object') defineData = Object.merge(defineData, key);
			else defineData[key] = value;
		}
		this.sets[set] = defineData;

		return this;
	},

	get: function(key, args, _base){
		var value = Object.getFromPath(this.sets, key);
		if (value != null){
			var type = typeOf(value);
			if (type == 'function') value = value.apply(null, Array.from(args));
			else if (type == 'object') value = Object.clone(value);
			return value;
		}

		// get value of inherited locales
		var index = key.indexOf('.'),
			set = index < 0 ? key : key.substr(0, index),
			names = (this.inherits.sets[set] || []).combine(this.inherits.locales).include('en-US');
		if (!_base) _base = [];

		for (var i = 0, l = names.length; i < l; i++){
			if (_base.contains(names[i])) continue;
			_base.include(names[i]);

			var locale = locales[names[i]];
			if (!locale) continue;

			value = locale.get(key, args, _base);
			if (value != null) return value;
		}

		return '';
	},

	inherit: function(names, set){
		names = Array.from(names);

		if (set && !this.inherits.sets[set]) this.inherits.sets[set] = [];

		var l = names.length;
		while (l--) (set ? this.inherits.sets[set] : this.inherits.locales).unshift(names[l]);

		return this;
	}

});

/*<1.2compat>*/
var lang = MooTools.lang = {};

Object.append(lang, Locale, {
	setLanguage: Locale.use,
	getCurrentLanguage: function(){
		var current = Locale.getCurrent();
		return (current) ? current.name : null;
	},
	set: function(){
		Locale.define.apply(this, arguments);
		return this;
	},
	get: function(set, key, args){
		if (key) set += '.' + key;
		return Locale.get(set, args);
	}
});
/*</1.2compat>*/

})();


/*
---

name: Locale.en-US.Number

description: Number messages for US English.

license: MIT-style license

authors:
  - Arian Stolwijk

requires:
  - /Locale

provides: [Locale.en-US.Number]

...
*/

Locale.define('en-US', 'Number', {

	decimal: '.',
	group: ',',

/* 	Commented properties are the defaults for Number.format
	decimals: 0,
	precision: 0,
	scientific: null,

	prefix: null,
	suffic: null,

	// Negative/Currency/percentage will mixin Number
	negative: {
		prefix: '-'
	},*/

	currency: {
//		decimals: 2,
		prefix: '$ '
	}/*,

	percentage: {
		decimals: 2,
		suffix: '%'
	}*/

});




/*
---
name: Number.Format
description: Extends the Number Type object to include a number formatting method.
license: MIT-style license
authors: [Arian Stolwijk]
requires: [Core/Number, Locale.en-US.Number]
# Number.Extras is for compatibility
provides: [Number.Format, Number.Extras]
...
*/


Number.implement({

	format: function(options){
		// Thanks dojo and YUI for some inspiration
		var value = this;
		options = options ? Object.clone(options) : {};
		var getOption = function(key){
			if (options[key] != null) return options[key];
			return Locale.get('Number.' + key);
		};

		var negative = value < 0,
			decimal = getOption('decimal'),
			precision = getOption('precision'),
			group = getOption('group'),
			decimals = getOption('decimals');

		if (negative){
			var negativeLocale = getOption('negative') || {};
			if (negativeLocale.prefix == null && negativeLocale.suffix == null) negativeLocale.prefix = '-';
			['prefix', 'suffix'].each(function(key){
				if (negativeLocale[key]) options[key] = getOption(key) + negativeLocale[key];
			});

			value = -value;
		}

		var prefix = getOption('prefix'),
			suffix = getOption('suffix');

		if (decimals !== '' && decimals >= 0 && decimals <= 20) value = value.toFixed(decimals);
		if (precision >= 1 && precision <= 21) value = (+value).toPrecision(precision);

		value += '';
		var index;
		if (getOption('scientific') === false && value.indexOf('e') > -1){
			var match = value.split('e'),
				zeros = +match[1];
			value = match[0].replace('.', '');

			if (zeros < 0){
				zeros = -zeros - 1;
				index = match[0].indexOf('.');
				if (index > -1) zeros -= index - 1;
				while (zeros--) value = '0' + value;
				value = '0.' + value;
			} else {
				index = match[0].lastIndexOf('.');
				if (index > -1) zeros -= match[0].length - index - 1;
				while (zeros--) value += '0';
			}
		}

		if (decimal != '.') value = value.replace('.', decimal);

		if (group){
			index = value.lastIndexOf(decimal);
			index = (index > -1) ? index : value.length;
			var newOutput = value.substring(index),
				i = index;

			while (i--){
				if ((index - i - 1) % 3 == 0 && i != (index - 1)) newOutput = group + newOutput;
				newOutput = value.charAt(i) + newOutput;
			}

			value = newOutput;
		}

		if (prefix) value = prefix + value;
		if (suffix) value += suffix;

		return value;
	},

	formatCurrency: function(decimals){
		var locale = Locale.get('Number.currency') || {};
		if (locale.scientific == null) locale.scientific = false;
		locale.decimals = decimals != null ? decimals
			: (locale.decimals == null ? 2 : locale.decimals);

		return this.format(locale);
	},

	formatPercentage: function(decimals){
		var locale = Locale.get('Number.percentage') || {};
		if (locale.suffix == null) locale.suffix = '%';
		locale.decimals = decimals != null ? decimals
			: (locale.decimals == null ? 2 : locale.decimals);

		return this.format(locale);
	}

});


/*
---

script: String.Extras.js

name: String.Extras

description: Extends the String native object to include methods useful in managing various kinds of strings (query strings, urls, html, etc).

license: MIT-style license

authors:
  - Aaron Newton
  - Guillermo Rauch
  - Christopher Pitt

requires:
  - Core/String
  - Core/Array
  - MooTools.More

provides: [String.Extras]

...
*/

(function(){



tidy = {
	' ': /[\xa0\u2002\u2003\u2009]/g,
	'*': /[\xb7]/g,
	'\'': /[\u2018\u2019]/g,
	'"': /[\u201c\u201d]/g,
	'-': /[\u2013]/g,
	'&raquo;': /[\uFFFD]/g
};

var walk = function(string, replacements){
	var result = string, key;
	for (key in replacements) result = result.replace(replacements[key], key);
	return result;
};

var getRegexForTag = function(tag, contents){
	tag = tag || '';
	var regstr = contents ? "<" + tag + "(?!\\w)[^>]*>([\\s\\S]*?)<\/" + tag + "(?!\\w)>" : "<\/?" + tag + "([^>]+)?>",
		reg = new RegExp(regstr, "gi");
	return reg;
};

String.implement({

	standardize: function(){
		return this; // return walk(this, special);
	},

	repeat: function(times){
		return new Array(times + 1).join(this);
	},

	pad: function(length, str, direction){
		if (this.length >= length) return this;

		var pad = (str == null ? ' ' : '' + str)
			.repeat(length - this.length)
			.substr(0, length - this.length);

		if (!direction || direction == 'right') return this + pad;
		if (direction == 'left') return pad + this;

		return pad.substr(0, (pad.length / 2).floor()) + this + pad.substr(0, (pad.length / 2).ceil());
	},

	getTags: function(tag, contents){
		return this.match(getRegexForTag(tag, contents)) || [];
	},

	stripTags: function(tag, contents){
		return this.replace(getRegexForTag(tag, contents), '');
	},

	tidy: function(){
		return walk(this, tidy);
	},

	truncate: function(max, trail, atChar){
		var string = this;
		if (trail == null && arguments.length == 1) trail = '.';
		if (string.length > max){
			string = string.substring(0, max);
			if (atChar){
				var index = string.lastIndexOf(atChar);
				if (index != -1) string = string.substr(0, index);
			}
			if (trail) string += trail;
		}
		return string;
	}

});

})();


/*
 ---

 name: jrd.soy.ext

 description: Javascript Implementation of soy extention. Requires Moo Tools

 requires: [Number, Number.Extras, String.Extras]

 provides: [jrd.soy.ext]

 ...
 */

var jrd = jrd || {};
jrd.soy = jrd.soy || {};
jrd.soy.ext = jrd.soy.ext || {};

(function() {
    var ns = jrd.soy.ext;
    ns._is_int = function (value) {
        if ((parseFloat(value) == parseInt(value)) && !isNaN(value)) {
            return true;
        } else {
            return false;
        }
    }

    ns.number = function(number, format) {
        try {
            if(format) {
                // parse the format
                try{
                    var split_number = format.split(".")
                    var decimals = 0;
                    var decimal = "."
                    var group = ","
                    var suffix=""

                    if(split_number.length > 1) {
                        decimals = split_number[1].replace(/[^0-9]/i, "").length
                        if(decimals < split_number[1].length){
                            suffix = split_number[1].substr(decimals, 1)
                        }
                    }
                    return Number.from(number).format({
                        decimal: decimal,
                        group: group,
                        decimals: decimals,
                        suffix: suffix
                    });
                } catch(e) {alert(e)}

            } else if (ns._is_int(number)) {

                return Number.from(number).format({
                    decimal: ".",
                    group: ",",
                    decimals: 0
                });
            } else {
                return Number.from(number).format({
                    decimal: ".",
                    group: ",",
                    decimals: 2
                });
            }
        } catch(e) {

        }
        return number

    }

    ns._def_or = "none"
    ns.set_or = function(d) {
        ns._def_or = d
    }

    ns._had_or = false
    ns.had_or = function() {
        return ns._had_or
    }
    ns.or = function(el, d) {
        var def = d
        if (typeof(def) == 'undefined') {
            def = ns._def_or
        }

        try {
            var to_test = el
            if ('function' == typeof(el)) {
                try {
                    to_test = el()
                } catch(e) {
                    to_test = null
                }
            }
            if (null == to_test || typeof(to_test) == 'undefined') {
                ns._had_or = false
                return def
            }
            ns._had_or = true
            return to_test
        } catch(e) {
            return ns._had_or = false
            return def
        }
    }

    ns.test = function(el) {      
      if ('function' == typeof(el)) {
          var result = null
           try {
                    result = el()
                } catch(e) {
                    result = null
                }
            return  (result && result.length !== 0) ? true : false
        }
        return (el && el.length !== 0) ? true : false
    }

     ns.call = function(fun) {
      if ('function' == typeof(fun)) {
         var result = null
           try {
                    result = fun()
                } catch(e) {
                    result = null
                }
            return  result
        }
        return fun
    }




    ns.randomize = function(input) {
        if(ns._is_int(input)) {
           return  Math.floor(Math.random() * 21)
        }
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz'.split('');


    
        var str = '';
        for (var i = 0; i < input.length; i++) {
            str += chars[Math.floor(Math.random() * chars.length)];
        }
        return str;
    }

    ns.humanize = function(input, from) {
        function size_format (filesize) {
            if (filesize >= 1073741824) {
                 filesize = ns.number(filesize / 1073741824) + ' Gb';
            } else {
                if (filesize >= 1048576) {
                    filesize = ns.number(filesize / 1048576) + ' Mb';
            } else {
                    if (filesize >= 1024) {
                    filesize = ns.number(filesize / 1024) + ' Kb';
                } else {
                    filesize = ns.number(filesize) + ' bytes';
                    }
                }
            }
        return filesize;
        }

        function time_ago(input) {
           var now = parseInt(new Date().getTime() / 1000);
           var period = (now - input) ;

          // alert(now + " - " + input + " = " + (now - input));
           var days = Math.floor(period / (60 * 60 * 24));
           var hours = Math.floor((period % (60 * 60 * 24)) / (60 * 60));
           var minutes = Math.floor((period % (60 * 60)) / (60));
           var seconds = Math.floor(period % 60);



           if (period < 60 * 60) {
               return minutes + " min. " + seconds + " sec. ago"
           } else if (period < 60 * 60 * 24 ) {
                return hours + " hr. " + minutes + " min. ago"
           } else if  (period < 60 * 60 * 24 * 10) {
                return days + " days " + hours + " hr. ago"
           } else {
               return "unknown time ago"
           }

        }


        if(from == "bytes") {
            return size_format(input)
        } else if(from == "unixtime") {
           return time_ago(input)
        }

        return input
    }

     ns.reverse = function(input) {
         var result = input.clone();
           try {
                    result.reverse()
                } catch(e) {
                    result = null
                }
            return  result
    }

     ns.lfill = function(input, length) {
       return  (""+input).pad(length, " ", "left");
    }

     ns.rfill = function(input, length) {
       return  (""+input).pad(length, " ", "right");
    }
}());

/*
 ---

 name: upoopu.soy.ext

 description: Javascript Implementation of soy extention. Requires Moo Tools

 requires: []

 provides: [upoopu.soy.ext]

 ...
 */

var upoopu = upoopu || {};
upoopu.soy = upoopu.soy || {};
upoopu.soy.ext = upoopu.soy.ext || {};

(function() {
    var ns = upoopu.soy.ext;
    ns.uposcience = function (hash, total, land, science) {
        var prov = upoopu.intel.utopia.datastore.get(hash)
        multiplier = 1

         if(science.$enum$science == UpoOpu.pb.ScienceType.CHANNELING && (prov && prov.$pers && prov.$pers() == UpoOpu.pb.PersonalityType.MYSTIC)) {
            multiplier = 1.75
         } else if(science.$enum$science == UpoOpu.pb.ScienceType.THIEVERY && (prov && prov.$pers && prov.$pers() == UpoOpu.pb.PersonalityType.ROGUE)) {
            multiplier = 2 
         }

         
         
         

        return prov.calculate_science_effects(total, land, science.factor || science, multiplier)
    }

    ns.upobuilding= function (hash, build_data, effect, be) {
        var prov = upoopu.intel.utopia.datastore.get(hash)
        var building_stats = null

        for(var idx=0; idx < build_data.build_stats.length; idx++) {
            if(build_data.build_stats[idx].effect == effect) {
                building_stats = build_data.build_stats[idx]
                break
            }
        }

        if(!building_stats) {
            return
        }

        var data_misc = {
            is_flatrate: (building_stats.abs && !building_stats.no_be) ? true : false,
            is_capacity: (building_stats.abs && building_stats.no_be) ? true : false,
            ignore_be: building_stats.no_be,
            custom_be: be,
            cap: building_stats.cap,
            building: build_data.$enum$building
          }



        return prov.calculate_build_effects(100 * build_data.quantity / build_data.percentage, build_data.quantity, building_stats.pct || building_stats.abs, data_misc)
    }


}());

// This file was automatically generated from soy.common.intel.soy.
// Please don't edit this file by hand.

if (typeof upoopu == 'undefined') { var upoopu = {}; }
if (typeof upoopu.common == 'undefined') { upoopu.common = {}; }
if (typeof upoopu.common.templates == 'undefined') { upoopu.common.templates = {}; }
if (typeof upoopu.common.templates.intel == 'undefined') { upoopu.common.templates.intel = {}; }


upoopu.common.templates.intel.message = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div class="intel message ', (opt_data.good) ? 'good' : 'bad', '">', soy.$$escapeHtml(opt_data.message), '</div>');
  return opt_sb ? '' : output.toString();
};


upoopu.common.templates.intel.overview = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div class="intel overview"><h1>The province of ', soy.$$escapeHtml(opt_data.data.province), ' (', soy.$$escapeHtml(opt_data.data.kd), ':', soy.$$escapeHtml(opt_data.data.isl), ')</h1><div class="tablewrap"><table class="overview"><tr><th>Accuracy</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.mil_accuracy)), '%</td><th>Age</th><td><small>', (opt_data.data.has_sot) ? 'SoT: ' + jrd.soy.ext.number(soy.$$escapeHtml(opt_data.data.has_sot)) + ' hrs' : 'No SoT', ', ', (opt_data.data.has_som) ? 'SoM: ' + jrd.soy.ext.number(soy.$$escapeHtml(opt_data.data.has_som)) + ' hrs' : 'No SoM', '</small></td></tr><tr><th>Race</th><td>', jrd.soy.ext.or(opt_data.data.race), ' <small>', jrd.soy.ext.or(opt_data.data.personality), '</small></td><th>Soldiers</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.soldiers)), '</td></tr><tr><th>Honor</th><td>', jrd.soy.ext.or(opt_data.data.honor), '</td><th style="color:#bb131e">', soy.$$escapeHtml(opt_data.data.ospec.name), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.ospecs)), '</td></tr><tr><th style="color:#9ab1a9">Land</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.land)), ' ', (jrd.soy.ext.had_or()) ? '<small>acres</small>' : '', '</td><th style="color:#598527">', soy.$$escapeHtml(opt_data.data.dspec.name), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.dspecs)), '</td></tr><tr><th style="color:#9ab1a9">Peasants</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.peasants)), '</td><th style="color:#ffd700">', soy.$$escapeHtml(opt_data.data.elite.name), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.elites)), '</td></tr><tr><th>BE</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.building_eff)), '%</td><th>Thieves</th><td>', (opt_data.data.thieves || opt_data.data.thieves_pct) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.thieves)) + ' <small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.thieves_pct)) + '%</small>  ' + ((jrd.soy.ext.call(opt_data.data.mtpa_accuracy) == 2) ? '<small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.mtpa)) + ' <strong>m</strong>t/a</small>' : '') : '', (jrd.soy.ext.test(opt_data.data.has_infiltrate) && ! opt_data.data.thieves) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.infiltrate_thieves)) + '  ' + ((jrd.soy.ext.call(opt_data.data.mtpa_accuracy) == 2) ? '<small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.mtpa)) + ' <strong>m</strong>t/a</small>' : '') : '', '</td></tr><tr><th style="color:#ffd700">Money</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.gold)), (jrd.soy.ext.had_or()) ? '<small>gc</small>' : '', '</td><th>Wizards</th><td>', (opt_data.data.wizards) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.wizards)) + ' <small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.wizards_pct)) + '%</small>  ' + ((jrd.soy.ext.call(opt_data.data.mwpa_accuracy) == 2) ? '<small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.mwpa)) + ' <strong>m</strong>w/a</small>' : '') : '', (jrd.soy.ext.test(opt_data.data.has_estimated_wizzards) && ! opt_data.data.wizards) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.estimated_wizzards)) + '  ' + ((jrd.soy.ext.call(opt_data.data.mwpa_accuracy) == 2) ? '<small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.mwpa)) + ' <strong>m</strong>w/a</small>' : '') : '', '</td></tr><tr><th style="color:#598527">Food</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.food)), ' ', (jrd.soy.ext.had_or()) ? '<small>bshls</small>' : '', '</td><th>Horses</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.horses)), '</td></tr><tr><th style="color:#6495ed">Runes</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.runes)), ' ', (jrd.soy.ext.had_or()) ? '<small>runes</small>' : '', '</td><th>Pris.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.prisoners)), '</td></tr><tr><th>TB</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.trade_balance)), ' ', (jrd.soy.ext.had_or()) ? '<small>gc</small>' : '', '</td><th style="color:#bb131e">Total Off.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.total_off)), ' <small>', (jrd.soy.ext.test(opt_data.data.land)) ? '(' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.total_off_acre)) + ' p/a)' : '', '</small></td></tr><tr><th style="color:#ffd700">Nw.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.nw)), ' ', (jrd.soy.ext.had_or()) ? '<small>gc</small>' : '', '</td><th style="color:#598527">Total Def.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.total_def)), ' <small>', (jrd.soy.ext.test(opt_data.data.land)) ? '(' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.total_def_acre)) + ' p/a)' : '', '</small></td></tr><tr><th>GB Rating</th><td>', jrd.soy.ext.number(soy.$$escapeHtml(opt_data.data.gbp)), '%</td><th>Off. ME</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.off_me)), '%</td></tr><tr><th>Books</th><td>', (jrd.soy.ext.test(opt_data.data.has_sos)) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.sos_books)) : 'Unknown', '</td><th>Deff. ME</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.def_me)), '%</td></tr></table>', (opt_data.data.has_plague) ? '<div>The Plague has spread through ' + soy.$$escapeHtml(opt_data.data.province) + ' (' + soy.$$escapeHtml(opt_data.data.kd) + ':' + soy.$$escapeHtml(opt_data.data.isl) + ')</div>' : '', '<table class="extended"><thead><tr><th colspan=2>Miscellaneous</th></tr></thead><tr><th>Defense 0 elites</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.def_elites_out)), ' ', (jrd.soy.ext.test(opt_data.data.land)) ? '(' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.def_elites_out_acre)) + ' p/a)' : '', '</td></tr>', (jrd.soy.ext.test(opt_data.data.army_out)) ? '<tr><th>Army out</th><td>Army out for ' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.army_out)) + ' more hours</td></tr><tr><th>Current Offense</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.offense)) + '</td></tr><tr><th>Current Defense</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.defense)) + '</td></tr>' + ((jrd.soy.ext.test(opt_data.data.perfect_defense)) ? '<tr><th>Trusted Defense</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.perfect_defense)) + '</td></tr>' : '') : '', '<tr><th>Dragons</th><td>', jrd.soy.ext.or(opt_data.data.dragon_string), '</td></tr><tr><th>Estimated thieves and wizards</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.estimated_thieves_and_wizzards)), ' ', (jrd.soy.ext.test(opt_data.data.land) && jrd.soy.ext.had_or()) ? '(' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.estimated_thieves_and_wizzards_acre)) + ' p/a)' : '', '</td></tr><tr><th>Calculated Networth</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.data.calculated_networth)), '</td></tr></table></div></div><div class="intel charts"><h1>Charts</h1><div class="tablewrap"><table class="charts"><tr><th>Land and NW/150</th><th>Resources</th></tr><tr><td><div id="charts_land"></div></td><td><div id="charts_resources"></div></td></tr><tr><th>Peasants and troops</th><th>Military</th></tr><tr><td><div id="charts_troops"></div></td><td><div id="charts_military"></div></td></tr></table></div></div>');
  if (jrd.soy.ext.test(opt_data.data.latest_attacks_made) || jrd.soy.ext.test(opt_data.data.latest_attacks_suffered)) {
    output.append('<div class="intel attacks"><h1>Attacks made and suffered</h1><div class="tablewrap"><table class="attacks"><tr><th>Attacker</th><th>Attack</th><th>Target</th><th>Age</th><th>Land</th><th>Out</th></tr>');
    var attackList317 = jrd.soy.ext.reverse(opt_data.data.latest_attacks_made);
    var attackListLen317 = attackList317.length;
    for (var attackIndex317 = 0; attackIndex317 < attackListLen317; attackIndex317++) {
      var attackData317 = attackList317[attackIndex317];
      output.append('<tr><td>', jrd.soy.ext.or(attackData317.attacker), '</td><td class="small">', jrd.soy.ext.or(attackData317.attack), '</td><td class="small">', jrd.soy.ext.or(attackData317.target), '</td><td class="small">', jrd.soy.ext.or(attackData317.age, '-'), '</td><td class="small">', jrd.soy.ext.number(soy.$$escapeHtml(attackData317.land)), '</td><td class="small">', jrd.soy.ext.or(jrd.soy.ext.number(attackData317.army_out), ''), '</td></tr>');
    }
    var attackList339 = jrd.soy.ext.reverse(opt_data.data.latest_attacks_suffered);
    var attackListLen339 = attackList339.length;
    for (var attackIndex339 = 0; attackIndex339 < attackListLen339; attackIndex339++) {
      var attackData339 = attackList339[attackIndex339];
      output.append('<tr class="altdata"><td>', jrd.soy.ext.or(attackData339.attacker), '</td><td class="small">', jrd.soy.ext.or(attackData339.attack), '</td><td class="small">', jrd.soy.ext.or(attackData339.target), '</td><td class="small">', jrd.soy.ext.or(attackData339.age, '-'), '</td><td class="small">', jrd.soy.ext.number(soy.$$escapeHtml(attackData339.land)), '</td><td class="small">', jrd.soy.ext.or(jrd.soy.ext.number(attackData339.army_out), ''), '</td></tr>');
    }
    output.append('</table></div></div>');
  }
  if (jrd.soy.ext.test(opt_data.data.sots)) {
    output.append('<div class="intel carousel"><div id="intel_all_sots" class="intel_all">');
    var sotList365 = jrd.soy.ext.reverse(opt_data.data.sots);
    var sotListLen365 = sotList365.length;
    if (sotListLen365 > 0) {
      for (var sotIndex365 = 0; sotIndex365 < sotListLen365; sotIndex365++) {
        var sotData365 = sotList365[sotIndex365];
        output.append('<div class="wrap">');
        upoopu.common.templates.intel.sot({pb: sotData365}, output);
        output.append('</div>');
      }
    } else {
    }
    output.append('</div><div id="intel_all_sots_pager" class="pager"></div></div>');
  }
  if (jrd.soy.ext.test(opt_data.data.soms)) {
    output.append('<div class="intel carousel"><div id="intel_all_soms" class="intel_all">');
    var somList376 = jrd.soy.ext.reverse(opt_data.data.soms);
    var somListLen376 = somList376.length;
    if (somListLen376 > 0) {
      for (var somIndex376 = 0; somIndex376 < somListLen376; somIndex376++) {
        var somData376 = somList376[somIndex376];
        output.append('<div class="wrap">');
        upoopu.common.templates.intel.som({pb: somData376}, output);
        output.append('</div>');
      }
    } else {
    }
    output.append('</div><div id="intel_all_soms_pager" class="pager"></div></div>');
  }
  if (jrd.soy.ext.test(opt_data.data.surveys)) {
    output.append('<div class="intel carousel"><div id="intel_all_surveys" class="intel_all">');
    var surveyList387 = jrd.soy.ext.reverse(opt_data.data.surveys);
    var surveyListLen387 = surveyList387.length;
    if (surveyListLen387 > 0) {
      for (var surveyIndex387 = 0; surveyIndex387 < surveyListLen387; surveyIndex387++) {
        var surveyData387 = surveyList387[surveyIndex387];
        output.append('<div class="wrap">');
        upoopu.common.templates.intel.survey({pb: surveyData387, hash: opt_data.data.hash}, output);
        output.append('</div>');
      }
    } else {
    }
    output.append('</div><div id="intel_all_surveys_pager" class="pager"></div></div>');
  }
  if (jrd.soy.ext.test(opt_data.data.science)) {
    output.append('<div class="intel carousel"><div id="intel_all_sos" class="intel_all">');
    var sosList399 = jrd.soy.ext.reverse(opt_data.data.science);
    var sosListLen399 = sosList399.length;
    if (sosListLen399 > 0) {
      for (var sosIndex399 = 0; sosIndex399 < sosListLen399; sosIndex399++) {
        var sosData399 = sosList399[sosIndex399];
        output.append('<div class="wrap">');
        upoopu.common.templates.intel.sos({pb: sosData399, hash: opt_data.data.hash}, output);
        output.append('</div>');
      }
    } else {
    }
    output.append('</div><div id="intel_all_sos_pager" class="pager"></div></div>');
  }
  if (jrd.soy.ext.test(opt_data.data.infiltrates)) {
    output.append('<div class="intel infiltrates"><h1>Infiltrates on ', soy.$$escapeHtml(opt_data.data.province), ' (', soy.$$escapeHtml(opt_data.data.kd), ':', soy.$$escapeHtml(opt_data.data.isl), ')</h1><div class="tablewrap"><table class="infiltrates"><tr><th>Thieves</th><th>Accuracy</th><th></th></tr>');
    var infList417 = jrd.soy.ext.reverse(opt_data.data.infiltrates);
    var infListLen417 = infList417.length;
    for (var infIndex417 = 0; infIndex417 < infListLen417; infIndex417++) {
      var infData417 = infList417[infIndex417];
      output.append('<tr><td>', jrd.soy.ext.number(jrd.soy.ext.or(infData417.thieves)), '</td><td>', jrd.soy.ext.number(jrd.soy.ext.or(infData417.accuracy)), '%</td><td class="small">', jrd.soy.ext.humanize(infData417.unix_time, 'unixtime'), ' by ', jrd.soy.ext.or(infData417.poster, ''), '</td></tr>');
    }
    output.append('</table></div></div>');
  }
  if (jrd.soy.ext.test(opt_data.data.military_log)) {
    output.append('<div class="intel log"><h1>Calculations on ', soy.$$escapeHtml(opt_data.data.province), ' (', soy.$$escapeHtml(opt_data.data.kd), ':', soy.$$escapeHtml(opt_data.data.isl), ')</h1><div class="log small">');
    var lineList444 = opt_data.data.military_log;
    var lineListLen444 = lineList444.length;
    for (var lineIndex444 = 0; lineIndex444 < lineListLen444; lineIndex444++) {
      var lineData444 = lineList444[lineIndex444];
      output.append(soy.$$escapeHtml(lineData444), '<br>');
    }
    output.append('</div></div>');
  }
  return opt_sb ? '' : output.toString();
};


upoopu.common.templates.intel.sot = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div class="intel sot"><h1>The province of ', soy.$$escapeHtml(opt_data.pb.province), ' (', soy.$$escapeHtml(opt_data.pb.kd), ':', soy.$$escapeHtml(opt_data.pb.isl), ')</h1><div class="tablewrap"><table class="throne">', (opt_data.pb.poster) ? '<tr class="small fullrow"><td colspan=4 >' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.accuracy)) + '% accurate. Posted ' + jrd.soy.ext.humanize(opt_data.pb.unix_time, 'unixtime') + ' by ' + soy.$$escapeHtml(opt_data.pb.poster) + '</td></tr>' : '', '<tr><th>Race</th><td>', soy.$$escapeHtml(opt_data.pb.race), ' <small>', soy.$$escapeHtml(opt_data.pb.personality), '</small></td><th>Soldiers</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.soldiers)), '</td></tr><tr><th>Honor</th><td>', soy.$$escapeHtml(opt_data.pb.honor), '</td><th>', soy.$$escapeHtml(opt_data.pb.ospec.name), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.ospecs)), '</td></tr><tr><th>Land</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.land)), ' ', (jrd.soy.ext.had_or()) ? '<small>acres</small>' : '', '</td><th>', soy.$$escapeHtml(opt_data.pb.dspec.name), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.dspecs)), '</td></tr><tr><th>Peasants</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.peasants)), '</td><th>', soy.$$escapeHtml(opt_data.pb.elite.name), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.elites)), '</td></tr><tr><th>BE</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.building_eff)), '%</td><th>Thieves</th><td>', (opt_data.pb.thieves) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.thieves)) + ' <small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.wizards_pct)) + '%</small>' : '', '</td></tr><tr><th>Money</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.gold)), ' ', (jrd.soy.ext.had_or()) ? '<small>gc</small>' : '', '</td><th>Wizards</th><td>', (opt_data.pb.wizards) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.wizards)) + ' <small>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.thieves_pct)) + '%</small>' : '', '</td></tr><tr><th>Food</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.food)), ' ', (jrd.soy.ext.had_or()) ? '<small>bshls</small>' : '', '</td><th>Horses</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.horses)), '</td></tr><tr><th>Runes</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.runes)), ' ', (jrd.soy.ext.had_or()) ? '<small>runes</small>' : '', '</td><th>Pris.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.prisoners)), '</td></tr><tr><th>TB</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.trade_balance)), ' ', (jrd.soy.ext.had_or()) ? '<small>gc</small>' : '', '</td><th>Off.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.offense)), '</td></tr><tr><th>Nw.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.nw)), ' ', (jrd.soy.ext.had_or()) ? '<small>gc</small>' : '', '</td><th>Def.</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.defense)), '</td></tr></table>', (opt_data.pb.has_plague) ? '<div><strong>The plague</strong> has spread through ' + soy.$$escapeHtml(opt_data.pb.province) + ' (' + soy.$$escapeHtml(opt_data.pb.kd) + ':' + soy.$$escapeHtml(opt_data.pb.isl) + ')</div>' : '', (opt_data.pb['$enum$string$dragon'] && opt_data.pb['$enum$string$dragon'] != 'nodragon') ? '<div>A <strong>' + soy.$$escapeHtml(opt_data.pb.dragon) + ' Dragon</strong> ravages the lands of ' + soy.$$escapeHtml(opt_data.pb.province) + ' (' + soy.$$escapeHtml(opt_data.pb.kd) + ':' + soy.$$escapeHtml(opt_data.pb.isl) + ')</div>' : '', '</div></div>');
  return opt_sb ? '' : output.toString();
};


upoopu.common.templates.intel.som = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div class="intel som"><h1>Army of ', soy.$$escapeHtml(opt_data.pb.province), ' (', soy.$$escapeHtml(opt_data.pb.kd), ':', soy.$$escapeHtml(opt_data.pb.isl), ')</h1><div class="tablewrap"><table class="som">', (opt_data.pb.poster) ? '<tr class="small fullrow"><td colspan=4 >' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.accuracy)) + '% accurate. Posted ' + jrd.soy.ext.humanize(opt_data.pb.unix_time, 'unixtime') + ' by ' + soy.$$escapeHtml(opt_data.pb.poster) + '</td></tr>' : '', '<tr><th>Offense</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.offense)), ' <small>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.off_me)), '% eff.</small></td><th>Non peasant</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.military_pct)), '%</td></tr><tr><th>Defense</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.defense)), ' <small>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.def_me)), '% eff.</small></td><th>Wages</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.wages)), '%</td></tr><tr><th></th><td></td><th>ME</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.military_efficiency)), '%</td></tr></table>', (opt_data.pb.army_home) ? '<table class="som army"><thead><tr><th colspan=4>Army Home</th></tr></thead><tr><th>Generals</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_home.generals)) + ' generals</td><th>' + soy.$$escapeHtml(opt_data.pb.ospec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_home.ospecs)) + '</td></tr><tr><th>Soldiers</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_home.soldiers)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.dspec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_home.dspecs)) + '</td></tr><tr><th>Horses</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_home.horses)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.elite.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_home.elites)) + '</td></tr></table>' : '', (opt_data.pb.army_out_1) ? '<table class="som army"><thead><tr><th colspan=4>Army 1</th></tr></thead><tr><th>Generals</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.generals)) + ' generals</td><th>' + soy.$$escapeHtml(opt_data.pb.ospec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.ospecs)) + '</td></tr><tr><th>Soldiers</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.soldiers)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.dspec.name) + '</th><td>0</td></tr><tr><th>Horses</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.horses)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.elite.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.elites)) + '</td></tr><tr class="altdata"><th>Away</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.away)) + ' hours</td><th>Land</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_1.land)) + '</td></tr></table>' : '', (opt_data.pb.army_out_2) ? '<table class="som army"><thead><tr><th colspan=4>Army 2</th></tr></thead><tr><th>Generals</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.generals)) + ' generals</td><th>' + soy.$$escapeHtml(opt_data.pb.ospec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.ospecs)) + '</td></tr><tr><th>Soldiers</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.soldiers)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.dspec.name) + '</th><td>0</td></tr><tr><th>Horses</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.horses)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.elite.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.elites)) + '</td></tr><tr  class="altdata"><th>Away</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.away)) + ' hours</td><th>Land</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_2.land)) + '</td></tr></table>' : '', (opt_data.pb.army_out_3) ? '<table class="som army"><thead><tr><th colspan=4>Army 3</th></tr></thead><tr><th>Generals</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.generals)) + ' generals</td><th>' + soy.$$escapeHtml(opt_data.pb.ospec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.ospecs)) + '</td></tr><tr><th>Soldiers</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.soldiers)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.dspec.name) + '</th><td>0</td></tr><tr><th>Horses</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.horses)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.elite.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.elites)) + '</td></tr><tr  class="altdata"><th>Away</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.away)) + ' hours</td><th>Land</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_3.land)) + '</td></tr></table>' : '', (opt_data.pb.army_out_4) ? '<table class="som army"><thead><tr><th colspan=4>Army 4</th></tr></thead><tr><th>Generals</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.generals)) + ' generals</td><th>' + soy.$$escapeHtml(opt_data.pb.ospec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.ospecs)) + '</td></tr><tr><th>Soldiers</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.soldiers)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.dspec.name) + '</th><td>0</td></tr><tr><th>Horses</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.horses)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.elite.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.elites)) + '</td></tr><tr  class="altdata"><th>Away</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.away)) + ' hours</td><th>Land</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_4.land)) + '</td></tr></table>' : '', (opt_data.pb.army_out_5) ? '<table class="som army"><thead><tr><th colspan=4>Army 5</th></tr></thead><tr><th>Generals</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.generals)) + ' generals</td><th>' + soy.$$escapeHtml(opt_data.pb.ospec.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.ospecs)) + '</td></tr><tr><th>Soldiers</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.soldiers)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.dspec.name) + '</th><td>0</td></tr><tr><th>Horses</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.horses)) + '</td><th>' + soy.$$escapeHtml(opt_data.pb.elite.name) + '</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.elites)) + '</td></tr><tr  class="altdata"><th>Away</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.away)) + ' hours</td><th>Land</th><td>' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.army_out_5.land)) + '</td></tr></table>' : '');
  if (jrd.soy.ext.test(opt_data.pb.training_ospecs) || jrd.soy.ext.test(opt_data.pb.training_dspecs) || jrd.soy.ext.test(opt_data.pb.training_elites) || jrd.soy.ext.test(opt_data.pb.training_thieves)) {
    output.append('<table class="som training"><thead><tr><th colspan=4>Training</th></tr></thead>');
    if (jrd.soy.ext.test(opt_data.pb.training_ospecs)) {
      output.append('<tr><th>', soy.$$escapeHtml(opt_data.pb.ospec.name), '</th><td class="small">');
      var troopList888 = opt_data.pb.training_ospecs;
      var troopListLen888 = troopList888.length;
      for (var troopIndex888 = 0; troopIndex888 < troopListLen888; troopIndex888++) {
        var troopData888 = troopList888[troopIndex888];
        output.append(soy.$$escapeHtml(troopData888), ' ');
      }
      output.append('</td></tr>');
    }
    if (jrd.soy.ext.test(opt_data.pb.training_dspecs)) {
      output.append('<tr><th>', soy.$$escapeHtml(opt_data.pb.dspec.name), '</th><td  class="small">');
      var troopList898 = opt_data.pb.training_dspecs;
      var troopListLen898 = troopList898.length;
      for (var troopIndex898 = 0; troopIndex898 < troopListLen898; troopIndex898++) {
        var troopData898 = troopList898[troopIndex898];
        output.append(soy.$$escapeHtml(troopData898), ' ');
      }
      output.append('</td></tr>');
    }
    if (jrd.soy.ext.test(opt_data.pb.training_elites)) {
      output.append('<tr><th>', soy.$$escapeHtml(opt_data.pb.elite.name), '</th><td  class="small">');
      var troopList908 = opt_data.pb.training_elites;
      var troopListLen908 = troopList908.length;
      for (var troopIndex908 = 0; troopIndex908 < troopListLen908; troopIndex908++) {
        var troopData908 = troopList908[troopIndex908];
        output.append(soy.$$escapeHtml(troopData908), ' ');
      }
      output.append('</td></tr>');
    }
    if (jrd.soy.ext.test(opt_data.pb.training_thieves)) {
      output.append('<tr><th>Thieves</th><td  class="small">');
      var troopList916 = opt_data.pb.training_thieves;
      var troopListLen916 = troopList916.length;
      for (var troopIndex916 = 0; troopIndex916 < troopListLen916; troopIndex916++) {
        var troopData916 = troopList916[troopIndex916];
        output.append(soy.$$escapeHtml(troopData916), ' ');
      }
      output.append('</td></tr>');
    }
    output.append('</table>');
  }
  output.append('</div></div>');
  return opt_sb ? '' : output.toString();
};


upoopu.common.templates.intel.survey = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div class="intel survey"><h1>Survey of ', soy.$$escapeHtml(opt_data.pb.province), ' (', soy.$$escapeHtml(opt_data.pb.kd), ':', soy.$$escapeHtml(opt_data.pb.isl), ')</h1><div class="tablewrap"><table class="survey">', (opt_data.pb.poster) ? '<tr class="small fullrow"><td colspan=4 >' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.accuracy)) + '% accurate. Posted ' + jrd.soy.ext.humanize(opt_data.pb.unix_time, 'unixtime') + ' by ' + soy.$$escapeHtml(opt_data.pb.poster) + '</td></tr>' : '', '<tr><th>Workers</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.workers)), '</td><th>BE</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.building_efficiency)), '%</td></tr><tr><th>Jobs</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.jobs)), '</td><th>W. Needed</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.workers_needed)), '</td></tr></table>');
  if (jrd.soy.ext.test(opt_data.pb.buildings)) {
    output.append('<table class="survey buildings"><thead><th colspan=', (opt_data.hash) ? '4' : '3', '>Buildings</th></thead><tr><th>Buiding</th><th class="number">Quantity</th><th class="number">Percentage</th>', (opt_data.hash) ? '<th class="number">Effects</th>' : '', '</tr>');
    var bList973 = opt_data.pb.buildings;
    var bListLen973 = bList973.length;
    for (var bIndex973 = 0; bIndex973 < bListLen973; bIndex973++) {
      var bData973 = bList973[bIndex973];
      output.append('<tr><th nowrap>', soy.$$escapeHtml(bData973.building), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(bData973.quantity)), '</td><td>', jrd.soy.ext.number(jrd.soy.ext.or(bData973.percentage), '#.00%'), '</td>');
      if (bData973.build_stats && opt_data.hash) {
        var sList987 = bData973.build_stats;
        var sListLen987 = sList987.length;
        for (var sIndex987 = 0; sIndex987 < sListLen987; sIndex987++) {
          var sData987 = sList987[sIndex987];
          output.append((! (sIndex987 == 0)) ? '<tr><td></td><td></td><td></td>' : '', '<td class="small">', soy.$$escapeHtml(sData987.effect), ': ', (sData987.abs) ? jrd.soy.ext.number(soy.$$escapeHtml(upoopu.soy.ext.upobuilding(opt_data.hash,bData973,sData987.effect, opt_data.pb.building_efficiency))) : jrd.soy.ext.number(soy.$$escapeHtml(100 * (upoopu.soy.ext.upobuilding(opt_data.hash,bData973,sData987.effect, opt_data.pb.building_efficiency) - 1)), '#.0%'), '</td>', (! (sIndex987 == 0)) ? '</tr>' : '');
        }
      }
      output.append('</tr>');
    }
    output.append('</table>');
  }
  output.append('</div></div>');
  return opt_sb ? '' : output.toString();
};


upoopu.common.templates.intel.sos = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div class="intel sos"><h1>Science of ', soy.$$escapeHtml(opt_data.pb.province), ' (', soy.$$escapeHtml(opt_data.pb.kd), ':', soy.$$escapeHtml(opt_data.pb.isl), ')</h1><div class="tablewrap"><table class="sos sciences">', (opt_data.pb.poster) ? '<tr class="small fullrow"><td colspan=' + ((opt_data.hash) ? '4' : '3') + ' >' + jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb.accuracy)) + '% accurate. Posted ' + jrd.soy.ext.humanize(opt_data.pb.unix_time, 'unixtime') + ' by ' + soy.$$escapeHtml(opt_data.pb.poster) + '</td></tr>' : '', '<tr><th>Science</th><th class="number">Quantity</th><th class="number">Per Acre</th>', (opt_data.hash) ? '<th class="number">Bonus</th>' : '', '</tr>');
  if (opt_data.pb.sciences) {
    var bList1043 = opt_data.pb.sciences;
    var bListLen1043 = bList1043.length;
    for (var bIndex1043 = 0; bIndex1043 < bListLen1043; bIndex1043++) {
      var bData1043 = bList1043[bIndex1043];
      output.append('<tr><th nowrap>', soy.$$escapeHtml(bData1043.science), '</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(bData1043.quantity)), '</td><td>', jrd.soy.ext.number(jrd.soy.ext.or(bData1043.per_acre), '#.0'), '</td>', (opt_data.hash) ? '<td>' + jrd.soy.ext.number(soy.$$escapeHtml(100 * upoopu.soy.ext.uposcience(opt_data.hash,bData1043.quantity,bData1043.quantity / bData1043.per_acre, bData1043)), '#.0%') + '</td>' : '', '</tr>');
    }
  }
  output.append('<tr><th nowrap>Total Books</th><td>', jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb['$total$quantity'])), '</td><td>', (opt_data.pb['$total$per_acre']) ? jrd.soy.ext.number(jrd.soy.ext.or(opt_data.pb['$total$per_acre']), '#.0') : '', '</td>', (opt_data.hash) ? '<td></td>' : '', '</tr></table></div></div>');
  return opt_sb ? '' : output.toString();
};


// This file was automatically generated from soy.common.common.soy.
// Please don't edit this file by hand.

if (typeof upoopu == 'undefined') { var upoopu = {}; }
if (typeof upoopu.common == 'undefined') { upoopu.common = {}; }
if (typeof upoopu.common.templates == 'undefined') { upoopu.common.templates = {}; }
if (typeof upoopu.common.templates.common == 'undefined') { upoopu.common.templates.common = {}; }


upoopu.common.templates.common.overlay = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div id="overlay" role="overlay" class="overlay intel">', opt_data.content, '</div>');
  return opt_sb ? '' : output.toString();
};


upoopu.common.templates.common.partialoverlay = function(opt_data, opt_sb) {
  var output = opt_sb || new soy.StringBuilder();
  output.append('\t<div id="overlay" role="overlay" class="overlay intel partialcover"><div class="overlaywrap">', opt_data.content, '</div></div>');
  return opt_sb ? '' : output.toString();
};



/***** xregexp.js *****/

/*!
 * XRegExp v2.0.0-beta2
 * Copyright 2007-2012 Steven Levithan <http://xregexp.com/>
 * Available under the MIT License
 */

/**
 * XRegExp provides augmented, extensible JavaScript regular expressions. You get new syntax,
 * flags, and methods beyond what browsers support natively. XRegExp is also a regex utility belt
 * with tools to make your client-side grepping simpler and more powerful, while freeing you from
 * worrying about pesky cross-browser inconsistencies and the dubious `lastIndex` property. See
 * XRegExp's documentation (http://xregexp.com/) for more details.
 * @module xregexp
 * @requires N/A
 */

// Avoid running twice; that would duplicate tokens and could break references to native globals
;typeof XRegExp === "undefined" &&
function (root, undefined) {
"use strict";

/*--------------------------------------
 *  Constructor
 *------------------------------------*/

/**
 * Creates an extended regular expression object for matching text with a pattern. Differs from a
 * native regular expression in that additional syntax and flags are supported. The returned object
 * is in fact a native `RegExp` and works with all native methods.
 * @class XRegExp
 * @constructor
 * @param {String} pattern Regular expression pattern string.
 * @param {String} [flags] Any combination of flags:
 *   <li>`g` - global
 *   <li>`i` - ignore case
 *   <li>`m` - multiline anchors
 *   <li>`n` - explicit capture
 *   <li>`s` - dot matches all (aka singleline)
 *   <li>`x` - free-spacing and line comments (aka extended)
 *   <li>`y` - sticky (Firefox 3+ only)
 * @returns {RegExp} Extended regular expression object.
 * @example
 *
 * // With named capture and flag x
 * date = XRegExp('(?<year>  [0-9]{4}) -?  # year  \n\
 *                 (?<month> [0-9]{2}) -?  # month \n\
 *                 (?<day>   [0-9]{2})     # day   ', 'x');
 *
 * // Clones the regex, preserving special properties for named capture
 * XRegExp(date);
 */
function XRegExp (pattern, flags) {
    if (X.isRegExp(pattern)) {
        if (flags !== undefined)
            throw new TypeError("can't supply flags when constructing one RegExp from another");
        return copy(pattern);
    }
    // Tokens become part of the regex construction process, so protect against infinite recursion
    // when an XRegExp is constructed within a token handler function
    if (isInsideConstructor)
        throw new Error("can't call the XRegExp constructor within token definition functions");
    var output = [],
        scope = defaultScope,
        tokenContext = {
            hasNamedCapture: false,
            captureNames: [],
            hasFlag: function (flag) {return flags.indexOf(flag) > -1;},
            setFlag: function (flag) {flags += flag;}
        },
        pos = 0,
        tokenResult, match, chr;
    pattern = pattern === undefined ? "" : pattern + "";
    flags = flags === undefined ? "" : flags + "";
    while (pos < pattern.length) {
        // Check for custom tokens at the current position
        tokenResult = runTokens(pattern, pos, scope, tokenContext);
        if (tokenResult) {
            output.push(tokenResult.output);
            pos += (tokenResult.match[0].length || 1);
        } else {
            // Check for native tokens (except character classes) at the current position
            if ((match = nativ.exec.call(nativeTokens[scope], pattern.slice(pos)))) {
                output.push(match[0]);
                pos += match[0].length;
            } else {
                chr = pattern.charAt(pos);
                if (chr === "[") scope = classScope;
                else if (chr === "]") scope = defaultScope;
                // Advance position by one character
                output.push(chr);
                pos++;
            }
        }
    }
    return augment(
        new R(output.join(""), nativ.replace.call(flags, flagClip, "")),
        tokenContext.hasNamedCapture ? tokenContext.captureNames : null
    );
}

/*--------------------------------------
 *  Private variables
 *------------------------------------*/

// Shortcuts
var X = XRegExp,
    R = RegExp,
    S = String;

// Optional features; can be installed and uninstalled
var features = {
    natives: false,
    methods: false,
    extensibility: false
};

// Store native methods to use and restore ("native" is an ES3 reserved keyword)
var nativ = {
    exec: R.prototype.exec,
    test: R.prototype.test,
    match: S.prototype.match,
    replace: S.prototype.replace,
    split: S.prototype.split,
    // Hold these so they can be given back if present before XRegExp runs
    apply: R.prototype.apply,
    call: R.prototype.call
};

// Storage for fixed/extended native methods
var fixed = {};

// Storage for addon tokens
var tokens = [];

// Token scope bitflags
var classScope = 0x1,
    defaultScope = 0x2;

// Storage for regexes that match native regex syntax
var nativeTokens = {};
// Any native multicharacter token in character class scope (includes octals)
nativeTokens[classScope] = /^(?:\\(?:[0-3][0-7]{0,2}|[4-7][0-7]?|x[\dA-Fa-f]{2}|u[\dA-Fa-f]{4}|c[A-Za-z]|[\s\S]))/;
// Any native multicharacter token in default scope (includes octals, excludes character classes)
nativeTokens[defaultScope] = /^(?:\\(?:0(?:[0-3][0-7]{0,2}|[4-7][0-7]?)?|[1-9]\d*|x[\dA-Fa-f]{2}|u[\dA-Fa-f]{4}|c[A-Za-z]|[\s\S])|\(\?[:=!]|[?*+]\?|{\d+(?:,\d*)?}\??)/;

// Any backreference in replacement strings
var replacementToken = /\$(?:(\d\d?|[$&`'])|{([$\w]+)})/g;

// Nonnative and duplicate flags
var flagClip = /[^gimy]+|([\s\S])(?=[\s\S]*\1)/g;

// Any greedy/lazy quantifier
var quantifier = /^(?:[?*+]|{\d+(?:,\d*)?})\??/;

// Check for correct `exec` handling of nonparticipating capturing groups
var compliantExecNpcg = nativ.exec.call(/()??/, "")[1] === undefined;

// Check for correct handling of `lastIndex` after zero-length matches
var compliantLastIndexIncrement = function () {
    var x = /^/g;
    nativ.test.call(x, "");
    return !x.lastIndex;
}();

// Check for flag y support (Firefox 3+)
var hasNativeY = R.prototype.sticky !== undefined;

// Used to kill infinite recursion during XRegExp construction
var isInsideConstructor = false;

// Installed and uninstalled states for `XRegExp.addToken`
var addToken = {
    on: function (regex, handler, scope, trigger) {
        tokens.push({
            pattern: copy(regex, "g" + (hasNativeY ? "y" : "")),
            handler: handler,
            scope: scope || defaultScope,
            trigger: trigger || null
        });
    },
    off: function () {
        throw new Error("extensibility must be installed before running addToken");
    }
};

/*--------------------------------------
 *  Public properties/methods
 *------------------------------------*/

/**
 * The semantic version number.
 * @static
 * @memberOf XRegExp
 * @type String
 */
X.version = "2.0.0-beta2";

/**
 * Bitflag for regex character class scope; used by addons.
 * @final
 * @memberOf XRegExp
 * @type Number
 */
X.INSIDE_CLASS = classScope;

/**
 * Bitflag for regex default scope; used by addons.
 * @final
 * @memberOf XRegExp
 * @type Number
 */
X.OUTSIDE_CLASS = defaultScope;

/**
 * Extends or changes XRegExp syntax and allows custom flags. This is used internally by XRegExp
 * and can be used to create XRegExp addons. `XRegExp.install('extensibility')` must be run before
 * calling this function, or an error is thrown. If more than one token can match the same string,
 * the last added wins.
 * @memberOf XRegExp
 * @param {RegExp} regex Regex object that matches the new token.
 * @param {Function} handler Function that returns a new pattern string (using native regex syntax)
 *   to replace the matched token within all future XRegExp regexes. Has access to persistent
 *   properties of the regex being built, through `this`. Invoked with two arguments:
 *   <li>The match array, with named backreference properties.
 *   <li>The regex scope where the match was found.
 * @param {Number} [scope=XRegExp.OUTSIDE_CLASS] Regex scopes where the token applies. Include
 *   multiple scopes using bitwise OR.
 * @param {Function} [trigger] Function that returns `true` when the token should be applied; e.g.,
 *   if a flag is set. If `false` is returned, the matched string can be matched by other tokens.
 *   Has access to persistent properties of the regex being built, through `this` (including
 *   function `this.hasFlag`).
 * @returns {undefined} N/A
 * @example
 *
 * // Adds support for escape sequences: \Q..\E and \Q..
 * XRegExp.addToken(
 *   /\\Q([\s\S]*?)(?:\\E|$)/,
 *   function (match) {return XRegExp.escape(match[1]);},
 *   XRegExp.INSIDE_CLASS | XRegExp.OUTSIDE_CLASS
 * );
 */
X.addToken = addToken.off;

/**
 * Caches and returns the result of calling `XRegExp(pattern, flags)`. On any subsequent call with
 * the same pattern and flag combination, the cached copy is returned.
 * @memberOf XRegExp
 * @param {String} pattern Regular expression pattern string.
 * @param {String} [flags] Any combination of flags.
 * @returns {RegExp} Cached XRegExp object.
 * @example
 *
 * while (match = XRegExp.cache('.', 'gs').exec(str)) {
 *   // The regex is compiled once only
 * }
 */
X.cache = function (pattern, flags) {
    var key = pattern + "/" + (flags || "");
    return X.cache[key] || (X.cache[key] = X(pattern, flags));
};

/**
 * Escapes any regular expression metacharacters, for use when matching literal strings. The result
 * can safely be used at any point within a regex that uses any flags.
 * @memberOf XRegExp
 * @param {String} str String to escape.
 * @returns {String} String with regex metacharacters escaped.
 * @example
 *
 * XRegExp.escape('Escaped? <.>');
 * // -> 'Escaped\?\ <\.>'
 */
X.escape = function (str) {
    return nativ.replace.call(str, /[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
};

/**
 * Executes a regex search in a specified string. Returns a match array or `null`. If the provided
 * regex uses named capture, named backreference properties are included on the match array.
 * Optional `pos` and `sticky` arguments specify the search start position, and whether the match
 * must start at the specified position only. The `lastIndex` property of the provided regex is not
 * used, but is updated for compatibility. Also fixes browser bugs compared to the native
 * `RegExp.prototype.exec` and can be used reliably cross-browser.
 * @memberOf XRegExp
 * @param {String} str String to search.
 * @param {RegExp} regex Regular expression to search with.
 * @param {Number} [pos=0] Zero-based index at which to start the search.
 * @param {Boolean} [sticky=false] Whether the match must start at the specified position only.
 * @returns {Array} Match array with named backreference properties, or null.
 * @example
 *
 * // Basic use, with named backreference
 * var match = XRegExp.exec('U+2620', XRegExp('U\\+(?<hex>[0-9A-F]{4})'));
 * match.hex; // -> '2620'
 *
 * // With pos and sticky, in a loop
 * var pos = 2, result = [];
 * while (match = XRegExp.exec('<1><2><3><4>5<6>', /<(\d)>/, pos, true)) {
 *   result.push(match[1]);
 *   pos = match.index + match[0].length;
 * }
 * // result -> ['2', '3', '4']
 */
X.exec = function (str, regex, pos, sticky) {
    var r2 = copy(regex, "g" + ((sticky && hasNativeY) ? "y" : "")),
        match;
    r2.lastIndex = pos = pos || 0;
    match = fixed.exec.call(r2, str); // Fixed `exec` required for `lastIndex` fix, etc.
    if (sticky && match && match.index !== pos)
        match = null;
    if (regex.global)
        regex.lastIndex = match ? r2.lastIndex : 0;
    return match;
};

/**
 * Executes a provided function once per regex match.
 * @memberOf XRegExp
 * @param {String} str String to search.
 * @param {RegExp} regex Regular expression to search with.
 * @param {Function} callback Function to execute for each match. Invoked with four arguments:
 *   <li>The match array, with named backreference properties.
 *   <li>The zero-based match index.
 *   <li>The string being traversed.
 *   <li>The regex object being used to traverse the string.
 * @param {Object|Array} [context] Object to use as `this` when executing `callback`.
 * @returns {Object|Array} Provided `context` object.
 * @example
 *
 * // Extracts every other digit from a string
 * XRegExp.forEach("1a2345", /\d/, function (match, i) {
 *   if (i % 2) this.push(+match[0]);
 * }, []);
 * // -> [2, 4]
 */
X.forEach = function (str, regex, callback, context) {
    var r2 = X.globalize(regex),
        i = -1, match;
    while ((match = fixed.exec.call(r2, str))) { // Fixed `exec` required for `lastIndex` fix, etc.
        if (regex.global)
            regex.lastIndex = r2.lastIndex; // Doing this to follow expectations if `lastIndex` is checked within `callback`
        callback.call(context, match, ++i, str, regex);
        if (r2.lastIndex === match.index)
            r2.lastIndex++;
    }
    if (regex.global)
        regex.lastIndex = 0;
    return context;
};

/**
 * Copies a regex object and adds flag g, preserving special properties for named capture. The copy
 * has a fresh `lastIndex` property (set to zero).
 * @memberOf XRegExp
 * @param {RegExp} regex Regex to globalize.
 * @returns {RegExp} Copy of the provided regex with flag g added.
 * @example
 *
 * var globalCopy = XRegExp.globalize(/regex/);
 * globalCopy.global; // -> true
 */
X.globalize = function (regex) {
    return copy(regex, "g");
};

/**
 * Installs optional features according to the specified options.
 * @memberOf XRegExp
 * @param {String|Object} options Options object.
 * @returns {undefined} N/A
 * @example
 *
 * // With an options object
 * XRegExp.install({
 *   // Overrides native regex methods with fixed/extended versions that support named
 *   // backreferences and fix numerous cross-browser bugs
 *   natives: true,
 *
 *   // Copies XRegExp.prototype methods to RegExp.prototype
 *   methods: true,
 *
 *   // Enables extensibility of XRegExp syntax and flag (used by addons)
 *   extensibility: true
 * });
 *
 * // With an options string
 * XRegExp.install('natives methods');
 *
 * // Using a shortcut to install all optional features
 * XRegExp.install('all');
 */
X.install = function (options) {
    options = prepareOptions(options);
    if (!features.natives && options.natives) setNatives(true);
    if (!features.methods && options.methods) setMethods(true);
    if (!features.extensibility && options.extensibility) setExtensibility(true);
};

/**
 * Returns `true` if an object is a regex; `false` if it isn't. This works correctly for regexes
 * created in another frame, when `instanceof` and `constructor` checks would fail.
 * @memberOf XRegExp
 * @param {*} value Object to check.
 * @returns {Boolean} Whether the object is a `RegExp` object.
 * @example
 *
 * XRegExp.isRegExp(XRegExp('(?s).')); // -> true
 */
X.isRegExp = function (value) {
    return Object.prototype.toString.call(value) === "[object RegExp]";
};

/**
 * Checks whether an individual optional feature is installed.
 * @memberOf XRegExp
 * @param {String} feature Name of the feature to check. Any one of:
 *   <li>`natives`
 *   <li>`methods`
 *   <li>`extensibility`
 * @returns {Boolean} Whether the feature is installed.
 * @example
 *
 * XRegExp.isInstalled('natives');
 */
X.isInstalled = function (feature) {
    return !!(features[feature]);
};

/**
 * Retrieves the matches from searching a string using a chain of regexes that successively search
 * within previous matches. The provided `chain` array can contain regexes and objects with `regex`
 * and `backref` properties. When a backreference is specified, the named or numbered backreference
 * is passed forward to the next regex or returned.
 * @memberOf XRegExp
 * @param {String} str String to search.
 * @param {Array} chain Regexes that each search for matches within preceding results.
 * @returns {Array} Matches by the last regex in the chain, or an empty array.
 * @example
 *
 * // Basic usage; matches numbers within <b> tags
 * XRegExp.matchChain('1 <b>2</b> 3 <b>4 a 56</b>', [
 *   XRegExp('(?is)<b>.*?<\\/b>'),
 *   /\d+/
 * ]);
 * // -> ['2', '4', '56']
 *
 * // Passing forward and returning specific backreferences
 * XRegExp.matchChain(html, [
 *   {regex: /<a href="([^"]+)">/i, backref: 1},
 *   {regex: XRegExp('(?i)^https?://(?<domain>[^/?#]+)'), backref: 'domain'}
 * ]);
 */
X.matchChain = function (str, chain) {
    return function recurseChain (values, level) {
        var item = chain[level].regex ? chain[level] : {regex: chain[level]},
            regex = X.globalize(item.regex),
            matches = [], i;
        for (i = 0; i < values.length; i++) {
            X.forEach(values[i], regex, function (match) {
                matches.push(item.backref ? (match[item.backref] || "") : match[0]);
            });
        }
        return ((level === chain.length - 1) || !matches.length) ?
            matches : recurseChain(matches, level + 1);
    }([str], 0);
};

/**
 * Returns a new string with one or all matches of a pattern replaced. The pattern can be a string
 * or regex, and the replacement can be a string or a function to be called for each match. To
 * perform a global search and replace, use the optional `replaceAll` argument or include flag g if
 * using a regex. Replacement strings can use `${n}` for named and numbered backreferences.
 * Replacement functions can use named backreferences via `arguments[0].name`. Also fixes browser
 * bugs compared to the native `String.prototype.replace` and can be used reliably cross-browser.
 * @memberOf XRegExp
 * @param {String} str String to search.
 * @param {RegExp|String} search Search pattern to be replaced.
 * @param {String|Function} replacement Replacement string or a function invoked to create it.
 *   Replacement strings can include special replacement patterns:
 *     <li>$$ - Inserts a "$".
 *     <li>$& - Inserts the matched substring.
 *     <li>$` - Inserts the string portion that precedes the matched substring.
 *     <li>$' - Inserts the string portion that follows the matched substring.
 *     <li>$n/$nn - Where n/nn are digits referencing an existent capturing group, inserts
 *       backreference n/nn.
 *     <li>${n} - Where n is a name or any number of digits that reference an existent capturing
 *       group, inserts backreference n.
 *   Replacement functions are invoked with three or more arguments:
 *     <li>The matched substring (corresponds to $& above). Named backreferences are accessible as
 *       properties of this first argument.
 *     <li>0..n arguments, one for each backreference (corresponding to $1, $2, etc. above).
 *     <li>The zero-based index of the match within the total search string.
 *     <li>The total string being searched.
 * @param {Boolean} [replaceAll=false] Whether to replace all matches or the first match only. If
 *   not explicitly specified as `false` and using a regex with flag g, `replaceAll` is `true`.
 * @returns {String} New string with one or all matches replaced.
 * @example
 *
 * // Regex search, using named backreferences in replacement string
 * var name = XRegExp('(?<first>\\w+) (?<last>\\w+)');
 * XRegExp.replace('John Smith', name, '${last}, ${first}');
 * // -> 'Smith, John'
 *
 * // Regex search, using named backreferences in replacement function
 * XRegExp.replace('John Smith', name, function (match) {
 *   return match.last + ', ' + match.first;
 * });
 * // -> 'Smith, John'
 *
 * // Global string search/replacement
 * XRegExp.replace('RegExp builds RegExps', 'RegExp', 'XRegExp', true);
 * // -> 'XRegExp builds XRegExps'
 */
X.replace = function (str, search, replacement, replaceAll) {
    var isRegex = X.isRegExp(search),
        search2 = search,
        result;
    if (isRegex) {
        if (replaceAll === undefined)
            replaceAll = search.global; // Follow flag g when `replaceAll` isn't explicit
        // Note that since a copy is used, `search`'s `lastIndex` isn't updated *during* replacement iterations
        search2 = copy(search, replaceAll ? "g" : "", replaceAll ? "" : "g");
    } else if (replaceAll) {
        search2 = new R(X.escape(search + ""), "g");
    }
    result = fixed.replace.call(str + "", search2, replacement); // Fixed `replace` required for named backreferences, etc.
    if (isRegex && search.global)
        search.lastIndex = 0; // Fixes IE, Safari bug (last tested IE 9, Safari 5.1)
    return result;
};

/**
 * Splits a string into an array of strings using a regex or string separator. Matches of the
 * separator are not included in the result array. However, if `separator` is a regex that contains
 * capturing groups, backreferences are spliced into the result each time `separator` is matched.
 * Fixes browser bugs compared to the native `String.prototype.split` and can be used reliably
 * cross-browser.
 * @memberOf XRegExp
 * @param {String} str String to split.
 * @param {RegExp|String} separator Regex or string to use for separating the string.
 * @param {Number} [limit] Maximum number of items to include in the result array.
 * @returns {Array} Array of substrings.
 * @example
 *
 * // Basic use
 * XRegExp.split('a b c d', ' ');
 * // -> ['a', 'b', 'c', 'd']
 *
 * // With limit
 * XRegExp.split('a b c d', ' ', 2);
 * // -> ['a', 'b']
 *
 * // Backreferences in result array
 * XRegExp.split('..word1 word2..', /([a-z]+)(\d+)/i);
 * // -> ['..', 'word', '1', ' ', 'word', '2', '..']
 */
X.split = function (str, separator, limit) {
    return fixed.split.call(str, separator, limit);
};

/**
 * Uninstalls optional features according to the specified options.
 * @memberOf XRegExp
 * @param {String|Object} options Options object.
 * @returns {undefined} N/A
 * @example
 *
 * // With an options object
 * XRegExp.uninstall({
 *   // Restores native regex methods
 *   natives: true,
 *
 *   // Removes added RegExp.prototype methods, or restores their original values
 *   methods: true,
 *
 *   // Disables additional syntax and flag extensions
 *   extensibility: true
 * });
 *
 * // With an options string
 * XRegExp.uninstall('natives methods');
 *
 * // Using a shortcut to uninstall all optional features
 * XRegExp.uninstall('all');
 */
X.uninstall = function (options) {
    options = prepareOptions(options);
    if (features.natives && options.natives) setNatives(false);
    if (features.methods && options.methods) setMethods(false);
    if (features.extensibility && options.extensibility) setExtensibility(false);
};

/*--------------------------------------
 *  XRegExp.prototype methods
 *------------------------------------*/

/**
 * Calls an XRegExp object's `test` method with the first value in the provided arguments array.
 * @memberOf XRegExp.prototype
 * @param {Object} context Ignored. Accepted only for congruity with `Function.prototype.apply`.
 * @param {Array} args Array with the string to search as its first value.
 * @returns {Boolean} Whether the regex matched the provided value.
 * @example
 *
 * XRegExp('\\d').apply(null, ['123']); // -> true
 */
X.prototype.apply = function (context, args) {
    return this.test(args[0]); // Intentionally doesn't specify fixed/native `test`
};

/**
 * Calls an XRegExp object's `test` method with the provided string.
 * @memberOf XRegExp.prototype
 * @param {Object} context Ignored. Accepted only for congruity with `Function.prototype.call`.
 * @param {String} str String to search.
 * @returns {Boolean} Whether the regex matched the provided value.
 * @example
 *
 * XRegExp('\\d').call(null, '123'); // -> true
 */
X.prototype.call = function (context, str) {
    return this.test(str); // Intentionally doesn't specify fixed/native `test`
};

/*--------------------------------------
 *  Fixed/extended native methods
 *------------------------------------*/

/**
 * Adds named capture support (with backreferences returned as `result.name`), and fixes browser
 * bugs in the native `RegExp.prototype.exec`. Calling `XRegExp.install('natives')` uses this to
 * override the native method. Use via `XRegExp.exec` without overriding natives.
 * @private
 * @param {String} str String to search.
 * @returns {Array} Match array with named backreference properties, or null.
 */
fixed.exec = function (str) {
    var match, name, r2, origLastIndex;
    if (!this.global)
        origLastIndex = this.lastIndex;
    match = nativ.exec.apply(this, arguments);
    if (match) {
        // Fix browsers whose `exec` methods don't consistently return `undefined` for
        // nonparticipating capturing groups
        if (!compliantExecNpcg && match.length > 1 && indexOf(match, "") > -1) {
            r2 = new R(this.source, nativ.replace.call(getNativeFlags(this), "g", ""));
            // Using `str.slice(match.index)` rather than `match[0]` in case lookahead allowed
            // matching due to characters outside the match
            nativ.replace.call((str + "").slice(match.index), r2, function () {
                for (var i = 1; i < arguments.length - 2; i++) {
                    if (arguments[i] === undefined)
                        match[i] = undefined;
                }
            });
        }
        // Attach named capture properties
        if (this._xregexp && this._xregexp.captureNames) {
            for (var i = 1; i < match.length; i++) {
                name = this._xregexp.captureNames[i - 1];
                if (name)
                   match[name] = match[i];
            }
        }
        // Fix browsers that increment `lastIndex` after zero-length matches
        if (!compliantLastIndexIncrement && this.global && !match[0].length && (this.lastIndex > match.index))
            this.lastIndex--;
    }
    if (!this.global)
        this.lastIndex = origLastIndex; // Fixes IE, Opera bug (last tested IE 9, Opera 11.6)
    return match;
};

/**
 * Fixes browser bugs in the native `RegExp.prototype.test`. Calling `XRegExp.install('natives')`
 * uses this to override the native method.
 * @private
 * @param {String} str String to search.
 * @returns {Boolean} Whether the regex matched the provided value.
 */
fixed.test = function (str) {
    // Do this the easy way :-)
    return !!fixed.exec.call(this, str);
};

/**
 * Adds named capture support (with backreferences returned as `result.name`), and fixes browser
 * bugs in the native `String.prototype.match`. Calling `XRegExp.install('natives')` uses this to
 * override the native method.
 * @private
 * @param {RegExp} regex Regular expression to search with.
 * @returns {Array} If `regex` uses flag g, an array of match strings or null. Without flag g, the
 *   result of calling `regex.exec(this)`.
 */
fixed.match = function (regex) {
    if (!X.isRegExp(regex))
        regex = new R(regex); // Use native `RegExp`
    if (regex.global) {
        var result = nativ.match.apply(this, arguments);
        regex.lastIndex = 0; // Fixes IE bug
        return result;
    }
    return fixed.exec.call(regex, this);
};

/**
 * Adds support for `${n}` tokens for named and numbered backreferences in replacement text, and
 * provides named backreferences to replacement functions as `arguments[0].name`. Also fixes
 * browser bugs in replacement text syntax when performing a replacement using a nonregex search
 * value, and the value of a replacement regex's `lastIndex` property during replacement iterations
 * and upon completion. Note that this doesn't support SpiderMonkey's proprietary third (`flags`)
 * argument. Calling `XRegExp.install('natives')` uses this to override the native method. Use via
 * `XRegExp.replace` without overriding natives.
 * @private
 * @param {RegExp|String} search Search pattern to be replaced.
 * @param {String|Function} replacement Replacement string or a function invoked to create it.
 * @returns {String} New string with one or all matches replaced.
 */
fixed.replace = function (search, replacement) {
    var isRegex = X.isRegExp(search),
        captureNames, result, str, origLastIndex;
    if (isRegex) {
        if (search._xregexp) captureNames = search._xregexp.captureNames;
        if (!search.global) origLastIndex = search.lastIndex;
    } else {
        search += "";
    }
    if (Object.prototype.toString.call(replacement) === "[object Function]") {
        result = nativ.replace.call(this + "", search, function () {
            if (captureNames) {
                // Change the `arguments[0]` string primitive to a `String` object that can store properties
                arguments[0] = new S(arguments[0]);
                // Store named backreferences on `arguments[0]`
                for (var i = 0; i < captureNames.length; i++) {
                    if (captureNames[i])
                        arguments[0][captureNames[i]] = arguments[i + 1];
                }
            }
            // Update `lastIndex` before calling `replacement`.
            // Fixes IE, Chrome, Firefox, Safari bug (last tested IE 9, Chrome 17, Firefox 10, Safari 5.1)
            if (isRegex && search.global)
                search.lastIndex = arguments[arguments.length - 2] + arguments[0].length;
            return replacement.apply(null, arguments);
        });
    } else {
        str = this + ""; // Ensure `args[args.length - 1]` will be a string when given nonstring `this`
        result = nativ.replace.call(str, search, function () {
            var args = arguments; // Keep this function's `arguments` available through closure
            return nativ.replace.call(replacement + "", replacementToken, function ($0, $1, $2) {
                // Numbered backreference (without delimiters) or special variable
                if ($1) {
                    if ($1 === "$") return "$";
                    if ($1 === "&") return args[0];
                    if ($1 === "`") return args[args.length - 1].slice(0, args[args.length - 2]);
                    if ($1 === "'") return args[args.length - 1].slice(args[args.length - 2] + args[0].length);
                    // Else, numbered backreference
                    /* Assert: `$10` in replacement is one of:
                     *   1. Backreference 10, if 10 or more capturing groups exist.
                     *   2. Backreference 1 followed by `0`, if 1-9 capturing groups exist.
                     *   3. Otherwise, it's the literal string `$10`.
                     * Details:
                     *   - Backreferences can't be more than two digits (enforced by `replacementToken`).
                     *   - `$01` is equivalent to `$1` if a capturing group exists, otherwise it's the string `$01`.
                     *   - There is no `$0` token (`$&` is the entire match).
                     */
                    var literalNumbers = "";
                    $1 = +$1; // Type-convert; drop leading zero
                    if (!$1) // `$1` was `0` or `00`
                        return $0;
                    while ($1 > args.length - 3) {
                        literalNumbers = S.prototype.slice.call($1, -1) + literalNumbers;
                        $1 = Math.floor($1 / 10); // Drop the last digit
                    }
                    return ($1 ? args[$1] || "" : "$") + literalNumbers;
                // Named backreference or delimited numbered backreference
                } else {
                    /* Assert: `${n}` in replacement is one of:
                     *   1. Backreference to numbered capture `n`. Differences from `$n`:
                     *     - `n` can be more than two digits.
                     *     - Backreference 0 is allowed, and is the entire match.
                     *   2. Backreference to named capture `n`, if it exists and is not a number overridden by numbered capture.
                     *   3. Otherwise, it's the literal string `${n}`.
                     */
                    var n = +$2; // Type-convert; drop leading zeros
                    if (n <= args.length - 3)
                        return args[n];
                    n = captureNames ? indexOf(captureNames, $2) : -1;
                    return n > -1 ? args[n + 1] : $0;
                }
            });
        });
    }
    if (isRegex) {
        if (search.global) search.lastIndex = 0; // Fixes IE, Safari bug (last tested IE 9, Safari 5.1)
        else search.lastIndex = origLastIndex; // Fixes IE, Opera bug (last tested IE 9, Opera 11.6)
    }
    return result;
};

/**
 * Fixes browser bugs in the native `String.prototype.split`. Calling `XRegExp.install('natives')`
 * uses this to override the native method. Use via `XRegExp.split` without overriding natives.
 * @private
 * @param {RegExp|String} separator Regex or string to use for separating the string.
 * @param {Number} [limit] Maximum number of items to include in the result array.
 * @returns {Array} Array of substrings.
 */
fixed.split = function (s /*separator*/, limit) {
    // If separator `s` is not a regex, use the native `split`
    if (!X.isRegExp(s))
        return nativ.split.apply(this, arguments);
    var str = this + "",
        output = [],
        lastLastIndex = 0,
        match, lastLength;
    /* Values for `limit`, per the spec:
     * If undefined: 4294967295 // Math.pow(2, 32) - 1
     * If 0, Infinity, or NaN: 0
     * If a positive number: limit = Math.floor(limit); if (limit > 4294967295) limit -= 4294967296;
     * If a negative number: 4294967296 - Math.floor(Math.abs(limit))
     * If other: Type-convert, then use the above rules
     */
    limit = limit === undefined ?
        -1 >>> 0 : // Math.pow(2, 32) - 1
        limit >>> 0; // ToUint32(limit)
    // This is required if not `s.global`, and it avoids needing to set `s.lastIndex` to zero
    // and restore it to its original value when we're done using the regex
    s = X.globalize(s);
    while ((match = fixed.exec.call(s, str))) { // Fixed `exec` required for `lastIndex` fix, etc.
        if (s.lastIndex > lastLastIndex) {
            output.push(str.slice(lastLastIndex, match.index));
            if (match.length > 1 && match.index < str.length)
                Array.prototype.push.apply(output, match.slice(1));
            lastLength = match[0].length;
            lastLastIndex = s.lastIndex;
            if (output.length >= limit)
                break;
        }
        if (s.lastIndex === match.index)
            s.lastIndex++;
    }
    if (lastLastIndex === str.length)
        (!nativ.test.call(s, "") || lastLength) && output.push("");
    else
        output.push(str.slice(lastLastIndex));
    return output.length > limit ? output.slice(0, limit) : output;
};

/*--------------------------------------
 *  Built-in tokens
 *------------------------------------*/

// Shortcut
var add = addToken.on;

/* Unicode token: \p{..} or \P{..}
 * Reserves syntax; superseded by the XRegExp Unicode Base addon.
 */
add(/\\[pP]{[^}]*}/,
    function () {
        throw new ReferenceError("Unicode tokens require XRegExp Unicode Base");
    },
    X.INSIDE_CLASS | X.OUTSIDE_CLASS);

/* Empty character class: [] or [^]
 * Fixes a critical cross-browser syntax inconsistency. Unless this is standardized (per the spec),
 * regex syntax can't be accurately parsed because character class endings can't be determined.
 */
add(/\[(\^?)]/,
    function (match) {
        // For cross-browser compatibility with ES3, convert [] to \b\B and [^] to [\s\S].
        // (?!) should work like \b\B, but is unreliable in Firefox
        return match[1] ? "[\\s\\S]" : "\\b\\B";
    });

/* Comment pattern: (?# )
 * Inline comments are an alternative to the line comments allowed in free-spacing mode (flag x).
 */
add(/\(\?#[^)]*\)/,
    function (match) {
        // Keep tokens separated unless the following token is a quantifier
        return nativ.test.call(quantifier, match.input.slice(match.index + match[0].length)) ? "" : "(?:)";
    });

/* Leading mode modifier, with any combination of flags except g or y: (?imnsx)
 * Does not support other uses of mode modifiers such as ..(?i), (?-i), (?i-m), (?i: ), or (?i)(?m)
 */
add(/^\(\?([a-z]+)\)/i,
    function (match) {
        if (nativ.test.call(/[gy]/, match[1]))
            throw new SyntaxError("can't use flag g or y in mode modifier");
        this.setFlag(match[1]);
        return "";
    });

/* Named backreference: \k<name>
 * Backreference names can use the characters A-Z, a-z, 0-9, _, and $ only.
 */
add(/\\k<([\w$]+)>/,
    function (match) {
        var index = indexOf(this.captureNames, match[1]);
        // Keep backreferences separate from subsequent literal numbers. Preserve back-
        // references to named groups that are undefined at this point as literal strings
        return index > -1 ?
            "\\" + (index + 1) + (isNaN(match.input.charAt(match.index + match[0].length)) ? "" : "(?:)") :
            match[0];
    });

/* Whitespace and line comments, in free-spacing mode (aka extended mode, flag x) only.
 */
add(/(?:\s+|#.*)+/,
    function (match) {
        // Keep tokens separated unless the following token is a quantifier
        return nativ.test.call(quantifier, match.input.slice(match.index + match[0].length)) ? "" : "(?:)";
    },
    X.OUTSIDE_CLASS,
    function () {return this.hasFlag("x");});

/* Dot, in dotall mode (aka singleline mode, flag s) only.
 */
add(/\./,
    function () {return "[\\s\\S]";},
    X.OUTSIDE_CLASS,
    function () {return this.hasFlag("s");});

/* Named capturing group; match the opening delimiter only: (?<name>
 * Capture names can use the characters A-Z, a-z, 0-9, _, and $ only. Names can't be integers.
 */
add(/\(\?<([$\w]+)>/,
    function (match) {
        if (!isNaN(match[1])) // Avoid incorrect lookups since named backreferences are added to match arrays
            throw new SyntaxError("can't use an integer as capture name");
        this.captureNames.push(match[1]);
        this.hasNamedCapture = true;
        return "(";
    });

/* Capturing group; match the opening parenthesis only.
 * Required for support of named capturing groups. Also adds explicit capture mode (flag n).
 */
add(/\((?!\?)/,
    function () {
        if (this.hasFlag("n")) {
            return "(?:";
        } else {
            this.captureNames.push(null);
            return "(";
        }
    });

/*--------------------------------------
 *  Private helper functions
 *------------------------------------*/

/**
 * Attaches methods and special properties for named capture to an `XRegExp` object.
 * @private
 * @param {RegExp} regex Regex to augment.
 * @param {Array} captureNames Array with capture names, or null.
 * @returns {RegExp} Augmented regex.
 */
function augment (regex, captureNames) {
    regex._xregexp = {captureNames: captureNames};
    // Can't automatically inherit these since the XRegExp constructor returns a nonprimitive value
    regex.apply = X.prototype.apply;
    regex.call = X.prototype.call;
    return regex;
}

/**
 * Copies a regex object, preserving special properties for named capture. The copy has a fresh
 * `lastIndex` property (set to zero). Allows adding and removing flags while copying the regex.
 * @private
 * @param {RegExp} regex Regex to copy.
 * @param {String} [addFlags] Flags to be added while copying the regex.
 * @param {String} [removeFlags] Flags to be removed while copying the regex.
 * @returns {RegExp} Copy of the provided regex, possibly with modified flags.
 */
function copy (regex, addFlags, removeFlags) {
    if (!X.isRegExp(regex))
        throw new TypeError("type RegExp expected");
    var x = regex._xregexp,
        flags = getNativeFlags(regex) + (addFlags || "");
    if (removeFlags)
        flags = nativ.replace.call(flags, new R("[" + removeFlags + "]+", "g"), ""); // Would need to escape `removeFlags` if this was public
    if (x) {
        // Compiling the current (rather than precompilation) source preserves the effects of nonnative source flags
        regex = augment(
            X(regex.source, flags),
            x.captureNames ? x.captureNames.slice(0) : null
        );
    } else {
        // Remove duplicate flags to avoid throwing
        flags = nativ.replace.call(flags, /([\s\S])(?=[\s\S]*\1)/g, "");
        // Don't use `XRegExp`; avoid searching for special tokens and adding special properties
        regex = new R(regex.source, flags); // Use native `RegExp`
    }
    return regex;
}

/**
 * Returns native `RegExp` flags used by a regex object.
 * @private
 * @param {RegExp} regex Regex to check.
 * @returns {String} Native flags in use.
 */
function getNativeFlags (regex) {
    //return nativ.exec.call(/\/([a-z]*)$/i, regex + "")[1];
    return (regex.global     ? "g" : "") +
           (regex.ignoreCase ? "i" : "") +
           (regex.multiline  ? "m" : "") +
           (regex.extended   ? "x" : "") + // Proposed for ES6; included in AS3
           (regex.sticky     ? "y" : ""); // Firefox 3+
}

/*
 * Returns the first index at which a given item can be found in the array, or -1.
 * @private
 * @param {Array} array Array to search.
 * @param {Object} item Item to locate in the array.
 * @param {Number} [from=0] Zero-based index at which to begin the search.
 * @returns {Number} First zero-based index at which the item was found, or -1.
 */
function indexOf (array, item, from) {
    if (Array.prototype.indexOf) // Use the native array method if available
        return array.indexOf(item, from);
    for (var i = from || 0; i < array.length; i++) {
        if (array[i] === item)
            return i;
    }
    return -1;
}

/**
 * Prepares an options object from the given value.
 * @private
 * @param {String|Object} value Value to convert to an options object.
 * @returns {Object} Options object.
 */
function prepareOptions (value) {
    value = value || {};
    if (value === "all" || value.all)
        value = {natives: true, methods: true, extensibility: true};
    else if (typeof value === "string")
        value = X.forEach(value, /[^\s,]+/, function (m) {this[m] = true;}, {});
    return value;
}

/**
 * Runs built-in/custom tokens in order from most to least recently added, until a match is found.
 * @private
 * @param {String} pattern Original pattern from which an XRegExp object is being built.
 * @param {Number} pos Position to search for tokens within `pattern`.
 * @param {Number} scope Current regex scope.
 * @param {Object} context Context object assigned to token handler functions.
 * @returns {Object} Object with properties `output` (the substitution string returned by the
 *   successful token handler) and `match` (the token's match array), or null.
 */
function runTokens (pattern, pos, scope, context) {
    var i = tokens.length,
        result = null,
        match, t;
    // Protect against constructing XRegExps within token handler and trigger functions
    isInsideConstructor = true;
    // Must reset `isInsideConstructor`, even if a `trigger` or `handler` throws
    try {
        while (i--) { // Run in reverse order
            t = tokens[i];
            if ((scope & t.scope) && (!t.trigger || t.trigger.call(context))) {
                t.pattern.lastIndex = pos;
                match = fixed.exec.call(t.pattern, pattern); // Fixed `exec` here allows use of named backreferences, etc.
                if (match && match.index === pos) {
                    result = {
                        output: t.handler.call(context, match, scope),
                        match: match
                    };
                    break;
                }
            }
        }
    } catch (err) {
        throw err;
    } finally {
        isInsideConstructor = false;
    }
    return result;
}

/**
 * Enables or disables XRegExp syntax and flag extensibility.
 * @private
 * @param {Boolean} on `true` to enable; `false` to disable.
 * @returns {undefined} N/A
 */
function setExtensibility (on) {
    X.addToken = addToken[on ? "on" : "off"];
    features.extensibility = on;
}

/**
 * Enables or disables new `RegExp.prototype` methods.
 * @private
 * @param {Boolean} on `true` to enable; `false` to disable.
 * @returns {undefined} N/A
 */
function setMethods (on) {
    if (on) {
        R.prototype.apply = X.prototype.apply;
        R.prototype.call = X.prototype.call;
    } else {
        // Restore methods if they existed before XRegExp ran; otherwise delete
        nativ.apply ? R.prototype.apply = nativ.apply : delete R.prototype.apply;
        nativ.call ? R.prototype.call = nativ.call : delete R.prototype.call;
    }
    features.methods = on;
}

/**
 * Enables or disables native method overrides.
 * @private
 * @param {Boolean} on `true` to enable; `false` to disable.
 * @returns {undefined} N/A
 */
function setNatives (on) {
    R.prototype.exec = (on ? fixed : nativ).exec;
    R.prototype.test = (on ? fixed : nativ).test;
    S.prototype.match = (on ? fixed : nativ).match;
    S.prototype.replace = (on ? fixed : nativ).replace;
    S.prototype.split = (on ? fixed : nativ).split;
    features.natives = on;
}

/*--------------------------------------
 *  Expose XRegExp
 *------------------------------------*/

if (typeof exports === "undefined")
    root.XRegExp = X; // Create global varable
else // For CommonJS enviroments
    exports.XRegExp = X;

}(this);


/***** concatenate-source-files-fix.js *****/

// Ensure that XRegExp is defined in the module scope, so included addons are able to access it
if (typeof exports !== "undefined") {
    var XRegExp = exports.XRegExp;
}


/***** xregexp-unicode-base.js *****/

/*!
 * XRegExp Unicode Base v1.0.0-beta
 * Copyright 2008-2012 Steven Levithan <http://xregexp.com/>
 * Available under the MIT License
 * Uses Unicode 6.1 <http://unicode.org/Public/6.1.0/ucd/>
 */

/**
 * Adds support for the `\p{L}` or `\p{Letter}` Unicode category. Addon packages for the remaining
 * Unicode categories, scripts, and blocks are available separately. All Unicode tokens can be
 * inverted using `\P{..}` or `\p{^..}`. Token names are case insensitive, and any spaces, hyphens,
 * and underscores are ignored.
 */
;(function () {
"use strict";

var unicode = {}, // Storage for package tokens
    extensible = XRegExp.isInstalled("extensibility");

if (!extensible)
    XRegExp.install("extensibility"); // Temporarily install

XRegExp.addUnicodePackage = function (pack, aliases) {
    var p;
    if (!XRegExp.isInstalled("extensibility"))
        throw new Error("can't add Unicode package unless extensibility is installed");
    for (p in pack)
        pack.hasOwnProperty(p) && (unicode[slug(p)] = expand(pack[p]));
    if (aliases) {
        for (p in aliases)
            aliases.hasOwnProperty(p) && (unicode[slug(aliases[p])] = unicode[slug(p)]);
    }
};

XRegExp.addToken(
    /\\([pP]){(\^?)([^}]*)}/,
    function (match, scope) {
        var inv = (match[1] === "P" || match[2]) ? "^" : "",
            item = slug(match[3]);
        // \p{..}, \P{..}, and \p{^..} are valid, but the double negative \P{^..} isn't
        if (match[1] === "P" && match[2])
            throw new SyntaxError("erroneous characters: " + match[0]);
        if (!unicode.hasOwnProperty(item))
            throw new SyntaxError("invalid or unsupported Unicode item: " + match[0]);
        return scope === XRegExp.INSIDE_CLASS ?
            (inv ? cacheInversion(item) : unicode[item]) :
            "[" + inv + unicode[item] + "]";
    },
    XRegExp.INSIDE_CLASS | XRegExp.OUTSIDE_CLASS
);

XRegExp.addUnicodePackage({
    L: "0041-005A0061-007A00AA00B500BA00C0-00D600D8-00F600F8-02C102C6-02D102E0-02E402EC02EE0370-037403760377037A-037D03860388-038A038C038E-03A103A3-03F503F7-0481048A-05270531-055605590561-058705D0-05EA05F0-05F20620-064A066E066F0671-06D306D506E506E606EE06EF06FA-06FC06FF07100712-072F074D-07A507B107CA-07EA07F407F507FA0800-0815081A082408280840-085808A008A2-08AC0904-0939093D09500958-09610971-09770979-097F0985-098C098F09900993-09A809AA-09B009B209B6-09B909BD09CE09DC09DD09DF-09E109F009F10A05-0A0A0A0F0A100A13-0A280A2A-0A300A320A330A350A360A380A390A59-0A5C0A5E0A72-0A740A85-0A8D0A8F-0A910A93-0AA80AAA-0AB00AB20AB30AB5-0AB90ABD0AD00AE00AE10B05-0B0C0B0F0B100B13-0B280B2A-0B300B320B330B35-0B390B3D0B5C0B5D0B5F-0B610B710B830B85-0B8A0B8E-0B900B92-0B950B990B9A0B9C0B9E0B9F0BA30BA40BA8-0BAA0BAE-0BB90BD00C05-0C0C0C0E-0C100C12-0C280C2A-0C330C35-0C390C3D0C580C590C600C610C85-0C8C0C8E-0C900C92-0CA80CAA-0CB30CB5-0CB90CBD0CDE0CE00CE10CF10CF20D05-0D0C0D0E-0D100D12-0D3A0D3D0D4E0D600D610D7A-0D7F0D85-0D960D9A-0DB10DB3-0DBB0DBD0DC0-0DC60E01-0E300E320E330E40-0E460E810E820E840E870E880E8A0E8D0E94-0E970E99-0E9F0EA1-0EA30EA50EA70EAA0EAB0EAD-0EB00EB20EB30EBD0EC0-0EC40EC60EDC-0EDF0F000F40-0F470F49-0F6C0F88-0F8C1000-102A103F1050-1055105A-105D106110651066106E-10701075-1081108E10A0-10C510C710CD10D0-10FA10FC-1248124A-124D1250-12561258125A-125D1260-1288128A-128D1290-12B012B2-12B512B8-12BE12C012C2-12C512C8-12D612D8-13101312-13151318-135A1380-138F13A0-13F41401-166C166F-167F1681-169A16A0-16EA1700-170C170E-17111720-17311740-17511760-176C176E-17701780-17B317D717DC1820-18771880-18A818AA18B0-18F51900-191C1950-196D1970-19741980-19AB19C1-19C71A00-1A161A20-1A541AA71B05-1B331B45-1B4B1B83-1BA01BAE1BAF1BBA-1BE51C00-1C231C4D-1C4F1C5A-1C7D1CE9-1CEC1CEE-1CF11CF51CF61D00-1DBF1E00-1F151F18-1F1D1F20-1F451F48-1F4D1F50-1F571F591F5B1F5D1F5F-1F7D1F80-1FB41FB6-1FBC1FBE1FC2-1FC41FC6-1FCC1FD0-1FD31FD6-1FDB1FE0-1FEC1FF2-1FF41FF6-1FFC2071207F2090-209C21022107210A-211321152119-211D212421262128212A-212D212F-2139213C-213F2145-2149214E218321842C00-2C2E2C30-2C5E2C60-2CE42CEB-2CEE2CF22CF32D00-2D252D272D2D2D30-2D672D6F2D80-2D962DA0-2DA62DA8-2DAE2DB0-2DB62DB8-2DBE2DC0-2DC62DC8-2DCE2DD0-2DD62DD8-2DDE2E2F300530063031-3035303B303C3041-3096309D-309F30A1-30FA30FC-30FF3105-312D3131-318E31A0-31BA31F0-31FF3400-4DB54E00-9FCCA000-A48CA4D0-A4FDA500-A60CA610-A61FA62AA62BA640-A66EA67F-A697A6A0-A6E5A717-A71FA722-A788A78B-A78EA790-A793A7A0-A7AAA7F8-A801A803-A805A807-A80AA80C-A822A840-A873A882-A8B3A8F2-A8F7A8FBA90A-A925A930-A946A960-A97CA984-A9B2A9CFAA00-AA28AA40-AA42AA44-AA4BAA60-AA76AA7AAA80-AAAFAAB1AAB5AAB6AAB9-AABDAAC0AAC2AADB-AADDAAE0-AAEAAAF2-AAF4AB01-AB06AB09-AB0EAB11-AB16AB20-AB26AB28-AB2EABC0-ABE2AC00-D7A3D7B0-D7C6D7CB-D7FBF900-FA6DFA70-FAD9FB00-FB06FB13-FB17FB1DFB1F-FB28FB2A-FB36FB38-FB3CFB3EFB40FB41FB43FB44FB46-FBB1FBD3-FD3DFD50-FD8FFD92-FDC7FDF0-FDFBFE70-FE74FE76-FEFCFF21-FF3AFF41-FF5AFF66-FFBEFFC2-FFC7FFCA-FFCFFFD2-FFD7FFDA-FFDC"
},
{
    L: "Letter"
});

if (!extensible)
    XRegExp.uninstall("extensibility"); // Revert to previous state

// Generates a standardized token name (lowercase, with hyphens, spaces, and underscores removed)
function slug (name) {return name.replace(/[- _]+/g, "").toLowerCase();}

// Expands a list of Unicode code points and ranges to be usable in a regex character class
function expand (str) {return str.replace(/\w{4}/g, "\\u$&");}

// Converts a hexadecimal number to decimal
function dec (hex) {return parseInt(hex, 16);}

// Converts a decimal number to hexadecimal
function hex (dec) {return pad(parseInt(dec, 10).toString(16));}

// Adds leading zeros if shorter than four characters
function pad (str) {while (str.length < 4) str = "0" + str; return str;}

// Generates an inverted token on first use
function cacheInversion (item) {return unicode["^" + item] || (unicode["^" + item] = invert(unicode[item]));}

// Inverts a list of Unicode code points and ranges
function invert (range) {
    var output = [],
        lastEnd = -1,
        start;
    XRegExp.forEach(range, /\\u(\w{4})(?:-\\u(\w{4}))?/, function (m) {
        start = dec(m[1]);
        if (start > (lastEnd + 1)) {
            output.push("\\u" + pad(hex(lastEnd + 1)));
            (start > (lastEnd + 2)) && output.push("-\\u" + pad(hex(start - 1)));
        }
        lastEnd = dec(m[2] || m[1]);
    });
    if (lastEnd < 65535) {
        output.push("\\u" + pad(hex(lastEnd + 1)));
        (lastEnd < 65534) && output.push("-\\uFFFF");
    }
    return output.join("");
}

}());


/***** xregexp-unicode-blocks.js *****/

/*!
 * XRegExp Unicode Blocks v1.2.0-beta
 * Copyright 2010-2012 Steven Levithan <http://xregexp.com/>
 * Available under the MIT License
 * Uses Unicode 6.1 <http://unicode.org/Public/6.1.0/ucd/Blocks.txt>
 */

/**
 * Adds support for all Unicode blocks in the Basic Multilingual Plane (U+0000-U+FFFF). Unicode
 * blocks use the prefix "In". E.g., `\p{InBasicLatin}`. Token names are case insensitive, and any
 * spaces, hyphens, and underscores are ignored.
 */
;(function () {
"use strict";

if (typeof XRegExp === "undefined" || typeof XRegExp.addUnicodePackage === "undefined") {
    throw new ReferenceError("XRegExp Unicode Base must be loaded before Unicode Blocks");
}

var extensible = XRegExp.isInstalled("extensibility");

if (!extensible)
    XRegExp.install("extensibility"); // Temporarily install

XRegExp.addUnicodePackage({
    InBasic_Latin: "0000-007F",
    InLatin_1_Supplement: "0080-00FF",
    InLatin_Extended_A: "0100-017F",
    InLatin_Extended_B: "0180-024F",
    InIPA_Extensions: "0250-02AF",
    InSpacing_Modifier_Letters: "02B0-02FF",
    InCombining_Diacritical_Marks: "0300-036F",
    InGreek_and_Coptic: "0370-03FF",
    InCyrillic: "0400-04FF",
    InCyrillic_Supplement: "0500-052F",
    InArmenian: "0530-058F",
    InHebrew: "0590-05FF",
    InArabic: "0600-06FF",
    InSyriac: "0700-074F",
    InArabic_Supplement: "0750-077F",
    InThaana: "0780-07BF",
    InNKo: "07C0-07FF",
    InSamaritan: "0800-083F",
    InMandaic: "0840-085F",
    InArabic_Extended_A: "08A0-08FF",
    InDevanagari: "0900-097F",
    InBengali: "0980-09FF",
    InGurmukhi: "0A00-0A7F",
    InGujarati: "0A80-0AFF",
    InOriya: "0B00-0B7F",
    InTamil: "0B80-0BFF",
    InTelugu: "0C00-0C7F",
    InKannada: "0C80-0CFF",
    InMalayalam: "0D00-0D7F",
    InSinhala: "0D80-0DFF",
    InThai: "0E00-0E7F",
    InLao: "0E80-0EFF",
    InTibetan: "0F00-0FFF",
    InMyanmar: "1000-109F",
    InGeorgian: "10A0-10FF",
    InHangul_Jamo: "1100-11FF",
    InEthiopic: "1200-137F",
    InEthiopic_Supplement: "1380-139F",
    InCherokee: "13A0-13FF",
    InUnified_Canadian_Aboriginal_Syllabics: "1400-167F",
    InOgham: "1680-169F",
    InRunic: "16A0-16FF",
    InTagalog: "1700-171F",
    InHanunoo: "1720-173F",
    InBuhid: "1740-175F",
    InTagbanwa: "1760-177F",
    InKhmer: "1780-17FF",
    InMongolian: "1800-18AF",
    InUnified_Canadian_Aboriginal_Syllabics_Extended: "18B0-18FF",
    InLimbu: "1900-194F",
    InTai_Le: "1950-197F",
    InNew_Tai_Lue: "1980-19DF",
    InKhmer_Symbols: "19E0-19FF",
    InBuginese: "1A00-1A1F",
    InTai_Tham: "1A20-1AAF",
    InBalinese: "1B00-1B7F",
    InSundanese: "1B80-1BBF",
    InBatak: "1BC0-1BFF",
    InLepcha: "1C00-1C4F",
    InOl_Chiki: "1C50-1C7F",
    InSundanese_Supplement: "1CC0-1CCF",
    InVedic_Extensions: "1CD0-1CFF",
    InPhonetic_Extensions: "1D00-1D7F",
    InPhonetic_Extensions_Supplement: "1D80-1DBF",
    InCombining_Diacritical_Marks_Supplement: "1DC0-1DFF",
    InLatin_Extended_Additional: "1E00-1EFF",
    InGreek_Extended: "1F00-1FFF",
    InGeneral_Punctuation: "2000-206F",
    InSuperscripts_and_Subscripts: "2070-209F",
    InCurrency_Symbols: "20A0-20CF",
    InCombining_Diacritical_Marks_for_Symbols: "20D0-20FF",
    InLetterlike_Symbols: "2100-214F",
    InNumber_Forms: "2150-218F",
    InArrows: "2190-21FF",
    InMathematical_Operators: "2200-22FF",
    InMiscellaneous_Technical: "2300-23FF",
    InControl_Pictures: "2400-243F",
    InOptical_Character_Recognition: "2440-245F",
    InEnclosed_Alphanumerics: "2460-24FF",
    InBox_Drawing: "2500-257F",
    InBlock_Elements: "2580-259F",
    InGeometric_Shapes: "25A0-25FF",
    InMiscellaneous_Symbols: "2600-26FF",
    InDingbats: "2700-27BF",
    InMiscellaneous_Mathematical_Symbols_A: "27C0-27EF",
    InSupplemental_Arrows_A: "27F0-27FF",
    InBraille_Patterns: "2800-28FF",
    InSupplemental_Arrows_B: "2900-297F",
    InMiscellaneous_Mathematical_Symbols_B: "2980-29FF",
    InSupplemental_Mathematical_Operators: "2A00-2AFF",
    InMiscellaneous_Symbols_and_Arrows: "2B00-2BFF",
    InGlagolitic: "2C00-2C5F",
    InLatin_Extended_C: "2C60-2C7F",
    InCoptic: "2C80-2CFF",
    InGeorgian_Supplement: "2D00-2D2F",
    InTifinagh: "2D30-2D7F",
    InEthiopic_Extended: "2D80-2DDF",
    InCyrillic_Extended_A: "2DE0-2DFF",
    InSupplemental_Punctuation: "2E00-2E7F",
    InCJK_Radicals_Supplement: "2E80-2EFF",
    InKangxi_Radicals: "2F00-2FDF",
    InIdeographic_Description_Characters: "2FF0-2FFF",
    InCJK_Symbols_and_Punctuation: "3000-303F",
    InHiragana: "3040-309F",
    InKatakana: "30A0-30FF",
    InBopomofo: "3100-312F",
    InHangul_Compatibility_Jamo: "3130-318F",
    InKanbun: "3190-319F",
    InBopomofo_Extended: "31A0-31BF",
    InCJK_Strokes: "31C0-31EF",
    InKatakana_Phonetic_Extensions: "31F0-31FF",
    InEnclosed_CJK_Letters_and_Months: "3200-32FF",
    InCJK_Compatibility: "3300-33FF",
    InCJK_Unified_Ideographs_Extension_A: "3400-4DBF",
    InYijing_Hexagram_Symbols: "4DC0-4DFF",
    InCJK_Unified_Ideographs: "4E00-9FFF",
    InYi_Syllables: "A000-A48F",
    InYi_Radicals: "A490-A4CF",
    InLisu: "A4D0-A4FF",
    InVai: "A500-A63F",
    InCyrillic_Extended_B: "A640-A69F",
    InBamum: "A6A0-A6FF",
    InModifier_Tone_Letters: "A700-A71F",
    InLatin_Extended_D: "A720-A7FF",
    InSyloti_Nagri: "A800-A82F",
    InCommon_Indic_Number_Forms: "A830-A83F",
    InPhags_pa: "A840-A87F",
    InSaurashtra: "A880-A8DF",
    InDevanagari_Extended: "A8E0-A8FF",
    InKayah_Li: "A900-A92F",
    InRejang: "A930-A95F",
    InHangul_Jamo_Extended_A: "A960-A97F",
    InJavanese: "A980-A9DF",
    InCham: "AA00-AA5F",
    InMyanmar_Extended_A: "AA60-AA7F",
    InTai_Viet: "AA80-AADF",
    InMeetei_Mayek_Extensions: "AAE0-AAFF",
    InEthiopic_Extended_A: "AB00-AB2F",
    InMeetei_Mayek: "ABC0-ABFF",
    InHangul_Syllables: "AC00-D7AF",
    InHangul_Jamo_Extended_B: "D7B0-D7FF",
    InHigh_Surrogates: "D800-DB7F",
    InHigh_Private_Use_Surrogates: "DB80-DBFF",
    InLow_Surrogates: "DC00-DFFF",
    InPrivate_Use_Area: "E000-F8FF",
    InCJK_Compatibility_Ideographs: "F900-FAFF",
    InAlphabetic_Presentation_Forms: "FB00-FB4F",
    InArabic_Presentation_Forms_A: "FB50-FDFF",
    InVariation_Selectors: "FE00-FE0F",
    InVertical_Forms: "FE10-FE1F",
    InCombining_Half_Marks: "FE20-FE2F",
    InCJK_Compatibility_Forms: "FE30-FE4F",
    InSmall_Form_Variants: "FE50-FE6F",
    InArabic_Presentation_Forms_B: "FE70-FEFF",
    InHalfwidth_and_Fullwidth_Forms: "FF00-FFEF",
    InSpecials: "FFF0-FFFF"
});

if (!extensible)
    XRegExp.uninstall("extensibility"); // Revert to previous state

}());


/***** xregexp-unicode-categories.js *****/

/*!
 * XRegExp Unicode Categories v1.2.0-beta
 * Copyright 2010-2012 Steven Levithan <http://xregexp.com/>
 * Available under the MIT License
 * Uses Unicode 6.1 <http://unicode.org/Public/6.1.0/ucd/UnicodeData.txt>
 */

/**
 * Adds support for all Unicode categories (aka properties) E.g., `\p{Lu}` or
 * `\p{Uppercase Letter}`. Token names are case insensitive, and any spaces, hyphens, and
 * underscores are ignored.
 */
;(function () {
"use strict";

if (typeof XRegExp === "undefined" || typeof XRegExp.addUnicodePackage === "undefined") {
    throw new ReferenceError("XRegExp Unicode Base must be loaded before Unicode Categories");
}

var extensible = XRegExp.isInstalled("extensibility");

if (!extensible)
    XRegExp.install("extensibility"); // Temporarily install

XRegExp.addUnicodePackage({
    //L: "", // Included in the Unicode Base addon
    Ll: "0061-007A00B500DF-00F600F8-00FF01010103010501070109010B010D010F01110113011501170119011B011D011F01210123012501270129012B012D012F01310133013501370138013A013C013E014001420144014601480149014B014D014F01510153015501570159015B015D015F01610163016501670169016B016D016F0171017301750177017A017C017E-0180018301850188018C018D019201950199-019B019E01A101A301A501A801AA01AB01AD01B001B401B601B901BA01BD-01BF01C601C901CC01CE01D001D201D401D601D801DA01DC01DD01DF01E101E301E501E701E901EB01ED01EF01F001F301F501F901FB01FD01FF02010203020502070209020B020D020F02110213021502170219021B021D021F02210223022502270229022B022D022F02310233-0239023C023F0240024202470249024B024D024F-02930295-02AF037103730377037B-037D039003AC-03CE03D003D103D5-03D703D903DB03DD03DF03E103E303E503E703E903EB03ED03EF-03F303F503F803FB03FC0430-045F04610463046504670469046B046D046F04710473047504770479047B047D047F0481048B048D048F04910493049504970499049B049D049F04A104A304A504A704A904AB04AD04AF04B104B304B504B704B904BB04BD04BF04C204C404C604C804CA04CC04CE04CF04D104D304D504D704D904DB04DD04DF04E104E304E504E704E904EB04ED04EF04F104F304F504F704F904FB04FD04FF05010503050505070509050B050D050F05110513051505170519051B051D051F05210523052505270561-05871D00-1D2B1D6B-1D771D79-1D9A1E011E031E051E071E091E0B1E0D1E0F1E111E131E151E171E191E1B1E1D1E1F1E211E231E251E271E291E2B1E2D1E2F1E311E331E351E371E391E3B1E3D1E3F1E411E431E451E471E491E4B1E4D1E4F1E511E531E551E571E591E5B1E5D1E5F1E611E631E651E671E691E6B1E6D1E6F1E711E731E751E771E791E7B1E7D1E7F1E811E831E851E871E891E8B1E8D1E8F1E911E931E95-1E9D1E9F1EA11EA31EA51EA71EA91EAB1EAD1EAF1EB11EB31EB51EB71EB91EBB1EBD1EBF1EC11EC31EC51EC71EC91ECB1ECD1ECF1ED11ED31ED51ED71ED91EDB1EDD1EDF1EE11EE31EE51EE71EE91EEB1EED1EEF1EF11EF31EF51EF71EF91EFB1EFD1EFF-1F071F10-1F151F20-1F271F30-1F371F40-1F451F50-1F571F60-1F671F70-1F7D1F80-1F871F90-1F971FA0-1FA71FB0-1FB41FB61FB71FBE1FC2-1FC41FC61FC71FD0-1FD31FD61FD71FE0-1FE71FF2-1FF41FF61FF7210A210E210F2113212F21342139213C213D2146-2149214E21842C30-2C5E2C612C652C662C682C6A2C6C2C712C732C742C76-2C7B2C812C832C852C872C892C8B2C8D2C8F2C912C932C952C972C992C9B2C9D2C9F2CA12CA32CA52CA72CA92CAB2CAD2CAF2CB12CB32CB52CB72CB92CBB2CBD2CBF2CC12CC32CC52CC72CC92CCB2CCD2CCF2CD12CD32CD52CD72CD92CDB2CDD2CDF2CE12CE32CE42CEC2CEE2CF32D00-2D252D272D2DA641A643A645A647A649A64BA64DA64FA651A653A655A657A659A65BA65DA65FA661A663A665A667A669A66BA66DA681A683A685A687A689A68BA68DA68FA691A693A695A697A723A725A727A729A72BA72DA72F-A731A733A735A737A739A73BA73DA73FA741A743A745A747A749A74BA74DA74FA751A753A755A757A759A75BA75DA75FA761A763A765A767A769A76BA76DA76FA771-A778A77AA77CA77FA781A783A785A787A78CA78EA791A793A7A1A7A3A7A5A7A7A7A9A7FAFB00-FB06FB13-FB17FF41-FF5A",
    Lu: "0041-005A00C0-00D600D8-00DE01000102010401060108010A010C010E01100112011401160118011A011C011E01200122012401260128012A012C012E01300132013401360139013B013D013F0141014301450147014A014C014E01500152015401560158015A015C015E01600162016401660168016A016C016E017001720174017601780179017B017D018101820184018601870189-018B018E-0191019301940196-0198019C019D019F01A001A201A401A601A701A901AC01AE01AF01B1-01B301B501B701B801BC01C401C701CA01CD01CF01D101D301D501D701D901DB01DE01E001E201E401E601E801EA01EC01EE01F101F401F6-01F801FA01FC01FE02000202020402060208020A020C020E02100212021402160218021A021C021E02200222022402260228022A022C022E02300232023A023B023D023E02410243-02460248024A024C024E03700372037603860388-038A038C038E038F0391-03A103A3-03AB03CF03D2-03D403D803DA03DC03DE03E003E203E403E603E803EA03EC03EE03F403F703F903FA03FD-042F04600462046404660468046A046C046E04700472047404760478047A047C047E0480048A048C048E04900492049404960498049A049C049E04A004A204A404A604A804AA04AC04AE04B004B204B404B604B804BA04BC04BE04C004C104C304C504C704C904CB04CD04D004D204D404D604D804DA04DC04DE04E004E204E404E604E804EA04EC04EE04F004F204F404F604F804FA04FC04FE05000502050405060508050A050C050E05100512051405160518051A051C051E05200522052405260531-055610A0-10C510C710CD1E001E021E041E061E081E0A1E0C1E0E1E101E121E141E161E181E1A1E1C1E1E1E201E221E241E261E281E2A1E2C1E2E1E301E321E341E361E381E3A1E3C1E3E1E401E421E441E461E481E4A1E4C1E4E1E501E521E541E561E581E5A1E5C1E5E1E601E621E641E661E681E6A1E6C1E6E1E701E721E741E761E781E7A1E7C1E7E1E801E821E841E861E881E8A1E8C1E8E1E901E921E941E9E1EA01EA21EA41EA61EA81EAA1EAC1EAE1EB01EB21EB41EB61EB81EBA1EBC1EBE1EC01EC21EC41EC61EC81ECA1ECC1ECE1ED01ED21ED41ED61ED81EDA1EDC1EDE1EE01EE21EE41EE61EE81EEA1EEC1EEE1EF01EF21EF41EF61EF81EFA1EFC1EFE1F08-1F0F1F18-1F1D1F28-1F2F1F38-1F3F1F48-1F4D1F591F5B1F5D1F5F1F68-1F6F1FB8-1FBB1FC8-1FCB1FD8-1FDB1FE8-1FEC1FF8-1FFB21022107210B-210D2110-211221152119-211D212421262128212A-212D2130-2133213E213F214521832C00-2C2E2C602C62-2C642C672C692C6B2C6D-2C702C722C752C7E-2C802C822C842C862C882C8A2C8C2C8E2C902C922C942C962C982C9A2C9C2C9E2CA02CA22CA42CA62CA82CAA2CAC2CAE2CB02CB22CB42CB62CB82CBA2CBC2CBE2CC02CC22CC42CC62CC82CCA2CCC2CCE2CD02CD22CD42CD62CD82CDA2CDC2CDE2CE02CE22CEB2CED2CF2A640A642A644A646A648A64AA64CA64EA650A652A654A656A658A65AA65CA65EA660A662A664A666A668A66AA66CA680A682A684A686A688A68AA68CA68EA690A692A694A696A722A724A726A728A72AA72CA72EA732A734A736A738A73AA73CA73EA740A742A744A746A748A74AA74CA74EA750A752A754A756A758A75AA75CA75EA760A762A764A766A768A76AA76CA76EA779A77BA77DA77EA780A782A784A786A78BA78DA790A792A7A0A7A2A7A4A7A6A7A8A7AAFF21-FF3A",
    Lt: "01C501C801CB01F21F88-1F8F1F98-1F9F1FA8-1FAF1FBC1FCC1FFC",
    Lm: "02B0-02C102C6-02D102E0-02E402EC02EE0374037A0559064006E506E607F407F507FA081A0824082809710E460EC610FC17D718431AA71C78-1C7D1D2C-1D6A1D781D9B-1DBF2071207F2090-209C2C7C2C7D2D6F2E2F30053031-3035303B309D309E30FC-30FEA015A4F8-A4FDA60CA67FA717-A71FA770A788A7F8A7F9A9CFAA70AADDAAF3AAF4FF70FF9EFF9F",
    Lo: "00AA00BA01BB01C0-01C3029405D0-05EA05F0-05F20620-063F0641-064A066E066F0671-06D306D506EE06EF06FA-06FC06FF07100712-072F074D-07A507B107CA-07EA0800-08150840-085808A008A2-08AC0904-0939093D09500958-09610972-09770979-097F0985-098C098F09900993-09A809AA-09B009B209B6-09B909BD09CE09DC09DD09DF-09E109F009F10A05-0A0A0A0F0A100A13-0A280A2A-0A300A320A330A350A360A380A390A59-0A5C0A5E0A72-0A740A85-0A8D0A8F-0A910A93-0AA80AAA-0AB00AB20AB30AB5-0AB90ABD0AD00AE00AE10B05-0B0C0B0F0B100B13-0B280B2A-0B300B320B330B35-0B390B3D0B5C0B5D0B5F-0B610B710B830B85-0B8A0B8E-0B900B92-0B950B990B9A0B9C0B9E0B9F0BA30BA40BA8-0BAA0BAE-0BB90BD00C05-0C0C0C0E-0C100C12-0C280C2A-0C330C35-0C390C3D0C580C590C600C610C85-0C8C0C8E-0C900C92-0CA80CAA-0CB30CB5-0CB90CBD0CDE0CE00CE10CF10CF20D05-0D0C0D0E-0D100D12-0D3A0D3D0D4E0D600D610D7A-0D7F0D85-0D960D9A-0DB10DB3-0DBB0DBD0DC0-0DC60E01-0E300E320E330E40-0E450E810E820E840E870E880E8A0E8D0E94-0E970E99-0E9F0EA1-0EA30EA50EA70EAA0EAB0EAD-0EB00EB20EB30EBD0EC0-0EC40EDC-0EDF0F000F40-0F470F49-0F6C0F88-0F8C1000-102A103F1050-1055105A-105D106110651066106E-10701075-1081108E10D0-10FA10FD-1248124A-124D1250-12561258125A-125D1260-1288128A-128D1290-12B012B2-12B512B8-12BE12C012C2-12C512C8-12D612D8-13101312-13151318-135A1380-138F13A0-13F41401-166C166F-167F1681-169A16A0-16EA1700-170C170E-17111720-17311740-17511760-176C176E-17701780-17B317DC1820-18421844-18771880-18A818AA18B0-18F51900-191C1950-196D1970-19741980-19AB19C1-19C71A00-1A161A20-1A541B05-1B331B45-1B4B1B83-1BA01BAE1BAF1BBA-1BE51C00-1C231C4D-1C4F1C5A-1C771CE9-1CEC1CEE-1CF11CF51CF62135-21382D30-2D672D80-2D962DA0-2DA62DA8-2DAE2DB0-2DB62DB8-2DBE2DC0-2DC62DC8-2DCE2DD0-2DD62DD8-2DDE3006303C3041-3096309F30A1-30FA30FF3105-312D3131-318E31A0-31BA31F0-31FF3400-4DB54E00-9FCCA000-A014A016-A48CA4D0-A4F7A500-A60BA610-A61FA62AA62BA66EA6A0-A6E5A7FB-A801A803-A805A807-A80AA80C-A822A840-A873A882-A8B3A8F2-A8F7A8FBA90A-A925A930-A946A960-A97CA984-A9B2AA00-AA28AA40-AA42AA44-AA4BAA60-AA6FAA71-AA76AA7AAA80-AAAFAAB1AAB5AAB6AAB9-AABDAAC0AAC2AADBAADCAAE0-AAEAAAF2AB01-AB06AB09-AB0EAB11-AB16AB20-AB26AB28-AB2EABC0-ABE2AC00-D7A3D7B0-D7C6D7CB-D7FBF900-FA6DFA70-FAD9FB1DFB1F-FB28FB2A-FB36FB38-FB3CFB3EFB40FB41FB43FB44FB46-FBB1FBD3-FD3DFD50-FD8FFD92-FDC7FDF0-FDFBFE70-FE74FE76-FEFCFF66-FF6FFF71-FF9DFFA0-FFBEFFC2-FFC7FFCA-FFCFFFD2-FFD7FFDA-FFDC",
    M: "0300-036F0483-04890591-05BD05BF05C105C205C405C505C70610-061A064B-065F067006D6-06DC06DF-06E406E706E806EA-06ED07110730-074A07A6-07B007EB-07F30816-0819081B-08230825-08270829-082D0859-085B08E4-08FE0900-0903093A-093C093E-094F0951-0957096209630981-098309BC09BE-09C409C709C809CB-09CD09D709E209E30A01-0A030A3C0A3E-0A420A470A480A4B-0A4D0A510A700A710A750A81-0A830ABC0ABE-0AC50AC7-0AC90ACB-0ACD0AE20AE30B01-0B030B3C0B3E-0B440B470B480B4B-0B4D0B560B570B620B630B820BBE-0BC20BC6-0BC80BCA-0BCD0BD70C01-0C030C3E-0C440C46-0C480C4A-0C4D0C550C560C620C630C820C830CBC0CBE-0CC40CC6-0CC80CCA-0CCD0CD50CD60CE20CE30D020D030D3E-0D440D46-0D480D4A-0D4D0D570D620D630D820D830DCA0DCF-0DD40DD60DD8-0DDF0DF20DF30E310E34-0E3A0E47-0E4E0EB10EB4-0EB90EBB0EBC0EC8-0ECD0F180F190F350F370F390F3E0F3F0F71-0F840F860F870F8D-0F970F99-0FBC0FC6102B-103E1056-1059105E-10601062-10641067-106D1071-10741082-108D108F109A-109D135D-135F1712-17141732-1734175217531772177317B4-17D317DD180B-180D18A91920-192B1930-193B19B0-19C019C819C91A17-1A1B1A55-1A5E1A60-1A7C1A7F1B00-1B041B34-1B441B6B-1B731B80-1B821BA1-1BAD1BE6-1BF31C24-1C371CD0-1CD21CD4-1CE81CED1CF2-1CF41DC0-1DE61DFC-1DFF20D0-20F02CEF-2CF12D7F2DE0-2DFF302A-302F3099309AA66F-A672A674-A67DA69FA6F0A6F1A802A806A80BA823-A827A880A881A8B4-A8C4A8E0-A8F1A926-A92DA947-A953A980-A983A9B3-A9C0AA29-AA36AA43AA4CAA4DAA7BAAB0AAB2-AAB4AAB7AAB8AABEAABFAAC1AAEB-AAEFAAF5AAF6ABE3-ABEAABECABEDFB1EFE00-FE0FFE20-FE26",
    Mn: "0300-036F0483-04870591-05BD05BF05C105C205C405C505C70610-061A064B-065F067006D6-06DC06DF-06E406E706E806EA-06ED07110730-074A07A6-07B007EB-07F30816-0819081B-08230825-08270829-082D0859-085B08E4-08FE0900-0902093A093C0941-0948094D0951-095709620963098109BC09C1-09C409CD09E209E30A010A020A3C0A410A420A470A480A4B-0A4D0A510A700A710A750A810A820ABC0AC1-0AC50AC70AC80ACD0AE20AE30B010B3C0B3F0B41-0B440B4D0B560B620B630B820BC00BCD0C3E-0C400C46-0C480C4A-0C4D0C550C560C620C630CBC0CBF0CC60CCC0CCD0CE20CE30D41-0D440D4D0D620D630DCA0DD2-0DD40DD60E310E34-0E3A0E47-0E4E0EB10EB4-0EB90EBB0EBC0EC8-0ECD0F180F190F350F370F390F71-0F7E0F80-0F840F860F870F8D-0F970F99-0FBC0FC6102D-10301032-10371039103A103D103E10581059105E-10601071-1074108210851086108D109D135D-135F1712-17141732-1734175217531772177317B417B517B7-17BD17C617C9-17D317DD180B-180D18A91920-19221927192819321939-193B1A171A181A561A58-1A5E1A601A621A65-1A6C1A73-1A7C1A7F1B00-1B031B341B36-1B3A1B3C1B421B6B-1B731B801B811BA2-1BA51BA81BA91BAB1BE61BE81BE91BED1BEF-1BF11C2C-1C331C361C371CD0-1CD21CD4-1CE01CE2-1CE81CED1CF41DC0-1DE61DFC-1DFF20D0-20DC20E120E5-20F02CEF-2CF12D7F2DE0-2DFF302A-302D3099309AA66FA674-A67DA69FA6F0A6F1A802A806A80BA825A826A8C4A8E0-A8F1A926-A92DA947-A951A980-A982A9B3A9B6-A9B9A9BCAA29-AA2EAA31AA32AA35AA36AA43AA4CAAB0AAB2-AAB4AAB7AAB8AABEAABFAAC1AAECAAEDAAF6ABE5ABE8ABEDFB1EFE00-FE0FFE20-FE26",
    Mc: "0903093B093E-09400949-094C094E094F0982098309BE-09C009C709C809CB09CC09D70A030A3E-0A400A830ABE-0AC00AC90ACB0ACC0B020B030B3E0B400B470B480B4B0B4C0B570BBE0BBF0BC10BC20BC6-0BC80BCA-0BCC0BD70C01-0C030C41-0C440C820C830CBE0CC0-0CC40CC70CC80CCA0CCB0CD50CD60D020D030D3E-0D400D46-0D480D4A-0D4C0D570D820D830DCF-0DD10DD8-0DDF0DF20DF30F3E0F3F0F7F102B102C10311038103B103C105610571062-10641067-106D108310841087-108C108F109A-109C17B617BE-17C517C717C81923-19261929-192B193019311933-193819B0-19C019C819C91A19-1A1B1A551A571A611A631A641A6D-1A721B041B351B3B1B3D-1B411B431B441B821BA11BA61BA71BAA1BAC1BAD1BE71BEA-1BEC1BEE1BF21BF31C24-1C2B1C341C351CE11CF21CF3302E302FA823A824A827A880A881A8B4-A8C3A952A953A983A9B4A9B5A9BAA9BBA9BD-A9C0AA2FAA30AA33AA34AA4DAA7BAAEBAAEEAAEFAAF5ABE3ABE4ABE6ABE7ABE9ABEAABEC",
    Me: "0488048920DD-20E020E2-20E4A670-A672",
    N: "0030-003900B200B300B900BC-00BE0660-066906F0-06F907C0-07C90966-096F09E6-09EF09F4-09F90A66-0A6F0AE6-0AEF0B66-0B6F0B72-0B770BE6-0BF20C66-0C6F0C78-0C7E0CE6-0CEF0D66-0D750E50-0E590ED0-0ED90F20-0F331040-10491090-10991369-137C16EE-16F017E0-17E917F0-17F91810-18191946-194F19D0-19DA1A80-1A891A90-1A991B50-1B591BB0-1BB91C40-1C491C50-1C5920702074-20792080-20892150-21822185-21892460-249B24EA-24FF2776-27932CFD30073021-30293038-303A3192-31953220-32293248-324F3251-325F3280-328932B1-32BFA620-A629A6E6-A6EFA830-A835A8D0-A8D9A900-A909A9D0-A9D9AA50-AA59ABF0-ABF9FF10-FF19",
    Nd: "0030-00390660-066906F0-06F907C0-07C90966-096F09E6-09EF0A66-0A6F0AE6-0AEF0B66-0B6F0BE6-0BEF0C66-0C6F0CE6-0CEF0D66-0D6F0E50-0E590ED0-0ED90F20-0F291040-10491090-109917E0-17E91810-18191946-194F19D0-19D91A80-1A891A90-1A991B50-1B591BB0-1BB91C40-1C491C50-1C59A620-A629A8D0-A8D9A900-A909A9D0-A9D9AA50-AA59ABF0-ABF9FF10-FF19",
    Nl: "16EE-16F02160-21822185-218830073021-30293038-303AA6E6-A6EF",
    No: "00B200B300B900BC-00BE09F4-09F90B72-0B770BF0-0BF20C78-0C7E0D70-0D750F2A-0F331369-137C17F0-17F919DA20702074-20792080-20892150-215F21892460-249B24EA-24FF2776-27932CFD3192-31953220-32293248-324F3251-325F3280-328932B1-32BFA830-A835",
    P: "0021-00230025-002A002C-002F003A003B003F0040005B-005D005F007B007D00A100A700AB00B600B700BB00BF037E0387055A-055F0589058A05BE05C005C305C605F305F40609060A060C060D061B061E061F066A-066D06D40700-070D07F7-07F90830-083E085E0964096509700AF00DF40E4F0E5A0E5B0F04-0F120F140F3A-0F3D0F850FD0-0FD40FD90FDA104A-104F10FB1360-13681400166D166E169B169C16EB-16ED1735173617D4-17D617D8-17DA1800-180A194419451A1E1A1F1AA0-1AA61AA8-1AAD1B5A-1B601BFC-1BFF1C3B-1C3F1C7E1C7F1CC0-1CC71CD32010-20272030-20432045-20512053-205E207D207E208D208E2329232A2768-277527C527C627E6-27EF2983-299829D8-29DB29FC29FD2CF9-2CFC2CFE2CFF2D702E00-2E2E2E30-2E3B3001-30033008-30113014-301F3030303D30A030FBA4FEA4FFA60D-A60FA673A67EA6F2-A6F7A874-A877A8CEA8CFA8F8-A8FAA92EA92FA95FA9C1-A9CDA9DEA9DFAA5C-AA5FAADEAADFAAF0AAF1ABEBFD3EFD3FFE10-FE19FE30-FE52FE54-FE61FE63FE68FE6AFE6BFF01-FF03FF05-FF0AFF0C-FF0FFF1AFF1BFF1FFF20FF3B-FF3DFF3FFF5BFF5DFF5F-FF65",
    Pd: "002D058A05BE140018062010-20152E172E1A2E3A2E3B301C303030A0FE31FE32FE58FE63FF0D",
    Ps: "0028005B007B0F3A0F3C169B201A201E2045207D208D23292768276A276C276E27702772277427C527E627E827EA27EC27EE2983298529872989298B298D298F299129932995299729D829DA29FC2E222E242E262E283008300A300C300E3010301430163018301A301DFD3EFE17FE35FE37FE39FE3BFE3DFE3FFE41FE43FE47FE59FE5BFE5DFF08FF3BFF5BFF5FFF62",
    Pe: "0029005D007D0F3B0F3D169C2046207E208E232A2769276B276D276F27712773277527C627E727E927EB27ED27EF298429862988298A298C298E2990299229942996299829D929DB29FD2E232E252E272E293009300B300D300F3011301530173019301B301E301FFD3FFE18FE36FE38FE3AFE3CFE3EFE40FE42FE44FE48FE5AFE5CFE5EFF09FF3DFF5DFF60FF63",
    Pi: "00AB2018201B201C201F20392E022E042E092E0C2E1C2E20",
    Pf: "00BB2019201D203A2E032E052E0A2E0D2E1D2E21",
    Pc: "005F203F20402054FE33FE34FE4D-FE4FFF3F",
    Po: "0021-00230025-0027002A002C002E002F003A003B003F0040005C00A100A700B600B700BF037E0387055A-055F058905C005C305C605F305F40609060A060C060D061B061E061F066A-066D06D40700-070D07F7-07F90830-083E085E0964096509700AF00DF40E4F0E5A0E5B0F04-0F120F140F850FD0-0FD40FD90FDA104A-104F10FB1360-1368166D166E16EB-16ED1735173617D4-17D617D8-17DA1800-18051807-180A194419451A1E1A1F1AA0-1AA61AA8-1AAD1B5A-1B601BFC-1BFF1C3B-1C3F1C7E1C7F1CC0-1CC71CD3201620172020-20272030-2038203B-203E2041-20432047-205120532055-205E2CF9-2CFC2CFE2CFF2D702E002E012E06-2E082E0B2E0E-2E162E182E192E1B2E1E2E1F2E2A-2E2E2E30-2E393001-3003303D30FBA4FEA4FFA60D-A60FA673A67EA6F2-A6F7A874-A877A8CEA8CFA8F8-A8FAA92EA92FA95FA9C1-A9CDA9DEA9DFAA5C-AA5FAADEAADFAAF0AAF1ABEBFE10-FE16FE19FE30FE45FE46FE49-FE4CFE50-FE52FE54-FE57FE5F-FE61FE68FE6AFE6BFF01-FF03FF05-FF07FF0AFF0CFF0EFF0FFF1AFF1BFF1FFF20FF3CFF61FF64FF65",
    S: "0024002B003C-003E005E0060007C007E00A2-00A600A800A900AC00AE-00B100B400B800D700F702C2-02C502D2-02DF02E5-02EB02ED02EF-02FF03750384038503F60482058F0606-0608060B060E060F06DE06E906FD06FE07F609F209F309FA09FB0AF10B700BF3-0BFA0C7F0D790E3F0F01-0F030F130F15-0F170F1A-0F1F0F340F360F380FBE-0FC50FC7-0FCC0FCE0FCF0FD5-0FD8109E109F1390-139917DB194019DE-19FF1B61-1B6A1B74-1B7C1FBD1FBF-1FC11FCD-1FCF1FDD-1FDF1FED-1FEF1FFD1FFE20442052207A-207C208A-208C20A0-20B9210021012103-21062108210921142116-2118211E-2123212521272129212E213A213B2140-2144214A-214D214F2190-2328232B-23F32400-24262440-244A249C-24E92500-26FF2701-27672794-27C427C7-27E527F0-29822999-29D729DC-29FB29FE-2B4C2B50-2B592CE5-2CEA2E80-2E992E9B-2EF32F00-2FD52FF0-2FFB300430123013302030363037303E303F309B309C319031913196-319F31C0-31E33200-321E322A-324732503260-327F328A-32B032C0-32FE3300-33FF4DC0-4DFFA490-A4C6A700-A716A720A721A789A78AA828-A82BA836-A839AA77-AA79FB29FBB2-FBC1FDFCFDFDFE62FE64-FE66FE69FF04FF0BFF1C-FF1EFF3EFF40FF5CFF5EFFE0-FFE6FFE8-FFEEFFFCFFFD",
    Sm: "002B003C-003E007C007E00AC00B100D700F703F60606-060820442052207A-207C208A-208C21182140-2144214B2190-2194219A219B21A021A321A621AE21CE21CF21D221D421F4-22FF2308-230B23202321237C239B-23B323DC-23E125B725C125F8-25FF266F27C0-27C427C7-27E527F0-27FF2900-29822999-29D729DC-29FB29FE-2AFF2B30-2B442B47-2B4CFB29FE62FE64-FE66FF0BFF1C-FF1EFF5CFF5EFFE2FFE9-FFEC",
    Sc: "002400A2-00A5058F060B09F209F309FB0AF10BF90E3F17DB20A0-20B9A838FDFCFE69FF04FFE0FFE1FFE5FFE6",
    Sk: "005E006000A800AF00B400B802C2-02C502D2-02DF02E5-02EB02ED02EF-02FF0375038403851FBD1FBF-1FC11FCD-1FCF1FDD-1FDF1FED-1FEF1FFD1FFE309B309CA700-A716A720A721A789A78AFBB2-FBC1FF3EFF40FFE3",
    So: "00A600A900AE00B00482060E060F06DE06E906FD06FE07F609FA0B700BF3-0BF80BFA0C7F0D790F01-0F030F130F15-0F170F1A-0F1F0F340F360F380FBE-0FC50FC7-0FCC0FCE0FCF0FD5-0FD8109E109F1390-1399194019DE-19FF1B61-1B6A1B74-1B7C210021012103-210621082109211421162117211E-2123212521272129212E213A213B214A214C214D214F2195-2199219C-219F21A121A221A421A521A7-21AD21AF-21CD21D021D121D321D5-21F32300-2307230C-231F2322-2328232B-237B237D-239A23B4-23DB23E2-23F32400-24262440-244A249C-24E92500-25B625B8-25C025C2-25F72600-266E2670-26FF2701-27672794-27BF2800-28FF2B00-2B2F2B452B462B50-2B592CE5-2CEA2E80-2E992E9B-2EF32F00-2FD52FF0-2FFB300430123013302030363037303E303F319031913196-319F31C0-31E33200-321E322A-324732503260-327F328A-32B032C0-32FE3300-33FF4DC0-4DFFA490-A4C6A828-A82BA836A837A839AA77-AA79FDFDFFE4FFE8FFEDFFEEFFFCFFFD",
    Z: "002000A01680180E2000-200A20282029202F205F3000",
    Zs: "002000A01680180E2000-200A202F205F3000",
    Zl: "2028",
    Zp: "2029",
    C: "0000-001F007F-009F00AD03780379037F-0383038B038D03A20528-05300557055805600588058B-058E059005C8-05CF05EB-05EF05F5-0605061C061D06DD070E070F074B074C07B2-07BF07FB-07FF082E082F083F085C085D085F-089F08A108AD-08E308FF097809800984098D098E0991099209A909B109B3-09B509BA09BB09C509C609C909CA09CF-09D609D8-09DB09DE09E409E509FC-0A000A040A0B-0A0E0A110A120A290A310A340A370A3A0A3B0A3D0A43-0A460A490A4A0A4E-0A500A52-0A580A5D0A5F-0A650A76-0A800A840A8E0A920AA90AB10AB40ABA0ABB0AC60ACA0ACE0ACF0AD1-0ADF0AE40AE50AF2-0B000B040B0D0B0E0B110B120B290B310B340B3A0B3B0B450B460B490B4A0B4E-0B550B58-0B5B0B5E0B640B650B78-0B810B840B8B-0B8D0B910B96-0B980B9B0B9D0BA0-0BA20BA5-0BA70BAB-0BAD0BBA-0BBD0BC3-0BC50BC90BCE0BCF0BD1-0BD60BD8-0BE50BFB-0C000C040C0D0C110C290C340C3A-0C3C0C450C490C4E-0C540C570C5A-0C5F0C640C650C70-0C770C800C810C840C8D0C910CA90CB40CBA0CBB0CC50CC90CCE-0CD40CD7-0CDD0CDF0CE40CE50CF00CF3-0D010D040D0D0D110D3B0D3C0D450D490D4F-0D560D58-0D5F0D640D650D76-0D780D800D810D840D97-0D990DB20DBC0DBE0DBF0DC7-0DC90DCB-0DCE0DD50DD70DE0-0DF10DF5-0E000E3B-0E3E0E5C-0E800E830E850E860E890E8B0E8C0E8E-0E930E980EA00EA40EA60EA80EA90EAC0EBA0EBE0EBF0EC50EC70ECE0ECF0EDA0EDB0EE0-0EFF0F480F6D-0F700F980FBD0FCD0FDB-0FFF10C610C8-10CC10CE10CF1249124E124F12571259125E125F1289128E128F12B112B612B712BF12C112C612C712D7131113161317135B135C137D-137F139A-139F13F5-13FF169D-169F16F1-16FF170D1715-171F1737-173F1754-175F176D17711774-177F17DE17DF17EA-17EF17FA-17FF180F181A-181F1878-187F18AB-18AF18F6-18FF191D-191F192C-192F193C-193F1941-1943196E196F1975-197F19AC-19AF19CA-19CF19DB-19DD1A1C1A1D1A5F1A7D1A7E1A8A-1A8F1A9A-1A9F1AAE-1AFF1B4C-1B4F1B7D-1B7F1BF4-1BFB1C38-1C3A1C4A-1C4C1C80-1CBF1CC8-1CCF1CF7-1CFF1DE7-1DFB1F161F171F1E1F1F1F461F471F4E1F4F1F581F5A1F5C1F5E1F7E1F7F1FB51FC51FD41FD51FDC1FF01FF11FF51FFF200B-200F202A-202E2060-206F20722073208F209D-209F20BA-20CF20F1-20FF218A-218F23F4-23FF2427-243F244B-245F27002B4D-2B4F2B5A-2BFF2C2F2C5F2CF4-2CF82D262D28-2D2C2D2E2D2F2D68-2D6E2D71-2D7E2D97-2D9F2DA72DAF2DB72DBF2DC72DCF2DD72DDF2E3C-2E7F2E9A2EF4-2EFF2FD6-2FEF2FFC-2FFF3040309730983100-3104312E-3130318F31BB-31BF31E4-31EF321F32FF4DB6-4DBF9FCD-9FFFA48D-A48FA4C7-A4CFA62C-A63FA698-A69EA6F8-A6FFA78FA794-A79FA7AB-A7F7A82C-A82FA83A-A83FA878-A87FA8C5-A8CDA8DA-A8DFA8FC-A8FFA954-A95EA97D-A97FA9CEA9DA-A9DDA9E0-A9FFAA37-AA3FAA4EAA4FAA5AAA5BAA7C-AA7FAAC3-AADAAAF7-AB00AB07AB08AB0FAB10AB17-AB1FAB27AB2F-ABBFABEEABEFABFA-ABFFD7A4-D7AFD7C7-D7CAD7FC-F8FFFA6EFA6FFADA-FAFFFB07-FB12FB18-FB1CFB37FB3DFB3FFB42FB45FBC2-FBD2FD40-FD4FFD90FD91FDC8-FDEFFDFEFDFFFE1A-FE1FFE27-FE2FFE53FE67FE6C-FE6FFE75FEFD-FF00FFBF-FFC1FFC8FFC9FFD0FFD1FFD8FFD9FFDD-FFDFFFE7FFEF-FFFBFFFEFFFF",
    Cc: "0000-001F007F-009F",
    Cf: "00AD0600-060406DD070F200B-200F202A-202E2060-2064206A-206FFEFFFFF9-FFFB",
    Co: "E000-F8FF",
    Cs: "D800-DFFF",
    Cn: "03780379037F-0383038B038D03A20528-05300557055805600588058B-058E059005C8-05CF05EB-05EF05F5-05FF0605061C061D070E074B074C07B2-07BF07FB-07FF082E082F083F085C085D085F-089F08A108AD-08E308FF097809800984098D098E0991099209A909B109B3-09B509BA09BB09C509C609C909CA09CF-09D609D8-09DB09DE09E409E509FC-0A000A040A0B-0A0E0A110A120A290A310A340A370A3A0A3B0A3D0A43-0A460A490A4A0A4E-0A500A52-0A580A5D0A5F-0A650A76-0A800A840A8E0A920AA90AB10AB40ABA0ABB0AC60ACA0ACE0ACF0AD1-0ADF0AE40AE50AF2-0B000B040B0D0B0E0B110B120B290B310B340B3A0B3B0B450B460B490B4A0B4E-0B550B58-0B5B0B5E0B640B650B78-0B810B840B8B-0B8D0B910B96-0B980B9B0B9D0BA0-0BA20BA5-0BA70BAB-0BAD0BBA-0BBD0BC3-0BC50BC90BCE0BCF0BD1-0BD60BD8-0BE50BFB-0C000C040C0D0C110C290C340C3A-0C3C0C450C490C4E-0C540C570C5A-0C5F0C640C650C70-0C770C800C810C840C8D0C910CA90CB40CBA0CBB0CC50CC90CCE-0CD40CD7-0CDD0CDF0CE40CE50CF00CF3-0D010D040D0D0D110D3B0D3C0D450D490D4F-0D560D58-0D5F0D640D650D76-0D780D800D810D840D97-0D990DB20DBC0DBE0DBF0DC7-0DC90DCB-0DCE0DD50DD70DE0-0DF10DF5-0E000E3B-0E3E0E5C-0E800E830E850E860E890E8B0E8C0E8E-0E930E980EA00EA40EA60EA80EA90EAC0EBA0EBE0EBF0EC50EC70ECE0ECF0EDA0EDB0EE0-0EFF0F480F6D-0F700F980FBD0FCD0FDB-0FFF10C610C8-10CC10CE10CF1249124E124F12571259125E125F1289128E128F12B112B612B712BF12C112C612C712D7131113161317135B135C137D-137F139A-139F13F5-13FF169D-169F16F1-16FF170D1715-171F1737-173F1754-175F176D17711774-177F17DE17DF17EA-17EF17FA-17FF180F181A-181F1878-187F18AB-18AF18F6-18FF191D-191F192C-192F193C-193F1941-1943196E196F1975-197F19AC-19AF19CA-19CF19DB-19DD1A1C1A1D1A5F1A7D1A7E1A8A-1A8F1A9A-1A9F1AAE-1AFF1B4C-1B4F1B7D-1B7F1BF4-1BFB1C38-1C3A1C4A-1C4C1C80-1CBF1CC8-1CCF1CF7-1CFF1DE7-1DFB1F161F171F1E1F1F1F461F471F4E1F4F1F581F5A1F5C1F5E1F7E1F7F1FB51FC51FD41FD51FDC1FF01FF11FF51FFF2065-206920722073208F209D-209F20BA-20CF20F1-20FF218A-218F23F4-23FF2427-243F244B-245F27002B4D-2B4F2B5A-2BFF2C2F2C5F2CF4-2CF82D262D28-2D2C2D2E2D2F2D68-2D6E2D71-2D7E2D97-2D9F2DA72DAF2DB72DBF2DC72DCF2DD72DDF2E3C-2E7F2E9A2EF4-2EFF2FD6-2FEF2FFC-2FFF3040309730983100-3104312E-3130318F31BB-31BF31E4-31EF321F32FF4DB6-4DBF9FCD-9FFFA48D-A48FA4C7-A4CFA62C-A63FA698-A69EA6F8-A6FFA78FA794-A79FA7AB-A7F7A82C-A82FA83A-A83FA878-A87FA8C5-A8CDA8DA-A8DFA8FC-A8FFA954-A95EA97D-A97FA9CEA9DA-A9DDA9E0-A9FFAA37-AA3FAA4EAA4FAA5AAA5BAA7C-AA7FAAC3-AADAAAF7-AB00AB07AB08AB0FAB10AB17-AB1FAB27AB2F-ABBFABEEABEFABFA-ABFFD7A4-D7AFD7C7-D7CAD7FC-D7FFFA6EFA6FFADA-FAFFFB07-FB12FB18-FB1CFB37FB3DFB3FFB42FB45FBC2-FBD2FD40-FD4FFD90FD91FDC8-FDEFFDFEFDFFFE1A-FE1FFE27-FE2FFE53FE67FE6C-FE6FFE75FEFDFEFEFF00FFBF-FFC1FFC8FFC9FFD0FFD1FFD8FFD9FFDD-FFDFFFE7FFEF-FFF8FFFEFFFF"
},
{
    //L: "Letter", // Included in the Unicode Base addon
    Ll: "Lowercase_Letter",
    Lu: "Uppercase_Letter",
    Lt: "Titlecase_Letter",
    Lm: "Modifier_Letter",
    Lo: "Other_Letter",
    M: "Mark",
    Mn: "Non_Spacing_Mark",
    Mc: "Spacing_Combining_Mark",
    Me: "Enclosing_Mark",
    N: "Number",
    Nd: "Decimal_Digit_Number",
    Nl: "Letter_Number",
    No: "Other_Number",
    P: "Punctuation",
    Pd: "Dash_Punctuation",
    Ps: "Open_Punctuation",
    Pe: "Close_Punctuation",
    Pi: "Initial_Punctuation",
    Pf: "Final_Punctuation",
    Pc: "Connector_Punctuation",
    Po: "Other_Punctuation",
    S: "Symbol",
    Sm: "Math_Symbol",
    Sc: "Currency_Symbol",
    Sk: "Modifier_Symbol",
    So: "Other_Symbol",
    Z: "Separator",
    Zs: "Space_Separator",
    Zl: "Line_Separator",
    Zp: "Paragraph_Separator",
    C: "Other",
    Cc: "Control",
    Cf: "Format",
    Co: "Private_Use",
    Cs: "Surrogate",
    Cn: "Unassigned"
});

if (!extensible)
    XRegExp.uninstall("extensibility"); // Revert to previous state

}());


/***** xregexp-unicode-scripts.js *****/

/*!
 * XRegExp Unicode Scripts v1.2.0-beta
 * Copyright 2010-2012 Steven Levithan <http://xregexp.com/>
 * Available under the MIT License
 * Uses Unicode 6.1 <http://unicode.org/Public/6.1.0/ucd/Scripts.txt>
 */

/**
 * Adds support for all Unicode scripts in the Basic Multilingual Plane (U+0000-U+FFFF).
 * E.g., `\p{Latin}`. Token names are case insensitive, and any spaces, hyphens, and underscores
 * are ignored.
 */
;(function () {
"use strict";

if (typeof XRegExp === "undefined" || typeof XRegExp.addUnicodePackage === "undefined") {
    throw new ReferenceError("XRegExp Unicode Base must be loaded before Unicode Scripts");
}

var extensible = XRegExp.isInstalled("extensibility");

if (!extensible)
    XRegExp.install("extensibility"); // Temporarily install

XRegExp.addUnicodePackage({
    Arabic: "0600-06040606-060B060D-061A061E0620-063F0641-064A0656-065E066A-066F0671-06DC06DE-06FF0750-077F08A008A2-08AC08E4-08FEFB50-FBC1FBD3-FD3DFD50-FD8FFD92-FDC7FDF0-FDFCFE70-FE74FE76-FEFC",
    Armenian: "0531-05560559-055F0561-0587058A058FFB13-FB17",
    Balinese: "1B00-1B4B1B50-1B7C",
    Bamum: "A6A0-A6F7",
    Batak: "1BC0-1BF31BFC-1BFF",
    Bengali: "0981-09830985-098C098F09900993-09A809AA-09B009B209B6-09B909BC-09C409C709C809CB-09CE09D709DC09DD09DF-09E309E6-09FB",
    Bopomofo: "02EA02EB3105-312D31A0-31BA",
    Braille: "2800-28FF",
    Buginese: "1A00-1A1B1A1E1A1F",
    Buhid: "1740-1753",
    Canadian_Aboriginal: "1400-167F18B0-18F5",
    Cham: "AA00-AA36AA40-AA4DAA50-AA59AA5C-AA5F",
    Cherokee: "13A0-13F4",
    Common: "0000-0040005B-0060007B-00A900AB-00B900BB-00BF00D700F702B9-02DF02E5-02E902EC-02FF0374037E038503870589060C061B061F06400660-066906DD096409650E3F0FD5-0FD810FB16EB-16ED173517361802180318051CD31CE11CE9-1CEC1CEE-1CF31CF51CF62000-200B200E-2064206A-20702074-207E2080-208E20A0-20B92100-21252127-2129212C-21312133-214D214F-215F21892190-23F32400-24262440-244A2460-26FF2701-27FF2900-2B4C2B50-2B592E00-2E3B2FF0-2FFB3000-300430063008-30203030-3037303C-303F309B309C30A030FB30FC3190-319F31C0-31E33220-325F327F-32CF3358-33FF4DC0-4DFFA700-A721A788-A78AA830-A839FD3EFD3FFDFDFE10-FE19FE30-FE52FE54-FE66FE68-FE6BFEFFFF01-FF20FF3B-FF40FF5B-FF65FF70FF9EFF9FFFE0-FFE6FFE8-FFEEFFF9-FFFD",
    Coptic: "03E2-03EF2C80-2CF32CF9-2CFF",
    Cyrillic: "0400-04840487-05271D2B1D782DE0-2DFFA640-A697A69F",
    Devanagari: "0900-09500953-09630966-09770979-097FA8E0-A8FB",
    Ethiopic: "1200-1248124A-124D1250-12561258125A-125D1260-1288128A-128D1290-12B012B2-12B512B8-12BE12C012C2-12C512C8-12D612D8-13101312-13151318-135A135D-137C1380-13992D80-2D962DA0-2DA62DA8-2DAE2DB0-2DB62DB8-2DBE2DC0-2DC62DC8-2DCE2DD0-2DD62DD8-2DDEAB01-AB06AB09-AB0EAB11-AB16AB20-AB26AB28-AB2E",
    Georgian: "10A0-10C510C710CD10D0-10FA10FC-10FF2D00-2D252D272D2D",
    Glagolitic: "2C00-2C2E2C30-2C5E",
    Greek: "0370-03730375-0377037A-037D038403860388-038A038C038E-03A103A3-03E103F0-03FF1D26-1D2A1D5D-1D611D66-1D6A1DBF1F00-1F151F18-1F1D1F20-1F451F48-1F4D1F50-1F571F591F5B1F5D1F5F-1F7D1F80-1FB41FB6-1FC41FC6-1FD31FD6-1FDB1FDD-1FEF1FF2-1FF41FF6-1FFE2126",
    Gujarati: "0A81-0A830A85-0A8D0A8F-0A910A93-0AA80AAA-0AB00AB20AB30AB5-0AB90ABC-0AC50AC7-0AC90ACB-0ACD0AD00AE0-0AE30AE6-0AF1",
    Gurmukhi: "0A01-0A030A05-0A0A0A0F0A100A13-0A280A2A-0A300A320A330A350A360A380A390A3C0A3E-0A420A470A480A4B-0A4D0A510A59-0A5C0A5E0A66-0A75",
    Han: "2E80-2E992E9B-2EF32F00-2FD5300530073021-30293038-303B3400-4DB54E00-9FCCF900-FA6DFA70-FAD9",
    Hangul: "1100-11FF302E302F3131-318E3200-321E3260-327EA960-A97CAC00-D7A3D7B0-D7C6D7CB-D7FBFFA0-FFBEFFC2-FFC7FFCA-FFCFFFD2-FFD7FFDA-FFDC",
    Hanunoo: "1720-1734",
    Hebrew: "0591-05C705D0-05EA05F0-05F4FB1D-FB36FB38-FB3CFB3EFB40FB41FB43FB44FB46-FB4F",
    Hiragana: "3041-3096309D-309F",
    Inherited: "0300-036F04850486064B-0655065F0670095109521CD0-1CD21CD4-1CE01CE2-1CE81CED1CF41DC0-1DE61DFC-1DFF200C200D20D0-20F0302A-302D3099309AFE00-FE0FFE20-FE26",
    Javanese: "A980-A9CDA9CF-A9D9A9DEA9DF",
    Kannada: "0C820C830C85-0C8C0C8E-0C900C92-0CA80CAA-0CB30CB5-0CB90CBC-0CC40CC6-0CC80CCA-0CCD0CD50CD60CDE0CE0-0CE30CE6-0CEF0CF10CF2",
    Katakana: "30A1-30FA30FD-30FF31F0-31FF32D0-32FE3300-3357FF66-FF6FFF71-FF9D",
    Kayah_Li: "A900-A92F",
    Khmer: "1780-17DD17E0-17E917F0-17F919E0-19FF",
    Lao: "0E810E820E840E870E880E8A0E8D0E94-0E970E99-0E9F0EA1-0EA30EA50EA70EAA0EAB0EAD-0EB90EBB-0EBD0EC0-0EC40EC60EC8-0ECD0ED0-0ED90EDC-0EDF",
    Latin: "0041-005A0061-007A00AA00BA00C0-00D600D8-00F600F8-02B802E0-02E41D00-1D251D2C-1D5C1D62-1D651D6B-1D771D79-1DBE1E00-1EFF2071207F2090-209C212A212B2132214E2160-21882C60-2C7FA722-A787A78B-A78EA790-A793A7A0-A7AAA7F8-A7FFFB00-FB06FF21-FF3AFF41-FF5A",
    Lepcha: "1C00-1C371C3B-1C491C4D-1C4F",
    Limbu: "1900-191C1920-192B1930-193B19401944-194F",
    Lisu: "A4D0-A4FF",
    Malayalam: "0D020D030D05-0D0C0D0E-0D100D12-0D3A0D3D-0D440D46-0D480D4A-0D4E0D570D60-0D630D66-0D750D79-0D7F",
    Mandaic: "0840-085B085E",
    Meetei_Mayek: "AAE0-AAF6ABC0-ABEDABF0-ABF9",
    Mongolian: "1800180118041806-180E1810-18191820-18771880-18AA",
    Myanmar: "1000-109FAA60-AA7B",
    New_Tai_Lue: "1980-19AB19B0-19C919D0-19DA19DE19DF",
    Nko: "07C0-07FA",
    Ogham: "1680-169C",
    Ol_Chiki: "1C50-1C7F",
    Oriya: "0B01-0B030B05-0B0C0B0F0B100B13-0B280B2A-0B300B320B330B35-0B390B3C-0B440B470B480B4B-0B4D0B560B570B5C0B5D0B5F-0B630B66-0B77",
    Phags_Pa: "A840-A877",
    Rejang: "A930-A953A95F",
    Runic: "16A0-16EA16EE-16F0",
    Samaritan: "0800-082D0830-083E",
    Saurashtra: "A880-A8C4A8CE-A8D9",
    Sinhala: "0D820D830D85-0D960D9A-0DB10DB3-0DBB0DBD0DC0-0DC60DCA0DCF-0DD40DD60DD8-0DDF0DF2-0DF4",
    Sundanese: "1B80-1BBF1CC0-1CC7",
    Syloti_Nagri: "A800-A82B",
    Syriac: "0700-070D070F-074A074D-074F",
    Tagalog: "1700-170C170E-1714",
    Tagbanwa: "1760-176C176E-177017721773",
    Tai_Le: "1950-196D1970-1974",
    Tai_Tham: "1A20-1A5E1A60-1A7C1A7F-1A891A90-1A991AA0-1AAD",
    Tai_Viet: "AA80-AAC2AADB-AADF",
    Tamil: "0B820B830B85-0B8A0B8E-0B900B92-0B950B990B9A0B9C0B9E0B9F0BA30BA40BA8-0BAA0BAE-0BB90BBE-0BC20BC6-0BC80BCA-0BCD0BD00BD70BE6-0BFA",
    Telugu: "0C01-0C030C05-0C0C0C0E-0C100C12-0C280C2A-0C330C35-0C390C3D-0C440C46-0C480C4A-0C4D0C550C560C580C590C60-0C630C66-0C6F0C78-0C7F",
    Thaana: "0780-07B1",
    Thai: "0E01-0E3A0E40-0E5B",
    Tibetan: "0F00-0F470F49-0F6C0F71-0F970F99-0FBC0FBE-0FCC0FCE-0FD40FD90FDA",
    Tifinagh: "2D30-2D672D6F2D702D7F",
    Vai: "A500-A62B",
    Yi: "A000-A48CA490-A4C6"
});

if (!extensible)
    XRegExp.uninstall("extensibility"); // Revert to previous state

}());


/***** xregexp-matchrecursive.js *****/

/*!
 * XRegExp Match Recursive v0.2.0-beta
 * Copyright 2009-2012 Steven Levithan <http://xregexp.com/>
 * Available under the MIT License
 */

/**
 * Returns matches between outermost left and right delimiters, or arrays of match parts and
 * position data. An error is thrown if delimiters are unbalanced within the data.
 * @param {String} str String to search.
 * @param {String} left Left delimiter as an XRegExp pattern string.
 * @param {String} right Right delimiter as an XRegExp pattern string.
 * @param {String} [flags] Flags for the left and right delimiters. Use any of: `gimnsxy`.
 * @param {Object} [options] Lets you specify `valueNames` and `escapeChar` options.
 * @returns {Array} Array of matches, or an empty array.
 * @example
 *
 * // Basic usage
 * XRegExp.matchRecursive('(t((e))s)t()(ing)', '\\(', '\\)', 'g');
 *
 * // With valueNames and escapeChar
 * var str = '...{1}\\{{function(x,y){return y+x;}}';
 * XRegExp.matchRecursive(str, '{', '}', 'gi', {
 *     valueNames: ['between', 'left', 'match', 'right'],
 *     escapeChar: '\\'
 * });
 */
;XRegExp.matchRecursive = function (str, left, right, flags, options) {
    "use strict";

    flags = flags || "";
    options = options || {};
    var global = flags.indexOf("g") > -1,
        sticky = flags.indexOf("y") > -1;
    flags = flags.replace(/y/g, ""); // Flag y handled internally
    var left = XRegExp(left, flags),
        right = XRegExp(right, flags),
        escapeChar = options.escapeChar,
        vN = options.valueNames,
        output = [],
        openTokens = 0, delimStart = 0, delimEnd = 0, lastOuterEnd = 0,
        outerStart, innerStart, leftMatch, rightMatch, escaped, esc;

    if (escapeChar) {
        if (escapeChar.length > 1)
            throw new SyntaxError("can't use more than one escape character");
        if (/\\[1-9]/.test(right.source.replace(/\\[0\D]|\[(?:[^\\\]]|\\[\s\S])*]/g, "")))
            throw new SyntaxError("can't use escape character if backreference in delimiter");
        escaped = XRegExp.escape(escapeChar);
        esc = new RegExp(
            "(?:" + escaped + "[\\S\\s]|(?:(?!" + left.source + "|" + right.source + ")[^" + escaped + "])+)+",
            flags.replace(/[^im]+/g, "") // Flags gy not needed here; flags nsx handled by XRegExp
        );
    }

    while (true) {
        // If using an escape character, advance to the delimiter's next starting position,
        // skipping any escaped characters in between
        if (escapeChar)
            delimEnd += (XRegExp.exec(str, esc, delimEnd, /*sticky*/ true) || [""])[0].length;
        leftMatch = XRegExp.exec(str, left, delimEnd);
        rightMatch = XRegExp.exec(str, right, delimEnd);
        // Keep only the leftmost result
        if (leftMatch && rightMatch) {
            if (leftMatch.index <= rightMatch.index) rightMatch = null;
            else leftMatch = null;
        }
        /* Paths (LM:leftMatch, RM:rightMatch, OT:openTokens):
        LM | RM | OT | Result
        1  | 0  | 1  | loop
        1  | 0  | 0  | loop
        0  | 1  | 1  | loop
        0  | 1  | 0  | throw
        0  | 0  | 1  | throw
        0  | 0  | 0  | break
        * Doesn't include the sticky mode special case
        * Loop ends after the first completed match if `!global` */
        if (leftMatch || rightMatch) {
            delimStart = (leftMatch || rightMatch).index;
            delimEnd = delimStart + (leftMatch || rightMatch)[0].length;
        } else if (!openTokens) {
            break;
        }
        if (sticky && !openTokens && delimStart > lastOuterEnd)
            break;
        if (leftMatch) {
            if (!openTokens++) {
                outerStart = delimStart;
                innerStart = delimEnd;
            }
        } else if (rightMatch && openTokens) {
            if (!--openTokens) {
                if (vN) {
                    if (vN[0] && outerStart > lastOuterEnd)
                               output.push([vN[0], str.slice(lastOuterEnd, outerStart), lastOuterEnd, outerStart]);
                    if (vN[1]) output.push([vN[1], str.slice(outerStart,   innerStart), outerStart,   innerStart]);
                    if (vN[2]) output.push([vN[2], str.slice(innerStart,   delimStart), innerStart,   delimStart]);
                    if (vN[3]) output.push([vN[3], str.slice(delimStart,   delimEnd),   delimStart,   delimEnd]);
                } else {
                    output.push(str.slice(innerStart, delimStart));
                }
                lastOuterEnd = delimEnd;
                if (!global) break;
            }
        } else {
            throw new Error("string contains unbalanced delimiters");
        }
        // If the delimiter matched an empty string, avoid an infinite loop
        if (delimStart === delimEnd) delimEnd++;
    }

    if (global && !sticky && vN && vN[0] && str.length > lastOuterEnd)
        output.push([vN[0], str.slice(lastOuterEnd), lastOuterEnd, str.length]);

    return output;
};



/*
---
name: numbers

description: Cocoscript helper to create handle numbers

requires: []

provides: [upoopu.tools.numbers]

...
*/
var replace$ = ''.replace;
namespace("upoopu.tools.numbers", function(ns){
  ns.fromString = function(it){
    var nonfancy;
    nonfancy = replace$.call(it, /[^0-9.]/g, '');
    if (nonfancy.indexOf(".") >= 0) {
      return parseFloat(nonfancy);
    } else {
      return parseInt(nonfancy);
    }
  };
  return ns.readableFileSizeString = function(fileSizeInBytes){
    var i, byte_units;
    i = -1;
    byte_units = [' kB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB'];
    do {
      fileSizeInBytes = fileSizeInBytes / 1024;
      i++;
    } while (fileSizeInBytes > 1024);
    return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byte_units[i];
  };
});

/*
---
name: upoopu.parsers.config

description: config for utopia data

requires: [namespace]

provides: [upoopu.parsers.config]

...
*/
namespace("upoopu.parsers.config", function(ns){
  var specialization, s, nobility, races, r, ospec;
  ns.SERVER = "utopia-game.com";
  ns.PROXY = "utopia.upoopu.com";
  ns.RACES = [["orc", "goblins", 4, "trolls", 4, "ogres", 7, 1, 5.75], ["human", "swordsmen", 4, "archers", 4, "knights", 6, 4, 5.5], ["halfling", "strongarms", 4, "slingers", 4, "brutes", 4, 4, 4], ["dwarf", "warriors", 4, "axemen", 4, "berserkers", 6, 3, 5.25], ["elf", "rangers", 4, "archers", 4, "elf lords", 5, 3, 5], ["avian", "griffins", 4, "harpies", 4, "drakes", 6, 2, 5], ["faery", "magicians", 4, "druids", 4, "beastmasters", 3, 5, 5.25], ["undead", "skeletons", 4, "zombies", 4, "ghouls", 7, 2, 6]];
  ns.rulerPrefix = 0;
  ns.rulerPostfix = 1;
  ns.RULERSPECIALIZATION = [["merchant", ns.rulerPrefix, "the wealthy"], ["shepherd", ns.rulerPrefix, "the humble"], ["sage", ns.rulerPrefix, "the wise"], ["rogue", ns.rulerPostfix, "the rogue"], ["mystic", ns.rulerPostfix, "the sorcerer", "the sorceress"], ["warrior", ns.rulerPostfix, "the warrior"], ["tactician", ns.rulerPrefix, "the conniving"], ["warhero", ns.rulerPostfix, "the hero"], ["cleric", ns.rulerPostfix, "the blessed"]];
  ns.RULERNOBILITY = [["peasant"], ["knight", "knight", "lady"], ["lord", "lord", "noble lady"], ["baron", "baron", "baroness"], ["viscount", "viscount", "viscountess"], ["count", "count", "countess"], ["marquis", "marquis", "marchioness"], ["duke", "duke", "duchess"], ["prince", "prince", "princess"], ["king", "king", "queen"]];
  ns.SPELLS = [["minorprotection", "Our realm is now under a sphere of protection (?:for|until) (?<duration>[duration])"], ["greaterprotection", "Our realm is now under a sphere of protection (?:for|until) (?<duration>[duration])"], ["fog"], ["magicshield", "The magical auras within our province will protect us from the black magic of our enemies (?:for|until) (?<duration>[duration])"], ["mysticaura", "A Mystic Aura has been placed around our province, protecting us from the next evil spell from abroad"], ["fertilelands", "We have made our lands extraordinarily fertile (?:for|until) (?<duration>[duration])"], ["naturesblessing", "Our lands have been blessed by nature (?:for|until) (?<duration>[duration]), and will be protected from drought and storms."], ["loveandpeace", "Our peasantry is influenced by a magical calm. We expect birth rates to be higher (?:for|until) (?<duration>[duration])"], ["treeofgold", "52,865 gold coins have fallen from the trees!", false, "gold"], ["quickfeet", "Our armies have been blessed with excellent speed (?:for|until) their next battle."], ["buildersboon", "Our builders have been blessed with unnatural speed (?:for|until) (?<duration>[duration])"], ["inspirearmy", "Our army has been inspired to train harder. We expect maintenance costs to be reduced (?:for|until) (?<duration>[duration])"], ["anonymity", "Our next attack will be cloaked under the shades of anonymity."], ["invisibility", "Our thieves have been made partially invisible (?:for|until) (?<duration>[duration])"], ["clearsight", "Our police have been blessed with Clear Sight (?:for|until) (?<duration>[duration])"], ["warspoils", "Our army has been blessed with immediate War Spoils (?:for|until) (?<duration>[duration])"], ["fanaticism", "Our army will fight with fanatical fervor (?:for|until) (?<duration>[duration])"], ["fountainofknowledge", "Our students are blessed with excellent concentration (?:for|until) (?<duration>[duration])"], ["townwatch", "Our peasants will help defend our lands (?:for|until) (?<duration>[duration])"], ["aggression", "Our soldiers will fight with unique aggression (?:for|until) (?<duration>[duration])"], ["animatedead", "Our dead will be awakened the next time our lands are attacked!"], ["reflectmagic", "Some of the spells cast upon our lands will be reflected back upon their creators (?:for|until) (?<duration>[duration])"], ["shadowlight", "ur lands are blessed with Shadowlight. The next time thieves enter our lands their identities will be revealed."], ["patriotism", "Our people are excited about the military and will signup more quickly (?:for|until) (?<duration>[duration])"], ["paradise", "Our mages created 5 acres more land for us to use."], ["bloodlust"], ["magesfury"], ["storms", "Storms will ravage (?<target>[province] [location]) for (?<duration>[duration])"], ["droughts", "A drought will reign over the lands of (?<target>[province] [location]) for (?<duration>[duration])"], ["vermin", "Vermin will feast on the granaries of (?<target>[province] [location]) for (?<duration>[duration])"], ["exposethieves", "Our mages have illuminated the lands of our enemies and exposed the thieves that walk through their lands", true], ["greed", "soldiers to turn greedy for (?<duration>[duration])", true], ["foolsgold", "mages have turned (?<delta>[number]) gold coins in (?<target>[province] [location]) to worthless lead", false, "gold"], ["pitfalls", "Pitfalls will haunt the lands of (?<target>[province] [location]) for (?<duration>[duration])"], ["fireball", "A fireball burns through the skies of (?<target>[province] [location]). (?<delta>[number]) peasants", false, "peasants"], ["chastity", "womenfolk of (?<target>[province] [location]) have taken a vow of chastity for (?<duration>[duration])"], ["lightningstrike"], ["explosions", "Explosions will rock aid shipments to and from (?<target>[province] [location]) for (?<duration>[duration])"], ["amnesia", "You were able to make the people of (?<target>[province] [location]) temporarily forget (?<delta>[number]) books of knowledge!", false, "science"], ["nightmares", "During the night, (?<delta>[number]) of the men in the armies and thieves' guilds of (?<target>[province] [location]) had nightmares", false, "troops"], ["mysticvortex"], ["meteorshowers", "Meteors will rain across the lands of (?<target>[province] [location]) for (?<duration>[duration])"], ["tornadoes", "Tornadoes scour the lands of (?<target>[province] [location]), laying waste to (?<delta>[number]) acres", false, "buildings"], ["landlust", "Our Land Lust over (?<target>[province] [location]) has given us another (?<delta>[number]) acres? of land", false, "land"], ["naturesblessing", "Unfortunately, that land has been blessed by nature, and our spell had no effect", true]];
  ns.THIEFOPS = [["sabotagewizards"], ["robthegranaries", "Early indications show that our operation was a success. Our thieves have returned with (?<delta>[number]) bushels.", "food"], ["robthevaults"], ["robthetowers"], ["kidnapping", "Our thieves kidnapped many people, but only were able to return with (?<delta>[number]) of them", "peasants"], ["arson", "Our thieves burned down (?<delta>[number]) acres of buildings", "buildings"], ["greaterarson"], ["nightstrike", "Our thieves assassinated (?<delta>[number]) enemy troops", "troops"], ["inciteriots", "Our thieves have caused rioting. It is expected to last (?<duration>[duration])"], ["stealhorses"], ["bribethieves", "Our thieves have bribed members of our enemies. guild"], ["bribegenerals", "Our thieves have bribed an enemy general"], ["freeprisoners"], ["assassinatewizards", "Our thieves assassinated (?<delta>[number]) wizards of the", "wizards"], ["propaganda", "We have converted (?<delta>[number]) .{0,40}(?<kind>(?:wizards|thieves|soldiers|specialist|[elite]))"]];
  ns.BUILDINGS = [["barrenlands", "Barren Land", null], ["homes", "Homes", "0"], ["farms", "Farms", "1"], ["mills", "Mills", "2"], ["banks", "Banks", "3"], ["traininggrounds", "Training Grounds", "4"], ["armouries", "Armouries", "5"], ["barracks", "Military Barracks", "6"], ["forts", "Forts", "7"], ["guardstations", "Guard Stations", "8"], ["hospitals", "Hospitals", "9"], ["guilds", "Guilds", "10"], ["towers", "Towers", "11"], ["thievesdens", "Thieves' Dens", "12"], ["watchtowers", "Watch Towers", "13"], ["libraries", "Libraries", "14"], ["schools", "Schools", "15"], ["stables", "Stables", "16"], ["dungeons", "Dungeons", "17"]];
  ns.SCIENCES = [["alchemy", "Alchemy"], ["tools", "Tools"], ["housing", "Housing"], ["food", "Food"], ["military", "Military"], ["thievery", "Crime"], ["channeling", "Channeling"]];
  ns.DRAGONS = [["gold"], ["ruby"], ["sapphire"], ["emerald"], ["nodragon"]];
  ns.GBP = [["nothit", "attacked much", "hit much", 0], ["couple", "attacked a little", "hit a little", 5], ["moderately", "attacked moderately", "hit moderately", 25], ["heavily", "attacked pretty heavily", "hit pretty heavily", 45], ["extremelyheavily", "attacked extremely badly", "hit extremely badly", 65]];
  ns.RELATIONS = [["norel", "Normal"], ["unfriendly", "Unfriendly"], ["hostile", "Hostile"], ["war", "War"], ["cf", "Non Aggression Pact"]];
  specialization = (function(){
    var i$, ref$, len$, results$ = [];
    for (i$ = 0, len$ = (ref$ = ns.RULERSPECIALIZATION).length; i$ < len$; ++i$) {
      s = ref$[i$];
      results$.push(s.slice(2).join("|"));
    }
    return results$;
  }()).join("|");
  nobility = (function(){
    var i$, ref$, len$, results$ = [];
    for (i$ = 0, len$ = (ref$ = ns.RULERNOBILITY).length; i$ < len$; ++i$) {
      s = ref$[i$];
      if (s.length > 1) {
        results$.push([s[2], s[1]].join("|"));
      }
    }
    return results$;
  }()).join("|");
  races = (function(){
    var i$, ref$, len$, results$ = [];
    for (i$ = 0, len$ = (ref$ = ns.RACES).length; i$ < len$; ++i$) {
      r = ref$[i$];
      results$.push(r[0]);
    }
    return results$;
  }()).join("|");
  ospec = (function(){
    var i$, ref$, len$, results$ = [];
    for (i$ = 0, len$ = (ref$ = ns.RACES).length; i$ < len$; ++i$) {
      r = ref$[i$];
      results$.push(r[1]);
    }
    return results$;
  }()).join("|");
  ns.FORMATS = [
    ["province", "[a-zA-z0-9 _-]{3,30}"], ["provinceng", "[a-zA-z0-9 _-]{3,30}?"], ["kingdom", "[a-zA-z0-9 _-]{3,30}"], ["location", "\\(\\s?([0-9]{1,2})\\s?:\\s?([0-9]{1,2})\\s?\\)"], ["uto_date", "[a-z]+ [0-9]+ of YR[0-9]+"], ["word", "\\S?\\s?\\S+\\b\\S*"], ["number", "[-0-9,.]+"], ["pct", "[-0-9,.]+%"], ["ruler", "(" + specialization + "|) ?(" + nobility + "|)([a-zA-Z0-9 _-]{3,35}?)(|" + specialization + ")(?= (" + races + ")| (" + ospec + ")|$|,[^0-9]|Personality|\\*\\* Summary)"], ["rulerbase", "(" + specialization + "|) ?(" + nobility + "|)([a-zA-Z0-9 _-]{3,35}?)(|" + specialization + ")"], [
      "race", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RACES).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[0]);
        }
        return results$;
      }()).join("|")
    ], [
      "ospec", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RACES).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[1]);
        }
        return results$;
      }()).join("|")
    ], [
      "dspec", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RACES).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[3]);
        }
        return results$;
      }()).join("|")
    ], [
      "elite", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RACES).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[5]);
        }
        return results$;
      }()).join("|")
    ], [
      "honor", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RULERNOBILITY).length; i$ < len$; ++i$) {
          s = ref$[i$];
          if (s.length > 1) {
            results$.push([s[2], s[1]].join("|"));
          }
        }
        return results$;
      }()).join("|")
    ], [
      "honorext", "peasant|" + (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RULERNOBILITY).length; i$ < len$; ++i$) {
          s = ref$[i$];
          if (s.length > 1) {
            results$.push([s[2], s[1]].join("|"));
          }
        }
        return results$;
      }()).join("|")
    ], [
      "dragon", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.DRAGONS).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[0]);
        }
        return results$;
      }()).join("|")
    ], [
      "gbp", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.GBP).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[1]);
        }
        return results$;
      }()).join("|")
    ], [
      "gbpangel", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.GBP).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[2]);
        }
        return results$;
      }()).join("|")
    ], ["stance", ["normal", "peaceful", "aggressive", "fortified"].join("|")], ["duration", "(?<durationnumber>[0-9]+|the end of this)? day"], [
      "spell_result", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.SPELLS).length; i$ < len$; ++i$) {
          s = ref$[i$];
          if (s.length > 1) {
            results$.push(s[1]);
          }
        }
        return results$;
      }()).join("|")
    ], [
      "thief_result", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.THIEFOPS).length; i$ < len$; ++i$) {
          s = ref$[i$];
          if (s.length > 1) {
            results$.push(s[1]);
          }
        }
        return results$;
      }()).join("|")
    ], [
      "relation", (function(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = ns.RELATIONS).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push(r[1]);
        }
        return results$;
      }()).join("|")
    ]
  ];
  return ns.DATASTRINGS = [["self", "self\\[[province]:[location]\\]"], ["sot", "the province of [province] [location] ?(?:Pre Age|[uto_date]).?[word]{3,15}.?race [race]"], ["angelsot", "The Province of [province] [location] \\[http://www.utopiatemple.com"], ["som", "thieves listen in on a report from the Military Elders of [province] [location]"], ["selfsom", "[ruler], we have [number] generals available to lead our armies. One must always stay here to lead our forces in defense"], ["angelsom", "Military Intelligence on [province] [location] \\[http://www.utopiatemple.com"], ["survey", "thieves scour the lands of [province] [location]"], ["selfsurvey", "(The structures we construct throughout our lands may or) may not function at full capacity"], ["science", "thieves visit the research centers of [province] [location]"], ["selfscience", "[you know that sending our scholars] to study requires time"], ["infiltrate", "Our thieves have infiltrated the Thieves' Guilds"], ["kdnews", "(Our thieves have stolen the last 2 month's of kingdom news|The Kingdom Reporter [word]{2,5} Edition [word]{2,5} Edition)"], ["kd", "(?:current kingdom is|kingdom of) [kingdom] [location](?:.{0,400} total provinces)"], ["magic", "Your wizards(?: gather [number] runes and)? begin casting.*?[spell_result]"], ["thievery", "Early indications show that our operation was a success.*?[thief_result]"], ["attack", "(Your army was no match for the defenses of|Your forces arrive at) [province] [location]"], ["selfspells", "(there are currently no magical auras affecting our province|the magical auras affecting our province are detailed below)"], ["aid", "We have sent [number] (?:bushel|gold coin|rune|soldier)s?.{0,80} to [province] [location]"]];
});

/*  ProtoJS - Protocol buffers for Javascript.
 *  protobuf.js
 *
 *  Copyright (c) 2009-2010, Patrick Reiter Horn
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *  * Neither the name of ProtoJS nor the names of its contributors may
 *    be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

var PROTO = {};

PROTO.DefineProperty = (function () {
        var DefineProperty;
        if (typeof(Object.defineProperty) != "undefined") {
            DefineProperty = function(prototype, property, getter, setter) {
                Object.defineProperty(prototype, property, {
                    'get': getter, 'set': setter,
                    'enumerable': true, 'configurable': false});
            };
        } else if (Object.prototype.__defineGetter__ && Object.prototype.__defineSetter__) {
            DefineProperty = function(prototype, property, getter, setter) {
                if (typeof getter !== 'undefined') {
                    prototype.__defineGetter__(property, getter);
                }
                if (typeof setter !== 'undefined') {
                    prototype.__defineSetter__(property, setter);
                }
            };
        }
        // IE8's Object.defineProperty method might be broken.
        // Make sure DefineProperty works before returning it.
        if (DefineProperty) {
            try {
                var TestClass = function(){};
                DefineProperty(TestClass.prototype, "x",
                               function(){return this.xval*2;},
                               function(newx){this.xval=newx;});
                var testinst = new TestClass;
                testinst.x = 5;
                if (testinst.x != 10) {
                    console.log("DefineProperty test gave the wrong result "+testinst.x);
                    DefineProperty = undefined;
                }
            } catch (e) {
                if (typeof(console)!="undefined" && console.log) {
                    console.log("DefineProperty should be supported, but threw "+e,e);
                }
                DefineProperty = undefined;
            }
        }
        return DefineProperty;
})();

/** Clones a PROTO type object. Does not work on arbitrary javascript objects.
For example, can be used to copy the "bytes" class and make a custom toString method.
*/
PROTO.cloneType = function(f) {
    var ret = {};
    for (var x in f) {
        ret[x] = f[x];
    }
    return ret;
}

PROTO.wiretypes = {
    varint: 0,
    fixed64: 1,
    lengthdelim: 2,
    fixed32: 5
};

PROTO.optional = 'optional';
PROTO.repeated = 'repeated';
PROTO.required = 'required';

/**
 * @constructor
 */
PROTO.I64 = function (msw, lsw, sign) {
    this.msw = msw;
    this.lsw = lsw;
    if (typeof lsw === undefined) {
        console.error("Too few arguments passed to I64 constructor: perhaps you meant PROTO.I64.fromNumber()");
        throw ("Too few arguments passed to I64 constructor: perhaps you meant PROTO.I64.fromNumber()");
    }
    if (sign === true) sign = -1;
    if (!sign) sign = 1;
    this.sign = sign;
};

PROTO.I64.prototype = {
    toNumber: function() {
        return (this.msw*4294967296 + this.lsw)*this.sign;
    },
    toString: function() {
        //return this.toNumber();
        function zeros(len){
            var retval="";
            for (var i=0;i<len;++i) {
                retval+="0";
            }
            return retval;
        }
        var firstHalf=this.msw.toString(16);
        var secondHalf=this.lsw.toString(16);
        var sign = (this.sign==-1 ? "-" : "");
        return sign+"0x"+zeros(8-firstHalf.length)+firstHalf+zeros(8-secondHalf.length)+secondHalf;
    },
    equals: function(other) {
        return this.sign==other.sign&&this.msw==other.msw&&this.lsw==other.lsw;
    },
    hash: function() {
        return (this.sign*this.msw)+"_"+this.lsw;
    },
    convertToUnsigned: function() {
        var local_lsw;
        local_lsw=this.lsw;
        var local_msw;
        if (this.sign<0) {
            local_msw=2147483647-this.msw;
            local_msw+=2147483647;
            local_msw+=2;
            local_lsw=2147483647-this.lsw;
            local_lsw+=2147483647;
            local_lsw+=2;
        }else {
            local_msw=this.msw;
        }
        return new PROTO.I64(local_msw,local_lsw,1);
    },
    convertFromUnsigned:function() {
        if(this.msw>=2147483648) {
            return new PROTO.I64(this.msw-2147483648,this.lsw,-1);
        }
        return new PROTO.I64(this.msw,this.lsw,1);
    },
    convertToZigzag: function() {
        var local_lsw;
        if (this.sign<0) {
            local_lsw=this.lsw*2+1;
        }else {
            local_lsw=this.lsw*2;
        }
        var local_msw=this.msw*2;
        if (local_lsw>4294967295){
            local_msw+=1;
            local_lsw-=4294967296;
        }
        return new PROTO.I64(local_msw,local_lsw,1);
    },
    convertFromZigzag:function() {
        if(this.msw&1) {
            return new PROTO.I64((this.msw>>>1),
                                 2147483648+(this.lsw>>>1),
                                 (this.lsw&1)?-1:1);
        }
        return new PROTO.I64((this.msw>>>1),
                             (this.lsw>>>1),
                             (this.lsw&1)?-1:1);
    },
    serializeToLEBase256: function() {
        var arr = new Array(8);
        var temp=this.lsw;
        for (var i = 0; i < 4; i++) {
            arr[i] = (temp&255);
            temp=(temp>>8);
        }
        temp = this.msw;
        for (var i = 4; i < 8; i++) {
            arr[i] = (temp&255);
            temp=(temp>>8);
        }
        return arr;
    },
    serializeToLEVar128: function() {
        var arr = new Array(1);
        var temp=this.lsw;
        for (var i = 0; i < 4; i++) {
            arr[i] = (temp&127);
            temp=(temp>>>7);
            if(temp==0&&this.msw==0) return arr;
            arr[i]+=128;
        }        
        arr[4] = (temp&15) | ((this.msw&7)<<4);
        temp=(this.msw>>>3);
        if (temp==0) return arr;
        arr[4]+=128;
        for (var i = 5; i<10; i++) {
            arr[i] = (temp&127);
            temp=(temp>>>7);
            if(temp==0) return arr;
            
            arr[i]+=128;
        }
        return arr;
    },
    unsigned_add:function(other) {
        var temp=this.lsw+other.lsw;
        var local_msw=this.msw+other.msw;
        var local_lsw=temp%4294967296;
        temp-=local_lsw;
        local_msw+=temp/4294967296;
        return new PROTO.I64(local_msw,local_lsw,this.sign);
    },
    sub : function(other) {
        if (other.sign!=this.sign) {
            return this.unsigned_add(other);
        }
        if (other.msw>this.msw || (other.msw==this.msw&&other.lsw>this.lsw)) {
            var retval=other.sub(this);
            retval.sign=-this.sign;
            return retval;
        }
        var local_lsw=this.lsw-other.lsw;
        var local_msw=this.msw-other.msw;       
        if (local_lsw<0) {
            local_lsw+=4294967296;
            local_msw-=1;
        }
        return new PROTO.I64(local_msw,local_lsw,this.sign);        
    },
    less:function(other){
        if (other.sign!=this.sign) {
            return this.sign<0;
        }
        var a=this,b=other;
        if (this.sign<0) {
            b=this;a=other;
        }
        if (a.msb==b.msb)
            return a.lsb<b.lsb;
        if (a.msb<b.msb)
            return true;
        return false;
    },
    unsigned_less:function(other){
        var a=this,b=other;
        if (a.msb==b.msb)
            return a.lsb<b.lsb;
        if (a.msb<b.msb)
            return true;
        return false;
    },
    add : function(other) {
        if (other.sign<0 && this.sign>=0)
            return this.sub(new PROTO.I64(other.msw,other.lsw,-other.sign));
        if (other.sign>=0 && this.sign<0)
            return other.sub(new PROTO.I64(this.msw,this.lsw,-this.sign));
        return this.unsigned_add(other);
    }
};

PROTO.I64.fromNumber = function(mynum) {
    var sign = (mynum < 0) ? -1 : 1;
    mynum *= sign;
    var lsw = (mynum%4294967296);
    var msw = ((mynum-lsw)/4294967296);
    return new PROTO.I64(msw, lsw, sign);
};

PROTO.I64.from32pair = function(msw, lsw, sign) {
    return new PROTO.I64(msw, lsw, sign);
};
PROTO.I64.parseLEVar128 = function (stream) {
    var n = 0;
    var endloop = false;
    var offset=1;
    for (var i = 0; !endloop && i < 5; i++) {
        var byt = stream.readByte();
        if (byt >= 128) {
            byt -= 128;
        } else {
            endloop = true;
        }
        n += offset*byt;
        offset *= 128;
    }
    var lsw=n%4294967296
    var msw = 0;    
    offset=8;
    for (var i = 0; !endloop && i < 5; i++) {
        var byt = stream.readByte();
        if (byt >= 128) {
            byt -= 128;
        } else {
            endloop = true;
        }
        msw += offset*byt;
        offset *= 128;
    }
    return new PROTO.I64(msw%4294967296,lsw,1);
};

PROTO.I64.parseLEBase256 = function (stream) {
    var n = 0;
    var endloop = false;
    var offset=1;
    for (var i = 0; i < 4; i++) {
        var byt = stream.readByte();
        n += offset*byt;
        offset *= 256;
    }
    var lsw=n;
    var msw=0;
    offset=1;
    for (var i = 0; i < 4; i++) {
        var byt = stream.readByte();
        msw += offset*byt;
        offset *= 256;
    }
    return new PROTO.I64(msw,lsw,1);
};

PROTO.I64.ONE = new PROTO.I64.fromNumber(1);
PROTO.I64.ZERO = new PROTO.I64.fromNumber(0);

/**
 * + Jonas Raoni Soares Silva
 * http://jsfromhell.com/classes/binary-parser [rev. #1]
 * @constructor
 */ 
PROTO.BinaryParser = function(bigEndian, allowExceptions){
    this.bigEndian = bigEndian, this.allowExceptions = allowExceptions;
};
    PROTO.BinaryParser.prototype.encodeFloat = function(number, precisionBits, exponentBits){
        var n;
        var bias = Math.pow(2, exponentBits - 1) - 1, minExp = -bias + 1, maxExp = bias, minUnnormExp = minExp - precisionBits,
        status = isNaN(n = parseFloat(number)) || n == -Infinity || n == +Infinity ? n : 0,
        exp = 0, len = 2 * bias + 1 + precisionBits + 3, bin = new Array(len),
        signal = (n = status !== 0 ? 0 : n) < 0;
        n = Math.abs(n);
        var intPart = Math.floor(n), floatPart = n - intPart, i, lastBit, rounded, j, result, r;
        for(i = len; i; bin[--i] = 0){}
        for(i = bias + 2; intPart && i; bin[--i] = intPart % 2, intPart = Math.floor(intPart / 2)){}
        for(i = bias + 1; floatPart > 0 && i; (bin[++i] = ((floatPart *= 2) >= 1) - 0) && --floatPart){}
        for(i = -1; ++i < len && !bin[i];){}
        if(bin[(lastBit = precisionBits - 1 + (i = (exp = bias + 1 - i) >= minExp && exp <= maxExp ? i + 1 : bias + 1 - (exp = minExp - 1))) + 1]){
            if(!(rounded = bin[lastBit]))
                for(j = lastBit + 2; !rounded && j < len; rounded = bin[j++]){}
            for(j = lastBit + 1; rounded && --j >= 0; (bin[j] = !bin[j] - 0) && (rounded = 0)){}
        }
        for(i = i - 2 < 0 ? -1 : i - 3; ++i < len && !bin[i];){}

        (exp = bias + 1 - i) >= minExp && exp <= maxExp ? ++i : exp < minExp &&
            (exp != bias + 1 - len && exp < minUnnormExp && this.warn("encodeFloat::float underflow"), i = bias + 1 - (exp = minExp - 1));
        (intPart || status !== 0) && (this.warn(intPart ? "encodeFloat::float overflow" : "encodeFloat::" + status),
            exp = maxExp + 1, i = bias + 2, status == -Infinity ? signal = 1 : isNaN(status) && (bin[i] = 1));
        for(n = Math.abs(exp + bias), j = exponentBits + 1, result = ""; --j; result = (n % 2) + result, n = n >>= 1){}
        for(n = 0, j = 0, i = (result = (signal ? "1" : "0") + result + bin.slice(i, i + precisionBits).join("")).length, r = [];
            i; n += (1 << j) * result.charAt(--i), j == 7 && (r[r.length] = n, n = 0), j = (j + 1) % 8){}
        
        return (this.bigEndian ? r.reverse() : r);
    };
    PROTO.BinaryParser.prototype.encodeInt = function(number, bits, signed){
        var max = Math.pow(2, bits), r = [];
        (number >= max || number < -(max >> 1)) && this.warn("encodeInt::overflow") && (number = 0);
        number < 0 && (number += max);
        for(; number; r[r.length] = number % 256, number = Math.floor(number / 256)){}
        for(bits = -(-bits >> 3) - r.length; bits--;){}
        return (this.bigEndian ? r.reverse() : r);
    };
    PROTO.BinaryParser.prototype.decodeFloat = function(data, precisionBits, exponentBits){
        var b = new this.Buffer(this.bigEndian, data);
        PROTO.BinaryParser.prototype.checkBuffer.call(b, precisionBits + exponentBits + 1);
        var bias = Math.pow(2, exponentBits - 1) - 1, signal = PROTO.BinaryParser.prototype.readBits.call(b,precisionBits + exponentBits, 1);
        var exponent = PROTO.BinaryParser.prototype.readBits.call(b,precisionBits, exponentBits), significand = 0;
        var divisor = 2;
        var curByte = b.buffer.length + (-precisionBits >> 3) - 1;
        var byteValue, startBit, mask;
        do
            for(byteValue = b.buffer[ ++curByte ], startBit = precisionBits % 8 || 8, mask = 1 << startBit;
                mask >>= 1; (byteValue & mask) && (significand += 1 / divisor), divisor *= 2){}
        while((precisionBits -= startBit));
        return exponent == (bias << 1) + 1 ? significand ? NaN : signal ? -Infinity : +Infinity
            : (1 + signal * -2) * (exponent || significand ? !exponent ? Math.pow(2, -bias + 1) * significand
            : Math.pow(2, exponent - bias) * (1 + significand) : 0);
    };
    PROTO.BinaryParser.prototype.decodeInt = function(data, bits, signed){
        var b = new this.Buffer(this.bigEndian, data), x = b.readBits(0, bits), max = Math.pow(2, bits);
        return signed && x >= max / 2 ? x - max : x;
    };
    PROTO.BinaryParser.prototype.Buffer = function(bigEndian, buffer){
        this.bigEndian = bigEndian || 0;
        this.buffer = [];
        PROTO.BinaryParser.prototype.setBuffer.call(this,buffer);
    };

        PROTO.BinaryParser.prototype.readBits = function(start, length){
            //shl fix: Henri Torgemane ~1996 (compressed by Jonas Raoni)
            function shl(a, b){
                for(++b; --b; a = ((a %= 0x7fffffff + 1) & 0x40000000) == 0x40000000 ? a * 2 : (a - 0x40000000) * 2 + 0x7fffffff + 1){}
                return a;
            }
            if(start < 0 || length <= 0)
                return 0;
            PROTO.BinaryParser.prototype.checkBuffer.call(this, start + length);
            for(var offsetLeft, offsetRight = start % 8, curByte = this.buffer.length - (start >> 3) - 1,
                lastByte = this.buffer.length + (-(start + length) >> 3), diff = curByte - lastByte,
                sum = ((this.buffer[ curByte ] >> offsetRight) & ((1 << (diff ? 8 - offsetRight : length)) - 1))
                + (diff && (offsetLeft = (start + length) % 8) ? (this.buffer[ lastByte++ ] & ((1 << offsetLeft) - 1))
                << (diff-- << 3) - offsetRight : 0); diff; sum += shl(this.buffer[ lastByte++ ], (diff-- << 3) - offsetRight)
                ){}
            return sum;
        };
        PROTO.BinaryParser.prototype.setBuffer = function(data){
            if(data){
                for(var l, i = l = data.length, b = this.buffer = new Array(l); i; b[l - i] = data[--i]){}
                this.bigEndian && b.reverse();
            }
        };
        PROTO.BinaryParser.prototype.hasNeededBits = function(neededBits){
            return this.buffer.length >= -(-neededBits >> 3);
        };
        PROTO.BinaryParser.prototype.checkBuffer = function(neededBits){
            if(!PROTO.BinaryParser.prototype.hasNeededBits.call(this,neededBits))
                throw new Error("checkBuffer::missing bytes");
        };
    
    PROTO.BinaryParser.prototype.warn = function(msg){
        if(this.allowExceptions)
            throw new Error(msg);
        return 1;
    };
    PROTO.BinaryParser.prototype.toSmall = function(data){return this.decodeInt(data, 8, true);};
    PROTO.BinaryParser.prototype.fromSmall = function(number){return this.encodeInt(number, 8, true);};
    PROTO.BinaryParser.prototype.toByte = function(data){return this.decodeInt(data, 8, false);};
    PROTO.BinaryParser.prototype.fromByte = function(number){return this.encodeInt(number, 8, false);};
    PROTO.BinaryParser.prototype.toShort = function(data){return this.decodeInt(data, 16, true);};
    PROTO.BinaryParser.prototype.fromShort = function(number){return this.encodeInt(number, 16, true);};
    PROTO.BinaryParser.prototype.toWord = function(data){return this.decodeInt(data, 16, false);};
    PROTO.BinaryParser.prototype.fromWord = function(number){return this.encodeInt(number, 16, false);};
    PROTO.BinaryParser.prototype.toInt = function(data){return this.decodeInt(data, 32, true);};
    PROTO.BinaryParser.prototype.fromInt = function(number){return this.encodeInt(number, 32, true);};
    PROTO.BinaryParser.prototype.toDWord = function(data){return this.decodeInt(data, 32, false);};
    PROTO.BinaryParser.prototype.fromDWord = function(number){return this.encodeInt(number, 32, false);};
    PROTO.BinaryParser.prototype.toFloat = function(data){return this.decodeFloat(data, 23, 8);};
    PROTO.BinaryParser.prototype.fromFloat = function(number){return this.encodeFloat(number, 23, 8);};
    PROTO.BinaryParser.prototype.toDouble = function(data){return this.decodeFloat(data, 52, 11);};
    PROTO.BinaryParser.prototype.fromDouble = function(number){return this.encodeFloat(number, 52, 11);};

PROTO.binaryParser = new PROTO.BinaryParser(false,false);


PROTO.encodeUTF8 = function(str) {
    var strlen = str.length;
    var u8 = [];
    var c, nextc;
    var x, y, z;
    for (var i = 0; i < strlen; i++) {
        c = str.charCodeAt(i);
        if ((c & 0xff80) == 0) {
            // ASCII
            u8.push(c);
        } else {
            if ((c & 0xfc00) == 0xD800) {
                nextc = str.charCodeAt(i+1);
                if ((nextc & 0xfc00) == 0xDC00) {
                    // UTF-16 Surrogate pair
                    c = (((c & 0x03ff)<<10) | (nextc & 0x3ff)) + 0x10000;
                    i++;
                } else {
                    // error.
                    console.log("Error decoding surrogate pair: "+c+"; "+nextc);
                }
            }
            x = c&0xff;
            y = c&0xff00;
            z = c&0xff0000;
            // Encode UCS code into UTF-8
            if (c <= 0x0007ff) {
                u8.push(0xc0 | (y>>6) | (x>>6));
                u8.push(0x80 | (x&63));
            } else if (c <= 0x00ffff) {
                u8.push(0xe0 | (y>>12));
                u8.push(0x80 | ((y>>6)&63) | (x>>6));
                u8.push(0x80 | (x&63));
            } else if (c <= 0x10ffff) {
                u8.push(0xf0 | (z>>18));
                u8.push(0x80 | ((z>>12)&63) | (y>>12));
                u8.push(0x80 | ((y>>6)&63) | (x>>6));
                u8.push(0x80 | (x&63));
            } else {
                // error.
                console.log("Error encoding to utf8: "+c+" is greater than U+10ffff");
                u8.push("?".charCodeAt(0));
            }
        }
    }
    return u8;
}

PROTO.decodeUTF8 = function(u8) {
    var u8len = u8.length;
    var str = "";
    var c, b2, b3, b4;
    for (var i = 0; i < u8len; i++) {
        c = u8[i];
        if ((c&0x80) == 0x00) {
        } else if ((c&0xf8) == 0xf0) {
            // 4 bytes: U+10000 - U+10FFFF
            b2 = u8[i+1];
            b3 = u8[i+2];
            b4 = u8[i+3];
            if ((b2&0xc0) == 0x80 && (b3&0xc0) == 0x80 && (b4&0xc0) == 0x80) {
                c = (c&7)<<18 | (b2&63)<<12 | (b3&63)<<6 | (b4&63);
                i+=3;
            } else {
                // error.
                console.log("Error decoding from utf8: "+c+","+b2+","+b3+","+b4);
                continue;
            }
        } else if ((c&0xf0)==0xe0) {
            // 3 bytes: U+0800 - U+FFFF
            b2 = u8[i+1];
            b3 = u8[i+2];
            if ((b2&0xc0) == 0x80 && (b3&0xc0) == 0x80) {
                c = (c&15)<<12 | (b2&63)<<6 | (b3&63);
                i+=2;
            } else {
                // error.
                console.log("Error decoding from utf8: "+c+","+b2+","+b3);
                continue;
            }
        } else if ((c&0xe0)==0xc0) {
            // 2 bytes: U+0080 - U+07FF
            b2 = u8[i+1];
            if ((b2&0xc0) == 0x80) {
                c = (c&31)<<6 | (b2&63);
                i+=1;
            } else {
                // error.
                console.log("Error decoding from utf8: "+c+","+b2);
                continue;
            }
        } else {
            // error.
            // 80-BF: Second, third, or fourth byte of a multi-byte sequence
            // F5-FF: Start of 4, 5, or 6 byte sequence
            console.log("Error decoding from utf8: "+c+" encountered not in multi-byte sequence");
            continue;
        }
        if (c <= 0xffff) {
            str += String.fromCharCode(c);
        } else if (c > 0xffff && c <= 0x10ffff) {
            // Must be encoded into UTF-16 surrogate pair.
            c -= 0x10000;
            str += (String.fromCharCode(0xD800 | (c>>10)) + String.fromCharCode(0xDC00 | (c&1023)));
        } else {
            console.log("Error encoding surrogate pair: "+c+" is greater than U+10ffff");
        }
    }
    return str;
}


/**
 * @constructor
 */
PROTO.Stream = function () {
    this.write_pos_ = 0;
    this.read_pos_ = 0;
};
PROTO.Stream.prototype = {
    read: function(amt) {
        var result = [];
        for (var i = 0; i < amt; ++i) {
            var byt = this.readByte();
            if (byt === null) {
                break;
            }
            result.push(byt);
        }
        return result;
    },
    write: function(array) {
        for (var i = 0; i < array.length; i++) {
            this.writeByte(array[i]);
        }
    },
    readByte: function() {
        return null;
    },
    writeByte: function(byt) {
        this.write_pos_ += 1;
    },
    valid: function() {
        return false;
    }
};
/**
 * @constructor
 * @param {Array=} arr  Existing byte array to read from, or append to.
 */
PROTO.ByteArrayStream = function(arr) {
    this.array_ = arr || new Array();
    this.read_pos_ = 0;
    this.write_pos_ = this.array_.length;
};
PROTO.ByteArrayStream.prototype = new PROTO.Stream();
PROTO.ByteArrayStream.prototype.read = function(amt) {
    if (this.read_pos_+amt > this.array_.length) {
        // incomplete stream.
        //throw new Error("Read past end of protobuf ByteArrayStream: "+
        //                this.array_.length+" < "+this.read_pos_+amt);
        return null;
    }
    var ret = this.array_.slice(this.read_pos_, this.read_pos_+amt);
    this.read_pos_ += amt;
    return ret;
};
PROTO.ByteArrayStream.prototype.write = function(arr) {
    Array.prototype.push.apply(this.array_, arr);
    this.write_pos_ = this.array_.length;
};
PROTO.ByteArrayStream.prototype.readByte = function() {
    return this.array_[this.read_pos_ ++];
};
PROTO.ByteArrayStream.prototype.writeByte = function(byt) {
    this.array_.push(byt);
    this.write_pos_ = this.array_.length;
};
PROTO.ByteArrayStream.prototype.valid = function() {
    return this.read_pos_ < this.array_.length;
};
PROTO.ByteArrayStream.prototype.getArray = function() {
    return this.array_;
};
/**
 * @constructor
 * @param {string=} b64string  String to read from, or append to.
 */
(function(){
    var FromB64AlphaMinus43=[
        62,-1,62,-1,63,52,53,54,55,56,57,58,59,60,61,
        -1,-1,-1,-1,-1,-1,-1,
        0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
        17,18,19,20,21,22,23,24,25,
        -1,-1,-1,-1,63,-1,
        26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,
        41,42,43,44,45,46,47,48,49,50,51];
    var ToB64Alpha=[
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        'a','b','c','d','e','f','g','h','i','j','k','l','m',
        'n','o','p','q','r','s','t','u','v','w','x','y','z',
        '0','1','2','3','4','5','6','7','8','9','+','/'];
    var ToB64Alpha_URLSafe=[
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        'a','b','c','d','e','f','g','h','i','j','k','l','m',
        'n','o','p','q','r','s','t','u','v','w','x','y','z',
        '0','1','2','3','4','5','6','7','8','9','-','_'];
    PROTO.Base64Stream = function(b64string) {
        this.alphabet = ToB64Alpha;
        this.string_ = b64string || '';
        this.read_pos_ = 0;
        this.read_incomplete_value_ = 0;
        this.read_needed_bits_ = 8;
        this.write_extra_bits_ = 0;
        this.write_incomplete_value_ = 0;
        this.fixString();
    };
    PROTO.Base64Stream.prototype = new PROTO.Stream();
    PROTO.Base64Stream.prototype.setURLSafe = function() {
        this.alphabet = ToB64Alpha_URLSafe;
    };
    PROTO.Base64Stream.prototype.fixString = function() {
        var len = this.string_.length;
        if (this.string_[len-1]=='=') {
            var n = 4;
            var cutoff = 2;
            if (this.string_[len-cutoff]=='=') {
                n = 2;
                cutoff = 3;
            }
            this.write_extra_bits_ = n;
            this.write_incomplete_value_ = FromB64AlphaMinus43[
                this.string_.charCodeAt(len-cutoff)-43];
            this.write_incomplete_value_ >>= (6-n);
            this.string_ = this.string_.substring(0,len-cutoff);
        }
    };
    PROTO.Base64Stream.prototype.readByte = function() {
        var next6bits;
        var n = this.read_needed_bits_;
        while (next6bits === undefined || next6bits == -1) {
            if (this.read_pos_ >= this.string_.length) {
                if (this.valid()) {
                    next6bits = this.write_incomplete_value_ << (6-n);
                    this.read_pos_++;
                    break;
                } else {
                    return null;
                }
            }
            next6bits = FromB64AlphaMinus43[
                this.string_.charCodeAt(this.read_pos_++)-43];
        }
        if (n == 8) {
            this.read_incomplete_value_ = next6bits;
            this.read_needed_bits_ = 2;
            return this.readByte();
        }
        var ret = this.read_incomplete_value_<<n;
        ret |= next6bits>>(6-n);
        this.read_incomplete_value_ = next6bits&((1<<(6-n))-1);
        this.read_needed_bits_ += 2;
        return ret;
    };

    PROTO.Base64Stream.prototype.writeByte = function(byt) {
        this.write_extra_bits_ += 2;
        var n = this.write_extra_bits_;
        this.string_ += this.alphabet[
                byt>>n | this.write_incomplete_value_<<(8-n)];
        this.write_incomplete_value_ = (byt&((1<<n)-1));
        if (n == 6) {
            this.string_ += this.alphabet[this.write_incomplete_value_];
            this.write_extra_bits_ = 0;
            this.write_incomplete_value_ = 0;
        }
        if (this.string_.length%77==76) {
            this.string_ += "\n";
        }
    };

    PROTO.Base64Stream.prototype.getString = function() {
        var len = this.string_.length;
        var retstr = this.string_;
        var n = this.write_extra_bits_;
        if (n > 0) {
            retstr += this.alphabet[this.write_incomplete_value_<<(6-n)];
            if (n==2) {
                retstr += "==";
            } else if (n==4) {
                retstr += "=";
            }
        }
        return retstr;
    };
    PROTO.Base64Stream.prototype.valid = function() {
        return (this.read_pos_ < this.string_.length) ||
               (this.read_pos_==this.string_.length && this.write_extra_bits_);
    };
})();

PROTO.array =
    (function() {
        /** @constructor */
        function ProtoArray(datatype, input) {
            this.datatype_ = datatype.type();
            this.length = 0;
            if (input instanceof Array) {
                for (var i=0;i<input.length;++i) {
                    this.push(input[i]);
                }
            }
        };
        ProtoArray.IsInitialized = function IsInitialized(val) {
            return val.length > 0;
        };
        ProtoArray.prototype = {};
        ProtoArray.prototype.push = function (var_args) {
            if (arguments.length === 0) {
                if (this.datatype_.composite) {
                    var newval = new this.datatype_;
                    this[this.length++] = newval;
                    return newval;
                } else {
                    throw "Called add(undefined) for a non-composite";
                }
            } else {
                for (var i = 0; i < arguments.length; i++) {
                    var newval = this.datatype_.Convert(arguments[i]);
                    if (this.datatype_.FromProto) {
                        newval = this.datatype_.FromProto(newval);
                    }
                    this[this.length++] = newval;
                }
            }
            return arguments[0];
        }
        ProtoArray.prototype.set = function (index, newval) {
            newval = this.datatype_.Convert(newval);
            if (this.datatype_.FromProto) {
                newval = this.datatype_.FromProto(newval);
            }
            if (index < this.length && index >= 0) {
                this[index] = newval;
            } else if (index == this.length) {
                this[this.length++] = newval;
            } else {
                throw "Called ProtoArray.set with index "+index+" higher than length "+this.length;
            }
            return newval;
        }
        ProtoArray.prototype.clear = function (index, newval) {
            this.length = 0;
        }
        return ProtoArray;
    })();

PROTO.string = {
    Convert: function(str) {
        return ''+str;
    },
    wiretype: PROTO.wiretypes.lengthdelim,
    SerializeToStream: function(str, stream) {
        var arr = PROTO.encodeUTF8(str);
        return PROTO.bytes.SerializeToStream(arr, stream);
    },
    ParseFromStream: function(stream) {
        var arr = PROTO.bytes.ParseFromStream(stream);
        return PROTO.decodeUTF8(arr);
    },
    toString: function(str) {return str;}
};

PROTO.bytes = {
    Convert: function(arr) {
        if (arr instanceof Array) {
            return arr;
        } else if (arr instanceof PROTO.ByteArrayStream) {
            return arr.getArray();
        } else if (arr.SerializeToStream) {
            /* This is useful for messages (e.g. RPC calls) that embed
             * other messages inside them using the bytes type.
             */
            // FIXME: should we always allow this? Can this cause mistakes?
            var tempStream = new PROTO.ByteArrayStream;
            arr.SerializeToStream(tempStream);
            return tempStream.getArray();
        } else {
            throw "Not a Byte Array: "+arr;
        }
    },
    wiretype: PROTO.wiretypes.lengthdelim,
    SerializeToStream: function(arr, stream) {
        PROTO.int32.SerializeToStream(arr.length, stream);
        stream.write(arr);
    },
    ParseFromStream: function(stream) {
        var len = PROTO.int32.ParseFromStream(stream);
        return stream.read(len);
    },
    toString: function(bytes) {return '['+bytes+']';}
};

(function() {
    function makeclass(converter, serializer, parser) {
        var myclass = {
            Convert: converter,
            wiretype: 0,
            SerializeToStream: serializer,
            ParseFromStream: parser,
            toString: function(val) {return "" + val}
        };
        return myclass;
    };
    function convertU32(n) { //unsigned
        if (n == NaN) {
            throw "not a number: "+n;
        }
        n = Math.round(n);
        if (n < 0) {
            throw "uint32/fixed32 does not allow negative: "+n;
        }
        if (n > 4294967295) {
            throw "uint32/fixed32 out of bounds: "+n;
        }
        return n;
    };
    function convertS32(n) { // signed
        if (n == NaN) {
            throw "not a number: "+n;
        }
        n = Math.round(n);
        if (n > 2147483647 || n < -2147483648) {
            throw "sfixed32/[s]int32 out of bounds: "+n;
        }
        return n;
    };
    function serializeFixed32(n, stream) {
        if (n<0) n += 4294967296;
        var arr = new Array(4);
        for (var i = 0; i < 4; i++) {
            arr[i] = n%256;
            n >>>= 8;
        }
        stream.write(arr);
    };
    function parseSFixed32(stream) {
        var n = 0;
        var offset=1;
        for (var i = 0; i < 4; i++) {
            n += offset*stream.readByte();
            offset *= 256;
        }
        return n;
    };
    function parseFixed32(stream) {
        var n = parseSFixed32(stream);
        if (n > 2147483647) {
            n -= 4294967296;
        }
        return n;
    };
    function serializeInt32(n, stream) {
        if (n < 0) {
            serializeInt64(PROTO.I64.fromNumber(n),stream);
            return;
        }
        // Loop once regardless of whether n is 0.
        for (var i = 0; i==0 || (n && i < 5); i++) {
            var byt = n%128;
            n >>>= 7;
            if (n) {
                byt += 128;
            }
            stream.writeByte(byt);
        }
    };
    function serializeSInt32(n, stream) {
        if (n < 0) {
            n = -n*2-1;
        } else {
            n = n*2;
        }
        serializeInt32(n, stream);
    };
    function parseUInt32(stream) {
        var n = 0;
        var endloop = false;
        var offset=1;
        for (var i = 0; !endloop && i < 5; i++) {
            var byt = stream.readByte();
            if (byt === undefined) {
                console.log("read undefined byte from stream: n is "+n);
                break;
            }
            if (byt < 128) {
                endloop = true;
            }
            n += offset*(byt&(i==4?15:127));
            offset *= 128;
        }
        return n;
    };
    function parseInt32(stream) {
        var n = parseUInt32(stream);//snag the first 4 bytes
        if (n > 2147483647) {
            n -= 2147483647;
            n -= 2147483647;
            n -= 2;
        }
        return n;
    };
    function parseSInt32(stream) {
        var n = parseUInt32(stream);
        if (n & 1) {
            return (n+1) / -2;
        }
        return n / 2;
    }
    PROTO.sfixed32 = makeclass(convertS32, serializeFixed32, parseSFixed32);
    PROTO.fixed32 = makeclass(convertU32, serializeFixed32, parseFixed32);
    PROTO.sfixed32.wiretype = PROTO.wiretypes.fixed32;
    PROTO.fixed32.wiretype = PROTO.wiretypes.fixed32;
    PROTO.int32 = makeclass(convertS32, serializeInt32, parseInt32);
    PROTO.sint32 = makeclass(convertS32, serializeSInt32, parseSInt32);
    PROTO.uint32 = makeclass(convertU32, serializeInt32, parseUInt32);

    function convert64(n) {
        if (n instanceof PROTO.I64) {
            return n;
        }
        throw "64-bit integers must be PROTO.I64 objects!";
    };
    function serializeInt64(n, stream) {
        stream.write(n.convertToUnsigned().serializeToLEVar128());
    }
    function serializeSInt64(n, stream) {
        stream.write(n.convertToZigzag().serializeToLEVar128());
    }
    function serializeUInt64(n, stream) {
        stream.write(n.serializeToLEVar128());
    }
    function serializeSFixed64(n, stream) {
        stream.write(n.convertToUnsigned().serializeToLEBase256());
    }
    function serializeFixed64(n, stream) {
        stream.write(n.serializeToLEBase256());
    }
    function parseSFixed64(stream) {
        return PROTO.I64.parseLEBase256(stream).convertFromUnsigned();
    }
    function parseFixed64(stream) {
        return PROTO.I64.parseLEBase256(stream);
    }
    function parseSInt64(stream) {
        return PROTO.I64.parseLEVar128(stream).convertFromZigzag();
    }
    function parseInt64(stream) {
        return PROTO.I64.parseLEVar128(stream).convertFromUnsigned();
    }
    function parseUInt64(stream) {
        return PROTO.I64.parseLEVar128(stream);
    }
    PROTO.sfixed64 = makeclass(convert64, serializeSFixed64, parseSFixed64);
    PROTO.fixed64 = makeclass(convert64, serializeFixed64, parseFixed64);
    PROTO.sfixed64.wiretype = PROTO.wiretypes.fixed64;
    PROTO.fixed64.wiretype = PROTO.wiretypes.fixed64;
    PROTO.int64 = makeclass(convert64, serializeInt64, parseInt64);
    PROTO.sint64 = makeclass(convert64, serializeSInt64, parseSInt64);
    PROTO.uint64 = makeclass(convert64, serializeUInt64, parseUInt64);

    PROTO.bool = makeclass(function(bool) {return bool?true:false;},
                           serializeInt32,
                           parseUInt32);

    function convertFloatingPoint(f) {
        var n = parseFloat(f);
        if (n == NaN) {
            throw "not a number: "+f;
        }
        return n;
    };
    function writeFloat(flt, stream) {
        stream.write(PROTO.binaryParser.fromFloat(flt));
    };
    function readFloat(stream) {
        var arr = stream.read(4);
        return PROTO.binaryParser.toFloat(arr);
    };
    function writeDouble(flt, stream) {
        stream.write(PROTO.binaryParser.fromDouble(flt));
    };
    function readDouble(stream) {
        var arr = stream.read(8);
        return PROTO.binaryParser.toDouble(arr);
    };
    PROTO.Float = makeclass(convertFloatingPoint, writeFloat, readFloat);
    PROTO.Double = makeclass(convertFloatingPoint, writeDouble, readDouble);
    PROTO.Float.wiretype = PROTO.wiretypes.fixed32;
    PROTO.Double.wiretype = PROTO.wiretypes.fixed64;
})();


PROTO.mergeProperties = function(properties, stream, values) {
    var fidToProp = {};
    for (var key in properties) {
        fidToProp[properties[key].id] = key;
    }
    var nextfid, nexttype, nextprop, nextproptype, nextval, nextpropname;
    var incompleteTuples = {};
    while (stream.valid()) {
        nextfid = PROTO.int32.ParseFromStream(stream);
//        console.log(""+stream.read_pos_+" ; "+stream.array_.length);
        nexttype = nextfid % 8;
        nextfid >>>= 3;
        nextpropname = fidToProp[nextfid];
        nextprop = nextpropname && properties[nextpropname];
        nextproptype = nextprop && nextprop.type();
        nextval = undefined;
        switch (nexttype) {
        case PROTO.wiretypes.varint:
//        console.log("read varint field is "+nextfid);
            if (nextprop && nextproptype.wiretype == PROTO.wiretypes.varint) {
                nextval = nextproptype.ParseFromStream(stream);
            } else {
                PROTO.int64.ParseFromStream(stream);
            }
            break;
        case PROTO.wiretypes.fixed64:
//        console.log("read fixed64 field is "+nextfid);
            if (nextprop && nextproptype.wiretype == PROTO.wiretypes.fixed64) {
                nextval = nextproptype.ParseFromStream(stream);
            } else {
                PROTO.fixed64.ParseFromStream(stream);
            }
            break;
        case PROTO.wiretypes.lengthdelim:
//        console.log("read lengthdelim field is "+nextfid);
            if (nextprop) {
                if (nextproptype.wiretype != PROTO.wiretypes.lengthdelim)
                {
                    var tup;
                    if (nextproptype.cardinality>1) {
                        if (incompleteTuples[nextpropname]===undefined) {
                            incompleteTuples[nextpropname]=new Array();
                        }
                        tup = incompleteTuples[nextpropname];
                    }
                    var bytearr = PROTO.bytes.ParseFromStream(stream);
                    var bas = new PROTO.ByteArrayStream(bytearr);
                    for (var j = 0; j < bytearr.length && bas.valid(); j++) {
                        var toappend = nextproptype.ParseFromStream(bas);

                        if (nextproptype.cardinality>1) {
                            tup.push(toappend);
                            if (tup.length==nextproptype.cardinality) {
                                if (nextprop.multiplicity == PROTO.repeated) {
                                    values[nextpropname].push(tup);
                                } else {
                                    values[nextpropname] =
                                        nextproptype.Convert(tup);
                                }
                                incompleteTuples[nextpropname]=new Array();
                                tup = incompleteTuples[nextpropname];
                            }
                        }else {
                            values[nextpropname].push(toappend);
                        }
                    }
                } else {
                    nextval = nextproptype.ParseFromStream(stream);
                    if (nextval == null) {
                        return false;
                    }
                }
            } else {
                PROTO.bytes.ParseFromStream(stream);
            }
            break;
        case PROTO.wiretypes.fixed32:
//        console.log("read fixed32 field is "+nextfid);
            if (nextprop && nextproptype.wiretype == PROTO.wiretypes.fixed32) {
                nextval = nextproptype.ParseFromStream(stream);
            } else {
                PROTO.fixed32.ParseFromStream(stream);
            }
            break;
        default:
            console.log("ERROR: Unknown type "+nexttype+" for "+nextfid);
            break;
        }
        if (nextval !== undefined) {
            if (values[nextpropname] === undefined && nextproptype.cardinality>1) {
                values[nextpropname] = {};
            }
            if (nextproptype.cardinality>1) {
                var tup;
                if (incompleteTuples[nextpropname]===undefined) {
                    incompleteTuples[nextpropname]=new Array();
                    tup = incompleteTuples[nextpropname];
                }
                tup.push(nextval);
                if (tup.length==nextproptype.cardinality) {
                    if (nextprop.multiplicity == PROTO.repeated) {
                        values[nextpropname].push(tup);
                    } else {
                        tup = nextproptype.Convert(tup);
                        if (!PROTO.DefineProperty && nextproptype.FromProto) {
                            tup = nextproptype.FromProto(tup);
                        }
                        values[nextpropname] = tup;
                    }
                    incompleteTuples[nextpropname] = undefined;
                }
            } else if (nextprop.multiplicity === PROTO.repeated) {
                values[nextpropname].push(nextval);
            } else {
                nextval = nextproptype.Convert(nextval);
                if (!PROTO.DefineProperty && nextproptype.FromProto) {
                    nextval = nextproptype.FromProto(nextval);
                }
                values[nextpropname] = nextval;
            }
        }
    }
    return true;
};

/*
    var str = '{';
    for (var key in property) {
        str+=key+': '+property[key]+', ';
    }
    str+='}';
    throw str;
*/

PROTO.serializeTupleProperty = function(property, stream, value) {
    var fid = property.id;
    var wiretype = property.type().wiretype;
    var wireId = fid * 8 + wiretype;
//    console.log("Serializing property "+fid+" as "+wiretype+" pos is "+stream.write_pos_);
    if (wiretype != PROTO.wiretypes.lengthdelim && property.options.packed) {
        var bytearr = new Array();
        // Don't know length beforehand.
        var bas = new PROTO.ByteArrayStream(bytearr);
        if (property.multiplicity == PROTO.repeated) {
            for (var i = 0; i < value.length; i++) {
                var val = property.type().Convert(value[i]);
                for (var j=0;j<property.type().cardinality;++j) {
                    property.type().SerializeToStream(val[j], bas);
                }
            }
        }else {
            var val = property.type().Convert(value);
            for (var j=0;j<property.type().cardinality;++j) {
                property.type().SerializeToStream(val[j], bas);
            }
        }
        wireId = fid * 8 + PROTO.wiretypes.lengthdelim;
        PROTO.int32.SerializeToStream(wireId, stream);
        PROTO.bytes.SerializeToStream(bytearr, stream);
    } else {
        if (property.multiplicity == PROTO.repeated) {
            for (var i = 0; i < value.length; i++) {
                var val = property.type().Convert(value[i]);
                for (var j=0;j<property.type().cardinality;++j) {
                    PROTO.int32.SerializeToStream(wireId, stream);
                    property.type().SerializeToStream(val[j], stream);
                }
            }
        }else {
            var val = property.type().Convert(value);
            for (var j=0;j<property.type().cardinality;++j) {
                PROTO.int32.SerializeToStream(wireId, stream);
                property.type().SerializeToStream(val[j], stream);
            }
        }
    }
};
PROTO.serializeProperty = function(property, stream, value) {
    var fid = property.id;
    if (!property.type()) return;
    if (property.type().cardinality>1) {
        PROTO.serializeTupleProperty(property,stream,value);
        return;
    }
    var wiretype = property.type().wiretype;
    var wireId = fid * 8 + wiretype;
//    console.log("Serializing property "+fid+" as "+wiretype+" pos is "+stream.write_pos_);
    if (property.multiplicity == PROTO.repeated) {
        if (wiretype != PROTO.wiretypes.lengthdelim && property.options.packed) {
            var bytearr = new Array();
            // Don't know length beforehand.
            var bas = new PROTO.ByteArrayStream(bytearr);
            for (var i = 0; i < value.length; i++) {
                var val = property.type().Convert(value[i]);
                property.type().SerializeToStream(val, bas);
            }
            wireId = fid * 8 + PROTO.wiretypes.lengthdelim;
            PROTO.int32.SerializeToStream(wireId, stream);
            PROTO.bytes.SerializeToStream(bytearr, stream);
        } else {
            for (var i = 0; i < value.length; i++) {
                PROTO.int32.SerializeToStream(wireId, stream);
                var val = property.type().Convert(value[i]);
                property.type().SerializeToStream(val, stream);
            }
        }
    } else {
        PROTO.int32.SerializeToStream(wireId, stream);
        var val = property.type().Convert(value);
        property.type().SerializeToStream(val, stream);
    }
};


PROTO.Message = function(name, properties) {
    /** @constructor */
    var Composite = function() {
        this.properties_ = Composite.properties_;
        if (!PROTO.DefineProperty) {
            this.values_ = this;
        } else {
            this.values_ = {};
        }
        this.Clear();
        this.message_type_ = name;
    };
    Composite.properties_ = {};
    for (var key in properties) {
        // HACK: classes are currently included alongside properties.
        if (properties[key].isType) {
            Composite[key] = properties[key];
        } else {
            Composite.properties_[key] = properties[key];
        }
    }
    Composite.isType = true;
    Composite.composite = true;
    Composite.wiretype = PROTO.wiretypes.lengthdelim;
    Composite.IsInitialized = function(value) {
        return value && value.IsInitialized();
    };
    Composite.Convert = function Convert(val) {
        if (!(val instanceof Composite)) {
            throw "Value not instanceof "+name+": "+typeof(val)+" : "+val;
        }
        return val;
    };
    Composite.SerializeToStream = function(value, stream) {
        var bytearr = new Array();
        var bas = new PROTO.ByteArrayStream(bytearr);
        value.SerializeToStream(bas);
        return PROTO.bytes.SerializeToStream(bytearr, stream);
    };
    Composite.ParseFromStream = function(stream) {
        var bytearr = PROTO.bytes.ParseFromStream(stream);
        var bas = new PROTO.ByteArrayStream(bytearr);
        var ret = new Composite;
        ret.ParseFromStream(bas);
        return ret;
    };
    Composite.prototype = {
        computeHasFields: function computeHasFields() {
            var has_fields = {};
            for (var key in this.properties_) {
                if (this.HasField(key)) {
                    has_fields[key] = true;
                }
            }
            return has_fields;
        },
        Clear: function Clear() {
            for (var prop in this.properties_) {
                this.ClearField(prop);
            }
        },
        IsInitialized: function IsInitialized() {
            var checked_any = false;
            for (var key in this.properties_) {
                checked_any = true;
                if (this.values_[key] !== undefined) {
                    var descriptor = this.properties_[key];
                    if (!descriptor.type()) continue;
                    if (descriptor.multiplicity == PROTO.repeated) {
                        if (PROTO.array.IsInitialized(this.values_[key])) {
                            return true;
                        }
                    } else {
                        if (!descriptor.type().IsInitialized ||
                            descriptor.type().IsInitialized(this.values_[key]))
                        {
                            return true;
                        }
                    }
                }
            }
            // As a special case, if there weren't any fields, we
            // treat it as initialized. This allows us to send
            // messages that are empty, but whose presence indicates
            // something.
            if (!checked_any) return true;
            // Otherwise, we checked at least one and it failed, so we
            // must be uninitialized.
            return false;
        },
        ParseFromStream: function Parse(stream) {
            this.Clear();
            return this.MergeFromStream(stream);
        },
        MergeFromStream: function Merge(stream) {
            return PROTO.mergeProperties(this.properties_, stream, this.values_);
        },
        SerializeToStream: function Serialize(outstream) {
            var hasfields = this.computeHasFields();
            for (var key in hasfields) {
                var val = this.values_[key];
                PROTO.serializeProperty(this.properties_[key], outstream, val);
            }
        },
        SerializeToArray: function (opt_array) {
            var stream = new PROTO.ByteArrayStream(opt_array);
            this.SerializeToStream(stream);
            return stream.getArray();
        },
        MergeFromArray: function (array) {
            return this.MergeFromStream(new PROTO.ByteArrayStream(array));
        },
        ParseFromArray: function (array) {
            this.Clear();
            return this.MergeFromArray(array);
        },
        // Not implemented:
        // CopyFrom, MergeFrom, SerializePartialToX,
        // RegisterExtension, Extensions, ClearExtension
        ClearField: function ClearField(propname) {
            var descriptor = this.properties_[propname];
            if (descriptor.multiplicity == PROTO.repeated) {
                this.values_[propname] = new PROTO.array(descriptor);
            } else {
                var type = descriptor.type();
                if (type && type.composite) {
                    // Don't special case this. Otherwise, we can't actually
                    // tell whether a composite child was initialized
                    // intentionally or if it just happened here.
                    //this.values_[propname] = new type();
                    delete this.values_[propname];
                } else {
                    delete this.values_[propname];
                }
            }
        },
        ListFields: function ListFields() {
            var ret = [];
            var hasfields = this.computeHasFields();
            for (var f in hasfields) {
                ret.push(f);
            }
            return ret;
        },
        GetField: function GetField(propname) {
            //console.log(propname);
            var ret = this.values_[propname];
            var type = this.properties_[propname].type();
            if (ret && type.FromProto) {
                return type.FromProto(ret);
            }
            return ret;
        },
        SetField: function SetField(propname, value) {
            //console.log(propname+"="+value);
            if (value === undefined || value === null) {
                this.ClearField(propname);
            } else {
                var prop = this.properties_[propname];
                if (prop.multiplicity == PROTO.repeated) {
                    this.ClearField(propname);
                    for (var i = 0; i < value.length; i++) {
                        this.values_[propname].push(
                                prop.type().Convert(value[i]));
                    }
                } else {
                    this.values_[propname] = prop.type().Convert(value);
                }
            }
        },
        HasField: function HasField(propname) {
            if (this.values_[propname] !== undefined) {
                var descriptor = this.properties_[propname];
                if (!descriptor.type()) {
                    return false;
                }
                if (descriptor.multiplicity == PROTO.repeated) {
                    return PROTO.array.IsInitialized(this.values_[propname]);
                } else {
                    if (!descriptor.type().IsInitialized ||
                        descriptor.type().IsInitialized(
                            this.values_[propname]))
                    {
                        return true;
                    }
                }
            }
            return false;
        },
        formatValue: function(level, spaces, propname, val) {
            var str = spaces + propname;
            var type = this.properties_[propname].type();
            if (type.composite) {
                str += " " + val.toString(level+1);
            } else if (typeof val == 'string') {
                var myval = val;
                myval = myval.replace("\"", "\\\"")
                             .replace("\n", "\\n")
                             .replace("\r","\\r");
                str += ": \"" + myval + "\"\n";
            } else {
                if (type.FromProto) {
                    val = type.FromProto(val);
                }
                if (type.toString) {
                    var myval = type.toString(val);
                    str += ": " + myval + "\n";
                } else {
                    str += ": " + val + "\n";
                }
            }
            return str;
        },
        toString: function toString(level) {
            var spaces = "";
            var str = "";
            if (level) {
                str = "{\n";
                for (var i = 0 ; i < level*2; i++) {
                    spaces += " ";
                }
            } else {
                level = 0;
            }
            for (var propname in this.properties_) {
                if (!this.properties_[propname].type()) {
                    continue; // HACK:
                }
                if (!this.HasField(propname)) {
                    continue;
                }
                if (this.properties_[propname].multiplicity == PROTO.repeated) {
                    var arr = this.values_[propname];
                    for (var i = 0; i < arr.length; i++) {
                        str += this.formatValue(level, spaces, propname, arr[i]);
                    }
                } else {
                    str += this.formatValue(level, spaces, propname,
                                            this.values_[propname]);
                }
            }
            if (level) {
                str += "}\n";
            }
            return str;
        }
    };
    if (PROTO.DefineProperty !== undefined) {
        for (var prop in Composite.properties_) {
            (function(prop){
            PROTO.DefineProperty(Composite.prototype, prop,
                           function GetProp() { return this.GetField(prop); },
                           function SetProp(newval) { this.SetField(prop, newval); });
            })(prop);
        }
    }
    return Composite;
};

/** Builds an enumeration type with a mapping of values.
@param {number=} bits  Preferred size of the enum (unused at the moment). */
PROTO.Enum = function (name, values, bits) {
    if (!bits) {
        bits = 32;
    }
    var reverseValues = {};
    var enumobj = {};
    enumobj.isType = true;
    for (var key in values) {
        reverseValues[values[key] ] = key;
        enumobj[key] = values[key];
        enumobj[values[key]] = key;
    }
    enumobj.values = values;
    enumobj.reverseValues = reverseValues;

    enumobj.Convert = function Convert(s) {
        if (typeof s == 'number') {
            // (reverseValues[s] !== undefined)
            return s;
        }
        if (values[s] !== undefined) {
            return values[s]; // Convert string -> int
        }
        throw "Not a valid "+name+" enumeration value: "+s;
    };
    enumobj.toString = function toString(num) {
        if (reverseValues[num]) {
            return reverseValues[num];
        }
        return "" + num;
    };
    enumobj.ParseFromStream = function(a,b) {
        var e = PROTO.int32.ParseFromStream(a,b);
        return e;
    }
    enumobj.SerializeToStream = function(a,b) {
        return PROTO.int32.SerializeToStream(a,b);
    }
    enumobj.wiretype = PROTO.wiretypes.varint;

    return enumobj;
};
PROTO.Flags = function(bits, name, values) {
    return PROTO.Enum(name, values, bits);
};

PROTO.Extend = function(parent, newproperties) {
    for (var key in newproperties) {
        parent.properties_[key] = newproperties[key];
    }
    return parent;
};

//////// DEBUG
if (typeof(console)=="undefined") console = {};
if (typeof(console.log)=="undefined") console.log = function(message){
    //if (document && document.body)
    //    document.body.appendChild(document.createTextNode(message+"..."));
};


/*  ProtoJS - Protocol buffers for Javascript.
 *  pbj.js
 *
 *  Copyright (c) 2009-2010, Patrick Reiter Horn
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *  * Neither the name of ProtoJS nor the names of its contributors may
 *    be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

var PBJ = {};

/** Generates a protojs compatible vector class.
@param {boolean=} magsquared  True if this vector's elements sum to 1.
        If magsquared is set, num should be one less than the vector length. */
function vectorGenerator(num,datatype,magsquared) {
    if (!magsquared) magsquared=false;
    var ret = {
        Convert:function Convert(vec) {
            if (vec instanceof Array && vec.length==num) {
                return vec;
            } else if (vec instanceof Array && magsquared && vec.length==num+1) {
                var ret = vec.slice(0, num);
                if (vec[num] < 0) {
                    ret[0] += 3.0;
                }
                return ret;
            } else {
                console.error("Vector_in_invalid_format: "+vec+"; expect "+num+" elements.");
                return new Array(num);
            }
        },
        toString: function toString(vec) {
            var ret = '<'+vec[0];
            for (var i = 1; i < num+(magsquared?1:0); i++) {
                ret += ', '+vec[i];
            }
            ret += '>';
            return ret;
        },
        wiretype: datatype.wiretype,
        SerializeToStream: datatype.SerializeToStream,
        ParseFromStream: datatype.ParseFromStream,
        cardinality:num
    };
    if (magsquared) {
        if (num == 2) {
            ret.FromProto = function (vec) {
                var x = vec[0], y = vec[1];
                var neg=(x>1.5||y>1.5)?-1.0:1.0;
                if (x>1.5)
                    x-=3.0;
                if (y>1.5)
                    y-=3.0;
                return [x,y,neg*Math.sqrt(1-x*x-y*y)];
            };
        } else if (num == 3) {
            ret.FromProto = function (vec) {
                var x = vec[0], y = vec[1], z = vec[2];
                var neg=(x>1.5||y>1.5||z>1.5)?-1.0:1.0;
                if (x>1.5)
                    x-=3.0;
                if (y>1.5)
                    y-=3.0;
                if (z>1.5)
                    z-=3.0;
                return [x,y,z,neg*Math.sqrt(1-x*x-y*y-z*z)];
            };
        }
    }
    return ret;
};

PBJ.uint8 = PROTO.uint32
PBJ.uint16 = PROTO.uint32
PBJ.int8 = PROTO.int32
PBJ.int16 = PROTO.int32
PBJ.sint8 = PROTO.sint32
PBJ.sint16 = PROTO.sint32

PBJ.vector2d=vectorGenerator(2,PROTO.Double);
PBJ.vector2f=vectorGenerator(2,PROTO.Float);

PBJ.vector3d=vectorGenerator(3,PROTO.Double);
PBJ.vector3f=vectorGenerator(3,PROTO.Float);

PBJ.vector4d=vectorGenerator(4,PROTO.Double);
PBJ.vector4f=vectorGenerator(4,PROTO.Float);

PBJ.normal=vectorGenerator(2,PROTO.Float,true);
PBJ.unitquaternion=vectorGenerator(3,PROTO.Float,true);
PBJ.quaternion=vectorGenerator(4,PROTO.Float);

PBJ.duration = PROTO.cloneType(PROTO.sfixed64);
PBJ.duration.Convert = function(ms) {
    if (ms instanceof PROTO.I64) {
        return ms;
    } else {
        return PROTO.I64.fromNumber(ms*1000);
    }
}
PBJ.duration.FromProto = function(ms) {
    return ms.toNumber()/1000.;
}

PBJ.time = PROTO.cloneType(PROTO.fixed64);
PBJ.time.Convert = function(date) {
    if (date instanceof Date) {
        date = date.getTime()*1000;
    } else if (date instanceof PROTO.I64) {
        return date;
    }
    return PROTO.I64.fromNumber(date);
};
// Can fit us since 1970 into a double, with 2 extra bits of precision.
PBJ.time.toString = function(arg) {
    var ms1970,us;
    if (arg instanceof PROTO.I64) {
        var us1970 = arg.toNumber();
        ms1970 = Math.floor(us1970/1000);
        var sec1970 = Math.floor(us1970/1000000);
        us = arg.sub(PROTO.I64.fromNumber(sec1970*1000000)).toNumber();
    } else {
        ms1970 = arg;
        var us1970 = arg*1000;
        var sec1970 = Math.floor(ms1970/1000);
        us = us1970 - (sec1970*1000000);
    }
    if (us < 0) { us += 1000000; }
    return "[" + new Date(ms1970).toUTCString() + "]." +
        (1000000+us).toString().substr(1);
};
PBJ.time.FromProto = function(i64) {
    return i64.toNumber()/1000.;
};
(function () {

    var zero = '0'.charCodeAt(0);
    var a = 'a'.charCodeAt(0);
    var A = 'A'.charCodeAt(0);
    var dash = '-'.charCodeAt(0);

    function hexCharToNumber(c) {
        if (c >= zero && c < zero+10) {
            return (c - zero);
        } else if (c >= a && c < a+6) {
            return 10+(c - a);
        } else if (c >= A && c < A+6) {
            return 10+(c - A);
        }
        return 0;
    }

    function hexToArray(str, arrlen) {
        var ret = new Array(arrlen);
        var strlen = str.length;
        for (var i = 0, j = 0; i < strlen || j < arrlen; i+=2, j++) {
            var charcode2, charcode = str.charCodeAt(i);
            if (charcode == dash) {
                i++;
                charcode = str.charCodeAt(i);
            }
            charcode2 = str.charCodeAt(i+1);
            ret[j] = hexCharToNumber(charcode)*16 + hexCharToNumber(charcode2);
        }
        return ret;
    }

    PBJ.sha256 = PROTO.cloneType(PROTO.bytes);
    PBJ.sha256.Convert = function(arg) {
        if (arg instanceof Array) {
            return PROTO.bytes.Convert(arg);
        }
        return hexToArray(arg, 32);
    };
    PBJ.sha256.toString = function(arg) {
        if (typeof arg == "string") {
            return arg;
        }
        var str = '';
        for (var i = 0; i < arg.length && i < 32; i++) {
            str += (256+arg[i]).toString(16).substr(1);
        }
        while (str.length < 64) {
            str += '0';
        }
        return str;
    };
    PBJ.sha256.FromProto = PBJ.sha256.toString;

    PBJ.uuid = PROTO.cloneType(PROTO.bytes);
    PBJ.uuid.Convert = function(arg) {
        if (arg instanceof Array) {
            return PROTO.bytes.Convert(arg);
        }
        return hexToArray(arg, 16);
    };
    PBJ.uuid.toString = function(arg) {
        if (typeof arg == "string") {
            return arg;
        }
        var str = '';
        for (var i = 0; i < 16; i++) {
            if (i == 4 || i == 6 || i == 8 || i == 10) {
                str += '-'
            }
            if (i >= arg.length) {
                str += "00";
            } else {
                str += (256+arg[i]).toString(16).substr(1);
            }
        }
        return str;
    };
    PBJ.uuid.FromProto = PBJ.uuid.toString;
})();

PBJ.angle = PROTO.Float;

PBJ.boundingsphere3f=vectorGenerator(4,PROTO.Float);
PBJ.boundingsphere3d=vectorGenerator(4,PROTO.Double);
PBJ.boundingbox3f3f=vectorGenerator(6,PROTO.Float);
PBJ.boundingbox3d3f=vectorGenerator(6,PROTO.Double);


/*
---

name: Locale.en-US.Date

description: Date messages for US English.

license: MIT-style license

authors:
  - Aaron Newton

requires:
  - /Locale

provides: [Locale.en-US.Date]

...
*/

Locale.define('en-US', 'Date', {

	months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
	months_abbr: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	days: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
	days_abbr: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],

	// Culture's date order: MM/DD/YYYY
	dateOrder: ['month', 'date', 'year'],
	shortDate: '%m/%d/%Y',
	shortTime: '%I:%M%p',
	AM: 'AM',
	PM: 'PM',
	firstDayOfWeek: 0,

	// Date.Extras
	ordinal: function(dayOfMonth){
		// 1st, 2nd, 3rd, etc.
		return (dayOfMonth > 3 && dayOfMonth < 21) ? 'th' : ['th', 'st', 'nd', 'rd', 'th'][Math.min(dayOfMonth % 10, 4)];
	},

	lessThanMinuteAgo: 'less than a minute ago',
	minuteAgo: 'about a minute ago',
	minutesAgo: '{delta} minutes ago',
	hourAgo: 'about an hour ago',
	hoursAgo: 'about {delta} hours ago',
	dayAgo: '1 day ago',
	daysAgo: '{delta} days ago',
	weekAgo: '1 week ago',
	weeksAgo: '{delta} weeks ago',
	monthAgo: '1 month ago',
	monthsAgo: '{delta} months ago',
	yearAgo: '1 year ago',
	yearsAgo: '{delta} years ago',

	lessThanMinuteUntil: 'less than a minute from now',
	minuteUntil: 'about a minute from now',
	minutesUntil: '{delta} minutes from now',
	hourUntil: 'about an hour from now',
	hoursUntil: 'about {delta} hours from now',
	dayUntil: '1 day from now',
	daysUntil: '{delta} days from now',
	weekUntil: '1 week from now',
	weeksUntil: '{delta} weeks from now',
	monthUntil: '1 month from now',
	monthsUntil: '{delta} months from now',
	yearUntil: '1 year from now',
	yearsUntil: '{delta} years from now'

});


/*
---

script: Date.js

name: Date

description: Extends the Date native object to include methods useful in managing dates.

license: MIT-style license

authors:
  - Aaron Newton
  - Nicholas Barthelemy - https://svn.nbarthelemy.com/date-js/
  - Harald Kirshner - mail [at] digitarald.de; http://digitarald.de
  - Scott Kyle - scott [at] appden.com; http://appden.com

requires:
  - Core/Array
  - Core/String
  - Core/Number
  - MooTools.More
  - Locale
  - Locale.en-US.Date

provides: [Date]

...
*/

(function(){

var Date = this.Date;

var DateMethods = Date.Methods = {
	ms: 'Milliseconds',
	year: 'FullYear',
	min: 'Minutes',
	mo: 'Month',
	sec: 'Seconds',
	hr: 'Hours'
};

['Date', 'Day', 'FullYear', 'Hours', 'Milliseconds', 'Minutes', 'Month', 'Seconds', 'Time', 'TimezoneOffset',
	'Week', 'Timezone', 'GMTOffset', 'DayOfYear', 'LastMonth', 'LastDayOfMonth', 'UTCDate', 'UTCDay', 'UTCFullYear',
	'AMPM', 'Ordinal', 'UTCHours', 'UTCMilliseconds', 'UTCMinutes', 'UTCMonth', 'UTCSeconds', 'UTCMilliseconds'].each(function(method){
	Date.Methods[method.toLowerCase()] = method;
});

var pad = function(n, digits, string){
	if (digits == 1) return n;
	return n < Math.pow(10, digits - 1) ? (string || '0') + pad(n, digits - 1, string) : n;
};

Date.implement({

	set: function(prop, value){
		prop = prop.toLowerCase();
		var method = DateMethods[prop] && 'set' + DateMethods[prop];
		if (method && this[method]) this[method](value);
		return this;
	}.overloadSetter(),

	get: function(prop){
		prop = prop.toLowerCase();
		var method = DateMethods[prop] && 'get' + DateMethods[prop];
		if (method && this[method]) return this[method]();
		return null;
	}.overloadGetter(),

	clone: function(){
		return new Date(this.get('time'));
	},

	increment: function(interval, times){
		interval = interval || 'day';
		times = times != null ? times : 1;

		switch (interval){
			case 'year':
				return this.increment('month', times * 12);
			case 'month':
				var d = this.get('date');
				this.set('date', 1).set('mo', this.get('mo') + times);
				return this.set('date', d.min(this.get('lastdayofmonth')));
			case 'week':
				return this.increment('day', times * 7);
			case 'day':
				return this.set('date', this.get('date') + times);
		}

		if (!Date.units[interval]) throw new Error(interval + ' is not a supported interval');

		return this.set('time', this.get('time') + times * Date.units[interval]());
	},

	decrement: function(interval, times){
		return this.increment(interval, -1 * (times != null ? times : 1));
	},

	isLeapYear: function(){
		return Date.isLeapYear(this.get('year'));
	},

	clearTime: function(){
		return this.set({hr: 0, min: 0, sec: 0, ms: 0});
	},

	diff: function(date, resolution){
		if (typeOf(date) == 'string') date = Date.parse(date);

		return ((date - this) / Date.units[resolution || 'day'](3, 3)).round(); // non-leap year, 30-day month
	},

	getLastDayOfMonth: function(){
		return Date.daysInMonth(this.get('mo'), this.get('year'));
	},

	getDayOfYear: function(){
		return (Date.UTC(this.get('year'), this.get('mo'), this.get('date') + 1)
			- Date.UTC(this.get('year'), 0, 1)) / Date.units.day();
	},

	setDay: function(day, firstDayOfWeek){
		if (firstDayOfWeek == null){
			firstDayOfWeek = Date.getMsg('firstDayOfWeek');
			if (firstDayOfWeek === '') firstDayOfWeek = 1;
		}

		day = (7 + Date.parseDay(day, true) - firstDayOfWeek) % 7;
		var currentDay = (7 + this.get('day') - firstDayOfWeek) % 7;

		return this.increment('day', day - currentDay);
	},

	getWeek: function(firstDayOfWeek){
		if (firstDayOfWeek == null){
			firstDayOfWeek = Date.getMsg('firstDayOfWeek');
			if (firstDayOfWeek === '') firstDayOfWeek = 1;
		}

		var date = this,
			dayOfWeek = (7 + date.get('day') - firstDayOfWeek) % 7,
			dividend = 0,
			firstDayOfYear;

		if (firstDayOfWeek == 1){
			// ISO-8601, week belongs to year that has the most days of the week (i.e. has the thursday of the week)
			var month = date.get('month'),
				startOfWeek = date.get('date') - dayOfWeek;

			if (month == 11 && startOfWeek > 28) return 1; // Week 1 of next year

			if (month == 0 && startOfWeek < -2){
				// Use a date from last year to determine the week
				date = new Date(date).decrement('day', dayOfWeek);
				dayOfWeek = 0;
			}

			firstDayOfYear = new Date(date.get('year'), 0, 1).get('day') || 7;
			if (firstDayOfYear > 4) dividend = -7; // First week of the year is not week 1
		} else {
			// In other cultures the first week of the year is always week 1 and the last week always 53 or 54.
			// Days in the same week can have a different weeknumber if the week spreads across two years.
			firstDayOfYear = new Date(date.get('year'), 0, 1).get('day');
		}

		dividend += date.get('dayofyear');
		dividend += 6 - dayOfWeek; // Add days so we calculate the current date's week as a full week
		dividend += (7 + firstDayOfYear - firstDayOfWeek) % 7; // Make up for first week of the year not being a full week

		return (dividend / 7);
	},

	getOrdinal: function(day){
		return Date.getMsg('ordinal', day || this.get('date'));
	},

	getTimezone: function(){
		return this.toString()
			.replace(/^.*? ([A-Z]{3}).[0-9]{4}.*$/, '$1')
			.replace(/^.*?\(([A-Z])[a-z]+ ([A-Z])[a-z]+ ([A-Z])[a-z]+\)$/, '$1$2$3');
	},

	getGMTOffset: function(){
		var off = this.get('timezoneOffset');
		return ((off > 0) ? '-' : '+') + pad((off.abs() / 60).floor(), 2) + pad(off % 60, 2);
	},

	setAMPM: function(ampm){
		ampm = ampm.toUpperCase();
		var hr = this.get('hr');
		if (hr > 11 && ampm == 'AM') return this.decrement('hour', 12);
		else if (hr < 12 && ampm == 'PM') return this.increment('hour', 12);
		return this;
	},

	getAMPM: function(){
		return (this.get('hr') < 12) ? 'AM' : 'PM';
	},

	parse: function(str){
		this.set('time', Date.parse(str));
		return this;
	},

	isValid: function(date){
		if (!date) date = this;
		return typeOf(date) == 'date' && !isNaN(date.valueOf());
	},

	format: function(format){
		if (!this.isValid()) return 'invalid date';

		if (!format) format = '%x %X';
		if (typeof format == 'string') format = formats[format.toLowerCase()] || format;
		if (typeof format == 'function') return format(this);

		var d = this;
		return format.replace(/%([a-z%])/gi,
			function($0, $1){
				switch ($1){
					case 'a': return Date.getMsg('days_abbr')[d.get('day')];
					case 'A': return Date.getMsg('days')[d.get('day')];
					case 'b': return Date.getMsg('months_abbr')[d.get('month')];
					case 'B': return Date.getMsg('months')[d.get('month')];
					case 'c': return d.format('%a %b %d %H:%M:%S %Y');
					case 'd': return pad(d.get('date'), 2);
					case 'e': return pad(d.get('date'), 2, ' ');
					case 'H': return pad(d.get('hr'), 2);
					case 'I': return pad((d.get('hr') % 12) || 12, 2);
					case 'j': return pad(d.get('dayofyear'), 3);
					case 'k': return pad(d.get('hr'), 2, ' ');
					case 'l': return pad((d.get('hr') % 12) || 12, 2, ' ');
					case 'L': return pad(d.get('ms'), 3);
					case 'm': return pad((d.get('mo') + 1), 2);
					case 'M': return pad(d.get('min'), 2);
					case 'o': return d.get('ordinal');
					case 'p': return Date.getMsg(d.get('ampm'));
					case 's': return Math.round(d / 1000);
					case 'S': return pad(d.get('seconds'), 2);
					case 'T': return d.format('%H:%M:%S');
					case 'U': return pad(d.get('week'), 2);
					case 'w': return d.get('day');
					case 'x': return d.format(Date.getMsg('shortDate'));
					case 'X': return d.format(Date.getMsg('shortTime'));
					case 'y': return d.get('year').toString().substr(2);
					case 'Y': return d.get('year');
					case 'z': return d.get('GMTOffset');
					case 'Z': return d.get('Timezone');
				}
				return $1;
			}
		);
	},

	toISOString: function(){
		return this.format('iso8601');
	}

}).alias({
	toJSON: 'toISOString',
	compare: 'diff',
	strftime: 'format'
});

// The day and month abbreviations are standardized, so we cannot use simply %a and %b because they will get localized
var rfcDayAbbr = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
	rfcMonthAbbr = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

var formats = {
	db: '%Y-%m-%d %H:%M:%S',
	compact: '%Y%m%dT%H%M%S',
	'short': '%d %b %H:%M',
	'long': '%B %d, %Y %H:%M',
	rfc822: function(date){
		return rfcDayAbbr[date.get('day')] + date.format(', %d ') + rfcMonthAbbr[date.get('month')] + date.format(' %Y %H:%M:%S %Z');
	},
	rfc2822: function(date){
		return rfcDayAbbr[date.get('day')] + date.format(', %d ') + rfcMonthAbbr[date.get('month')] + date.format(' %Y %H:%M:%S %z');
	},
	iso8601: function(date){
		return (
			date.getUTCFullYear() + '-' +
			pad(date.getUTCMonth() + 1, 2) + '-' +
			pad(date.getUTCDate(), 2) + 'T' +
			pad(date.getUTCHours(), 2) + ':' +
			pad(date.getUTCMinutes(), 2) + ':' +
			pad(date.getUTCSeconds(), 2) + '.' +
			pad(date.getUTCMilliseconds(), 3) + 'Z'
		);
	}
};

var parsePatterns = [],
	nativeParse = Date.parse;

var parseWord = function(type, word, num){
	var ret = -1,
		translated = Date.getMsg(type + 's');
	switch (typeOf(word)){
		case 'object':
			ret = translated[word.get(type)];
			break;
		case 'number':
			ret = translated[word];
			if (!ret) throw new Error('Invalid ' + type + ' index: ' + word);
			break;
		case 'string':
			var match = translated.filter(function(name){
				return this.test(name);
			}, new RegExp('^' + word, 'i'));
			if (!match.length) throw new Error('Invalid ' + type + ' string');
			if (match.length > 1) throw new Error('Ambiguous ' + type);
			ret = match[0];
	}

	return (num) ? translated.indexOf(ret) : ret;
};

var startCentury = 1900,
	startYear = 70;

Date.extend({

	getMsg: function(key, args){
		return Locale.get('Date.' + key, args);
	},

	units: {
		ms: Function.from(1),
		second: Function.from(1000),
		minute: Function.from(60000),
		hour: Function.from(3600000),
		day: Function.from(86400000),
		week: Function.from(608400000),
		month: function(month, year){
			var d = new Date;
			return Date.daysInMonth(month != null ? month : d.get('mo'), year != null ? year : d.get('year')) * 86400000;
		},
		year: function(year){
			year = year || new Date().get('year');
			return Date.isLeapYear(year) ? 31622400000 : 31536000000;
		}
	},

	daysInMonth: function(month, year){
		return [31, Date.isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
	},

	isLeapYear: function(year){
		return ((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0);
	},

	parse: function(from){
		var t = typeOf(from);
		if (t == 'number') return new Date(from);
		if (t != 'string') return from;
		from = from.clean();
		if (!from.length) return null;

		var parsed;
		parsePatterns.some(function(pattern){
			var bits = pattern.re.exec(from);
			return (bits) ? (parsed = pattern.handler(bits)) : false;
		});

		if (!(parsed && parsed.isValid())){
			parsed = new Date(nativeParse(from));
			if (!(parsed && parsed.isValid())) parsed = new Date(from.toInt());
		}
		return parsed;
	},

	parseDay: function(day, num){
		return parseWord('day', day, num);
	},

	parseMonth: function(month, num){
		return parseWord('month', month, num);
	},

	parseUTC: function(value){
		var localDate = new Date(value);
		var utcSeconds = Date.UTC(
			localDate.get('year'),
			localDate.get('mo'),
			localDate.get('date'),
			localDate.get('hr'),
			localDate.get('min'),
			localDate.get('sec'),
			localDate.get('ms')
		);
		return new Date(utcSeconds);
	},

	orderIndex: function(unit){
		return Date.getMsg('dateOrder').indexOf(unit) + 1;
	},

	defineFormat: function(name, format){
		formats[name] = format;
		return this;
	},

	//<1.2compat>
	parsePatterns: parsePatterns,
	//</1.2compat>

	defineParser: function(pattern){
		parsePatterns.push((pattern.re && pattern.handler) ? pattern : build(pattern));
		return this;
	},

	defineParsers: function(){
		Array.flatten(arguments).each(Date.defineParser);
		return this;
	},

	define2DigitYearStart: function(year){
		startYear = year % 100;
		startCentury = year - startYear;
		return this;
	}

}).extend({
	defineFormats: Date.defineFormat.overloadSetter()
});

var regexOf = function(type){
	return new RegExp('(?:' + Date.getMsg(type).map(function(name){
		return name.substr(0, 3);
	}).join('|') + ')[a-z]*');
};

var replacers = function(key){
	switch (key){
		case 'T':
			return '%H:%M:%S';
		case 'x': // iso8601 covers yyyy-mm-dd, so just check if month is first
			return ((Date.orderIndex('month') == 1) ? '%m[-./]%d' : '%d[-./]%m') + '([-./]%y)?';
		case 'X':
			return '%H([.:]%M)?([.:]%S([.:]%s)?)? ?%p? ?%z?';
	}
	return null;
};

var keys = {
	d: /[0-2]?[0-9]|3[01]/,
	H: /[01]?[0-9]|2[0-3]/,
	I: /0?[1-9]|1[0-2]/,
	M: /[0-5]?\d/,
	s: /\d+/,
	o: /[a-z]*/,
	p: /[ap]\.?m\.?/,
	y: /\d{2}|\d{4}/,
	Y: /\d{4}/,
	z: /Z|[+-]\d{2}(?::?\d{2})?/
};

keys.m = keys.I;
keys.S = keys.M;

var currentLanguage;

var recompile = function(language){
	currentLanguage = language;

	keys.a = keys.A = regexOf('days');
	keys.b = keys.B = regexOf('months');

	parsePatterns.each(function(pattern, i){
		if (pattern.format) parsePatterns[i] = build(pattern.format);
	});
};

var build = function(format){
	if (!currentLanguage) return {format: format};

	var parsed = [];
	var re = (format.source || format) // allow format to be regex
	 .replace(/%([a-z])/gi,
		function($0, $1){
			return replacers($1) || $0;
		}
	).replace(/\((?!\?)/g, '(?:') // make all groups non-capturing
	 .replace(/ (?!\?|\*)/g, ',? ') // be forgiving with spaces and commas
	 .replace(/%([a-z%])/gi,
		function($0, $1){
			var p = keys[$1];
			if (!p) return $1;
			parsed.push($1);
			return '(' + p.source + ')';
		}
	).replace(/\[a-z\]/gi, '[a-z\\u00c0-\\uffff;\&]'); // handle unicode words

	return {
		format: format,
		re: new RegExp('^' + re + '$', 'i'),
		handler: function(bits){
			bits = bits.slice(1).associate(parsed);
			var date = new Date().clearTime(),
				year = bits.y || bits.Y;

			if (year != null) handle.call(date, 'y', year); // need to start in the right year
			if ('d' in bits) handle.call(date, 'd', 1);
			if ('m' in bits || bits.b || bits.B) handle.call(date, 'm', 1);

			for (var key in bits) handle.call(date, key, bits[key]);
			return date;
		}
	};
};

var handle = function(key, value){
	if (!value) return this;

	switch (key){
		case 'a': case 'A': return this.set('day', Date.parseDay(value, true));
		case 'b': case 'B': return this.set('mo', Date.parseMonth(value, true));
		case 'd': return this.set('date', value);
		case 'H': case 'I': return this.set('hr', value);
		case 'm': return this.set('mo', value - 1);
		case 'M': return this.set('min', value);
		case 'p': return this.set('ampm', value.replace(/\./g, ''));
		case 'S': return this.set('sec', value);
		case 's': return this.set('ms', ('0.' + value) * 1000);
		case 'w': return this.set('day', value);
		case 'Y': return this.set('year', value);
		case 'y':
			value = +value;
			if (value < 100) value += startCentury + (value < startYear ? 100 : 0);
			return this.set('year', value);
		case 'z':
			if (value == 'Z') value = '+00';
			var offset = value.match(/([+-])(\d{2}):?(\d{2})?/);
			offset = (offset[1] + '1') * (offset[2] * 60 + (+offset[3] || 0)) + this.getTimezoneOffset();
			return this.set('time', this - offset * 60000);
	}

	return this;
};

Date.defineParsers(
	'%Y([-./]%m([-./]%d((T| )%X)?)?)?', // "1999-12-31", "1999-12-31 11:59pm", "1999-12-31 23:59:59", ISO8601
	'%Y%m%d(T%H(%M%S?)?)?', // "19991231", "19991231T1159", compact
	'%x( %X)?', // "12/31", "12.31.99", "12-31-1999", "12/31/2008 11:59 PM"
	'%d%o( %b( %Y)?)?( %X)?', // "31st", "31st December", "31 Dec 1999", "31 Dec 1999 11:59pm"
	'%b( %d%o)?( %Y)?( %X)?', // Same as above with month and day switched
	'%Y %b( %d%o( %X)?)?', // Same as above with year coming first
	'%o %b %d %X %z %Y', // "Thu Oct 22 08:11:23 +0000 2009"
	'%T', // %H:%M:%S
	'%H:%M( ?%p)?' // "11:05pm", "11:05 am" and "11:05"
);

Locale.addEvent('change', function(language){
	if (Locale.get('Date')) recompile(language);
}).fireEvent('change', Locale.getCurrent());

})();


/*
---

name: Hash

description: Contains Hash Prototypes. Provides a means for overcoming the JavaScript practical impossibility of extending native Objects.

license: MIT-style license.

requires:
  - Core/Object
  - /MooTools.More

provides: [Hash]

...
*/

(function(){

if (this.Hash) return;

var Hash = this.Hash = new Type('Hash', function(object){
	if (typeOf(object) == 'hash') object = Object.clone(object.getClean());
	for (var key in object) this[key] = object[key];
	return this;
});

this.$H = function(object){
	return new Hash(object);
};

Hash.implement({

	forEach: function(fn, bind){
		Object.forEach(this, fn, bind);
	},

	getClean: function(){
		var clean = {};
		for (var key in this){
			if (this.hasOwnProperty(key)) clean[key] = this[key];
		}
		return clean;
	},

	getLength: function(){
		var length = 0;
		for (var key in this){
			if (this.hasOwnProperty(key)) length++;
		}
		return length;
	}

});

Hash.alias('each', 'forEach');

Hash.implement({

	has: Object.prototype.hasOwnProperty,

	keyOf: function(value){
		return Object.keyOf(this, value);
	},

	hasValue: function(value){
		return Object.contains(this, value);
	},

	extend: function(properties){
		Hash.each(properties || {}, function(value, key){
			Hash.set(this, key, value);
		}, this);
		return this;
	},

	combine: function(properties){
		Hash.each(properties || {}, function(value, key){
			Hash.include(this, key, value);
		}, this);
		return this;
	},

	erase: function(key){
		if (this.hasOwnProperty(key)) delete this[key];
		return this;
	},

	get: function(key){
		return (this.hasOwnProperty(key)) ? this[key] : null;
	},

	set: function(key, value){
		if (!this[key] || this.hasOwnProperty(key)) this[key] = value;
		return this;
	},

	empty: function(){
		Hash.each(this, function(value, key){
			delete this[key];
		}, this);
		return this;
	},

	include: function(key, value){
		if (this[key] == undefined) this[key] = value;
		return this;
	},

	map: function(fn, bind){
		return new Hash(Object.map(this, fn, bind));
	},

	filter: function(fn, bind){
		return new Hash(Object.filter(this, fn, bind));
	},

	every: function(fn, bind){
		return Object.every(this, fn, bind);
	},

	some: function(fn, bind){
		return Object.some(this, fn, bind);
	},

	getKeys: function(){
		return Object.keys(this);
	},

	getValues: function(){
		return Object.values(this);
	},

	toQueryString: function(base){
		return Object.toQueryString(this, base);
	}

});

Hash.alias({indexOf: 'keyOf', contains: 'hasValue'});


})();



if (typeof(UpoOpu)=="undefined") {UpoOpu = {};}
if (typeof(UpoOpu.pb)=="undefined") {UpoOpu.pb = {};}
//import "upoopu_enums.proto";
//import "upoopu_utopia_misc.proto";

UpoOpu.pb.IntelSoT = PROTO.Message("UpoOpu.pb.IntelSoT",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	race: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.RaceType;},
		id: 4
	},
	soldiers: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	ruler: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 6
	},
	honor: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.HonorType;},
		id: 7
	},
	personality: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.PersonalityType;},
		id: 8
	},
	monarch: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 9
	},
	sex: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.SexType;},
		id: 10
	},
	ospecs: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 11
	},
	land: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 12
	},
	dspecs: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 13
	},
	peasants: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 14
	},
	elites: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 15
	},
	building_eff: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 16
	},
	thieves: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 17
	},
	thieves_pct: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 18
	},
	gold: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 19
	},
	wizards: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 20
	},
	wizards_pct: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 21
	},
	food: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 22
	},
	horses: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 23
	},
	runes: {
		options: {default_value:0},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 24
	},
	prisoners: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	trade_balance: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 26
	},
	offense: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 27
	},
	defense: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 28
	},
	dragon: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.DragonType;},
		id: 29
	},
	relations: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.RelationType;},
		id: 30
	},
	nw: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 31
	},
	gbp: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 32
	},
	has_plague: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 33
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 35
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	trust: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 36
	},
	accuracy: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 52
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 37
	},
	by_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 38
	},
	by_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 39
	},
	is_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 40
	},
	tick: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 41
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelSoM = PROTO.Message("UpoOpu.pb.IntelSoM",{
Army : PROTO.Message("UpoOpu.pb.IntelSoM.Army",{
	away: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 1
	},
	generals: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	soldiers: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 8
	},
	ospecs: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	dspecs: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	elites: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	horses: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	land: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 7
	}})
,
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	military_pct: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 4
	},
	wages: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 5
	},
	military_efficiency: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 6
	},
	off_me: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 7
	},
	def_me: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 8
	},
	defense: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 9
	},
	offense: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 10
	},
	army_home: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.IntelSoM.Army;},
		id: 11
	},
	army_out_1: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.IntelSoM.Army;},
		id: 12
	},
	army_out_2: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.IntelSoM.Army;},
		id: 13
	},
	army_out_3: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.IntelSoM.Army;},
		id: 14
	},
	army_out_4: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.IntelSoM.Army;},
		id: 15
	},
	army_out_5: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.IntelSoM.Army;},
		id: 21
	},
	training_ospecs: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 16
	},
	training_dspecs: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 17
	},
	training_elites: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 18
	},
	training_thieves: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 19
	},
	race: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.RaceType;},
		id: 20
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	trust: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 26
	},
	accuracy: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 52
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 29
	},
	by_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 27
	},
	by_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 28
	},
	is_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 30
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelSurvey = PROTO.Message("UpoOpu.pb.IntelSurvey",{
BuildingData : PROTO.Message("UpoOpu.pb.IntelSurvey.BuildingData",{
	building: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.BuildingType;},
		id: 1
	},
	quantity: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	percentage: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 3
	},
	incoming: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 4
	}})
,
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	workers: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	jobs: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	building_efficiency: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 6
	},
	workers_needed: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 7
	},
	buildings: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelSurvey.BuildingData;},
		id: 10
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	trust: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 26
	},
	accuracy: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 52
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 29
	},
	by_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 27
	},
	by_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 28
	},
	is_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 30
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelScience = PROTO.Message("UpoOpu.pb.IntelScience",{
ScienceData : PROTO.Message("UpoOpu.pb.IntelScience.ScienceData",{
	science: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.ScienceType;},
		id: 1
	},
	quantity: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	per_acre: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 3
	},
	incoming: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 4
	}})
,
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	sciences: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelScience.ScienceData;},
		id: 10
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	trust: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 26
	},
	accuracy: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 52
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 29
	},
	by_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 27
	},
	by_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 28
	},
	is_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 30
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelInfiltrate = PROTO.Message("UpoOpu.pb.IntelInfiltrate",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	thieves: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	by_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	by_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	trust: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 26
	},
	accuracy: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 52
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 29
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelKD = PROTO.Message("UpoOpu.pb.IntelKD",{
Province : PROTO.Message("UpoOpu.pb.IntelKD.Province",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	race: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.RaceType;},
		id: 2
	},
	land: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	nw: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	honor: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.HonorType;},
		id: 5
	},
	monarch: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 6
	},
	protection: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 7
	}})
,
	kingdom: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	by_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 13
	},
	by_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 14
	},
	relation_origin_to_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.RelationType;},
		id: 15
	},
	relation_kd_to_origin: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.RelationType;},
		id: 16
	},
	points_origin_to_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 17
	},
	points_kd_to_origin: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 18
	},
	stance: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.StanceType;},
		id: 6
	},
	wars_won: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 7
	},
	wars_concluded: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 8
	},
	avg_opponent_nw: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 9
	},
	provinces: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelKD.Province;},
		id: 10
	},
	war_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 11
	},
	war_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 12
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelKdNews = PROTO.Message("UpoOpu.pb.IntelKdNews",{
ProvinceAction : PROTO.Message("UpoOpu.pb.IntelKdNews.ProvinceAction",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	target_province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 4
	},
	target_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	target_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	acres_taken: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 7
	},
	attack: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.AttackType;},
		id: 8
	},
	tick: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 10
	},
	action: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 11
	}})
,
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 1
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	province_actions: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelKdNews.ProvinceAction;},
		id: 3
	},
	tick_start: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	tick_end: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 7
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelOp = PROTO.Message("UpoOpu.pb.IntelOp",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	spell: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.SpellType;},
		id: 4
	},
	thiefop: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.ThiefType;},
		id: 5
	},
	dur: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	delta_acres: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 7
	},
	delta_peasants: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 8
	},
	delta_troops: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 9
	},
	delta_wizards: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 10
	},
	delta_thieves: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 11
	},
	delta_specs: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 12
	},
	delta_soldiers: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 13
	},
	delta_elites: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 14
	},
	resource: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.ResourceType;},
		id: 15
	},
	delta: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.sint32;},
		id: 16
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 17
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelSelfSpells = PROTO.Message("UpoOpu.pb.IntelSelfSpells",{
Spell : PROTO.Message("UpoOpu.pb.IntelSelfSpells.Spell",{
	spell: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.SpellType;},
		id: 1
	},
	dur: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	}})
,
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	spells: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelSelfSpells.Spell;},
		id: 4
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelAid = PROTO.Message("UpoOpu.pb.IntelAid",{
Spell : PROTO.Message("UpoOpu.pb.IntelAid.Spell",{
	spell: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.SpellType;},
		id: 1
	},
	dur: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	}})
,
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	gold: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	runes: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	food: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	soldiers: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 7
	},
	poster: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 17
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelCombined = PROTO.Message("UpoOpu.pb.IntelCombined",{
	sots: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelSoT;},
		id: 1
	},
	soms: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelSoM;},
		id: 2
	},
	kds: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelKD;},
		id: 3
	},
	ops: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelOp;},
		id: 5
	},
	surveys: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelSurvey;},
		id: 6
	},
	kdnews: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelKdNews;},
		id: 7
	},
	science: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelScience;},
		id: 8
	},
	infiltrates: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelInfiltrate;},
		id: 9
	},
	attacks: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelAttack;},
		id: 11
	},
	selfspells: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelSelfSpells;},
		id: 12
	},
	aid: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelAid;},
		id: 13
	},
	current_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 10
	},
	historic_data: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelHistory;},
		id: 20
	}});


if (typeof(UpoOpu)=="undefined") {UpoOpu = {};}
if (typeof(UpoOpu.pb)=="undefined") {UpoOpu.pb = {};}

UpoOpu.pb.SexType= PROTO.Enum("UpoOpu.pb.SexType",{
		MALE :1,
		FEMALE :2});
UpoOpu.pb.RaceType= PROTO.Enum("UpoOpu.pb.RaceType",{
		HUMAN :1,
		ELF :2,
		DWARF :3,
		ORC :4,
		GNOME :5,
		HALFLING :6,
		DARKELF :7,
		AVIAN :8,
		UNDEAD :9,
		FAERY :10});
UpoOpu.pb.PersonalityType= PROTO.Enum("UpoOpu.pb.PersonalityType",{
		MERCHANT :1,
		SHEPHERD :2,
		SAGE :3,
		ROGUE :4,
		MYSTIC :5,
		WARRIOR :6,
		WARHERO :7,
		ARTISAN :8,
		TACTICIAN :9,
		CLERIC :10});
UpoOpu.pb.RelationType= PROTO.Enum("UpoOpu.pb.RelationType",{
		NOREL :1,
		UNFRIENDLY :2,
		HOSTILE :3,
		WAR :4,
		CF :5});
UpoOpu.pb.SpellType= PROTO.Enum("UpoOpu.pb.SpellType",{
		MINORPROTECTION :1,
		GREATERPROTECTION :2,
		FOG :3,
		MAGICSHIELD :4,
		MYSTICAURA :5,
		FERTILELANDS :6,
		NATURESBLESSING :7,
		LOVEANDPEACE :8,
		TREEOFGOLD :9,
		QUICKFEET :10,
		BUILDERSBOON :11,
		INSPIREARMY :12,
		ANONYMITY :13,
		INVISIBILITY :14,
		CLEARSIGHT :15,
		WARSPOILS :17,
		FANATICISM :18,
		FOUNTAINOFKNOWLEDGE :19,
		TOWNWATCH :20,
		AGGRESSION :21,
		ANIMATEDEAD :22,
		REFLECTMAGIC :23,
		SHADOWLIGHT :24,
		PATRIOTISM :25,
		PARADISE :26,
		MAGESFURY :44,
		BLOODLUST :45,
		STORMS :27,
		DROUGHTS :28,
		VERMIN :29,
		EXPOSETHIEVES :30,
		GREED :31,
		FOOLSGOLD :32,
		PITFALLS :33,
		FIREBALL :34,
		CHASTITY :35,
		LIGHTNINGSTRIKE :36,
		EXPLOSIONS :37,
		AMNESIA :38,
		NIGHTMARES :39,
		MYSTICVORTEX :40,
		METEORSHOWERS :41,
		TORNADOES :42,
		LANDLUST :43});
UpoOpu.pb.ThiefType= PROTO.Enum("UpoOpu.pb.ThiefType",{
		SPYONTHRONE :1,
		SNATCHNEWS :2,
		INFILTRATE :3,
		SURVEY :4,
		SPYONMILITARY :5,
		SPYONSCIENCES :6,
		SABOTAGEWIZARDS :7,
		ROBTHEGRANARIES :8,
		ROBTHEVAULTS :9,
		ROBTHETOWERS :10,
		KIDNAPPING :11,
		ARSON :12,
		GREATERARSON :13,
		NIGHTSTRIKE :14,
		INCITERIOTS :15,
		STEALHORSES :16,
		BRIBETHIEVES :17,
		BRIBEGENERALS :18,
		FREEPRISONERS :19,
		ASSASSINATEWIZARDS :20,
		PROPAGANDA :30});
UpoOpu.pb.ResourceType= PROTO.Enum("UpoOpu.pb.ResourceType",{
		RES_GOLD :1,
		RES_RUNES :2,
		RES_FOOD :3,
		RES_PEASANTS :4,
		RES_SOLDIERS :5,
		RES_OSPECS :6,
		RES_DSPECS :7,
		RES_ELITES :8,
		RES_THIEVES :9,
		RES_WIZARDS :10,
		RES_LAND :11,
		RES_SCIENCE :12,
		RES_HORSES :13,
		RES_PRISONERS :14,
		RES_SPECS :15,
		RES_TROOPS :16,
		RES_BUILDINGS :17});
UpoOpu.pb.DragonType= PROTO.Enum("UpoOpu.pb.DragonType",{
		GOLD :1,
		RUBY :2,
		SAPPHIRE :3,
		EMERALD :4,
		NODRAGON :5});
UpoOpu.pb.StanceType= PROTO.Enum("UpoOpu.pb.StanceType",{
		NORMAL :1,
		PEACEFUL :2,
		AGGRESSIVE :3,
		FORTIFIED :4,
		STANCEWAR :5});
UpoOpu.pb.BuildingType= PROTO.Enum("UpoOpu.pb.BuildingType",{
		BARRENLANDS :1,
		HOMES :2,
		FARMS :3,
		MILLS :4,
		BANKS :5,
		TRAININGGROUNDS :6,
		ARMOURIES :7,
		BARRACKS :8,
		FORTS :9,
		GUARDSTATIONS :10,
		HOSPITALS :11,
		GUILDS :12,
		TOWERS :13,
		THIEVESDENS :14,
		WATCHTOWERS :15,
		LIBRARIES :16,
		SCHOOLS :17,
		STABLES :18,
		DUNGEONS :19});
UpoOpu.pb.ScienceType= PROTO.Enum("UpoOpu.pb.ScienceType",{
		ALCHEMY :1,
		TOOLS :2,
		HOUSING :3,
		FOOD :4,
		MILITARY :5,
		THIEVERY :6,
		CHANNELING :7});
UpoOpu.pb.HonorType= PROTO.Enum("UpoOpu.pb.HonorType",{
		PEASANT :1,
		KNIGHT :2,
		LORD :3,
		BARON :4,
		VISCOUNT :5,
		COUNT :6,
		MARQUIS :7,
		DUKE :8,
		PRINCE :9});
UpoOpu.pb.AttackType= PROTO.Enum("UpoOpu.pb.AttackType",{
		TRADMARCH :1,
		RAZE :2,
		PLUNDER :3,
		LEARN :4,
		MASSACRE :5,
		CONQUEST :6,
		AMBUSH :7,
		FAILEDATTACK :8});
UpoOpu.pb.AttackOversendType= PROTO.Enum("UpoOpu.pb.AttackOversendType",{
		NOOVERSEND :1,
		EVOVERSEND :2,
		TBROVERSEND :3,
		CRUSHOVERSEND :4,
		LMROVERSEND :5,
		UNKNOWNOVERSEND :6});


if (typeof(UpoOpu)=="undefined") {UpoOpu = {};}
if (typeof(UpoOpu.pb)=="undefined") {UpoOpu.pb = {};}
//import "upoopu_enums.proto";

UpoOpu.pb.IntelAttack = PROTO.Message("UpoOpu.pb.IntelAttack",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	self_province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 4
	},
	self_kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	self_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	},
	kills: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 11
	},
	losses: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 12
	},
	taken_acres: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 13
	},
	taken_books: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 14
	},
	taken_peasants: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 15
	},
	destroyed_t_and_m: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 16
	},
	taken_gold: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 17
	},
	taken_runes: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 18
	},
	taken_food: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 19
	},
	destroyed_acres: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 20
	},
	attack_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.Float;},
		id: 21
	},
	attack_type: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.AttackType;},
		id: 22
	},
	oversend: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return UpoOpu.pb.AttackOversendType;},
		id: 23
	},
	unix_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 25
	},
	uto_time: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 53
	},
	parser_build: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 54
	},
	by_self: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 50
	},
	by_groups: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 51
	}});
UpoOpu.pb.IntelHistory = PROTO.Message("UpoOpu.pb.IntelHistory",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	honor: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.HonorType;},
		id: 4
	},
	peasants: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 5
	},
	soldiers: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 6
	},
	ospecs: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 7
	},
	dspecs: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 8
	},
	elites: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 9
	},
	horses: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 10
	},
	thieves: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 11
	},
	wizards: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 12
	},
	thieves_pct: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 13
	},
	wizards_pct: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 14
	},
	land: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 15
	},
	nw: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 16
	},
	gold: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 17
	},
	food: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 18
	},
	runes: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 19
	},
	building_eff: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 20
	},
	trade_balance: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.sint32;},
		id: 21
	},
	offense: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 22
	},
	defense: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 23
	},
	unix_time: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 35
	},
	trust: {
		options: {packed:true},
		multiplicity: PROTO.repeated,
		type: function(){return PROTO.int32;},
		id: 36
	}});
UpoOpu.pb.UserProvinces = PROTO.Message("UpoOpu.pb.UserProvinces",{
Province : PROTO.Message("UpoOpu.pb.UserProvinces.Province",{
	province: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	kd: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 2
	},
	isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	trust: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	t5: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bool;},
		id: 5
	},
	id: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 6
	}})
,
	provinces: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.UserProvinces.Province;},
		id: 1
	}});
UpoOpu.pb.IntelTargetFinder = PROTO.Message("UpoOpu.pb.IntelTargetFinder",{
Province : PROTO.Message("UpoOpu.pb.IntelTargetFinder.Province",{
	name: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 1
	},
	race: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 2
	},
	kd_isl: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 3
	},
	nw: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 4
	},
	kd_nw: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.int32;},
		id: 5
	},
	norm_data: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bytes;},
		id: 6
	},
	list_data: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.bytes;},
		id: 7
	}})
,
	provinces: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return UpoOpu.pb.IntelTargetFinder.Province;},
		id: 1
	}});


/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*jsl:option explicit*/

/**
 * @fileoverview log4js is a library to log in JavaScript in similar manner 
 * than in log4j for Java. The API should be nearly the same.
 * 
 * This file contains all log4js code and is the only file required for logging.
 * 
 * <h3>Example:</h3>
 * <pre>
 *  var log = new Log4js.getLogger("some-category-name"); //create logger instance
 *  log.setLevel(Log4js.Level.TRACE); //set the Level
 *  log.addAppender(new ConsoleAppender(log, false)); // console that launches in new window
 
 *  // if multiple appenders are set all will log
 *  log.addAppender(new ConsoleAppender(log, true)); // console that is in-line in the page
 *  log.addAppender(new FileAppender("C:\\somefile.log")); // file appender logs to C:\\somefile.log
 * 
 *  ...
 * 
 *  //call the log
 *  log.trace("trace me" );
 * </pre>
 *
 * @version 0.3
 * @author Stephan Strittmatter - http://jroller.com/page/stritti
 * @author Seth Chisamore - http://www.chisamore.com
 * @since 2005-05-20
 * Website: http://log4js.berlios.de
 */
var Log4js = {
	
	/** 
	 * Current version of log4js. 
	 * @static
	 * @final
	 */
  	version: "1.0",

	/**  
	 * Date of logger initialized.
	 * @static
	 * @final
	 */
	applicationStartDate: new Date(),
		
	/**  
	 * Hashtable of loggers.
	 * @static
	 * @final
	 * @private  
	 */
	loggers: {},
	
	/**
	 * Get a logger instance. Instance is cached on categoryName level.
	 * @param  {String} categoryName name of category to log to.
	 * @return {Logger} instance of logger for the category
	 * @static
	 */
	getLogger: function(categoryName) {
		
		// Use default logger if categoryName is not specified or invalid
		if (!(typeof categoryName == "string")) {
			categoryName = "[default]";
		}

		if (!Log4js.loggers[categoryName]) {
			// Create the logger for this name if it doesn't already exist
			Log4js.loggers[categoryName] = new Log4js.Logger(categoryName);
		}
		
		return Log4js.loggers[categoryName];
	},
	
	/**
	 * Get the default logger instance.
	 * @return {Logger} instance of default logger
	 * @static
	 */
	getDefaultLogger: function() {
		return Log4js.getLogger("[default]"); 
	},
	
  	/**
  	 * Atatch an observer function to an elements event browser independent.
  	 * 
  	 * @param element element to attach event
  	 * @param name name of event
  	 * @param observer observer method to be called
  	 * @private
  	 */
  	attachEvent: function (element, name, observer) {
		if (element.addEventListener) { //DOM event model
			element.addEventListener(name, observer, false);
    	} else if (element.attachEvent) { //M$ event model
			element.attachEvent('on' + name, observer);
    	}
	}
	
	/**
	 * Load a JS-script dynamically.
	 * @param {String} src
	 */
/*	$import: function (src) {
		var documentScripts = document.getElementsByTagName("script");
	
		for (index = 0; index < documentScripts.length; ++index)
		{
			var documentScript = documentScripts[index];
			if (documentScript.src == src) {
				return false;
			}
		}
		
		var script = document.createElement('script');
		script.type = 'text/javascript';
		script.src = src;
		document.getElementsByTagName('head')[0].appendChild(script); 
		
		return true;
	}
	*/
};

/**
 * Internal object extension (OO) methods.
 * 
 * @private
 * @ignore
 */
Log4js.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}
    
/**
 * Functions taken from Prototype library,  
 * didn't want to require for just few functions.
 * More info at {@link http://prototype.conio.net/}
 * @private
 */	
Log4js.bind = function(fn, object) {
  return function() {
        return fn.apply(object, arguments);
  };
};

/**
 * Log4js.Level Enumeration. Do not use directly. Use static objects instead.
 * @constructor
 * @param {Number} level number of level
 * @param {String} levelString String representation of level
 * @private
 */
Log4js.Level = function(level, levelStr) {
	this.level = level;
	this.levelStr = levelStr;
};

Log4js.Level.prototype =  {
	/** 
	 * converts given String to corresponding Level
	 * @param {String} sArg String value of Level
	 * @param {Log4js.Level} defaultLevel default Level, if no String representation
	 * @return Level object
	 * @type Log4js.Level
	 */
	toLevel: function(sArg, defaultLevel) {                  
				
		if(sArg === null) {
			return defaultLevel;
		}
		
		if(typeof sArg == "string") { 
			var s = sArg.toUpperCase();
			if(s == "ALL") {return Log4js.Level.ALL;}
			if(s == "DEBUG") {return Log4js.Level.DEBUG;}
			if(s == "INFO") {return Log4js.Level.INFO;}
			if(s == "WARN") {return Log4js.Level.WARN;}
			if(s == "ERROR") {return Log4js.Level.ERROR;}
			if(s == "FATAL") {return Log4js.Level.FATAL;}
			if(s == "OFF") {return Log4js.Level.OFF;}
			if(s == "TRACE") {return Log4js.Level.TRACE;}
			return defaultLevel;
		} else if(typeof sArg == "number") {
			switch(sArg) {
				case ALL_INT: return Log4js.Level.ALL;
				case DEBUG_INT: return Log4js.Level.DEBUG;
				case INFO_INT: return Log4js.Level.INFO;
				case WARN_INT: return Log4js.Level.WARN;
				case ERROR_INT: return Log4js.Level.ERROR;
				case FATAL_INT: return Log4js.Level.FATAL;
				case OFF_INT: return Log4js.Level.OFF;
				case TRACE_INT: return Log4js.Level.TRACE;
				default: return defaultLevel;
			}
		} else {
			return defaultLevel;	
		}
	},	
	/** 
	 * @return  converted Level to String
	 * @type String
	 */		
	toString: function() {
		return this.levelStr;	
	},
	/** 
	 * @return internal Number value of Level
	 * @type Number
	 */			
	valueOf: function() {
		return this.level;
	}
};

// Static variables
/** 
 * @private
 */
Log4js.Level.OFF_INT = Number.MAX_VALUE;
/** 
 * @private
 */
Log4js.Level.FATAL_INT = 50000;
/** 
 * @private
 */
Log4js.Level.ERROR_INT = 40000;
/** 
 * @private
 */
Log4js.Level.WARN_INT = 30000;
/** 
 * @private
 */
Log4js.Level.INFO_INT = 20000;
/** 
 * @private
 */
Log4js.Level.DEBUG_INT = 10000;
/** 
 * @private
 */
Log4js.Level.TRACE_INT = 5000;
/** 
 * @private
 */
Log4js.Level.ALL_INT = Number.MIN_VALUE;

/** 
 * Logging Level OFF - all disabled
 * @type Log4js.Level
 * @static
 */
Log4js.Level.OFF = new Log4js.Level(Log4js.Level.OFF_INT, "OFF");
/** 
 * Logging Level Fatal
 * @type Log4js.Level
 * @static
 */
Log4js.Level.FATAL = new Log4js.Level(Log4js.Level.FATAL_INT, "FATAL");
/** 
 * Logging Level Error
 * @type Log4js.Level
 * @static
 */
Log4js.Level.ERROR = new Log4js.Level(Log4js.Level.ERROR_INT, "ERROR"); 
/** 
 * Logging Level Warn
 * @type Log4js.Level
 * @static
 */
Log4js.Level.WARN = new Log4js.Level(Log4js.Level.WARN_INT, "WARN"); 
/** 
 * Logging Level Info
 * @type Log4js.Level
 * @static
 */
Log4js.Level.INFO = new Log4js.Level(Log4js.Level.INFO_INT, "INFO");     
/** 
 * Logging Level Debug
 * @type Log4js.Level
 * @static
 */
Log4js.Level.DEBUG = new Log4js.Level(Log4js.Level.DEBUG_INT, "DEBUG");  
/** 
 * Logging Level Trace
 * @type Log4js.Level
 * @static
 */
Log4js.Level.TRACE = new Log4js.Level(Log4js.Level.TRACE_INT, "TRACE");  
/** 
 * Logging Level All - All traces are enabled
 * @type Log4js.Level
 * @static
 */
Log4js.Level.ALL = new Log4js.Level(Log4js.Level.ALL_INT, "ALL"); 

/**
 * Log4js CustomEvent
 * @constructor
 * @author Corey Johnson - original code in Lumberjack (http://gleepglop.com/javascripts/logger/)
 * @author Seth Chisamore - adapted for Log4js
 * @private
 */
Log4js.CustomEvent = function() {
	this.listeners = [];
};

Log4js.CustomEvent.prototype = {
 
 	/**
 	 * @param method method to be added
 	 */ 
	addListener : function(method) {
		this.listeners.push(method);
	},

 	/**
 	 * @param method method to be removed
 	 */ 
	removeListener : function(method) {
		var foundIndexes = this.findListenerIndexes(method);

		for(var i = 0; i < foundIndexes.length; i++) {
			this.listeners.splice(foundIndexes[i], 1);
		}
	},

 	/**
 	 * @param handler
 	 */ 
	dispatch : function(handler) {
		for(var i = 0; i < this.listeners.length; i++) {
			try {
				this.listeners[i](handler);
			}
			catch (e) {
				log4jsLogger.warn("Could not run the listener " + this.listeners[i] + ". \n" + e);
			}
		}
	},

	/**
	 * @private
	 * @param method
	 */
	findListenerIndexes : function(method) {
		var indexes = [];
		for(var i = 0; i < this.listeners.length; i++) {			
			if (this.listeners[i] == method) {
				indexes.push(i);
			}
		}

		return indexes;
	}
};

/**
 * Models a logging event.
 * @constructor
 * @param {String} categoryName name of category
 * @param {Log4js.Level} level level of message
 * @param {String} message message to log
 * @param {Log4js.Logger} logger the associated logger
 * @author Seth Chisamore
 */
Log4js.LoggingEvent = function(categoryName, level, message, exception, logger) {
	/**
	 * the timestamp of the Logging Event
	 * @type Date
	 * @private
	 */
	this.startTime = new Date();
	/**
	 * category of event
	 * @type String
	 * @private
	 */
	this.categoryName = categoryName;
	/**
	 * the logging message
	 * @type String
	 * @private
	 */
	this.message = message;
	/**
	 * the logging exception
	 * @type Exception
	 * @private
	 */
	this.exception = exception;
	/**
	 * level of log
	 * @type Log4js.Level
	 * @private
	 */
	this.level = level;
	/**
	 * reference to logger
	 * @type Log4js.Logger
	 * @private
	 */
	this.logger = logger;
};

Log4js.LoggingEvent.prototype = {	
	/**
	 * get the timestamp formatted as String.
	 * @return {String} formatted timestamp
	 * @see Log4js#setDateFormat()
	 */
	getFormattedTimestamp: function() {
		if(this.logger) {
			return this.logger.getFormattedTimestamp(this.startTime);
		} else {
			return this.startTime.toGMTString();
		}
	}
};

/**
 * Logger to log messages to the defined appender.</p>
 * Default appender is Appender, which is ignoring all messages. Please
 * use setAppender() to set a specific appender (e.g. WindowAppender).
 * use {@see Log4js#getLogger(String)} to get an instance.
 * @constructor
 * @param name name of category to log to
 * @author Stephan Strittmatter
 */
Log4js.Logger = function(name) {
	this.loggingEvents = [];
	this.appenders = [];
	/** category of logger */
	this.category = name || "";
	/** level to be logged */
	this.level = Log4js.Level.FATAL;
	
	this.dateformat = Log4js.DateFormatter.DEFAULT_DATE_FORMAT;
	this.dateformatter = new Log4js.DateFormatter();
	
	this.onlog = new Log4js.CustomEvent();
	this.onclear = new Log4js.CustomEvent();
	
	/** appender to write in */
	this.appenders.push(new Log4js.Appender(this));
	
	// if multiple log objects are instantiated this will only log to the log 
	// object that is declared last can't seem to get the attachEvent method to 
	// work correctly
	try {
		window.onerror = this.windowError.bind(this);
	} catch (e) {
		//log4jsLogger.fatal(e);
	}
};

Log4js.Logger.prototype = {

	/**
	 * add additional appender. DefaultAppender always is there.
	 * @param appender additional wanted appender
	 */
	addAppender: function(appender) {
		if (appender instanceof Log4js.Appender) {
			appender.setLogger(this);
			this.appenders.push(appender);			
		} else {
			throw "Not instance of an Appender: " + appender;
		}
	},

	/**
	 * set Array of appenders. Previous Appenders are cleared and removed.
	 * @param {Array} appenders Array of Appenders
	 */
	setAppenders: function(appenders) {
		//clear first all existing appenders
		for(var i = 0; i < this.appenders.length; i++) {
			this.appenders[i].doClear();
		}
		
		this.appenders = appenders;
		
		for(var j = 0; j < this.appenders.length; j++) {
			this.appenders[j].setLogger(this);
		}
	},
	
	/**
	 * Set the Loglevel default is LogLEvel.TRACE
	 * @param level wanted logging level
	 */
	setLevel: function(level) {
		this.level = level;
	},
	
	/** 
	 * main log method logging to all available appenders 
	 * @private
	 */
	log: function(logLevel, message, exception) {
		var loggingEvent = new Log4js.LoggingEvent(this.category, logLevel, 
			message, exception, this);
		this.loggingEvents.push(loggingEvent);
		this.onlog.dispatch(loggingEvent);
	},
	
	/** clear logging */
	clear : function () {
		try{
			this.loggingEvents = [];
			this.onclear.dispatch();
		} catch(e){}
	},
	/** checks if Level Trace is enabled */
	isTraceEnabled: function() {
		if (this.level.valueOf() <= Log4js.Level.TRACE.valueOf()) {
			return true;
		}
		return false;
	},
	/** 
	 * Trace messages 
	 * @param message {Object} message to be logged
	 */
	trace: function(message) {
		if (this.isTraceEnabled()) {
			this.log(Log4js.Level.TRACE, message, null);
		}
	},
	/** checks if Level Debug is enabled */
	isDebugEnabled: function() {
		if (this.level.valueOf() <= Log4js.Level.DEBUG.valueOf()) {
			return true;
		}
		return false;
	},
	/** 
	 * Debug messages 
	 * @param message {Object} message to be logged
	 */
	debug: function(message) {
		if (this.isDebugEnabled()) {
			this.log(Log4js.Level.DEBUG, message, null);
		}
		// android.onJsResult(message);
	},
	/**
	 * Debug messages 
	 * @param {Object} message  message to be logged
	 * @param {Throwable} throwable 
	 */
	debug: function(message, throwable) {
		if (this.isDebugEnabled()) {
			this.log(Log4js.Level.DEBUG, message, throwable);
		}
		// android.onJsResult(message);
	},	
	/** checks if Level Info is enabled */
	isInfoEnabled: function() {
		if (this.level.valueOf() <= Log4js.Level.INFO.valueOf()) {
			return true;
		}
		return false;
	},
	/** 
	 * logging info messages 
	 * @param {Object} message  message to be logged
	 */
	info: function(message) {
		if (this.isInfoEnabled()) {
			this.log(Log4js.Level.INFO, message, null);
		}
	},
	/** 
	 * logging info messages 
	 * @param {Object} message  message to be logged
	 * @param {Throwable} throwable  
	 */
	info: function(message, throwable) {
		if (this.isInfoEnabled()) {
			this.log(Log4js.Level.INFO, message, throwable);
		}
	},
	/** checks if Level Warn is enabled */
	isWarnEnabled: function() {
		if (this.level.valueOf() <= Log4js.Level.WARN.valueOf()) {
			return true;
		}
		return false;
	},

	/** logging warn messages */
	warn: function(message) {
		if (this.isWarnEnabled()) {
			this.log(Log4js.Level.WARN, message, null);
		}
	},
	/** logging warn messages */
	warn: function(message, throwable) {
		if (this.isWarnEnabled()) {
			this.log(Log4js.Level.WARN, message, throwable);
		}
	},
	/** checks if Level Error is enabled */
	isErrorEnabled: function() {
		if (this.level.valueOf() <= Log4js.Level.ERROR.valueOf()) {
			return true;
		}
		return false;
	},
	/** logging error messages */
	error: function(message) {
		if (this.isErrorEnabled()) {
			this.log(Log4js.Level.ERROR, message, null);
		}
	},
	/** logging error messages */
	error: function(message, throwable) {
		if (this.isErrorEnabled()) {
			this.log(Log4js.Level.ERROR, message, throwable);
		}
	},
	/** checks if Level Fatal is enabled */
	isFatalEnabled: function() {
		if (this.level.valueOf() <= Log4js.Level.FATAL.valueOf()) {
			return true;
		}
		return false;
	},
	/** logging fatal messages */
	fatal: function(message) {
		if (this.isFatalEnabled()) {
			this.log(Log4js.Level.FATAL, message, null);
		}
	},
	/** logging fatal messages */
	fatal: function(message, throwable) {
		if (this.isFatalEnabled()) {
			this.log(Log4js.Level.FATAL, message, throwable);
		}
	},	
	/** 
	 * Capture main window errors and log as fatal.
	 * @private
	 */
	windowError: function(msg, url, line){
		var message = "Error in (" + (url || window.location) + ") on line "+ line +" with message (" + msg + ")";
		this.log(Log4js.Level.FATAL, message, null);	
	},
	
	/**
	 * Set the date format of logger. Following switches are supported:
	 * <ul>
	 * <li>yyyy - The year</li>
	 * <li>MM - the month</li>
	 * <li>dd - the day of month<li>
	 * <li>hh - the hour<li>
	 * <li>mm - minutes</li>
	 * <li>O - timezone offset</li>
	 * </ul>
	 * @param {String} format format String for the date
	 * @see #getTimestamp
	 */
	setDateFormat: function(format) {
	 	this.dateformat = format;
	},
	 
	/**
	 * Generates a timestamp using the format set in {Log4js.setDateFormat}.
	 * @param {Date} date the date to format
	 * @see #setDateFormat
	 * @return A formatted timestamp with the current date and time.
	 */
	getFormattedTimestamp: function(date) {
	  return this.dateformatter.formatDate(date, this.dateformat);
	}
};

/**
 * Abstract base class for other appenders. 
 * It is doing nothing.
 *
 * @constructor
 * @param {Log4js.Logger} logger log4js instance this appender is attached to
 * @author Stephan Strittmatter
 */
Log4js.Appender = function () {
	/**
	 * Reference to calling logger
	 * @type Log4js.Logger
	 * @private
	 */
	 this.logger = null;
};

Log4js.Appender.prototype = {
	/** 
	 * appends the given loggingEvent appender specific
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to append
	 */
	doAppend: function(loggingEvent) {
		return;
	},
	/** 
	 * clears the Appender
	 */
	doClear: function() {
		return;
	},
	
	/**
	 * Set the Layout for this appender.
	 * @param {Log4js.Layout} layout Layout for formatting loggingEvent
	 */
	setLayout: function(layout){
		this.layout = layout;
	},
	/**
	 * Set reference to the logger.
	 * @param {Log4js.Logger} the invoking logger
	 */
	setLogger: function(logger){
		// add listener to the logger methods
		logger.onlog.addListener(Log4js.bind(this.doAppend, this));
		logger.onclear.addListener(Log4js.bind(this.doClear, this));
	
		this.logger = logger;
	}
};

/**
 * Interface for Layouts.
 * Use this Layout as "interface" for other Layouts. It is doing nothing.
 *
 * @constructor
 * @author Stephan Strittmatter
 */
Log4js.Layout = function(){return;};
Log4js.Layout.prototype = {
	/** 
	 * Implement this method to create your own layout format.
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to format
	 * @return formatted String
	 * @type String
	 */
	format: function(loggingEvent) {
		return "";
	},
	/** 
	 * Returns the content type output by this layout. 
	 * @return The base class returns "text/plain".
	 * @type String
	 */
	getContentType: function() {
		return "text/plain";
	},
	/** 
	 * @return Returns the header for the layout format. The base class returns null.
	 * @type String
	 */
	getHeader: function() {
		return null;
	},
	/** 
	 * @return Returns the footer for the layout format. The base class returns null.
	 * @type String
	 */
	getFooter: function() {
		return null;
	},
	
	/**
	 * @return Separator between events
	 * @type String
	 */
	getSeparator: function() {
		return "";
	}
};

/**
 * Console Appender writes the logs to a console.  If "inline" is
 * set to "false" the console launches in another window otherwise
 * the window is inline on the page and toggled on and off with "Alt-D".
 * Note: At FireFox &gb; 2.0 the keystroke is little different now: "SHIFT+ALT+D".
 *
 * @constructor
 * @extends Log4js.Appender
 * @param {boolean} isInline boolean value that indicates whether the console be placed inline, default is to launch in new window
 *
 * @author Corey Johnson - original console code in Lumberjack (http://gleepglop.com/javascripts/logger/)
 * @author Seth Chisamore - adapted for use as a log4js appender
 */
Log4js.ConsoleAppender = function(isInline) {
	
	/**
	 * @type Log4js.Layout
	 * @private
	 */
	this.layout = new Log4js.PatternLayout(Log4js.PatternLayout.TTCC_CONVERSION_PATTERN);
	/**
	 * @type boolean
	 * @private
	 */
	this.inline = isInline;

	/**
	 * @type String
	 * @private
	 */
	this.accesskey = "d";
	
	/**
	 * @private
	 */
	this.tagPattern = null;
	
	this.commandHistory = [];
  	this.commandIndex = 0;
  	
  	/**
  	 * true if popup is blocked.
  	 */
  	this.popupBlocker = false;
  	
  	/**
  	 * current output div-element.
  	 */
  	this.outputElement = null;
  	
  	this.docReference = null;
	this.winReference = null;		
		
	if(this.inline) {
		Log4js.attachEvent(window, 'load', Log4js.bind(this.initialize, this));
	}
};

Log4js.ConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {  

	/**
	 * Set the access key to show/hide the inline console (default &quote;d&quote;)
	 * @param key access key to show/hide the inline console
	 */	
	setAccessKey : function(key) {
		this.accesskey = key;
	},
	
	/**
	 * @private
	 */
  	initialize : function() {
		
		if(!this.inline) {
			var doc = null;	
			var win = null;
			window.top.consoleWindow = window.open("", this.logger.category, 
				"left=0,top=0,width=700,height=700,scrollbars=no,status=no,resizable=yes;toolbar=no");
			window.top.consoleWindow.opener = self;
			win = window.top.consoleWindow;
								
			if (!win) { 
				this.popupBlocker=true; 
				alert("Popup window manager blocking the Log4js popup window to bedisplayed.\n\n" 
					+ "Please disabled this to properly see logged events.");  
			} else {	

				doc = win.document;
				doc.open();
				doc.write("<!DOCTYPE html PUBLIC -//W3C//DTD XHTML 1.0 Transitional//EN ");
				doc.write("  http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd>\n\n");
				doc.write("<html><head><title>Log4js - " + this.logger.category + "</title>\n");
				doc.write("</head><body style=\"background-color:darkgray\"></body>\n");
				win.blur();
				win.focus();
			}
			
			this.docReference = doc;
			this.winReference = win;
		} else {
			this.docReference = document;
			this.winReference = window;			
		}
				
		this.outputCount = 0;
		this.tagPattern = ".*";
	  
		// I hate writing javascript in HTML... but what's a better alternative
		this.logElement = this.docReference.createElement('div');
		this.docReference.body.appendChild(this.logElement);
		this.logElement.style.display = 'none';
		
		this.logElement.style.position = "absolute";
		this.logElement.style.left = '0px';
		this.logElement.style.width = '100%';
	
		this.logElement.style.textAlign = "left";
		this.logElement.style.fontFamily = "lucida console";
		this.logElement.style.fontSize = "100%";
		this.logElement.style.backgroundColor = 'darkgray';      
		this.logElement.style.opacity = 0.9;
		this.logElement.style.zIndex = 2000; 
	
		// Add toolbarElement
		this.toolbarElement = this.docReference.createElement('div');
		this.logElement.appendChild(this.toolbarElement);     
		this.toolbarElement.style.padding = "0 0 0 2px";
	
		// Add buttons        
		this.buttonsContainerElement = this.docReference.createElement('span');
		this.toolbarElement.appendChild(this.buttonsContainerElement); 
	
		if(this.inline) {
			var closeButton = this.docReference.createElement('button');
			closeButton.style.cssFloat = "right";
			closeButton.style.styleFloat = "right"; // IE dom bug...doesn't understand cssFloat
			closeButton.style.color = "black";
			closeButton.innerHTML = "close";
			closeButton.onclick = Log4js.bind(this.toggle, this);
			this.buttonsContainerElement.appendChild(closeButton);
		}
		
		var clearButton = this.docReference.createElement('button');
		clearButton.style.cssFloat = "right";
		clearButton.style.styleFloat = "right"; // IE dom bug...doesn't understand cssFloat
		clearButton.style.color = "black";
		clearButton.innerHTML = "clear";
		clearButton.onclick = Log4js.bind(this.logger.clear, this.logger);
		this.buttonsContainerElement.appendChild(clearButton);
	

		//Add CategoryName and  Level Filter
		this.tagFilterContainerElement = this.docReference.createElement('span');
		this.toolbarElement.appendChild(this.tagFilterContainerElement);
		this.tagFilterContainerElement.style.cssFloat = 'left';
		
		this.tagFilterContainerElement.appendChild(this.docReference.createTextNode("Log4js - " + this.logger.category));
		this.tagFilterContainerElement.appendChild(this.docReference.createTextNode(" | Level Filter: "));
		
		this.tagFilterElement = this.docReference.createElement('input');
		this.tagFilterContainerElement.appendChild(this.tagFilterElement);
		this.tagFilterElement.style.width = '200px';                    
		this.tagFilterElement.value = this.tagPattern;    
		this.tagFilterElement.setAttribute('autocomplete', 'off'); // So Firefox doesn't flip out
		
		Log4js.attachEvent(this.tagFilterElement, 'keyup', Log4js.bind(this.updateTags, this));
		Log4js.attachEvent(this.tagFilterElement, 'click', Log4js.bind( function() {this.tagFilterElement.select();}, this));
		
		// Add outputElement
		this.outputElement = this.docReference.createElement('div');
		this.logElement.appendChild(this.outputElement);  
		this.outputElement.style.overflow = "auto";              
		this.outputElement.style.clear = "both";
		this.outputElement.style.height = (this.inline) ? ("200px"):("650px");
		this.outputElement.style.width = "100%";
		this.outputElement.style.backgroundColor = 'black'; 
			  
		this.inputContainerElement = this.docReference.createElement('div');
		this.inputContainerElement.style.width = "100%";
		this.logElement.appendChild(this.inputContainerElement);      
		
		this.inputElement = this.docReference.createElement('input');
		this.inputContainerElement.appendChild(this.inputElement);  
		this.inputElement.style.width = '100%';
		this.inputElement.style.borderWidth = '0px'; // Inputs with 100% width always seem to be too large (I HATE THEM) they only work if the border, margin and padding are 0
		this.inputElement.style.margin = '0px';
		this.inputElement.style.padding = '0px';
		this.inputElement.value = 'Type command here'; 
		this.inputElement.setAttribute('autocomplete', 'off'); // So Firefox doesn't flip out
	
		Log4js.attachEvent(this.inputElement, 'keyup', Log4js.bind(this.handleInput, this));
		Log4js.attachEvent(this.inputElement, 'click', Log4js.bind( function() {this.inputElement.select();}, this));
		
		if(this.inline){
			window.setInterval(Log4js.bind(this.repositionWindow, this), 500);
			this.repositionWindow();	
			// Allow acess key link          
			var accessElement = this.docReference.createElement('button');
			accessElement.style.position = "absolute";
			accessElement.style.top = "-100px";
			accessElement.accessKey = this.accesskey;
			accessElement.onclick = Log4js.bind(this.toggle, this);
			this.docReference.body.appendChild(accessElement);
		} else {
			this.show();
		}
	},
	/**
	 * shows/hide an element
	 * @private
	 * @return true if shown
	 */
	toggle : function() {
		if (this.logElement.style.display == 'none') {
		 	this.show();
		 	return true;
		} else {
			this.hide();
			return false;
		}
	}, 
	/**
	 * @private
	 */
	show : function() {
		this.logElement.style.display = '';
	  	this.outputElement.scrollTop = this.outputElement.scrollHeight; // Scroll to bottom when toggled
 	  	this.inputElement.select();
	}, 
	/**
	 * @private
	 */	
	hide : function() {
		this.logElement.style.display = 'none';
	},  
	/**
	 * @private
	 * @param message
	 * @style
	 */	
	output : function(message, style) {

		// If we are at the bottom of the window, then keep scrolling with the output			
		var shouldScroll = (this.outputElement.scrollTop + (2 * this.outputElement.clientHeight)) >= this.outputElement.scrollHeight;
		
		this.outputCount++;
	  	style = (style ? style += ';' : '');	  	
	  	style += 'padding:1px;margin:0 0 5px 0';	     
		  
		if (this.outputCount % 2 === 0) {
			style += ";background-color:#101010";
		}
	  	
	  	message = message || "undefined";
	  	message = message.toString();
	  	
	  	this.outputElement.innerHTML += "<pre style='" + style + "'>" + message + "</pre>";
	  	
	  	if (shouldScroll) {				
			this.outputElement.scrollTop = this.outputElement.scrollHeight;
		}
	},
	
	/**
	 * @private
	 */
	updateTags : function() {
		
		var pattern = this.tagFilterElement.value;
	
		if (this.tagPattern == pattern) {
			return;
		}
		
		try {
			new RegExp(pattern);
		} catch (e) {
			return;
		}
		
		this.tagPattern = pattern;

		this.outputElement.innerHTML = "";
		
		// Go through each log entry again
		this.outputCount = 0;
		for (var i = 0; i < this.logger.loggingEvents.length; i++) {
  			this.doAppend(this.logger.loggingEvents[i]);
		}  
	},

	/**
	 * @private
	 */	
	repositionWindow : function() {
		var offset = window.pageYOffset || this.docReference.documentElement.scrollTop || this.docReference.body.scrollTop;
		var pageHeight = self.innerHeight || this.docReference.documentElement.clientHeight || this.docReference.body.clientHeight;
		this.logElement.style.top = (offset + pageHeight - this.logElement.offsetHeight) + "px";
	},

	/**
	 * @param loggingEvent event to be logged
	 * @see Log4js.Appender#doAppend
	 */
	doAppend : function(loggingEvent) {
		
		if(this.popupBlocker) {
			//popup blocked, we return in this case
			return;
		}
		
		if ((!this.inline) && (!this.winReference || this.winReference.closed)) {
			this.initialize();
		}
		
		if (this.tagPattern !== null && 
			loggingEvent.level.toString().search(new RegExp(this.tagPattern, 'igm')) == -1) {
			return;
		}
		
		var style = '';
	  	
		if (loggingEvent.level.toString().search(/ERROR/) != -1) { 
			style += 'color:red';
		} else if (loggingEvent.level.toString().search(/FATAL/) != -1) { 
			style += 'color:red';
		} else if (loggingEvent.level.toString().search(/WARN/) != -1) { 
			style += 'color:orange';
		} else if (loggingEvent.level.toString().search(/DEBUG/) != -1) {
			style += 'color:green';
		} else if (loggingEvent.level.toString().search(/INFO/) != -1) {
			style += 'color:white';
		} else {
			style += 'color:yellow';
		}
	
		this.output(this.layout.format(loggingEvent), style);	
	},

	/**
	 * @see Log4js.Appender#doClear
	 */
	doClear : function() {
		this.outputElement.innerHTML = "";
	},
	/**
	 * @private
	 * @param e
	 */
	handleInput : function(e) {
		if (e.keyCode == 13 ) {      
			var command = this.inputElement.value;
			
			switch(command) {
				case "clear":
					this.logger.clear();  
					break;
					
				default:        
					var consoleOutput = "";
				
					try {
						consoleOutput = eval(this.inputElement.value);
					} catch (e) {  
						this.logger.error("Problem parsing input <" + command + ">" + e.message);
						break;
					}
						
					this.logger.trace(consoleOutput);
					break;
			}        
		
			if (this.inputElement.value !== "" && this.inputElement.value !== this.commandHistory[0]) {
				this.commandHistory.unshift(this.inputElement.value);
			}
		  
			this.commandIndex = 0;
			this.inputElement.value = "";                                                     
		} else if (e.keyCode == 38 && this.commandHistory.length > 0) {
    		this.inputElement.value = this.commandHistory[this.commandIndex];

			if (this.commandIndex < this.commandHistory.length - 1) {
      			this.commandIndex += 1;
      		}
    	} else if (e.keyCode == 40 && this.commandHistory.length > 0) {
    		if (this.commandIndex > 0) {                                      
      			this.commandIndex -= 1;
	    	}                       

			this.inputElement.value = this.commandHistory[this.commandIndex];
	  	} else {
    		this.commandIndex = 0;
    	}
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.ConsoleAppender[inline=" + this.inline + "]"; 
	 }
}); 

/**
 * Metatag Appender writing the logs to meta tags
 *
 * @extends Log4js.Appender
 * @constructor
 * @param logger log4js instance this appender is attached to
 * @author Stephan Strittmatter
 */
Log4js.MetatagAppender = function() {
	this.currentLine = 0;
};
Log4js.MetatagAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/**
	 * @param loggingEvent event to be logged
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		var now = new Date();
		var lines = loggingEvent.message.split("\n");
		var headTag = document.getElementsByTagName("head")[0];

		for (var i = 1; i <= lines.length; i++) {
			var value = lines[i - 1];
			if (i == 1) {
				value = loggingEvent.level.toString() + ": " + value;
			} else {
				value = "> " + value;
			}

			var metaTag = document.createElement("meta");
			metaTag.setAttribute("name", "X-log4js:" + this.currentLine);
			metaTag.setAttribute("content", value);
			headTag.appendChild(metaTag);
			this.currentLine += 1;
		}
	},

	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.MetatagAppender"; 
	 }
});

/**
 * AJAX Appender sending {@link Log4js.LoggingEvent}s asynchron via 
 * <code>XMLHttpRequest</code> to server.<br />
 * The {@link Log4js.LoggingEvent} is POSTed as response content and is 
 * formatted by the accociated layout. Default layout is {@link Log4js.XMLLayout}. 
 * The <code>threshold</code> defines when the logs 
 * should be send to the server. By default every event is sent on its
 * own (threshold=1). If it is set to 10, then the events are send in groups of
 * 10 events.
 *
 * @extends Log4js.Appender 
 * @constructor
 * @param {Log4js.Logger} logger log4js instance this appender is attached to
 * @param {String} loggingUrl url where appender will post log messages to
 * @author Stephan Strittmatter
 */
Log4js.AjaxAppender = function(loggingUrl) {

	/**
	 * is still esnding data to server
	 * @type boolean
	 * @private
	 */
	this.isInProgress = false;
	
	/**
	 * @type String
	 * @private
	 */
	this.loggingUrl = loggingUrl || "logging.log4js";
	
	/**
	 * @type Integer
	 * @private
	 */
	this.threshold = 1;
	
	/**
	 * timeout when request is aborted.
	 * @private
	 */
	this.timeout = 2000;
	
	/**
	 * List of LoggingEvents which should be send after threshold is reached.
	 * @type Map
	 * @private
	 */
	this.loggingEventMap = new Log4js.FifoBuffer();

	/**
	 * @type Log4js.Layout
	 * @private
	 */
	this.layout = new Log4js.XMLLayout();
	/**
	 * @type XMLHttpRequest
	 * @private
	 */	
	this.httpRequest = null;
};

Log4js.AjaxAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/**
	 * sends the logs to the server
	 * @param loggingEvent event to be logged
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		log4jsLogger.trace("> AjaxAppender.append");
	
		if (this.loggingEventMap.length() <= this.threshold || this.isInProgress === true) {
			this.loggingEventMap.push(loggingEvent);
		}
		
		if(this.loggingEventMap.length() >= this.threshold && this.isInProgress === false) {
			//if threshold is reached send the events and reset current threshold
			this.send();
		}
		
		log4jsLogger.trace("< AjaxAppender.append");
	},
	
	/** @see Appender#doClear */
	doClear: function() {
		log4jsLogger.trace("> AjaxAppender.doClear" );
		if(this.loggingEventMap.length() > 0) {
			this.send();
		}
		log4jsLogger.trace("< AjaxAppender.doClear" );
	},
	
	/**
	 * Set the threshold when logs have to be send. Default threshold is 1.
	 * @praram {int} threshold new threshold
	 */
	setThreshold: function(threshold) {
		log4jsLogger.trace("> AjaxAppender.setThreshold: " + threshold );
		this.threshold = threshold;
		log4jsLogger.trace("< AjaxAppender.setThreshold" );
	},
	
	/**
	 * Set the timeout in milli seconds until sending request is aborted.
	 * Default is 2000 ms.
	 * @param {int} milliseconds the new timeout
	 */
	setTimeout: function(milliseconds) {
		this.timeout = milliseconds;
	},
	
	/**
	 * send the request.
	 */
	send: function() {
		if(this.loggingEventMap.length() >0) {
			
			log4jsLogger.trace("> AjaxAppender.send");
			
			
			this.isInProgress = true;
			var a = [];
	
			for(var i = 0; i < this.loggingEventMap.length() && i < this.threshold; i++) {
				a.push(this.layout.format(this.loggingEventMap.pull()));
			} 
					
			var content = this.layout.getHeader();	
			content += a.join(this.layout.getSeparator());
			content += this.layout.getFooter();
			
			var appender = this;
			if(this.httpRequest === null){
				this.httpRequest = this.getXmlHttpRequest();
			}
			this.httpRequest.onreadystatechange = function() {
				appender.onReadyStateChanged.call(appender);
			};
			
			this.httpRequest.open("POST", this.loggingUrl, true);
			// set the request headers.
			//this.httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			this.httpRequest.setRequestHeader("Content-type", this.layout.getContentType());
			//REFERER will be the top-level
			// URI which may differ from the location of the error if
			// it occurs in an included .js file
			this.httpRequest.setRequestHeader("REFERER", location.href);
	 		this.httpRequest.setRequestHeader("Content-length", content.length);
			this.httpRequest.setRequestHeader("Connection", "close");
			this.httpRequest.send( content );
			
			appender = this;
			
			try {
				window.setTimeout(function(){
					log4jsLogger.trace("> AjaxAppender.timeout");
					appender.httpRequest.onreadystatechange = function(){return;};
					appender.httpRequest.abort();
					//this.httpRequest = null;
					appender.isInProgress = false;
		
					if(appender.loggingEventMap.length() > 0) {
						appender.send();
					}
					log4jsLogger.trace("< AjaxAppender.timeout");
				}, this.timeout);
			} catch (e) {
				log4jsLogger.fatal(e);
			}
			log4jsLogger.trace("> AjaxAppender.send");
		}
	},
	
	/**
	 * @private
	 */
	onReadyStateChanged: function() {
		log4jsLogger.trace("> AjaxAppender.onReadyStateChanged");
		var req = this.httpRequest;
		if (this.httpRequest.readyState != 4) { 
			log4jsLogger.trace("< AjaxAppender.onReadyStateChanged: readyState " + req.readyState + " != 4");
			return; 
		}
		
		var success = ((typeof req.status === "undefined") || req.status === 0 || (req.status >= 200 && req.status < 300));
		
		if (success) {
			log4jsLogger.trace("  AjaxAppender.onReadyStateChanged: success");

			//ready sending data
			this.isInProgress = false;

		} else {
			var msg = "  AjaxAppender.onReadyStateChanged: XMLHttpRequest request to URL " + this.loggingUrl + " returned status code " + this.httpRequest.status;
			log4jsLogger.error(msg);
		}
		
		log4jsLogger.trace("< AjaxAppender.onReadyStateChanged: readyState == 4");		
	},
	/**
	 * Get the XMLHttpRequest object independent of browser.
	 * @private
	 */
	getXmlHttpRequest: function() {
		log4jsLogger.trace("> AjaxAppender.getXmlHttpRequest");
		
		var httpRequest = false;

		try {		
			if (window.XMLHttpRequest) { // Mozilla, Safari, IE7...
					httpRequest = new XMLHttpRequest();
				if (httpRequest.overrideMimeType) {
					httpRequest.overrideMimeType(this.layout.getContentType());
				}
			} else if (window.ActiveXObject) { // IE
				try {
					httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				}
			}
		} catch (e) {
			httpRequest = false;
		}
		
		if (!httpRequest) {
			log4jsLogger.fatal("Unfortunatelly your browser does not support AjaxAppender for log4js!");
		}
		
		log4jsLogger.trace("< AjaxAppender.getXmlHttpRequest");
		return httpRequest;
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.AjaxAppender[loggingUrl=" + this.loggingUrl + ", threshold=" + this.threshold + "]"; 
	 }
});

/**
 * File Appender writing the logs to a text file.
 * PLEASE NOTE - Only works in IE and Mozilla 
 * use ActiveX to write file on IE
 * use XPCom components  to write file on Mozilla
 * 
 * @extends Log4js.Appender 
 * @constructor
 * @param logger log4js instance this appender is attached to
 * @param file file log messages will be written to
 * @author Seth Chisamore
 * @author Nicolas Justin njustin@idealx.com
 * @author Gregory Kokanosky gkokanosky@idealx.com
 */
Log4js.FileAppender = function(file) {

	this.layout = new Log4js.SimpleLayout();
	this.isIE = 'undefined';
	
	this.file = file || "log4js.log";	
	
	try{
		this.fso = new ActiveXObject("Scripting.FileSystemObject");
		this.isIE = true;
	} catch(e){
		try {
			//netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			this.fso =  Components.classes["@mozilla.org/file/local;1"].createInstance(Components.interfaces.nsILocalFile);
			this.isIE = false; //mozilla & co
		} catch (e) {
			log4jsLogger.error(e);
		}
	}
};

Log4js.FileAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/**
	 * @param loggingEvent event to be logged
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		try {
			var fileHandle = null;
			
			if( this.isIE === 'undefined') {
				log4jsLogger.error("Unsupported ")
			}
			else if( this.isIE ){
				// try opening existing file, create if needed
				fileHandle = this.fso.OpenTextFile(this.file, 8, true);        
				// write out our data
				fileHandle.WriteLine(this.layout.format(loggingEvent));
				fileHandle.close();   
			} else {
				//netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
				this.fso.initWithPath(this.file);
    			if(!this.fso.exists()) {
    				//create file if needed
            		this.fso.create(0x00, 0600);
    			}
				
 				fileHandle = Components.classes["@mozilla.org/network/file-output-stream;1"].createInstance(Components.interfaces.nsIFileOutputStream);
        		fileHandle.init( this.fso, 0x04 | 0x08 | 0x10, 064, 0);
				var line = this.layout.format(loggingEvent);
        		fileHandle.write(line, line.length); //write data
        		fileHandle.close();
			}
		} catch (e) {
			log4jsLogger.error(e);
		}
	},
	/*
	 * @see Log4js.Appender#doClear
	 */
	doClear: function() {
		try {
			if( this.isIE ){
				var fileHandle = this.fso.GetFile(this.file);
				fileHandle.Delete();
			} else {
				//netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
				this.fso.initWithPath(this.file);
				if(this.fso.exists()) {
					this.fso.remove(false);
				}
			}
		} catch (e) {
			log4jsLogger.error(e);
		}
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.FileAppender[file=" + this.file + "]"; 
	 }
});

/**
 * Windows Event Appender writes the logs to the Windows Event log.
 * PLEASE NOTE - Only works in IE..uses ActiveX to write to Windows Event log
 *
 * @extends Log4js.Appender 
 * @constructor
 * @param logger log4js instance this appender is attached to
 * @author Seth Chisamore
 */
Log4js.WindowsEventAppender = function() {
	
	this.layout = new Log4js.SimpleLayout();
	
	try {
		this.shell = new ActiveXObject("WScript.Shell");
	} catch(e) {
		log4jsLogger.error(e);
	}
};

Log4js.WindowsEventAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/**
	 * @param loggingEvent event to be logged
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		var winLevel = 4;
		
		// Map log level to windows event log level.
		// Windows events: - SUCCESS: 0, ERROR: 1, WARNING: 2, INFORMATION: 4, AUDIT_SUCCESS: 8, AUDIT_FAILURE: 16
		switch (loggingEvent.level) {	
			case Log4js.Level.FATAL:
				winLevel = 1;
				break;
			case Log4js.Level.ERROR:
				winLevel = 1;
				break;
			case Log4js.Level.WARN:
				winLevel = 2;
				break;
			default:
				winLevel = 4;
				break;
		}
		
		try {
			this.shell.LogEvent(winLevel, this.level.format(loggingEvent));
		} catch(e) {
			log4jsLogger.error(e);
		}
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.WindowsEventAppender"; 
	 } 
});

/**
 * JS Alert Appender writes the logs to the JavaScript alert dialog box
 * @constructor
 * @extends Log4js.Appender  
 * @param logger log4js instance this appender is attached to
 * @author S&eacute;bastien LECACHEUR
 */
Log4js.JSAlertAppender = function() {

	this.layout = new Log4js.SimpleLayout();
};

Log4js.JSAlertAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/** 
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		alert(this.layout.getHeader() + this.layout.format(loggingEvent) + this.layout.getFooter());
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.JSAlertAppender"; 
	 }	
});

/**
 * Appender writes the logs to the JavaScript console of Mozilla browser
 * More infos: http://kb.mozillazine.org/index.php?title=JavaScript_Console&redirect=no
 * PLEASE NOTE - Only works in Mozilla browser
 * @constructor
 * @extends Log4js.Appender  
 * @param logger log4js instance this appender is attached to
 * @author Stephan Strittmatter
 */
Log4js.MozillaJSConsoleAppender = function() {
	this.layout = new Log4js.SimpleLayout();
	try {
		//netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		this.jsConsole = Components.classes["@mozilla.org/consoleservice;1"].getService(Components.interfaces.nsIConsoleService);
		this.scriptError = Components.classes["@mozilla.org/scripterror;1"].createInstance(Components.interfaces.nsIScriptError);
	} catch (e) {
		log4jsLogger.error(e);
	}
};

Log4js.MozillaJSConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/** 
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		try {
			//netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			this.scriptError.init(this.layout.format(loggingEvent), null, null, null, null, this.getFlag(loggingEvent), loggingEvent.categoryName);
			this.jsConsole.logMessage(this.scriptError);
		} catch (e) {
			log4jsLogger.error(e);
		}
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.MozillaJSConsoleAppender"; 
	 },
	 
	/**
	 * Map Log4js.Level to jsConsole Flags:
	 * <ul>
	 * <li>nsIScriptError.errorFlag (0) = Level.Error</li>
	 * <li>nsIScriptError.warningFlag (1)= Log4js.Level.WARN</li>
	 * <li>nsIScriptError.exceptionFlag (2) = Log4js.Level.FATAL</li>
	 * <li>nsIScriptError.strictFlag (4) = unused</li>
	 * </ul>
	 * @private
	 */	
	getFlag: function(loggingEvent)
	{
		var retval;
		switch (loggingEvent.level) {	
			case Log4js.Level.FATAL:
				retval = 2;//nsIScriptError.exceptionFlag = 2
				break;
			case Log4js.Level.ERROR:
				retval = 0;//nsIScriptError.errorFlag
				break;
			case Log4js.Level.WARN:
				retval = 1;//nsIScriptError.warningFlag = 1
				break;
			default:
				retval = 1;//nsIScriptError.warningFlag = 1
				break;
		}
		
		return retval;		
	}
});

/**
 * Appender writes the logs to the JavaScript console of Opera browser
 * PLEASE NOTE - Only works in Opera browser
 * @constructor
 * @extends Log4js.Appender  
 * @param logger log4js instance this appender is attached to
 * @author Stephan Strittmatter
 */
Log4js.OperaJSConsoleAppender = function() {
	this.layout = new Log4js.SimpleLayout();
};

Log4js.OperaJSConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/** 
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		opera.postError(this.layout.format(loggingEvent));
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.OperaJSConsoleAppender"; 
	 }
});

/**
 * Appender writes the logs to the JavaScript console of Safari browser
 * PLEASE NOTE - Only works in Safari browser
 * @constructor
 * @extends Log4js.Appender  
 * @param logger log4js instance this appender is attached to
 * @author Stephan Strittmatter
 */
Log4js.SafariJSConsoleAppender = function() {
	this.layout = new Log4js.SimpleLayout();
};

Log4js.SafariJSConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/** 
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		window.console.log(this.layout.format(loggingEvent));
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.SafariJSConsoleAppender"; 
	 }
});

/**
 * JavaScript Console Appender which is browser independent.
 * It checks internally for the current browser and adds delegate to
 * specific JavaScript Console Appender of the browser.
 * 
 * @author Stephan Strittmatter
 * @since 1.0
 */
Log4js.BrowserConsoleAppender = function() {
	/**
	 * Delegate for browser specific implementation
	 * @type Log4js.Appender
	 * @private
	 */
	this.consoleDelegate = null;
	
	if (window.console) {
		this.consoleDelegate = new Log4js.SafariJSConsoleAppender(); 
	}
    else if (window.opera) {
		this.consoleDelegate = new Log4js.OperaJSConsoleAppender(); 
	}
	else if(netscape) {
		this.consoleDelegate = new Log4js.MozJSConsoleAppender(); 
	}
    else {
       //@todo
       log4jsLogger.error("Unsupported Browser");
    }
};

Log4js.BrowserConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {  
	/** 
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
		this.consoleDelegate.doAppend(loggingEvent);
	},
	/** 
	 * @see Log4js.Appender#doClear
	 */
	doClear: function() {
		this.consoleDelegate.doClear();
	},
	/**
	 * @see Log4js.Appender#setLayout
	 */
	setLayout: function(layout){
		this.consoleDelegate.setLayout(layout);
	},
	
	/** 
	 * toString
	 */
	 toString: function() {
	 	return "Log4js.BrowserConsoleAppender: " + this.consoleDelegate.toString(); 
	 }
});

/**
 * SimpleLayout consists of the level of the log statement, followed by " - " 
 * and then the log message itself. For example,
 * <code>DEBUG - Hello world</code>
 *
 * @constructor
 * @extends Log4js.Layout
 * @extends Layout
 * @author Stephan Strittmatter
 */
Log4js.SimpleLayout = function() {
	this.LINE_SEP  = "\n";
	this.LINE_SEP_LEN = 1;
};

Log4js.SimpleLayout.prototype = Log4js.extend(new Log4js.Layout(), {
	/** 
	 * Implement this method to create your own layout format.
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to format
	 * @return formatted String
	 * @type String
	 */
	format: function(loggingEvent) {
		return loggingEvent.level.toString() + " - " + loggingEvent.message + this.LINE_SEP;
	},
	/** 
	 * Returns the content type output by this layout. 
	 * @return The base class returns "text/plain".
	 * @type String
	 */
	getContentType: function() {
		return "text/plain";
	},
	/** 
	 * @return Returns the header for the layout format. The base class returns null.
	 * @type String
	 */
	getHeader: function() {
		return "";
	},
	/** 
	 * @return Returns the footer for the layout format. The base class returns null.
	 * @type String
	 */
	getFooter: function() {
		return "";
	}
});
	
/**
 * BasicLayout is a simple layout for storing the loggs. The loggs are stored
 * in following format:
 * <pre>
 * categoryName~startTime [logLevel] message\n
 * </pre>
 *
 * @constructor
 * @extends Log4js.Layout
 * @author Stephan Strittmatter
 */
Log4js.BasicLayout = function() {
	this.LINE_SEP  = "\n";
};

Log4js.BasicLayout.prototype = Log4js.extend(new Log4js.Layout(), {
	/** 
	 * Implement this method to create your own layout format.
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to format
	 * @return formatted String
	 * @type String
	 */
	format: function(loggingEvent) {
		return loggingEvent.categoryName + "~" + loggingEvent.startTime.toLocaleString() + " [" + loggingEvent.level.toString() + "] " + loggingEvent.message + this.LINE_SEP;
	},
	/** 
	 * Returns the content type output by this layout. 
	 * @return The base class returns "text/plain".
	 * @type String
	 */
	getContentType: function() {
		return "text/plain";
	},
	/** 
	 * @return Returns the header for the layout format. The base class returns null.
	 * @type String
	 */
	getHeader: function() {
		return "";
	},
	/** 
	 * @return Returns the footer for the layout format. The base class returns null.
	 * @type String
	 */
	getFooter: function() {
		return "";
	}
});

/**
 * HtmlLayout write the logs in Html format.
 *
 * @constructor
 * @extends Log4js.Layout
 * @author Stephan Strittmatter
 */
Log4js.HtmlLayout = function() {return;};

Log4js.HtmlLayout.prototype = Log4js.extend(new Log4js.Layout(), {
	/** 
	 * Implement this method to create your own layout format.
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to format
	 * @return formatted String
	 * @type String
	 */
	format: function(loggingEvent) {
		return "<div style=\"" + this.getStyle(loggingEvent) + "\">" + loggingEvent.getFormattedTimestamp() + " - " + loggingEvent.level.toString() + " - " + loggingEvent.message + "</div>\n";
	},
	/** 
	 * Returns the content type output by this layout. 
	 * @return The base class returns "text/html".
	 * @type String
	 */
	getContentType: function() {
		return "text/html";
	},
	/** 
	 * @return Returns the header for the layout format. The base class returns null.
	 * @type String
	 */
	getHeader: function() {
		return "<html><head><title>log4js</head><body>";
	},
	/** 
	 * @return Returns the footer for the layout format. The base class returns null.
	 * @type String
	 */
	getFooter: function() {
		return "</body></html>";
	},
	
	getStyle: function(loggingEvent)
	{
		var style;
		if (loggingEvent.level.toString().search(/ERROR/) != -1) { 
			style = 'color:red';
		} else if (loggingEvent.level.toString().search(/FATAL/) != -1) { 
			style = 'color:red';
		} else if (loggingEvent.level.toString().search(/WARN/) != -1) { 
			style = 'color:orange';
		} else if (loggingEvent.level.toString().search(/DEBUG/) != -1) {
			style = 'color:green';
		} else if (loggingEvent.level.toString().search(/INFO/) != -1) {
			style = 'color:white';
		} else {
			style = 'color:yellow';
		}	
		return style;
	}
});

/**
 * XMLLayout write the logs in XML format.
 * Layout is simmilar to log4j's XMLLayout:
 * <pre>
 * <log4js:event category="category" level="Level" client="Client" referer="ref" timestam="Date">
 * <log4js:message>Logged message</log4js:message>
 * </log4js:event>
 * </pre>
 * @constructor
 * @extends Layout
 * @author Stephan Strittmatter
 */
Log4js.XMLLayout = function(){return;};
Log4js.XMLLayout.prototype = Log4js.extend(new Log4js.Layout(), {
	/** 
	 * Implement this method to create your own layout format.
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to format
	 * @return formatted String
	 * @type String
	 */
	format: function(loggingEvent) {
		var useragent = "unknown";
		try {
			useragent = navigator.userAgent;
		} catch(e){
			useragent = "unknown";
		}
		
		var referer = "unknown";
		try {
			referer = location.href;
		} catch(e){
			referer = "unknown";
		}
				
		var content = "<log4js:event logger=\"";
		content += loggingEvent.categoryName + "\" level=\"";
		content += loggingEvent.level.toString() + "\" useragent=\"";
		content += useragent + "\" referer=\"";
		content += referer.replace(/&/g, "&amp;") + "\" timestamp=\"";
		content += loggingEvent.getFormattedTimestamp() + "\">\n";
		content += "\t<log4js:message><![CDATA[" + this.escapeCdata(loggingEvent.message) + "]]></log4js:message>\n";	
 		
 		if (loggingEvent.exception) {
			content += this.formatException(loggingEvent.exception) ;
		}
 		content += "</log4js:event>\n";
        
      return content;
	},
	/** 
	 * Returns the content type output by this layout. 
	 * @return The base class returns "text/xml".
	 * @type String
	 */
	getContentType: function() {
		return "text/xml";
	},
	/** 
	 * @return Returns the header for the layout format. The base class returns null.
	 * @type String
	 */
	getHeader: function() {
		return "<log4js:eventSet version=\"" + Log4js.version + 
			"\" xmlns:log4js=\"http://log4js.berlios.de/2007/log4js/\">\n";
	},
	/** 
	 * @return Returns the footer for the layout format. The base class returns null.
	 * @type String
	 */
	getFooter: function() {
		return "</log4js:eventSet>\n";
	},
	
	getSeparator: function() {
		return "\n";
	},
	
	/**
	 * better readable formatted Exceptions.
	 * @param ex {Exception} the exception to be formatted.
	 * @return {String} the formatted String representation of the exception.
	 * @private
	 */
	formatException: function(ex) {
		if (ex) {
			var exStr = "\t<log4js:throwable>"; 
			if (ex.message) {
				exStr +=  "\t\t<log4js:message><![CDATA[" + this.escapeCdata(ex.message) + "]]></log4js:message>\n";	
			} 
			if (ex.description) {
				exStr +=  "\t\t<log4js:description><![CDATA[" + this.escapeCdata(ex.description) + "]]></log4js:description>\n";	
			}
			
			exStr +=  "\t\t<log4js:stacktrace>";
			exStr +=  "\t\t\t<log4js:location fileName=\""+ex.fileName+"\" lineNumber=\""+ex.lineNumber+"\" />";
			exStr +=  "\t\t</log4js:stacktrace>";
			exStr = "\t</log4js:throwable>";
			return exStr;
		}
		return null;
	},
	/**
	 * Escape Cdata messages
	 * @param str {String} message to escape
	 * @return {String} the escaped message
	 * @private
	 */
	escapeCdata: function(str) {
		return str.replace(/\]\]>/, "]]>]]&gt;<![CDATA[");
	}
});

/**
 * JSONLayout write the logs in JSON format.
 * JSON library is required to use this Layout. See also {@link http://www.json.org}
 * @constructor
 * @extends Log4js.Layout
 * @author Stephan Strittmatter
 */
Log4js.JSONLayout = function() {
	this.df = new Log4js.DateFormatter();
};
Log4js.JSONLayout.prototype = Log4js.extend(new Log4js.Layout(), {
	/** 
	 * Implement this method to create your own layout format.
	 * @param {Log4js.LoggingEvent} loggingEvent loggingEvent to format
	 * @return formatted String
	 * @type String
	 */
	format: function(loggingEvent) {
		
				var useragent = "unknown";
		try {
			useragent = navigator.userAgent;
		} catch(e){
			useragent = "unknown";
		}
		
		var referer = "unknown";
		try {
			referer = location.href;
		} catch(e){
			referer = "unknown";
		}
		
		var jsonString = "{\n \"LoggingEvent\": {\n";
		
		jsonString += "\t\"logger\": \"" +  loggingEvent.categoryName + "\",\n";
		jsonString += "\t\"level\": \"" +  loggingEvent.level.toString() + "\",\n";
		jsonString += "\t\"message\": \"" +  loggingEvent.message + "\",\n"; 
		jsonString += "\t\"referer\": \"" + referer + "\",\n"; 
		jsonString += "\t\"useragent\": \"" + useragent + "\",\n"; 
		jsonString += "\t\"timestamp\": \"" +  this.df.formatDate(loggingEvent.startTime, "yyyy-MM-ddThh:mm:ssZ") + "\",\n";
		jsonString += "\t\"exception\": \"" +  loggingEvent.exception + "\"\n"; 
		jsonString += "}}";      
        
        return jsonString;
	},
	/** 
	 * Returns the content type output by this layout. 
	 * @return The base class returns "text/xml".
	 * @type String
	 */
	getContentType: function() {
		return "text/json";
	},
	/** 
	 * @return Returns the header for the layout format. The base class returns null.
	 * @type String
	 */
	getHeader: function() {
		return "{\"Log4js\": [\n";
	},
	/** 
	 * @return Returns the footer for the layout format. The base class returns null.
	 * @type String
	 */
	getFooter: function() {
		return "\n]}";
	},
	
	getSeparator: function() {
		return ",\n";
	}
});

/** 
 * PatternLayout 
 */
Log4js.PatternLayout = function(pattern) {
	if (pattern) {
		this.pattern = pattern;
	} else {
		this.pattern = Log4js.PatternLayout.DEFAULT_CONVERSION_PATTERN;
	}
};

Log4js.PatternLayout.TTCC_CONVERSION_PATTERN = "%r %p %c - %m%n";
Log4js.PatternLayout.DEFAULT_CONVERSION_PATTERN = "%m%n";
Log4js.PatternLayout.ISO8601_DATEFORMAT = "yyyy-MM-dd HH:mm:ss,SSS";
Log4js.PatternLayout.DATETIME_DATEFORMAT = "dd MMM YYYY HH:mm:ss,SSS";
Log4js.PatternLayout.ABSOLUTETIME_DATEFORMAT = "HH:mm:ss,SSS";

Log4js.PatternLayout.prototype = Log4js.extend(new Log4js.Layout(), {
	/** 
	 * Returns the content type output by this layout. 
	 * @return "text/plain".
	 * @type String
	 */
	getContentType: function() {
		return "text/plain";
	},
	/** 
	 * @return Returns the header for the layout format.
	 * @type String
	 */
	getHeader: function() {
		return null;
	},
	/** 
	 * @return Returns the footer for the layout format.
	 * @type String
	 */
	getFooter: function() {
		return null;
	},
	
	format: function(loggingEvent) {
		var regex = /%(-?[0-9]+)?(\.?[0-9]+)?([cdmnpr%])(\{([^\}]+)\})?|([^%]+)/;
		var formattedString = "";
		var result;
		var searchString = this.pattern;

		// Cannot use regex global flag since it doesn't work in IE5
		while ((result = regex.exec(searchString))) {
			var matchedString = result[0];
			var padding = result[1];
			var truncation = result[2];
			var conversionCharacter = result[3];
			var specifier = result[5];
			var text = result[6];

			// Check if the pattern matched was just normal text
			if (text) {
				formattedString += "" + text;
			} else {
				// Create a raw replacement string based on the conversion
				// character and specifier
				var replacement = "";
				switch(conversionCharacter) {
					case "c":
						var loggerName = loggingEvent.categoryName;
						if (specifier) {
							var precision = parseInt(specifier, 10);
							var loggerNameBits = loggingEvent.categoryName.split(".");
							if (precision >= loggerNameBits.length) {
								replacement = loggerName;
							} else {
								replacement = loggerNameBits.slice(loggerNameBits.length - precision).join(".");
							}
						} else {
							replacement = loggerName;
						}
						break;
					case "d":
						var dateFormat = Log4js.PatternLayout.ISO8601_DATEFORMAT;
						if (specifier) {
							dateFormat = specifier;
							// Pick up special cases
							if (dateFormat == "ISO8601") {
								dateFormat = Log4js.PatternLayout.ISO8601_DATEFORMAT;
							} else if (dateFormat == "ABSOLUTE") {
								dateFormat = Log4js.PatternLayout.ABSOLUTETIME_DATEFORMAT;
							} else if (dateFormat == "DATE") {
								dateFormat = Log4js.PatternLayout.DATETIME_DATEFORMAT;
							}
						}
						// Format the date
						replacement = (new Log4js.SimpleDateFormat(dateFormat)).format(loggingEvent.startTime);
						break;
					case "m":
						replacement = loggingEvent.message;
						break;
					case "n":
						replacement = "\n";
						break;
					case "p":
						replacement = loggingEvent.level.toString();
						break;
					case "r":
						replacement = "" + loggingEvent.startTime.toLocaleTimeString(); //TODO: .getDifference(Log4js.applicationStartDate);
						break;
					case "%":
						replacement = "%";
						break;
					default:
						replacement = matchedString;
						break;
				}
				// Format the replacement according to any padding or
				// truncation specified

				var len;

				// First, truncation
				if (truncation) {
					len = parseInt(truncation.substr(1), 10);
					replacement = replacement.substring(0, len);
				}
				// Next, padding
				if (padding) {
					if (padding.charAt(0) == "-") {
						len = parseInt(padding.substr(1), 10);
						// Right pad with spaces
						while (replacement.length < len) {
							replacement += " ";
						}
					} else {
						len = parseInt(padding, 10);
						// Left pad with spaces
						while (replacement.length < len) {
							replacement = " " + replacement;
						}
					}
				}
				formattedString += replacement;
			}
			searchString = searchString.substr(result.index + result[0].length);
		}
		return formattedString;
	}
});

/**
 * @private
 * @ignore
 */
if (!Array.prototype.push) {
	/**
	 * Functions taken from Prototype library, didn't want to require for just few 
	 * functions.
	 * More info at {@link http://
	 * prototype.conio.net/}
	 * @private
	 */
	Array.prototype.push = function() {
		var startLength = this.length;
		for (var i = 0; i < arguments.length; i++) {
			this[startLength + i] = arguments[i];
		}
		return this.length;
	};
}

/**
 * FIFO buffer
 * @private
 */
Log4js.FifoBuffer = function()
{
  this.array = new Array();
};

Log4js.FifoBuffer.prototype = {

	/**
	 * @param {Object} obj any object added to buffer
	 */
	push : function(obj) {
        this.array[this.array.length] = obj;
        return this.array.length;
	},
	
	/**
	 * @return first putted in Object
	 */
	pull : function() {
		if (this.array.length > 0) {
			var firstItem = this.array[0];
			for (var i = 0; i < this.array.length - 1; i++) {
				this.array[i] = this.array[i + 1];
			}
			this.array.length = this.array.length - 1;
			return firstItem;
		}
		return null;
	},
	
	length : function() {
		return this.array.length;
	}
};



/**
 * Date Formatter
 * addZero() and formatDate() are courtesy of Mike Golding:
 * http://www.mikezilla.com/exp0015.html
 * @private
 */ 
Log4js.DateFormatter = function() {
	return;
};
/**
 * default format of date (ISO-8601)
 * @static
 * @final
 */
Log4js.DateFormatter.DEFAULT_DATE_FORMAT = "yyyy-MM-ddThh:mm:ssO";


Log4js.DateFormatter.prototype = {
	/**
	 * Formats the given date by the given pattern.<br />
	 * Following switches are supported:
	 * <ul>
	 * <li>yyyy: The year</li>
	 * <li>MM: the month</li>
	 * <li>dd: the day of month<li>
	 * <li>hh: the hour<li>
	 * <li>mm: minutes</li>
	 * <li>O: timezone offset</li>
	 * </ul>
	 * @param {Date} vDate the date to format
	 * @param {String} vFormat the format pattern
	 * @return {String} formatted date string
	 * @static
	 */
	formatDate : function(vDate, vFormat) {
	  var vDay = this.addZero(vDate.getDate());
	  var vMonth = this.addZero(vDate.getMonth()+1);
	  var vYearLong = this.addZero(vDate.getFullYear());
	  var vYearShort = this.addZero(vDate.getFullYear().toString().substring(3,4));
	  var vYear = (vFormat.indexOf("yyyy")>-1?vYearLong:vYearShort);
	  var vHour  = this.addZero(vDate.getHours());
	  var vMinute = this.addZero(vDate.getMinutes());
	  var vSecond = this.addZero(vDate.getSeconds());
	  var vTimeZone = this.O(vDate);
	  var vDateString = vFormat.replace(/dd/g, vDay).replace(/MM/g, vMonth).replace(/y{1,4}/g, vYear);
	  vDateString = vDateString.replace(/hh/g, vHour).replace(/mm/g, vMinute).replace(/ss/g, vSecond);
	  vDateString = vDateString.replace(/O/g, vTimeZone);
	  return vDateString;
	},
		
	/**
	 * @private
	 * @static
	 */
	addZero : function(vNumber) {
	  return ((vNumber < 10) ? "0" : "") + vNumber;
	},
	
	/**
	 * Formates the TimeOffest
	 * Thanks to http://www.svendtofte.com/code/date_format/
	 * @private
	 */
	O : function (date) {
		// Difference to Greenwich time (GMT) in hours
		var os = Math.abs(date.getTimezoneOffset());
		var h = String(Math.floor(os/60));
		var m = String(os%60);
		h.length == 1? h = "0"+h:1;
		m.length == 1? m = "0"+m:1;
		return date.getTimezoneOffset() < 0 ? "+"+h+m : "-"+h+m;
	}
};


/**
 * internal Logger to be used
 * @private
 */
var log4jsLogger = Log4js.getLogger("Log4js");
log4jsLogger.addAppender(new Log4js.ConsoleAppender());
log4jsLogger.setLevel(Log4js.Level.ALL);

/*
---
name: logger

description: Basic logger

requires: [log4js.js]

provides: [logger]

...
*/
var __logger, setLogger, info, debug, warn, error, fatal;
__logger = new Log4js.getDefaultLogger();
setLogger = function(name){
  return __logger = new Log4js.getLogger(name);
};
info = function(it){
  return __logger.info(it);
};
debug = function(it){
  return __logger.debug(it);
};
warn = function(it){
  return __logger.warn(it);
};
error = function(it){
  return __logger.error(it);
};
fatal = function(it){
  return __logger.fatal(it);
};

/*
---
name: upoopu.tools.pb

description: Help to fill protocol buffers

requires: [Object, logger]

provides: [upoopu.tools.pb]

...
*/
namespace("upoopu.tools.pb", function(ns){
  return ns.buildEncoded = function(pbClass, data){
    var pb, fillPb;
    info("building pb");
    pb = new pbClass;
    fillPb = function(pb, data){
      var key, functionString, ref$, fullMtch, proto, clazz, e, d, res$, i$, ref1$, len$, o;
      for (key in pb.properties_) {
        functionString = pb.properties_[key]["type"].toString();
        ref$ = /(PROTO|)\.([^.;]*)\s*;?\s*}?\s*$/g.exec(functionString), fullMtch = ref$[0], proto = ref$[1], clazz = ref$[2];
        if (proto == "PROTO") {
          if (data[key] != undefined) {
            pb[key] = data[key];
          }
        } else {
          if (data[key] != undefined) {
            try {
              pb[key] = data[key].toUpperCase();
            } catch (e$) {
              e = e$;
              if (data[key] instanceof Array) {
                res$ = [];
                for (i$ = 0, len$ = (ref1$ = data[key]).length; i$ < len$; ++i$) {
                  o = ref1$[i$];
                  res$.push(fillPb(new (pb.properties_[key]["type"]()), o));
                }
                d = res$;
                pb[key] = d;
              } else {
                pb[key] = new (pb.properties_[key]["type"]());
                fillPb(pb[key], data[key]);
              }
            }
          }
        }
      }
      return pb;
    };
    fillPb(pb, data);
    return pb;
  };
});

/*
---
name: memoize

description: Memoize thingy

requires: []

provides: [memoize] 

...
*/


function memoize() {
    var margs = Array.prototype.slice.call(arguments),
        check = null;
    
   
    if(margs.length == 2) { 
        check = margs[0];
        fn = margs[1];
    } else {
        fn = margs[0];
    }
 

    return function () {  
        var args = Array.prototype.slice.call(arguments),  
            hash = "",
            c,
            i = args.length;  

        currentArg = null;

        check || (check = this.memoCheck);

        if (check) {
            c = check.apply(this, args);  
            hash = "!v" + ((c === Object(c)) ? JSON.stringify(c) : c) + "v!"  
        } else if (i == 0) {
            hash = "!noarggraon!"
            fn.memoize || (fn.memoize = {});  
        }
                
        while (i--) {  
            currentArg = args[i];  
            hash += (currentArg === Object(currentArg)) ?  
            JSON.stringify(currentArg) : currentArg;  
            fn.memoize || (fn.memoize = {});  
        }
 
        return (fn.memoize[hash] != undefined) ? fn.memoize[hash] :  
        fn.memoize[hash] = fn.apply(this, args);  
    };  
}

/**
 * HashMap
 * @author Ariel Flesler <aflesler@gmail.com>
 * @version 1.0.0
 * Homepage: https://github.com/flesler/hashmap
 */

;(function(exports){
	
	function HashMap() {
		this.clear();
	};

	HashMap.prototype = {
		constructor:HashMap,

		get:function(key) {
			var data = this._data[this.hash(key)];
			return data && data[1];
		},
		
		set:function(key, value) {
			// Store original key as well (for iteration)
			this._data[this.hash(key)] = [key, value];
		},
		
		has:function(key) {
			return this.hash(key) in this._data;
		},
		
		remove:function(key) {
			delete this._data[this.hash(key)];
		},

		type:function(key) {
			var str = Object.prototype.toString.call(key);
			var type = str.slice(8, -1).toLowerCase();
			// Some browsers yield DOMWindow for null and undefined, works fine on Node
			if (type === 'domwindow' && !key) {
				return key + '';
			}
			return type;
		},

		count:function() {
			var n = 0;
			for (var key in this._data) {
				n++;
			}
			return n;
		},

		clear:function() {
			// TODO: Would Object.create(null) make any difference
			this._data = {};
		},

		hash:function(key) {
			switch (this.type(key)) {
				case 'undefined':
				case 'null':
				case 'boolean':
				case 'number':
				case 'regexp':
					return key + '';

				case 'date':
					return ':' + key.getTime();

				case 'string':
					return '"' + key;

				case 'array':
					var hashes = [];
					for (var i = 0; i < key.length; i++)
						hashes[i] = this.hash(key[i]);
					return '[' + hashes.join('|');

				case 'object':
				default:
					// TODO: Don't use expandos when Object.defineProperty is not available?
					if (!key._hmuid_) {
						key._hmuid_ = ++HashMap.uid;
						hide(key, '_hmuid_');
					}

					return '{' + key._hmuid_;
			}
		},

		forEach:function(func) {
			for (var key in this._data) {
				var data = this._data[key];
				func(data[1], data[0]);
			}
		}
	};

	HashMap.uid = 0;

	
	function hide(obj, prop) {
		// Make non iterable if supported
		if (Object.defineProperty) {
			Object.defineProperty(obj, prop, {enumerable:false});
		}
	};

	exports.HashMap = HashMap;

})(this.exports || this);

//     Underscore.js 1.4.2
//     http://underscorejs.org
//     (c) 2009-2012 Jeremy Ashkenas, DocumentCloud Inc.
//     Underscore may be freely distributed under the MIT license.
(function(){var e=this,t=e._,n={},r=Array.prototype,i=Object.prototype,s=Function.prototype,o=r.push,u=r.slice,a=r.concat,f=r.unshift,l=i.toString,c=i.hasOwnProperty,h=r.forEach,p=r.map,d=r.reduce,v=r.reduceRight,m=r.filter,g=r.every,y=r.some,b=r.indexOf,w=r.lastIndexOf,E=Array.isArray,S=Object.keys,x=s.bind,T=function(e){if(e instanceof T)return e;if(!(this instanceof T))return new T(e);this._wrapped=e};typeof exports!="undefined"?(typeof module!="undefined"&&module.exports&&(exports=module.exports=T),exports._=T):e._=T,T.VERSION="1.4.2";var N=T.each=T.forEach=function(e,t,r){if(e==null)return;if(h&&e.forEach===h)e.forEach(t,r);else if(e.length===+e.length){for(var i=0,s=e.length;i<s;i++)if(t.call(r,e[i],i,e)===n)return}else for(var o in e)if(T.has(e,o)&&t.call(r,e[o],o,e)===n)return};T.map=T.collect=function(e,t,n){var r=[];return e==null?r:p&&e.map===p?e.map(t,n):(N(e,function(e,i,s){r[r.length]=t.call(n,e,i,s)}),r)},T.reduce=T.foldl=T.inject=function(e,t,n,r){var i=arguments.length>2;e==null&&(e=[]);if(d&&e.reduce===d)return r&&(t=T.bind(t,r)),i?e.reduce(t,n):e.reduce(t);N(e,function(e,s,o){i?n=t.call(r,n,e,s,o):(n=e,i=!0)});if(!i)throw new TypeError("Reduce of empty array with no initial value");return n},T.reduceRight=T.foldr=function(e,t,n,r){var i=arguments.length>2;e==null&&(e=[]);if(v&&e.reduceRight===v)return r&&(t=T.bind(t,r)),arguments.length>2?e.reduceRight(t,n):e.reduceRight(t);var s=e.length;if(s!==+s){var o=T.keys(e);s=o.length}N(e,function(u,a,f){a=o?o[--s]:--s,i?n=t.call(r,n,e[a],a,f):(n=e[a],i=!0)});if(!i)throw new TypeError("Reduce of empty array with no initial value");return n},T.find=T.detect=function(e,t,n){var r;return C(e,function(e,i,s){if(t.call(n,e,i,s))return r=e,!0}),r},T.filter=T.select=function(e,t,n){var r=[];return e==null?r:m&&e.filter===m?e.filter(t,n):(N(e,function(e,i,s){t.call(n,e,i,s)&&(r[r.length]=e)}),r)},T.reject=function(e,t,n){var r=[];return e==null?r:(N(e,function(e,i,s){t.call(n,e,i,s)||(r[r.length]=e)}),r)},T.every=T.all=function(e,t,r){t||(t=T.identity);var i=!0;return e==null?i:g&&e.every===g?e.every(t,r):(N(e,function(e,s,o){if(!(i=i&&t.call(r,e,s,o)))return n}),!!i)};var C=T.some=T.any=function(e,t,r){t||(t=T.identity);var i=!1;return e==null?i:y&&e.some===y?e.some(t,r):(N(e,function(e,s,o){if(i||(i=t.call(r,e,s,o)))return n}),!!i)};T.contains=T.include=function(e,t){var n=!1;return e==null?n:b&&e.indexOf===b?e.indexOf(t)!=-1:(n=C(e,function(e){return e===t}),n)},T.invoke=function(e,t){var n=u.call(arguments,2);return T.map(e,function(e){return(T.isFunction(t)?t:e[t]).apply(e,n)})},T.pluck=function(e,t){return T.map(e,function(e){return e[t]})},T.where=function(e,t){return T.isEmpty(t)?[]:T.filter(e,function(e){for(var n in t)if(t[n]!==e[n])return!1;return!0})},T.max=function(e,t,n){if(!t&&T.isArray(e)&&e[0]===+e[0]&&e.length<65535)return Math.max.apply(Math,e);if(!t&&T.isEmpty(e))return-Infinity;var r={computed:-Infinity};return N(e,function(e,i,s){var o=t?t.call(n,e,i,s):e;o>=r.computed&&(r={value:e,computed:o})}),r.value},T.min=function(e,t,n){if(!t&&T.isArray(e)&&e[0]===+e[0]&&e.length<65535)return Math.min.apply(Math,e);if(!t&&T.isEmpty(e))return Infinity;var r={computed:Infinity};return N(e,function(e,i,s){var o=t?t.call(n,e,i,s):e;o<r.computed&&(r={value:e,computed:o})}),r.value},T.shuffle=function(e){var t,n=0,r=[];return N(e,function(e){t=T.random(n++),r[n-1]=r[t],r[t]=e}),r};var k=function(e){return T.isFunction(e)?e:function(t){return t[e]}};T.sortBy=function(e,t,n){var r=k(t);return T.pluck(T.map(e,function(e,t,i){return{value:e,index:t,criteria:r.call(n,e,t,i)}}).sort(function(e,t){var n=e.criteria,r=t.criteria;if(n!==r){if(n>r||n===void 0)return 1;if(n<r||r===void 0)return-1}return e.index<t.index?-1:1}),"value")};var L=function(e,t,n,r){var i={},s=k(t);return N(e,function(t,o){var u=s.call(n,t,o,e);r(i,u,t)}),i};T.groupBy=function(e,t,n){return L(e,t,n,function(e,t,n){(T.has(e,t)?e[t]:e[t]=[]).push(n)})},T.countBy=function(e,t,n){return L(e,t,n,function(e,t,n){T.has(e,t)||(e[t]=0),e[t]++})},T.sortedIndex=function(e,t,n,r){n=n==null?T.identity:k(n);var i=n.call(r,t),s=0,o=e.length;while(s<o){var u=s+o>>>1;n.call(r,e[u])<i?s=u+1:o=u}return s},T.toArray=function(e){return e?e.length===+e.length?u.call(e):T.values(e):[]},T.size=function(e){return e.length===+e.length?e.length:T.keys(e).length},T.first=T.head=T.take=function(e,t,n){return t!=null&&!n?u.call(e,0,t):e[0]},T.initial=function(e,t,n){return u.call(e,0,e.length-(t==null||n?1:t))},T.last=function(e,t,n){return t!=null&&!n?u.call(e,Math.max(e.length-t,0)):e[e.length-1]},T.rest=T.tail=T.drop=function(e,t,n){return u.call(e,t==null||n?1:t)},T.compact=function(e){return T.filter(e,function(e){return!!e})};var A=function(e,t,n){return N(e,function(e){T.isArray(e)?t?o.apply(n,e):A(e,t,n):n.push(e)}),n};T.flatten=function(e,t){return A(e,t,[])},T.without=function(e){return T.difference(e,u.call(arguments,1))},T.uniq=T.unique=function(e,t,n,r){var i=n?T.map(e,n,r):e,s=[],o=[];return N(i,function(n,r){if(t?!r||o[o.length-1]!==n:!T.contains(o,n))o.push(n),s.push(e[r])}),s},T.union=function(){return T.uniq(a.apply(r,arguments))},T.intersection=function(e){var t=u.call(arguments,1);return T.filter(T.uniq(e),function(e){return T.every(t,function(t){return T.indexOf(t,e)>=0})})},T.difference=function(e){var t=a.apply(r,u.call(arguments,1));return T.filter(e,function(e){return!T.contains(t,e)})},T.zip=function(){var e=u.call(arguments),t=T.max(T.pluck(e,"length")),n=new Array(t);for(var r=0;r<t;r++)n[r]=T.pluck(e,""+r);return n},T.object=function(e,t){var n={};for(var r=0,i=e.length;r<i;r++)t?n[e[r]]=t[r]:n[e[r][0]]=e[r][1];return n},T.indexOf=function(e,t,n){if(e==null)return-1;var r=0,i=e.length;if(n){if(typeof n!="number")return r=T.sortedIndex(e,t),e[r]===t?r:-1;r=n<0?Math.max(0,i+n):n}if(b&&e.indexOf===b)return e.indexOf(t,n);for(;r<i;r++)if(e[r]===t)return r;return-1},T.lastIndexOf=function(e,t,n){if(e==null)return-1;var r=n!=null;if(w&&e.lastIndexOf===w)return r?e.lastIndexOf(t,n):e.lastIndexOf(t);var i=r?n:e.length;while(i--)if(e[i]===t)return i;return-1},T.range=function(e,t,n){arguments.length<=1&&(t=e||0,e=0),n=arguments[2]||1;var r=Math.max(Math.ceil((t-e)/n),0),i=0,s=new Array(r);while(i<r)s[i++]=e,e+=n;return s};var O=function(){};T.bind=function(t,n){var r,i;if(t.bind===x&&x)return x.apply(t,u.call(arguments,1));if(!T.isFunction(t))throw new TypeError;return i=u.call(arguments,2),r=function(){if(this instanceof r){O.prototype=t.prototype;var e=new O,s=t.apply(e,i.concat(u.call(arguments)));return Object(s)===s?s:e}return t.apply(n,i.concat(u.call(arguments)))}},T.bindAll=function(e){var t=u.call(arguments,1);return t.length==0&&(t=T.functions(e)),N(t,function(t){e[t]=T.bind(e[t],e)}),e},T.memoize=function(e,t){var n={};return t||(t=T.identity),function(){var r=t.apply(this,arguments);return T.has(n,r)?n[r]:n[r]=e.apply(this,arguments)}},T.delay=function(e,t){var n=u.call(arguments,2);return setTimeout(function(){return e.apply(null,n)},t)},T.defer=function(e){return T.delay.apply(T,[e,1].concat(u.call(arguments,1)))},T.throttle=function(e,t){var n,r,i,s,o,u,a=T.debounce(function(){o=s=!1},t);return function(){n=this,r=arguments;var f=function(){i=null,o&&(u=e.apply(n,r)),a()};return i||(i=setTimeout(f,t)),s?o=!0:(s=!0,u=e.apply(n,r)),a(),u}},T.debounce=function(e,t,n){var r,i;return function(){var s=this,o=arguments,u=function(){r=null,n||(i=e.apply(s,o))},a=n&&!r;return clearTimeout(r),r=setTimeout(u,t),a&&(i=e.apply(s,o)),i}},T.once=function(e){var t=!1,n;return function(){return t?n:(t=!0,n=e.apply(this,arguments),e=null,n)}},T.wrap=function(e,t){return function(){var n=[e];return o.apply(n,arguments),t.apply(this,n)}},T.compose=function(){var e=arguments;return function(){var t=arguments;for(var n=e.length-1;n>=0;n--)t=[e[n].apply(this,t)];return t[0]}},T.after=function(e,t){return e<=0?t():function(){if(--e<1)return t.apply(this,arguments)}},T.keys=S||function(e){if(e!==Object(e))throw new TypeError("Invalid object");var t=[];for(var n in e)T.has(e,n)&&(t[t.length]=n);return t},T.values=function(e){var t=[];for(var n in e)T.has(e,n)&&t.push(e[n]);return t},T.pairs=function(e){var t=[];for(var n in e)T.has(e,n)&&t.push([n,e[n]]);return t},T.invert=function(e){var t={};for(var n in e)T.has(e,n)&&(t[e[n]]=n);return t},T.functions=T.methods=function(e){var t=[];for(var n in e)T.isFunction(e[n])&&t.push(n);return t.sort()},T.extend=function(e){return N(u.call(arguments,1),function(t){for(var n in t)e[n]=t[n]}),e},T.pick=function(e){var t={},n=a.apply(r,u.call(arguments,1));return N(n,function(n){n in e&&(t[n]=e[n])}),t},T.omit=function(e){var t={},n=a.apply(r,u.call(arguments,1));for(var i in e)T.contains(n,i)||(t[i]=e[i]);return t},T.defaults=function(e){return N(u.call(arguments,1),function(t){for(var n in t)e[n]==null&&(e[n]=t[n])}),e},T.clone=function(e){return T.isObject(e)?T.isArray(e)?e.slice():T.extend({},e):e},T.tap=function(e,t){return t(e),e};var M=function(e,t,n,r){if(e===t)return e!==0||1/e==1/t;if(e==null||t==null)return e===t;e instanceof T&&(e=e._wrapped),t instanceof T&&(t=t._wrapped);var i=l.call(e);if(i!=l.call(t))return!1;switch(i){case"[object String]":return e==String(t);case"[object Number]":return e!=+e?t!=+t:e==0?1/e==1/t:e==+t;case"[object Date]":case"[object Boolean]":return+e==+t;case"[object RegExp]":return e.source==t.source&&e.global==t.global&&e.multiline==t.multiline&&e.ignoreCase==t.ignoreCase}if(typeof e!="object"||typeof t!="object")return!1;var s=n.length;while(s--)if(n[s]==e)return r[s]==t;n.push(e),r.push(t);var o=0,u=!0;if(i=="[object Array]"){o=e.length,u=o==t.length;if(u)while(o--)if(!(u=M(e[o],t[o],n,r)))break}else{var a=e.constructor,f=t.constructor;if(a!==f&&!(T.isFunction(a)&&a instanceof a&&T.isFunction(f)&&f instanceof f))return!1;for(var c in e)if(T.has(e,c)){o++;if(!(u=T.has(t,c)&&M(e[c],t[c],n,r)))break}if(u){for(c in t)if(T.has(t,c)&&!(o--))break;u=!o}}return n.pop(),r.pop(),u};T.isEqual=function(e,t){return M(e,t,[],[])},T.isEmpty=function(e){if(e==null)return!0;if(T.isArray(e)||T.isString(e))return e.length===0;for(var t in e)if(T.has(e,t))return!1;return!0},T.isElement=function(e){return!!e&&e.nodeType===1},T.isArray=E||function(e){return l.call(e)=="[object Array]"},T.isObject=function(e){return e===Object(e)},N(["Arguments","Function","String","Number","Date","RegExp"],function(e){T["is"+e]=function(t){return l.call(t)=="[object "+e+"]"}}),T.isArguments(arguments)||(T.isArguments=function(e){return!!e&&!!T.has(e,"callee")}),typeof /./!="function"&&(T.isFunction=function(e){return typeof e=="function"}),T.isFinite=function(e){return T.isNumber(e)&&isFinite(e)},T.isNaN=function(e){return T.isNumber(e)&&e!=+e},T.isBoolean=function(e){return e===!0||e===!1||l.call(e)=="[object Boolean]"},T.isNull=function(e){return e===null},T.isUndefined=function(e){return e===void 0},T.has=function(e,t){return c.call(e,t)},T.noConflict=function(){return e._=t,this},T.identity=function(e){return e},T.times=function(e,t,n){for(var r=0;r<e;r++)t.call(n,r)},T.random=function(e,t){return t==null&&(t=e,e=0),e+(0|Math.random()*(t-e+1))};var _={escape:{"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#x27;","/":"&#x2F;"}};_.unescape=T.invert(_.escape);var D={escape:new RegExp("["+T.keys(_.escape).join("")+"]","g"),unescape:new RegExp("("+T.keys(_.unescape).join("|")+")","g")};T.each(["escape","unescape"],function(e){T[e]=function(t){return t==null?"":(""+t).replace(D[e],function(t){return _[e][t]})}}),T.result=function(e,t){if(e==null)return null;var n=e[t];return T.isFunction(n)?n.call(e):n},T.mixin=function(e){N(T.functions(e),function(t){var n=T[t]=e[t];T.prototype[t]=function(){var e=[this._wrapped];return o.apply(e,arguments),F.call(this,n.apply(T,e))}})};var P=0;T.uniqueId=function(e){var t=P++;return e?e+t:t},T.templateSettings={evaluate:/<%([\s\S]+?)%>/g,interpolate:/<%=([\s\S]+?)%>/g,escape:/<%-([\s\S]+?)%>/g};var H=/(.)^/,B={"'":"'","\\":"\\","\r":"r","\n":"n","	":"t","\u2028":"u2028","\u2029":"u2029"},j=/\\|'|\r|\n|\t|\u2028|\u2029/g;T.template=function(e,t,n){n=T.defaults({},n,T.templateSettings);var r=new RegExp([(n.escape||H).source,(n.interpolate||H).source,(n.evaluate||H).source].join("|")+"|$","g"),i=0,s="__p+='";e.replace(r,function(t,n,r,o,u){s+=e.slice(i,u).replace(j,function(e){return"\\"+B[e]}),s+=n?"'+\n((__t=("+n+"))==null?'':_.escape(__t))+\n'":r?"'+\n((__t=("+r+"))==null?'':__t)+\n'":o?"';\n"+o+"\n__p+='":"",i=u+t.length}),s+="';\n",n.variable||(s="with(obj||{}){\n"+s+"}\n"),s="var __t,__p='',__j=Array.prototype.join,print=function(){__p+=__j.call(arguments,'');};\n"+s+"return __p;\n";try{var o=new Function(n.variable||"obj","_",s)}catch(u){throw u.source=s,u}if(t)return o(t,T);var a=function(e){return o.call(this,e,T)};return a.source="function("+(n.variable||"obj")+"){\n"+s+"}",a},T.chain=function(e){return T(e).chain()};var F=function(e){return this._chain?T(e).chain():e};T.mixin(T),N(["pop","push","reverse","shift","sort","splice","unshift"],function(e){var t=r[e];T.prototype[e]=function(){var n=this._wrapped;return t.apply(n,arguments),(e=="shift"||e=="splice")&&n.length===0&&delete n[0],F.call(this,n)}}),N(["concat","join","slice"],function(e){var t=r[e];T.prototype[e]=function(){return F.call(this,t.apply(this._wrapped,arguments))}}),T.extend(T.prototype,{chain:function(){return this._chain=!0,this},value:function(){return this._wrapped}})}).call(this);

/*
---
name: upoopu.parsers.BaseParser

description: Parsers for utopia data

requires: 
    - namespace
    - upoopu.tools.numbers    
    - upoopu.parsers.config
    - protobuf.js
    - pbj.js
    - MooTools
    - Date
    - Array
    - String
    - Hash
    - upoopu.intel.friendly.data
    - upoopu.proto.js
    - upoopu_enums.proto.js
    - upoopu_utopia_misc.proto.js
    - logger
    - upoopu.tools.pb
    - xregexp-all.js
    - memoize
    - hashmap.js
    - underscore-min.js

provides: [upoopu.parsers.BaseParser]
...
*/
/*
Removed
- MooTools
    - Date
    - Array
    - String
    - Hash
*/
var slice$ = [].slice, replace$ = ''.replace;
namespace("upoopu.parsers", function(ns){
  var c, reCache, utimeRegex, ushortTimeRegex, BaseParser, SelfParser;
  ns.selfData = null;
  ns.lastUtoTime = null;
  c = upoopu.parsers.config;
  reCache = {};
  ns.hoursBetween = function(oldStamp, newStamp){
    /*
    o = new Date(oldStamp * 1000).set({
            'min': 0,
            'sec': 0,
            'ms': 0
    })
    n = new Date(newStamp * 1000).set({
            'min': 0,
            'sec': 0,
            'ms': 0
    })
    o.diff(n, "hour")
    */
    var o, n;
    o = new Date(oldStamp * 1000);
    o.setHours(o.getHours(), 0, 0, 0);
    n = new Date(oldStamp * 1000);
    n.setHours(n.getHours(), 0, 0, 0);
    return Math.floor(Math.abs(n.getTime() - o.getTime()) / 360000);
  };
  ns.findData = function(data, type, regex){
    var buildRegex, results, i$, ref$, len$, s, fullRegex, pat, res, start;
    buildRegex = ns.buildRegex;
    results = [];
    if (!regex) {
      for (i$ = 0, len$ = (ref$ = c.DATASTRINGS).length; i$ < len$; ++i$) {
        s = ref$[i$];
        if (!type || s[0] == type) {
          fullRegex = buildRegex(s[1]);
          if (fullRegex.indexOf("(?<") >= 0) {
            if (reCache[fullRegex] != null) {
              pat = reCache[fullRegex];
            } else {
              pat = reCache[fullRegex] = new XRegExp(fullRegex, "i");
            }
            res = XRegExp.exec(data, pat);
          } else {
            if (reCache[fullRegex] != null) {
              pat = reCache[fullRegex];
            } else {
              pat = reCache[fullRegex] = new RegExp(fullRegex, "i");
            }
            res = pat.exec(data);
          }
          if (res) {
            start = data.indexOf(res[0]);
            results.push([s[0], res, start, start + 1 + res[0].length]);
          }
        }
      }
    } else if (regex) {
      fullRegex = buildRegex(regex);
      if (fullRegex.indexOf("(?<") >= 0) {
        if (reCache[fullRegex] != null) {
          pat = reCache[fullRegex];
        } else {
          pat = reCache[fullRegex] = new XRegExp(fullRegex, "i");
        }
        res = XRegExp.exec(data, pat);
      } else {
        if (reCache[fullRegex] != null) {
          pat = reCache[fullRegex];
        } else {
          pat = reCache[fullRegex] = new RegExp(fullRegex, "i");
        }
        res = pat.exec(data);
      }
      if (res) {
        start = data.indexOf(res[0]);
        results.push([null, res, start, start + 1 + res[0].length]);
      }
    }
    return results;
  };
  ns.parseRuler = function(rulerString){
    var ruler, nobility, specialization, king, sex, ref$, specPre, specPost, i$, ref1$, len$, nob, j$, ref2$, len1$, spec, compareAgainst, ref3$, e;
    info(rulerString);
    ruler = void 8;
    nobility = void 8;
    specialization = void 8;
    king = false;
    sex = "male";
    try {
      ref$ = ns.findData(rulerString, null, "[ruler]")[0][1].slice(2), specPre = ref$[0], nobility = ref$[1], ruler = ref$[2], specPost = ref$[3];
      debug([specPre, nobility, ruler, specPost]);
      if (nobility) {
        for (i$ = 0, len$ = (ref1$ = c.RULERNOBILITY).length; i$ < len$; ++i$) {
          nob = ref1$[i$];
          if (nob.length > 2 && nob.slice(2).indexOf(nobility.toLowerCase()) !== -1) {
            sex = "female";
          }
          if (nob.slice(1).indexOf(nobility.toLowerCase()) !== -1) {
            nobility = nob[0];
          }
        }
      }
      if (!nobility) {
        nobility = c.RULERNOBILITY[0][0];
      }
      for (j$ = 0, len1$ = (ref2$ = c.RULERSPECIALIZATION).length; j$ < len1$; ++j$) {
        spec = ref2$[j$];
        compareAgainst = (ref3$ = spec[1] == c.rulerPrefix ? specPre : specPost) != null ? ref3$.toLowerCase() : void 8;
        if (compareAgainst) {
          if (spec.slice(2).indexOf(compareAgainst) !== -1) {
            specialization = spec[0];
          }
          if (spec.length > 2 && spec.slice(3).indexOf(compareAgainst) !== -1) {
            sex = "female";
          }
        }
      }
      if ("king" == nobility.toLowerCase()) {
        nobility = c.RULERNOBILITY[2][0];
        king = true;
      }
    } catch (e$) {
      e = e$;
      debug(e);
    }
    return [ruler.replace(/^\s+|\s+$/g, ''), nobility, specialization, king, sex];
  };
  utimeRegex = /([a-z]+) ([0-9]+) of YR([0-9]+)/i;
  ns.utimeToInt = function(utime){
    var months, timeMonth, i$, len$, month, m, e;
    try {
      months = ["january", "february", "march", "april", "may", "june", "july"];
      timeMonth = 0;
      for (i$ = 0, len$ = months.length; i$ < len$; ++i$) {
        month = months[i$];
        if (utime.toLowerCase().indexOf(month) == 0) {
          break;
        }
        timeMonth += 1;
      }
      m = utimeRegex.exec(utime);
      return timeMonth * 24 + parseInt(m[2]) + parseInt(m[3]) * 24 * 7;
    } catch (e$) {
      e = e$;
      return 0;
    }
  };
  ushortTimeRegex = /([a-z]+) ([0-9]+), YR([0-9]+)/i;
  ns.ushortTimeToInt = function(utime){
    var months, timeMonth, i$, len$, month, m, e;
    try {
      months = ["jan", "feb", "mar", "apr", "may", "jun", "jul"];
      timeMonth = 0;
      for (i$ = 0, len$ = months.length; i$ < len$; ++i$) {
        month = months[i$];
        if (utime.toLowerCase().indexOf(month) == 0) {
          break;
        }
        timeMonth += 1;
      }
      m = ushortTimeRegex.exec(utime);
      return timeMonth * 24 + parseInt(m[2]) + parseInt(m[3]) * 24 * 7;
    } catch (e$) {
      e = e$;
      return 0;
    }
  };
  ns.BaseParser = BaseParser = (function(){
    BaseParser.displayName = 'BaseParser';
    var prototype = BaseParser.prototype, constructor = BaseParser;
    BaseParser.__responds_to__ = null;
    BaseParser.__build__ = 101;
    function BaseParser(data, type, meta){
      var ref$, province, isl, kd;
      this.type = type;
      this.meta = meta != null
        ? meta
        : {};
      this.regexes = {};
      if (this.meta.utime != null) {
        ns.lastUtoTime = [new Date().valueOf() * 0.001 | 0, ns.utimeToInt(this.meta.utime)];
      }
      if (this.meta.hash != null) {
        ref$ = this.meta.hash.split(":"), province = ref$[1], isl = ref$[2], kd = ref$[3];
        ns.selfData = [province, parseInt(kd), parseInt(isl)];
      }
      if (this.meta.self != null) {
        ns.selfData = [this.meta.self.province, this.meta.self.kd, this.meta.self.isl];
      }
      this.data = data;
      this.full_data = data;
      this.used_data = "";
      this.start_data = data.length;
      this.end_data = 0;
      this.sequential = true;
      this.pb = null;
      this.header_format = null;
      this.get_count = 0;
      this.parsed = null;
      /*
      @__defineGetter__("name", ~> @parse().get("province" , ""))
      @__defineGetter__("major_location", ~> @parse().get("isl" , 0))
      @__defineGetter__("minor_location", ~> @parse().get("kd" , 0))
      @__defineGetter__("hash", ~> "utopia:#@name:#@major_location:#@minor_location")
      @__defineGetter__("intel_type", ~> @pb?.message_type_)
      @__defineGetter__("intel_sub_type", ~> @parse().get("intel_sub_type" , ""))
      */
    }
    prototype.catcher = function(name, count, expression){
      var catchers, indices, i, d, i$, ref$, len$, c, results$ = [];
      catchers = slice$.call(arguments, 3);
      indices = [];
      for (i = -count; i <= 0; ++i) {
        indices.push(i);
      }
      d = this.get.apply(this, [expression].concat(slice$.call(indices)));
      if (typeof d == typeof 1) {
        d = [d];
      }
      d.reverse();
      for (i$ = 0, len$ = (ref$ = catchers.slice(0, count)).length; i$ < len$; ++i$) {
        c = ref$[i$];
        results$.push(c[name] = d.pop());
      }
      return results$;
    };
    prototype.get = function(format){
      var indices, kwargs, supplied_hash, k, v, get, requested_result_type, set_start, ref$, start, end, result, ref1$, this$ = this;
      indices = slice$.call(arguments, 1);
      kwargs = new HashMap();
      if (typeof indices[indices.length - 1] == typeof {}) {
        supplied_hash = indices.pop();
        for (k in supplied_hash) {
          v = supplied_hash[k];
          kwargs.set(k + "", v);
        }
      }
      get = function(data, format, indices, fromdata, split, result_type){
        var end, start, type, regex, s, result, i$, ref$, len$, ref1$, t, r, idxStart, idxEnd, j$, len1$, i, rr, k$, len2$, tmp;
        fromdata == null && (fromdata = null);
        split == null && (split = null);
        result_type == null && (result_type = null);
        end = 0;
        start = data.length;
        type = null;
        regex = null;
        if ((function(){
          var i$, ref$, len$, results$ = [];
          for (i$ = 0, len$ = (ref$ = c.DATASTRINGS).length; i$ < len$; ++i$) {
            s = ref$[i$];
            results$.push(s[0]);
          }
          return results$;
        }()).indexOf(format) !== -1) {
          type = format;
        } else {
          regex = format;
        }
        if (indices.length == 0) {
          indices = [-1];
        }
        if (indices.length == 1 && indices[0] === null) {
          indices = [];
        }
        indices = _.map(indices, function(it){
          if (!isNaN(it) && it >= 0) {
            return it + 1;
          } else {
            return it;
          }
        });
        result = [];
        for (i$ = 0, len$ = (ref$ = ns.findData(data, type, regex)).length; i$ < len$; ++i$) {
          ref1$ = ref$[i$], t = ref1$[0], r = ref1$[1], idxStart = ref1$[2], idxEnd = ref1$[3];
          if (fromdata) {
            this$.get_count += 1;
            end = Math.max(end, idxEnd);
            start = Math.min(start, idxStart);
          }
          if (indices.length == 0) {
            result.push(r[0]);
          }
          for (j$ = 0, len1$ = indices.length; j$ < len1$; ++j$) {
            i = indices[j$];
            if (typeof i == typeof 1) {
              rr = !isNaN(i) && i >= 0
                ? r[i]
                : r[r.length + i];
              if (split && rr) {
                result.append(rr.replace(new RegExp("^" + split + split + "*"), '').replace(new RegExp(split + "*" + split + "$"), '').split(split));
              } else {
                result.push(rr);
              }
            } else if (typeof i == typeof "string") {
              rr = r[i];
              if (split && rr) {
                result.append(rr.replace(new RegExp("^" + split + split + "*"), '').replace(new RegExp(split + "*" + split + "$"), '').split(split));
              } else {
                result.push(rr);
              }
            } else {
              result.extend(get(r[i[0]], i[1], i.slice(2)));
            }
          }
        }
        if (result.length == 0) {
          result = repeatArray$([null], indices.length);
        }
        r = [];
        for (k$ = 0, len2$ = result.length; k$ < len2$; ++k$) {
          t = result[k$];
          if (null == t || !t) {
            true;
          } else if ((!result_type && /^[ 0-9,%-]*$/.test(t)) || result_type == "number") {
            if (t == "-") {
              t = 0;
            } else if (t == "") {
              t = null;
            } else {
              tmp = parseInt(replace$.call(t, /[, %]/g, ''));
              if (isNaN(tmp)) {
                t = t.toString().replace(/^\s+|\s+$/g, '');
              } else {
                t = tmp;
              }
            }
          } else if ((!result_type && /^ 0-9,.%-]*$/.test(t)) || result_type == "number") {
            tmp = parseFloat(replace$.call(t, /[, %]/g, ''));
            if (isNaN(tmp)) {
              t = t.toString().replace(/^\s+|\s+$/g, '');
            } else {
              t = tmp;
            }
          } else {
            t = t.toString().replace(/^\s+|\s+$/g, '');
          }
          r.push(t);
        }
        if (result.length == 0) {
          r = null;
        } else if (result.length == 1 && !split || (split && r[0] == 0)) {
          r = r[0];
        }
        if (fromdata) {
          return [start, end, r];
        } else {
          return r;
        }
      };
      requested_result_type = null;
      if (kwargs.has("result_type")) {
        requested_result_type = kwargs.get("result_type");
      }
      set_start = false;
      if (kwargs.has("set_start")) {
        set_start = kwargs.get("set_start");
      }
      if (kwargs.has("data")) {
        ref$ = get(kwargs.get("data"), format, indices, true, kwargs.get("split"), requested_result_type), start = ref$[0], end = ref$[1], result = ref$[2];
        if (set_start && result && result[0]) {
          this.start_data = start;
        }
        return result;
      } else {
        ref1$ = get(this.data, format, indices, true, kwargs.get("split"), requested_result_type), start = ref1$[0], end = ref1$[1], result = ref1$[2];
        if (this.sequential) {
          this.data = this.data.slice(end);
          this.end_data += end;
          if (this.get_count == 1) {
            this.start_data = Math.min(this.start_data, start);
          }
        }
        return result;
      }
    };
    prototype.parse = function(){
      if (this.parsed == null) {
        this.parsed = this._parse.apply(this, arguments);
      }
      return this.parsed;
    };
    prototype._parse = function(){};
    prototype.header = function(){
      var d, header, k, v;
      d = this.parse();
      header = this.header_format;
      for (k in d) {
        v = d[k];
        header = header.replace("${" + k + "}", v);
      }
      return header;
    };
    prototype.toPb = function(trust){
      var data, utime;
      trust == null && (trust = 0);
      if (!this.pb) {
        return null;
      }
      data = this.parse();
      data["unix_time"] = new Date().valueOf() * 0.001 | 0;
      utime = this.get("(([a-z]+) ([0-9]+), YR([0-9]+)) throne", 0, {
        data: this.full_data
      });
      if (utime) {
        data["uto_time"] = ns.ushortTimeToInt(utime);
      }
      data["parser_build"] = ns.BaseParser.__build__;
      if (trust > 0 && trust <= 5) {
        data["trust"] = trust;
      }
      return upoopu.tools.pb.buildEncoded(this.pb, data);
    };
    /*
    def to_b64(self, trust = 0):
        if not @pb: return None
        data = @parse()
        h = None
        if @header:
            h = @header % data
    
        return build_encoded(@pb, header=h, **data)
    
    def to_bin(self, trust = 0):
        if not @pb: return None
        data = @parse()
    
        return build_encoded(@pb, bin=True,  **data)
    
    def to_json(self, trust = 0):
        if not @pb: return None
        pb = @to_pb(trust)
        d = pb2json(pb)
        d["_intel_type"] = @pb.DESCRIPTOR.name
        return json.dumps(d)
    */
    prototype.rewind = function(){
      this.data = this.full_data;
      return this.parsed = null;
    };
    prototype.toString = function(){
      return "Parser for " + this.type;
    };
    return BaseParser;
  }());
  return ns.SelfParser = SelfParser = (function(superclass){
    SelfParser.displayName = 'SelfParser';
    var prototype = extend$(SelfParser, superclass).prototype, constructor = SelfParser;
    SelfParser.__responds_to__ = ["self"];
    function SelfParser(data, type){
      type == null && (type = null);
      superclass.apply(this, arguments);
      this.pb = null;
      this.header = "Self on %{province} (%{kd}:%{isl})";
    }
    prototype._parse = function(){
      var ref$, province, kd, isl;
      ref$ = this.get("self", 0, 2, 3), province = ref$[0], kd = ref$[1], isl = ref$[2];
      ns.selfData = [province, kd, isl];
      return {};
    };
    return SelfParser;
  }(ns.BaseParser));
});
function repeatArray$(arr, n){
  for (var r = []; n > 0; (n >>= 1) && (arr = arr.concat(arr)))
    if (n & 1) r.push.apply(r, arr);
  return r;
}
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.KDParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.KDParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, KDParser;
  c = upoopu.parsers.config;
  return ns.KDParser = KDParser = (function(superclass){
    KDParser.displayName = 'KDParser';
    var prototype = extend$(KDParser, superclass).prototype, constructor = KDParser;
    KDParser.__responds_to__ = ["kd"];
    function KDParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelKD;
      this.header_format = "Kingdom of ${kingdom} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, ref1$, ref2$, rel, relpts, i$, ref3$, len$, relstrings, ref4$, j$, ref5$, len1$, header, extra_col, ref6$, province, race, land, nw, honor, mod, monarch, sex, nobility, protection, k$, ref7$, len2$, nob, ref8$;
      d = {};
      ref$ = this.get("kd", 0, -2, -1), d.kingdom = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      this.full_data = this.full_data.replace(/- - - - - - /g, "- - - - - o$");
      this.data = this.data.replace(/- - - - - - /g, "- - - - - o$");
      d.kingdom = d.kingdom + "";
      d.stance = this.get("stance [stance]");
      ref1$ = this.get("[number] / [number]", -2, -1), d.wars_won = ref1$[0], d.wars_concluded = ref1$[1];
      d.avg_opponent_nw = this.get("Relative Size [pct]");
      ref2$ = this.get("Their Attitude To Us (?<rel>[relation])( \\((?<relpts>[number]) points\\))?", "rel", "relpts"), rel = ref2$[0], relpts = ref2$[1];
      if (rel) {
        for (i$ = 0, len$ = (ref3$ = c.RELATIONS).length; i$ < len$; ++i$) {
          relstrings = ref3$[i$];
          if (relstrings[1].toLowerCase() == rel.toLowerCase()) {
            d.relation_kd_to_origin = relstrings[0];
          }
        }
      }
      d.points_kd_to_origin = relpts;
      ref4$ = this.get("Our Attitude To Them (?<rel>[relation])( \\((?<relpts>[number]) points\\))?", "rel", "relpts"), rel = ref4$[0], relpts = ref4$[1];
      if (rel) {
        for (j$ = 0, len1$ = (ref5$ = c.RELATIONS).length; j$ < len1$; ++j$) {
          relstrings = ref5$[j$];
          if (relstrings[1].toLowerCase() == rel.toLowerCase()) {
            d.relation_origin_to_kd = relstrings[0];
          }
        }
      }
      d.points_origin_to_kd = relpts;
      header = this.get("( nobility| gains| honour)+");
      extra_col = "";
      if (header && header.toLowerCase().match("gains$") == "gains") {
        extra_col = " [number]";
      }
      d.provinces = [];
      for (;;) {
        ref6$ = this.get("([0-9]+ )?(?<province>[provinceng])( \\([MS]\\))?(?<mod>[*^+]*) (?<race>[race]) (?<land>[number]) acres (?<nw>[number])gc [number]gc (?<honor>[honorext])" + extra_col, "province", "race", "land", "nw", "honor", "mod"), province = ref6$[0], race = ref6$[1], land = ref6$[2], nw = ref6$[3], honor = ref6$[4], mod = ref6$[5];
        if (!province) {
          break;
        }
        monarch = false;
        sex = "male";
        nobility = honor;
        protection = mod == "^";
        for (k$ = 0, len2$ = (ref7$ = c.RULERNOBILITY).length; k$ < len2$; ++k$) {
          nob = ref7$[k$];
          if (nob.slice(1).indexOf(honor.toLowerCase()) !== -1) {
            nobility = nob[0];
          }
          if (nob.length > 2 && nob.slice(2).indexOf(honor.toLowerCase()) !== -1) {
            sex = "female";
          }
          if (nobility.toLowerCase() == "king") {
            nobility = c.RULERNOBILITY[2][0];
            monarch = true;
          }
        }
        d.provinces.push({
          province: province + "",
          race: race,
          land: land,
          nw: nw,
          honor: nobility,
          monarch: monarch,
          protection: protection
        });
      }
      ref8$ = this.get("This kingdom is currently at war with [kingdom] [location]", -2, -1, {
        data: this.full_data
      }), d.war_kd = ref8$[0], d.war_isl = ref8$[1];
      if (d.war_kd) {
        d.stance = 'STANCEWAR';
      }
      return d;
    };
    return KDParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.ScienceParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.ScienceParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, ScienceParser;
  c = upoopu.parsers.config;
  return ns.ScienceParser = ScienceParser = (function(superclass){
    ScienceParser.displayName = 'ScienceParser';
    var prototype = extend$(ScienceParser, superclass).prototype, constructor = ScienceParser;
    ScienceParser.__responds_to__ = ["science", "selfscience"];
    function ScienceParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelScience;
      this.header_format = "Science on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, ref1$, i$, ref2$, len$, s, ref3$, quantity, per_acre, j$, ref4$, len1$, incoming, k$, ref5$, len2$, ss;
      d = {};
      ref$ = this.get("thieves visit the research centers of [province] [location]", 0, -2, -1), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      if (!d.kd || !d.isl || this.type == "selfscience") {
        this.get(this.type);
        if (ns.selfData) {
          ref1$ = ns.selfData, d.province = ref1$[0], d.kd = ref1$[1], d.isl = ref1$[2];
        }
        d.is_self = true;
        d.trust = 5;
        d.accuracy = 100;
      } else {
        d.is_self = false;
        d.accuracy = this.get("our operation was a success and we have [pct] confidence in the information", {
          data: this.full_data.slice(Math.max(0, this.start_data - 450), this.start_data)
        });
        d.trust = Math.floor((d.accuracy || 0) / 25) + 1;
      }
      d.province == null && (d.province += "");
      d.sciences = [];
      for (i$ = 0, len$ = (ref2$ = c.SCIENCES).length; i$ < len$; ++i$) {
        s = ref2$[i$];
        ref3$ = this.get(s[1] + " [number] [number]", -2, -1), quantity = ref3$[0], per_acre = ref3$[1];
        if (quantity) {
          d.sciences.push({
            "science": s[0],
            "quantity": quantity,
            "per_acre": per_acre
          });
        }
      }
      if (d.is_self) {
        for (j$ = 0, len1$ = (ref4$ = c.SCIENCES).length; j$ < len1$; ++j$) {
          s = ref4$[j$];
          if (incoming = this.get(s[1] + "(( [number]){0,24})", 0, {
            split: " ",
            fromdata: this.data.slice(1680)
          })) {
            debug("Got training for " + s[1] + " " + incoming.join(","));
            for (k$ = 0, len2$ = (ref5$ = d.sciences).length; k$ < len2$; ++k$) {
              ss = ref5$[k$];
              if (ss.science == s[0]) {
                ss.incoming = incoming;
                break;
              }
            }
          }
        }
      }
      return d;
    };
    return ScienceParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.SoMParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.SoMParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, SoMParser;
  c = upoopu.parsers.config;
  return ns.SoMParser = SoMParser = (function(superclass){
    SoMParser.displayName = 'SoMParser';
    var prototype = extend$(SoMParser, superclass).prototype, constructor = SoMParser;
    SoMParser.__responds_to__ = ["som", "selfsom"];
    function SoMParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelSoM;
      this.header_format = "SoM on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, ref1$, _out, _temp, _elite_name, i$, ref2$, r, len$, ref3$, e;
      d = {};
      ref$ = this.get("military elders of [province] [location]", 0, -2, -1), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      if (!d.kd || !d.isl || this.type == "selfsom") {
        this.get(this.type);
        if (ns.selfData) {
          ref1$ = ns.selfData, d.province = ref1$[0], d.kd = ref1$[1], d.isl = ref1$[2];
        }
        d.is_self = true;
        d.accuracy = 100;
        d.trust = 5;
      } else {
        d.is_self = false;
        d.accuracy = this.get("our operation was a success and we have [pct] confidence in the information", {
          data: this.full_data.slice(Math.max(0, this.start_data - 450), this.start_data)
        });
        d.trust = Math.floor((d.accuracy || 0) / 25) + 1;
      }
      if (d.province) {
        d.province = d.province + "";
      }
      d.military_pct = this.get("approximately [pct] of our maximum population");
      d.wages = this.get("wage rate is [pct]");
      d.military_efficiency = this.get("military is functioning at [pct] efficiency");
      d.off_me = this.get("offensive military effectiveness [pct]");
      d.offense = this.get("net offensive points at home [number]");
      d.def_me = this.get("defensive military effectiveness [pct]");
      d.defense = this.get("net defensive points at home [number]");
      _out = 0;
      d.army_home = {};
      d.army_out_1 = {};
      d.army_out_2 = {};
      d.army_out_3 = {};
      d.army_out_4 = {};
      d.army_out_5 = {};
      _temp = this.get("Army #1 \\([number] days left");
      if (_temp) {
        _out = 1;
        d.army_out_1["away"] = _temp;
      }
      _temp = this.get("Army #2 \\([number] days left");
      if (_temp) {
        _out = 2;
        d.army_out_2["away"] = _temp;
      }
      _temp = this.get("Army #3 \\([number] days left");
      if (_temp) {
        _out = 3;
        d.army_out_3["away"] = _temp;
      }
      _temp = this.get("Army #4 \\([number] days left");
      if (_temp) {
        _out = 4;
        d.army_out_4["away"] = _temp;
      }
      _temp = this.get("Army #5 \\([number] days left");
      if (_temp) {
        _out = 5;
        d.army_out_5["away"] = _temp;
      }
      debug('***************************');
      debug(_out);
      this.catcher("generals", _out + 1, "generals " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      this.catcher("soldiers", _out + 1, "soldiers " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      this.catcher("ospecs", _out + 1, "[ospec] " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      this.catcher("dspecs", _out + 1, "[dspec] " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      this.catcher("elites", _out + 1, "[elite] " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      this.catcher("horses", _out + 1, "war horses " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      this.catcher("land", _out + 1, "captured land " + (repeatArray$(["[number]"], _out + 1)).join(" "), d.army_home, d.army_out_1, d.army_out_2, d.army_out_3, d.army_out_4, d.army_out_5);
      d.training_ospecs = this.get("[ospec](( [number]){0,24})", 1, {
        split: " "
      });
      d.training_dspecs = this.get("[dspec](( [number]){0,24})", 1, {
        split: " "
      });
      _elite_name = this.get("[elite]");
      d.race = "";
      for (i$ = 0, len$ = (ref2$ = (fn$())).length; i$ < len$; ++i$) {
        ref3$ = ref2$[i$], e = ref3$[0], r = ref3$[1];
        if (e.toLowerCase() == _elite_name.toLowerCase()) {
          d.race = r;
          break;
        }
      }
      d.training_elites = this.get("((.?[number].?){0,24})", 0, {
        split: " "
      });
      d.training_thieves = this.get("(hieves(?<all>( [number]){0,24}))", "all", {
        split: " "
      });
      return d;
      function fn$(){
        var i$, ref$, len$, results$ = [];
        for (i$ = 0, len$ = (ref$ = c.RACES).length; i$ < len$; ++i$) {
          r = ref$[i$];
          results$.push([r[5], r[0]]);
        }
        return results$;
      }
    };
    return SoMParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}
function repeatArray$(arr, n){
  for (var r = []; n > 0; (n >>= 1) && (arr = arr.concat(arr)))
    if (n & 1) r.push.apply(r, arr);
  return r;
}

/*
---
name: upoopu.parsers.SoTParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.SoTParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, SoTParser;
  c = upoopu.parsers.config;
  return ns.SoTParser = SoTParser = (function(superclass){
    SoTParser.displayName = 'SoTParser';
    var prototype = extend$(SoTParser, superclass).prototype, constructor = SoTParser;
    SoTParser.__responds_to__ = ["sot"];
    function SoTParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelSoT;
      this.header_format = "SoT on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, province, gbp, i$, ref1$, len$, g, ref2$, ref3$, ref4$, dragon;
      d = {};
      ref$ = this.get("sot", 0, 2, 3, 4, -1), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2], d.utime = ref$[3], d.race = ref$[4];
      if (d.utime) {
        d.tick = ns.utimeToInt(d.utime);
      }
      ns.lastUtoTime = [new Date().valueOf() * 0.001 | 0, ns.utimeToInt(d.utime)];
      if (province) {
        province = province + "";
      }
      d.accuracy = this.get("our operation was a success and we have [pct] confidence in the information", {
        data: this.full_data.slice(Math.max(0, this.start_data - 450), this.start_data)
      });
      d.trust = Math.floor((d.accuracy || 0) / 25) + 1;
      gbp = this.get("this province has (not been|been) [gbp] in the last month", {
        data: this.full_data.slice(Math.max(0, this.start_data - 450), this.start_data)
      });
      d.gbp = 0;
      if (gbp) {
        for (i$ = 0, len$ = (ref1$ = c.GBP).length; i$ < len$; ++i$) {
          g = ref1$[i$];
          if (g[1] == gbp) {
            d.gbp = g[3];
          }
        }
      }
      d.soldiers = this.get("soldiers [number]");
      ref2$ = ns.parseRuler(this.get("ruler [ruler]", 0)), d.ruler = ref2$[0], d.honor = ref2$[1], d.personality = ref2$[2], d.monarch = ref2$[3], d.sex = ref2$[4];
      d.ruler = d.ruler + "";
      d.ospecs = this.get("[ospec] [number]");
      d.land = this.get("land [number]");
      d.dspecs = this.get("[dspec] [number]");
      d.peasants = this.get("peasants [number]");
      d.elites = this.get("[elite] [number]");
      d.building_eff = this.get("building eff. [pct]");
      ref3$ = this.get("thieves [number] \\([pct]\\)", 0, -1), d.thieves = ref3$[0], d.thieves_pct = ref3$[1];
      d.gold = this.get("money [number]");
      ref4$ = this.get("wizards [number] \\([pct]\\)", 0, -1), d.wizards = ref4$[0], d.wizards_pct = ref4$[1];
      d.food = this.get("food [number]");
      d.horses = this.get("war horses [number]");
      d.runes = this.get("runes [number]");
      d.prisoners = this.get("prisoners [number]");
      d.trade_balance = this.get("trade balance [number]");
      d.offense = this.get("off. points [number]");
      d.nw = this.get("networth [number]");
      d.defense = this.get("def. points [number]");
      dragon = this.get("a [dragon] dragon ravages the lands!", {
        data: this.full_data.slice(this.end_data, Math.min(this.end_data + 100, this.full_data.length))
      });
      if (!dragon) {
        d.dragon = "nodragon";
      } else {
        d.dragon = dragon;
        this.get("a [dragon] dragon ravages the lands!");
      }
      if (d.thieves_pct && d.wizards_pct) {
        d.is_self = true;
        ns.selfData = [d.province, d.kd, d.isl];
        d.trust = 5;
        d.accuracy = 100;
      } else {
        d.is_self = false;
      }
      if (this.get("(The Plague has spread throughout our people!)", {
        data: this.full_data.slice(this.end_data, Math.min(this.end_data + 100, this.full_data.length))
      })) {
        d.has_plague = true;
        this.get("(The Plague has spread throughout our people!)");
      } else {
        d.has_plague = false;
      }
      d.relations = "norel";
      if (this.get("(Our Kingdom is at WAR!)", {
        data: this.full_data.slice(this.end_data, Math.min(this.end_data + 100, this.full_data.length))
      })) {
        d.relations = "war";
        this.get("(Our Kingdom is at WAR!)");
      }
      return d;
    };
    return SoTParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.SurveyParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.SurveyParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, SurveyParser;
  c = upoopu.parsers.config;
  return ns.SurveyParser = SurveyParser = (function(superclass){
    SurveyParser.displayName = 'SurveyParser';
    var prototype = extend$(SurveyParser, superclass).prototype, constructor = SurveyParser;
    SurveyParser.__responds_to__ = ["survey", "selfsurvey"];
    function SurveyParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelSurvey;
      this.header_format = "Survey on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, ref1$, i$, ref2$, len$, b, ref3$, quantity, percentage, j$, ref4$, len1$, incoming, found, k$, ref5$, len2$, bb;
      d = {};
      ref$ = this.get("thieves scour the lands of [province] [location]", 0, -2, -1), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      if (!d.kd || !d.isl || this.type == "selfsurvey") {
        this.get(this.type);
        if (ns.selfData) {
          ref1$ = ns.selfData, d.province = ref1$[0], d.kd = ref1$[1], d.isl = ref1$[2];
        }
        d.is_self = true;
        d.trust = 5;
        d.accuracy = 100;
      } else {
        d.is_self = false;
        d.accuracy = this.get("our operation was a success and we have [pct] confidence in the information", {
          data: this.full_data.slice(Math.max(0, this.start_data - 450), this.start_data)
        });
        d.trust = Math.floor((d.accuracy || 0) / 25) + 1;
      }
      d.province == null && (d.province += "");
      d.workers = this.get("Available Workers [number]");
      d.building_efficiency = this.get("Building Efficiency [pct]");
      d.jobs = this.get("Available Jobs [number]");
      d.workers_needed = this.get("Max. Efficiency [number]");
      d.buildings = [];
      for (i$ = 0, len$ = (ref2$ = c.BUILDINGS).length; i$ < len$; ++i$) {
        b = ref2$[i$];
        ref3$ = this.get(b[1] + " [number] [pct]", -2, -1), quantity = ref3$[0], percentage = ref3$[1];
        if (quantity) {
          d.buildings.push({
            "building": b[0],
            "quantity": quantity,
            "percentage": percentage
          });
        }
      }
      if (d.is_self) {
        for (j$ = 0, len1$ = (ref4$ = c.BUILDINGS).length; j$ < len1$; ++j$) {
          b = ref4$[j$];
          if (incoming = this.get(b[1] + "(( [number]){0,24})", 0, {
            split: " "
          })) {
            found = false;
            for (k$ = 0, len2$ = (ref5$ = d.buildings).length; k$ < len2$; ++k$) {
              bb = ref5$[k$];
              if (bb.building == b[0]) {
                bb.incoming = incoming;
                found = true;
                break;
              }
            }
            if (!found) {
              d.buildings.push({
                "building": b[0],
                "quantity": 0,
                "percentage": 0,
                "incoming": incoming
              });
            }
          }
        }
      }
      return d;
    };
    return SurveyParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.NewsParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.NewsParser] 
...
*/
namespace("upoopu.parsers", function(ns){
  var c, NewsParser;
  c = upoopu.parsers.config;
  return ns.NewsParser = NewsParser = (function(superclass){
    NewsParser.displayName = 'NewsParser';
    var prototype = extend$(NewsParser, superclass).prototype, constructor = NewsParser;
    NewsParser.__responds_to__ = ["kdnews"];
    function NewsParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelKdNews;
      this.header_format = "KD News on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, kd_intel, isl_intel, ref1$, ref2$, attack_formats, fill_province_actions, ref3$, ref4$, province, kd, isl, this$ = this;
      d = {};
      this.get("kdnews");
      ref$ = this.get("kingdom news from [province] [location]", 0, -2, -1), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      kd_intel = isl_intel = null;
      if (!d.kd || !d.isl) {
        if (ns.selfData) {
          ref1$ = ns.selfData, d.province = ref1$[0], d.kd = ref1$[1], d.isl = ref1$[2];
        }
      }
      if (d.province) {
        d.province == null && (d.province += "");
        ref2$ = [d.kd, d.isl], kd_intel = ref2$[0], isl_intel = ref2$[1];
      }
      d.province_actions = [];
      attack_formats = [["tradmarch", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) invaded [province] [location] and captured", false], ["tradmarch", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) captured [0-9,]+ acres of land from [province] [location]", true], ["plunder", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) attacked and pillaged the lands of [province] [location]", false], ["plunder", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) invaded and pillaged [province] [location]", true], ["learn", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) attacked and stole from [province] [location]", false], ["learn", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) invaded and stole from [province] [location]", true], ["massacre", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) killed [0-9,]+ people within [province] [location]", true], ["massacre", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) invaded [province] [location] and killed", false], ["ambush", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) ambushed armies from [province] [location] and", false], ["ambush", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) recaptured [0-9,]+ acres of land from [province] [location]", true], ["raze", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) invaded [province] [location] and razed", true], ["raze", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) razed [0-9,]+ acres of [province] [location]", false], ["conquest", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]), invaded [province] [location] and captured", false], ["conquest", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]), captured [0-9,]+ acres of land from [province] [location]", true], ["failedattack", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) attempted to invade [province] [location]", false], ["failedattack", "(?<attacker>[province] [location]|An unknown province from [kingdom] [location]) attempted an invasion of [province] [location]", true]];
      fill_province_actions = function(province_actions, attack_formats, kd_intel, isl_intel){
        var ref$, _date, _action, _tick, _new_uto_time, attacker, i$, len$, attack_format, ref1$, target_prov, target_kd, target_isl, _attack, _self_is_attacker, ref2$, _kd_name, kd, isl, ref3$, prov, _acres, ref4$, ref5$;
        for (;;) {
          ref$ = this$.get("[uto_date] ([^.^!]+)", 0, -1), _date = ref$[0], _action = ref$[1];
          if (_date) {
            _tick = _new_uto_time = ns.utimeToInt(_date);
          }
          attacker = null;
          if (_date && _action) {
            for (i$ = 0, len$ = attack_formats.length; i$ < len$; ++i$) {
              attack_format = attack_formats[i$];
              ref1$ = this$.get("(In local kingdom strife )?" + attack_format[1], "attacker", -4, -2, -1, {
                data: _action
              }), attacker = ref1$[0], target_prov = ref1$[1], target_kd = ref1$[2], target_isl = ref1$[3];
              if (attacker) {
                _attack = attack_format[0];
                _self_is_attacker = attack_format[2];
                break;
              }
            }
            if (attacker) {
              ref2$ = this$.get("An unknown province from [kingdom] [location]", -4, -2, -1, {
                data: attacker
              }), _kd_name = ref2$[0], kd = ref2$[1], isl = ref2$[2];
              if (!_kd_name) {
                ref3$ = this$.get("[province] [location]", -4, -2, -1, {
                  data: attacker
                }), prov = ref3$[0], kd = ref3$[1], isl = ref3$[2];
              } else {
                prov = null;
              }
              if (["conquest", "tradmarch", "raze", "ambush"].indexOf(_attack) >= 0) {
                _acres = this$.get("[number] acre", {
                  data: _action
                });
              } else {
                _acres = null;
              }
              if (_self_is_attacker) {
                ref4$ = [kd, isl], kd_intel = ref4$[0], isl_intel = ref4$[1];
              } else {
                ref5$ = [target_kd, target_isl], kd_intel = ref5$[0], isl_intel = ref5$[1];
              }
              province_actions.push({
                "province": prov,
                "kd": kd,
                "isl": isl,
                "target_province": target_prov,
                "target_kd": target_kd,
                "target_isl": target_isl,
                "attack": _attack,
                "acres_taken": _acres,
                "tick": _tick
              });
            } else {
              province_actions.push({
                "tick": _tick,
                "action": _action
              });
            }
          } else {
            break;
          }
        }
        return [kd_intel, isl_intel];
      };
      if (true || ns.lastUtoTime) {
        ref3$ = fill_province_actions(d.province_actions, attack_formats, kd_intel, isl_intel), d.kd = ref3$[0], d.isl = ref3$[1];
        if (d.province_actions.length > 0) {
          d.tick_start = d.province_actions[0]["tick"];
          d.tick_end = (ref4$ = d.province_actions)[ref4$.length - 1]["tick"];
        }
      } else {
        province = kd = isl = null;
      }
      return d;
    };
    return NewsParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.MagicParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.MagicParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, MagicParser;
  c = upoopu.parsers.config;
  return ns.MagicParser = MagicParser = (function(superclass){
    MagicParser.displayName = 'MagicParser';
    var prototype = extend$(MagicParser, superclass).prototype, constructor = MagicParser;
    MagicParser.__responds_to__ = ["magic"];
    function MagicParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelOp;
      this.header_format = "Spell on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, get_spell_data, ref$, ref1$, ref2$, ref3$, this$ = this;
      this.rewind();
      d = {};
      get_spell_data = function(spell_string){
        var i$, ref$, len$, format, op, regex, no_caster_not_self, damage_to, match, is_self, ref1$, province, kd, isl;
        for (i$ = 0, len$ = (ref$ = c.SPELLS).length; i$ < len$; ++i$) {
          format = ref$[i$];
          if (format.length > 1) {
            op = format[0], regex = format[1], no_caster_not_self = format[2], damage_to = format[3];
            match = this$.get("(?<all>" + regex + ")", "durationnumber", "delta", "target", "all", {
              data: spell_string
            });
            if (match != null && (match[0] || match[1] || match[2] || match[3])) {
              if (match[2]) {
                is_self = false;
                ref1$ = this$.get("[province] [location]", 0, 2, 3, {
                  data: match[2]
                }), province = ref1$[0], kd = ref1$[1], isl = ref1$[2];
              } else if (no_caster_not_self) {
                is_self = false;
              } else {
                is_self = true;
              }
              return [format[0], province, kd, isl, match[0], match[1] ? -match[1] : void 8, is_self, damage_to ? "res_" + damage_to : void 8];
            }
          }
        }
      };
      ref$ = get_spell_data(this.get("magic", 1)), d.spell = ref$[0], d.province = ref$[1], d.kd = ref$[2], d.isl = ref$[3], d.dur = ref$[4], d.delta = ref$[5], d.is_self = ref$[6], d.resource = ref$[7];
      if (!d.province) {
        ref1$ = this.get("\\[[province]:[location]\\]", 0, 2, 3, {
          data: this.full_data,
          set_start: true
        }), d.province = ref1$[0], d.kd = ref1$[1], d.isl = ref1$[2];
      }
      if (d.is_self) {
        if (ns.selfData) {
          ref2$ = ns.selfData, d.province = ref2$[0], d.kd = ref2$[1], d.isl = ref2$[2];
        } else {
          ref3$ = [null, null, null], d.province = ref3$[0], d.kd = ref3$[1], d.isl = ref3$[2];
        }
      }
      d.province == null && (d.province += "");
      switch (d.spell) {
      case "landlust":
      case "tornadoes":
        d.delta_acres = d.delta;
        break;
      case "fireball":
        d.delta_peasants = d.delta;
      }
      /*
      else
          d.dur = data
          if isinstance(dur , str): dur = 0
      */
      d.intel_sub_type = d.spell;
      return d;
    };
    return MagicParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.InfiltrateParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.InfiltrateParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, InfiltrateParser;
  c = upoopu.parsers.config;
  return ns.InfiltrateParser = InfiltrateParser = (function(superclass){
    InfiltrateParser.displayName = 'InfiltrateParser';
    var prototype = extend$(InfiltrateParser, superclass).prototype, constructor = InfiltrateParser;
    InfiltrateParser.__responds_to__ = ["infiltrate"];
    function InfiltrateParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelInfiltrate;
      this.header_format = "Infiltrate on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, province;
      d = {};
      ref$ = this.get("Guilds of [province] [location]", 0, -2, -1), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      if (province) {
        province = province + "";
      }
      d.accuracy = this.get("our operation was a success and we have [pct] confidence in the information", {
        data: this.full_data.slice(Math.max(0, this.start_data - 450), this.start_data)
      });
      d.trust = Math.floor((d.accuracy || 0) / 25) + 1;
      d.thieves = this.get("about [number] thieves");
      return d;
    };
    return InfiltrateParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.ThieveryParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.ThieveryParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, ThieveryParser;
  c = upoopu.parsers.config;
  return ns.ThieveryParser = ThieveryParser = (function(superclass){
    ThieveryParser.displayName = 'ThieveryParser';
    var prototype = extend$(ThieveryParser, superclass).prototype, constructor = ThieveryParser;
    ThieveryParser.__responds_to__ = ["thievery"];
    function ThieveryParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelOp;
      this.header_format = "Op on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, get_thievery_data, ref1$, damage_to, this$ = this;
      this.rewind();
      d = {};
      ref$ = this.get("\\[[province]:[location]\\]", 0, 2, 3), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      if (d.province) {
        get_thievery_data = function(op_string){
          var i$, ref$, len$, format, op, regex, damage_to, match, duration, kind;
          for (i$ = 0, len$ = (ref$ = c.THIEFOPS).length; i$ < len$; ++i$) {
            format = ref$[i$];
            if (format.length > 1) {
              op = format[0], regex = format[1], damage_to = format[2];
              match = this$.get("(?<all>" + regex + ")", "durationnumber", "delta", "all", {
                data: op_string
              });
              if (match != null && (match[0] || match[1] || match[2])) {
                if (op == "bribegenerals" || op == "bribethieves") {
                  duration = 1;
                } else if (op == "propaganda") {
                  kind = this$.get("(?<all>" + regex + ")", "kind", {
                    data: op_string
                  });
                  damage_to = (fn$());
                } else {
                  duration = match[0];
                }
                break;
              }
            }
          }
          return [op, duration, match[1] ? -match[1] : void 8, damage_to ? "res_" + damage_to : void 8, damage_to];
          function fn$(){
            switch (kind) {
            case "wizards":
            case "thieves":
            case "soldiers":
              return kind;
            case "specialist":
              return "specs";
            default:
              return "elites";
            }
          }
        };
        ref1$ = get_thievery_data(this.get("thievery", 0)), d.thiefop = ref1$[0], d.dur = ref1$[1], d.delta = ref1$[2], d.resource = ref1$[3], damage_to = ref1$[4];
        d.province == null && (d.province += "");
        if (damage_to) {
          d["delta_" + damage_to] = d.delta;
        }
        d.intel_sub_type = d.thiefop;
      }
      return d;
    };
    return ThieveryParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.SelfSpellParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.SelfSpellParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, SelfSpellParser;
  c = upoopu.parsers.config;
  return ns.SelfSpellParser = SelfSpellParser = (function(superclass){
    SelfSpellParser.displayName = 'SelfSpellParser';
    var prototype = extend$(SelfSpellParser, superclass).prototype, constructor = SelfSpellParser;
    SelfSpellParser.__responds_to__ = ["selfspells"];
    function SelfSpellParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelSelfSpells;
      this.header_format = "Spells on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, get_spell_data, ref$, ref1$, this$ = this;
      this.rewind();
      d = {};
      get_spell_data = function(){
        var i$, ref$, len$, format, op, regex, no_caster_not_self, damage_to, match, dur, results$ = [];
        for (i$ = 0, len$ = (ref$ = c.SPELLS).length; i$ < len$; ++i$) {
          format = ref$[i$];
          if (format.length > 1) {
            op = format[0], regex = format[1], no_caster_not_self = format[2], damage_to = format[3];
            match = this$.get("(?<all>" + regex + ")", "all", "durationnumber", {
              data: this$.full_data
            });
            dur = match[1];
            if (!parseInt(dur)) {
              dur = 0;
            }
            if (match != null && match[0]) {
              if (match[1]) {
                results$.push({
                  spell: format[0],
                  dur: dur
                });
              } else {
                results$.push({
                  spell: format[0]
                });
              }
            }
          }
        }
        return results$;
      };
      d.spells = get_spell_data();
      if (ns.selfData) {
        ref$ = ns.selfData, d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      } else {
        ref1$ = [null, null, null], d.province = ref1$[0], d.kd = ref1$[1], d.isl = ref1$[2];
      }
      d.province == null && (d.province += "");
      return d;
    };
    return SelfSpellParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.AttackParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.AttackParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, AttackParser;
  c = upoopu.parsers.config;
  return ns.AttackParser = AttackParser = (function(superclass){
    AttackParser.displayName = 'AttackParser';
    var prototype = extend$(AttackParser, superclass).prototype, constructor = AttackParser;
    AttackParser.__responds_to__ = ["som", "selfsom"];
    function AttackParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelAttack;
      this.header_format = "Attack on ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, ref$, _attack_data, ref1$;
      d = {};
      ref$ = this.get("attack", 1, 3, 4), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      d.attack_time = this.get("Our forces will be available again in [number] days");
      _attack_data = this.full_data.slice(this.start_data, this.end_data);
      d.taken_acres = this.get("Your army has taken [number] acres", {
        data: _attack_data
      });
      if (d.taken_acres) {
        d.attack_type = "tradmarch";
      }
      d.destroyed_p_t_and_m = this.get("Your army massacred [number]", {
        data: _attack_data
      });
      if (d.destroyed_p_t_and_m) {
        d.attack_type = "massacre";
      }
      d.destroyed_acres = this.get("Your army burned and destroyed [number] acres", {
        data: _attack_data
      });
      if (d.destroyed_acres) {
        d.attack_type = "raze";
      }
      ref1$ = this.get("Your army looted [number] gold coins, [number] bushels and [number] runes", -3, -2, -1, {
        data: _attack_data
      }), d.taken_gold = ref1$[0], d.taken_food = ref1$[1], d.taken_runes = ref1$[2];
      if (d.taken_gold) {
        d.attack_type = "plunder";
      }
      d.taken_books = this.get("Your army stole [number] books", {
        data: _attack_data
      });
      if (d.taken_books) {
        d.attack_type = "learn";
      }
      if (this.get("(Your army was no match for the defenses of)", {
        data: _attack_data
      })) {
        d.attack_type = "failedattack";
      }
      d.kills = this.get("We killed about [number] enemy troops", {
        data: _attack_data
      });
      d.kills += this.get("imprisoned [number] additional troops", {
        data: _attack_data
      }) || 0;
      d.taken_peasants = this.get("[number] peasants settled", {
        data: _attack_data
      });
      if (this.get("(A tough battle took place)", {
        data: _attack_data
      })) {
        d.oversend = 'NOOVERSEND';
      } else if (this.get("(Emerged Victorious)", {
        data: _attack_data
      })) {
        d.oversend = 'EVOVERSEND';
      } else if (this.get("(tough but resounding victory)", {
        data: _attack_data
      })) {
        d.oversend = 'TBROVERSEND';
      } else if (this.get("(crushes their resistance)", {
        data: _attack_data
      })) {
        d.oversend = 'CRUSHOVERSEND';
      } else if (this.get("(little meaningful resistance)", {
        data: _attack_data
      })) {
        d.oversend = 'LMROVERSEND';
      } else {
        d.oversend = 'UNKNOWNOVERSEND';
      }
      return d;
    };
    return AttackParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers.AidParser

description: Parsers for utopia data

requires: 
    - upoopu.parsers.BaseParser

provides: [upoopu.parsers.AidParser]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, AidParser;
  c = upoopu.parsers.config;
  return ns.AidParser = AidParser = (function(superclass){
    AidParser.displayName = 'AidParser';
    var prototype = extend$(AidParser, superclass).prototype, constructor = AidParser;
    AidParser.__responds_to__ = ["som", "selfsom"];
    function AidParser(data, type){
      superclass.apply(this, arguments);
      this.pb = UpoOpu.pb.IntelAid;
      this.header_format = "Aid to ${province} (${kd}:${isl})";
    }
    prototype._parse = function(){
      var d, _aid_string, ref$, province;
      d = {};
      _aid_string = this.get("(We have sent .* it should arrive shortly)");
      d.food = this.get("[number] bushel", {
        data: _aid_string
      });
      d.gold = this.get("[number] gold", {
        data: _aid_string
      });
      d.runes = this.get("[number] rune", {
        data: _aid_string
      });
      d.soldiers = this.get("[number] soldier", {
        data: _aid_string
      });
      ref$ = this.get("to [province] [location]", 0, -2, -1, {
        data: _aid_string
      }), d.province = ref$[0], d.kd = ref$[1], d.isl = ref$[2];
      if (province) {
        province = province + "";
      }
      return d;
    };
    return AidParser;
  }(ns.BaseParser));
});
function extend$(sub, sup){
  function fun(){} fun.prototype = (sub.superclass = sup).prototype;
  (sub.prototype = new fun).constructor = sub;
  if (typeof sup.extended == 'function') sup.extended(sub);
  return sub;
}

/*
---
name: upoopu.parsers

description: Parsers for utopia data

requires:
   - xregexp-all.js
   - upoopu.parsers.BaseParser
   - upoopu.parsers.KDParser
   - upoopu.parsers.ScienceParser
   - upoopu.parsers.SoMParser
   - upoopu.parsers.SoTParser
   - upoopu.parsers.SurveyParser
   - upoopu.parsers.NewsParser
   - upoopu.parsers.MagicParser
   - upoopu.parsers.InfiltrateParser
   - upoopu.parsers.ThieveryParser
   - upoopu.parsers.SelfSpellParser
   - upoopu.parsers.AttackParser
   - upoopu.parsers.AidParser

provides: [upoopu.parsers]
...
*/
namespace("upoopu.parsers", function(ns){
  var c, buildStrings;
  c = upoopu.parsers.config;
  ns.NO_PARSER_FOUND = "No Parser Found";
  buildStrings = {};
  ns.buildRegex = function(s){
    var regexString, i$, ref$, len$, t, j$, ref1$, len1$, f, holder, regex;
    if (buildStrings[s] != null) {
      regexString = buildStrings[s];
    } else {
      regexString = buildStrings[s] = s;
      for (i$ = 0, len$ = (ref$ = [0, 1, 2]).length; i$ < len$; ++i$) {
        t = ref$[i$];
        for (j$ = 0, len1$ = (ref1$ = c.FORMATS).length; j$ < len1$; ++j$) {
          f = ref1$[j$];
          holder = f[0];
          regex = f[1];
          while (regexString.indexOf("[" + holder + "]") >= 0) {
            regexString = buildStrings[s] = regexString.replace("[" + holder + "]", "(" + regex + ")");
          }
        }
      }
    }
    return regexString;
  };
  ns.getParser = function(data, type, meta){
    type == null && (type = "base");
    meta == null && (meta = {});
    switch (type) {
    case "base":
      return new ns.BaseParser(data, null, meta);
    case "self":
      return new ns.SelfParser(" " + data + "  ", type, meta);
    case "sot":
    case "selfsot":
      return new ns.SoTParser(" " + data + "  ", type, meta);
    case "som":
    case "selfsom":
      return new ns.SoMParser(" " + data + "  ", type, meta);
    case "survey":
    case "selfsurvey":
      return new ns.SurveyParser(" " + data + "  ", type, meta);
    case "science":
    case "selfscience":
      return new ns.ScienceParser(" " + data + "  ", type, meta);
    case "kdnews":
      return new ns.NewsParser(" " + data + "  ", type, meta);
    case "kd":
      return new ns.KDParser(" " + data + "  ", type, meta);
    case "magic":
      return new ns.MagicParser(" " + data + "  ", type, meta);
    case "infiltrate":
      return new ns.InfiltrateParser(" " + data + "  ", type, meta);
    case "thievery":
      return new ns.ThieveryParser(" " + data + "  ", type, meta);
    case "selfspells":
      return new ns.SelfSpellParser(" " + data + "  ", type, meta);
    case "attack":
      return new ns.AttackParser(" " + data + "  ", type, meta);
    case "aid":
      return new ns.AidParser(" " + data + "  ", type, meta);
    default:
      throw ns.NO_PARSER_FOUND;
    }
  };
  ns.findParser = function(data, meta){
    var i$, ref$, len$, d, fullRegex, pat, res;
    for (i$ = 0, len$ = (ref$ = c.DATASTRINGS).length; i$ < len$; ++i$) {
      d = ref$[i$];
      fullRegex = ns.buildRegex(d[1]);
      if (fullRegex.indexOf("(?<") >= 0) {
        pat = new XRegExp(fullRegex, "i");
        res = XRegExp.exec(data, pat);
      } else {
        pat = new RegExp(fullRegex, "i");
        res = pat.exec(data);
      }
      if (res) {
        debug("1: "+ res);
        debug("2: "+ d[0]);
        return ns.getParser(data, d[0], meta);
      }
    }
    throw ns.NO_PARSER_FOUND;
  };
  return ns.mergeToBulk = function(pb, bulk){
    var key, t;
    bulk == null && (bulk = new UpoOpu.pb.IntelCombined);
    for (key in bulk.properties_) {
      try {
        t = bulk.properties_[key]["type"]();
      } catch (e$) {}
      try {
        if (pb instanceof t) {
          bulk[key].push(pb);
          break;
        }
      } catch (e$) {}
    }
    return bulk;
  };
});


    /*
    ---

    name: upoopu.intel.friendly.data

    description: Auto generated friendly data

    requires: []

    provides: [upoopu.intel.friendly.data]

    ...
    */

    var upoopu = upoopu || {};
    upoopu.intel = upoopu.intel || {};
    upoopu.intel.friendly = upoopu.intel.friendly || {};
    upoopu.intel.friendly.data = {
    "enums": [
        {
            "fields": [
                {
                    "nr": 1, 
                    "friendly": "Barrenlands", 
                    "name": "BARRENLANDS"
                }, 
                {
                    "nr": 2, 
                    "friendly": "Homes", 
                    "name": "HOMES", 
                    "build_stats": [
                        {
                            "effect": "Birthrate", 
                            "pct": 4
                        }
                    ]
                }, 
                {
                    "nr": 3, 
                    "build_stats": [
                        {
                            "abs": 70, 
                            "effect": "Bushels"
                        }
                    ], 
                    "friendly": "Farms", 
                    "name": "FARMS"
                }, 
                {
                    "nr": 4, 
                    "build_stats": [
                        {
                            "cap": -99, 
                            "effect": "Build Costs", 
                            "pct": -4
                        }, 
                        {
                            "effect": "Explore Costs", 
                            "pct": -3
                        }, {
                            "effect": "Explore Soldier Costs", 
                            "pct": -2
                        }
                    ], 
                    "friendly": "Mills", 
                    "name": "MILLS"
                }, 
                {
                    "nr": 5, 
                    "build_stats": [
                        {
                            "abs": 25, 
                            "effect": "Gold"
                        }, 
                        {
                            "effect": "Income", 
                            "pct": 1.25
                        }
                    ], 
                    "friendly": "Banks", 
                    "name": "BANKS"
                }, 
                {
                    "nr": 6, 
                    "build_stats": [
                        {
                            "effect": "OME", 
                            "pct": 1.5
                        }
                    ], 
                    "friendly": "Traininggrounds", 
                    "name": "TRAININGGROUNDS"
                }, 
                {
                    "nr": 7, 
                    "build_stats": [
                        {
                            "effect": "Training Cost", 
                            "pct": -1.5
                        }, 
                        {
                            "effect": "Draft", 
                            "pct": -2
                        }, 
                        {
                            "effect": "Wages", 
                            "pct": -2
                        }
                    ], 
                    "friendly": "Armouries", 
                    "name": "ARMOURIES"
                }, 
                {
                    "nr": 8, 
                    "build_stats": [
                        {
                            "effect": "Attack Time", 
                            "pct": -1.5
                        }
                    ], 
                    "friendly": "Barracks", 
                    "name": "BARRACKS"
                }, 
                {
                    "nr": 9, 
                    "build_stats": [
                        {
                            "effect": "DME", 
                            "pct": 2
                        }
                    ], 
                    "friendly": "Forts", 
                    "name": "FORTS"
                }, 
                {
                    "nr": 10, 
                    "build_stats": [
                        {
                            "effect": "Land Loss", 
                            "pct": -2
                        }
                    ], 
                    "friendly": "Guardstations", 
                    "name": "GUARDSTATIONS"
                }, 
                {
                    "nr": 11, 
                    "build_stats": [
                        {
                            "effect": "Military Loss", 
                            "pct": -3
                        }
                    ], 
                    "friendly": "Hospitals", 
                    "name": "HOSPITALS"
                }, 
                {
                    "nr": 12, 
                    "build_stats": [
                        {
                            "abs": 0.02, 
                            "effect": "Wizzards"
                        }
                    ], 
                    "friendly": "Guilds", 
                    "name": "GUILDS"
                }, 
                {
                    "nr": 13, 
                    "build_stats": [
                        {
                            "abs": 12, 
                            "effect": "Runes"
                        }
                    ], 
                    "friendly": "Towers", 
                    "name": "TOWERS"
                }, 
                {
                    "nr": 14, 
                    "build_stats": [
                        {
                            "effect": "TPA", 
                            "pct": 3
                        }, 
                        {
                            "cap": -95, 
                            "effect": "Thief losses", 
                            "pct": -4
                        }
                    ], 
                    "friendly": "Thievesdens", 
                    "name": "THIEVESDENS"
                }, 
                {
                    "nr": 15, 
                    "build_stats": [
                        {
                            "effect": "Catch thieves", 
                            "pct": 2
                        }, 
                        {
                            "cap": -95, 
                            "effect": "Thief Damage", 
                            "pct": -4
                        }
                    ], 
                    "friendly": "Watchtowers", 
                    "name": "WATCHTOWERS"
                }, 
                {
                    "nr": 16, 
                    "build_stats": [
                        {
                            "no_be": 1, 
                            "effect": "Inc Science", 
                            "pct": 2
                        }
                    ], 
                    "friendly": "Libraries", 
                    "name": "LIBRARIES"
                }, 
                {
                    "nr": 17, 
                    "build_stats": [
                        {
                            "effect": "Books Costs", 
                            "pct": -1.5
                        }, 
                        {
                            "effect": "Books Losses", 
                            "pct": -5
                        }
                    ], 
                    "friendly": "Schools", 
                    "name": "SCHOOLS"
                }, 
                {
                    "nr": 18, 
                    "build_stats": [
                        {
                            "abs": 80, 
                            "effect": "Hold Horses", 
                            "no_be": 1
                        }, 
                        {
                            "abs": 2, 
                            "effect": "Create Horses"
                        }
                    ], 
                    "friendly": "Stables", 
                    "name": "STABLES"
                }, 
                {
                    "nr": 19, 
                    "build_stats": [
                        {
                            "abs": 20, 
                            "effect": "Hold pris", 
                            "no_be": 1
                        }
                    ], 
                    "friendly": "Dungeons", 
                    "name": "DUNGEONS"
                }
            ], 
            "name": "BuildingType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "GOLD", 
                    "friendly": "Gold"
                }, 
                {
                    "nr": 2, 
                    "name": "RUBY", 
                    "friendly": "Ruby"
                }, 
                {
                    "nr": 3, 
                    "name": "SAPPHIRE", 
                    "friendly": "Sapphire"
                }, 
                {
                    "nr": 4, 
                    "name": "EMERALD", 
                    "friendly": "Emerald"
                }, 
                {
                    "nr": 5, 
                    "name": "NODRAGON", 
                    "friendly": "No Dragon"
                }
            ], 
            "name": "DragonType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "PEASANT", 
                    "friendly": "Peasant"
                }, 
                {
                    "nr": 2, 
                    "name": "KNIGHT", 
                    "friendly": "Knight"
                }, 
                {
                    "nr": 3, 
                    "name": "LORD", 
                    "friendly": "Lord"
                }, 
                {
                    "nr": 4, 
                    "name": "BARON", 
                    "friendly": "Baron"
                }, 
                {
                    "nr": 5, 
                    "name": "VISCOUNT", 
                    "friendly": "Viscount"
                }, 
                {
                    "nr": 6, 
                    "name": "COUNT", 
                    "friendly": "Count"
                }, 
                {
                    "nr": 7, 
                    "name": "MARQUIS", 
                    "friendly": "Marquis"
                }, 
                {
                    "nr": 8, 
                    "name": "DUKE", 
                    "friendly": "Duke"
                }, 
                {
                    "nr": 9, 
                    "name": "PRINCE", 
                    "friendly": "Prince"
                }
            ], 
            "name": "HonorType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "MERCHANT", 
                    "friendly": "Merchant"
                }, 
                {
                    "nr": 2, 
                    "name": "SHEPHERD", 
                    "friendly": "Shepherd"
                }, 
                {
                    "nr": 3, 
                    "name": "SAGE", 
                    "friendly": "Sage"
                }, 
                {
                    "nr": 4, 
                    "name": "ROGUE", 
                    "friendly": "Rogue"
                }, 
                {
                    "nr": 5, 
                    "name": "MYSTIC", 
                    "friendly": "Mystic"
                }, 
                {
                    "nr": 6, 
                    "name": "WARRIOR", 
                    "friendly": "Warrior"
                }, 
                {
                    "nr": 7, 
                    "name": "WARHERO", 
                    "friendly": "War Hero"
                }, 
                {
                    "nr": 8, 
                    "name": "ARTISAN", 
                    "friendly": "Artisan"
                }, 
                {
                    "nr": 9, 
                    "name": "TACTICIAN", 
                    "friendly": "Tactician"
                }, 
                {
                    "nr": 10, 
                    "name": "CLERIC", 
                    "friendly": "Cleric"
                }
            ], 
            "name": "PersonalityType"
        }, 
        {
            "fields": [
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Archers"
                    }, 
                    "name": "HUMAN", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Swordsmen"
                    }, 
                    "nr": 1, 
                    "elite": {
                        "offense": 6, 
                        "defense": 3, 
                        "name": "Knights", 
                        "nw": 6.5
                    }, 
                    "friendly": "Human"
                }, 
                {
                    "dspec": {
                        "defense": 5, 
                        "name": "Archers"
                    }, 
                    "name": "ELF", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Rangers"
                    }, 
                    "nr": 2, 
                    "elite": {
                        "offense": 5, 
                        "defense": 4, 
                        "name": "Elf Lords", 
                        "nw": 5.75
                    }, 
                    "friendly": "Elf"
                }, 
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Axemen"
                    }, 
                    "name": "DWARF", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Warriors"
                    }, 
                    "nr": 3, 
                    "elite": {
                        "offense": 6, 
                        "defense": 3, 
                        "name": "Berserkers", 
                        "nw": 5.25
                    }, 
                    "friendly": "Dwarf"
                }, 
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Trolls"
                    }, 
                    "name": "ORC", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Goblins"
                    }, 
                    "nr": 4, 
                    "elite": {
                        "offense": 7, 
                        "defense": 1, 
                        "name": "Ogres", 
                        "nw": 5.75
                    }, 
                    "friendly": "Orc"
                }, 
                {
                    "dspec": {
                        "defense": 5, 
                        "name": "Pikemen"
                    }, 
                    "name": "GNOME", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 6, 
                        "name": "Quickblades"
                    }, 
                    "nr": 5, 
                    "elite": {
                        "offense": 5, 
                        "defense": 5, 
                        "name": "Golems", 
                        "nw": 4.0
                    }, 
                    "friendly": "Gnome"
                }, 
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Slingers"
                    }, 
                    "name": "HALFLING", 
                    "soldier": {
                        "offense": 2, 
                        "defense": 2
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Strongarms"
                    }, 
                    "nr": 6, 
                    "elite": {
                        "offense": 4, 
                        "defense": 4, 
                        "name": "Brutes", 
                        "nw": 5
                    }, 
                    "friendly": "Halfling"
                }, 
                {
                    "dspec": {
                        "defense": 5, 
                        "name": "Druids"
                    }, 
                    "name": "DARKELF", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 5, 
                        "name": "Night Rangers"
                    }, 
                    "nr": 7, 
                    "elite": {
                        "offense": 6, 
                        "defense": 4, 
                        "name": "Drows", 
                        "nw": 5.0
                    }, 
                    "friendly": "Dark Elf"
                }, 
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Harpies"
                    }, 
                    "name": "AVIAN", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 5, 
                        "name": "Griffins"
                    }, 
                    "nr": 8, 
                    "elite": {
                        "offense": 6, 
                        "defense": 2, 
                        "name": "Drakes", 
                        "nw": 5
                    }, 
                    "friendly": "Avian"
                }, 
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Druids"
                    }, 
                    "name": "FAERY", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Magicians"
                    }, 
                    "nr": 10, 
                    "elite": {
                        "offense": 3, 
                        "defense": 5, 
                        "name": "Beast Masters", 
                        "nw": 5.25
                    }, 
                    "friendly": "Faery"
                }, 
                {
                    "dspec": {
                        "defense": 4, 
                        "name": "Zombies"
                    }, 
                    "name": "UNDEAD", 
                    "soldier": {
                        "offense": 1, 
                        "defense": 1
                    }, 
                    "ospec": {
                        "offense": 4, 
                        "name": "Skeletons"
                    }, 
                    "nr": 9, 
                    "elite": {
                        "offense": 7, 
                        "defense": 2, 
                        "name": "Ghouls", 
                        "nw": 6.25
                    }, 
                    "friendly": "Undead"
                }
            ], 
            "name": "RaceType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "NOREL", 
                    "friendly": "Norel"
                }, 
                {
                    "nr": 2, 
                    "name": "UNFRIENDLY", 
                    "friendly": "Unfriendly"
                }, 
                {
                    "nr": 3, 
                    "name": "HOSTILE", 
                    "friendly": "Hostile"
                }, 
                {
                    "nr": 4, 
                    "name": "WAR", 
                    "friendly": "War"
                }, 
                {
                    "nr": 5, 
                    "name": "CF", 
                    "friendly": "Cf"
                }
            ], 
            "name": "RelationType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "friendly": "Alchemy", 
                    "name": "ALCHEMY", 
                    "factor": 1.4
                }, 
                {
                    "nr": 2, 
                    "friendly": "Tools", 
                    "name": "TOOLS", 
                    "factor": 1
                }, 
                {
                    "nr": 3, 
                    "friendly": "Housing", 
                    "name": "HOUSING", 
                    "factor": 0.65
                }, 
                {
                    "nr": 4, 
                    "friendly": "Food", 
                    "name": "FOOD", 
                    "factor": 8
                }, 
                {
                    "nr": 5, 
                    "friendly": "Military", 
                    "name": "MILITARY", 
                    "factor": 0.5
                }, 
                {
                    "nr": 6, 
                    "friendly": "Thievery", 
                    "name": "THIEVERY", 
                    "factor": 6
                }, 
                {
                    "nr": 7, 
                    "friendly": "Channeling", 
                    "name": "CHANNELING", 
                    "factor": 6
                }
            ], 
            "name": "ScienceType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "MALE", 
                    "friendly": "Male"
                }, 
                {
                    "nr": 2, 
                    "name": "FEMALE", 
                    "friendly": "Female"
                }
            ], 
            "name": "SexType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "MINORPROTECTION", 
                    "friendly": "Minor Protection"
                }, 
                {
                    "nr": 2, 
                    "name": "GREATERPROTECTION", 
                    "friendly": "Greater Protection"
                }, 
                {
                    "nr": 3, 
                    "name": "FOG", 
                    "friendly": "Fog"
                }, 
                {
                    "nr": 4, 
                    "name": "MAGICSHIELD", 
                    "friendly": "Magic Shield"
                }, 
                {
                    "nr": 5, 
                    "name": "MYSTICAURA", 
                    "friendly": "Mystic Aura"
                }, 
                {
                    "nr": 6, 
                    "name": "FERTILELANDS", 
                    "friendly": "Fertile Lands"
                }, 
                {
                    "nr": 7, 
                    "name": "NATURESBLESSING", 
                    "friendly": "Natures Blessing"
                }, 
                {
                    "nr": 8, 
                    "name": "LOVEANDPEACE", 
                    "friendly": "Love and Peace"
                }, 
                {
                    "nr": 9, 
                    "name": "TREEOFGOLD", 
                    "friendly": "Tree of Gold"
                }, 
                {
                    "nr": 10, 
                    "name": "QUICKFEET", 
                    "friendly": "Quickfeet"
                }, 
                {
                    "nr": 11, 
                    "name": "BUILDERSBOON", 
                    "friendly": "Builders Boon"
                }, 
                {
                    "nr": 12, 
                    "name": "INSPIREARMY", 
                    "friendly": "Inspire Army"
                }, 
                {
                    "nr": 13, 
                    "name": "ANONYMITY", 
                    "friendly": "Anonymity"
                }, 
                {
                    "nr": 14, 
                    "name": "INVISIBILITY", 
                    "friendly": "Invisibility"
                }, 
                {
                    "nr": 15, 
                    "name": "CLEARSIGHT", 
                    "friendly": "Clearsight"
                }, 
                {
                    "nr": 17, 
                    "name": "WARSPOILS", 
                    "friendly": "Warspoils"
                }, 
                {
                    "nr": 18, 
                    "name": "FANATICISM", 
                    "friendly": "Fanaticism"
                }, 
                {
                    "nr": 19, 
                    "name": "FOUNTAINOFKNOWLEDGE", 
                    "friendly": "Fountain of Knowledge"
                }, 
                {
                    "nr": 20, 
                    "name": "TOWNWATCH", 
                    "friendly": "Town Watch"
                }, 
                {
                    "nr": 21, 
                    "name": "AGGRESSION", 
                    "friendly": "Aggression"
                }, 
                {
                    "nr": 22, 
                    "name": "ANIMATEDEAD", 
                    "friendly": "Animate Dead"
                }, 
                {
                    "nr": 23, 
                    "name": "REFLECTMAGIC", 
                    "friendly": "Reflect Magic"
                }, 
                {
                    "nr": 24, 
                    "name": "SHADOWLIGHT", 
                    "friendly": "Shadow Light"
                }, 
                {
                    "nr": 25, 
                    "name": "PATRIOTISM", 
                    "friendly": "Patriotism"
                }, 
                {
                    "nr": 26, 
                    "name": "PARADISE", 
                    "friendly": "Paradise"
                }, 
                {
                    "nr": 27, 
                    "name": "STORMS", 
                    "friendly": "Storms"
                }, 
                {
                    "nr": 28, 
                    "name": "DROUGHTS", 
                    "friendly": "Droughts"
                }, 
                {
                    "nr": 29, 
                    "name": "VERMIN", 
                    "friendly": "Vermin"
                }, 
                {
                    "nr": 30, 
                    "name": "EXPOSETHIEVES", 
                    "friendly": "Expose Thieves"
                }, 
                {
                    "nr": 31, 
                    "name": "GREED", 
                    "friendly": "Greed"
                }, 
                {
                    "nr": 32, 
                    "name": "FOOLSGOLD", 
                    "friendly": "Fools Gold"
                }, 
                {
                    "nr": 33, 
                    "name": "PITFALLS", 
                    "friendly": "Pitfalls"
                }, 
                {
                    "nr": 34, 
                    "name": "FIREBALL", 
                    "friendly": "Fireball"
                }, 
                {
                    "nr": 35, 
                    "name": "CHASTITY", 
                    "friendly": "Chastity"
                }, 
                {
                    "nr": 36, 
                    "name": "LIGHTNINGSTRIKE", 
                    "friendly": "Lightning Strike"
                }, 
                {
                    "nr": 37, 
                    "name": "EXPLOSIONS", 
                    "friendly": "Explosions"
                }, 
                {
                    "nr": 38, 
                    "name": "AMNESIA", 
                    "friendly": "Amnesia"
                }, 
                {
                    "nr": 39, 
                    "name": "NIGHTMARES", 
                    "friendly": "Nightmares"
                }, 
                {
                    "nr": 40, 
                    "name": "MYSTICVORTEX", 
                    "friendly": "Mystic Vortex"
                }, 
                {
                    "nr": 41, 
                    "name": "METEORSHOWERS", 
                    "friendly": "Meteor Showers"
                }, 
                {
                    "nr": 42, 
                    "name": "TORNADOES", 
                    "friendly": "Tornadoes"
                }, 
                {
                    "nr": 43, 
                    "name": "LANDLUST", 
                    "friendly": "Landlust"
                }, 
                {
                    "nr": 44, 
                    "name": "MAGESFURY", 
                    "friendly": "Magesfury"
                }, 
                {
                    "nr": 45, 
                    "name": "BLOODLUST", 
                    "friendly": "Bloodlust"
                }
            ], 
            "name": "SpellType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "NORMAL", 
                    "friendly": "Normal"
                }, 
                {
                    "nr": 2, 
                    "name": "PEACEFUL", 
                    "friendly": "Peaceful"
                }, 
                {
                    "nr": 3, 
                    "name": "AGGRESSIVE", 
                    "friendly": "Aggressive"
                }, 
                {
                    "nr": 4, 
                    "name": "FORTIFIED", 
                    "friendly": "Fortified"
                }, 
                {
                    "nr": 5, 
                    "name": "STANCEWAR", 
                    "friendly": "Stancewar"
                }
            ], 
            "name": "StanceType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "SPYONTHRONE", 
                    "friendly": "Spyonthrone"
                }, 
                {
                    "nr": 2, 
                    "name": "SNATCHNEWS", 
                    "friendly": "Snatchnews"
                }, 
                {
                    "nr": 3, 
                    "name": "INFILTRATE", 
                    "friendly": "Infiltrate"
                }, 
                {
                    "nr": 4, 
                    "name": "SURVEY", 
                    "friendly": "Survey"
                }, 
                {
                    "nr": 5, 
                    "name": "SPYONMILITARY", 
                    "friendly": "Spyonmilitary"
                }, 
                {
                    "nr": 6, 
                    "name": "SPYONSCIENCES", 
                    "friendly": "Spyonsciences"
                }, 
                {
                    "nr": 7, 
                    "name": "SABOTAGEWIZARDS", 
                    "friendly": "Sabotagewizards"
                }, 
                {
                    "nr": 8, 
                    "name": "ROBTHEGRANARIES", 
                    "friendly": "Robthegranaries"
                }, 
                {
                    "nr": 9, 
                    "name": "ROBTHEVAULTS", 
                    "friendly": "Robthevaults"
                }, 
                {
                    "nr": 10, 
                    "name": "ROBTHETOWERS", 
                    "friendly": "Robthetowers"
                }, 
                {
                    "nr": 11, 
                    "name": "KIDNAPPING", 
                    "friendly": "Kidnapping"
                }, 
                {
                    "nr": 12, 
                    "name": "ARSON", 
                    "friendly": "Arson"
                }, 
                {
                    "nr": 13, 
                    "name": "GREATERARSON", 
                    "friendly": "Greaterarson"
                }, 
                {
                    "nr": 14, 
                    "name": "NIGHTSTRIKE", 
                    "friendly": "Nightstrike"
                }, 
                {
                    "nr": 15, 
                    "name": "INCITERIOTS", 
                    "friendly": "Inciteriots"
                }, 
                {
                    "nr": 16, 
                    "name": "STEALHORSES", 
                    "friendly": "Stealhorses"
                }, 
                {
                    "nr": 17, 
                    "name": "BRIBETHIEVES", 
                    "friendly": "Bribethieves"
                }, 
                {
                    "nr": 18, 
                    "name": "BRIBEGENERALS", 
                    "friendly": "Bribegenerals"
                }, 
                {
                    "nr": 19, 
                    "name": "FREEPRISONERS", 
                    "friendly": "Freeprisoners"
                }, 
                {
                    "nr": 20, 
                    "name": "ASSASSINATEWIZARDS", 
                    "friendly": "AW"
                }, 
                {
                    "nr": 30, 
                    "name": "PROPAGANDA", 
                    "friendly": "Propaganda"
                }
            ], 
            "name": "ThiefType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "TRADMARCH", 
                    "friendly": "Tradmarch"
                }, 
                {
                    "nr": 2, 
                    "name": "RAZE", 
                    "friendly": "Raze"
                }, 
                {
                    "nr": 3, 
                    "name": "PLUNDER", 
                    "friendly": "Plunder"
                }, 
                {
                    "nr": 4, 
                    "name": "LEARN", 
                    "friendly": "Learn"
                }, 
                {
                    "nr": 5, 
                    "name": "MASSACRE", 
                    "friendly": "Massacre"
                }, 
                {
                    "nr": 6, 
                    "name": "CONQUEST", 
                    "friendly": "Conquest"
                }, 
                {
                    "nr": 7, 
                    "name": "AMBUSH", 
                    "friendly": "Ambush"
                }, 
                {
                    "nr": 8, 
                    "name": "FAILEDATTACK", 
                    "friendly": "Fail"
                }
            ], 
            "name": "AttackType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "NOOVERSEND", 
                    "friendly": "Nooversend"
                }, 
                {
                    "nr": 2, 
                    "name": "EVOVERSEND", 
                    "friendly": "Evoversend"
                }, 
                {
                    "nr": 3, 
                    "name": "TBROVERSEND", 
                    "friendly": "Tbroversend"
                }, 
                {
                    "nr": 4, 
                    "name": "CRUSHOVERSEND", 
                    "friendly": "Crushoversend"
                }, 
                {
                    "nr": 5, 
                    "name": "LMROVERSEND", 
                    "friendly": "Lmroversend"
                }, 
                {
                    "nr": 6, 
                    "name": "UNKNOWNOVERSEND", 
                    "friendly": "Unknownoversend"
                }
            ], 
            "name": "AttackOversendType"
        }, 
        {
            "fields": [
                {
                    "nr": 1, 
                    "name": "RES_GOLD", 
                    "friendly": "Res_gold"
                }, 
                {
                    "nr": 2, 
                    "name": "RES_RUNES", 
                    "friendly": "Res_runes"
                }, 
                {
                    "nr": 3, 
                    "name": "RES_FOOD", 
                    "friendly": "Res_food"
                }, 
                {
                    "nr": 4, 
                    "name": "RES_PEASANTS", 
                    "friendly": "Res_peasants" 
                }, 
                {
                    "nr": 5, 
                    "name": "RES_SOLDIERS", 
                    "friendly": "Res_soldiers"
                }, 
                {
                    "nr": 6, 
                    "name": "RES_OSPECS", 
                    "friendly": "Res_ospecs"
                }, 
                {
                    "nr": 7, 
                    "name": "RES_DSPECS", 
                    "friendly": "Res_dspecs"
                }, 
                {
                    "nr": 8, 
                    "name": "RES_ELITES", 
                    "friendly": "Res_elites"
                }, 
                {
                    "nr": 9, 
                    "name": "RES_THIEVES", 
                    "friendly": "Res_thieves"
                }, 
                {
                    "nr": 10, 
                    "name": "RES_WIZARDS", 
                    "friendly": "Res_wizards"
                }, 
                {
                    "nr": 11, 
                    "name": "RES_LAND", 
                    "friendly": "Res_land"
                }, 
                {
                    "nr": 12, 
                    "name": "RES_SCIENCE", 
                    "friendly": "Res_science"
                }, 
                {
                    "nr": 13, 
                    "name": "RES_HORSES", 
                    "friendly": "Res_horses"
                }, 
                {
                    "nr": 14, 
                    "name": "RES_PRISONERS", 
                    "friendly": "Res_prisoners"
                }, 
                {
                    "nr": 15, 
                    "name": "RES_SPECS", 
                    "friendly": "Res_specs"
                }, 
                {
                    "nr": 16, 
                    "name": "RES_TROOPS", 
                    "friendly": "Res_troops"
                }, 
                {
                    "nr": 17, 
                    "name": "RES_BUILDINGS", 
                    "friendly": "Res_buildings"
                }
            ], 
            "name": "ResourceType"
        }
    ]
}

/*
---

name: upoopu.intel.utopia.enhance

description: Intel Displaying, is fun

requires: [Core, upoopu.intel.friendly.data, friendly.json.js]

provides: [upoopu.intel.utopia.enhance]

...
*/

var upoopu = upoopu || {};
upoopu.intel = upoopu.intel || {};
upoopu.intel.utopia = upoopu.intel.utopia || {};
upoopu.intel.utopia.enhance = upoopu.intel.utopia.enhance || {};

 
// make a nice dictionary



(function(){
    var ns = upoopu.intel.utopia.enhance
    
var fantasy_places = ["Aldbay Field", "Barrowden", "Beltown", "Bluebridge", "Bluehaven Hollow", "Blueriver Bridge", "Butterbeech", "Butterhaven", "Butternesse", "Coldbeach", "Courtacre", "Draccliff",
"Dragonpond", "Esterburn", "Freyhedge", "Goldbeach", "Goldburn", "Goldkeep", "Goldshore", "Greencrest", "Griffinley", "Highsilver Haven", "Ironburn Barrens", "Lakewick", "Lightston Point", "Linville", 
"Mallowbarrow Downs", "Mallowspell Shore", "Marblebeach", "Marblemeadow", "Northwilde Shore", "Pryhill", "Rosebridge", "Shadowgate", "Springbeech", "Springbush Crags", "Stonecoast", "Strongfog Haven", 
"Swyntown", "Vertspring", "Violetland", "Violetwell Field", "Wellbridge", "Wellville", "Westcoast", "Westerhedge Point", "Westermoor", "Wildebarrow", "Winterbank", "Woodgate" , "Aelmere" , "Aldviolet Barrow" ,
    "Belmarsh" , "Blackbridge" , "Brightmarsh Crossing" , "Buttersnow Field" , "Byrock" , "Clifflake" , "Coldcrystal" , "Courtvale" , "Crystalwick Downs" , "Deepbarrow" , "Dracmount" , "Eridale" , "Esterash" ,
    "Estersummer" , "Fallcastle" , "Fallmere Crossing" , "Faycastle" , "Goldcastle Coast" , "Goldhaven" , "Goldland" , "Highshore" , "Ironflower" , "Mallowmeadow" , "Marblebarrow" , "Marbleland" , "Merriwynne" ,
    "Mistcliff" , "Morley" , "Newhollow" , "Northloch Coast" , "Ornesse" , "Ostbarrow" , "Redfield" , "Shadowfield" ,"Shadowmerrow Marsh" , "Snowfay" , "Springhedge" , "Starrywinter" , "Strongwater Coast" ,
    "Valpond" , "Vertcastle Dell" , "Violetbeech" , "Wayhaven" , "Westermere Crossing" , "Wildeton Forest" , "Winddale" , "Winterpond" , "Wolfmont", "Aldbay Field", "Barrowden", "Beltown", "Bluebridge", "Bluehaven Hollow", "Blueriver Bridge", "Butterbeech", "Butterhaven", "Butternesse", "Coldbeach", "Courtacre", "Draccliff",
"Dragonpond", "Esterburn", "Freyhedge", "Goldbeach", "Goldburn", "Goldkeep", "Goldshore", "Greencrest", "Griffinley", "Highsilver Haven", "Ironburn Barrens", "Lakewick", "Lightston Point", "Linville",
"Mallowbarrow Downs", "Mallowspell Shore", "Marblebeach", "Marblemeadow", "Northwilde Shore", "Pryhill", "Rosebridge", "Shadowgate", "Springbeech", "Springbush Crags", "Stonecoast", "Strongfog Haven",
"Swyntown", "Vertspring", "Violetland", "Violetwell Field", "Wellbridge", "Wellville", "Westcoast", "Westerhedge Point", "Westermoor", "Wildebarrow", "Winterbank", "Woodgate" , "Aelmere" , "Aldviolet Barrow" ,
    "Belmarsh" , "Blackbridge" , "Brightmarsh Crossing" , "Buttersnow Field" , "Byrock" , "Clifflake" , "Coldcrystal" , "Courtvale" , "Crystalwick Downs" , "Deepbarrow" , "Dracmount" , "Eridale" , "Esterash" ,
    "Estersummer" , "Fallcastle" , "Fallmere Crossing" , "Faycastle" , "Goldcastle Coast" , "Goldhaven" , "Goldland" , "Highshore" , "Ironflower" , "Mallowmeadow" , "Marblebarrow" , "Marbleland" , "Merriwynne" ,
    "Mistcliff" , "Morley" , "Newhollow" , "Northloch Coast" , "Ornesse" , "Ostbarrow" , "Redfield" , "Shadowfield" ,"Shadowmerrow Marsh" , "Snowfay" , "Springhedge" , "Starrywinter" , "Strongwater Coast" ,
    "Valpond" , "Vertcastle Dell" , "Violetbeech" , "Wayhaven" , "Westermere Crossing" , "Wildeton Forest" , "Winddale" , "Winterpond" , "Wolfmont"]

var fantasy_places_replacement = {}

var replace_name = function(name) {
	var n = "-" + name.toLowerCase()
	if(fantasy_places_replacement[n]) return fantasy_places_replacement[n]
	fantasy_places_replacement[n] = fantasy_places.pop()
	return fantasy_places_replacement[n]
}

var fantasy_numbers = {}
var fantasy_numbers_val = 15
var replace_number = function(n) {
	if(fantasy_numbers[n]) return fantasy_numbers[n]
	fantasy_numbers[n] = ++fantasy_numbers_val
	return fantasy_numbers[n]
}



ns._enumdata = {}

upoopu.intel.friendly.data.enums.forEach(function(data) {
    ns._enumdata[data.name] = {}
    data.fields.forEach(function(field) {ns._enumdata[data.name][field.nr] = field})
})

ns.pb = function(pball) {
    // convert the pb to a simple object

    function convert_message(pb) {
        var message = {}
        for (var key in pb.properties_) {
            var val = null
            var thing = null
            var result = null
            if (pb.HasField(key)) {
                val = pb.GetField(key)
                thing = pb.properties_[key]["type"].toString()
                result =   /UpoOpu.pb.(.[^;]*)/.exec(thing);
                if(result) {
                        // result can either be an enum or a sub class
                    //console.log(result)
                    //console.log(ns._enumdata)
                    if (result[1] in ns._enumdata) {
                        // result is an enum
                        var friendly_data = ns._enumdata[result[1]]
                        message["$enum$"+key] = val
                        try {
                        message["$enum$string$"+key] =  friendly_data[val]["name"].toLowerCase()
                        } catch(e) {
                            alert(key + "-" + val)
                        }

                        if(friendly_data[val].ospec) {
                            // we have got some military data
                            message["ospec"] = friendly_data[val].ospec
                            message["dspec"] = friendly_data[val].dspec
                            message["elite"] = friendly_data[val].elite
                            message["soldier"] = friendly_data[val].soldier
                        }

                        if(friendly_data[val].factor) {
                             message["factor"] = friendly_data[val].factor
                        }

                        if(friendly_data[val].build_stats) {
                             message["build_stats"] = friendly_data[val].build_stats
                        }
                        val = friendly_data[val].friendly
                    } else {
                        // result is a sub message

                        if (pb.properties_[key]["multiplicity"] == PROTO.repeated) {
                            //if(key == "ospecs")
                            // console.log("Not found " + val + " - thing: " + thing)
                            var total_quantity = 0
                            var total_per_acre = 0
                            var set_total_quantity = false
                            var set_total_per_acre = false 

                            var temp = []
                            for (var idx = 0; idx < val.length; idx++) {
                                // document.write("<br> new thing: " + key + " kd " + pb.kingdom)
                                if (val[idx].quantity && isFinite(val[idx].quantity)) {
                                    total_quantity = total_quantity + parseInt(val[idx].quantity)
                                    set_total_quantity = true
                                } 
                                if (val[idx].per_acre && isFinite(val[idx].per_acre)) {
                                    total_per_acre = total_per_acre + parseFloat(val[idx].per_acre)
                                    set_total_per_acre = true
                                }
                                temp.push(convert_message(val[idx]))
                            }
                            if (set_total_quantity) { message["$total$quantity"] = total_quantity }
                            if (set_total_per_acre) { message["$total$per_acre"] = total_per_acre }
                            val = temp
                        } else {
                           try {
                               val = convert_message(val)
                            } catch(e) {}
                        }

                    }
                } else if (upoopu.intel.utopia.enhance.safe_for_public && (key == "province" || key == "target_province" || key == "kd" || key == "isl" || key == "target_kd" || key == "target_isl")) {
                    switch(key) {
                        case "province":
                        case "target_province":
                            val = replace_name(val)
                            break
                        default:
                           val = replace_number(val)
                    }
            	} else if (pb.properties_[key]["multiplicity"] == PROTO.repeated) {
                    var temp = []
                    for (var idx = 0; idx < val.length; idx++) {
                        temp.push((val[idx]))
                    }
                    val = temp
                }

                
            }  else {
                thing = pb.properties_[key]["type"].toString()
                result =   /PROTO.int32/.exec(thing);
                if(result) {
                    val = 0
                }
            }
            message[key] = val
        }
        return message
    }

    return convert_message(pball)
}
}());


/*
---
name: upoopu.extension.content.upo

description: Content script, handles basic stuff

requires:
    - namespace
    - upoopu.ubrowser.injectables.context_menu
    - soyutils.js
    - jrd.soy.ext
    - upoopu.soy.ext
    - soy.common.intel.soy
    - soy.common.common.soy
    - upoopu.parsers
    - upoopu.intel.utopia.enhance
    - String.Extras

provides: [upoopu.extension.content.upo]

...
*/
var slice$ = [].slice;
namespace("upoopu.extension.content.upo", function(ns){
  var push_cb;
  push_cb = function(cb){
    return function(){
      var data;
      data = slice$.call(arguments);
      ns.push_notification("<div class='intel progress'>Getting intel on " + data[1][0] + " (" + data[1][1] + ":" + data[1][2] + ")</div>");
      return cb.apply(null, data);
    };
  };
  ns.inject_context_menu = function(check_for_utopia, cb){
    return upoopu.ubrowser.injectables.context_menu.inject(check_for_utopia, push_cb(cb));
  };
  jQuery.extend(jQuery.fn, {
    hasParent: function(p){
      return this.filter(function(){
        return $(p).find(this).length;
      });
    }
  });
  ns.push_notification = function(data){
    var test, test2, overlay;
    $('#overlay div.intel.progress').remove();
    if (!data) {
      test = '<div class="intel message good">Early indications show that our operation was a success and we have 75% confidence in the information retrieved. Our report shows that this province has not been attacked much in the last month. Remember, there are few rewards for attacking a badly hit province, as the easily captured resources have already been taken.</div>  <div class="intel sot"><h1>The province of Patroclus of Achaea (7:34)</h1><div class="tablewrap"><table class="throne"><tbody><tr><th>Race</th><td>Orc <small>Tactician</small></td><th>Soldiers</th><td>0</td></tr><tr><th>Honor</th><td>Knight</td><th>Goblins</th><td>0</td></tr><tr><th>Land</th><td>8,877 <small>acres</small></td><th>Trolls</th><td>2,284</td></tr><tr><th>Peasants</th><td>251,567</td><th>Ogres</th><td>27,891</td></tr><tr><th>BE</th><td>97%</td><th>Thieves</th><td>0</td></tr><tr><th>Money</th><td>10,099,777 <small>gc</small></td><th>Wizards</th><td>0</td></tr><tr><th>Food</th><td>362,359 <small>bshls</small></td><th>Horses</th><td>0</td></tr><tr><th>Runes</th><td>168,354 <small>runes</small></td><th>Pris.</th><td>0</td></tr><tr><th>TB</th><td>-15,393,895 <small>gc</small></td><th>Off.</th><td>178,371</td></tr><tr><th>Nw.</th><td>1,002,588 <small>gc</small></td><th>Def.</th><td>49,449</td></tr></tbody></table></div></div>   <div class="intel message good">Early indications show that our operation was a success and we have 75% confidence in the information retrieved.</div>  <div class="intel survey"><h1>The province of Patroclus of Achaea (7:34)</h1><div class="tablewrap"><table class="survey"><tbody><tr><th>Workers</th><td>215,720</td><th>BE</th><td>107.60%</td></tr><tr><th>Jobs</th><td>192,300</td><th>W. Needed</th><td>128,841</td></tr></tbody></table><table class="survey buildings"><thead><tr><th colspan="3">Buildings</th></tr></thead><tbody><tr><th>Buiding</th><th class="number">Quantity</th><th class="number">Percentage</th></tr><tr><th nowrap="">Homes</th><td>1,358</td><td>15.00%</td></tr><tr><th nowrap="">Farms</th><td>724</td><td>8.00%</td></tr><tr><th nowrap="">Banks</th><td>2,715</td><td>30.00%</td></tr><tr><th nowrap="">Armouries</th><td>2,353</td><td>26.00%</td></tr><tr><th nowrap="">Guilds</th><td>1,086</td><td>12.00%</td></tr><tr><th nowrap="">Towers</th><td>814</td><td>9.00%</td></tr></tbody></table></div></div>   <div class="intel message good">Early indications show that our operation was a success and we have 75% confidence in the information retrieved.</div>  <div class="intel som"><h1>The province of Patroclus of Achaea (7:34)</h1><div class="tablewrap"><table class="som"><tbody><tr><th>Offense</th><td>208,638 <small>100.50% eff.</small></td><th>Non peasant</th><td>12.30%</td></tr><tr><th>Defense</th><td>33,044 <small>75.70% eff.</small></td><th>Wages</th><td>60.40%</td></tr><tr><th></th><td></td><th>ME</th><td>110.70%</td></tr></tbody></table><table class="som army"><thead><tr><th colspan="4">Army Home</th></tr></thead><tbody><tr><th>Generals</th><td>5 generals</td><th>Goblins</th><td>0</td></tr><tr><th>Soldiers</th><td>0</td><th>Trolls</th><td>3,049</td></tr><tr><th>Horses</th><td>0</td><th>Ogres</th><td>33,502</td></tr></tbody></table></div></div>';
      test2 = '<div class="intel message good">Early indications show that our operation was a success and we have 75% confidence in the information retrieved.</div>  <div class="intel sos"><h1>The province of Patroclus of Achaea (7:34)</h1><div class="tablewrap"><table class="sos sciences"><tbody><tr><th>Science</th><th class="number">Quantity</th><th class="number">Per Acre</th></tr><tr><th nowrap="">Alchemy</th><td>554,160</td><td>61.2</td></tr><tr><th nowrap="">Tools</th><td>539,327</td><td>59.6</td></tr><tr><th nowrap="">Housing</th><td>1,102,546</td><td>121.8</td></tr><tr><th nowrap="">Food</th><td>268,990</td><td>29.7</td></tr><tr><th nowrap="">Military</th><td>897,778</td><td>99.1</td></tr><tr><th nowrap="">Thievery</th><td>196,219</td><td>21.7</td></tr><tr><th nowrap="">Channeling</th><td>902,569</td><td>99.7</td></tr><tr><th nowrap="">Total Books</th><td>4,461,589</td><td>0.0</td></tr></tbody></table></div></div><div class="intel progress">Getting intel</div>';
      overlay = upoopu.common.templates.common.partialoverlay({
        content: test
      });
      return $('body').prepend(overlay);
    } else {
      if ($('#overlay').length) {
        return $('#overlay > div').prepend(data);
      } else {
        overlay = upoopu.common.templates.common.partialoverlay({
          content: data
        });
        $('body').prepend(overlay);
        return $('body').bind('click', function(e){
          if (!$(e.target).parents(".context-menu-root").length) {
            $('#overlay').remove();
            return $(this).unbind(e);
          }
        });
      }
    }
  };
  return ns.show_action_result = function(action, params, result){
    var target, kd, isl, op, thieves, retry, ref$, template, p, parser, pb, pb_enhanced, e;
    if (action == "thief_op") {
      target = params[0], kd = params[1], isl = params[2], op = params[3], thieves = params[4], retry = params[5];
      if (result.success) {
        ref$ = (function(){
          switch (op) {
          case "SPY_ON_THRONE":
            return [upoopu.common.templates.intel.sot, 'sot'];
          case "SPY_ON_MILITARY":
            return [upoopu.common.templates.intel.som, 'som'];
          case "SURVEY":
            return [upoopu.common.templates.intel.survey, 'survey'];
          case "SPY_ON_SCIENCES":
            return [upoopu.common.templates.intel.sos, 'science'];
          default:
            return [void 8, void 8];
          }
        }()), template = ref$[0], p = ref$[1];
        if (p && template) {
          parser = upoopu.parsers.getParser(result.body.stripTags().replace(/\s+/g, ' ').replace(/^\s+|\s+$/g, ''), p);
          pb = parser.toPb();
          if (pb) {
            try {
              pb_enhanced = upoopu.intel.utopia.enhance.pb(pb);
              ns.push_notification(template({
                pb: pb_enhanced
              }));
            } catch (e$) {
              e = e$;
              alert("Error showing result " + e);
            }
          }
        }
      }
      return ns.push_notification(upoopu.common.templates.intel.message({
        message: result.outerHTML.stripTags().replace(/\s+/g, ' ').replace(/^\s+|\s+$/g, ''),
        good: result.success
      }));
    }
  };
});

