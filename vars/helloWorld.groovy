@Grab(group='com.couchbase.client', module='java-client', version='3.4.1')
import static com.couchbase.client.java.kv.MutateInSpec.insert;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;
import java.time.Duration;

def call(String username, String password, String connectstring, String name, String actions){
  println(username + " x " + password + " x " + connectstring + " x " + name + " x " +actions)
  sh "echo Hello World"
  Cluster cluster = Cluster.connect("couchbases://" + connectstring, username, password);
  Bucket bucket = cluster.bucket("qe24_status_sarthak");
  bucket.waitUntilReady(Duration.ofSeconds(120));
  Scope scope = bucket.scope("_default");
  Collection collection = scope.collection("_default");

  if(actions=="checkname"){
    try{
      sh 'echo in checkname'
      result = collection.get(name)
      println("Found")
      return true
    }catch (Exception ex){
      JsonObject env = JsonObject.create().put(key , "STARTED").put("latest", false);
      JsonObject content = JsonObject.create().put("AMI", name ).put(env as String, env);
      MutationResult insertResult = collection.insert(name , content);
      println("Doc Created")
      return False
    }
  }else if(actions=="update"){
    try{
      collection.mutateIn("hotel_1368", Collections.singletonList(insert(key , value)));
      return "Success"
    }catch (Exception ex){
      return "Failed"
    }
  }else if(actions=="update_latest"){
    try{
      statement = "select AMI from `qe24_status` where" + env + "latest= TRUE and" +  env + ".PIPELINE_STATUS=SUCCESS"
      QueryResult result = cluster.query(statement);

      for (JsonObject row : result.rowsAsObject()) {
        collection.mutateIn(row.AMI, Collections.singletonList(insert(env+".latest", false)));
      }
      collection.mutateIn(row.AMI, Collections.singletonList(insert(env+".latest", true)));
      return true
    }catch (Exception ex){
      print("FALSE")
    }
    return false

  }else if(actions=="get_latest_name"){
    println("")
  }else{
    println("invalid action")
  }
}