import java.io.*;

public class PlantUmlRunner {
    private static String PlantUmlPath;
    public static void setJarPath(String PlantUmlPath)
    {
        PlantUmlRunner.PlantUmlPath=PlantUmlPath;
    }
    public static void generateDiagram(String umlData,String dirPath,String filename) throws IOException, InterruptedException {
        umlData="@startuml\n"+umlData+"\n@enduml";
        File dir = new File(dirPath);
        if(!dir.exists())
        {
            //mkdir towrzy ostatni katalog, mkdirs tworzy cala sciezke
            if(!dir.mkdirs())
            {
                throw new IOException("nie udalo sie utworzyc katalogu: "+ dir.getAbsolutePath());
            }
        }
        File outDiagram=new File(dir,filename);

        Process plantUmlProcess = new ProcessBuilder("java", "-jar", PlantUmlPath, "-pipe")
                .redirectOutput(outDiagram)
                .start();
        Writer writer = new BufferedWriter(new OutputStreamWriter(plantUmlProcess.getOutputStream()));
        writer.write(umlData);
        writer.close();
        int exitCode = plantUmlProcess.waitFor();
        if(exitCode!=0)
        {
            System.err.println("blad uml: " + exitCode);
        }

    }


}
