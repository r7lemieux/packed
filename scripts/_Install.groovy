//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//
     
/*
includeTargets << new File ("${packedPluginDir}/scripts/InstallPackedTemplates.groovy")
target('default': 'install templates') {
install()
}
target('install': 'install templates') {
install()
}
*/


  copyDir(dirName:'src/templates', overwrite:false)
  copyDir(dirName:'src/templates/scaffolding/regen/packed', overwrite:true)
  copyDir(dirName:'web-app/css/regen/packed/listEdit/', overwrite:true)
  copyDir(dirName:'web-app/images/regen', overwrite:true)
  copyDir(dirName:'web-app/js/regen', overwrite:true)

  event("StatusUpdate", [ "Templates installed successfully"])


def copyDir(Map args) {
  def dirName = args.dirName
  def overwrite = args.overwrite
  def sourceDir = "${packedPluginDir}/${dirName}" + (args.fileName? "/${args.fileName}":'')
  def targetDir = basedir + '/' + dirName

  // only if template dir already exists in, ask to overwrite templates
  if (overwrite == null && new File(targetDir).exists()) {
     if (!isInteractive || confirmInput("Overwrite existing ${dirName} ? [y/n]","overwrite.files"))
      overwrite = true
  }
  else {
      ant.mkdir(dir: targetDir)
  }

  ant.copy(todir:"${targetDir}", overwrite:overwrite) {
     fileset(dir:"${sourceDir}", includes:"**" )
  }
}

