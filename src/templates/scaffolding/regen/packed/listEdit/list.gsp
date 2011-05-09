<% import grails.persistence.Event %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="\${resource(dir:'css',file:'regen/packed/listEdit/listEdit.css')}" />     
        <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
        <g:javascript src="regen/listEdit.js"  />        
      <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="\${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="\${flash.message}">
            <div class="message">\${flash.message}</div>
            </g:if>
            <div style='visibility:hidden' id='errors' class="errors"></div>
            <div class="list">
              
                <table>
                    <thead>
                        <tr><td></td><td></td>
                        <%
                            props.eachWithIndex { p, i ->
                              if (p.isAssociation()) { %>
                            <th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
                   	    <%      } else { %>
                            <g:sortableColumn property="${p.name}" title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" />
                        <%  }   } %>
                        </tr>
                    </thead>
                    <tbody>        
                    <g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}" name="${domainClass.propertyName}.\${${propertyName}.id}">
                          <g:render template="listRow" model="[${propertyName}:${propertyName}, props:props]"></g:render>
                        </tr>
                    </g:each>                    
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="\${${propertyName}Total}" />
            </div>
        </div>
       <label id='log'></label>         
    </body>
</html>
