/**
 * Created by homeuser on 10.05.2017.
 */
jQuery("document").ready(function(){

    $(function(){
        $('#sidebar').css({"min-height" : $('#content').height()});
    });

    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#image').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#imgInput").change(function(){
        readURL(this);
    });

   /* $('.hovertip').mousemove(function(e){
        var hint = $(this).attr("rel");
        $("#hint").css({"left": e.pageX + 10, "top": e.pageY + 10});
        $("#hint").show().text(hint);

    }).mouseout(function(){
        $('#hint').hide();
    });*/

});