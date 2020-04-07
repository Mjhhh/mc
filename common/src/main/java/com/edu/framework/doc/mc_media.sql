/*
 Navicat Premium Data Transfer

 Source Server         : localhost_27017
 Source Server Type    : MongoDB
 Source Server Version : 40203
 Source Host           : localhost:27017
 Source Schema         : mc_media

 Target Server Type    : MongoDB
 Target Server Version : 40203
 File Encoding         : 65001

 Date: 07/04/2020 22:26:34
*/


// ----------------------------
// Collection structure for media_file
// ----------------------------
db.getCollection("media_file").drop();
db.createCollection("media_file");

// ----------------------------
// Documents of media_file
// ----------------------------
db.getCollection("media_file").insert([ {
    _id: "c5c75d70f382e6016d2f506d134eee11",
    fileName: "c5c75d70f382e6016d2f506d134eee11.avi",
    fileOriginalName: "lucene.avi",
    filePath: "c/5/c5c75d70f382e6016d2f506d134eee11/",
    fileUrl: "c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("52553728"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-03-25T09:35:55.458Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "c5c75d70f382e6016d2f506d134eee11_00000.ts",
            "c5c75d70f382e6016d2f506d134eee11_00001.ts",
            "c5c75d70f382e6016d2f506d134eee11_00002.ts",
            "c5c75d70f382e6016d2f506d134eee11_00003.ts",
            "c5c75d70f382e6016d2f506d134eee11_00004.ts",
            "c5c75d70f382e6016d2f506d134eee11_00005.ts",
            "c5c75d70f382e6016d2f506d134eee11_00006.ts",
            "c5c75d70f382e6016d2f506d134eee11_00007.ts",
            "c5c75d70f382e6016d2f506d134eee11_00008.ts",
            "c5c75d70f382e6016d2f506d134eee11_00009.ts",
            "c5c75d70f382e6016d2f506d134eee11_00010.ts",
            "c5c75d70f382e6016d2f506d134eee11_00011.ts",
            "c5c75d70f382e6016d2f506d134eee11_00012.ts",
            "c5c75d70f382e6016d2f506d134eee11_00013.ts",
            "c5c75d70f382e6016d2f506d134eee11_00014.ts",
            "c5c75d70f382e6016d2f506d134eee11_00015.ts",
            "c5c75d70f382e6016d2f506d134eee11_00016.ts",
            "c5c75d70f382e6016d2f506d134eee11_00017.ts",
            "c5c75d70f382e6016d2f506d134eee11_00018.ts",
            "c5c75d70f382e6016d2f506d134eee11_00019.ts",
            "c5c75d70f382e6016d2f506d134eee11_00020.ts",
            "c5c75d70f382e6016d2f506d134eee11_00021.ts",
            "c5c75d70f382e6016d2f506d134eee11_00022.ts",
            "c5c75d70f382e6016d2f506d134eee11_00023.ts",
            "c5c75d70f382e6016d2f506d134eee11_00024.ts",
            "c5c75d70f382e6016d2f506d134eee11_00025.ts",
            "c5c75d70f382e6016d2f506d134eee11_00026.ts",
            "c5c75d70f382e6016d2f506d134eee11_00027.ts",
            "c5c75d70f382e6016d2f506d134eee11_00028.ts",
            "c5c75d70f382e6016d2f506d134eee11_00029.ts",
            "c5c75d70f382e6016d2f506d134eee11_00030.ts",
            "c5c75d70f382e6016d2f506d134eee11_00031.ts",
            "c5c75d70f382e6016d2f506d134eee11_00032.ts",
            "c5c75d70f382e6016d2f506d134eee11_00033.ts",
            "c5c75d70f382e6016d2f506d134eee11_00034.ts",
            "c5c75d70f382e6016d2f506d134eee11_00035.ts",
            "c5c75d70f382e6016d2f506d134eee11_00036.ts",
            "c5c75d70f382e6016d2f506d134eee11_00037.ts",
            "c5c75d70f382e6016d2f506d134eee11_00038.ts",
            "c5c75d70f382e6016d2f506d134eee11_00039.ts",
            "c5c75d70f382e6016d2f506d134eee11_00040.ts",
            "c5c75d70f382e6016d2f506d134eee11_00041.ts",
            "c5c75d70f382e6016d2f506d134eee11_00042.ts",
            "c5c75d70f382e6016d2f506d134eee11_00043.ts",
            "c5c75d70f382e6016d2f506d134eee11_00044.ts",
            "c5c75d70f382e6016d2f506d134eee11_00045.ts",
            "c5c75d70f382e6016d2f506d134eee11_00046.ts",
            "c5c75d70f382e6016d2f506d134eee11_00047.ts",
            "c5c75d70f382e6016d2f506d134eee11_00048.ts",
            "c5c75d70f382e6016d2f506d134eee11_00049.ts",
            "c5c75d70f382e6016d2f506d134eee11_00050.ts",
            "c5c75d70f382e6016d2f506d134eee11_00051.ts",
            "c5c75d70f382e6016d2f506d134eee11_00052.ts",
            "c5c75d70f382e6016d2f506d134eee11_00053.ts",
            "c5c75d70f382e6016d2f506d134eee11_00054.ts",
            "c5c75d70f382e6016d2f506d134eee11_00055.ts",
            "c5c75d70f382e6016d2f506d134eee11_00056.ts",
            "c5c75d70f382e6016d2f506d134eee11_00057.ts",
            "c5c75d70f382e6016d2f506d134eee11_00058.ts",
            "c5c75d70f382e6016d2f506d134eee11_00059.ts",
            "c5c75d70f382e6016d2f506d134eee11_00060.ts",
            "c5c75d70f382e6016d2f506d134eee11_00061.ts",
            "c5c75d70f382e6016d2f506d134eee11_00062.ts",
            "c5c75d70f382e6016d2f506d134eee11_00063.ts"
        ]
    },
    companyId: "297ec72c710172200171017561c60000",
    _class: "com.edu.framework.domain.media.MediaFile"
} ]);
db.getCollection("media_file").insert([ {
    _id: "5fbb79a2016c0eb609ecd0cd3dc48016",
    fileName: "5fbb79a2016c0eb609ecd0cd3dc48016.avi",
    fileOriginalName: "solr.avi",
    filePath: "5/f/5fbb79a2016c0eb609ecd0cd3dc48016/",
    fileUrl: "5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8",
    fileType: "avi",
    mimeType: "video/avi",
    fileSize: NumberLong("36413952"),
    fileStatus: "301002",
    uploadTime: ISODate("2020-03-25T09:36:03.209Z"),
    processStatus: "303002",
    "mediaFileProcess_m3u8": {
        tslist: [
            "5fbb79a2016c0eb609ecd0cd3dc48016_00000.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00001.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00002.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00003.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00004.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00005.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00006.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00007.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00008.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00009.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00010.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00011.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00012.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00013.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00014.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00015.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00016.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00017.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00018.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00019.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00020.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00021.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00022.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00023.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00024.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00025.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00026.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00027.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00028.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00029.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00030.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00031.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00032.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00033.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00034.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00035.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00036.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00037.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00038.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00039.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00040.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00041.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00042.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00043.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00044.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00045.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00046.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00047.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00048.ts",
            "5fbb79a2016c0eb609ecd0cd3dc48016_00049.ts"
        ]
    },
    companyId: "297ec72c710172200171017561c60000",
    _class: "com.edu.framework.domain.media.MediaFile"
} ]);
