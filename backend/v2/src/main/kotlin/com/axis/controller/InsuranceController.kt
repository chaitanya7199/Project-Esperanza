package com.axis.controller

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.swagger.annotations.ApiOperation
import org.bson.Document
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000", "http://localhost:3001")
//@RequestMapping("/esperanza")
@RestController
class InsuranceController {

    @ApiOperation(value = "Get quote urls from all partners for a product")
    @GetMapping("/quotes/{insuranceProductId}")
    fun getQuotesForProduct(@PathVariable insuranceProductId: String): ArrayList<Any> {
        val quotes = ArrayList<Any>()
        var endPoints = arrayListOf<String>()
        var endpoint: String = ""
        var product: String = ""
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection1: MongoCollection<org.bson.Document> = database.getCollection("products")
            collection1.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (doc.get("productID").toString().contains(insuranceProductId)) {
                        product = doc.get("id").toString()
                        break
                    }
                }
            }
            println(product)
            val collection2: MongoCollection<org.bson.Document> = database.getCollection("partners")
            collection2.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    endPoints = doc.get("apiEndpoints") as ArrayList<String>
                    for(endPoint in endPoints) {
                        if(endPoint.contains(product)) {
                            quotes.add(endPoint)
                        }
                    }
                }
            }
        }
        return (quotes!!)
    }
}