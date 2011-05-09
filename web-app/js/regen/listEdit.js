var updateRowfct = function updateRow(xmlhttp) {
      if (xmlhttp.status == 200) {
        //alert('updateRowfct\n' + xmlhttp + "state " + xmlhttp.state + " response\n" + xmlhttp.responseText);
        htmlText = xmlhttp.responseText.replace(/bullet_white.png" alt="OK"/, 'bullet_green.png" alt="OK"');
        //alert('htmlTest ' + htmlText);
        var idMatch = htmlText.match( /class='packedUpdateAction' onclick="update\('(\w*)', (\d*)\)/ );

        //log ('  id  ' + idMatch);
        //var id = xmlDoc.getElementsByTagName('td').childNodes[1].nodeValue;
        var className = idMatch[1]
        var id = idMatch[2];
        //log ('instance ' + className + ' ' + id);
        var row = document.getElementsByName(className + '.' + id);
        row[0].innerHTML=htmlText;
        listenToRow(row[0]);

         //alert('row updated');
      } else if (xmlhttp.status == 227) {
        //alert('updateRowfct\n' + xmlhttp + "state " + xmlhttp.state + " response\n" + xmlhttp.responseText);
        //log(xmlhttp.responseText);
        var resp = eval('(' + xmlhttp.responseText + ')');
        var errors = resp.errors.errors;
        //log ( "resp              " + resp);
        var id = resp.id;
        //log ("     id     " + resp.instanceName + resp.id);
        var row = document.getElementsByName(resp.instanceName + '.'+ resp.id)[0];
        var cells = row.childNodes;

        //log ( "   row     " + row);

        for (var c=0; c<cells.length; c++) {
          var cell = cells[c];
          if (cell.nodeName == 'TD') {
            var cellChildren = cell.childNodes;
            if (cellChildren != null){
              var match = false;
              for (var cc=0; !match && cc<cellChildren.length; cc++) {
                content = cellChildren[cc];
                if (content.nodeName == 'INPUT' || content.nodeName == 'SELECT') {
                  content.disabled = '';
                  if (errors != null) {
                  for (var e=0; !match && e<errors.length; e++) {
                    var error = errors[e];
                    if (error.field == content.getAttribute('name')) {
                      //log ("error   " + error.field);
                      //alert ('match ' + error.field);
                      match = true;
                      content.setAttribute('class','packedErrorField');
                      var errorDiv = document.createElement('div');
                      errorDiv.innerHTML = error.message;
                      errorDiv.setAttribute('class','hiddenError');
                      content.parentNode.insertBefore(errorDiv,content);

                    }
                  }
                  }
                } if (content.nodeName == 'A' || content.nodeName == 'BUTTON' || content.nodeName == 'IMG') {
                   //alert (content.getAttribute('class') + " " + content.getAttribute('class').match(/Grey$/));
                   if (content.getAttribute('class').match(/Action$/) != null) {
                     //alert('action ' + content.style.display);
                     content.style.display = 'inline';
                   } else if (content.getAttribute('class').match(/Grey$/) != null) {
                     //alert("grey " + content);
                     content.style.display = 'none';
                   }
                   //cellContent.style.display = 'none';
                   //cellContent.style.visibility = 'hidden';
                   //cellContent.style.background = 'transparent url(../../images/regen/packed/show_grey.png) 5px 50% no-repeat';
                }
              }
            }

          }
        }
        if (errors != null) {
          var statusImg = cells[3].childNodes[1];
          var rootImg = statusImg.src.match( /(.*\/)(.*)/ )[1];
          var imgUrl = rootImg + 'regen/packed/bullet_red.png';
          statusImg.src = imgUrl;
        }

      } else {
        alert('not 200 ' + xmlhttp.status);
        //alert('id ' + xmlhttp.getRequestHeader('id'));
      }
     }

    function update(classPropertyName, id) {

      var src = document.getElementsByName(classPropertyName + '.' + id)[0];
      //log ('update ' + classPropertyName +'.'+ id);
      var row = src;

      //alert("row " + row);
      //alert('update ' + classPropertyName + ' id ' + id + '\n' + src.id);
      var url = document.URL.split('/');
      //updateUrlPath = url[2] + '/' + url[3] + '/' + classPropertyName + '/update?id=' + id
      //var updateUrlPath = url[3] + '/' + classPropertyName + '/update?id=' + id;
      var updateUrlPath  = 'updateAjax?id=' + id;
      var cells = row.childNodes;

      //alert("row " + row.nodeType + ' ' + row.nodeName + ' ' + row.Value);

      for (var c=1; c < cells.length; c++ ) {
        if (cells[c].nodeName == 'TD')  {
          var ccs = cells[c].childNodes;
          for (var cc=0; cc < ccs.length; cc++) {
            var content = ccs[cc];

            if (content.nodeName == 'INPUT' ) {
              content.disabled = true;
              var fieldName = content.getAttributeNode('name').value;
              var fieldValue = content.value;
              if (fieldValue && fieldName != 'id') {
                 updateUrlPath += '&' + fieldName + '=' + fieldValue;
               //alert(fieldName + ' ' + fieldValue);
              }
            } else if (content.nodeName == 'SELECT') {
              content.disabled = true;
              var fieldName = content.getAttributeNode('name').value;
              var fieldValue = content.value;
              updateUrlPath += '&' + fieldName + '=' + fieldValue;
              //alert(fieldName + ' ' + fieldValue);
            } else {
                //if (content.nodeName == 'BUTTON') {
                //  content.disabled = 'true';
                //} else
                if (content.nodeName == 'A' || content.nodeName == 'BUTTON' || content.nodeName == 'IMG') {
                   //alert (content.getAttribute('class') + " " + content.getAttribute('class').match(/Grey$/));
                   if (content.getAttribute('class').match(/Action$/) != null) {
                     //alert('action ' + content.style.display);
                     //cells[c].removeChild(content);
                     content.style.display='none';
                   } else if (content.getAttribute('class').match(/Grey$/) != null) {
                     //alert("grey " + content);
                     content.style.display = 'inline';
                   }
                   //cellContent.style.display = 'none';
                   //cellContent.style.visibility = 'hidden';
                   //cellContent.style.background = 'transparent url(../../images/regen/packed/show_grey.png) 5px 50% no-repeat';
                } else {
                  //alert ('nn ' + cellContent.nodeName);
                }
              }

          }
        }
      }
      for (var f=1; f<cells.length; f+=2) {
        //alert('cells['+f+'] ' + cells[f]);
        //log('cells['+f+'] ' + cells[f].childNodes[1]);

      }
      var statusImg = cells[3].childNodes[1];
      //alert ('statusImg ' + statusImg.src);
      var rootImg = statusImg.src.match( /(.*\/)(.*)/ )[1];
      //alert('rootImg ' + rootImg);
      var imgUrl = rootImg + '../../spinner.gif';
      statusImg.src = imgUrl;
      //alert("updateUrlPath " + updateUrlPath);
      ajax(updateUrlPath, updateRowfct);
    }



function ajax(url, fct)
{
var xmlhttp;
if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
}
else { // code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}

xmlhttp.onreadystatechange=function() {
  //alert ("state " + xmlhttp.readyState);
  //log (xmlhttp.readyState);
  if (xmlhttp.readyState == 4) {// && xmlhttp.status == 200) {
    //alert ("state " + xmlhttp.readyState + " status " + xmlhttp.status + " " + xmlhttp.responseText);
    fct(xmlhttp);
  }
}

xmlhttp.open("GET", url ,true);

xmlhttp.send(null);
//alert("ajax after url " + url);
}

function log(txt) {
var log = document.getElementById('log');
logtext = log.innerHTML + '\n' + txt;
log.innerHTML = logtext;

}
var focusedValue;
function onFieldFocus(e) {
//alert('focus');
focusValue = e.target.value;
}
function onFieldBlur(e) {
 //alert('fieldBlur focusValue ' + focusValue  + ' target.value ' + e.target.value);
 if (focusValue != e.target.value) {
 e.target.setAttribute('class', 'packedDirtyField');
 img = e.target.parentNode.parentNode.childNodes[3].childNodes[1];
 //for (i=0; i<aaa.length; i++) {log ('' + i + ' ' + aaa[i].nodeName)};
 newImg = img.getAttribute('src').replace(/bullet_\w*.png/, 'bullet_yellow.png');
 img.setAttribute('src', newImg);
 }
}

function onFieldClick(e) {
var siblings = e.target.parentNode.childNodes;
var hasError = false;
var errordiv = document.getElementById('errors');
for (var s=0; s<siblings.length; s++) {
  var sibling = siblings[s];
  //alert ('sibling ' + sibling.nodeName);
  if (sibling.nodeName == 'DIV' && sibling.getAttribute('class') == 'hiddenError') {
    errordiv.style.visibility='visible';
    errordiv.innerHTML = sibling.innerHTML;
    hasError=true;
  }
}
if (!hasError) {
  errordiv.style.visibility='hidden';
}
}

var lastClick = 0;
function showSelection(e) {
  //alert('showSelection ' + e)
  var thisClick = (new Date()).getTime();
  if (thisClick - lastClick < 400) {
    var url = '/'+document.location.pathname.split('/')[1]+'/';
    if (e.target.nodeName == 'SELECT') {
      var id = e.target.options[e.target.selectedIndex].value;
      var showUrl = null;
      if (id != null) {
        var selectSiblings = e.target.parentNode.childNodes;
        for (var ss=0; ss<selectSiblings.length; ss++) {
          var selectSibling = selectSiblings[ss];
          if (selectSibling.nodeName == 'A') {
            showUrl = selectSibling.getAttribute('href');
          }
        };
        var newWindow = window.open(showUrl, 'show', 'height=500, width=350');
        return true;
      }
    } else {
      var objName = e.target.parentNode.parentNode.getAttributeNode('name').value;
      var classname = objName.split('.')[0];
      var id =  objName.split('.')[1];
      var showUrl = url+classname+'/show/'+id;
      //var newWindow = window.open(showUrl, 'show', 'height=500, width=350');
      return true;
    }
  }
  lastClick = thisClick;
}

window.onload = function() {
var rows = document.getElementsByTagName('TR');
for (var r=0; r<rows.length; r++) {
  var row = rows[r];
  listenToRow(row);
}
}

function listenToRow(row) {
  var rowname = row.getAttributeNode('name');
  if (row.getAttributeNode('name') != null) {
    //alert('row ' + rowname.value);
    var cells = row.childNodes;
    //alert('row ' + row + ' nodeType ' + row.nodeType + ' nodeName ' + row.nodeName + ' ' + row.value);
    for (var c=1; c < cells.length; c++ ) {
      if (cells[c].nodeName == 'TD')  {
        var ccs = cells[c].childNodes;
        for (var cc=0; cc < ccs.length; cc++) {
          var cellContent = ccs[cc];
          if (cellContent.nodeName == 'INPUT' || cellContent.nodeName == 'SELECT') {
            cellContent.onfocus =  onFieldFocus;
            cellContent.onblur = onFieldBlur;
            cellContent.onmouseover = onFieldClick;
          //}
          //if (cellContent.nodeName == 'SELECT') {
            cellContent.onclick = showSelection;
          }
        }
      }
    }
  }
}

var popShow = function popupShow(xmlhttp) {
  if (xmlhttp.status == 200) {
    //alert('updateRowfct\n' + xmlhttp + "state " + xmlhttp.state + " response\n" + xmlhttp.responseText);
    htmlText = xmlhttp.responseText;
    var newWindow = window.open(url, 'show', 'height=200, width=150');
    return false;
  }
}

