from datetime import timedelta

from couchbase.auth import PasswordAuthenticator
from couchbase.options import ClusterOptions, ClusterTimeoutOptions
from couchbase.cluster import Cluster, QueryOptions
import couchbase.subdocument as SD
from couchbase.exceptions import QueryIndexAlreadyExistsException, TimeoutException
from couchbase.durability import (Durability, ServerDurability,
                                  ClientDurability, ReplicateTo, PersistTo)
from couchbase.collection import (
    InsertOptions,
    ReplaceOptions,
    UpsertOptions,
    GetOptions,
    RemoveOptions,
    IncrementOptions,
    DecrementOptions)

import argparse
import json


class updateAMI:

    def __init__(self):
        parser = argparse.ArgumentParser()
        parser.add_argument("-u", "--username", help="Couchbase Server Cluster Username")
        parser.add_argument("-p", "--password", help="Couchbase Server Cluster Password")
        parser.add_argument("-c", "--connectstr", help="Couchbase Server Connection String")
        parser.add_argument("-m", "--name", help="Name of AMI")
        parser.add_argument("-k", "--key", help="Key to be added")
        parser.add_argument("-v", "--value", help="Value to be added")
        parser.add_argument("-e", "--env", help="Pipeline Environment", default="dev")
        parser.add_argument("-a", "--action",
                            choices=["checkname", "update", "update_latest", "get_latest_name"],
                            help="Choose an action to be performed. Valid actions : checkname, update, latest, last",
                            default="checkname")

        args = parser.parse_args()
        self.username = args.username
        self.password = args.password
        self.action = args.action
        self.connectstring = args.connectstr
        self.name = args.name
        self.key = args.key
        self.value = args.value
        self.env = args.env

        timeout_opts = ClusterTimeoutOptions(kv_timeout=timedelta(seconds=120), query_timeout=timedelta(seconds=10), connect_timeout=timedelta(seconds=30))
        auth = PasswordAuthenticator(self.username, self.password)
        self.cluster = Cluster(self.connectstring, ClusterOptions(auth, timeout_options=timeout_opts))
        self.cb = self.cluster.bucket("qe24_status")
        self.coll = self.cb.scope("_default").collection("_default")

    def checkname(self):
        try:
            result = self.coll.get(self.name)
            print("Found")
            return True
        except Exception as e:
            document = {"AMI" : self.name, self.env : {self.key : "STARTED", "latest" : False}}
            result = self.coll.insert(self.name, document)
            print("Doc Created")
            return False

    def updateDoc(self):
        try:
            self.coll.mutate_in(self.name, [SD.upsert(self.key, self.value)])
            return "Success"
        except Exception as e:
            return "Failed"

    def updateLatest(self):
        try:
            result = self.cluster.query(
                "select AMI from `qe24_status` where {env}.latest=$1 and {env}.PIPELINE_STATUS=$2".format(env=self.env),
                True, "SUCCESS"
            )
            for entry in result:
                self.coll.mutate_in(entry["AMI"], [SD.upsert("{}.latest".format(self.env), False)])
            self.coll.mutate_in(self.name, [SD.upsert("{}.latest".format(self.env), True)])
            return True
        except Exception:
            print("FALSE")
            return False

    def getLatestName(self):
        try:
            result = self.cluster.query(
                "select AMI from `qe24-status` where {env}.latest=$1 and {env}.PIPELINE_STATUS=$2".format(env=self.env),
                True, "SUCCESS"
            )
            for entry in result:
                print(entry["AMI"])
        except Exception:
            print("Latest cannot be found")
        return True

if __name__ == '__main__':
    update_ami = updateAMI()

    if update_ami.action == "checkname":
        update_ami.checkname()
    elif update_ami.action == "update":
        update_ami.updateDoc()
    elif update_ami.action == "update_latest":
        update_ami.updateLatest()
    elif update_ami.action == "get_latest_name":
        update_ami.getLatestName()
    else:
        print("invalid action")