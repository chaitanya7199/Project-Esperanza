package com.axis.controller

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.swagger.annotations.ApiOperation
import org.bson.Document
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class UserController {

    @ApiOperation(value = "Add user")
    @PostMapping("/users")
    fun addUser(@RequestBody user: String): Document {
        val parser = JSONParser()
        val jsonPartner = parser.parse(user) as JSONObject
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("users")
            var doc: Document = Document.parse(jsonPartner.toString())
            collection.insertOne(doc)
            return doc
        }
    }
}