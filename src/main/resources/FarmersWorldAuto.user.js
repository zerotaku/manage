// ==UserScript==
// @name         FarmersWorldAuto
// @namespace    http://tampermonkey.net/
// @version      0.2
// @description  自动登录fw,然后定时收集领取物品
// @author       zerotaku
// @match        *://play.farmersworld.io/*
// @include      *://play.farmersworld.io//*
// @icon         https://www.google.com/s2/favicons?domain=baidu.com
// @grant        none
// ==/UserScript==

(function() {
    'use strict';
    //是否登录
    var isLogin = false;
    var isLoading = false;
    var isHome = false;
    var timeOutNum = 0;
    var imageNum = 0;
    var imageAction = 0;
    var receiveNum = 0;
    var isRunScript = false;
    var myVar;

    window.onload = function(){
        try {
            setTimeout(function () {
                myVar=setInterval(function(){loginScheduling()},2*1000);
            }, 1000);

        }
        catch(err) {
            var log =+ localStorage.getItem("FarmersWorldLog") + new Date().toTimeString() + err.message + "][";
            localStorage.setItem("FarmersWorldLog",log);
            console.error(log);
            location.replace("https://play.farmersworld.io/");
            //cation.reload();
        }
    }

    function loginScheduling() {
        if(timeOutNum > 30 || receiveNum > 4 ){
            recordLog();
            clearInterval(myVar);
           //location.reload();
            location.replace("https://play.farmersworld.io/");
            return;
        }
        //console.log('进入loginScheduling');
        if(document.getElementsByClassName('modal-wrapper').length > 0){
            var sucessHtml = document.getElementsByClassName("modal-wrapper")[0].innerHTML;
            if(sucessHtml.toLowerCase().indexOf("You've just mined") >= 0){
                receiveNum--;
                console.info(sucessHtml);
                localStorage.setItem("FarmersWorldValue", localStorage.getItem("FarmersWorldValue") + "{" + sucessHtml + "}," );
                recordLog();
                //location.reload();
            }

            setTimeout(function () {
                if(document.getElementsByClassName('plain-button short undefined').length>0){
                    var okBtn = document.getElementsByClassName('plain-button short undefined')[0];
                    okBtn.click();
                }
            }, 500);
        }
        isLoading = document.getElementsByClassName('modal-wrapper').length > 0;
        if(isLogin || isLoading || isRunScript){
            timeOutNum ++;
            return;
        }
        var serverList = document.getElementById('RPC-Endpoint');
        if(serverList !=null && serverList.length > 0){
            isLogin = true;
            login();
        }

        if(document.getElementsByClassName('card-container--time').length > 0){
            isHome = true;
            getCompletedRewards();
        }

        if(document.getElementsByClassName('ttttttttt').length > 0){
            var layerCloseBtn = document.getElementById('RPC-Endpoint');
            if(layerCloseBtn != null && layerCloseBtn.length > 0){
                layerCloseBtn[0].click();
            }
        }
        timeOutNum = 0;
    }

    function login() {
        var rpcUrls = document.getElementById('RPC-Endpoint');
        if(rpcUrls == null && rpcUrls.options.length < 3){
            return;
        }
        var loginBtn = document.getElementsByClassName('login-button')[0];
        if(loginBtn == null){
            return;
        }
        loginBtn.click();
        document.getElementsByClassName('login-modal-button')[0].click();
        isLogin = false;
    }

    function getCompletedRewards() {
        if(imageNum == imageAction){
            imageAction = 0;
        }
        var oEvent = document.createEvent("MouseEvents");
        oEvent.initMouseEvent("click", true, true, document.defaultView, 0, 0, 0,
                              5, 5/*, false, false, false, false, 0, null*/);
        var cardImages = document.getElementsByClassName('carousel__img--item');
        if(cardImages == null && cardImages.length <= 0){
            console.log('error:cardImages == null');
            return ;
        }
        imageNum = cardImages.length;
        var card = cardImages[imageAction];
        cardImages[imageAction].dispatchEvent(oEvent);
        setTimeout(function () {
            isRunScript = true;
            var time = document.getElementsByClassName('card-container--time')[0];
            if(time.textContent.indexOf("00:00:00") >= 0){
                receiveNum++;
                document.getElementsByClassName('button-section set-height')[0].click();
            }
            setTimeout(function () {isRunScript = false}, 500);
        }, 500);
        imageAction++;
        isHome = false;
    }

    function recordLog(){
        var valNums = document.getElementsByClassName('resource-number');
        var msg = new Date().toTimeString() + '黄金/木材/食物/体力:';
        for(var i=0;i<valNums.length;i++){
            msg += "/" + valNums[i].getElementsByTagName("div")[0].textContent;
        }
        console.log(msg);
        localStorage.setItem("FarmersWorldValue", localStorage.getItem("FarmersWorldValue") + "[" + msg + "],");
    }

    function getCardTimeFater(time){
        var node = time;
        for(var i=0;i<5;i++){
            node = node.parentNode;
        }
        return node;
    }

})();

//}