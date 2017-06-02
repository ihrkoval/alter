<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://jonmiles.github.io/bootstrap-treeview/js/bootstrap-treeview.js"></script>
</head>
<body>
<div>
<div class="col-sm-4">
    <h2>Дерево </h2>
    <div id="treeview-checkable" class=""></div>
</div>
<div class="col-sm-4">
    <h2>Отмечены: </h2>
    <div id="checkable-output"></div>
</div>
    <div class="form-group row">
        <div class="col-sm-3">
            <h2>Подтверждение </h2>
            <button type="button" class="btn btn-success" id="btn-confirm">Отправить</button>
            <div id = "status"></div>
        </div>
    </div>
</div>
</body>


<script>
    jQuery.ajax({
        url: './agent/jsontree',
        method: 'GET',
        success: function (responce) {
            var agentListlvl2 = responce;
            var selected = [];

            var $checkableTree = $('#treeview-checkable').treeview({
                data: agentListlvl2,
                showIcon: true,
                showCheckbox: true,
                levels: 0,
                onNodeChecked: function (event, node) {
                    /*selected.push(node);*/
                    $('#checkable-output').prepend('<p id = "action'+node.tags[0]+'">' + node.text + '</p>');
                },
                onNodeUnchecked: function (event, node) {
                    $('#action'+node.tags[0]).remove();

                }
            });
            $('#btn-confirm').on('click', function (e) {
                alert("Идет отправка. Не закрывайте вкладку до появления сообщения о завершении");
                document.getElementById("status").innerHTML = "Идет отправка...";
               var checked =  $checkableTree.treeview('getChecked');
                const objs = JSON.stringify(checked);

           /*     function newmessage(){
                    document.getElementById("status").innerHTML = "ОТПРАВКА ЗАВЕРШЕНА!";
                    alert('ok');
                }

                function erroremessage(jqXHR){
                    document.getElementById("status").innerHTML = "Ошибка:" +textStatus;
                    alert("ОШИБКА: "+jqXHR.statusText);
                }*/
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    url: "./agent/submit",
                    type: "POST",
                    data: objs,
                    dataType: "json",
                    statusCode: {
                        200: function() {
                            document.getElementById("status").innerHTML = "ОТПРАВКА ЗАВЕРШЕНА!";
                            alert("отправка завершена");
                        },
                        500: function() {
                            document.getElementById("status").innerHTML = "Что-то пошло не так... :(";
                            alert("ОШИБКА!");
                        }
                    }
                });
               for(var i = 0; i<checked.length; i++){
                   console.log(checked[i].tags.toString()+" "+checked[i].text + " "+checked[i].href+" "+checked[i].nodes);
               }

            });
        }});



/*$.ajax('http://localhost:8080/agent/tree?treelevel=3&parentid=11')
.then(function(result){
console.log(JSON.stringify(result));
return $.ajax('http://echo.jsontest.com/id/2')
}).then(function(result){
console.log(JSON.stringify(result));
return $.ajax('http://echo.jsontest.com/id/3')
}).then(function(result){
console.log(JSON.stringify(result));
});*/
</script>
</body>
</html>