@Library('cb-bucket-update') _
pipeline {
  agent any
  stages {
    stage('stage 1') {
      steps {
        sh 'maven clean install'
        helloWorld()
//        helloWorld(new LinkedHashMap<>([username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe"]))
//        helloWorld(username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe")
//        helloWorld("sarthak","Password@123","cb.wey5oeokxs3xxgrd.cloud.couchbase.com","pipe","checkname")
      }
    }
  }
}