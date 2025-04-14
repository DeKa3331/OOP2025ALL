import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
//import java.util.TreeSet;

public class Person implements Comparable<Person>, Serializable {
    private final String name, surname;
    private final LocalDate birth;
    private final LocalDate death;
    private final Set<Person> children; //= new TreeSet<>();

    public Person(String name, String surname, LocalDate birth, LocalDate death) throws NegativeLifespanException {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.death = death;
        this.children = new HashSet<>();
        if (death != null && birth.isAfter(death)){
            throw new NegativeLifespanException(this);
        }
    }

    public static Person fromCsvline(String csvLine) throws NegativeLifespanException {
        String[] elements = csvLine.split(",", -1);
        String[] fullName = elements[0].split(" ", 2);
        LocalDate birth = LocalDate.parse(elements[1], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendLiteral('.')
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral('.')
                .appendValue(ChronoField.YEAR, 4)
                .toFormatter();
        LocalDate death;
        try {
            death = LocalDate.parse(elements[2], formatter);
        } catch (DateTimeParseException e) {
            death = null;
        }

        return new Person(fullName[0], fullName[1], birth, death);
    }

    public static List<Person> fromCsv(String csvFileName) throws AmbiguousPersonException {
        Map<String, Person> family = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            br.readLine();      // ignorujemy pierwszą linię (nagłówek)
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Person readPerson = fromCsvline(line);
                    if (family.containsKey(readPerson.getFullName())){
                        throw new AmbiguousPersonException(readPerson.getFullName());
                    }
                    family.put(readPerson.getFullName(), readPerson);
                    // dodawanie dzieci
                    String[] elements = line.split(",", -1);
                    Person parentA = family.get(elements[3]);
                    Person parentB = family.get(elements[4]);
                    if(parentA != null) {
                        try {
                            parentA.adopt(readPerson);
                        } catch (ParentingAgeException e){
                            System.out.println(e.getMessage());
                            System.out.println("Are you sure you want to adopt? [Y/n(default)]");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().equalsIgnoreCase("Y")) {
                                e.getParent().children.add(e.getChild());
                            }
                        }
                    }
                    if(parentB != null) {
                        try {
                            parentB.adopt(readPerson);
                        } catch (ParentingAgeException e){
                            System.out.println(e.getMessage());
                            System.out.println("Are you sure you want to adopt? [Y/n(default)]");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().equalsIgnoreCase("Y")) {
                                e.getParent().children.add(e.getChild());
                            }
                        }

                    }
                } catch (NegativeLifespanException e) {
                    // po prostu ignorujemy linię i nie dodajemy nic do listy
                    // nie ma potrzeby przerywania wczytywania całego pliku
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return family.values().stream().toList();
    }

    public static void toBinaryFile(List<Person> personList, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(personList);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<Person> fromBinaryFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
            Object o = in.readObject();
            return (List<Person>) o;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public boolean adopt(Person p) throws ParentingAgeException {
        if (this == p)
            return false;
        if (this.birth.until(p.birth).getYears() < 15 ||
                (this.death != null && this.death.isBefore(p.birth))) {
            throw new ParentingAgeException(this, p);
        }
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

    public String getUMLObject()
    {
        return "object \""+getFullName()+"\" {\n" +
                " birth = "+birth+"\n"+
                (death==null? "" : "death = " + death + "\n") +
                "}\n";

    }
    public static String umlFromList(List<Person> personList, Function<String, String> postProcess, Predicate<Person> condition)
    {
        StringBuilder umlDate= new StringBuilder();
        for(Person p : personList)
        {
            String umlPerson = p.getUMLObject();
            if(condition.test(p))
            {
                umlPerson= postProcess.apply(umlPerson);
            }
            umlDate.append(umlPerson);
        }
        for(Person p : personList)
        {

            for(Person child : p.getChildren())
            {
                umlDate.append("\"").append(child.getFullName()).append("\"")
                        .append(" --> ")
                        .append("\"").append(p.getFullName()).append("\"")
                        .append("\n");
            }
        }
        return umlDate.toString();
    }

    public static List<Person> selectName(List<Person> from,String name)
    {
        List<Person> result=new ArrayList<>();
        for(Person p : from)
        {
            if(p.getFullName().toLowerCase().contains(name.toLowerCase()))
            {
                result.add(p);
            }
        }
        return result;
    }
    public static List<Person> selectName2(List<Person> from,String name)
    {
        return from.stream().filter(p -> p.getFullName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
    public static List<Person> sortedByBirth(List<Person> from)
    {
        return from.stream().sorted(Comparator.comparing(a->a.birth))
                .collect(Collectors.toList());
    }

    public static List<Person> selecctDecreased(List<Person> from)
    {
        return from.stream().filter(p->p.death !=null)
                .sorted((a,b)-> (int) (ChronoUnit.DAYS.between(a.death,b.birth)-ChronoUnit.DAYS.between(b.death,b.birth)))
                .toList();
    }
    public static Person selectOldestAlive(List<Person> from)
    {
        return from.stream()
                .filter(p-> p.death==null)
                .min((a,b)->a.birth.compareTo(b.birth))
        .orElse(null);
    }


}