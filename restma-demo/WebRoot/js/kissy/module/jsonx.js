define(
		function(JSON, UTIL) {
			if (typeof JSON.retrocycle !== 'function') {
				'use strict';
				var t_obj = typeof {}, t_arr = Object.prototype.toString
						.apply([]), t_str = typeof "";
				var walk = function(path, _xpath, array) {
					if (UTIL.startsWith(path, '$')) // 基于xpath直接定位
						return path;
					else { // 相对回溯定位
						var x, j = path.split('..'), k = -j.length
								+ (array ? 2 : 1), last = j.slice(-1)[0]
								.replace('/', '.');
						x = k < 0 ? _xpath.slice(0, k) : _xpath.slice(0);
						if (last && !UTIL.startsWith(last, '.')
								&& !UTIL.startsWith(last, '['))
							last = '.' + last;
						path = x.join('.') + last;
					}
					return path; // 最终得到绝对xpath地址
				};
				JSON.retrocycle = (function() {
					return function($) {
						var xpath = [ '$' ];
						(function rez(value) {
							var i, item, name, path, _x;
							if (value && typeof value === t_obj) {
								if (Object.prototype.toString.apply(value) === t_arr) {
									for (i = 0; i < value.length; i += 1) {
										item = value[i];
										if (item && typeof item === t_obj) {
											xpath.push(xpath.pop() + '[' + i
													+ ']'); // 下标引用要合并分级
											path = item.$ref;
											if (typeof path === t_str)
												value[i] = eval(walk(path,
														xpath, true));
											else
												rez(item);
											if (_x = xpath.pop())
												xpath.push(_x.slice(0, _x
														.indexOf('['))); // 下标引用还原分级
										}
									}
								} else {
									for (name in value) {
										if (value.hasOwnProperty(name)
												&& typeof value[name] === t_obj) {
											xpath.push(name);
											item = value[name];
											if (item) {
												path = item.$ref;
												if (typeof path === t_str)
													value[name] = eval(walk(
															path, xpath));
												else
													rez(item);
											}
											xpath.pop();
										}
									}
								}
							}
						})($);
						return $;
					}
				})();
			}
			var jsonx = {
				decode : function(data) {
					var ret;
					if (typeof data === 'string') {
						ret = JSON.retrocycle(JSON.parse(data));
					} else if (typeof data === 'object') {
						ret = JSON.retrocycle(data);
					}
					return ret;
				}
			};
			return jsonx;
		}, {
			requires : [ 'json', 'util' ]
		});