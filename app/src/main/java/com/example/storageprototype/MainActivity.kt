package com.example.storageprototype

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset
import java.nio.file.Files.createFile

//import android.support.v7.app.AppCompatActivity;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
          createJsonData()

    }

    private fun createJsonData() {
        var json = JSONObject()

        val config = Config("BEA","1.30","WS_","012345678",
            "2021-11-09T11:17:01Z",2,1,"41.4033,02.17403")
                  json.put("config",addConfig(config))
//        json.put("Device",2)
//        json.put("Parameters",JSONArray()
//            .put("WID")
//            .put("ORA"))
        val parameters = arrayListOf<Parameter>(
            Parameter("U01",1,25),
            Parameter("U02",0,15),
            Parameter("U03",1,255),
            Parameter("U04",1,125),
            Parameter("U05",1,5)

        )
        json.put("parameters",addconfig(parameters))
        saveJson(json.toString())
    }

    private fun addConfig(config: Config): JSONObject {

        return  JSONObject()
            .put("Company",config.Company)
            .put("FP_version",config.FP_version)
            .put("Family",config.Family)
            .put("CAN_ID",config.CAN_ID)
            .put("DT_UTC",config.DT_UTC)
            .put("user",config.user)
            .put("unit",config.unit)
            .put("DD_localization",config.DD_localisation)
    }

    private fun addconfig(parameters: ArrayList<Parameter>): JSONArray{
        var parJson = JSONArray()
        parameters.forEach {
            parJson.put(
                JSONArray()
                    .put(it.id)
                    .put(it.restore)
                    .put(it.value)
            )
        }
        return parJson
    }
    }
    private fun saveJson(jsonString: String) {

        val output : Writer
        val file = createFile()
        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
    }
    private fun createFile(): File {
        val fileName = "myJson"
        val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName)
//        if (storageDir != null) {
//            if (!storageDir.exists()) {
//                storageDir.mkdir()
//            }

        if (!storageDir.exists()){
            storageDir.mkdir()

        }
        return File.createTempFile(
            fileName, ".json", storageDir
        )

    }






