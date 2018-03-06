var zClipBoard = {
    setData : function(v){
         var bro = jQuery.browser;
         if(bro.msie){
            window.clipboardData.clearData();
            window.clipboardData.setData("Text", v);
            return true;
         }else if(bro.opera){
            alert("此功能不支持Opera,请手工复制文本框中内容");
            return false;
         }else if (bro.mozilla){
            try{
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            }catch (e){
               alert("您的firefox安全限制限制您进行剪贴板操作，请打开'about:config'将 signed.applets.codebase_principal_support'设置为true'之后重试");
               return false;
            }
            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
            if (!clip){
                return false;
            }
            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
            if (!trans){
                return false;
            }
            trans.addDataFlavor('text/unicode');
            var str = {};
            var len = {};
            var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
            var copytext = v;
            str.data = copytext;
            trans.setTransferData("text/unicode",str,copytext.length*2);
            var clipid = Components.interfaces.nsIClipboard;
            if (!clip){
              return false;
            }
            clip.setData(trans,null,clipid.kGlobalClipboard);
            return true;
         }else if(bro.webkit || bro.chrome){
             return false;
         }
    },
    getData : function(){
        var bro = jQuery.browser;
        if(bro.msie){
            return window.clipboardData.getData("Text");
        }else if(bro.opera){
            return undefined;
        }else if(bro.mozilla){
            netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect'); 
            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard); 
            if (!clip) return; 
            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
            if (!trans) return; 
            trans.addDataFlavor('text/unicode'); 
            clip.getData(trans,clip.kGlobalClipboard); 
            var str = {};
            var len = {};
            try { 
                trans.getTransferData('text/unicode',str,len); 
            }catch(error) { 
                return null; 
            } 
            if (str) { 
                if (Components.interfaces.nsISupportsWString) str=str.value.QueryInterface(Components.interfaces.nsISupportsWString); 
                else if (Components.interfaces.nsISupportsString) str=str.value.QueryInterface(Components.interfaces.nsISupportsString); 
                else str = null; 
            } 
            if (str) { 
                return (str.data.substring(0,len.value / 2)); 
            } 
           return null; 
        }else if(bro.webkit || bro.chrome){
            return false;
        }
    }
};
