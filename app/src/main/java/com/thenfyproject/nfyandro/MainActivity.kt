package com.thenfyproject.nfyandro

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    //songbuF.setDataSource("")
    //songbuF.prepare()
    val songbuF = MediaPlayer();
    val songArray = ArrayList<String>()
    var currentSong = 0;
    val fNV = 0;
    val songs = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "songs")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = this@MainActivity

        verifyStoragePermissions(activity);
        setContentView(R.layout.activity_main)

        val a = songs.listFiles()

        a.forEach {
            songArray.add(it.toString());
        }

        val tv2 = findViewById<TextView>(R.id.TVX)

        val tokens = songArray[currentSong].substring(songArray[currentSong].lastIndexOf("/")+1);
        println("P - $tokens")
        tv2.text = "Current Song - $tokens"


    }
    @Suppress("PrivatePropertyName")
    private val REQUEST_EXTERNAL_STORAGE = 1
    @Suppress("PrivatePropertyName")
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )



    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
    fun onClick(v: View?) {

        val b = v as Button



        try {
            songbuF.reset();
            songbuF.setDataSource(songArray[currentSong]);
            songbuF.prepare()
            songbuF.start();
        } catch (e: Exception) {
            println("Errored: ")
            println(e.message)
        }

    }
    fun onStop(v: View?) {
        songbuF.stop();
    }

    fun ClickedChange(v: View?) {
        currentSong += 1
    }
}