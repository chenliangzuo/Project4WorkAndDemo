/** 用户信息 */
var username;
var toName;

this.initPage();
var ws = this.init4WebSocket();

$(function(){

    $("#sendMessage").click(function(){
        ws.send(JSON.stringify({toName:toName,message:$("#talk").val()}));
    });

});

/** 初始化页面 */
function initPage(){
    username = null;
    toName = null;
    $("#info").html("离线");
    $.ajax({
        url: "/getUsername",
        async: true,//改为同步方式
        type: "POST",
        data: {},
        success: function (data) {
            username = data;
            $("#info").html(data+" - 离线");
        }
    });
}

function init4WebSocket(){
    // 创建socket对象
    var ws = new WebSocket("ws://localhost:8080/chat");
    // 绑定ws事件
    ws.onopen = function(){
        debugger
        // 建立连接之后需要做什么
        $("#info").html(username+" - 在线");
    };
    ws.onmessage = function(evt){
        debugger
        var dataStr = evt.data;
        var res = JSON.parse(dataStr);
        // 判断是否是系统消息
        if(res.isSystem){
            // 好友列表的展示 + 系统消息的展示
            var userListStr = "";
            var boardcastListStr = "";
            var names = res.message;
            for(var i=0;i<names.length;i++){
                var name = names[i];
                if(username!=name){
                    userListStr += ("<li><a href=\"javascript:void(0);\" onclick=\"chatWith('"+name+"')\">"+name+"</a></li>");
                    boardcastListStr += ("<li>您的好友："+name+"已上线!</li>");
                }
            }
            // 渲染好友列表和系统广播
            $("#boardcastList").html(boardcastListStr);
            $("#userList").html(userListStr);

        } else {

        }


    };
    ws.onclose = function(){
        debugger
        $("#info").html(username+" - 离线");
    };
    return ws;
}

function chatWith(name) {
    $("#chatPanel").show();
    $("#widthFriend").html("正在与" + name + "聊天!");
    toName = name;

}
