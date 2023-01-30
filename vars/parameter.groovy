@Library('shared-library') _
pipeline {
  agent { label 'centos-slave' }
  tools{
    maven 'maven'
  }
  stages {
    stage('stage 1') {
      steps {
        helloWorld()
//        helloWorld(new LinkedHashMap<>([username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe"]))
//        helloWorld(username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe")
//        helloWorld("sarthak","Password@123","cb.wey5oeokxs3xxgrd.cloud.couchbase.com","pipe","checkname")
      }
    }
  }
}