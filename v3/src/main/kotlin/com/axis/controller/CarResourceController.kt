package com.axis.controller

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.swagger.annotations.ApiOperation
import org.bson.Document
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class CarResourceController {

    @ApiOperation(value = "Get all car makers")
    @GetMapping("/car/makers")
    fun getCarMakers(): MutableSet<String> {
        var makers = mutableSetOf<String>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("carResource")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    if(doc.get("Make").toString().length!=0)
                        makers.add(doc.get("Make") as String)
                }
            }
        }
        return (makers!!)
    }

    @ApiOperation(value = "Get all models of the car maker")
    @GetMapping("/car/models/{maker}")
    fun getCarModelsForMake(@PathVariable maker: String): MutableSet<String> {
        var models = mutableSetOf<String>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("carResource")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    if(doc.get("Make")==maker) {
                        models.add(doc.get("Model") as String)
                    }
                }
            }
        }
        return (models!!)
    }

    @ApiOperation(value = "Get all variants of car model of car maker")
    @GetMapping("/car/variants/{maker}/{model}")
    fun getCarVariants(@PathVariable maker: String, @PathVariable model: String): MutableSet<String> {
        var variants = mutableSetOf<String>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("carResource")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    if(doc.get("Make")==maker && doc.get("Model")==model) {
                        variants.add(doc.get("Variant") as String)
                    }
                }
            }
        }
        return (variants!!)
    }

}