/**
 * Created by homeuser on 10.05.2017.
 */
jQuery("document").ready(function(){

    $('#submit').on('click', function(){

       $("[type=text], [type=number], select").each(function(){
            if($(this).val() == '' || $(this).val() == 0){
                $(this).css({'background-color' : "red"});
                if(!$(this).hasClass("wrong")){
                    $(this).addClass("wrong");
                    $(this).parent().append("<img style='margin-bottom: -6px;' src='../images/arrow-30_30.png' alt=''>");
                }
            }
        });

        return confirm("Are you sure you want to create an account with such data?");
    });


    $("input").keyup(function(){
        $(this).css({"background" : "#ddffcb"});
        $(this).parent().find("img").remove();
        $(this).removeClass("wrong");

    });

    $("input[type=number]").keypress(function(e) {
        if(isNaN(this.value+""+String.fromCharCode(e.charCode))) return false;
    })
        .on("cut copy paste",function(e){
            e.preventDefault();
        });


    $(function(){
        $('#sidebar').css({"min-height" : $('#content').height()});
    });


});