package com.ethlo.jsons2xsd;

import org.w3c.dom.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.*;

public class Program {

    public static void main (String[] args) {
        MyArguments myArguments= new MyArguments(args);
        if (myArguments.hasErrors()) {
            System.out.println(myArguments.errorMessage);
            System.out.println(myArguments.showHelp());
            System.out.println("thats it");
            System.exit(1);
        }

        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            System.out.print("Geef het pad van het JSON-schema: ");
//            String jsonSchemaPath = null;
//            jsonSchemaPath = reader.readLine();
//            System.out.print("Geef het pad voor het te maken XSD-bestand: ");
//            String xsdOutputPath = reader.readLine();
//            System.out.print("Geef de naam van het hoofdelement [Array]: ");
//            String rootName = reader.readLine();
//            if (rootName.length()==0){ rootName = "Array";}
//            System.out.print("Geef de targetNamespace [http://ethlo.com/schema/array-test-1.0.xsd]: ");
//            String targetNamespace = reader.readLine();
//            if (targetNamespace.length()==0){ targetNamespace = "http://ethlo.com/schema/array-test-1.0.xsd";}

            System.out.println("Trying to open file: " + myArguments.getJsonSchemaPath());
            final Reader r = new FileReader(new File(myArguments.getJsonSchemaPath()));
            final Config cfg = new Config.Builder()
                    .createRootElement(false)
                    .targetNamespace(myArguments.getTargetNamespace())
                    .name(myArguments.getRootName())
                    .build();
            final Document doc = Jsons2Xsd.convert(r, cfg);
            final String xsdResult = XmlUtil.asXmlString(doc.getDocumentElement());//.replace("»", "").replace("«","_");
            r.close();

            byte[] ptext = xsdResult.getBytes(ISO_8859_1);
            String value = new String(ptext, UTF_8);
            value = value.replace("»", "").replace("«","_");

            BufferedWriter writer = new BufferedWriter(new FileWriter(myArguments.getXsdOutputPath()));
            writer.write(value);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
}

}
