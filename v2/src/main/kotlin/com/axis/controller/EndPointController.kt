package com.axis.controller

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.swagger.annotations.ApiOperation
import org.bson.Document
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000", "http://localhost:3001")
//@RequestMapping("/esperanza")
@RestController
class EndPointController {
    @ApiOperation(value = "Get all endPoints details")
    @GetMapping("/endPoints")
    fun getAllEndPoints(): ArrayList<*> {
        val endPoints = ArrayList<Any>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("endPoints")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    endPoints.add(doc)
                }
            }
        }
        return (endPoints!!)
    }

    @ApiOperation(value = "Get endpoint for a particular product of a particular partner")
    @GetMapping("/endPoints/{insuranceProductId}/{partnerID}")
    fun getEndpointForProductOfPartner(@PathVariable insuranceProductId: String, @PathVariable partnerID: String): Document? {
        var endPointDoc: Document? = null
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("endPoints")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (doc.get("productID").toString().equals(insuranceProductId) && doc.get("partnerID").toString().equals(partnerID)) {
                        endPointDoc = doc
                        break
                    }
                }
            }
        }
        if(endPointDoc===null) {
            return null
        }
        return (endPointDoc as Document)
    }

    @ApiOperation(value = "Add new endPoints")
    @PostMapping("/endPoints")
    fun addEndPoints(@RequestBody partner: String): Document {
        val parser = JSONParser()
        val jsonPartner = parser.parse(partner) as JSONObject
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("endPoints")
            var doc: Document = Document.parse(jsonPartner.toString())
            collection.insertOne(doc)
            return doc
        }
    }

    @ApiOperation(value = "Get all endpoints for a particular product")
    @GetMapping("/endPoints/{insuranceProductId}")
    fun getAllEndpointsForProduct(@PathVariable insuranceProductId: String): List<Document> {
        var endPointDocs = arrayListOf<Document>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("endPoints")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (doc.get("productID").toString().equals(insuranceProductId)) {
                        endPointDocs.add(doc)
                    }
                }
            }
        }
        return (endPointDocs!!)
    }

    @ApiOperation(value = "Update api config of a product for a partner")
    @PutMapping("/endPoints")
    fun updateFormFieldsForAProduct(@RequestBody endpointDetails: String) {
        val parser = JSONParser()
        val endpointDetails = parser.parse(endpointDetails) as JSONObject
        val productID = endpointDetails.get("productID")
        val partnerID = endpointDetails.get("partnerID")
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("endPoints")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val formsDoc = cur.next()
                    val updatedDoc: Document = Document.parse(endpointDetails.toString())
                    if (formsDoc.get("productID").toString()?.equals(productID) && formsDoc.get("partnerID").toString()?.equals(partnerID)) {
                        println(productID)
                        collection.findOneAndReplace(formsDoc, updatedDoc)
                        //collection.updateOne(productDoc, updatedDoc)
                        break
                    }
                    else {
                        println("No endpoint details found for that product and partner")
                    }
                }
            }
        }
    }

    @ApiOperation(value = "Delete endPoints of a product for a partner")
    @DeleteMapping("/endPoints/{productID}/{partnerID}")
    fun deleteEndPointsOfAProductForAPartner(@PathVariable productID: String, @PathVariable partnerID: String) {
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("endPoints")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val endPointDoc = cur.next()
                    endPointDoc.remove("_id")
                    if (endPointDoc.get("productID").toString()?.equals(productID) && endPointDoc.get("partnerID").toString()?.equals(partnerID)) {
                        collection.deleteOne(endPointDoc)
                    }
                }
            }
        }
    }

    @ApiOperation(value = "Delete endPoints for a product")
    @DeleteMapping("/endPoints")
    fun deleteEndPointsForAProduct(productID: String) {
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("endPoints")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val endPointDoc = cur.next()
                    endPointDoc.remove("_id")
                    if (endPointDoc.get("productID").toString()?.equals(productID)) {
                        collection.deleteOne(endPointDoc)
                    }
                }
            }
        }
    }
}