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
class FormsController {

    @ApiOperation(value = "Get form details for a particular product")
    @GetMapping("/forms/{insuranceProductId}")
    fun getFormsForProduct(@PathVariable insuranceProductId: String): ArrayList<*> {
        val formFields = ArrayList<Any>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("formFields")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (doc.containsValue(insuranceProductId) && formFields != null) {
                        formFields.add(doc)
                    }
                }
            }
        }
        return (formFields!!)
    }

    @ApiOperation(value = "Add form fields for a product")
    @PostMapping("/formFields")
    fun addFormFields(@RequestBody partner: String): Document {
        val parser = JSONParser()
        val jsonPartner = parser.parse(partner) as JSONObject
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("formFields")
            var doc: Document = Document.parse(jsonPartner.toString())
            collection.insertOne(doc)
            return doc
        }
    }

    @ApiOperation(value = "Update form details for a product")
    @PutMapping("/forms")
    fun updateFormFieldsForAProduct(@RequestBody formFields: String) {
        val parser = JSONParser()
        val formFields = parser.parse(formFields) as JSONObject
        val productID = formFields.get("productID")
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("formFields")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val formsDoc = cur.next()
                    val updatedDoc: Document = Document.parse(formFields.toString())
                    if (formsDoc.get("productID").toString()?.equals(productID)) {
                        println(productID)
                        collection.findOneAndReplace(formsDoc, updatedDoc)
                        //collection.updateOne(productDoc, updatedDoc)
                        break
                    }
                    else {
                        println("No form fields found for that product")
                    }
                }
            }
        }
    }

    @ApiOperation(value = "Delete a form for a product")
    @DeleteMapping("/forms")
    fun deleteFormForAProduct(productID: String) {
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("formFields")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val formFieldsDoc = cur.next()
                    formFieldsDoc.remove("_id")
                    if (formFieldsDoc.get("productID").toString()?.equals(productID)) {
                        collection.deleteOne(formFieldsDoc)
                        break
                    }
                }
            }
        }
    }
}