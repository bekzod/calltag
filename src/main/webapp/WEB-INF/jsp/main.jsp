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
    <link href="resources/css/bootstrapSwitch.css" rel="stylesheet" />

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script src="resources/js/bootstrapSwitch.js"></script>


</head>
<body>
    
<div id="container">

    <div class="row mainText">
        <div class="span12 pagination-centered">
            <h2>hi <%= user.getName()%> !, how are you today?</h2>
            <h3>Ready for <span class="pinkText">#text</span>ing and <span class="pinkText">#call</span>ing</h3><h4>Choose options and save.</h4>
        </div>
    </div>

    <div class="row">
        <div class="span12 pagination-centered">
            <form action="main.htm" method="POST">
                <label class="checkbox inline">
                    <input type="checkbox" 
                    id="is_call_enabled" 
                    name="is_call_enabled" 
                    <%= user.getIsCallEnabled()?"checked":""%>
                    value="1"> <h3 class="pinkText">#call</h3>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" 
                    id="is_text_enabled" 
                    name="is_text_enabled"
                    <%= user.getIsTextEnabled()?"checked":""%> 
                    value="1"> <h3 class="pinkText">#text</h3>
                </label>
                <button type="submit" class="btn">Save</button>
            </form>
        </div>
    </div>

    <div class="row mainText">
        <div class="span12 pagination-centered">
            <h3><span class="pinkText">#text</span> <span class="pinkText">+447414651623</span> 
            Hey dude tweetcall is awesome!
            </h3>
        </div>
        <div class="span12 pagination-centered">
            <h3><span class="pinkText">#call</span> <span class="pinkText">+447414651623</span> 
            Hey Rob, tweet is calling you!
            </h3>
        </div>
    </div>

     <div class="row">
        <div class="span12 pagination-centered">
            <a href="logout.htm" class='btn'>log out</a>
        </div>
    </div>

</div>
    
</body>
</html>
