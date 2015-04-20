define('bee-demo/header/header', [ "node", "io" ], function(require, exports,
		module) {
	var $ = require('node').all;
	var Node = require('node');
	var IO = require('io');
	module.exports = {
		init : function() {
			var headerBG = new Node('<div>').addClass('banner');
			var logoKnot = new Node('<img>').prop({
				src : 'image/logoKnot.png'
			}).addClass('logoKnot');
			var logoText = new Node('<img>').prop({
				src : 'image/logoText.png'
			}).addClass('logoText');
			$('header').append(headerBG.append(logoKnot).append(logoText));
			var rightBox = new Node('<div>').addClass('rightBox');
			rightBox.append(new Node('<div>').addClass('headerSetting').append(
					new Node('<img>').prop({
						src : 'image/headerSetting.png'
					})));
			rightBox.append(new Node('<div>').addClass('headerHelping').append(
					new Node('<img>').prop({
						src : 'image/headerHelping.png'
					})));
			headerBG.append(rightBox);
			var welcomePanel = new Node('<div>').addClass('welcomePanel')
					.append(new Node('<img>').prop({
						src : 'image/welcomePanel.png'
					}));
			var welcomePerson = new Node('<div>').addClass('welcomePerson');
			headerBG.append(welcomePanel.append(welcomePerson));
			initWelcome(welcomePerson);
			var rightPanel = new Node('<div>').addClass('rightPanel');
			var favoritesPanel = new Node('<div>').addClass('rightPanelItems')
					.append(new Node('<img>').prop({
						src : 'image/favoritesPanel.png'
					}));
			var filesPanel = new Node('<div>').addClass('rightPanelItems')
					.append(new Node('<img>').prop({
						src : 'image/filesPanel.png'
					}));
			headerBG.append(rightPanel.append(filesPanel)
					.append(favoritesPanel));
		}
	}
	function initWelcome(welcomePerson) {
		IO.get('loginPerson.json', {
			'index' : 1
		}, function(data) {
			welcomePerson.html(data.loginPerson.name);
		}, 'json');
	}
});