<button class='packedUpdateAction' onclick="update('${domainClass.propertyName}', \${${propertyName}.id})" id="${domainClass.propertyName}\${${propertyName}.id}">
   <img class='packedUpdateImg' src="\${resource(dir:'images/regen/packed',file:'check.png')}" alt='Ok' border="0"/>
 </button>
 <img class='packedUpdateGrey' src="\${resource(dir:'images/regen/packed',file:'check_grey.png')}" alt='' border="0"/>
 <g:link class="packedShowAction" action="show" id="\${${propertyName}.id}"></g:link>
 <img class='packedShowGrey' src="\${resource(dir:'images/regen/packed',file:'show_grey.png')}" alt='' border="0"/>
 <g:link class="packedDeleteAction" action="delete" id="\${${propertyName}.id}"></g:link>
 <img class='packedDeleteGrey' src="\${resource(dir:'images/regen/packed',file:'delete_grey.png')}" alt='' border="0"/>
