<!DOCTYPE html>
<html>
<head>
    <style>
        textarea {
            position:relative;
            left: 20px;
            width: 93%;
            background: lightyellow;
            readonly;
            disabled;
        }
        tr {
            background-color: beige;
            max-height: 80%;
        }
        th {
            background-color:darkgray;
        }
        table {
            width: 100%;
        }
        .alt {
            background-color:lightgray ;
        }
        .sel:hover, button:hover{
            background-color:#A1C942 ;
            cursor: pointer
        }
        .sh {
            color: darkblue;
        }
        #hd {
            border: 1px;
            background-color:lightgray;
        }
        #lp {
            width: 20%;
            vertical-align: top;
        }
        #rp {
            width: 80%;
            vertical-align: top;
        }
        #tb {
            border: 1px;
            background-color:aliceblue;
            max-height: 460px;
            overflow-y: scroll;
        }
        #dl {
            border:1px solid #4d4d4d;
            overflow-y: auto;
        }
    </style>
    <script src="MyBase.js"></script>
    <script src='MyJson.js'></script>
    <script>
<%
        out.println("form=\"" + request.getParameter("q") +"\"");
        out.println("id=\"" + request.getParameter("id") + "\"");
%>
        title = ["Name", "Value"]
        data = {}
        fdata = []
        cstat = 0
        
        function reloadValue(s) {
            try { 
                ldata = JSON.parse(s)
            }
            catch(e) {
                ldata = new MyJson().parse(s)
            }
            fdata = ldata.Value;                  
            document.getElementById('dl').innerHTML = getftable(ldata.Entry, ldata.Select)
        }
        function reloadData(s) {
            data = JSON.parse(s)
            document.getElementById('fn').innerHTML = "(" + data.Number + ")"
            document.getElementById("tb").innerHTML = gettable()
            if (parseInt(data.Number) > 0)
                loadXMLDoc("getfield.jsp?q=" + form + "&id=" + id, reloadValue);
        }
        function getrow(r, f) {
            if (f) s="<tr class='alt'>"
            else s = "<tr>" 
            if (cstat == 0)
                s += "<td class='sel' onclick='load(" + r[1] + ")'> " + r[0] + "</td>"
            else
                s += "<td class='sel' onclick='loadALE(\"" + r + "\")'> " + r + "</td>"
            return s + "</tr>"
        }
        function gettable() {
            s = "<table>"
            for (i = 0; i<data.Ids.length; i++) {
                s += getrow(data.Ids[i], i%2)
            }
            return s + "</table>"
        }
        function getfrow(lr, f) {
            if (f) s = "<tr class='alt'>"
            else s = "<tr>"
            for (j =0; j<2; j++)
                s += "<td>" + lr[j] + "</td>"
            return s + "</tr>"
        }
        function getheader() {
            s = "<tr>"
            for (h = 0; h< title.length; h++)
                s += "<th>" + title[h] + "</th>"
            return s + "</tr>"
        }
        function getftable(entry, name) {
            s = "<h4 class='sh'>&nbsp;&nbsp;<span >" + name + "&nbsp;&nbsp;--&nbsp;&nbsp;"
            if (cstat == 0)
                s += entry + "</span> &nbsp;&nbsp;-- [ " + id +" ] </h4><table>"
            else
                s += entry + "</span> &nbsp;&nbsp;-- ( " + fdata.length+" ) </h4><table>"
            s += getheader()
            for (k = 0; k<fdata.length; k++)
                 s += getfrow(fdata[k], k%2)
            return s + "</table>"
        }
        function init() {
            if (form == "") form = "User"; 
            loadXMLDoc("getinfo.jsp?q="+form, loadpage)
        }
        function load(lid) {
            id = lid
            loadXMLDoc("getfield.jsp?q="+form + "&id=" + id, reloadValue)
        }
        function loadpage(str) {
            obj = document.getElementById('hd');
            obj.innerHTML = str;
            loadXMLDoc("getfids.jsp?q="+form, reloadData);
        }
        function loadALE(nm) {
            loadXMLDoc("getafe.jsp?q="+nm + "&id=" + cstat, reloadValue)
        }
        function reloadALEs(s){
            data = JSON.parse(s)
            document.getElementById('fn').innerHTML = "(" + data.Number + ")"
            document.getElementById("tb").innerHTML = gettable()
            if (parseInt(data.Number) > 0) {
                nm = data.Ids[0]
                loadALE(nm)
            }
        }
        function change(v) {
            cstat = v
            if (cstat == 0) 
                loadXMLDoc("getfids.jsp?q="+form, reloadData) 
            else     
                loadXMLDoc("getafes.jsp?q=" + form +"&id=" + cstat, reloadALEs)
        }
    </script>
</head>
<body onload="init()">
    <div id="hd" class="sh"></div>
    <table>
        <tr><td colspan="2">
        <h4>Select <select id='sf' onchange='change(this.value)'> 
                <option value=0>Field</option>
                <option value=1>ActiveLink</option>
                <option value=2>Filter</option>
                <option value=3>Escalation</option>
            </select>
            :&nbsp;&nbsp; <span id="fn">(10) </span></h4> 
        </td></tr>
        <tr><td id="lp">
        <div id="tb">Forms</div>
         </td><td id="rp">
        <div id="dl">Detail</div>
        </td></tr>
    </table>   
</body>
</html>