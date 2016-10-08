/*
 * Copyright (c) 2016 Farooq Khan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.jsondb;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import io.jsondb.query.CollectionSchemaUpdate;
import io.jsondb.query.Update;

/**
 * Interface that defines the methods available in JsonDBTemplate
 *
 * @author Farooq Khan
 * @version 1.0 21 Aug 2016
 */
public interface JsonDBOperations {

  /**
   * Re-load the collections from dblocation folder.
   * This functionality is useful if you some other process is going to directly update
   * the collection files in dblocation
   */
  void reLoadDB();

  /**
   * Reloads a particular collection from dblocation directory
   * @param collectionName name of the collection to reload
   */
  void reloadCollection(String collectionName);

  /**
   * Shuts down all event system in Json DB
   */
  void shutdown();

  /**
   * adds a CollectionFileChangeListener to db.
   * @param listener actual listener to add
   */
  void addCollectionFileChangeListener(CollectionFileChangeListener listener);

  /**
   * removes a previously added CollectionFileChangeListener
   * @param listener actual listener to remove
   */
  void removeCollectionFileChangeListener(CollectionFileChangeListener listener);

  /**
   * Create an uncapped collection with a name based on the provided entity class.
   *
   * @param entityClass class that determines the collection to create
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void createCollection(Class<T> entityClass);

  /**
   * Create an uncapped collection with the provided name.
   *
   * @param collectionName name of the collection
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void createCollection(String collectionName);

  /**
   * Drop the collection with the name indicated by the entity class.
   *
   * @param entityClass class that determines the collection to drop/delete.
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void dropCollection(Class<T> entityClass);

  /**
   * Drop the collection with the given name.
   *
   * @param collectionName name of the collection to drop/delete.
   */
  void dropCollection(String collectionName);

  <T> void updateCollectionSchema(CollectionSchemaUpdate update, Class<T> entityClass);

  /**
   * Update a collection as per the specified CollectionUpdate param.
   * This method is only available by collectionName and not by Class.
   *
   * @param update how to update the Collection
   * @param collectionName  name of the collection to update schema for
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void updateCollectionSchema(CollectionSchemaUpdate update, String collectionName);

  /**
   * A set of collection names.
   *
   * @return list of collection names
   */
  Set<String> getCollectionNames();

  /**
   * The collection name used for the specified class by this template.
   *
   * @param entityClass must not be {@literal null}.
   * @return name of the collection
   */
  String getCollectionName(Class<?> entityClass);

  /**
   * Get a collection by name, creating it if it doesn't exist.
   * The returned collection will be a new copy of the existing collection
   * Modifying its contents will not modify the contents of collection in JsonDB memory.
   *
   * @param entityClass class that determines the name of the collection
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return A copy of the existing collection or a newly created one.
   */
  <T> List<T> getCollection(Class<T> entityClass);

  /**
   * Check to see if a collection with a name indicated by the entity class exists.
   *
   * @param entityClass class that determines the name of the collection
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return true if a collection with the given name is found, false otherwise.
   */
  <T> boolean collectionExists(Class<T> entityClass);

  /**
   * Check to see if a collection with a given name exists.
   *
   * @param collectionName name of the collection
   * @return true if a collection with the given name is found, false otherwise.
   */
  boolean collectionExists(String collectionName);

  /**
   * is a collection readonly,
   * A collection can be readonly if its schema version does not match the actualSchema version
   *
   * @param entityClass class that determines the collection
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return true if collection is readonly
   */
  <T> boolean isCollectionReadonly(Class<T> entityClass);

  <T> boolean isCollectionReadonly(String collectionName);

  /**
   * Map the results of an ad-hoc query on the collection for the entity class to a List of the specified type.
   *
   * @param jxQuery a XPATH query expression
   * @param entityClass the parameterized type of the returned list.
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return the list of found objects
   */
  <T> List<T> find(String jxQuery, Class<T> entityClass);

  /**
   * Map the results of an ad-hoc query on the specified collection to a List of the specified type.
   *
   * @param jxQuery a XPATH query expression
   * @param collectionName name of the collection to retrieve the objects from
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return the list of found objects
   */
  <T> List<T> find(String jxQuery, String collectionName);

  /**
   * Query for a list of objects of type T from the specified collection.
   *
   * @param entityClass the parameterized type of the returned list.
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return the found collection
   */
  <T> List<T> findAll(Class<T> entityClass);

  /**
   * Query for a list of objects of type T from the specified collection.
   *
   * @param collectionName name of the collection to retrieve the objects from
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return the found collection
   */
  <T> List<T> findAll(String collectionName);

  /**
   * Returns a document with the given id mapped onto the given class. The collection the query is ran against will be
   * derived from the given target class as well.
   *
   * @param id the id of the document to return.
   * @param entityClass the type the document shall be converted into.
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return the document with the given id mapped onto the given target class.
   */
  <T> T findById(Object id, Class<T> entityClass);

  /**
   * Returns the document with the given id from the given collection mapped onto the given target class.
   *
   * @param id the id of the document to return
   * @param collectionName the collection to query for the document
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   * @return object searched within the collection
   */
  <T> T findById(Object id, String collectionName);

  <T> T findOne(String jxQuery, Class<T> entityClass);
  <T> T findOne(String jxQuery, String collectionName);

  /**
   * Insert the object into correct collection. The collection type of the object is automatically determined.
   *
   * Insert is used to initially store the object into the database. To update an existing object use the save method.
   *
   * @param objectToSave the object to store in the collection
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void insert(Object objectToSave);

  /**
   * Insert the object into the specified collection.
   *
   * Insert is used to initially store the object into the database. To update an existing object use the save method.
   *
   * @param objectToSave the object to store in the collection
   * @param collectionName name of the collection to store the object in
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void insert(Object objectToSave, String collectionName);

  /**
   * Insert a Collection of objects into a collection in a single batch write to the database.
   *
   * @param batchToSave  the list of objects to save.
   * @param entityClass  class that determines the collection to use
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void insert(Collection<? extends T> batchToSave, Class<T> entityClass);

  /**
   * Insert a Collection of objects into a collection in a single batch write to the database.
   *
   * @param batchToSave  the list of objects to save.
   * @param collectionName  name of the collection to store the object in
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void insert(Collection<? extends T> batchToSave, String collectionName);

  /**
   * Save the object to the collection for the entity type of the object to save.
   * This will throw a exception if the object is not already present.
   * This is a not same as MongoDB behaviour
   *
   * @param objectToSave  the object to store in the collection
   * @param entityClass  class that determines the collection to use
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void save(Object objectToSave, Class<T> entityClass);

  /**
   * Save the object to the collection for the entity type of the object to save.
   * This will throw a exception if the object is not already present.
   * This is a not same as MongoDB behaviour
   *
   * @param objectToSave  the object to store in the collection
   * @param collectionName  name of the collection to store the object in
   * @param <T> Type annotated with {@link io.jsondb.annotation.Document} annotation
   *            and member of the baseScanPackage
   */
  <T> void save(Object objectToSave, String collectionName);

  <T> int findAndRemove(String jxQuery, Class<T> entityClass);
  <T> int findAndRemove(String jxQuery, Class<T> entityClass, String collectionName);

  <T> int remove(Object object, Class<T> entityClass);
  <T> int remove(Object object, String collectionName);

  <T> int remove(Collection<? extends T> batchToRemove, Class<T> entityClass);
  <T> int remove(Collection<? extends T> batchToRemove, String collectionName);

  <T> int findAndModify(String jxQuery, Update update, Class<T> entityClass);
  <T> int findAndModify(String jxQuery, Update update, String collectionName);



  <T> void upsert(Object objectToSave);
  <T> void upsert(Object objectToSave, String collectionName);

  <T> void upsert(Collection<? extends T> batchToSave, Class<T> entityClass);
  <T> void upsert(Collection<? extends T> batchToSave, String collectionName);

  /**
   * This method backs up JSONDB collections to specified backup path
   *
   * @param backupPath location at which to backup the database contents
   */
  void backup(String backupPath);

  /**
   * This method restores JSONDB collections from specified restore path.
   * if merge flag is set to true restore operation will merge collections from restore location
   * and if it is set to false it will replace existing collections with collections being
   * restored
   *
   * @param restorePath path were backup jsondb files are present
   * @param merge whether to merge data from restore location
   */
  void restore(String restorePath, boolean merge);
}