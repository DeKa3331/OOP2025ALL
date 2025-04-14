import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        PlantUmlRunner.setJarPath("\"C:\\Users\\Jakub\\Downloads\\plantuml-1.2025.2.jar\"");
        String umlData="Alice -> Bob : test";
        PlantUmlRunner.generateDiagram(umlData,"C:\\Users\\Jakub\\Downloads\\","diagram2.png");


        try {
            List<Person> personList = Person.fromCsv("family.csv");

            Person.toBinaryFile(personList, "family.bin");
            List<Person> family = Person.fromBinaryFile("family.bin");

            System.out.println(family.size());
            for (Person p : family) {
                System.out.println(p);
                System.out.println("Dzieci:");
                for (Person child: p.getChildren()) {
                    System.out.println("\t"+child.getFullName());
                }
            }


            String umldata=Person.umlFromList(family,
                    uml -> uml.replaceFirst("\\{","#yellow {"),
                    //Function.identity() - funkcja nic nie zmieniajaca
                    p->Person.selecctDecreased(family).contains(p)
            );

            PlantUmlRunner.generateDiagram(umldata,"C:\\Users\\Jakub\\Downloads\\","diagram3.png");


            System.out.println(Person.selectName(family,"dąb"));
            System.out.println(Person.sortedByBirth(family));
            System.out.println(Person.selecctDecreased(family));
            System.out.println(Person.selectOldestAlive(family));

        } catch (AmbiguousPersonException e){
            System.err.println(e.getMessage());
        }

        // Na 4 punkty -- kolokwium I 2024 (3 kroki -- 1 punkt)
        // czas do terminu I kolokwium
    }
}