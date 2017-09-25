$(function(){

    function createJsonFromForm() {
        var type = $("#type").val();
        var qualifier = $("#qualifier").val();
        var description = $("#description").val();
        var date = $("#date").val();
        var duration = $("#duration").val();
        var difficulty = $("#difficulty").val();
        var enjoyment = $("#enjoyment").val();

        var data = {};
        data.name = "trevor";
        data.type = type;
        data.qualifier = qualifier;
        data.description = description;
        data.date = new Date(date);
        data.duration = parseInt(duration);
        data.difficulty = parseInt(difficulty);
        data.enjoyment = parseInt(enjoyment);

        return JSON.stringify(data);
    }

    $("#submitChore").click(function(){
        $('#submitChore').prop('disabled', true);
        var str = createJsonFromForm();

        $.ajax ({
            url: "api/chore",
            type: "POST",
            data: str,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function(){
                $('#submitChore').prop('disabled', false);
            },
            error: function(){
                $('#submitChore').prop('disabled', false);
            }
        });
    });

    $("#date").pickadate({
        format: "yyyy-mm-dd"
    });

});