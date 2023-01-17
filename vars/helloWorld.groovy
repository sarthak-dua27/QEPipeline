import static com.couchbase.client.java.kv.MutateInSpec.insert;
import java.time.Duration;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;


def call(){
  sh "echo Hello World"
}

//def call(Map config = [:]){
//  sh "echo Hello World"
//  Cluster cluster = Cluster.connect("couchbases://" + config.connectstring, config.username as String, config.password as String);
//  Bucket bucket = cluster.bucket("qe24_status");
//  bucket.waitUntilReady(Duration.ofSeconds(120));
//  Scope scope = bucket.scope("_default");
//  Collection collection = scope.collection("_default");
//
//  if(config.actions=="checkname"){
//    try{
//      result = collection.get(config.name as String)
//      println("Found")
//      return true
//    }catch (Exception ex){
//      JsonObject env = JsonObject.create().put(config.key as String, "STARTED").put("latest", false);
//      JsonObject content = JsonObject.create().put("AMI", config.name as String).put(config.env as String, env);
//      MutationResult insertResult = collection.insert(config.name as String, content);
//      println("Doc Created")
//      return False
//    }
//  }else if(config.actions=="update"){
//    try{
//      collection.mutateIn("hotel_1368", Collections.singletonList(insert(config.key as String, config.value)));
//      return "Success"
//    }catch (Exception ex){
//      return "Failed"
//    }
//  }else if(config.actions=="update_latest"){
//    try{
//      statement = "select AMI from `qe24_status` where" + config.env + "latest= TRUE and" +  config.env ".PIPELINE_STATUS=SUCCESS"
//      QueryResult result = cluster.query(statement);
//
//      for (JsonObject row : result.rowsAsObject()) {
//        collection.mutateIn(row.AMI, Collections.singletonList(insert(config.env+".latest", false)));
//      }
//      collection.mutateIn(row.AMI, Collections.singletonList(insert(config.env+".latest", true)));
//      return true
//    }catch (Exception ex){
//      print("FALSE")
//    }
//    return false
//
//  }else if(config.actions=="get_latest_name"){
//    println("")
//  }else{
//    println("invalid action")
//  }
//}
