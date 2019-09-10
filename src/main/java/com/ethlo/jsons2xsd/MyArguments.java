package com.ethlo.jsons2xsd;

public class MyArguments {
    private boolean _error = false;
    public  String errorMessage = "";
    public MyArguments(String[] args) {
        errorMessage = "";
        try {
            for (int i = 0; i < args.length; i = i + 2) {
                if (args[i].equals("-j")) {
                    _jsonSchemaPath = args[i + 1];
                } else if (args[i].equals("-x")) {
                    _xsdOutputPath = args[i + 1];
                } else if (args[i].equals("-r")) {
                    _rootName = args[i + 1];
                } else if (args[i].equals("-n")) {
                    _targetNamespace = args[i + 1];
                }
            }
        }
        catch(Exception e) {
            errorMessage = e.getMessage();
            _error = true;
        }
    }
    String _jsonSchemaPath = "";
    String _xsdOutputPath = "";
    String _rootName = "Array";
    String _targetNamespace = "http://ethlo.com/schema/array-test-1.0.xsd";

    public String getJsonSchemaPath(){return _jsonSchemaPath;}
    public String getXsdOutputPath(){return _xsdOutputPath;}
    public String getRootName(){return _rootName;}
    public String getTargetNamespace(){return _targetNamespace;}

    public boolean hasErrors() {
        return _error;
    }

    public String showHelp() {
        return "java -jar jsons2xsd.jar [-jxrn] [value]\n" +
                "\n" +
                "-j [path]      : path to the jsonSchema file\n" +
                "-x [path]      : path to use as output (.xsd)\n" +
                "\n" +
                "optional:\n" +
                "-r [name]      : name of a rootelement\n" +
                "-n [namespace] : targetNamespace to use\n";
    }
}
