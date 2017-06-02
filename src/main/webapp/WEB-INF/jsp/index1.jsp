<%--
  Created by IntelliJ IDEA.
  User: jlab13
  Date: 19.04.2017
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <h2>Списк агентов</h2>
    <p>Кликните по имени для отображения дочерних агентов</p>
    <div class="panel-group" id="panel-group">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse1">Collapsible panel</a>
                </h4>
            </div>
                <div id="collapse1" class="panel-collapse collapse">
                     <div class="panel-body">Panel Body</div>
                </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse2">Collapsible panel222</a>
                </h4>
            </div>
            <div id="collapse2" class="panel-collapse collapse">
                <div class="panel-body">Panel Body2222</div>
            </div>

        </div>
    </div>
</div>
<script>
  var agentsLevelTwo = ${agentsLvlTwo};
  var panelGroup = document.getElementById("panel-group");
              for (let i = 0; i < agentsLevelTwo.length; i++) {
                const agentTopObj = agentsLevelTwo[i];
                getAgentsByPArent(agentTopObj.id , 3);

                function getAgentsByPArent(parent, level){
                    jQuery.ajax({
                        url: './agent/tree?treelevel=' + level + '&parentid=' + parent,
                        method: 'GET',
                        success: function (responce) {
                            var panelDefault = document.createElement("div");
                            panelDefault.className = "panel panel-default";

                            var headingPtopAgentDiv = document.createElement("div");
                            headingPtopAgentDiv.className="panel-heading";

                            var h4 = document.createElement("h4");
                            h4.className = "panel-title";

                            var a = document.createElement("a");
                            a.setAttribute("data-toggle", "collapse");
                            a.setAttribute("href", "#id"+agentTopObj.id);
                            a.innerHTML = agentTopObj.sname;

                            var divAgent = document.createElement("div");
                            divAgent.className= "panel-collapse collapse";
                            divAgent.id = "id"+agentTopObj.id;

                            for (let i = 0; i < responce.length; i++) {
                                const agent = responce[i];
                                var agentName = document.createElement("div");
                                agentName.className = "panel-body";
                                agentName.innerHTML = agent.sname;
                                divAgent.appendChild(agentName);
                                //console.log($.ajax('http://localhost:8080/agent/tree?treelevel=4&parentid='+agent.id))
                            }


                            h4.appendChild(a);
                            headingPtopAgentDiv.appendChild(h4);

                            panelDefault.appendChild(headingPtopAgentDiv);
                            panelDefault.appendChild(divAgent);
                            panelGroup.appendChild(panelDefault);



                            return responce;
                        }
                    });

                };



               /* var panelDefault = document.createElement("div");
                panelDefault.className = "panel panel-default";

                var headingPtopAgentDiv = document.createElement("div");
                headingPtopAgentDiv.className="panel-heading";

                var h4 = document.createElement("h4");
                h4.className = "panel-title";

                var a = document.createElement("a");
                a.setAttribute("data-toggle", "collapse");
                a.setAttribute("href", "#id"+agentTopObj.id);
                a.innerHTML = agentTopObj.sname;

                var divAgent = document.createElement("div");
                divAgent.className= "panel-collapse collapse";
                divAgent.id = "id"+agentTopObj.id;

                console.log("AFTER RESPONCE VAR "+listChildLevel3)
                for (let i = 0; i < listChildLevel3; i++) {
                    const agent = listChildLevel3[i];
                    var agentName = document.createElement("div");
                    agentName.className = "panel-body";
                    agentName.innerHTML = agent.sname;
                    divAgent.appendChild(agentName);
                }


                h4.appendChild(a);
                headingPtopAgentDiv.appendChild(h4);

                panelDefault.appendChild(headingPtopAgentDiv);
                panelDefault.appendChild(divAgent);
                panelGroup.appendChild(panelDefault);*/




            /*    jQuery(function() {
                    function getAgentsChild(callback) {
                        jQuery.ajax({
                            url: './agent/tree?treelevel=' + 3 + '&parentid=' + agentTopObj.id,
                            method: 'GET',
                            success: callback
                        });
                    }
                    function renderChild(response) {
                        var divAgent = document.createElement("div");
                        divAgent.className= "panel-collapse collapse";
                        divAgent.id = "id"+agentTopObj.id;

                          for (let i = 0; i < response.length; i++) {
                            const agent = response[i];
                            var agentName = document.createElement("div");
                            agentName.className = "panel-body";
                            agentName.innerHTML = agent.sname;
                            divAgent.appendChild(agentName);
                        }
                        panelDefault.appendChild(divAgent);

                    }
                    getAgentsChild(renderChild);
                })*/
        }



</script>
</body>
</html>
