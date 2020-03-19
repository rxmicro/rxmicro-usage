db.account.insert({
  "_id": NumberLong(1),
  "email": "richard.hendricks@piedpiper.com",
  "firstName": "Richard",
  "lastName": "Hendricks",
  "balance": NumberDecimal("70000.00"),
  "role": "CEO"
})
db.account.insert({
  "_id": NumberLong(2),
  "email": "bertram.gilfoyle@piedpiper.com",
  "firstName": "Bertram",
  "lastName": "Gilfoyle",
  "balance": NumberDecimal("20000.00"),
  "role": "Systems_Architect"
})
db.account.insert({
  "_id": NumberLong(3),
  "email": "dinesh.chugtai@piedpiper.com",
  "firstName": "Dinesh",
  "lastName": "Chugtai",
  "balance": NumberDecimal("10000.00"),
  "role": "Lead_Engineer"
})
db.account.insert({
  "_id": NumberLong(4),
  "email": "carla.walton@piedpiper.com",
  "firstName": "Carla",
  "lastName": "Walton",
  "balance": NumberDecimal("5000.00"),
  "role": "Engineer"
})
db.account.insert({
  "_id": NumberLong(5),
  "email": "sanjay.basu@piedpiper.com",
  "firstName": "Sanjay",
  "lastName": "Basu",
  "balance": NumberDecimal("2000.00"),
  "role": "Engineer"
})
db.account.insert({
  "_id": NumberLong(6),
  "email": "elizabet.kirsipuu@piedpiper.com",
  "firstName": "Elizabet",
  "lastName": "Kirsipuu",
  "balance": NumberDecimal("3000.00"),
  "role": "Engineer"
})

// https://docs.mongodb.com/manual/core/shell-types/
db.supportedTypes.insert({
  "_id" : ObjectId("507f1f77bcf86cd799439011"),
  "status" : "created",
  "statusList" : [ "created" ],
  "aBoolean" : true,
  "booleanList" : [ true ],
  "aByte" : NumberInt(127),
  "byteList" : [ NumberInt(127) ],
  "aShort" : NumberInt(500),
  "shortList" : [ NumberInt(500) ],
  "aInteger" : NumberInt(1000),
  "integerList" : [ NumberInt(1000) ],
  "aLong" : NumberLong(999999999999),
  "longList" : [ NumberLong(999999999999) ],
  "aFloat" : 3.14,
  "floatList" : [ 3.14 ],
  "aDouble" : 3.14,
  "doubleList" : [ 3.14 ],
  "bigDecimal" : NumberDecimal("3.14"),
  "bigDecimalList" : [ NumberDecimal("3.14") ],
  "character" : "y",
  "characterList" : [ "y" ],
  "string" : "string",
  "stringList" : [ "string" ],
  "pattern" : /rxmicro/,
  "patternList" : [ /rxmicro/ ],
  "instant" : ISODate("2020-02-02T02:20:00.000Z"),
  "instantList" : [ ISODate("2020-02-02T02:20:00.000Z") ],
  "localDate" : ISODate("2020-02-02T00:00:00.000Z"),
  "localDateList" : [ ISODate("2020-02-02T00:00:00.000Z") ],
  "localDateTime" : ISODate("2020-02-02T02:20:00.000Z"),
  "localDateTimeList" : [ ISODate("2020-02-02T02:20:00.000Z") ],
  "localTime" : ISODate("1970-01-01T02:20:00.000Z"),
  "localTimeList" : [ ISODate("1970-01-01T02:20:00.000Z") ],
  "uuid" : BinData(03, "Ej5FZ+ibEtOkVlVmQkQAAA=="),
  "uuidList" : [ BinData(03, "Ej5FZ+ibEtOkVlVmQkQAAA==") ],
  "code" : function test(){},
  "codeList" : [ function test(){} ],
  "binary" : BinData(128, "AAECAwQFBgcICQ=="),
  "binaryList" : [ BinData(128, "AAECAwQFBgcICQ==") ]
})
