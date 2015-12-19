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
        #qr {
            width: 60%;
            margin: 10px;
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
    <script src="MyJson.js"></script>

    <script>
        tipmsg = ["",
                  "Option using bitmask indicating field option :\n1. Required (data fields only)\n2. Optional (data fields only)\n4. Display-only (data fields only. Works like an optional field but doesn't write to the database)",
                  "Returns the data type of the field :\n1. Keyword(Constants.AR_DATA_TYPE_KEYWORD)\n2. Integer(Constants.AR_DATA_TYPE_INTEGER)\n3. Real(Constants.AR_DATA_TYPE_REAL)\n4. Char(Constants.AR_DATA_TYPE_CHAR)\n5. Diary(Constants.AR_DATA_TYPE_DIARY)\n6. Enum(Constants.AR_DATA_TYPE_ENUM)\n7. Time(Constants.AR_DATA_TYPE_TIME)\n8. Bitmask(Constants.AR_DATA_TYPE_BITMASK)\n9. Bytes(Constants.AR_DATA_TYPE_BYTES)\n10. Decimal(Constants.AR_DATA_TYPE_DECIMAL)\n11. Attach(Constants.AR_DATA_TYPE_ATTACH)\n12. Currency(Constants.AR_DATA_TYPE_CURRENCY)\n13. Date(Constants.AR_DATA_TYPE_DATE)\n14. Time_of_Day(Constants.AR_DATA_TYPE_TIME_OF_DAY)\n30. Join(Constants.AR_DATA_TYPE_JOIN)\n31. Trim(Constants.AR_DATA_TYPE_TRIM)\n32. Control(Constants.AR_DATA_TYPE_CONTROL)\n33. Table(Constants.AR_DATA_TYPE_TABLE)\n34. Column(Constants.AR_DATA_TYPE_COLUMN)\n35. Page(Constants.AR_DATA_TYPE_PAGE)\n36. Page_Holder(Constants.AR_DATA_TYPE_PAGE_HOLDER)\n37. Attach_Pool(Constants.AR_DATA_TYPE_ATTACH_POOL)\n40. Ulong(Constants.AR_DATA_TYPE_ULONG)\n41. Coords(Constants.AR_DATA_TYPE_COORDS)\n42. View(Constants.AR_DATA_TYPE_VIEW)\n43. Display(Constants.AR_DATA_TYPE_DISPLAY)\n100. Value_List(Constants.AR_DATA_TYPE_VALUE_LIST) ",
                  "Data type using bitmask indicating field types :\n1. Data fields (AR_FIELD_TYPE_DATA).\n2. Trim fields (AR_FIELD_TYPE_TRIM)\n4. Control fields (AR_FIELD_TYPE_CONTROL).\n8. Page fields (AR_FIELD_TYPE_PAGE).\n16. Page Holder fields (AR_FIELD_TYPE_PAGE_HOLDER).\n32. Table fields (AR_FIELD_TYPE_TABLE).\n64. Column fields (AR_FIELD_TYPE_COLUMN).\n128. Attachment fields (AR_FIELD_TYPE_ATTACH).\n256. Attachment Pool fields (AR_FIELD_TYPE_ATTACH_POOL)."
        ]
<%
        out.println("form=\"" + request.getParameter("q") +"\"");
%>
        title = ["Name", "Value", "Option", "Type",]
        data = {}
        fdata = []

        options = { 1: "Required",
                2:"Optional",
                3:"Req_Opt",
                4:"Display" }
        datatypes = { 1: "Kwyword",
                2: "Integer",
                3: "Real",
                4: "Char",
                5: "Diary",
                6: "Enum",
                7: "Time",
                8: "Bitmask",
                9: "Bytes",
                10: "Decimal",
                11: "Attach",
                12: "Currency",
                13: "Date",
                14: "Time_of_Day",
                30: "Join",
                31: "Trim",
                32: "Control",
                33: "Table",
                34: "Column",
                35: "Page",
                36: "Page_Holder",
                37: "Attach_Pool",
                40: "Ulong",
                41: "Coords",
                42: "View",
                43: "Display",
                100: "Value_List"}
        fieldtypes = {1: "Data",
                2: "Trim",
                4: "Control",
                8: "Page",
                16: "Page Holder",
                32: "Table",
                64: "Column",
                128: "Attachment",
                256: "Attachment-pool",
                511: "All" }
                  
        function showtip(n) {
            alert(tipmsg[n])
        }
        function trimQ(s) {
            if (s.charAt(0) == '"')
                s = s.replace(/^\"|\"$/gm,'');
            return s
        }
        function getConst(n, k) {
            r = ""
            if (n == 2)
                r = options[k]
            else if (n == 3)
                r = datatypes[k]
            else if (n == 3)
                r = fieldtypes[k]
            if (r == "" || r == undefined)
                r = k
            return r
        }
        function reloadValue(s) {
            try { 
                ldata = JSON.parse(s)
            }
            catch(e) {
                ldata = new MyJson().parse(s)
            }
            fdata = ldata.Value;                  
            document.getElementById('dl').innerHTML = getftable(ldata.Entry)
        }
        function reloadData(s) {
            data = JSON.parse(s)
            document.getElementById('fn').innerHTML = "(" + data.Number + ")"
            document.getElementById("tb").innerHTML = gettable()
            if (parseInt(data.Number) > 0)
                loadXMLDoc("getentry.jsp?q=" + form + "&id=" + data.Ids[0], reloadValue);
        }
        function getrow(r, f) {
            if (f) s="<tr class='alt'>"
            else s = "<tr>"    
            s += "<td class='sel' onclick='load(this)'> " + r + "</td>"
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
            for (j =0; j<lr.length; j++)
                if (j > 1 && j < 4)
                    s += "<td>" + getConst(j, trimQ(lr[j])) + "</td>"
                else
                    s += "<td>" + trimQ(lr[j]) + "</td>"
            return s + "</tr>"
        }
        function getheader() {
            s = "<tr>"
            for (h = 0; h< title.length; h++)
                if (h>1 && h < 4)
                    s += "<th class='sel' onmousedown='showtip(" + (h-1) + ")'>" + title[h] + "</th>"
                else 
                    s += "<th>" + title[h] + "</th>"
            return s + "</tr>"
        }
        function getftable(entry) {
            s = "<h4 class='sh'>&nbsp;&nbsp;<span >" + form + "&nbsp;&nbsp;--&nbsp;&nbsp;"
            s += entry + "</span> &nbsp;&nbsp;(" + fdata.length +")</h4><table>"
            s += getheader()
            for (k = 0; k<fdata.length; k++)
                 s += getfrow(fdata[k], k%2)
            return s + "</table>"
        }
        function init() {
            if (form == "") form = "User"; 
            loadXMLDoc("getinfo.jsp?q="+form, loadpage)
        }
        function load(obj) {
            id= obj.innerHTML;
            loadXMLDoc("getentry.jsp?q="+form + "&id=" + id.trim(), reloadValue)
        }
        function loadpage(str) {
            obj = document.getElementById('hd');
            obj.innerHTML = str;
            loadXMLDoc("getqids.jsp?q=" + form, reloadData);
        }
        function loadids() {
            qustr = document.getElementById("qr").value;
            loadXMLDoc("getqids.jsp?q=" + form + "&str=" + qustr, reloadData);
        }
    </script>
</head>
<body onload="init()">
    <div id="hd" class="sh"></div>
    <table>
        <tr><td colspan="2">
            Query String:<input id="qr"/><button onclick="loadids()">submit</button></td></tr>
        <tr><td colspan="2">
        <h4>Select Id:&nbsp;&nbsp; <span id="fn">(140) </span></h4> 
        </td></tr>
        <tr><td id="lp">
        <div id="tb">Forms</div>
         </td><td id="rp">
        <div id="dl">Detail</div>
        </td></tr>
    </table>   
</body>
</html>