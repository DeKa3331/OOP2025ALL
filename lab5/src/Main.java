import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = Person.fromCSV(("family.csv"));
        System.out.println(personList.size());
        for(Person p : personList)
        {
            System.out.println(p);
        }
    }
}