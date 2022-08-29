plugins { id("io.vacco.oss.gitflow") version "0.9.8" }

group = "io.vacco.ziminiar"
version = "0.1.5"

configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
  addClasspathHell()
  sharedLibrary(true, false)
}

dependencies {
  testImplementation("com.google.code.gson:gson:2.9.1")
}
