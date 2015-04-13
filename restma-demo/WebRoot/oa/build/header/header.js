define('bee-demo/header/header',["node","io"],function(require, exports, module) {
 var $ = require('node').all;
 var Node = require('node');
 var add = require('bee-demo/addition');
 var IO = require('io');
module.exports = {
    init:function(){
    	var headerBG = new Node('<div></div>').addClass('banner');
    	var logoKnot = new Node('<img src = "image/logoKnot.png">').addClass('logoKnot');
    	var logoText = new Node('<img src = "image/logoText.png">').addClass('logoText');
    	$('header').append(headerBG.append(logoKnot).append(logoText));
    	var rightBox = new Node('<div></div>').addClass('rightBox');
    	rightBox.append(new Node('<div></div>').addClass('headerSetting').append(new Node('<img src = "image/headerSetting.png">')));
    	rightBox.append(new Node('<div></div>').addClass('headerHelping').append(new Node('<img src = "image/headerHelping.png">')));
    	headerBG.append(rightBox);
    	var welcomePanel = new Node('<div></div>').addClass('welcomePanel').append(new Node('<img src = "image/welcomePanel.png">'));
    	var welcomePerson = new Node('<div></div>').addClass('welcomePerson');
    	headerBG.append(welcomePanel.append(welcomePerson));
    	initWelcome(welcomePerson);
    	var rightPanel = new Node('<div></div>').addClass('rightPanel');
    	var favoritesPanel = new Node('<div></div>').addClass('rightPanelItems').append(new Node('<img src = "image/favoritesPanel.png">'));
    	var filesPanel = new Node('<div></div>').addClass('rightPanelItems').append(new Node('<img src = "image/filesPanel.png">'));
    	headerBG.append(rightPanel.append(filesPanel).append(favoritesPanel));
    }
}
function initWelcome(welcomePerson){
	IO.get('loginPerson.json', {'index': 1}, function(data){
		welcomePerson.html(data.loginPerson.name);
	}, 'json');
}
});