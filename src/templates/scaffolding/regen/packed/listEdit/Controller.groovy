<%=packageName ? "package ${packageName}" : ''%>

import org.codehaus.groovy.grails.plugins.web.taglib.RenderTagLib
import grails.converters.JSON
import java.text.SimpleDateFormat

class ${className}Controller {
<% def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
    SimpleDateFormat dateFormat = new SimpleDateFormat('MM/dd/yy');
    def fields =  ${className}.getDeclaredFields().toList()
    def fieldDates = fields.findAll{f->f.getGenericType() == Date.class}.collect{f->f.name}

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count()]
    }

    def create = {
        def ${propertyName} = new ${className}()
        ${propertyName}.properties = params
        return [${propertyName}: ${propertyName}]
    }

    def formatDates = {params ->
      params.each { p->
        if ( fieldDates.contains(p.key)) {
          try {
            params["\${p.key}"] = dateFormat.parse(p.value)
          } catch (Exception ex) {}
        }
      }
    }

    def save = {
        formatDates(params)
        def ${propertyName} = new ${className}(params)
        if (${propertyName}.save(flush: true)) {
            flash.message = "\${message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
            redirect(action: "show", id: ${propertyName}.id)
        }
        else {
            render(view: "create", model: [${propertyName}: ${propertyName}])
        }
    }

    def show = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
        else {
            [${propertyName}: ${propertyName}]
        }
    }

    def edit = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [${propertyName}: ${propertyName}]
        }
    }

    def update = {
        try {
          params.id = Long.parseLong(params.id)
        } catch (MissingMethodException e) {
          params.id = params.id[0]
        }
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            if (params.version) {
                def version = params.version.toLong()
                if (${propertyName}.version > version) {
                    ${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[], "Another user has updated this ${className} while you were editing")
                    render(view: "edit", model: [${propertyName}: ${propertyName}])
                    return
                }
            }
            formatDates(params)

            ${propertyName}.properties = params
            if (!${propertyName}.hasErrors() && ${propertyName}.save(flush: true)) {
                flash.message = "\${message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
                redirect(action: "show", id: ${propertyName}.id)
            }
            else {
                render(view: "edit", model: [${propertyName}: ${propertyName}])
            }
        }
        else {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
    }


    def showRow = {
      def ${propertyName} = ${className}.get(params.id)
      render(template:'listRow', model:[${propertyName}:${propertyName}])
    }

    def updateAjax = {

        Thread.sleep(300);
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
          if (params.version) {
            def version = params.version.toLong()
            if (${propertyName}.version > version) {
              ${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[], "Another user has updated this ${className} while you were editing")
              render(template:'listRow', model:[${propertyName}:${propertyName}])
              return
            }
          }
          formatDates(params)

          ${propertyName}.properties = params
          def val = ${propertyName}.validate()
          if(!val) {
            ${propertyName}.errors.allErrors.each {
              //println it
            }
          }

          if (!${propertyName}.hasErrors() && ${propertyName}.save(flush: true)) {
            render(template:'listRow', model:[${propertyName}:${propertyName}])
          }
          else {
            def message = "\${message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
            response.status = 227;
            def ret = [id:params.id, instanceName:'${domainClass.propertyName}', errors:${propertyName}.errors]
            render ret as JSON
          }
        }
        else {
          flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
          render(template:'listRow', model:[${propertyName}:${propertyName}])
        }
      }

    def updateLine = {
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            if (params.version) {

                def version = params.version.toLong()
                if (${propertyName}.version > version) {
                    ${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[], "Another user has updated this ${className} while you were editing")
                    flash.message = "\${message(code: 'default.optimistic.locking.failure', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
                    redirect(action: "list")
                    return
                }

            }
            ${propertyName}.properties = params
            if (!${propertyName}.hasErrors() && ${propertyName}.save(flush: true)) {
                flash.message = "\${message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
            }
        }
        else {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
        }
        redirect(action: "list")
    }

    def delete = {
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            try {
                ${propertyName}.delete(flush: true)
                flash.message = "\${message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "\${message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
    }

}
