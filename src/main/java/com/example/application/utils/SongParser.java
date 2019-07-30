package com.example.application.utils;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


//        String[] metadataNames = metadata.names();
//
//                for (String name : metadataNames) {
//                System.out.println(name + ": " + metadata.get(name));
//                }

//       System.out.println("----------------------------------------------");
//       System.out.println("Title: " + metadata.get("title"));
//       System.out.println("Artists: " + metadata.get("xmpDM:artist"));
//       System.out.println("Composer : "+metadata.get("xmpDM:composer"));
//       System.out.println("Genre : "+metadata.get("xmpDM:genre"));
//       System.out.println("Album : "+metadata.get("xmpDM:album"));


public class SongParser {


    public static Metadata readSong(String fileLocation) throws IOException, TikaException, SAXException {

        InputStream input = new FileInputStream(new File(fileLocation));
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input, handler, metadata, parseCtx);
        input.close();

        return metadata;
    }
}
