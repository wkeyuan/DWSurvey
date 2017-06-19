var TEXTAREA_HELP = {
    
    /*
     * 获取光标位置
     * @Method getCursorPosition
     * @param t element
     * @return number
     */
    getCursorPosition: function(t){
        if (document.selection) {
            t.focus();
            var ds = document.selection;
            var range = ds.createRange();
            var stored_range = range.duplicate();
            stored_range.moveToElementText(t);
            stored_range.setEndPoint("EndToEnd", range);
            t.selectionStart = stored_range.text.length - range.text.length;
            t.selectionEnd = t.selectionStart + range.text.length;
            return t.selectionStart;
        } else return t.selectionStart;
    },
    
    
    /*
     * 设置光标位置
     * @Method setCursorPosition
     * @param t element
     * @param p number
     * @return
     */
    setCursorPosition:function(t, p){
        this.sel(t,p,p);
    },
    
    /*
     * 插入到光标后面
     * @Method add
     * @param t element
     * @param txt String
     * @return
     */
    add:function (t, txt){
//        var val = t.value;
        if(document.selection){
            t.focus();
            document.selection.createRange().text = txt;  
        } else {
            var cp = t.selectionStart;
            var ubbLength = t.value.length;
            var s = t.scrollTop;
        //    document.getElementById('aaa').innerHTML += s + '<br/>';
            t.value = t.value.slice(0,t.selectionStart) + txt + t.value.slice(t.selectionStart, ubbLength);
            this.setCursorPosition(t, cp + txt.length);
        //    document.getElementById('aaa').innerHTML += t.scrollTop + '<br/>';
            firefox=navigator.userAgent.toLowerCase().match(/firefox\/([\d\.]+)/) && setTimeout(function(){
                if(t.scrollTop != s) t.scrollTop=s;
            },0);

        };
    },
    
    
    /*
     * 删除光标 前面或者后面的 n 个字符
     * @Method del
     * @param t element
     * @param n number  n>0 后面 n<0 前面
     * @return
     * 重新设置 value 的时候 scrollTop 的值会被清0
     */
    del:function(t, n){
        var p = this.getCursorPosition(t);
        var s = t.scrollTop;
        var val = t.value;
        t.value = n > 0 ? val.slice(0, p - n) + val.slice(p):
                        val.slice(0, p) + val.slice(p - n);
        this.setCursorPosition(t ,p - (n < 0 ? 0 : n));
        firefox=navigator.userAgent.toLowerCase().match(/firefox\/([\d\.]+)/) && setTimeout(function(){
            if(t.scrollTop != s) t.scrollTop=s;
        },10);
    },
    
    /*
     * 选中 s 到 z 位置的文字
     * @Method sel
     * @param t element
     * @param s number
     * @param z number
     * @return
     */
    sel:function(t, s, z){
        if(document.selection){
            var range = t.createTextRange();
            range.moveEnd('character', -t.value.length);         
            range.moveEnd('character', z);
            range.moveStart('character', s);
            range.select();
        }else{
            t.setSelectionRange(s,z);
            t.focus();
        }

    },
    
    
    /*
     * 选中一个字符串
     * @Method sel
     * @param t element
     * @param s String
     * @return
     */
    selString:function(t, s){
        var index = t.value.indexOf(s);
        index != -1 ? this.sel(t, index, index + s.length) : false;
    }

};