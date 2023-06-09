import au.com.bytecode.opencsv.CSVReader;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import qa.util.Logger;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class TestRailIntegration {
    private final String pathCSV = System.getProperty("user.dir") + "/src/main/resources/TestRailCSV/Test_CSV.csv";
    private final String YamlPfad = System.getProperty("user.dir") + "/trcli-config.yml";
    protected Map<String, Integer> ImportAndFilterCSV(String suiteXmlFile) throws Exception {

        Map<String, Integer> map1 = new HashMap<>();
        // to read the value from csv
        CSVReader reader = new CSVReader(new FileReader(pathCSV));
        String[] nextLine;
        int Run_ID_Var = 0;
        int Suite_ID_Var = 0;

        while ((nextLine = reader.readNext()) != null) {
            // to filter the value by xmlfilename - trim to remove all whitespaces in PackageName
            if (nextLine[2].trim().equals(suiteXmlFile)) {
                // PackageName 0  + Suite_id 1  + XmlFileName 2  +  run_id 3
               //   System.out.println(nextLine[0] + nextLine[1] + nextLine[2] + nextLine[3]);
                if (!nextLine[1].trim().equals("")) {
                    Suite_ID_Var = TestIfIntGiven(nextLine[1], "Suite_ID");
                   // Suite_ID_Var = Integer.parseInt(nextLine[1].trim());
                }
                if (!nextLine[3].trim().equals("")) {
                    Run_ID_Var = TestIfIntGiven(nextLine[3], "Run_ID");
                 //   Run_ID_Var = Integer.parseInt(nextLine[3].trim());
                }
            }
        }
        map1.put("Run_ID_Var", Run_ID_Var);
        map1.put("Suite_ID_Var", Suite_ID_Var);
        return map1;
    }

    protected int TestIfIntGiven(String Input, String NameString) throws Exception {
        int num;
        try {
            num = Integer.parseInt(Input.trim());
            // is an integer!
            //  System.out.println(NameString + " is an integer");
        } catch (NumberFormatException e) {
            // not an integer!
            //    System.out.println(NameString + " not an integer");
            throw new Exception(NameString + " not an integer");
        }
        return num;
    }

    protected String PathNameFilter(String SuitName) {
        String filterUntilBackslash = "";
        String reverseSuiteName = "";

       // System.out.println(SuitName.length());
        for (int i = SuitName.length() - 1; i >= 0; i--) {
            //  filterUntilBackslash += String.valueOf(SuitName.charAt(i));
            filterUntilBackslash = (new StringBuilder()).append(filterUntilBackslash).append(SuitName.charAt(i)).toString();
            if (SuitName.charAt(i) == '/' || SuitName.charAt(i) == '\\') {
                for (int j = filterUntilBackslash.length() - 2; j >= 0; j--) {
                    // reverseSuiteName += String.valueOf(filterUntilBackslash.charAt(j));
                    reverseSuiteName = (new StringBuilder()).append(reverseSuiteName).append(filterUntilBackslash.charAt(j)).toString();
                }
                break;
            }
        }
        return reverseSuiteName.trim();
    }

    public void TestrailUpdateCSVtoYml(String suiteXmlFile) throws Exception {

        //  final String YamlPfad = "C:\\Users\\Hasan Abdullah\\IdeaProjects\\testng\\trcli-config.yml";  System.getProperty("user.dir")

        // System.out.println(YamlPfad);
        //System.out.println(suiteXmlFile);
        if(suiteXmlFile.equals("noUpdate")){
            Logger.logger(Level.WARNING,"not update result in TestRail");
          //  throw new Exception("no suiteXmlFile is provided");
           // System.exit(0);
        }
        String SuiteNameFiltered = PathNameFilter(suiteXmlFile);


        Map<String, Integer> SuiteAndRunID;
        SuiteAndRunID = ImportAndFilterCSV(SuiteNameFiltered);

        //read from yaml file
        InputStream inputStream = new FileInputStream(YamlPfad);
        Yaml yaml = new Yaml();
        Map<String, Object> ReadYaml = yaml.load(inputStream);

        final Object host = ReadYaml.get("host");
        final Object project = ReadYaml.get("project");
        final Object username = ReadYaml.get("username");
       // final Object key = ReadYaml.get("key");
        final Object title = SuiteNameFiltered;
        final int run_id = SuiteAndRunID.get("Run_ID_Var");
        final int suite_id = SuiteAndRunID.get("Suite_ID_Var");
        final Object fileResult = ReadYaml.get("file");


        Map<Object, Object> WriteYaml = new LinkedHashMap<>();
        WriteYaml.put("host", host);
        WriteYaml.put("project", project);
        WriteYaml.put("username", username);
      //  WriteYaml.put("key", key);
        WriteYaml.put("title", title);
        // update run_id and suite_id
        if (run_id != 0 && suite_id != 0) {
            WriteYaml.put("run_id", run_id);
            WriteYaml.put("suite_id", suite_id);
              System.out.println("***the results in suite_id: " + suite_id + " and run_id: " + run_id + "  are updated***");
        } if (run_id == 0 && suite_id == 0) {
            System.out.println("no suite_id and run_id is given,they are created");
            //  confirmDialog("no suite_id and run_id is given");
        }
        // no run_id then create run_id and update suite_id
        if (run_id == 0 && suite_id != 0) {
            // System.out.println("no run_id is given, it will be created");
            WriteYaml.put("suite_id", suite_id);
            //  confirmDialog("no run_id");
        }
        String Filter = PathNameFilter(fileResult.toString());
        // WriteYaml.put("file", fileResult);
        WriteYaml.put("file", System.getProperty("user.dir") + "\\report_sample\\TEST-TestSuite.xml");

        //   System.out.println(System.getProperty("user.dir") + "\\report_sample\\" + Filter);

        // to ordere the values as given
        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        //   final Yaml yaml = new Yaml(options);
        yaml = new Yaml(options);

        FileWriter writer = new FileWriter(YamlPfad);
        yaml.dump(WriteYaml, writer);

    }

}
