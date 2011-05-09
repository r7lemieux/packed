
                            <td class='rowBtns'>
                              <g:render template='rowButtons' model="\${[${propertyName}:${propertyName}]}"/>
                            </td>
                            <td>
                              <img class='bullet' src="\${resource(dir:'images/regen/packed',
                                file:(${propertyName}.hasErrors())?'bullet_red.png':'bullet_white.png')}" alt="OK" border="0" />
                            </td>
                        
                        <%  props.eachWithIndex { p, i ->
                              cp = domainClass.constrainedProperties[p.name] %>
                           <% if (p.name == 'id') { %>
                              <td valign='top' class='value'>
                                <%  if (p.isEnum()) { %>
                                  \${${propertyName}?.${p.name}?.encodeAsHTML()}
                                <%  } else if (p.oneToMany || p.manyToMany) { %>

                                    <ul>
                                    <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                                        <li><g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}">\${${p.name[0]}?.encodeAsHTML()}</g:link></li>
                                    </g:each>
                                    </ul>

                                <%  } else if (p.manyToOne || p.oneToOne) { %>
                                <g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${propertyName}?.${p.name}?.id}">\${${propertyName}?.${p.name}?.encodeAsHTML()}</g:link>
                                <%  } else if (p.type == Boolean.class || p.type == boolean.class) { %>
                                <g:formatBoolean boolean="\${${propertyName}?.${p.name}}" />
                                <%  } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
                                <g:formatDate date="\${${propertyName}?.${p.name}}" />
                                <%  } else { %>
                                \${fieldValue(bean: ${propertyName}, field: "${p.name}")}
                                <%  } %>

                              </td>
                              <% } else { %>
                              <td valign="top" class="value \${hasErrors(bean: ${propertyName}, field: '${p.name}', 'errors')}">
                              ${renderEditor(p)}
                                <% if (p.manyToOne || p.oneToOne) { %>
                                  <g:link class="packedLinkShowAction" controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${propertyName}?.${p.name}?.id}">
                                    <img class='packedShowGrey' src="\${resource(dir:'images/regen/packed',file:'show_grey.png')}" alt='' border="0"/>
                                  </g:link>
                                <% } %>
                              </td>
                        <%  }  } %>