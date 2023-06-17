$(document).ready(function() {
    const username = [[${#authentication.principal.username}]];

    $("#send-bt").on("click", (e) => {
        send();
    });

    const websocket = new WebSocket("ws://" + location.host + "/ws/chat");

    websocket.onmessage = onMessage;

    function send() {
        let msg = document.getElementById("msg");
    }

    function onMessage(msg) {
        var data = msg.data;
        var sessionId = null;
        var message = null;

        for (var i=0; i<arr.length; i++) {
            console.log('arr[' + i + ']: ' + arr[i]);
        }

        var cur_session = username;

        // 현재 세션에 로그인 한 사람
        console.log("cur_session : " + cur_session);
        sessionId = arr[0];
        message = arr[1];

        console.log("sessionID : " + sessionId);
        console.log("cur_session : " + cur_session);

        //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
        if (sessionId == cur_session) {
            var str = "<div class='col-6'>";
            str += "<div class='alert alert-secondary'>";
            str += "<b>" + sessionId + " : " + message + "</b>";
            str += "</div></div>";
            $("#msgArea").append(str);
        }
        else {
            var str = "<div class='col-6'>";
            str += "<div class='alert alert-warning'>";
            str += "<b>" + sessionId + " : " + message + "</b>";
            str += "</div></div>";
            $("#msgArea").append(str);
        }
    }
})