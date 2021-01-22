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
class PartnersController {

    @ApiOperation(value = "Get partners details for a particular product")
    @GetMapping("/partners/{insuranceProductId}")
    fun getPartnersForProduct(@PathVariable insuranceProductId: String): ArrayList<*> {
        val partners = ArrayList<Any>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection1: MongoCollection<org.bson.Document> = database.getCollection("endPoints")
            val collection2: MongoCollection<org.bson.Document> = database.getCollection("partners")
            collection1.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc1 = cur.next()
                    doc1.remove("_id")
                    System.out.println(doc1.get("productID").toString().contains(insuranceProductId))
                    if (doc1.get("productID").toString().contains(insuranceProductId) && partners != null) {
                        var partnerID = doc1.get("partnerID")
                        collection2.find().iterator().use { cur ->
                            while (cur.hasNext()) {
                                val doc2 = cur.next()
                                doc2.remove("_id")
                                if (doc2.get("partnerID").toString().equals(partnerID) && partners != null) {
                                    partners.add(doc2)
                                }
                            }
                        }
                    }
                }
            }
        }
        return (partners!!)
    }

    @ApiOperation(value = "Get all partners details")
    @GetMapping("/partners")
    fun getAllPartners(): ArrayList<*> {
        val partners = ArrayList<Any>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("partners")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    partners.add(doc)
                }
            }
        }
        return (partners!!)
    }

    @ApiOperation(value = "Add new partners")
    @PostMapping("/partners")
    fun addPartners(@RequestBody partner: String): Document {
        val parser = JSONParser()
        val jsonPartner = parser.parse(partner) as JSONObject
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("partners")
            var doc: Document = Document.parse(jsonPartner.toString())
            collection.insertOne(doc)
            return doc
        }
    }

    @ApiOperation(value = "Update partner details")
    @PutMapping("/partners")
    fun updatePartner(@RequestBody partnerDetails: String) {
        val parser = JSONParser()
        val partnerDetails = parser.parse(partnerDetails) as JSONObject
        val partnerID = partnerDetails.get("partnerID")
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(partnerID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("partners")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val partnerDoc = cur.next()
                    val updatedDoc: Document = Document.parse(partnerDetails.toString())
                    if (partnerDoc.get("partnerID").toString()?.equals(partnerID)) {
                        println(partnerID)
                        collection.findOneAndReplace(partnerDoc, updatedDoc)
                        //collection.updateOne(productDoc, updatedDoc)
                        break
                    }
                    else {
                        println("No partner found")
                    }
                }
            }
        }
    }
}