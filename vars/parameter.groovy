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
        helloWorld()
      }
    }
  }
}