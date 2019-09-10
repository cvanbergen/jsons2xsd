package com.ethlo.jsons2xsd;

import org.w3c.dom.Document;
import java.io.*;
import static java.nio.charset.StandardCharsets.*;

public class Program {

    public static void main(String[] args) {
        MyArguments myArguments = new MyArguments(args);
        if (myArguments.hasErrors()) {
            System.out.println(myArguments.errorMessage);
            System.out.println(myArguments.showHelp());
            System.exit(1);
        }

        final Document jsonSchemaDocument = readDocument(myArguments.getJsonSchemaPath(),
                myArguments.getTargetNamespace(),
                myArguments.getRootName());

        final String contentToWrite = modifyDocumentToString(jsonSchemaDocument);

        writeDocument(myArguments.getXsdOutputPath(), contentToWrite);

    }

    private static String modifyDocumentToString(Document document) {
        String xsdResult = null;
        try {
            xsdResult = XmlUtil.asXmlString(document.getDocumentElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] ptext = xsdResult.getBytes(ISO_8859_1);
        String contentToWrite = new String(ptext, UTF_8);
        return contentToWrite.replace("»", "").replace("«", "_");
    }

    private static void writeDocument(String path, String content) {
        System.out.println("Trying to write file: " + path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document readDocument(String path, String targetNamespace, String name) {
        System.out.println("Trying to open file: " + path);
        try (final Reader r = new FileReader(new File(path))) {
            final Config cfg = new Config.Builder()
                    .createRootElement(true)
                    .targetNamespace(targetNamespace)
                    .name(name)
                    .build();
            return Jsons2Xsd.convert(r, cfg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}