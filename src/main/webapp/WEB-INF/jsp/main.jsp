<%@page import="com.calltag.model.User"%>
<% User user = (User)request.getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tweet Call</title>    
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="resources/css/main.css" rel="stylesheet" />

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
</head>
<body>
    
    <div id="container">
        <div class="row">
            <div class="span7">
                <h3>hi <%= user.getName()%> !, how are you today?</h3>
            </div>
        </div>

        <div class="row">
            <div class="span7">
                <form action="main.htm" method="GET">
                    <label class="checkbox inline">
                        <input type="checkbox" id="is_call_enabled" <%=user.getIsCallEnabled()?"checked":""%> value="is_call_enabled"> #call
                    </label>
                    <label class="checkbox inline">
                        <input type="checkbox" id="is_text_enabled <%=user.getIsTextEnabled()?"checked":""%> value="is_text_enabled"> #text
                    </label>
                    <button type="submit" class="btn">Save</button>
                </form>
            </div>
        </div>

    </div>
    
</body>
</html>






