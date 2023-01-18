@Library('shared-library') _
pipeline {
  agent any
  tools{
    maven 'maven'
  }
  stages {
    stage('stage 1') {
      steps {
        mvn --version
        mvn clean install
        helloWorld()
      }
    }
  }
}