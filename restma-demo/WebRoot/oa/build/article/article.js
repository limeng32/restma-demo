define('bee-demo/article/article',["node","./article-view","kg/xtemplate/3.4.1/runtime"],function(require, exports, module) {
 var $ = require('node').all;
var tpl = require('./article-view');
var XTemplate = require("kg/xtemplate/3.4.1/runtime");
module.exports = {
    init:function(){
        var html = new XTemplate(tpl).render({
            title:'this is article111111111111111111111111112',
            content:'render by kg/xtemplate2'
        });
        //$('article').html(html);
    }
}
});