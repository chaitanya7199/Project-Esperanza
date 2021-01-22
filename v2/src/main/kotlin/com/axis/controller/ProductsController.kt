package com.axis.controller

import com.mongodb.DBObject
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.swagger.annotations.ApiOperation
import org.bson.BSONObject
import org.bson.Document
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3001", "http://localhost:3000")
//@RequestMapping("/esperanza")
@RestController
class ProductsController {

    @Autowired
    private val formsController: FormsController? = null

    @Autowired
    private val endPointController: EndPointController? = null

    @CrossOrigin("http://localhost:3001")
    @ApiOperation(value = "Get all the products")
    @GetMapping("/products")
    //getting data from mongo
    fun getProducts(): ArrayList<*> {
        val products = ArrayList<Any>()
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("products")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (products != null) {
                        products.add(doc)
                    }
                }
            }
        }
        return (products!!)
    }

    @CrossOrigin("http://localhost:3001")
    @ApiOperation(value = "Get a product by Id")
    @GetMapping("/products/{productId}")
    fun getProductById(@PathVariable productId: String): Document? {
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("products")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (doc.get("productID").toString()?.equals(productId)) {
                        return doc
                    }
                }
            }
        }
        return null
    }

    @CrossOrigin("http://localhost:3001")
    @ApiOperation(value = "Get a product by title")
    @GetMapping("/products/by/{title}")
    fun getProductByTitle(@PathVariable title: String): Document? {
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("products")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    doc.remove("_id")
                    if (doc.get("title").toString()?.equals(title)) {
                        return doc
                    }
                }
            }
        }
        return null
    }

    @ApiOperation(value = "Add new products")
    @PostMapping("/products")
    fun addProducts(@RequestBody partner: String): Document {
        val parser = JSONParser()
        val jsonPartner = parser.parse(partner) as JSONObject
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<org.bson.Document> = database.getCollection("products")
            var doc: Document = Document.parse(jsonPartner.toString())
            collection.insertOne(doc)
            return doc
        }
    }

    @ApiOperation(value = "Update product details")
    @PutMapping("/products")
    fun updateProduct(@RequestBody productDetails: String) {
        val parser = JSONParser()
        val productDetails = parser.parse(productDetails) as JSONObject
        val productID = productDetails.get("productID")
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("products")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val productDoc = cur.next()
                    val updatedDoc: Document = Document.parse(productDetails.toString())
                    if (productDoc.get("productID").toString()?.equals(productID)) {
                        println(productID)
                        collection.findOneAndReplace(productDoc, updatedDoc)
                        //collection.updateOne(productDoc, updatedDoc)
                        break
                    }
                    else {
                        println("No product")
                    }
                }
            }
        }
    }

    @ApiOperation(value = "Delete a product")
    @DeleteMapping("/products")
    fun deleteProduct(@RequestBody productDetails: String) {
        val parser = JSONParser()
        val productDetails = parser.parse(productDetails) as JSONObject
        val productID = productDetails.get("productID")
        MongoClients.create("mongodb://localhost:27017").use { mongoClient ->
            println(productID)
            val database = mongoClient.getDatabase("axisdb")
            val collection: MongoCollection<Document> = database.getCollection("products")
            collection.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val productDoc = cur.next()
                    productDoc.remove("_id")
                    if (productDoc.get("productID").toString()?.equals(productID)) {
                        println(productID)
                        formsController?.deleteFormForAProduct(productID.toString())
                        endPointController?.deleteEndPointsForAProduct(productID.toString())
                        collection.deleteOne(productDoc)
                        break
                    }
                    else {
                        println("No product")
                    }
                }
            }
        }
    }
}