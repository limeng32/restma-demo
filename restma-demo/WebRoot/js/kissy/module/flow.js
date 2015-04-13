define(function(Node, IO, DD){
	var flow = {
		init: function(){
			var flow = new Node('<div>').html('Hello Kissy by module!').addClass('_flow').appendTo('body');
			new DD.Draggable({node:flow,move:true});
		}
	};
	return flow;
}, {requires: ['node', 'io', 'dd']});