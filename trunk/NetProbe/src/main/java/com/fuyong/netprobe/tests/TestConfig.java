package com.fuyong.netprobe.tests;

import com.fuyong.netprobe.common.Log;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Visitor;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: democrazy
 * Date: 13-7-20
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
public class TestConfig {
    public static final String TAG_WEB_BROWSE = "web-browse";
    public static final String TAG_VIDEO = "video";
    public static final String TAG_HTTP_DOWNLOAD = "http-download";
    public static final String TAG_VOICE = "voice";

    String filePath;
    List<Test> testList = new ArrayList<Test>();
    private SAXReader reader = new SAXReader();
    private Document document;

    public List<Test> getTestList() {
        return testList;
    }

    public boolean load(String filePath) {
        if (null == filePath) {
            return false;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        this.filePath = filePath;
        try {
            document = reader.read(file);
            return true;
        } catch (DocumentException e) {
            Log.getLogger(Log.MY_APP).error("parse xml error:" + filePath);
            return false;
        }
    }

    public boolean load(InputStream in) {
        try {
            document = reader.read(in);
            return true;
        } catch (DocumentException e) {
            Log.getLogger(Log.MY_APP).error("parse xml error:" + filePath);
            return false;
        }
    }

    public void setVisitor(Visitor visitor) {
        document.accept(visitor);
    }

    public void parse() {
        testList.clear();
        Element root = document.getRootElement();
        if (!root.getName().equals("test")) {
            return;
        }
        for (Iterator iter = root.elementIterator(); iter.hasNext(); ) {
            Element element = (Element) iter.next();
            if (element.getName().equals(TAG_VOICE)) {
                VoiceTest voiceTest = new VoiceTest();
                voiceTest.config(element);
                testList.add(voiceTest);
            } else if (element.getName().equals(TAG_WEB_BROWSE)) {
                WebBrowseTest webBrowseTest = new WebBrowseTest();
                webBrowseTest.config(element);
                testList.add(webBrowseTest);
            } else if (element.getName().equals(TAG_VIDEO)) {
                HTTPVideoTest HTTPVideoTest = new HTTPVideoTest();
                HTTPVideoTest.config(element);
                testList.add(HTTPVideoTest);
            } else if (element.getName().equals(TAG_HTTP_DOWNLOAD)) {
                HttpDownloadTest httpDownloadTest = new HttpDownloadTest();
                httpDownloadTest.config(element);
                testList.add(httpDownloadTest);
            }
        }
    }

    public void save() {
        Writer writer = null;
        XMLWriter xmlWriter = null;
        try {
            writer = new FileWriter(filePath);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document);
        } catch (IOException e) {
            Log.e("TestConfig", e);
        } finally {
            if (null != xmlWriter) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    Log.e("TestConfig", e);
                }
            }
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.e("TestConfig", e);
                }
            }
        }
    }

    public Element getRoot() {
        return document.getRootElement();
    }
}
