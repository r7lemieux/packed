/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Gant script that installs artifact and scaffolding templates
 *
 */

includeTargets << grailsScript("_GrailsInit")

target ('default': "Installs the artifact and scaffolding templates") {
  depends(checkVersion)
  installTemplates();
}

target (install: "Installs the artifact and scaffolding templates") {
  installTemplates();
}

def installTemplates() {
  copyDir(dirName:'src/templates', overwrite:false)
  copyDir(dirName:'src/templates/scaffolding/regen/packed', overwrite:true)
  copyDir(dirName:'web-app/css/regen/packed/listEdit/', overwrite:true)
  copyDir(dirName:'web-app/images/')
  copyDir(dirName:'web-app/js/')

  event("StatusUpdate", [ "Templates installed successfully"])
}

def copyDir(Map args) {
  def dirName = args.dirName
  def overwrite = args.overwrite
  def sourceDir = "${packedPluginDir}/${dirName}" + (args.fileName? "/${args.fileName}":'')
  def targetDir = basedir + '/' + dirName

  // only if template dir already exists in, ask to overwrite templates
  if (overwrite == null && new File(targetDir).exists()) {
     if (!isInteractive || confirmInput("Overwrite existing + ${dirName} ? [y/n]","overwrite.files"))
      overwrite = true
  }
  else {
      ant.mkdir(dir: targetDir)
  }

  ant.copy(todir:"${targetDir}", overwrite:overwrite) {
     fileset(dir:"${sourceDir}", includes:"**" )
  }
}
  /*
  def atarget('default': "Installs the artifact and scaffolding templates")  = {
    depends(checkVersion, parseArguments)
    def templatesName = argsMap['params'][0]

    copyDir(dirname:'src/templates', overwrite:false)
    copyDir(dirname:'src/templates', overwrite:false)

  	def templatesSourceDir = "${packedPluginDir}/src/templates" // + (templatesName ? "/${templateName}":'')
    def templateTargetDir = basedir + '/src/templates'
   	def cssSourceDir = "${packedPluginDir}/src/templates" // + (templatesName ? "/${templateName}":'')
    def cssTargetDir = basedir + '/src/templates'
  	def jsSourceDir = "${packedPluginDir}/src/templates" // + (templatesName ? "/${templateName}":'')
    def jsTargetDir = basedir + '/src/templates'
    def imgSourceDir = "${packedPluginDir}/src/templates" // + (templatesName ? "/${templateName}":'')
    def imgTargetDir = basedir + '/src/templates'
    overwrite = false

    // only if template dir already exists in, ask to overwrite templates
    if (new File(templateTargetDir).exists()) {
       if (!isInteractive || confirmInput("Overwrite existing templates? [y/n]","overwrite.templates"))
        overwrite = true
    }
    else {
        ant.mkdir(dir: templateTargetDir)
    }

     ant.copy(todir:"${templateTargetDir}", overwrite:false) {
       fileset(dir:"${templatesSourceDir}", includes:"**" )
     }
     ant.copy(todir:"${templateTargetDir}/templates/scaffolding/regen", overwrite:true) {
       fileset(dir:"${templatesSourceDir}/scaffolding/regen", includes:"**" )
     }

     def cssDir = 'web-app'
     ant.copy(todir:"${templateTargetDir}/templates/scaffolding/regen", overwrite:true) {
       fileset(dir:"${templatesSourceDir}/scaffolding/regen", includes:"**" )
     }

     event("StatusUpdate", [ "Templates installed successfully"])

    //copyGrailsResources("${targetDir}/scaffolding", "${templatesDir}/scaffolding/*") //, overwrite)
    //ant.copy(file:webXml.file, tofile:tmpWebXml, overwrite:true)
    //ant.mkdir(dir:"${targetDir}/war")
    //copyGrailsResource("${targetDir}/war/web.xml", grailsResource("src/war/WEB-INF/web${servletVersion}.template.xml"), overwrite)

}
*/


