<?xml version="1.0" encoding="UTF-8"?>
<Response>
    <Say voice="woman" language="en-gb">
        <%=((String)request.getAttribute("text"))%> send by <%=((String) request.getAttribute("author"))%>
    </Say>
</Response>