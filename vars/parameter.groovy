@Library('cb-bucket-update') _
import static com.couchbase.client.java.kv.MutateInSpec.insert;
import java.time.Duration;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;

pipeline {
  agent any
  stages {
    stage('stage 1') {
      steps {
        sh 'mvn --version'
        sh 'mvn clean install'
        helloWorld()
//        helloWorld(new LinkedHashMap<>([username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe"]))
//        helloWorld(username:"sarthak", password:"Password@123",connectstring:"cb.wey5oeokxs3xxgrd.cloud.couchbase.com",name:"pipe")
//        helloWorld("sarthak","Password@123","cb.wey5oeokxs3xxgrd.cloud.couchbase.com","pipe","checkname")
      }
    }
  }
}