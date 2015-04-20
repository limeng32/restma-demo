define('bee-demo/article/article', [ "node", "./article-view", 'button',
		'util', 'bee-demo/buttonPlus', "kg/xtemplate/3.4.1/runtime" ],
		function(require, exports, module) {
			var $ = require('node').all;
			var Node = require('node');
			var Util = require('util');
			var BP = require('bee-demo/buttonPlus');
			var XTemplate = require("kg/xtemplate/3.4.1/runtime");
			module.exports = {
				init : function() {
					var BG = new Node('<div>').addClass('middleBanner');
					var sorterOfTime = new Node('<div>').unselectable().append(
							'到达时间').addClass('oa-sorter').addClass('ks-button')
							.addClass('ks-button-mini').append(
									new Node('<img>').prop({
										src : 'image/arrowDownUnuse.png'
									}).addClass('oa-sorter-arrow'));
					var sorterOfUrgency = new Node('<div>').unselectable()
							.append('缓急').addClass('oa-sorter').addClass(
									'ks-button').addClass('ks-button-mini')
							.append(new Node('<img>').prop({
								src : 'image/arrowDownUnuse.png'
							}).addClass('oa-sorter-arrow'));
					var sorterOfCustom = new Node('<div>').unselectable()
							.append('自定义').addClass('oa-sorter').addClass(
									'ks-button').addClass('ks-button-mini')
							.append(new Node('<img>').prop({
								src : 'image/arrowDownUnuse.png'
							}).addClass('oa-sorter-arrow'));
					$('article').append(BG);
					var sorterPanel = new Node('<div>').addClass('sorterPanel')
							.addClass('ks-button-group');
					BG.append(
							new Node('<div>').addClass('bannerSeparatorSpan')
									.append(new Node('<img>').prop({
										src : 'image/bannerSeparator.png'
									}).addClass('bannerSeparator'))).append(
							new Node('<div>').addClass('sorterPanelLeft')
									.append('按')).append(sorterPanel).append(
							new Node('<div>').addClass('sorterPanelRight')
									.append('排序'));
					sorterPanel.append(sorterOfTime).append(sorterOfUrgency)
							.append(sorterOfCustom);
					BP.tapelize(sorterPanel.children(), function(n) {
						n.addClass('ks-button-pressed');
						var src = n.one('.oa-sorter-arrow').prop('src');
						if (Util.endsWith(src, 'Unuse.png')) {
							n.one('.oa-sorter-arrow').prop('src',
									src.substring(0, src.length - 9) + '.png');
						} else {
							if (Util.endsWith(src, 'Down.png')) {
								n.one('.oa-sorter-arrow').prop(
										'src',
										src.substring(0, src.length - 8)
												+ 'Up.png');
							} else if (Util.endsWith(src, 'Up.png')) {
								n.one('.oa-sorter-arrow').prop(
										'src',
										src.substring(0, src.length - 6)
												+ 'Down.png');
							}
						}
					}, function(n) {
						n.removeClass('ks-button-pressed');
						var src = n.one('.oa-sorter-arrow').prop('src');
						if (!Util.endsWith(src, 'Unuse.png')) {
							n.one('.oa-sorter-arrow').prop(
									'src',
									src.substring(0, src.length - 4)
											+ 'Unuse.png');
						}
					});
					BP.nOrders(sorterOfTime, [ function(n) {
					}, function(n) {
					} ]);

					BP.nOrders(sorterOfUrgency, [ function(n) {
					}, function(n) {
					} ]);

					BP.nOrders(sorterOfCustom, [ function(n) {
					}, function(n) {
					} ]);
				}
			}
		});