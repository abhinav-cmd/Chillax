var ws = null;
var name = null;


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#loginarea").hide();
        $("#chatarea").show();
        $("#chatareaX").show();
        
    }
    else {
        $("#loginarea").show();
        $("#chatarea").hide();
        $("#chatareaX").hide();
    }
    $("#chatX").html("");
}

function connect() {
    ws = new WebSocket('ws://localhost:4000/name');
    ws.addEventListener('open',function(event){
        sendName();
    });
	ws.onmessage = function(data){
		showNewMessage(data.data);
    }
    setConnected(true);
}


function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    name = $("#username").val();
    ws.send('{"name":"'+$("#username").val()+'"}');
    
}
function sendMessage() {
    ws.send('{"message":"'+$("#messageX").val()+'"}');
}

function showNewMessage(message) {
    var senderName = message.substring(0,message.indexOf(":"));
    var msg = message.substring(message.indexOf(":")+1);
    $("#chatX").append( '<li class="left clearfix">'+
            `<span class="chat-img1 ${name != senderName?'pull-right':'pull-left'}">`+
        '<h6>'+
        senderName+'</h6>'+
        '</span>'+
        '<div class="chat-body1 clearfix">'+
        '<p>'+
         msg +
        '</p>'+
        //'<div class="chat_time pull-right">09:40PM</div>'+
        '</div>'+
        '</li>');
}
$(function () {
    $("#loginarea").show();
    $("#chatarea").hide();
    $("#chatareaX").hide();
    $("#conversation").hide();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    
});