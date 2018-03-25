package com.frankxulei.mongodb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/*
 * 《Mongodb实战 》 第2版本 译者 Java和C#实战例子代码。
 * 中国MongoDB学习交流群 511943641。
 * 微软&Java实战训练营QQ群 203822816
 *  徐雷 Frank Xu Lei
 */
public class App {
	public static void main(String[] args) {
		MongoClient mongoClient = null;
		try {
			// 获取链接 Get MongoClient Connect to MongoDB
			mongoClient = new MongoClient("localhost", 27017);
			// 获取数据库 Get database
			MongoDatabase db = mongoClient.getDatabase("taobao");
			// 获取集合 Get collection / table from 'orders'
			// If collection doesn't exists, MongoDB will create it for you
			// MongoCollection<Order> collection = db.getCollection("orders",
			// Order.class);
			MongoCollection<Document> collection = db.getCollection("orders");
			// 插入 Insert a new document into MongoDB
			System.out.println("\n********MondoDB实战  插入 - Insert operation ******\n");
			insert(collection);
			find(collection);

			// Update a document
			System.out.println("\n********MondoDB实战   更新- Update operation ******\n");
			update(collection);
			find(collection);

			System.out.println("\n********MondoDB实战   删除- Delete operation ******\n");
			collection.deleteOne(new Document("id", "1"));
			find(collection);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}

	private static void insert(MongoCollection<Document> Collection) {
		for (int i = 0; i < 10000; i++) {
			Document document = new Document("title", "Mongodb实战 第二版 NoSQL排名第一 ").append("name", "徐雷FrankXuLei")
					.append("mobile", "").append("qq", 40334120).append("price", 888 * i)
					.append("address", "16期Mongodb实战训练营 上海交大科技园");

			Collection.insertOne(document);

		}
	}

	/**
	 * 序列化保存 订单信息
	 * 
	 * @param Collection
	 * @throws IOException 
	 */
	private void saveEntity(Order order, MongoCollection<DBObject> Collection) throws IOException {
		DBObject dbo = new BasicDBObject();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(outputStream);
		out.writeObject(order);
		dbo.put("Order", outputStream.toByteArray());
		out.close();
		outputStream.close();
		Collection.insertOne(dbo);
	}

	/**
	 * 反序列化获取对象
	 * 
	 * @param order
	 * @param Collection
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private Order getEntity(MongoCollection<DBObject> Collection) throws IOException, ClassNotFoundException {
		DBObject object = (DBObject) Collection.find(new Document("id", "1"));
		byte[] javaObjectByte = (byte[]) object.get("JavaObject");
		InputStream inputStream = new ByteArrayInputStream(javaObjectByte);
		ObjectInputStream in = new ObjectInputStream(inputStream);
		Order order = (Order) in.readObject();
		in.close();
		inputStream.close();
        return order;
	}

	private static void update(MongoCollection<Document> Collection) {
		Collection.updateOne(new Document("id", "1"),
				new Document("$set", new Order(1, "徐雷", "mongodb实战 第二版", "上海交大科技园", 65)).append("$currentDate",
						new Document("lastModified", true)));
	}

	private static void find(MongoCollection<Document> collection) {
		final FindIterable<Document> itr = collection.find();
		final MongoCursor<Document> mongoItr = itr.iterator();
		System.out.println("Mongdo实战订单详情:");
		System.out.println("====================");
		while (mongoItr.hasNext()) {
			Document order = mongoItr.next();
			System.out.println(order);
		}
	}
}
