/**
 * Created by homeuser on 10.05.2017.
 */
jQuery("document").ready(function(){

    $(function(){
        $('#sidebar').css({"min-height" : $('#content').height()});
    });

   /* $('.hovertip').mousemove(function(e){
        var hint = $(this).attr("rel");
        $("#hint").css({"left": e.pageX + 10, "top": e.pageY + 10});
        $("#hint").show().text(hint);

    }).mouseout(function(){
        $('#hint').hide();
    });*/

});