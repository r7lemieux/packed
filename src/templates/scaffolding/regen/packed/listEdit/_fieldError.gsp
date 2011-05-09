<g:hasErrors  bean="\${${propertyName}}" field="\${fieldName}">
    <div style='display:none' class="errors">
       <g:renderErrors bean="\${${propertyName}}" field="\${fieldName}" as="list" />
    </div>
</g:hasErrors>
