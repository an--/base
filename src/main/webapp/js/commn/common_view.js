/**
 * Backbone element_view
 */
define(function(require, exports, module) {
	
	"use strict";
	
	var Backbone = require('backbone');
	
	var ElementView = Backbone.View.extend({
		name	:	undefined,
		subView	:	null,
        constructor :   function(){
            this.name = this.constructor.name;
            this.subView = {};
            Backbone.View.prototype.constructor.apply(this , arguments);
            this.on( "load" , this._onload , this );
            this.on( "unload" , this._onunload , this );
        },
		_onload	:	function(){
			if( this.$el.closest("body").length ){
                this.$parenEl = this.$el.parent();
                if("function" === typeof this.onload ){
				    this.onload.apply( this );
                }
                for( var i in this.subView ){
                    this.subView[i].trigger("load");
                }
			}
		},
		_onunload	:	function( ){
			if( !this.$el.closest("body").length) {
                if( "function" === typeof this.onunload ) {
                    this.onunload.apply(this);
                }
                this.$parenEl = null;
			}
            for( var i in this.subView ){
                this.subView[i].trigger("unload");
            }
		},
		getSubView	:	function( subViewIndex ){
			return this.subView[subViewIndex];
		},
		/* append子view在指定的html容器内。
		 * 如果没有指定容器,则以当前view的$el对象作为容器。
		 * 如果没有指定childView则根据childViewIndex在当前subView中取childView。 */
		appendSubView	:	function(childViewIndex , childView , $parentEl ){
			var _childViewIndex,_$parentEl,_childView;
			for( var i = 0, l = arguments.length; i < l && arguments[i] ; i++ ){
				var argI = arguments[i];
				if( argI instanceof Backbone.View ){
					_childView = argI;
				}else if( argI.jquery ){
					_$parentEl = argI;
				}else if( argI.nodeType ){
					_$parentEl = $(argI);
				}else if( argI ){
					_childViewIndex = argI;
				}
			}
			
			if( !_childViewIndex ){
				throw "childViewIndex 为空 ";
			}
			_$parentEl = _$parentEl ? _$parentEl : this.$el; 
			_childView = _childView ? _childView : this.getSubView(_childViewIndex);
			if(_childView){
                _$parentEl.append( _childView.el );
                if( this.$el.closest("body").length ){
                    _childView.trigger("load" , _$parentEl );
                }
				if(this.subView[_childViewIndex] !== _childView){
					this.subView[_childViewIndex] = _childView;
				}
				return _childView;
			}else{
				throw _childViewIndex + " view is undefined ";
			}
			
		},
        delegateEventsRecursive :   function(){
            this.delegateEvents();
            for( var i in this.subView ){
                if( this.subView[i].$el.closest( "body").length ){
                    if(this.subView[i].delegateEventsRecursive){
                        this.subView[i].delegateEventsRecursive();
                    }else{
                        this.subView[i].delegateEvents();
                    }
                }
            }
        },
		/* 移除子view,如果isDelelete===true,将该子view从subView集合中删除 */
		removeSubView	:	function( childView , isDelelete ){
			var childViewIndex,onDomTree = false;
			if(this.subView[childView]){
				childViewIndex = childView;
			}else if( childView instanceof Backbone.View ) {
				for( var i in this.subView ){
					if( this.subView[i] === childView ){
						childViewIndex = i;
						break;
					}
				}
			}
			if(childViewIndex){
                if(this.subView[childViewIndex].$el.closest("body").length){
                    onDomTree = true;
                }
				this.subView[childViewIndex].remove();
                if( onDomTree ){
                    this._onload();
                    onDomTree = false;
                }
				if( true === isDelelete ){
					return delete this.subView[childViewIndex];
				}else{
					return this.subView[childViewIndex]
				}
			}else {
				return childView;
			}
		},
		/* 移除当前view,在移除当前view之前移除并删除所有的子view */
		remove	:	function( isDelete ){
            var onDomTree = false;
            if(this.subView[childViewIndex].$el.closest("body").length){
                onDomTree = true;
            }
			Backbone.View.prototype.remove.apply(this);
            if(onDomTree){
                this._onunload();
            }
            if(isDelete){
                delete this;
            }
		}
	});
	
	return ElementView;
});