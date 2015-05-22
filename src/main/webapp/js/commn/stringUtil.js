var StringUtil = {
		fillCharToLength : function( srcStr , fillChar , toLength , insertToLeft , position ){
	        if( "string" != typeof srcStr && !isNumber(srcStr) ){
	            return srcStr;
	        }
	        if( "string" != typeof fillChar && !isNumber(fillChar) ){
	            return srcStr;
	        }
	        if( !isNumber(toLength) || toLength <= srcStr.length ){
	            return srcStr;
	        }
	        var leftHalf = "",rightHalf = "",positionStr = "";
	        var strLength = 0;
	        if( isNumber(position) && position < srcStr.length  ){
	            leftHalf = srcStr.substring(0 , position - 1  );
	            positionStr = srcStr[position]
	            rightHalf = srcStr.substring( position + 1 , srcStr.length - 1 );
	        }else{
	            positionStr = srcStr;
	        }
	        for( var i = 0; strLength < toLength ; i++ ){
	            if( insertToLeft ){
	                leftHalf +=  fillChar;
	            }else{
	                rightHalf += fillChar;
	            }
	            strLength = leftHalf.length + positionStr.length + rightHalf.length;
	        }
	        if( strLength > toLength ){
	            var overLength = strLength - toLength;
	            if( insertToLeft ){
	                leftHalf = leftHalf.substring(0 , leftHalf.length - overLength - 1 );
	            }else{
	                rightHalf = rightHalf.substring( overLength , rightHalf.length - 1 );
	            }
	        }
	        return leftHalf + position + rightHalf;
	    }	
};


