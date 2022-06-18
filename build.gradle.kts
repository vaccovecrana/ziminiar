plugins { id("io.vacco.oss.gitflow") version "0.9.8" }

group = "io.vacco.ziminiar"
version = "0.1.0"

configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
  addClasspathHell()
  sharedLibrary(true, false)
}
