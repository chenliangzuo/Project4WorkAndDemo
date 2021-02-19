$(function(){

    $("#logBut").click(function(){
        $("#showError").hide();
        $.post("/login",$("#usernameForm").serialize(),function(result){
            if(result.flag){
                location.href="chat.html";
            } else {
                $("#showError").html(result.message);
                $("#showError").show();
            }
        });
        return false;
    });

});
