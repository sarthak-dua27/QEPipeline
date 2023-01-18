@Library('shared-library') _
pipeline {
  agent any
  stages {
    stage('stage 1') {
      steps {
        helloWorld()
      }
    }
  }
}