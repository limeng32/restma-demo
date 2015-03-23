KISSY.add(function(S, N, E, A, IO) {
	var $ = S.all;

	var opLotto = {
		init : function() {
			this.imgList = $('.img-list').all('li');
			this.length = this.imgList.length;
			this.startIndex = 0;
			this.attempts = 0;
			this.remain = this.imgList.length;
			this.done = false;
		},
		_run : function(index) {
			var next = index + 1 === this.length ? 0 : index + 1;
			this.imgList.removeClass('active');
			this.imgList.item(next).addClass('active');
			this.startIndex = next;
		},
		start : function() {
			var self = this;
			self.attempts += 1;
			$('.attemptsSpan').html(self.attempts);
			self.timer = setInterval(function() {
				self._run(self.startIndex);
			}, 50);
		},
		pause : function() {
			this.timer && clearInterval(this.timer);
			this._animate(this.startIndex);
		},
		_animate : function(index) {
			var self = this;
			var selectedItem = self.imgList.item(index);
			var offsetTop = selectedItem.css('top');
			var offsetLeft = selectedItem.css('left');
			var cloneItem = selectedItem.one('img').clone()
					.appendTo('.content');
			cloneItem.css({
				'width' : '100px',
				'height' : '100px',
				'position' : 'absolute',
				'top' : offsetTop,
				'left' : offsetLeft,
				'border-radius' : '50px'
			}).animate({
				'top' : parseInt(offsetTop, 10) - 50
			}, 0.2, 'easeOut').animate({
				'top' : 200,
				'left' : 200,
				'opacity' : 0
			}, 1, 'bounceOut', function() {
				cloneItem.remove();
				self._print(index);
			});
		},
		_print : function(index) {
			var indexSelected = index + 1;
			if (!$('.checker' + indexSelected).hasClass('active')) {
				this.remain -= 1;
				$('.checker' + indexSelected).addClass('active');
			}
			$('.detail').html(
					'<img src="./image/op/' + indexSelected + '.png" />')
					.fadeIn(0.5);
			if (this.remain == 0) {
				if (!this.done) {
					IO.post('op/test?_content=json', {
						'attempts' : this.attempts
					}, function(data) {
						var echo0 = new N('<span>').html(data._content[0])
								.addClass('chars').appendTo($('.echo'));
						var echo1 = new N('<span>').html(data._content[1])
								.addClass('digit').appendTo($('.echo'));
						var echo2 = new N('<span>').html(data._content[2])
								.addClass('chars').appendTo($('.echo'));
						var echo3 = new N('<span>').html(data._content[3])
								.addClass('chars').appendTo($('.echo'));
						var echo4 = new N('<span>').html(data._content[4])
								.addClass('digit').appendTo($('.echo'));
						$('.echo').fadeIn(0.5);
					}, 'json');
					this.done = true;
				}
			}
		}
	};

	return opLotto;

}, {
	requires : [ 'node', 'event', 'anim', 'ajax' ]
});