define('bee-demo/buttonPlus', [ 'event-dom', 'util' ], function(require,
		exports, module) {
	var DomEvent = require('event-dom'), Util = require('util');
	exports.tapelize = function(nodes, pressF, releaseF) {
		nodes.each(function(node) {
			DomEvent.on(node, 'click', function(ev) {
				nodes.each(function(_node) {
					if (!node.equals(_node)) {
						releaseF(_node);
					}
				})
				pressF(node);
			})
		})
	};
	exports.nOrders = function(n, FArray) {
		var cycle = FArray.length;
		if (cycle == 0) {
			return;
		}
		DomEvent.on(n, 'click', function(ev) {
			var status = n.attr('__tapeDoubleStairStatus');
			if (status == null) {
				n.attr('__tapeDoubleStairStatus', 0);
				return FArray[0](n);
			}
			var statusVal = parseInt(status);
			if (!Util.isNumber(statusVal)) {
				return;
			}
			var statusValNext = (statusVal + 1) % cycle;
			n.attr('__tapeDoubleStairStatus', statusValNext);
			FArray[statusValNext](n);
		})
	}
});