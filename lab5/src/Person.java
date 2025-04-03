import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.*;
//import java.util.TreeSet;

public class Person implements Comparable<Person>{

    private final String name, surname;
    private final LocalDate birth;
    private final LocalDate death;
    private final Set<Person> children;


    public Person(String name, String surname, LocalDate birth,LocalDate death) throws NegativeLifespanException {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.death = death;
        this.children = new HashSet<>();
        if(death!= null && birth.isAfter(death))
        {
            throw new NegativeLifespanException(this);
        }
    }

    public boolean adopt(Person p) {
        if (this == p)
            return false;

        return children.add(p);
    }

    public Person getYoungestChild() {
        if(children.isEmpty())
            return null;

        return Collections.max(children);
//        Person youngest = null;
//        for (Person child : children) {
//            if (youngest == null || child.birth.isAfter(youngest.birth)) {
//                youngest = child;
//            }
//        }
//        return youngest;
    }

    public List<Person> getChildren() {
        return children.stream().sorted().toList();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.birth.compareTo(o.birth);
    }

    public String getFullName() {
        return name + ' ' + surname;
    }
    public static Person fromCsvline(String csvLine) throws NegativeLifespanException {
        String[] elements = csvLine.split(",", -1);
        String[] fullName = elements[0].split(" ");
        LocalDate birth = LocalDate.parse(elements[1], DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendLiteral(".")
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(".")
                .appendValue(ChronoField.YEAR, 4)
                .toFormatter();
        LocalDate death;
        try {
            death = LocalDate.parse(elements[2], formatter);
        } catch (DateTimeParseException e){
            death = null;
        }
        return new Person(fullName[0], fullName[1], birth, death);
    }

    public static List<Person> fromCSV(String csvFileName)
    {
        List<Person> personList=new ArrayList<>();
        /*try {
            FileReader fr =new FileReader(csvFileName);
            BufferedReader br = new BufferedReader(fr);
            System.out.println(br.readLine());
            br.close();
        }*/
        try(BufferedReader br = new BufferedReader(new FileReader(csvFileName)))
        {
            br.readLine();
            String line;
            while((line=br.readLine()) != null)
            {
                Person readPerson= null;
                try {
                    readPerson = fromCsvline(line);
                    personList.add(readPerson);
                } catch (NegativeLifespanException e) {
                    //ignorujemy i nic nie dodajemy
                    //nie ma potrzeby przerywania wczytywania calego pliku
                    System.err.println(e.getMessage());
                }
                personList.add(readPerson);
            }

        }
//jesli chcielibysmy wczytywac do czasu bledu to tutaj moglibysmy wyrzucic to exception zamiast w petli
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
        return personList;
    }
}