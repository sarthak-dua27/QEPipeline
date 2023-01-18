@Library('shared-library') _
pipeline {
  agent any
  tools{
    maven 'maven'
  }
  stages {
    stage('stage 1') {
      steps {
        sh 'mvn --version'
        sh 'mvn clean install'
        helloWorld(new LinkedHashMap<>([username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe"]))
      }
    }
  }
}